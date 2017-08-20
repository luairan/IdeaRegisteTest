package com.example.demo;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class EncryptionUtil {

    private static final char[] hexDigits = "0123456789abcdef".toCharArray();

    private static Map<Type, MessageDigest> map = new HashMap<>();

    public final static String encrypt(String sourceText, Charset charset, Type type) throws NoSuchAlgorithmException {
        byte[] btInput = sourceText.getBytes(charset);
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = map.get(type);
        if (mdInst == null) { throw new NoSuchAlgorithmException("not find " + type.name() + " in system"); }
        // 使用指定的字节更新摘要
        mdInst.update(btInput);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        //            int j = md.length;
        //            char str[] = new char[j * 2];
        //            int k = 0;
        //            for (int i = 0; i < j; i++) {
        //                byte byte0 = md[i];
        //                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        //                str[k++] = hexDigits[byte0 & 0xf];
        //            }
        return toString(md);

    }

    public static final byte[] encrypt(byte[] sourceText, Charset charset, Type type) throws NoSuchAlgorithmException {
        //byte[] btInput = sourceText.getBytes(charset);
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = map.get(type);
        if (mdInst == null) { throw new NoSuchAlgorithmException("not find " + type.name() + " in system"); }
        // 使用指定的字节更新摘要
        mdInst.update(sourceText);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        //            int j = md.length;
        //            char str[] = new char[j * 2];
        //            int k = 0;
        //            for (int i = 0; i < j; i++) {
        //                byte byte0 = md[i];
        //                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        //                str[k++] = hexDigits[byte0 & 0xf];
        //            }
        return md;

    }

    static final String toString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            sb.append(hexDigits[(b >> 4) & 0xf]).append(hexDigits[b & 0xf]);
        }
        return sb.toString();
    }

    public enum Type {
        MD5 {{
            putMessageDigest(this, "MD5");
        }
        },
        SHA1 {{
            putMessageDigest(this, "SHA-1");
        }
        };

        private static void putMessageDigest(Type type, String string) {
            try {
                map.put(type, MessageDigest.getInstance(string));
            } catch (NoSuchAlgorithmException e) {
            }
        }
    }
}