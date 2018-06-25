package com.sample.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.model.ApplicationUser;

@Service
public class LoginService implements ILoginService {

    @Value("${application.users}")
    String applicationUsers;

    @Override
    public String validateUser(String userName, String userPassword) throws IOException {

        File file = ResourceUtils.getFile("classpath:" + applicationUsers);
        String response = FileUtils.readFileToString(file, Charset.forName(StandardCharsets.UTF_8.name()));
        ObjectMapper mapper = new ObjectMapper();
        Base64.Decoder dec = Base64.getDecoder();
        byte[] decbytes = dec.decode(userPassword);
        String updatedPassword = new String(decbytes);
        List<ApplicationUser> allUsers = mapper.convertValue(mapper.readTree(response),
                new TypeReference<List<ApplicationUser>>() {
                });
        List<ApplicationUser> validUser = allUsers.stream()
                .filter(user -> StringUtils.equalsIgnoreCase(user.getUserName(), userName.trim())
                        && StringUtils.equalsIgnoreCase(user.getPassword(), updatedPassword.trim()))
                .collect(Collectors.toList());
        return CollectionUtils.isNotEmpty(validUser) ? "ValidUser" : "Invalid User";

    }

}
