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
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.Attribute;
import fm.qingting.qtradio.model.Attributes;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import java.util.List;

class CampusCategoryView extends QtView
  implements ViewElement.OnElementClickListener, InfoManager.ISubscribeEventListener
{
  private final int ID = 82;
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(180, 90, 720, 80, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(1, 60, 0, 15, ViewLayout.SCALE_FLAG_SLTCW);
  private final Paint mLinePaint = new Paint();
  private CategoryNode mNode;

  public CampusCategoryView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getCardColor());
    this.mLinePaint.setColor(SkinManager.getDividerColor());
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
        if (j % 4 == 0)
        {
          int n = getChildAt(j).getTopMargin();
          paramCanvas.drawLine(0.0F, n, getMeasuredWidth(), n, this.mLinePaint);
        }
        if (j % 4 != 3)
        {
          ViewElement localViewElement = getChildAt(j);
          int k = localViewElement.getRightMargin();
          int m = localViewElement.getTopMargin();
          paramCanvas.drawLine(k, m + this.lineLayout.topMargin, k, m + this.lineLayout.getBottom(), this.mLinePaint);
        }
      }
    }
  }

  private Attribute findAttribute(int paramInt)
  {
    List localList1 = this.mNode.getLstAttributes(true);
    if (localList1 == null)
      return null;
    for (int i = 0; i < localList1.size(); i++)
    {
      Attributes localAttributes = (Attributes)localList1.get(i);
      if (localAttributes.id == 82)
      {
        List localList2 = localAttributes.mLstAttribute;
        if ((localList2 == null) || (paramInt >= localList2.size()))
          break;
        return (Attribute)localList2.get(paramInt);
      }
    }
    return null;
  }

  private void setData(List<Attribute> paramList)
  {
    removeAllElements();
    if (paramList == null)
      return;
    for (int i = 0; i < paramList.size(); i++)
    {
      ButtonViewElement localButtonViewElement = new ButtonViewElement(getContext());
      localButtonViewElement.setBackgroundColor(SkinManager.getBackgroundColor(), 0);
      localButtonViewElement.setTextColor(SkinManager.getTextColorHighlight(), SkinManager.getTextColorNormal_New());
      localButtonViewElement.setText(((Attribute)paramList.get(i)).name);
      addElement(localButtonViewElement);
      localButtonViewElement.setOnElementClickListener(this);
    }
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
        Attribute localAttribute = findAttribute(j);
        if (localAttribute != null)
        {
          PlayerAgent.getInstance().addPlaySource(40);
          ControllerManager.getInstance().openChannelListByAttributeController(this.mNode, localAttribute);
        }
      }
      return;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.mLinePaint.setStrokeWidth(this.lineLayout.width);
    int i = getChildCount();
    if (i == 0)
    {
      setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), 0);
      return;
    }
    float f = SkinManager.getInstance().getSubTextSize();
    int j = 0;
    int k = 0;
    int m = 0;
    int i5;
    if (j < i)
    {
      ButtonViewElement localButtonViewElement = (ButtonViewElement)getChildAt(j);
      localButtonViewElement.measure(this.itemLayout);
      localButtonViewElement.setTextSize(f);
      if (j % 4 == 0)
        m = 0;
      localButtonViewElement.setTranslationX(m);
      i5 = m + this.itemLayout.width;
      localButtonViewElement.setTranslationY(k);
      if (j % 4 != 3)
        break label234;
    }
    label234: for (int i6 = k + this.itemLayout.height; ; i6 = k)
    {
      j++;
      k = i6;
      m = i5;
      break;
      int n = View.MeasureSpec.getSize(paramInt1);
      int i1 = this.itemLayout.height;
      int i2 = i / 4;
      int i3 = i % 4;
      int i4 = 0;
      if (i3 == 0);
      while (true)
      {
        setMeasuredDimension(n, i1 * (i4 + i2));
        return;
        i4 = 1;
      }
    }
  }

  public void onNotification(String paramString)
  {
    List localList;
    if (paramString.equalsIgnoreCase("RECV_ATTRIBUTES"))
    {
      localList = this.mNode.getLstAttributes(true);
      if (localList == null);
    }
    for (int i = 0; ; i++)
      if (i < localList.size())
      {
        Attributes localAttributes = (Attributes)localList.get(i);
        if (localAttributes.id == 82)
          setData(localAttributes.mLstAttribute);
      }
      else
      {
        return;
      }
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void update(String paramString, Object paramObject)
  {
    List localList;
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mNode = ((CategoryNode)paramObject);
      localList = this.mNode.getLstAttributes(false);
      if (localList != null)
        break label55;
      InfoManager.getInstance().loadCategoryAttrs(this.mNode, this.mNode.categoryId, this);
      setData(null);
    }
    while (true)
    {
      return;
      label55: for (int i = 0; i < localList.size(); i++)
      {
        Attributes localAttributes = (Attributes)localList.get(i);
        if (localAttributes.id == 82)
        {
          setData(localAttributes.mLstAttribute);
          return;
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.discover.CampusCategoryView
 * JD-Core Version:    0.6.2
 */