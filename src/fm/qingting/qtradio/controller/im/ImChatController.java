package fm.qingting.qtradio.controller.im;

import android.content.Context;
import android.text.TextUtils;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.IMAgent.IMEventListener;
import fm.qingting.qtradio.im.IMContacts;
import fm.qingting.qtradio.im.LatestMessages;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.view.chatroom.FlowerInfo;
import fm.qingting.qtradio.view.im.chat.ImChatMainView;
import fm.qingting.utils.ExpressionUtil;
import java.util.List;

public class ImChatController extends ViewController
  implements IMAgent.IMEventListener
{
  private String mAddGroupId = null;
  private Object mObject;
  private String mTalkingId;
  private int mTalkingType;
  private ImChatMainView mainView;

  public ImChatController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "imchat";
    ExpressionUtil.getInstance().init(paramContext, hashCode());
    this.mainView = new ImChatMainView(paramContext);
    attachView(this.mainView);
    IMAgent.getInstance().registerIMEventListener(this, "RECV_LIST_MSG");
    IMAgent.getInstance().registerIMEventListener(this, "RECV_SINGLE_MSG");
  }

  private void loadGroupHistoryMessages(String paramString, int paramInt)
  {
    List localList = IMAgent.getInstance().loadMoreGroupMsgFromDB(paramString, paramInt);
    if ((localList != null) && (localList.size() > 0))
    {
      this.mainView.update("addhistory", localList);
      return;
    }
    this.mainView.update("addhistory", null);
  }

  private void loadGroupHistoryMessagesFromNet(String paramString)
  {
    List localList = IMAgent.getInstance().loadMoreGroupMsgFromNet(paramString, null);
    if ((localList != null) && (localList.size() > 0))
      this.mainView.update("addhistory", localList);
  }

  private void loadUserHistoryMessages(String paramString, int paramInt)
  {
    List localList = IMAgent.getInstance().loadMoreUserMsgFromDB(paramString, paramInt);
    if ((localList != null) && (localList.size() > 0))
    {
      this.mainView.update("addhistory", localList);
      return;
    }
    this.mainView.update("addhistory", null);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mObject = paramObject;
      if ((paramObject instanceof GroupInfo))
      {
        localGroupInfo = (GroupInfo)paramObject;
        this.mTalkingId = localGroupInfo.groupId;
        this.mTalkingType = 1;
        FlowerInfo.checkFlowerCnt(this.mTalkingId);
        this.mainView.update("setData", localGroupInfo);
        this.mAddGroupId = localGroupInfo.groupId;
        IMAgent.getInstance().addGroup(localGroupInfo.groupId);
        if (!IMContacts.getInstance().hasRecentGroupContacts(this.mTalkingId))
          loadGroupHistoryMessagesFromNet(this.mTalkingId);
      }
    }
    while (!paramString.equalsIgnoreCase("atTa"))
    {
      do
      {
        GroupInfo localGroupInfo;
        return;
        loadGroupHistoryMessages(this.mTalkingId, -1);
        return;
        if ((paramObject instanceof UserInfo))
        {
          this.mAddGroupId = null;
          UserInfo localUserInfo = (UserInfo)paramObject;
          this.mTalkingId = localUserInfo.userKey;
          this.mTalkingType = 0;
          this.mainView.update("setData", localUserInfo);
          loadUserHistoryMessages(this.mTalkingId, -1);
          return;
        }
        if ((paramObject instanceof IMMessage))
        {
          IMMessage localIMMessage = (IMMessage)paramObject;
          this.mAddGroupId = null;
          this.mTalkingId = localIMMessage.mFromID;
          this.mTalkingType = 0;
          this.mainView.update("setData", localIMMessage);
          loadUserHistoryMessages(this.mTalkingId, -1);
          return;
        }
      }
      while (!(paramObject instanceof String));
      String str = (String)paramObject;
      this.mTalkingId = str;
      this.mTalkingType = 1;
      FlowerInfo.checkFlowerCnt(this.mTalkingId);
      this.mainView.update("setData", str);
      this.mAddGroupId = str;
      IMAgent.getInstance().addGroup(str);
      if (!IMContacts.getInstance().hasRecentGroupContacts(this.mTalkingId))
      {
        loadGroupHistoryMessagesFromNet(this.mTalkingId);
        return;
      }
      loadGroupHistoryMessages(this.mTalkingId, -1);
      return;
    }
    this.mainView.update(paramString, paramObject);
  }

  public void controllerDidPopped()
  {
    IMAgent.getInstance().unRegisterIMEventListener("RECV_LIST_MSG", this);
    IMAgent.getInstance().unRegisterIMEventListener("RECV_SINGLE_MSG", this);
    IMAgent.getInstance().clearUnreadCnt(this.mTalkingId);
    LatestMessages.loadUnreadMsgs(true);
    ViewController localViewController = ControllerManager.getInstance().getLastViewController();
    if ((localViewController != null) && (localViewController.controllerName.equalsIgnoreCase("conversations")))
      localViewController.config("sortListIfNeed", null);
    if ((this.mAddGroupId != null) && (!this.mAddGroupId.equalsIgnoreCase("")))
      IMAgent.getInstance().leaveGroup(this.mAddGroupId);
    this.mainView.close(false);
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.controllerDidPopped();
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("getTalkingType"))
      return Integer.valueOf(this.mTalkingType);
    if (paramString.equalsIgnoreCase("getTalkingId"))
      return this.mTalkingId;
    return null;
  }

  public boolean onIMEvent(String paramString, IMMessage paramIMMessage)
  {
    if (paramString.equalsIgnoreCase("RECV_SINGLE_MSG"))
    {
      if (this.mTalkingType != 1)
        break label50;
      if ((paramIMMessage.isGroupMsg()) && (TextUtils.equals(paramIMMessage.mFromGroupId, this.mTalkingId)))
        this.mainView.update("addmessage", paramIMMessage);
    }
    while (true)
    {
      return false;
      label50: if ((!paramIMMessage.isGroupMsg()) && (TextUtils.equals(paramIMMessage.mFromID, this.mTalkingId)))
        this.mainView.update("addmessage", paramIMMessage);
    }
  }

  public boolean onIMListMsg(String paramString, List<IMMessage> paramList)
  {
    this.mainView.update("addhistory", paramList);
    return false;
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    long l;
    if (paramString.equalsIgnoreCase("loadMore"))
    {
      l = ((Long)paramObject2).longValue();
      if (this.mTalkingType == 1)
        loadGroupHistoryMessages(this.mTalkingId, (int)l);
    }
    label37: 
    do
    {
      do
      {
        do
        {
          do
          {
            break label37;
            break label37;
            do
              return;
            while (this.mTalkingType != 0);
            loadUserHistoryMessages(this.mTalkingId, (int)l);
            return;
            if (!paramString.equalsIgnoreCase("clickright"))
              break;
          }
          while (this.mObject == null);
          this.mainView.update("closeKeyboard", null);
          if (this.mTalkingType != 1)
            break;
          if ((this.mObject instanceof GroupInfo))
          {
            ControllerManager.getInstance().openImGroupProfileController(((GroupInfo)this.mObject).groupId);
            return;
          }
        }
        while (!(this.mObject instanceof String));
        ControllerManager.getInstance().openImGroupProfileController((String)this.mObject);
        return;
      }
      while (!(this.mObject instanceof UserInfo));
      ControllerManager.getInstance().openImUserProfileController(((UserInfo)this.mObject).userKey);
      return;
    }
    while (!paramString.equalsIgnoreCase("clickback"));
    this.mainView.update("closeKeyboard", null);
    ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.im.ImChatController
 * JD-Core Version:    0.6.2
 */