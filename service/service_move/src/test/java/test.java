import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lw.moveservice.entity.front.VodPlayer;
import com.lw.servicebase.config.douban.DouBanConfig;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class test {
    //配置请求信息
    private static RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(10000)   //创建连接的最长时间，单位毫秒
                .setConnectionRequestTimeout(10000) //设置获取链接的最长时间，单位毫秒
                .setSocketTimeout(10000)   //设置数据传输的最长时间，单位毫秒
                .build();
        return config;
    }
    /**
     * 根据请求地址下载页面数据
     * @param url  请求路径
     * @param map  请求参数
     * @param mapTile  请求头
     * @return //页面数据
     * @throws URISyntaxException
     */
    public static String doGetHtml(String url, Map<String, String> map, Map<String, String> mapTile) throws URISyntaxException {
        //创建HTTPClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置请求地址
        //创建URLBuilder
        URIBuilder uriBuilder = new URIBuilder(url);

        //设置参数
        if(!map.isEmpty()){
            for(String key : map.keySet()){
                uriBuilder.setParameter(key, map.get(key));
            }
        }

        //创建HTTPGet对象，设置url访问地址
        //uriBuilder.build()得到请求地址
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        //设置请求头信息
        if(!mapTile.isEmpty()){
            for(String key : mapTile.keySet()){
                httpGet.addHeader(key, mapTile.get(key));
            }
        }

        //设置请求信息
        httpGet.setConfig(getConfig());
        System.out.println("发起请求的信息："+httpGet);

        //使用HTTPClient发起请求，获取response
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            //解析响应
            if(response.getStatusLine().getStatusCode() == 200){
                //判断响应体Entity是否不为空，如果不为空就可以使用EntityUtils
                if(response.getEntity() != null) {
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            //关闭response
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    public boolean checkXMatrix(int[][] grid) {
        int num = grid[0].length-1;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[i].length;j++){
                if(i==j&&grid[i][j]==0){
                    return false;
                }else if(grid[i][num]==0){
                    return false;
                }else if(i!=j&&j!=num&&grid[i][j]!=0){
                    return false;
                }
                num--;
            }
        }
        return true;
    }
    @Test
    public void run() {
    int [][] grid ={{2,0,0,1},{0,3,1,0},{0,5,2,0},{4,0,0,2}};
    checkXMatrix(grid);
    }
    }

