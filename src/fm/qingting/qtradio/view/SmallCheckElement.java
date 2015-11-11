package fm.qingting.qtradio.view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout.Alignment;
import fm.qingting.framework.view.AbsCheckBoxElement;
import fm.qingting.framework.view.ImageViewElement;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.utils.ScreenType;

class SmallCheckElement extends AbsCheckBoxElement
{
  private ViewLayout checkLayout;
  private ViewLayout imageLayout;
  private ViewLayout itemLayout;
  private ImageViewElement mCheckElement;
  private ImageViewElement mImageViewElement;
  private ImageViewElement mMarkElement;
  private boolean mMarkEnabled;
  private TextViewElement mNameElement;
  private ViewLayout markLayout;
  private ViewLayout nameLayout;

  public SmallCheckElement(Context paramContext)
  {
    super(paramContext);
    int i;
    int j;
    label23: int k;
    label59: int m;
    label95: int n;
    label129: ViewLayout localViewLayout4;
    if (ScreenType.isUltraWideScreen())
    {
      i = 76;
      if (!ScreenType.isUltraWideScreen())
        break label334;
      j = 76;
      this.itemLayout = ViewLayout.createViewLayoutWithBoundsLT(180, i, 180, j, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
      ViewLayout localViewLayout1 = this.itemLayout;
      if (!ScreenType.isUltraWideScreen())
        break label340;
      k = 8;
      this.imageLayout = localViewLayout1.createChildLT(150, 60, 15, k, ViewLayout.SCALE_FLAG_SLTCW);
      ViewLayout localViewLayout2 = this.itemLayout;
      if (!ScreenType.isUltraWideScreen())
        break label347;
      m = 0;
      this.checkLayout = localViewLayout2.createChildLT(40, 40, 5, m, ViewLayout.SCALE_FLAG_SLTCW);
      ViewLayout localViewLayout3 = this.itemLayout;
      if (!ScreenType.isUltraWideScreen())
        break label353;
      n = 0;
      this.markLayout = localViewLayout3.createChildLT(48, 48, 131, n, ViewLayout.SCALE_FLAG_SLTCW);
      localViewLayout4 = this.itemLayout;
      if (!ScreenType.isUltraWideScreen())
        break label359;
    }
    label334: label340: label347: label353: label359: for (int i1 = 18; ; i1 = 20)
    {
      this.nameLayout = localViewLayout4.createChildLT(150, 40, 15, i1, ViewLayout.SCALE_FLAG_SLTCW);
      this.mMarkEnabled = false;
      this.mImageViewElement = new ImageViewElement(paramContext);
      this.mImageViewElement.setImageRes(2130837536);
      this.mImageViewElement.setBelonging(this);
      this.mNameElement = new TextViewElement(paramContext);
      this.mNameElement.setMaxLineLimit(1);
      this.mNameElement.setColor(SkinManager.getTextColorSubInfo());
      this.mNameElement.setAlignment(Layout.Alignment.ALIGN_CENTER);
      this.mNameElement.setBelonging(this);
      this.mCheckElement = new ImageViewElement(paramContext);
      this.mCheckElement.setImageRes(2130837996);
      this.mCheckElement.setBelonging(this);
      this.mMarkElement = new ImageViewElement(paramContext);
      this.mMarkElement.setImageRes(2130837994);
      this.mMarkElement.setBelonging(this);
      return;
      i = 80;
      break;
      j = 80;
      break label23;
      k = 10;
      break label59;
      m = 2;
      break label95;
      n = 2;
      break label129;
    }
  }

  protected void drawCheckState(Canvas paramCanvas)
  {
    int i = getLeftMargin();
    int j = getTopMargin();
    this.mImageViewElement.setTranslationX(i);
    this.mCheckElement.setTranslationX(i);
    this.mNameElement.setTranslationX(i);
    this.mMarkElement.setTranslationX(i);
    this.mImageViewElement.setTranslationY(j);
    this.mCheckElement.setTranslationY(j);
    this.mNameElement.setTranslationY(j);
    this.mMarkElement.setTranslationY(j);
    this.mImageViewElement.draw(paramCanvas);
    this.mNameElement.draw(paramCanvas);
    if (isChecked())
      this.mCheckElement.draw(paramCanvas);
    if (this.mMarkEnabled)
      this.mMarkElement.draw(paramCanvas);
  }

  String getLabel()
  {
    return this.mNameElement.getText();
  }

  protected void onCheckChanged(boolean paramBoolean)
  {
    TextViewElement localTextViewElement = this.mNameElement;
    int i;
    ImageViewElement localImageViewElement;
    if (isChecked())
    {
      i = SkinManager.getTextColorHighlight();
      localTextViewElement.setColor(i);
      localImageViewElement = this.mImageViewElement;
      if (!isChecked())
        break label58;
    }
    label58: for (int j = 2130837538; ; j = 2130837536)
    {
      localImageViewElement.setImageRes(j);
      super.onCheckChanged(paramBoolean);
      return;
      i = SkinManager.getTextColorSubInfo();
      break;
    }
  }

  protected void onMeasureElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.itemLayout.scaleToBounds(paramInt3 - paramInt1, paramInt4 - paramInt2);
    this.imageLayout.scaleToBounds(this.itemLayout);
    this.checkLayout.scaleToBounds(this.itemLayout);
    this.nameLayout.scaleToBounds(this.itemLayout);
    this.markLayout.scaleToBounds(this.itemLayout);
    this.mImageViewElement.measure(this.imageLayout);
    this.mCheckElement.measure(this.checkLayout);
    this.mNameElement.measure(this.nameLayout);
    this.mMarkElement.measure(this.markLayout);
    this.mNameElement.setTextSize(SkinManager.getInstance().getSubTextSize());
  }

  void setMarkEnabled(boolean paramBoolean)
  {
    this.mMarkEnabled = paramBoolean;
    ImageViewElement localImageViewElement = this.mMarkElement;
    if (paramBoolean);
    for (int i = 0; ; i = 4)
    {
      localImageViewElement.setVisible(i);
      return;
    }
  }

  void setParam(String paramString)
  {
    this.mNameElement.setText(paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.SmallCheckElement
 * JD-Core Version:    0.6.2
 */