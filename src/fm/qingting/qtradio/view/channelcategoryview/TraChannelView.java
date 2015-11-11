package fm.qingting.qtradio.view.channelcategoryview;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ListView;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PlayingProgramInfoNode;
import fm.qingting.qtradio.model.RadioChannelNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.LoadingView;
import fm.qingting.qtradio.view.MiniPlayerView;
import fm.qingting.utils.ListUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TraChannelView extends ViewGroupViewImpl
  implements InfoManager.ISubscribeEventListener
{
  private CustomizedAdapter adapter;
  private IAdapterIViewFactory factory;
  private ListView mListView;
  private LoadingView mLoadingView;
  private MiniPlayerView mMiniView;
  private final ViewLayout miniLayout = this.standardLayout.createChildLT(720, 110, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public TraChannelView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor());
    this.factory = new IAdapterIViewFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        return new TraChannelItemView(TraChannelView.this.getContext(), this.val$hash);
      }
    };
    this.adapter = new CustomizedAdapter(new ArrayList(), this.factory);
    this.mListView = new ListView(paramContext);
    this.mListView.setVerticalScrollBarEnabled(false);
    this.mListView.setVerticalFadingEdgeEnabled(false);
    this.mListView.setCacheColorHint(0);
    this.mListView.setDivider(null);
    this.mLoadingView = new LoadingView(paramContext);
    addView(this.mLoadingView);
    this.mListView.setEmptyView(this.mLoadingView);
    this.mListView.setAdapter(this.adapter);
    addView(this.mListView);
    this.mMiniView = new MiniPlayerView(paramContext);
    addView(this.mMiniView);
  }

  private void invalidateCurrentPlayingProgram()
  {
    int i = this.mListView.getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = this.mListView.getChildAt(j);
      if ((localView != null) && ((localView instanceof IView)))
        ((IView)localView).update("ip", null);
    }
  }

  private void loadPlayingProgram(List<Node> paramList)
  {
    if (paramList == null)
      return;
    Object localObject1 = "";
    Iterator localIterator = paramList.iterator();
    label15: Node localNode;
    int i;
    if (localIterator.hasNext())
    {
      localNode = (Node)localIterator.next();
      if (localNode.nodeName.equalsIgnoreCase("channel"))
        i = ((ChannelNode)localNode).channelId;
    }
    while (true)
    {
      label58: Object localObject2;
      if (!InfoManager.getInstance().root().mPlayingProgramInfo.isExist(i))
        if (((String)localObject1).equalsIgnoreCase(""))
          localObject2 = (String)localObject1 + i;
      while (true)
      {
        localObject1 = localObject2;
        break label15;
        if (!localNode.nodeName.equalsIgnoreCase("radiochannel"))
          break label190;
        i = ((RadioChannelNode)localNode).channelId;
        break label58;
        localObject2 = (String)localObject1 + "," + i;
        continue;
        if (((String)localObject1).equalsIgnoreCase(""))
          break;
        InfoManager.getInstance().loadCurrentPlayingPrograms((String)localObject1, this);
        return;
        localObject2 = localObject1;
      }
      label190: i = 0;
    }
  }

  public void close(boolean paramBoolean)
  {
    this.mMiniView.destroy();
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mListView.layout(0, 0, this.standardLayout.width, this.standardLayout.height - this.miniLayout.height);
    this.mLoadingView.layout(0, 0, this.standardLayout.width, this.standardLayout.height - this.miniLayout.height);
    this.mMiniView.layout(0, this.standardLayout.height - this.miniLayout.height, this.standardLayout.width, this.standardLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.mListView.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.miniLayout.height, 1073741824));
    this.mLoadingView.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.miniLayout.height, 1073741824));
    this.miniLayout.scaleToBounds(this.standardLayout);
    this.miniLayout.measureView(this.mMiniView);
    setMeasuredDimension(i, j);
  }

  public void onNotification(String paramString)
  {
    if (paramString.equalsIgnoreCase("RCPPL"))
      invalidateCurrentPlayingProgram();
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      localList = (List)paramObject;
      this.adapter.setData(ListUtils.convertToObjectList(localList));
      loadPlayingProgram(localList);
    }
    while (!paramString.equalsIgnoreCase("refresh"))
    {
      List localList;
      return;
    }
    this.adapter.notifyDataSetChanged();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.channelcategoryview.TraChannelView
 * JD-Core Version:    0.6.2
 */