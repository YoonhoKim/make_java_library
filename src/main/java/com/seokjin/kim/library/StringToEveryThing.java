package com.seokjin.kim.library;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class StringToEveryThing {
    /**
     * 시간 없이 2013-04-08 , 20130408, 2013.04.08, 2013 04 08 케이스 원하는 type "yyyy-mm-dd"으로 형변환
     * @param date
     * @param type
     * @return Date
     */
    public static Date getStringToDate( String date, String type ) {
        
        String defaultType = StringUtils.isEmpty(type) ? "yyyy-mm-dd" : type;
        String changeDate  = date.replaceAll("-","").replaceAll(" ","").replaceAll("\\.","");
        SimpleDateFormat format = new SimpleDateFormat(defaultType);
        if( changeDate.length() != 7 ) {
            new Throwable("형식을 확인하세요");
        }
        changeDate = new StringBuilder(changeDate.substring(0,4)).append("-").append(changeDate.substring(4,6)).append("-").append(changeDate.substring(6,8)).toString();
        Date result = new Date();
        try {
            result = format.parse(changeDate);
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
        
        return result;
        
    }
    
    /**
     * String 숫자 number로 변경
     * @param number
     * @return Double
     */
    public static Double getStringToDouble( String number ) {
        Double todayPrice = new BigDecimal(number.replaceAll(",", "").replaceAll(" ", "").replace("%","")).doubleValue();
        return todayPrice;
        
    }
}
