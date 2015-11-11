package fm.qingting.qtradio.view.popviews;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.utils.ScreenType;

class ShareActionContainerView extends QtView
  implements ViewElement.OnElementClickListener
{
  private final int[] PLATFORM_ICONS = { 2130837968, 2130837973, 2130837971, 2130837969, 2130837970, 2130837972 };
  private final String[] PLATFORM_NAMES = { "朋友圈", "微信好友", "新浪微博", "QQ好友", "QQ空间", "腾讯微博" };
  private final ViewLayout containerLayout = this.standardLayout.createChildLT(204, 220, 22, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = this.containerLayout.createChildLT(136, 220, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private PopActionButtonElement[] mItems;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public ShareActionContainerView(Context paramContext)
  {
    super(paramContext);
    int j = hashCode();
    this.mItems = new PopActionButtonElement[6];
    while (i < this.mItems.length)
    {
      PopActionButtonElement localPopActionButtonElement = new PopActionButtonElement(paramContext);
      localPopActionButtonElement.setStyle(1);
      localPopActionButtonElement.setAction(this.PLATFORM_NAMES[i], this.PLATFORM_ICONS[i]);
      addElement(localPopActionButtonElement, j);
      this.mItems[i] = localPopActionButtonElement;
      localPopActionButtonElement.setOnElementClickListener(this);
      i++;
    }
  }

  private int getPastType(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
    default:
      return 0;
    case 0:
      return 1;
    case 2:
      return 4;
    case 3:
      return 3;
    case 4:
      return 2;
    case 5:
    }
    return 5;
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    for (int i = 0; ; i++)
      if (i < this.mItems.length)
      {
        if (paramViewElement == this.mItems[i])
          EventDispacthManager.getInstance().dispatchAction("shareToPlatform", Integer.valueOf(getPastType(i)));
      }
      else
        return;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(ScreenType.getWidth(), View.MeasureSpec.getSize(paramInt2));
    this.containerLayout.scaleToBounds(this.standardLayout);
    this.itemLayout.scaleToBounds(this.containerLayout);
    int i = this.containerLayout.leftMargin;
    for (int j = 0; j < this.mItems.length; j++)
    {
      this.mItems[j].measure(this.itemLayout);
      this.mItems[j].setTranslationX(i);
      i += this.containerLayout.width;
    }
    setMeasuredDimension(i + this.containerLayout.leftMargin, this.standardLayout.height);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.ShareActionContainerView
 * JD-Core Version:    0.6.2
 */