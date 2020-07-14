/******************************************************************************
 * @(#)Encrypt.java 1.0  2005-6-2
 *
 * 版权(c) 2005-2010  沈阳丰联数码科技有限公司
 * 中国·辽宁·沈阳市和平区中华路188号中进大厦14层
 * 所有权限被保留。
 *
 *     本软件为丰联公司所拥有的保密信息。在未经过本公司许可的情况下，任何人或
 * 机构不可以将该软件的使用权和原代码泄露给其他人或机构。
 ******************************************************************************/
package com.fenglian.tools.util;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



/**
 * 提供字符串的简单加密和解密操作。 <br>
 * 程序举例：
 * <p><blockquote><pre>
 *     Sting md; // 信息摘要
 * 　　String message = "好好学习，天天向上"; // 明文
 * 　　String cryptograph; // 密文
 * 
 *     md = Encrypt.md5("message");
 * 　　cryptograph = Encrypt.encrypt( message ); // 加密
 * 　　System.out.println( md );// 打印信息摘要
 * 　　System.out.println( cryptograph );// 打印密文
 * 　　System.out.println( Encrypt.decrypt(cryptograph) ); // 解密、打印明文
 * </pre></blockquote></p>
 * 
 * @author 
 * @version 1.0, 2006-12-18
 * @since JDK1.4.2
 */
public class Encrypt
{

    /**
     * 采用的加密算法,例如：DES,DESede,Blowfish等
     */
    private static String    EncryptAlgorithm = "Blowfish";

    /**
     * 固定密钥
     */
    private static SecretKey SECRET_KEY       = new SecretKeySpec("ILOVEMONEY".getBytes(),
                                                      EncryptAlgorithm);

    /**
     * 信息摘要算法，例如："SHA", "MD5"等
     */
    private static String    MDAlgorithm      = "MD5";

    /**
     * 所有象征数字的字符。
     */
    private final static char[]      digits           = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'     };

    /**
     * 数据加密
     * 
     * @param message - 明文
     * @return 密文
     * 
     * @since JDK1.4.2
     */
    public static String encrypt(String message)
    {
        if (message == null)
        {
            throw new RuntimeException("com.fenglian.Encrypt.encrypt（参数 message 不能为 null）");
        }

        byte[] input = message.getBytes();

        try
        {
            Cipher cipher = Cipher.getInstance(EncryptAlgorithm); // 获得Cipher类实例
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY); // 初始化Cipher实例
            byte[] cipherByte = cipher.doFinal(input); // 完成加密
            return byte2hex(cipherByte);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("数据加密:NoSuchAlgorithmException - com.fenglian.Encrypt.encrypt（调用 Cipher.getInstance() 失败）");
        }
        catch (NoSuchPaddingException e)
        {
            throw new RuntimeException("数据加密:NoSuchPaddingException - com.fenglian.Encrypt.encrypt（调用 Cipher.getInstance() 失败）");
        }
        catch (InvalidKeyException e)
        {
            throw new RuntimeException("数据加密:InvalidKeyException - com.fenglian.Encrypt.encrypt（调用 cipher.init() 失败）");
        }
        catch (IllegalStateException e)
        {
            throw new RuntimeException("数据加密:IllegalStateException - com.fenglian.Encrypt.encrypt（调用 cipher.doFinal() 失败）");
        }
        catch (IllegalBlockSizeException e)
        {
            throw new RuntimeException("数据加密:IllegalBlockSizeException - com.fenglian.Encrypt.encrypt（调用 cipher.doFinal() 失败）");
        }
        catch (BadPaddingException e)
        {
            throw new RuntimeException("数据加密"+
                    "BadPaddingException - com.fenglian.Encrypt.encrypt（调用 cipher.doFinal() 失败）");
        }

    }

    /**
     * 数据解密
     * 
     * @param cryptograph - 密文
     * @return 明文
     * 
     * @since JDK1.4.2
     */
    public static String decrypt(String cryptograph)
    {
        if (cryptograph == null)
        {
            throw new RuntimeException("com.fenglian.Encrypt.decrypt（参数 message 不能为 null）");
        }
        byte[] input = hex2byte(cryptograph);
        try
        {

            Cipher cipher = Cipher.getInstance(EncryptAlgorithm); // 获得Cipher类实例
            cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY); // 初始化Cipher实例
            byte[] clearByte = cipher.doFinal(input); // 完成加密
            return new String(clearByte);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("数据解密:"+
                    "NoSuchAlgorithmException - com.fenglian.Encrypt.decrypt（调用 Cipher.getInstance() 失败）");
        }
        catch (NoSuchPaddingException e)
        {
            throw new RuntimeException("数据解密:"+
                    "NoSuchPaddingException - com.fenglian.Encrypt.decrypt（调用 Cipher.getInstance() 失败）");
        }
        catch (InvalidKeyException e)
        {
            throw new RuntimeException("数据解密:"+
                    "InvalidKeyException - com.fenglian.Encrypt.decrypt（调用 cipher.init() 失败）");
        }
        catch (IllegalStateException e)
        {
            throw new RuntimeException("数据解密:"+
                    "IllegalStateException - com.fenglian.Encrypt.decrypt（调用 cipher.doFinal() 失败）");
        }
        catch (IllegalBlockSizeException e)
        {
            throw new RuntimeException("数据解密:"+
                    "IllegalBlockSizeException - com.fenglian.Encrypt.decrypt（调用 cipher.doFinal() 失败）");
        }
        catch (BadPaddingException e)
        {
            throw new RuntimeException("数据解密:"+
                    "BadPaddingException - com.fenglian.Encrypt.decrypt（调用 cipher.doFinal() 失败）");
        }
    }

    /**
     * 采用MD5算法提取message的信息摘要。
     * 
     * @param message - 消息
     * @return 消息摘要
     * 
     * @since JDK1.4.2
     */
    public static String md5(String message)
    {
        String str = null;

        if (message == null)
        {
            throw new RuntimeException("com.fenglian.Encrypt.md5（参数 message 不能为 null）");
        }

        try
        {
            MessageDigest md = null;
            md = MessageDigest.getInstance(MDAlgorithm);// 获得MessageDigest类实例
            md.update(message.getBytes()); // 更新摘要。
            byte[] toChapterDigest = md.digest(); // 完成哈希计算
            str = byte2hex(toChapterDigest); // 生成16进制字符串
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("MD5算法:"+
                    "NoSuchAlgorithmException - com.fenglian.Encrypt.md5（调用 MessageDigest.getInstance() 失败）");
        }

        return str;

    }

    /**
     * 将byte数组转化成16进制字符串。hex2byte(String str)的逆向方法<br>
     * 
     * @param input - byte数组
     * @return 16进制字符串
     * 
     * @since JDK1.4.2
     */
    private static String byte2hex(byte[] input)
    {

        if (input == null)
        {
            throw new RuntimeException("byte数组转化成16进制字符串:"+ "com.fenglian.Encrypt.byte2hex（参数 input 不能为 null）");
        }

        int len = input.length << 1;// 相当：len = input.length * 2;
        char[] buf = new char[len];

        for (int i = 0; i < input.length; i++)
        {
            buf[(i << 1)] = digits[(input[i] & 0XFF) >> 4]; // 高4位
            buf[(i << 1) + 1] = digits[input[i] & 0XF]; // 低4为
        }
        return new String(buf);
    }

    /**
     * 将16进制字符串str转化成byte数组。byte2hex(String str)的逆向方法<br>
     * 
     * @param str - 16进制字符串
     * @return byte数组
     * 
     * @since JDK1.4.2
     */
    private static byte[] hex2byte(String str)
    {

        if (str == null)
        {
            throw new RuntimeException("16进制字符串str转化成byte数组:"+
                    "com.fenglian.Encrypt.hex2byte（参数 str 不能为 null）");
        }

        int len = str.length();
        if ((len & 1) != 0)
        {// 长度len不能被2整除，表示str非法
            throw new RuntimeException("16进制字符串str转化成byte数组:"+
                    "com.fenglian.Encrypt.hex2byte（参数 str 的长度不能为奇数）");
        }

        byte[] buf = new byte[len >> 1]; // len/2

        for (int i = 0; i < len; i++)
        {
            buf[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str
                    .charAt(++i), 16));
        }

        return buf;
    }
    
    public static void main(String avgs[])
    {
        Encrypt md = new Encrypt();
        String aaa = "BC804FEC2769CE8377E809776411F7567961C60E858079F6";
        System.out.println(md.decrypt(aaa));
        System.out.println(md.md5("123456"));
        System.out.println(md.encrypt("123456"));
        
    }

}
