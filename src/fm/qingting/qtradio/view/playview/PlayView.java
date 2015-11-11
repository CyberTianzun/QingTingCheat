package fm.qingting.qtradio.view.playview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.utils.ImageLoader;
import fm.qingting.framework.utils.ImageLoaderHandler;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.manager.BlurManager;
import fm.qingting.qtradio.manager.BlurManager.IImageBluredListener;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.model.AdvertisementInfoNode;
import fm.qingting.qtradio.model.AdvertisementItemNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RadioChannelNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.ScreenType;
import java.util.List;

public class PlayView extends ViewGroupViewImpl
  implements BlurManager.IImageBluredListener, IEventHandler
{
  private static final int DISMISS_LENGTH = 2000;
  private final int ACTIONHEIGHT;
  private final int BUTTONHEIGHT;
  private final int INFOHEIGHT;
  private final int PROCESSHEIGHT;
  private final String TAG = "play";
  private final ViewLayout actionLayout;
  private final ViewLayout bottomBarLayout;
  private AccurateSeekView mAccurateSeekView;
  private PlayActionsView mActionsView;
  private String mBackgroundUrl;
  private PlayButtonsView mButtonsView;
  private Handler mDelayHandler;
  private Runnable mDelayRunnable;
  private String mHasLoadAdvUrl;
  private boolean mHasSetAdvThumb;
  private PlayInfoView mInfoView;
  private PlayJoinChatView mJoinChatView;
  private boolean mLifted;
  private PlayNaviView mNaviView;
  private SeekBarView mSeekBarView;
  private PlayVirtualInfoView mVirtualInfoView;
  private final ViewLayout naviLayout;
  private final ViewLayout playButtonsLayout;
  private final ViewLayout playInfoLayout;
  private final ViewLayout processLayout;
  private final ViewLayout standardLayout;

  public PlayView(Context paramContext)
  {
    super(paramContext);
    int i;
    int j;
    if (ScreenType.isWideScreen())
    {
      i = 630;
      this.INFOHEIGHT = i;
      if (ScreenType.isWideScreen());
      this.PROCESSHEIGHT = 105;
      if (!ScreenType.isWideScreen())
        break label494;
      j = 100;
      label47: this.ACTIONHEIGHT = j;
      if (!ScreenType.isWideScreen())
        break label500;
    }
    label494: label500: for (int k = 200; ; k = 240)
    {
      this.BUTTONHEIGHT = k;
      this.standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
      this.playInfoLayout = this.standardLayout.createChildLT(720, this.INFOHEIGHT, 0, 98, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
      this.processLayout = this.standardLayout.createChildLT(720, this.PROCESSHEIGHT, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
      this.bottomBarLayout = this.standardLayout.createChildLT(720, 98, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
      this.naviLayout = this.standardLayout.createChildLT(720, 98, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
      this.actionLayout = this.standardLayout.createChildLT(720, this.ACTIONHEIGHT, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
      this.playButtonsLayout = this.standardLayout.createChildLT(720, this.BUTTONHEIGHT, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
      this.mLifted = false;
      this.mHasSetAdvThumb = false;
      this.mHasLoadAdvUrl = null;
      this.mDelayHandler = new Handler();
      this.mDelayRunnable = new Runnable()
      {
        public void run()
        {
          PlayView.this.hideAccurateSeek();
        }
      };
      BlurManager.getInstance().addListener(this);
      setProgramBackground(BitmapResourceCache.getInstance().getResourceCache(getResources(), this, 2130837878));
      this.mNaviView = new PlayNaviView(paramContext);
      addView(this.mNaviView);
      this.mNaviView.setEventHandler(this);
      this.mInfoView = new PlayInfoView(paramContext);
      addView(this.mInfoView);
      this.mInfoView.setVisibility(4);
      this.mVirtualInfoView = new PlayVirtualInfoView(paramContext);
      addView(this.mVirtualInfoView);
      this.mActionsView = new PlayActionsView(paramContext);
      addView(this.mActionsView);
      this.mActionsView.setEventHandler(this);
      this.mButtonsView = new PlayButtonsView(paramContext);
      addView(this.mButtonsView);
      this.mButtonsView.setEventHandler(this);
      this.mJoinChatView = new PlayJoinChatView(paramContext);
      addView(this.mJoinChatView);
      this.mSeekBarView = new SeekBarView(paramContext);
      addView(this.mSeekBarView);
      this.mSeekBarView.setEventHandler(this);
      return;
      i = 594;
      break;
      j = 110;
      break label47;
    }
  }

  private void autoLoadAdvUrl()
  {
    if (InfoManager.getInstance().root().isPlayingAd())
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          ControllerManager.getInstance().redirectToActiviyByUrl(PlayView.this.mHasLoadAdvUrl, null, true);
        }
      }
      , 2000L);
  }

  private void hideAccurateSeek()
  {
    if (this.mAccurateSeekView != null)
    {
      removeView(this.mAccurateSeekView);
      this.mAccurateSeekView.close(false);
      this.mAccurateSeekView = null;
    }
  }

  @TargetApi(11)
  private void liftSomeViews(int paramInt)
  {
    this.mLifted = true;
    if (this.mVirtualInfoView.getVisibility() == 0)
      this.mVirtualInfoView.update("liftTitle", null);
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      AnimatorSet localAnimatorSet = new AnimatorSet();
      PlayActionsView localPlayActionsView = this.mActionsView;
      float[] arrayOfFloat = new float[1];
      arrayOfFloat[0] = (-paramInt);
      localAnimatorSet.play(ObjectAnimator.ofFloat(localPlayActionsView, "translationY", arrayOfFloat));
      localAnimatorSet.addListener(new Animator.AnimatorListener()
      {
        public void onAnimationCancel(Animator paramAnonymousAnimator)
        {
        }

        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
        }

        public void onAnimationRepeat(Animator paramAnonymousAnimator)
        {
        }

        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
        }
      });
      localAnimatorSet.start();
      return;
    }
    int i = this.standardLayout.height - this.bottomBarLayout.height - this.playButtonsLayout.height - this.processLayout.height - paramInt;
    this.mActionsView.layout(0, i - this.actionLayout.height, this.actionLayout.width, i);
  }

  @TargetApi(11)
  private void resetSomeViews()
  {
    this.mLifted = false;
    this.mVirtualInfoView.update("resetTitle", null);
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      AnimatorSet localAnimatorSet = new AnimatorSet();
      localAnimatorSet.play(ObjectAnimator.ofFloat(this.mActionsView, "translationY", new float[] { 0.0F }));
      localAnimatorSet.addListener(new Animator.AnimatorListener()
      {
        public void onAnimationCancel(Animator paramAnonymousAnimator)
        {
        }

        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
        }

        public void onAnimationRepeat(Animator paramAnonymousAnimator)
        {
        }

        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
        }
      });
      localAnimatorSet.start();
      return;
    }
    int i = this.standardLayout.height - this.bottomBarLayout.height - this.playButtonsLayout.height - this.processLayout.height;
    this.mActionsView.layout(0, i - this.actionLayout.height, this.actionLayout.width, i);
  }

  private void setAccurateInitStateIfExists()
  {
    if (this.mAccurateSeekView == null)
      return;
    this.mAccurateSeekView.update("leftTimeOffset", this.mSeekBarView.getValue("leftTimeOffset", null));
    this.mAccurateSeekView.update("rightTime", this.mSeekBarView.getValue("rightTime", null));
    this.mAccurateSeekView.update("progress", this.mSeekBarView.getValue("progress", null));
  }

  private void setBackgroundUsingBitmap(Bitmap paramBitmap)
  {
    if ((this.mBackgroundUrl == null) || (paramBitmap == null))
      return;
    try
    {
      Bitmap localBitmap = BlurManager.getInstance().getBluredBitmapInPlay(this.mBackgroundUrl + "play");
      if (localBitmap == null)
      {
        BlurManager.getInstance().blurBitmapInPlay(paramBitmap, -2013265920, new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight()), this.mBackgroundUrl + "play");
        return;
      }
      setProgramBackground(localBitmap);
      return;
    }
    catch (Error localError)
    {
    }
    catch (Exception localException)
    {
    }
  }

  private void setProgramBackground(Bitmap paramBitmap)
  {
    setBackgroundDrawable(new TrimDrawable(paramBitmap));
  }

  private void setProgramBackground(String paramString)
  {
    this.mBackgroundUrl = paramString;
    int i = ScreenType.getWidth();
    int j = ScreenType.getHeight();
    Bitmap localBitmap = ImageLoader.getInstance(getContext()).getImage(paramString, i, j);
    if (localBitmap == null)
    {
      ImageLoaderHandler local4 = new ImageLoaderHandler()
      {
        public void loadImageFinish(boolean paramAnonymousBoolean, String paramAnonymousString, Bitmap paramAnonymousBitmap, int paramAnonymousInt1, int paramAnonymousInt2)
        {
          PlayView.this.setBackgroundUsingBitmap(paramAnonymousBitmap);
        }

        public void updateImageViewFinish(boolean paramAnonymousBoolean, ImageView paramAnonymousImageView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
        {
        }
      };
      ImageLoader.getInstance(getContext()).loadImage(paramString, null, this, local4);
      return;
    }
    setBackgroundUsingBitmap(localBitmap);
  }

  private void showAccurateSeek()
  {
    if (this.mAccurateSeekView == null)
    {
      this.mAccurateSeekView = new AccurateSeekView(getContext());
      this.mAccurateSeekView.setEventHandler(this);
      addView(this.mAccurateSeekView);
    }
    setAccurateInitStateIfExists();
    this.mDelayHandler.removeCallbacks(this.mDelayRunnable);
  }

  private void showSchedule(int paramInt)
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
    if (localChannelNode2.channelType == 0)
    {
      ControllerManager.getInstance().openTraScheduleController(getBackground(), localChannelNode2, paramInt);
      return;
    }
    List localList = localChannelNode2.getAllLstProgramNode();
    ControllerManager.getInstance().openPlayListContoller(getBackground(), localList);
  }

  public void close(boolean paramBoolean)
  {
    this.mNaviView.close(paramBoolean);
    this.mInfoView.close(paramBoolean);
    this.mActionsView.close(paramBoolean);
    this.mButtonsView.close(paramBoolean);
    this.mJoinChatView.close(paramBoolean);
    this.mSeekBarView.close(paramBoolean);
    this.mVirtualInfoView.close(paramBoolean);
    this.mAccurateSeekView.close(paramBoolean);
    super.close(paramBoolean);
  }

  protected void dispatchDraw(Canvas paramCanvas)
  {
    int i = paramCanvas.save();
    paramCanvas.clipRect(0, this.standardLayout.height - this.bottomBarLayout.height - this.playButtonsLayout.height - this.processLayout.height, this.standardLayout.width, this.standardLayout.height);
    paramCanvas.drawColor(855638016);
    paramCanvas.restoreToCount(i);
    super.dispatchDraw(paramCanvas);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("progressPosition"))
    {
      int i = this.processLayout.height + this.playButtonsLayout.height + this.bottomBarLayout.height;
      Point localPoint = (Point)this.mSeekBarView.getValue(paramString, paramObject);
      localPoint.y = (i - localPoint.y);
      return localPoint;
    }
    return super.getValue(paramString, paramObject);
  }

  public void onBlurFinished(String paramString)
  {
    if (TextUtils.equals(paramString, this.mBackgroundUrl + "play"))
      setProgramBackground(this.mBackgroundUrl);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    AdvertisementItemNode localAdvertisementItemNode;
    if (paramString.equalsIgnoreCase("progresschanged"))
    {
      if (this.mAccurateSeekView != null)
        this.mAccurateSeekView.update(paramString, paramObject2);
      if ((InfoManager.getInstance().root().isPlayingAd()) && ((!this.mHasSetAdvThumb) || (this.mHasLoadAdvUrl == null)))
      {
        localAdvertisementItemNode = InfoManager.getInstance().root().mAdvertisementInfoNode.getCurrPlayingAdv();
        if (localAdvertisementItemNode != null)
        {
          if ((localAdvertisementItemNode.image == null) || (localAdvertisementItemNode.image.equalsIgnoreCase("")))
            break label129;
          this.mHasSetAdvThumb = true;
          this.mVirtualInfoView.update("setthumb", localAdvertisementItemNode.image);
          MobclickAgent.onEvent(getContext(), "PlayviewShowAdv", localAdvertisementItemNode.landing);
        }
      }
    }
    label129: 
    do
    {
      do
      {
        do
          return;
        while ((localAdvertisementItemNode.landing == null) || (localAdvertisementItemNode.landing.equalsIgnoreCase("")) || (this.mHasLoadAdvUrl != null));
        this.mHasLoadAdvUrl = localAdvertisementItemNode.landing;
        autoLoadAdvUrl();
        MobclickAgent.onEvent(getContext(), "PlayviewLoadAdv", localAdvertisementItemNode.landing);
        return;
      }
      while (InfoManager.getInstance().root().isPlayingAd());
      if (this.mHasSetAdvThumb)
      {
        this.mHasSetAdvThumb = false;
        this.mVirtualInfoView.update("recoverthumb", "");
      }
      this.mHasLoadAdvUrl = null;
      return;
      if (paramString.equalsIgnoreCase("showSchedule"))
      {
        showSchedule(1);
        return;
      }
      if (paramString.equalsIgnoreCase("checkin"))
      {
        dispatchActionEvent(paramString, paramObject2);
        return;
      }
      if (paramString.equalsIgnoreCase("showAccurateSeek"))
      {
        showAccurateSeek();
        return;
      }
      if (paramString.equalsIgnoreCase("extendDismissLength"))
      {
        this.mDelayHandler.removeCallbacks(this.mDelayRunnable);
        this.mDelayHandler.postDelayed(this.mDelayRunnable, 2000L);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("hideAccurateSeek"));
    this.mDelayHandler.removeCallbacks(this.mDelayRunnable);
    this.mDelayHandler.postDelayed(this.mDelayRunnable, 2000L);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = this.standardLayout.height;
    this.mJoinChatView.layout(0, i - this.bottomBarLayout.height, this.bottomBarLayout.width, i);
    int j = i - this.bottomBarLayout.height;
    this.mButtonsView.layout(0, j - this.playButtonsLayout.height, this.playButtonsLayout.width, j);
    int k = j - this.playButtonsLayout.height;
    this.mSeekBarView.layout(0, k - this.processLayout.height, this.processLayout.width, k);
    int m = k - this.processLayout.height;
    this.mActionsView.layout(0, m - this.actionLayout.height, this.actionLayout.width, m);
    int n = m - this.actionLayout.height;
    this.mInfoView.layout(0, this.naviLayout.height, this.playInfoLayout.width, n);
    this.mVirtualInfoView.layout(0, this.naviLayout.height, this.playInfoLayout.width, n);
    this.naviLayout.layoutView(this.mNaviView);
    if (this.mAccurateSeekView != null)
      this.mAccurateSeekView.layout(0, 0, this.standardLayout.width, this.standardLayout.height - this.processLayout.height - this.playButtonsLayout.height - this.bottomBarLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.naviLayout.scaleToBounds(this.standardLayout);
    this.naviLayout.measureView(this.mNaviView);
    this.playInfoLayout.scaleToBounds(this.standardLayout);
    this.actionLayout.scaleToBounds(this.standardLayout);
    this.actionLayout.measureView(this.mActionsView);
    this.processLayout.scaleToBounds(this.standardLayout);
    this.processLayout.measureView(this.mSeekBarView);
    this.bottomBarLayout.scaleToBounds(this.standardLayout);
    this.bottomBarLayout.measureView(this.mJoinChatView);
    this.playButtonsLayout.scaleToBounds(this.standardLayout);
    this.playButtonsLayout.measureView(this.mButtonsView);
    int i = this.standardLayout.height - this.naviLayout.height - this.actionLayout.height - this.processLayout.height - this.playButtonsLayout.height - this.bottomBarLayout.height;
    this.mInfoView.measure(this.playInfoLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(i, 1073741824));
    this.mVirtualInfoView.measure(this.playInfoLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(i, 1073741824));
    if (this.mAccurateSeekView != null)
      this.mAccurateSeekView.measure(this.playInfoLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.processLayout.height - this.playButtonsLayout.height - this.bottomBarLayout.height, 1073741824));
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    ProgramNode localProgramNode;
    if (paramString.equalsIgnoreCase("setProgramNode"))
    {
      Node localNode = (Node)paramObject;
      this.mHasSetAdvThumb = false;
      if (localNode.nodeName.equalsIgnoreCase("program"))
      {
        this.mSeekBarView.update("setNode", paramObject);
        this.mButtonsView.update("setNode", paramObject);
        setAccurateInitStateIfExists();
        this.mJoinChatView.update("setNode", paramObject);
        localProgramNode = (ProgramNode)localNode;
        String str1 = InfoManager.getInstance().getWsq(localProgramNode.channelId);
        if (str1 == null)
          break label223;
        InfoManager.getInstance().loadWsqNew(str1);
        this.mJoinChatView.update("useWsq", str1);
        if (!localProgramNode.isDownloadProgram())
          break label253;
        ChannelNode localChannelNode4 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
        if ((localChannelNode4 == null) || (!localChannelNode4.nodeName.equalsIgnoreCase("channel")))
          break label237;
        this.mNaviView.update("setData", localChannelNode4.title);
        label167: this.mActionsView.update("hideFav", null);
        this.mInfoView.update("setNode", paramObject);
        this.mVirtualInfoView.setVisibility(4);
        this.mInfoView.setVisibility(0);
        setProgramBackground(BitmapResourceCache.getInstance().getResourceCache(getResources(), this, 2130837878));
      }
    }
    label223: label237: label253: 
    do
    {
      do
      {
        return;
        this.mJoinChatView.update("noWsq", null);
        break;
        this.mNaviView.update("setData", "我的下载");
        break label167;
        if (localProgramNode.channelType == 1)
        {
          ChannelNode localChannelNode3 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
          this.mVirtualInfoView.update("setNode", paramObject);
          this.mActionsView.update("showFav", null);
          this.mVirtualInfoView.setVisibility(0);
          this.mInfoView.setVisibility(4);
          if (localChannelNode3 != null)
          {
            this.mNaviView.update("setData", localChannelNode3.title);
            String str3 = localChannelNode3.getApproximativeThumb();
            if (!TextUtils.isEmpty(str3))
              setProgramBackground(str3);
          }
          String str2 = InfoManager.getInstance().getMall(localProgramNode.id);
          if (str2 != null)
          {
            this.mActionsView.update("showMall", str2);
            QTMSGManage.getInstance().sendStatistcsMessage("mallPageView", "" + localProgramNode.id);
            return;
          }
          this.mActionsView.update("hideMall", null);
          return;
        }
        ChannelNode localChannelNode1 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
        if ((localChannelNode1 != null) && (localChannelNode1.nodeName.equalsIgnoreCase("channel")))
        {
          ChannelNode localChannelNode2 = (ChannelNode)localChannelNode1;
          this.mInfoView.update("setNode", paramObject);
          this.mVirtualInfoView.setVisibility(4);
          this.mInfoView.setVisibility(0);
          this.mNaviView.update("setData", localChannelNode2.title);
          setProgramBackground(BitmapResourceCache.getInstance().getResourceCache(getResources(), this, 2130837878));
        }
        while (true)
        {
          this.mActionsView.update("showFav", null);
          return;
          if ((localChannelNode1 != null) && (localChannelNode1.nodeName.equalsIgnoreCase("radiochannel")))
          {
            RadioChannelNode localRadioChannelNode = (RadioChannelNode)localChannelNode1;
            this.mInfoView.update("setNode", paramObject);
            this.mVirtualInfoView.setVisibility(4);
            this.mInfoView.setVisibility(0);
            this.mNaviView.update("setData", localRadioChannelNode.channelName);
            setProgramBackground(BitmapResourceCache.getInstance().getResourceCache(getResources(), this, 2130837878));
          }
        }
        if (paramString.equalsIgnoreCase("liftSomeViews"))
        {
          liftSomeViews(ScreenType.getCustomLinkHeight());
          return;
        }
        if (!paramString.equalsIgnoreCase("resetSomeViews"))
          break label664;
      }
      while (!this.mLifted);
      resetSomeViews();
      return;
    }
    while (!paramString.equalsIgnoreCase("showSchedule"));
    label664: showSchedule(0);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview.PlayView
 * JD-Core Version:    0.6.2
 */