package fm.qingting.qtradio.view.scheduleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import java.util.Locale;

public class BatchDownloadConfirmView extends ViewGroupViewImpl
{
  private final ViewLayout leftButtonLayout = this.standardLayout.createChildLT(300, 80, 40, 58, ViewLayout.SCALE_FLAG_SLTCW);
  private boolean mAllSelected = false;
  private Button mConfirmButton;
  private boolean mEnabled = false;
  private Button mSelectButton;
  private TextView mTip;
  private final ViewLayout rightButtonLayout = this.standardLayout.createChildLT(300, 80, 380, 58, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 153, 720, 153, 0, 0, ViewLayout.FILL);
  private final ViewLayout tipLayout = this.standardLayout.createChildLT(720, 43, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public BatchDownloadConfirmView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getCardColor());
    LayoutInflater localLayoutInflater = LayoutInflater.from(paramContext);
    this.mSelectButton = ((Button)localLayoutInflater.inflate(2130903053, null));
    this.mSelectButton.setText("全选");
    addView(this.mSelectButton);
    this.mSelectButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        BatchDownloadConfirmView localBatchDownloadConfirmView = BatchDownloadConfirmView.this;
        boolean bool;
        Button localButton;
        if (!BatchDownloadConfirmView.this.mAllSelected)
        {
          bool = true;
          BatchDownloadConfirmView.access$002(localBatchDownloadConfirmView, bool);
          localButton = BatchDownloadConfirmView.this.mSelectButton;
          if (!BatchDownloadConfirmView.this.mAllSelected)
            break label78;
        }
        label78: for (String str = "取消全选"; ; str = "全选")
        {
          localButton.setText(str);
          BatchDownloadConfirmView.this.dispatchActionEvent("selectAll", Boolean.valueOf(BatchDownloadConfirmView.this.mAllSelected));
          return;
          bool = false;
          break;
        }
      }
    });
    this.mConfirmButton = ((Button)localLayoutInflater.inflate(2130903057, null));
    this.mConfirmButton.setEnabled(false);
    this.mConfirmButton.setText("下载");
    addView(this.mConfirmButton);
    this.mConfirmButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        BatchDownloadConfirmView.this.dispatchActionEvent("startDownload", null);
      }
    });
    this.mTip = new TextView(paramContext);
    this.mTip.setTextColor(SkinManager.getTextColorSecondLevel());
    this.mTip.setGravity(17);
    addView(this.mTip);
    this.mTip.setVisibility(8);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((paramMotionEvent.getAction() == 0) && (paramMotionEvent.getY() < this.tipLayout.height))
      return false;
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("offset"))
      return Integer.valueOf(this.tipLayout.height);
    return super.getValue(paramString, paramObject);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.leftButtonLayout.layoutView(this.mSelectButton);
    this.rightButtonLayout.layoutView(this.mConfirmButton);
    this.tipLayout.layoutView(this.mTip);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.leftButtonLayout.scaleToBounds(this.standardLayout);
    this.rightButtonLayout.scaleToBounds(this.standardLayout);
    this.tipLayout.scaleToBounds(this.standardLayout);
    this.leftButtonLayout.measureView(this.mSelectButton);
    this.rightButtonLayout.measureView(this.mConfirmButton);
    this.tipLayout.measureView(this.mTip);
    this.mSelectButton.setTextSize(0, SkinManager.getInstance().getSubTextSize());
    this.mConfirmButton.setTextSize(0, SkinManager.getInstance().getSubTextSize());
    this.mTip.setTextSize(0, SkinManager.getInstance().getRecommendTextSize());
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("stateChanged"))
    {
      if (paramObject != null)
        break label38;
      this.mEnabled = false;
      this.mTip.setVisibility(4);
    }
    while (true)
    {
      this.mConfirmButton.setEnabled(this.mEnabled);
      return;
      label38: SizeInfo localSizeInfo = (SizeInfo)paramObject;
      if ((localSizeInfo.mCnt > 0) && (localSizeInfo.mSizeString != null) && (!localSizeInfo.mSizeString.equalsIgnoreCase("")))
      {
        Locale localLocale = Locale.CHINESE;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(localSizeInfo.mCnt);
        arrayOfObject[1] = localSizeInfo.mSizeString;
        String str = String.format(localLocale, "已选择%d个文件，约%s", arrayOfObject);
        this.mEnabled = true;
        this.mTip.setText(str);
        this.mTip.setVisibility(0);
      }
      else
      {
        this.mEnabled = false;
        this.mTip.setVisibility(8);
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.scheduleview.BatchDownloadConfirmView
 * JD-Core Version:    0.6.2
 */