package cn.com.iresearch.mapptracker.fm.util;

public class DataProvider
{
  static
  {
    try
    {
      System.loadLibrary("mresearch");
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
    }
  }

  public static native String getVVUid();

  public static native String pd();
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.util.DataProvider
 * JD-Core Version:    0.6.2
 */