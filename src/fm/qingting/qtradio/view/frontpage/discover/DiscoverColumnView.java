package fm.qingting.qtradio.view.frontpage.discover;

import android.content.Context;
import android.widget.ListView;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.qtradio.ad.AdPos;
import fm.qingting.qtradio.jd.view.JDItemView;
import fm.qingting.qtradio.jd.view.JDRecommendTagView;
import fm.qingting.qtradio.model.AdvertisementInfoNode;
import fm.qingting.qtradio.model.AdvertisementItemNode;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.RecommendCategoryNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.LoadingView;
import fm.qingting.qtradio.view.moreContentView.CustomSectionView;
import fm.qingting.qtradio.view.switchview.SwitchView;
import java.util.ArrayList;
import java.util.List;

public class DiscoverColumnView extends ViewGroupViewImpl
  implements InfoManager.ISubscribeEventListener
{
  private AdvertisementItemNode mAdNode;
  private SectionAdapter mAdapter = new SectionAdapter(null, this.mFactory);
  private LoadingView mEmptyTipsView;
  private ISectionFactory mFactory = new ISectionFactory()
  {
    public IView createView(int paramAnonymousInt)
    {
      switch (paramAnonymousInt)
      {
      case 4:
      default:
        return null;
      case 0:
        return new CustomSectionView(DiscoverColumnView.this.getContext());
      case 1:
        return new DiscoverItemAllView(DiscoverColumnView.this.getContext(), this.val$hash);
      case 3:
        return new DiscoverItemOrdinaryViewV2(DiscoverColumnView.this.getContext(), this.val$hash);
      case 2:
        return new DiscoverItemTagView(DiscoverColumnView.this.getContext(), this.val$hash);
      case 5:
        JDRecommendTagView localJDRecommendTagView = new JDRecommendTagView(DiscoverColumnView.this.getContext(), this.val$hash);
        localJDRecommendTagView.hideTag();
        return localJDRecommendTagView;
      case 6:
        return new JDItemView(DiscoverColumnView.this.getContext(), this.val$hash);
      case 7:
      }
      DiscoverAdItemView localDiscoverAdItemView = new DiscoverAdItemView(DiscoverColumnView.this.getContext(), this.val$hash);
      localDiscoverAdItemView.setListener(new DiscoverAdItemView.OnDeleteListener()
      {
        public void onDelete()
        {
          DiscoverColumnView.access$002(DiscoverColumnView.this, null);
          DiscoverColumnView.access$102(DiscoverColumnView.this, null);
          DiscoverColumnView.this.setData(DiscoverColumnView.this.mRecommendCategoryNode);
        }
      });
      return localDiscoverAdItemView;
    }
  };
  private SwitchView mHeaderView;
  private ListView mListView;
  private CategoryNode mNode;
  private RecommendCategoryNode mRecommendCategoryNode;
  private AdPos mSectionPos;

  public DiscoverColumnView(Context paramContext)
  {
    super(paramContext);
    this.mListView = new ListView(paramContext);
    this.mHeaderView = new SwitchView(paramContext);
    this.mListView.addHeaderView(this.mHeaderView);
    this.mListView.setDivider(null);
    this.mListView.setSelector(17170445);
    this.mEmptyTipsView = new LoadingView(paramContext);
    addView(this.mEmptyTipsView);
    this.mListView.setEmptyView(this.mEmptyTipsView);
    this.mListView.setAdapter(this.mAdapter);
    addView(this.mListView);
  }

  private void setData(RecommendCategoryNode paramRecommendCategoryNode)
  {
    if (paramRecommendCategoryNode == null);
    List localList1;
    do
    {
      return;
      this.mHeaderView.update("setData", paramRecommendCategoryNode.getLstBanner());
      localList1 = paramRecommendCategoryNode.lstRecMain;
    }
    while (localList1 == null);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new SectionItem(0, null));
    localArrayList.add(new SectionItem(1, this.mNode));
    if ((this.mSectionPos != null) && (this.mSectionPos.sectionPosition >= localList1.size()))
      this.mSectionPos.sectionPosition = (-1 + localList1.size());
    for (int i = 0; i < localList1.size(); i++)
    {
      List localList2 = (List)localList1.get(i);
      if (localList2.size() > 0)
      {
        localArrayList.add(new SectionItem(0, null));
        localArrayList.add(new SectionItem(2, localList2.get(0)));
        int j = Math.min(3, localList2.size());
        for (int k = 0; k < j; k++)
          localArrayList.add(new SectionItem(3, localList2.get(k)));
        if ((this.mSectionPos != null) && (this.mSectionPos.sectionPosition == i) && (this.mAdNode != null))
          localArrayList.add(new SectionItem(7, this.mAdNode));
      }
    }
    localArrayList.add(new SectionItem(0, null));
    this.mAdapter.setData(localArrayList);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("headOffset"))
      return Integer.valueOf(this.mHeaderView.getBottom());
    return super.getValue(paramString, paramObject);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mListView.layout(0, 0, this.mListView.getMeasuredWidth(), this.mListView.getMeasuredHeight());
    this.mEmptyTipsView.layout(0, 0, this.mListView.getMeasuredWidth(), this.mListView.getMeasuredHeight());
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.mEmptyTipsView.measure(paramInt1, paramInt2);
    this.mListView.measure(paramInt1, paramInt2);
    super.onMeasure(paramInt1, paramInt2);
  }

  public void onNotification(String paramString)
  {
    if (paramString.equalsIgnoreCase("RECV_RECOMMEND_INFO"))
    {
      this.mRecommendCategoryNode = InfoManager.getInstance().root().getRecommendCategoryNode(this.mNode.sectionId);
      if (this.mRecommendCategoryNode != null)
        setData(this.mRecommendCategoryNode);
    }
    while (!paramString.equalsIgnoreCase("RADI"))
      return;
    this.mAdNode = InfoManager.getInstance().root().mAdvertisementInfoNode.getAdvertisement(this.mSectionPos.posid);
    setData(this.mRecommendCategoryNode);
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mNode = ((CategoryNode)paramObject);
      this.mSectionPos = InfoManager.getInstance().root().mAdvertisementInfoNode.getSection(this.mNode.categoryId);
      if (this.mSectionPos != null)
      {
        this.mAdNode = InfoManager.getInstance().root().mAdvertisementInfoNode.getAdvertisement(this.mSectionPos.posid);
        if (this.mAdNode == null)
          InfoManager.getInstance().loadAdvertisement(this.mSectionPos, -1, this);
      }
      this.mRecommendCategoryNode = InfoManager.getInstance().root().getRecommendCategoryNode(this.mNode.sectionId);
      if (this.mRecommendCategoryNode == null)
        InfoManager.getInstance().loadRecommendInfo(this.mNode.sectionId, this);
    }
    else
    {
      return;
    }
    setData(this.mRecommendCategoryNode);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.discover.DiscoverColumnView
 * JD-Core Version:    0.6.2
 */