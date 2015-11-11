package fm.qingting.qtradio.view.frontpage.discover;

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
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.model.SpecialTopicNode;
import java.util.List;

public class DiscoverItemRecommendView extends QtView
  implements ViewElement.OnElementClickListener
{
  private final ViewLayout boundLayout = this.standardLayout.createChildLT(1, 1, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout gradientLayout = this.standardLayout.createChildLT(214, 54, 10, 173, ViewLayout.SCALE_FLAG_SLTCW);
  private List<RecommendItemNode> mDatas;
  private TextViewElement[] mNames;
  private NetImageViewElement[] mPics;
  private TagElement[] mTags;
  private final ViewLayout nameLayout = this.standardLayout.createChildLT(216, 90, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout picLayout = this.standardLayout.createChildLT(214, 214, 0, 13, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 328, 720, 328, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public DiscoverItemRecommendView(Context paramContext, int paramInt)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getCardColor());
    initPics(paramContext, paramInt);
    initTags(paramContext);
    initNames(paramContext);
  }

  private String getTagName(RecommendItemNode paramRecommendItemNode)
  {
    Node localNode = paramRecommendItemNode.mNode;
    String str;
    if (localNode == null)
      str = null;
    do
    {
      return str;
      if (!localNode.nodeName.equalsIgnoreCase("program"))
        break;
      str = ((ProgramNode)localNode).getChannelName();
    }
    while (str != null);
    return paramRecommendItemNode.belongName;
    if (localNode.nodeName.equalsIgnoreCase("channel"))
      return ((ChannelNode)localNode).title;
    if (localNode.nodeName.equalsIgnoreCase("specialtopic"))
      return ((SpecialTopicNode)localNode).title;
    return null;
  }

  private void initNames(Context paramContext)
  {
    this.mNames = new TextViewElement[3];
    for (int i = 0; i < this.mNames.length; i++)
    {
      TextViewElement localTextViewElement = new TextViewElement(paramContext);
      this.mNames[i] = localTextViewElement;
      localTextViewElement.setAlignment(Layout.Alignment.ALIGN_NORMAL);
      localTextViewElement.setColor(SkinManager.getTextColorRecommend());
      localTextViewElement.setMaxLineLimit(2);
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
    this.mTags = new TagElement[3];
    for (int i = 0; i < this.mTags.length; i++)
    {
      TagElement localTagElement = new TagElement(paramContext);
      this.mTags[i] = localTagElement;
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
      TagElement localTagElement = this.mTags[m];
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
        if (i < this.mDatas.size())
        {
          PlayerAgent.getInstance().addPlaySource(22);
          ControllerManager.getInstance().openControllerByRecommendNode((Node)this.mDatas.get(i));
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
      this.mDatas = ((List)paramObject);
      int i = Math.min(3, this.mDatas.size());
      for (int j = 0; j < i; j++)
      {
        RecommendItemNode localRecommendItemNode = (RecommendItemNode)this.mDatas.get(j);
        this.mPics[j].setVisible(0);
        this.mNames[j].setVisible(0);
        this.mTags[j].setVisible(0);
        String str = localRecommendItemNode.getApproximativeThumb(214, 214);
        this.mPics[j].setImageUrl(str, false);
        this.mNames[j].setText(localRecommendItemNode.name, false);
        this.mTags[j].setText(getTagName(localRecommendItemNode));
      }
      if (i < 3)
        for (int k = i; k < 3; k++)
        {
          this.mPics[k].setVisible(4);
          this.mNames[k].setVisible(4);
          this.mTags[k].setVisible(4);
        }
      invalidate();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.discover.DiscoverItemRecommendView
 * JD-Core Version:    0.6.2
 */