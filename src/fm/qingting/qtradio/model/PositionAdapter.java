package fm.qingting.qtradio.model;

import android.view.View;
import android.view.ViewGroup;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.view.IView;
import java.util.List;

public class PositionAdapter extends CustomizedAdapter
{
  private int mCheckPosition = -1;

  public PositionAdapter(List<Object> paramList, IAdapterIViewFactory paramIAdapterIViewFactory)
  {
    super(paramList, paramIAdapterIViewFactory);
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    IView localIView = (IView)super.getView(paramInt, paramView, paramViewGroup).getTag();
    localIView.update("checkIndex", Integer.valueOf(this.mCheckPosition));
    localIView.update("position", Integer.valueOf(paramInt));
    return localIView.getView();
  }

  public void setCheckIndex(int paramInt)
  {
    this.mCheckPosition = paramInt;
    notifyDataSetChanged();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.PositionAdapter
 * JD-Core Version:    0.6.2
 */