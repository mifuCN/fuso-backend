package com.mifu.fuso.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface DataSource<T> {

    /**
     * 搜索功能
     * 这里主要是定义一个适配器模式的规范,需要适配器来实现这个规范
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize);

}