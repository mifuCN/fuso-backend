package com.mifu.fuso.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mifu.fuso.model.dto.post.PostQueryRequest;
import com.mifu.fuso.model.vo.PostVO;
import com.mifu.fuso.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class PostDataSource implements DataSource<PostVO> {

    @Resource
    private PostService postService;

    /**
     * 这个就是适配器模式的简单实现
     * 也就是别的项目的接口调用参数和你预期的不一致(不适配)，就自己编写一个适配器来转换
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        // postQueryRequest和request,那么我们就把请求参数转为postQueryRequest,这个request要具体分析一下是干嘛的
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setPageSize(pageSize);
        // 分析request的作用，做出响应的适配
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        // 转换好之后就开始调用
        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
        return postVOPage;
    }

}