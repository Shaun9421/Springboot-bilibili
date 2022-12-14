package com.shaun.fansQuery.service;


import com.alibaba.fastjson.JSONObject;
import com.shaun.fansQuery.config.HttpUtils;
import com.shaun.fansQuery.entity.FansQueryEntity;
import com.shaun.fansQuery.entity.JsonTest;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class FansQueryService {

    @Autowired
    private  MailService mailService;
    public void fansQuery() throws Exception {
        /**
         *     * @param host   主机地址
         *      * @param path  请求路径
         *      * @param method    请求方法 get
         *      * @param headers   请求头数据,使用map封装
         *      * @param querys    请求参数,使用map封装
         */

        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> querys = new HashMap<>();

        String vmid = "330850792";
        String host = "https://api.bilibili.com/x/relation/stat?vmid="+vmid+"&jsonp=jsonp";

        String path = "";

        String method = "get";


        HttpResponse httpResponse = HttpUtils.doGet(host, path, method, headers, querys);
//        System.out.println("httpResponse"+httpResponse);

        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            System.out.println("访问成功");
            //获取结果转换为json类型
            String s = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(s+"s");
            JsonTest person = JSONObject.parseObject(s, JsonTest.class);
            for (FansQueryEntity datum : person.getData()){
                System.out.println("粉丝数是:"+datum.getFollower());

                if (!ObjectUtils.isEmpty(datum.getFollower())){
                    String to = "shaun9421@126.com";
                    String subject = "新增粉丝，详情查看";
                    String content = "<html>\n"+
                            "<body>\n"+
                            "<h3 style='color:blue'>涨粉丝了好兄弟 你现在有"+datum.getFollower()+"个粉丝</h3>\n"+
                            "</body>\n"+
                            "</html>"
                            ;



                mailService.sendHTMLMail(to, subject, content);
                }
            }
        }

    }
}
