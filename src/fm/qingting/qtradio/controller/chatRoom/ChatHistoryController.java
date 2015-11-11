package fm.qingting.qtradio.controller.chatRoom;

import android.content.Context;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.view.chatroom.chatlist.ChatroomListView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;

public class ChatHistoryController extends ViewController
  implements INavigationBarListener
{
  private NavigationBarTopView mBarTopView;
  private ChatroomListView mListView = new ChatroomListView(getContext(), false);

  public ChatHistoryController(Context paramContext)
  {
    super(paramContext);
    attachView(this.mListView);
    this.mBarTopView = new NavigationBarTopView(paramContext);
    this.mBarTopView.setBarListener(this);
    this.mBarTopView.setLeftItem(0);
    setNavigationBar(this.mBarTopView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      this.mListView.update(paramString, paramObject);
    while ((!paramString.equalsIgnoreCase("setTitle")) || (paramObject == null))
      return;
    this.mBarTopView.setTitleItem(new NavigationBarItem((String)paramObject));
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.chatRoom.ChatHistoryController
 * JD-Core Version:    0.6.2
 */