package fm.qingting.qtradio.view.im.profile;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.text.Layout.Alignment;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.TextViewElement.VerticalAlignment;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.manager.SkinManager;

public class GroupDescView extends QtView
{
  private final ViewLayout infoLayout = this.itemLayout.createChildLT(500, 45, 200, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 120, 720, 120, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 180, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private TextViewElement mInfoElement;
  private boolean mNeedMatchParentLine = false;
  private TextViewElement mTypeElement;
  private final ViewLayout typeLayout = this.itemLayout.createChildLT(160, 45, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public GroupDescView(Context paramContext)
  {
    super(paramContext);
    this.mTypeElement = new TextViewElement(paramContext);
    this.mTypeElement.setMaxLineLimit(1);
    this.mTypeElement.setAlignment(Layout.Alignment.ALIGN_CENTER);
    this.mTypeElement.setColor(SkinManager.getTextColorNormal());
    this.mTypeElement.setText("简介");
    addElement(this.mTypeElement);
    this.mInfoElement = new TextViewElement(paramContext);
    this.mInfoElement.setMaxLineLimit(10);
    this.mInfoElement.setColor(SkinManager.getTextColorNormal());
    this.mInfoElement.setVerticalAlignment(TextViewElement.VerticalAlignment.CENTER);
    addElement(this.mInfoElement);
  }

  private void drawLine(Canvas paramCanvas)
  {
    SkinManager localSkinManager = SkinManager.getInstance();
    if (this.mNeedMatchParentLine);
    for (int i = 0; ; i = this.lineLayout.leftMargin)
    {
      localSkinManager.drawHorizontalLine(paramCanvas, i, this.itemLayout.width, getMeasuredHeight() - this.lineLayout.height, this.lineLayout.height);
      return;
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    drawLine(paramCanvas);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.typeLayout.scaleToBounds(this.itemLayout);
    this.infoLayout.scaleToBounds(this.itemLayout);
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.mInfoElement.measure(this.infoLayout);
    this.mInfoElement.setTextSize(SkinManager.getInstance().getSubTextSize());
    String str = this.mInfoElement.getText();
    int i = 0;
    int k;
    if (str != null)
    {
      int j = this.mInfoElement.getText().length();
      i = 0;
      if (j > 0)
      {
        k = this.mInfoElement.getHeight();
        if (k - this.infoLayout.height <= 0)
          break label220;
      }
    }
    label220: for (i = k + this.itemLayout.height - this.infoLayout.height; ; i = this.itemLayout.height)
    {
      this.mInfoElement.setTranslationY((i - k) / 2);
      this.mTypeElement.measure(this.typeLayout);
      this.mTypeElement.setTextSize(SkinManager.getInstance().getMiddleTextSize());
      this.mTypeElement.setTranslationY((i - this.mTypeElement.getHeight()) / 2);
      setMeasuredDimension(this.itemLayout.width, i);
      return;
    }
  }

  public void update(String paramString, Object paramObject)
  {
    GroupInfo localGroupInfo;
    if (paramString.equalsIgnoreCase("setData"))
    {
      localGroupInfo = (GroupInfo)paramObject;
      if (localGroupInfo != null);
    }
    do
    {
      return;
      if ((localGroupInfo.groupDesc == null) || (localGroupInfo.groupDesc.length() == 0))
        this.mInfoElement.setText(getResources().getString(2131492888));
      while (true)
      {
        requestLayout();
        return;
        this.mInfoElement.setText(localGroupInfo.groupDesc);
      }
      if (paramString.equalsIgnoreCase("needfillline"))
      {
        this.mNeedMatchParentLine = true;
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("setType"));
    this.mTypeElement.setText((String)paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.im.profile.GroupDescView
 * JD-Core Version:    0.6.2
 */