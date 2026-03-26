package com.devtrack.modules.user.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.devtrack.modules.shared.dto.PageInfoDTO;
import com.devtrack.modules.user.dto.UserLoginDTO;
import com.devtrack.modules.user.dto.UserRegisterDTO;
import com.devtrack.modules.user.vo.LoginVO;
import com.devtrack.modules.shared.vo.PageInfoVO;
import com.devtrack.modules.user.vo.UserListVO;
import com.devtrack.modules.user.vo.UserVO;


public interface UserService {
    void register(UserRegisterDTO userRegisterDTO);
    LoginVO login(UserLoginDTO userLoginDTO);
    UserVO getUserInfo(Long userId);
    IPage<UserListVO> getuserList(PageInfoDTO pageInfoDTO);
}


