package fm.qingting.qtradio.view.personalcenter.mypodcaster;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.adapter.ItemParam;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ListViewImpl;
import fm.qingting.qtradio.helper.PodcasterHelper;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.MutiCheckManageableAdapter;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.social.UserProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyPodcasterListView extends ListViewImpl
  implements IEventHandler
{
  private MutiCheckManageableAdapter adapter;
  private IAdapterIViewFactory factory;
  private int mFirstPosition = 0;
  private boolean mIsLogin;
  private UserProfile mUserProfile;
  private int mVisibleCnt = 0;
  private List<Object> podcasterList;

  public MyPodcasterListView(Context paramContext, final int paramInt)
  {
    super(paramContext);
    this.factory = new IAdapterIViewFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        return new MyPodcasterItemView(MyPodcasterListView.this.getContext(), paramInt);
      }
    };
    this.podcasterList = new ArrayList();
    this.adapter = new MutiCheckManageableAdapter(this.podcasterList, this.factory);
    this.adapter.setEventHandler(this);
    setVerticalScrollBarEnabled(false);
    setVerticalFadingEdgeEnabled(false);
    setCacheColorHint(0);
    setDivider(null);
    setHeaderDividersEnabled(false);
    setSelector(new ColorDrawable(0));
    setAdapter(this.adapter);
    setOnScrollListener(new AbsListView.OnScrollListener()
    {
      public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        MyPodcasterListView.access$002(MyPodcasterListView.this, paramAnonymousInt1);
        MyPodcasterListView.access$102(MyPodcasterListView.this, paramAnonymousInt2);
      }

      public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
      {
      }
    });
    this.mIsLogin = CloudCenter.getInstance().isLogin();
    if (this.mIsLogin)
    {
      this.mUserProfile = InfoManager.getInstance().getUserProfile();
      return;
    }
    this.mUserProfile = null;
  }

  private void invalidateVisibleChildren()
  {
    for (int i = this.mFirstPosition; i < this.mFirstPosition + this.mVisibleCnt; i++)
    {
      View localView = getChildAt(i);
      if (localView != null)
        localView.invalidate();
    }
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    Boolean localBoolean;
    if (paramString.equalsIgnoreCase("hasCheckedIndexs"))
      localBoolean = Boolean.valueOf(this.adapter.hasCheckedIndexs());
    boolean bool;
    do
    {
      Iterator localIterator;
      List localList;
      do
      {
        do
        {
          return localBoolean;
          if (!paramString.equalsIgnoreCase("deletelist"))
            break;
          localIterator = this.adapter.getCheckList();
          localList = this.adapter.getData();
          localBoolean = null;
        }
        while (localIterator == null);
        localBoolean = null;
      }
      while (localList == null);
      ArrayList localArrayList = new ArrayList();
      while (localIterator.hasNext())
      {
        int i = ((Integer)localIterator.next()).intValue();
        if ((i >= 0) && (i < localList.size()))
          localArrayList.add(localList.get(i));
      }
      return localArrayList;
      bool = paramString.equalsIgnoreCase("allData");
      localBoolean = null;
    }
    while (!bool);
    return this.adapter.getData();
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("itemCallback"))
    {
      ItemParam localItemParam = (ItemParam)paramObject2;
      int i = localItemParam.position;
      if (localItemParam.type.equalsIgnoreCase("select"))
        return;
      this.adapter.checkIndex(i);
      return;
    }
    dispatchActionEvent(paramString, paramObject2);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setdata"))
    {
      boolean bool3 = this.mIsLogin;
      List localList2 = null;
      if (bool3)
      {
        UserProfile localUserProfile2 = this.mUserProfile;
        localList2 = null;
        if (localUserProfile2 != null)
        {
          UserInfo localUserInfo2 = this.mUserProfile.getUserInfo();
          localList2 = null;
          if (localUserInfo2 != null)
          {
            boolean bool4 = TextUtils.isEmpty(this.mUserProfile.getUserInfo().snsInfo.sns_id);
            localList2 = null;
            if (!bool4)
              localList2 = PodcasterHelper.getInstance().getAllMyPodcaster(this.mUserProfile.getUserInfo().snsInfo.sns_id);
          }
        }
      }
      this.podcasterList.clear();
      if (localList2 != null)
      {
        Iterator localIterator2 = localList2.iterator();
        while (localIterator2.hasNext())
        {
          int j = ((Integer)localIterator2.next()).intValue();
          this.podcasterList.add(PodcasterHelper.getInstance().getPodcaster(j));
        }
      }
      this.adapter.setData(this.podcasterList);
    }
    do
    {
      return;
      if (paramString.equalsIgnoreCase("invalidateList"))
      {
        invalidateVisibleChildren();
        return;
      }
      if (paramString.equalsIgnoreCase("refreshList"))
      {
        boolean bool1 = this.mIsLogin;
        List localList1 = null;
        if (bool1)
        {
          UserProfile localUserProfile1 = this.mUserProfile;
          localList1 = null;
          if (localUserProfile1 != null)
          {
            UserInfo localUserInfo1 = this.mUserProfile.getUserInfo();
            localList1 = null;
            if (localUserInfo1 != null)
            {
              boolean bool2 = TextUtils.isEmpty(this.mUserProfile.getUserInfo().snsInfo.sns_id);
              localList1 = null;
              if (!bool2)
                localList1 = PodcasterHelper.getInstance().getAllMyPodcaster(this.mUserProfile.getUserInfo().snsInfo.sns_id);
            }
          }
        }
        this.podcasterList.clear();
        if (localList1 != null)
        {
          Iterator localIterator1 = localList1.iterator();
          while (localIterator1.hasNext())
          {
            int i = ((Integer)localIterator1.next()).intValue();
            this.podcasterList.add(PodcasterHelper.getInstance().getPodcaster(i));
          }
        }
        this.adapter.setData(this.podcasterList);
        this.adapter.resetCheck();
        return;
      }
      if (paramString.equalsIgnoreCase("resetData"))
      {
        this.adapter.setData((List)paramObject);
        return;
      }
      if (paramString.equalsIgnoreCase("showManage"))
      {
        this.adapter.showManage(((Integer)paramObject).intValue());
        return;
      }
      if (paramString.equalsIgnoreCase("hideManage"))
      {
        this.adapter.hideManage();
        return;
      }
      if (paramString.equalsIgnoreCase("changeProcessState"))
      {
        this.adapter.notifyDataSetChanged();
        return;
      }
      if (paramString.equalsIgnoreCase("resetCheckList"))
      {
        this.adapter.resetCheck();
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("selectAll"));
    if (((Boolean)paramObject).booleanValue())
    {
      this.adapter.checkAll();
      return;
    }
    this.adapter.resetCheck();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.mypodcaster.MyPodcasterListView
 * JD-Core Version:    0.6.2
 */