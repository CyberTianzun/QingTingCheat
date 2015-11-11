package fm.qingting.qtradio.view.virtualchannels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtListItemView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.PlayHistoryNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.ReserveInfoNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.popviews.CustomPopActionParam;
import fm.qingting.qtradio.view.popviews.CustomPopActionParam.Builder;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.TimeUtil;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class ChannelDetailItemView extends QtListItemView
{
  private static final String DOWNLOADED = "已下载";
  private static final String DOWNLOADING = "正在下载";
  private static final int INDEX_CLOSE = 2;
  private static final int INDEX_ITEM = 0;
  private static final int INDEX_TRIANGLE = 1;
  private static final int INDEX_UNKNOWN = -1;
  private final long TEN_MINUTE = 600000L;
  private final ViewLayout channelnameLayout = this.itemLayout.createChildLT(720, 45, 30, 5, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final DrawFilter filter = SkinManager.getInstance().getDrawFilter();
  private int hash = -24;
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 126, 720, 126, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout livingLayout = this.itemLayout.createChildLT(10, 90, 0, 18, ViewLayout.SCALE_FLAG_SLTCW);
  boolean mBelongToPodcasterInfo = false;
  private int mDownloadState;
  private final Paint mHighlightPaint = new Paint();
  private boolean mInTouchMode = false;
  private boolean mIsLiving = false;
  private int mItemHeight = 100;
  private float mLastMotionX = 0.0F;
  private float mLastMotionY = 0.0F;
  private final Paint mLinePaint = new Paint();
  private final Rect mLivingRect = new Rect();
  private Node mNode;
  private final Paint mPaint = new Paint();
  private boolean mRemind = false;
  private boolean mSameDay = false;
  private int mSelectedIndex = -1;
  private final TextPaint mSubPaint = new TextPaint();
  private final TextPaint mTimePaint = SkinManager.getInstance().getSubTextPaint();
  private int mTitleHeight = 30;
  private StaticLayout mTitleLayout;
  private final Paint mTitlePaint = new TextPaint();
  private final Rect mTriangularRect = new Rect();
  private final ViewLayout programLayout = this.itemLayout.createChildLT(720, 45, 30, 26, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout recentCloseButtonLayout = this.itemLayout.createChildLT(20, 26, 20, 30, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout recentIndicatorLayout = this.itemLayout.createChildLT(20, 26, 32, 30, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout recentItemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 86, 720, 50, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout recentLineLayout = this.itemLayout.createChildLT(720, 1, 30, 1, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout recentTitleLayout = this.itemLayout.createChildLT(570, 50, 80, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout remindLayout = this.itemLayout.createChildLT(22, 22, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final Rect textBound = new Rect();
  private final ViewLayout triangularLayout = this.itemLayout.createChildLT(22, 22, 20, 20, ViewLayout.SCALE_FLAG_SLTCW);

  public ChannelDetailItemView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.hash = paramInt;
    this.mHighlightPaint.setColor(SkinManager.getTextColorHighlight2());
    this.mHighlightPaint.setStyle(Paint.Style.FILL);
    this.mSubPaint.setColor(SkinManager.getTextColorHighlight2());
    this.mSubPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mLinePaint.setColor(SkinManager.getDividerColor_new());
    setOnClickListener(this);
    setItemSelectedEnable();
  }

  private void doClickEvent()
  {
    if ((this.mNode == null) || (this.mSelectedIndex == -1))
      return;
    if (this.mSelectedIndex == 2)
    {
      QTMSGManage.getInstance().sendStatistcsMessage("resumerecent_close");
      EventDispacthManager.getInstance().dispatchAction("closerecentplay", null);
      return;
    }
    if (this.mSelectedIndex == 1)
    {
      CustomPopActionParam.Builder localBuilder = new CustomPopActionParam.Builder();
      localBuilder.setTitle(getTitle()).addNode(this.mNode);
      if (this.mDownloadState == 0)
        localBuilder.addButton(1);
      localBuilder.addButton(0);
      CustomPopActionParam localCustomPopActionParam = localBuilder.create();
      EventDispacthManager.getInstance().dispatchAction("showoperatepop", localCustomPopActionParam);
      return;
    }
    Node localNode;
    int i;
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
    {
      int j = ((ProgramNode)this.mNode).getCurrPlayStatus();
      localNode = null;
      i = j;
    }
    while (true)
    {
      if (i == 2)
      {
        if (InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.isExisted(this.mNode))
          InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.cancelReserve(((ProgramNode)this.mNode).id);
        while (true)
        {
          invalidate();
          label203: if (!this.mBelongToPodcasterInfo)
            break label336;
          QTMSGManage.getInstance().sendStatistcsMessage("PodcasterInfo", "收听");
          return;
          if (this.mNode.nodeName.equalsIgnoreCase("ondemandprogram"))
          {
            i = 3;
            localNode = null;
            break;
          }
          if (!this.mNode.nodeName.equalsIgnoreCase("playhistory"))
            break label347;
          QTMSGManage.getInstance().sendStatistcsMessage("resumerecent_play");
          localNode = ((PlayHistoryNode)this.mNode).playNode;
          i = 1;
          break;
          InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.addReserveNode((ProgramNode)this.mNode);
        }
      }
      ControllerManager localControllerManager = ControllerManager.getInstance();
      if (localNode != null);
      while (true)
      {
        localControllerManager.redirectToPlayViewByNode(localNode, true);
        break label203;
        label336: break;
        localNode = this.mNode;
      }
      label347: i = 1;
      localNode = null;
    }
  }

  private void drawBg(Canvas paramCanvas)
  {
    if ((isItemPressed()) && (this.mSelectedIndex == 0))
    {
      int i = paramCanvas.save();
      paramCanvas.clipRect(0, 0, this.itemLayout.width, this.mItemHeight);
      paramCanvas.drawColor(SkinManager.getItemHighlightMaskColor_new());
      paramCanvas.restoreToCount(i);
    }
  }

  private void drawChannel(Canvas paramCanvas, float paramFloat1, float paramFloat2)
  {
    if ((this.mNode.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)this.mNode).channelType == 1));
    for (String str1 = getUpdateTimeString((ProgramNode)this.mNode); ; str1 = null)
    {
      if (str1 == null)
        return;
      TextPaint localTextPaint = SkinManager.getInstance().getSubTextPaint();
      String str2 = TextUtils.ellipsize(str1, localTextPaint, paramFloat2 - paramFloat1, TextUtils.TruncateAt.END).toString();
      localTextPaint.getTextBounds(str2, 0, str2.length(), this.textBound);
      paramCanvas.drawText(str2, paramFloat1 + this.channelnameLayout.leftMargin, this.programLayout.topMargin + this.mTitleHeight + (this.channelnameLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
      return;
    }
  }

  private int drawDownloadState(Canvas paramCanvas)
  {
    int i = this.mDownloadState;
    String str = null;
    switch (i)
    {
    case 0:
    case 2:
    default:
    case 1:
    case 3:
    }
    while (str == null)
    {
      return this.mTriangularRect.left;
      str = "正在下载";
      continue;
      str = "已下载";
    }
    this.mSubPaint.getTextBounds(str, 0, str.length(), this.textBound);
    paramCanvas.drawText(str, this.mTriangularRect.left - this.textBound.width() - this.channelnameLayout.leftMargin, this.programLayout.topMargin + this.mTitleHeight + (this.channelnameLayout.height - this.textBound.top - this.textBound.bottom) / 2, this.mSubPaint);
    return this.mTriangularRect.left - this.textBound.width() - this.channelnameLayout.leftMargin;
  }

  private void drawLine(Canvas paramCanvas)
  {
    paramCanvas.drawLine(this.lineLayout.leftMargin, this.mItemHeight - this.lineLayout.height, this.itemLayout.width, this.mItemHeight - this.lineLayout.height, this.mLinePaint);
  }

  private void drawLivingLabel(Canvas paramCanvas)
  {
    paramCanvas.drawRect(this.mLivingRect, this.mHighlightPaint);
  }

  private void drawNormalItem(Canvas paramCanvas)
  {
    drawBg(paramCanvas);
    drawTriangular(paramCanvas);
    drawTitle(paramCanvas);
    drawSubInfo(paramCanvas);
    drawLine(paramCanvas);
  }

  private void drawRecentBg(Canvas paramCanvas)
  {
    Paint localPaint = new Paint();
    localPaint.setStyle(Paint.Style.FILL);
    localPaint.setColor(-1513237);
    paramCanvas.drawRect(0.0F, 0.0F, this.recentItemLayout.width, this.recentItemLayout.height, localPaint);
  }

  private void drawRecentCloseButton(Canvas paramCanvas)
  {
    paramCanvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), 2130837719), this.recentCloseButtonLayout.width, this.recentCloseButtonLayout.height, false), this.recentItemLayout.width - this.recentCloseButtonLayout.width - this.recentCloseButtonLayout.leftMargin, (this.recentItemLayout.height - this.recentCloseButtonLayout.height) / 2, new Paint());
  }

  private void drawRecentIndicator(Canvas paramCanvas)
  {
    paramCanvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), 2130837753), this.recentIndicatorLayout.width, this.recentIndicatorLayout.height, false), this.recentIndicatorLayout.leftMargin, (this.recentItemLayout.height - this.recentIndicatorLayout.height) / 2, new Paint());
  }

  private void drawRecentItem(Canvas paramCanvas)
  {
    drawRecentBg(paramCanvas);
    drawRecentIndicator(paramCanvas);
    drawRecentTitle(paramCanvas);
    drawRecentCloseButton(paramCanvas);
    drawRecentLine(paramCanvas);
  }

  private void drawRecentLine(Canvas paramCanvas)
  {
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, this.recentLineLayout.leftMargin, this.recentItemLayout.width, this.recentItemLayout.height - this.recentLineLayout.height, this.recentLineLayout.height);
  }

  private void drawRecentTitle(Canvas paramCanvas)
  {
    String str1 = "继续收听  “";
    if ((this.mNode != null) && (this.mNode.nodeName != null) && (this.mNode.nodeName.equalsIgnoreCase("playhistory")))
      str1 = str1 + ((ProgramNode)((PlayHistoryNode)this.mNode).playNode).title + "”";
    TextPaint localTextPaint = SkinManager.getInstance().getSubTextPaint();
    String str2 = TextUtils.ellipsize(str1, localTextPaint, this.recentTitleLayout.width, TextUtils.TruncateAt.END).toString();
    Rect localRect = new Rect();
    localTextPaint.getTextBounds(str2, 0, str2.length(), localRect);
    paramCanvas.drawText(str2, this.recentTitleLayout.leftMargin, this.recentTitleLayout.topMargin - localRect.top + (this.recentItemLayout.height - localRect.height()) / 2, localTextPaint);
  }

  private void drawReminder(Canvas paramCanvas, float paramFloat1, float paramFloat2)
  {
    paramCanvas.drawCircle(paramFloat1 + this.remindLayout.width / 2, paramFloat2, this.remindLayout.width / 2, this.mHighlightPaint);
  }

  private float drawStateLabel(Canvas paramCanvas)
  {
    int i;
    String str;
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
    {
      i = ((ProgramNode)this.mNode).getCurrPlayStatus();
      switch (i)
      {
      default:
        str = "";
      case 1:
      case 2:
      case 3:
      }
    }
    while (true)
      if ((str == null) || (str.equalsIgnoreCase("")))
      {
        return this.itemLayout.leftMargin;
        i = 3;
        break;
        str = "直播";
        this.mTitlePaint.setColor(SkinManager.getTextColorHighlight2());
        continue;
        if (InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.isExisted(this.mNode));
        for (str = "已预约"; ; str = "预约")
        {
          this.mTitlePaint.setColor(SkinManager.getTextColorHighlight2());
          break;
        }
        return this.itemLayout.leftMargin;
      }
    this.mTitlePaint.getTextBounds(str, 0, str.length(), this.textBound);
    paramCanvas.drawText(str, this.itemLayout.leftMargin + this.channelnameLayout.leftMargin, this.programLayout.topMargin + this.mTitleHeight + (this.channelnameLayout.height - this.textBound.top - this.textBound.bottom) / 2, this.mTitlePaint);
    return this.itemLayout.leftMargin + this.channelnameLayout.leftMargin + this.textBound.width();
  }

  private void drawSubInfo(Canvas paramCanvas)
  {
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
      drawChannel(paramCanvas, drawStateLabel(paramCanvas), drawTime(paramCanvas));
  }

  private float drawTime(Canvas paramCanvas)
  {
    if (this.mNode.nodeName.equalsIgnoreCase("program"));
    String str;
    switch (((ProgramNode)this.mNode).getCurrPlayStatus())
    {
    default:
      str = "";
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      int i = drawDownloadState(paramCanvas);
      this.mTimePaint.getTextBounds(str, 0, str.length(), this.textBound);
      paramCanvas.drawText(str, i - this.textBound.width() - this.channelnameLayout.leftMargin, this.programLayout.topMargin + this.mTitleHeight + (this.channelnameLayout.height - this.textBound.top - this.textBound.bottom) / 2, this.mTimePaint);
      return i - this.textBound.width() - this.channelnameLayout.leftMargin;
      str = TimeUtil.msToDate3(1000L * ((ProgramNode)this.mNode).getAbsoluteStartTime()) + "-" + TimeUtil.msToDate3(1000L * ((ProgramNode)this.mNode).getAbsoluteEndTime());
      continue;
      str = getDurationTime(((ProgramNode)this.mNode).getDuration());
    }
  }

  private void drawTitle(Canvas paramCanvas)
  {
    if (this.mIsLiving)
      drawLivingLabel(paramCanvas);
    if (this.mRemind)
      drawReminder(paramCanvas, this.itemLayout.leftMargin + this.remindLayout.leftMargin, this.programLayout.topMargin + this.programLayout.height / 2);
    int i = paramCanvas.save();
    paramCanvas.translate(this.programLayout.leftMargin, this.programLayout.topMargin);
    this.mTitleLayout.draw(paramCanvas);
    paramCanvas.restoreToCount(i);
  }

  private void drawTriangular(Canvas paramCanvas)
  {
    paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getResources(), this.hash, 2130838033), null, this.mTriangularRect, this.mPaint);
  }

  private void generateRect()
  {
    this.mTriangularRect.set(this.itemLayout.leftMargin + this.itemLayout.width - this.triangularLayout.leftMargin - this.triangularLayout.width, this.mItemHeight - this.triangularLayout.topMargin - this.triangularLayout.height, this.itemLayout.leftMargin + this.itemLayout.width - this.triangularLayout.leftMargin, this.mItemHeight - this.triangularLayout.topMargin);
    int i = (this.mItemHeight - this.livingLayout.height) / 2;
    this.mLivingRect.set(this.livingLayout.leftMargin, i, this.livingLayout.leftMargin + this.livingLayout.width, i + this.livingLayout.height);
  }

  private String getDurationTime(int paramInt)
  {
    int i = paramInt / 60;
    String str = "" + i + ":";
    int j = paramInt % 60;
    if (j < 10)
      return str + "0" + j;
    return str + j;
  }

  private int getSelectedIndex()
  {
    if ((this.mNode != null) && (this.mNode.nodeName != null) && (this.mNode.nodeName.equalsIgnoreCase("playhistory")) && (this.mLastMotionX > this.recentItemLayout.width - this.recentCloseButtonLayout.leftMargin - 2 * this.recentCloseButtonLayout.width) && (this.mLastMotionY > 0.0F) && (this.mLastMotionY < this.recentItemLayout.height))
      return 2;
    if ((this.mLastMotionX > this.itemLayout.width - this.triangularLayout.leftMargin - 2 * this.triangularLayout.width) && (this.mLastMotionY > this.mItemHeight - this.triangularLayout.topMargin - 2 * this.triangularLayout.height))
      return 1;
    return 0;
  }

  private int getThisHeight()
  {
    if (this.mNode == null)
      return this.itemLayout.height;
    this.mIsLiving = isPlaying();
    this.mDownloadState = InfoManager.getInstance().root().mDownLoadInfoNode.hasDownLoad(this.mNode);
    String str = "";
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
    {
      if (this.mRemind)
        str = "    " + ((ProgramNode)this.mNode).title;
    }
    else
      if (!this.mIsLiving)
        break label208;
    label208: for (TextPaint localTextPaint = SkinManager.getInstance().getmHighlightTextPaint2(); ; localTextPaint = SkinManager.getInstance().getNormalTextPaint())
    {
      this.mTitleLayout = new StaticLayout(str, localTextPaint, this.itemLayout.width - this.programLayout.leftMargin, Layout.Alignment.ALIGN_NORMAL, 1.2F, 0.5F, false);
      this.mTitleHeight = this.mTitleLayout.getHeight();
      return this.programLayout.topMargin + this.mTitleHeight + this.channelnameLayout.height + 2 * this.channelnameLayout.topMargin;
      str = ((ProgramNode)this.mNode).title;
      break;
    }
  }

  private String getTitle()
  {
    if (this.mNode == null)
      return "";
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
      return ((ProgramNode)this.mNode).title;
    if (this.mNode.nodeName.equalsIgnoreCase("channel"))
      return ((ChannelNode)this.mNode).title;
    return "";
  }

  private String getUpdateTimeString(ProgramNode paramProgramNode)
  {
    long l1 = paramProgramNode.getUpdateTime();
    if (this.mSameDay)
    {
      long l2 = System.currentTimeMillis() - l1;
      if (l2 <= 600000L)
        return "刚刚更新";
      int i = (int)(l2 / 1000L / 3600L);
      if (i > 0)
      {
        Locale localLocale2 = Locale.CHINA;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(i);
        return String.format(localLocale2, "%d小时前", arrayOfObject2);
      }
      int j = (int)(l2 / 1000L / 60L % 60L);
      if (j > 0)
      {
        Locale localLocale1 = Locale.CHINA;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(j);
        return String.format(localLocale1, "%d分钟前", arrayOfObject1);
      }
      return "刚刚更新";
    }
    return TimeUtil.msToDate5(l1);
  }

  private boolean isPlaying()
  {
    if (this.mNode == null)
      return false;
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if (localNode == null)
      return false;
    if (!this.mNode.nodeName.equalsIgnoreCase(localNode.nodeName))
      return false;
    return (this.mNode.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)this.mNode).id == ((ProgramNode)localNode).id);
  }

  private boolean isSameDay()
  {
    if (this.mNode == null)
      return false;
    if ((this.mNode.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)this.mNode).channelType == 1))
    {
      long l = ((ProgramNode)this.mNode).getUpdateTime();
      Calendar localCalendar = Calendar.getInstance();
      int i = localCalendar.get(1);
      int j = localCalendar.get(6);
      localCalendar.setTimeInMillis(l);
      int k = localCalendar.get(1);
      int m = localCalendar.get(6);
      if ((i == k) && (j == m))
        return true;
    }
    return false;
  }

  private boolean shouldRemind()
  {
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
    {
      Node localNode = this.mNode.parent;
      if ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("channel")) || (((ChannelNode)localNode).channelType != 1));
    }
    return false;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mNode == null)
      return;
    paramCanvas.setDrawFilter(this.filter);
    paramCanvas.save();
    if (this.mNode.nodeName.equalsIgnoreCase("playhistory"))
      drawRecentItem(paramCanvas);
    while (true)
    {
      paramCanvas.restore();
      return;
      drawNormalItem(paramCanvas);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.programLayout.scaleToBounds(this.itemLayout);
    this.channelnameLayout.scaleToBounds(this.itemLayout);
    this.triangularLayout.scaleToBounds(this.itemLayout);
    this.livingLayout.scaleToBounds(this.itemLayout);
    this.remindLayout.scaleToBounds(this.itemLayout);
    this.mTitlePaint.setTextSize(0.5F * this.channelnameLayout.height);
    this.mItemHeight = getThisHeight();
    generateRect();
    this.recentItemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.recentIndicatorLayout.scaleToBounds(this.recentItemLayout);
    this.recentTitleLayout.scaleToBounds(this.recentItemLayout);
    this.recentCloseButtonLayout.scaleToBounds(this.recentItemLayout);
    this.recentLineLayout.scaleToBounds(this.recentItemLayout);
    if ((this.mNode != null) && (this.mNode.nodeName.equalsIgnoreCase("playhistory")))
    {
      setMeasuredDimension(this.recentItemLayout.width, this.recentItemLayout.height);
      return;
    }
    setMeasuredDimension(this.itemLayout.width, this.mItemHeight);
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
    doClickEvent();
    return true;
  }

  public void setBelongToPodcasterInfo(boolean paramBoolean)
  {
    this.mBelongToPodcasterInfo = paramBoolean;
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("content"))
    {
      if (!(paramObject instanceof Node))
        break label38;
      this.mNode = ((Node)paramObject);
      this.mSameDay = isSameDay();
    }
    while (true)
    {
      requestLayout();
      return;
      label38: if ((paramObject instanceof HashMap))
      {
        HashMap localHashMap = (HashMap)paramObject;
        this.mNode = ((Node)localHashMap.get("node"));
        this.mSameDay = isSameDay();
        this.mRemind = ((Boolean)localHashMap.get("remind")).booleanValue();
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.virtualchannels.ChannelDetailItemView
 * JD-Core Version:    0.6.2
 */