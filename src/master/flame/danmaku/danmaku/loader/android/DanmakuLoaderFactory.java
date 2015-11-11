package master.flame.danmaku.danmaku.loader.android;

import master.flame.danmaku.danmaku.loader.ILoader;

public class DanmakuLoaderFactory
{
  public static String TAG_ACFUN = "acfun";
  public static String TAG_BILI = "bili";

  public static ILoader create(String paramString)
  {
    if (TAG_BILI.equalsIgnoreCase(paramString))
      return BiliDanmakuLoader.instance();
    if (TAG_ACFUN.equalsIgnoreCase(paramString))
      return AcFunDanmakuLoader.instance();
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory
 * JD-Core Version:    0.6.2
 */