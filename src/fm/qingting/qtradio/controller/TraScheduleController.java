package fm.qingting.qtradio.controller;

import android.content.Context;
import android.graphics.drawable.Drawable;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.qtradio.view.playview.TraScheduleView;

public class TraScheduleController extends ViewController
{
  private TraScheduleView mainView;

  public TraScheduleController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "traschedule";
    this.mainView = new TraScheduleView(paramContext);
    attachView(this.mainView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setBackground"))
      this.mainView.setBackgroundDrawable((Drawable)paramObject);
    do
    {
      return;
      if (paramString.equalsIgnoreCase("setData"))
      {
        this.mainView.update(paramString, paramObject);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("initState"));
    this.mainView.update(paramString, paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.TraScheduleController
 * JD-Core Version:    0.6.2
 */