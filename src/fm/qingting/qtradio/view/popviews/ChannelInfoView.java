package fm.qingting.qtradio.view.popviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout.Alignment;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.NetImageViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.Node;
import fm.qingting.utils.ScreenType;

public class ChannelInfoView extends QtView
{
  private final ViewLayout closeLayout;
  private final ViewLayout coverLayout;
  private final ViewLayout infoLayout;
  private final ViewLayout lineLayout;
  private ButtonViewElement mClose;
  private NetImageViewElement mCover;
  private TextViewElement mInfo;
  private TextViewElement mName;
  private String mTag;
  private final Paint mTagBgPaint;
  private final Paint mTagPaint;
  private final RectF mTagRectF;
  private TextViewElement mTagText;
  private final Rect mTextBound;
  private final ViewLayout nameLayout;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private final ViewLayout tagTextLayout;

  public ChannelInfoView(Context paramContext)
  {
    super(paramContext);
    ViewLayout localViewLayout1 = this.standardLayout;
    int i;
    int j;
    label78: ViewLayout localViewLayout3;
    if (ScreenType.isUltraWideScreen())
    {
      i = 60;
      this.coverLayout = localViewLayout1.createChildLT(400, 400, 0, i, ViewLayout.SCALE_FLAG_SLTCW);
      ViewLayout localViewLayout2 = this.standardLayout;
      if (!ScreenType.isUltraWideScreen())
        break label507;
      j = 30;
      this.nameLayout = localViewLayout2.createChildLT(630, 75, 45, j, ViewLayout.SCALE_FLAG_SLTCW);
      this.lineLayout = this.standardLayout.createChildLT(630, 1, 45, 4, ViewLayout.SCALE_FLAG_SLTCW);
      localViewLayout3 = this.standardLayout;
      if (!ScreenType.isUltraWideScreen())
        break label514;
    }
    label514: for (int k = 75; ; k = 150)
    {
      this.infoLayout = localViewLayout3.createChildLT(630, k, 45, 9, ViewLayout.SCALE_FLAG_SLTCW);
      this.tagTextLayout = this.standardLayout.createChildLT(90, 45, 45, 25, ViewLayout.SCALE_FLAG_SLTCW);
      this.closeLayout = this.standardLayout.createChildLT(50, 50, 630, 30, ViewLayout.SCALE_FLAG_SLTCW);
      this.mTagRectF = new RectF();
      this.mTextBound = new Rect();
      this.mTagPaint = new Paint();
      this.mTagBgPaint = new Paint();
      int m = hashCode();
      this.mCover = new NetImageViewElement(paramContext);
      this.mCover.setDefaultImageRes(2130837898);
      addElement(this.mCover, m);
      this.mName = new TextViewElement(paramContext);
      this.mName.setColor(SkinManager.getBackgroundColor());
      this.mName.setAlignment(Layout.Alignment.ALIGN_CENTER);
      this.mName.setMaxLineLimit(1);
      addElement(this.mName);
      this.mInfo = new TextViewElement(paramContext);
      this.mInfo.setColor(SkinManager.getTextColorSubInfo());
      this.mInfo.setAlignment(Layout.Alignment.ALIGN_NORMAL);
      this.mInfo.setMaxLineLimit(20);
      addElement(this.mInfo);
      this.mTagText = new TextViewElement(paramContext);
      this.mTagText.setText("标签：");
      this.mTagText.setColor(SkinManager.getBackgroundColor());
      this.mTagText.setAlignment(Layout.Alignment.ALIGN_NORMAL);
      this.mTagText.setMaxLineLimit(1);
      addElement(this.mTagText);
      this.mClose = new ButtonViewElement(paramContext);
      this.mClose.setBackground(2130837535, 2130837534);
      addElement(this.mClose, m);
      this.mTagPaint.setColor(SkinManager.getBackgroundColor());
      this.mTagBgPaint.setColor(SkinManager.getTextColorHighlight());
      this.mTagBgPaint.setStyle(Paint.Style.FILL);
      return;
      i = 120;
      break;
      label507: j = 60;
      break label78;
    }
  }

  private void drawBg(Canvas paramCanvas)
  {
    paramCanvas.drawColor(-872415232);
    int i = this.coverLayout.topMargin + this.coverLayout.height + this.nameLayout.topMargin + this.nameLayout.height + this.lineLayout.topMargin + this.infoLayout.topMargin + this.mInfo.getHeight();
    this.mTagText.setTranslationY(i);
  }

  private void drawLine(Canvas paramCanvas)
  {
    int i = this.coverLayout.getBottom() + this.nameLayout.getBottom() + this.lineLayout.topMargin;
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, this.lineLayout.leftMargin, this.lineLayout.getRight(), i, this.lineLayout.height);
  }

  private void drawTag(Canvas paramCanvas)
  {
    if ((this.mTag == null) || (this.mTag.equalsIgnoreCase("")))
      return;
    this.mTagPaint.getTextBounds(this.mTag, 0, this.mTag.length(), this.mTextBound);
    this.mTagRectF.set(this.mTagText.getRightMargin(), this.mTagText.getTopMargin(), this.mTagText.getRightMargin() + this.mTextBound.width() + this.tagTextLayout.height, this.mTagText.getBottomMargin());
    paramCanvas.drawRoundRect(this.mTagRectF, this.tagTextLayout.height / 2.0F, this.tagTextLayout.height / 2.0F, this.mTagBgPaint);
    paramCanvas.drawText(this.mTag, this.mTagText.getRightMargin() + this.tagTextLayout.height / 2.0F, (this.mTagText.getTopMargin() + this.mTagText.getBottomMargin() - this.mTextBound.top - this.mTextBound.bottom) / 2, this.mTagPaint);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    drawBg(paramCanvas);
    super.onDraw(paramCanvas);
    drawLine(paramCanvas);
    drawTag(paramCanvas);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.coverLayout.scaleToBounds(this.standardLayout);
    this.nameLayout.scaleToBounds(this.standardLayout);
    this.lineLayout.scaleToBounds(this.standardLayout);
    this.infoLayout.scaleToBounds(this.standardLayout);
    this.tagTextLayout.scaleToBounds(this.standardLayout);
    this.closeLayout.scaleToBounds(this.standardLayout);
    this.mClose.measure(this.closeLayout);
    int i = this.coverLayout.topMargin;
    this.mCover.measure((this.standardLayout.width - this.coverLayout.width) / 2, i, (this.standardLayout.width + this.coverLayout.width) / 2, i + this.coverLayout.height);
    int j = i + this.coverLayout.height + this.nameLayout.topMargin;
    this.mName.measure(this.nameLayout.leftMargin, j, this.nameLayout.getRight(), j + this.nameLayout.height);
    int k = j + this.nameLayout.height + this.lineLayout.topMargin + this.infoLayout.topMargin;
    this.mInfo.measure(this.infoLayout.leftMargin, k, this.infoLayout.getRight(), k + this.infoLayout.height);
    TextViewElement localTextViewElement = this.mInfo;
    float f1 = this.infoLayout.height;
    if (ScreenType.isUltraWideScreen());
    for (float f2 = 0.36F; ; f2 = 0.18F)
    {
      localTextViewElement.setTextSize(f2 * f1);
      this.mTagText.measure(this.tagTextLayout.leftMargin, this.tagTextLayout.topMargin, this.tagTextLayout.getRight(), this.tagTextLayout.topMargin + this.tagTextLayout.height);
      this.mName.setTextSize(0.5F * this.nameLayout.height);
      this.mTagText.setTextSize(0.6F * this.tagTextLayout.height);
      this.mTagPaint.setTextSize(0.6F * this.tagTextLayout.height);
      setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
      return;
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 1)
      dispatchActionEvent("cancelPop", null);
    return super.onTouchEvent(paramMotionEvent);
  }

  public void update(String paramString, Object paramObject)
  {
    ChannelNode localChannelNode;
    Node localNode1;
    if (paramString.equalsIgnoreCase("setBubbleData"))
    {
      localChannelNode = (ChannelNode)paramObject;
      this.mCover.setImageUrl(localChannelNode.getApproximativeThumb(400, 400, true));
      this.mName.setText(localChannelNode.title, false);
      localNode1 = localChannelNode.parent;
      if (localNode1 != null)
      {
        if (!localNode1.nodeName.equalsIgnoreCase("category"))
          break label101;
        this.mTag = ((CategoryNode)localNode1).name;
      }
    }
    while (localChannelNode.isNovelChannel())
    {
      this.mInfo.setText(localChannelNode.desc);
      return;
      label101: if (localNode1.nodeName.equalsIgnoreCase("subcategory"))
      {
        Node localNode2 = localNode1.parent;
        if ((localNode2 != null) && (localNode2.nodeName.equalsIgnoreCase("category")))
          this.mTag = ((CategoryNode)localNode2).name;
      }
    }
    this.mInfo.setText(localChannelNode.desc);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.ChannelInfoView
 * JD-Core Version:    0.6.2
 */