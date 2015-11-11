package fm.qingting.utils;

public class FansUtils
{
  public static String toString(long paramLong)
  {
    if (paramLong <= 0L)
      return "暂无粉丝";
    if (paramLong < 10000L)
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Long.valueOf(paramLong);
      return String.format("%d个粉丝", arrayOfObject2);
    }
    float f = (float)paramLong / 10000.0F;
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Float.valueOf(f);
    return String.format("%.1f万粉丝", arrayOfObject1);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.FansUtils
 * JD-Core Version:    0.6.2
 */