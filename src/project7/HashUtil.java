/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project7;

import java.security.MessageDigest;

/**
 *
 * @author 20114
 */
public class HashUtil {
    public static String hashPassword(String password)
    {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for(byte b : hash)
            {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
