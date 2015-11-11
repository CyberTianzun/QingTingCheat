package fm.qingting.qtradio.view.personalcenter.clock.djringtone;

import android.view.View;
import android.view.ViewGroup;
import fm.qingting.framework.adapter.CustomizedAdapter;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.view.IView;
import java.util.List;

public class DjRingtoneAdapter extends CustomizedAdapter
{
  private int checkList = -1;
  private int playingIndex = -1;

  public DjRingtoneAdapter(List<Object> paramList, IAdapterIViewFactory paramIAdapterIViewFactory)
  {
    super(paramList, paramIAdapterIViewFactory);
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    IView localIView = (IView)super.getView(paramInt, paramView, paramViewGroup).getTag();
    if (this.checkList == paramInt)
    {
      localIView.update("check", null);
      if (this.playingIndex != paramInt)
        break label79;
    }
    label79: for (boolean bool = true; ; bool = false)
    {
      localIView.update("setPlay", Boolean.valueOf(bool));
      return localIView.getView();
      localIView.update("unCheck", null);
      break;
    }
  }

  public void initCheck(int paramInt)
  {
    this.checkList = paramInt;
  }

  public void setCheck(int paramInt)
  {
    this.checkList = paramInt;
    notifyDataSetChanged();
  }

  public void setPlayingIndex(int paramInt)
  {
    this.playingIndex = paramInt;
    notifyDataSetChanged();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.clock.djringtone.DjRingtoneAdapter
 * JD-Core Version:    0.6.2
 */