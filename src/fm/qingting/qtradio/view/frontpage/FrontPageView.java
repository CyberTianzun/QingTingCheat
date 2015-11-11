package fm.qingting.qtradio.view.frontpage;

import android.content.Context;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.View.MeasureSpec;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.EducationManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.view.MiniPlayerView;
import fm.qingting.qtradio.view.moreContentView.UserInfoView;
import fm.qingting.qtradio.view.personalcenter.mydownload.MyDownloadView;
import fm.qingting.utils.QTMSGManage;

public class FrontPageView extends ViewGroupViewImpl
  implements IEventHandler
{
  private DiscoverView mDiscoverView;
  private MyDownloadView mDownloadView;
  private int mLastType = 1;
  private MiniPlayerView mPlayerView;
  private MainTabView mTabView;
  private UserInfoView mUserInfoView;
  private final ViewLayout miniLayout = this.standardLayout.createChildLT(720, 110, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private final ViewLayout tabLayout = this.standardLayout.createChildLT(720, 114, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public FrontPageView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor());
    this.mTabView = new MainTabView(paramContext);
    addView(this.mTabView);
    this.mTabView.setEventHandler(this);
    this.mDiscoverView = new DiscoverView(paramContext);
    addView(this.mDiscoverView);
    this.mPlayerView = new MiniPlayerView(paramContext);
    addView(this.mPlayerView);
  }

  private void changeShowingView(int paramInt)
  {
    if (this.mLastType == paramInt)
      return;
    removeCurrentView(this.mLastType);
    switch (paramInt)
    {
    case 3:
    default:
    case 1:
      while (true)
      {
        this.mLastType = paramInt;
        return;
        this.mDiscoverView.setVisibility(0);
        QTMSGManage.getInstance().sendStatistcsMessage("newnavi_maintabclick", "DISCOVERY");
      }
    case 0:
      if (this.mUserInfoView == null)
      {
        this.mUserInfoView = new UserInfoView(getContext());
        addView(this.mUserInfoView);
        this.mUserInfoView.update("setData", null);
      }
      while (true)
      {
        QTMSGManage.getInstance().sendStatistcsMessage("newnavi_maintabclick", "PERSONAL");
        if (!EducationManager.getInstance().isShown())
          break;
        EducationManager.getInstance().cancelTip();
        break;
        this.mUserInfoView.setVisibility(0);
      }
    case 2:
    }
    if (this.mDownloadView == null)
    {
      this.mDownloadView = new MyDownloadView(getContext());
      addView(this.mDownloadView);
      this.mDownloadView.update("setData", null);
    }
    while (true)
    {
      QTMSGManage.getInstance().sendStatistcsMessage("newnavi_maintabclick", "DOWNLOAD");
      if (!EducationManager.getInstance().isShown())
        break;
      EducationManager.getInstance().cancelTip();
      break;
      this.mDownloadView.update("setData", null);
      this.mDownloadView.setVisibility(0);
    }
  }

  private void removeCurrentView(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 1:
      this.mDiscoverView.setVisibility(8);
      return;
    case 0:
      this.mUserInfoView.setVisibility(8);
      return;
    case 2:
    }
    this.mDownloadView.setVisibility(8);
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (this.mLastType == 1)
      return this.mDiscoverView.dispatchKeyEvent(paramKeyEvent);
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    int i = 1;
    if (paramString.equalsIgnoreCase("currentIndex"))
    {
      if (this.mLastType == i)
        return this.mDiscoverView.getValue(paramString, paramObject);
    }
    else if (paramString.equalsIgnoreCase("divide"))
    {
      if (this.mLastType == i)
      {
        int k = this.tabLayout.height;
        return new Point(k, k + ((Integer)this.mDiscoverView.getValue("tabHeight", null)).intValue());
      }
    }
    else if (paramString.equalsIgnoreCase("isPersonal"))
    {
      if (this.mLastType == 0);
      while (true)
      {
        return Boolean.valueOf(i);
        int j = 0;
      }
    }
    return super.getValue(paramString, paramObject);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("selectTab"))
      changeShowingView(((Integer)paramObject2).intValue());
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.tabLayout.layoutView(this.mTabView);
    this.mDiscoverView.layout(0, this.tabLayout.height, this.standardLayout.width, this.standardLayout.height - this.miniLayout.height);
    if (this.mUserInfoView != null)
      this.mUserInfoView.layout(0, this.tabLayout.height, this.standardLayout.width, this.standardLayout.height - this.miniLayout.height);
    if (this.mDownloadView != null)
      this.mDownloadView.layout(0, this.tabLayout.height, this.standardLayout.width, this.standardLayout.height - this.miniLayout.height);
    this.mPlayerView.layout(0, this.standardLayout.height - this.miniLayout.height, this.standardLayout.width, this.standardLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.tabLayout.scaleToBounds(this.standardLayout);
    this.tabLayout.measureView(this.mTabView);
    this.miniLayout.scaleToBounds(this.standardLayout);
    this.miniLayout.measureView(this.mPlayerView);
    int i = this.standardLayout.getWidthMeasureSpec();
    int j = View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.tabLayout.height - this.miniLayout.height, 1073741824);
    this.mDiscoverView.measure(i, j);
    if (this.mUserInfoView != null)
      this.mUserInfoView.measure(i, j);
    if (this.mDownloadView != null)
      this.mDownloadView.measure(i, j);
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      this.mDiscoverView.update(paramString, paramObject);
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              return;
              if (!paramString.equalsIgnoreCase("loginSuccess"))
                break;
            }
            while (this.mDownloadView == null);
            this.mDownloadView.update(paramString, paramObject);
            return;
          }
          while (paramString.equalsIgnoreCase("showSettingView"));
          if (!paramString.equalsIgnoreCase("updateWoState"))
            break;
        }
        while (this.mDownloadView == null);
        this.mDownloadView.update("setData", null);
        return;
        if (!paramString.equalsIgnoreCase("refreshView"))
          break;
      }
      while ((this.mLastType != 0) || (this.mUserInfoView == null));
      this.mUserInfoView.update(paramString, paramObject);
      return;
    }
    while ((!paramString.equalsIgnoreCase("resortCategoryList")) || (this.mDiscoverView == null));
    this.mDiscoverView.update(paramString, paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.FrontPageView
 * JD-Core Version:    0.6.2
 */