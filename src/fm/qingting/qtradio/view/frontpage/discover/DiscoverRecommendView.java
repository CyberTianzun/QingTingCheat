package fm.qingting.qtradio.view.frontpage.discover;

import android.content.Context;
import android.util.SparseArray;
import android.widget.ListView;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.qtradio.jd.data.CommodityInfo;
import fm.qingting.qtradio.jd.data.JDApi;
import fm.qingting.qtradio.jd.data.JDApi.ErrorCode;
import fm.qingting.qtradio.jd.data.JDApi.OnResponseListener;
import fm.qingting.qtradio.jd.data.Response;
import fm.qingting.qtradio.jd.view.JDRecommendItemView;
import fm.qingting.qtradio.jd.view.JDRecommendTagView;
import fm.qingting.qtradio.jd.view.JDRecommendTagView.OnDeleteListener;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.RecommendCategoryNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.view.LoadingView;
import fm.qingting.qtradio.view.moreContentView.CustomSectionView;
import fm.qingting.qtradio.view.popviews.CategoryResortPopView.CategoryResortInfo;
import fm.qingting.qtradio.view.switchview.SwitchView;
import fm.qingting.utils.ThirdTracker;
import java.util.ArrayList;
import java.util.List;

public class DiscoverRecommendView extends ViewGroupViewImpl
  implements InfoManager.ISubscribeEventListener, RootNode.IInfoUpdateEventListener
{
  private SectionAdapter mAdapter;
  private ArrayList<CommodityInfo> mCommodityInfos;
  private ISectionFactory mFactory = new ISectionFactory()
  {
    public IView createView(int paramAnonymousInt)
    {
      switch (paramAnonymousInt)
      {
      case 1:
      default:
        return null;
      case 0:
        return new CustomSectionView(DiscoverRecommendView.this.getContext());
      case 3:
        return new DiscoverItemRecommendView(DiscoverRecommendView.this.getContext(), this.val$hash);
      case 2:
        return new DiscoverItemRecommendTagView(DiscoverRecommendView.this.getContext(), this.val$hash);
      case 4:
        return new DiscoverCollectionItemView(DiscoverRecommendView.this.getContext(), this.val$hash);
      case 5:
        JDRecommendTagView localJDRecommendTagView = new JDRecommendTagView(DiscoverRecommendView.this.getContext(), this.val$hash);
        localJDRecommendTagView.setListenr(new JDRecommendTagView.OnDeleteListener()
        {
          public void onDelete()
          {
            DiscoverRecommendView.access$002(DiscoverRecommendView.this, null);
            DiscoverRecommendView.this.setData(DiscoverRecommendView.this.mRecommendCategoryNode);
          }
        });
        return localJDRecommendTagView;
      case 6:
      }
      return new JDRecommendItemView(DiscoverRecommendView.this.getContext(), this.val$hash);
    }
  };
  private SwitchView mHeaderView;
  private ListView mListView;
  private LoadingView mLoadingView;
  private RecommendCategoryNode mRecommendCategoryNode;

  public DiscoverRecommendView(Context paramContext)
  {
    super(paramContext);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 0);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 12);
    this.mAdapter = new SectionAdapter(null, this.mFactory);
    this.mListView = new ListView(paramContext);
    this.mHeaderView = new SwitchView(paramContext);
    this.mListView.addHeaderView(this.mHeaderView);
    this.mListView.setDivider(null);
    this.mListView.setSelector(17170445);
    this.mLoadingView = new LoadingView(paramContext);
    addView(this.mLoadingView);
    this.mListView.setEmptyView(this.mLoadingView);
    this.mListView.setAdapter(this.mAdapter);
    addView(this.mListView);
    if ((InfoManager.getInstance().enableJdAd()) && (InfoManager.getInstance().getJdAdPosition() >= 0))
      JDApi.request(new JDApi.OnResponseListener()
      {
        public void onResponse(Response paramAnonymousResponse)
        {
          if (paramAnonymousResponse.getErrorCode() == JDApi.ErrorCode.SUCCESS)
          {
            DiscoverRecommendView.access$002(DiscoverRecommendView.this, (ArrayList)paramAnonymousResponse.getData());
            DiscoverRecommendView.this.setData(DiscoverRecommendView.this.mRecommendCategoryNode);
            ThirdTracker.getInstance().setJDAdv(DiscoverRecommendView.this.mCommodityInfos);
          }
        }
      });
  }

  private List<List<RecommendItemNode>> getCustomCategoryList(List<List<RecommendItemNode>> paramList)
  {
    ArrayList localArrayList1 = CategoryResortPopView.CategoryResortInfo.getSortedIdArrayList();
    SparseArray localSparseArray = new SparseArray();
    ArrayList localArrayList2 = new ArrayList();
    RecommendCategoryNode localRecommendCategoryNode = InfoManager.getInstance().root().mLocalRecommendCategoryNode;
    List localList3;
    if ((localRecommendCategoryNode != null) && (InfoManager.getInstance().isLocalRecommendInfoAdded()))
    {
      List localList2 = localRecommendCategoryNode.lstRecMain;
      if ((localList2 != null) && (localList2.get(0) != null))
      {
        localList3 = (List)localList2.get(0);
        if ((localList3 != null) && (localList3.size() > 0))
        {
          int i2 = localArrayList1.indexOf(Integer.valueOf(((RecommendItemNode)localList3.get(0)).sectionId));
          if (i2 < 0)
            break label216;
          localSparseArray.put(i2, localList3);
        }
      }
    }
    int i = 0;
    label138: if (i < paramList.size())
    {
      List localList1 = (List)paramList.get(i);
      int i1;
      if (localList1.size() > 0)
      {
        i1 = localArrayList1.indexOf(Integer.valueOf(((RecommendItemNode)localList1.get(0)).sectionId));
        if (i1 < 0)
          break label239;
      }
      while (true)
      {
        try
        {
          localSparseArray.put(i1, localList1);
          i++;
          break label138;
          label216: localArrayList2.add(localList3);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          continue;
        }
        label239: localArrayList2.add(localList1);
      }
    }
    ArrayList localArrayList3 = new ArrayList();
    int m;
    for (int j = 0; ; j++)
    {
      int k = localSparseArray.size();
      m = 0;
      if (j >= k)
        break;
      int n = localSparseArray.keyAt(j);
      if (localSparseArray.get(n) != null)
        localArrayList3.add(localSparseArray.get(n));
    }
    while (m < localArrayList2.size())
    {
      if (localArrayList2.get(m) != null)
        localArrayList3.add(localArrayList2.get(m));
      m++;
    }
    return localArrayList3;
  }

  private void setData(RecommendCategoryNode paramRecommendCategoryNode)
  {
    if (paramRecommendCategoryNode == null);
    Object localObject;
    do
    {
      return;
      this.mHeaderView.update("setData", paramRecommendCategoryNode.getLstBanner());
      localObject = paramRecommendCategoryNode.lstRecMain;
    }
    while (localObject == null);
    if (InfoManager.getInstance().getEnableCustomCategory())
    {
      List localList4 = getCustomCategoryList((List)localObject);
      if (localList4.size() > 0)
        localObject = localList4;
    }
    ArrayList localArrayList = new ArrayList();
    List localList1 = InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes();
    if (!InfoManager.getInstance().getEnableFrontCollection());
    for (List localList2 = null; ; localList2 = localList1)
    {
      if ((localList2 != null) && (localList2.size() > 0))
      {
        RecommendItemNode localRecommendItemNode = new RecommendItemNode();
        localRecommendItemNode.sectionId = -100;
        localRecommendItemNode.belongName = "我的收藏";
        localArrayList.add(new SectionItem(0, null));
        localArrayList.add(new SectionItem(2, localRecommendItemNode));
        if (localList2.size() >= 2)
          break label686;
      }
      label281: label550: label686: for (int k = localList2.size(); ; k = 2)
      {
        long l = SharedCfg.getInstance().getLatestBootstrapTime();
        int m = 0;
        int n = 0;
        int i1;
        if ((n < localList2.size()) && (m < k))
        {
          ChannelNode localChannelNode3 = (ChannelNode)localList2.get(n);
          if ((localChannelNode3.channelType == 1) && (localChannelNode3.getUpdateTime() > l))
          {
            m++;
            localArrayList.add(new SectionItem(4, localChannelNode3));
            if (m < k);
          }
        }
        else
        {
          i1 = 0;
          if ((i1 < localList2.size()) && (m < k))
          {
            ChannelNode localChannelNode2 = (ChannelNode)localList2.get(i1);
            if (localChannelNode2.channelType != 0)
              break label550;
            m++;
            localArrayList.add(new SectionItem(4, localChannelNode2));
            if (m < k)
              break label550;
          }
        }
        for (int i2 = 0; ; i2++)
          if ((i2 < localList2.size()) && (m < k))
          {
            ChannelNode localChannelNode1 = (ChannelNode)localList2.get(i2);
            if (localChannelNode1.channelType == 1)
            {
              m++;
              localArrayList.add(new SectionItem(4, localChannelNode1));
              if (m < k);
            }
          }
          else
          {
            MobclickAgent.onEvent(InfoManager.getInstance().getContext(), "frontCollection");
            TCAgent.onEvent(InfoManager.getInstance().getContext(), "frontCollection");
            for (int i = 0; i < ((List)localObject).size(); i++)
            {
              List localList3 = (List)((List)localObject).get(i);
              if (localList3.size() > 0)
              {
                localArrayList.add(new SectionItem(0, null));
                localArrayList.add(new SectionItem(2, localList3.get(0)));
                localArrayList.add(new SectionItem(3, localList3));
              }
            }
            n++;
            break;
            i1++;
            break label281;
          }
        if (this.mCommodityInfos != null)
        {
          int j = 1 + 3 * InfoManager.getInstance().getJdAdPosition();
          if (j > localArrayList.size())
            j = localArrayList.size();
          localArrayList.add(j + 0, new SectionItem(5, null));
          localArrayList.add(j + 1, new SectionItem(6, this.mCommodityInfos));
          localArrayList.add(j + 2, new SectionItem(0, null));
        }
        localArrayList.add(new SectionItem(0, null));
        this.mAdapter.setData(localArrayList);
        return;
      }
    }
  }

  public void close(boolean paramBoolean)
  {
    super.close(paramBoolean);
  }

  public void onInfoUpdated(int paramInt)
  {
    if (paramInt == 0)
      update("setData", null);
    while (paramInt != 12)
      return;
    update("updatejd", null);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mListView.layout(0, 0, this.mListView.getMeasuredWidth(), this.mListView.getMeasuredHeight());
    this.mLoadingView.layout(0, 0, this.mListView.getMeasuredWidth(), this.mListView.getMeasuredHeight());
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.mListView.measure(paramInt1, paramInt2);
    this.mLoadingView.measure(paramInt1, paramInt2);
    super.onMeasure(paramInt1, paramInt2);
  }

  public void onNotification(String paramString)
  {
    if (paramString.equalsIgnoreCase("RECV_RECOMMEND_INFO"))
    {
      this.mRecommendCategoryNode = InfoManager.getInstance().root().getRecommendCategoryNode(0);
      if (this.mRecommendCategoryNode != null)
        setData(this.mRecommendCategoryNode);
    }
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mRecommendCategoryNode = InfoManager.getInstance().root().getRecommendCategoryNode(0);
      if (this.mRecommendCategoryNode == null)
        InfoManager.getInstance().loadRecommendInfo(0, this);
    }
    List localList;
    do
    {
      do
      {
        return;
        setData(this.mRecommendCategoryNode);
        return;
      }
      while (!paramString.equalsIgnoreCase("updatejd"));
      localList = ThirdTracker.getInstance().getJDAdv();
    }
    while ((localList == null) || (localList.size() <= 0));
    this.mCommodityInfos = ((ArrayList)localList);
    this.mAdapter.notifyDataSetChanged();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.frontpage.discover.DiscoverRecommendView
 * JD-Core Version:    0.6.2
 */