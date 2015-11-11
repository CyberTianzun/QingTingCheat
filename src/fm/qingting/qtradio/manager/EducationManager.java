package fm.qingting.qtradio.manager;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.view.education.balloon.EducationType;

public class EducationManager
{
  private static EducationManager sInstance;
  private Context mContext;
  private WindowManager.LayoutParams mLp;
  private boolean mShowing = false;
  private IView mView;
  private WindowManager mWm;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public static EducationManager getInstance()
  {
    if (sInstance == null)
      sInstance = new EducationManager();
    return sInstance;
  }

  private int getLeft(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt2 + paramInt4;
    int j = 0x111111 & paramInt1;
    if (j == 0)
      i = 0;
    do
    {
      return i;
      if ((j & 0x1) > 0)
        return paramInt2 + paramInt4;
      if ((j & 0x10) > 0)
        return paramInt4 + (paramInt2 - paramInt3);
    }
    while ((j & 0x100) <= 0);
    return paramInt4 + (paramInt2 - paramInt3 / 2);
  }

  private int getTop(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt2 + paramInt4;
    int j = 0x111111 & paramInt1;
    if (j == 0)
      i = 0;
    do
    {
      return i;
      if ((j & 0x1000) > 0)
        return paramInt2 + paramInt4;
      if ((0x10000 & j) > 0)
        return paramInt4 + (paramInt2 - paramInt3);
    }
    while ((j & 0x100000) <= 0);
    return paramInt4 + (paramInt2 - paramInt3 / 2);
  }

  public void cancelTip()
  {
    if (this.mView != null)
    {
      this.mView.getView().setVisibility(8);
      this.mWm.removeView(this.mView.getView());
      this.mView.close(false);
      this.mView = null;
    }
    this.mShowing = false;
  }

  public void init(Context paramContext)
  {
    this.mContext = paramContext;
    this.mWm = ((WindowManager)paramContext.getSystemService("window"));
    this.mLp = new WindowManager.LayoutParams();
    this.mLp.flags = 24;
    this.mLp.format = -3;
    this.mLp.windowAnimations = 0;
  }

  public boolean isShown()
  {
    return this.mShowing;
  }

  public void measure(ViewLayout paramViewLayout)
  {
    this.standardLayout.scaleToBounds(paramViewLayout);
  }

  public boolean needShowTip(EducationType paramEducationType)
  {
    switch (1.$SwitchMap$fm$qingting$qtradio$view$education$balloon$EducationType[paramEducationType.ordinal()])
    {
    default:
      return false;
    case 1:
      return SharedCfg.getInstance().getNeedEducationCollapse();
    case 2:
    }
    return SharedCfg.getInstance().getNeedEducationSort();
  }

  public void setEducationShowed(EducationType paramEducationType)
  {
    switch (1.$SwitchMap$fm$qingting$qtradio$view$education$balloon$EducationType[paramEducationType.ordinal()])
    {
    default:
      return;
    case 1:
      SharedCfg.getInstance().setEducationCollspaseShowed();
      return;
    case 2:
    }
    SharedCfg.getInstance().setEducationSortShowed();
  }

  public void showTip(EducationType paramEducationType, int paramInt, Point paramPoint)
  {
    if (this.mShowing)
      cancelTip();
    this.mView = paramEducationType.getView(this.mContext);
    if (this.mView == null)
      return;
    ViewLayout localViewLayout = paramEducationType.getViewLayout();
    localViewLayout.scaleToBounds(this.standardLayout);
    this.mLp.gravity = 51;
    this.mLp.x = getLeft(paramInt, paramPoint.x, localViewLayout.width, -localViewLayout.leftMargin);
    this.mLp.y = getTop(paramInt, paramPoint.y, localViewLayout.height, -localViewLayout.topMargin);
    this.mLp.width = localViewLayout.width;
    this.mLp.height = localViewLayout.height;
    this.mWm.addView(this.mView.getView(), this.mLp);
    this.mView.update("setData", paramPoint);
    this.mShowing = true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.manager.EducationManager
 * JD-Core Version:    0.6.2
 */