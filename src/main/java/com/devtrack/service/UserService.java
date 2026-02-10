package com.devtrack.service;



import com.devtrack.dto.PageInfoDTO;
import com.devtrack.dto.UserLoginDTO;
import com.devtrack.dto.UserRegisterDTO;
import com.devtrack.vo.LoginVO;
import com.devtrack.vo.PageInfoVO;
import com.devtrack.vo.UserVO;


public interface UserService {
    void register(UserRegisterDTO userRegisterDTO);
    LoginVO login(UserLoginDTO userLoginDTO);
    UserVO getUserInfo(Long userId);
    PageInfoVO getuserList(PageInfoDTO pageInfoDTO);
}
