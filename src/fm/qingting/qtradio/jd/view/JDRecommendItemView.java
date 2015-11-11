package fm.qingting.qtradio.jd.view;

import android.content.Context;
import android.text.Layout.Alignment;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.NetImageViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.jd.data.CommodityInfo;
import fm.qingting.qtradio.jd.data.JDApi;
import fm.qingting.qtradio.jd.data.JDApi.OnResponseListener;
import fm.qingting.qtradio.jd.data.Response;
import fm.qingting.qtradio.manager.SkinManager;
import java.util.ArrayList;

public class JDRecommendItemView extends QtView
  implements ViewElement.OnElementClickListener
{
  private final ViewLayout boundLayout = this.standardLayout.createChildLT(1, 1, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout gradientLayout = this.standardLayout.createChildLT(214, 54, 10, 173, ViewLayout.SCALE_FLAG_SLTCW);
  private ArrayList<CommodityInfo> mDataList;
  private boolean mHasExplosure;
  private TextViewElement[] mNames;
  private NetImageViewElement[] mPics;
  private TagElement[] mPrices;
  private final ViewLayout nameLayout = this.standardLayout.createChildLT(216, 90, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout picLayout = this.standardLayout.createChildLT(214, 214, 0, 13, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 328, 720, 328, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public JDRecommendItemView(Context paramContext, int paramInt)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getCardColor());
    initPics(paramContext, paramInt);
    initTags(paramContext);
    initNames(paramContext);
    this.mHasExplosure = false;
  }

  private void initNames(Context paramContext)
  {
    this.mNames = new TextViewElement[3];
    for (int i = 0; i < this.mNames.length; i++)
    {
      TextViewElement localTextViewElement = new TextViewElement(paramContext);
      this.mNames[i] = localTextViewElement;
      localTextViewElement.setMaxLineLimit(2);
      localTextViewElement.setAlignment(Layout.Alignment.ALIGN_NORMAL);
      localTextViewElement.setColor(SkinManager.getTextColorRecommend());
      localTextViewElement.setText("", false);
      addElement(localTextViewElement);
    }
  }

  private void initPics(Context paramContext, int paramInt)
  {
    this.mPics = new NetImageViewElement[3];
    for (int i = 0; i < this.mPics.length; i++)
    {
      NetImageViewElement localNetImageViewElement = new NetImageViewElement(paramContext);
      this.mPics[i] = localNetImageViewElement;
      localNetImageViewElement.setDefaultImageRes(2130837902);
      localNetImageViewElement.setHighlightColor(SkinManager.getItemHighlightMaskColor());
      localNetImageViewElement.setBoundColor(SkinManager.getDividerColor());
      localNetImageViewElement.setOnElementClickListener(this);
      addElement(localNetImageViewElement, paramInt);
    }
  }

  private void initTags(Context paramContext)
  {
    this.mPrices = new TagElement[3];
    for (int i = 0; i < this.mPrices.length; i++)
    {
      TagElement localTagElement = new TagElement(paramContext);
      this.mPrices[i] = localTagElement;
      addElement(localTagElement);
    }
  }

  private void measureArrayElement()
  {
    int i = (this.standardLayout.width - 3 * this.picLayout.width) / 4;
    int j = this.picLayout.topMargin;
    int k = (this.picLayout.width - this.nameLayout.width) / 2;
    int m = 0;
    int n = i;
    while (m < this.mPics.length)
    {
      NetImageViewElement localNetImageViewElement = this.mPics[m];
      localNetImageViewElement.measure(n, j, n + this.picLayout.width, j + this.picLayout.height);
      localNetImageViewElement.setBoundLineWidth(this.boundLayout.width);
      TagElement localTagElement = this.mPrices[m];
      localTagElement.measure(n, this.gradientLayout.topMargin, n + this.gradientLayout.width, this.gradientLayout.getBottom());
      localTagElement.setTextSize(SkinManager.getInstance().getTeenyTinyTextSize());
      localTagElement.setTextColor(-1);
      localTagElement.setTextOffset(this.gradientLayout.leftMargin);
      TextViewElement localTextViewElement = this.mNames[m];
      localTextViewElement.measure(n + k, j + this.picLayout.height, n + k + this.nameLayout.width, j + this.picLayout.height + this.nameLayout.height);
      localTextViewElement.setTextSize(SkinManager.getInstance().getRecommendTextSize());
      n += i + this.picLayout.width;
      m++;
    }
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    for (int i = 0; ; i++)
    {
      if (i < 3)
      {
        if (this.mPics[i] != paramViewElement)
          continue;
        if (i < this.mDataList.size())
        {
          ControllerManager.getInstance().openJDShop((CommodityInfo)this.mDataList.get(i));
          JDApi.sendEventMessage("JDADClick");
        }
      }
      return;
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.picLayout.scaleToBounds(this.standardLayout);
    this.nameLayout.scaleToBounds(this.standardLayout);
    this.boundLayout.scaleToBounds(this.standardLayout);
    this.gradientLayout.scaleToBounds(this.standardLayout);
    measureArrayElement();
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("content"))
    {
      this.mDataList = ((ArrayList)paramObject);
      int i = Math.min(3, this.mDataList.size());
      for (int j = 0; j < i; j++)
      {
        CommodityInfo localCommodityInfo = (CommodityInfo)this.mDataList.get(j);
        this.mPics[j].setVisible(0);
        this.mNames[j].setVisible(0);
        this.mPrices[j].setVisible(0);
        String str = localCommodityInfo.getAvatar();
        this.mPics[j].setImageUrl(str, false);
        this.mNames[j].setText(localCommodityInfo.getTitle(), false);
        this.mPrices[j].setText("￥" + localCommodityInfo.getPrice());
        if (!this.mHasExplosure)
        {
          JDApi.feedback(new JDApi.OnResponseListener()
          {
            public void onResponse(Response paramAnonymousResponse)
            {
            }
          }
          , ((CommodityInfo)this.mDataList.get(j)).getTrackUrl());
          JDApi.sendEventMessage("JDADExplosure");
        }
      }
      this.mHasExplosure = true;
      if (i < 3)
        for (int k = i; k < 3; k++)
        {
          this.mPics[k].setVisible(4);
          this.mNames[k].setVisible(4);
          this.mPrices[k].setVisible(4);
        }
      invalidate();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.jd.view.JDRecommendItemView
 * JD-Core Version:    0.6.2
 */