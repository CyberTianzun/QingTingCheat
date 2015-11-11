package fm.qingting.qtradio.model;

import android.view.View;
import android.view.ViewGroup;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.view.IView;
import java.util.List;

public class ChannelCategoryAdapter extends CustomizedAdapter
{
  private int fmIndex = -1;
  private boolean showHeadSet = false;

  public ChannelCategoryAdapter(List<Object> paramList, IAdapterIViewFactory paramIAdapterIViewFactory)
  {
    super(paramList, paramIAdapterIViewFactory);
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    IView localIView = (IView)super.getView(paramInt, paramView, paramViewGroup).getTag();
    if ((this.showHeadSet) && (paramInt == this.fmIndex))
    {
      localIView.update("refreshHeadSet", Boolean.valueOf(true));
      this.showHeadSet = false;
    }
    while (true)
    {
      return localIView.getView();
      localIView.update("refreshHeadSet", Boolean.valueOf(false));
    }
  }

  public void showHeadSet(int paramInt)
  {
    this.showHeadSet = true;
    this.fmIndex = paramInt;
    notifyDataSetChanged();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ChannelCategoryAdapter
 * JD-Core Version:    0.6.2
 */