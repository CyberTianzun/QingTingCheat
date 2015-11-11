package fm.qingting.qtradio.view.viewmodel;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.view.INavigationSetting.Mode;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.IViewEventListener;
import fm.qingting.framework.view.IViewModel;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;

public class NavigationBarModel extends ViewGroupViewImpl
  implements IViewModel
{
  private INavigationSetting.Mode mMode;
  protected IView mainView;
  protected final ViewLayout standardlayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  protected View topBar;
  protected final ViewLayout toplayout = ViewLayout.createViewLayoutWithBoundsLT(720, 114, 720, 1200, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);

  public NavigationBarModel(Context paramContext, INavigationSetting.Mode paramMode)
  {
    super(paramContext);
    this.mMode = paramMode;
  }

  public void LayoutView(ViewController paramViewController)
  {
    if ((paramViewController instanceof IEventHandler))
      setEventHandler((IEventHandler)paramViewController);
    IView localIView1 = paramViewController.getView();
    if (localIView1 != null)
    {
      this.mainView = localIView1;
      if ((this.mainView instanceof IViewEventListener))
        addViewEventListener((IViewEventListener)this.mainView);
      addView(this.mainView.getView());
    }
    IView localIView2 = paramViewController.getTopBar();
    if (localIView2 != null)
    {
      this.topBar = localIView2.getView();
      addView(this.topBar);
    }
    paramViewController.setShellView(this);
    addViewEventListener(paramViewController);
    requestLayout();
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((this.topBar == null) && (this.mainView == null));
    do
    {
      return;
      if ((this.topBar != null) && (this.mainView != null))
      {
        int i = 1.$SwitchMap$fm$qingting$framework$view$INavigationSetting$Mode[this.mMode.ordinal()];
        int j = 0;
        switch (i)
        {
        case 2:
        default:
        case 1:
        }
        while (true)
        {
          this.mainView.getView().layout(paramInt1, j + paramInt2, paramInt3, paramInt2 + this.standardlayout.height);
          this.topBar.layout(paramInt1, paramInt2, paramInt3, paramInt2 + this.toplayout.height);
          return;
          j = this.toplayout.height;
        }
      }
    }
    while ((this.topBar != null) || (this.mainView == null));
    this.mainView.getView().layout(paramInt1, paramInt2, paramInt3, paramInt2 + this.standardlayout.height);
  }

  public void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardlayout.scaleToBounds(i, j);
    this.toplayout.scaleToBounds(this.standardlayout);
    if ((this.topBar == null) && (this.mainView == null))
    {
      setMeasuredDimension(this.standardlayout.width, this.standardlayout.height);
      return;
    }
    int m;
    if ((this.topBar != null) && (this.mainView != null))
    {
      this.toplayout.measureView(this.topBar);
      int k = 1.$SwitchMap$fm$qingting$framework$view$INavigationSetting$Mode[this.mMode.ordinal()];
      m = 0;
      switch (k)
      {
      default:
        this.mainView.getView().measure(this.standardlayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(m, 1073741824));
      case 1:
      case 2:
      }
    }
    while (true)
    {
      setMeasuredDimension(this.standardlayout.width, this.standardlayout.height);
      return;
      m = this.standardlayout.height - this.toplayout.height;
      break;
      m = this.standardlayout.height;
      break;
      if ((this.topBar == null) && (this.mainView != null))
        this.standardlayout.measureView(this.mainView.getView());
    }
  }

  public void setActivate(boolean paramBoolean)
  {
    this.mainView.setActivate(paramBoolean);
  }

  public void update(String paramString, Object paramObject)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.viewmodel.NavigationBarModel
 * JD-Core Version:    0.6.2
 */