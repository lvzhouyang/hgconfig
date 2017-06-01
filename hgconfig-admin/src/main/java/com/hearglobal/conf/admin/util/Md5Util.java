package com.hearglobal.conf.admin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The type Md 5 util.
 * Description
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.04.01
 */
public class Md5Util {

    /**
     * 字符串MD5加密 (32位小写)
     *
     * @param text the text
     * @return :encrypt/decrypt
     * @since 2017.04.01
     */
    public static String encrypt(String text) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
            msgDigest.update(text.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("系统不支持MD5加密.");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("系统不支持utf-8加密..");
        }
        byte[] bytes = msgDigest.digest();
        String md5Str = new String(encodeHex(bytes));
        return md5Str;
    }

    /**
     * 字节转为16进制字符串
     *
     * @param data
     * @return
     */
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Encode hex char [ ].
     *
     * @param data the data
     * @return the char [ ]
     * @since 2017.04.01
     */
    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = HEX_DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = HEX_DIGITS[0x0F & data[i]];
        }
        return out;
    }

}
