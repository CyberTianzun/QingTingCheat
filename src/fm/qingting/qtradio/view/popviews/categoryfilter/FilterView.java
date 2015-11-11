package fm.qingting.qtradio.view.popviews.categoryfilter;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.Attribute;
import java.util.List;

class FilterView extends QtView
{
  static final String GET_ATTRIBUTE = "ga";
  private static final int ROWCNT = 4;
  private final ViewLayout boundLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 200, 720, 200, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout buttonLayout = this.itemLayout.createChildLT(154, 68, 13, 18, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = this.boundLayout.createChildLT(180, 104, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private List<Attribute> mAttributes;
  private List<Attribute> mInitAttributes;

  public FilterView(Context paramContext)
  {
    super(paramContext);
  }

  private void addNewItem(String paramString, boolean paramBoolean)
  {
    ButtonViewElement localButtonViewElement = new ButtonViewElement(getContext());
    localButtonViewElement.setBackground(2130837537, 2130837536, 0, 2130837536);
    if (paramBoolean)
      localButtonViewElement.setSelected(true);
    localButtonViewElement.setText(paramString);
    localButtonViewElement.setTextColor(-1, SkinManager.getTextColorRecommend(), 0, SkinManager.getTextColorHighlight());
    localButtonViewElement.setOnElementClickListener(new ViewElement.OnElementClickListener()
    {
      public void onElementClick(ViewElement paramAnonymousViewElement)
      {
        if (paramAnonymousViewElement.isSelected())
          return;
        for (int i = 0; ; i++)
          if (i < FilterView.this.getChildCount())
          {
            ViewElement localViewElement = FilterView.this.getChildAt(i);
            if (localViewElement.isSelected())
              localViewElement.setSelected(false);
          }
          else
          {
            paramAnonymousViewElement.setSelected(true);
            FilterView.this.invalidate();
            return;
          }
      }
    });
    addElement(localButtonViewElement);
  }

  private boolean initSelected(Attribute paramAttribute)
  {
    if ((this.mInitAttributes == null) || (this.mInitAttributes.size() == 0));
    while (true)
    {
      return false;
      for (int i = 0; i < this.mInitAttributes.size(); i++)
        if (((Attribute)this.mInitAttributes.get(i)).id == paramAttribute.id)
          return true;
    }
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("ga"))
      for (int i = 0; i < getChildCount(); i++)
        if ((getChildAt(i).isSelected()) && (i > 0))
          return this.mAttributes.get(i - 1);
    return super.getValue(paramString, paramObject);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.boundLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.itemLayout.scaleToBounds(this.boundLayout);
    this.buttonLayout.scaleToBounds(this.itemLayout);
    int i = getChildCount();
    for (int j = 0; j < i; j++)
    {
      ViewElement localViewElement = getChildAt(j);
      localViewElement.measure(this.buttonLayout);
      ((ButtonViewElement)localViewElement).setTextSize(SkinManager.getInstance().getMiddleTextSize());
      int i1 = j % 4 * this.itemLayout.width;
      int i2 = j / 4 * this.itemLayout.height;
      localViewElement.setTranslationX(i1);
      localViewElement.setTranslationY(i2);
    }
    int k = this.boundLayout.width;
    int m = i / 4;
    if (i % 4 == 0);
    for (int n = 0; ; n = 1)
    {
      setMeasuredDimension(k, (n + m) * this.itemLayout.height);
      return;
    }
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mAttributes = ((List)paramObject);
      int i = this.mAttributes.size();
      addNewItem("全部", true);
      for (int j = 0; j < i; j++)
      {
        Attribute localAttribute = (Attribute)this.mAttributes.get(j);
        boolean bool = initSelected(localAttribute);
        addNewItem(localAttribute.name, bool);
        if (bool)
          ((ButtonViewElement)getChildAt(0)).setSelected(false);
      }
    }
    if (paramString.equalsIgnoreCase("setInitAttributes"))
      this.mInitAttributes = ((List)paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.popviews.categoryfilter.FilterView
 * JD-Core Version:    0.6.2
 */