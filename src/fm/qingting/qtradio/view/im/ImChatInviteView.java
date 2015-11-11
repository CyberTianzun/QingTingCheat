package fm.qingting.qtradio.view.im;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtListItemView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;

public class ImChatInviteView extends QtListItemView
{
  private final int EMPTY = 20;
  private final int[] PLATFORM_ICONS = { 2130837973, 2130837968, 2130837970, 2130837969, 2130837971, 2130837972 };
  private final String[] PLATFORM_NAMES = { "微信", "朋友圈", "QQ空间", "QQ好友", "新浪微博", "腾讯微博" };
  private final String TITLE = "邀请好友一起收听";
  private final ViewLayout iconLayout = this.itemLayout.createChildLT(120, 120, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = this.standardLayout.createChildLT(180, 200, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final Rect mIconRect = new Rect();
  private boolean mInTouchMode = false;
  private float mLastMotionX = 0.0F;
  private float mLastMotionY = 0.0F;
  private final Paint mMaskPaint = new Paint();
  private final Paint mNameHighlightPaint = new Paint();
  private final Paint mNamePaint = new Paint();
  private final Paint mPaint = new Paint();
  private int mSelectIndex = -1;
  private final Rect mTextBound = new Rect();
  private final Paint mTitlePaint = new Paint();
  private final ViewLayout nameLayout = this.itemLayout.createChildLT(180, 45, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private final ViewLayout titleLayout = this.standardLayout.createChildLT(720, 80, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public ImChatInviteView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(-1);
    setItemSelectedEnable();
    this.mNamePaint.setColor(SkinManager.getTextColorNormal());
    this.mTitlePaint.setColor(SkinManager.getTextColorNormal());
    this.mTitlePaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mNameHighlightPaint.setColor(SkinManager.getTextColorNormal());
    this.mNameHighlightPaint.setAlpha(144);
    this.mMaskPaint.setStyle(Paint.Style.FILL);
    this.mMaskPaint.setColor(SkinManager.getItemHighlightMaskColor());
  }

  private void drawItem(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    Rect localRect = this.mIconRect;
    localRect.offset(paramInt1, paramInt2);
    paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCache(getResources(), this, this.PLATFORM_ICONS[paramInt3]), null, localRect, this.mPaint);
    String str = this.PLATFORM_NAMES[paramInt3];
    this.mNamePaint.getTextBounds(str, 0, str.length(), this.mTextBound);
    float f1 = paramInt1 + (this.itemLayout.width - this.mTextBound.width()) / 2;
    float f2 = localRect.bottom + (this.nameLayout.height - this.mTextBound.top - this.mTextBound.bottom) / 2;
    if (paramBoolean);
    for (Paint localPaint = this.mNameHighlightPaint; ; localPaint = this.mNamePaint)
    {
      paramCanvas.drawText(str, f1, f2, localPaint);
      if (paramBoolean)
      {
        float f3 = localRect.width() / 2.0F;
        paramCanvas.drawCircle(localRect.exactCenterX(), localRect.exactCenterY(), f3, this.mMaskPaint);
      }
      localRect.offset(-paramInt1, -paramInt2);
      return;
    }
  }

  private void drawItems(Canvas paramCanvas)
  {
    int i = 0;
    if (i < 6)
    {
      int j = this.itemLayout.width * (i % 4);
      int k = getTopMargin() + this.titleLayout.height;
      int m = this.itemLayout.height;
      int n;
      label51: int i1;
      if (i < 4)
      {
        n = 0;
        i1 = k + n * m;
        if ((!isItemPressed()) || (this.mSelectIndex != i))
          break label102;
      }
      label102: for (boolean bool = true; ; bool = false)
      {
        drawItem(paramCanvas, j, i1, i, bool);
        i++;
        break;
        n = 1;
        break label51;
      }
    }
  }

  private void drawTitle(Canvas paramCanvas)
  {
    this.mTitlePaint.getTextBounds("邀请好友一起收听", 0, "邀请好友一起收听".length(), this.mTextBound);
    paramCanvas.drawText("邀请好友一起收听", (this.standardLayout.width - this.mTextBound.width()) / 2, getTopMargin() + (this.titleLayout.height - this.mTextBound.top - this.mTextBound.bottom) / 2, this.mTitlePaint);
  }

  private int getSelectIndex()
  {
    if (this.mLastMotionY < getTopMargin());
    int k;
    do
    {
      int i;
      int j;
      do
      {
        return -1;
        if ((this.mLastMotionY > getTopMargin()) && (this.mLastMotionY < getTopMargin() + this.titleLayout.height))
          return 20;
        i = (int)Math.floor(this.mLastMotionX / this.itemLayout.width);
        j = (int)Math.floor((this.mLastMotionY - getTopMargin() - this.titleLayout.height) / this.itemLayout.height);
      }
      while ((i < 0) || (j < 0));
      k = i + j * 4;
    }
    while (k > this.PLATFORM_NAMES.length);
    return k;
  }

  private int getThisHeight()
  {
    int i = this.PLATFORM_NAMES.length;
    int j = i / 4;
    if (i % 4 == 0);
    for (int k = 0; ; k = 1)
      return (k + j) * this.itemLayout.height + this.titleLayout.height;
  }

  private int getTopMargin()
  {
    return 0;
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.setDrawFilter(SkinManager.getInstance().getDrawFilter());
    paramCanvas.save();
    drawTitle(paramCanvas);
    drawItems(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.itemLayout.scaleToBounds(this.standardLayout);
    this.iconLayout.scaleToBounds(this.itemLayout);
    this.nameLayout.scaleToBounds(this.itemLayout);
    this.titleLayout.scaleToBounds(this.standardLayout);
    this.mNamePaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mNameHighlightPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    int i = (this.itemLayout.height - this.iconLayout.height - this.nameLayout.height) / 2;
    this.mIconRect.set((this.itemLayout.width - this.iconLayout.width) / 2, i, (this.itemLayout.width + this.iconLayout.width) / 2, i + this.iconLayout.height);
    setMeasuredDimension(this.standardLayout.width, getThisHeight());
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((!this.mInTouchMode) && (paramMotionEvent.getAction() != 0));
    do
    {
      do
      {
        int i;
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
            this.mSelectIndex = getSelectIndex();
            if (this.mSelectIndex < 0)
            {
              this.mInTouchMode = false;
              dispatchActionEvent("cancelPop", null);
              return true;
            }
            this.mInTouchMode = true;
            invalidate();
            return true;
          case 2:
            this.mLastMotionX = paramMotionEvent.getX();
            this.mLastMotionY = paramMotionEvent.getY();
            i = getSelectIndex();
          case 3:
          case 1:
          }
        }
        while ((this.mSelectIndex <= -1) || (i == this.mSelectIndex));
        this.mInTouchMode = false;
        this.mSelectIndex = -1;
      }
      while (!isItemPressed());
      invalidate();
      return true;
      this.mInTouchMode = false;
      this.mSelectIndex = -1;
    }
    while (!isItemPressed());
    invalidate();
    return true;
    if ((this.mSelectIndex > -1) && (this.mSelectIndex < this.PLATFORM_NAMES.length))
    {
      MobclickAgent.onEvent(getContext(), "ChatroomInvite", this.PLATFORM_NAMES[this.mSelectIndex]);
      dispatchActionEvent("shareToPlatform", Integer.valueOf(this.mSelectIndex));
    }
    this.mInTouchMode = false;
    invalidate();
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.im.ImChatInviteView
 * JD-Core Version:    0.6.2
 */