package fm.qingting.qtradio.view.popviews;

import android.content.Context;
import android.text.Layout.Alignment;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.social.CloudCenter.OnLoginEventListerner;
import fm.qingting.qtradio.view.playview.LineElement;
import fm.qingting.utils.QTMSGManage;

public class AccountPopView extends QtView
  implements ViewElement.OnElementClickListener
{
  private final String[] EVENT_TYPES = { "redirectToSina", "redirectToTencent" };
  private final int[] PLATFORM_ICONS = { 2130837971, 2130837972 };
  private final String[] PLATFORM_NAMES = { "新浪微博", "腾讯微博" };
  private final ViewLayout cancelLayout = this.standardLayout.createChildLT(720, 100, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = this.standardLayout.createChildLT(136, 220, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.standardLayout.createChildLT(720, 1, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private ButtonViewElement mBg;
  private ButtonViewElement mCancelElement;
  private PopActionButtonElement[] mItems;
  private LineElement mLineElement;
  private CloudCenter.OnLoginEventListerner mListerner;
  private TextViewElement mTitleElement;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private final ViewLayout titleLayout = this.standardLayout.createChildLT(720, 100, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public AccountPopView(Context paramContext)
  {
    super(paramContext);
    int i = hashCode();
    this.mBg = new ButtonViewElement(paramContext);
    this.mBg.setBackgroundColor(SkinManager.getNewPopBgColor(), SkinManager.getNewPopBgColor());
    addElement(this.mBg);
    this.mItems = new PopActionButtonElement[2];
    for (int j = 0; j < this.mItems.length; j++)
    {
      PopActionButtonElement localPopActionButtonElement = new PopActionButtonElement(paramContext);
      localPopActionButtonElement.setStyle(1);
      localPopActionButtonElement.setAction(this.PLATFORM_NAMES[j], this.PLATFORM_ICONS[j]);
      addElement(localPopActionButtonElement, i);
      this.mItems[j] = localPopActionButtonElement;
      localPopActionButtonElement.setOnElementClickListener(this);
    }
    this.mTitleElement = new TextViewElement(paramContext);
    this.mTitleElement.setMaxLineLimit(1);
    this.mTitleElement.setAlignment(Layout.Alignment.ALIGN_CENTER);
    this.mTitleElement.setColor(SkinManager.getNewPopTextColor());
    if (InfoManager.getInstance().forceLogin())
      this.mTitleElement.setText("听说登录后再下载,速度快到不行哟!", false);
    while (true)
    {
      addElement(this.mTitleElement);
      this.mCancelElement = new ButtonViewElement(paramContext);
      this.mCancelElement.setBackgroundColor(SkinManager.getItemHighlightMaskColor(), 0);
      this.mCancelElement.setText("取消");
      this.mCancelElement.setTextColor(SkinManager.getNewPopTextColor());
      addElement(this.mCancelElement);
      this.mCancelElement.setOnElementClickListener(this);
      this.mLineElement = new LineElement(paramContext);
      this.mLineElement.setColor(SkinManager.getDividerColor());
      this.mLineElement.setOrientation(1);
      addElement(this.mLineElement);
      return;
      this.mTitleElement.setText("请登录", false);
    }
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
    {
      int i = this.titleLayout.height + this.itemLayout.height + this.cancelLayout.height;
      if (paramMotionEvent.getY() < this.standardLayout.height - i)
      {
        dispatchActionEvent("cancelPop", null);
        return true;
      }
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    if (this.mCancelElement == paramViewElement)
      dispatchActionEvent("cancelPop", null);
    for (int i = 0; i < this.mItems.length; i++)
      if (paramViewElement == this.mItems[i])
      {
        if (InfoManager.getInstance().getBindRecommendShare())
        {
          InfoManager.getInstance().setBindRecommendShare(false);
          QTMSGManage.getInstance().sendStatistcsMessage("loginRecommendShare", this.PLATFORM_NAMES[i]);
        }
        dispatchActionEvent(this.EVENT_TYPES[i], this.mListerner);
      }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = 0;
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.titleLayout.scaleToBounds(this.standardLayout);
    this.itemLayout.scaleToBounds(this.standardLayout);
    this.cancelLayout.scaleToBounds(this.standardLayout);
    this.lineLayout.scaleToBounds(this.standardLayout);
    int j = this.titleLayout.height + this.itemLayout.height + this.cancelLayout.height;
    int k = this.standardLayout.height - j;
    this.mBg.measure(0, k, this.standardLayout.width, this.standardLayout.height);
    this.mTitleElement.measure(this.titleLayout);
    this.mTitleElement.setTextSize(SkinManager.getInstance().getNormalTextSize());
    this.mTitleElement.setTranslationY(k);
    int m = (this.standardLayout.width - 2 * this.itemLayout.width) / 3;
    int n = k + this.titleLayout.height;
    int i1 = m;
    while (i < this.mItems.length)
    {
      if (i % 2 == 0)
        i1 = m;
      this.mItems[i].measure(this.itemLayout);
      this.mItems[i].setTranslationX(i1);
      this.mItems[i].setTranslationY(n);
      if (i % 2 == 1)
        n += this.itemLayout.height;
      i1 += m + this.itemLayout.width;
      i++;
    }
    this.mCancelElement.measure(this.cancelLayout);
    this.mCancelElement.setTranslationY(this.standardLayout.height - this.cancelLayout.height);
    this.mCancelElement.setTextSize(SkinManager.getInstance().getNormalTextSize());
    this.mLineElement.measure(this.lineLayout);
    this.mLineElement.setTranslationY(this.standardLayout.height - this.cancelLayout.height);
    super.onMeasure(paramInt1, paramInt2);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setBubbleData"))
    {
      if (paramObject == null)
        this.mListerner = null;
    }
    else
      return;
    this.mListerner = ((CloudCenter.OnLoginEventListerner)paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.AccountPopView
 * JD-Core Version:    0.6.2
 */