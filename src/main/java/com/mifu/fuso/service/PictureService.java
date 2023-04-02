package com.mifu.fuso.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mifu.fuso.model.entity.Picture;

/**
 * 图片服务
 * @Author mifuCN
 * @Date 2023-04-02 09:30
 **/
public interface PictureService {
    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}
