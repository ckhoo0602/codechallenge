package com.codechallenge.api.controller;

import com.codechallenge.api.config.AsyncConfig;
import com.codechallenge.api.db.entity.User;
import com.codechallenge.api.db.entity.UserAddress;
import com.codechallenge.api.db.entity.UserCompany;
import com.codechallenge.integration.request.UpdateUserReq;
import com.codechallenge.integration.response.GetUserResp;
import com.codechallenge.integration.response.ListUserResp;
import com.codechallenge.integration.response.SearchUserResp;
import com.codechallenge.integration.response.UserDTO;
import com.codechallenge.api.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Async(AsyncConfig.TASK_EXECUTOR_CONTROLLER)
    @GetMapping
    public CompletableFuture<ResponseEntity<ListUserResp>> listUser(final Pageable paging) {
        return userService.findAll(paging)
                .thenApply(page -> {
                    List<User> users = page.getContent();
                    ListUserResp resp = new ListUserResp();
                    for (User user : users) {
                        resp.getUsers().add(mapToUserDTO(user));
                    }
                    return ResponseEntity.ok(resp);
                });
    }

    @Async(AsyncConfig.TASK_EXECUTOR_CONTROLLER)
    @GetMapping("/{userId}")
    public CompletableFuture<ResponseEntity<GetUserResp>> getUserById(@PathVariable final Long userId) {
        return userService.findOneById(userId)
                .thenApply(user -> {
                    if (user == null) {
                        return ResponseEntity.notFound().build();
                    }
                    GetUserResp resp = new GetUserResp();
                    resp.setUser(mapToUserDTO(user));
                    return ResponseEntity.ok(resp);
                });
    }

    @Async(AsyncConfig.TASK_EXECUTOR_CONTROLLER)
    @GetMapping("/search")
    public CompletableFuture<ResponseEntity<SearchUserResp>> searchByName(@RequestParam String name) {
        return userService.findByName(name)
                .thenApply(users -> {
                    SearchUserResp resp = new SearchUserResp();
                    for (User user : users) {
                        resp.getUsers().add(mapToUserDTO(user));
                    }
                    return ResponseEntity.ok(resp);
                });
    }

    @PutMapping("/{userId}")
    public ResponseEntity update(@PathVariable final Long userId, @Valid @RequestBody UpdateUserReq updateUserReq)
            throws ExecutionException, InterruptedException {
        User user = userService.findOneById(userId).get();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setName(updateUserReq.getName());
        user.setEmail(updateUserReq.getEmail());
        userService.update(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity delete(@PathVariable final Long userId)
            throws ExecutionException, InterruptedException {
        User user = userService.findOneById(userId).get();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(user);
        return ResponseEntity.noContent().build();
    }

    private UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPhone(user.getPhone());
        userDTO.setUsername(user.getUsername());
        userDTO.setWebsite(user.getWebsite());
        if (user.getAddress() != null) {
            UserAddress userAddress = user.getAddress();
            UserDTO.UserAddressDTO userAddressDTO = new UserDTO.UserAddressDTO();
            userAddressDTO.setCity(userAddress.getCity());
            userAddressDTO.setStreet(userAddress.getStreet());
            userAddressDTO.setZipcode(userAddress.getZipcode());
            userAddressDTO.setSuite(userAddress.getSuite());
            if (userAddress.getGeo() != null) {
                UserDTO.UserAddressDTO.Geo geo = new UserDTO.UserAddressDTO.Geo();
                geo.setLat(userAddress.getGeo().getLat());
                geo.setLng(userAddress.getGeo().getLng());
                userAddressDTO.setGeo(geo);
            }
            userDTO.setAddress(userAddressDTO);
        }
        if (user.getCompany() != null) {
            UserCompany userCompany = user.getCompany();
            UserDTO.UserCompanyDTO userCompanyDTO = new UserDTO.UserCompanyDTO();
            userCompanyDTO.setBs(userCompany.getBs());
            userCompanyDTO.setCatchPhrase(userCompany.getCatchPhrase());
            userCompanyDTO.setName(userCompany.getName());
            userDTO.setCompany(userCompanyDTO);
        }
        return userDTO;
    }
}
