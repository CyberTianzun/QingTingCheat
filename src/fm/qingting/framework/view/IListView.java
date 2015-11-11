package fm.qingting.framework.view;

import android.widget.ListAdapter;

public abstract interface IListView extends IView
{
  public abstract ListAdapter getAdapter();

  public abstract void setAdapter(ListAdapter paramListAdapter);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.IListView
 * JD-Core Version:    0.6.2
 */