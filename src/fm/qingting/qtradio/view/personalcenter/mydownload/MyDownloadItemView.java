package fm.qingting.qtradio.view.personalcenter.mydownload;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import fm.qingting.downloadnew.DownloadService;
import fm.qingting.downloadnew.DownloadTask;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.tween.FrameTween;
import fm.qingting.framework.tween.FrameTween.SyncType;
import fm.qingting.framework.tween.IMotionHandler;
import fm.qingting.framework.tween.MotionController;
import fm.qingting.framework.tween.TweenProperty;
import fm.qingting.framework.tween.easing.Quad.EaseIn;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtListItemView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.QTApplication;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.Download;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.popviews.AlertParam;
import fm.qingting.qtradio.view.popviews.AlertParam.Builder;
import fm.qingting.qtradio.view.popviews.AlertParam.OnButtonClickListener;
import fm.qingting.qtradio.view.scheduleview.SizeInfo;
import fm.qingting.utils.TimeUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyDownloadItemView extends QtListItemView
  implements IMotionHandler
{
  private static final String DELETE = "删除";
  private static final String PAUSE = "暂停";
  private static final String RESUME = "继续";
  private static final String RETRY = "重试";
  private final ViewLayout checkStateLayout = this.checkbgLayout.createChildLT(30, 22, 2, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout checkbgLayout = this.itemLayout.createChildLT(48, 48, 30, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private DrawFilter filter = SkinManager.getInstance().getDrawFilter();
  private int hash = -25;
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 136, 720, 136, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 30, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private Object mAnimator;
  private Paint mCheckBgPaint = new Paint();
  private Rect mCheckRect = new Rect();
  private Paint mCheckStatePaint = new Paint();
  private boolean mInTouchMode = false;
  private Paint mInfoHighlightPaint = new Paint();
  private boolean mIsChecked = false;
  private float mLastMotionX = 0.0F;
  private float mLastMotionY = 0.0F;
  private Paint mManageHighlightPaint = new Paint();
  private Node mNode;
  private int mOffset = 0;
  private Paint mPaint = new Paint();
  private Paint mPauseHighlightPaint = new Paint();
  private Paint mPausePaint = new Paint();
  private Paint mPauseTextPaint = new Paint();
  private boolean mPaused = false;
  private Paint mProcessBgPaint = new Paint();
  private Paint mProcessingPaint = new Paint();
  private Paint mResumeHighlightPaint = new Paint();
  private Paint mResumePaint = new Paint();
  private Paint mResumeTextPaint = new Paint();
  private int mSelectedIndex = -1;
  private final ViewLayout manageLayout = this.itemLayout.createChildLT(90, 90, 608, 2, ViewLayout.SCALE_FLAG_SLTCW);
  private MotionController motionController;
  private final ViewLayout nameLayout = this.itemLayout.createChildLT(450, 45, 30, 20, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout processBgLayout = this.itemLayout.createChildLT(450, 9, 30, 15, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout sizeLayout = this.itemLayout.createChildLT(150, 45, 30, 10, ViewLayout.SCALE_FLAG_SLTCW);
  private Rect textBound = new Rect();
  private final ViewLayout timeLayout = this.itemLayout.createChildLT(150, 45, 30, 10, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout updateLayout = this.itemLayout.createChildLT(150, 45, 450, 10, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);

  public MyDownloadItemView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.hash = paramInt;
    this.mInfoHighlightPaint.setColor(SkinManager.getTextColorHighlight());
    this.mProcessBgPaint.setColor(-3552823);
    this.mProcessingPaint.setColor(SkinManager.getTextColorHighlight());
    this.mProcessBgPaint.setStyle(Paint.Style.FILL);
    this.mProcessingPaint.setStyle(Paint.Style.FILL);
    this.mResumePaint.setColor(SkinManager.getTextColorHighlight());
    this.mResumePaint.setStyle(Paint.Style.STROKE);
    this.mResumeHighlightPaint.setColor(SkinManager.getTextColorHighlight());
    this.mResumeHighlightPaint.setStyle(Paint.Style.FILL);
    this.mManageHighlightPaint.setColor(SkinManager.getBackgroundColor());
    this.mResumeTextPaint.setColor(SkinManager.getTextColorHighlight());
    this.mPausePaint.setColor(-9408400);
    this.mPausePaint.setStyle(Paint.Style.STROKE);
    this.mPauseHighlightPaint.setColor(-9408400);
    this.mPauseHighlightPaint.setStyle(Paint.Style.FILL);
    this.mPauseTextPaint.setColor(-9408400);
    this.mCheckBgPaint.setColor(SkinManager.getTextColorSubInfo());
    this.mCheckStatePaint.setColor(SkinManager.getTextColorHighlight());
    this.mCheckBgPaint.setStyle(Paint.Style.STROKE);
    this.mCheckStatePaint.setStyle(Paint.Style.FILL);
    setItemSelectedEnable();
    init();
  }

  private void drawBg(Canvas paramCanvas)
  {
    if ((isItemPressed()) && (this.mSelectedIndex == 0) && (this.mOffset == 0))
      return;
    int i = paramCanvas.save();
    paramCanvas.clipRect(0, 0, this.itemLayout.width, this.itemLayout.height);
    paramCanvas.drawColor(SkinManager.getCardColor());
    paramCanvas.restoreToCount(i);
  }

  private void drawCheck(Canvas paramCanvas)
  {
    if (this.mOffset > 0)
    {
      this.mCheckRect.offset(this.mOffset, 0);
      if (!this.mIsChecked)
        break label104;
      paramCanvas.drawCircle(this.mCheckRect.centerX(), this.mCheckRect.centerY(), this.checkbgLayout.width / 2, this.mCheckStatePaint);
      paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getResources(), this.hash, 2130837748), null, this.mCheckRect, this.mPaint);
    }
    while (true)
    {
      this.mCheckRect.offset(-this.mOffset, 0);
      return;
      label104: paramCanvas.drawCircle(this.mCheckRect.centerX(), this.mCheckRect.centerY(), this.checkbgLayout.width / 2, this.mCheckBgPaint);
    }
  }

  private void drawDeleteButton(Canvas paramCanvas, boolean paramBoolean)
  {
    TextPaint localTextPaint = SkinManager.getInstance().getSubTextPaint();
    float f1 = this.mOffset + this.manageLayout.leftMargin + this.manageLayout.width / 2;
    float f2 = this.itemLayout.height / 2;
    float f3 = this.manageLayout.width / 2;
    Paint localPaint1;
    float f4;
    float f5;
    if (paramBoolean)
    {
      localPaint1 = this.mPauseHighlightPaint;
      paramCanvas.drawCircle(f1, f2, f3, localPaint1);
      localTextPaint.getTextBounds("删除", 0, "删除".length(), this.textBound);
      f4 = this.mOffset + this.manageLayout.leftMargin + this.manageLayout.width / 2 - this.textBound.width() / 2;
      f5 = (this.itemLayout.height - this.textBound.top - this.textBound.bottom) / 2;
      if (!paramBoolean)
        break label189;
    }
    label189: for (Paint localPaint2 = this.mManageHighlightPaint; ; localPaint2 = this.mPauseTextPaint)
    {
      paramCanvas.drawText("删除", f4, f5, localPaint2);
      return;
      localPaint1 = this.mPausePaint;
      break;
    }
  }

  private void drawInfo(Canvas paramCanvas, DownloadState paramDownloadState)
  {
    int i = 1;
    TextPaint localTextPaint = SkinManager.getInstance().getSubTextPaint();
    String str1;
    if ((paramDownloadState == DownloadState.downloading) || (paramDownloadState == DownloadState.pausing) || (paramDownloadState == DownloadState.waiting))
    {
      str1 = getStateLable(paramDownloadState);
      if ((str1 != null) && (!str1.equalsIgnoreCase("")));
    }
    label160: label333: label339: 
    do
    {
      return;
      float f1;
      int k;
      float f2;
      if (paramDownloadState == DownloadState.downloading)
      {
        this.mInfoHighlightPaint.getTextBounds(str1, 0, str1.length(), this.textBound);
        f1 = this.mOffset + this.timeLayout.leftMargin;
        int j = this.nameLayout.topMargin + this.nameLayout.height + this.timeLayout.topMargin + (this.timeLayout.height - this.textBound.top - this.textBound.bottom) / 2;
        if (i == 0)
          break label333;
        k = this.timeLayout.topMargin;
        f2 = j - k;
        if ((i == 0) && (paramDownloadState != DownloadState.notstarted))
          break label339;
      }
      for (Object localObject = this.mInfoHighlightPaint; ; localObject = localTextPaint)
      {
        paramCanvas.drawText(str1, f1, f2, (Paint)localObject);
        String str2 = getProcessInfo(paramDownloadState);
        localTextPaint.getTextBounds(str2, 0, str2.length(), this.textBound);
        float f3 = this.mOffset + this.sizeLayout.getRight() + this.timeLayout.leftMargin;
        int m = this.nameLayout.topMargin + this.nameLayout.height + this.timeLayout.topMargin + (this.timeLayout.height - this.textBound.top - this.textBound.bottom) / 2;
        int n = 0;
        if (i != 0)
          n = this.timeLayout.topMargin;
        paramCanvas.drawText(str2, f3, m - n, localTextPaint);
        return;
        i = 0;
        break;
        k = 0;
        break label160;
      }
      if (this.mNode.nodeName.equalsIgnoreCase("program"))
      {
        String str5 = getDurationTime(((ProgramNode)this.mNode).getDuration());
        localTextPaint.getTextBounds(str5, 0, str5.length(), this.textBound);
        paramCanvas.drawText(str5, this.mOffset + this.timeLayout.leftMargin, this.nameLayout.topMargin + this.nameLayout.height + this.timeLayout.topMargin + (this.timeLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
        String str6 = getFileSize((ProgramNode)this.mNode);
        localTextPaint.getTextBounds(str6, 0, str6.length(), this.textBound);
        paramCanvas.drawText(str6, this.mOffset + this.timeLayout.getRight() + this.sizeLayout.leftMargin, this.nameLayout.topMargin + this.nameLayout.height + this.timeLayout.topMargin + (this.timeLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
        if (((ProgramNode)this.mNode).downloadInfo == null);
        for (long l = ((ProgramNode)this.mNode).getUpdateTime(); ; l = ((ProgramNode)this.mNode).downloadInfo.updateTime)
        {
          String str7 = TimeUtil.msToDate2(l * 1000L);
          localTextPaint.getTextBounds(str7, 0, str7.length(), this.textBound);
          paramCanvas.drawText(str7, this.mOffset + this.updateLayout.leftMargin, this.nameLayout.topMargin + this.nameLayout.height + this.timeLayout.topMargin + (this.updateLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
          return;
        }
      }
    }
    while (!this.mNode.nodeName.equalsIgnoreCase("channel"));
    Locale localLocale = Locale.CHINA;
    Object[] arrayOfObject = new Object[i];
    arrayOfObject[0] = Integer.valueOf(((ChannelNode)this.mNode).programCnt);
    String str3 = String.format(localLocale, "%d个文件", arrayOfObject);
    localTextPaint.getTextBounds(str3, 0, str3.length(), this.textBound);
    paramCanvas.drawText(str3, this.mOffset + this.timeLayout.leftMargin, this.nameLayout.topMargin + this.nameLayout.height + this.timeLayout.topMargin + (this.timeLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
    String str4 = String.valueOf(System.currentTimeMillis() / 1000L);
    localTextPaint.getTextBounds(str4, 0, str4.length(), this.textBound);
    paramCanvas.drawText(str4, this.mOffset + this.updateLayout.leftMargin, this.nameLayout.topMargin + this.nameLayout.height + this.updateLayout.topMargin + (this.updateLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
  }

  private void drawLine(Canvas paramCanvas)
  {
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, this.mOffset + this.lineLayout.leftMargin, this.itemLayout.width, this.itemLayout.height - this.lineLayout.height, this.lineLayout.height);
  }

  private void drawManage(Canvas paramCanvas, DownloadState paramDownloadState, boolean paramBoolean)
  {
    TextPaint localTextPaint = SkinManager.getInstance().getSubTextPaint();
    String str = "继续";
    int i = 1;
    Paint localPaint1;
    label120: float f4;
    float f5;
    Paint localPaint2;
    switch (3.$SwitchMap$fm$qingting$qtradio$view$personalcenter$mydownload$DownloadState[paramDownloadState.ordinal()])
    {
    case 2:
    case 3:
    default:
      float f1 = this.mOffset + this.manageLayout.leftMargin + this.manageLayout.width / 2;
      float f2 = this.itemLayout.height / 2;
      float f3 = this.manageLayout.width / 2;
      if (i != 0)
        if (paramBoolean)
        {
          localPaint1 = this.mResumeHighlightPaint;
          paramCanvas.drawCircle(f1, f2, f3, localPaint1);
          localTextPaint.getTextBounds(str, 0, str.length(), this.textBound);
          f4 = this.mOffset + this.manageLayout.leftMargin + this.manageLayout.width / 2 - this.textBound.width() / 2;
          f5 = (this.itemLayout.height - this.textBound.top - this.textBound.bottom) / 2;
          if (!paramBoolean)
            break label293;
          localPaint2 = this.mManageHighlightPaint;
        }
      break;
    case 4:
    case 1:
    case 5:
    }
    while (true)
    {
      paramCanvas.drawText(str, f4, f5, localPaint2);
      return;
      str = "暂停";
      i = 0;
      break;
      str = "暂停";
      i = 0;
      break;
      str = "重试";
      break;
      localPaint1 = this.mResumePaint;
      break label120;
      if (paramBoolean)
      {
        localPaint1 = this.mPauseHighlightPaint;
        break label120;
      }
      localPaint1 = this.mPausePaint;
      break label120;
      label293: if (i != 0)
        localPaint2 = this.mResumeTextPaint;
      else
        localPaint2 = this.mPauseTextPaint;
    }
  }

  private void drawProcessBar(Canvas paramCanvas, DownloadState paramDownloadState)
  {
    if (paramDownloadState != DownloadState.downloading);
    int i;
    do
    {
      return;
      paramCanvas.drawRect(this.mOffset + this.processBgLayout.leftMargin, this.itemLayout.height - this.processBgLayout.topMargin - this.processBgLayout.height, this.mOffset + this.processBgLayout.leftMargin + this.processBgLayout.width, this.itemLayout.height - this.processBgLayout.topMargin, this.mProcessBgPaint);
      i = getPercent(paramDownloadState);
    }
    while (i == 0);
    paramCanvas.drawRect(this.mOffset + this.processBgLayout.leftMargin, this.itemLayout.height - this.processBgLayout.topMargin - this.processBgLayout.height, this.mOffset + this.processBgLayout.leftMargin + i * this.processBgLayout.width / 100, this.itemLayout.height - this.processBgLayout.topMargin, this.mProcessingPaint);
  }

  private void drawTitle(Canvas paramCanvas, boolean paramBoolean)
  {
    String str1 = getName();
    if (str1 == null)
      str1 = "";
    TextPaint localTextPaint = SkinManager.getInstance().getNormalTextPaint();
    if (paramBoolean);
    for (float f = this.manageLayout.leftMargin - this.nameLayout.leftMargin; ; f = this.itemLayout.width - this.nameLayout.leftMargin)
    {
      String str2 = TextUtils.ellipsize(str1, localTextPaint, f, TextUtils.TruncateAt.END).toString();
      localTextPaint.getTextBounds(str2, 0, str2.length(), this.textBound);
      paramCanvas.drawText(str2, this.mOffset + this.nameLayout.leftMargin, this.nameLayout.topMargin + (this.nameLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
      return;
    }
  }

  private void generateRect()
  {
    this.mCheckRect.set((-this.checkbgLayout.width - this.checkStateLayout.width) / 2, (this.itemLayout.height - this.checkStateLayout.height) / 2, (-this.checkbgLayout.width + this.checkStateLayout.width) / 2, (this.itemLayout.height + this.checkStateLayout.height) / 2);
  }

  private String getDurationTime(int paramInt)
  {
    int i = paramInt / 3600;
    int j = paramInt / 60 % 60;
    if (i == 0)
    {
      if (j == 0)
      {
        int k = paramInt % 60;
        Locale localLocale4 = Locale.CHINA;
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = Integer.valueOf(k);
        return String.format(localLocale4, "时长%d秒", arrayOfObject4);
      }
      Locale localLocale3 = Locale.CHINA;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(j);
      return String.format(localLocale3, "时长%d分钟", arrayOfObject3);
    }
    if (j == 0)
    {
      Locale localLocale2 = Locale.CHINA;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(i);
      return String.format(localLocale2, "时长%d小时", arrayOfObject2);
    }
    Locale localLocale1 = Locale.CHINA;
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(i);
    arrayOfObject1[1] = Integer.valueOf(j);
    return String.format(localLocale1, "时长:%d小时%d分", arrayOfObject1);
  }

  private String getFileSize(ProgramNode paramProgramNode)
  {
    if (paramProgramNode.downloadInfo == null);
    for (int i = 125 * (24 * paramProgramNode.getDuration()); i < 0; i = paramProgramNode.downloadInfo.fileSize)
      return "";
    if (i < 1000)
      return i + "B";
    if (i < 1000000)
    {
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Float.valueOf(i / 1000.0F);
      return String.format("%.1fkB", arrayOfObject3);
    }
    if (i < 1000000000)
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Float.valueOf(i / 1000000.0F);
      return String.format("%.1fMB", arrayOfObject2);
    }
    if (i < 1000000000000L)
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Float.valueOf(i / 1.0E+09F);
      return String.format("%.1fG", arrayOfObject1);
    }
    return "";
  }

  private int getMaxOffset()
  {
    return this.checkbgLayout.leftMargin + this.checkbgLayout.width;
  }

  private String getName()
  {
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
      return ((ProgramNode)this.mNode).title;
    if (this.mNode.nodeName.equalsIgnoreCase("channel"))
      return ((ChannelNode)this.mNode).title;
    return "";
  }

  private int getPercent(DownloadState paramDownloadState)
  {
    if (this.mNode == null);
    while ((!this.mNode.nodeName.equalsIgnoreCase("program")) || (((ProgramNode)this.mNode).downloadInfo == null))
      return 0;
    int i = ((ProgramNode)this.mNode).downloadInfo.progress;
    if (i > 100)
      i = 100;
    if (i < 0)
      i = 0;
    return i;
  }

  private String getProcessInfo(DownloadState paramDownloadState)
  {
    if (this.mNode == null)
      return "";
    if (!this.mNode.nodeName.equalsIgnoreCase("program"))
      return "";
    int i = getPercent(paramDownloadState);
    if (((ProgramNode)this.mNode).downloadInfo == null);
    for (int j = 125 * (24 * ((ProgramNode)this.mNode).getDuration()); ; j = ((ProgramNode)this.mNode).downloadInfo.fileSize)
    {
      int k = (int)(j * (i / 100.0D));
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = SizeInfo.getFileSize(k);
      arrayOfObject[1] = SizeInfo.getFileSize(j);
      return String.format(localLocale, "%s/%s", arrayOfObject);
    }
  }

  private int getSelectedIndex()
  {
    if ((this.mOffset == 0) && (pointInManage(this.mLastMotionX, this.mLastMotionY)))
    {
      if (this.mNode.nodeName.equalsIgnoreCase("channel"));
      while (!isDownloading())
        return 1;
      return 1;
    }
    return 0;
  }

  private DownloadState getState()
  {
    DownloadState localDownloadState1 = DownloadState.none;
    if ((this.mNode == null) || (!this.mNode.nodeName.equalsIgnoreCase("program")))
      return localDownloadState1;
    if (((ProgramNode)this.mNode).downloadInfo == null)
      return DownloadState.notstarted;
    if (this.mPaused)
      return DownloadState.pausing;
    switch (((ProgramNode)this.mNode).downloadInfo.state)
    {
    default:
      return localDownloadState1;
    case 3:
      return DownloadState.completed;
    case 1:
      return DownloadState.downloading;
    case 2:
      DownloadState localDownloadState2 = DownloadState.pausing;
      this.mPaused = true;
      return localDownloadState2;
    case 4:
      return DownloadState.error;
    case 0:
    }
    return DownloadState.waiting;
  }

  private String getStateLable(DownloadState paramDownloadState)
  {
    switch (3.$SwitchMap$fm$qingting$qtradio$view$personalcenter$mydownload$DownloadState[paramDownloadState.ordinal()])
    {
    default:
      return null;
    case 1:
      return "正在下载";
    case 2:
      return "已暂停";
    case 3:
      return "等待下载";
    case 4:
    }
    return "等待中";
  }

  private String getUpdateTime(String paramString)
  {
    try
    {
      long l2 = Long.parseLong(paramString);
      l1 = l2;
      return TimeUtil.msToDate2(l1 * 1000L);
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      while (true)
      {
        long l1;
        localNumberFormatException1.printStackTrace();
        try
        {
          int i = Integer.parseInt(paramString);
          l1 = i;
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          localNumberFormatException2.printStackTrace();
        }
      }
    }
    return "";
  }

  @TargetApi(11)
  private void init()
  {
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      this.mAnimator = new ValueAnimator();
      ((ValueAnimator)this.mAnimator).setDuration(200L);
      ((ValueAnimator)this.mAnimator).addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
        {
          MyDownloadItemView.this.setPosition(((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue());
        }
      });
      return;
    }
    this.motionController = new MotionController(this);
  }

  private boolean isDownloading()
  {
    DownloadState localDownloadState = getState();
    return (localDownloadState == DownloadState.downloading) || (localDownloadState == DownloadState.pausing);
  }

  private boolean pointInManage(float paramFloat1, float paramFloat2)
  {
    return (paramFloat1 > this.manageLayout.leftMargin) && (paramFloat1 < this.manageLayout.getRight()) && (paramFloat2 > (this.itemLayout.height - this.manageLayout.height) / 2) && (paramFloat2 < (this.itemLayout.height + this.manageLayout.height) / 2);
  }

  private void setPosition(float paramFloat)
  {
    this.mOffset = ((int)paramFloat);
    invalidate();
  }

  @TargetApi(11)
  private void startHideManageAnimation()
  {
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      ValueAnimator localValueAnimator = (ValueAnimator)this.mAnimator;
      float[] arrayOfFloat = new float[2];
      arrayOfFloat[0] = getMaxOffset();
      arrayOfFloat[1] = 0.0F;
      localValueAnimator.setFloatValues(arrayOfFloat);
      ((ValueAnimator)this.mAnimator).start();
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new TweenProperty("position", getMaxOffset(), 0.0F, 10.0F, new Quad.EaseIn()));
    FrameTween.to(this.motionController, this, localArrayList, FrameTween.SyncType.ASYNC);
  }

  @TargetApi(11)
  private void startShowManageAnimation(int paramInt)
  {
    if (QtApiLevelManager.isApiLevelSupported(11))
    {
      ValueAnimator localValueAnimator = (ValueAnimator)this.mAnimator;
      float[] arrayOfFloat = new float[2];
      arrayOfFloat[0] = 0.0F;
      arrayOfFloat[1] = paramInt;
      localValueAnimator.setFloatValues(arrayOfFloat);
      ((ValueAnimator)this.mAnimator).start();
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new TweenProperty("position", 0.0F, paramInt, 10.0F, new Quad.EaseIn()));
    FrameTween.to(this.motionController, this, localArrayList, FrameTween.SyncType.ASYNC);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    int i = 1;
    if (this.mNode == null)
      return;
    paramCanvas.setDrawFilter(this.filter);
    paramCanvas.save();
    drawBg(paramCanvas);
    DownloadState localDownloadState = getState();
    if ((localDownloadState == DownloadState.downloading) || (localDownloadState == DownloadState.pausing) || (localDownloadState == DownloadState.none) || (localDownloadState == DownloadState.waiting))
    {
      int m = i;
      drawTitle(paramCanvas, m);
      if ((localDownloadState != DownloadState.downloading) && (localDownloadState != DownloadState.pausing) && (localDownloadState != DownloadState.waiting))
        break label163;
      drawProcessBar(paramCanvas, localDownloadState);
      if ((!isItemPressed()) || (this.mSelectedIndex != i))
        break label158;
      drawManage(paramCanvas, localDownloadState, i);
    }
    label158: int j;
    label163: 
    while (localDownloadState != DownloadState.none)
      while (true)
      {
        drawInfo(paramCanvas, localDownloadState);
        drawCheck(paramCanvas);
        drawLine(paramCanvas);
        paramCanvas.restore();
        return;
        int n = 0;
        break;
        j = 0;
      }
    if ((isItemPressed()) && (this.mSelectedIndex == j));
    while (true)
    {
      drawDeleteButton(paramCanvas, j);
      break;
      int k = 0;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.nameLayout.scaleToBounds(this.itemLayout);
    this.updateLayout.scaleToBounds(this.itemLayout);
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.sizeLayout.scaleToBounds(this.itemLayout);
    this.timeLayout.scaleToBounds(this.itemLayout);
    this.checkbgLayout.scaleToBounds(this.itemLayout);
    this.checkStateLayout.scaleToBounds(this.checkbgLayout);
    this.processBgLayout.scaleToBounds(this.itemLayout);
    this.manageLayout.scaleToBounds(this.itemLayout);
    this.mInfoHighlightPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mCheckBgPaint.setStrokeWidth(this.checkStateLayout.leftMargin);
    generateRect();
    this.mPausePaint.setStrokeWidth(this.manageLayout.topMargin);
    this.mResumePaint.setStrokeWidth(this.manageLayout.topMargin);
    this.mPauseTextPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mResumeTextPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mManageHighlightPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  public void onMotionCancel(MotionController paramMotionController)
  {
  }

  public void onMotionComplete(MotionController paramMotionController)
  {
  }

  public void onMotionProgress(MotionController paramMotionController, float paramFloat1, float paramFloat2)
  {
    setPosition(paramFloat1);
  }

  public void onMotionStart(MotionController paramMotionController)
  {
  }

  public void onTargetChange(MotionController paramMotionController, float paramFloat)
  {
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((!this.mInTouchMode) && (paramMotionEvent.getAction() != 0));
    do
    {
      do
      {
        do
        {
          return true;
          switch (paramMotionEvent.getAction())
          {
          default:
            return true;
          case 0:
            this.mLastMotionX = paramMotionEvent.getX();
            this.mLastMotionY = paramMotionEvent.getY();
            this.mInTouchMode = true;
            this.mSelectedIndex = getSelectedIndex();
            invalidate();
            return true;
          case 2:
            this.mLastMotionX = paramMotionEvent.getX();
            this.mLastMotionY = paramMotionEvent.getY();
          case 3:
          case 1:
          }
        }
        while (getSelectedIndex() == this.mSelectedIndex);
        this.mSelectedIndex = -1;
        this.mInTouchMode = false;
      }
      while (!isItemPressed());
      invalidate();
      return true;
      this.mSelectedIndex = -1;
    }
    while (!isItemPressed());
    invalidate();
    return true;
    if (this.mSelectedIndex == 0)
      if (this.mOffset > 0)
        dispatchActionEvent("itemSelect", null);
    while (true)
    {
      invalidate();
      return true;
      if (this.mNode != null)
        if (this.mNode.nodeName.equalsIgnoreCase("program"))
        {
          if ((InfoManager.getInstance().root().mDownLoadInfoNode.hasInDownLoading(this.mNode) < 0) && (InfoManager.getInstance().root().mDownLoadInfoNode.hasNodeDownloaded(this.mNode)))
          {
            PlayerAgent.getInstance().addPlaySource(6);
            ControllerManager.getInstance().redirectToPlayViewByNode(this.mNode, true);
          }
        }
        else if (this.mNode.nodeName.equalsIgnoreCase("channel"))
        {
          ControllerManager.getInstance().redirectToDownloadProgramsController((ChannelNode)this.mNode);
          continue;
          if ((this.mSelectedIndex == 1) && (this.mNode != null))
          {
            DownloadState localDownloadState = getState();
            if (localDownloadState == DownloadState.pausing)
            {
              this.mPaused = false;
              InfoManager.getInstance().root().mDownLoadInfoNode.resumeDownLoad(this.mNode);
            }
            else if ((localDownloadState == DownloadState.downloading) || (localDownloadState == DownloadState.waiting))
            {
              this.mPaused = true;
              InfoManager.getInstance().root().mDownLoadInfoNode.pauseDownLoad(this.mNode, true);
            }
            else if (this.mNode.nodeName.equalsIgnoreCase("channel"))
            {
              AlertParam.Builder localBuilder = new AlertParam.Builder();
              localBuilder.setMessage("确定删除" + ((ChannelNode)this.mNode).title + "及其中的所有节目吗？").addButton("取消").addButton("确定").setListener(new AlertParam.OnButtonClickListener()
              {
                public void onClick(int paramAnonymousInt, boolean paramAnonymousBoolean)
                {
                  if (paramAnonymousInt == 1)
                    InfoManager.getInstance().root().mDownLoadInfoNode.delDownLoad((ChannelNode)MyDownloadItemView.this.mNode, true);
                  MyDownloadItemView.this.dispatchActionEvent("cancelPop", null);
                }
              });
              AlertParam localAlertParam = localBuilder.create();
              EventDispacthManager.getInstance().dispatchAction("showAlert", localAlertParam);
            }
          }
        }
    }
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("content"))
      if (paramObject != null);
    do
    {
      int i;
      do
      {
        do
        {
          return;
          this.mNode = ((Node)paramObject);
          if (this.mNode.nodeName.equalsIgnoreCase("program"))
            if (((ProgramNode)this.mNode).downloadInfo != null)
            {
              String str = ((ProgramNode)this.mNode).downloadInfo.id;
              DownloadTask localDownloadTask = DownloadService.getInstance(QTApplication.appContext).getTask(str);
              if ((localDownloadTask != null) && (localDownloadTask.getDownloadState() == fm.qingting.downloadnew.DownloadState.PAUSED))
                this.mPaused = true;
            }
          while (true)
          {
            invalidate();
            return;
            this.mPaused = false;
            continue;
            this.mPaused = false;
            continue;
            this.mPaused = false;
          }
          if (paramString.equalsIgnoreCase("checkState"))
          {
            this.mIsChecked = ((Boolean)paramObject).booleanValue();
            invalidate();
            return;
          }
          if (!paramString.equalsIgnoreCase("showManage"))
            break;
        }
        while (this.mOffset > 0);
        startShowManageAnimation(((Integer)paramObject).intValue());
        return;
        if (!paramString.equalsIgnoreCase("showManageWithoutShift"))
          break;
        i = ((Integer)paramObject).intValue();
      }
      while (this.mOffset == i);
      this.mOffset = i;
      invalidate();
      return;
    }
    while ((!paramString.equalsIgnoreCase("hideManage")) || (this.mOffset == 0));
    startHideManageAnimation();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.mydownload.MyDownloadItemView
 * JD-Core Version:    0.6.2
 */