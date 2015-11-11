package fm.qingting.qtradio.view.popviews;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.ListView;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.PlayHistoryInfoNode;
import fm.qingting.qtradio.model.RootNode;
import java.util.ArrayList;
import java.util.List;

public class TopHistoryPopView extends ViewGroupViewImpl
{
  private CustomizedAdapter adapter = new CustomizedAdapter(new ArrayList(), this.factory);
  private IAdapterIViewFactory factory = new IAdapterIViewFactory()
  {
    public IView createView(int paramAnonymousInt)
    {
      return new TopHistoryItemView(TopHistoryPopView.this.getContext());
    }
  };
  private final ViewLayout listLayout = this.standardLayout.createChildLT(480, 300, 0, 79, ViewLayout.SCALE_FLAG_SLTCW);
  private ListView mListView;
  private int mSchedulePopViewBottom = 0;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);

  public TopHistoryPopView(Context paramContext)
  {
    super(paramContext);
    this.mListView = new ListView(paramContext);
    this.mListView.setBackgroundColor(-538889168);
    this.mListView.setVerticalScrollBarEnabled(false);
    this.mListView.setVerticalFadingEdgeEnabled(false);
    this.mListView.setCacheColorHint(0);
    this.mListView.setDivider(null);
    this.mListView.setAdapter(this.adapter);
    addView(this.mListView);
    ArrayList localArrayList = new ArrayList();
    List localList = InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.getPlayHistoryNodes();
    int i;
    if (localList.size() >= 3)
      i = 2;
    while (i >= 1)
    {
      localArrayList.add(localList.get(i));
      i--;
      continue;
      i = -1 + localList.size();
    }
    this.adapter.setData(localArrayList);
    setBackgroundColor(0);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
    {
      int i = this.mSchedulePopViewBottom - this.mListView.getMeasuredHeight();
      if (paramMotionEvent.getY() < i)
      {
        dispatchActionEvent("cancelPop", null);
        EventDispacthManager.getInstance().dispatchAction("hideMiniplayerTrangle", null);
        return super.dispatchTouchEvent(paramMotionEvent);
      }
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mSchedulePopViewBottom = paramInt4;
    try
    {
      this.mListView.layout(this.listLayout.leftMargin, 3 + (this.mSchedulePopViewBottom - this.mListView.getMeasuredHeight()), this.standardLayout.width - this.listLayout.leftMargin, 3 + this.mSchedulePopViewBottom);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.listLayout.scaleToBounds(this.standardLayout);
    this.mListView.measure(this.listLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(2 * this.standardLayout.height / 3, -2147483648));
    setMeasuredDimension(i, j);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.TopHistoryPopView
 * JD-Core Version:    0.6.2
 */