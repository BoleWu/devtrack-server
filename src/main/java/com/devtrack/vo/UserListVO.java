package com.devtrack.vo;

import lombok.Data;
import java.util.List;

/**
 * 用户列表视图对象
 */
@Data
public class UserListVO {
    private List<UserVO> users;
    private Long total;
    private Integer currentPage;
    private Integer pageSize;
}