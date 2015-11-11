package fm.qingting.qtradio.view.virtualchannels;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import java.util.Locale;

class ProgramIntervalChooseView extends QtView
  implements ViewElement.OnElementClickListener
{
  private final ViewLayout btnLayout = this.standardLayout.createChildLT(160, 60, 20, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private ButtonViewElement[] mIntervalButtons;
  private int mProgramCnt = 1000;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 180, 720, 180, 0, 0, ViewLayout.FILL);

  public ProgramIntervalChooseView(Context paramContext)
  {
    super(paramContext);
  }

  private void addButtons()
  {
    if (this.mProgramCnt < 1);
    while (true)
    {
      return;
      int i = 1 + (-1 + this.mProgramCnt) / 100;
      this.mIntervalButtons = new ButtonViewElement[i];
      for (int j = 0; j < i; j++)
      {
        ButtonViewElement localButtonViewElement = new ButtonViewElement(getContext());
        localButtonViewElement.setBackgroundColor(SkinManager.getPopButtonHighlightColor(), SkinManager.getBackgroundColor());
        localButtonViewElement.setTextColor(SkinManager.getBackgroundColor(), SkinManager.getTextColorSubInfo());
        int k = getStart(j);
        int m = k + 99;
        if (m > this.mProgramCnt)
          m = this.mProgramCnt;
        localButtonViewElement.setText(getIntervalText(k, m));
        localButtonViewElement.setOnElementClickListener(this);
        this.mIntervalButtons[j] = localButtonViewElement;
        addElement(localButtonViewElement);
      }
    }
  }

  private String getIntervalText(int paramInt1, int paramInt2)
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramInt1);
    arrayOfObject[1] = Integer.valueOf(paramInt2);
    return String.format(localLocale, "%d-%d", arrayOfObject);
  }

  private int getStart(int paramInt)
  {
    return 1 + paramInt * 100;
  }

  private int measureElements()
  {
    int i = 0;
    if (this.mIntervalButtons == null)
      return 0;
    int j = (this.standardLayout.width - 2 * this.btnLayout.leftMargin - 4 * this.btnLayout.width) / 3;
    int k = this.btnLayout.leftMargin;
    int m = j + (j + this.btnLayout.height);
    int n = k;
    int i1 = j;
    if (i < this.mIntervalButtons.length)
    {
      ButtonViewElement localButtonViewElement = this.mIntervalButtons[i];
      localButtonViewElement.measure(n, i1, n + this.btnLayout.width, i1 + this.btnLayout.height);
      localButtonViewElement.setTextSize(0.5F * this.btnLayout.height);
      if (i % 4 == 3)
      {
        n = this.btnLayout.leftMargin;
        i1 += j + this.btnLayout.height;
        if (i != -1 + this.mIntervalButtons.length)
          m += j + this.btnLayout.height;
      }
      while (true)
      {
        i++;
        break;
        n += j + this.btnLayout.width;
      }
    }
    return m;
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    for (int i = 0; ; i++)
      if (i < this.mIntervalButtons.length)
      {
        if (paramViewElement == this.mIntervalButtons[i])
          dispatchActionEvent("jumptopoint", Integer.valueOf(getStart(i)));
      }
      else
        return;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.btnLayout.scaleToBounds(this.standardLayout);
    int i = measureElements();
    setMeasuredDimension(this.standardLayout.width, i);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mProgramCnt = ((Integer)paramObject).intValue();
      addButtons();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.virtualchannels.ProgramIntervalChooseView
 * JD-Core Version:    0.6.2
 */