package fm.qingting.qtradio.view.personalcenter.clock.djringtone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.utils.ImageLoader;
import fm.qingting.framework.utils.ImageLoaderHandler;
import fm.qingting.framework.view.ViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.BroadcasterNode;
import fm.qingting.qtradio.model.Download;
import fm.qingting.qtradio.model.RingToneNode;

public class DJRingtoneItemView extends ViewImpl
  implements View.OnClickListener, ImageLoaderHandler
{
  private int TouchSlop = 20;
  private final ViewLayout avatarLayout = this.itemLayout.createChildLT(135, 135, 30, 6, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout checkStateLayout = this.checkbgLayout.createChildLT(30, 22, 2, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout checkbgLayout = this.itemLayout.createChildLT(48, 48, 622, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private boolean checked = false;
  private TextPaint eclipsePaint = new TextPaint();
  private DrawFilter filter;
  private int hashCode = -15;
  private final ViewLayout iconLayout = this.itemLayout.createChildLT(49, 49, 0, 8, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private Paint infoHighlightPaint = new Paint();
  private final ViewLayout infoLayout = this.itemLayout.createChildLT(500, 45, 185, 10, ViewLayout.SCALE_FLAG_SLTCW);
  private Paint infoPaint = new Paint();
  private boolean isPlaying = false;
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 180, 720, 800, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private Paint mAvatarPaint = new Paint();
  private RectF mAvatarRectF = new RectF();
  private Paint mCheckBgPaint = new Paint();
  private Rect mCheckRect = new Rect();
  private Paint mCheckStatePaint = new Paint();
  private Rect mDefaultAvatarRect = new Rect();
  private RectF mOriginRectF = new RectF();
  private Paint mPaint = new Paint();
  private Rect mPlayRect = new Rect();
  private Rect mTextBound = new Rect();
  private Paint nameHighlightPaint = new Paint();
  private final ViewLayout nameLayout = this.itemLayout.createChildLT(500, 45, 185, 20, ViewLayout.SCALE_FLAG_SLTCW);
  private Paint namePaint = new Paint();
  private final ViewLayout playStateLayout = this.itemLayout.createChildLT(36, 36, 30, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private String ringtoneId;
  private RingToneNode ringtoneInfo;
  private float touchDownX = 0.0F;
  private float touchDownY = 0.0F;

  public DJRingtoneItemView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.hashCode = paramInt;
    setBackgroundColor(SkinManager.getCardColor());
    this.namePaint.setColor(SkinManager.getTextColorNormal());
    this.infoPaint.setColor(SkinManager.getTextColorSubInfo());
    this.nameHighlightPaint.setColor(SkinManager.getTextColorHighlight());
    this.infoHighlightPaint.setColor(SkinManager.getTextColorHighlight());
    this.mCheckBgPaint.setColor(SkinManager.getTextColorSubInfo());
    this.mCheckStatePaint.setColor(SkinManager.getTextColorHighlight());
    this.mCheckBgPaint.setStyle(Paint.Style.STROKE);
    this.mCheckStatePaint.setStyle(Paint.Style.FILL);
    this.filter = SkinManager.getInstance().getDrawFilter();
    setOnClickListener(this);
    this.mAvatarPaint.setAntiAlias(true);
  }

  private void drawAvatar(Canvas paramCanvas, String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getResources(), this.hashCode, 2130837902), null, this.mDefaultAvatarRect, this.mPaint);
    Bitmap localBitmap;
    int i;
    int j;
    int k;
    do
    {
      return;
      localBitmap = ImageLoader.getInstance(getContext()).getImage(paramString, this.avatarLayout.width, this.avatarLayout.height);
      if (localBitmap == null)
      {
        paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getResources(), this.hashCode, 2130837902), null, this.mDefaultAvatarRect, this.mPaint);
        ImageLoader.getInstance(getContext()).loadImage(paramString, null, this, this.avatarLayout.width, this.avatarLayout.height, this);
        return;
      }
      i = localBitmap.getWidth();
      j = localBitmap.getHeight();
      k = Math.min(i, j);
    }
    while (k == 0);
    float f1 = this.mAvatarRectF.width() / k;
    float f2 = (i - k) / 2;
    float f3 = (j - k) / 2;
    this.mAvatarPaint.setShader(new BitmapShader(localBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    int m = paramCanvas.save();
    paramCanvas.scale(f1, f1, this.mAvatarRectF.left, this.mAvatarRectF.top);
    paramCanvas.translate(this.mAvatarRectF.left - f2, this.mAvatarRectF.top - f3);
    this.mOriginRectF.set(0.0F, 0.0F, i, j);
    paramCanvas.drawRoundRect(this.mOriginRectF, this.avatarLayout.topMargin / f1, this.avatarLayout.topMargin / f1, this.mAvatarPaint);
    paramCanvas.restoreToCount(m);
  }

  private void drawCheckState(Canvas paramCanvas)
  {
    if (this.checked)
    {
      paramCanvas.drawCircle(this.mCheckRect.centerX(), this.mCheckRect.centerY(), this.checkbgLayout.width / 2, this.mCheckStatePaint);
      paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCacheByParent(getResources(), this.hashCode, 2130837748), null, this.mCheckRect, this.mPaint);
      return;
    }
    paramCanvas.drawCircle(this.mCheckRect.centerX(), this.mCheckRect.centerY(), this.checkbgLayout.width / 2, this.mCheckBgPaint);
  }

  private void drawInfo(Canvas paramCanvas, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (paramString1 == null)
      paramString1 = "";
    if (paramString1.equalsIgnoreCase(""))
      paramString1 = paramString2;
    this.namePaint.getTextBounds(paramString1, 0, paramString1.length(), this.mTextBound);
    float f1 = this.nameLayout.leftMargin;
    float f2 = this.nameLayout.topMargin + (this.nameLayout.height - this.mTextBound.top - this.mTextBound.bottom) / 2;
    Paint localPaint1;
    int j;
    label134: Paint localPaint2;
    label444: float f5;
    float f6;
    if (this.checked)
    {
      localPaint1 = this.nameHighlightPaint;
      paramCanvas.drawText(paramString1, f1, f2, localPaint1);
      BitmapResourceCache localBitmapResourceCache = BitmapResourceCache.getInstance();
      Resources localResources = getResources();
      int i = this.hashCode;
      if (!this.isPlaying)
        break label563;
      j = 2130837903;
      Bitmap localBitmap = localBitmapResourceCache.getResourceCacheByParent(localResources, i, j);
      this.mPlayRect.set(this.nameLayout.leftMargin + this.mTextBound.width() + this.playStateLayout.width, this.nameLayout.topMargin + (this.nameLayout.height - this.playStateLayout.height) / 2, this.nameLayout.leftMargin + this.mTextBound.width() + this.playStateLayout.width + this.playStateLayout.width, this.nameLayout.topMargin + (this.nameLayout.height + this.playStateLayout.height) / 2);
      paramCanvas.drawBitmap(localBitmap, null, this.mPlayRect, this.mPaint);
      if (paramString2 == null)
        paramString2 = "";
      if ((paramString4 != null) && (!paramString4.equalsIgnoreCase("")))
      {
        String str = TextUtils.ellipsize(paramString4, this.eclipsePaint, this.checkbgLayout.leftMargin - this.nameLayout.leftMargin, TextUtils.TruncateAt.END).toString();
        paramString3 = paramString2 + " " + paramString3;
        paramString2 = str;
      }
      this.infoPaint.getTextBounds(paramString2, 0, paramString2.length(), this.mTextBound);
      float f3 = this.infoLayout.leftMargin;
      float f4 = this.nameLayout.getBottom() + this.infoLayout.topMargin + (this.infoLayout.height - this.mTextBound.top - this.mTextBound.bottom) / 2;
      if (!this.checked)
        break label571;
      localPaint2 = this.infoHighlightPaint;
      paramCanvas.drawText(paramString2, f3, f4, localPaint2);
      this.infoPaint.getTextBounds(paramString3, 0, paramString3.length(), this.mTextBound);
      f5 = this.infoLayout.leftMargin;
      f6 = this.nameLayout.getBottom() + this.infoLayout.getBottom() + (this.infoLayout.height - this.mTextBound.top - this.mTextBound.bottom) / 2;
      if (!this.checked)
        break label580;
    }
    label563: label571: label580: for (Paint localPaint3 = this.infoHighlightPaint; ; localPaint3 = this.infoPaint)
    {
      paramCanvas.drawText(paramString3, f5, f6, localPaint3);
      return;
      localPaint1 = this.namePaint;
      break;
      j = 2130837904;
      break label134;
      localPaint2 = this.infoPaint;
      break label444;
    }
  }

  private void drawItem(Canvas paramCanvas, RingToneNode paramRingToneNode)
  {
    if (paramRingToneNode == null)
      return;
    BroadcasterNode localBroadcasterNode = paramRingToneNode.getBroadcaster();
    String str1;
    String str2;
    int i;
    if (localBroadcasterNode == null)
    {
      str1 = "";
      drawAvatar(paramCanvas, str1);
      str2 = "";
      if (paramRingToneNode.downloadInfo != null)
      {
        i = paramRingToneNode.downloadInfo.fileSize;
        if (i > 0)
        {
          if (i >= 1000)
            break label144;
          StringBuilder localStringBuilder3 = new StringBuilder().append(str2);
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = Integer.valueOf(i);
          str2 = String.format("%dB  ", arrayOfObject3);
        }
        label105: if (localBroadcasterNode != null)
          break label269;
      }
    }
    label269: for (String str3 = null; ; str3 = localBroadcasterNode.nick)
    {
      drawInfo(paramCanvas, str3, paramRingToneNode.ringDesc, str2, paramRingToneNode.getBelongRadio());
      drawCheckState(paramCanvas);
      return;
      str1 = localBroadcasterNode.avatar;
      break;
      label144: if (i < 1000000)
      {
        StringBuilder localStringBuilder2 = new StringBuilder().append(str2);
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(i / 1000);
        str2 = String.format("%dkB  ", arrayOfObject2);
        break label105;
      }
      if (i >= 1000000000)
        break label105;
      StringBuilder localStringBuilder1 = new StringBuilder().append(str2);
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Float.valueOf(i / 1000000.0F);
      str2 = String.format("%.1fMB  ", arrayOfObject1);
      break label105;
    }
  }

  private void drawLine(Canvas paramCanvas)
  {
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, this.lineLayout.leftMargin, this.itemLayout.width, this.itemLayout.height - this.lineLayout.height, this.lineLayout.height);
  }

  private void findSelected()
  {
    if (this.ringtoneId == null);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    case 1:
    case 2:
    case 3:
    default:
    case 0:
    }
    while (true)
    {
      return super.dispatchTouchEvent(paramMotionEvent);
      this.touchDownY = paramMotionEvent.getY();
      this.touchDownX = paramMotionEvent.getX();
    }
  }

  public void loadImageFinish(boolean paramBoolean, String paramString, Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    if (paramBoolean)
      invalidate();
  }

  public void onClick(View paramView)
  {
    int i = (this.itemLayout.height - this.checkbgLayout.height) / 2;
    if ((this.touchDownX > this.checkbgLayout.leftMargin - this.TouchSlop) && (this.touchDownX < this.checkbgLayout.leftMargin + this.checkbgLayout.width + this.TouchSlop) && (this.touchDownY > i - this.TouchSlop) && (this.touchDownY < this.itemLayout.height - i + this.TouchSlop))
    {
      dispatchActionEvent("changeCheckState", null);
      return;
    }
    dispatchActionEvent("previewRingtone", null);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.ringtoneInfo == null)
      return;
    paramCanvas.save();
    paramCanvas.setDrawFilter(this.filter);
    drawLine(paramCanvas);
    drawItem(paramCanvas, this.ringtoneInfo);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.itemLayout.scaleToBounds(i, j);
    this.nameLayout.scaleToBounds(this.itemLayout);
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.avatarLayout.scaleToBounds(this.itemLayout);
    this.infoLayout.scaleToBounds(this.itemLayout);
    this.playStateLayout.scaleToBounds(this.itemLayout);
    this.iconLayout.scaleToBounds(this.itemLayout);
    this.checkbgLayout.scaleToBounds(this.itemLayout);
    this.checkStateLayout.scaleToBounds(this.checkbgLayout);
    this.mCheckBgPaint.setStrokeWidth(this.checkStateLayout.leftMargin);
    this.mCheckRect.set(this.checkbgLayout.leftMargin + (this.checkbgLayout.width - this.checkStateLayout.width) / 2, (this.itemLayout.height - this.checkStateLayout.height) / 2, this.checkbgLayout.leftMargin + (this.checkbgLayout.width + this.checkStateLayout.width) / 2, (this.itemLayout.height + this.checkStateLayout.height) / 2);
    this.namePaint.setTextSize(SkinManager.getInstance().getNormalTextSize());
    this.nameHighlightPaint.setTextSize(SkinManager.getInstance().getNormalTextSize());
    this.infoPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.eclipsePaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.infoHighlightPaint.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mAvatarRectF.set(this.avatarLayout.leftMargin, (this.itemLayout.height - this.avatarLayout.height) / 2, this.avatarLayout.leftMargin + this.avatarLayout.width, (this.itemLayout.height + this.avatarLayout.height) / 2);
    this.mDefaultAvatarRect.set(this.avatarLayout.leftMargin, (this.itemLayout.height - this.avatarLayout.height) / 2, this.avatarLayout.leftMargin + this.avatarLayout.width, (this.itemLayout.height + this.avatarLayout.height) / 2);
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("content"))
    {
      this.ringtoneInfo = ((RingToneNode)paramObject);
      invalidate();
    }
    do
    {
      return;
      if (paramString.equalsIgnoreCase("uncheck"))
      {
        this.checked = false;
        invalidate();
        return;
      }
      if (paramString.equalsIgnoreCase("check"))
      {
        this.checked = true;
        invalidate();
        return;
      }
      if (paramString.equalsIgnoreCase("setData"))
      {
        findSelected();
        invalidate();
        return;
      }
      if (paramString.equalsIgnoreCase("setChecked"))
      {
        this.ringtoneId = ((String)paramObject);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("setPlay"));
    this.isPlaying = ((Boolean)paramObject).booleanValue();
    invalidate();
  }

  public void updateImageViewFinish(boolean paramBoolean, ImageView paramImageView, String paramString, Bitmap paramBitmap)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.clock.djringtone.DJRingtoneItemView
 * JD-Core Version:    0.6.2
 */