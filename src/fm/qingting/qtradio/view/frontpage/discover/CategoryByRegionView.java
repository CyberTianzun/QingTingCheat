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
import fm.qingting.qtradio.model.Attribute;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.ContentCategoryNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.LiveNode;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.RadioNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.utils.QTMSGManage;
import java.util.List;

class CategoryByRegionView extends QtView
  implements ViewElement.OnElementClickListener
{
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(240, 90, 720, 90, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(1, 60, 0, 15, ViewLayout.SCALE_FLAG_SLTCW);
  private final Paint mLinePaint = new Paint();
  private Node mLocalNode;
  private List<CategoryNode> mNodes;
  private Node mRadioNode;
  private final ViewLayout narrowLayout = ViewLayout.createViewLayoutWithBoundsLT(180, 90, 720, 90, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public CategoryByRegionView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getCardColor());
    this.mLinePaint.setColor(SkinManager.getDividerColor());
  }

  private void addNewItem(String paramString)
  {
    ButtonViewElement localButtonViewElement = new ButtonViewElement(getContext());
    localButtonViewElement.setBackgroundColor(SkinManager.getBackgroundColor(), 0);
    localButtonViewElement.setTextColor(SkinManager.getTextColorHighlight(), SkinManager.getTextColorNormal_New());
    localButtonViewElement.setText(paramString);
    addElement(localButtonViewElement);
    localButtonViewElement.setOnElementClickListener(this);
  }

  private void drawLines(Canvas paramCanvas)
  {
    int i = getChildCount();
    if (i == 0)
      return;
    if (narrow());
    for (int j = 4; ; j = 3)
    {
      for (int k = 0; k < i; k++)
      {
        if (k % j == 0)
        {
          int i1 = getChildAt(k).getTopMargin();
          paramCanvas.drawLine(0.0F, i1, getMeasuredWidth(), i1, this.mLinePaint);
        }
        if (k % j != j - 1)
        {
          ViewElement localViewElement = getChildAt(k);
          int m = localViewElement.getRightMargin();
          int n = localViewElement.getTopMargin();
          paramCanvas.drawLine(m, n + this.lineLayout.topMargin, m, n + this.lineLayout.getBottom(), this.mLinePaint);
        }
      }
      break;
    }
  }

  private boolean narrow()
  {
    return getChildCount() == 4;
  }

  private void setData(List<CategoryNode> paramList)
  {
    if (this.mLocalNode != null)
    {
      String str = ((Attribute)this.mLocalNode).name;
      if ((str != null) && (!str.equalsIgnoreCase("台")))
        str = str + "台";
      addNewItem(str);
    }
    for (int i = 0; i < paramList.size(); i++)
      addNewItem(((CategoryNode)paramList.get(i)).name);
    if (this.mRadioNode != null)
      addNewItem(((RadioNode)this.mRadioNode).mTitle);
    requestLayout();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    drawLines(paramCanvas);
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    int i = getChildCount();
    for (int j = 0; ; j++)
    {
      if (j < i)
      {
        if (paramViewElement != getChildAt(j))
          continue;
        if (this.mLocalNode == null)
          break label169;
        if (j != 0)
          break label53;
        ControllerManager.getInstance().openTraditionalChannelsView(this.mLocalNode);
        QTMSGManage.getInstance().sendStatistcsMessage("v6_category_live_click", "本地");
      }
      label53: 
      do
      {
        do
        {
          return;
          int k = j - 1;
          if (k < this.mNodes.size())
          {
            CategoryNode localCategoryNode2 = (CategoryNode)this.mNodes.get(k);
            if (localCategoryNode2.isRegionCategory())
              ControllerManager.getInstance().openCategoryListView((Node)this.mNodes.get(k));
            while (true)
            {
              QTMSGManage.getInstance().sendStatistcsMessage("v6_category_live_click", localCategoryNode2.name);
              return;
              ControllerManager.getInstance().openTraditionalChannelsView(localCategoryNode2);
            }
          }
        }
        while (this.mRadioNode == null);
        ControllerManager.getInstance().openRadioChannelsController(this.mRadioNode);
        QTMSGManage.getInstance().sendStatistcsMessage("v6_category_live_click", "系统收音机");
        return;
        if (j < this.mNodes.size())
        {
          CategoryNode localCategoryNode1 = (CategoryNode)this.mNodes.get(j);
          if (localCategoryNode1.isRegionCategory())
            ControllerManager.getInstance().openCategoryListView((Node)this.mNodes.get(j));
          while (true)
          {
            QTMSGManage.getInstance().sendStatistcsMessage("v6_category_live_click", localCategoryNode1.name);
            return;
            ControllerManager.getInstance().openTraditionalChannelsView(localCategoryNode1);
          }
        }
      }
      while (this.mRadioNode == null);
      label169: ControllerManager.getInstance().openRadioChannelsController(this.mRadioNode);
      QTMSGManage.getInstance().sendStatistcsMessage("v6_category_live_click", "系统收音机");
      return;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.narrowLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.mLinePaint.setStrokeWidth(this.lineLayout.width);
    int i = getChildCount();
    if (i == 0)
    {
      setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), 0);
      return;
    }
    boolean bool = narrow();
    int j;
    int k;
    int m;
    int n;
    label106: ButtonViewElement localButtonViewElement;
    label137: int i6;
    label176: int i7;
    if (bool)
    {
      j = 4;
      float f = SkinManager.getInstance().getSubTextSize();
      k = 0;
      m = 0;
      n = 0;
      if (k >= i)
        break label258;
      localButtonViewElement = (ButtonViewElement)getChildAt(k);
      if (!bool)
        break label234;
      localButtonViewElement.measure(this.narrowLayout);
      localButtonViewElement.setTextSize(f);
      if (k % j == 0)
        n = 0;
      localButtonViewElement.setTranslationX(n);
      if (!bool)
        break label246;
      i6 = this.narrowLayout.width;
      i7 = i6 + n;
      localButtonViewElement.setTranslationY(m);
      if (k % j != j - 1)
        break label314;
    }
    label258: label314: for (int i8 = m + this.itemLayout.height; ; i8 = m)
    {
      k++;
      m = i8;
      n = i7;
      break label106;
      j = 3;
      break;
      label234: localButtonViewElement.measure(this.itemLayout);
      break label137;
      label246: i6 = this.itemLayout.width;
      break label176;
      int i1 = View.MeasureSpec.getSize(paramInt1);
      int i2 = this.itemLayout.height;
      int i3 = i / j;
      int i4 = i % j;
      int i5 = 0;
      if (i4 == 0);
      while (true)
      {
        setMeasuredDimension(i1, i2 * (i3 + i5));
        return;
        i5 = 1;
      }
    }
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mNodes = ((List)paramObject);
      this.mLocalNode = InfoManager.getInstance().root().mContentCategory.mLiveNode.getLocalCategoryNode();
      this.mRadioNode = InfoManager.getInstance().root().mContentCategory.mLiveNode.mRadioNode;
      if (this.mNodes != null)
        setData(this.mNodes);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.discover.CategoryByRegionView
 * JD-Core Version:    0.6.2
 */