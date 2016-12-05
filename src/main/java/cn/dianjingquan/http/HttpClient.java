package cn.dianjingquan.http;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by tommy on 2016-10-31.
 * utils
 * cn.dianjingquan.http.
 */
public class HttpClient {
    public static HttpClient getInstance() { return instance;}

    public Map<Integer, String> ClientPut(String url, JsonNode json) throws Exception {
        HttpPut p = new HttpPut(url);
        ObjectMapper objectMapper = new ObjectMapper();
        StringEntity stringEntity = new StringEntity(objectMapper.writeValueAsString(json), "UTF-8");
        stringEntity.setContentType("application/json;charset=UTF-8");
        p.setEntity(stringEntity);

        return SendHttp(p);
    }

    public Map<Integer, String> ClientPost(String url, JsonNode json) throws Exception {
        HttpPost p = new HttpPost(url);
        ObjectMapper objectMapper = new ObjectMapper();
        StringEntity stringEntity = new StringEntity(objectMapper.writeValueAsString(json), "UTF-8");
        stringEntity.setContentType("application/json;charset=UTF-8");
        p.setEntity(stringEntity);

        return SendHttp(p);
    }

    public Map<Integer, String> ClientPost(String url, String params) throws Exception {
        HttpPost p = new HttpPost(url);

        StringEntity stringEntity = new StringEntity(params, "UTF-8");
        stringEntity.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
        p.setEntity(stringEntity);

        return SendHttp(p);
    }

    public Map<Integer, String> ClientPost(String url) throws Exception {
        HttpPost p = new HttpPost(url);
        return SendHttp(p);
    }

    public Map<Integer, String> ClientPost(String url, File file) throws Exception {
        HttpPost p = new HttpPost(url);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart("file", new FileBody(file, ContentType.MULTIPART_FORM_DATA));
        //builder.setCharset(Charset.forName("UTF-8"));TODO

        p.setEntity(builder.build());

        return SendHttp(p);
    }

    public Map<Integer, String> ClientGet(String url) throws Exception {
        HttpGet p = new HttpGet(url);
        return SendHttp(p);
    }

    public Map<Integer, String> ClientDelete(String url) throws Exception {
        HttpDelete httpDelete = new HttpDelete(url);
        return SendHttp(httpDelete);
    }

    private <T extends HttpRequestBase> Map<Integer, String> SendHttp(T req) throws Exception {
        CloseableHttpClient client = null;
        CloseableHttpResponse res = null;
        HttpEntity body = null;
        String resContent = null;
        int statusCode = 200;
        Map<Integer, String> result = new HashMap<Integer, String>();
        try {
            client = HttpClients.createDefault();
            req.setConfig(requestConfig);
            res = client.execute(req);
            body = res.getEntity();
            statusCode = res.getStatusLine().getStatusCode();
            resContent = EntityUtils.toString(body, "UTF-8");
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (res != null) res.close();
                if (client != null) client.close();
            } catch (IOException ex) {
                throw ex;
            }
        }

        result.put(statusCode, resContent);
        return result;
    }

    private RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();

    private static HttpClient instance = new HttpClient();

    private HttpClient() {}
}
