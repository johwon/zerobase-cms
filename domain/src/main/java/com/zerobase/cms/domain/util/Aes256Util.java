package com.zerobase.cms.domain.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class Aes256Util {
    public static String alg = "AES/CBC/PKCS5Padding";
    private static final String KEY = "ZEROBASEKEYISZER"; // 16글자
    private static final String IV = KEY.substring(0,16);

    public static String encrypt(String text){
        try {
            System.out.println("KEY bytes length: " + KEY.getBytes(StandardCharsets.UTF_8).length);
            System.out.println("IV bytes length: " + IV.getBytes(StandardCharsets.UTF_8).length);

            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String decrypt(String cipherText){
        System.out.println("Decrypt called with cipherText: " + cipherText);

        try{

            System.out.println("KEY bytes length: " + KEY.getBytes(StandardCharsets.UTF_8).length);
            if (cipherText == null) {
                System.out.println("Decrypt input is null, returning null");
                return null;
            }
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

            byte[] decodeBytes = Base64.decodeBase64(cipherText);
            byte[] decrypted = cipher.doFinal(decodeBytes);
            String result = new String(decrypted, StandardCharsets.UTF_8);
            System.out.println("Decrypt success: " + result);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Decrypt failed for input: " + cipherText);
            return null;
        }
    }

}
