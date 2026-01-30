package com.devtrack.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.devtrack.common.exception.LoginException;
import com.devtrack.common.exception.ServiceException;
import com.devtrack.dto.UserLoginDTO;
import com.devtrack.dto.UserRegisterDTO;
import com.devtrack.entity.User;
import com.devtrack.mapper.UserMapper;
import com.devtrack.service.UserService;
import com.devtrack.service.log.LoginLogService;
import com.devtrack.vo.LoginVO;
import com.devtrack.vo.UserVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.devtrack.common.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final LoginLogService loginLogService;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, LoginLogService loginLogService, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.loginLogService = loginLogService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        // 检查用户名是否已存在
        if (userMapper.exists(new LambdaQueryWrapper<User>().eq(User::getUsername, userRegisterDTO.getUsername()))) {
            throw new ServiceException("用户名已存在");
        }

        // 创建用户实体并设置加密后的密码
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword())); // 加密密码
        user.setRole(userRegisterDTO.getRole()); // 默认角色
        user.setStatus(1); // 默认启用状态

        // 保存用户到数据库
        userMapper.insert(user);
    }

    @Override
    public LoginVO login(UserLoginDTO userLoginDTO) {
        // 检查用户名是否存在
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userLoginDTO.getUsername()));
        if (user == null) {
            // 记录登录失败日志
            HttpServletRequest request = getCurrentRequest();
            String ip = getClientIpAddress(request);
            String userAgent = request != null ? request.getHeader("User-Agent") : "";
            loginLogService.recordLoginLog(
                    userLoginDTO.getUsername(),
                    ip,
                    userAgent,
                    false,
                    "用户名错误"
            );

            throw new LoginException("用户名错误");
        }
        // 检查密码是否正确
        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            // 记录登录失败日志
            HttpServletRequest request = getCurrentRequest();
            String ip = getClientIpAddress(request);
            String userAgent = request != null ? request.getHeader("User-Agent") : "";

            loginLogService.recordLoginLog(
                    userLoginDTO.getUsername(),
                    ip,
                    userAgent,
                    false,
                    "密码错误"
            );

            throw new LoginException("密码错误");
        }
        
        // 生成JWT令牌
        String token = jwtUtil.generateToken(userLoginDTO);

        // 记录登录成功日志
        HttpServletRequest request = getCurrentRequest();
        String ip = getClientIpAddress(request);
        String userAgent = request != null ? request.getHeader("User-Agent") : "";

        loginLogService.recordLoginLog(
                userLoginDTO.getUsername(),
                ip,
                userAgent,
                true,
                null
        );

        // 构建登录响应对象
        LoginVO loginVO = new LoginVO();
        loginVO.setMessage("登录成功");
        loginVO.setUsername(userLoginDTO.getUsername());
        loginVO.setToken(token);

        return loginVO;
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }

        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setRole(user.getRole());
        userVO.setStatus(user.getStatus());

        return userVO;
    }

    private HttpServletRequest getCurrentRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attributes != null ? attributes.getRequest() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "";
        }

        String xip = request.getHeader("X-Real-IP");
        String xfor = request.getHeader("X-Forwarded-For");
        if (xfor != null && !xfor.isEmpty() && !"unknown".equalsIgnoreCase(xfor)) {
            int index = xfor.indexOf(",");
            if (index != -1) {
                return xfor.substring(0, index);
            } else {
                return xfor;
            }
        }
        if (xip != null && !xip.isEmpty() && !"unknown".equalsIgnoreCase(xip)) {
            return xip;
        }
        return request.getRemoteAddr();
    }
}