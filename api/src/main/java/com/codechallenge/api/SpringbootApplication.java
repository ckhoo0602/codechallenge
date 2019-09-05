package com.codechallenge.api;

import com.codechallenge.api.config.ApplicationProperties;
import com.codechallenge.api.db.entity.User;
import com.codechallenge.api.db.entity.UserAddress;
import com.codechallenge.api.db.entity.UserCompany;
import com.codechallenge.api.db.repo.UserRepo;
import com.codechallenge.api.model.UserImportModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class SpringbootApplication {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepo userRepo;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @PostConstruct
    public void constructData() throws IOException {
        String inputFile = getFile("user.json");
        List<UserImportModel> payloads = objectMapper.readValue(inputFile, new TypeReference<List<UserImportModel>>() {
        });
        for (UserImportModel userImportModel : payloads) {
            User user = new User();
            user.setId(userImportModel.getId());
            user.setName(userImportModel.getName());
            user.setUsername(userImportModel.getUsername());
            user.setEmail(userImportModel.getEmail());
            user.setPhone(userImportModel.getPhone());
            user.setWebsite(userImportModel.getWebsite());
            if (userImportModel.getCompany() != null) {
                UserImportModel.Company company = userImportModel.getCompany();
                UserCompany userCompany = new UserCompany();
                userCompany.setBs(company.getBs());
                userCompany.setCatchPhrase(company.getCatchPhrase());
                userCompany.setName(company.getName());
                userCompany.setUser(user);
                user.setCompany(userCompany);
            }
            if (userImportModel.getAddress() != null) {
                UserImportModel.Address address = userImportModel.getAddress();
                UserAddress userAddress = new UserAddress();
                userAddress.setCity(address.getCity());
                userAddress.setStreet(address.getStreet());
                userAddress.setZipcode(address.getZipcode());
                userAddress.setSuite(address.getSuite());
                UserImportModel.Address.Geo geo = Optional.ofNullable(userImportModel)
                        .map(UserImportModel::getAddress)
                        .map(UserImportModel.Address::getGeo).orElse(null);
                if (geo != null) {
                    UserAddress.Geo geoAddress = new UserAddress.Geo();
                    geoAddress.setLat(geo.getLat());
                    geoAddress.setLng(geo.getLng());
                    userAddress.setGeo(geoAddress);
                }
                userAddress.setUser(user);
                user.setAddress(userAddress);
            }
            userRepo.save(user);
        }
    }

    private String getFile(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        return readFromInputStream(inputStream);
    }

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
