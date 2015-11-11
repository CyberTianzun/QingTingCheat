package fm.qingting.qtradio.view.chatroom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.ViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;

public class FakeHeadView extends ViewImpl
{
  private final ViewLayout actualLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 228, 480, 800, 0, 0, ViewLayout.LT | ViewLayout.SLT | ViewLayout.CW);
  private final DrawFilter filter = SkinManager.getInstance().getDrawFilter();
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);
  private final String text = "已经到顶啦，没有更多评论了：）";
  private final Paint textPaint = new Paint();

  public FakeHeadView(Context paramContext)
  {
    super(paramContext);
    this.textPaint.setColor(-14013910);
  }

  private void drawText(Canvas paramCanvas)
  {
    Rect localRect = new Rect();
    this.textPaint.getTextBounds("已经到顶啦，没有更多评论了：）", 0, "已经到顶啦，没有更多评论了：）".length(), localRect);
    paramCanvas.drawText("已经到顶啦，没有更多评论了：）", (this.actualLayout.width - localRect.width()) / 2.0F, this.actualLayout.height / 2.0F - (localRect.top + localRect.bottom) / 2.0F, this.textPaint);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.save();
    paramCanvas.setDrawFilter(this.filter);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.actualLayout.scaleToBounds(this.standardLayout);
    this.textPaint.setTextSize(0.06F * this.actualLayout.width);
    setMeasuredDimension(this.actualLayout.width, this.actualLayout.height);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.chatroom.FakeHeadView
 * JD-Core Version:    0.6.2
 */