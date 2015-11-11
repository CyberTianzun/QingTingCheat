package fm.qingting.downloadnew;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.binary.Base64;
import fm.qingting.qtradio.wo.WoApiRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class QTDownloadNetwork extends DefaultNetwork
{
  private static boolean isViaWoProxy = false;
  private final int CHUNK_SIZE = 4194304;

  QTDownloadNetwork(Context paramContext, EventDispatcher paramEventDispatcher, String paramString)
  {
    super(paramContext, paramEventDispatcher, paramString);
  }

  private String calcETag(String paramString)
    throws IOException, NoSuchAlgorithmException
  {
    Log.i(this.TAG, "开始计算下载得到文件的checksum");
    File localFile = new File(paramString);
    if ((!localFile.exists()) || (!localFile.isFile()) || (!localFile.canRead()))
    {
      Log.e(this.TAG, "无法读取文件");
      return "";
    }
    long l = localFile.length();
    FileInputStream localFileInputStream = new FileInputStream(localFile);
    byte[] arrayOfByte9;
    if (l <= 4194304L)
    {
      byte[] arrayOfByte7 = new byte[(int)l];
      localFileInputStream.read(arrayOfByte7, 0, (int)l);
      byte[] arrayOfByte8 = sha1(arrayOfByte7);
      int m = arrayOfByte8.length;
      arrayOfByte9 = new byte[m + 1];
      System.arraycopy(arrayOfByte8, 0, arrayOfByte9, 1, m);
      arrayOfByte9[0] = 22;
    }
    byte[] arrayOfByte2;
    for (String str = urlSafeBase64Encode(arrayOfByte9); ; str = urlSafeBase64Encode(arrayOfByte2))
    {
      localFileInputStream.close();
      Log.d(this.TAG, "checksum = " + str);
      return str;
      int i = (int)(l / 4194304L);
      if (l % 4194304L != 0L)
        i++;
      Object localObject = new byte[0];
      int j = 0;
      while (j < i)
      {
        byte[] arrayOfByte3 = new byte[4194304];
        int k = localFileInputStream.read(arrayOfByte3, 0, 4194304);
        byte[] arrayOfByte4 = new byte[k];
        System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 0, k);
        byte[] arrayOfByte5 = sha1(arrayOfByte4);
        byte[] arrayOfByte6 = new byte[arrayOfByte5.length + localObject.length];
        System.arraycopy(localObject, 0, arrayOfByte6, 0, localObject.length);
        System.arraycopy(arrayOfByte5, 0, arrayOfByte6, localObject.length, arrayOfByte5.length);
        j++;
        localObject = arrayOfByte6;
      }
      byte[] arrayOfByte1 = sha1((byte[])localObject);
      arrayOfByte2 = new byte[1 + arrayOfByte1.length];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 1, arrayOfByte1.length);
      arrayOfByte2[0] = -106;
    }
  }

  public static void setIsViaWoProxy(boolean paramBoolean)
  {
    isViaWoProxy = paramBoolean;
  }

  private byte[] sha1(byte[] paramArrayOfByte)
    throws NoSuchAlgorithmException
  {
    return MessageDigest.getInstance("sha1").digest(paramArrayOfByte);
  }

  protected boolean checkFile()
  {
    if (TextUtils.isEmpty(this.mCurTask.mExtra))
      return true;
    String str1 = this.mCurTask.mExtra;
    Object localObject = "";
    try
    {
      String str2 = calcETag(this.mCurTask.mFileName);
      localObject = str2;
      label42: return str1.equalsIgnoreCase((String)localObject);
    }
    catch (Exception localException)
    {
      break label42;
    }
  }

  protected HttpURLConnection openConnection(URL paramURL)
    throws IOException
  {
    if (isViaWoProxy);
    while (true)
    {
      try
      {
        String str1 = WoApiRequest.getProxyServer();
        if (str1 != null)
        {
          String str2 = WoApiRequest.getProxyPortString();
          System.getProperties().put("http.proxySet", "true");
          System.getProperties().put("http.proxyHost", str1);
          System.getProperties().put("http.proxyPort", str2);
          Authenticator.setDefault(new Authenticator()
          {
            protected PasswordAuthentication getPasswordAuthentication()
            {
              return new PasswordAuthentication(WoApiRequest.getAppKey(), WoApiRequest.getAppSecret().toCharArray());
            }
          });
        }
        return (HttpURLConnection)paramURL.openConnection();
      }
      catch (Exception localException)
      {
        Log.e(this.TAG, "设置代理失败");
        continue;
      }
      System.getProperties().put("http.proxySet", "false");
    }
  }

  public String urlSafeBase64Encode(byte[] paramArrayOfByte)
  {
    return new String(Base64.encodeBase64(paramArrayOfByte)).replace('+', '-').replace('/', '_');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.downloadnew.QTDownloadNetwork
 * JD-Core Version:    0.6.2
 */