package edu.tdp2.server.utils;

import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Encrypter
{
	private Cipher ecipher;
	private Cipher dcipher;

	private static final char[] KEY = { 'S', 'K', 'R', 'A', '2', 'U', 'd', 'M', '5', '9', 'L', 't', 'K', 'L', 'l', 'L',
			'7', '0', 'V', 'r', 'S', 'I', 'N', 'r', 'e', 'h', 'M', 'f', 'W', 'm', '3', 'a', 'l', 'M', '7', 'n', 'J',
			'X', 'h', 'a', 'a', 'm', 'N', '3', 'f', 's', 'E', 'c', 'T', 's', 'N', 'K', 'x', 's', 'a', 'R', 'z', 'X' };

	// 8-byte Salt
	private byte[] salt = { (byte) 0x53, (byte) 0x32, (byte) 0xC8, (byte) 0x03, (byte) 0x96, (byte) 0xF9, (byte) 0xBC,
			(byte) 0x3A };

	// Iteration count
	private int iterationCount = 19;

	private static Encrypter instance;

	public synchronized static Encrypter getInstance()
	{
		if (instance == null)
			instance = new Encrypter();
		return instance;
	}

	private Encrypter()
	{
		try
		{
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			// Create the key
			KeySpec keySpec = new PBEKeySpec(getKey(KEY), salt, iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			// Prepare the parameter to the ciphers
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

			// Create the ciphers
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		}
		catch (GeneralSecurityException e)
		{
			throw new RuntimeException(e);
		}

	}

	private char[] getKey(char[] sourceKey)
	{
		String definitiveKey = "";
		int i = 0;
		for (char aChar : sourceKey)
		{
			i++;
			if (i % 5 == 0 || (i - 2) % 5 == 0 || (i - 3) % 5 == 0)
				definitiveKey += aChar;
		}
		return definitiveKey.toCharArray();
	}

	public synchronized String encrypt(String str)
	{
		try
		{
			byte[] utf8 = str.getBytes("UTF8");

			byte[] enc = ecipher.doFinal(utf8);// Encrypt

			// Encode bytes to base64 to get a string
			return new EncryptionCodec().encode(enc);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public synchronized String decrypt(String str)
	{
		try
		{
			// Decode base64 to get bytes
			byte[] dec = new EncryptionCodec().decode(str);

			byte[] utf8 = dcipher.doFinal(dec); // Decrypt

			return new String(utf8, "UTF8");
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
