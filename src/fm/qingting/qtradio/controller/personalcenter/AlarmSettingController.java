package fm.qingting.qtradio.controller.personalcenter;

import android.content.Context;
import android.widget.Toast;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.manager.EventDispacthManager.IActionEventHandler;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.model.AlarmInfo;
import fm.qingting.qtradio.model.AlarmInfoNode;
import fm.qingting.qtradio.model.BroadcasterNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.DownLoadInfoNode.IDownloadInfoEventListener;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RingToneInfoNode;
import fm.qingting.qtradio.model.RingToneNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.qtradio.view.personalcenter.clock.AlarmSettingView;
import fm.qingting.utils.QTMSGManage;
import java.util.Calendar;
import java.util.List;

public class AlarmSettingController extends ViewController
  implements INavigationBarListener, IEventHandler, DownLoadInfoNode.IDownloadInfoEventListener, EventDispacthManager.IActionEventHandler
{
  private boolean add = false;
  private AlarmInfo alarmInfo = null;
  private NavigationBarTopView barTopView;
  private ChannelNode extraChannel;
  private ProgramNode extraProgram;
  private boolean isDirect2this = false;
  private boolean remind = false;
  private AlarmSettingView settingView;
  private boolean showingSubController = false;

  public AlarmSettingController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "alarmsetting";
    this.settingView = new AlarmSettingView(paramContext);
    attachView(this.settingView);
    this.barTopView = new NavigationBarTopView(paramContext);
    this.barTopView.setLeftItem(0);
    this.barTopView.setTitleItem(new NavigationBarItem("编辑闹钟"));
    this.barTopView.setRightItem(2);
    this.barTopView.setBarListener(this);
    setNavigationBar(this.barTopView);
    EventDispacthManager.getInstance().addListener(this);
    InfoManager.getInstance().root().mDownLoadInfoNode.registerListener(this);
  }

  private void constructAlarm()
  {
    int i = ((Integer)this.settingView.getValue("time", null)).intValue();
    int j = ((Integer)this.settingView.getValue("day", null)).intValue();
    boolean bool = ((Boolean)this.settingView.getValue("repeat", null)).booleanValue();
    if (this.alarmInfo == null)
      this.alarmInfo = new AlarmInfo();
    this.alarmInfo.alarmTime = i;
    this.alarmInfo.dayOfWeek = j;
    this.alarmInfo.repeat = bool;
    this.alarmInfo.isAvailable = true;
    RingToneNode localRingToneNode = InfoManager.getInstance().root().mRingToneInfoNode.getRingNodeById(this.alarmInfo.ringToneId);
    if ((localRingToneNode != null) && (localRingToneNode.ringType.equalsIgnoreCase("online")))
    {
      EventDispacthManager.getInstance().dispatchAction("showRingtoneLoadingView", null);
      InfoManager.getInstance().root().mDownLoadInfoNode.startDownLoadRing(localRingToneNode);
    }
    while (true)
    {
      return;
      InfoManager.getInstance().root().mPersonalCenterNode.alarmInfoNode.updateAlarm(this.alarmInfo);
      SharedCfg.getInstance().setAlarmAdded();
      if (this.remind)
        Toast.makeText(getContext(), "闹钟添加成功啦！", 0).show();
      if (!this.isDirect2this)
      {
        dispatchEvent("refreshList", null);
        ControllerManager.getInstance().popLastController();
      }
      while (this.add)
      {
        QTMSGManage.getInstance().sendStatistcsMessage("alarm_addsucess", this.alarmInfo.ringToneId);
        return;
        ControllerManager.getInstance().popLastControllerAndOpenParent();
      }
    }
  }

  private void openDaySetting()
  {
    boolean bool = ((Boolean)this.settingView.getValue("repeat", null)).booleanValue();
    int i = ((Integer)this.settingView.getValue("day", null)).intValue();
    ControllerManager localControllerManager = ControllerManager.getInstance();
    if (bool);
    while (true)
    {
      localControllerManager.openDaySettingController(i, this);
      return;
      i = -1;
    }
  }

  private void openRingChannelSetting()
  {
    ControllerManager.getInstance().openRingChannelSettingController(this.alarmInfo, this.extraChannel, this);
  }

  private void openRingtoneSetting()
  {
    this.showingSubController = true;
    ControllerManager.getInstance().openRingtoneSettingController(this.alarmInfo, this);
  }

  private void resetData(int paramInt)
  {
    if ((paramInt == 0) || ((paramInt & (int)Math.pow(2.0D, 7.0D)) > 0))
    {
      this.alarmInfo.repeat = false;
      this.settingView.update("repeat", Boolean.valueOf(false));
      return;
    }
    int i = paramInt & 0x40;
    int j = 0xFF & paramInt << 2 | i >> 5;
    this.alarmInfo.repeat = true;
    this.settingView.update("repeat", Boolean.valueOf(true));
    this.settingView.update("day", Integer.valueOf(j));
  }

  public void addAlarmByRingtone(BroadcasterNode paramBroadcasterNode, Node paramNode)
  {
    this.add = true;
    this.remind = true;
    this.barTopView.setTitleItem(new NavigationBarItem("添加闹钟"));
    ChannelNode localChannelNode;
    if (paramNode != null)
      if (paramNode.nodeName.equalsIgnoreCase("program"))
      {
        if ((paramNode.parent == null) || (!paramNode.parent.nodeName.equalsIgnoreCase("channel")))
          break label329;
        localChannelNode = (ChannelNode)paramNode.parent;
      }
    while (true)
    {
      if ((localChannelNode == null) && (InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes().size() > 0))
        localChannelNode = (ChannelNode)InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes().get(0);
      if (this.alarmInfo == null)
        this.alarmInfo = new AlarmInfo();
      if (localChannelNode == null)
      {
        this.alarmInfo.channelId = 386;
        this.alarmInfo.channelName = "CNR中国之声";
        this.alarmInfo.categoryId = 54;
      }
      for (this.alarmInfo.alarmType = 0; ; this.alarmInfo.alarmType = localChannelNode.channelType)
      {
        this.alarmInfo.alarmTime = 25200;
        this.alarmInfo.repeat = true;
        this.alarmInfo.dayOfWeek = 0;
        this.alarmInfo.isAvailable = true;
        this.alarmInfo.ringToneId = String.valueOf(paramBroadcasterNode.ringToneId);
        this.settingView.update("setData", this.alarmInfo);
        this.settingView.update("noDelete", null);
        return;
        if (!paramNode.nodeName.equalsIgnoreCase("channel"))
          break label329;
        localChannelNode = (ChannelNode)paramNode;
        break;
        this.alarmInfo.channelName = localChannelNode.title;
        this.alarmInfo.channelId = localChannelNode.channelId;
        this.alarmInfo.categoryId = localChannelNode.categoryId;
      }
      label329: localChannelNode = null;
    }
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.alarmInfo = ((AlarmInfo)paramObject);
      this.settingView.update(paramString, paramObject);
      return;
    }
    if (paramString.equalsIgnoreCase("addalarm"))
    {
      this.add = true;
      this.barTopView.setTitleItem(new NavigationBarItem("添加闹钟"));
      if (InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes().size() <= 0)
        break label974;
    }
    label969: label974: for (ChannelNode localChannelNode2 = (ChannelNode)InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes().get(0); ; localChannelNode2 = null)
    {
      if (this.alarmInfo == null)
        this.alarmInfo = new AlarmInfo();
      if (localChannelNode2 == null)
      {
        this.alarmInfo.channelId = 386;
        this.alarmInfo.channelName = "CNR中国之声";
        this.alarmInfo.categoryId = 54;
      }
      for (this.alarmInfo.alarmType = 0; ; this.alarmInfo.alarmType = localChannelNode2.channelType)
      {
        Calendar.getInstance().setTimeInMillis(60000L + System.currentTimeMillis());
        this.alarmInfo.alarmTime = 25200;
        this.alarmInfo.repeat = true;
        this.alarmInfo.dayOfWeek = 0;
        this.alarmInfo.isAvailable = true;
        this.settingView.update("setData", this.alarmInfo);
        this.settingView.update("noDelete", null);
        return;
        this.alarmInfo.channelName = localChannelNode2.title;
        this.alarmInfo.channelId = localChannelNode2.channelId;
        this.alarmInfo.categoryId = localChannelNode2.categoryId;
      }
      if (paramString.equalsIgnoreCase("addalarmbyChannel"))
      {
        if (paramObject == null)
          break;
        this.remind = true;
        this.add = true;
        this.barTopView.setTitleItem(new NavigationBarItem("添加闹钟"));
        this.extraChannel = ((ChannelNode)paramObject);
        if (this.alarmInfo == null)
          this.alarmInfo = new AlarmInfo();
        this.alarmInfo.channelName = this.extraChannel.title;
        this.alarmInfo.channelId = this.extraChannel.channelId;
        this.alarmInfo.categoryId = this.extraChannel.categoryId;
        this.alarmInfo.alarmType = this.extraChannel.channelType;
        this.alarmInfo.alarmTime = 25200;
        this.alarmInfo.repeat = true;
        this.alarmInfo.dayOfWeek = 0;
        this.alarmInfo.isAvailable = true;
        this.settingView.update("setData", this.alarmInfo);
        this.settingView.update("noDelete", null);
        return;
      }
      if (paramString.equalsIgnoreCase("addalarmbyprogram"))
      {
        if (paramObject == null)
          break;
        this.remind = true;
        this.add = true;
        this.barTopView.setTitleItem(new NavigationBarItem("添加闹钟"));
        this.extraProgram = ((ProgramNode)paramObject);
        if (this.alarmInfo == null)
          this.alarmInfo = new AlarmInfo();
        this.alarmInfo.channelName = this.extraProgram.title;
        this.alarmInfo.channelId = this.extraProgram.channelId;
        this.alarmInfo.categoryId = this.extraProgram.getCategoryId();
        this.alarmInfo.alarmType = this.extraProgram.channelType;
        this.alarmInfo.programId = this.extraProgram.id;
        this.alarmInfo.alarmTime = 25200;
        this.alarmInfo.repeat = true;
        this.alarmInfo.dayOfWeek = 0;
        this.alarmInfo.isAvailable = true;
        this.settingView.update("setData", this.alarmInfo);
        this.settingView.update("noDelete", null);
        return;
      }
      if (paramString.equalsIgnoreCase("addalarmbyRingtone"))
      {
        this.add = true;
        this.remind = true;
        this.barTopView.setTitleItem(new NavigationBarItem("添加闹钟"));
        if (InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes().size() <= 0)
          break label969;
      }
      for (ChannelNode localChannelNode1 = (ChannelNode)InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes().get(0); ; localChannelNode1 = null)
      {
        if (this.alarmInfo == null)
          this.alarmInfo = new AlarmInfo();
        if (localChannelNode1 == null)
        {
          this.alarmInfo.channelId = 386;
          this.alarmInfo.channelName = "CNR中国之声";
          this.alarmInfo.categoryId = 54;
        }
        for (this.alarmInfo.alarmType = 0; ; this.alarmInfo.alarmType = localChannelNode1.channelType)
        {
          this.alarmInfo.alarmTime = 25200;
          this.alarmInfo.repeat = true;
          this.alarmInfo.dayOfWeek = 0;
          this.alarmInfo.isAvailable = true;
          if (paramObject != null)
          {
            BroadcasterNode localBroadcasterNode = (BroadcasterNode)paramObject;
            this.alarmInfo.ringToneId = String.valueOf(localBroadcasterNode.ringToneId);
          }
          this.settingView.update("setData", this.alarmInfo);
          this.settingView.update("noDelete", null);
          return;
          this.alarmInfo.channelName = localChannelNode1.title;
          this.alarmInfo.channelId = localChannelNode1.channelId;
          this.alarmInfo.categoryId = localChannelNode1.categoryId;
        }
        if (!paramString.equalsIgnoreCase("setDirect"))
          break;
        this.isDirect2this = true;
        return;
      }
    }
  }

  public void controllerDidPopped()
  {
    InfoManager.getInstance().root().mDownLoadInfoNode.unregisterListener(this);
    EventDispacthManager.getInstance().removeListener(this);
    this.settingView.close(false);
    super.controllerDidPopped();
  }

  public void onAction(String paramString, Object paramObject)
  {
    if ((paramString.equalsIgnoreCase("retryDownload")) && (!this.showingSubController))
      constructAlarm();
  }

  public void onDownLoadInfoUpdated(int paramInt, Node paramNode)
  {
    switch (paramInt)
    {
    default:
    case 1:
    case 2:
    }
    do
    {
      do
        return;
      while ((this.showingSubController) || (paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("ringtone")));
      EventDispacthManager.getInstance().dispatchAction("ringtoneLoadComplete", null);
      constructAlarm();
      ControllerManager.getInstance().popLastController();
      return;
    }
    while ((this.showingSubController) || (paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("ringtone")));
    EventDispacthManager.getInstance().dispatchAction("ringtoneLoadFailed", null);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("day"))
      resetData(((Integer)paramObject2).intValue());
    do
    {
      return;
      if (paramString.equalsIgnoreCase("select"))
      {
        ChannelNode localChannelNode = (ChannelNode)paramObject2;
        if (this.alarmInfo == null)
          this.alarmInfo = new AlarmInfo();
        this.alarmInfo.channelName = localChannelNode.title;
        this.alarmInfo.channelId = localChannelNode.channelId;
        this.alarmInfo.categoryId = localChannelNode.categoryId;
        this.alarmInfo.alarmType = localChannelNode.channelType;
        this.settingView.update("changeRing", localChannelNode.title);
        return;
      }
      if (paramString.equalsIgnoreCase("pickedRingtone"))
      {
        this.settingView.update(paramString, paramObject2);
        if (paramObject2 != null)
        {
          RingToneNode localRingToneNode = (RingToneNode)paramObject2;
          if (this.alarmInfo == null)
            this.alarmInfo = new AlarmInfo();
          this.alarmInfo.ringToneId = localRingToneNode.ringToneId;
          return;
        }
        if (this.alarmInfo == null)
          this.alarmInfo = new AlarmInfo();
        this.alarmInfo.ringToneId = "0";
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("resetFlag"));
    this.showingSubController = false;
  }

  public void onItemClick(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 2:
      ControllerManager.getInstance().popLastController();
      return;
    case 3:
    }
    constructAlarm();
  }

  protected void onViewEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("editDay"))
    {
      QTMSGManage.getInstance().sendStatistcsMessage("alarm_settingclick", "daysetting");
      openDaySetting();
    }
    do
    {
      return;
      if (paramString.equalsIgnoreCase("editRingtone"))
      {
        QTMSGManage.getInstance().sendStatistcsMessage("alarm_settingclick", "ringtone");
        openRingtoneSetting();
        return;
      }
      if (paramString.equalsIgnoreCase("editChannel"))
      {
        QTMSGManage.getInstance().sendStatistcsMessage("alarm_settingclick", "channel");
        openRingChannelSetting();
        return;
      }
    }
    while ((!paramString.equalsIgnoreCase("deleteAlarm")) || (this.alarmInfo == null));
    InfoManager.getInstance().root().mPersonalCenterNode.alarmInfoNode.removeAlarm(this.alarmInfo);
    dispatchEvent("refreshList", null);
    ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.personalcenter.AlarmSettingController
 * JD-Core Version:    0.6.2
 */