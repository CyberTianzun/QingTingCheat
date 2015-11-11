package fm.qingting.qtradio.view.scheduleview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtListItemView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.Download;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.OnDemandProgramNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.utils.TimeUtil;
import java.util.Locale;

public class BatchDownloadItemView extends QtListItemView
{
  private final ViewLayout checkStateLayout = this.checkbgLayout.createChildLT(30, 22, 2, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout checkbgLayout = this.itemLayout.createChildLT(48, 48, 30, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final DrawFilter filter = SkinManager.getInstance().getDrawFilter();
  private int hash = -24;
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 136, 720, 136, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout labelLayout = this.itemLayout.createChildLT(100, 45, 30, 10, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final TextPaint mChannelPaint = new TextPaint();
  private Paint mCheckBgPaint = new Paint();
  private Rect mCheckRect = new Rect();
  private Paint mCheckStatePaint = new Paint();
  private int mDownloading = 0;
  private boolean mIsChecked = false;
  private final Paint mLabelHighlightPaint = new Paint();
  private final Paint mLabelPaint = new Paint();
  private Node mNode;
  private final Paint mPaint = new Paint();
  private final ViewLayout programLayout = this.itemLayout.createChildLT(600, 45, 30, 20, ViewLayout.SCALE_FLAG_SLTCW);
  private Rect textBound = new Rect();
  private final ViewLayout timeLayout = this.itemLayout.createChildLT(300, 45, 300, 10, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout updateLayout = this.itemLayout.createChildLT(300, 45, 550, 10, ViewLayout.SCALE_FLAG_SLTCW);

  public BatchDownloadItemView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.hash = paramInt;
    setBackgroundColor(SkinManager.getCardColor());
    this.mChannelPaint.setColor(SkinManager.getTextColorSubInfo());
    this.mLabelPaint.setColor(SkinManager.getTextColorHighlight());
    this.mLabelHighlightPaint.setColor(SkinManager.getLiveColor());
    this.mCheckBgPaint.setColor(SkinManager.getTextColorSubInfo());
    this.mCheckStatePaint.setColor(SkinManager.getTextColorHighlight());
    this.mCheckBgPaint.setStyle(Paint.Style.STROKE);
    this.mCheckStatePaint.setStyle(Paint.Style.FILL);
    setItemSelectedEnable();
    setOnClickListener(this);
  }

  private void drawBg(Canvas paramCanvas)
  {
  }

  private void drawCheckState(Canvas paramCanvas)
  {
    if (this.mDownloading == 0)
    {
      if (this.mIsChecked)
      {
        paramCanvas.drawCircle(this.mCheckRect.centerX(), this.mCheckRect.centerY(), this.checkbgLayout.width / 2, this.mCheckStatePaint);
        paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getResources(), this.hash, 2130837748), null, this.mCheckRect, this.mPaint);
      }
    }
    else
      return;
    paramCanvas.drawCircle(this.mCheckRect.centerX(), this.mCheckRect.centerY(), this.checkbgLayout.width / 2, this.mCheckBgPaint);
  }

  private void drawDownloadLabel(Canvas paramCanvas)
  {
    String str;
    float f1;
    float f2;
    if (this.mDownloading > 0)
    {
      if (this.mDownloading != 1)
        break label121;
      str = "正在下载";
      this.mLabelPaint.getTextBounds(str, 0, str.length(), this.textBound);
      f1 = this.labelLayout.leftMargin;
      f2 = this.programLayout.topMargin + this.programLayout.height + this.labelLayout.topMargin + (this.labelLayout.height - this.textBound.top - this.textBound.bottom) / 2;
      if (this.mDownloading != 1)
        break label127;
    }
    label121: label127: for (Paint localPaint = this.mLabelHighlightPaint; ; localPaint = this.mLabelPaint)
    {
      paramCanvas.drawText(str, f1, f2, localPaint);
      return;
      str = "已下载";
      break;
    }
  }

  private void drawFileSize(Canvas paramCanvas)
  {
    String str = "";
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
    {
      str = SizeInfo.getFileSize(125 * (24 * ((ProgramNode)this.mNode).getDuration()));
      this.mChannelPaint.getTextBounds(str, 0, str.length(), this.textBound);
      if (this.mDownloading != 0)
        break label181;
    }
    label181: for (float f = this.checkbgLayout.getRight() + this.programLayout.leftMargin; ; f = this.labelLayout.getRight() + this.programLayout.leftMargin)
    {
      paramCanvas.drawText(str, f, this.programLayout.topMargin + this.programLayout.height + this.timeLayout.topMargin + (this.timeLayout.height - this.textBound.top - this.textBound.bottom) / 2, this.mChannelPaint);
      return;
      if (!this.mNode.nodeName.equalsIgnoreCase("ondemandprogram"))
        break;
      str = SizeInfo.getFileSize(125 * (24 * ((OnDemandProgramNode)this.mNode).getDuration()));
      break;
    }
  }

  private void drawLine(Canvas paramCanvas)
  {
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, this.lineLayout.leftMargin, this.itemLayout.width, this.itemLayout.height - this.lineLayout.height, this.lineLayout.height);
  }

  private void drawTime(Canvas paramCanvas)
  {
    String str = "";
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
      str = "时长:" + getDurationTime(((ProgramNode)this.mNode).getDuration());
    while (true)
    {
      this.mChannelPaint.getTextBounds(str, 0, str.length(), this.textBound);
      paramCanvas.drawText(str, this.timeLayout.leftMargin, this.programLayout.topMargin + this.programLayout.height + this.timeLayout.topMargin + (this.timeLayout.height - this.textBound.top - this.textBound.bottom) / 2, this.mChannelPaint);
      return;
      if (this.mNode.nodeName.equalsIgnoreCase("ondemandprogram"))
        str = "时长:" + getDurationTime(((OnDemandProgramNode)this.mNode).getDuration());
    }
  }

  private void drawTitle(Canvas paramCanvas)
  {
    String str1 = "";
    TextPaint localTextPaint;
    float f1;
    label69: String str2;
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
    {
      str1 = ((ProgramNode)this.mNode).title;
      localTextPaint = SkinManager.getInstance().getNormalTextPaint();
      if (this.mDownloading != 0)
        break label201;
      f1 = this.itemLayout.width - this.programLayout.leftMargin - this.checkbgLayout.getRight();
      str2 = TextUtils.ellipsize(str1, localTextPaint, f1, TextUtils.TruncateAt.END).toString();
      localTextPaint.getTextBounds(str2, 0, str2.length(), this.textBound);
      if (this.mDownloading != 0)
        break label222;
    }
    label201: label222: for (float f2 = this.checkbgLayout.getRight() + this.programLayout.leftMargin; ; f2 = this.checkbgLayout.leftMargin)
    {
      paramCanvas.drawText(str2, f2, this.programLayout.topMargin + (this.programLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
      return;
      if (!this.mNode.nodeName.equalsIgnoreCase("ondemandprogram"))
        break;
      str1 = ((OnDemandProgramNode)this.mNode).title;
      break;
      f1 = this.itemLayout.width - this.checkbgLayout.leftMargin;
      break label69;
    }
  }

  private void drawUpdate(Canvas paramCanvas)
  {
    String str = "";
    if (this.mNode.nodeName.equalsIgnoreCase("program"))
    {
      if (((ProgramNode)this.mNode).channelType == 0)
        str = TimeUtil.msToDate2(1000L * ((ProgramNode)this.mNode).getAbsoluteStartTime());
    }
    else
    {
      if ((str != null) && (!str.equalsIgnoreCase("")))
        break label133;
      return;
    }
    if (((ProgramNode)this.mNode).isDownloadProgram())
      if (((ProgramNode)this.mNode).downloadInfo == null)
        break label218;
    label133: label218: for (long l = ((ProgramNode)this.mNode).downloadInfo.updateTime; ; l = 0L)
    {
      str = TimeUtil.msToDate2(l * 1000L);
      break;
      str = TimeUtil.msToDate2(((ProgramNode)this.mNode).getUpdateTime());
      break;
      this.mChannelPaint.getTextBounds(str, 0, str.length(), this.textBound);
      paramCanvas.drawText(str, this.updateLayout.leftMargin, this.programLayout.topMargin + this.programLayout.height + this.updateLayout.topMargin + (this.updateLayout.height - this.textBound.top - this.textBound.bottom) / 2, this.mChannelPaint);
      return;
    }
  }

  private void generateRect()
  {
    this.mCheckRect.set(this.checkbgLayout.leftMargin + (this.checkbgLayout.width - this.checkStateLayout.width) / 2, (this.itemLayout.height - this.checkStateLayout.height) / 2, this.checkbgLayout.leftMargin + (this.checkbgLayout.width + this.checkStateLayout.width) / 2, (this.itemLayout.height + this.checkStateLayout.height) / 2);
  }

  private String getDurationTime(int paramInt)
  {
    int i = paramInt / 3600;
    int j = paramInt / 60 % 60;
    int k = paramInt % 60;
    if (i == 0)
    {
      if (k == 0)
      {
        Locale localLocale5 = Locale.CHINA;
        Object[] arrayOfObject5 = new Object[1];
        arrayOfObject5[0] = Integer.valueOf(j);
        return String.format(localLocale5, "%d分", arrayOfObject5);
      }
      if (j == 0)
      {
        Locale localLocale4 = Locale.CHINA;
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = Integer.valueOf(k);
        return String.format(localLocale4, "%d秒", arrayOfObject4);
      }
      Locale localLocale3 = Locale.CHINA;
      Object[] arrayOfObject3 = new Object[2];
      arrayOfObject3[0] = Integer.valueOf(j);
      arrayOfObject3[1] = Integer.valueOf(k);
      return String.format(localLocale3, "%d分%d秒", arrayOfObject3);
    }
    if (j == 0)
    {
      Locale localLocale2 = Locale.CHINA;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(i);
      return String.format(localLocale2, "%d小时", arrayOfObject2);
    }
    Locale localLocale1 = Locale.CHINA;
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(i);
    arrayOfObject1[1] = Integer.valueOf(j);
    return String.format(localLocale1, "%d小时%d分", arrayOfObject1);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mNode == null)
      return;
    paramCanvas.setDrawFilter(this.filter);
    paramCanvas.save();
    drawBg(paramCanvas);
    drawCheckState(paramCanvas);
    drawDownloadLabel(paramCanvas);
    drawTitle(paramCanvas);
    drawFileSize(paramCanvas);
    drawTime(paramCanvas);
    drawUpdate(paramCanvas);
    drawLine(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.programLayout.scaleToBounds(this.itemLayout);
    this.checkbgLayout.scaleToBounds(this.itemLayout);
    this.checkStateLayout.scaleToBounds(this.checkbgLayout);
    this.updateLayout.scaleToBounds(this.itemLayout);
    this.timeLayout.scaleToBounds(this.itemLayout);
    this.labelLayout.scaleToBounds(this.itemLayout);
    this.mChannelPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mLabelPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mLabelHighlightPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mCheckBgPaint.setStrokeWidth(this.checkStateLayout.leftMargin);
    generateRect();
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  protected void onQtItemClick(View paramView)
  {
    if (this.mDownloading == 0)
      dispatchActionEvent("itemSelect", null);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("content"))
    {
      this.mNode = ((Node)paramObject);
      this.mDownloading = InfoManager.getInstance().root().mDownLoadInfoNode.hasDownLoad(this.mNode);
    }
    while (!paramString.equalsIgnoreCase("checkState"))
      return;
    this.mIsChecked = ((Boolean)paramObject).booleanValue();
    invalidate();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.scheduleview.BatchDownloadItemView
 * JD-Core Version:    0.6.2
 */