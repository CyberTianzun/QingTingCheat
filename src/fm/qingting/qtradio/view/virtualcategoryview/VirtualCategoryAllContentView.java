package fm.qingting.qtradio.view.virtualcategoryview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.Attribute;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.RecommendCategoryNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.view.MiniPlayerView;
import fm.qingting.qtradio.view.tab.TabPageIndicator;
import fm.qingting.qtradio.view.virtualchannellist.VirtualChannelListAllView;
import fm.qingting.qtradio.view.virtualchannellist.VirtualChannelListByAttrsView;
import fm.qingting.qtradio.view.virtualchannellist.VirtualChannelListView;
import java.util.List;

public class VirtualCategoryAllContentView extends ViewGroupViewImpl
  implements IEventHandler, InfoManager.ISubscribeEventListener
{
  private List<Attribute> mAttributes;
  private VirtualChannelListByAttrsView mFilteredView;
  private TabPageIndicator mIndicator;
  private ChoosenLabelsView mLabelsView;
  private int mLocateSection = -1;
  private CategoryNode mNode;
  private MiniPlayerView mPlayerView;
  private ViewPager mViewPager;
  private final ViewLayout miniLayout = this.standardLayout.createChildLT(720, 110, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);
  private final ViewLayout tabLayout = this.standardLayout.createChildLT(720, 88, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public VirtualCategoryAllContentView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor());
    this.mViewPager = new ViewPager(paramContext);
    this.mViewPager.setAdapter(new MyPagerAdapter(null));
    addView(this.mViewPager);
    this.mIndicator = new TabPageIndicator(paramContext);
    this.mIndicator.setViewPager(this.mViewPager);
    addView(this.mIndicator);
    this.mLabelsView = new ChoosenLabelsView(paramContext);
    addView(this.mLabelsView);
    this.mLabelsView.setVisibility(8);
    this.mLabelsView.setEventHandler(this);
    this.mFilteredView = new VirtualChannelListByAttrsView(paramContext);
    addView(this.mFilteredView);
    this.mFilteredView.setVisibility(8);
    this.mPlayerView = new MiniPlayerView(paramContext);
    addView(this.mPlayerView);
  }

  private void hideFilteredView()
  {
    this.mLabelsView.setVisibility(8);
    this.mFilteredView.setVisibility(8);
    this.mViewPager.setVisibility(0);
  }

  private void showFilteredView(List<Attribute> paramList)
  {
    this.mLabelsView.setVisibility(0);
    this.mFilteredView.setVisibility(0);
    this.mViewPager.setVisibility(8);
    this.mFilteredView.update("setFilter", paramList);
  }

  public void close(boolean paramBoolean)
  {
    this.mPlayerView.destroy();
    this.mLabelsView.close(paramBoolean);
    this.mFilteredView.close(paramBoolean);
    super.close(paramBoolean);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    boolean bool = paramString.equalsIgnoreCase("getAttributes");
    List localList = null;
    if (bool)
    {
      int i = this.mLabelsView.getVisibility();
      localList = null;
      if (i == 0)
        localList = this.mAttributes;
    }
    return localList;
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("clear"))
      hideFilteredView();
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mLabelsView.layout(0, 0, this.mLabelsView.getMeasuredWidth(), this.mLabelsView.getMeasuredHeight());
    this.mFilteredView.layout(0, this.mLabelsView.getMeasuredHeight(), this.standardLayout.width, this.standardLayout.height - this.miniLayout.height);
    this.tabLayout.layoutView(this.mIndicator);
    this.mViewPager.layout(0, this.tabLayout.height, this.standardLayout.width, this.standardLayout.height - this.miniLayout.height);
    this.mPlayerView.layout(0, this.standardLayout.height - this.miniLayout.height, this.standardLayout.width, this.standardLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.tabLayout.scaleToBounds(this.standardLayout);
    this.tabLayout.measureView(this.mIndicator);
    this.miniLayout.scaleToBounds(this.standardLayout);
    this.miniLayout.measureView(this.mPlayerView);
    this.mViewPager.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.tabLayout.height - this.miniLayout.height, 1073741824));
    this.tabLayout.measureView(this.mLabelsView);
    this.mFilteredView.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(this.standardLayout.height - this.miniLayout.height - this.mLabelsView.getMeasuredHeight(), 1073741824));
    super.onMeasure(paramInt1, paramInt2);
  }

  public void onNotification(String paramString)
  {
    if (paramString.equalsIgnoreCase("RECV_RECOMMEND_INFO"))
    {
      RecommendCategoryNode localRecommendCategoryNode = InfoManager.getInstance().getRecommendCategoryBySecId(this.mNode.sectionId);
      if (localRecommendCategoryNode != null)
      {
        List localList = localRecommendCategoryNode.lstRecMain;
        if (localList != null)
        {
          this.mViewPager.setAdapter(new MyPagerAdapter(localList));
          if (this.mLocateSection != -1)
            update("setId", Integer.valueOf(this.mLocateSection));
          this.mIndicator.notifyDataSetChanged();
        }
      }
    }
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void update(String paramString, Object paramObject)
  {
    int i = -1;
    if (paramString.equalsIgnoreCase("resetFilter"))
    {
      this.mAttributes = ((List)paramObject);
      if ((this.mAttributes == null) || (this.mAttributes.size() == 0))
      {
        hideFilteredView();
        this.mLabelsView.update(paramString, paramObject);
        this.mLocateSection = i;
      }
    }
    do
    {
      return;
      showFilteredView(this.mAttributes);
      break;
      if (!paramString.equalsIgnoreCase("setId"))
        break label292;
    }
    while (paramObject == null);
    this.mLocateSection = i;
    RecommendCategoryNode localRecommendCategoryNode3 = InfoManager.getInstance().getRecommendCategoryBySecId(this.mNode.sectionId);
    List localList4;
    int m;
    if (localRecommendCategoryNode3 != null)
    {
      localList4 = localRecommendCategoryNode3.lstRecMain;
      int k = ((Integer)paramObject).intValue();
      if (localList4 == null)
        break label595;
      m = 0;
      label131: if (m >= localList4.size())
        break label595;
      List localList5 = (List)localList4.get(m);
      if ((localList5 == null) || (localList5.size() <= 0) || (((RecommendItemNode)localList5.get(0)).sectionId != k));
    }
    while (true)
    {
      this.mViewPager.setAdapter(new MyPagerAdapter(localList4));
      this.mIndicator.notifyDataSetChanged();
      if ((m < 0) || (m >= -1 + this.mViewPager.getAdapter().getCount()))
        break;
      this.mViewPager.setCurrentItem(m + 1);
      this.mIndicator.shiftSlide(m + 1);
      return;
      m++;
      break label131;
      this.mLocateSection = ((Integer)paramObject).intValue();
      InfoManager.getInstance().loadRecommendInfo(this.mNode.sectionId, this);
      return;
      label292: if (paramString.equalsIgnoreCase("setData"))
      {
        if (paramObject == null)
        {
          RecommendCategoryNode localRecommendCategoryNode2 = InfoManager.getInstance().getRecommendCategoryBySecId(this.mNode.sectionId);
          if (localRecommendCategoryNode2 != null)
          {
            List localList3 = localRecommendCategoryNode2.lstRecMain;
            this.mViewPager.setAdapter(new MyPagerAdapter(localList3));
            this.mIndicator.notifyDataSetChanged();
            return;
          }
          InfoManager.getInstance().loadRecommendInfo(this.mNode.sectionId, this);
          return;
        }
        this.mLocateSection = i;
        RecommendCategoryNode localRecommendCategoryNode1 = InfoManager.getInstance().getRecommendCategoryBySecId(this.mNode.sectionId);
        if (localRecommendCategoryNode1 != null)
        {
          List localList1 = localRecommendCategoryNode1.lstRecMain;
          RecommendItemNode localRecommendItemNode = (RecommendItemNode)paramObject;
          if (localList1 != null);
          for (int j = 0; ; j++)
            if (j < localList1.size())
            {
              List localList2 = (List)localList1.get(j);
              if ((localList2 != null) && (localList2.size() > 0) && (localList2.get(0) == localRecommendItemNode))
                i = j;
            }
            else
            {
              this.mViewPager.setAdapter(new MyPagerAdapter(localList1));
              this.mIndicator.notifyDataSetChanged();
              if ((i < 0) || (i >= -1 + this.mViewPager.getAdapter().getCount()))
                break;
              this.mViewPager.setCurrentItem(i + 1);
              this.mIndicator.shiftSlide(i + 1);
              return;
            }
        }
        InfoManager.getInstance().loadRecommendInfo(this.mNode.sectionId, this);
        return;
      }
      if (!paramString.equalsIgnoreCase("setNode"))
        break;
      this.mNode = ((CategoryNode)paramObject);
      this.mFilteredView.update(paramString, paramObject);
      this.mLocateSection = i;
      return;
      label595: m = i;
    }
  }

  class MyPagerAdapter extends PagerAdapter
  {
    private List<List<RecommendItemNode>> mList;

    public MyPagerAdapter()
    {
      Object localObject;
      this.mList = localObject;
    }

    public void destroyItem(View paramView, int paramInt, Object paramObject)
    {
      ((ViewPager)paramView).removeView((View)paramObject);
    }

    public int getCount()
    {
      if (this.mList == null)
        return 1;
      return 1 + this.mList.size();
    }

    public int getItemPosition(Object paramObject)
    {
      return super.getItemPosition(paramObject);
    }

    public CharSequence getPageTitle(int paramInt)
    {
      if (this.mList == null)
        return "全部";
      if (paramInt == 0)
        return "全部";
      return ((RecommendItemNode)((List)this.mList.get(paramInt - 1)).get(0)).briefName;
    }

    public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
    {
      Object localObject;
      if (paramInt == 0)
      {
        localObject = new VirtualChannelListAllView(VirtualCategoryAllContentView.this.getContext());
        ((IView)localObject).update("setData", VirtualCategoryAllContentView.this.mNode);
      }
      while (true)
      {
        ((ViewPager)paramViewGroup).addView(((IView)localObject).getView());
        return ((IView)localObject).getView();
        VirtualChannelListView localVirtualChannelListView = new VirtualChannelListView(VirtualCategoryAllContentView.this.getContext());
        localVirtualChannelListView.update("setData", ((List)this.mList.get(paramInt - 1)).get(0));
        localObject = localVirtualChannelListView;
      }
    }

    public boolean isViewFromObject(View paramView, Object paramObject)
    {
      return paramView == paramObject;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.virtualcategoryview.VirtualCategoryAllContentView
 * JD-Core Version:    0.6.2
 */