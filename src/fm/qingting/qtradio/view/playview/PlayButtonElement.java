package fm.qingting.qtradio.view.playview;

import android.content.Context;

public class PlayButtonElement extends AbsPlayButtonElement
{
  public PlayButtonElement(Context paramContext)
  {
    super(paramContext);
  }

  protected int getBufferResource(boolean paramBoolean)
  {
    return 2130837757;
  }

  protected int getErrorResource(boolean paramBoolean)
  {
    if (paramBoolean)
      return 2130837758;
    return 2130837757;
  }

  protected int getPauseResource(boolean paramBoolean)
  {
    if (paramBoolean)
      return 2130837776;
    return 2130837775;
  }

  protected int getPlayResource(boolean paramBoolean)
  {
    if (paramBoolean)
      return 2130837778;
    return 2130837777;
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview.PlayButtonElement
 * JD-Core Version:    0.6.2
 */