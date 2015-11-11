package fm.qingting.qtradio.view;

import android.content.Context;
import android.text.Layout.Alignment;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.ContentCategoryNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.VirtualNode;
import fm.qingting.utils.ScreenType;
import java.util.ArrayList;
import java.util.List;

class UserGuideCategoryView extends QtView
{
  static final String LAST = "ugcv_last";
  static final String START = "ugcv_start";
  private ViewLayout firstLayout;
  private ViewLayout indicatorLayout;
  private ViewLayout itemLayout;
  private ViewLayout lastLayout;
  private boolean mDataSet;
  private TextViewElement mIndicator;
  private LastElement mLastElement;
  private ButtonViewElement mNextElement;
  private TextViewElement mTitleElement;
  private ViewLayout nextLayout;
  private ViewLayout roundLayout;
  private ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1280, 720, 1280, 0, 0, ViewLayout.FILL);
  private ViewLayout titleLayout = this.standardLayout.createChildLT(720, 70, 0, 170, ViewLayout.SCALE_FLAG_SLTCW);

  public UserGuideCategoryView(Context paramContext)
  {
    super(paramContext);
    ViewLayout localViewLayout1 = this.standardLayout;
    int i;
    ViewLayout localViewLayout2;
    if (ScreenType.isUltraWideScreen())
    {
      i = 76;
      this.firstLayout = localViewLayout1.createChildLT(180, i, 0, 280, ViewLayout.SCALE_FLAG_SLTCW);
      localViewLayout2 = this.standardLayout;
      if (!ScreenType.isUltraWideScreen())
        break label454;
    }
    label454: for (int j = 76; ; j = 80)
    {
      this.itemLayout = localViewLayout2.createChildLT(180, j, 0, 35, ViewLayout.SCALE_FLAG_SLTCW);
      this.nextLayout = this.standardLayout.createChildLT(630, 90, 45, 30, ViewLayout.SCALE_FLAG_SLTCW);
      this.indicatorLayout = this.standardLayout.createChildLT(720, 40, 0, 19, ViewLayout.SCALE_FLAG_SLTCW);
      this.roundLayout = this.standardLayout.createChildLT(10, 10, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
      this.lastLayout = this.standardLayout.createChildLT(300, 40, 50, 19, ViewLayout.SCALE_FLAG_SLTCW);
      this.mDataSet = false;
      setBackgroundResource(2130837993);
      this.mTitleElement = new TextViewElement(paramContext);
      this.mTitleElement.setMaxLineLimit(1);
      this.mTitleElement.setAlignment(Layout.Alignment.ALIGN_CENTER);
      this.mTitleElement.setColor(SkinManager.getTextColorNormal());
      this.mTitleElement.setText("请选择您感兴趣的内容", false);
      addElement(this.mTitleElement);
      this.mIndicator = new TextViewElement(paramContext);
      this.mIndicator.setMaxLineLimit(1);
      this.mIndicator.setColor(-8355712);
      this.mIndicator.setAlignment(Layout.Alignment.ALIGN_CENTER);
      this.mIndicator.setText("3/3", false);
      addElement(this.mIndicator);
      this.mNextElement = new ButtonViewElement(paramContext);
      this.mNextElement.setBackgroundColor(SkinManager.getTextColorHighlight(), SkinManager.getTextColorHighlight(), -3158065);
      this.mNextElement.setRoundCorner(true);
      this.mNextElement.setTextColor(-1, -1, -1);
      this.mNextElement.setText("开启您的收听之旅");
      addElement(this.mNextElement);
      this.mNextElement.setOnElementClickListener(new ViewElement.OnElementClickListener()
      {
        public void onElementClick(ViewElement paramAnonymousViewElement)
        {
          UserGuideCategoryView.this.dispatchActionEvent("ugcv_start", null);
        }
      });
      this.mLastElement = new LastElement(paramContext);
      this.mLastElement.setOnElementClickListener(new ViewElement.OnElementClickListener()
      {
        public void onElementClick(ViewElement paramAnonymousViewElement)
        {
          UserGuideCategoryView.this.dispatchActionEvent("ugcv_last", null);
        }
      });
      addElement(this.mLastElement);
      return;
      i = 80;
      break;
    }
  }

  public List<CategoryNode> getSelectedCategory()
  {
    List localList = InfoManager.getInstance().root().mContentCategory.mVirtualNode.getLstCategoryNodes();
    if (localList == null)
      return null;
    int i = getChildCount();
    ArrayList localArrayList = new ArrayList();
    for (int j = 0; j < i; j++)
    {
      ViewElement localViewElement = getChildAt(j);
      if (((localViewElement instanceof SmallCheckElement)) && (((SmallCheckElement)localViewElement).isChecked()) && (j < 3 + localList.size()))
        localArrayList.add(localList.get(j - 3));
    }
    return localArrayList;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.titleLayout.scaleToBounds(this.standardLayout);
    this.firstLayout.scaleToBounds(this.standardLayout);
    this.itemLayout.scaleToBounds(this.standardLayout);
    this.nextLayout.scaleToBounds(this.standardLayout);
    this.indicatorLayout.scaleToBounds(this.standardLayout);
    this.roundLayout.scaleToBounds(this.standardLayout);
    this.lastLayout.scaleToBounds(this.standardLayout);
    int i = this.titleLayout.topMargin;
    this.mTitleElement.measure(0, i, this.standardLayout.width, i + this.titleLayout.height);
    this.mTitleElement.setTextSize(SkinManager.getInstance().getLargeTextSize());
    this.mNextElement.measure(this.nextLayout.leftMargin, this.standardLayout.height - this.nextLayout.getBottom(), this.nextLayout.getRight(), this.standardLayout.height - this.nextLayout.topMargin);
    this.mIndicator.measure(this.indicatorLayout.leftMargin, this.standardLayout.height - this.nextLayout.getBottom() - this.indicatorLayout.getBottom(), this.indicatorLayout.getRight(), this.standardLayout.height - this.nextLayout.getBottom() - this.indicatorLayout.topMargin);
    this.mLastElement.measure(this.lastLayout.leftMargin, this.standardLayout.height - this.nextLayout.getBottom() - this.lastLayout.getBottom(), this.lastLayout.getRight(), this.standardLayout.height - this.nextLayout.getBottom() - this.lastLayout.topMargin);
    this.mIndicator.setTextSize(SkinManager.getInstance().getMiddleTextSize());
    this.mNextElement.setTextSize(SkinManager.getInstance().getNormalTextSize());
    this.mNextElement.setRoundCornerRadius(this.roundLayout.width);
    int j = this.firstLayout.topMargin;
    int k = getChildCount();
    int m;
    int n;
    int i2;
    if (k > 4)
    {
      m = 4;
      n = 0;
      if (m < k)
      {
        int i1 = m - 4;
        if (i1 % 4 == 0)
          n = 0;
        getChildAt(m).measure(n, j, n + this.itemLayout.width, j + this.itemLayout.height);
        i2 = n + this.itemLayout.width;
        if (i1 % 4 != 3)
          break label512;
      }
    }
    label512: for (int i3 = j + this.itemLayout.height; ; i3 = j)
    {
      m++;
      j = i3;
      n = i2;
      break;
      setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
      return;
    }
  }

  void setData()
  {
    int i = 0;
    if (this.mDataSet)
      return;
    List localList;
    ArrayList localArrayList;
    while (true)
    {
      this.mDataSet = true;
      String str1 = InfoManager.getInstance().getUserguideRecommend();
      localList = InfoManager.getInstance().root().mContentCategory.mVirtualNode.getLstCategoryNodes();
      localArrayList = new ArrayList();
      if ((localList != null) && (localList.size() != 0))
        break;
      localArrayList.add("电台");
      localArrayList.add("小说");
      localArrayList.add("音乐");
      localArrayList.add("读报");
      localArrayList.add("新闻");
      localArrayList.add("相声小品");
      localArrayList.add("脱口秀");
      localArrayList.add("情感");
      localArrayList.add("健康");
      localArrayList.add("军事");
      localArrayList.add("历史");
      localArrayList.add("儿童");
      localArrayList.add("娱乐");
      localArrayList.add("女性");
      localArrayList.add("搞笑");
      localArrayList.add("教育");
      localArrayList.add("外语");
      localArrayList.add("公开课");
      localArrayList.add("评书");
      localArrayList.add("戏曲");
      localArrayList.add("财经");
      localArrayList.add("科技");
      localArrayList.add("汽车");
      localArrayList.add("体育");
      localArrayList.add("校园");
      localArrayList.add("游戏");
      localArrayList.add("动漫");
      localArrayList.add("广播剧");
      localArrayList.add("主播");
      while (i < localArrayList.size())
      {
        SmallCheckElement localSmallCheckElement = new SmallCheckElement(getContext());
        String str2 = (String)localArrayList.get(i);
        localSmallCheckElement.setParam(str2);
        if (str1 != null)
          localSmallCheckElement.setMarkEnabled(str1.contains(str2));
        addElement(localSmallCheckElement);
        i++;
      }
    }
    for (int j = 0; ; j++)
    {
      int k = localList.size();
      i = 0;
      if (j >= k)
        break;
      CategoryNode localCategoryNode = (CategoryNode)localList.get(j);
      if (localCategoryNode.sectionId != 0)
        localArrayList.add(localCategoryNode.name);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.UserGuideCategoryView
 * JD-Core Version:    0.6.2
 */