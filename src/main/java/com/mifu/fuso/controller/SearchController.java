package com.mifu.fuso.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mifu.fuso.common.BaseResponse;
import com.mifu.fuso.common.ErrorCode;
import com.mifu.fuso.common.ResultUtils;
import com.mifu.fuso.exception.BusinessException;
import com.mifu.fuso.model.dto.post.PostQueryRequest;
import com.mifu.fuso.model.dto.search.SearchRequest;
import com.mifu.fuso.model.dto.user.UserQueryRequest;
import com.mifu.fuso.model.entity.Picture;
import com.mifu.fuso.model.vo.PostVO;
import com.mifu.fuso.model.vo.SearchVO;
import com.mifu.fuso.model.vo.UserVO;
import com.mifu.fuso.service.PictureService;
import com.mifu.fuso.service.PostService;
import com.mifu.fuso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 聚合搜索
 * @author <a href="https://github.com/mifuCN">米芾</a>
 * @from <a href="https://201314.tk">我的博客</a>
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
    @Resource
    private UserService userService;
    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();

        CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
            return userVOPage;
        });

        CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
            return postVOPage;
        });

        CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
            Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
            return picturePage;
        });

        CompletableFuture.allOf(userTask, postTask, pictureTask).join();
        try {
            Page<UserVO> userVOPage = userTask.get();
            Page<PostVO> postVOPage = postTask.get();
            Page<Picture> picturePage = pictureTask.get();
            SearchVO searchVO = new SearchVO();
            searchVO.setUserList(userVOPage.getRecords());
            searchVO.setPostList(postVOPage.getRecords());
            searchVO.setPictureList(picturePage.getRecords());
            return ResultUtils.success(searchVO);
        } catch (Exception e) {
            log.error("查询异常,e");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
        }
    }
}
