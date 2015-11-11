package fm.qingting.qtradio.view.im.profile;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout.Alignment;
import android.view.View.MeasureSpec;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.room.UserInfo;
import java.util.List;

public class MutipleMemberItemsView extends QtView
  implements ViewElement.OnElementClickListener
{
  private final ViewLayout infoLayout = this.itemLayout.createChildLT(520, 120, 200, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 120, 720, 120, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 180, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private int mChildrenCnt = 0;
  private MemberItemElement[] mInfoElement;
  private List<UserInfo> mListDatas;
  private TextViewElement mNumberElement;
  private TextViewElement mTypeElement;
  private final ViewLayout numberLayout = this.itemLayout.createChildLT(160, 45, 0, 65, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout typeLayout = this.itemLayout.createChildLT(160, 45, 0, 17, ViewLayout.SCALE_FLAG_SLTCW);

  public MutipleMemberItemsView(Context paramContext)
  {
    super(paramContext);
    this.mTypeElement = new TextViewElement(paramContext);
    this.mTypeElement.setMaxLineLimit(1);
    this.mTypeElement.setAlignment(Layout.Alignment.ALIGN_CENTER);
    this.mTypeElement.setColor(SkinManager.getTextColorNormal());
    addElement(this.mTypeElement);
    this.mNumberElement = new TextViewElement(paramContext);
    this.mNumberElement.setMaxLineLimit(1);
    this.mNumberElement.setAlignment(Layout.Alignment.ALIGN_CENTER);
    this.mNumberElement.setColor(SkinManager.getTextColorSubInfo());
    addElement(this.mNumberElement);
  }

  private void drawLines(Canvas paramCanvas)
  {
    if (this.mInfoElement == null)
      return;
    for (int i = 0; i < -1 + this.mInfoElement.length; i++)
      if (this.mInfoElement[i] != null)
        SkinManager.getInstance().drawHorizontalLine(paramCanvas, this.lineLayout.leftMargin, this.itemLayout.width, i * this.itemLayout.height + this.itemLayout.height - this.lineLayout.height, this.lineLayout.height);
    SkinManager.getInstance().drawHorizontalLine(paramCanvas, 0, this.itemLayout.width, getMeasuredHeight() - this.lineLayout.height, this.lineLayout.height);
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    drawLines(paramCanvas);
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    for (int i = 0; i < this.mInfoElement.length; i++)
      if (paramViewElement == this.mInfoElement[i])
        ControllerManager.getInstance().openImUserProfileController(((UserInfo)this.mListDatas.get(i)).userKey);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.typeLayout.scaleToBounds(this.itemLayout);
    this.infoLayout.scaleToBounds(this.itemLayout);
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.numberLayout.scaleToBounds(this.itemLayout);
    List localList = this.mListDatas;
    int i = 0;
    if (localList != null)
    {
      int j = this.mListDatas.size();
      i = 0;
      if (j > 0)
      {
        MemberItemElement[] arrayOfMemberItemElement = this.mInfoElement;
        i = 0;
        if (arrayOfMemberItemElement != null)
        {
          int k = Math.min(this.mListDatas.size(), this.mInfoElement.length);
          for (int m = 0; m < k; m++)
          {
            MemberItemElement localMemberItemElement = this.mInfoElement[m];
            if (localMemberItemElement != null)
            {
              localMemberItemElement.measure(this.infoLayout);
              localMemberItemElement.setTranslationY(i);
              i += this.itemLayout.height;
            }
          }
        }
      }
    }
    this.mTypeElement.measure(this.typeLayout);
    this.mTypeElement.setTextSize(SkinManager.getInstance().getMiddleTextSize());
    this.mNumberElement.measure(this.numberLayout);
    this.mNumberElement.setTextSize(SkinManager.getInstance().getTinyTextSize());
    setMeasuredDimension(this.itemLayout.width, i);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mListDatas = ((List)paramObject);
      if ((this.mListDatas == null) || (this.mListDatas.size() == 0))
        requestLayout();
    }
    else
    {
      return;
    }
    MemberItemElement[] arrayOfMemberItemElement = this.mInfoElement;
    this.mTypeElement.setText("群主", false);
    int i = this.mListDatas.size();
    if (this.mChildrenCnt == 0)
    {
      this.mInfoElement = new MemberItemElement[i];
      for (int n = 0; n < i; n++)
      {
        MemberItemElement localMemberItemElement2 = new MemberItemElement(getContext());
        this.mInfoElement[n] = localMemberItemElement2;
        localMemberItemElement2.setData((UserInfo)this.mListDatas.get(n));
        addElement(localMemberItemElement2);
        localMemberItemElement2.setOnElementClickListener(this);
      }
      this.mChildrenCnt = i;
    }
    while (true)
    {
      this.mNumberElement.setText(String.valueOf(i), false);
      requestLayout();
      return;
      if (i > this.mChildrenCnt)
      {
        this.mInfoElement = new MemberItemElement[i];
        System.arraycopy(arrayOfMemberItemElement, 0, this.mInfoElement, 0, this.mChildrenCnt);
        for (int k = 0; k < this.mChildrenCnt; k++)
          this.mInfoElement[k].setData((UserInfo)this.mListDatas.get(k));
        for (int m = this.mChildrenCnt; m < i; m++)
        {
          MemberItemElement localMemberItemElement1 = new MemberItemElement(getContext());
          this.mInfoElement[m] = localMemberItemElement1;
          localMemberItemElement1.setData((UserInfo)this.mListDatas.get(m));
          addElement(localMemberItemElement1);
          localMemberItemElement1.setOnElementClickListener(this);
        }
        this.mChildrenCnt = i;
      }
      else
      {
        for (int j = 0; j < i; j++)
          this.mInfoElement[j].setData((UserInfo)this.mListDatas.get(j));
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.im.profile.MutipleMemberItemsView
 * JD-Core Version:    0.6.2
 */