//package com.example.demo;
//
//import java.security.Key;
//import java.security.KeyFactory;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.Signature;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//
//import javax.crypto.Cipher;
//
//import org.apache.commons.codec.binary.Base64;
//
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
//
//
//public class RSAtest{
//    public static final String KEY_ALGORITHM="RSA";
//    public static final String SIGNATURE_ALGORITHM="MD5withRSA";
//    private static final int KEY_SIZE=1024;
//    private static final String PUBLIC_KEY="RSAPublicKey";
//    private static final String PRIVATE_KEY="RSAPrivateKey";
//    public static String str_pubK = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqPvovSfXcwBbW8cKMCgwqNpsYuzF8RPAPFb7LGsnVo44JhM/xxzDyzoYtdfNmtbIuKVi9PzIsyp6rg+09gbuI6UGwBZ5DWBDBMqv5MPdOF5dCQkB2Bbr5yPfURPENypUz+pBFBg41d+BC+rwRiXELwKy7Y9caD/MtJyHydj8OUwIDAQAB";
//    public static String str_priK = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKo++i9J9dzAFtbxwowKDCo2mxi7MXxE8A8VvssaydWjjgmEz/HHMPLOhi1182a1si4pWL0/MizKnquD7T2Bu4jpQbAFnkNYEMEyq/kw904Xl0JCQHYFuvnI99RE8Q3KlTP6kEUGDjV34EL6vBGJcQvArLtj1xoP8y0nIfJ2Pw5TAgMBAAECgYAGGB8IllMwxceLhjf6n1l0IWRH7FuHIUieoZ6k0p6rASHSgWiYNRMxfecbtX8zDAoG0QAWNi7rn40ygpR5gS1fWDAKhmnhKgQIT6wW0VmD4hraaeyP78iy8BLhlvblri2nCPIhDH5+l96v7D47ZZi3ZSOzcj89s1eS/k7/N4peEQJBAPEtGGJY+lBoCxQMhGyzuzDmgcS1Un1ZE2pt+XNCVl2b+T8fxWJH3tRRR8wOY5uvtPiK1HM/IjT0T5qwQeH8Yk0CQQC0tcv3d/bDb7bOe9QzUFDQkUSpTdPWAgMX2OVPxjdq3Sls9oA5+fGNYEy0OgyqTjde0b4iRzlD1O0OhLqPSUMfAkEAh5FIvqezdRU2/PsYSR4yoAdCdLdT+h/jGRVefhqQ/6eYUJJkWp15tTFHQX3pIe9/s6IeT/XyHYAjaxmevxAmlQJBAKSdhvQjf9KAjZKDEsa7vyJ/coCXuQUWSCMNHbcR5aGfXgE4e45UtUoIE1eKGcd6AM6LWhx3rR6xdFDpb9je8BkCQB0SpevGfOQkMk5i8xkEt9eeYP0fi8nv6eOUcK96EXbzs4jV2SAoQJ9oJegPtPROHbhIvVUmNQTbuP10Yjg59+8=";
//      /**
//       * 使用getPublicKey得到公钥,返回类型为PublicKey
//       * @param base64 String to PublicKey
//       * @throws Exception
//       */
//      public static PublicKey getPublicKey(String key) throws Exception {
//            byte[] keyBytes;
//            keyBytes = (new BASE64Decoder()).decodeBuffer(key);
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PublicKey publicKey = keyFactory.generatePublic(keySpec);
//            return publicKey;
//      }
//      /**
//       * 转换私钥
//       * @param base64 String to PrivateKey
//       * @throws Exception
//       */
//      public static PrivateKey getPrivateKey(String key) throws Exception {
//            byte[] keyBytes;
//            keyBytes = (new BASE64Decoder()).decodeBuffer(key);
//            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
//            return privateKey;
//      }
//
//      //***************************签名和验证*******************************
//      public static byte[] sign(byte[] data) throws Exception{
//        PrivateKey priK = getPrivateKey(str_priK);
//          Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
//          sig.initSign(priK);
//          sig.update(data);
//          return sig.sign();
//      }
//
//      public static boolean verify(byte[] data,byte[] sign) throws Exception{
//          PublicKey pubK = getPublicKey(str_pubK);
//          Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
//          sig.initVerify(pubK);
//          sig.update(data);
//          return sig.verify(sign);
//      }
//
//      //************************加密解密**************************
//      public static byte[] encrypt(byte[] bt_plaintext)throws Exception{
//          PublicKey publicKey = getPublicKey(str_pubK);
//          Cipher cipher = Cipher.getInstance("RSA");
//          cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        byte[] bt_encrypted = cipher.doFinal(bt_plaintext);
//        return bt_encrypted;
//      }
//
//      public static byte[] decrypt(byte[] bt_encrypted)throws Exception{
//        PrivateKey privateKey = getPrivateKey(str_priK);
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        byte[] bt_original = cipher.doFinal(bt_encrypted);
//        return bt_original;
//      }
//      //********************main函数：加密解密和签名验证*********************
//      public static void main(String[] args) throws Exception {
//            String str_plaintext = "这是一段用来测试密钥转换的明文";
//            System.err.println("明文："+str_plaintext);
//
//
//            byte[] bt_cipher = encrypt(str_plaintext.getBytes());
//            System.out.println("加密后："+Base64.encodeBase64String(bt_cipher));
//
//            byte[] bt_original = decrypt(bt_cipher);
//            String str_original = new String(bt_original);
//            System.out.println("解密结果:"+str_original);
//
//            String str="被签名的内容";
//            System.err.println("\n原文:"+str);
//            byte[] signature=sign(str.getBytes());
//            System.out.println("产生签名："+Base64.encodeBase64String(signature));
//            boolean status=verify(str.getBytes(), signature);
//            System.out.println("验证情况："+status);
//      }
//
//}