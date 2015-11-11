package fm.qingting.qtradio.controller.personalcenter;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.framework.view.IView;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.view.navigation.NavigationBarCustomView;
import fm.qingting.qtradio.view.personalcenter.feedback.FeedbackView;

public class ContactInfoController extends ViewController
  implements INavigationBarListener
{
  public ContactInfoController(Context paramContext)
  {
    super(paramContext);
    attachView(new FeedbackView(paramContext));
    NavigationBarCustomView localNavigationBarCustomView = new NavigationBarCustomView(paramContext);
    localNavigationBarCustomView.addLeftItem(0);
    localNavigationBarCustomView.setTitleItem(new NavigationBarItem("联系信息"));
    localNavigationBarCustomView.addRightItem(2);
    localNavigationBarCustomView.setBarListener(this);
    setNavigationBar(localNavigationBarCustomView);
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
    {
      this.view.update("closeKeyboard", null);
      ControllerManager.getInstance().popLastController();
    }
    while (paramInt != 3)
      return;
    this.view.update("saveInfo", null);
    this.view.update("closeKeyboard", null);
    ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.personalcenter.ContactInfoController
 * JD-Core Version:    0.6.2
 */