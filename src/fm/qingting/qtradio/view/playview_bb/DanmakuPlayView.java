package fm.qingting.qtradio.view.playview_bb;

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
import fm.qingting.qtradio.model.AdvertisementInfoNode;
import fm.qingting.qtradio.model.AdvertisementItemNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RadioChannelNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.playview.AccurateSeekView;
import fm.qingting.utils.ScreenType;
import java.util.List;

public class DanmakuPlayView extends ViewGroupViewImpl
  implements BlurManager.IImageBluredListener, IEventHandler
{
  private static final int DISMISS_LENGTH = 2000;
  private final int ACTIONHEIGHT;
  private final int BUTTONHEIGHT;
  private final int PROCESSHEIGHT;
  private final String TAG = "play";
  private final ViewLayout barrageLayout;
  private int fuckingHeight;
  private AccurateSeekView mAccurateSeekView;
  private DanmakuPlayActionsView mActionsView;
  private String mBackgroundUrl;
  private BBDanmakuView mBarrageView;
  private DanmakuPlayButtonsView mButtonsView;
  private Handler mDelayHandler;
  private Runnable mDelayRunnable;
  private String mHasLoadAdvUrl;
  private boolean mHasSetAdvThumb;
  private DanmakuPlayNaviView mNaviView;
  private DanmakuSeekBarView mSeekBarView;
  private DanmakuPlayTextView mTextView;
  private final ViewLayout naviLayout;
  private final ViewLayout playButtonsLayout;
  private final ViewLayout playTextLayout;
  private final ViewLayout processLayout;
  private final ViewLayout standardLayout;

  public DanmakuPlayView(Context paramContext)
  {
    super(paramContext);
    if (ScreenType.isWideScreen());
    this.PROCESSHEIGHT = 105;
    int i;
    if (ScreenType.isWideScreen())
    {
      i = 100;
      this.ACTIONHEIGHT = i;
      if (!ScreenType.isWideScreen())
        break label403;
    }
    label403: for (int j = 160; ; j = 180)
    {
      this.BUTTONHEIGHT = j;
      this.standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
      this.barrageLayout = this.standardLayout.createChildLT(720, 574, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
      this.processLayout = this.standardLayout.createChildLT(720, this.PROCESSHEIGHT, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
      this.naviLayout = this.standardLayout.createChildLT(720, 98, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
      this.playButtonsLayout = this.standardLayout.createChildLT(720, this.BUTTONHEIGHT, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
      this.playTextLayout = this.standardLayout.createChildLT(720, 98, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
      this.fuckingHeight = 0;
      this.mHasSetAdvThumb = false;
      this.mHasLoadAdvUrl = null;
      this.mDelayHandler = new Handler();
      this.mDelayRunnable = new Runnable()
      {
        public void run()
        {
          DanmakuPlayView.this.hideAccurateSeek();
        }
      };
      BlurManager.getInstance().addListener(this);
      setBackgroundColor(-1);
      this.mNaviView = new DanmakuPlayNaviView(paramContext);
      addView(this.mNaviView);
      this.mNaviView.setEventHandler(this);
      this.mBarrageView = new BBDanmakuView(paramContext);
      addView(this.mBarrageView);
      this.mActionsView = new DanmakuPlayActionsView(paramContext);
      addView(this.mActionsView);
      this.mActionsView.setEventHandler(this);
      this.mButtonsView = new DanmakuPlayButtonsView(paramContext);
      addView(this.mButtonsView);
      this.mButtonsView.setEventHandler(this);
      this.mSeekBarView = new DanmakuSeekBarView(paramContext);
      addView(this.mSeekBarView);
      this.mSeekBarView.setEventHandler(this);
      this.mTextView = new DanmakuPlayTextView(paramContext);
      addView(this.mTextView);
      return;
      i = 110;
      break;
    }
  }

  private void autoLoadAdvUrl()
  {
    if (InfoManager.getInstance().root().isPlayingAd())
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          ControllerManager.getInstance().redirectToActiviyByUrl(DanmakuPlayView.this.mHasLoadAdvUrl, null, true);
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
  }

  private void setProgramBackground(String paramString)
  {
    this.mBackgroundUrl = paramString;
    int i = ScreenType.getWidth();
    int j = ScreenType.getHeight();
    Bitmap localBitmap = ImageLoader.getInstance(getContext()).getImage(paramString, i, j);
    if (localBitmap == null)
    {
      ImageLoaderHandler local2 = new ImageLoaderHandler()
      {
        public void loadImageFinish(boolean paramAnonymousBoolean, String paramAnonymousString, Bitmap paramAnonymousBitmap, int paramAnonymousInt1, int paramAnonymousInt2)
        {
          DanmakuPlayView.this.setBackgroundUsingBitmap(paramAnonymousBitmap);
        }

        public void updateImageViewFinish(boolean paramAnonymousBoolean, ImageView paramAnonymousImageView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
        {
        }
      };
      ImageLoader.getInstance(getContext()).loadImage(paramString, null, this, local2);
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
    ControllerManager.getInstance().openDanmakuPlayListContoller(getBackground(), localList);
  }

  public void close(boolean paramBoolean)
  {
    this.mNaviView.close(paramBoolean);
    this.mActionsView.close(paramBoolean);
    this.mButtonsView.close(paramBoolean);
    this.mSeekBarView.close(paramBoolean);
    this.mAccurateSeekView.close(paramBoolean);
    this.mTextView.close(paramBoolean);
  }

  protected void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("progressPosition"))
    {
      int i = this.processLayout.height + this.playButtonsLayout.height + this.playTextLayout.height;
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
        if ((localAdvertisementItemNode.image != null) && (!localAdvertisementItemNode.image.equalsIgnoreCase("")))
        {
          this.mHasSetAdvThumb = true;
          MobclickAgent.onEvent(getContext(), "PlayviewShowAdv", localAdvertisementItemNode.landing);
        }
      }
    }
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
        this.mHasSetAdvThumb = false;
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
    int i = this.naviLayout.height;
    this.mBarrageView.layout(0, i, this.barrageLayout.width, i + this.barrageLayout.height);
    int j = i + this.barrageLayout.height;
    int k = this.standardLayout.height - this.naviLayout.height - this.barrageLayout.height - this.playButtonsLayout.height - this.processLayout.height - this.playTextLayout.height;
    if (this.fuckingHeight < k)
      this.fuckingHeight = k;
    this.mActionsView.layout(0, j, this.standardLayout.width, j + this.fuckingHeight);
    int m = j + this.fuckingHeight;
    this.mSeekBarView.layout(0, m, this.processLayout.width, m + this.processLayout.height);
    int n = m + this.processLayout.height;
    this.mButtonsView.layout(0, n, this.playButtonsLayout.width, n + this.playButtonsLayout.height);
    (n + this.playButtonsLayout.height);
    this.mTextView.layout(0, this.standardLayout.height - this.playTextLayout.height, this.playTextLayout.width, this.standardLayout.height);
    this.naviLayout.layoutView(this.mNaviView);
    if (this.mAccurateSeekView != null)
      this.mAccurateSeekView.layout(0, 0, this.standardLayout.width, this.standardLayout.height - this.processLayout.height - this.playButtonsLayout.height - this.playTextLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.naviLayout.scaleToBounds(this.standardLayout);
    this.naviLayout.measureView(this.mNaviView);
    this.barrageLayout.scaleToBounds(this.standardLayout);
    this.processLayout.scaleToBounds(this.standardLayout);
    this.processLayout.measureView(this.mSeekBarView);
    this.barrageLayout.measureView(this.mBarrageView);
    this.playButtonsLayout.scaleToBounds(this.standardLayout);
    this.playButtonsLayout.measureView(this.mButtonsView);
    int i = this.standardLayout.height - this.naviLayout.height - this.barrageLayout.height - this.playButtonsLayout.height - this.processLayout.height - this.playTextLayout.height;
    this.mActionsView.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(i, 1073741824));
    if (this.mAccurateSeekView != null)
      this.mAccurateSeekView.measure(this.playButtonsLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.processLayout.height - this.playButtonsLayout.height - this.playTextLayout.height, 1073741824));
    this.playTextLayout.scaleToBounds(this.standardLayout);
    this.playTextLayout.measureView(this.mTextView);
    if (this.fuckingHeight > i)
      this.mTextView.isFocus(true);
    while (true)
    {
      setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
      return;
      if (this.fuckingHeight == i)
        this.mTextView.isFocus(false);
    }
  }

  public void setActivate(boolean paramBoolean)
  {
    if (paramBoolean)
      super.setActivate(paramBoolean);
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
        localProgramNode = (ProgramNode)localNode;
        if (!localProgramNode.isDownloadProgram())
          break label175;
        ChannelNode localChannelNode4 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
        if ((localChannelNode4 == null) || (!localChannelNode4.nodeName.equalsIgnoreCase("channel")))
          break label159;
        this.mNaviView.update("setData", localChannelNode4.title);
        this.mActionsView.update("hideFav", null);
        this.mNaviView.update("setSubData", paramObject);
        setProgramBackground(BitmapResourceCache.getInstance().getResourceCache(getResources(), this, 2130837878));
      }
    }
    label159: label175: label238: 
    do
    {
      ChannelNode localChannelNode3;
      do
      {
        return;
        this.mNaviView.update("setData", "我的下载");
        break;
        if (localProgramNode.channelType != 1)
          break label238;
        localChannelNode3 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
        this.mNaviView.update("setSubData", paramObject);
        this.mActionsView.update("showFav", null);
      }
      while (localChannelNode3 == null);
      this.mNaviView.update("setData", localChannelNode3.title);
      return;
      ChannelNode localChannelNode1 = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      if ((localChannelNode1 != null) && (localChannelNode1.nodeName.equalsIgnoreCase("channel")))
      {
        ChannelNode localChannelNode2 = (ChannelNode)localChannelNode1;
        this.mNaviView.update("setSubData", paramObject);
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
          this.mNaviView.update("setSubData", paramObject);
          this.mNaviView.update("setData", localRadioChannelNode.channelName);
          setProgramBackground(BitmapResourceCache.getInstance().getResourceCache(getResources(), this, 2130837878));
        }
      }
      if (paramString.equalsIgnoreCase("showSchedule"))
      {
        showSchedule(0);
        return;
      }
      if (paramString.equalsIgnoreCase("setImageBarrage"))
      {
        this.mBarrageView.update(paramString, paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("setTxtBarrage"))
      {
        this.mBarrageView.update(paramString, paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("addDanmaku"))
      {
        this.mBarrageView.update(paramString, paramObject);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("setSendBarrage"));
    this.mBarrageView.update(paramString, paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview_bb.DanmakuPlayView
 * JD-Core Version:    0.6.2
 */