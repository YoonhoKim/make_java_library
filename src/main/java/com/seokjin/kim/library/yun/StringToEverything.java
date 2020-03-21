package com.seokjin.kim.library.yun;

import org.apache.commons.lang3.StringUtils;

public class StringToEverything {
	/**
     * 시간 없이 2013-04-08 , 20130408, 2013.04.08, 2013 04 08 케이스 2013-04-08로 통일 
     * @param date
     * @param type
     * @return Date
     */
    public static String getStringToDateDash( String date ) {
        if(  StringUtils.isEmpty(date) ) return "";
        
        String changeDate  = date.replaceAll("-","").replaceAll(" ","").replaceAll("\\.","");
        if( changeDate.length() == 8 ) {
            changeDate = new StringBuilder(changeDate.substring(0,4)).append("-").append(changeDate.substring(4,6)).append("-").append(changeDate.substring(6,8)).toString();
        }
        
        return changeDate;
        
    }
}
