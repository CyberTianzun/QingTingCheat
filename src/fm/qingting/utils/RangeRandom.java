package fm.qingting.utils;

import java.util.Random;

public class RangeRandom
{
  private static Random random = new Random(System.nanoTime());

  public static long Random(long paramLong)
  {
    long l = random.nextLong();
    if (l < 0L)
      l = 0L - l;
    return l % paramLong;
  }

  public static boolean random(double paramDouble)
  {
    return Random(1000000L) < paramDouble * 1000000L;
  }

  public static boolean randomSwitch()
  {
    return Random(1000000L) % 2L == 1L;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.RangeRandom
 * JD-Core Version:    0.6.2
 */