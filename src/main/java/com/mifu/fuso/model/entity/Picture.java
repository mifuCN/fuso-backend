package com.mifu.fuso.model.entity;

import lombok.Data;

/**
 * 封装从Bing返回的图片和标题
 * @Author mifuCN
 * @Date 2023-04-01 22:33
 **/
@Data
public class Picture {
    private String title;
    private String url;
}
