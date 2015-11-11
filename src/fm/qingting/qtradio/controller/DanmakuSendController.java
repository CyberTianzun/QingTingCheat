package fm.qingting.qtradio.controller;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.qtradio.view.playview_bb.DanmakuSendView;

public class DanmakuSendController extends ViewController
{
  public static String NAME = "danmakusend";
  private DanmakuSendView v;

  public DanmakuSendController(Context paramContext)
  {
    super(paramContext);
    this.v = new DanmakuSendView(paramContext);
    attachView(this.v);
    this.controllerName = NAME;
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      this.v.update(paramString, paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.DanmakuSendController
 * JD-Core Version:    0.6.2
 */