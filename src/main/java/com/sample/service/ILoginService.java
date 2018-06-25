package com.sample.service;

import java.io.IOException;

public interface ILoginService {

    String validateUser(String userName, String userPassword) throws IOException;

}
