package com.seokjin.kim.com.make.javalibrary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seokjin.kim.library.GetProperties;
import com.seokjin.kim.library.HttpClientCustom;

public class Main {
    public static void main( String[] args ) {
        
        GetProperties properties = new GetProperties("config/secure.properties");
        Map<String, String> params = new HashMap<>();
        String crtfc_key    = properties.getValue("API_KEY_DART");
        String corp_code    = properties.getValue("삼성전자");
        params.put("bgn_de", "20180117");
        params.put("end_de", "20190117");
        params.put("page_no", "1");
        params.put("page_count", "100");
        params.put("last_reprt_at", "Y");
        params.put("crtfc_key", crtfc_key);
        params.put("corp_code", corp_code);
        try {
            String getDisclosureSearch  =  HttpClientCustom.getHttpGetStringApiData("https://opendart.fss.or.kr/api/list.json", params);
            System.out.println(getDisclosureSearch);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*
        1. API서버에서 고유 번호 Zip파일 받아서 원하는 곳에 zip 만들기. => 서버 다운로드 조금 오래걸림
        String dartUrl = "https://opendart.fss.or.kr/api/corpCode.xml?crtfc_key="+GetProperties.getSecureValue("API_KEY_DART");
        HttpClientCustom.getHttpGetStremApiData(dartUrl,"C:\\Users\\Jin\\Desktop\\zzz\\corpCode.zip");
        
        */
       
       /*
        2. String 으로 API 서버에서 데이터 받아오기
        String getDisclosureSearch  =  HttpClientCustom.getHttpGetStrinApiData("https://opendart.fss.or.kr/api/list.json", "20180117", "20200117");
        
        */
       
       /*
       3. 원하는 객체로  API 서버에서 데이터 받아오기
       List<Disclousure> disLit = HttpClientCustom.getHttpGetListObjectDartApiData("https://opendart.fss.or.kr/api/list.json", "20180117", "20200117", Disclousure.class);
       for (int index = 0; index < disLit.size(); index++) {
           
           Disclousure temp = disLit.get(index);
           System.out.println(temp.getCorp_name());
       }
       */
       
       /*
       4. 멀티 쓰레드 환경에서 테스트 
       
       long start = System.currentTimeMillis();
       
       Thread thread1 = new Thread( () -> {
           for (int index2 = 0; index2 < 30; index2++) {
               List<Disclousure> disLit = HttpClientCustom.getHttpGetListObjectDartApiData("https://opendart.fss.or.kr/api/list.json", "20180117", "20200117","삼성전자", Disclousure.class);
               System.out.println("1번 시작!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
               for (int index = 0; index < disLit.size(); index++) {
                  Disclousure temp = disLit.get(index);
                  //System.out.println(temp.getCorp_name());
                  //System.out.println(temp.getReport_nm());
              }
               System.out.println("1번끝!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
               long end = System.currentTimeMillis();
               System.out.println("속도 측정!!!!!!!!!!!!!!!!!!!");
               System.out.println(end - start);
               System.out.println("zzzz");
           }
       });
       
       Thread thread2 = new Thread( () -> {
           for (int index2 = 0; index2 < 30; index2++) {
               List<Disclousure> disLit = HttpClientCustom.getHttpGetListObjectDartApiData("https://opendart.fss.or.kr/api/list.json", "20180117", "20200117","웹바이오텍", Disclousure.class);
               System.out.println("2번 시작!!!!!!!!!!!!!!!!!!!!!!!!!");
               for (int index = 0; index < disLit.size(); index++) {
                  Disclousure temp = disLit.get(index);
                  System.out.println(temp.getCorp_name());
                  System.out.println(temp.getReport_nm());
              }
               System.out.println("2번 끝!!!!!!!!!!!!!!!!!!!!!!!!!");
               long end = System.currentTimeMillis();
               System.out.println("속도 측정!!!!!!!!!!!!!!!!!!!");
               System.out.println(end - start);
           }
       });
      
       
       thread1.start();
       thread2.start();
       */
    }
}
