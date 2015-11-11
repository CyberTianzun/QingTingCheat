package fm.qingting.qtradio.view.popviews;

import android.content.Context;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.helper.OnlineUpdateHelper;

public class OnlineUpdatePopuView extends ViewGroupViewImpl
  implements View.OnClickListener
{
  private final RectF mBgRectF = new RectF();
  private TextView mCancelTv;
  private RelativeLayout mContentView = (RelativeLayout)inflate(getContext(), 2130903064, null);
  private TextView mDownloadTv;
  private TextView mMessageTv;
  private RelativeLayout mQuickDonwloadTv;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public OnlineUpdatePopuView(Context paramContext)
  {
    super(paramContext);
    addView(this.mContentView);
    this.mMessageTv = ((TextView)this.mContentView.findViewById(2131230863));
    this.mCancelTv = ((TextView)this.mContentView.findViewById(2131230865));
    this.mDownloadTv = ((TextView)this.mContentView.findViewById(2131230866));
    this.mQuickDonwloadTv = ((RelativeLayout)this.mContentView.findViewById(2131230867));
    if (!OnlineUpdateHelper.getInstance().needQuickDownload())
      this.mQuickDonwloadTv.setVisibility(8);
    this.mCancelTv.setOnClickListener(this);
    this.mDownloadTv.setOnClickListener(this);
    this.mQuickDonwloadTv.setOnClickListener(this);
    this.mContentView.setOnClickListener(this);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((paramMotionEvent.getAction() == 0) && (!this.mBgRectF.contains(paramMotionEvent.getX(), paramMotionEvent.getY())))
    {
      dispatchActionEvent("cancelPop", null);
      return true;
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public void onClick(View paramView)
  {
    if (paramView == this.mCancelTv)
    {
      OnlineUpdateHelper.getInstance().sendEventMessage("updateWait");
      dispatchActionEvent("cancelPop", null);
    }
    do
    {
      return;
      if (paramView == this.mDownloadTv)
      {
        OnlineUpdateHelper.getInstance().download();
        dispatchActionEvent("cancelPop", null);
        OnlineUpdateHelper.getInstance().sendEventMessage("updateDownload");
        return;
      }
      if (paramView == this.mQuickDonwloadTv)
      {
        OnlineUpdateHelper.getInstance().quickDownload();
        dispatchActionEvent("cancelPop", null);
        OnlineUpdateHelper.getInstance().sendEventMessage("updateLightDownload");
        return;
      }
    }
    while (paramView != this.mContentView);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = this.mContentView.getMeasuredWidth();
    int j = this.mContentView.getMeasuredHeight();
    int k = (this.standardLayout.width - i) / 2;
    int m = (i + this.standardLayout.width) / 2;
    int n = this.standardLayout.height - j;
    int i1 = this.standardLayout.height;
    this.mBgRectF.set(k, n, m, i1);
    this.mContentView.layout(k, n, m, i1);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.mContentView.measure(paramInt1, -2);
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramObject != null)
    {
      String str = (String)paramObject;
      if (!str.equalsIgnoreCase(""))
        this.mMessageTv.setText(str);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.OnlineUpdatePopuView
 * JD-Core Version:    0.6.2
 */