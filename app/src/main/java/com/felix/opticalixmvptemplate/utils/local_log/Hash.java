package com.felix.opticalixmvptemplate.utils.local_log;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hash工具
 */
public class Hash {

    /**
     * 字符串md5
     *
     * @param s 字符串
     * @return md5值，字符串为空返回null
     */
    public static String md5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte b[] = md.digest(s.getBytes());
            return bytesToHexString(b);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 字符串sha1
     *
     * @param str 字符串
     * @return sha1值。字符串为空返回null
     */
    public static String sha1(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            byte[] digest = sha1.digest(str.getBytes());
            return bytesToHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 文件md5
     *
     * @param filePath 文件路径
     * @return 文件md5值。文件路径或者文件夹返回null。
     */
    public static String fileMd5(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (file.isDirectory()) {
            return null;
        }
        return fileMessageDigest(file, "MD5");
    }

    /**
     * 文件sha1
     *
     * @param filePath 文件路径
     * @return 文件sha1值。文件路径或者文件夹返回null。
     */
    public static String fileSha1(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (file.isDirectory()) {
            return null;
        }
        return fileMessageDigest(file, "SHA1");
    }

    private static String fileMessageDigest(File file, String digestType) {
        if (file == null) {
            return null;
        }
        FileInputStream in = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(digestType);
            in = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int readCount = 0;
            while ((readCount = in.read(buffer)) != -1) {
                digest.update(buffer, 0, readCount);
            }
            return bytesToHexString(digest.digest());
        } catch (Exception e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return "";
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            int val = b & 0xff;
            if (val < 0x10) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString();
    }

    private static byte[] hexStringToBytes(String hex) {
        final byte[] encodingTable = {(byte) '0', (byte) '1', (byte) '2',
                (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
                (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c',
                (byte) 'd', (byte) 'e', (byte) 'f'};

        final byte[] decodingTable = new byte[128];
        for (int i = 0; i < encodingTable.length; i++) {
            decodingTable[encodingTable[i]] = (byte) i;
        }
        decodingTable['A'] = decodingTable['a'];
        decodingTable['B'] = decodingTable['b'];
        decodingTable['C'] = decodingTable['c'];
        decodingTable['D'] = decodingTable['d'];
        decodingTable['E'] = decodingTable['e'];
        decodingTable['F'] = decodingTable['f'];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StringReader rd = new StringReader(hex);
        byte b = 0;
        while (true) {
            try {
                b = (byte) (decodingTable[nextByte(rd)] << 4);
                b += (byte) (decodingTable[nextByte(rd)]);
                out.write(b);
            } catch (IOException e) {
                break;
            }
        }
        return out.toByteArray();
    }

    private static byte nextByte(StringReader rd) throws IOException {
        int n = 0;
        while (true) {
            n = rd.read();
            if (n == '\n' || n == '\r' || n == '\t' || n == ' ') {
                continue;
            }
            if (n == -1) {
                throw new IOException();
            }
            return (byte) n;
        }

    }
}
