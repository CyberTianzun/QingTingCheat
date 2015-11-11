package cn.com.iresearch.mapptracker.fm.util;

import cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class d
{
  private static IvParameterSpec a = new IvParameterSpec(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 });

  public static byte[] a(String paramString, byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = Base64.decodeBase64(paramString);
    SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte, "DES");
    Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    localCipher.init(2, localSecretKeySpec, a);
    return localCipher.doFinal(arrayOfByte);
  }

  public static byte[] a(byte[] paramArrayOfByte, String paramString)
  {
    SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramString.getBytes(), "DES");
    Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    localCipher.init(1, localSecretKeySpec, a);
    return localCipher.doFinal(paramArrayOfByte);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.util.d
 * JD-Core Version:    0.6.2
 */