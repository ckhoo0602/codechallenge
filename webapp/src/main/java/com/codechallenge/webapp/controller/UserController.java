package com.codechallenge.webapp.controller;

import com.codechallenge.integration.request.UpdateUserReq;
import com.codechallenge.integration.response.GetUserResp;
import com.codechallenge.integration.response.UserDTO;
import com.codechallenge.webapp.service.ApiService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private ApiService apiService;

    @GetMapping
    public String listUser(Model model) {
        model.addAttribute("users", apiService.listUsers().getUsers());
        return "list-user";
    }

    @GetMapping("/search")
    public String searchUser(@RequestParam("name") String name, Model model) {
        model.addAttribute("users", apiService.searchUserByName(name).getUsers());
        return "list-user";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        UserDTO userDTO = Optional.ofNullable(apiService.getUserById(id))
                .map(GetUserResp::getUser).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", userDTO);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid UpdateUserReq updateUserReq, BindingResult result, Model model) {
        if (result.hasErrors()) {
            updateUserReq.setId(id);
            model.addAttribute("user", updateUserReq);
            return "update-user";
        }
        apiService.updateUser(id, updateUserReq);
        model.addAttribute("users", apiService.listUsers().getUsers());
        return "redirect:/listUser";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        apiService.deleteUser(id);
        model.addAttribute("users", apiService.listUsers().getUsers());
        return "redirect:/listUser";
    }
}
