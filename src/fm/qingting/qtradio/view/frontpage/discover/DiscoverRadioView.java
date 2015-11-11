package fm.qingting.qtradio.view.frontpage.discover;

import android.content.Context;
import android.widget.ListView;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.qtradio.model.ContentCategoryNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.LiveNode;
import fm.qingting.qtradio.model.RecommendPlayingInfoNode;
import fm.qingting.qtradio.model.RecommendPlayingItemNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.moreContentView.CustomSectionView;
import java.util.ArrayList;
import java.util.List;

public class DiscoverRadioView extends ViewGroupViewImpl
  implements InfoManager.ISubscribeEventListener
{
  static final String STATISTIC_TAG = "v6_category_live_click";
  private SectionAdapter mAdapter;
  private CategoryByContentView mContentView;
  private ISectionFactory mFactory;
  private ListView mListView;
  private CategoryByRegionView mRegionView;

  public DiscoverRadioView(Context paramContext)
  {
    super(paramContext);
    final int i = hashCode();
    this.mFactory = new ISectionFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        default:
          return null;
        case 0:
          return new CustomSectionView(DiscoverRadioView.this.getContext());
        case 1:
          return new DiscoverItemAllView(DiscoverRadioView.this.getContext(), i);
        case 3:
          return new DiscoverItemRecommendPlayingView(DiscoverRadioView.this.getContext(), i);
        case 2:
        }
        return new DiscoverItemRecommendPlayingTagView(DiscoverRadioView.this.getContext(), i);
      }
    };
    this.mAdapter = new SectionAdapter(null, this.mFactory);
    this.mListView = new ListView(paramContext);
    RadioTagView localRadioTagView1 = new RadioTagView(paramContext, i);
    localRadioTagView1.update("setData", "地区");
    this.mListView.addHeaderView(localRadioTagView1);
    this.mRegionView = new CategoryByRegionView(paramContext);
    this.mListView.addHeaderView(this.mRegionView);
    RadioTagView localRadioTagView2 = new RadioTagView(paramContext, i);
    localRadioTagView2.update("setData", "内容");
    this.mListView.addHeaderView(localRadioTagView2);
    this.mContentView = new CategoryByContentView(paramContext);
    this.mListView.addHeaderView(this.mContentView);
    this.mListView.setDivider(null);
    this.mListView.setSelector(17170445);
    this.mListView.setAdapter(this.mAdapter);
    addView(this.mListView);
  }

  private void setCategoryData()
  {
    this.mRegionView.update("setData", InfoManager.getInstance().root().mContentCategory.mLiveNode.getRegionCategory());
    this.mContentView.update("setData", InfoManager.getInstance().root().mContentCategory.mLiveNode.getContentCategory());
  }

  private void setData(List<RecommendPlayingItemNode> paramList)
  {
    int i = 0;
    if ((paramList == null) || (paramList.size() == 0))
      return;
    ArrayList localArrayList = new ArrayList();
    if (paramList.size() > 0)
    {
      localArrayList.add(new SectionItem(0, null));
      localArrayList.add(new SectionItem(2, null));
      while (i < paramList.size())
      {
        localArrayList.add(new SectionItem(3, paramList.get(i)));
        i++;
      }
    }
    this.mAdapter.setData(localArrayList);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mListView.layout(0, 0, this.mListView.getMeasuredWidth(), this.mListView.getMeasuredHeight());
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.mListView.measure(paramInt1, paramInt2);
    super.onMeasure(paramInt1, paramInt2);
  }

  public void onNotification(String paramString)
  {
    if (paramString.equalsIgnoreCase("RRPPI"))
    {
      List localList = InfoManager.getInstance().root().mRecommendPlayingInfo.getCurrPlayingForShow();
      if (localList != null)
        setData(localList);
    }
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      setCategoryData();
      List localList = InfoManager.getInstance().root().mRecommendPlayingInfo.getCurrPlayingForShow();
      if (localList != null)
        setData(localList);
    }
    else
    {
      return;
    }
    InfoManager.getInstance().loadRecommendPlayingProgramsInfo(this);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.discover.DiscoverRadioView
 * JD-Core Version:    0.6.2
 */