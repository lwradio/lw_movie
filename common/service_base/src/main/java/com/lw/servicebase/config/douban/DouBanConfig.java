package com.lw.servicebase.config.douban;

import com.lw.servicebase.config.douban.entity.DouBanTypeEnum;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class DouBanConfig {
    private static final Map<String, String> map = new HashMap<>();
    private static final Map<String, String> mapTitle = new HashMap<>();

    //配置请求信息
    private static RequestConfig getConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(10000)   //创建连接的最长时间，单位毫秒
                .setConnectionRequestTimeout(10000) //设置获取链接的最长时间，单位毫秒
                .setSocketTimeout(10000)   //设置数据传输的最长时间，单位毫秒
                .build();
    }

    /**
     * 根据请求地址下载页面数据
     *
     * @return //页面数据
     * @throws URISyntaxException
     */
    private static String doGetHtml() throws URISyntaxException {
        //创建HTTPClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置请求地址
        //创建URLBuilder
        String baseUrl = "https://movie.douban.com/j/search_subjects";
//        String baseUrl = "https://movie.douban.com/j/new_search_subjects";
        URIBuilder uriBuilder = new URIBuilder(baseUrl);

        //设置参数
        if (!map.isEmpty()) {
            for (String key : map.keySet()) {
                uriBuilder.setParameter(key, map.get(key));
            }
        }

        //创建HTTPGet对象，设置url访问地址
        //uriBuilder.build()得到请求地址
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        //设置请求头信息
        if (!mapTitle.isEmpty()) {
            for (String key : mapTitle.keySet()) {
                httpGet.addHeader(key, mapTitle.get(key));
            }
        }

        //设置请求信息
        httpGet.setConfig(getConfig());

        //使用HTTPClient发起请求，获取response
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            //解析响应
            if (response.getStatusLine().getStatusCode() == 200) {
                //判断响应体Entity是否不为空，如果不为空就可以使用EntityUtils
                if (response.getEntity() != null) {
                    return EntityUtils.toString(response.getEntity(), "utf8");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭response
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<String> startLoad(DouBanTypeEnum type, String limit, String start) throws URISyntaxException {
        map.put("tag", "热门");
        map.put("sort", "recommend");
        map.put("type", type.name());
        map.put("page_limit", limit);
        map.put("page_start", start);
        //设置请求头
        mapTitle.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        mapTitle.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0");
        mapTitle.put("Cookie", "bid=QNoG_zn4mZY; _pk_id.100001.4cf6=6209709719896af7.1575619506.2.1575940374.1575621362.; __utma=30149280.1889677372.1575619507.1575619507.1575940335.2; __utmz=30149280.1575619507.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utma=223695111.986359939.1575619507.1575619507.1575940335.2; __utmz=223695111.1575619507.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __yadk_uid=QVSP2uvzzDBrpnvHKzZpZEWJnuARZ4aL; ll=\"118259\"; _vwo_uuid_v2=D1FC45CAE50CF6EE38D245C68D7CECC4F|e8d1db73f4c914f0b0be7ed85ac50d14; trc_cookie_storage=taboola%2520global%253Auser-id%3D690a21c0-9ad9-4f8d-b997-f0decb3cfc9b-tuct4e39874; _pk_ses.100001.4cf6=*; ap_v=0,6.0; __utmb=30149280.0.10.1575940335; __utmc=30149280; __utmb=223695111.0.10.1575940335; __utmc=223695111; __gads=ID=2f06cb0af40206d0:T=1575940336:S=ALNI_Ma4rv9YmqrkIUNXsIt5E7zT6kZy2w");
        List<String> re = new ArrayList<>();
        String html = doGetHtml();
        if (StringUtils.isEmpty(html)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(html);
        JSONArray jsonArray = jsonObject.getJSONArray("subjects");
        for (Object o : jsonArray) {
            JSONObject json = (JSONObject) o;
            re.add(json.getString("title"));
        }
        return re;
    }

    public static Integer getDouBanId(Map<String, Object> parameters) {

        return 0;
    }
}
