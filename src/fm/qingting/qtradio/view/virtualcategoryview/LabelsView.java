package fm.qingting.qtradio.view.virtualcategoryview;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.Attribute;
import java.util.ArrayList;
import java.util.List;

class LabelsView extends QtView
{
  private final ViewLayout buttonLayout = this.itemLayout.createChildLT(154, 68, 10, 10, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(170, 88, 170, 88, 0, 0, ViewLayout.SLT | ViewLayout.LT | ViewLayout.CH);
  private List<ButtonViewElement> mChildren;
  private int mHash;

  public LabelsView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getCardColor());
    this.mHash = hashCode();
  }

  private void clear()
  {
    if (this.mChildren != null)
    {
      for (int i = -1 + this.mChildren.size(); i >= 0; i--)
        removeElement((ViewElement)this.mChildren.get(i));
      this.mChildren.clear();
    }
    dispatchActionEvent("clear", null);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = 0;
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    if ((this.mChildren == null) || (this.mChildren.size() == 0))
    {
      setMeasuredDimension(0, this.itemLayout.height);
      return;
    }
    this.buttonLayout.scaleToBounds(this.itemLayout);
    int j = this.mChildren.size();
    int k = 0;
    while (k < j)
    {
      int m = k * this.itemLayout.width + this.buttonLayout.leftMargin;
      ((ButtonViewElement)this.mChildren.get(k)).measure(m, this.buttonLayout.topMargin, m + this.buttonLayout.width, this.buttonLayout.getBottom());
      k++;
      i = m;
    }
    setMeasuredDimension(i + this.buttonLayout.getRight(), this.itemLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("resetFilter"))
    {
      localList = (List)paramObject;
      if (this.mChildren != null)
      {
        for (j = -1 + this.mChildren.size(); j >= 0; j--)
          removeElement((ViewElement)this.mChildren.get(j));
        this.mChildren.clear();
      }
      if (localList != null)
      {
        if (this.mChildren == null)
          this.mChildren = new ArrayList();
        for (i = 0; i < localList.size(); i++)
        {
          localButtonViewElement = new ButtonViewElement(getContext());
          localButtonViewElement.setBackground(2130837536, 2130837536);
          localButtonViewElement.setTextColor(SkinManager.getTextColorNormal());
          addElement(localButtonViewElement, this.mHash);
          localButtonViewElement.setTextSize(SkinManager.getInstance().getSubTextSize());
          localButtonViewElement.setText(((Attribute)localList.get(i)).name);
          this.mChildren.add(localButtonViewElement);
        }
      }
      requestLayout();
    }
    while (!paramString.equalsIgnoreCase("clear"))
    {
      List localList;
      int j;
      int i;
      ButtonViewElement localButtonViewElement;
      return;
    }
    clear();
    requestLayout();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.virtualcategoryview.LabelsView
 * JD-Core Version:    0.6.2
 */