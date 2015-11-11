package master.flame.danmaku.danmaku.model;

public class GlobalFlagValues
{
  public static int MEASURE_RESET_FLAG = 0;
  public static int VISIBLE_RESET_FLAG = 0;

  public static void resetAll()
  {
    VISIBLE_RESET_FLAG = 0;
    MEASURE_RESET_FLAG = 0;
  }

  public static void updateMeasureFlag()
  {
    MEASURE_RESET_FLAG = 1 + MEASURE_RESET_FLAG;
  }

  public static void updateVisibleFlag()
  {
    VISIBLE_RESET_FLAG = 1 + VISIBLE_RESET_FLAG;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.GlobalFlagValues
 * JD-Core Version:    0.6.2
 */