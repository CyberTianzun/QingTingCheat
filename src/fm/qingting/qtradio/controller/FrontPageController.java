package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.view.frontpage.FrontPageView;

public class FrontPageController extends ViewController
  implements InfoManager.ISubscribeEventListener
{
  public static final String NAME = "frontpage";
  private FrontPageView mainView;

  public FrontPageController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "frontpage";
    this.mainView = new FrontPageView(paramContext);
    attachView(this.mainView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      this.mainView.update("setData", null);
    do
    {
      return;
      if (paramString.equalsIgnoreCase("loginSuccess"))
      {
        this.mainView.update(paramString, paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("showSettingView"))
      {
        this.mainView.update(paramString, paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("updateWoState"))
      {
        this.mainView.update(paramString, paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("refreshView"))
      {
        this.mainView.update(paramString, paramObject);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("resortCategoryList"));
    this.mainView.update(paramString, paramObject);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("currentIndex"))
      return this.mainView.getValue(paramString, paramObject);
    if (paramString.equalsIgnoreCase("divide"))
      return this.mainView.getValue(paramString, paramObject);
    return super.getValue(paramString, paramObject);
  }

  public boolean hasMiniPlayer()
  {
    return true;
  }

  public void onNotification(String paramString)
  {
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.FrontPageController
 * JD-Core Version:    0.6.2
 */