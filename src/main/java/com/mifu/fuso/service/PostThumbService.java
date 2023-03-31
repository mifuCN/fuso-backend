package com.mifu.fuso.service;

import com.mifu.fuso.model.entity.PostThumb;
import com.mifu.fuso.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 帖子点赞服务
 *
 * @author <a href="https://github.com/mifuCN">米芾</a>
 * @from <a href="https://201314.tk">我的博客</a>
 */
public interface PostThumbService extends IService<PostThumb> {

    /**
     * 点赞
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostThumb(long postId, User loginUser);

    /**
     * 帖子点赞（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostThumbInner(long userId, long postId);
}
