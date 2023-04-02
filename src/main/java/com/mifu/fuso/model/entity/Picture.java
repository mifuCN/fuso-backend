package com.mifu.fuso.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装从Bing返回的图片和标题
 * @Author mifuCN
 * @Date 2023-04-01 22:33
 **/
@Data
public class Picture implements Serializable {
    private String title;
    private String url;
    private static final long serialVersionUID = 1L;
}
