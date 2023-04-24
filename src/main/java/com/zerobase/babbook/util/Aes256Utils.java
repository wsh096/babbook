package com.zerobase.babbook.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Aes256Utils 클래스는 AES256 알고리즘 사용
 * 문자열을 암호화하거나 복호화하는 유틸리티 클래스
 * IV와 KEY 값 고정, 암호화한 문자열은 Base64 인코딩해 반환.
 */
public class Aes256Utils {
    public static String alg = "AES/CBC/PKCS5Padding";
    private static final String KEY = "ZEROBASEKEYISZEROBASEKEY";
    private static final String IV = KEY.substring(0,16);
    public static String encrypt(String text){
        try{
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec =
                new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParameterSpec =
                new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

            byte[] encrypted = cipher
                .doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(encrypted);
        }catch(Exception e){
            return null;
        }
    }
    public static String decrypt(String cipherText){
        try{
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec =
                new SecretKeySpec(KEY.getBytes(),"AES");
            IvParameterSpec ivParameterSpec =
                new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
            byte[] decodeBytest = Base64.decodeBase64(cipherText);
            byte[] decrypted = cipher.doFinal(decodeBytest);
            return new String(decrypted, StandardCharsets.UTF_8);
        }catch(Exception e){
            return null;
        }
    }
}
