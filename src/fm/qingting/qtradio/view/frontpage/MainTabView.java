package fm.qingting.qtradio.view.frontpage;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.manager.EducationManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.wo.WoApiRequest;
import fm.qingting.utils.QTMSGManage;

public class MainTabView extends QtView
  implements ViewElement.OnElementClickListener
{
  public static final int TAB_DISCOVERY = 1;
  public static final int TAB_DOWNLOAD = 2;
  private static final String[] TAB_NAMES = { "我的", "发现", "下载", "搜索" };
  public static final int TAB_PERSONAL = 0;
  private static final int[][] TAB_RESOURCES = { { 2130837987, 2130837988 }, { 2130837981, 2130837982 }, { 2130837983, 2130837984 }, { 2130837989, 2130837990 } };
  public static final int TAB_SEARCH = 3;
  private final ViewLayout itemLayout = this.standardLayout.createChildLT(150, 114, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 114, 720, 114, 0, 0, ViewLayout.FILL);

  public MainTabView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getNaviBgColor());
    int j = hashCode();
    while (i < TAB_NAMES.length)
    {
      ItemElement localItemElement = new ItemElement(paramContext);
      localItemElement.setOnElementClickListener(this);
      localItemElement.setAction(TAB_NAMES[i], TAB_RESOURCES[i]);
      addElement(localItemElement, j);
      if (i == 1)
        localItemElement.setSelected(true);
      i++;
    }
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    int i = -1 + getChildCount();
    int j = 0;
    ViewElement localViewElement;
    if (i >= 0)
    {
      localViewElement = getChildAt(i);
      if (paramViewElement == localViewElement)
        if (i == 3)
        {
          QTMSGManage.getInstance().sendStatistcsMessage("search_fromsearchframe");
          QTMSGManage.getInstance().sendStatistcsMessage("newnavi_maintabclick", "SEARCH");
          ControllerManager.getInstance().redirectToSearchView(true);
          if (EducationManager.getInstance().isShown())
            EducationManager.getInstance().cancelTip();
        }
    }
    do
    {
      return;
      localViewElement.setSelected(true);
      j = i;
      while (true)
      {
        i--;
        break;
        localViewElement.setSelected(false);
      }
      dispatchActionEvent("selectTab", Integer.valueOf(j));
    }
    while (j != 0);
    WoApiRequest.sendEventMessage("unicomClickMy");
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.itemLayout.scaleToBounds(this.standardLayout);
    int i = getChildCount();
    int j = (this.standardLayout.width - i * this.itemLayout.width) / (i + 1);
    int k = 0;
    int m = j;
    while (k < i)
    {
      getChildAt(k).measure(m, this.itemLayout.topMargin, m + this.itemLayout.width, this.itemLayout.getBottom());
      m += j + this.itemLayout.width;
      k++;
    }
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.MainTabView
 * JD-Core Version:    0.6.2
 */