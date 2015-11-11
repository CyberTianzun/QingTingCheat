package fm.qingting.qtradio.view.frontpage.discover;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import fm.qingting.framework.adapter.ILayoutParamsBuilder;
import fm.qingting.framework.adapter.IReusableCollection;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.view.IView;
import java.util.List;

class SectionAdapter extends BaseAdapter
{
  static final String NEEDBOTTOMLINE = "nbl";
  protected ILayoutParamsBuilder builder;
  protected List<SectionItem> data;
  protected IEventHandler eventHandler;
  protected ISectionFactory factory;
  protected String idKey;

  public SectionAdapter(List<SectionItem> paramList, ISectionFactory paramISectionFactory)
  {
    this.data = paramList;
    this.factory = paramISectionFactory;
  }

  private boolean needBottomLine(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 3)
    {
      localSectionItem = getItem(paramInt2 + 1);
      if (localSectionItem == null);
      while (localSectionItem.type == 0)
        return false;
    }
    while ((paramInt1 != 0) || (getItem(paramInt2 + 1) != null))
    {
      SectionItem localSectionItem;
      return true;
    }
    return false;
  }

  public void build(List<SectionItem> paramList, ISectionFactory paramISectionFactory)
  {
    this.factory = paramISectionFactory;
    setData(paramList);
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
    try
    {
      int i = this.data.size();
      return i;
    }
    catch (Exception localException)
    {
    }
    return 1;
  }

  public List<SectionItem> getData()
  {
    return this.data;
  }

  public SectionItem getItem(int paramInt)
  {
    if (this.data == null);
    while (this.data.size() <= paramInt)
      return null;
    return (SectionItem)this.data.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public int getItemViewType(int paramInt)
  {
    SectionItem localSectionItem = getItem(paramInt);
    if (localSectionItem == null)
      return 0;
    return localSectionItem.type;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    SectionItem localSectionItem = getItem(paramInt);
    if (paramView == null)
      if ((paramViewGroup == null) || (!(paramViewGroup instanceof IReusableCollection)))
        break label164;
    label164: for (IView localIView = (IView)((IReusableCollection)paramViewGroup).getReusableItem(null); ; localIView = null)
    {
      if (localIView == null)
        localIView = this.factory.createView(localSectionItem.type);
      paramView = localIView.getView();
      paramView.setTag(localIView);
      while (true)
      {
        localIView.setEventHandler(null);
        if (localSectionItem != null)
        {
          localIView.update("nbl", Boolean.valueOf(needBottomLine(localSectionItem.type, paramInt)));
          localIView.update("content", localSectionItem.data);
        }
        if (this.builder != null)
        {
          ViewGroup.LayoutParams localLayoutParams = this.builder.getLayoutParams();
          if (localLayoutParams != null)
            paramView.setLayoutParams(localLayoutParams);
        }
        return paramView;
        localIView = (IView)paramView.getTag();
      }
    }
  }

  public int getViewTypeCount()
  {
    return 8;
  }

  public void setData(List<SectionItem> paramList)
  {
    this.data = paramList;
    notifyDataSetChanged();
  }

  public void setEventHandler(IEventHandler paramIEventHandler)
  {
    this.eventHandler = paramIEventHandler;
  }

  public void setFactory(ISectionFactory paramISectionFactory)
  {
    this.factory = paramISectionFactory;
  }

  public void setIDKey(String paramString)
  {
    this.idKey = paramString;
  }

  public void setLayoutParamsBuilder(ILayoutParamsBuilder paramILayoutParamsBuilder)
  {
    this.builder = paramILayoutParamsBuilder;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.discover.SectionAdapter
 * JD-Core Version:    0.6.2
 */