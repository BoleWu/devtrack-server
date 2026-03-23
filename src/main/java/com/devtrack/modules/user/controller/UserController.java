package com.devtrack.modules.user.controller;

import com.devtrack.common.result.R;
import com.devtrack.modules.shared.dto.PageInfoDTO;
import com.devtrack.modules.user.dto.UserLoginDTO;
import com.devtrack.modules.user.dto.UserRegisterDTO;
import com.devtrack.modules.user.service.UserService;
import com.devtrack.modules.user.vo.LoginVO;
import com.devtrack.modules.shared.vo.PageInfoVO;
import com.devtrack.modules.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;
    
    @PostMapping("/register")
    public R<?> register(@Validated @RequestBody UserRegisterDTO dto){
         userService.register(dto);
       return R.success("注册成功");

    }
    @PostMapping("/login")
    public R<?> login(@Validated @RequestBody UserLoginDTO logindto, HttpServletRequest request) {
        //        return R.ok(loginVO);
        return R.success(userService.login(logindto));
    }
    

    @RequestMapping("/queryUserInfoByList")
    public R<PageInfoVO> queryUserInfoByList(@Validated @RequestBody PageInfoDTO pageInfoDTO) {
        return R.success(userService.getuserList(pageInfoDTO));
    }
}

