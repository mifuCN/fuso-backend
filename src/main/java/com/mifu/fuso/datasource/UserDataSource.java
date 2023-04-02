package com.mifu.fuso.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mifu.fuso.model.dto.user.UserQueryRequest;
import com.mifu.fuso.model.vo.UserVO;
import com.mifu.fuso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserDataSource implements DataSource<UserVO> {

    @Resource
    private UserService userService;

    /**
     * 这个就是适配器模式的简单实现
     * 也就是别的项目的接口调用参数和你预期的不一致(不适配)，就自己编写一个适配器来转换
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        // 要的是userQueryRequest,那么我们就把请求参数转为userQueryRequest
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize(pageSize);
        // 转换好之后就开始调用
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
        return userVOPage;
    }

}