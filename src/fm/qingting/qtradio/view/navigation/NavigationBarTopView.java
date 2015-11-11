package fm.qingting.qtradio.view.navigation;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.view.navigation.items.TopAutoScalePlainView;
import fm.qingting.qtradio.view.navigation.items.TopAutoScaleView;
import fm.qingting.qtradio.view.navigation.items.TopButtonView;
import fm.qingting.qtradio.view.navigation.items.TopCustomButton;
import fm.qingting.qtradio.view.navigation.items.TopRecordView;
import fm.qingting.qtradio.view.navigation.items.TopTextView;

public class NavigationBarTopView extends ViewGroupViewImpl
  implements IEventHandler
{
  private INavigationBarListener barListener;
  private LinearLayout leftContainer;
  private final ViewLayout leftLayout = this.standardLayout.createChildLT(702, 114, 8, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private LinearLayout rightContainer;
  private final ViewLayout rightLayout = this.standardLayout.createChildLT(702, 114, 10, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 114, 720, 114, 0, 0, ViewLayout.FILL);
  private LinearLayout titleContainer;
  private final ViewLayout titleLayout = this.standardLayout.createChildLT(480, 98, 120, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);

  public NavigationBarTopView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getNaviBgColor());
    this.titleContainer = new LinearLayout(paramContext);
    this.titleContainer.setGravity(17);
    this.titleContainer.setVisibility(4);
    addView(this.titleContainer);
    this.rightContainer = new LinearLayout(paramContext);
    this.rightContainer.setGravity(5);
    addView(this.rightContainer);
    this.leftContainer = new LinearLayout(paramContext);
    this.leftContainer.setGravity(3);
    addView(this.leftContainer);
  }

  public void close(boolean paramBoolean)
  {
    this.barListener = null;
    super.close(paramBoolean);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    return null;
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if ((paramString.equalsIgnoreCase("click")) && (paramObject2 != null) && (this.barListener != null))
      this.barListener.onItemClick(((Integer)paramObject2).intValue());
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3 - paramInt1;
    int j = paramInt4 - paramInt2;
    this.standardLayout.scaleToBounds(i, j);
    this.titleLayout.scaleToBounds(this.standardLayout);
    this.rightLayout.scaleToBounds(this.standardLayout);
    this.leftLayout.scaleToBounds(this.standardLayout);
    if (j > this.standardLayout.height)
    {
      int k = (j - this.standardLayout.height) / 2;
      this.titleContainer.layout(this.titleLayout.getLeft(), k + this.titleLayout.getTop(), this.titleLayout.getLeft() + this.titleLayout.width, k + (this.titleLayout.getTop() + this.titleLayout.height));
      this.rightContainer.layout(this.rightLayout.getLeft(), k + this.rightLayout.getTop(), this.rightLayout.getLeft() + this.rightLayout.width, k + (this.rightLayout.getTop() + this.rightLayout.height));
      this.leftContainer.layout(this.leftLayout.getLeft(), k + this.leftLayout.getTop(), this.leftLayout.getLeft() + this.leftLayout.width, k + (this.leftLayout.getTop() + this.leftLayout.height));
      return;
    }
    this.titleLayout.layoutView(this.titleContainer);
    this.rightLayout.layoutView(this.rightContainer);
    this.leftLayout.layoutView(this.leftContainer);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.titleLayout.scaleToBounds(this.standardLayout);
    this.rightLayout.scaleToBounds(this.standardLayout);
    this.leftLayout.scaleToBounds(this.standardLayout);
    this.titleLayout.measureView(this.titleContainer);
    this.titleContainer.measure(View.MeasureSpec.makeMeasureSpec(this.titleLayout.getWidthMeasureSpec(), 0), View.MeasureSpec.makeMeasureSpec(this.titleLayout.getHeightMeasureSpec(), 0));
    this.rightLayout.measureView(this.rightContainer);
    this.leftLayout.measureView(this.leftContainer);
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void setBarListener(INavigationBarListener paramINavigationBarListener)
  {
    this.barListener = paramINavigationBarListener;
  }

  public void setLeftItem(int paramInt)
  {
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
    TopButtonView localTopButtonView = new TopButtonView(getContext(), paramInt);
    localTopButtonView.setEventHandler(this);
    localTopButtonView.setItemType(2);
    this.leftContainer.removeAllViews();
    this.leftContainer.setVisibility(0);
    this.leftContainer.addView(localTopButtonView, localLayoutParams);
  }

  public void setLeftItem(String paramString)
  {
    if (this.leftContainer.getChildCount() == 1)
    {
      IView localIView = (IView)this.leftContainer.getChildAt(0);
      if ((localIView instanceof TopAutoScaleView))
      {
        ((TopAutoScaleView)localIView).setTitle(paramString);
        this.leftContainer.setVisibility(0);
        return;
      }
    }
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
    TopAutoScaleView localTopAutoScaleView = new TopAutoScaleView(getContext());
    localTopAutoScaleView.setItemType(2);
    localTopAutoScaleView.setEventHandler(this);
    localTopAutoScaleView.setTitle(paramString);
    this.leftContainer.removeAllViews();
    this.leftContainer.setVisibility(0);
    this.leftContainer.addView(localTopAutoScaleView, localLayoutParams);
  }

  public void setLeftItem(String paramString, int paramInt1, int paramInt2)
  {
    if (this.leftContainer.getChildCount() == 1)
    {
      IView localIView = (IView)this.leftContainer.getChildAt(0);
      if ((localIView instanceof TopAutoScalePlainView))
      {
        ((TopAutoScalePlainView)localIView).setTitle(paramString);
        this.leftContainer.setVisibility(0);
        return;
      }
    }
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
    TopAutoScalePlainView localTopAutoScalePlainView = new TopAutoScalePlainView(getContext(), paramInt1, paramInt2);
    localTopAutoScalePlainView.setItemType(2);
    localTopAutoScalePlainView.setEventHandler(this);
    localTopAutoScalePlainView.setTitle(paramString);
    this.leftContainer.removeAllViews();
    this.leftContainer.setVisibility(0);
    this.leftContainer.addView(localTopAutoScalePlainView, localLayoutParams);
  }

  public void setRecordItem(String paramString)
  {
    if (this.rightContainer.getChildCount() == 1)
    {
      IView localIView = (IView)this.rightContainer.getChildAt(0);
      if ((localIView instanceof TopRecordView))
      {
        ((TopRecordView)localIView).setTitle(paramString);
        this.rightContainer.setVisibility(0);
        return;
      }
    }
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
    TopRecordView localTopRecordView = new TopRecordView(getContext());
    localTopRecordView.setItemType(3);
    localTopRecordView.setEventHandler(this);
    localTopRecordView.setTitle(paramString);
    this.rightContainer.removeAllViews();
    this.rightContainer.setVisibility(0);
    this.rightContainer.addView(localTopRecordView, localLayoutParams);
  }

  public void setRightItem(int paramInt)
  {
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
    TopButtonView localTopButtonView = new TopButtonView(getContext(), paramInt);
    localTopButtonView.setEventHandler(this);
    localTopButtonView.setItemType(3);
    this.rightContainer.removeAllViews();
    this.rightContainer.setVisibility(0);
    this.rightContainer.addView(localTopButtonView, localLayoutParams);
  }

  public void setRightItem(String paramString)
  {
    if (this.rightContainer.getChildCount() == 1)
    {
      IView localIView = (IView)this.rightContainer.getChildAt(0);
      if ((localIView instanceof TopCustomButton))
      {
        ((TopCustomButton)localIView).setTitle(paramString);
        this.rightContainer.setVisibility(0);
        return;
      }
    }
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
    TopCustomButton localTopCustomButton = new TopCustomButton(getContext());
    localTopCustomButton.setItemType(3);
    localTopCustomButton.setEventHandler(this);
    localTopCustomButton.setTitle(paramString);
    this.rightContainer.removeAllViews();
    this.rightContainer.setVisibility(0);
    this.rightContainer.addView(localTopCustomButton, localLayoutParams);
  }

  public void setRightItem(String paramString, int paramInt1, int paramInt2)
  {
    if (this.rightContainer.getChildCount() == 1)
    {
      IView localIView = (IView)this.rightContainer.getChildAt(0);
      if ((localIView instanceof TopAutoScalePlainView))
      {
        ((TopAutoScalePlainView)localIView).setTitle(paramString);
        this.rightContainer.setVisibility(0);
        return;
      }
    }
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
    TopAutoScalePlainView localTopAutoScalePlainView = new TopAutoScalePlainView(getContext(), paramInt1, paramInt2);
    localTopAutoScalePlainView.setItemType(3);
    localTopAutoScalePlainView.setEventHandler(this);
    localTopAutoScalePlainView.setTitle(paramString);
    this.rightContainer.removeAllViews();
    this.rightContainer.setVisibility(0);
    this.rightContainer.addView(localTopAutoScalePlainView, localLayoutParams);
  }

  public void setRightItemVisibility(int paramInt)
  {
    this.rightContainer.setVisibility(paramInt);
  }

  public void setRightTip(String paramString)
  {
    if (this.rightContainer.getChildCount() == 1)
    {
      IView localIView = (IView)this.rightContainer.getChildAt(0);
      if ((localIView instanceof TopAutoScaleView))
        ((TopAutoScaleView)localIView).setTip(paramString);
    }
  }

  public void setTitleItem(NavigationBarItem paramNavigationBarItem)
  {
    if (paramNavigationBarItem == null)
      return;
    if (paramNavigationBarItem.getCustomeView() != null)
    {
      LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-2, -1);
      this.titleContainer.removeAllViews();
      this.titleContainer.addView(paramNavigationBarItem.getCustomeView(), localLayoutParams1);
      this.titleContainer.setVisibility(0);
      return;
    }
    if (paramNavigationBarItem.getTitle() != null)
    {
      LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-2, -1);
      TopTextView localTopTextView = new TopTextView(getContext());
      localTopTextView.setTitle(paramNavigationBarItem.getTitle());
      if (paramNavigationBarItem.getTextColor() != 0)
        localTopTextView.setTitleColor(paramNavigationBarItem.getTextColor());
      localTopTextView.setItemType(1);
      localTopTextView.setEventHandler(this);
      this.titleContainer.removeAllViews();
      this.titleContainer.addView(localTopTextView, localLayoutParams2);
      this.titleContainer.setVisibility(0);
      return;
    }
    this.titleContainer.setVisibility(4);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.navigation.NavigationBarTopView
 * JD-Core Version:    0.6.2
 */