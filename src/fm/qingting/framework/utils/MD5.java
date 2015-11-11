package fm.qingting.framework.utils;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{
  public static String md5Encode(String paramString)
  {
    MessageDigest localMessageDigest = null;
    byte[] arrayOfByte;
    StringBuffer localStringBuffer;
    int i;
    try
    {
      localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.reset();
      localMessageDigest.update(paramString.getBytes("UTF-8"));
      arrayOfByte = localMessageDigest.digest();
      localStringBuffer = new StringBuffer();
      i = 0;
      if (i >= arrayOfByte.length)
        return localStringBuffer.toString();
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      while (true)
      {
        System.out.println("NoSuchAlgorithmException caught!");
        System.exit(-1);
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        localUnsupportedEncodingException.printStackTrace();
    }
    if (Integer.toHexString(0xFF & arrayOfByte[i]).length() == 1)
      localStringBuffer.append("0").append(Integer.toHexString(0xFF & arrayOfByte[i]));
    while (true)
    {
      i++;
      break;
      localStringBuffer.append(Integer.toHexString(0xFF & arrayOfByte[i]));
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.MD5
 * JD-Core Version:    0.6.2
 */