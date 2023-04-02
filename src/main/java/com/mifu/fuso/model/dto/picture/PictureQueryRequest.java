package com.mifu.fuso.model.dto.picture;

import com.mifu.fuso.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author mifuCN
 * @Date 2023-04-02 09:25
 **/
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {
    /**
     * 搜索词
     */
    private String searchText;
    private static final long serialVersionUID = 1L;
}
