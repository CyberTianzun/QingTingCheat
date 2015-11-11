package fm.qingting.qtradio.view.chatroom.onlinemembersview;

import android.content.Context;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.View.MeasureSpec;
import android.widget.ListView;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.adapter.ItemParam;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.utils.ListUtils;
import java.util.ArrayList;
import java.util.List;

public class OnlineMembersListView extends ViewGroupViewImpl
  implements IEventHandler
{
  private CustomizedAdapter adapter;
  private IAdapterIViewFactory factory;
  private OnlineBubbleView mBubbleView;
  private ListView mListView;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public OnlineMembersListView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor());
    this.factory = new IAdapterIViewFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        return new MembersItemViewNew(OnlineMembersListView.this.getContext(), this.val$hash);
      }
    };
    this.adapter = new CustomizedAdapter(new ArrayList(), this.factory);
    this.adapter.setEventHandler(this);
    this.mListView = new ListView(paramContext);
    this.mListView.setVerticalFadingEdgeEnabled(false);
    this.mListView.setCacheColorHint(0);
    this.mListView.setDivider(null);
    this.mListView.setHeaderDividersEnabled(false);
    this.mListView.setSelector(17170445);
    this.mListView.setAdapter(this.adapter);
    addView(this.mListView);
    this.mBubbleView = new OnlineBubbleView(paramContext);
    addView(this.mBubbleView);
    this.mBubbleView.setVisibility(4);
    this.mBubbleView.setEventHandler(this);
  }

  private void initData(List<UserInfo> paramList)
  {
    this.adapter.setData(ListUtils.convertToObjectList(paramList));
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if ((paramKeyEvent.getAction() == 1) && (this.mBubbleView.getVisibility() == 0))
    {
      this.mBubbleView.setVisibility(4);
      return true;
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("itemCallback"))
    {
      ItemParam localItemParam = (ItemParam)paramObject2;
      Point localPoint = (Point)localItemParam.param;
      int i = localItemParam.position;
      if (this.adapter.getData() != null)
      {
        UserInfo localUserInfo = (UserInfo)this.adapter.getData().get(i);
        this.mBubbleView.setChatParam(localUserInfo, localPoint);
        this.mBubbleView.setVisibility(0);
      }
    }
    do
    {
      return;
      if (paramString.equalsIgnoreCase("select"))
      {
        dispatchActionEvent(paramString, paramObject2);
        return;
      }
      if (paramString.equalsIgnoreCase("cancelPop"))
      {
        this.mBubbleView.setVisibility(4);
        return;
      }
      if (paramString.equalsIgnoreCase("talkWithIt"))
      {
        dispatchActionEvent(paramString, paramObject2);
        this.mBubbleView.setVisibility(4);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("lookItsInfo"));
    dispatchActionEvent(paramString, paramObject2);
    this.mBubbleView.setVisibility(4);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.standardLayout.layoutView(this.mListView);
    this.standardLayout.layoutView(this.mBubbleView);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.standardLayout.measureView(this.mListView);
    this.standardLayout.measureView(this.mBubbleView);
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      initData((List)paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.chatroom.onlinemembersview.OnlineMembersListView
 * JD-Core Version:    0.6.2
 */