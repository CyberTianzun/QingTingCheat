package fm.qingting.qtradio.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.controller.INavigationEventListener;
import fm.qingting.framework.controller.NavigationController;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.manager.EventDispacthManager.IActionEventHandler;
import fm.qingting.framework.utils.ImageLoader;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.controller.FrontPageController;
import fm.qingting.qtradio.controller.virtual.UploadVoiceController;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.helper.OnlineUpdateHelper;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.manager.CollectionRemindManager;
import fm.qingting.qtradio.manager.EducationManager;
import fm.qingting.qtradio.manager.LinkManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.manager.advertisement.AdvertisementManage;
import fm.qingting.qtradio.manager.advertisement.UMengLogger;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.ContentCategoryNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.LiveNode;
import fm.qingting.qtradio.model.NavigationSettingController;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.PlayHistoryInfoNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RadioNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.model.SpecialTopicNode;
import fm.qingting.qtradio.model.advertisement.QTCoupon;
import fm.qingting.qtradio.share.ShareUtil;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.social.CloudCenter.OnLoginEventListerner;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.qtradio.tencentAgent.TencentAgent;
import fm.qingting.qtradio.view.education.shareguide.ShareGuideView;
import fm.qingting.qtradio.view.popviews.AlertParam.Builder;
import fm.qingting.qtradio.view.popviews.AlertParam.OnButtonClickListener;
import fm.qingting.qtradio.weiboAgent.WeiboAgent;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.RecommendStatisticsUtil;
import fm.qingting.utils.ScreenType;
import java.util.List;

public class MainView extends ViewGroupViewImpl
  implements IEventHandler, EventDispacthManager.IActionEventHandler, INavigationEventListener
{
  private static final int BUBBLE_SCALE_BOTTOMGAP = 2;
  private static final int BUBBLE_SCALE_FILL = 0;
  private static final int BUBBLE_SCALE_TOPGAP = 1;
  public static boolean sDoubleBackQuit = false;
  private final ViewLayout bottomLayout = this.standardLayout.createChildLT(720, 110, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private FrameLayout container;
  private int mBubbleScaleType = 0;
  private QtBubbleView mBubbleView;
  private long mLastBackTime = 0L;
  private String mLastViewName = "";
  private FrontPageController mRootController;
  private ShareGuideView mShareGuideView;
  private Object mShareObject;
  private boolean mShowingShare = false;
  private final ViewLayout naviLayout = this.standardLayout.createChildLT(720, 114, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private NavigationController navigationController;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private int viewHeight = 1200;
  private int viewWidth = 720;

  public MainView(Context paramContext)
  {
    super(paramContext);
    EventDispacthManager.getInstance().addListener(this);
    buildViews();
    OnlineUpdateHelper.getInstance();
    List localList = InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.getPlayHistoryNodes();
    if ((localList != null) && (localList.size() > 1) && (InfoManager.getInstance().getTopHistory()))
      EventDispacthManager.getInstance().dispatchAction("topPlayHistory", null);
  }

  private void buildViews()
  {
    this.navigationController = new NavigationController(getContext());
    this.navigationController.setNavigationSetting(new NavigationSettingController());
    this.navigationController.setNavigationEventListener(this);
    this.container = this.navigationController.getViewContainer();
    addView(this.container);
    this.mRootController = new FrontPageController(getContext());
    this.mRootController.config("setData", null);
    this.navigationController.setRootController(this.mRootController, null);
    ControllerManager.getInstance().setContext(getContext());
    ControllerManager.getInstance().setNavigationController(this.navigationController);
    if (InfoManager.getInstance().enableRecommendShare())
    {
      this.mShareGuideView = new ShareGuideView(getContext());
      this.mShareGuideView.setEventHandler(this);
      addView(this.mShareGuideView);
      this.mShowingShare = true;
      MobclickAgent.onEvent(getContext(), "showShare", "addShare");
    }
    this.mBubbleView = new QtBubbleView(getContext());
    this.mBubbleView.setEventHandler(this);
    addView(this.mBubbleView);
    this.mBubbleView.setVisibility(8);
    EducationManager.getInstance().init(getContext());
    sDoubleBackQuit = SharedCfg.getInstance().getDoubleBackToQuit();
  }

  private void cancelBubble()
  {
    cancelBubble(true);
  }

  @TargetApi(11)
  private void cancelBubble(boolean paramBoolean)
  {
    EventDispacthManager.getInstance().dispatchAction("hideMiniplayerTrangle", null);
    if ((paramBoolean) && (this.mBubbleView.isAnimationAvailable()))
    {
      startDimBackAnimation();
      this.mBubbleView.hide();
    }
    while (true)
    {
      InfoManager.getInstance().setBindRecommendShare(false);
      return;
      this.mBubbleView.clearAnimation();
      this.mBubbleView.onViewHidden();
      this.mBubbleView.setVisibility(8);
      switch (this.mBubbleView.getBubbleType())
      {
      default:
        break;
      case 7:
        changePlayViewState(true);
        break;
      case 13:
        radiocastMessage("qt_action_stopservice");
        break;
      case 18:
        resetFilterStateIfNeed();
      }
    }
  }

  private void changePlayViewState(boolean paramBoolean)
  {
  }

  private int getBubbleHeight()
  {
    int i;
    if (this.mBubbleScaleType == 1)
    {
      i = this.naviLayout.height;
      if (this.mBubbleScaleType != 2)
        break label49;
    }
    label49: for (int j = this.standardLayout.height - this.bottomLayout.height; ; j = this.standardLayout.height)
    {
      return j - i;
      i = 0;
      break;
    }
  }

  private void handleBackAction()
  {
    if (isBubbleShowing())
    {
      cancelBubble();
      return;
    }
    boolean bool = ((Boolean)getValue("isRoot", null)).booleanValue();
    String str = (String)getValue("topControllerName", null);
    if (bool)
    {
      if (sDoubleBackQuit)
      {
        long l = SystemClock.uptimeMillis();
        if (l - this.mLastBackTime < 3000L)
        {
          dispatchActionEvent("immediateQuit", null);
          return;
        }
        Toast.makeText(getContext(), "再按一次退出", 0).show();
        this.mLastBackTime = l;
        return;
      }
      dispatchActionEvent("showQuitAlert", null);
      return;
    }
    if ((str != null) && (str.equalsIgnoreCase("uploadvoice")))
    {
      ((UploadVoiceController)this.navigationController.getLastViewController()).performStop(true);
      return;
    }
    update("performPop", null);
  }

  private void handleOnPause()
  {
    String str = (String)getValue("topControllerName", null);
    if ((str != null) && (str.equalsIgnoreCase("uploadvoice")))
      ((UploadVoiceController)this.navigationController.getLastViewController()).performStop(false);
  }

  private boolean isBubbleShowing()
  {
    return this.mBubbleView.isShown();
  }

  private void layoutBubble(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      requestLayout();
      return;
    }
    int i;
    if (this.mBubbleScaleType == 1)
    {
      i = this.naviLayout.height;
      if (this.mBubbleScaleType != 2)
        break label69;
    }
    label69: for (int j = this.standardLayout.height - this.bottomLayout.height; ; j = this.standardLayout.height)
    {
      this.mBubbleView.layout(0, i, this.viewWidth, j);
      return;
      i = 0;
      break;
    }
  }

  private void markPageChanged(ViewController paramViewController)
  {
    if (paramViewController != null)
    {
      if (!this.mLastViewName.equalsIgnoreCase(""))
        MobclickAgent.onPageEnd(this.mLastViewName);
      this.mLastViewName = paramViewController.controllerName;
      MobclickAgent.onPageStart(this.mLastViewName);
    }
  }

  private void popController()
  {
    ControllerManager.getInstance().popLastController();
  }

  private void radiocastMessage(String paramString)
  {
    if (getContext() != null)
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("qtactionkey", paramString);
      localIntent.setAction("fm.qingting.radio.qt_ui_toservice");
      getContext().sendBroadcast(localIntent);
    }
  }

  private void redirectToGroupChat(GroupInfo paramGroupInfo)
  {
    if (CloudCenter.getInstance().isLogin())
    {
      InfoManager.getInstance().getUserProfile().followGroup(paramGroupInfo.groupId);
      ControllerManager.getInstance().openImChatController(paramGroupInfo);
      return;
    }
    ControllerManager.getInstance().openImGroupProfileController(paramGroupInfo.groupId);
  }

  private void resetFilterStateIfNeed()
  {
    ViewController localViewController = ControllerManager.getInstance().getLastViewController();
    if ((localViewController != null) && (localViewController.controllerName.equalsIgnoreCase("vcacc")))
      localViewController.config("resetFilterState", null);
  }

  private void setFlagOnTopViewChanged(ViewController paramViewController)
  {
    if (paramViewController == null);
    while (true)
    {
      return;
      if (paramViewController.controllerName.equalsIgnoreCase("frontpage"))
      {
        Object localObject = paramViewController.getValue("currentIndex", null);
        if (localObject != null)
          if (((Integer)localObject).intValue() == 0)
            RecommendStatisticsUtil.INSTANCE.resume();
      }
      while (EducationManager.getInstance().isShown())
      {
        EducationManager.getInstance().cancelTip();
        return;
        RecommendStatisticsUtil.INSTANCE.pause();
        continue;
        RecommendStatisticsUtil.INSTANCE.pause();
        continue;
        RecommendStatisticsUtil.INSTANCE.pause();
      }
    }
  }

  private void setFlagOnTopViewChanged(boolean paramBoolean)
  {
    ViewController localViewController = this.navigationController.getLastViewController();
    if (localViewController == null);
    do
    {
      return;
      if (localViewController.controllerName.equalsIgnoreCase("frontpage"))
      {
        Object localObject = localViewController.getValue("currentIndex", null);
        if (localObject != null)
          if (((Integer)localObject).intValue() == 0)
            RecommendStatisticsUtil.INSTANCE.resume();
        while (true)
        {
          localViewController.config("refreshView", null);
          return;
          RecommendStatisticsUtil.INSTANCE.pause();
          continue;
          RecommendStatisticsUtil.INSTANCE.pause();
        }
      }
    }
    while ((!localViewController.controllerName.equalsIgnoreCase("channeldetail")) || (!paramBoolean));
    localViewController.config("syncdata", null);
  }

  private void share(int paramInt)
  {
    ShareUtil.shareToPlatform(getContext(), (Node)this.mShareObject, paramInt);
  }

  private void shareSdkShare(Object paramObject)
  {
    this.mShareObject = paramObject;
    String str;
    if ((this.mShareObject instanceof ChannelNode))
      str = ((ChannelNode)this.mShareObject).getApproximativeThumb();
    while (true)
    {
      if (str != null)
        ImageLoader.getInstance(getContext()).getImage(str, 200, 200);
      showBubble(4, null);
      MobclickAgent.onEvent(getContext(), "SharePopView");
      return;
      if ((this.mShareObject instanceof ProgramNode))
      {
        ChannelNode localChannelNode = ChannelHelper.getInstance().getChannel((ProgramNode)this.mShareObject);
        if (localChannelNode != null)
          str = localChannelNode.getApproximativeThumb();
      }
      else if ((this.mShareObject instanceof SpecialTopicNode))
      {
        str = ((SpecialTopicNode)this.mShareObject).thumb;
      }
      else if ((this.mShareObject instanceof ActivityNode))
      {
        str = ((ActivityNode)this.mShareObject).infoUrl;
      }
      else
      {
        str = null;
      }
    }
  }

  private void showAlert(String paramString)
  {
    if (paramString == null)
      return;
    update("showAlert", new AlertParam.Builder().setMessage(paramString).addButton("继续播放").addButton("停止播放").addForbidBox().setListener(new AlertParam.OnButtonClickListener()
    {
      public void onClick(int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        switch (paramAnonymousInt)
        {
        default:
        case 0:
        case 1:
        }
        do
        {
          do
          {
            return;
            MainView.this.update("cancelBubble", null);
            PlayerAgent.getInstance().play();
          }
          while (!paramAnonymousBoolean);
          InfoManager.getInstance().setMobileAlert(false);
          InfoManager.getInstance().setMobilePlay(true);
          return;
          MainView.this.update("cancelBubble", null);
          PlayerAgent.getInstance().stop();
        }
        while (!paramAnonymousBoolean);
        InfoManager.getInstance().setMobileAlert(false);
        InfoManager.getInstance().setMobilePlay(false);
      }
    }).create());
  }

  private void showBubble(int paramInt, Object paramObject)
  {
    LinkManager.cancelLinkIfExists(getContext());
    this.mBubbleView.update("dimin", null);
    int i;
    switch (paramInt)
    {
    default:
      i = 0;
    case 7:
    case 24:
    case 18:
    }
    while (true)
    {
      if (i != this.mBubbleScaleType)
      {
        this.mBubbleScaleType = i;
        layoutBubble(true);
      }
      this.mBubbleView.setViewByType(paramInt, paramObject);
      this.mBubbleView.setVisibility(0);
      return;
      i = 2;
      changePlayViewState(false);
      continue;
      i = 1;
    }
  }

  private void showDownloadAlert(String paramString)
  {
    if (paramString == null)
      return;
    update("showAlert", new AlertParam.Builder().setMessage(paramString).addButton("设置").addButton("下次再说").setListener(new AlertParam.OnButtonClickListener()
    {
      public void onClick(int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        switch (paramAnonymousInt)
        {
        default:
          return;
        case 0:
          MainView.this.update("cancelBubble", null);
          ControllerManager.getInstance().openSettingController();
          return;
        case 1:
        }
        MainView.this.update("cancelBubble", null);
      }
    }).create());
  }

  private void showSchedule()
  {
    ChannelNode localChannelNode1 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    if (localChannelNode1 == null);
    while (!localChannelNode1.nodeName.equalsIgnoreCase("channel"))
      return;
    ChannelNode localChannelNode2 = (ChannelNode)localChannelNode1;
    if (localChannelNode2.hasEmptyProgramSchedule())
    {
      Toast.makeText(getContext(), "节目单正在加载中", 0).show();
      return;
    }
    showBubble(7, localChannelNode2);
  }

  private void startDimBackAnimation()
  {
    this.mBubbleView.update("dimout", null);
  }

  public void destroy()
  {
    if (this.navigationController != null)
    {
      this.navigationController.destroy();
      this.navigationController = null;
    }
    if (this.mRootController != null)
    {
      this.mRootController.destroy();
      this.mRootController = null;
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getKeyCode();
    if (this.navigationController.getLastViewController().getView().getView().dispatchKeyEvent(paramKeyEvent))
      return true;
    if ((i == 24) || (i == 25))
      return false;
    if ((i == 82) && (paramKeyEvent.getAction() == 1))
    {
      ((InputMethodManager)getContext().getSystemService("input_method")).hideSoftInputFromWindow(getApplicationWindowToken(), 0);
      if (this.mShowingShare)
        return true;
      if (isBubbleShowing())
        cancelBubble();
      while (true)
      {
        return true;
        if (EducationManager.getInstance().isShown())
          EducationManager.getInstance().cancelTip();
        showBubble(8, null);
      }
    }
    if (i == 4)
    {
      if (paramKeyEvent.getAction() == 1)
      {
        if (EducationManager.getInstance().isShown())
          EducationManager.getInstance().cancelTip();
        handleBackAction();
        return true;
      }
    }
    else if (i == 84)
      return true;
    return false;
  }

  public boolean dispatchKeyEventPreIme(KeyEvent paramKeyEvent)
  {
    return super.dispatchKeyEventPreIme(paramKeyEvent);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("isRoot"))
    {
      if (this.navigationController.getLastViewController() == this.mRootController);
      for (boolean bool = true; ; bool = false)
        return Boolean.valueOf(bool);
    }
    if ((paramString.equalsIgnoreCase("topControllerName")) && (this.navigationController.getLastViewController() != null))
      return this.navigationController.getLastViewController().controllerName;
    return null;
  }

  public void onAction(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("voice_view"))
      showBubble(15, null);
    do
    {
      do
      {
        ViewController localViewController1;
        do
        {
          RecommendItemNode localRecommendItemNode;
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                    {
                      while (true)
                      {
                        return;
                        if (paramString.equalsIgnoreCase("scanFm"))
                        {
                          showBubble(11, null);
                          return;
                        }
                        if (paramString.equalsIgnoreCase("scanUpdate"))
                        {
                          if ((isBubbleShowing()) && (this.mBubbleView.getBubbleType() == 11))
                            showBubble(11, paramObject);
                        }
                        else
                        {
                          if (paramString.equalsIgnoreCase("scanCancel"))
                          {
                            cancelBubble();
                            return;
                          }
                          if (paramString.equalsIgnoreCase("showFeedbackPop"))
                          {
                            showBubble(10, paramObject);
                            return;
                          }
                          if (paramString.equalsIgnoreCase("showRingtoneLoadingView"))
                          {
                            showBubble(9, null);
                            return;
                          }
                          if (paramString.equalsIgnoreCase("ringtoneLoadComplete"))
                          {
                            showBubble(9, Boolean.valueOf(true));
                            return;
                          }
                          if (paramString.equalsIgnoreCase("ringtoneLoadFailed"))
                          {
                            showBubble(9, Boolean.valueOf(false));
                            return;
                          }
                          if (paramString.equalsIgnoreCase("showAlarmRemind"))
                          {
                            showBubble(5, new AlertParam.Builder().addButton("不再提醒").addButton("设定闹钟").setMessage("设定自己喜欢的广播作为闹钟，在熟悉的声音中醒来。").setListener(new AlertParam.OnButtonClickListener()
                            {
                              public void onClick(int paramAnonymousInt, boolean paramAnonymousBoolean)
                              {
                                switch (paramAnonymousInt)
                                {
                                default:
                                  return;
                                case 0:
                                  MainView.this.cancelBubble();
                                  return;
                                case 1:
                                }
                                MainView.this.cancelBubble();
                                ControllerManager.getInstance().redirectToAddAlarmViewByRemind();
                              }
                            }).create());
                            return;
                          }
                          if (paramString.equalsIgnoreCase("showGroupRemind"))
                          {
                            final GroupInfo localGroupInfo = (GroupInfo)paramObject;
                            if (localGroupInfo != null)
                              showBubble(5, new AlertParam.Builder().addButton("下次再说").addButton("加入群聊").setMessage(InfoManager.getInstance().getAddGroupSlogan()).setListener(new AlertParam.OnButtonClickListener()
                              {
                                public void onClick(int paramAnonymousInt, boolean paramAnonymousBoolean)
                                {
                                  switch (paramAnonymousInt)
                                  {
                                  default:
                                    return;
                                  case 0:
                                    MainView.this.cancelBubble();
                                    return;
                                  case 1:
                                  }
                                  MainView.this.cancelBubble();
                                  MainView.this.redirectToGroupChat(localGroupInfo);
                                }
                              }).create());
                          }
                          else
                          {
                            if (paramString.equalsIgnoreCase("showWoQtAlert"))
                            {
                              showBubble(5, new AlertParam.Builder().addButton("取消").addButton("去绑定").setMessage("如果您曾开通过蜻蜓FM-包流量畅听，如您想要继续使用，需要您重新绑定手机号码。").setListener(new AlertParam.OnButtonClickListener()
                              {
                                public void onClick(int paramAnonymousInt, boolean paramAnonymousBoolean)
                                {
                                  switch (paramAnonymousInt)
                                  {
                                  default:
                                    return;
                                  case 0:
                                    MainView.this.cancelBubble();
                                    return;
                                  case 1:
                                  }
                                  MainView.this.cancelBubble();
                                  ControllerManager.getInstance().redirectToWoQtView();
                                }
                              }).create());
                              return;
                            }
                            if (paramString.equalsIgnoreCase("showLogin"))
                            {
                              showBubble(12, paramObject);
                              return;
                            }
                            if (paramString.equalsIgnoreCase("showSchedule"))
                            {
                              if ((isBubbleShowing()) && ((this.mBubbleView.getBubbleType() == 7) || (this.mBubbleView.getBubbleType() == 24)))
                              {
                                cancelBubble();
                                return;
                              }
                              showSchedule();
                              return;
                            }
                            if (paramString.equalsIgnoreCase("toPlayView"))
                            {
                              if ((isBubbleShowing()) && ((this.mBubbleView.getBubbleType() == 7) || (this.mBubbleView.getBubbleType() == 24)))
                              {
                                cancelBubble();
                                return;
                              }
                              ControllerManager.getInstance().redirectToPlayViewByNode();
                              return;
                            }
                            if (paramString.equalsIgnoreCase("hideSchedule"))
                            {
                              if ((isBubbleShowing()) && ((this.mBubbleView.getBubbleType() == 7) || (this.mBubbleView.getBubbleType() == 24)))
                                cancelBubble();
                            }
                            else
                            {
                              if (paramString.equalsIgnoreCase("showoperatepop"))
                              {
                                showBubble(6, paramObject);
                                return;
                              }
                              if (paramString.equalsIgnoreCase("shareChoose"))
                              {
                                shareSdkShare(paramObject);
                                return;
                              }
                              if (paramString.equalsIgnoreCase("shareToPlatform"))
                              {
                                cancelBubble();
                                share(((Integer)paramObject).intValue());
                                return;
                              }
                              if (paramString.equalsIgnoreCase("showToast"))
                              {
                                if (paramObject != null)
                                {
                                  String str5 = (String)paramObject;
                                  try
                                  {
                                    Toast.makeText(getContext(), str5, 0).show();
                                    return;
                                  }
                                  catch (OutOfMemoryError localOutOfMemoryError)
                                  {
                                    localOutOfMemoryError.printStackTrace();
                                    return;
                                  }
                                }
                              }
                              else
                              {
                                if (paramString.equalsIgnoreCase("refreshUploadView"))
                                {
                                  String str4 = (String)paramObject;
                                  if ((str4 != null) && (str4.length() > 0))
                                    Toast.makeText(getContext(), str4, 0).show();
                                  this.navigationController.getLastViewController().config("refreshUploadView", null);
                                  return;
                                }
                                if (paramString.equalsIgnoreCase("alertSetting"))
                                {
                                  if (paramObject != null)
                                    showAlert((String)paramObject);
                                }
                                else if (paramString.equalsIgnoreCase("alertSettingdownload"))
                                {
                                  if (paramObject != null)
                                    showDownloadAlert((String)paramObject);
                                }
                                else
                                {
                                  if (paramString.equalsIgnoreCase("cancelPop"))
                                  {
                                    cancelBubble();
                                    return;
                                  }
                                  if (paramString.equalsIgnoreCase("showAlert"))
                                  {
                                    update(paramString, paramObject);
                                    return;
                                  }
                                  if (!paramString.equalsIgnoreCase("showEducationFav"))
                                    break;
                                  ViewController localViewController2 = this.navigationController.getLastViewController();
                                  if ((!localViewController2.controllerName.equalsIgnoreCase("imchat")) && (!localViewController2.controllerName.equalsIgnoreCase("chatroom")) && (!isBubbleShowing()))
                                  {
                                    if ((paramObject != null) && ((paramObject instanceof ChannelNode)));
                                    for (ChannelNode localChannelNode = (ChannelNode)paramObject; (localChannelNode != null) && (!localChannelNode.isDownloadChannel()) && (!isBubbleShowing()) && (!InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(localChannelNode)) && (CollectionRemindManager.shoudShowRemind(getContext(), localChannelNode.channelId)); localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode())
                                    {
                                      String str3 = CollectionRemindManager.getSource();
                                      QTMSGManage.getInstance().sendStatistcsMessage("pHintFavoriteDisplay", str3);
                                      showBubble(20, localChannelNode);
                                      return;
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                      if (!paramString.equalsIgnoreCase("showChannelInfo"))
                        break;
                    }
                    while (paramObject == null);
                    showBubble(3, paramObject);
                    return;
                  }
                  while (paramString.equalsIgnoreCase("cancelTip"));
                  if (paramString.equalsIgnoreCase("sendErrorLog"))
                  {
                    String str2 = (String)paramObject;
                    QTMSGManage.getInstance().sendStatistcsMessage("textelementerror", str2);
                    return;
                  }
                  if (!paramString.equalsIgnoreCase("showSettingView"))
                    break;
                }
                while (this.navigationController == null);
                if (this.navigationController.getLastViewController() != this.mRootController)
                  this.navigationController.popToRootViewController(true);
                this.mRootController.config("showSettingView", null);
                return;
                if (!paramString.equalsIgnoreCase("showError"))
                  break;
              }
              while (!SharedCfg.getInstance().getDevMode());
              String str1 = (String)paramObject;
              MainHandler localMainHandler = new MainHandler(Looper.getMainLooper());
              Message localMessage = Message.obtain();
              Bundle localBundle = new Bundle();
              localBundle.putString("log", str1);
              localMessage.setData(localBundle);
              localMainHandler.sendMessage(localMessage);
              return;
              if (paramString.equalsIgnoreCase("quit"))
              {
                dispatchActionEvent(paramString, paramObject);
                return;
              }
              if (paramString.equalsIgnoreCase("showChatUserAction"))
              {
                showBubble(14, paramObject);
                return;
              }
              if (paramString.equalsIgnoreCase("selectdir"))
              {
                dispatchActionEvent(paramString, paramObject);
                return;
              }
              if (paramString.equalsIgnoreCase("showfmtest"))
              {
                showBubble(11, null);
                return;
              }
              if (!paramString.equalsIgnoreCase("showLink"))
                break;
            }
            while ((paramObject == null) || (this.navigationController.isAnimating()));
            if (!isBubbleShowing())
              break;
            localRecommendItemNode = LinkManager.getLastNode();
          }
          while ((localRecommendItemNode != null) && (localRecommendItemNode.id.equalsIgnoreCase(((RecommendItemNode)paramObject).id)));
          localViewController1 = ControllerManager.getInstance().getLastViewController();
          if (localViewController1.controllerName.equalsIgnoreCase("mainplayview"))
          {
            Point localPoint = (Point)localViewController1.getValue("progressPosition", paramObject);
            localViewController1.config("liftSomeViews", null);
            LinkManager.showLink(getContext(), (RecommendItemNode)paramObject, true, localPoint);
            return;
          }
        }
        while (!localViewController1.hasMiniPlayer());
        LinkManager.showLink(getContext(), (RecommendItemNode)paramObject, false, null);
        return;
        if (paramString.equalsIgnoreCase("showimmenu"))
        {
          showBubble(1, paramObject);
          return;
        }
        if (paramString.equalsIgnoreCase("QTquit"))
        {
          dispatchActionEvent("quit", null);
          return;
        }
        if (paramString.equalsIgnoreCase("showblockremind"))
        {
          showBubble(16, paramObject);
          return;
        }
        if (paramString.equalsIgnoreCase("showBlockRemovePop"))
        {
          showBubble(17, paramObject);
          return;
        }
        if (paramString.equalsIgnoreCase("toggleCategoryFilter"))
        {
          if ((isBubbleShowing()) && (this.mBubbleView.getBubbleType() == 18))
          {
            cancelBubble();
            return;
          }
          showBubble(18, paramObject);
          return;
        }
        if (!paramString.equalsIgnoreCase("hideCategoryFilterIfExist"))
          break;
      }
      while ((!isBubbleShowing()) || (this.mBubbleView.getBubbleType() != 18));
      cancelBubble();
      return;
      if (paramString.equalsIgnoreCase("openFeedback"))
      {
        cancelBubble();
        ControllerManager.getInstance().openFeedBackController();
        return;
      }
      if (paramString.equalsIgnoreCase("closerecentplay"))
      {
        ControllerManager.getInstance().updateRecentPlayOnChannelDetail(paramString);
        return;
      }
      if (paramString.equalsIgnoreCase("onlineUpgrade"))
      {
        showBubble(22, paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("toast"))
      {
        showBubble(23, paramObject);
        new Handler().postDelayed(new Runnable()
        {
          public void run()
          {
            EventDispacthManager.getInstance().dispatchAction("cancelPop", null);
          }
        }
        , 2000L);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("topPlayHistory"));
    if ((isBubbleShowing()) && (this.mBubbleView.getBubbleType() == 24))
    {
      cancelBubble();
      return;
    }
    showBubble(24, paramObject);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("showQuitAlert"))
    {
      cancelBubble(false);
      dispatchActionEvent(paramString, paramObject2);
    }
    while (true)
    {
      return;
      if (paramString.equalsIgnoreCase("quit"))
      {
        cancelBubble();
        dispatchActionEvent(paramString, paramObject2);
        return;
      }
      if (paramString.equalsIgnoreCase("playAtBack"))
      {
        cancelBubble();
        dispatchActionEvent(paramString, paramObject2);
        return;
      }
      if (paramString.equalsIgnoreCase("cancelPop"))
      {
        cancelBubble();
        return;
      }
      if (paramString.equalsIgnoreCase("startDimBackAnimation"))
      {
        startDimBackAnimation();
        return;
      }
      if (paramString.equalsIgnoreCase("havealookatalarm"))
      {
        String str2 = (String)paramObject2;
        QTMSGManage.getInstance().sendStatistcsMessage("alarm_enter", str2);
        cancelBubble();
        ControllerManager.getInstance().redirectToAddAlarmViewByRemind();
        return;
      }
      if (paramString.equalsIgnoreCase("addringtone"))
      {
        cancelBubble();
        return;
      }
      if (paramString.equalsIgnoreCase("scanCancel"))
      {
        if (InfoManager.getInstance().root().mContentCategory.mLiveNode.mRadioNode == null)
          continue;
        InfoManager.getInstance().root().mContentCategory.mLiveNode.mRadioNode.addDefaultNode();
        return;
      }
      if (paramString.equalsIgnoreCase("redirectToSina"))
      {
        cancelBubble();
        if (paramObject2 == null)
        {
          CloudCenter.getInstance().login(1, null);
          return;
        }
        CloudCenter.getInstance().login(1, (CloudCenter.OnLoginEventListerner)paramObject2);
        return;
      }
      if (paramString.equalsIgnoreCase("redirectToTencent"))
      {
        cancelBubble();
        if (paramObject2 == null)
        {
          CloudCenter.getInstance().login(2, null);
          return;
        }
        CloudCenter.getInstance().login(2, (CloudCenter.OnLoginEventListerner)paramObject2);
        return;
      }
      if (paramString.equalsIgnoreCase("download"))
      {
        cancelBubble();
        Node localNode = (Node)paramObject2;
        if (!InfoManager.getInstance().root().mDownLoadInfoNode.addToDownloadList(localNode))
          continue;
        String str1 = "开始下载";
        if (localNode.nodeName.equalsIgnoreCase("program"))
          str1 = str1 + ((ProgramNode)localNode).title;
        Toast.makeText(getContext(), str1, 0).show();
        return;
      }
      if (paramString.equalsIgnoreCase("closeAdView"))
        try
        {
          radiocastMessage("qt_action_stopservice");
          cancelBubble();
          return;
        }
        catch (Exception localException2)
        {
          return;
        }
      if (paramString.equalsIgnoreCase("openADRegin"));
      try
      {
        radiocastMessage("qt_action_stopservice");
        cancelBubble();
        label416: ControllerManager.getInstance().openADwebviewController(AdvertisementManage.get_qtAdvertisementInfo(), new QTCoupon(), "0");
        return;
        if (paramString.equalsIgnoreCase("showReplaySchedule"))
        {
          cancelBubble();
          return;
        }
        if (paramString.equalsIgnoreCase("showReserveSchedule"))
        {
          cancelBubble();
          return;
        }
        if (paramString.equalsIgnoreCase("shareToPlatform"))
        {
          cancelBubble();
          share(((Integer)paramObject2).intValue());
          return;
        }
        if (paramString.equalsIgnoreCase("jumpShare"))
        {
          if (this.mShareGuideView == null)
            continue;
          removeView(this.mShareGuideView);
          this.mShareGuideView.close(false);
          this.mShareGuideView = null;
          this.mShowingShare = false;
          return;
        }
        if (paramString.equalsIgnoreCase("recommendShare"))
        {
          if (this.mShareGuideView != null)
          {
            removeView(this.mShareGuideView);
            this.mShareGuideView.close(false);
            this.mShareGuideView = null;
            this.mShowingShare = false;
          }
          RecommendItemNode localRecommendItemNode = (RecommendItemNode)paramObject2;
          if (localRecommendItemNode == null)
            continue;
          this.mShareObject = localRecommendItemNode.mNode;
          if (WeiboAgent.getInstance().isLoggedIn().booleanValue())
          {
            QTMSGManage.getInstance().sendStatistcsMessage("SendRecommendShare", "weibo");
            share(4);
            return;
          }
          if (TencentAgent.getInstance().isLoggedIn().booleanValue())
          {
            QTMSGManage.getInstance().sendStatistcsMessage("SendRecommendShare", "tencent");
            share(5);
            return;
          }
          if (SharedCfg.getInstance().getRecommendShareLogin())
            continue;
          QTMSGManage.getInstance().sendStatistcsMessage("BindRecommendShare", "bind");
          InfoManager.getInstance().setBindRecommendShare(true);
          showBubble(12, null);
          SharedCfg.getInstance().setRecommendShareLogin(true);
          return;
        }
        dispatchActionEvent(paramString, paramObject2);
        return;
      }
      catch (Exception localException1)
      {
        break label416;
      }
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.container.layout(0, 0, this.viewWidth, this.viewHeight);
    layoutBubble(false);
    if (this.mShareGuideView != null)
      this.mShareGuideView.layout(0, 0, this.viewWidth, this.viewHeight);
    if (paramBoolean);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.viewWidth = View.MeasureSpec.getSize(paramInt1);
    this.viewHeight = View.MeasureSpec.getSize(paramInt2);
    ScreenType.setViewParam(this.viewWidth, this.viewHeight);
    SkinManager.getInstance().calculateFontSize(this.viewWidth);
    this.standardLayout.scaleToBounds(this.viewWidth, this.viewHeight);
    EducationManager.getInstance().measure(this.standardLayout);
    this.naviLayout.scaleToBounds(this.standardLayout);
    this.bottomLayout.scaleToBounds(this.standardLayout);
    int i = View.MeasureSpec.makeMeasureSpec(this.viewHeight, 1073741824);
    this.container.measure(paramInt1, i);
    this.mBubbleView.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(getBubbleHeight(), 1073741824));
    if (this.mShareGuideView != null)
      this.mShareGuideView.measure(paramInt1, i);
    setMeasuredDimension(this.viewWidth, this.viewHeight);
  }

  public void onPopControllers(List<ViewController> paramList, boolean paramBoolean)
  {
    boolean bool;
    if ((paramList != null) && (paramList.size() > 0) && (((ViewController)paramList.get(0)).controllerName.equalsIgnoreCase("mainplayview")))
    {
      bool = InfoManager.getInstance().root().getHasChangedChannel();
      InfoManager.getInstance().root().clearHasChangedChannel();
    }
    while (true)
    {
      setFlagOnTopViewChanged(bool);
      markPageChanged(this.navigationController.getLastViewController());
      return;
      bool = false;
    }
  }

  public void onPushController(ViewController paramViewController, boolean paramBoolean)
  {
    setFlagOnTopViewChanged(paramViewController);
    markPageChanged(paramViewController);
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    LinkManager.cancelLinkIfExists(getContext());
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("performPop"))
    {
      popController();
      ((InputMethodManager)getContext().getSystemService("input_method")).hideSoftInputFromWindow(getApplicationWindowToken(), 0);
    }
    do
    {
      do
      {
        do
        {
          Object localObject;
          do
          {
            ViewController localViewController;
            do
            {
              return;
              if (paramString.equalsIgnoreCase("onPause"))
              {
                handleOnPause();
                return;
              }
              if (paramString.equalsIgnoreCase("cancelBubble"))
              {
                cancelBubble();
                return;
              }
              if (!paramString.equalsIgnoreCase("refresh"))
                break;
              if (this.container != null)
                this.container.invalidate();
              localViewController = this.navigationController.getLastViewController();
            }
            while ((localViewController == null) || (!localViewController.controllerName.equalsIgnoreCase("frontpage")));
            localObject = localViewController.getValue("currentIndex", null);
          }
          while ((localObject == null) || (((Integer)localObject).intValue() != 0));
          RecommendStatisticsUtil.INSTANCE.resume();
          return;
          if (!paramString.equalsIgnoreCase("showadBubble"))
            break;
        }
        while ((this.navigationController == null) || (this.navigationController.getLastViewController() == null) || (this.navigationController.getLastViewController().controllerName == null));
        if (this.navigationController.getLastViewController().controllerName.equalsIgnoreCase("mainplayview"))
        {
          showBubble(13, null);
          UMengLogger.sendmessage(getContext(), "adPlay", AdvertisementManage.getInstance().currentADKey, 1);
          return;
        }
        UMengLogger.sendmessage(getContext(), "adPlay", AdvertisementManage.getInstance().currentADKey, 2);
        return;
        if (paramString.equalsIgnoreCase("openadwebview"))
        {
          ControllerManager.getInstance().openADwebviewController((String)paramObject);
          return;
        }
        if (paramString.equalsIgnoreCase("showAlert"))
        {
          showBubble(5, paramObject);
          return;
        }
        if (!paramString.equalsIgnoreCase("removeShare"))
          break;
      }
      while (this.mShareGuideView == null);
      removeView(this.mShareGuideView);
      this.mShareGuideView.close(false);
      this.mShareGuideView = null;
      this.mShowingShare = false;
      MobclickAgent.onEvent(getContext(), "showShare", "remove");
      return;
    }
    while (!paramString.equalsIgnoreCase("resortCategoryList"));
    this.mRootController.config(paramString, paramObject);
  }

  private class MainHandler extends Handler
  {
    public MainHandler(Looper arg2)
    {
      super();
    }

    public void handleMessage(Message paramMessage)
    {
      String str = paramMessage.getData().getString("log");
      MainView.this.showBubble(2, str);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.MainView
 * JD-Core Version:    0.6.2
 */