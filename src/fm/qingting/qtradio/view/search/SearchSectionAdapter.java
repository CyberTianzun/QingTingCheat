package fm.qingting.qtradio.view.search;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import fm.qingting.framework.adapter.IReusableCollection;
import fm.qingting.framework.view.IView;
import java.util.List;

class SearchSectionAdapter extends BaseAdapter
{
  static final String NEEDBOTTOMLINE = "nbl";
  protected List<SearchSectionItem> data;
  protected ISearchSectionFactory factory;

  public SearchSectionAdapter(List<SearchSectionItem> paramList, ISearchSectionFactory paramISearchSectionFactory)
  {
    this.data = paramList;
    this.factory = paramISearchSectionFactory;
  }

  private static boolean isContent(int paramInt)
  {
    return (paramInt >= 1) && (paramInt <= 2);
  }

  private boolean needBottomLine(int paramInt1, int paramInt2)
  {
    if (isContent(paramInt1))
    {
      SearchSectionItem localSearchSectionItem = getItem(paramInt2 + 1);
      if (localSearchSectionItem == null);
      while (localSearchSectionItem.type == 0)
        return false;
    }
    do
    {
      return true;
      if (paramInt1 == 5)
        break;
    }
    while ((paramInt1 != 0) || (getItem(paramInt2 + 1) != null));
    return false;
  }

  public void clear()
  {
    this.data.clear();
    notifyDataSetChanged();
  }

  public int getCount()
  {
    if (this.data == null)
      return 0;
    return this.data.size();
  }

  public List<SearchSectionItem> getData()
  {
    return this.data;
  }

  public SearchSectionItem getItem(int paramInt)
  {
    if (this.data == null);
    while (this.data.size() <= paramInt)
      return null;
    return (SearchSectionItem)this.data.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public int getItemViewType(int paramInt)
  {
    SearchSectionItem localSearchSectionItem = getItem(paramInt);
    if (localSearchSectionItem == null)
      return 0;
    return localSearchSectionItem.type;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    SearchSectionItem localSearchSectionItem = getItem(paramInt);
    IView localIView;
    if (paramView == null)
    {
      localIView = null;
      if (paramViewGroup != null)
      {
        boolean bool = paramViewGroup instanceof IReusableCollection;
        localIView = null;
        if (bool)
          localIView = (IView)((IReusableCollection)paramViewGroup).getReusableItem(null);
      }
      if (localIView == null)
        localIView = this.factory.createView(localSearchSectionItem.type);
      paramView = localIView.getView();
      paramView.setTag(localIView);
    }
    while (true)
    {
      if (localSearchSectionItem != null)
      {
        localIView.update("nbl", Boolean.valueOf(needBottomLine(localSearchSectionItem.type, paramInt)));
        localIView.update("content", localSearchSectionItem.data);
      }
      return paramView;
      localIView = (IView)paramView.getTag();
    }
  }

  public int getViewTypeCount()
  {
    return 7;
  }

  public void setData(List<SearchSectionItem> paramList)
  {
    this.data = paramList;
    notifyDataSetChanged();
  }

  public void setFactory(ISearchSectionFactory paramISearchSectionFactory)
  {
    this.factory = paramISearchSectionFactory;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.search.SearchSectionAdapter
 * JD-Core Version:    0.6.2
 */