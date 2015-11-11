package fm.qingting.qtradio.view.navigation;

public class NaviFaceType
{
  public static final int BACK = 0;
  public static final int CONFIRM = 2;
  public static final int FILTER = 3;
  public static final int SEARCH = 1;
  public static final int SHARE = 4;

  public static int getHighlightRes(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
    default:
      return 2130837859;
    case 1:
      return 2130837867;
    case 2:
      return 2130837862;
    case 3:
      return 2130837864;
    case 4:
    }
    return 2130837869;
  }

  public static int getNormalRes(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
    default:
      return 2130837859;
    case 1:
      return 2130837867;
    case 2:
      return 2130837862;
    case 3:
      return 2130837863;
    case 4:
    }
    return 2130837869;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.navigation.NaviFaceType
 * JD-Core Version:    0.6.2
 */