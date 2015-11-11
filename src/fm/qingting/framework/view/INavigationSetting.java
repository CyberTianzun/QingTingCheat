package fm.qingting.framework.view;

import android.content.Context;
import android.widget.FrameLayout;
import fm.qingting.framework.controller.ViewController;

public abstract interface INavigationSetting
{
  public abstract IViewModel getLayoutView(Context paramContext, Mode paramMode);

  public abstract void navigationWillPopped(ViewController paramViewController, FrameLayout paramFrameLayout, IView paramIView);

  public abstract void navigationWillPushed(ViewController paramViewController, FrameLayout paramFrameLayout, IView paramIView);

  public static enum Mode
  {
    static
    {
      Mode[] arrayOfMode = new Mode[2];
      arrayOfMode[0] = NORMAL;
      arrayOfMode[1] = OVERLAY;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.INavigationSetting
 * JD-Core Version:    0.6.2
 */