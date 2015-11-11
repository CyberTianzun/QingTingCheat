package master.flame.danmaku.danmaku.parser.android;

import android.net.Uri;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.util.IOUtils;

public class AndroidFileSource
  implements IDataSource<InputStream>
{
  private InputStream inStream;

  public AndroidFileSource(Uri paramUri)
  {
    fillStreamFromUri(paramUri);
  }

  public AndroidFileSource(File paramFile)
  {
    fillStreamFromFile(paramFile);
  }

  public AndroidFileSource(InputStream paramInputStream)
  {
    this.inStream = paramInputStream;
  }

  public AndroidFileSource(String paramString)
  {
    fillStreamFromFile(new File(paramString));
  }

  public InputStream data()
  {
    return this.inStream;
  }

  public void fillStreamFromFile(File paramFile)
  {
    try
    {
      this.inStream = new BufferedInputStream(new FileInputStream(paramFile));
      return;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
    }
  }

  public void fillStreamFromHttpFile(Uri paramUri)
  {
    try
    {
      URL localURL = new URL(paramUri.getPath());
      localURL.openConnection();
      this.inStream = new BufferedInputStream(localURL.openStream());
      return;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public void fillStreamFromUri(Uri paramUri)
  {
    String str = paramUri.getScheme();
    if (("http".equalsIgnoreCase(str)) || ("https".equalsIgnoreCase(str)))
      fillStreamFromHttpFile(paramUri);
    while (!"file".equalsIgnoreCase(str))
      return;
    fillStreamFromFile(new File(paramUri.getPath()));
  }

  public void release()
  {
    IOUtils.closeQuietly(this.inStream);
    this.inStream = null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.parser.android.AndroidFileSource
 * JD-Core Version:    0.6.2
 */