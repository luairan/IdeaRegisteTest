package com.example.demo;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import sun.misc.BASE64Decoder;

public class RSAtes {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final int KEY_SIZE = 1024;
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 使用getPublicKey得到公钥,返回类型为PublicKey
     *
     * @param base64 String to PublicKey
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 转换私钥
     *
     * @param base64 String to PrivateKey
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        //PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        //KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        RSAPrivateKeyStructure
            asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence)ASN1Sequence.fromByteArray(keyBytes));
        RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(),
                                                                 asn1PrivKey.getPrivateExponent());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(rsaPrivKeySpec);

        return priKey;
    }

    //***************************签名和验证*******************************
    public static byte[] sign(byte[] data) throws Exception {
        PrivateKey priK = getPrivateKey(str_priK);
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initSign(priK);
        sig.update(data);
        return sig.sign();
    }

    public static boolean verify(byte[] data, byte[] sign) throws Exception {
        PublicKey pubK = getPublicKey(str_pubK);
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(pubK);
        sig.update(data);
        return sig.verify(sign);
    }

    //************************加密解密**************************
    public static byte[] encrypt(byte[] bt_plaintext) throws Exception {
        PublicKey publicKey = getPublicKey(str_pubK);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bt_encrypted = cipher.doFinal(bt_plaintext);
        return bt_encrypted;
    }

    public static byte[] decrypt(byte[] bt_encrypted) throws Exception {
        PrivateKey privateKey = getPrivateKey(str_priK);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bt_original = cipher.doFinal(bt_encrypted);
        return bt_original;
    }

    //********************main函数：加密解密和签名验证*********************
    public static void main(String[] args) throws Exception {
        //String str_plaintext = "这是一段用来测试密钥转换的明文";
        //System.err.println("明文："+str_plaintext);

        System.out.println("======MD5=====");

        String sss = "<ObtainTicketResponse><message></message><prolongationPeriod>607875500</prolongationPeriod"
            + "><responseCode>OK</responseCode><salt>1503145454359</salt><ticketId>1</ticketId><ticketProperties"
            + ">licensee=luairan\tlicenseType=0\t</ticketProperties></ObtainTicketResponse>";

        //byte []bytess  =  hexString2Bytes(sss);
        //
        //System.out.println(EncryptionUtil.toString(EncryptionUtil.encrypt(bytess, null, Type.MD5)));
        //
        //
        //byte [] md5s = EncryptionUtil.encrypt(bytess, null, Type.MD5);

        //byte[] bt_cipher = encrypt(str_plaintext.getBytes());
        //System.out.println("加密后："+Base64.encodeBase64String(bt_cipher));
        //
        //byte[] bt_original = decrypt(bt_cipher);
        //String str_original = new String(bt_original);
        //System.out.println("解密结果:"+str_original);

        //String str="9bbb7b6e2a2c0a96faa10c70a66e94c9";
        //System.err.println("\n原文:"+new String(hexString2Bytes(str)));
        byte[] signature = sign(sss.getBytes());

        System.out.println("产生签名：" + bytes2HexString(signature));
        //boolean status=verify(str.getBytes(), signature);
        //System.out.println("验证情况："+status);
    }

    public static String bytes2HexString(byte[] b) {
        StringBuffer result = new StringBuffer();
        String hex;
        for (int i = 0; i < b.length; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex.toLowerCase());
        }
        return result.toString();
    }

    public static byte[] hexString2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte)Integer
                .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }
}