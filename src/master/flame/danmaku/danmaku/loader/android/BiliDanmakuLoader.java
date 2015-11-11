package master.flame.danmaku.danmaku.loader.android;

import java.io.InputStream;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.parser.android.AndroidFileSource;

public class BiliDanmakuLoader
  implements ILoader
{
  private static BiliDanmakuLoader _instance;
  private AndroidFileSource dataSource;

  public static BiliDanmakuLoader instance()
  {
    if (_instance == null)
      _instance = new BiliDanmakuLoader();
    return _instance;
  }

  public AndroidFileSource getDataSource()
  {
    return this.dataSource;
  }

  public void load(InputStream paramInputStream)
  {
    this.dataSource = new AndroidFileSource(paramInputStream);
  }

  public void load(String paramString)
    throws IllegalDataException
  {
    try
    {
      this.dataSource = new AndroidFileSource(paramString);
      return;
    }
    catch (Exception localException)
    {
      throw new IllegalDataException(localException);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.loader.android.BiliDanmakuLoader
 * JD-Core Version:    0.6.2
 */