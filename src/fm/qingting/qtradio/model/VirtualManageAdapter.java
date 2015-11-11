package fm.qingting.qtradio.model;

import android.view.View;
import android.view.ViewGroup;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.view.IView;
import java.util.List;

public class VirtualManageAdapter extends CustomizedAdapter
{
  private int mConfirmIndex = -1;
  private int mConfirmOffset = 0;
  private int mDeleteOffset = 0;
  private boolean mManage = false;

  public VirtualManageAdapter(List<Object> paramList, IAdapterIViewFactory paramIAdapterIViewFactory)
  {
    super(paramList, paramIAdapterIViewFactory);
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    IView localIView = (IView)super.getView(paramInt, paramView, paramViewGroup).getTag();
    if (this.mManage)
      if (paramInt == this.mConfirmIndex)
        localIView.update("showConfirm", Integer.valueOf(this.mConfirmOffset));
    while (true)
    {
      return localIView.getView();
      if (this.mConfirmIndex > -1)
      {
        localIView.update("showManageWithoutShift", Integer.valueOf(this.mDeleteOffset));
      }
      else
      {
        localIView.update("showManage", Integer.valueOf(this.mDeleteOffset));
        continue;
        localIView.update("hideManage", null);
      }
    }
  }

  public void hideConfirm()
  {
    this.mConfirmIndex = -1;
    notifyDataSetChanged();
  }

  public void hideManage()
  {
    if (!this.mManage)
      return;
    this.mManage = false;
    this.mConfirmIndex = -1;
    notifyDataSetChanged();
  }

  public boolean isShowingConfirm()
  {
    return this.mConfirmIndex > -1;
  }

  public void showConfirm(int paramInt1, int paramInt2)
  {
    this.mConfirmIndex = paramInt1;
    this.mConfirmOffset = paramInt2;
    notifyDataSetChanged();
  }

  public void showManage(int paramInt)
  {
    if (this.mManage)
      return;
    this.mDeleteOffset = paramInt;
    this.mManage = true;
    notifyDataSetChanged();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.VirtualManageAdapter
 * JD-Core Version:    0.6.2
 */