package com.seokjin.kim.library;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class HttpClientCustom {
    private static String userAgent;
    private static final HttpClient HTTP_CLIENT;
    private static final Gson GSON_OBJ = new Gson();
    /**
     * HttpClient 기본 세팅 300개까지 허용, 2초 후 타임아웃
     */
    static {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(300);
        connectionManager.setDefaultMaxPerRoute(50);
        
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(2000)
                .setSoKeepAlive(true)
                .setTcpNoDelay(true)
                .setSoReuseAddress(true)
                .build();
        
        HTTP_CLIENT = HttpClients.custom().setConnectionManager(connectionManager).setDefaultSocketConfig(socketConfig).build();
        userAgent = "Mozila/5.0";
    };
    
    private HttpClientCustom() {}
    
    /**
     * Http 통신 가능한 객체 얻기
     * @param 
     * @return HttpClient
     */
    public static HttpClient getInstance() {
        return HTTP_CLIENT;
    }
    
    /**
     * Http통신 후 결과 String객체로 얻기
     * @param url
     * @return String
     * @throws Exception 
     */
    public static String getHttpGetStringApiData( String url ) throws Exception {
        return getStringApiDataWithParamDoGet( url, Collections.EMPTY_MAP );
    }
    
    /**
     * API서버에서 Json데이터 String으로 얻기 GET방식 통신 ( URL, 파레메터 세팅 )
     * @param url
     * @param params
     * @return String
     * @throws Exception 
     */
    public static String getStringApiDataWithParamDoGet( String url, Map<String, String> params ) throws Exception {
        String result = "";
        if( StringUtils.isNotBlank( url ) ) {
            String changedUrl = getParamToString(url, params);
            HttpEntity httpEntity = getEntity(changedUrl);
            if( httpEntity != null ) {
                result = EntityUtils.toString(httpEntity);
            }
        }
        return result;
    }
    
    /**
     * url을 가지고 실제 통신구현부 통신 성공시 HttpEntity 객체 리턴 실패시 null 리턴
     * @param url
     * @return HttpEntity
     */
    private static HttpEntity getEntity( String url ) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        HttpEntity result = null;
        HttpResponse httpResponse = HTTP_CLIENT.execute(httpGet);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if( statusCode == 200 ) {
            result = httpResponse.getEntity();
        }
        
        return result;
        
    }
    
    /**
     * url과 param을 가지고 한글 인코딩된 url 리턴
     * @param url
     * @param params
     * @return String
     */
    public static String getParamToString( String url, Map<String, String> params ) {
        String resultUrl = "";
        try {
            URIBuilder uri = new URIBuilder(url);
            for (String key : params.keySet()) {
                uri.addParameter(key, params.get(key));
            }
            resultUrl = uri.build().toString();
            
        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }
        
        return resultUrl;
    }
    
    /**
     * url과 param을 가지고 결과가 zip파일일 시 해당 zip 파일을 destination에 복사함.
     * @param url
     * @param destination
     * @return
     */
    public static void getHttpGetStremApiData( String url, Map<String, String> params, String destination ) {
        
        try{
            String changedUrl = getParamToString(url, params);
            HttpEntity httpEntity = getEntity(changedUrl);
            if( httpEntity != null ) {
                File copyFile = new File(destination);
                try(
                    InputStream  inputStream = httpEntity.getContent();
                    OutputStream outputStream = new FileOutputStream(copyFile);
                  ) {
                    long length = inputStream.transferTo(outputStream);
                    System.out.println("고유번호 zip 파일 저장 완료 " + length);
                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    /**
     * url을 가지고 원하 model객체 List형 객체로 결과 받아오기
     * @param url
     * @param model
     * @return List<T>
     * @throws Exception 
     */
    public static <T> List<T> getHttpGetListObjectApiData( String url, Class<T> model ) throws Exception {
        return getHttpGetListObjectApiData(url, Collections.EMPTY_MAP, model);
    }
    
    /**
     * url과 param을 가지고 원하 model객체 List형 객체로 결과 받아오기
     * @param url
     * @param params
     * @param model
     * @return List<T>
     * @throws Exception 
     */
    public static <T> List<T> getHttpGetListObjectApiData( String url,  Map<String, String> params, Class<T> model ) throws Exception {
        List<T> result = Collections.EMPTY_LIST;
        String apiData = getStringApiDataWithParamDoGet(url, params);
        if( StringUtils.isNoneBlank(apiData) ){
            JsonElement jsonElement = JsonParser.parseString(apiData);
            JsonObject jobject = jsonElement.getAsJsonObject();
            JsonArray list  = jobject.getAsJsonArray("list");
            if( list != null ) {
                Type typeOfModel = TypeToken.getParameterized(List.class, model).getType();
                result = GSON_OBJ.fromJson(list, typeOfModel);
            }
        }
        
        return result;
    }
}
