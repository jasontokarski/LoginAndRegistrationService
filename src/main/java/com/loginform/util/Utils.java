package com.loginform.util;

public class Utils {

    public static String generateRandomString(int length, String allowableChars) 
    { 
        StringBuilder sb = new StringBuilder(length); 
  
        for (int i = 0; i < length; i++) { 
            int index = (int)(allowableChars.length() * Math.random()); 
            sb.append(allowableChars.charAt(index)); 
        } 
  
        return sb.toString(); 
    } 
}
