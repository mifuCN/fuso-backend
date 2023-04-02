package com.mifu.fuso.model.vo;

import com.mifu.fuso.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜索
 * @author <a href="https://github.com/mifuCN">米芾</a>
 * @from <a href="https://201314.tk">我的博客</a>
 */
@Data
public class SearchVO implements Serializable {
    private List<UserVO> userList;
    private List<PostVO> postList;
    private List<Picture> pictureList;
    private List<?> dataList;
    private static final long serialVersionUID = 1L;
}
