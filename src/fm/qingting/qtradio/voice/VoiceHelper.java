package fm.qingting.qtradio.voice;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VoiceHelper
{
  public static void Recognize(Context paramContext)
  {
    ByteArrayOutputStream localByteArrayOutputStream;
    try
    {
      InputStream localInputStream = paramContext.getAssets().open("test.pcm");
      localByteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        int i = localInputStream.read(arrayOfByte, 0, arrayOfByte.length);
        if (i == -1)
          break;
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return;
    }
    new String(localByteArrayOutputStream.toByteArray(), "UTF-8");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.voice.VoiceHelper
 * JD-Core Version:    0.6.2
 */