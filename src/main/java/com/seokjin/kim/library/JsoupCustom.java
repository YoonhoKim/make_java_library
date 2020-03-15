package com.seokjin.kim.library;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupCustom {
    
    private JsoupCustom() {}
    
    
    /**
     * Url에서 Dom객체 얻어오기, GET 통신
     * @param url
     * @return Document
     * @throws IOException 
     * @throws Exception 
     */
    public static Document getGetDocumentFromURL( String url ) {
        return getGetDocumentFromURL( url, Collections.EMPTY_MAP );
    }
    public static Document getGetDocumentFromURL( String url, Map<String, String> params ) {
        String changedURL = HttpClientCustom.getParamToString(url, params);
        Document result = new Document("");
        try {
            Connection connection = Jsoup.connect(changedURL);
            result = connection.get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
