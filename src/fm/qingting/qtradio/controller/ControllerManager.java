package fm.qingting.qtradio.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.controller.ISwitchAnimation;
import fm.qingting.framework.controller.NavigationController;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.qtradio.animation.MoveLtoRUncoverAnimation;
import fm.qingting.qtradio.animation.MoveRtoLSwitchAnimation;
import fm.qingting.qtradio.animation.SearchSpecialEnterAnimation;
import fm.qingting.qtradio.animation.SearchSpecialExitAnimation;
import fm.qingting.qtradio.controller.chatRoom.ChatHistoryController;
import fm.qingting.qtradio.controller.chatRoom.ChatRoomcontroller;
import fm.qingting.qtradio.controller.chatRoom.OnlineMembersController;
import fm.qingting.qtradio.controller.groupselect.GroupWebViewController;
import fm.qingting.qtradio.controller.im.ImBlackListController;
import fm.qingting.qtradio.controller.im.ImChatController;
import fm.qingting.qtradio.controller.im.ImContactSpecificController;
import fm.qingting.qtradio.controller.im.ImContactsController;
import fm.qingting.qtradio.controller.im.ImConversationsController;
import fm.qingting.qtradio.controller.im.ImGroupMemberListController;
import fm.qingting.qtradio.controller.im.ImGroupProfileController;
import fm.qingting.qtradio.controller.im.ImGroupSettingController;
import fm.qingting.qtradio.controller.im.ImMyGroupsController;
import fm.qingting.qtradio.controller.im.ImUserProfileController;
import fm.qingting.qtradio.controller.im.ImUserSettingController;
import fm.qingting.qtradio.controller.im.ReportController;
import fm.qingting.qtradio.controller.personalcenter.AboutQtController;
import fm.qingting.qtradio.controller.personalcenter.AlarmDaySettingController;
import fm.qingting.qtradio.controller.personalcenter.AlarmDjRingtoneSettingController;
import fm.qingting.qtradio.controller.personalcenter.AlarmListController;
import fm.qingting.qtradio.controller.personalcenter.AlarmSettingController;
import fm.qingting.qtradio.controller.personalcenter.AudioQualitySettingController;
import fm.qingting.qtradio.controller.personalcenter.ContactInfoController;
import fm.qingting.qtradio.controller.personalcenter.DownloadDirSettingController;
import fm.qingting.qtradio.controller.personalcenter.FaqController;
import fm.qingting.qtradio.controller.personalcenter.FeedbackController;
import fm.qingting.qtradio.controller.personalcenter.HiddenFeaturesController;
import fm.qingting.qtradio.controller.personalcenter.MyDownloadController;
import fm.qingting.qtradio.controller.personalcenter.MyDownloadProgramController;
import fm.qingting.qtradio.controller.personalcenter.MyPodcasterController;
import fm.qingting.qtradio.controller.personalcenter.PlayGameController;
import fm.qingting.qtradio.controller.personalcenter.PlayHistoryController;
import fm.qingting.qtradio.controller.personalcenter.PushMessageSettingController;
import fm.qingting.qtradio.controller.personalcenter.ReserveInfoController;
import fm.qingting.qtradio.controller.personalcenter.RingChannelPickController;
import fm.qingting.qtradio.controller.virtual.ChannelDetailController;
import fm.qingting.qtradio.controller.virtual.MyCollectionController;
import fm.qingting.qtradio.controller.virtual.NovelDetailController;
import fm.qingting.qtradio.controller.virtual.UploadVoiceController;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.helper.PodcasterHelper;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.jd.data.CommodityInfo;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.manager.LinkManager;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.model.AdvertisementItemNode;
import fm.qingting.qtradio.model.AlarmInfo;
import fm.qingting.qtradio.model.AlarmInfoNode;
import fm.qingting.qtradio.model.Attribute;
import fm.qingting.qtradio.model.Attributes;
import fm.qingting.qtradio.model.BillboardItemNode;
import fm.qingting.qtradio.model.BroadcasterNode;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.ContentCategoryNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.GameBean;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.LiveNode;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.PlayHistoryInfoNode;
import fm.qingting.qtradio.model.PlayHistoryNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RadioChannelNode;
import fm.qingting.qtradio.model.RecommendCategoryNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.model.RecommendPlayingItemNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.model.SpecialTopicNode;
import fm.qingting.qtradio.model.VirtualNode;
import fm.qingting.qtradio.model.advertisement.QTAdvertisementInfo;
import fm.qingting.qtradio.model.advertisement.QTCoupon;
import fm.qingting.qtradio.room.TencentChat;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.room.WeiboChat;
import fm.qingting.qtradio.search.SearchItemNode;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.view.chatroom.chatlist.ChatItem;
import fm.qingting.qtradio.view.groupselect.GroupWebView;
import fm.qingting.qtradio.wo.WoFaqViewController;
import fm.qingting.qtradio.wo.WoQtController;
import fm.qingting.utils.DateUtil;
import fm.qingting.utils.QTMSGManage;
import java.util.Iterator;
import java.util.List;

public class ControllerManager
{
  private static ControllerManager _instance;
  private boolean hasPlayedLastChannel = false;
  private int mChannelSource = 0;
  public Context mContext;
  private NavigationController navigationController = null;

  private ViewController createController(String paramString)
  {
    if (paramString.equalsIgnoreCase("activities"))
      return new ActivitiesController(this.mContext);
    if (paramString.equalsIgnoreCase("categorylist"))
      return new CategoryListController(this.mContext);
    if (paramString.equalsIgnoreCase("channellist"))
      return new ChannelsListController(this.mContext);
    if (paramString.equalsIgnoreCase("radiochannellist"))
      return new RadioChannelsListController(this.mContext);
    if (paramString.equalsIgnoreCase("mainplayview"))
      return PlayController.getInstance(this.mContext);
    if (paramString.equalsIgnoreCase("danmakumainplayview"))
      return DanmakuPlayController.getInstance(this.mContext);
    if (paramString.equalsIgnoreCase("setting"))
      return new SettingController(this.mContext);
    if (paramString.equalsIgnoreCase("noveldetail"))
      return new NovelDetailController(this.mContext);
    if (paramString.equalsIgnoreCase("channeldetail"))
      return new ChannelDetailController(this.mContext);
    if (paramString.equalsIgnoreCase("batchDownload"))
      return new BatchDownloadController(this.mContext);
    if (paramString.equalsIgnoreCase("batchdownload_tradition"))
      return new BatchDownloadTraditionController(this.mContext);
    if (paramString.equalsIgnoreCase("mycollection"))
      return new MyCollectionController(this.mContext);
    if (paramString.equalsIgnoreCase("playhistory"))
      return new PlayHistoryController(this.mContext);
    if (paramString.equalsIgnoreCase("playgame"))
      return new PlayGameController(this.mContext);
    if (paramString.equalsIgnoreCase("search"))
      return new SearchController(this.mContext);
    if (paramString.equalsIgnoreCase("alarmsetting"))
      return new AlarmSettingController(this.mContext);
    if (paramString.equalsIgnoreCase("downloadprogram"))
      return new MyDownloadProgramController(this.mContext);
    if (paramString.equalsIgnoreCase("hiddenfeatures"))
      return new HiddenFeaturesController(this.mContext);
    if (paramString.equalsIgnoreCase("contacts"))
      return new ImContactsController(this.mContext);
    if (paramString.equalsIgnoreCase("conversations"))
      return new ImConversationsController(this.mContext);
    if (paramString.equalsIgnoreCase("userprofile"))
      return new ImUserProfileController(this.mContext);
    if (paramString.equalsIgnoreCase("groupprofile"))
      return new ImGroupProfileController(this.mContext);
    if (paramString.equalsIgnoreCase("imchat"))
      return new ImChatController(this.mContext);
    if (paramString.equalsIgnoreCase("groupmemberlist"))
      return new ImGroupMemberListController(this.mContext);
    if (paramString.equalsIgnoreCase("groupsetting"))
      return new ImGroupSettingController(this.mContext);
    if (paramString.equalsIgnoreCase("mygroups"))
      return new ImMyGroupsController(this.mContext);
    if (paramString.equalsIgnoreCase("contactspecific"))
      return new ImContactSpecificController(this.mContext);
    if (paramString.equalsIgnoreCase("usersetting"))
      return new ImUserSettingController(this.mContext);
    if (paramString.equalsIgnoreCase("playlist"))
      return new PlayListController(this.mContext);
    if (paramString.equalsIgnoreCase("danmakuplaylist"))
      return new DanmakuPlayListController(this.mContext);
    if (paramString.equalsIgnoreCase("traschedule"))
      return new TraScheduleController(this.mContext);
    if (paramString.equalsIgnoreCase("timerSetting"))
      return new TimerPickController(this.mContext);
    if (paramString.equalsIgnoreCase("play"))
      return PlayController.getInstance(this.mContext);
    if (paramString.equalsIgnoreCase("report"))
      return new ReportController(this.mContext);
    if (paramString.equalsIgnoreCase("myreserve"))
      return new ReserveInfoController(this.mContext);
    if (paramString.equalsIgnoreCase("blockedmembers"))
      return new ImBlackListController(this.mContext);
    if (paramString.equalsIgnoreCase("virtualchannellist"))
      return new VirtualChannelListController(this.mContext);
    if (paramString.equalsIgnoreCase("specialtopic"))
      return new SpecialTopicController(this.mContext);
    if (paramString.equalsIgnoreCase("podcasterinfo"))
      return new PodcasterInfoController(this.mContext);
    if (paramString.equalsIgnoreCase("mypodcaster"))
      return new MyPodcasterController(this.mContext);
    if (paramString.equalsIgnoreCase(DanmakuSendController.NAME))
      return new DanmakuSendController(this.mContext);
    return null;
  }

  private ViewController getController(String paramString)
  {
    Iterator localIterator = this.navigationController.getAllControllers().iterator();
    int i = 0;
    int j;
    ViewController localViewController;
    if (localIterator.hasNext())
      if (((ViewController)localIterator.next()).controllerName.equalsIgnoreCase(paramString))
      {
        j = 1;
        localViewController = this.navigationController.removeController(i);
      }
    while (true)
    {
      if ((j == 0) || (localViewController == null))
        localViewController = createController(paramString);
      return localViewController;
      i++;
      break;
      localViewController = null;
      j = 0;
    }
  }

  public static ControllerManager getInstance()
  {
    if (_instance == null)
      _instance = new ControllerManager();
    return _instance;
  }

  private boolean openDefaultAlarm()
  {
    if (InfoManager.getInstance().root().mContentCategory == null)
      InfoManager.getInstance().initInfoTreeFromDB();
    openPlayController(54, 386, 0, true, "CNR中国之声");
    return true;
  }

  private void pushControllerByProperAnimation(ViewController paramViewController)
  {
    LinkManager.cancelLinkIfExists(this.mContext);
    this.navigationController.pushViewController(paramViewController, true, new MoveRtoLSwitchAnimation(), new MoveLtoRUncoverAnimation(), "");
  }

  private void pushSearchControllerSpecial(ViewController paramViewController)
  {
    LinkManager.cancelLinkIfExists(this.mContext);
    this.navigationController.pushViewController(paramViewController, true, new SearchSpecialEnterAnimation(), new SearchSpecialExitAnimation(), "");
  }

  public boolean dipatchEventToCurrentController(String paramString)
  {
    if ((this.navigationController == null) || (paramString == null))
      return false;
    if (paramString.equalsIgnoreCase("weibo_login_success"))
      WeiboChat.getInstance().getUserInfo();
    while (true)
    {
      ViewController localViewController = this.navigationController.getLastViewController();
      if (((!paramString.equalsIgnoreCase("weibo_login_success")) && (!paramString.equalsIgnoreCase("tencent_login_success"))) || (localViewController == null) || (!localViewController.controllerName.equalsIgnoreCase("setting")))
        break;
      localViewController.config("loginSuccess", null);
      return false;
      if (paramString.equalsIgnoreCase("tencent_login_success"))
        TencentChat.getInstance().getUserInfo();
    }
  }

  public int getChannelSource()
  {
    return this.mChannelSource;
  }

  public Context getContext()
  {
    return this.mContext;
  }

  public ViewController getControllerUnderneath()
  {
    int i = this.navigationController.getCount();
    if (i >= 2)
      return this.navigationController.getViewController(i - 2);
    return null;
  }

  public ViewController getFrontPageNewController()
  {
    ViewController localViewController = this.navigationController.getLastViewController();
    if ((localViewController != null) && (localViewController.controllerName.equalsIgnoreCase("frontpage")))
      return localViewController;
    return null;
  }

  public ViewController getLastViewController()
  {
    if (this.navigationController != null)
      return this.navigationController.getLastViewController();
    return null;
  }

  public boolean isActive(int paramInt, String paramString)
  {
    if (paramString == null)
      return false;
    ViewController localViewController = getLastViewController();
    if (localViewController == null)
      return false;
    if (localViewController.controllerName.equalsIgnoreCase("imchat"))
    {
      int i = ((Integer)((ImChatController)localViewController).getValue("getTalkingType", null)).intValue();
      String str = (String)((ImChatController)localViewController).getValue("getTalkingId", null);
      if ((str != null) && (i == paramInt) && (str.equalsIgnoreCase(paramString)))
        return true;
    }
    return false;
  }

  public boolean isTopController(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")) || (this.navigationController == null));
    ViewController localViewController;
    do
    {
      return false;
      localViewController = this.navigationController.getLastViewController();
    }
    while ((localViewController == null) || (!localViewController.controllerName.equalsIgnoreCase(paramString)));
    return true;
  }

  public void openADwebviewController(QTAdvertisementInfo paramQTAdvertisementInfo, QTCoupon paramQTCoupon, String paramString)
  {
    if ((paramQTAdvertisementInfo != null) && (paramQTCoupon == null));
  }

  public void openADwebviewController(String paramString)
  {
    GroupWebViewController localGroupWebViewController = new GroupWebViewController(getContext(), new GroupWebView(getContext(), paramString, true, false), null);
    localGroupWebViewController.controllerName = "qtadcontroller";
    pushControllerByProperAnimation(localGroupWebViewController);
  }

  public void openAboutQtController()
  {
    AboutQtController localAboutQtController = new AboutQtController(this.mContext);
    localAboutQtController.config("setData", null);
    pushControllerByProperAnimation(localAboutQtController);
  }

  public void openActivitiesView(Node paramNode)
  {
    if (paramNode == null)
      return;
    redirect2View("activities", paramNode);
  }

  public void openAlarmAddController(IEventHandler paramIEventHandler)
  {
    ViewController localViewController = getController("alarmsetting");
    localViewController.config("addalarm", null);
    localViewController.setEventHandler(paramIEventHandler);
    pushControllerByProperAnimation(localViewController);
  }

  public void openAlarmControllerListOrAdd()
  {
    List localList = InfoManager.getInstance().root().mPersonalCenterNode.alarmInfoNode.mLstAlarms;
    if ((localList == null) || (localList.size() == 0))
    {
      QTMSGManage.getInstance().sendStatistcsMessage("alarm_enter", "personel");
      AlarmSettingController localAlarmSettingController = new AlarmSettingController(getContext());
      localAlarmSettingController.config("addalarm", null);
      localAlarmSettingController.config("setDirect", null);
      pushControllerByProperAnimation(localAlarmSettingController);
      return;
    }
    QTMSGManage.getInstance().sendStatistcsMessage("clickAlarm");
    QTMSGManage.getInstance().sendStatistcsMessage("alarm_enter", "personel");
    AlarmListController localAlarmListController = new AlarmListController(getContext(), true);
    localAlarmListController.config("setData", null);
    pushControllerByProperAnimation(localAlarmListController);
  }

  public void openAlarmSettingController(AlarmInfo paramAlarmInfo, IEventHandler paramIEventHandler)
  {
    ViewController localViewController = getController("alarmsetting");
    localViewController.config("setData", paramAlarmInfo);
    localViewController.setEventHandler(paramIEventHandler);
    pushControllerByProperAnimation(localViewController);
  }

  public void openAudioQualitySettingController()
  {
    AudioQualitySettingController localAudioQualitySettingController = new AudioQualitySettingController(this.mContext);
    localAudioQualitySettingController.config("setData", null);
    pushControllerByProperAnimation(localAudioQualitySettingController);
  }

  public void openBlockedMembersController()
  {
    ViewController localViewController = getController("blockedmembers");
    localViewController.config("setData", null);
    pushControllerByProperAnimation(localViewController);
  }

  public void openCategoryListView(Node paramNode)
  {
    redirect2View("categorylist", paramNode);
  }

  public void openCategoryOrderManageController()
  {
    CategoryOrderManageController localCategoryOrderManageController = new CategoryOrderManageController(this.mContext);
    localCategoryOrderManageController.config("setData", null);
    pushControllerByProperAnimation(localCategoryOrderManageController);
  }

  public void openChannelDetailController(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString, boolean paramBoolean)
  {
    getInstance().setChannelSource(0);
    ChannelNode localChannelNode = ChannelHelper.getInstance().getChannel(paramInt2, paramInt4);
    if (localChannelNode == null)
    {
      if (paramInt4 != 1)
        break label76;
      localChannelNode = ChannelHelper.getInstance().getFakeVirtualChannel(paramInt2, paramInt1, paramString);
    }
    while (true)
    {
      redirect2View("channeldetail", localChannelNode);
      if (paramBoolean)
      {
        ProgramNode localProgramNode = localChannelNode.getProgramNode(paramInt3);
        if (localProgramNode != null)
          PlayerAgent.getInstance().play(localProgramNode);
      }
      return;
      label76: if (paramInt4 == 0)
        localChannelNode = ChannelHelper.getInstance().getFakeLiveChannel(paramInt2, paramInt1, paramString);
    }
  }

  public void openChannelDetailController(Node paramNode)
  {
    if (paramNode == null)
      return;
    openChannelDetailController(paramNode, false);
  }

  public void openChannelDetailController(Node paramNode, boolean paramBoolean)
  {
    if (paramNode == null)
      return;
    ChannelNode localChannelNode1;
    ChannelNode localChannelNode2;
    if (paramNode.nodeName.equalsIgnoreCase("program"))
      if (((ProgramNode)paramNode).mLiveInVirtual)
      {
        localChannelNode1 = ChannelHelper.getInstance().getChannel(((ProgramNode)paramNode).channelId, 1);
        if (localChannelNode1 != null)
          break label357;
        localChannelNode2 = ChannelHelper.getInstance().getFakeVirtualChannel(((ProgramNode)paramNode).channelId, ((ProgramNode)paramNode).getCategoryId(), ((ProgramNode)paramNode).title);
      }
    while (true)
    {
      label78: if (localChannelNode2.ratingStar == -1)
        localChannelNode2.ratingStar = ((ProgramNode)paramNode).channelRatingStar;
      if (InfoManager.getInstance().enableBarrage(((ProgramNode)paramNode).channelId))
        openDamakuPlayController();
      while (true)
      {
        if (!paramBoolean)
          break label271;
        if (!((ProgramNode)paramNode).mLiveInVirtual)
          break label273;
        if (((ProgramNode)paramNode).getCurrPlayStatus() == 2)
          break;
        PlayerAgent.getInstance().play(paramNode);
        return;
        localChannelNode1 = ChannelHelper.getInstance().getChannel(((ProgramNode)paramNode).channelId, ((ProgramNode)paramNode).channelType);
        if (localChannelNode1 != null)
          break label357;
        localChannelNode2 = ChannelHelper.getInstance().getFakeChannel(((ProgramNode)paramNode).channelId, ((ProgramNode)paramNode).getCategoryId(), ((ProgramNode)paramNode).title, ((ProgramNode)paramNode).channelType);
        break label78;
        String str2 = InfoManager.getInstance().h5Channel(localChannelNode2.channelId);
        if ((str2 != null) && (!str2.equalsIgnoreCase("")))
          redirectToActiviyByUrl(str2, localChannelNode2.title, false);
        else
          redirect2View("channeldetail", localChannelNode2);
      }
      label271: break;
      label273: PlayerAgent.getInstance().play(paramNode);
      return;
      if (!paramNode.nodeName.equalsIgnoreCase("channel"))
        break;
      String str1 = InfoManager.getInstance().h5Channel(((ChannelNode)paramNode).channelId);
      if ((str1 != null) && (!str1.equalsIgnoreCase("")))
        redirectToActiviyByUrl(str1, ((ChannelNode)paramNode).title, false);
      while (paramBoolean)
      {
        PlayerAgent.getInstance().play(paramNode);
        return;
        redirect2View("channeldetail", paramNode);
      }
      break;
      label357: localChannelNode2 = localChannelNode1;
    }
  }

  public void openChannelListByAttributeController(CategoryNode paramCategoryNode, Attribute paramAttribute)
  {
    ViewController localViewController = getController("virtualchannellist");
    localViewController.config("setNode", paramCategoryNode);
    localViewController.config("setData", paramAttribute);
    pushControllerByProperAnimation(localViewController);
  }

  public void openChatHistoryController(String paramString, List<ChatItem> paramList)
  {
    ChatHistoryController localChatHistoryController = new ChatHistoryController(this.mContext);
    localChatHistoryController.config("setTitle", paramString);
    localChatHistoryController.config("setData", paramList);
    pushControllerByProperAnimation(localChatHistoryController);
  }

  public void openChatRoom(String paramString1, String paramString2, String paramString3, Node paramNode, Object[] paramArrayOfObject)
  {
    Iterator localIterator = this.navigationController.getAllControllers().iterator();
    int i = 0;
    ChatRoomcontroller localChatRoomcontroller;
    if (localIterator.hasNext())
      if (((ViewController)localIterator.next()).controllerName.equalsIgnoreCase("chatroom"))
        localChatRoomcontroller = (ChatRoomcontroller)this.navigationController.removeController(i);
    for (int j = 1; ; j = 0)
    {
      if ((j == 0) || (localChatRoomcontroller == null))
        localChatRoomcontroller = new ChatRoomcontroller(this.mContext);
      if (paramString2 == null)
        paramString2 = "startRoom";
      localChatRoomcontroller.config(paramString2, paramNode);
      if ((paramString3 != null) && (paramArrayOfObject != null) && (paramArrayOfObject.length > 0))
        localChatRoomcontroller.config(paramString3, paramArrayOfObject[0]);
      pushControllerByProperAnimation(localChatRoomcontroller);
      if ((paramString1.equalsIgnoreCase("flower")) && (paramArrayOfObject != null) && (paramArrayOfObject.length > 0))
        localChatRoomcontroller.config("flower", paramArrayOfObject[0]);
      return;
      i++;
      break;
      localChatRoomcontroller = null;
    }
  }

  public void openChinaUnicomZone()
  {
    pushControllerByProperAnimation(new ChinaUnicomZoneController(this.mContext));
  }

  public void openControllerByBillboardItemNode(Node paramNode)
  {
    if ((paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("billboarditem")));
    BillboardItemNode localBillboardItemNode;
    do
    {
      do
      {
        return;
        localBillboardItemNode = (BillboardItemNode)paramNode;
        localBillboardItemNode.mClickCnt = (1 + localBillboardItemNode.mClickCnt);
      }
      while (localBillboardItemNode.mNode == null);
      if (localBillboardItemNode.mNode.nodeName.equalsIgnoreCase("channel"))
      {
        if (((ChannelNode)localBillboardItemNode.mNode).isNovelChannel())
        {
          getInstance().setChannelSource(0);
          openNovelDetailView(localBillboardItemNode.mNode);
          return;
        }
        if (((ChannelNode)localBillboardItemNode.mNode).channelType == 1)
        {
          getInstance().setChannelSource(0);
          openChannelDetailController(localBillboardItemNode.mNode);
          return;
        }
        redirectToPlayViewByNode(localBillboardItemNode.mNode, true);
        return;
      }
    }
    while (!localBillboardItemNode.mNode.nodeName.equalsIgnoreCase("program"));
    getInstance().setChannelSource(0);
    openChannelDetailController((ProgramNode)localBillboardItemNode.mNode, true);
  }

  public void openControllerByPlayingProgramNode(Node paramNode)
  {
    if ((paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("recommendplayingitem")))
      return;
    redirectToPlayViewByType(0, 0, ((RecommendPlayingItemNode)paramNode).channelId, 0, 0, true, true);
  }

  public void openControllerByRecommendNode(Node paramNode)
  {
    if ((paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("recommenditem")));
    RecommendItemNode localRecommendItemNode;
    do
    {
      return;
      localRecommendItemNode = (RecommendItemNode)paramNode;
      localRecommendItemNode.mClickCnt = (1 + localRecommendItemNode.mClickCnt);
    }
    while ((localRecommendItemNode.mNode == null) || (localRecommendItemNode.mNode.nodeName.equalsIgnoreCase("category")));
    if (localRecommendItemNode.mNode.nodeName.equalsIgnoreCase("channel"))
    {
      ChannelNode localChannelNode = (ChannelNode)localRecommendItemNode.mNode;
      if (localRecommendItemNode.ratingStar != -1)
        localChannelNode.ratingStar = localRecommendItemNode.ratingStar;
      if (localChannelNode.isNovelChannel())
      {
        getInstance().setChannelSource(1);
        openNovelDetailView(localRecommendItemNode.mNode);
        return;
      }
      if (localChannelNode.channelType == 1)
      {
        getInstance().setChannelSource(1);
        openChannelDetailController(localRecommendItemNode.mNode);
        return;
      }
      redirectToPlayViewByNode(localRecommendItemNode.mNode, true);
      return;
    }
    Node localNode;
    if (localRecommendItemNode.mNode.nodeName.equalsIgnoreCase("program"))
    {
      localNode = localRecommendItemNode.parent;
      if ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("recommendcategory")))
        break label377;
    }
    label377: for (boolean bool = ((RecommendCategoryNode)localNode).isFrontpage(); ; bool = false)
    {
      if (bool)
        if (localRecommendItemNode.categoryPos == 0)
          PlayerAgent.getInstance().addPlaySource(21);
      while (true)
      {
        getInstance().setChannelSource(1);
        openChannelDetailController((ProgramNode)localRecommendItemNode.mNode, true);
        return;
        PlayerAgent.getInstance().addPlaySource(22);
        continue;
        if (localRecommendItemNode.categoryPos == 0)
          PlayerAgent.getInstance().addPlaySource(25);
        else
          PlayerAgent.getInstance().addPlaySource(36);
      }
      if (localRecommendItemNode.mNode.nodeName.equalsIgnoreCase("activity"))
      {
        MobclickAgent.onEvent(getContext(), "openActivityFromRecommend", localRecommendItemNode.name);
        if ((localRecommendItemNode.isAds) && (localRecommendItemNode.mAdNode != null))
          localRecommendItemNode.mAdNode.onClick();
        redirectToActivityViewByNode(localRecommendItemNode.mNode);
        return;
      }
      if (!localRecommendItemNode.mNode.nodeName.equalsIgnoreCase("specialtopic"))
        break;
      openSpecialTopicController((SpecialTopicNode)localRecommendItemNode.mNode);
      return;
    }
  }

  public void openDamakuPlayController()
  {
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
    {
      int i = ((ProgramNode)localNode).id;
      QTMSGManage.getInstance().sendStatistcsMessage("danmaku_open", "" + i);
    }
    ViewController localViewController = getController("danmakumainplayview");
    localViewController.config("setData", null);
    pushControllerByProperAnimation(localViewController);
  }

  public void openDanmakuPlayListContoller(Drawable paramDrawable, List<ProgramNode> paramList)
  {
    ViewController localViewController = getController("danmakuplaylist");
    LinkManager.cancelLinkIfExists(this.mContext);
    localViewController.config("setBackground", paramDrawable);
    localViewController.config("setData", paramList);
    this.navigationController.pushViewController(localViewController, false);
  }

  public void openDanmakuSendController(Object paramObject)
  {
    ViewController localViewController = getController("danmakusend");
    localViewController.config("setData", paramObject);
    pushControllerByProperAnimation(localViewController);
  }

  public void openDaySettingController(int paramInt, IEventHandler paramIEventHandler)
  {
    AlarmDaySettingController localAlarmDaySettingController = new AlarmDaySettingController(getContext());
    localAlarmDaySettingController.config("day", Integer.valueOf(paramInt));
    localAlarmDaySettingController.setEventHandler(paramIEventHandler);
    pushControllerByProperAnimation(localAlarmDaySettingController);
  }

  public void openDiscoverCategoryController(int paramInt)
  {
    CategoryNode localCategoryNode = InfoManager.getInstance().root().mContentCategory.mVirtualNode.getCategoryNode(paramInt);
    if (localCategoryNode == null)
      return;
    pushControllerByProperAnimation(new DiscoverCategoryController(this.mContext, localCategoryNode));
  }

  public void openDownloadDirSettingController()
  {
    DownloadDirSettingController localDownloadDirSettingController = new DownloadDirSettingController(this.mContext);
    localDownloadDirSettingController.config("setData", null);
    pushControllerByProperAnimation(localDownloadDirSettingController);
  }

  public void openFaqController()
  {
    FaqController localFaqController = new FaqController(this.mContext);
    localFaqController.config("setData", null);
    pushControllerByProperAnimation(localFaqController);
  }

  public void openFeedBackController()
  {
    FeedbackController localFeedbackController = new FeedbackController(this.mContext);
    localFeedbackController.config("setData", null);
    pushControllerByProperAnimation(localFeedbackController);
  }

  public void openFeedbackContactInfoController()
  {
    pushControllerByProperAnimation(new ContactInfoController(this.mContext));
  }

  public void openHiddenFeatureController()
  {
    ViewController localViewController = getController("hiddenfeatures");
    localViewController.config("setData", null);
    pushControllerByProperAnimation(localViewController);
  }

  public void openImAddcontactController()
  {
    ViewController localViewController = getController("addcontact");
    localViewController.config("setData", null);
    pushControllerByProperAnimation(localViewController);
  }

  public void openImChatController(Object paramObject)
  {
    ViewController localViewController = getController("imchat");
    localViewController.config("setData", paramObject);
    pushControllerByProperAnimation(localViewController);
  }

  public void openImContactSpecificController(boolean paramBoolean)
  {
    ViewController localViewController = getController("contactspecific");
    localViewController.config("setData", Boolean.valueOf(paramBoolean));
    pushControllerByProperAnimation(localViewController);
  }

  public void openImContactsController()
  {
    ViewController localViewController = getController("contacts");
    localViewController.config("setData", null);
    pushControllerByProperAnimation(localViewController);
  }

  public void openImConversationsController()
  {
    ViewController localViewController = getController("conversations");
    localViewController.config("setData", null);
    pushControllerByProperAnimation(localViewController);
  }

  public void openImGroupMemberListController(List<UserInfo> paramList)
  {
    ViewController localViewController = getController("groupmemberlist");
    localViewController.config("setData", paramList);
    pushControllerByProperAnimation(localViewController);
  }

  public void openImGroupProfileController(String paramString)
  {
    ViewController localViewController = getController("groupprofile");
    localViewController.config("setData", paramString);
    pushControllerByProperAnimation(localViewController);
  }

  public void openImGroupSettingController(String paramString)
  {
    ViewController localViewController = getController("groupsetting");
    localViewController.config("setData", paramString);
    pushControllerByProperAnimation(localViewController);
  }

  public void openImReportController(IMMessage paramIMMessage)
  {
    ViewController localViewController = getController("report");
    localViewController.config("setData", paramIMMessage);
    pushControllerByProperAnimation(localViewController);
  }

  public void openImUserProfileController(String paramString)
  {
    if (paramString == null)
      return;
    ViewController localViewController = getController("userprofile");
    localViewController.config("setData", paramString);
    pushControllerByProperAnimation(localViewController);
  }

  public void openImUserSettingController(String paramString)
  {
    ViewController localViewController = getController("usersetting");
    localViewController.config("setData", paramString);
    pushControllerByProperAnimation(localViewController);
  }

  public void openJDShop(CommodityInfo paramCommodityInfo)
  {
    redirectToActiviyByUrl(paramCommodityInfo.getShopUrl(), paramCommodityInfo.getTitle(), true);
  }

  public void openLauncherController()
  {
    pushControllerByProperAnimation(new LauncherController(this.mContext));
  }

  public void openMyDownloadController(String paramString)
  {
    MyDownloadController localMyDownloadController = new MyDownloadController(this.mContext);
    localMyDownloadController.config("setData", null);
    pushControllerByProperAnimation(localMyDownloadController);
  }

  public void openMyGroupsController(List<GroupInfo> paramList)
  {
    ViewController localViewController = getController("mygroups");
    localViewController.config("setData", paramList);
    pushControllerByProperAnimation(localViewController);
  }

  public void openMyPodcasterController()
  {
    ViewController localViewController = getController("mypodcaster");
    localViewController.config("setData", null);
    pushControllerByProperAnimation(localViewController);
  }

  public void openNovelAllContentController(int paramInt)
  {
    int i = InfoManager.getInstance().root().getSecIdByCatId(paramInt);
    CategoryNode localCategoryNode = InfoManager.getInstance().root().mContentCategory.mVirtualNode.getCategoryNode(i);
    if (localCategoryNode == null)
      return;
    NovelAllContentController localNovelAllContentController = new NovelAllContentController(this.mContext);
    localNovelAllContentController.config("setNode", localCategoryNode);
    pushControllerByProperAnimation(localNovelAllContentController);
  }

  public void openNovelAllContentController(CategoryNode paramCategoryNode)
  {
    NovelAllContentController localNovelAllContentController = new NovelAllContentController(this.mContext);
    localNovelAllContentController.config("setNode", paramCategoryNode);
    pushControllerByProperAnimation(localNovelAllContentController);
  }

  public void openNovelDetailView(Node paramNode)
  {
    openChannelDetailController(paramNode);
  }

  public void openOnlineMemberController(String paramString, IEventHandler paramIEventHandler)
  {
    OnlineMembersController localOnlineMembersController = new OnlineMembersController(getContext());
    localOnlineMembersController.setEventHandler(paramIEventHandler);
    localOnlineMembersController.config("setData", paramString);
    pushControllerByProperAnimation(localOnlineMembersController);
  }

  public void openPlayController(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString, boolean paramBoolean)
  {
    if (paramInt3 == 0)
    {
      openPlayController(paramInt1, paramInt2, paramInt4, paramBoolean, null);
      return;
    }
    ChannelNode localChannelNode;
    if (paramInt1 == DownLoadInfoNode.mDownloadId)
    {
      localChannelNode = InfoManager.getInstance().root().mDownLoadInfoNode.getChannelNode(paramInt2);
      if (localChannelNode != null)
        break label129;
      if (paramInt4 != 1)
        break label109;
      localChannelNode = ChannelHelper.getInstance().getFakeVirtualChannel(paramInt2, paramInt1, paramString);
    }
    label129: 
    while (true)
    {
      if (localChannelNode != null)
        InfoManager.getInstance().root().setPlayingChannelNode(localChannelNode);
      openPlayController(paramBoolean, paramInt3);
      return;
      localChannelNode = ChannelHelper.getInstance().getChannel(paramInt2, paramInt4);
      break;
      label109: if (paramInt4 == 0)
      {
        localChannelNode = ChannelHelper.getInstance().getFakeLiveChannel(paramInt2, paramInt1, paramString);
        continue;
        if ((paramBoolean) && (localChannelNode.channelType == 0))
          PlayerAgent.getInstance().play(localChannelNode);
      }
    }
  }

  public void openPlayController(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    if (paramInt3 == 0)
    {
      openPlayController(paramInt1, paramInt2, paramInt4, paramBoolean, null);
      return;
    }
    ChannelNode localChannelNode;
    if (paramInt1 == DownLoadInfoNode.mDownloadId)
    {
      localChannelNode = InfoManager.getInstance().root().mDownLoadInfoNode.getChannelNode(paramInt2);
      if (localChannelNode == null)
      {
        if (paramInt4 != 1)
          break label133;
        localChannelNode = ChannelHelper.getInstance().getFakeVirtualChannel(paramInt2, paramInt1, null);
      }
    }
    while (true)
    {
      if (localChannelNode != null)
      {
        InfoManager.getInstance().root().setPlayingChannelNode(localChannelNode);
        if ((paramBoolean) && (localChannelNode.channelType == 0))
          PlayerAgent.getInstance().play(localChannelNode);
      }
      openPlayController(paramInt1, paramInt2, paramInt4, paramBoolean, null);
      return;
      localChannelNode = ChannelHelper.getInstance().getChannel(paramInt2, paramInt4);
      break;
      label133: if (paramInt4 == 0)
        localChannelNode = ChannelHelper.getInstance().getFakeLiveChannel(paramInt2, paramInt1, null);
    }
  }

  public void openPlayController(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, String paramString)
  {
    if (paramInt2 == 0)
    {
      openPlayController(false, 0);
      return;
    }
    ChannelNode localChannelNode;
    if (paramInt1 == DownLoadInfoNode.mDownloadId)
    {
      localChannelNode = InfoManager.getInstance().root().mDownLoadInfoNode.getChannelNode(paramInt2);
      if (localChannelNode == null)
      {
        if (paramInt3 != 1)
          break label144;
        localChannelNode = ChannelHelper.getInstance().getFakeVirtualChannel(paramInt2, paramInt1, paramString);
      }
    }
    while (true)
      if (localChannelNode != null)
      {
        InfoManager.getInstance().root().setPlayingChannelNode(localChannelNode);
        if ((paramBoolean) && (localChannelNode.channelType == 0))
        {
          if ((localChannelNode.getSourceUrl() != null) && (!localChannelNode.getSourceUrl().equalsIgnoreCase("")))
          {
            PlayerAgent.getInstance().play(localChannelNode);
            openPlayController(false, 0);
            return;
            localChannelNode = ChannelHelper.getInstance().getChannel(paramInt2, paramInt3);
            break;
            label144: if (paramInt3 != 0)
              continue;
            localChannelNode = ChannelHelper.getInstance().getFakeLiveChannel(paramInt1, paramInt2, paramString);
            continue;
          }
          openPlayController(true, 0);
          return;
        }
      }
    openPlayController(paramBoolean, 0);
  }

  public void openPlayController(ProgramNode paramProgramNode, boolean paramBoolean)
  {
    if (paramProgramNode == null)
    {
      openPlayController(false, 0);
      return;
    }
    ChannelNode localChannelNode1 = ChannelHelper.getInstance().getChannel(paramProgramNode);
    if (localChannelNode1 != null)
      InfoManager.getInstance().root().setPlayingChannelNode(localChannelNode1);
    while (true)
    {
      InfoManager.getInstance().root().setPlayingNode(paramProgramNode);
      openPlayController(paramBoolean, paramProgramNode.id);
      return;
      ChannelNode localChannelNode2 = ChannelHelper.getInstance().getFakeChannel(paramProgramNode.channelId, -1, paramProgramNode.title, paramProgramNode.channelType);
      if (localChannelNode2 != null)
        InfoManager.getInstance().root().setPlayingChannelNode(localChannelNode2);
    }
  }

  public void openPlayController(String paramString)
  {
    String str = "mainplayview";
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    if ((localChannelNode != null) && (!localChannelNode.isLiveChannel()) && (!localChannelNode.isDownloadChannel()) && (InfoManager.getInstance().enableBarrage(localChannelNode.channelId)))
    {
      str = "danmakumainplayview";
      QTMSGManage.getInstance().sendStatistcsMessage("danmaku_open", paramString);
    }
    ViewController localViewController = getController(str);
    localViewController.config("programid", paramString);
    pushControllerByProperAnimation(localViewController);
  }

  public void openPlayController(boolean paramBoolean, int paramInt)
  {
    String str = "mainplayview";
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    if ((localChannelNode != null) && (!localChannelNode.isLiveChannel()) && (!localChannelNode.isDownloadChannel()) && (InfoManager.getInstance().enableBarrage(localChannelNode.channelId)))
    {
      str = "danmakumainplayview";
      QTMSGManage.getInstance().sendStatistcsMessage("danmaku_open", "" + paramInt);
    }
    ViewController localViewController = getController(str);
    localViewController.config("setData", null);
    if (paramBoolean)
      localViewController.config("autoplay", Integer.valueOf(paramInt));
    pushControllerByProperAnimation(localViewController);
  }

  public void openPlayGameController()
  {
    if ((this.navigationController.getLastViewController() instanceof PlayGameController))
    {
      popLastController();
      return;
    }
    ViewController localViewController = getController("playgame");
    localViewController.config("setData", null);
    pushControllerByProperAnimation(localViewController);
  }

  public void openPlayHistoryController()
  {
    if ((this.navigationController.getLastViewController() instanceof PlayHistoryController))
    {
      popLastController();
      return;
    }
    ViewController localViewController = getController("playhistory");
    localViewController.config("setData", null);
    pushControllerByProperAnimation(localViewController);
  }

  public void openPlayListContoller(Drawable paramDrawable, List<ProgramNode> paramList)
  {
    ViewController localViewController = getController("playlist");
    LinkManager.cancelLinkIfExists(this.mContext);
    localViewController.config("setBackground", paramDrawable);
    localViewController.config("setData", paramList);
    this.navigationController.pushViewController(localViewController, false);
  }

  public boolean openPlayViewForAlarm(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return openDefaultAlarm();
    if (InfoManager.getInstance().root().mContentCategory == null)
      InfoManager.getInstance().initInfoTreeFromDB();
    openPlayController(paramInt1, paramInt2, paramInt3, paramInt4, "蜻蜓闹钟", true);
    return true;
  }

  public void openPodcasterInfoController(UserInfo paramUserInfo)
  {
    if (paramUserInfo != null)
    {
      String str1 = InfoManager.getInstance().h5Podcaster(paramUserInfo.podcasterId);
      if ((str1 != null) && (!str1.equalsIgnoreCase("")))
      {
        String str2 = paramUserInfo.podcasterName;
        if (str2.equalsIgnoreCase("加载中"))
          str2 = null;
        redirectToActiviyByUrl(str1, str2, false);
        return;
      }
    }
    ViewController localViewController = getController("podcasterinfo");
    localViewController.config("setData", paramUserInfo);
    pushControllerByProperAnimation(localViewController);
  }

  public void openPushMessageController()
  {
    PushMessageSettingController localPushMessageSettingController = new PushMessageSettingController(this.mContext);
    localPushMessageSettingController.config("setData", null);
    pushControllerByProperAnimation(localPushMessageSettingController);
  }

  public void openRadioChannelsController(Node paramNode)
  {
    if (paramNode == null)
      return;
    redirect2View("radiochannellist", paramNode);
  }

  public void openReserveController()
  {
    ViewController localViewController = getController("myreserve");
    localViewController.config("setData", null);
    pushControllerByProperAnimation(localViewController);
  }

  public void openRingChannelSettingController(AlarmInfo paramAlarmInfo, ChannelNode paramChannelNode, IEventHandler paramIEventHandler)
  {
    RingChannelPickController localRingChannelPickController = new RingChannelPickController(getContext());
    localRingChannelPickController.setEventHandler(paramIEventHandler);
    if (paramAlarmInfo != null)
      localRingChannelPickController.config("setRingtone", Integer.valueOf(paramAlarmInfo.channelId));
    if (paramChannelNode != null)
      localRingChannelPickController.config("setRingChannel", paramChannelNode);
    localRingChannelPickController.config("setData", null);
    pushControllerByProperAnimation(localRingChannelPickController);
  }

  public void openRingtoneSettingController(AlarmInfo paramAlarmInfo, IEventHandler paramIEventHandler)
  {
    AlarmDjRingtoneSettingController localAlarmDjRingtoneSettingController = new AlarmDjRingtoneSettingController(getContext());
    localAlarmDjRingtoneSettingController.setEventHandler(paramIEventHandler);
    if (paramAlarmInfo != null)
      localAlarmDjRingtoneSettingController.config("setRingtone", paramAlarmInfo.ringToneId);
    localAlarmDjRingtoneSettingController.config("setData", null);
    pushControllerByProperAnimation(localAlarmDjRingtoneSettingController);
  }

  public void openSettingController()
  {
    ViewController localViewController = getController("setting");
    localViewController.config("setData", null);
    pushControllerByProperAnimation(localViewController);
  }

  public void openSpecialTopicController(int paramInt)
  {
    PlayerAgent.getInstance().addPlaySource(41);
    String str = InfoManager.getInstance().h5SpecialTopic(paramInt);
    if ((str != null) && (!str.equalsIgnoreCase("")))
    {
      redirectToActiviyByUrl(str, null, false);
      return;
    }
    ViewController localViewController = getController("specialtopic");
    localViewController.config("setid", Integer.valueOf(paramInt));
    pushControllerByProperAnimation(localViewController);
  }

  public void openSpecialTopicController(SpecialTopicNode paramSpecialTopicNode)
  {
    PlayerAgent.getInstance().addPlaySource(41);
    if (paramSpecialTopicNode == null)
      return;
    String str = InfoManager.getInstance().h5SpecialTopic(paramSpecialTopicNode.getApiId());
    if ((str != null) && (!str.equalsIgnoreCase("")))
    {
      redirectToActiviyByUrl(str, paramSpecialTopicNode.title, false);
      return;
    }
    ViewController localViewController = getController("specialtopic");
    localViewController.config("setData", paramSpecialTopicNode);
    pushControllerByProperAnimation(localViewController);
  }

  public void openTimerSettingController()
  {
    ViewController localViewController = getController("timerSetting");
    localViewController.config("setData", null);
    pushControllerByProperAnimation(localViewController);
  }

  public void openTraScheduleController(Drawable paramDrawable, ChannelNode paramChannelNode, int paramInt)
  {
    ViewController localViewController = getController("traschedule");
    LinkManager.cancelLinkIfExists(this.mContext);
    localViewController.config("setBackground", paramDrawable);
    localViewController.config("initState", Integer.valueOf(paramInt));
    localViewController.config("setData", paramChannelNode);
    this.navigationController.pushViewController(localViewController, false);
  }

  public void openTraditionalChannelsView(int paramInt)
  {
    openTraditionalChannelsView(InfoManager.getInstance().root().mContentCategory.mLiveNode.getCategoryNode(paramInt));
  }

  public void openTraditionalChannelsView(Node paramNode)
  {
    if (paramNode == null)
      return;
    redirect2View("channellist", paramNode);
  }

  public void openUmengRecommendApp()
  {
  }

  public void openUploadVoiceController(ChannelNode paramChannelNode)
  {
    UploadVoiceController localUploadVoiceController = new UploadVoiceController(getContext());
    localUploadVoiceController.config("setData", paramChannelNode);
    if (localUploadVoiceController.performInit())
      pushControllerByProperAnimation(localUploadVoiceController);
  }

  public void openVirtualCategoryAllContentController(int paramInt)
  {
    CategoryNode localCategoryNode = InfoManager.getInstance().root().mContentCategory.mVirtualNode.getCategoryNode(paramInt);
    if (localCategoryNode == null)
      return;
    VirtualCategoryAllContentController localVirtualCategoryAllContentController = new VirtualCategoryAllContentController(this.mContext);
    localVirtualCategoryAllContentController.config("setNode", localCategoryNode);
    localVirtualCategoryAllContentController.config("setData", null);
    pushControllerByProperAnimation(localVirtualCategoryAllContentController);
  }

  public void openVirtualCategoryAllContentController(int paramInt1, int paramInt2)
  {
    int i = InfoManager.getInstance().root().getSecIdByCatId(paramInt1);
    CategoryNode localCategoryNode = InfoManager.getInstance().root().mContentCategory.mVirtualNode.getCategoryNode(i);
    VirtualCategoryAllContentController localVirtualCategoryAllContentController = new VirtualCategoryAllContentController(this.mContext);
    localVirtualCategoryAllContentController.config("setNode", localCategoryNode);
    localVirtualCategoryAllContentController.config("setId", Integer.valueOf(paramInt2));
    localVirtualCategoryAllContentController.config("setData", null);
    pushControllerByProperAnimation(localVirtualCategoryAllContentController);
  }

  public void openVirtualCategoryAllContentController(int paramInt, RecommendItemNode paramRecommendItemNode)
  {
    int i = InfoManager.getInstance().root().getSecIdByCatId(paramInt);
    CategoryNode localCategoryNode = InfoManager.getInstance().root().mContentCategory.mVirtualNode.getCategoryNode(i);
    VirtualCategoryAllContentController localVirtualCategoryAllContentController = new VirtualCategoryAllContentController(this.mContext);
    localVirtualCategoryAllContentController.config("setNode", localCategoryNode);
    localVirtualCategoryAllContentController.config("setData", paramRecommendItemNode);
    pushControllerByProperAnimation(localVirtualCategoryAllContentController);
  }

  public void openVirtualCategoryAllContentController(int paramInt, String paramString)
  {
    CategoryNode localCategoryNode = InfoManager.getInstance().root().mContentCategory.mVirtualNode.getCategoryNode(paramInt);
    if (localCategoryNode == null)
      break label21;
    while (true)
    {
      label21: return;
      if (paramString != null)
      {
        List localList = localCategoryNode.getLstAttributes(true);
        if (localList == null)
          break;
        for (int i = 0; i < localList.size(); i++)
          if (((Attributes)localList.get(i)).mLstAttribute != null)
            for (int j = 0; j < ((Attributes)localList.get(i)).mLstAttribute.size(); j++)
              if (paramString.contains(String.valueOf(((Attribute)((Attributes)localList.get(i)).mLstAttribute.get(j)).id)))
              {
                openChannelListByAttributeController(localCategoryNode, (Attribute)((Attributes)localList.get(i)).mLstAttribute.get(j));
                return;
              }
      }
    }
  }

  public void openVirtualCategoryAllContentController(CategoryNode paramCategoryNode)
  {
    if (InfoManager.getInstance().getRecommendCategoryBySecId(paramCategoryNode.sectionId) == null)
      return;
    VirtualCategoryAllContentController localVirtualCategoryAllContentController = new VirtualCategoryAllContentController(this.mContext);
    localVirtualCategoryAllContentController.config("setNode", paramCategoryNode);
    localVirtualCategoryAllContentController.config("setData", null);
    pushControllerByProperAnimation(localVirtualCategoryAllContentController);
  }

  public void openVirtualCategoryController(Node paramNode)
  {
    if (paramNode == null)
      return;
    ViewController localViewController = getController("virtualcategory");
    localViewController.config("setData", paramNode);
    pushControllerByProperAnimation(localViewController);
  }

  public void openWoController()
  {
    WoQtController localWoQtController = new WoQtController(this.mContext);
    localWoQtController.config("setData", null);
    pushControllerByProperAnimation(localWoQtController);
  }

  public void openWoFaqController()
  {
    pushControllerByProperAnimation(new WoFaqViewController(getContext()));
  }

  public boolean playedLastChannel()
  {
    return this.hasPlayedLastChannel;
  }

  public void popLastController()
  {
    if ((this.navigationController.getLastViewController() instanceof WoQtController))
    {
      if (((WoQtController)this.navigationController.getLastViewController()).isHome())
      {
        LinkManager.cancelLinkIfExists(this.mContext);
        this.navigationController.popViewController(true);
      }
      return;
    }
    if ((this.navigationController.getLastViewController() instanceof ChinaUnicomZoneController))
    {
      ((ChinaUnicomZoneController)this.navigationController.getLastViewController()).back();
      LinkManager.cancelLinkIfExists(this.mContext);
      this.navigationController.popViewController(true);
      return;
    }
    LinkManager.cancelLinkIfExists(this.mContext);
    this.navigationController.popViewController(true);
  }

  public void popLastControllerAndOpenParent()
  {
    int i = 0;
    int j;
    boolean bool;
    label57: Iterator localIterator;
    if ((InfoManager.getInstance().root().mPersonalCenterNode.alarmInfoNode.mLstAlarms == null) || (InfoManager.getInstance().root().mPersonalCenterNode.alarmInfoNode.mLstAlarms.size() == 0))
    {
      j = 1;
      Context localContext = getContext();
      if (j != 0)
        break label167;
      bool = true;
      AlarmListController localAlarmListController = new AlarmListController(localContext, bool);
      localAlarmListController.config("setData", null);
      this.navigationController.pushViewController(localAlarmListController, true, new MoveLtoRUncoverAnimation(), new MoveLtoRUncoverAnimation(), "showPersonal");
      localIterator = this.navigationController.getAllControllers().iterator();
    }
    while (true)
    {
      if (localIterator.hasNext())
      {
        if (((ViewController)localIterator.next()).controllerName.equalsIgnoreCase("alarmsetting"))
          this.navigationController.removeController(i).controllerDidPopped();
      }
      else
      {
        return;
        j = 0;
        break;
        label167: bool = false;
        break label57;
      }
      i++;
    }
  }

  public void popToRootController()
  {
    this.navigationController.popToRootViewController(true);
  }

  public void popToRootControllerUsingAnimation(ISwitchAnimation paramISwitchAnimation)
  {
    this.navigationController.popToRootViewControllerUsingAnimation(paramISwitchAnimation);
  }

  public void redirect2View(String paramString, Object paramObject)
  {
    try
    {
      ViewController localViewController = getController(paramString);
      localViewController.config("setData", paramObject);
      pushControllerByProperAnimation(localViewController);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void redirectPlayViewTimer()
  {
    openPlayController(false, 0);
  }

  public void redirectToActivityByGame(GameBean paramGameBean)
  {
    if (paramGameBean == null)
      return;
    ActivityNode localActivityNode = new ActivityNode();
    localActivityNode.id = 1;
    localActivityNode.name = paramGameBean.title;
    localActivityNode.type = "1";
    localActivityNode.updatetime = 25200;
    localActivityNode.infoUrl = null;
    localActivityNode.infoTitle = paramGameBean.title;
    localActivityNode.desc = paramGameBean.desc;
    localActivityNode.titleIconUrl = null;
    localActivityNode.network = null;
    localActivityNode.putUserInfo = false;
    localActivityNode.contentUrl = paramGameBean.url;
    localActivityNode.hasShared = paramGameBean.hasShared;
    pushControllerByProperAnimation(new GroupWebViewController(getContext(), new GroupWebView(getContext(), paramGameBean.url, false, true), localActivityNode));
  }

  public boolean redirectToActivityViewByNode(Node paramNode)
  {
    int i = 1;
    if (paramNode == null)
      i = 0;
    ActivityNode localActivityNode;
    do
    {
      return i;
      if (!paramNode.nodeName.equalsIgnoreCase("activity"))
        break;
      localActivityNode = (ActivityNode)paramNode;
      if ((localActivityNode.channelId != 0) && (localActivityNode.categoryId != 0) && (localActivityNode.channelId != 0) && (localActivityNode.categoryId != 0))
      {
        redirectToPlayView(localActivityNode.categoryId, localActivityNode.channelId, 0);
        return i;
      }
    }
    while (localActivityNode.categoryId != 0);
    if ((localActivityNode.useLocalWebview) && (localActivityNode.contentUrl != null))
    {
      Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(localActivityNode.contentUrl));
      InfoManager.getInstance().getContext().startActivity(localIntent);
      return i;
    }
    MobclickAgent.onEvent(this.mContext, "openActivity", localActivityNode.name);
    GroupWebViewController localGroupWebViewController;
    if (localActivityNode.contentUrl != null)
    {
      if ((localActivityNode.network != null) && (!localActivityNode.network.equalsIgnoreCase("all")) && (!localActivityNode.network.equalsIgnoreCase("")) && (this.mContext != null))
        Toast.makeText(this.mContext, "亲，该活动需要在" + localActivityNode.network + "网络下访问。", i).show();
      localGroupWebViewController = new GroupWebViewController(getContext(), new GroupWebView(getContext(), localActivityNode.contentUrl, localActivityNode.putUserInfo, false), localActivityNode);
      localGroupWebViewController.config("setTitle", localActivityNode.titleIconUrl);
    }
    while (true)
    {
      pushControllerByProperAnimation(localGroupWebViewController);
      return false;
      localGroupWebViewController = new GroupWebViewController(getContext(), new GroupWebView(getContext(), "http://qingting.fm", false, false), null);
      localGroupWebViewController.config("setTitle", localActivityNode.titleIconUrl);
    }
  }

  public void redirectToActiviyByUrl(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (paramString1 == null)
      return;
    ActivityNode localActivityNode = new ActivityNode();
    localActivityNode.id = 1;
    localActivityNode.name = "蜻蜓fm";
    if ((paramString2 != null) && (!paramString2.equalsIgnoreCase("")))
      localActivityNode.name = paramString2;
    localActivityNode.type = "1";
    localActivityNode.updatetime = 25200;
    localActivityNode.infoUrl = null;
    localActivityNode.infoTitle = "";
    localActivityNode.desc = "有声世界,无限精彩";
    localActivityNode.titleIconUrl = null;
    localActivityNode.network = null;
    localActivityNode.putUserInfo = false;
    localActivityNode.contentUrl = paramString1;
    localActivityNode.hasShared = paramBoolean;
    pushControllerByProperAnimation(new GroupWebViewController(getContext(), new GroupWebView(getContext(), paramString1, false, true), localActivityNode));
  }

  public void redirectToAddAlarmView(Node paramNode)
  {
    if (paramNode == null)
      return;
    QTMSGManage.getInstance().sendStatistcsMessage("alarm_enter", "3dots");
    AlarmSettingController localAlarmSettingController = new AlarmSettingController(getContext());
    if (paramNode.nodeName.equalsIgnoreCase("channel"))
      localAlarmSettingController.config("addalarmbyChannel", paramNode);
    while (true)
    {
      pushControllerByProperAnimation(localAlarmSettingController);
      return;
      if (!paramNode.nodeName.equalsIgnoreCase("program"))
        break;
      localAlarmSettingController.config("addalarmbyprogram", paramNode);
    }
  }

  public void redirectToAddAlarmViewByRemind()
  {
    ViewController localViewController = this.navigationController.getLastViewController();
    if ((localViewController != null) && (localViewController.controllerName.equalsIgnoreCase("alarmsetting")))
      return;
    Iterator localIterator = this.navigationController.getAllControllers().iterator();
    int i = 0;
    int j;
    AlarmSettingController localAlarmSettingController;
    if (localIterator.hasNext())
      if (((ViewController)localIterator.next()).controllerName.equalsIgnoreCase("alarmsetting"))
      {
        j = 1;
        localAlarmSettingController = (AlarmSettingController)this.navigationController.removeController(i);
      }
    while (true)
    {
      if ((j == 0) || (localAlarmSettingController == null))
        localAlarmSettingController = new AlarmSettingController(getContext());
      localAlarmSettingController.config("addalarm", null);
      pushControllerByProperAnimation(localAlarmSettingController);
      return;
      i++;
      break;
      localAlarmSettingController = null;
      j = 0;
    }
  }

  public void redirectToAddAlarmViewByRingtone(BroadcasterNode paramBroadcasterNode, Node paramNode)
  {
    if (paramBroadcasterNode == null)
      return;
    QTMSGManage.getInstance().sendStatistcsMessage("alarm_enter", "broadcaster");
    AlarmSettingController localAlarmSettingController = new AlarmSettingController(getContext());
    localAlarmSettingController.addAlarmByRingtone(paramBroadcasterNode, paramNode);
    pushControllerByProperAnimation(localAlarmSettingController);
  }

  public void redirectToBatchDownloadView(Node paramNode, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramNode == null);
    while (true)
    {
      return;
      if ((InfoManager.getInstance().forceLogin()) && (!CloudCenter.getInstance().isLogin()))
      {
        long l1 = SharedCfg.getInstance().getForceLogin();
        long l2 = DateUtil.getCurrentMillis();
        if (DateUtil.isDifferentDayMs(l1, l2))
        {
          EventDispacthManager.getInstance().dispatchAction("showLogin", null);
          SharedCfg.getInstance().setForceLogin(l2);
          return;
        }
      }
      ChannelNode localChannelNode;
      if (paramNode.nodeName.equalsIgnoreCase("channel"))
        localChannelNode = (ChannelNode)paramNode;
      while (localChannelNode != null)
      {
        if (localChannelNode.channelType == 0);
        for (String str = "batchdownload_tradition"; ; str = "batchdownload")
        {
          ViewController localViewController = getController(str);
          if (paramBoolean1)
            localViewController.config("checkNew", null);
          if (paramBoolean2)
            localViewController.config("checkNow", null);
          localViewController.config("setData", localChannelNode);
          pushControllerByProperAnimation(localViewController);
          return;
          if (!paramNode.nodeName.equalsIgnoreCase("program"))
            break label220;
          if ((paramNode.parent != null) && (paramNode.parent.nodeName.equalsIgnoreCase("channel")))
          {
            localChannelNode = (ChannelNode)paramNode.parent;
            break;
          }
          localChannelNode = InfoManager.getInstance().findChannelNodeByRecommendDetail(paramNode);
          break;
        }
        label220: localChannelNode = null;
      }
    }
  }

  public void redirectToDownloadProgramsController(ChannelNode paramChannelNode)
  {
    ViewController localViewController = getController("downloadprogram");
    localViewController.config("setData", paramChannelNode);
    pushControllerByProperAnimation(localViewController);
  }

  public void redirectToHistoryView()
  {
    ViewController localViewController1 = this.navigationController.getLastViewController();
    if ((localViewController1 != null) && (localViewController1.controllerName.equalsIgnoreCase("playhistory")))
      return;
    ViewController localViewController2 = getController("playhistory");
    localViewController2.config("setData", InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.getPlayHistoryNodes());
    pushControllerByProperAnimation(localViewController2);
  }

  public void redirectToLocalWebView(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (paramString1 == null)
      return;
    MobclickAgent.onEvent(InfoManager.getInstance().getContext(), "localwebview", paramString2);
    pushControllerByProperAnimation(new LocalWebViewController(getContext(), paramString1, paramString2, paramBoolean));
  }

  public void redirectToMyCollectionView()
  {
    ViewController localViewController1 = this.navigationController.getLastViewController();
    if ((localViewController1 != null) && (localViewController1.controllerName.equalsIgnoreCase("mycollection")))
      return;
    ViewController localViewController2 = getController("mycollection");
    localViewController2.config("setData", null);
    pushControllerByProperAnimation(localViewController2);
  }

  public void redirectToPlayView(int paramInt1, int paramInt2, int paramInt3)
  {
    openPlayController(paramInt1, paramInt2, 0, true, null);
  }

  public boolean redirectToPlayViewById(int paramInt1, int paramInt2, int paramInt3)
  {
    openPlayController(paramInt1, paramInt2, paramInt3, true, null);
    return false;
  }

  public boolean redirectToPlayViewById(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt3 == 0)
      return redirectToPlayViewById(paramInt1, paramInt2, paramInt4);
    if (InfoManager.getInstance().root().mContentCategory == null)
      InfoManager.getInstance().initInfoTreeFromDB();
    openPlayController(paramInt1, paramInt2, paramInt4, true, null);
    return true;
  }

  public boolean redirectToPlayViewById(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String paramString1, String paramString2)
  {
    if (paramInt3 == 0)
      return redirectToPlayViewById(paramInt1, paramInt2, paramInt4, paramInt5);
    if (InfoManager.getInstance().root().mContentCategory == null)
      InfoManager.getInstance().initInfoTreeFromDB();
    openPlayController(paramInt1, paramInt2, paramInt3, paramInt4, true);
    return true;
  }

  public boolean redirectToPlayViewById(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (InfoManager.getInstance().root().mContentCategory == null)
      InfoManager.getInstance().initInfoTreeFromDB();
    openPlayController(paramInt1, paramInt2, 0, true, null);
    return true;
  }

  public boolean redirectToPlayViewByNode()
  {
    openPlayController(false, 0);
    return true;
  }

  public boolean redirectToPlayViewByNode(Node paramNode, boolean paramBoolean)
  {
    int i = 1;
    if ((paramNode == null) || (this.mContext == null))
      i = 0;
    label352: ChannelNode localChannelNode1;
    do
    {
      Node localNode;
      do
      {
        do
        {
          return i;
          if (paramNode.nodeName.equalsIgnoreCase("activity"))
          {
            redirectToActivityViewByNode(paramNode);
            return i;
          }
          if (paramNode.nodeName.equalsIgnoreCase("radiochannel"))
          {
            if (paramBoolean)
              PlayerAgent.getInstance().startFM((RadioChannelNode)paramNode);
            ChannelNode localChannelNode3 = ChannelHelper.getInstance().getChannel(((RadioChannelNode)paramNode).channelId, 0);
            if (localChannelNode3 != null)
              InfoManager.getInstance().root().setPlayingChannelNode(localChannelNode3);
            openPlayController(false, 0);
            return i;
          }
          if (paramNode.nodeName.equalsIgnoreCase("channel"))
          {
            ChannelHelper.getInstance().setChannel((ChannelNode)paramNode, false);
            InfoManager.getInstance().root().setPlayingChannelNode((ChannelNode)paramNode);
            if (paramBoolean)
              PlayerAgent.getInstance().play((ChannelNode)paramNode);
            String str = InfoManager.getInstance().h5Channel(((ChannelNode)paramNode).channelId);
            if ((str != null) && (!str.equalsIgnoreCase("")))
            {
              redirectToActiviyByUrl(str, ((ChannelNode)paramNode).title, false);
              return i;
            }
            openPlayController(paramBoolean, 0);
            return i;
          }
          if (paramNode.nodeName.equalsIgnoreCase("program"))
          {
            if (paramBoolean)
              PlayerAgent.getInstance().play(paramNode);
            openPlayController((ProgramNode)paramNode, false);
            return i;
          }
        }
        while (!paramNode.nodeName.equalsIgnoreCase("playhistory"));
        localNode = ((PlayHistoryNode)paramNode).playNode;
        if (localNode == null)
          break;
        if (localNode.nodeName.equalsIgnoreCase("program"))
        {
          if (!((ProgramNode)localNode).isLiveProgram())
            break label352;
          if (paramBoolean)
          {
            ChannelNode localChannelNode2 = ChannelHelper.getInstance().getChannel(((ProgramNode)localNode).channelId, 0);
            PlayerAgent.getInstance().play(localChannelNode2);
          }
        }
        while (localNode.nodeName.equalsIgnoreCase("program"))
        {
          openPlayController((ProgramNode)localNode, false);
          return i;
          if (paramBoolean)
            PlayerAgent.getInstance().play(localNode);
        }
      }
      while (!localNode.nodeName.equalsIgnoreCase("channel"));
      ChannelHelper.getInstance().setChannel((ChannelNode)localNode, false);
      InfoManager.getInstance().root().setPlayingChannelNode((ChannelNode)localNode);
      openPlayController(false, 0);
      return i;
      localChannelNode1 = ChannelHelper.getInstance().getChannel(((PlayHistoryNode)paramNode).channelId, 0);
      if (localChannelNode1 == null)
      {
        localChannelNode1 = ChannelHelper.getInstance().getChannel(((PlayHistoryNode)paramNode).channelId, i);
        if (localChannelNode1 == null)
          localChannelNode1 = ChannelHelper.getInstance().getFakeVirtualChannel(((PlayHistoryNode)paramNode).channelId, ((PlayHistoryNode)paramNode).categoryId, ((PlayHistoryNode)paramNode).channelName);
      }
    }
    while (localChannelNode1 == null);
    openPlayController(localChannelNode1.categoryId, localChannelNode1.channelId, localChannelNode1.channelType, i, localChannelNode1.title);
    return i;
  }

  public boolean redirectToPlayViewByPlayList(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt3 == 0)
      return redirectToPlayViewById(paramInt1, paramInt2, paramInt4);
    if (InfoManager.getInstance().root().mContentCategory == null)
      InfoManager.getInstance().initInfoTreeFromDB();
    openPlayController(paramInt1, paramInt2, paramInt4, true, null);
    return true;
  }

  public boolean redirectToPlayViewByType(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (InfoManager.getInstance().root().mContentCategory == null)
      InfoManager.getInstance().initInfoTreeFromDB();
    if ((InfoManager.getInstance().root().mContentCategory == null) || ((paramInt4 == 0) && (InfoManager.getInstance().root().mContentCategory.mLiveNode == null)))
      return false;
    openPlayController(paramInt1, paramInt2, paramInt3, paramInt5, paramBoolean2);
    return false;
  }

  public boolean redirectToRecommendCategoryViewByCatid(int paramInt)
  {
    return false;
  }

  public boolean redirectToScheduleViewById(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, String paramString3)
  {
    return false;
  }

  public void redirectToSearchView(String paramString, boolean paramBoolean)
  {
    ViewController localViewController1 = this.navigationController.getLastViewController();
    if ((localViewController1 != null) && (localViewController1.controllerName.equalsIgnoreCase("search")))
    {
      localViewController1.config("setData", paramString);
      return;
    }
    ViewController localViewController2 = getController("search");
    localViewController2.config("setData", paramString);
    if (paramBoolean)
    {
      pushSearchControllerSpecial(localViewController2);
      return;
    }
    pushControllerByProperAnimation(localViewController2);
  }

  public void redirectToSearchView(boolean paramBoolean)
  {
    redirectToSearchView(null, paramBoolean);
  }

  public void redirectToUsersProfileView(UserInfo paramUserInfo)
  {
  }

  public boolean redirectToViewBySearchNode(SearchItemNode paramSearchItemNode)
  {
    if (paramSearchItemNode == null)
      return false;
    PlayerAgent.getInstance().addPlaySource(5);
    if (paramSearchItemNode.groupType == 1)
    {
      QTMSGManage.getInstance().sendStatistcsMessage("search_clickresult", "program");
      ChannelNode localChannelNode3 = ChannelHelper.getInstance().getChannel(paramSearchItemNode.channelId, paramSearchItemNode.channelType);
      if (localChannelNode3 == null)
        localChannelNode3 = ChannelHelper.getInstance().getFakeChannel(paramSearchItemNode.channelId, paramSearchItemNode.categoryId, paramSearchItemNode.cName, paramSearchItemNode.channelType);
      getInstance().setChannelSource(0);
      openChannelDetailController(localChannelNode3, false);
      PlayerAgent.getInstance().playAndLoadData(paramSearchItemNode.categoryId, paramSearchItemNode.channelId, paramSearchItemNode.programId, paramSearchItemNode.channelType, paramSearchItemNode.name);
    }
    while (true)
    {
      String str1 = QTLogger.getInstance().buildSearchedClickLog(paramSearchItemNode);
      if (str1 == null)
        break;
      LogModule.getInstance().send("search_click_v6", str1);
      return false;
      if (paramSearchItemNode.groupType == 0)
      {
        String str2 = InfoManager.getInstance().h5Channel(paramSearchItemNode.channelId);
        if ((str2 != null) && (!str2.equalsIgnoreCase("")))
        {
          String str3 = paramSearchItemNode.name;
          getInstance().redirectToActiviyByUrl(str2, str3, false);
          ChannelNode localChannelNode2 = ChannelHelper.getInstance().getChannel(paramSearchItemNode.channelId, 0);
          if (localChannelNode2 != null)
            PlayerAgent.getInstance().play(localChannelNode2);
        }
        while (true)
        {
          QTMSGManage.getInstance().sendStatistcsMessage("search_clickresult", "channel");
          break;
          openPlayController(paramSearchItemNode.categoryId, paramSearchItemNode.channelId, paramSearchItemNode.programId, paramSearchItemNode.channelType, paramSearchItemNode.name, true);
        }
      }
      if (paramSearchItemNode.groupType == 2)
      {
        ChannelNode localChannelNode1 = ChannelHelper.getInstance().getChannel(paramSearchItemNode.channelId, paramSearchItemNode.channelType);
        if (localChannelNode1 == null)
          localChannelNode1 = ChannelHelper.getInstance().getFakeChannel(paramSearchItemNode.channelId, paramSearchItemNode.categoryId, paramSearchItemNode.cName, paramSearchItemNode.channelType);
        getInstance().setChannelSource(0);
        openChannelDetailController(localChannelNode1, false);
        QTMSGManage.getInstance().sendStatistcsMessage("search_clickresult", "channel");
      }
      else if (paramSearchItemNode.groupType == 3)
      {
        openPodcasterInfoController(PodcasterHelper.getInstance().getPodcaster(paramSearchItemNode.podcasterId));
        QTMSGManage.getInstance().sendStatistcsMessage("search_clickresult", "podcaster");
      }
    }
  }

  public void redirectToWoQtView()
  {
    ViewController localViewController = this.navigationController.getLastViewController();
    if ((localViewController != null) && (localViewController.controllerName.equalsIgnoreCase("wo")))
      return;
    Iterator localIterator = this.navigationController.getAllControllers().iterator();
    int i = 0;
    int j;
    WoQtController localWoQtController;
    if (localIterator.hasNext())
      if (((ViewController)localIterator.next()).controllerName.equalsIgnoreCase("wo"))
      {
        j = 1;
        localWoQtController = (WoQtController)this.navigationController.removeController(i);
      }
    while (true)
    {
      if ((j == 0) || (localWoQtController == null))
        localWoQtController = new WoQtController(getContext());
      localWoQtController.config("setData", null);
      pushControllerByProperAnimation(localWoQtController);
      return;
      i++;
      break;
      localWoQtController = null;
      j = 0;
    }
  }

  public void setChannelSource(int paramInt)
  {
    this.mChannelSource = paramInt;
  }

  public void setContext(Context paramContext)
  {
    this.mContext = paramContext;
    if (this.mContext != null);
  }

  public void setNavigationController(NavigationController paramNavigationController)
  {
    this.navigationController = paramNavigationController;
  }

  public void updateRecentPlayOnChannelDetail(String paramString)
  {
    ((ChannelDetailController)getController("channeldetail")).config(paramString, null);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.ControllerManager
 * JD-Core Version:    0.6.2
 */