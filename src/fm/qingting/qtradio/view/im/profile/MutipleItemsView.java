package fm.qingting.qtradio.view.im.profile;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout.Alignment;
import android.view.View.MeasureSpec;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.TextViewElement.VerticalAlignment;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.social.UserProfile;
import java.util.ArrayList;
import java.util.List;

public class MutipleItemsView extends QtView
  implements ViewElement.OnElementClickListener
{
  private static final int MAXCNT = 3;
  private static String VIEWALL = "查看全部";
  private final ViewLayout infoLayout = this.itemLayout.createChildLT(500, 45, 200, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 120, 720, 120, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 180, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private TextViewElement[] mInfoElement;
  private List<GroupInfo> mListDatas = new ArrayList();
  private TextViewElement mNumberElement;
  private TextViewElement mTypeElement;
  private final ViewLayout numberLayout = this.itemLayout.createChildLT(160, 45, 0, 65, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout typeLayout = this.itemLayout.createChildLT(160, 45, 0, 17, ViewLayout.SCALE_FLAG_SLTCW);

  public MutipleItemsView(Context paramContext)
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
    this.mInfoElement = new TextViewElement[3];
  }

  private void drawLines(Canvas paramCanvas)
  {
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
    if ((this.mInfoElement != null) && (this.mListDatas != null))
    {
      int i = Math.min(this.mListDatas.size(), 3);
      int j = 0;
      if (j < i)
      {
        if (this.mInfoElement[j] == paramViewElement)
        {
          if ((j != 2) || (this.mListDatas.size() <= 3))
            break label79;
          ControllerManager.getInstance().openMyGroupsController(this.mListDatas);
        }
        while (true)
        {
          j++;
          break;
          label79: ControllerManager.getInstance().openImGroupProfileController(((GroupInfo)this.mListDatas.get(j)).groupId);
        }
      }
    }
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
      int j = Math.min(3, this.mListDatas.size());
      for (int k = 0; k < j; k++)
      {
        TextViewElement localTextViewElement = this.mInfoElement[k];
        if (localTextViewElement != null)
        {
          localTextViewElement.measure(this.infoLayout);
          localTextViewElement.setTextSize(SkinManager.getInstance().getSubTextSize());
          localTextViewElement.setTranslationY(i + (this.itemLayout.height - localTextViewElement.getHeight()) / 2);
          i += this.itemLayout.height;
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
      this.mListDatas = ((UserProfile)paramObject).getGroups();
      if ((this.mListDatas != null) && (this.mListDatas.size() > 0))
        if (this.mListDatas.size() > 3)
        {
          j = 0;
          if (j < 2)
          {
            if (this.mInfoElement[j] == null)
            {
              localTextViewElement4 = new TextViewElement(getContext());
              localTextViewElement4.setMaxLineLimit(2);
              localTextViewElement4.setColor(SkinManager.getTextColorNormal());
              localTextViewElement4.setVerticalAlignment(TextViewElement.VerticalAlignment.CENTER);
              this.mInfoElement[j] = localTextViewElement4;
              localTextViewElement4.setText(((GroupInfo)this.mListDatas.get(j)).groupName, false);
              localTextViewElement4.setOnElementClickListener(this);
              addElement(localTextViewElement4);
            }
            while (true)
            {
              j++;
              break;
              this.mInfoElement[j].setText(((GroupInfo)this.mListDatas.get(j)).groupName, false);
            }
          }
          if (this.mInfoElement[2] == null)
          {
            localTextViewElement2 = new TextViewElement(getContext());
            localTextViewElement2.setMaxLineLimit(2);
            localTextViewElement2.setColor(SkinManager.getTextColorNormal());
            localTextViewElement2.setAlignment(Layout.Alignment.ALIGN_CENTER);
            localTextViewElement2.setVerticalAlignment(TextViewElement.VerticalAlignment.CENTER);
            this.mInfoElement[2] = localTextViewElement2;
            localTextViewElement2.setText(VIEWALL, false);
            localTextViewElement2.setOnElementClickListener(this);
            addElement(localTextViewElement2);
            this.mNumberElement.setText(String.valueOf(this.mListDatas.size()), false);
            requestLayout();
          }
        }
    }
    label323: 
    while (!paramString.equalsIgnoreCase("setType"))
      while (true)
      {
        int j;
        TextViewElement localTextViewElement4;
        TextViewElement localTextViewElement2;
        return;
        TextViewElement localTextViewElement3 = this.mInfoElement[2];
        localTextViewElement3.setAlignment(Layout.Alignment.ALIGN_CENTER);
        localTextViewElement3.setText(VIEWALL, false);
        continue;
        int i = 0;
        if (i < this.mListDatas.size())
        {
          if (this.mInfoElement[i] != null)
            break label436;
          TextViewElement localTextViewElement1 = new TextViewElement(getContext());
          localTextViewElement1.setMaxLineLimit(2);
          localTextViewElement1.setColor(SkinManager.getTextColorNormal());
          localTextViewElement1.setAlignment(Layout.Alignment.ALIGN_NORMAL);
          localTextViewElement1.setVerticalAlignment(TextViewElement.VerticalAlignment.CENTER);
          this.mInfoElement[i] = localTextViewElement1;
          localTextViewElement1.setText(((GroupInfo)this.mListDatas.get(i)).groupName, false);
          localTextViewElement1.setOnElementClickListener(this);
          addElement(localTextViewElement1);
        }
        while (true)
        {
          i++;
          break label323;
          break;
          this.mInfoElement[i].setText(((GroupInfo)this.mListDatas.get(i)).groupName, false);
        }
        this.mNumberElement.setText("0");
      }
    label436: this.mTypeElement.setText((String)paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.im.profile.MutipleItemsView
 * JD-Core Version:    0.6.2
 */