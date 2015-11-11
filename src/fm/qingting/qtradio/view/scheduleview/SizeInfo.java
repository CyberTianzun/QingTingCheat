package fm.qingting.qtradio.view.scheduleview;

import java.util.Locale;

public class SizeInfo
{
  private static final String SIZEMODEL = "共%d个文件，占用%s";
  public int mCnt;
  public int mFileSize;
  public String mSizeString;

  public static String getFileSize(long paramLong)
  {
    String str = "";
    if (paramLong < 0L)
      str = "";
    do
    {
      return str;
      if (paramLong == 0L)
        return "0";
      if (paramLong < 1000L)
      {
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = Long.valueOf(paramLong);
        return String.format("%dB", arrayOfObject4);
      }
      if (paramLong < 1000000L)
      {
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Float.valueOf((float)paramLong / 1000.0F);
        return String.format("%.1fkB", arrayOfObject3);
      }
      if (paramLong < 1000000000L)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Float.valueOf((float)paramLong / 1000000.0F);
        return String.format("%.1fM", arrayOfObject2);
      }
    }
    while (paramLong >= 1000000000000L);
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Float.valueOf((float)paramLong / 1.0E+09F);
    return String.format("%.1fG", arrayOfObject1);
  }

  public static String getStorageInfo(int paramInt, long paramLong)
  {
    Locale localLocale = Locale.CHINA;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    arrayOfObject[1] = getFileSize(paramLong);
    return String.format(localLocale, "共%d个文件，占用%s", arrayOfObject);
  }

  public static SizeInfo getSumInfo(SizeInfo paramSizeInfo1, SizeInfo paramSizeInfo2)
  {
    if (paramSizeInfo1 == null)
      return paramSizeInfo2;
    if (paramSizeInfo2 == null)
      return paramSizeInfo1;
    SizeInfo localSizeInfo = new SizeInfo();
    paramSizeInfo1.mCnt += paramSizeInfo2.mCnt;
    paramSizeInfo1.mFileSize += paramSizeInfo2.mFileSize;
    localSizeInfo.mSizeString = getFileSize(localSizeInfo.mFileSize);
    return localSizeInfo;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.scheduleview.SizeInfo
 * JD-Core Version:    0.6.2
 */