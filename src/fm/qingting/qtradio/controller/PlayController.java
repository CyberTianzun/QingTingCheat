package fm.qingting.qtradio.controller;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.helper.ProgramHelper;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IPlayInfoEventListener;
import fm.qingting.qtradio.model.RootNode.PlayMode;
import fm.qingting.qtradio.room.CheckInAction;
import fm.qingting.qtradio.room.GetHistoryAction;
import fm.qingting.qtradio.room.Room;
import fm.qingting.qtradio.room.RoomManager;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.social.CloudCenter.OnLoginEventListerner;
import fm.qingting.qtradio.tencentAgent.TencentAgent;
import fm.qingting.qtradio.view.playview.PlayView;
import fm.qingting.qtradio.weiboAgent.WeiboAgent;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.ViewCaptureUtil;
import java.util.List;

public class PlayController extends ViewController
  implements RootNode.IPlayInfoEventListener, InfoManager.ISubscribeEventListener
{
  private static PlayController _instance;
  private ActivityNode mActivityNode;
  private boolean mAutoPlay = false;
  private boolean mHasLoadProgramSchedule = false;
  private int mLoadProgramId = 0;
  private PlayView mainView;

  private PlayController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "mainplayview";
    this.mainView = new PlayView(paramContext);
    ViewCaptureUtil.setScreenView(this.mainView);
    attachView(this.mainView);
    InfoManager.getInstance().root().registerSubscribeEventListener(this, 1);
    InfoManager.getInstance().root().registerSubscribeEventListener(this, 0);
  }

  private void autoPlay()
  {
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    if (localChannelNode != null)
    {
      if (localChannelNode.channelType != 0)
        break label97;
      if (!localChannelNode.hasEmptyProgramSchedule())
        break label49;
      PlayerAgent.getInstance().play(localChannelNode);
      this.mHasLoadProgramSchedule = true;
      InfoManager.getInstance().loadProgramsScheduleNode(localChannelNode, this);
    }
    label49: label97: ProgramNode localProgramNode;
    do
    {
      Node localNode;
      do
      {
        return;
        localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      }
      while ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)localNode).channelId == localChannelNode.channelId));
      PlayerAgent.getInstance().play(localChannelNode);
      return;
      if (localChannelNode.hasEmptyProgramSchedule())
      {
        this.mAutoPlay = true;
        this.mHasLoadProgramSchedule = true;
        InfoManager.getInstance().loadProgramsScheduleNode(localChannelNode, this);
        return;
      }
      localProgramNode = (ProgramNode)localChannelNode.getAllLstProgramNode().get(0);
    }
    while (localProgramNode == null);
    PlayerAgent.getInstance().play(localProgramNode);
    chooseNodetoSet();
  }

  private void checkin()
  {
    if (!CloudCenter.getInstance().isLogin())
    {
      CloudCenter.OnLoginEventListerner local1 = new CloudCenter.OnLoginEventListerner()
      {
        public void onLoginFailed(int paramAnonymousInt)
        {
        }

        public void onLoginSuccessed(int paramAnonymousInt)
        {
          if (InfoManager.getInstance().getEnableNewCheckin() == 1)
            QTMSGManage.getInstance().sendStatistcsMessage("checkinABTestOldFin");
          MobclickAgent.onEvent(PlayController.this.getContext(), "PlayViewCheckIS");
          if ((WeiboAgent.getInstance().isSessionValid().booleanValue()) || (TencentAgent.getInstance().isSessionValid().booleanValue()))
          {
            ViewCaptureUtil.setScreenView(PlayController.this.view);
            ViewCaptureUtil.captureViewPath();
          }
          CheckInAction localCheckInAction = new CheckInAction();
          localCheckInAction.setContentInfo(1, 0);
          RoomManager.getInstance().getRoomByType(1).doAction(localCheckInAction);
        }
      };
      EventDispacthManager.getInstance().dispatchAction("showLogin", local1);
      return;
    }
    if (InfoManager.getInstance().getEnableNewCheckin() == 1)
      QTMSGManage.getInstance().sendStatistcsMessage("checkinABTestOldFin");
    MobclickAgent.onEvent(getContext(), "PlayViewCheckIS");
    if ((WeiboAgent.getInstance().isSessionValid().booleanValue()) || (TencentAgent.getInstance().isSessionValid().booleanValue()))
    {
      ViewCaptureUtil.setScreenView(this.view);
      ViewCaptureUtil.captureViewPath();
    }
    CheckInAction localCheckInAction = new CheckInAction();
    localCheckInAction.setContentInfo(1, 0);
    RoomManager.getInstance().getRoomByType(1).doAction(localCheckInAction);
  }

  private void chooseNodetoSet()
  {
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if (localNode == null)
    {
      ChannelNode localChannelNode2 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      if ((localChannelNode2 != null) && (localChannelNode2.hasEmptyProgramSchedule()))
      {
        this.mHasLoadProgramSchedule = true;
        InfoManager.getInstance().loadProgramsScheduleNode(localChannelNode2, this);
      }
    }
    ChannelNode localChannelNode1;
    do
    {
      do
      {
        return;
        this.mainView.update("setProgramNode", localNode);
        this.mHasLoadProgramSchedule = false;
        localChannelNode1 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      }
      while (localChannelNode1 == null);
      if (localChannelNode1.hasEmptyProgramSchedule())
      {
        this.mHasLoadProgramSchedule = true;
        this.mainView.update("setProgramNode", ProgramHelper.getInstance().getProgramTempNode());
        InfoManager.getInstance().loadProgramsScheduleNode(localChannelNode1, this);
        return;
      }
      if (localNode.nodeName.equalsIgnoreCase("channel"))
      {
        ProgramNode localProgramNode2 = localChannelNode1.getProgramNodeByTime(System.currentTimeMillis());
        if (localProgramNode2 != null)
        {
          this.mainView.update("setProgramNode", localProgramNode2);
          return;
        }
        this.mainView.update("setProgramNode", ProgramHelper.getInstance().getProgramTempNode());
        return;
      }
    }
    while ((!localNode.nodeName.equalsIgnoreCase("program")) || (((ProgramNode)localNode).available));
    ProgramNode localProgramNode1 = localChannelNode1.getProgramNodeByTime(System.currentTimeMillis());
    if (localProgramNode1 != null)
    {
      this.mainView.update("setProgramNode", localProgramNode1);
      return;
    }
    this.mainView.update("setProgramNode", ProgramHelper.getInstance().getProgramTempNode());
  }

  private void execAchilles()
  {
    if (this.mActivityNode == null)
    {
      this.mActivityNode = new ActivityNode();
      this.mActivityNode.id = 1;
      this.mActivityNode.name = "活动";
      this.mActivityNode.type = "1";
      this.mActivityNode.updatetime = 25200;
      this.mActivityNode.infoUrl = null;
      this.mActivityNode.infoTitle = "";
      this.mActivityNode.desc = null;
      this.mActivityNode.titleIconUrl = null;
      this.mActivityNode.network = null;
      this.mActivityNode.putUserInfo = false;
    }
    this.mActivityNode.contentUrl = InfoManager.getInstance().getAchillesUrl();
    if (this.mActivityNode.contentUrl == null)
    {
      InfoManager.getInstance().setAchilles(false);
      return;
    }
    ControllerManager.getInstance().redirectToActivityViewByNode(this.mActivityNode);
  }

  public static PlayController getInstance(Context paramContext)
  {
    if (_instance == null)
      _instance = new PlayController(paramContext);
    return _instance;
  }

  private void joinChat()
  {
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    if (localChannelNode == null);
    while (true)
    {
      return;
      if (localChannelNode.groupId == 0)
      {
        String str1 = String.valueOf(localChannelNode.channelId);
        Node localNode;
        if (localChannelNode.channelType == 1)
        {
          localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
          if ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("program")) || (((ProgramNode)localNode).getCurrPlayStatus() != 1));
        }
        for (String str2 = String.valueOf(((ProgramNode)localNode).channelId); str2 != null; str2 = str1)
        {
          GetHistoryAction localGetHistoryAction = new GetHistoryAction();
          localGetHistoryAction.setConnectUrl(InfoManager.getInstance().chatServer, str2, 1);
          RoomManager.getInstance().getRoomByType(1).doAction(localGetHistoryAction);
          return;
        }
      }
    }
  }

  private void openChatRoom()
  {
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")));
    for (ProgramNode localProgramNode = (ProgramNode)localNode; ; localProgramNode = null)
    {
      if (localProgramNode == null)
        return;
      ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      GroupInfo localGroupInfo;
      if (localProgramNode.channelType == 0)
        localGroupInfo = IMAgent.getInstance().getGroupInfo(String.valueOf(localProgramNode.groupId));
      while (true)
        if ((localGroupInfo != null) && (localGroupInfo.groupId != null) && (localGroupInfo.groupId.length() > 0) && (InfoManager.getInstance().enableSocial(localGroupInfo.groupId)))
        {
          ControllerManager.getInstance().openImGroupProfileController(localGroupInfo.groupId);
          return;
          if (localProgramNode.groupId != 0)
            localGroupInfo = IMAgent.getInstance().getGroupInfo(String.valueOf(localProgramNode.groupId));
          else if (localChannelNode != null)
            localGroupInfo = IMAgent.getInstance().getGroupInfo(String.valueOf(localChannelNode.groupId));
        }
        else
        {
          if (!QtApiLevelManager.isApiLevelSupported(8))
          {
            Toast.makeText(getContext(), "非常抱歉直播间只能在Android2.2版本以上使用", 0).show();
            return;
          }
          ControllerManager.getInstance().openChatRoom("", null, null, localProgramNode, new Object[0]);
          return;
          localGroupInfo = null;
        }
    }
  }

  private void setProgramNode(int paramInt)
  {
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    if (localChannelNode != null)
    {
      if (localChannelNode.hasEmptyProgramSchedule())
      {
        this.mLoadProgramId = paramInt;
        InfoManager.getInstance().loadProgramsScheduleNode(localChannelNode, null);
        InfoManager.getInstance().loadProgramInfo(localChannelNode, paramInt, this);
      }
    }
    else
      return;
    ProgramNode localProgramNode = localChannelNode.getProgramNode(Integer.valueOf(paramInt).intValue());
    if (localProgramNode != null)
    {
      PlayerAgent.getInstance().play(localProgramNode);
      chooseNodetoSet();
      return;
    }
    this.mLoadProgramId = paramInt;
    InfoManager.getInstance().loadProgramInfo(localChannelNode, paramInt, this);
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      chooseNodetoSet();
    do
    {
      return;
      if (paramString.equalsIgnoreCase("programid"))
      {
        setProgramNode(((Integer)paramObject).intValue());
        return;
      }
      if (paramString.equalsIgnoreCase("autoplay"))
      {
        int i = ((Integer)paramObject).intValue();
        if (i == 0)
        {
          autoPlay();
          return;
        }
        setProgramNode(i);
        return;
      }
      if (paramString.equalsIgnoreCase("liftSomeViews"))
      {
        this.mainView.update(paramString, paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("resetSomeViews"))
      {
        this.mainView.update(paramString, paramObject);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("showSchedule"));
    this.mainView.update(paramString, paramObject);
  }

  public void controllerDidPopped()
  {
    super.controllerDidPopped();
  }

  public void controllerDidPushed()
  {
    joinChat();
    if (InfoManager.getInstance().enterChatroom(InfoManager.getInstance().root().getCurrentPlayingChannelNode()))
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          PlayController.this.openChatRoom();
        }
      }
      , 2000L);
    while (!InfoManager.getInstance().enableAchilles())
      return;
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        PlayController.this.execAchilles();
      }
    }
    , 2000L);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("progressPosition"))
      return this.mainView.getValue(paramString, paramObject);
    return super.getValue(paramString, paramObject);
  }

  public void onNotification(String paramString)
  {
    ChannelNode localChannelNode2;
    ProgramNode localProgramNode3;
    if (paramString.equalsIgnoreCase("RPS"))
    {
      localChannelNode2 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      if ((this.mHasLoadProgramSchedule) && (localChannelNode2 != null) && (!localChannelNode2.hasEmptyProgramSchedule()))
      {
        if (localChannelNode2.channelType != 0)
          break label151;
        localProgramNode3 = localChannelNode2.getProgramNodeByTime(System.currentTimeMillis());
        if (localProgramNode3 != null)
        {
          if (!this.mAutoPlay)
            break label96;
          this.mAutoPlay = false;
          PlayerAgent.getInstance().play(localProgramNode3);
          this.mainView.update("setProgramNode", localProgramNode3);
        }
      }
    }
    label96: ProgramNode localProgramNode1;
    label151: 
    do
    {
      ChannelNode localChannelNode1;
      do
      {
        do
        {
          ProgramNode localProgramNode2;
          do
          {
            return;
            if ((InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.ALARMPLAY) || (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.ALARM_PLAY_ONLINE))
            {
              PlayerAgent.getInstance().play(localProgramNode3);
              break;
            }
            InfoManager.getInstance().root().setPlayingNode(localProgramNode3);
            break;
            localProgramNode2 = (ProgramNode)localChannelNode2.getAllLstProgramNode().get(0);
          }
          while (localProgramNode2 == null);
          if (this.mAutoPlay)
          {
            this.mAutoPlay = false;
            PlayerAgent.getInstance().play(localProgramNode2);
          }
          while (true)
          {
            chooseNodetoSet();
            return;
            if ((InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.ALARMPLAY) || (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.ALARM_PLAY_ONLINE))
              PlayerAgent.getInstance().play(localProgramNode2);
          }
        }
        while ((!paramString.equalsIgnoreCase("RVPI")) || (this.mLoadProgramId == 0));
        localChannelNode1 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      }
      while ((localChannelNode1 == null) || (localChannelNode1.hasEmptyProgramSchedule()));
      localProgramNode1 = localChannelNode1.getProgramNode(Integer.valueOf(this.mLoadProgramId).intValue());
    }
    while (localProgramNode1 == null);
    this.mLoadProgramId = 0;
    PlayerAgent.getInstance().play(localProgramNode1);
    chooseNodetoSet();
  }

  public void onPlayInfoUpdated(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
    default:
      return;
    case 1:
    }
    chooseNodetoSet();
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("checkin"))
      checkin();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.PlayController
 * JD-Core Version:    0.6.2
 */