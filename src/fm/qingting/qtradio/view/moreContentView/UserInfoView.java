package fm.qingting.qtradio.view.moreContentView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ListView;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.alarm.ClockManager;
import fm.qingting.qtradio.alarm.ClockManager.IClockListener;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.IMAgent.IMEventListener;
import fm.qingting.qtradio.im.LatestMessages;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.Clock;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.IInfoUpdateEventListener;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.qtradio.wo.IHttpAsyncTaskListener;
import fm.qingting.qtradio.wo.WoApiRequest;
import fm.qingting.qtradio.wo.WoApiRequest.OnCompletedInitListener;
import fm.qingting.qtradio.wo.WoNetEventListener;
import java.util.ArrayList;
import java.util.List;

public class UserInfoView extends ViewGroupViewImpl
  implements RootNode.IInfoUpdateEventListener, InfoManager.ISubscribeEventListener, IMAgent.IMEventListener, ClockManager.IClockListener, IHttpAsyncTaskListener, WoApiRequest.OnCompletedInitListener
{
  private SectionAdapter adapter;
  private ISectionAdapterIViewFactory factory;
  private int mLatestMessageCnt = 0;
  private ListView mListView;
  private UserTitleView mTitleView;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public UserInfoView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor());
    this.factory = new ISectionAdapterIViewFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        default:
          return null;
        case 0:
          return new CustomSectionView(UserInfoView.this.getContext());
        case 1:
          return new UserinfoItemView(UserInfoView.this.getContext());
        case 2:
        }
        return new BannerItemView(UserInfoView.this.getContext());
      }
    };
    this.adapter = new SectionAdapter(new ArrayList(), this.factory)
    {
      public int getViewTypeCount()
      {
        return 3;
      }
    };
    this.mListView = new ListView(paramContext);
    this.mTitleView = new UserTitleView(paramContext);
    this.mListView.addHeaderView(this.mTitleView);
    this.mListView.setVerticalScrollBarEnabled(false);
    this.mListView.setVerticalFadingEdgeEnabled(false);
    this.mListView.setCacheColorHint(0);
    this.mListView.setDivider(null);
    this.mListView.setHeaderDividersEnabled(false);
    this.mListView.setSelector(new ColorDrawable(0));
    this.mListView.setAdapter(this.adapter);
    addView(this.mListView);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 0);
    InfoManager.getInstance().root().registerInfoUpdateListener(this, 11);
    InfoManager.getInstance().registerViewTime(this);
    InfoManager.getInstance().registerSubscribeEventListener(this, "RUIU");
    IMAgent.getInstance().registerIMEventListener(this, "RECV_LIST_MSG");
    IMAgent.getInstance().registerIMEventListener(this, "RECV_SINGLE_MSG");
    ClockManager.getInstance().addListener(this);
    LatestMessages.resetBaseTime();
    boolean bool1 = WoNetEventListener.isChinaUnicom(getContext());
    boolean bool2 = false;
    if (bool1)
    {
      if (!WoApiRequest.hasOpen())
        break label285;
      generateSectionList(true);
      bool2 = true;
    }
    while (true)
    {
      generateSectionList(bool2);
      return;
      label285: WoApiRequest.addOnCompletedIniteListener(this);
      boolean bool3 = InfoManager.getInstance().enableWoQt();
      bool2 = false;
      if (bool3)
      {
        String str = WoApiRequest.getBindCallNumber();
        if ((str == null) || (str.equals("")))
        {
          str = WoApiRequest.getCacheCallNumber();
          if ((str == null) || (str.equals("")))
            str = WoApiRequest.getSimcardCallNumber();
        }
        WoApiRequest.qryUserLocation(str, this, "qryUserLocation");
        bool2 = false;
      }
    }
  }

  private void generateSectionList(boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new SectionItem(2, null));
    localArrayList.add(new SectionItem(1, Integer.valueOf(0)));
    localArrayList.add(new SectionItem(1, Integer.valueOf(10)));
    localArrayList.add(new SectionItem(1, Integer.valueOf(1)));
    localArrayList.add(new SectionItem(1, Integer.valueOf(2)));
    localArrayList.add(new SectionItem(0, null));
    if ((InfoManager.getInstance().getLstGameBean() != null) && (InfoManager.getInstance().getLstGameBean().size() > 0))
    {
      localArrayList.add(new SectionItem(1, Integer.valueOf(11)));
      localArrayList.add(new SectionItem(0, null));
    }
    if (paramBoolean)
      localArrayList.add(new SectionItem(1, Integer.valueOf(8)));
    localArrayList.add(new SectionItem(1, Integer.valueOf(4)));
    localArrayList.add(new SectionItem(1, Integer.valueOf(3)));
    localArrayList.add(new SectionItem(0, null));
    localArrayList.add(new SectionItem(1, Integer.valueOf(5)));
    localArrayList.add(new SectionItem(1, Integer.valueOf(6)));
    localArrayList.add(new SectionItem(0, null));
    localArrayList.add(new SectionItem(1, Integer.valueOf(7)));
    localArrayList.add(new SectionItem(0, null));
    this.adapter.setData(localArrayList);
  }

  private void initData()
  {
    this.mTitleView.update("setUser", InfoManager.getInstance().getUserProfile());
    LatestMessages.loadUnreadMsgs(false);
  }

  private void invalidateCertainView(int paramInt)
  {
    for (int i = 0; i < this.adapter.getCount(); i++)
    {
      IView localIView = (IView)this.mListView.getChildAt(i);
      if (localIView != null)
      {
        Object localObject = localIView.getValue("type", null);
        if ((localObject != null) && (((Integer)localObject).intValue() == paramInt))
          localIView.getView().invalidate();
      }
    }
  }

  private void invalidateVisibleChildren()
  {
    for (int i = 0; i < this.adapter.getCount(); i++)
    {
      View localView = this.mListView.getChildAt(i);
      if (localView != null)
        localView.invalidate();
    }
  }

  public void close(boolean paramBoolean)
  {
    IMAgent.getInstance().unRegisterIMEventListener("RECV_LAST_MSG_ACTION", this);
    IMAgent.getInstance().unRegisterIMEventListener("RECV_LIST_MSG", this);
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    InfoManager.getInstance().unregisterViewTime(this);
    InfoManager.getInstance().root().unRegisterInfoUpdateListener(0, this);
    InfoManager.getInstance().root().unRegisterInfoUpdateListener(11, this);
    super.close(paramBoolean);
  }

  public void onClockTime(int paramInt)
  {
    if (ClockManager.getInstance().getTimerAvailable())
      invalidateCertainView(5);
  }

  public void onCompletedInit()
  {
    if (WoApiRequest.hasOpen())
      generateSectionList(true);
  }

  // ERROR //
  public void onGetResult(Object paramObject1, Object paramObject2)
  {
    // Byte code:
    //   0: aload_1
    //   1: checkcast 206	java/lang/String
    //   4: astore 4
    //   6: aload 4
    //   8: ifnull +92 -> 100
    //   11: aload 4
    //   13: ldc 218
    //   15: invokevirtual 334	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   18: istore 5
    //   20: iload 5
    //   22: ifeq +78 -> 100
    //   25: aload_2
    //   26: checkcast 206	java/lang/String
    //   29: invokestatic 338	fm/qingting/qtradio/wo/WoApiRequest:parseJsonString	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   32: astore 7
    //   34: aload 7
    //   36: ldc_w 340
    //   39: invokevirtual 346	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   42: astore 9
    //   44: aload 9
    //   46: invokestatic 338	fm/qingting/qtradio/wo/WoApiRequest:parseJsonString	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   49: astore 10
    //   51: aload 10
    //   53: ifnull +47 -> 100
    //   56: aload 10
    //   58: ldc_w 348
    //   61: invokevirtual 346	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   64: ldc_w 350
    //   67: invokevirtual 334	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   70: ifeq +30 -> 100
    //   73: aload 10
    //   75: ldc_w 352
    //   78: invokevirtual 346	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   81: invokestatic 355	fm/qingting/qtradio/wo/WoNetEventListener:hasOpenUniCom	(Ljava/lang/String;)Z
    //   84: ifeq +16 -> 100
    //   87: aload_0
    //   88: invokevirtual 176	fm/qingting/qtradio/view/moreContentView/UserInfoView:getContext	()Landroid/content/Context;
    //   91: invokestatic 358	fm/qingting/qtradio/wo/WoApiRequest:init	(Landroid/content/Context;)Z
    //   94: pop
    //   95: aload_0
    //   96: iconst_1
    //   97: invokespecial 191	fm/qingting/qtradio/view/moreContentView/UserInfoView:generateSectionList	(Z)V
    //   100: return
    //   101: astore 8
    //   103: aload 8
    //   105: invokevirtual 361	java/lang/Exception:printStackTrace	()V
    //   108: return
    //   109: astore 11
    //   111: aload 11
    //   113: invokevirtual 361	java/lang/Exception:printStackTrace	()V
    //   116: return
    //   117: astore 6
    //   119: return
    //   120: astore_3
    //   121: return
    //
    // Exception table:
    //   from	to	target	type
    //   34	44	101	java/lang/Exception
    //   56	100	109	java/lang/Exception
    //   25	34	117	java/lang/Exception
    //   0	6	120	java/lang/Exception
    //   11	20	120	java/lang/Exception
    //   44	51	120	java/lang/Exception
    //   103	108	120	java/lang/Exception
    //   111	116	120	java/lang/Exception
  }

  public boolean onIMEvent(String paramString, IMMessage paramIMMessage)
  {
    if (paramString.equalsIgnoreCase("RECV_SINGLE_MSG"))
    {
      if (paramIMMessage.chatType != 1)
        break label32;
      LatestMessages.putMessage(paramIMMessage.mFromGroupId, paramIMMessage);
    }
    while (true)
    {
      invalidateCertainView(4);
      return false;
      label32: String str = InfoManager.getInstance().getUserProfile().getUserKey();
      if ((paramIMMessage.mToUserId != null) && (paramIMMessage.mToUserId.length() > 0) && (!str.equalsIgnoreCase(paramIMMessage.mToUserId)))
        LatestMessages.putMessage(paramIMMessage.mToUserId, paramIMMessage);
      else if ((paramIMMessage.mFromID != null) && (paramIMMessage.mFromID.length() > 0))
        LatestMessages.putMessage(paramIMMessage.mFromID, paramIMMessage);
    }
  }

  public boolean onIMListMsg(String paramString, List<IMMessage> paramList)
  {
    return false;
  }

  public void onInfoUpdated(int paramInt)
  {
    if (paramInt == 0)
      invalidateCertainView(0);
    while ((paramInt != 11) || (this.adapter.getItem(0).type != 2))
      return;
    this.adapter.getData().remove(0);
    this.adapter.notifyDataSetChanged();
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mListView.layout(0, 0, this.standardLayout.width, this.standardLayout.height);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.mListView.measure(this.standardLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.standardLayout.height, 1073741824));
    setMeasuredDimension(i, j);
  }

  public void onNotification(String paramString)
  {
    if (paramString.equalsIgnoreCase("viewTimeUpdated"))
      invalidateCertainView(0);
    while (!paramString.equalsIgnoreCase("RUIU"))
      return;
    this.mTitleView.update("setUser", InfoManager.getInstance().getUserProfile());
    LatestMessages.loadUnreadMsgs(false);
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void onTime(Clock paramClock)
  {
  }

  public void onTimeStart(Clock paramClock)
  {
    if ((paramClock.type == 2) && (ClockManager.getInstance().getTimerAvailable()))
      invalidateCertainView(5);
  }

  public void onTimeStop(Clock paramClock)
  {
  }

  public void onTimerRemoved()
  {
    invalidateCertainView(5);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      initData();
    while (!paramString.equalsIgnoreCase("refreshView"))
      return;
    invalidateVisibleChildren();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.moreContentView.UserInfoView
 * JD-Core Version:    0.6.2
 */