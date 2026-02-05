package com.devtrack.service;



import com.devtrack.dto.UserLoginDTO;
import com.devtrack.dto.UserRegisterDTO;
import com.devtrack.vo.LoginVO;
import com.devtrack.vo.UserVO;


public interface UserService {
    UserVO register(UserRegisterDTO userRegisterDTO);
    LoginVO login(UserLoginDTO userLoginDTO);
    UserVO getUserInfo(Long userId);
}
