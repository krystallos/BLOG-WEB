package com.example.util;


import com.example.api.imgSelect.app.SelectSetuAppConteroller;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 非对称Rsa
 * @author Enoki
 * 密钥存在文件内
 */
public class rsaKey {

    private final Logger logger = LoggerFactory.getLogger(rsaKey.class);

    //唯一Key,欧拉函数积
    private static final String ALGORITHM = "RSA"; //加密类型
    private static final Integer KEY_LENGTH = 1024; //密文长度约束
    private static final int MAX_ENCRYPT_BLOCK = 117; //最大明文
    private static final int MAX_DECRYPT_BLOCK = 128; //最大密文

    //公钥
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqgk+0rho7haDKtXis9dVw21ODm1lXd5cT5YPTIkTS5BTlQDxEp23AC2/XjgeJgSo3p2iOHI5+reSj3gVSo96FbPzPn1RVqkqtL6XQdtLEOYe6RSITZZzbExro/xfATpMFKNTYyLGqX9q+MNI6vvQqHO6OJnZB2sEvC+pmRnT6HwIDAQAB";
    //私钥
    private static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKqCT7SuGjuFoMq1eKz11XDbU4ObWVd3lxPlg9MiRNLkFOVAPESnbcALb9eOB4mBKjenaI4cjn6t5KPeBVKj3oVs/M+fVFWqSq0vpdB20sQ5h7pFIhNlnNsTGuj/F8BOkwUo1NjIsapf2r4w0jq+9Coc7o4mdkHawS8L6mZGdPofAgMBAAECgYAAuH5HwDiiKcVtNeklqgBV8sdelgiBoyLDVcQB33I1BfvEoEng7Gg9WI8HdTU48o/QHmgFub8sb+W4f5a8K68kFXU0TKnGvu97Yzn69fR9BqfMlxwLYvA/x3wZp1p6k7w1Zy2JTi7lkEGE7B+Bva5DLZhqc8Hc4fS9miBIh+ZJOQJBAOK2MHeNy+SnTnaKpliDWZqaP72RHct5NhwLAmHDBXMvfygftpgOJAV/iODwi3ZKQGdDDbbvXLzZjWMiw/V0CT0CQQDAiWM0pOOvP0JXufOmrMboGZzPEPaKw8ots1qkmhuy7CzsbnLagKPJLq62Ed4+fptI/bXg6TERUHkeQfGgpy6LAkAqKlJ3WeMLIubf+0eW/M8Ehx3FxyCwgFnE+3M09Y2k77eJoeRXAYJHl4HAvFxj4sl5qyn41Sn9YcT/JC8oDq3NAkEArWBKNFKzMNEPJb4uOQ3mw4Sk3xWQXR63xfV3Dzgy7yibv4Ap7rbeRkDCVPO8JJyPqufESzbR15yWpHePZTKGxwJBAKxJnonk41s6YiIxoTagQa2nAHZMksGD1ycl6Sqa3DgvclWpsUpI+L1nPsQjz7VRGqXeGay/pOszCbqZpI0slho=";


    /**
     * 加密逻辑
     * @param key 密码传入
     * @return
     */
    public static String getKeyBtye(String key) throws IOException {
        //调用
        /*InputStreamReader isr;
        try {
            isr = new InputStreamReader(new FileInputStream("src\\main\\java\\com\\example\\file\\client1PubKey1.key"));
        }catch (FileNotFoundException e){
            KeyFilePriPub(genKeyPair());
            isr = new InputStreamReader(new FileInputStream("src\\main\\java\\com\\example\\file\\client1PubKey1.key"));
        }
        String client1PubKey = null;
        int ch = 0;
        while((ch=isr.read())!=-1){
            if(client1PubKey == null){
                client1PubKey = String.valueOf((char)ch);
            }else {
                client1PubKey = client1PubKey + (char) ch;
            }
        }
        isr.close();*/
        String messKey = encrypt(key,publicKey,MAX_ENCRYPT_BLOCK);
        return messKey;
    }

    /**
     * 解密逻辑
     * @param key 密码传入
     * @return
     */
    public static String setKeyBtye(String key) throws IOException {
        //调用
        /*InputStreamReader isr2;
        try {
            isr2 = new InputStreamReader(new FileInputStream("src\\main\\java\\com\\example\\file\\client1PriKey1.key"));
        }catch (FileNotFoundException e){
            KeyFilePriPub(genKeyPair());
            isr2 = new InputStreamReader(new FileInputStream("src\\main\\java\\com\\example\\file\\client1PriKey1.key"));
        }
        String client1PriKey="";
        int ch2 = 0;
        while((ch2=isr2.read())!=-1){
            if(client1PriKey == null || client1PriKey==""){
                client1PriKey = String.valueOf((char)ch2);
            }else {
                client1PriKey = client1PriKey + (char)ch2;
            }
        }
        isr2.close();*/
        String messKey = decrypt(key,privateKey);
        return messKey;
    }

    //随机生成密钥信息对
    public static KeyPair genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_LENGTH);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 密钥信息对保存
     * @param keyPair 加密组
     */
    public static Map<String, Object> KeyFilePriPub(KeyPair keyPair){
        Map<String, Object> keyMap = new HashMap<String, Object>();
        String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
        String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("src\\main\\java\\com\\example\\file\\client1PubKey1.key"));
            osw.write(publicKey, 0, publicKey.length());
            osw.flush();
            osw.close();
            //私钥文件存储
            OutputStreamWriter osw2 = new OutputStreamWriter(new FileOutputStream("src\\main\\java\\com\\example\\file\\client1PriKey1.key"));
            osw2.write(privateKey, 0, privateKey.length());
            osw2.flush();
            osw2.close();
        }catch (IOException e){
            return null;
        }
        keyMap.put("privateKey",privateKey);
        keyMap.put("publicKey",publicKey);
        return keyMap;
    }

    /**
     * 以下两种方式为了重复利用
     * @param str
     * @param leng
     * @return
     */
    public static String impEncrypt(String str, int leng){
        return encrypt(str,publicKey,leng);
    }

    public static String impEncrypt(String str){
        return encrypt(str,publicKey,MAX_ENCRYPT_BLOCK);
    }


    /**
     * RSA加密
     * @param str 加密字符串
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static String encrypt(String str, String publicKey, int maxEnc ){

        try {
            // 得到公钥
            byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            Key key = keyFactory.generatePublic(x509EncodedKeySpec);
            // 加密数据，分段加密
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            int inputLen = str.getBytes().length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offset > 0) {
                if (inputLen - offset > maxEnc) {
                    cache = cipher.doFinal(str.getBytes(), offset, maxEnc);
                } else {
                    cache = cipher.doFinal(str.getBytes(), offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * maxEnc;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();

            return new String(Base64.encodeBase64String(encryptedData));
        }catch (Exception e){

        }
        return null;
    }

    /**
     * RSA解密
     * @param str 加密字符串
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String decrypt(String str, String privateKey){

        try{
            // 得到私钥
            byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            Key key = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
            // 解密数据，分段解密
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] dataBytes = Base64.decodeBase64(str);
            int inputLength = dataBytes.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            while (inputLength - offset > 0) {
                if (inputLength - offset > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataBytes, offset, inputLength - offset);
                }
                out.write(cache);
                i++;
                offset = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData,"UTF-8");

        }catch (Exception e){
            return null;
        }
    }

    /**
     * 不重复的UUID(大写字符串)
     * @param str 保证无序，可以随机带入任意全新数据组合
     * @return
     */
    public static String uuid(String str) {
        String newUuid = null;
        newUuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        if(str!=null){
            newUuid += str;
        }
        return newUuid;
    }

}
