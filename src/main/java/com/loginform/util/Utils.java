package com.loginform.util;

import com.loginform.model.UserEntity;

import de.mkammerer.argon2.Argon2;

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

    public static void hashPass(UserEntity user, Argon2 argon2) {
            char[] password = user.getPassword().toCharArray();
            String hash = argon2.hash(10, 65536, 5, password);
            user.setPassword(hash);
            argon2.wipeArray(password);
    }
}
