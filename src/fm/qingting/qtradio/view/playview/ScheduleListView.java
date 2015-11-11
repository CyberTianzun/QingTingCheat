package fm.qingting.qtradio.view.playview;

import android.content.Context;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ListViewImpl;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.utils.ListUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScheduleListView extends ListViewImpl
{
  private CustomizedAdapter mAdapter = new CustomizedAdapter(new ArrayList(), this.mFactory);
  private IAdapterIViewFactory mFactory = new IAdapterIViewFactory()
  {
    public IView createView(int paramAnonymousInt)
    {
      return new PlayListItemView(ScheduleListView.this.getContext(), this.val$hash);
    }
  };

  public ScheduleListView(Context paramContext)
  {
    super(paramContext);
    setDivider(null);
    setAdapter(this.mAdapter);
  }

  public void update(String paramString, Object paramObject)
  {
    List localList;
    int j;
    int i;
    if (paramString.equalsIgnoreCase("setData"))
    {
      localList = (List)paramObject;
      Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("program")))
        break label137;
      Iterator localIterator = localList.iterator();
      j = 0;
      if (!localIterator.hasNext())
        break label131;
      if (((ProgramNode)localIterator.next()).uniqueId != ((ProgramNode)localNode).uniqueId)
        break label119;
      i = 1;
    }
    while (true)
    {
      this.mAdapter.setData(ListUtils.convertToObjectList(localList));
      if ((i != 0) && (j > 0))
      {
        setSelection(j);
        return;
        label119: j++;
        break;
      }
      setSelection(0);
      return;
      label131: i = 0;
      continue;
      label137: i = 0;
      j = 0;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview.ScheduleListView
 * JD-Core Version:    0.6.2
 */