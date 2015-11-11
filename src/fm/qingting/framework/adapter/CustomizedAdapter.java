package fm.qingting.framework.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.view.IView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CustomizedAdapter extends BaseAdapter
{
  public static final String ITEM_CALLBACK = "itemCallback";
  public static final String SET_DATA_TYPE = "content";
  protected ILayoutParamsBuilder builder;
  protected List<Object> data;
  protected IEventHandler eventHandler;
  protected IAdapterIViewFactory factory;
  protected String idKey;

  public CustomizedAdapter(List<Object> paramList, IAdapterIViewFactory paramIAdapterIViewFactory)
  {
    this.data = paramList;
    this.factory = paramIAdapterIViewFactory;
  }

  public static List<Map<String, Object>> object2Map(List<? extends Object> paramList, String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramList == null);
    while (true)
    {
      return localArrayList;
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put(paramString, localIterator.next());
        localArrayList.add(localHashMap);
      }
    }
  }

  public void addItem(Object paramObject)
  {
    if (this.data != null)
      this.data.add(paramObject);
  }

  public void addItems(List<Object> paramList)
  {
    if (this.data != null);
    for (int i = 0; ; i++)
    {
      if (i >= paramList.size())
        return;
      addItem(paramList.get(i));
    }
  }

  public void build(List<Object> paramList, IAdapterIViewFactory paramIAdapterIViewFactory)
  {
    this.factory = paramIAdapterIViewFactory;
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

  public List<Object> getData()
  {
    return this.data;
  }

  public Object getItem(int paramInt)
  {
    if (this.data == null);
    while (this.data.size() <= paramInt)
      return null;
    return this.data.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
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
        localIView = this.factory.createView(paramInt);
      paramView = localIView.getView();
      paramView.setTag(localIView);
    }
    while (true)
    {
      localIView.setEventHandler(null);
      Object localObject = getItem(paramInt);
      localIView.setEventHandler(new ViewEventHandler(paramInt));
      if (localObject != null)
        localIView.update("content", localObject);
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

  public void setData(List<Object> paramList)
  {
    this.data = paramList;
    notifyDataSetChanged();
  }

  public void setEventHandler(IEventHandler paramIEventHandler)
  {
    this.eventHandler = paramIEventHandler;
  }

  public void setFactory(IAdapterIViewFactory paramIAdapterIViewFactory)
  {
    this.factory = paramIAdapterIViewFactory;
  }

  public void setIDKey(String paramString)
  {
    this.idKey = paramString;
  }

  public void setLayoutParamsBuilder(ILayoutParamsBuilder paramILayoutParamsBuilder)
  {
    this.builder = paramILayoutParamsBuilder;
  }

  public void updateValue(int paramInt, String paramString, Object paramObject)
  {
    updateValue(paramInt, paramString, paramObject, true);
  }

  public void updateValue(int paramInt, String paramString, Object paramObject, boolean paramBoolean)
  {
    if (paramInt >= this.data.size());
    do
    {
      return;
      this.data.set(paramInt, paramObject);
    }
    while (!paramBoolean);
    notifyDataSetChanged();
  }

  protected class ViewEventHandler
    implements IEventHandler
  {
    int _position;

    public ViewEventHandler(int arg2)
    {
      int i;
      this._position = i;
    }

    public void onEvent(Object paramObject1, String paramString, Object paramObject2)
    {
      if (CustomizedAdapter.this.eventHandler == null)
        return;
      CustomizedAdapter.this.eventHandler.onEvent(this, "itemCallback", new ItemParam(paramString, this._position, paramObject1, paramObject2));
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.adapter.CustomizedAdapter
 * JD-Core Version:    0.6.2
 */