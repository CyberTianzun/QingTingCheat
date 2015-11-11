package master.flame.danmaku.danmaku.model.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.text.TextPaint;
import java.util.HashMap;
import java.util.Map;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.BaseDanmaku;

public class AndroidDisplayer extends AbsDisplayer<Canvas>
{
  private static Paint ALPHA_PAINT;
  private static boolean ANTI_ALIAS = false;
  public static final int BG_CORNER = 6;
  public static final int BG_HEIGHT = 12;
  private static Paint BG_PAINT;
  private static RectF BG_RECTF = new RectF();
  public static final int BG_WIDTH = 24;
  private static Paint BORDER_PAINT;
  public static final int BORDER_WIDTH = 4;
  public static boolean CONFIG_ANTI_ALIAS = false;
  public static boolean CONFIG_HAS_PROJECTION = false;
  public static boolean CONFIG_HAS_SHADOW = false;
  public static boolean CONFIG_HAS_STROKE = false;
  public static final int DRAWABLE_PADDING = 20;
  private static Paint DRAWABLE_PAINT;
  private static Rect DRAWABLE_RECT = new Rect();
  public static final int DRAWABLE_SIZE = 44;
  private static boolean HAS_PROJECTION;
  private static boolean HAS_SHADOW;
  private static boolean HAS_STROKE;
  public static TextPaint PAINT;
  public static TextPaint PAINT_DUPLICATE;
  private static float SHADOW_RADIUS;
  private static float STROKE_WIDTH;
  public static int UNDERLINE_HEIGHT;
  private static Paint UNDERLINE_PAINT;
  private static final Map<Integer, Bitmap> sCachedBitmap;
  private static final Map<Float, Float> sCachedScaleSize;
  private static float sLastScaleTextSize;
  private static int sProjectionAlpha;
  private static float sProjectionOffsetX;
  private static float sProjectionOffsetY;
  private static final Map<Float, Float> sTextHeightCache = new HashMap();
  private int HIT_CACHE_COUNT = 0;
  private int NO_CACHE_COUNT = 0;
  private Camera camera = new Camera();
  public Canvas canvas;
  private Context context;
  private float density = 1.0F;
  private int densityDpi = 160;
  private int height;
  private boolean mIsHardwareAccelerated = true;
  private int mMaximumBitmapHeight = 2048;
  private int mMaximumBitmapWidth = 2048;
  private int mSlopPixel = 0;
  private Matrix matrix = new Matrix();
  private float scaledDensity = 1.0F;
  private int width;

  static
  {
    sCachedScaleSize = new HashMap(10);
    sCachedBitmap = new HashMap(2);
    UNDERLINE_HEIGHT = 4;
    SHADOW_RADIUS = 4.0F;
    STROKE_WIDTH = 3.5F;
    sProjectionOffsetX = 1.0F;
    sProjectionOffsetY = 1.0F;
    sProjectionAlpha = 204;
    CONFIG_HAS_SHADOW = false;
    HAS_SHADOW = CONFIG_HAS_SHADOW;
    CONFIG_HAS_STROKE = true;
    HAS_STROKE = CONFIG_HAS_STROKE;
    CONFIG_HAS_PROJECTION = false;
    HAS_PROJECTION = CONFIG_HAS_PROJECTION;
    CONFIG_ANTI_ALIAS = true;
    ANTI_ALIAS = CONFIG_ANTI_ALIAS;
    PAINT = new TextPaint();
    PAINT.setStrokeWidth(STROKE_WIDTH);
    PAINT_DUPLICATE = new TextPaint(PAINT);
    ALPHA_PAINT = new Paint();
    UNDERLINE_PAINT = new Paint();
    UNDERLINE_PAINT.setStrokeWidth(UNDERLINE_HEIGHT);
    UNDERLINE_PAINT.setStyle(Paint.Style.STROKE);
    BORDER_PAINT = new Paint();
    BORDER_PAINT.setStyle(Paint.Style.STROKE);
    BORDER_PAINT.setStrokeWidth(4.0F);
    DRAWABLE_PAINT = new Paint();
    BG_PAINT = new Paint();
    BG_PAINT.setStyle(Paint.Style.FILL);
  }

  private static void applyPaintConfig(BaseDanmaku paramBaseDanmaku, Paint paramPaint, boolean paramBoolean)
  {
    if (DanmakuGlobalConfig.DEFAULT.isTranslucent)
    {
      if (paramBoolean)
      {
        Paint.Style localStyle2;
        if (HAS_PROJECTION)
        {
          localStyle2 = Paint.Style.FILL;
          paramPaint.setStyle(localStyle2);
          paramPaint.setColor(0xFFFFFF & paramBaseDanmaku.textShadowColor);
          if (!HAS_PROJECTION)
            break label82;
        }
        label82: for (int j = (int)(sProjectionAlpha * (DanmakuGlobalConfig.DEFAULT.transparency / AlphaValue.MAX)); ; j = DanmakuGlobalConfig.DEFAULT.transparency)
        {
          paramPaint.setAlpha(j);
          return;
          localStyle2 = Paint.Style.STROKE;
          break;
        }
      }
      paramPaint.setStyle(Paint.Style.FILL);
      paramPaint.setColor(0xFFFFFF & paramBaseDanmaku.textColor);
      paramPaint.setAlpha(DanmakuGlobalConfig.DEFAULT.transparency);
      return;
    }
    if (paramBoolean)
    {
      Paint.Style localStyle1;
      if (HAS_PROJECTION)
      {
        localStyle1 = Paint.Style.FILL;
        paramPaint.setStyle(localStyle1);
        paramPaint.setColor(0xFFFFFF & paramBaseDanmaku.textShadowColor);
        if (!HAS_PROJECTION)
          break label177;
      }
      label177: for (int i = sProjectionAlpha; ; i = AlphaValue.MAX)
      {
        paramPaint.setAlpha(i);
        return;
        localStyle1 = Paint.Style.STROKE;
        break;
      }
    }
    paramPaint.setStyle(Paint.Style.FILL);
    paramPaint.setColor(0xFFFFFF & paramBaseDanmaku.textColor);
    paramPaint.setAlpha(AlphaValue.MAX);
  }

  private static void applyTextScaleConfig(BaseDanmaku paramBaseDanmaku, Paint paramPaint)
  {
    if (!DanmakuGlobalConfig.DEFAULT.isTextScaled)
      return;
    Float localFloat = (Float)sCachedScaleSize.get(Float.valueOf(paramBaseDanmaku.textSize));
    if ((localFloat == null) || (sLastScaleTextSize != DanmakuGlobalConfig.DEFAULT.scaleTextSize))
    {
      sLastScaleTextSize = DanmakuGlobalConfig.DEFAULT.scaleTextSize;
      localFloat = Float.valueOf(paramBaseDanmaku.textSize * DanmakuGlobalConfig.DEFAULT.scaleTextSize);
      sCachedScaleSize.put(Float.valueOf(paramBaseDanmaku.textSize), localFloat);
    }
    paramPaint.setTextSize(localFloat.floatValue());
  }

  private void calcPaintWH(BaseDanmaku paramBaseDanmaku, TextPaint paramTextPaint)
  {
    float f1 = 0.0F;
    Float localFloat = Float.valueOf(getTextHeight(paramTextPaint));
    if (paramBaseDanmaku.lines == null)
    {
      String str2 = paramBaseDanmaku.text;
      float f2 = 0.0F;
      if (str2 == null);
      while (true)
      {
        setDanmakuPaintWidthAndHeight(paramBaseDanmaku, f2, localFloat.floatValue());
        return;
        f2 = paramTextPaint.measureText(paramBaseDanmaku.text);
      }
    }
    for (String str1 : paramBaseDanmaku.lines)
      if (str1.length() > 0)
        f1 = Math.max(paramTextPaint.measureText(str1), f1);
    setDanmakuPaintWidthAndHeight(paramBaseDanmaku, f1, paramBaseDanmaku.lines.length * localFloat.floatValue());
  }

  public static void clearTextHeightCache()
  {
    sTextHeightCache.clear();
    sCachedScaleSize.clear();
  }

  public static void drawDanmaku(Context paramContext, BaseDanmaku paramBaseDanmaku, Canvas paramCanvas, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    float f1 = paramFloat1 + paramBaseDanmaku.padding;
    float f2 = paramFloat2 + paramBaseDanmaku.padding;
    float f3;
    float f4;
    if (paramBaseDanmaku.borderColor != 0)
    {
      float f14 = f1 + 4.0F;
      f3 = 4.0F + f2;
      f4 = f14;
    }
    while (true)
    {
      if (paramBaseDanmaku.drawableLeftResid != 0)
      {
        Bitmap localBitmap = getDrawableBitmap(paramContext, paramBaseDanmaku.drawableLeftResid);
        int j = (int)f4;
        int k = (int)(12.0F + paramFloat2 + (paramBaseDanmaku.paintHeight - 44.0F) / 2.0F);
        DRAWABLE_RECT.set(-20 + (j - 44), k, j - 20, k + 44);
        paramCanvas.drawBitmap(localBitmap, null, DRAWABLE_RECT, DRAWABLE_PAINT);
      }
      HAS_STROKE = CONFIG_HAS_STROKE;
      HAS_SHADOW = CONFIG_HAS_SHADOW;
      HAS_PROJECTION = CONFIG_HAS_PROJECTION;
      boolean bool;
      label157: TextPaint localTextPaint;
      String[] arrayOfString;
      float f11;
      float f12;
      if ((!paramBoolean) && (CONFIG_ANTI_ALIAS))
      {
        bool = true;
        ANTI_ALIAS = bool;
        localTextPaint = getPaint(paramBaseDanmaku, paramBoolean);
        if (paramBaseDanmaku.lines == null)
          break label630;
        arrayOfString = paramBaseDanmaku.lines;
        if (arrayOfString.length != 1)
          break label469;
        if (hasStroke(paramBaseDanmaku))
        {
          applyPaintConfig(paramBaseDanmaku, localTextPaint, true);
          f11 = f3 - localTextPaint.ascent();
          if (!HAS_PROJECTION)
            break label734;
          f12 = f4 + sProjectionOffsetX;
          f11 += sProjectionOffsetY;
        }
      }
      while (true)
      {
        paramCanvas.drawText(arrayOfString[0], f12, f11, localTextPaint);
        applyPaintConfig(paramBaseDanmaku, localTextPaint, false);
        paramCanvas.drawText(arrayOfString[0], f4, f3 - localTextPaint.ascent(), localTextPaint);
        label469: float f8;
        int i;
        while (true)
        {
          if (paramBaseDanmaku.underlineColor != 0)
          {
            Paint localPaint2 = getUnderlinePaint(paramBaseDanmaku);
            float f5 = paramFloat2 + paramBaseDanmaku.paintHeight - UNDERLINE_HEIGHT;
            paramCanvas.drawLine(paramFloat1, f5, paramFloat1 + paramBaseDanmaku.paintWidth, f5, localPaint2);
          }
          if (paramBaseDanmaku.borderColor != 0)
          {
            Paint localPaint1 = getBorderPaint(paramBaseDanmaku);
            paramCanvas.drawRect(paramFloat1, paramFloat2, paramFloat1 + paramBaseDanmaku.paintWidth, paramFloat2 + paramBaseDanmaku.paintHeight, localPaint1);
          }
          return;
          if (paramBaseDanmaku.bgColor == 0)
            break label741;
          Paint localPaint3 = getBgPaint(paramBaseDanmaku);
          int m = paramBaseDanmaku.drawableLeftResid;
          int n = 0;
          if (m != 0)
            n = 64;
          BG_RECTF.set(paramFloat1, paramFloat2, 48.0F + (paramFloat1 + paramBaseDanmaku.paintWidth) + n, 24.0F + (paramFloat2 + paramBaseDanmaku.paintHeight));
          paramCanvas.drawRoundRect(BG_RECTF, 6.0F, 6.0F, localPaint3);
          float f13 = f1 + (n + 24);
          f3 = 12.0F + f2;
          f4 = f13;
          break;
          bool = false;
          break label157;
          f8 = (paramBaseDanmaku.paintHeight - 2 * paramBaseDanmaku.padding) / arrayOfString.length;
          label518: for (i = 0; i < arrayOfString.length; i++)
            if ((arrayOfString[i] != null) && (arrayOfString[i].length() != 0))
              break label524;
        }
        label524: float f9;
        float f10;
        if (hasStroke(paramBaseDanmaku))
        {
          applyPaintConfig(paramBaseDanmaku, localTextPaint, true);
          f9 = f3 + f8 * i - localTextPaint.ascent();
          if (!HAS_PROJECTION)
            break label727;
          f10 = f4 + sProjectionOffsetX;
          f9 += sProjectionOffsetY;
        }
        while (true)
        {
          paramCanvas.drawText(arrayOfString[i], f10, f9, localTextPaint);
          applyPaintConfig(paramBaseDanmaku, localTextPaint, false);
          paramCanvas.drawText(arrayOfString[i], f4, f3 + f8 * i - localTextPaint.ascent(), localTextPaint);
          break label518;
          label630: float f6;
          float f7;
          if (hasStroke(paramBaseDanmaku))
          {
            applyPaintConfig(paramBaseDanmaku, localTextPaint, true);
            f6 = f3 - localTextPaint.ascent();
            if (!HAS_PROJECTION)
              break label720;
            f7 = f4 + sProjectionOffsetX;
            f6 += sProjectionOffsetY;
          }
          while (true)
          {
            paramCanvas.drawText(paramBaseDanmaku.text, f7, f6, localTextPaint);
            applyPaintConfig(paramBaseDanmaku, localTextPaint, false);
            paramCanvas.drawText(paramBaseDanmaku.text, f4, f3 - localTextPaint.ascent(), localTextPaint);
            break;
            label720: f7 = f4;
          }
          label727: f10 = f4;
        }
        label734: f12 = f4;
      }
      label741: f3 = f2;
      f4 = f1;
    }
  }

  public static Paint getBgPaint(BaseDanmaku paramBaseDanmaku)
  {
    BG_PAINT.setColor(paramBaseDanmaku.bgColor);
    return BG_PAINT;
  }

  public static Paint getBorderPaint(BaseDanmaku paramBaseDanmaku)
  {
    BORDER_PAINT.setColor(paramBaseDanmaku.borderColor);
    return BORDER_PAINT;
  }

  private static Bitmap getDrawableBitmap(Context paramContext, int paramInt)
  {
    Bitmap localBitmap = (Bitmap)sCachedBitmap.get(Integer.valueOf(paramInt));
    if ((localBitmap == null) || (localBitmap.isRecycled()))
    {
      localBitmap = BitmapFactory.decodeResource(paramContext.getResources(), paramInt);
      sCachedBitmap.put(Integer.valueOf(paramInt), localBitmap);
    }
    return localBitmap;
  }

  @SuppressLint({"NewApi"})
  private static final int getMaximumBitmapHeight(Canvas paramCanvas)
  {
    if (Build.VERSION.SDK_INT >= 14)
      return paramCanvas.getMaximumBitmapHeight();
    return paramCanvas.getHeight();
  }

  @SuppressLint({"NewApi"})
  private static final int getMaximumBitmapWidth(Canvas paramCanvas)
  {
    if (Build.VERSION.SDK_INT >= 14)
      return paramCanvas.getMaximumBitmapWidth();
    return paramCanvas.getWidth();
  }

  public static TextPaint getPaint(BaseDanmaku paramBaseDanmaku)
  {
    return getPaint(paramBaseDanmaku, false);
  }

  private static TextPaint getPaint(BaseDanmaku paramBaseDanmaku, boolean paramBoolean)
  {
    TextPaint localTextPaint;
    if (paramBoolean)
    {
      localTextPaint = PAINT_DUPLICATE;
      localTextPaint.set(PAINT);
      localTextPaint.setTextSize(paramBaseDanmaku.textSize);
      applyTextScaleConfig(paramBaseDanmaku, localTextPaint);
      if ((HAS_SHADOW) && (SHADOW_RADIUS > 0.0F) && (paramBaseDanmaku.textShadowColor != 0))
        break label69;
      localTextPaint.clearShadowLayer();
    }
    while (true)
    {
      localTextPaint.setAntiAlias(ANTI_ALIAS);
      return localTextPaint;
      localTextPaint = PAINT;
      break;
      label69: localTextPaint.setShadowLayer(SHADOW_RADIUS, 0.0F, 0.0F, paramBaseDanmaku.textShadowColor);
    }
  }

  private static float getTextHeight(TextPaint paramTextPaint)
  {
    Float localFloat1 = Float.valueOf(paramTextPaint.getTextSize());
    Float localFloat2 = (Float)sTextHeightCache.get(localFloat1);
    if (localFloat2 == null)
    {
      Paint.FontMetrics localFontMetrics = paramTextPaint.getFontMetrics();
      localFloat2 = Float.valueOf(localFontMetrics.descent - localFontMetrics.ascent + localFontMetrics.leading);
      sTextHeightCache.put(localFloat1, localFloat2);
    }
    return localFloat2.floatValue();
  }

  public static Paint getUnderlinePaint(BaseDanmaku paramBaseDanmaku)
  {
    UNDERLINE_PAINT.setColor(paramBaseDanmaku.underlineColor);
    return UNDERLINE_PAINT;
  }

  private static boolean hasStroke(BaseDanmaku paramBaseDanmaku)
  {
    return ((HAS_STROKE) || (HAS_PROJECTION)) && (STROKE_WIDTH > 0.0F) && (paramBaseDanmaku.textShadowColor != 0);
  }

  private void resetPaintAlpha(Paint paramPaint)
  {
    if (paramPaint.getAlpha() != AlphaValue.MAX)
      paramPaint.setAlpha(AlphaValue.MAX);
  }

  private void restoreCanvas(Canvas paramCanvas)
  {
    paramCanvas.restore();
  }

  private int saveCanvas(BaseDanmaku paramBaseDanmaku, Canvas paramCanvas, float paramFloat1, float paramFloat2)
  {
    this.camera.save();
    this.camera.rotateY(-paramBaseDanmaku.rotationY);
    this.camera.rotateZ(-paramBaseDanmaku.rotationZ);
    this.camera.getMatrix(this.matrix);
    this.matrix.preTranslate(-paramFloat1, -paramFloat2);
    this.matrix.postTranslate(paramFloat1, paramFloat2);
    this.camera.restore();
    int i = paramCanvas.save();
    paramCanvas.concat(this.matrix);
    return i;
  }

  private void setDanmakuPaintWidthAndHeight(BaseDanmaku paramBaseDanmaku, float paramFloat1, float paramFloat2)
  {
    float f1 = paramFloat1 + 2 * paramBaseDanmaku.padding;
    float f2 = paramFloat2 + 2 * paramBaseDanmaku.padding;
    if (paramBaseDanmaku.borderColor != 0)
    {
      f1 += 8.0F;
      f2 += 8.0F;
    }
    paramBaseDanmaku.paintWidth = (f1 + getStrokeWidth());
    paramBaseDanmaku.paintHeight = f2;
  }

  public static void setFakeBoldText(boolean paramBoolean)
  {
    PAINT.setFakeBoldText(paramBoolean);
  }

  public static void setPaintStorkeWidth(float paramFloat)
  {
    PAINT.setStrokeWidth(paramFloat);
    STROKE_WIDTH = paramFloat;
  }

  public static void setProjectionConfig(float paramFloat1, float paramFloat2, int paramInt)
  {
    if ((sProjectionOffsetX != paramFloat1) || (sProjectionOffsetY != paramFloat2) || (sProjectionAlpha != paramInt))
    {
      if (paramFloat1 <= 1.0F)
        break label54;
      sProjectionOffsetX = paramFloat1;
      if (paramFloat2 <= 1.0F)
        break label59;
      label39: sProjectionOffsetY = paramFloat2;
      if (paramInt >= 0)
        break label64;
      paramInt = 0;
    }
    while (true)
    {
      sProjectionAlpha = paramInt;
      return;
      label54: paramFloat1 = 1.0F;
      break;
      label59: paramFloat2 = 1.0F;
      break label39;
      label64: if (paramInt > 255)
        paramInt = 255;
    }
  }

  public static void setShadowRadius(float paramFloat)
  {
    SHADOW_RADIUS = paramFloat;
  }

  public static void setTypeFace(Typeface paramTypeface)
  {
    if (PAINT != null)
      PAINT.setTypeface(paramTypeface);
  }

  private void update(Canvas paramCanvas)
  {
    this.canvas = paramCanvas;
    if (paramCanvas != null)
    {
      this.width = paramCanvas.getWidth();
      this.height = paramCanvas.getHeight();
      if (this.mIsHardwareAccelerated)
      {
        this.mMaximumBitmapWidth = getMaximumBitmapWidth(paramCanvas);
        this.mMaximumBitmapHeight = getMaximumBitmapHeight(paramCanvas);
      }
    }
  }

  public int draw(BaseDanmaku paramBaseDanmaku)
  {
    float f1 = paramBaseDanmaku.getTop();
    float f2 = paramBaseDanmaku.getLeft();
    Canvas localCanvas = this.canvas;
    int i = 0;
    if (localCanvas != null)
    {
      if (paramBaseDanmaku.getType() != 7)
        break label282;
      int n = paramBaseDanmaku.getAlpha();
      int i1 = AlphaValue.TRANSPARENT;
      i = 0;
      if (n != i1);
    }
    else
    {
      return i;
    }
    int i2;
    label90: Paint localPaint;
    if ((paramBaseDanmaku.rotationZ != 0.0F) || (paramBaseDanmaku.rotationY != 0.0F))
    {
      saveCanvas(paramBaseDanmaku, this.canvas, f2, f1);
      i2 = 1;
      int i3 = paramBaseDanmaku.getAlpha();
      int i4 = AlphaValue.MAX;
      localPaint = null;
      if (i3 != i4)
      {
        localPaint = ALPHA_PAINT;
        localPaint.setAlpha(paramBaseDanmaku.getAlpha());
      }
    }
    for (int j = i2; ; j = 0)
    {
      if (localPaint != null)
      {
        int k = localPaint.getAlpha();
        int m = AlphaValue.TRANSPARENT;
        i = 0;
        if (k == m)
          break;
      }
      boolean bool1 = paramBaseDanmaku.hasDrawingCache();
      boolean bool2 = false;
      if (bool1)
      {
        DrawingCacheHolder localDrawingCacheHolder = ((DrawingCache)paramBaseDanmaku.cache).get();
        bool2 = false;
        if (localDrawingCacheHolder != null)
          bool2 = localDrawingCacheHolder.draw(this.canvas, f2, f1, localPaint);
      }
      if (!bool2)
        if (localPaint != null)
        {
          PAINT.setAlpha(localPaint.getAlpha());
          drawDanmaku(this.context, paramBaseDanmaku, this.canvas, f2, f1, true);
        }
      for (i = 2; ; i = 1)
      {
        if (j == 0)
          break label274;
        restoreCanvas(this.canvas);
        return i;
        resetPaintAlpha(PAINT);
        break;
      }
      label274: break;
      i2 = 0;
      break label90;
      label282: localPaint = null;
    }
  }

  public Context getContext()
  {
    return this.context;
  }

  public float getDensity()
  {
    return this.density;
  }

  public int getDensityDpi()
  {
    return this.densityDpi;
  }

  public Canvas getExtraData()
  {
    return this.canvas;
  }

  public int getHeight()
  {
    return this.height;
  }

  public int getMaximumCacheHeight()
  {
    return this.mMaximumBitmapHeight;
  }

  public int getMaximumCacheWidth()
  {
    return this.mMaximumBitmapWidth;
  }

  public float getScaledDensity()
  {
    return this.scaledDensity;
  }

  public int getSlopPixel()
  {
    return this.mSlopPixel;
  }

  public float getStrokeWidth()
  {
    if ((HAS_SHADOW) && (HAS_STROKE))
      return Math.max(SHADOW_RADIUS, STROKE_WIDTH);
    if (HAS_SHADOW)
      return SHADOW_RADIUS;
    if (HAS_STROKE)
      return STROKE_WIDTH;
    return 0.0F;
  }

  public int getWidth()
  {
    return this.width;
  }

  public boolean isHardwareAccelerated()
  {
    return this.mIsHardwareAccelerated;
  }

  public void measure(BaseDanmaku paramBaseDanmaku)
  {
    TextPaint localTextPaint = getPaint(paramBaseDanmaku);
    if (HAS_STROKE)
      applyPaintConfig(paramBaseDanmaku, localTextPaint, true);
    calcPaintWH(paramBaseDanmaku, localTextPaint);
    if (HAS_STROKE)
      applyPaintConfig(paramBaseDanmaku, localTextPaint, false);
  }

  public void resetSlopPixel(float paramFloat)
  {
    Math.max(this.density, this.scaledDensity);
    float f = 25.0F * Math.max(paramFloat, getWidth() / 682.0F);
    this.mSlopPixel = ((int)f);
    if (paramFloat > 1.0F)
      this.mSlopPixel = ((int)(f * paramFloat));
  }

  public void setContext(Context paramContext)
  {
    this.context = paramContext;
  }

  public void setDensities(float paramFloat1, int paramInt, float paramFloat2)
  {
    this.density = paramFloat1;
    this.densityDpi = paramInt;
    this.scaledDensity = paramFloat2;
  }

  public void setExtraData(Canvas paramCanvas)
  {
    update(paramCanvas);
  }

  public void setHardwareAccelerated(boolean paramBoolean)
  {
    this.mIsHardwareAccelerated = paramBoolean;
  }

  public void setSize(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.model.android.AndroidDisplayer
 * JD-Core Version:    0.6.2
 */