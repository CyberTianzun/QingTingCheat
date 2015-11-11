package fm.qingting.qtradio.view.personalcenter.timer;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.ListView;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.adapter.ItemParam;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.alarm.ClockManager;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.Clock;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.SingleCheckAdapter;
import fm.qingting.qtradio.view.settingviews.SettingItem;
import fm.qingting.qtradio.view.settingviews.SettingItem.SettingType;
import fm.qingting.utils.PlayProcessSyncUtil;
import fm.qingting.utils.QTMSGManage;
import java.util.ArrayList;
import java.util.List;

public class TimerPickView extends ViewGroupViewImpl
  implements IEventHandler
{
  private static final String[] TIMER_IDS = { "ten", "twenty", "thirty", "sixty", "ninety", "twohour" };
  private static final int[] TIMER_LENGTHS = { 600, 1200, 1800, 3600, 5400, 7200 };
  private static final String[] TIMER_TIMES = { "10分钟", "20分钟", "30分钟", "60分钟", "90分钟", "120分钟" };
  private SingleCheckAdapter adapter;
  private IAdapterIViewFactory factory;
  private ListView mListView;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 1200, 720, 1200, 0, 0, ViewLayout.FILL);

  public TimerPickView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor());
    this.factory = new IAdapterIViewFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        return new TimerItemView(TimerPickView.this.getContext(), this.val$hash);
      }
    };
    this.adapter = new SingleCheckAdapter(new ArrayList(), this.factory);
    this.adapter.setEventHandler(this);
    this.mListView = new ListView(paramContext);
    this.mListView.setVerticalScrollBarEnabled(false);
    this.mListView.setVerticalFadingEdgeEnabled(false);
    this.mListView.setCacheColorHint(0);
    this.mListView.setDivider(null);
    this.mListView.setHeaderDividersEnabled(false);
    this.mListView.setSelector(17170445);
    this.mListView.setAdapter(this.adapter);
    addView(this.mListView);
  }

  private void addTimer(String paramString)
  {
    if (paramString.equalsIgnoreCase("cancel"))
    {
      ClockManager.getInstance().removeTimer();
      GlobalCfg.getInstance(getContext()).setQuitTime(9223372036854775807L);
      PlayerAgent.getInstance().stopQuitTimer();
      InfoManager.getInstance().setQuitAfterPlay(false);
      return;
    }
    if (paramString.equalsIgnoreCase("quitafterplay"))
    {
      Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      int k;
      int m;
      if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
      {
        k = ((ProgramNode)localNode).getCurrPlayStatus();
        if (k != 1)
          break label180;
        long l3 = System.currentTimeMillis() / 1000L;
        m = (int)(((ProgramNode)localNode).getAbsoluteEndTime() - l3);
      }
      while (true)
      {
        if (m > 0)
        {
          ClockManager.getInstance().addTimer(new Clock(2, m, true));
          long l2 = System.currentTimeMillis() / 1000L + m;
          GlobalCfg.getInstance(getContext()).setQuitTime(l2);
        }
        PlayerAgent.getInstance().startQuitTimer();
        InfoManager.getInstance().setQuitAfterPlay(true);
        return;
        label180: m = 0;
        if (k == 3)
        {
          int n = PlayProcessSyncUtil.getInstance().getCurrentPlayTime();
          m = ((ProgramNode)localNode).getDuration() - n;
        }
      }
    }
    int i = 0;
    if (i < TIMER_IDS.length)
      if (!TIMER_IDS[i].equalsIgnoreCase(paramString));
    for (int j = TIMER_LENGTHS[i]; ; j = 0)
    {
      ClockManager.getInstance().addTimer(new Clock(2, j, true));
      InfoManager.getInstance().setQuitAfterPlay(false);
      long l1 = System.currentTimeMillis() / 1000L + j;
      GlobalCfg.getInstance(getContext()).setQuitTime(l1);
      PlayerAgent.getInstance().startQuitTimer();
      QTMSGManage.getInstance().sendStatistcsMessage("timer_add");
      return;
      i++;
      break;
    }
  }

  private void initData()
  {
    int i = 0;
    ArrayList localArrayList = new ArrayList();
    for (int j = 0; j < TIMER_IDS.length; j++)
      localArrayList.add(new SettingItem(TIMER_TIMES[j], SettingItem.SettingType.check, TIMER_IDS[j]));
    localArrayList.add(new SettingItem("当前节目播放完关闭", SettingItem.SettingType.check, "quitafterplay"));
    localArrayList.add(new SettingItem("取消定时关闭", SettingItem.SettingType.check, "cancel"));
    this.adapter.setData(localArrayList);
    Clock localClock = ClockManager.getInstance().getTimer();
    if (localClock != null)
    {
      int m = localClock.getTime();
      if (i >= TIMER_LENGTHS.length)
        break label204;
      if (TIMER_LENGTHS[i] != m);
    }
    label204: for (int k = i; ; k = -1)
    {
      if ((k == -1) && (InfoManager.getInstance().getQuitAfterPlay()));
      for (k = 6; ; k = 7)
      {
        if (k == -1)
          k = 7;
        if (k > -1)
          this.adapter.checkIndex(k);
        return;
        i++;
        break;
      }
    }
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("itemCallback"))
    {
      ItemParam localItemParam = (ItemParam)paramObject2;
      if (localItemParam.type.equalsIgnoreCase("check"))
      {
        int i = localItemParam.position;
        this.adapter.checkIndex(i);
        addTimer((String)localItemParam.param);
        ControllerManager.getInstance().popLastController();
      }
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.standardLayout.layoutView(this.mListView);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.standardLayout.measureView(this.mListView);
    setMeasuredDimension(i, j);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
      initData();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.timer.TimerPickView
 * JD-Core Version:    0.6.2
 */