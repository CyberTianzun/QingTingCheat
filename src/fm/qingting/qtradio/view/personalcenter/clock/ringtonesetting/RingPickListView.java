package fm.qingting.qtradio.view.personalcenter.clock.ringtonesetting;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.ListView;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.adapter.ItemParam;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.PlayHistoryInfoNode;
import fm.qingting.qtradio.model.PlayHistoryNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.SingleCheckAdapter;
import fm.qingting.qtradio.view.personalcenter.clock.AlarmTagView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RingPickListView extends ViewGroupViewImpl
  implements IEventHandler
{
  private final int MAX_HISTORY = 5;
  private SingleCheckAdapter adapter;
  private int channelId = 0;
  private Node extraNode;
  private IAdapterIViewFactory factory;
  private ListView mListView;
  private AlarmTagView mTagView;
  private List<Object> names;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 800, 480, 800, 0, 0, ViewLayout.FILL);
  private final ViewLayout titleLayout = this.standardLayout.createChildLT(480, 50, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public RingPickListView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor());
    this.factory = new IAdapterIViewFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        return new RingPickItemView(RingPickListView.this.getContext(), this.val$hash);
      }
    };
    this.adapter = new SingleCheckAdapter(new ArrayList(), this.factory);
    this.adapter.setEventHandler(this);
    this.mListView = new ListView(paramContext);
    this.mListView.setVerticalScrollBarEnabled(false);
    this.mListView.setVerticalFadingEdgeEnabled(false);
    this.mListView.setCacheColorHint(0);
    this.mListView.setDivider(null);
    this.mListView.setAdapter(this.adapter);
    addView(this.mListView);
    this.mTagView = new AlarmTagView(paramContext);
    this.mTagView.setTagName("您可以选择从收藏电台添加");
    addView(this.mTagView);
  }

  private void addDefaultChannel()
  {
    int m;
    if ((this.extraNode != null) && (this.extraNode.nodeName.equalsIgnoreCase("channel")))
    {
      m = ((ChannelNode)this.extraNode).channelId;
      if (!InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(m))
        this.names.add(0, this.extraNode);
    }
    for (int i = m; ; i = 0)
    {
      ChannelNode localChannelNode1 = new ChannelNode();
      localChannelNode1.channelId = 386;
      localChannelNode1.title = "CNR中国之声";
      localChannelNode1.categoryId = 54;
      if ((i != 386) && (!InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(localChannelNode1)))
        this.names.add(localChannelNode1);
      ChannelNode localChannelNode2 = new ChannelNode();
      localChannelNode2.channelId = 1006;
      localChannelNode2.title = "CRI轻松调频";
      localChannelNode2.categoryId = 54;
      if ((i != 1006) && (!InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(localChannelNode2)))
        this.names.add(localChannelNode2);
      ChannelNode localChannelNode3 = new ChannelNode();
      localChannelNode3.channelId = 4935;
      localChannelNode3.title = "iRadio音乐台iPlay";
      localChannelNode3.categoryId = 60;
      if ((i != 4935) && (!InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(localChannelNode3)))
        this.names.add(localChannelNode3);
      if ((InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.getPlayHistoryNodes() == null) || (InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.getPlayHistoryNodes().size() == 0))
        return;
      Object localObject1 = ",";
      Iterator localIterator = InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.getPlayHistoryNodes().iterator();
      int j = 0;
      label340: int k;
      Object localObject2;
      while ((localIterator.hasNext()) && (j < 5))
      {
        PlayHistoryNode localPlayHistoryNode = (PlayHistoryNode)localIterator.next();
        if ((localPlayHistoryNode.playNode == null) || (!localPlayHistoryNode.playNode.nodeName.equalsIgnoreCase("program")))
          break label619;
        ChannelNode localChannelNode4 = new ChannelNode();
        if (localPlayHistoryNode.channelName != null)
        {
          localChannelNode4.channelId = localPlayHistoryNode.channelId;
          localChannelNode4.categoryId = localPlayHistoryNode.categoryId;
          localChannelNode4.title = localPlayHistoryNode.channelName;
          if (localPlayHistoryNode.playNode != null)
            localChannelNode4.channelType = ((ProgramNode)localPlayHistoryNode.playNode).channelType;
          if ((localPlayHistoryNode.channelId == i) || (localPlayHistoryNode.channelId != 386) || (localPlayHistoryNode.channelId == 1006) || (localPlayHistoryNode.channelId == 4935) || (InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.isExisted(localChannelNode4)))
            break label619;
          String str = "," + localPlayHistoryNode.channelId + ",";
          if (((String)localObject1).contains(str))
            break label619;
          this.names.add(localChannelNode4);
          k = j + 1;
          localObject2 = (String)localObject1 + str;
        }
      }
      while (true)
      {
        localObject1 = localObject2;
        j = k;
        break label340;
        break;
        label619: localObject2 = localObject1;
        k = j;
      }
    }
  }

  private void findSelected()
  {
    int i = 0;
    if (i < this.names.size())
      if (this.channelId != ((ChannelNode)this.names.get(i)).channelId);
    while (true)
    {
      this.adapter.setData(this.names);
      if (i > -1)
        this.adapter.checkIndex(i);
      return;
      i++;
      break;
      i = -1;
    }
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("itemCallback"))
    {
      ItemParam localItemParam = (ItemParam)paramObject2;
      if (localItemParam.type.equalsIgnoreCase("itemClick"))
      {
        int i = localItemParam.position;
        this.adapter.checkIndex(i);
        dispatchActionEvent("select", localItemParam.param);
      }
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mTagView.layout(0, 0, this.standardLayout.width, this.mTagView.getMeasuredHeight());
    this.mListView.layout(0, this.mTagView.getMeasuredHeight(), this.standardLayout.width, this.standardLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.titleLayout.scaleToBounds(this.standardLayout);
    this.titleLayout.measureView(this.mTagView);
    this.mListView.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.mTagView.getMeasuredHeight(), 1073741824));
    setMeasuredDimension(i, j);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.names = new ArrayList((List)paramObject);
      addDefaultChannel();
      findSelected();
    }
    do
    {
      return;
      if (paramString.equalsIgnoreCase("setRingtone"))
      {
        this.channelId = ((Integer)paramObject).intValue();
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("setRingChannel"));
    this.extraNode = ((Node)paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.clock.ringtonesetting.RingPickListView
 * JD-Core Version:    0.6.2
 */