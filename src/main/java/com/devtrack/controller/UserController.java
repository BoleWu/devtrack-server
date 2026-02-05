package com.devtrack.controller;

import com.devtrack.common.result.R;
import com.devtrack.dto.UserLoginDTO;
import com.devtrack.dto.UserRegisterDTO;
import com.devtrack.service.UserService;
import com.devtrack.vo.LoginVO;
import com.devtrack.vo.UserVO;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import javax.security.auth.login.LoginException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;
    
    @PostMapping("/register")
    public R<UserVO> register(@Validated @RequestBody UserRegisterDTO dto){
       return R.success(userService.register(dto));

    }
    @PostMapping("/login")
    public R<LoginVO> login(@Validated @RequestBody UserLoginDTO logindto, HttpServletRequest request) {
        //        return R.ok(loginVO);
        return R.success(userService.login(logindto));
    }
    
    @GetMapping("/profile")
    public R<UserVO> getProfile() {
        // 实际应用中这里会从认证上下文中获取用户ID
        // 这里为了演示使用固定ID
        UserVO userVO = userService.getUserInfo(1L);
        return R.success(userVO);
    }
}