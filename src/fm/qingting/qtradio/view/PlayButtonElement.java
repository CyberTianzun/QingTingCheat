package fm.qingting.qtradio.view;

import android.content.Context;
import fm.qingting.qtradio.view.playview.AbsPlayButtonElement;

class PlayButtonElement extends AbsPlayButtonElement
{
  public PlayButtonElement(Context paramContext)
  {
    super(paramContext);
  }

  protected int getBufferResource(boolean paramBoolean)
  {
    return 2130837831;
  }

  protected int getErrorResource(boolean paramBoolean)
  {
    if (paramBoolean)
      return 2130837832;
    return 2130837831;
  }

  protected int getPauseResource(boolean paramBoolean)
  {
    if (paramBoolean);
    return 2130837838;
  }

  protected int getPlayResource(boolean paramBoolean)
  {
    if (paramBoolean)
      return 2130837841;
    return 2130837840;
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.PlayButtonElement
 * JD-Core Version:    0.6.2
 */