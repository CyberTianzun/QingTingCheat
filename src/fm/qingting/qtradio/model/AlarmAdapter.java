package fm.qingting.qtradio.model;

import android.view.View;
import android.view.ViewGroup;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.view.IView;
import java.util.List;

public class AlarmAdapter extends CustomizedAdapter
{
  private boolean inManageMode = false;

  public AlarmAdapter(List<Object> paramList, IAdapterIViewFactory paramIAdapterIViewFactory)
  {
    super(paramList, paramIAdapterIViewFactory);
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    IView localIView = (IView)super.getView(paramInt, paramView, paramViewGroup).getTag();
    if (getCount() == 1)
    {
      localIView.update("all", null);
      if (!this.inManageMode)
        break label111;
      localIView.update("showManage", null);
    }
    while (true)
    {
      return localIView.getView();
      if (paramInt == 0)
      {
        localIView.update("first", null);
        break;
      }
      if (paramInt == -1 + getCount())
      {
        localIView.update("last", null);
        break;
      }
      localIView.update("normal", null);
      break;
      label111: localIView.update("hideManage", null);
    }
  }

  public void hideManage()
  {
    if (!this.inManageMode)
      return;
    this.inManageMode = false;
    notifyDataSetChanged();
  }

  public void showManage()
  {
    if (this.inManageMode)
      return;
    this.inManageMode = true;
    notifyDataSetChanged();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.AlarmAdapter
 * JD-Core Version:    0.6.2
 */