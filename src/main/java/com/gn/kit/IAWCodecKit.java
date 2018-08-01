package com.gn.kit;

import java.util.zip.CRC32;
import org.apache.commons.codec.digest.DigestUtils;

import com.common.aes.AES;
import com.jfinal.kit.AesKit;
import com.jfinal.kit.Base64Kit;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.security.krb5.internal.crypto.Aes128;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;

public class IAWCodecKit {
	/**
	 * md5 16进制
	 * 
	 * @param para
	 * @return
	 */
	public static String md5(String para) {
		return DigestUtils.md5Hex(para);
	}

	/**
	 * crc32编码
	 * 
	 * @param para
	 * @return
	 */
	public static Long crc32(String para) {
		CRC32 crc32 = new CRC32();
		crc32.update(para.getBytes());
		return crc32.getValue();
	}

	private static final String KEY = "1111111111111111";
	private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

	public static String base64Encode(byte[] bytes) {
		return Base64Kit.encode(bytes);
	}

	public static byte[] base64Decode(String base64Code) throws Exception {
		return Base64Kit.decode(base64Code);
	}

	private static byte[] aesEncryptToBytes(String content, String sKey) throws Exception {
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		// // 判断Key是否�?16�?
		// if (sKey.length() != 16) {
		// System.out.print("Key长度不是16�?");
		// return null;
		// }
		String keyMd5 = md5(sKey).substring(8, 24);
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyMd5.getBytes(), "AES"));

		return cipher.doFinal(content.getBytes("utf-8"));
	}

	private static String aesDecryptByBytes(byte[] encryptBytes, String sKey) throws Exception {
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
//		// 判断Key是否�?16�?
//		if (sKey.length() != 16) {
//			System.out.print("Key长度不是16�?");
//			return null;
//		}
		String keyMd5 = md5(sKey).substring(8, 24);
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);

		Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyMd5.getBytes(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);

		return new String(decryptBytes);
	}

	/**
	 * 加密
	 * 
	 * @param content
	 * @param encryptKey
	 * @return
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	/**
	 * 解密
	 * 
	 * @param encryptStr
	 * @param decryptKey
	 * @return
	 * @throws Exception
	 */
	public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
		return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
	}

	/**
	 * 测试
	 * 
	 */
	public static void main(String[] args) throws Exception {

//		String content = "11"; // 0gqIDaFNAAmwvv3tKsFOFf9P9m/6MWlmtB8SspgxqpWKYnELb/lXkyXm7P4sMf3e
//		System.out.println("加密前：" + content);
//
//		System.out.println("加密密钥和解密密钥：" + KEY.length());
//
		String encrypt = aesEncrypt("你好，了你就看见", "abcdefgabcdefg12");
		System.out.println(encrypt.length() + ":加密后：" + encrypt);
//		System.out.println(md5("aaaa").substring(8, 24).length());
		System.out.println(aesDecrypt("Jz2f+FHUm+3F3cN19vfwMMFOT8XZ1GItbBABxQ/kK8E=", "abcdefgabcdefg12"));
		// s
		// String decrypt = aesDecrypt("QATgqFVkxOdf7tJ+ez2v7lLm0WpzVEVW1jfj/bWdekA=",
		// KEY);
		// System.out.println("解密后：" + decrypt);

	}

}
