package com.seokjin.kim.library;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;


public class GetProperties {
    //private static final Properties PROPERTIES_COMMON = new Properties();
    private static Properties PROPERTIES_SECURE = new Properties();
    //private static final String COMMON_CONFIG="config/config.properties";
    //private static final String SECURES_CONFIG="config/secure.properties";
    private static final ClassLoader DEFULT_CLASSLOADER;
    
   
     static {
        // Resorce 하위 propertiese들 읽기
        DEFULT_CLASSLOADER = ClassLoader.getSystemClassLoader();
        /*try( InputStreamReader inputStreamCommon = new InputStreamReader( DEFULT_CLASSLOADER.getSystemResourceAsStream(COMMON_CONFIG) , Charset.forName("UTF-8"));
               InputStreamReader inputStreamSecure = new InputStreamReader( DEFULT_CLASSLOADER.getSystemResourceAsStream(SECURES_CONFIG) , Charset.forName("UTF-8"));){
           
           PROPERTIES_COMMON.load ( inputStreamCommon );
           PROPERTIES_SECURE.load( inputStreamSecure );
           
        } catch (Exception e) {
        System.out.println(e.getMessage());
        }*/
    }
    
    
    /**
     * 보안안 관련된 properties를 읽어오기 ( secure.properties) , configLocation에 해당 경로 설정, classPath기준 만약 src/main/resources 하위에 들어가 있다면 config/secure.properties으로 설정. 절대경로 불가능하다.
     * @param String configLocation
     * @return
     */
    private GetProperties( String configLocation ){}
    
    /**
     * 보안안 관련된 properties classPath기준 얻기 configLocation의 위치가 src/main/resources 하위에 들어가 있다면 config/secure.properties으로 설정. 절대경로 불가능하다
     * @param configLocation
     * @return Properties
    */
    public static Properties getProperties ( String configLocation ) {
        try( InputStreamReader inputStreamSecure = new InputStreamReader( DEFULT_CLASSLOADER.getSystemResourceAsStream(configLocation), Charset.forName("UTF-8"));){
            PROPERTIES_SECURE.load( inputStreamSecure );
            
        } catch (Exception e) {
         System.out.println(e.getMessage());
        }
        return PROPERTIES_SECURE;
    }
}
