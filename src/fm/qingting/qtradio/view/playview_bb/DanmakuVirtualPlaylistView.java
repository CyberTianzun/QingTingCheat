package fm.qingting.qtradio.view.playview_bb;

import android.content.Context;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.view.IView;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.LoadMoreListView;
import fm.qingting.utils.ListUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DanmakuVirtualPlaylistView extends LoadMoreListView
{
  private CustomizedAdapter mAdapter = new CustomizedAdapter(new ArrayList(), this.mFactory);
  private IAdapterIViewFactory mFactory = new IAdapterIViewFactory()
  {
    public IView createView(int paramAnonymousInt)
    {
      return new DanmakuVirtualPlaylistItemView(DanmakuVirtualPlaylistView.this.getContext(), this.val$hash);
    }
  };

  public DanmakuVirtualPlaylistView(Context paramContext)
  {
    super(paramContext);
    setDivider(null);
    setAdapter(this.mAdapter);
  }

  public void update(String paramString, Object paramObject)
  {
    List localList2;
    int j;
    label62: int i;
    if (paramString.equalsIgnoreCase("setData"))
    {
      localList2 = (List)paramObject;
      if (localList2 == null)
        return;
      Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("program")))
        break label176;
      Iterator localIterator = localList2.iterator();
      j = 0;
      if (!localIterator.hasNext())
        break label170;
      if (((ProgramNode)localIterator.next()).uniqueId == ((ProgramNode)localNode).uniqueId)
        i = 1;
    }
    while (true)
    {
      this.mAdapter.setData(ListUtils.convertToObjectList(localList2));
      if ((i != 0) && (j > 0))
      {
        setSelection(j);
        return;
        j++;
        break label62;
      }
      setSelection(0);
      return;
      if (!paramString.equalsIgnoreCase("addmore"))
        break;
      List localList1 = (List)paramObject;
      if (localList1 == null)
        break;
      this.mAdapter.setData(ListUtils.convertToObjectList(localList1));
      return;
      label170: i = 0;
      continue;
      label176: i = 0;
      j = 0;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview_bb.DanmakuVirtualPlaylistView
 * JD-Core Version:    0.6.2
 */