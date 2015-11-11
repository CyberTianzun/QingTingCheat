package fm.qingting.qtradio.view.advertisement;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.utils.ImageLoader;
import fm.qingting.framework.utils.ImageLoaderHandler;
import fm.qingting.framework.view.ViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.advertisement.AdvertisementManage;
import fm.qingting.qtradio.model.advertisement.QTAdvertisementInfo;

public class AdvertisementView extends ViewImpl
  implements ImageLoaderHandler
{
  private final ViewLayout bgLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 241, 480, 800, 0, 70, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private Paint bgPaint = new Paint();
  private Paint btmPaint = new Paint();
  private final ViewLayout buttonClickLayout = ViewLayout.createViewLayoutWithBoundsLT(80, 65, 480, 800, 410, 70, ViewLayout.RT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout buttonLayout = ViewLayout.createViewLayoutWithBoundsLT(45, 45, 480, 800, 410, 80, ViewLayout.RT | ViewLayout.SLT | ViewLayout.CW);
  private final ViewLayout contentLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 350, 480, 800, 0, 70, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final DrawFilter filter = new PaintFlagsDrawFilter(0, 67);
  private boolean isselectbutton = false;
  private final ViewLayout lbLayout = ViewLayout.createViewLayoutWithBoundsLT(440, 80, 480, 800, 20, 311, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private boolean onTouchDown = false;
  private ClickType preType = ClickType.none;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);
  private TextPaint textPaint = new TextPaint();
  private int touchDownPositionX = 0;
  private int touchDownPositionY = 0;

  public AdvertisementView(Context paramContext)
  {
    super(paramContext);
    this.btmPaint.setAntiAlias(true);
    this.bgPaint.setAntiAlias(true);
    this.bgPaint.setStyle(Paint.Style.FILL);
    this.bgPaint.setColor(-297450171);
    this.textPaint.setAntiAlias(true);
    this.textPaint.setColor(-1);
  }

  private void drawBitmap(Canvas paramCanvas, int paramInt, ViewLayout paramViewLayout)
  {
    Bitmap localBitmap = BitmapResourceCache.getInstance().getResourceCache(getResources(), this, paramInt);
    paramCanvas.drawBitmap(localBitmap, new Rect(0, 0, localBitmap.getWidth(), localBitmap.getHeight()), paramViewLayout.getRect(), this.btmPaint);
  }

  private void drawBitmap(Canvas paramCanvas, String paramString, ViewLayout paramViewLayout)
  {
    Bitmap localBitmap = ImageLoader.getInstance(getContext()).getImage(paramString, paramViewLayout.width, paramViewLayout.height);
    if ((localBitmap == null) || (!localBitmap.isRecycled()))
      ImageLoader.getInstance(getContext()).loadImage(paramString, null, this, paramViewLayout.width, paramViewLayout.height, this);
    if ((localBitmap != null) && (!localBitmap.isRecycled()))
      paramCanvas.drawBitmap(localBitmap, new Rect(0, 0, localBitmap.getWidth(), localBitmap.getHeight()), paramViewLayout.getRect(), this.btmPaint);
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public void loadImageFinish(boolean paramBoolean, String paramString, Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    if (paramBoolean)
      invalidate();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.save();
    paramCanvas.setDrawFilter(this.filter);
    QTAdvertisementInfo localQTAdvertisementInfo = AdvertisementManage.get_qtAdvertisementInfo();
    if (localQTAdvertisementInfo == null)
      return;
    String str1 = localQTAdvertisementInfo.adThumb;
    String str2 = localQTAdvertisementInfo.adDescription;
    if (str2 == null)
      str2 = "";
    paramCanvas.drawRect(this.contentLayout.getRect(), this.bgPaint);
    drawBitmap(paramCanvas, str1, this.bgLayout);
    if (this.isselectbutton);
    for (int i = 2130837535; ; i = 2130837534)
    {
      drawBitmap(paramCanvas, i, this.buttonLayout);
      if ((str2 != null) && (!str2.equalsIgnoreCase("")))
      {
        StaticLayout localStaticLayout = new StaticLayout(str2, this.textPaint, this.lbLayout.width, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        paramCanvas.translate(this.lbLayout.getLeft(), this.bgLayout.getBottom());
        localStaticLayout.draw(paramCanvas);
      }
      paramCanvas.restore();
      return;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.bgLayout.scaleToBounds(this.standardLayout);
    this.contentLayout.scaleToBounds(this.standardLayout);
    this.lbLayout.scaleToBounds(this.standardLayout);
    this.buttonClickLayout.scaleToBounds(this.standardLayout);
    this.buttonLayout.scaleToBounds(this.standardLayout);
    this.textPaint.setTextSize(0.055F * this.lbLayout.width);
    setMeasuredDimension(i, i);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 2:
    case 1:
    }
    do
    {
      do
      {
        return true;
        this.touchDownPositionX = ((int)paramMotionEvent.getX());
        this.touchDownPositionY = ((int)paramMotionEvent.getY());
        if (this.buttonClickLayout.getRect().contains(this.touchDownPositionX, this.touchDownPositionY))
          this.preType = ClickType.btn;
        while (true)
        {
          if (this.preType.isbtn())
          {
            this.isselectbutton = true;
            this.onTouchDown = true;
          }
          if (this.preType.isbg())
          {
            this.isselectbutton = false;
            this.onTouchDown = true;
          }
          if (this.preType.isnone())
          {
            this.isselectbutton = false;
            this.onTouchDown = false;
          }
          invalidate();
          postInvalidate();
          return true;
          if (this.bgLayout.getRect().contains(this.touchDownPositionX, this.touchDownPositionY))
            this.preType = ClickType.bg;
          else
            this.preType = ClickType.none;
        }
      }
      while (!this.onTouchDown);
      this.touchDownPositionX = ((int)paramMotionEvent.getX());
      this.touchDownPositionY = ((int)paramMotionEvent.getY());
      if (this.buttonClickLayout.getRect().contains(this.touchDownPositionX, this.touchDownPositionY))
        this.preType = ClickType.btn;
      while (true)
      {
        if (this.preType.isbtn())
        {
          this.isselectbutton = true;
          this.onTouchDown = true;
        }
        if (this.preType.isbg())
        {
          this.isselectbutton = false;
          this.onTouchDown = true;
        }
        if (this.preType.isnone())
        {
          this.isselectbutton = false;
          this.onTouchDown = false;
        }
        invalidate();
        return true;
        if (this.bgLayout.getRect().contains(this.touchDownPositionX, this.touchDownPositionY))
          this.preType = ClickType.bg;
        else
          this.preType = ClickType.none;
      }
    }
    while (!this.onTouchDown);
    if (this.preType.isbg())
      dispatchActionEvent("openADRegin", null);
    while (true)
    {
      this.isselectbutton = false;
      this.onTouchDown = false;
      invalidate();
      return true;
      if (this.preType.isbtn())
        dispatchActionEvent("closeAdView", null);
    }
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setBubbledata"))
      invalidate();
  }

  public void updateImageViewFinish(boolean paramBoolean, ImageView paramImageView, String paramString, Bitmap paramBitmap)
  {
  }

  private static enum ClickType
  {
    static
    {
      bg = new ClickType("bg", 1);
      btn = new ClickType("btn", 2);
      ClickType[] arrayOfClickType = new ClickType[3];
      arrayOfClickType[0] = none;
      arrayOfClickType[1] = bg;
      arrayOfClickType[2] = btn;
    }

    public boolean isbg()
    {
      return this == bg;
    }

    public boolean isbtn()
    {
      return this == btn;
    }

    public boolean isnone()
    {
      return this == none;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.advertisement.AdvertisementView
 * JD-Core Version:    0.6.2
 */