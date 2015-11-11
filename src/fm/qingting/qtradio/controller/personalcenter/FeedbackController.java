package fm.qingting.qtradio.controller.personalcenter;

import android.content.Context;
import android.widget.Toast;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Conversation.SyncListener;
import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;
import com.umeng.fb.model.UserInfo;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.framework.utils.MobileState;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.QTLocation;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.personalcenter.feedback.FeedbackView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackController extends ViewController
  implements INavigationBarListener
{
  private FeedbackAgent agent;
  private Conversation defaultConversation;
  private Conversation.SyncListener listener;
  private FeedbackView mView;

  public FeedbackController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "feedback";
    this.mView = new FeedbackView(paramContext);
    attachView(this.mView);
    NavigationBarTopView localNavigationBarTopView = new NavigationBarTopView(paramContext);
    localNavigationBarTopView.setLeftItem(0);
    localNavigationBarTopView.setTitleItem(new NavigationBarItem("问题描述"));
    localNavigationBarTopView.setBarListener(this);
    setNavigationBar(localNavigationBarTopView);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.agent = new FeedbackAgent(getContext());
      this.defaultConversation = this.agent.getDefaultConversation();
      List localList = this.defaultConversation.getReplyList();
      this.mView.update("setData", localList);
      this.listener = new Conversation.SyncListener()
      {
        public void onReceiveDevReply(List<DevReply> paramAnonymousList)
        {
        }

        public void onSendUserReply(List<Reply> paramAnonymousList)
        {
          FeedbackController.this.mView.update("setData", FeedbackController.this.defaultConversation.getReplyList());
        }
      };
      this.defaultConversation.sync(this.listener);
    }
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
    {
      this.mView.closeIm();
      ControllerManager.getInstance().popLastController();
    }
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    String str1;
    GroupInfo localGroupInfo;
    String str3;
    if (paramString.equalsIgnoreCase("sendDiscuss"))
    {
      str1 = "【" + SharedCfg.getInstance().getFeedbackCategory() + "】" + (String)paramObject2;
      localGroupInfo = new GroupInfo();
      localGroupInfo.groupId = "24124";
      localGroupInfo.groupName = "Android技术群";
      String str2 = SharedCfg.getInstance().getFeedbackContactInfo();
      if ((str2 == null) || (str2.length() <= 0))
        break label419;
      str3 = "安卓反馈:" + str1 + " 联系信息：" + str2;
    }
    while (true)
    {
      IMAgent.getInstance().sendFeedbackMessage(str3, localGroupInfo);
      Toast.makeText(getContext(), "反馈信息发送成功，感谢您对蜻蜓的支持！", 0).show();
      this.defaultConversation.addUserReply(str1);
      try
      {
        HashMap localHashMap = new HashMap();
        label220: String str5;
        String str4;
        switch (MobileState.getNetWorkType(getContext()))
        {
        case 4:
        default:
          localHashMap.put("NetState", "offline");
          Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
          if (localNode != null)
          {
            if ((!localNode.nodeName.equalsIgnoreCase("program")) || (localNode.parent == null) || (!localNode.parent.nodeName.equalsIgnoreCase("channel")))
              break label508;
            str5 = ((ChannelNode)localNode.parent).title;
            str4 = ((ProgramNode)localNode).title;
          }
          break;
        case 1:
        case 2:
        case 3:
        case 5:
        }
        while (true)
        {
          if (str5 != null)
            localHashMap.put("Channel", str5);
          if (str4 != null)
            localHashMap.put("Program", str4);
          QTLocation localQTLocation = InfoManager.getInstance().getCurrentLocation();
          if ((localQTLocation != null) && (localQTLocation.city != null))
            localHashMap.put("City", localQTLocation.city);
          UserInfo localUserInfo = this.agent.getUserInfo();
          if (localUserInfo == null)
            localUserInfo = new UserInfo();
          localUserInfo.setRemark(localHashMap);
          this.agent.setUserInfo(localUserInfo);
          this.defaultConversation.sync(this.listener);
          return;
          label419: str3 = "安卓反馈:" + str1;
          break;
          localHashMap.put("NetState", "Wifi");
          break label220;
          localHashMap.put("NetState", "3G");
          break label220;
          localHashMap.put("NetState", "2G");
          break label220;
          localHashMap.put("NetState", "unknown");
          break label220;
          label508: str4 = null;
          str5 = null;
        }
      }
      catch (Exception localException)
      {
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.personalcenter.FeedbackController
 * JD-Core Version:    0.6.2
 */