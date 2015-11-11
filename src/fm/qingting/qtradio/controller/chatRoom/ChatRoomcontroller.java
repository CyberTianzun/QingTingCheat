package fm.qingting.qtradio.controller.chatRoom;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.model.BroadcasterNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.ProgramTopicInfoNode;
import fm.qingting.qtradio.model.QTLocation;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IPlayInfoEventListener;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.room.AskSongAction;
import fm.qingting.qtradio.room.AskSongTogetherAction;
import fm.qingting.qtradio.room.ChatData;
import fm.qingting.qtradio.room.CheckInAction;
import fm.qingting.qtradio.room.CustomData;
import fm.qingting.qtradio.room.FlowerAction;
import fm.qingting.qtradio.room.GetHistoryAction;
import fm.qingting.qtradio.room.GetTopicAction;
import fm.qingting.qtradio.room.JoinAction;
import fm.qingting.qtradio.room.LeaveAction;
import fm.qingting.qtradio.room.LoginAction;
import fm.qingting.qtradio.room.Room;
import fm.qingting.qtradio.room.RoomDataCenter;
import fm.qingting.qtradio.room.RoomDataCenter.IRoomDataEventListener;
import fm.qingting.qtradio.room.RoomDataCenter.IRoomStateListener;
import fm.qingting.qtradio.room.RoomManager;
import fm.qingting.qtradio.room.SendAction;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.SpeakToAction;
import fm.qingting.qtradio.room.TellSongAction;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.room.WeiboData;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.social.CloudCenter.OnLoginEventListerner;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.qtradio.tencentAgent.TencentAgent;
import fm.qingting.qtradio.view.chatroom.ActionsFloatView.ChatAction;
import fm.qingting.qtradio.view.chatroom.ChatActionsView.ChatActionType;
import fm.qingting.qtradio.view.chatroom.ChatRoomMainView;
import fm.qingting.qtradio.view.chatroom.FlowerInfo;
import fm.qingting.qtradio.view.chatroom.chatlist.ChatItem;
import fm.qingting.qtradio.weiboAgent.WeiboAgent;
import fm.qingting.qtradio.weiboAgent.WeiboAgent.WeiboDataType;
import fm.qingting.utils.ExpressionUtil;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.ViewCaptureUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChatRoomcontroller extends ViewController
  implements RoomDataCenter.IRoomDataEventListener, RoomDataCenter.IRoomStateListener, IEventHandler, RootNode.IPlayInfoEventListener, InfoManager.ISubscribeEventListener
{
  private String LiveRoomName;
  private final long TIME_INTERVAL = 300000L;
  private final Handler delayHandler = new Handler();
  private Runnable delayRunnable = new Runnable()
  {
    public void run()
    {
      ChatRoomcontroller.RemindMessage localRemindMessage = ChatRoomcontroller.this.mMsgPool.pickMessage();
      if (localRemindMessage != null)
      {
        ChatRoomcontroller.this.showMessage(localRemindMessage);
        return;
      }
      ChatRoomcontroller.this.hideMessage();
    }
  };
  private boolean hasDoneSpeak = true;
  private Calendar mCalendar;
  private ActionsFloatView.ChatAction mCurrentAction = ActionsFloatView.ChatAction.None;
  private int mDay;
  private HashSet<String> mDjIds;
  private boolean mFirstFilter = true;
  private long mLastAtMeTime;
  private int mLastIndexAtMe = -1;
  private MessagePool mMsgPool = new MessagePool(null);
  private ProgramNode mNode;
  private String mRoomId = "";
  private ChatRoomMainView mainView;
  private UserInfo reportObject;
  private Object speakParam;

  public ChatRoomcontroller(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "chatroom";
    ExpressionUtil.getInstance().init(paramContext, hashCode());
    this.mainView = new ChatRoomMainView(paramContext);
    attachView(this.mainView);
    RoomDataCenter.getInstance().registerRoomDataEventListener(this, "RLRCD");
    RoomDataCenter.getInstance().registerRoomStateEventListener(this, "RLRJ");
    RoomDataCenter.getInstance().registerRoomDataEventListener(this, "RLRUE");
    RoomDataCenter.getInstance().registerRoomDataEventListener(this, "RLROU");
    RoomDataCenter.getInstance().registerRoomDataEventListener(this, "RLRT");
    RoomDataCenter.getInstance().registerRoomDataEventListener(this, "RLRAS");
    InfoManager.getInstance().root().registerSubscribeEventListener(this, 1);
    QTMSGManage.getInstance().sendStatistcsMessage("chatroom_enter");
  }

  private void addCollection()
  {
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    if (localChannelNode == null);
    do
    {
      Node localNode;
      do
      {
        return;
        if (!localChannelNode.nodeName.equalsIgnoreCase("program"))
          break;
        localNode = localChannelNode.parent;
      }
      while ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("channel")));
      if (InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(localNode))
      {
        InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.deleteFavNode(localNode);
        QTMSGManage.getInstance().sendStatistcsMessage("chatroom_clickcollection", "del");
        return;
      }
      InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.addFavNode(localNode);
      QTMSGManage.getInstance().sendStatistcsMessage("chatroom_clickcollection", "add");
      return;
    }
    while (!localChannelNode.nodeName.equalsIgnoreCase("channel"));
    if (InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(localChannelNode))
    {
      InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.deleteFavNode(localChannelNode);
      QTMSGManage.getInstance().sendStatistcsMessage("chatroom_clickcollection", "del");
      return;
    }
    InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.addFavNode(localChannelNode);
    QTMSGManage.getInstance().sendStatistcsMessage("chatroom_clickcollection", "add");
  }

  private void answerSongName()
  {
    this.reportObject = null;
    if (CloudCenter.getInstance().isLogin())
    {
      this.mainView.update("reportSongName", null);
      return;
    }
    CloudCenter.OnLoginEventListerner local4 = new CloudCenter.OnLoginEventListerner()
    {
      public void onLoginFailed(int paramAnonymousInt)
      {
      }

      public void onLoginSuccessed(int paramAnonymousInt)
      {
        ChatRoomcontroller.this.mainView.update("reportSongName", null);
      }
    };
    EventDispacthManager.getInstance().dispatchAction("showLogin", local4);
  }

  private List<ChatItem> filterDataByUserInfo(List<CustomData> paramList, UserInfo paramUserInfo)
  {
    if (paramList != null);
    while (true)
    {
      Object localObject1;
      long l1;
      Object localObject3;
      String str1;
      long l2;
      int k;
      int n;
      Object localObject4;
      long l4;
      label204: long l3;
      int m;
      int i2;
      int i3;
      int i5;
      int i7;
      Object localObject7;
      long l7;
      Object localObject6;
      long l6;
      UserInfo localUserInfo2;
      UserInfo localUserInfo3;
      int i4;
      long l5;
      try
      {
        int i = paramList.size();
        if (i == 0)
        {
          localObject1 = null;
          return localObject1;
        }
        resetBaseTime();
        if (paramUserInfo != null)
          break label1360;
        this.mLastIndexAtMe = -1;
        l1 = 0L;
        localObject3 = null;
        if (!this.mFirstFilter)
          break label1326;
        ArrayList localArrayList;
        CustomData localCustomData1;
        String str2;
        if ((InfoManager.getInstance().getUserProfile() != null) && (InfoManager.getInstance().getUserProfile().getUserInfo() != null))
        {
          str1 = InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_name;
          localArrayList = new ArrayList();
          int j = 0;
          l2 = 0L;
          if (j >= paramList.size())
            continue;
          k = 17;
          localCustomData1 = (CustomData)paramList.get(j);
          if (localCustomData1.type != 1)
            continue;
          UserInfo localUserInfo1 = ((ChatData)localCustomData1).user;
          str2 = localUserInfo1.snsInfo.sns_id;
          if (!localUserInfo1.userId.equalsIgnoreCase(InfoManager.getInstance().getDeviceId()))
            continue;
          if (((ChatData)localCustomData1).conentType == 2)
          {
            n = 2;
            localObject4 = localObject3;
            l4 = l1;
            break label1311;
            if (localCustomData1.createTime - l2 < 300000L)
              break label1304;
            l3 = localCustomData1.createTime;
            localArrayList.add(new ChatItem(m, localCustomData1));
            j++;
            l2 = l3;
            continue;
          }
        }
        else
        {
          this.mFirstFilter = false;
          break label1326;
        }
        if (((ChatData)localCustomData1).conentType == 1)
        {
          n = 3;
          localObject4 = localObject3;
          l4 = l1;
          break label1311;
        }
        if (((ChatData)localCustomData1).conentType != 0)
          break label1289;
        if ((this.mDjIds == null) || (!this.mDjIds.contains(str2)))
          break label1332;
        n = 4;
        localObject4 = localObject3;
        l4 = l1;
        break label1311;
        if (((ChatData)localCustomData1).conentType == 2)
        {
          n = 18;
          localObject4 = localObject3;
          l4 = l1;
          break label1311;
        }
        if (((ChatData)localCustomData1).conentType == 1)
        {
          n = 19;
          localObject4 = localObject3;
          l4 = l1;
          break label1311;
        }
        if (((ChatData)localCustomData1).conentType != 0)
          break label1289;
        if ((this.mDjIds == null) || (!this.mDjIds.contains(str2)))
          break label1346;
        k = 20;
        if ((str1 == null) || (((ChatData)localCustomData1).content == null) || (!((ChatData)localCustomData1).content.contains("@" + str1)))
          break label1289;
        if (localArrayList != null)
          this.mLastIndexAtMe = (1 + localArrayList.size());
        l4 = ((ChatData)localCustomData1).createTime;
        String str3 = ((ChatData)localCustomData1).user.snsInfo.sns_name;
        int i1 = k;
        localObject4 = str3;
        n = i1;
        break label1311;
        if (localCustomData1.type != 2)
          break label1282;
        if (!((WeiboData)localCustomData1).user.userId.equalsIgnoreCase(InfoManager.getInstance().getDeviceId()))
          break label1353;
        m = 1;
        continue;
        if ((this.mLastIndexAtMe >= 0) && (l1 > this.mLastAtMeTime) && (localObject3 != null))
        {
          this.mLastAtMeTime = l1;
          SharedCfg.getInstance().updateAtMeLatestTime(this.mRoomId, this.mLastAtMeTime);
          this.mMsgPool.addMessage(new RemindMessage(ActionsFloatView.ChatAction.Remind, localObject3));
        }
        this.mFirstFilter = false;
        localObject1 = localArrayList;
        continue;
        if (i2 >= paramList.size())
          break label1232;
        CustomData localCustomData2 = (CustomData)paramList.get(i2);
        i3 = 17;
        Object localObject5;
        if (localCustomData2.type == 1)
        {
          UserInfo localUserInfo4 = ((ChatData)localCustomData2).user;
          UserInfo localUserInfo5 = ((ChatData)localCustomData2).toUser;
          if (localUserInfo4.userId.equalsIgnoreCase(InfoManager.getInstance().getDeviceId()))
          {
            int i6 = ((ChatData)localCustomData2).conentType;
            i5 = 0;
            if (i6 == 0)
            {
              if (localUserInfo5 == null)
              {
                if ((((ChatData)localCustomData2).content == null) || (!((ChatData)localCustomData2).content.contains("@" + paramUserInfo.snsInfo.sns_name)))
                  break label1269;
                i7 = 1;
                break label1371;
              }
            }
            else
            {
              if (i5 == 0)
                break label1259;
              if (localObject1 != null)
                break label1253;
              localObject7 = new ArrayList();
              if (localCustomData2.createTime - localObject5 < 300000L)
                break label1275;
              ((List)localObject7).add(new ChatItem(32, getTimestamp(localCustomData2.createTime)));
              l7 = localCustomData2.createTime;
              ((List)localObject7).add(new ChatItem(i3, localCustomData2));
              localObject6 = localObject7;
              l6 = l7;
              break label1381;
            }
            if (!localUserInfo5.getUid().equalsIgnoreCase(paramUserInfo.getUid()))
              break label1269;
            i7 = 1;
            break label1371;
          }
          boolean bool = localUserInfo4.getUid().equalsIgnoreCase(paramUserInfo.getUid());
          i5 = 0;
          if (!bool)
            continue;
          i5 = 1;
          if (((ChatData)localCustomData2).conentType == 2)
          {
            i3 = 18;
            continue;
          }
          if (((ChatData)localCustomData2).conentType == 1)
          {
            i3 = 19;
            continue;
          }
          if (((ChatData)localCustomData2).conentType != 0)
            continue;
          i3 = 17;
          continue;
        }
        if (localCustomData2.type != 2)
          break label1388;
        localUserInfo2 = ((WeiboData)localCustomData2).user;
        localUserInfo3 = ((WeiboData)localCustomData2).toUser;
        if (!localUserInfo2.userId.equalsIgnoreCase(InfoManager.getInstance().getDeviceId()))
          break label1211;
        if (localUserInfo3 == null)
        {
          if ((((ChatData)localCustomData2).content == null) || (!((ChatData)localCustomData2).content.contains("@" + paramUserInfo.snsInfo.sns_name)))
            break label1247;
          i4 = 1;
          if (i4 == 0)
            break label1388;
          if (localObject1 == null)
            localObject1 = new ArrayList();
          if (localCustomData2.createTime - localObject5 >= 300000L)
          {
            ((List)localObject1).add(new ChatItem(32, getTimestamp(localCustomData2.createTime)));
            l5 = localCustomData2.createTime;
          }
          ((List)localObject1).add(new ChatItem(2, localCustomData2));
        }
      }
      finally
      {
      }
      if (localUserInfo3.getUid().equalsIgnoreCase(paramUserInfo.getUid()))
      {
        i4 = 1;
        continue;
        label1211: if (localUserInfo2.getUid().equalsIgnoreCase(paramUserInfo.getUid()))
        {
          i4 = 1;
          continue;
          label1232: if (localObject1 != null)
            continue;
          localObject1 = new ArrayList();
        }
      }
      else
      {
        label1247: i4 = 0;
        continue;
        label1253: localObject7 = localObject1;
        continue;
        label1259: localObject6 = localObject1;
        l6 = l5;
        break label1381;
        label1269: i7 = 0;
        break label1371;
        label1275: l7 = l5;
        continue;
        label1282: m = k;
        continue;
        label1289: n = k;
        localObject4 = localObject3;
        l4 = l1;
        break label1311;
        label1304: l3 = l2;
        continue;
        while (true)
        {
          label1311: m = n;
          l1 = l4;
          localObject3 = localObject4;
          break label204;
          label1326: str1 = null;
          break;
          label1332: n = 1;
          localObject4 = localObject3;
          l4 = l1;
        }
        label1346: k = 17;
        continue;
        label1353: m = 17;
        continue;
        label1360: l5 = 0L;
        localObject1 = null;
        i2 = 0;
        continue;
        label1371: i3 = 1;
        i5 = i7;
        continue;
        label1381: l5 = l6;
        localObject1 = localObject6;
        label1388: i2++;
      }
    }
  }

  private void getBroadcastersId(ProgramNode paramProgramNode)
  {
    if (this.mDjIds != null)
    {
      this.mDjIds.clear();
      this.mDjIds = null;
    }
    List localList = paramProgramNode.lstBroadcaster;
    if ((localList == null) || (localList.size() == 0));
    while (true)
    {
      return;
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        BroadcasterNode localBroadcasterNode = (BroadcasterNode)localIterator.next();
        if ((localBroadcasterNode.weiboId != null) && (!localBroadcasterNode.weiboId.equalsIgnoreCase("")))
        {
          if (this.mDjIds == null)
            this.mDjIds = new HashSet();
          this.mDjIds.add(localBroadcasterNode.weiboId);
        }
      }
    }
  }

  private String getTimeInDay(int paramInt1, int paramInt2)
  {
    String str;
    if (paramInt1 < 6)
      str = "凌晨";
    while (true)
    {
      Locale localLocale = Locale.CHINESE;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = str;
      arrayOfObject[1] = Integer.valueOf(paramInt1);
      arrayOfObject[2] = Integer.valueOf(paramInt2);
      return String.format(localLocale, "%s%02d:%02d", arrayOfObject);
      if (paramInt1 < 12)
        str = "早上";
      else if (paramInt1 < 13)
        str = "中午";
      else if (paramInt1 < 18)
        str = "下午";
      else
        str = "晚上";
    }
  }

  private String getTimestamp(long paramLong)
  {
    this.mCalendar.setTimeInMillis(paramLong);
    int i = this.mCalendar.get(6);
    int j = this.mCalendar.get(11);
    int k = this.mCalendar.get(12);
    if (i == this.mDay)
      return getTimeInDay(j, k);
    if (i == -1 + this.mDay)
      return "昨天 " + getTimeInDay(j, k);
    int m = this.mCalendar.get(2);
    int n = this.mCalendar.get(5);
    Locale localLocale = Locale.CHINESE;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(m);
    arrayOfObject[1] = Integer.valueOf(n);
    arrayOfObject[2] = getTimeInDay(j, k);
    return String.format(localLocale, "%d月%d日 %s", arrayOfObject);
  }

  private String getTopic()
  {
    GetTopicAction localGetTopicAction = new GetTopicAction();
    localGetTopicAction.setContentInfo(1, this.mRoomId);
    RoomManager.getInstance().getRoomByType(1).doAction(localGetTopicAction);
    return RoomDataCenter.getInstance().getRoomTopic(1, this.mRoomId);
  }

  private void hideMessage()
  {
    this.mCurrentAction = ActionsFloatView.ChatAction.None;
    this.mainView.update("hideMessage", null);
  }

  private boolean isAccountValid()
  {
    return CloudCenter.getInstance().isLogin();
  }

  private void leaveLiveRoom()
  {
    LeaveAction localLeaveAction = new LeaveAction();
    localLeaveAction.setContentInfo(1, 1);
    RoomManager.getInstance().getRoomByType(1).doAction(localLeaveAction);
  }

  private void openMemberController()
  {
    QTMSGManage.getInstance().sendStatistcsMessage("chat_clickOnline");
    this.mainView.update("closeKeyboard", null);
    ControllerManager.getInstance().openOnlineMemberController(this.mRoomId, this);
  }

  private void openUserProfile(final UserInfo paramUserInfo)
  {
    if (!CloudCenter.getInstance().isLogin())
    {
      CloudCenter.OnLoginEventListerner local5 = new CloudCenter.OnLoginEventListerner()
      {
        public void onLoginFailed(int paramAnonymousInt)
        {
        }

        public void onLoginSuccessed(int paramAnonymousInt)
        {
          ControllerManager.getInstance().redirectToUsersProfileView(paramUserInfo);
        }
      };
      EventDispacthManager.getInstance().dispatchAction("showLogin", local5);
      return;
    }
    ControllerManager.getInstance().redirectToUsersProfileView(paramUserInfo);
  }

  private void requestSongName()
  {
    if (CloudCenter.getInstance().isLogin())
    {
      AskSongAction localAskSongAction = new AskSongAction();
      localAskSongAction.setContentInfo(1, this.mRoomId);
      RoomManager.getInstance().getRoomByType(1).doAction(localAskSongAction);
      return;
    }
    CloudCenter.OnLoginEventListerner local6 = new CloudCenter.OnLoginEventListerner()
    {
      public void onLoginFailed(int paramAnonymousInt)
      {
      }

      public void onLoginSuccessed(int paramAnonymousInt)
      {
        AskSongAction localAskSongAction = new AskSongAction();
        localAskSongAction.setContentInfo(1, ChatRoomcontroller.this.mRoomId);
        RoomManager.getInstance().getRoomByType(1).doAction(localAskSongAction);
      }
    };
    EventDispacthManager.getInstance().dispatchAction("showLogin", local6);
  }

  private void resetBaseTime()
  {
    if (this.mCalendar == null)
      this.mCalendar = Calendar.getInstance();
    this.mCalendar.setTimeInMillis(System.currentTimeMillis());
    this.mDay = this.mCalendar.get(6);
  }

  private void setTopic(String paramString)
  {
    if (paramString == null)
      return;
    this.mMsgPool.addMessage(new RemindMessage(ActionsFloatView.ChatAction.Topic, paramString));
  }

  private void showMessage(RemindMessage paramRemindMessage)
  {
    this.mCurrentAction = paramRemindMessage.getAction();
    this.mainView.update(this.mCurrentAction.getMessageEventType(), paramRemindMessage.getData());
    startDelayTimer(this.mCurrentAction.getTimeLength());
  }

  private void startDelayTimer(long paramLong)
  {
    this.delayHandler.removeCallbacks(this.delayRunnable);
    this.delayHandler.postDelayed(this.delayRunnable, paramLong);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("startRoom"))
    {
      this.mNode = ((ProgramNode)paramObject);
      getBroadcastersId(this.mNode);
      this.mRoomId = String.valueOf(InfoManager.getInstance().root().getCurrentPlayingChannelNode().channelId);
      if (this.mNode.channelType == 1)
      {
        this.mNode.getCurrPlayStatus();
        this.mRoomId = String.valueOf(this.mNode.channelId);
      }
      if ((this.mRoomId == null) || (this.mRoomId.equalsIgnoreCase("")))
        this.mainView.update("setHeadInfo", this.mNode);
    }
    ChatData localChatData;
    do
    {
      CustomData localCustomData;
      do
      {
        do
        {
          return;
          FlowerInfo.checkFlowerCnt(this.mRoomId);
          this.mLastAtMeTime = SharedCfg.getInstance().getAtMeLatestTime(this.mRoomId);
          if (RoomDataCenter.getInstance().getRoomDataByType(1, this.mRoomId) == null)
          {
            GetHistoryAction localGetHistoryAction = new GetHistoryAction();
            localGetHistoryAction.setConnectUrl(InfoManager.getInstance().chatServer, this.mRoomId, 1);
            RoomManager.getInstance().getRoomByType(1).doAction(localGetHistoryAction);
          }
          JoinAction localJoinAction2 = new JoinAction();
          localJoinAction2.setConnectUrl(InfoManager.getInstance().chatServer, this.mRoomId, 1, InfoManager.getInstance().root().mProgramTopicInfoNode.getCurrentProgramTopic(String.valueOf(this.mNode.id), this.mRoomId, System.currentTimeMillis() / 1000L));
          List localList2 = RoomDataCenter.getInstance().getRoomDataByType(1, this.mRoomId);
          int i = 0;
          if (localList2 != null)
          {
            localJoinAction2.setRecordCount(0);
            i = 1;
          }
          RoomManager.getInstance().getRoomByType(1).doAction(localJoinAction2);
          if (RoomDataCenter.getInstance().getRoomTopic(1, this.mRoomId) != null)
            this.mMsgPool.addMessage(new RemindMessage(ActionsFloatView.ChatAction.Topic, RoomDataCenter.getInstance().getRoomTopic(1, this.mRoomId)));
          String str3 = getTopic();
          if (str3 != null)
            setTopic(str3);
          if (i != 0)
            this.mainView.update("setData", filterDataByUserInfo(RoomDataCenter.getInstance().getRoomDataByType(1, this.mRoomId), null));
          this.mainView.update("setHeadInfo", this.mNode);
          return;
          if (paramString.equalsIgnoreCase("startLocalRoom"))
          {
            QTLocation localQTLocation = InfoManager.getInstance().getCurrentLocation();
            if (localQTLocation != null)
            {
              str2 = localQTLocation.region;
              if ((str2 != null) && (!str2.equalsIgnoreCase("")));
            }
            for (String str2 = "火星"; ; str2 = "火星")
            {
              Toast.makeText(getContext(), "欢迎来到" + str2 + "同城直播间,", 0).show();
              ProgramNode localProgramNode = (ProgramNode)paramObject;
              this.mRoomId = InfoManager.getInstance().getLocalRoomId();
              JoinAction localJoinAction1 = new JoinAction();
              localJoinAction1.setConnectUrl(InfoManager.getInstance().chatServer, this.mRoomId, 1, null);
              if (RoomDataCenter.getInstance().getRoomDataByType(1, this.mRoomId) != null)
              {
                this.mainView.update("setData", filterDataByUserInfo(RoomDataCenter.getInstance().getRoomDataByType(1, this.mRoomId), null));
                localJoinAction1.setRecordCount(0);
              }
              RoomManager.getInstance().getRoomByType(1).doAction(localJoinAction1);
              this.mainView.update("setLocalHeadInfo", localProgramNode);
              return;
            }
          }
          if (paramString.equalsIgnoreCase("speakTodj"))
          {
            this.hasDoneSpeak = false;
            this.speakParam = paramObject;
            return;
          }
          if (paramString.equalsIgnoreCase("flower"))
          {
            onViewEvent(this, paramString, paramObject);
            return;
          }
          if (paramString.equalsIgnoreCase("sayToIt"))
          {
            this.mainView.update(paramString, paramObject);
            return;
          }
        }
        while ((!paramString.equalsIgnoreCase("showchathistory")) || (paramObject == null) || (!(paramObject instanceof CustomData)));
        localCustomData = (CustomData)paramObject;
      }
      while (localCustomData.type != 1);
      localChatData = (ChatData)localCustomData;
    }
    while (localChatData.user == null);
    String str1 = localChatData.user.snsInfo.sns_name;
    List localList1 = filterDataByUserInfo(RoomDataCenter.getInstance().getRoomDataByType(1, this.mRoomId), localChatData.user);
    ControllerManager.getInstance().openChatHistoryController(str1, localList1);
  }

  public void controllerDidPopped()
  {
    leaveLiveRoom();
    this.mainView.close(false);
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    RoomDataCenter.getInstance().unRegisterRoomDataEventListener("RLRCD", this);
    RoomDataCenter.getInstance().unRegisterRoomStateEventListener("RLRJ", this);
    RoomDataCenter.getInstance().unRegisterRoomDataEventListener("RLRUE", this);
    RoomDataCenter.getInstance().unRegisterRoomDataEventListener("RLRT", this);
    RoomDataCenter.getInstance().unRegisterRoomDataEventListener("RLROU", this);
    RoomDataCenter.getInstance().unRegisterRoomDataEventListener("RLRAS", this);
    InfoManager.getInstance().root().unRegisterSubscribeEventListener(1, this);
    super.controllerDidPopped();
  }

  public void controllerDidPushed()
  {
    if (!this.hasDoneSpeak)
      this.mainView.update("speakTodj", this.speakParam);
    this.hasDoneSpeak = true;
    super.controllerDidPushed();
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("login_Success"))
      ControllerManager.getInstance().popLastController();
    do
    {
      return;
      if (paramString.equalsIgnoreCase("Auth_Cancel_Return"))
      {
        ControllerManager.getInstance().popLastController();
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("talkWithIt"));
    this.mainView.update("sayToIt", paramObject2);
  }

  public void onNotification(String paramString)
  {
    if (paramString == "RCPT");
  }

  public void onPlayInfoUpdated(int paramInt)
  {
    Node localNode;
    if (paramInt == 1)
    {
      localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")));
    }
    else
    {
      return;
    }
    this.mNode = ((ProgramNode)localNode);
    getBroadcastersId(this.mNode);
    this.mainView.update("setHeadInfo", this.mNode);
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void onRoomData(String paramString)
  {
    if (paramString.equalsIgnoreCase("RLRCD"))
      this.mainView.update("setData", filterDataByUserInfo(RoomDataCenter.getInstance().getRoomDataByType(1, this.mRoomId), null));
    do
    {
      String str;
      do
      {
        UserInfo localUserInfo;
        do
        {
          return;
          if (!paramString.equalsIgnoreCase("RLRUE"))
            break;
          localUserInfo = RoomDataCenter.getInstance().getEnterUser(1, null);
        }
        while ((localUserInfo == null) || (localUserInfo.userId.equalsIgnoreCase(InfoManager.getInstance().getDeviceId())));
        this.mMsgPool.addMessage(new RemindMessage(ActionsFloatView.ChatAction.SayHi, localUserInfo));
        return;
        if (paramString.equalsIgnoreCase("RLROU"))
        {
          EventDispacthManager.getInstance().dispatchAction("refreshSearchData", RoomDataCenter.getInstance().getRoomUsersByType(1, this.mRoomId));
          return;
        }
        if (!paramString.equalsIgnoreCase("RLRT"))
          break;
        str = RoomDataCenter.getInstance().getRoomTopic(1, this.mRoomId);
      }
      while (str == null);
      setTopic(str);
      return;
    }
    while ((!paramString.equalsIgnoreCase("RLRAS")) || (RoomDataCenter.getInstance().getAskSongInfo(1, this.mRoomId) == null));
    this.mMsgPool.addMessage(new RemindMessage(ActionsFloatView.ChatAction.RequestSn, RoomDataCenter.getInstance().getAskSongInfo(1, this.mRoomId)));
  }

  public void onRoomState(String paramString)
  {
    if (paramString.equalsIgnoreCase("RLRJ"))
    {
      UserInfo localUserInfo = new UserInfo();
      localUserInfo.userId = InfoManager.getInstance().getDeviceId();
      localUserInfo.snsInfo.sns_id = localUserInfo.userId;
      LoginAction localLoginAction = new LoginAction();
      localLoginAction.setUserInfo(localUserInfo, 1);
      RoomManager.getInstance().getRoomByType(1).doAction(localLoginAction);
    }
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    String str1;
    if (paramString.equalsIgnoreCase("sendDiscuss"))
    {
      str1 = (String)paramObject2;
      if ((str1 != null) && (!str1.trim().equalsIgnoreCase("")));
    }
    do
    {
      String str2;
      do
      {
        return;
        QTMSGManage.getInstance().sendStatistcsMessage("chat_sendDiscuss", "all");
        QTMSGManage.getInstance().sendStatistcsMessage("chatroom_all");
        SendAction localSendAction4 = new SendAction();
        localSendAction4.setContentInfo(str1, 1);
        RoomManager.getInstance().getRoomByType(1).doAction(localSendAction4);
        str2 = this.LiveRoomName;
      }
      while (str2 == null);
      String str3 = str2 + "_sendContent";
      QTMSGManage.getInstance().sendStatistcsMessage("ClickLiveRoom", str3);
      return;
      if (paramString.equalsIgnoreCase("sendReply"))
      {
        QTMSGManage.getInstance().sendStatistcsMessage("chat_sendDiscuss", "single");
        QTMSGManage.getInstance().sendStatistcsMessage("chatroom_single");
        ChatData localChatData3 = (ChatData)paramObject2;
        SendAction localSendAction3 = new SendAction();
        localSendAction3.setContentInfo(1, localChatData3);
        RoomManager.getInstance().getRoomByType(1).doAction(localSendAction3);
        return;
      }
      if (paramString.equalsIgnoreCase("praise"))
      {
        ChatData localChatData2 = (ChatData)paramObject2;
        SendAction localSendAction2 = new SendAction();
        localSendAction2.setContentInfo(1, localChatData2);
        RoomManager.getInstance().getRoomByType(1).doAction(localSendAction2);
        return;
      }
      if (paramString.equalsIgnoreCase("playPoint"))
      {
        long l = ((ChatData)paramObject2).createTime;
        dispatchEvent("queryPosition", Long.valueOf(InfoManager.getInstance().root().replayCurrNodeByTime(this.mRoomId, l)));
        return;
      }
      if (paramString.equalsIgnoreCase("CheckIn"))
      {
        if (!isAccountValid())
        {
          CloudCenter.OnLoginEventListerner local1 = new CloudCenter.OnLoginEventListerner()
          {
            public void onLoginFailed(int paramAnonymousInt)
            {
            }

            public void onLoginSuccessed(int paramAnonymousInt)
            {
              MobclickAgent.onEvent(ChatRoomcontroller.this.getContext(), "ChatRoomCheckIS");
              if ((WeiboAgent.getInstance().isSessionValid().booleanValue()) || (TencentAgent.getInstance().isSessionValid().booleanValue()))
                ViewCaptureUtil.captureViewPath();
              CheckInAction localCheckInAction = new CheckInAction();
              localCheckInAction.setContentInfo(1, 0);
              RoomManager.getInstance().getRoomByType(1).doAction(localCheckInAction);
            }
          };
          EventDispacthManager.getInstance().dispatchAction("showLogin", local1);
          return;
        }
        MobclickAgent.onEvent(getContext(), "ChatRoomCheckIS");
        if ((WeiboAgent.getInstance().isSessionValid().booleanValue()) || (TencentAgent.getInstance().isSessionValid().booleanValue()))
          ViewCaptureUtil.captureViewPath();
        CheckInAction localCheckInAction = new CheckInAction();
        localCheckInAction.setContentInfo(1, 0);
        RoomManager.getInstance().getRoomByType(1).doAction(localCheckInAction);
        return;
      }
      if (paramString.equalsIgnoreCase("sayhi"))
      {
        hideMessage();
        final UserInfo localUserInfo3 = (UserInfo)paramObject2;
        if ((!WeiboAgent.getInstance().isSessionValid().booleanValue()) && (!TencentAgent.getInstance().isSessionValid().booleanValue()))
        {
          CloudCenter.OnLoginEventListerner local2 = new CloudCenter.OnLoginEventListerner()
          {
            public void onLoginFailed(int paramAnonymousInt)
            {
            }

            public void onLoginSuccessed(int paramAnonymousInt)
            {
              ChatData localChatData = new ChatData();
              localChatData.toUser = localUserInfo3;
              localChatData.content = "你好啊:)";
              SendAction localSendAction = new SendAction();
              localSendAction.setContentInfo(1, localChatData);
              RoomManager.getInstance().getRoomByType(1).doAction(localSendAction);
            }
          };
          EventDispacthManager.getInstance().dispatchAction("showLogin", local2);
          return;
        }
        ChatData localChatData1 = new ChatData();
        localChatData1.toUser = localUserInfo3;
        localChatData1.content = "你好啊:)";
        SendAction localSendAction1 = new SendAction();
        localSendAction1.setContentInfo(1, localChatData1);
        RoomManager.getInstance().getRoomByType(1).doAction(localSendAction1);
        return;
      }
      if (paramString.equalsIgnoreCase("asktoo"))
      {
        hideMessage();
        AskSongTogetherAction localAskSongTogetherAction = new AskSongTogetherAction();
        localAskSongTogetherAction.setContentInfo(1, this.mRoomId);
        RoomManager.getInstance().getRoomByType(1).doAction(localAskSongTogetherAction);
        return;
      }
      if (paramString.equalsIgnoreCase("response"))
      {
        hideMessage();
        this.reportObject = ((UserInfo)paramObject2);
        this.mainView.update("reportSongName", null);
        return;
      }
      if (paramString.equalsIgnoreCase("scrollToFirstIndexAtMe"))
      {
        hideMessage();
        this.mainView.update(paramString, Integer.valueOf(this.mLastIndexAtMe));
        return;
      }
      if (paramString.equalsIgnoreCase("sendSn"))
      {
        TellSongAction localTellSongAction = new TellSongAction();
        localTellSongAction.setContentInfo(1, this.mRoomId, this.reportObject, (String)paramObject2);
        RoomManager.getInstance().getRoomByType(1).doAction(localTellSongAction);
        return;
      }
      if (paramString.equalsIgnoreCase("tosay"))
      {
        Map localMap = (Map)paramObject2;
        BroadcasterNode localBroadcasterNode3 = (BroadcasterNode)localMap.get("dj");
        UserInfo localUserInfo2 = new UserInfo();
        localUserInfo2.userId = String.valueOf(localBroadcasterNode3.id);
        localUserInfo2.snsInfo.sns_id = localBroadcasterNode3.weiboId;
        localUserInfo2.snsInfo.sns_name = localBroadcasterNode3.weiboName;
        SpeakToAction localSpeakToAction = new SpeakToAction();
        boolean bool = WeiboAgent.getInstance().isSessionValid().booleanValue();
        int i = 0;
        if (bool);
        while (true)
        {
          localSpeakToAction.setContentInfo(1, i, localUserInfo2, (String)localMap.get("message"));
          RoomManager.getInstance().getRoomByType(1).doAction(localSpeakToAction);
          return;
          i = 1;
        }
      }
      if (paramString.equalsIgnoreCase("followDj"))
      {
        BroadcasterNode localBroadcasterNode2 = (BroadcasterNode)paramObject2;
        HashMap localHashMap = new HashMap();
        localHashMap.put("uid", localBroadcasterNode2.weiboId);
        WeiboAgent.getInstance().sendWeibo(WeiboAgent.WeiboDataType.TO_ADD, localHashMap, null, null);
        return;
      }
      if (paramString.equalsIgnoreCase("flower"))
      {
        final BroadcasterNode localBroadcasterNode1 = (BroadcasterNode)paramObject2;
        if (!isAccountValid())
        {
          CloudCenter.OnLoginEventListerner local3 = new CloudCenter.OnLoginEventListerner()
          {
            public void onLoginFailed(int paramAnonymousInt)
            {
            }

            public void onLoginSuccessed(int paramAnonymousInt)
            {
              UserInfo localUserInfo = new UserInfo();
              localUserInfo.userId = String.valueOf(localBroadcasterNode1.id);
              localUserInfo.snsInfo.sns_id = localBroadcasterNode1.weiboId;
              localUserInfo.snsInfo.sns_name = localBroadcasterNode1.weiboName;
              FlowerAction localFlowerAction = new FlowerAction();
              localFlowerAction.setContentInfo(1, 0, localUserInfo);
              RoomManager.getInstance().getRoomByType(1).doAction(localFlowerAction);
            }
          };
          EventDispacthManager.getInstance().dispatchAction("showLogin", local3);
          return;
        }
        UserInfo localUserInfo1 = new UserInfo();
        localUserInfo1.userId = String.valueOf(localBroadcasterNode1.id);
        localUserInfo1.snsInfo.sns_id = localBroadcasterNode1.weiboId;
        localUserInfo1.snsInfo.sns_name = localBroadcasterNode1.weiboName;
        FlowerAction localFlowerAction = new FlowerAction();
        localFlowerAction.setContentInfo(1, 0, localUserInfo1);
        RoomManager.getInstance().getRoomByType(1).doAction(localFlowerAction);
        return;
      }
      if (paramString.equalsIgnoreCase("exit"))
      {
        leaveLiveRoom();
        return;
      }
      if (paramString.equalsIgnoreCase("lookItsInfo"))
      {
        openUserProfile((UserInfo)paramObject2);
        return;
      }
      if (paramString.equalsIgnoreCase("chatActionType"))
      {
        ChatActionsView.ChatActionType localChatActionType = (ChatActionsView.ChatActionType)paramObject2;
        switch (8.$SwitchMap$fm$qingting$qtradio$view$chatroom$ChatActionsView$ChatActionType[localChatActionType.ordinal()])
        {
        default:
          return;
        case 1:
          addCollection();
          return;
        case 2:
          EventDispacthManager.getInstance().dispatchAction("shareChoose", InfoManager.getInstance().root().getCurrentPlayingNode());
          QTMSGManage.getInstance().sendStatistcsMessage("chatroom_clickshare");
          return;
        case 3:
          requestSongName();
          QTMSGManage.getInstance().sendStatistcsMessage("chatroom_asksong");
          return;
        case 4:
        }
        answerSongName();
        QTMSGManage.getInstance().sendStatistcsMessage("chatroom_reportsong");
        return;
      }
      if (paramString.equalsIgnoreCase("openonlinemember"))
      {
        openMemberController();
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("clickback"));
    leaveLiveRoom();
    this.mainView.update("closeKeyboard", null);
    ControllerManager.getInstance().popLastController();
  }

  private class MessagePool
  {
    private HashSet<ChatRoomcontroller.RemindMessage> mPool = new HashSet();

    private MessagePool()
    {
    }

    protected void addMessage(ChatRoomcontroller.RemindMessage paramRemindMessage)
    {
      Object localObject1;
      label84: Object localObject2;
      switch (ChatRoomcontroller.8.$SwitchMap$fm$qingting$qtradio$view$chatroom$ActionsFloatView$ChatAction[paramRemindMessage.getAction().ordinal()])
      {
      default:
        return;
      case 1:
        ChatRoomcontroller.this.showMessage(paramRemindMessage);
        return;
      case 2:
        if (ChatRoomcontroller.this.mCurrentAction == ActionsFloatView.ChatAction.Topic)
          if (this.mPool.size() > 0)
          {
            Iterator localIterator = this.mPool.iterator();
            localObject1 = null;
            if (localIterator.hasNext())
            {
              localObject2 = (ChatRoomcontroller.RemindMessage)localIterator.next();
              if (((ChatRoomcontroller.RemindMessage)localObject2).getAction() != ActionsFloatView.ChatAction.Remind)
                break label191;
            }
          }
        break;
      case 3:
      case 4:
      }
      while (true)
      {
        localObject1 = localObject2;
        break label84;
        if (localObject1 != null)
          this.mPool.remove(localObject1);
        this.mPool.add(paramRemindMessage);
        return;
        ChatRoomcontroller.this.showMessage(paramRemindMessage);
        return;
        if ((ChatRoomcontroller.this.mCurrentAction == ActionsFloatView.ChatAction.Topic) || (ChatRoomcontroller.this.mCurrentAction == ActionsFloatView.ChatAction.SayHi))
          break;
        ChatRoomcontroller.this.showMessage(paramRemindMessage);
        return;
        label191: localObject2 = localObject1;
      }
    }

    protected ChatRoomcontroller.RemindMessage pickMessage()
    {
      int i = this.mPool.size();
      Object localObject1 = null;
      if (i > 0)
      {
        Iterator localIterator = this.mPool.iterator();
        if (localIterator.hasNext())
        {
          Object localObject2 = (ChatRoomcontroller.RemindMessage)localIterator.next();
          if (localObject1 == null);
          while (true)
          {
            localObject1 = localObject2;
            break;
            if (!((ChatRoomcontroller.RemindMessage)localObject2).priorityThan(localObject1))
              localObject2 = localObject1;
          }
        }
        if (localObject1 != null)
          this.mPool.remove(localObject1);
      }
      return localObject1;
    }
  }

  private class RemindMessage
  {
    private ActionsFloatView.ChatAction mAction;
    private Object mData;
    private long mInsertTime;

    public RemindMessage(ActionsFloatView.ChatAction paramObject, Object arg3)
    {
      this.mAction = paramObject;
      Object localObject;
      this.mData = localObject;
      this.mInsertTime = System.currentTimeMillis();
    }

    protected ActionsFloatView.ChatAction getAction()
    {
      return this.mAction;
    }

    protected Object getData()
    {
      return this.mData;
    }

    protected long getTime()
    {
      return this.mInsertTime;
    }

    protected boolean priorityThan(RemindMessage paramRemindMessage)
    {
      boolean bool = true;
      switch (ChatRoomcontroller.8.$SwitchMap$fm$qingting$qtradio$view$chatroom$ActionsFloatView$ChatAction[this.mAction.ordinal()])
      {
      default:
        bool = false;
      case 1:
      case 2:
      case 3:
      case 4:
      }
      do
      {
        do
        {
          do
            return bool;
          while (this.mAction.ordinal() > paramRemindMessage.getAction().ordinal());
          if (this.mAction.ordinal() != paramRemindMessage.getAction().ordinal())
            break;
        }
        while (this.mInsertTime < paramRemindMessage.getTime());
        return false;
        return false;
        if ((paramRemindMessage.getAction() == ActionsFloatView.ChatAction.Topic) || (paramRemindMessage.getAction() == ActionsFloatView.ChatAction.Remind))
          return false;
      }
      while (this.mInsertTime < paramRemindMessage.getTime());
      return false;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.chatRoom.ChatRoomcontroller
 * JD-Core Version:    0.6.2
 */