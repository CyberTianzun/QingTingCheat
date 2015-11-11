package fm.qingting.qtradio.view.frontpage.discover;

import android.content.Context;
import android.widget.ListView;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.qtradio.ad.AdPos;
import fm.qingting.qtradio.model.AdvertisementInfoNode;
import fm.qingting.qtradio.model.AdvertisementItemNode;
import fm.qingting.qtradio.model.CategoryNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.RecommendCategoryNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.moreContentView.CustomSectionView;
import fm.qingting.qtradio.view.switchview.SwitchView;
import java.util.ArrayList;
import java.util.List;

public class DiscoverCampusView extends ViewGroupViewImpl
  implements InfoManager.ISubscribeEventListener
{
  private AdvertisementItemNode mAdNode;
  private SectionAdapter mAdapter;
  private CampusCategoryView mCategoryView;
  private ISectionFactory mFactory;
  private SwitchView mHeaderView;
  private ListView mListView;
  private CategoryNode mNode;
  private RecommendCategoryNode mRecommendCategoryNode;
  private AdPos mSectionPos;

  public DiscoverCampusView(Context paramContext)
  {
    super(paramContext);
    final int i = hashCode();
    this.mFactory = new ISectionFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        case 4:
        case 5:
        case 6:
        default:
          return null;
        case 0:
          return new CustomSectionView(DiscoverCampusView.this.getContext());
        case 1:
          return new DiscoverItemAllView(DiscoverCampusView.this.getContext(), i);
        case 3:
          return new DiscoverItemOrdinaryViewV2(DiscoverCampusView.this.getContext(), i);
        case 2:
          return new DiscoverItemTagView(DiscoverCampusView.this.getContext(), i);
        case 7:
        }
        DiscoverAdItemView localDiscoverAdItemView = new DiscoverAdItemView(DiscoverCampusView.this.getContext(), i);
        localDiscoverAdItemView.setListener(new DiscoverAdItemView.OnDeleteListener()
        {
          public void onDelete()
          {
            DiscoverCampusView.access$002(DiscoverCampusView.this, null);
            DiscoverCampusView.access$102(DiscoverCampusView.this, null);
            DiscoverCampusView.this.setData(DiscoverCampusView.this.mRecommendCategoryNode);
          }
        });
        return localDiscoverAdItemView;
      }
    };
    this.mAdapter = new SectionAdapter(null, this.mFactory);
    this.mListView = new ListView(paramContext);
    this.mHeaderView = new SwitchView(paramContext);
    this.mListView.addHeaderView(this.mHeaderView);
    this.mListView.setDivider(null);
    this.mListView.setSelector(17170445);
    this.mListView.addFooterView(new CampusTagView(paramContext, i));
    this.mCategoryView = new CampusCategoryView(paramContext);
    this.mListView.addFooterView(this.mCategoryView);
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
      this.mRecommendCategoryNode = InfoManager.getInstance().root().getRecommendCategoryNode(this.mNode.sectionId);
      if (this.mRecommendCategoryNode != null)
        break label144;
      InfoManager.getInstance().loadRecommendInfo(this.mNode.sectionId, this);
    }
    while (true)
    {
      this.mSectionPos = InfoManager.getInstance().root().mAdvertisementInfoNode.getSection(this.mNode.categoryId);
      if (this.mSectionPos != null)
      {
        this.mAdNode = InfoManager.getInstance().root().mAdvertisementInfoNode.getAdvertisement(this.mSectionPos.posid);
        if (this.mAdNode == null)
          InfoManager.getInstance().loadAdvertisement(this.mSectionPos, -1, this);
      }
      this.mCategoryView.update("setData", this.mNode);
      return;
      label144: setData(this.mRecommendCategoryNode);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.discover.DiscoverCampusView
 * JD-Core Version:    0.6.2
 */