package org.dromara.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dromara.common.core.utils.StringUtils;

public class HttpClientUtils {

    /**
     *
     * @param url
     *            传递过来的地址
     * @param params
     *            参数信息（参数仅为json格式）
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doGet(String url, String params) throws ClientProtocolException, IOException {
        // 用来接收响应信息
        String response = null;
        // 创建HttpClient
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        // 执行请求
        HttpResponse httpResponse = httpClient.execute(httpGet);
        // 判断响应值是否是200
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            // 请求和响应都成功了
            // 调用getEntity()方法获取到一个HttpEntity实例
            HttpEntity entity = httpResponse.getEntity();
            // 用EntityUtils.toString()这个静态方法将HttpEntity转换成字符串
            // 防止服务器返回的数据带有中文,所以在转换的时候将字符集指定成utf-8就可以了
            response = EntityUtils.toString(entity, "utf-8");
        }
        return response;
    }

    /**
     *
     * @param url
     *            请求地址
     * @param params
     *            元素参数
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doPost(String url, String params) throws ClientProtocolException, IOException {
        // 用来接收响应信息
        String response = null;
        // 创建HttpClient
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        //设置请求头为Json形式
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");

        if (StringUtils.isNotBlank(params)) {
            httpPost.setEntity(new StringEntity(params, "utf-8"));
        }
        HttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            // 请求和响应都成功了
            // 调用getEntity()方法获取到一个HttpEntity实例
            HttpEntity entity = httpResponse.getEntity();
            // 用EntityUtils.toString()这个静态方法将HttpEntity转换成字符串
            // 防止服务器返回的数据带有中文,所以在转换的时候将字符集指定成utf-8就可以了
            response = EntityUtils.toString(entity, "utf-8");
        }
        return response;
    }
}
