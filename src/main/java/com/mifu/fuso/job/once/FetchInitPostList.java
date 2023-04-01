package com.mifu.fuso.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mifu.fuso.model.entity.Post;
import com.mifu.fuso.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取初始帖子列表
 * @author <a href="https://github.com/mifuCN">米芾</a>
 * @from <a href="https://201314.tk">我的博客</a>
 */
// 取消@Component注释后,每次启动 springboot 顶目时会执行一次run方法
// @Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Override
    public void run(String... args) {
        {
            // 1.获取数据
            String json = "{\n" +
                    "  \"current\": 1,\n" +
                    "  \"pageSize\": 8,\n" +
                    "  \"sortField\": \"thumbNum\",\n" +
                    "  \"sortOrder\": \"descend\",\n" +
                    "  \"category\": \"文章\",\n" +
                    "  \"reviewStatus\": 1\n" +
                    "}";
            String url = "https://www.code-nav.cn/api/post/search/page/vo";
            String result = HttpRequest.post(url)
                    .body(json)
                    .execute().body();
            System.out.println(result);

            // 2.获取到的 json 数据转为对象
            Map<String, Object> map = JSONUtil.toBean(result, Map.class);
            JSONObject data = (JSONObject) map.get("data");
            JSONArray records = (JSONArray) data.get("records");
            List<Post> postList = new ArrayList<>();
            for (Object record : records) {
                JSONObject tempRecord = (JSONObject) record;
                Post post = new Post();
                post.setTitle(tempRecord.getStr("title"));
                post.setContent(tempRecord.getStr("content"));
                JSONArray tags = (JSONArray) tempRecord.get("tags");
                List<String> tagList = tags.toList(String.class);
                post.setTags(JSONUtil.toJsonStr(tagList));
                post.setUserId(1L);
                postList.add(post);
            }
            System.out.println(postList);

            // 3.数据入库(使用MP封装好的方法)
            boolean flag = postService.saveBatch(postList);
            if (flag) {
                log.info("获取初始化帖子列表成功，条数={}", postList.size());
            } else {
                log.error("获取初始化帖子列表失败");
            }
        }
    }
}
