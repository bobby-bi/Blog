package com.bi.service;

import com.bi.entity.User;

public interface UserService {
    User checkUser(String username,String password);
}
