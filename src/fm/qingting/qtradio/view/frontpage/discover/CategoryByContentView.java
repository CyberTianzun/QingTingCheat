package fm.qingting.qtradio.view.frontpage.discover;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.utils.QTMSGManage;
import java.util.List;

class CategoryByContentView extends QtView
  implements ViewElement.OnElementClickListener
{
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(240, 90, 720, 90, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(1, 60, 0, 15, ViewLayout.SCALE_FLAG_SLTCW);
  private CollapseElement mCollapsElement;
  private boolean mIsCollapsed = true;
  private final Paint mLinePaint = new Paint();
  private List<CategoryNode> mNodes;
  private boolean mUseCollapse = false;

  public CategoryByContentView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getCardColor());
  }

  private void drawLines(Canvas paramCanvas)
  {
    int i = getChildCount();
    if (i == 0);
    while (true)
    {
      return;
      for (int j = 0; j < i; j++)
      {
        if (j % 3 == 0)
        {
          int n = getChildAt(j).getTopMargin();
          paramCanvas.drawLine(0.0F, n, getMeasuredWidth(), n, this.mLinePaint);
        }
        if (j % 3 != 2)
        {
          ViewElement localViewElement = getChildAt(j);
          int k = localViewElement.getRightMargin();
          int m = localViewElement.getTopMargin();
          paramCanvas.drawLine(k, m + this.lineLayout.topMargin, k, m + this.lineLayout.getBottom(), this.mLinePaint);
        }
      }
    }
  }

  private void setData(List<CategoryNode> paramList)
  {
    if (paramList.size() > 6);
    for (boolean bool = true; ; bool = false)
    {
      this.mUseCollapse = bool;
      for (int i = 0; i < paramList.size(); i++)
      {
        ButtonViewElement localButtonViewElement = new ButtonViewElement(getContext());
        localButtonViewElement.setBackgroundColor(SkinManager.getBackgroundColor(), 0);
        localButtonViewElement.setTextColor(SkinManager.getTextColorHighlight(), SkinManager.getTextColorNormal_New());
        String str = ((CategoryNode)paramList.get(i)).name;
        if (!str.endsWith("台"))
          str = str + "台";
        localButtonViewElement.setText(str);
        addElement(localButtonViewElement);
        localButtonViewElement.setOnElementClickListener(this);
      }
    }
    this.mCollapsElement = new CollapseElement(getContext());
    addElement(this.mCollapsElement);
    this.mCollapsElement.setOnElementClickListener(this);
    this.mLinePaint.setColor(SkinManager.getDividerColor());
    requestLayout();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    drawLines(paramCanvas);
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    int i = 0;
    if (paramViewElement == this.mCollapsElement)
    {
      boolean bool1 = this.mIsCollapsed;
      boolean bool2 = false;
      if (!bool1)
        bool2 = true;
      this.mIsCollapsed = bool2;
      this.mCollapsElement.toggle();
      requestLayout();
    }
    while (true)
    {
      return;
      int j = getChildCount();
      while (i < j)
      {
        if (paramViewElement == getChildAt(i))
        {
          CategoryNode localCategoryNode = (CategoryNode)this.mNodes.get(i);
          ControllerManager.getInstance().openTraditionalChannelsView(localCategoryNode);
          QTMSGManage.getInstance().sendStatistcsMessage("v6_category_live_click", localCategoryNode.name);
          return;
        }
        i++;
      }
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.mLinePaint.setStrokeWidth(this.lineLayout.width);
    int i = getChildCount();
    if ((i == 0) || (i == 1))
    {
      setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), 0);
      return;
    }
    float f = SkinManager.getInstance().getSubTextSize();
    int j;
    int k;
    int m;
    int n;
    label99: int i7;
    if (this.mUseCollapse)
      if (this.mIsCollapsed)
      {
        j = 5;
        k = 0;
        m = 0;
        n = 0;
        if (k >= j)
          break label219;
        ButtonViewElement localButtonViewElement2 = (ButtonViewElement)getChildAt(k);
        localButtonViewElement2.measure(this.itemLayout);
        localButtonViewElement2.setTextSize(f);
        if (k % 3 == 0)
          n = 0;
        localButtonViewElement2.setTranslationX(n);
        i7 = n + this.itemLayout.width;
        localButtonViewElement2.setTranslationY(m);
        if (k % 3 != 2)
          break label368;
      }
    label219: label368: for (int i8 = m + this.itemLayout.height; ; i8 = m)
    {
      k++;
      m = i8;
      n = i7;
      break label99;
      j = i - 1;
      break;
      j = i - 1;
      break;
      if ((this.mUseCollapse) && (this.mIsCollapsed))
      {
        ButtonViewElement localButtonViewElement1 = (ButtonViewElement)getChildAt(5);
        localButtonViewElement1.measure(this.itemLayout);
        localButtonViewElement1.setTranslationY(m + this.itemLayout.height);
      }
      this.mCollapsElement.measure(this.itemLayout);
      if (j % 3 == 0)
        n = 0;
      this.mCollapsElement.setTranslationX(n);
      this.mCollapsElement.setTranslationY(m);
      int i1 = j + 1;
      int i2 = View.MeasureSpec.getSize(paramInt1);
      int i3 = this.itemLayout.height;
      int i4 = i1 / 3;
      int i5 = i1 % 3;
      int i6 = 0;
      if (i5 == 0);
      while (true)
      {
        setMeasuredDimension(i2, i3 * (i4 + i6));
        return;
        i6 = 1;
      }
    }
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mNodes = ((List)paramObject);
      if (this.mNodes != null)
        setData(this.mNodes);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.discover.CategoryByContentView
 * JD-Core Version:    0.6.2
 */