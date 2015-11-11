package fm.qingting.qtradio.manager;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import fm.qingting.utils.QTRamInfo;

public enum SkinManager
{
  public static boolean isLowMemory = false;
  private final float LARGEFONTSIZE = 46.0F;
  private final float MIDDLEFONTSIZE = 30.0F;
  private final float NORMALFONTSIZE = 34.0F;
  private final float RECOMMENDFONTSIZE = 26.0F;
  private final float SMALLLABELFONTSIZE = 18.0F;
  private final int STANDARDWIDTH = 720;
  private final float SUBFONTSIZE = 26.0F;
  private final float TEENYTNYFONTSIZE = 22.0F;
  private final float TINYFONTSIZE = 24.0F;
  private DrawFilter mDrawfilter;
  private final TextPaint mHighlightTextPaint = new TextPaint();
  private final TextPaint mHighlightTextPaint2 = new TextPaint();
  private float mLargeTextSize;
  private boolean mLineColorSetted = false;
  private int mLineWidth = 1;
  private float mMiddleTextSize;
  private final TextPaint mNormalTextPaint = new TextPaint();
  private float mNormalTextSize;
  private float mRecommendTextSize;
  private float mSmallLabelTextSize;
  private final TextPaint mSubTextPaint = new TextPaint();
  private float mSubTextSize;
  private float mTeenyTinyTextSize;
  private float mTinyTextSize;
  private Path mTriangularPath;
  private final Paint mUpperLinePaint = new Paint();

  static
  {
    SkinManager[] arrayOfSkinManager = new SkinManager[1];
    arrayOfSkinManager[0] = INSTANCE;
  }

  public static Bitmap boxBlur(Bitmap paramBitmap, int paramInt)
  {
    if ((paramInt & 0x1) == 0)
      paramInt--;
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.RGB_565);
    Canvas localCanvas = new Canvas(localBitmap);
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    int[] arrayOfInt = new int[paramBitmap.getWidth() * paramBitmap.getHeight()];
    paramBitmap.getPixels(arrayOfInt, 0, i, 0, 0, i, j);
    boxBlurHorizontal(arrayOfInt, i, j, paramInt / 2);
    boxBlurVertical(arrayOfInt, i, j, paramInt / 2);
    localCanvas.drawBitmap(arrayOfInt, 0, i, 0.0F, 0.0F, i, j, true, null);
    return localBitmap;
  }

  private static void boxBlurHorizontal(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    int[] arrayOfInt = new int[paramInt1];
    int i = 0;
    int i2;
    for (int j = 0; i < paramInt2; j = i2)
    {
      int k = -paramInt3;
      long l1 = 0L;
      long l2 = 0L;
      long l3 = 0L;
      int m = 0;
      for (int n = k; n < paramInt1; n++)
      {
        int i3 = -1 + (n - paramInt3);
        if (i3 >= 0)
        {
          int i6 = paramArrayOfInt[(i3 + j)];
          if (i6 != 0)
          {
            l3 -= Color.red(i6);
            l2 -= Color.green(i6);
            l1 -= Color.blue(i6);
          }
          m--;
        }
        int i4 = n + paramInt3;
        if (i4 < paramInt1)
        {
          int i5 = paramArrayOfInt[(i4 + j)];
          if (i5 != 0)
          {
            l3 += Color.red(i5);
            l2 += Color.green(i5);
            l1 += Color.blue(i5);
          }
          m++;
        }
        if (n >= 0)
          arrayOfInt[n] = Color.argb(255, (int)(l3 / m), (int)(l2 / m), (int)(l1 / m));
      }
      for (int i1 = 0; i1 < paramInt1; i1++)
        paramArrayOfInt[(j + i1)] = arrayOfInt[i1];
      i2 = j + paramInt1;
      i++;
    }
  }

  private static void boxBlurVertical(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
  {
    int[] arrayOfInt = new int[paramInt2];
    int i = paramInt1 * -(paramInt3 + 1);
    int j = paramInt3 * paramInt1;
    for (int k = 0; k < paramInt1; k++)
    {
      int m = k + paramInt1 * -paramInt3;
      int n = -paramInt3;
      long l1 = 0L;
      long l2 = 0L;
      long l3 = 0L;
      int i1 = n;
      int i2 = 0;
      int i3 = m;
      while (i1 < paramInt2)
      {
        if (-1 + (i1 - paramInt3) >= 0)
        {
          int i6 = paramArrayOfInt[(i3 + i)];
          if (i6 != 0)
          {
            l3 -= Color.red(i6);
            l2 -= Color.green(i6);
            l1 -= Color.blue(i6);
          }
          i2--;
        }
        if (i1 + paramInt3 < paramInt2)
        {
          int i5 = paramArrayOfInt[(i3 + j)];
          if (i5 != 0)
          {
            l3 += Color.red(i5);
            l2 += Color.green(i5);
            l1 += Color.blue(i5);
          }
          i2++;
        }
        if (i1 >= 0)
          arrayOfInt[i1] = Color.argb(255, (int)(l3 / i2), (int)(l2 / i2), (int)(l1 / i2));
        i3 += paramInt1;
        i1++;
      }
      for (int i4 = 0; i4 < paramInt2; i4++)
        paramArrayOfInt[(k + i4 * paramInt1)] = arrayOfInt[i4];
    }
  }

  public static int getBackgroundColor()
  {
    return -1118482;
  }

  public static int getBackgroundColor_New()
  {
    return -328966;
  }

  public static int getBackgroundColor_item()
  {
    return -723463;
  }

  public static int getCardColor()
  {
    return -1;
  }

  public static int getDefaultPicColor()
  {
    return -9013642;
  }

  public static int getDividerColor()
  {
    return -2236963;
  }

  public static int getDividerColor_new()
  {
    return -4671302;
  }

  public static int getDominantColor(Bitmap paramBitmap, int paramInt)
  {
    return 0;
  }

  public static int getDownloadTipBgColor()
  {
    return getTextColorHighlight();
  }

  public static int getGenerabButtonDisableColor()
  {
    return getGeneralButtonColor();
  }

  public static int getGeneralButtonColor()
  {
    return -286331154;
  }

  public static int getGreenColor()
  {
    return -14365861;
  }

  public static int getGreenHighlightColor()
  {
    return -15418549;
  }

  @Deprecated
  public static Bitmap getHollowBitmap(Bitmap paramBitmap, String paramString)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    try
    {
      Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      Paint localPaint1 = new Paint();
      Rect localRect = new Rect(0, 0, i, j);
      RectF localRectF = new RectF(localRect);
      localPaint1.setAntiAlias(true);
      localPaint1.setStyle(Paint.Style.FILL);
      localCanvas.drawARGB(0, 0, 0, 0);
      Paint localPaint2 = new Paint();
      localPaint2.setAntiAlias(true);
      localPaint2.setTextSize(30.0F);
      localCanvas.drawText(paramString, -30 + i / 2, j / 2, localPaint2);
      localPaint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
      localCanvas.drawBitmap(paramBitmap, localRect, localRectF, localPaint1);
      return localBitmap;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
    }
    return paramBitmap;
  }

  public static SkinManager getInstance()
  {
    return INSTANCE;
  }

  public static int getItemHighlightMaskColor()
  {
    return 855638016;
  }

  public static int getItemHighlightMaskColor_new()
  {
    return -2302237;
  }

  public static int getLiveColor()
  {
    return -45747;
  }

  public static int getLoadMoreFooterColor()
  {
    return getTextColorSubInfo();
  }

  public static int getMiniplayerBgColor()
  {
    return -13751253;
  }

  public static int getNaviBgColor()
  {
    return -3061434;
  }

  public static int getNewPlaySubColor()
  {
    return -3355444;
  }

  public static int getNewPopBgColor()
  {
    return -986895;
  }

  public static int getNewPopTextColor()
  {
    return -16777216;
  }

  public static int getPopBgColor()
  {
    return -1579033;
  }

  public static int getPopButtonHighlightColor()
  {
    return getTextColorHighlight();
  }

  public static int getPopButtonNormalColor()
  {
    return -592138;
  }

  public static int getPopTextColor()
  {
    return -9145228;
  }

  @Deprecated
  public static Bitmap getRoundBitmap(Bitmap paramBitmap)
  {
    int i = Math.min(paramBitmap.getWidth(), paramBitmap.getHeight());
    try
    {
      Bitmap localBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      Paint localPaint = new Paint();
      Rect localRect = new Rect(0, 0, i, i);
      RectF localRectF = new RectF(localRect);
      localPaint.setAntiAlias(true);
      localPaint.setStyle(Paint.Style.FILL);
      localCanvas.drawARGB(0, 0, 0, 0);
      localCanvas.drawCircle(i / 2.0F, i / 2.0F, i / 2.0F, localPaint);
      localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      localCanvas.drawBitmap(paramBitmap, localRect, localRectF, localPaint);
      return localBitmap;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
    }
    return paramBitmap;
  }

  @Deprecated
  public static Bitmap getRoundedCornerBitmap(Bitmap paramBitmap, float paramFloat, boolean paramBoolean)
  {
    if ((!paramBoolean) || (isLowMemory))
      return paramBitmap;
    try
    {
      Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      Paint localPaint = new Paint();
      Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
      RectF localRectF = new RectF(localRect);
      localPaint.setAntiAlias(true);
      localCanvas.drawARGB(0, 0, 0, 0);
      localCanvas.drawRoundRect(localRectF, paramFloat, paramFloat, localPaint);
      localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
      return localBitmap;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
    }
    return paramBitmap;
  }

  @Deprecated
  public static Bitmap getRoundedCornerBitmapBottom(Bitmap paramBitmap, float paramFloat)
  {
    if (isLowMemory)
      return paramBitmap;
    try
    {
      Bitmap localBitmap2 = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
      localBitmap1 = localBitmap2;
      Canvas localCanvas = new Canvas(localBitmap1);
      Paint localPaint = new Paint();
      Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
      RectF localRectF = new RectF(localRect);
      localPaint.setAntiAlias(true);
      localCanvas.drawARGB(0, 0, 0, 0);
      localCanvas.drawRoundRect(localRectF, paramFloat, paramFloat, localPaint);
      localCanvas.drawRect(new RectF(0.0F, 0.0F, paramBitmap.getWidth(), paramFloat), localPaint);
      localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
      return localBitmap1;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
      {
        localOutOfMemoryError.printStackTrace();
        System.gc();
        Bitmap localBitmap1 = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);
      }
    }
  }

  @Deprecated
  public static Bitmap getRoundedCornerBitmapUp(Bitmap paramBitmap, float paramFloat)
  {
    if (isLowMemory)
      return paramBitmap;
    try
    {
      Bitmap localBitmap2 = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
      localBitmap1 = localBitmap2;
      Canvas localCanvas = new Canvas(localBitmap1);
      Paint localPaint = new Paint();
      Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
      RectF localRectF = new RectF(localRect);
      localPaint.setAntiAlias(true);
      localCanvas.drawARGB(0, 0, 0, 0);
      localCanvas.drawRoundRect(localRectF, paramFloat, paramFloat, localPaint);
      localCanvas.drawRect(new RectF(0.0F, paramBitmap.getHeight() - paramFloat, paramBitmap.getWidth(), paramBitmap.getHeight()), localPaint);
      localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
      return localBitmap1;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        System.gc();
        localBitmap1 = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
      {
        System.gc();
        Bitmap localBitmap1 = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);
      }
    }
  }

  public static int getSpecialButtonHighlightColor()
  {
    return -2804432;
  }

  public static int getTagBackgroundColor()
  {
    return -1381654;
  }

  public static int getTextColorDisable()
  {
    return -4671304;
  }

  public static int getTextColorHeat()
  {
    return -1541249;
  }

  public static int getTextColorHighlight()
  {
    return -2018256;
  }

  public static int getTextColorHighlight2()
  {
    return -3061434;
  }

  public static int getTextColorNormal()
  {
    return -14210768;
  }

  public static int getTextColorNormal_New()
  {
    return -11908534;
  }

  public static int getTextColorRecommend()
  {
    return -10461088;
  }

  public static int getTextColorSecondLevel()
  {
    return -7566196;
  }

  public static int getTextColorSubInfo()
  {
    return -8882056;
  }

  public static int getTextColorThirdLevel()
  {
    return -6974059;
  }

  public static int getTextColorWhite()
  {
    return -1;
  }

  public static Rect getTrimedBitmapRect(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    if (paramInt1 * j > i * paramInt2)
    {
      int m = i * paramInt2 / paramInt1;
      return new Rect(0, (j - m) / 2, i, (j + m) / 2);
    }
    int k = paramInt1 * j / paramInt2;
    return new Rect((i - k) / 2, 0, (i + k) / 2, j);
  }

  public static Rect getTrimedBitmapRectBaseBottom(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    if (paramInt1 * j > i * paramInt2)
      return new Rect(0, j - i * paramInt2 / paramInt1, i, j);
    int k = paramInt1 * j / paramInt2;
    return new Rect((i - k) / 2, 0, (i + k) / 2, j);
  }

  public static Rect getTrimedBitmapRectBaseTop(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    if (paramInt1 * j > i * paramInt2)
      return new Rect(0, 0, i, i * paramInt2 / paramInt1);
    int k = paramInt1 * j / paramInt2;
    return new Rect((i - k) / 2, 0, (i + k) / 2, j);
  }

  public static int getUploadPageElementColor()
  {
    return Color.parseColor("#62e0d7");
  }

  private boolean isLowMemory()
  {
    int i = QTRamInfo.getTotalRam();
    if (i <= 0);
    while (i > 768)
      return false;
    return true;
  }

  public static Bitmap trimBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    int m;
    int k;
    if (paramInt1 * j > i * paramInt2)
    {
      m = i * paramInt2 / paramInt1;
      k = i;
    }
    while (true)
    {
      return Bitmap.createBitmap(paramBitmap, (i - k) / 2, (j - m) / 2, k, m);
      k = paramInt1 * j / paramInt2;
      m = j;
    }
  }

  public static Bitmap trimBitmapBaseTop(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    if (paramInt1 * j > i * paramInt2)
      j = i * paramInt2 / paramInt1;
    for (int k = i; ; k = paramInt1 * j / paramInt2)
      return Bitmap.createBitmap(paramBitmap, (i - k) / 2, 0, (k + i) / 2, j);
  }

  public void calculateFontSize(int paramInt)
  {
    this.mNormalTextSize = (34.0F * paramInt / 720.0F);
    this.mSubTextSize = (26.0F * paramInt / 720.0F);
    this.mMiddleTextSize = (30.0F * paramInt / 720.0F);
    this.mRecommendTextSize = (26.0F * paramInt / 720.0F);
    this.mTinyTextSize = (24.0F * paramInt / 720.0F);
    this.mLargeTextSize = (46.0F * paramInt / 720.0F);
    this.mTeenyTinyTextSize = (22.0F * paramInt / 720.0F);
    this.mSmallLabelTextSize = (18.0F * paramInt / 720.0F);
    this.mNormalTextPaint.setTextSize(this.mNormalTextSize);
    this.mHighlightTextPaint.setTextSize(this.mNormalTextSize);
    this.mHighlightTextPaint2.setTextSize(this.mNormalTextSize);
    this.mSubTextPaint.setTextSize(this.mSubTextSize);
    this.mNormalTextPaint.setColor(getTextColorNormal());
    this.mHighlightTextPaint.setColor(getTextColorHighlight());
    this.mHighlightTextPaint2.setColor(getTextColorHighlight2());
    this.mSubTextPaint.setColor(getTextColorSubInfo());
  }

  public void drawHorizontalLine(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!this.mLineColorSetted)
    {
      this.mUpperLinePaint.setColor(getDividerColor());
      this.mLineColorSetted = true;
    }
    if (this.mLineWidth != paramInt4)
    {
      this.mLineWidth = paramInt4;
      this.mUpperLinePaint.setStrokeWidth(this.mLineWidth);
    }
    paramCanvas.drawLine(paramInt1, paramInt3 + 0.5F * this.mLineWidth, paramInt2, paramInt3 + 0.5F * this.mLineWidth, this.mUpperLinePaint);
  }

  public void drawVerticalLine(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!this.mLineColorSetted)
    {
      this.mUpperLinePaint.setColor(getDividerColor());
      this.mLineColorSetted = true;
    }
    if (this.mLineWidth != paramInt4)
    {
      this.mLineWidth = paramInt4;
      this.mUpperLinePaint.setStrokeWidth(this.mLineWidth);
    }
    paramCanvas.drawLine(paramInt3 + 0.5F * this.mLineWidth, paramInt1, paramInt3 + 0.5F * this.mLineWidth, paramInt2, this.mUpperLinePaint);
  }

  public DrawFilter getDrawFilter()
  {
    if (this.mDrawfilter == null)
      this.mDrawfilter = new PaintFlagsDrawFilter(0, 67);
    return this.mDrawfilter;
  }

  public TextPaint getHighlightTextPaint()
  {
    return this.mHighlightTextPaint;
  }

  public float getLargeTextSize()
  {
    return this.mLargeTextSize;
  }

  public Path getLowerTriangularPath(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2)
  {
    if (this.mTriangularPath == null)
      this.mTriangularPath = new Path();
    this.mTriangularPath.rewind();
    this.mTriangularPath.moveTo(paramFloat1 - paramInt1 / 2.0F, paramFloat2 - paramInt2);
    this.mTriangularPath.lineTo(paramFloat1 + paramInt1 / 2.0F, paramFloat2 - paramInt2);
    this.mTriangularPath.lineTo(paramFloat1, paramFloat2);
    this.mTriangularPath.lineTo(paramFloat1 - paramInt1 / 2.0F, paramFloat2 - paramInt2);
    return this.mTriangularPath;
  }

  public float getMiddleTextSize()
  {
    return this.mMiddleTextSize;
  }

  public TextPaint getNormalTextPaint()
  {
    return this.mNormalTextPaint;
  }

  public float getNormalTextSize()
  {
    return this.mNormalTextSize;
  }

  public float getRecommendTextSize()
  {
    return this.mRecommendTextSize;
  }

  public float getSmallLabelTextSize()
  {
    return this.mSmallLabelTextSize;
  }

  public TextPaint getSubTextPaint()
  {
    return this.mSubTextPaint;
  }

  public float getSubTextSize()
  {
    return this.mSubTextSize;
  }

  public float getTeenyTinyTextSize()
  {
    return this.mTeenyTinyTextSize;
  }

  public float getTinyTextSize()
  {
    return this.mTinyTextSize;
  }

  public Path getUpperTriangularPath(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2)
  {
    if (this.mTriangularPath == null)
      this.mTriangularPath = new Path();
    this.mTriangularPath.rewind();
    this.mTriangularPath.moveTo(paramFloat1 - paramInt1 / 2.0F, paramFloat2 + paramInt2);
    this.mTriangularPath.lineTo(paramFloat1 + paramInt1 / 2.0F, paramFloat2 + paramInt2);
    this.mTriangularPath.lineTo(paramFloat1, paramFloat2);
    this.mTriangularPath.lineTo(paramFloat1 - paramInt1 / 2.0F, paramFloat2 + paramInt2);
    return this.mTriangularPath;
  }

  public TextPaint getmHighlightTextPaint2()
  {
    return this.mHighlightTextPaint2;
  }

  public void setConfig()
  {
    isLowMemory = isLowMemory();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.manager.SkinManager
 * JD-Core Version:    0.6.2
 */