package fm.qingting.qtradio.view.channelcategoryview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtListItemView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ActivityNode;
import fm.qingting.qtradio.model.Node;

public class ActivityItemView extends QtListItemView
{
  private final ViewLayout arrowLayout = this.itemLayout.createChildLT(36, 36, 650, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout channelLayout = this.itemLayout.createChildLT(600, 45, 30, 20, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout detailLayout = this.itemLayout.createChildLT(600, 45, 30, 10, ViewLayout.SCALE_FLAG_SLTCW);
  private DrawFilter filter;
  private int hash = -2;
  private Paint iconPaint = new Paint();
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 136, 720, 800, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private Rect mArrowRect = new Rect();
  private Node mNode;
  private Rect textBound = new Rect();

  public ActivityItemView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.hash = paramInt;
    setItemSelectedEnable();
    setOnClickListener(this);
    this.filter = SkinManager.getInstance().getDrawFilter();
  }

  private void drawArrow(Canvas paramCanvas)
  {
    Bitmap localBitmap = BitmapResourceCache.getInstance().getResourceCacheByParent(getResources(), this.hash, 2130837694);
    int i = this.arrowLayout.leftMargin + this.itemLayout.leftMargin;
    int j = (this.itemLayout.height - this.arrowLayout.height) / 2;
    this.mArrowRect.offset(i, j);
    paramCanvas.drawBitmap(localBitmap, null, this.mArrowRect, this.iconPaint);
    this.mArrowRect.offset(-i, -j);
  }

  private void drawBg(Canvas paramCanvas)
  {
    if (isItemPressed())
      paramCanvas.drawColor(SkinManager.getItemHighlightMaskColor());
  }

  private void drawInfo(Canvas paramCanvas)
  {
    if (this.mNode.nodeName.equalsIgnoreCase("activity"))
    {
      String str1 = ((ActivityNode)this.mNode).infoTitle;
      if ((str1 == null) || (str1.equalsIgnoreCase("")))
        str1 = "暂无活动简介";
      TextPaint localTextPaint = SkinManager.getInstance().getSubTextPaint();
      String str2 = TextUtils.ellipsize(str1, localTextPaint, this.arrowLayout.leftMargin - this.detailLayout.leftMargin, TextUtils.TruncateAt.END).toString();
      localTextPaint.getTextBounds(str2, 0, str2.length(), this.textBound);
      paramCanvas.drawText(str2, this.detailLayout.leftMargin, this.channelLayout.topMargin + this.channelLayout.height + this.detailLayout.topMargin + (this.detailLayout.height - this.textBound.top - this.textBound.bottom) / 2, localTextPaint);
    }
  }

  private void drawLine(Canvas paramCanvas)
  {
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, 0, this.itemLayout.width, this.itemLayout.height - this.lineLayout.height, this.lineLayout.height);
  }

  private void drawTitle(Canvas paramCanvas)
  {
    String str1 = getTitle();
    TextPaint localTextPaint = SkinManager.getInstance().getNormalTextPaint();
    String str2 = TextUtils.ellipsize(str1, localTextPaint, this.arrowLayout.leftMargin - this.channelLayout.leftMargin, TextUtils.TruncateAt.END).toString();
    localTextPaint.getTextBounds(str2, 0, str2.length(), this.textBound);
    paramCanvas.drawText(str2, this.channelLayout.getLeft() + this.itemLayout.leftMargin, this.channelLayout.topMargin + (this.channelLayout.height - this.textBound.top - this.textBound.bottom) / 2.0F, localTextPaint);
  }

  private void generateRect()
  {
    this.mArrowRect.set(0, 0, this.arrowLayout.width, this.arrowLayout.height);
  }

  private String getTitle()
  {
    if (this.mNode.nodeName.equalsIgnoreCase("activity"))
      return ((ActivityNode)this.mNode).name;
    return "";
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mNode == null)
      return;
    paramCanvas.save();
    paramCanvas.setDrawFilter(this.filter);
    paramCanvas.clipRect(0, 0, this.itemLayout.width, this.itemLayout.height);
    drawBg(paramCanvas);
    drawLine(paramCanvas);
    drawTitle(paramCanvas);
    drawInfo(paramCanvas);
    drawArrow(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.itemLayout.scaleToBounds(i, j);
    this.channelLayout.scaleToBounds(this.itemLayout);
    this.detailLayout.scaleToBounds(this.itemLayout);
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.arrowLayout.scaleToBounds(this.itemLayout);
    generateRect();
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  protected void onQtItemClick(View paramView)
  {
    if (this.mNode == null)
      return;
    ControllerManager.getInstance().redirectToPlayViewByNode(this.mNode, true);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("content"))
    {
      this.mNode = ((Node)paramObject);
      invalidate();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.channelcategoryview.ActivityItemView
 * JD-Core Version:    0.6.2
 */