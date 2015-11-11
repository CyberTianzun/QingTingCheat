package fm.qingting.qtradio.fm;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.qtradio.QTRadioService;
import fm.qingting.qtradio.fmdriver.FMManager;
import fm.qingting.qtradio.fmdriver.FMcontrol;
import fm.qingting.qtradio.fmdriver.IFMControlEventListener;
import fm.qingting.qtradio.helper.ChannelHelper;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.manager.INETEventListener;
import fm.qingting.qtradio.manager.NetWorkManage;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.InfoManager.AUDIO_QUALITY_MODE;
import fm.qingting.qtradio.model.InfoManager.DataExceptionStatus;
import fm.qingting.qtradio.model.InfoManager.ISubscribeEventListener;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.OnDemandProgramNode;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.PlayedMetaData;
import fm.qingting.qtradio.model.PlayedMetaInfo;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RadioChannelNode;
import fm.qingting.qtradio.model.RecommendCategoryNode;
import fm.qingting.qtradio.model.RecommendItemNode;
import fm.qingting.qtradio.model.ReserveInfoNode;
import fm.qingting.qtradio.model.RingToneNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.PlayMode;
import fm.qingting.qtradio.model.SharedCfg;
import fm.qingting.qtradio.remotecontrol.RemoteControl;
import fm.qingting.utils.PlaybackMonitor;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PlayerAgent
  implements IFMControlEventListener, INETEventListener, InfoManager.ISubscribeEventListener
{
  private static final String Tag = "PlayerAgent";
  private static PlayerAgent instance;
  private long FromPlayToPlaying = 0L;
  private final int MIN_AD_PROGRAM_DURATION = 300;
  private final int UPDATE_PLAYSTATUS = 1;
  private Context _context;
  private long beginFromPlay = 0L;
  private long beginPlay = 0L;
  private ServiceConnection conn = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      PlayerAgent.this.setServiceControl(IServiceControl.Stub.asInterface(paramAnonymousIBinder));
      if (PlayerAgent.this.mEventHandler != null)
        PlayerAgent.this.mEventHandler.onEvent(null, "serviceConnected", null);
      PlaybackMonitor.getInstance();
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      PlayerAgent.this.releaseServiceControl();
    }
  };
  private int currPlayState = 30583;
  private final int defaultBufferTime = 2000;
  private Runnable doSeek = new Runnable()
  {
    public void run()
    {
      try
      {
        if (PlayerAgent.this.iService == null)
          return;
        PlayerAgent.this.iService.seek(PlayerAgent.this.seekTime);
        if (PlayerAgent.this.hasAutoSeek)
        {
          InfoManager.getInstance().root().setInfoUpdate(1);
          PlayerAgent.this.clearSeekState();
          return;
        }
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
      }
    }
  };
  private boolean firstTips = false;
  private boolean hasAutoSeek = false;
  private boolean hasPlayed = false;
  private boolean hasRecoveredFromCrash = false;
  private IServiceControl iService = null;
  private Set<WeakReference<IMediaEventListener>> listeners = new HashSet();
  private boolean liveStream = true;
  private boolean mAutoPlay = false;
  private boolean mConnected = false;
  private IEventHandler mEventHandler;
  private String mLastNetType = "";
  private int mLoadedAndPlayCid = 0;
  private int mLoadedAndPlayPid = 0;
  private PlayControllReceiver mPlayControllReceiver;
  private int mPlaySource = 0;
  private String mPlayingSourceUrls = "";
  private int mShouldPlay = 0;
  private Map<String, Integer> mapBufferCnt = new HashMap();
  private int playDuration = 0;
  private boolean preemptiveFlag = false;
  private WeakReference<IMediaEventListener> preemptiveListener = null;
  private String preloaded_source;
  private PlayStatus ps = new PlayStatus(0, 2147483647L, 0L, 2000L, 0L);
  private QTReceiver recevier;
  private boolean recvDoPlay = false;
  private Handler seekHandler = new Handler();
  private int seekTime = 0;
  private String source;

  private PlayerAgent()
  {
    FMcontrol.getInstance().addListener(this);
    NetWorkManage.getInstance().addListener(this);
  }

  private void _addPlaySource()
  {
    try
    {
      if (this.iService != null)
        this.iService.addPlaySource(this.mPlaySource);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void _pause()
  {
    if (this.iService != null);
    try
    {
      this.iService.pause();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  private boolean _play(String paramString)
  {
    if ((this.iService == null) || ((paramString == null) && (this.source == null)));
    while ((this.source != null) && (this.source.equalsIgnoreCase(paramString)) && (this.currPlayState == 4096))
      return false;
    long l1 = queryPosition();
    long l2 = queryDuration();
    if (paramString != null)
      this.source = paramString;
    if ((this.source != null) && (!this.source.equalsIgnoreCase(this.preloaded_source)))
      setSource(this.source);
    dispatchPlayState(4101);
    autoReserve();
    this.beginPlay = (System.currentTimeMillis() / 1000L);
    this.beginFromPlay = System.currentTimeMillis();
    this.recvDoPlay = false;
    this.mConnected = false;
    try
    {
      if (((InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.PLAY_FRONT_ADVERTISEMENT) || (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.PLAY_END_ADVERTISEMENT)) && (InfoManager.getInstance().getTaobaoAudioAdv()))
        TaobaoAgent.getInstance().stopAD();
      this.hasPlayed = true;
      this.iService.play();
      PlayedMetaInfo.getInstance().addPlayedMeta(InfoManager.getInstance().root().getCurrentPlayingNode(), (int)l1, (int)l2);
      if (!this.liveStream)
      {
        this.playDuration = queryDuration();
        return true;
      }
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
      {
        localRemoteException.printStackTrace();
        continue;
        this.playDuration = 0;
      }
    }
  }

  private void _playCache(String paramString)
  {
    try
    {
      int i = queryPosition();
      setSource(paramString);
      this.iService.play();
      this.iService.seek(i);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void _resume()
  {
    if (this.iService != null)
    {
      try
      {
        dispatchPlayState(4101);
        this.beginFromPlay = System.currentTimeMillis();
        if (!InfoManager.getInstance().getShowMobileNetworkSetting())
          break label105;
        if ((this.source.startsWith("file:")) || (InfoManager.getInstance().hasWifi()))
        {
          this.iService.resume();
          return;
        }
        if (InfoManager.getInstance().useMobileNetwork())
        {
          this.iService.resume();
          EventDispacthManager.getInstance().dispatchAction("showToast", "播放5分钟需要消耗1M流量，请注意使用");
          return;
        }
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
        return;
      }
      this.mShouldPlay = 2;
      return;
      label105: this.iService.resume();
    }
  }

  private void _setPlayingSourceList()
  {
    if ((this.mPlayingSourceUrls == null) || (this.mPlayingSourceUrls.equalsIgnoreCase("")));
    while (this.iService == null)
      return;
    try
    {
      this.iService.setPlayingSourceList(this.mPlayingSourceUrls);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  private void _stop()
  {
    if (this.iService != null);
    try
    {
      this.iService.stop();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  private void autoSeek(int paramInt)
  {
    PlayedMetaData localPlayedMetaData = PlayedMetaInfo.getInstance().getPlayedMeta(paramInt);
    if (localPlayedMetaData != null)
    {
      this.hasAutoSeek = true;
      asyncSeek(localPlayedMetaData.position);
    }
  }

  private void buildPlayingSourceListByNode(Node paramNode)
  {
  }

  private void clearSeekState()
  {
    this.hasAutoSeek = false;
  }

  private void dispatchMediaEvent(int paramInt, Object paramObject)
  {
    if ((this.preemptiveFlag) && (this.preemptiveListener != null))
    {
      IMediaEventListener localIMediaEventListener2 = (IMediaEventListener)this.preemptiveListener.get();
      if (localIMediaEventListener2 != null)
        localIMediaEventListener2.onPlayStatusUpdated((PlayStatus)paramObject);
    }
    label66: IMediaEventListener localIMediaEventListener1;
    do
    {
      return;
      HashSet localHashSet = new HashSet();
      localHashSet.addAll(this.listeners);
      Iterator localIterator = localHashSet.iterator();
      do
        while (true)
        {
          if (!localIterator.hasNext())
            break label302;
          WeakReference localWeakReference = (WeakReference)localIterator.next();
          localIMediaEventListener1 = (IMediaEventListener)localWeakReference.get();
          if (localIMediaEventListener1 != null)
            break;
          this.listeners.remove(localWeakReference);
        }
      while (((paramInt != 1) || (paramObject == null) || (this.currPlayState == 0)) && (((PlayStatus)paramObject).state != 4101));
      if (((PlayStatus)paramObject).state == 4116)
      {
        this.recvDoPlay = true;
        return;
      }
      if (((PlayStatus)paramObject).state != 4096)
        break label304;
    }
    while (!this.recvDoPlay);
    this.mConnected = true;
    SystemPlayer.getInstance().stop();
    if (this.beginFromPlay > 0L)
    {
      this.FromPlayToPlaying = (System.currentTimeMillis() - this.beginFromPlay);
      if ((this.FromPlayToPlaying > 0L) && (this.FromPlayToPlaying < 1000000L))
      {
        String str2 = QTLogger.getInstance().buildListeneringQualityLog(InfoManager.getInstance().root().getCurrentPlayingNode(), this.FromPlayToPlaying / 1000.0D, 0, queryPlayingUrl());
        if (str2 != null)
          LogModule.getInstance().send("PlayExperience", str2);
      }
      this.beginFromPlay = 0L;
    }
    while (true)
    {
      localIMediaEventListener1.onPlayStatusUpdated((PlayStatus)paramObject);
      break label66;
      label302: break;
      label304: if ((paramInt == 1) && (paramObject != null) && (((PlayStatus)paramObject).state == 4098))
      {
        String str1 = queryPlayingUrl();
        if (str1 != null)
          if (this.mapBufferCnt.containsKey(str1))
          {
            int i = 1 + ((Integer)this.mapBufferCnt.get(str1)).intValue();
            this.mapBufferCnt.put(str1, Integer.valueOf(i));
          }
          else
          {
            this.mapBufferCnt.put(str1, Integer.valueOf(1));
          }
      }
    }
  }

  private void dispatchMediaEventInFM(int paramInt, Object paramObject)
  {
    HashSet localHashSet = new HashSet();
    localHashSet.addAll(this.listeners);
    Iterator localIterator = localHashSet.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      IMediaEventListener localIMediaEventListener = (IMediaEventListener)localWeakReference.get();
      if (localIMediaEventListener == null)
        this.listeners.remove(localWeakReference);
      else
        localIMediaEventListener.onPlayStatusUpdated((PlayStatus)paramObject);
    }
  }

  private void dispatchPlayState(int paramInt)
  {
    this.ps.state = paramInt;
    this.ps.bufferLength = 0L;
    this.ps.bufferTime = 2000L;
    this.ps.duration = 0L;
    this.ps.time = 0L;
    dispatchMediaEvent(1, this.ps);
  }

  private void exit()
  {
    if (this.iService == null)
      return;
    try
    {
      this.iService.exit();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  public static PlayerAgent getInstance()
  {
    try
    {
      if (instance == null)
        instance = new PlayerAgent();
      PlayerAgent localPlayerAgent = instance;
      return localPlayerAgent;
    }
    finally
    {
    }
  }

  private boolean inPlayingSourceList(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")) || (this.mPlayingSourceUrls == null) || (this.mPlayingSourceUrls.equalsIgnoreCase("")));
    while (!this.mPlayingSourceUrls.contains(paramString))
      return false;
    return true;
  }

  private void initPlayControll()
  {
    this.mPlayControllReceiver = new PlayControllReceiver();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.UPDATE_PLAY_INFO_QT");
    localIntentFilter.addAction("fm.qingting.qtradio.CAR_PAUSE");
    localIntentFilter.addAction("fm.qingting.qtradio.CAR_PLAY");
    localIntentFilter.addAction("fm.qingting.qtradio.CAR_PLAY_PRE");
    localIntentFilter.addAction("fm.qingting.qtradio.CAR_PLAY_NEXT");
    localIntentFilter.addAction("fm.qingting.qtradio.CAR_PLAY_PRE_CATEGORY");
    localIntentFilter.addAction("fm.qingting.qtradio.CAR_PLAY_NEXT_CATEGORY");
    localIntentFilter.setPriority(2);
    this._context.registerReceiver(this.mPlayControllReceiver, localIntentFilter);
  }

  private static void log(String paramString)
  {
    log("playerAgent", paramString);
  }

  private static void log(String paramString1, String paramString2)
  {
    Log.e(paramString1, paramString2);
  }

  private boolean playFrontAudioAdv(Node paramNode)
  {
    if (paramNode == null)
      return false;
    if (paramNode.nodeName.equalsIgnoreCase("program"))
    {
      if (((ProgramNode)paramNode).channelType == 0)
        return false;
      if (InfoManager.getInstance().enableAudioAdv((ProgramNode)paramNode))
      {
        int i = ((ProgramNode)paramNode).getCategoryId();
        String str = InfoManager.getInstance().getFrontAudioAdv(i, ((ProgramNode)paramNode).channelId);
        if (((ProgramNode)paramNode).duration < 300.0D)
          return false;
        if (getInstance().playSource(str))
        {
          InfoManager.getInstance().root().setPlayMode(RootNode.PlayMode.PLAY_FRONT_ADVERTISEMENT);
          return true;
        }
        if ((InfoManager.getInstance().enableTaobaoAudioAdv()) && (TaobaoAgent.getInstance().playAD(false)))
          return true;
      }
      else if (InfoManager.getInstance().enableTaobaoAudioAdv())
      {
        if (((ProgramNode)paramNode).duration < 300.0D)
          return false;
        if (TaobaoAgent.getInstance().playAD(false))
          return true;
      }
    }
    return false;
  }

  private String queryPlayingUrl()
  {
    if (this.iService == null)
      return null;
    try
    {
      String str = this.iService.queryPlayingUrl();
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private boolean quickIsSameSource(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null));
    while (true)
    {
      return false;
      for (int i = 0; (i < paramString1.length()) && (i < paramString2.length()) && (paramString1.charAt(i) == paramString2.charAt(i)); i++)
        if (paramString1.charAt(i) == '?')
          return true;
    }
  }

  private void releasePlayControll()
  {
    try
    {
      if (this.mPlayControllReceiver != null)
      {
        this._context.unregisterReceiver(this.mPlayControllReceiver);
        this.mPlayControllReceiver = null;
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void releaseServiceControl()
  {
    this.iService = null;
  }

  private boolean requestAudioFocus()
  {
    return true;
  }

  private void setLoadedAndPlayId(int paramInt1, int paramInt2)
  {
    this.mLoadedAndPlayPid = paramInt2;
    this.mLoadedAndPlayCid = paramInt1;
  }

  private boolean setPlayingSourceList(Node paramNode)
  {
    if (paramNode == null)
      return false;
    if (paramNode.nodeName.equalsIgnoreCase("ondemandprogram"))
    {
      if (inPlayingSourceList(((OnDemandProgramNode)paramNode).getSourceUrls()))
        return false;
      buildPlayingSourceListByNode(paramNode);
      _setPlayingSourceList();
      return true;
    }
    return false;
  }

  private void setSource(String paramString)
  {
    if ((this.iService == null) || (paramString == null));
    while ((this.preloaded_source != null) && (this.preloaded_source.equalsIgnoreCase(paramString)))
      return;
    try
    {
      _stop();
      this.preloaded_source = paramString;
      this.iService.setSource(this.preloaded_source);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  public void addABTestCategory(int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      if (this.iService != null)
        this.iService.addABTestCategory(paramInt1, paramInt2, paramInt3);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void addABTestProgram(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    try
    {
      if (this.iService != null)
        this.iService.addABTestProgram(paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void addMediaEventListener(IMediaEventListener paramIMediaEventListener)
  {
    removeMediaEventListener(paramIMediaEventListener);
    this.listeners.add(new WeakReference(paramIMediaEventListener));
  }

  public void addPlaySource(int paramInt)
  {
    this.mPlaySource = paramInt;
  }

  public void addPreemptiveListener(IMediaEventListener paramIMediaEventListener)
  {
    this.preemptiveFlag = true;
    this.preemptiveListener = new WeakReference(paramIMediaEventListener);
  }

  public void asyncSeek(int paramInt)
  {
    if ((this.iService == null) || (paramInt < 5))
      return;
    this.seekTime = (paramInt - 5);
    this.seekHandler.removeCallbacks(this.doSeek);
    if (InfoManager.getInstance().hasWifi())
    {
      this.seekHandler.postDelayed(this.doSeek, 1000L);
      return;
    }
    this.seekHandler.postDelayed(this.doSeek, 2000L);
  }

  public void autoReserve()
  {
    if ((this.beginPlay != 0L) && (InfoManager.getInstance().getAutoReserve()) && (System.currentTimeMillis() / 1000L - this.beginPlay > InfoManager.getInstance().getAutoReserveMinDuration()))
    {
      Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")) && (!InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.isExisted(localNode)))
        InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.autoReserveNextProgram((ProgramNode)localNode);
    }
  }

  public void delPreemptiveListener()
  {
    this.preemptiveFlag = false;
    this.preemptiveListener = null;
  }

  public void dispatchPlayStateInFM(int paramInt)
  {
    this.ps.state = paramInt;
    this.ps.bufferLength = 0L;
    this.ps.bufferTime = 2000L;
    this.ps.duration = 0L;
    this.ps.time = 0L;
    dispatchMediaEventInFM(1, this.ps);
  }

  public void forceSetSource()
  {
    if ((this.iService == null) || (this.source == null));
    while (true)
    {
      return;
      try
      {
        Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
        boolean bool = isPlaying();
        stop();
        this.preloaded_source = this.source;
        setSource(this.preloaded_source);
        if (bool)
        {
          play(localNode);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public int getCurrentPlayStatus()
  {
    return this.currPlayState;
  }

  public String getHttpProxy()
  {
    try
    {
      if (this.iService != null)
        return this.iService.getHttpProxy();
      return "";
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return "";
  }

  public boolean getIsViaWoProxy()
  {
    try
    {
      IServiceControl localIServiceControl = this.iService;
      boolean bool1 = false;
      if (localIServiceControl != null)
      {
        boolean bool2 = this.iService.getIsViaWoProxy();
        bool1 = bool2;
      }
      return bool1;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return false;
  }

  public int getProgramPlayInfo(int paramInt)
  {
    if (this.iService == null)
      return 0;
    try
    {
      int i = this.iService.queryPlayInfo(paramInt);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return 0;
  }

  public String getSource()
  {
    if (this.iService == null)
      return null;
    try
    {
      String str = this.iService.getSource();
      return str;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return null;
  }

  public boolean hasConnected()
  {
    return this.mConnected;
  }

  public void init(Context paramContext)
  {
    this._context = paramContext;
    paramContext.bindService(new Intent(this._context, QTRadioService.class), this.conn, 1);
    this.recevier = new QTReceiver();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.UPDATE_PLAY_STATUS");
    this._context.registerReceiver(this.recevier, localIntentFilter);
    initPlayControll();
    this.preloaded_source = null;
    this.source = null;
    this.mLastNetType = NetWorkManage.getInstance().getNetWorkType();
  }

  public boolean isLiveStream()
  {
    return this.liveStream;
  }

  public boolean isPause()
  {
    return this.currPlayState == 1;
  }

  public boolean isPlaying()
  {
    return this.currPlayState == 4096;
  }

  public boolean isPreemptive()
  {
    return this.preemptiveFlag;
  }

  public void onHeadsetPlugged()
  {
  }

  public void onHeadsetUnplugged()
  {
  }

  public void onMobilesState(boolean paramBoolean)
  {
    if ((InfoManager.getInstance().root().isOpenFm()) && (!paramBoolean))
      if (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.FMPLAY)
      {
        localNode1 = InfoManager.getInstance().root().getCurrentPlayingNode();
        if ((localNode1 == null) || (!localNode1.nodeName.equalsIgnoreCase("radiochannel")))
          break label78;
        FMManager.getInstance().tune(Integer.valueOf(((RadioChannelNode)localNode1).freq).intValue());
      }
    label78: 
    while ((!paramBoolean) || (InfoManager.getInstance().root().currentPlayMode() != RootNode.PlayMode.FMPLAY))
    {
      Node localNode2;
      do
      {
        Node localNode1;
        do
          return;
        while (localNode1 == null);
        localNode2 = localNode1.parent;
      }
      while ((localNode2 == null) || (!localNode2.nodeName.equalsIgnoreCase("radiochannel")));
      FMManager.getInstance().tune(Integer.valueOf(((RadioChannelNode)localNode2).freq).intValue());
      return;
    }
    FMManager.getInstance().turnOff();
  }

  public void onNetChanged(String paramString)
  {
    if (paramString == null);
    Node localNode;
    do
    {
      do
      {
        do
          return;
        while ((!InfoManager.getInstance().getMutiRate()) || (paramString.equalsIgnoreCase("nonet")) || (paramString.equalsIgnoreCase(this.mLastNetType)));
        this.mLastNetType = paramString;
      }
      while ((InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.ALARMPLAY) || (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.ALARM_PLAY_ONLINE) || (InfoManager.getInstance().getAudioQualitySetting() != InfoManager.AUDIO_QUALITY_MODE.SMART.ordinal()) || (!isPlaying()) || (!InfoManager.getInstance().hasConnectedNetwork()));
      localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    }
    while ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")) && (((ProgramNode)localNode).isDownloadProgram()));
    getInstance().stop();
    getInstance().play(localNode);
    if (InfoManager.getInstance().hasWifi())
    {
      Toast.makeText(this._context, "您正处于wifi网络，自动切换至高音质模式", 1).show();
      return;
    }
    Toast.makeText(this._context, "您正处于移动网络，自动切换至省流量模式", 1).show();
  }

  public void onNotification(String paramString)
  {
    if ((paramString.equalsIgnoreCase("RVPI")) && (this.mLoadedAndPlayCid != 0) && (this.mLoadedAndPlayPid != 0))
    {
      ChannelNode localChannelNode = ChannelHelper.getInstance().getChannel(this.mLoadedAndPlayCid, 1);
      if ((localChannelNode != null) && (!localChannelNode.hasEmptyProgramSchedule()))
      {
        ProgramNode localProgramNode = localChannelNode.getProgramNode(this.mLoadedAndPlayPid);
        if (localProgramNode != null)
          play(localProgramNode);
      }
    }
  }

  public void onRecvDataException(String paramString, InfoManager.DataExceptionStatus paramDataExceptionStatus)
  {
  }

  public void play()
  {
    try
    {
      if (this.mShouldPlay == 1)
      {
        this.hasPlayed = true;
        this.iService.play();
        return;
      }
      if (this.mShouldPlay == 2)
      {
        this.iService.resume();
        return;
      }
    }
    catch (Exception localException)
    {
    }
  }

  public void play(Node paramNode)
  {
    if (paramNode == null);
    ChannelNode localChannelNode2;
    List localList;
    do
    {
      return;
      requestAudioFocus();
      stopFM();
      setLoadedAndPlayId(0, 0);
      if (!paramNode.nodeName.equalsIgnoreCase("channel"))
        break label400;
      localChannelNode2 = (ChannelNode)paramNode;
      if (localChannelNode2.channelType != 1)
        break;
      localList = localChannelNode2.getAllLstProgramNode();
    }
    while ((localList == null) || (localList.size() <= 0));
    play((Node)localList.get(0));
    return;
    InfoManager.getInstance().root().setPlayingChannelNode(paramNode);
    String str2 = localChannelNode2.getSourceUrl();
    if ((str2 != null) && (!str2.equalsIgnoreCase("")))
    {
      ProgramNode localProgramNode3 = localChannelNode2.getProgramNodeByTime(System.currentTimeMillis());
      if ((localProgramNode3 != null) && (localProgramNode3.getCurrPlayStatus() == 1))
        str2 = localProgramNode3.getSourceUrl();
      if (!playFrontAudioAdv(localProgramNode3))
      {
        InfoManager.getInstance().root().setPlayMode();
        _play(str2);
      }
      this.liveStream = true;
      if (localProgramNode3 != null)
      {
        InfoManager.getInstance().root().setPlayingNode(localProgramNode3);
        RemoteControl.getInstance().updateProgramInfo(this._context, localChannelNode2, localProgramNode3);
      }
    }
    while (true)
    {
      this.currPlayState = 4096;
      InfoManager.getInstance().runSellApps();
      return;
      InfoManager.getInstance().root().setPlayingNode(localChannelNode2);
      continue;
      if (this.hasRecoveredFromCrash)
        this.hasRecoveredFromCrash = false;
      ProgramNode localProgramNode2 = localChannelNode2.getProgramNodeByTime(System.currentTimeMillis());
      if (localProgramNode2 != null)
      {
        String str3;
        if (localProgramNode2.getCurrPlayStatus() == 3)
        {
          str3 = localProgramNode2.getSourceUrl();
          label280: if ((str3 == null) || (str3.equalsIgnoreCase("")))
            break label390;
          if (!playFrontAudioAdv(localProgramNode2))
          {
            InfoManager.getInstance().root().setPlayMode();
            if ((_play(str3)) && (localProgramNode2.getCurrPlayStatus() == 3))
              autoSeek(localProgramNode2.id);
          }
          if (localProgramNode2.getCurrPlayStatus() != 3)
            break label392;
        }
        label390: label392: for (this.liveStream = false; ; this.liveStream = true)
        {
          InfoManager.getInstance().root().setPlayingNode(localProgramNode2);
          RemoteControl.getInstance().updateProgramInfo(this._context, localChannelNode2, localProgramNode2);
          break;
          str3 = localProgramNode2.getSourceUrl();
          break label280;
          break;
        }
        label400: if (paramNode.nodeName.equalsIgnoreCase("program"))
        {
          ProgramNode localProgramNode1 = (ProgramNode)paramNode;
          if (localProgramNode1.channelType == 1)
            InfoManager.getInstance().updateViewTime(localProgramNode1.channelId + "", localProgramNode1.getUpdateTime());
          ChannelNode localChannelNode1 = ChannelHelper.getInstance().getChannel(localProgramNode1);
          if (localChannelNode1 != null)
            InfoManager.getInstance().root().setPlayingChannelNode(localChannelNode1);
          String str1 = "";
          if (localProgramNode1.getCurrPlayStatus() == 3)
          {
            if (!localProgramNode1.isDownloadProgram())
              str1 = InfoManager.getInstance().root().getLocalProgramSource(localProgramNode1);
            if ((str1 == null) || (str1.equalsIgnoreCase("")))
            {
              str1 = PlayCacheAgent.getInstance().getCache(localProgramNode1);
              if (str1 == null);
            }
            else
            {
              label543: if ((str1 == null) || (str1.equalsIgnoreCase("")))
                break label690;
              if ((this.currPlayState == 1) && ((this.source == null) || (this.source.equalsIgnoreCase(str1))))
                break label692;
              if (!playFrontAudioAdv(localProgramNode1))
              {
                InfoManager.getInstance().root().setPlayMode();
                if ((_play(str1)) && (localProgramNode1.getCurrPlayStatus() == 3))
                  autoSeek(localProgramNode1.id);
              }
              label627: if (localProgramNode1.getCurrPlayStatus() != 3)
                break label699;
            }
          }
          label690: label692: label699: for (this.liveStream = false; ; this.liveStream = true)
          {
            InfoManager.getInstance().root().setPlayingNode(localProgramNode1);
            RemoteControl.getInstance().updateProgramInfo(this._context, localChannelNode1, localProgramNode1);
            break;
            PlayCacheAgent.getInstance().cacheNode(localProgramNode1);
            str1 = localProgramNode1.getSourceUrl();
            break label543;
            str1 = localProgramNode1.getSourceUrl();
            break label543;
            break;
            _resume();
            break label627;
          }
        }
        if (paramNode.nodeName.equalsIgnoreCase("ringtone"))
        {
          playRingTone(paramNode);
          InfoManager.getInstance().root().setPlayMode(RootNode.PlayMode.ALARM_PLAY_ONLINE);
        }
      }
    }
  }

  public void play(Node paramNode, boolean paramBoolean)
  {
    if (paramNode == null);
    ChannelNode localChannelNode2;
    do
    {
      return;
      requestAudioFocus();
      stopFM();
      if (!paramNode.nodeName.equalsIgnoreCase("channel"))
        break;
      localChannelNode2 = (ChannelNode)paramNode;
    }
    while (localChannelNode2.channelType == 1);
    InfoManager.getInstance().root().setPlayingChannelNode(paramNode);
    String str2 = localChannelNode2.getSourceUrl();
    if ((str2 != null) && (!str2.equalsIgnoreCase("")))
    {
      ProgramNode localProgramNode3 = localChannelNode2.getProgramNodeByTime(System.currentTimeMillis());
      if ((localProgramNode3 != null) && (localProgramNode3.getCurrPlayStatus() == 1))
        str2 = localProgramNode3.getSourceUrl();
      _play(str2);
      this.liveStream = true;
      if (localProgramNode3 != null)
        InfoManager.getInstance().root().setPlayingNode(localProgramNode3);
    }
    while (true)
    {
      this.currPlayState = 4096;
      InfoManager.getInstance().runSellApps();
      return;
      InfoManager.getInstance().root().setPlayingNode(localChannelNode2);
      continue;
      if (this.hasRecoveredFromCrash)
        this.hasRecoveredFromCrash = false;
      ProgramNode localProgramNode2 = localChannelNode2.getProgramNodeByTime(System.currentTimeMillis());
      if (localProgramNode2 != null)
      {
        String str3;
        if (localProgramNode2.getCurrPlayStatus() == 3)
        {
          str3 = localProgramNode2.getSourceUrl();
          label204: if ((str3 == null) || (str3.equalsIgnoreCase("")))
            break label282;
          if ((_play(str3)) && (localProgramNode2.getCurrPlayStatus() == 3))
            autoSeek(localProgramNode2.id);
          if (localProgramNode2.getCurrPlayStatus() != 3)
            break label284;
        }
        label282: label284: for (this.liveStream = false; ; this.liveStream = true)
        {
          InfoManager.getInstance().root().setPlayingNode(localProgramNode2);
          break;
          str3 = localProgramNode2.getSourceUrl();
          break label204;
          break;
        }
        if (paramNode.nodeName.equalsIgnoreCase("program"))
        {
          ProgramNode localProgramNode1 = (ProgramNode)paramNode;
          if (localProgramNode1.channelType == 1)
            InfoManager.getInstance().updateViewTime(localProgramNode1.channelId + "", localProgramNode1.getUpdateTime());
          ChannelNode localChannelNode1 = ChannelHelper.getInstance().getChannel(localProgramNode1);
          if (localChannelNode1 != null)
            InfoManager.getInstance().root().setPlayingChannelNode(localChannelNode1);
          String str1 = "";
          if (localProgramNode1.getCurrPlayStatus() == 3)
          {
            if (!localProgramNode1.isDownloadProgram())
              str1 = InfoManager.getInstance().root().getLocalProgramSource(localProgramNode1);
            if ((str1 == null) || (str1.equalsIgnoreCase("")))
            {
              str1 = PlayCacheAgent.getInstance().getCache(localProgramNode1);
              if (str1 == null);
            }
            else
            {
              label444: if ((str1 == null) || (str1.equalsIgnoreCase("")))
                break label568;
              if ((this.currPlayState == 1) && ((this.source == null) || (this.source.equalsIgnoreCase(str1))))
                break label570;
              if ((_play(str1)) && (localProgramNode1.getCurrPlayStatus() == 3))
                autoSeek(localProgramNode1.id);
              label513: if (localProgramNode1.getCurrPlayStatus() != 3)
                break label577;
            }
          }
          label568: label570: label577: for (this.liveStream = false; ; this.liveStream = true)
          {
            InfoManager.getInstance().root().setPlayingNode(localProgramNode1);
            break;
            PlayCacheAgent.getInstance().cacheNode(localProgramNode1);
            str1 = localProgramNode1.getSourceUrl();
            break label444;
            str1 = localProgramNode1.getSourceUrl();
            break label444;
            break;
            _resume();
            break label513;
          }
        }
        if (paramNode.nodeName.equalsIgnoreCase("ringtone"))
        {
          playRingTone(paramNode);
          InfoManager.getInstance().root().setPlayMode(RootNode.PlayMode.ALARM_PLAY_ONLINE);
        }
      }
    }
  }

  public void play(String paramString)
  {
    try
    {
      InfoManager.getInstance().root().setPlayMode(RootNode.PlayMode.ALARM_PLAY_ONLINE);
      if ((this.iService != null) && (paramString != null) && (!paramString.equalsIgnoreCase("")))
      {
        this.source = paramString;
        this.iService.stop();
        this.iService.setSource(paramString);
        this.iService.play();
      }
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void playAndLoadData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString)
  {
    if (paramInt3 != 0);
    for (int i = 1; ; i = 0)
    {
      ChannelNode localChannelNode1;
      ChannelNode localChannelNode2;
      if (paramInt1 == DownLoadInfoNode.mDownloadId)
      {
        localChannelNode1 = InfoManager.getInstance().root().mDownLoadInfoNode.getChannelNode(paramInt2);
        if (localChannelNode1 != null)
          break label141;
        if (paramInt4 != 1)
          break label121;
        localChannelNode2 = ChannelHelper.getInstance().getFakeVirtualChannel(paramInt2, paramInt1, paramString);
      }
      while (true)
      {
        label52: if (localChannelNode2 != null)
        {
          InfoManager.getInstance().root().setPlayingChannelNode(localChannelNode2);
          if (!localChannelNode2.hasEmptyProgramSchedule())
            break label165;
          InfoManager.getInstance().loadProgramsScheduleNode(localChannelNode2, null);
        }
        label121: ProgramNode localProgramNode;
        label141: label165: 
        do
        {
          do
          {
            if (i != 0)
            {
              setLoadedAndPlayId(paramInt2, paramInt3);
              InfoManager.getInstance().loadProgramInfo(localChannelNode2, paramInt3, this);
            }
            return;
            localChannelNode1 = ChannelHelper.getInstance().getChannel(paramInt2, paramInt4);
            break;
            if (paramInt4 != 0)
              break label189;
            localChannelNode2 = ChannelHelper.getInstance().getFakeLiveChannel(paramInt2, paramInt1, paramString);
            break label52;
            if (localChannelNode1.channelType != 0)
              break label189;
            play(localChannelNode1);
            localChannelNode2 = localChannelNode1;
            i = 0;
            break label52;
          }
          while (paramInt3 == 0);
          localProgramNode = localChannelNode2.getProgramNode(paramInt3);
        }
        while (localProgramNode == null);
        play(localProgramNode);
        return;
        label189: localChannelNode2 = localChannelNode1;
      }
    }
  }

  public void playCurrCache(int paramInt, String paramString)
  {
    try
    {
      if (InfoManager.getInstance().root().currentPlayMode() != RootNode.PlayMode.PLAY_END_ADVERTISEMENT)
      {
        if (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.PLAY_FRONT_ADVERTISEMENT)
          return;
        Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
        if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")) && (paramInt == ((ProgramNode)localNode).resId))
        {
          _playCache(paramString);
          return;
        }
      }
    }
    catch (Exception localException)
    {
    }
  }

  public void playNext()
  {
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if (localNode != null)
    {
      if (localNode.nextSibling == null)
        break label30;
      play(localNode.nextSibling);
    }
    label30: ProgramNode localProgramNode;
    do
    {
      ChannelNode localChannelNode;
      do
      {
        return;
        localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      }
      while ((localChannelNode == null) || (localChannelNode.hasEmptyProgramSchedule()) || (localChannelNode.getAllLstProgramNode() == null) || (!localNode.nodeName.equalsIgnoreCase("program")));
      localProgramNode = localChannelNode.getProgramNodeByProgramId(((ProgramNode)localNode).id);
    }
    while ((localProgramNode == null) || (localProgramNode.nextSibling == null));
    play(localProgramNode.nextSibling);
  }

  public void playNextCategory()
  {
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    Node localNode1 = InfoManager.getInstance().root().getCurrentPlayingNode();
    List localList = InfoManager.getInstance().root().getRecommendCategoryNode(0).lstRecMain;
    if ((localList == null) || (localList.size() == 0))
      return;
    if (localChannelNode == null)
    {
      play(((RecommendItemNode)((List)localList.get(0)).get(0)).mNode);
      return;
    }
    for (int i = 0; i < localList.size(); i++)
      for (int j = 0; j < ((List)localList.get(i)).size(); j++)
        if (((RecommendItemNode)((List)localList.get(i)).get(j)).mCategoryId == localChannelNode.categoryId)
        {
          int k = (i + 1) % localList.size();
          Node localNode2 = ((RecommendItemNode)((List)localList.get(k)).get(0)).mNode;
          if (localNode1 == localNode2);
          for (localNode2 = ((RecommendItemNode)((List)localList.get((k + 1) % localList.size())).get(0)).mNode; ; localNode2 = ((RecommendItemNode)((List)localList.get((k + 1) % localList.size())).get(0)).mNode)
            do
            {
              play(localNode2);
              return;
            }
            while ((localNode2.nodeName.equalsIgnoreCase("program")) || (localNode2.nodeName.equalsIgnoreCase("channel")));
        }
    play(((RecommendItemNode)((List)localList.get(0)).get(0)).mNode);
  }

  public void playNextForCar()
  {
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if (localChannelNode != null)
    {
      if (localChannelNode.channelType != 0)
        break label47;
      play(ChannelHelper.getInstance().getNextChannel(localChannelNode.channelId, 0));
    }
    label47: ProgramNode localProgramNode;
    do
    {
      do
      {
        do
          return;
        while (localChannelNode.channelType != 1);
        if (localNode.nextSibling != null)
        {
          play(localNode.nextSibling);
          return;
        }
      }
      while ((localChannelNode.hasEmptyProgramSchedule()) || (localChannelNode.getAllLstProgramNode() == null) || (!localNode.nodeName.equalsIgnoreCase("program")));
      localProgramNode = localChannelNode.getProgramNodeByProgramId(((ProgramNode)localNode).id);
    }
    while ((localProgramNode == null) || (localProgramNode.nextSibling == null));
    play(localProgramNode.nextSibling);
  }

  public void playPreCategory()
  {
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    List localList = InfoManager.getInstance().root().getRecommendCategoryNode(0).lstRecMain;
    if ((localList == null) || (localList.size() == 0))
      return;
    if (localChannelNode == null)
    {
      play(((RecommendItemNode)((List)localList.get(0)).get(0)).mNode);
      return;
    }
    for (int i = 0; i < localList.size(); i++)
      for (int j = 0; j < ((List)localList.get(i)).size(); j++)
        if ((((RecommendItemNode)((List)localList.get(i)).get(j)).mCategoryId == localChannelNode.categoryId) && (i >= 0))
        {
          int k = (i - 1 + localList.size()) % localList.size();
          Node localNode = ((RecommendItemNode)((List)localList.get(k)).get(0)).mNode;
          if (localNode == localChannelNode);
          for (localNode = ((RecommendItemNode)((List)localList.get((i - 1 + localList.size()) % localList.size())).get(0)).mNode; ; localNode = ((RecommendItemNode)((List)localList.get((k - 1 + localList.size()) % localList.size())).get(0)).mNode)
            do
            {
              play(localNode);
              return;
            }
            while ((localNode.nodeName.equalsIgnoreCase("program")) || (localNode.nodeName.equalsIgnoreCase("channel")));
        }
    play(((RecommendItemNode)((List)localList.get(0)).get(0)).mNode);
  }

  public void playPreForCar()
  {
    ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if (localChannelNode != null)
    {
      if (localChannelNode.channelType != 0)
        break label47;
      play(ChannelHelper.getInstance().getPrevChannel(localChannelNode.channelId, 0));
    }
    label47: ProgramNode localProgramNode;
    do
    {
      do
      {
        do
          return;
        while (localChannelNode.channelType != 1);
        if (localNode.prevSibling != null)
        {
          play(localNode.prevSibling);
          return;
        }
      }
      while ((localChannelNode.hasEmptyProgramSchedule()) || (localChannelNode.getAllLstProgramNode() == null) || (!localNode.nodeName.equalsIgnoreCase("program")));
      localProgramNode = localChannelNode.getProgramNodeByProgramId(((ProgramNode)localNode).id);
    }
    while ((localProgramNode == null) || (localProgramNode.prevSibling == null));
    play(localProgramNode.prevSibling);
  }

  public void playRingTone(Node paramNode)
  {
    if (paramNode == null)
      return;
    requestAudioFocus();
    stopFM();
    if (paramNode.nodeName.equalsIgnoreCase("ringtone"))
    {
      if ((this.currPlayState == 4096) && (InfoManager.getInstance().root().getCurrentPlayingNode() != null))
        stop();
      playSource(((RingToneNode)paramNode).getListenOnLineUrl());
      this.liveStream = false;
    }
    this.currPlayState = 4096;
  }

  public boolean playSource(String paramString)
  {
    if ((this.iService == null) || (paramString == null) || (paramString.equalsIgnoreCase("")))
      return false;
    try
    {
      setSource(paramString);
      this.iService.play();
      return true;
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        localRemoteException.printStackTrace();
    }
  }

  public int queryDuration()
  {
    if (this.iService == null)
      return -1;
    try
    {
      int i = this.iService.queryDuration();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -1;
  }

  public boolean queryIsLiveStream()
  {
    if (this.iService == null)
      return false;
    try
    {
      boolean bool = this.iService.queryIsLiveStream();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return false;
  }

  public int queryPosition()
  {
    if (this.iService == null)
      return -1;
    try
    {
      int i = this.iService.queryPosition();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -1;
  }

  public void recoverRecvPlay(boolean paramBoolean)
  {
    this.recvDoPlay = paramBoolean;
  }

  public void recoverSource(String paramString)
  {
    if ((this.iService == null) || (paramString == null));
    while ((this.preloaded_source != null) && (this.preloaded_source.equalsIgnoreCase(paramString)))
      return;
    this.hasRecoveredFromCrash = true;
    try
    {
      this.source = paramString;
      this.preloaded_source = paramString;
      this.iService.setSource(this.preloaded_source);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  public void removeMediaEventListener(IMediaEventListener paramIMediaEventListener)
  {
    HashSet localHashSet = new HashSet();
    localHashSet.addAll(this.listeners);
    Iterator localIterator = localHashSet.iterator();
    while (localIterator.hasNext())
    {
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      IMediaEventListener localIMediaEventListener = (IMediaEventListener)localWeakReference.get();
      if ((localIMediaEventListener == null) || (localIMediaEventListener == paramIMediaEventListener))
        this.listeners.remove(localWeakReference);
    }
  }

  public void replay(Node paramNode)
  {
    if (paramNode == null)
      return;
    requestAudioFocus();
    stopFM();
    ProgramNode localProgramNode;
    String str;
    if (paramNode.nodeName.equalsIgnoreCase("program"))
    {
      localProgramNode = (ProgramNode)paramNode;
      ChannelNode localChannelNode = ChannelHelper.getInstance().getChannel(localProgramNode);
      if (localChannelNode != null)
        InfoManager.getInstance().root().setPlayingChannelNode(localChannelNode);
      str = "";
      if (localProgramNode.getCurrPlayStatus() != 3)
        break label209;
      if (!localProgramNode.isDownloadProgram())
        str = InfoManager.getInstance().root().getLocalProgramSource(localProgramNode);
      if ((str == null) || (str.equalsIgnoreCase("")))
      {
        str = PlayCacheAgent.getInstance().getCache(localProgramNode);
        if (str == null)
          break label192;
      }
      if ((this.currPlayState == 1) && ((this.source == null) || (this.source.equalsIgnoreCase(str))))
        break label218;
      if (_play(str))
        autoSeek(localProgramNode.id);
      label161: if (localProgramNode.getCurrPlayStatus() != 3)
        break label225;
    }
    label192: label209: label218: label225: for (this.liveStream = false; ; this.liveStream = true)
    {
      InfoManager.getInstance().root().setPlayingNode(localProgramNode);
      this.currPlayState = 4096;
      return;
      PlayCacheAgent.getInstance().cacheNode(localProgramNode);
      str = localProgramNode.getSourceUrl();
      break;
      str = localProgramNode.getSourceUrl();
      break;
      _resume();
      break label161;
    }
  }

  public long replayByTime(Node paramNode, long paramLong)
  {
    return -1L;
  }

  public void reset()
  {
    this.playDuration = 0;
    this.currPlayState = 30583;
    this.liveStream = true;
    stop();
    stopFM();
    exit();
    releasePlayControll();
    this.iService = null;
  }

  public void saveBattery(boolean paramBoolean)
  {
    if (this.iService == null)
      return;
    if (paramBoolean)
      try
      {
        this.iService.enableOpt();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    this.iService.disableOpt();
  }

  public void seek(float paramFloat)
  {
    if (this.iService == null);
    while ((InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.PLAY_END_ADVERTISEMENT) || (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.PLAY_FRONT_ADVERTISEMENT))
      return;
    this.playDuration = queryDuration();
    int i = (int)(paramFloat * this.playDuration);
    if (i >= this.playDuration)
      i = -5 + this.playDuration;
    if (i < 0)
      i = 0;
    this.seekTime = i;
    this.seekHandler.removeCallbacks(this.doSeek);
    this.seekHandler.postDelayed(this.doSeek, 1000L);
  }

  public void seekPosition(int paramInt)
  {
    if (this.iService == null)
      return;
    this.playDuration = queryDuration();
    if (paramInt >= this.playDuration);
    for (int i = -5 + this.playDuration; ; i = paramInt)
    {
      if (i < 0)
        i = 0;
      try
      {
        this.iService.seek(i);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
        return;
      }
    }
  }

  public void sendBufferLog()
  {
    Iterator localIterator = this.mapBufferCnt.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      int i = ((Integer)localEntry.getValue()).intValue();
      if (i > 0)
      {
        String str = QTLogger.getInstance().buildListeneringQualityLog(InfoManager.getInstance().root().getCurrentPlayingNode(), 0.0D, i, (String)localEntry.getKey());
        if (str != null)
          LogModule.getInstance().send("PlayExperience", str);
        localEntry.setValue(Integer.valueOf(0));
      }
    }
    this.mapBufferCnt.clear();
  }

  public void setBufferTime(float paramFloat)
  {
    if (this.iService == null)
      return;
    try
    {
      this.iService.setBufferTime(paramFloat);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  public void setCurrPlayState(int paramInt)
  {
    this.currPlayState = paramInt;
  }

  public void setEventHandler(IEventHandler paramIEventHandler)
  {
    this.mEventHandler = paramIEventHandler;
  }

  public boolean setHttpProxy(String paramString)
  {
    try
    {
      IServiceControl localIServiceControl = this.iService;
      boolean bool1 = false;
      if (localIServiceControl != null)
      {
        boolean bool2 = this.iService.setHttpProxy(paramString);
        bool1 = bool2;
      }
      return bool1;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return false;
  }

  public boolean setIsViaWoProxy(boolean paramBoolean)
  {
    try
    {
      IServiceControl localIServiceControl = this.iService;
      boolean bool1 = false;
      if (localIServiceControl != null)
      {
        boolean bool2 = this.iService.setIsViaWoProxy(paramBoolean);
        bool1 = bool2;
      }
      return bool1;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return false;
  }

  public void setLiveStream(boolean paramBoolean)
  {
    this.liveStream = paramBoolean;
  }

  public void setLocation(String paramString1, String paramString2)
  {
    try
    {
      this.iService.setLocation(paramString1, paramString2);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void setPlayingChannel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String paramString1, String paramString2, int paramInt6)
  {
    SharedCfg.getInstance().setLastPlayInfo(paramInt1, paramInt2, paramInt3, paramInt5);
    if (this.iService != null);
    try
    {
      this.iService.setPlayingChannel(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, this.mPlaySource, paramString1, paramString2, paramInt6);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  public void setServiceControl(IServiceControl paramIServiceControl)
  {
    this.iService = paramIServiceControl;
  }

  public void setSource(Node paramNode)
  {
    if (paramNode == null);
    ProgramNode localProgramNode2;
    do
    {
      return;
      if (!paramNode.nodeName.equalsIgnoreCase("channel"))
        break label171;
      if (((ChannelNode)paramNode).channelType != 1)
        break;
      localProgramNode2 = ((ChannelNode)paramNode).getProgramNodeByTime(System.currentTimeMillis());
    }
    while ((localProgramNode2 == null) || (localProgramNode2.channelType == 1));
    Object localObject = localProgramNode2.getSourceUrl();
    while (true)
    {
      label61: Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if (localNode != null)
      {
        String str1;
        if (localNode.nodeName.equalsIgnoreCase("channel"))
          str1 = ((ChannelNode)localNode).getSourceUrl();
        while (true)
        {
          if ((str1 == null) || (localObject == null))
            break label274;
          if (str1.equalsIgnoreCase((String)localObject))
            break;
          getInstance().setSource((String)localObject);
          return;
          String str2 = ((ChannelNode)paramNode).getSourceUrl();
          ProgramNode localProgramNode1 = ((ChannelNode)paramNode).getProgramNodeByTime(System.currentTimeMillis());
          if ((localProgramNode1 != null) && (localProgramNode1.getCurrPlayStatus() == 1))
            str2 = localProgramNode1.getSourceUrl();
          localObject = str2;
          break label61;
          label171: if (!paramNode.nodeName.equalsIgnoreCase("program"))
            break label298;
          if (((ProgramNode)paramNode).getCurrPlayStatus() == 3)
          {
            localObject = ((ProgramNode)paramNode).getSourceUrl();
            break label61;
          }
          localObject = ((ProgramNode)paramNode).getSourceUrl();
          break label61;
          if (localNode.nodeName.equalsIgnoreCase("program"))
          {
            str1 = ((ProgramNode)localNode).getSourceUrl();
          }
          else
          {
            boolean bool = localNode.nodeName.equalsIgnoreCase("ondemandprogram");
            str1 = null;
            if (bool)
              str1 = ((OnDemandProgramNode)localNode).getSourceUrls();
          }
        }
        label274: if (localObject == null)
          break;
        getInstance().setSource((String)localObject);
        return;
      }
      if (localObject == null)
        break;
      getInstance().setSource((String)localObject);
      return;
      label298: localObject = null;
    }
  }

  public void setVolume(float paramFloat)
  {
    if (this.iService == null)
      return;
    try
    {
      this.iService.setVolume(paramFloat);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  public void setWillPlayNode(Node paramNode)
  {
    if (paramNode == null)
      return;
    setSource(paramNode);
    InfoManager.getInstance().root().setWillPlayNode(paramNode);
  }

  public void startFM(RadioChannelNode paramRadioChannelNode)
  {
    if (paramRadioChannelNode != null)
    {
      if (InfoManager.getInstance().root().isOpenFm())
        FMManager.getInstance().turnOff();
      FMManager.getInstance().tune(Integer.valueOf(paramRadioChannelNode.freq).intValue());
      getInstance().dispatchPlayStateInFM(4096);
      InfoManager.getInstance().root().setPlayingNode(paramRadioChannelNode);
      InfoManager.getInstance().root().tuneFM(true);
    }
  }

  public void startQuitTimer()
  {
    if (this.iService == null)
      return;
    try
    {
      this.iService.startQuitTimer();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  public void stop()
  {
    if (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.ALARM_PLAY_ONLINE)
    {
      dispatchPlayState(0);
      _stop();
      this.currPlayState = 0;
    }
    do
    {
      return;
      if ((InfoManager.getInstance().root().currentPlayMode() != RootNode.PlayMode.PLAY_END_ADVERTISEMENT) && (InfoManager.getInstance().root().currentPlayMode() != RootNode.PlayMode.PLAY_FRONT_ADVERTISEMENT))
        break;
      dispatchPlayState(0);
      _pause();
      this.currPlayState = 1;
    }
    while (!InfoManager.getInstance().getTaobaoAudioAdv());
    TaobaoAgent.getInstance().stopAD();
    return;
    this.mConnected = false;
    dispatchPlayState(0);
    SystemPlayer.getInstance().stop();
    autoReserve();
    this.beginPlay = 0L;
    int i = queryPosition();
    int j = queryDuration();
    if ((this.liveStream) || (!this.hasPlayed))
      _stop();
    for (this.currPlayState = 0; ; this.currPlayState = 1)
    {
      PlayedMetaInfo.getInstance().addPlayedMeta(InfoManager.getInstance().root().getCurrentPlayingNode(), i, j);
      return;
      _pause();
    }
  }

  public void stopFM()
  {
    if (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.FMPLAY)
    {
      FMManager.getInstance().turnOff();
      InfoManager.getInstance().root().tuneFM(false);
    }
  }

  public void stopQuitTimer()
  {
    if (this.iService == null)
      return;
    try
    {
      this.iService.stopQuitTimer();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  public void stopWithoutDispatchState()
  {
    long l = queryPosition();
    int i = queryDuration();
    if ((this.liveStream) || (!this.hasPlayed))
      _stop();
    for (this.currPlayState = 0; ; this.currPlayState = 1)
    {
      PlayedMetaInfo.getInstance().addPlayedMeta(InfoManager.getInstance().root().getCurrentPlayingNode(), (int)l, i);
      return;
      _pause();
    }
  }

  public void unbindService()
  {
    try
    {
      this._context.unbindService(this.conn);
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      try
      {
        while (true)
        {
          if (this.recevier != null)
          {
            this._context.unregisterReceiver(this.recevier);
            this.recevier = null;
          }
          return;
          localIllegalArgumentException1 = localIllegalArgumentException1;
          localIllegalArgumentException1.printStackTrace();
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException2)
      {
        localIllegalArgumentException2.printStackTrace();
      }
    }
  }

  public boolean unsetHttpProxy()
  {
    try
    {
      IServiceControl localIServiceControl = this.iService;
      boolean bool1 = false;
      if (localIServiceControl != null)
      {
        boolean bool2 = this.iService.unsetHttpProxy();
        bool1 = bool2;
      }
      return bool1;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return false;
  }

  public void updatePlayInfo(Node paramNode)
  {
    if ((paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("ondemandprogram")));
    String str1;
    do
    {
      return;
      str1 = ((OnDemandProgramNode)paramNode).getSourceUrls();
    }
    while ((str1 == null) || (str1.equalsIgnoreCase("")) || (this.iService == null));
    try
    {
      String str2 = this.iService.getSource();
      if (str2 != null)
      {
        if (str2.equalsIgnoreCase(str1))
          break label93;
        _play(str1);
      }
      while (true)
      {
        label77: this.liveStream = false;
        InfoManager.getInstance().root().setPlayingNode(paramNode);
        return;
        label93: this.preloaded_source = str2;
        this.source = str2;
      }
    }
    catch (Exception localException)
    {
      break label77;
    }
  }

  public void updatePlayInfo(List<Node> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0))
      return;
    while (true)
    {
      try
      {
        String str = this.iService.getSource();
        Node localNode;
        if ((str != null) && (!str.equalsIgnoreCase("")))
        {
          int i = 0;
          if (i < paramList.size())
          {
            if ((((Node)paramList.get(i)).nodeName.equalsIgnoreCase("ondemandprogram")) && (((OnDemandProgramNode)paramList.get(i)).getSourceUrls().equalsIgnoreCase(str)))
            {
              localNode = (Node)paramList.get(i);
              this.preloaded_source = str;
              this.source = str;
              if (localNode == null)
                break;
              this.liveStream = false;
              InfoManager.getInstance().root().setPlayingNode(localNode);
              return;
            }
            i++;
            continue;
          }
        }
      }
      catch (Exception localException)
      {
        return;
      }
      localNode = null;
    }
  }

  public void updatePlayStatus(PlayStatus paramPlayStatus)
  {
    if (paramPlayStatus.state == 8192)
    {
      this.currPlayState = 0;
      String str = QTLogger.getInstance().buildResourceUnavailLog(InfoManager.getInstance().root().getCurrentPlayingNode());
      if (str != null)
        QTLogger.getInstance().sendResourceUnavailLog(str);
    }
  }

  public void updateRePlayInfoByNode(Node paramNode)
  {
    if (paramNode == null);
    String str;
    do
    {
      do
        return;
      while (!paramNode.nodeName.equalsIgnoreCase("program"));
      str = ((ProgramNode)paramNode).getSourceUrl();
    }
    while ((str == null) || (str.equalsIgnoreCase("")));
    this.preloaded_source = str;
    this.source = str;
    this.liveStream = false;
    InfoManager.getInstance().root().setPlayingNode(paramNode);
  }

  class PlayControllReceiver extends BroadcastReceiver
  {
    PlayControllReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent == null);
      label576: 
      while (true)
      {
        return;
        if (paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.CAR_PAUSE"))
        {
          MobclickAgent.onEvent(PlayerAgent.this._context, "fujia", "pause");
          if (PlayerAgent.this.isPlaying())
            PlayerAgent.this.stop();
        }
        else
        {
          if (paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.CAR_PLAY"))
          {
            MobclickAgent.onEvent(PlayerAgent.this._context, "fujia", "play");
            Node localNode4 = InfoManager.getInstance().root().getCurrentPlayingNode();
            if (localNode4 != null)
            {
              PlayerAgent.this.play(localNode4);
              return;
            }
            ChannelNode localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
            PlayerAgent.this.play(localChannelNode);
            return;
          }
          if (paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.CAR_PLAY_NEXT"))
          {
            MobclickAgent.onEvent(PlayerAgent.this._context, "fujia", "playnext");
            PlayerAgent.this.playNextForCar();
            return;
          }
          if (paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.CAR_PLAY_PRE"))
          {
            MobclickAgent.onEvent(PlayerAgent.this._context, "fujia", "playpre");
            PlayerAgent.this.playPreForCar();
            return;
          }
          label223: Iterator localIterator;
          if (paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.CAR_PLAY_NEXT_CATEGORY"))
          {
            MobclickAgent.onEvent(PlayerAgent.this._context, "fujia", "playnextcat");
            PlayerAgent.this.playNextCategory();
            Bundle localBundle = paramIntent.getExtras();
            if (localBundle != null)
            {
              Set localSet = localBundle.keySet();
              if (localSet != null)
                localIterator = localSet.iterator();
            }
          }
          else
          {
            while (true)
            {
              if (!localIterator.hasNext())
                break label576;
              String str = (String)localIterator.next();
              if (str != null)
              {
                if (str.equalsIgnoreCase("toggleplay"))
                {
                  abortBroadcast();
                  if (PlayerAgent.this.isPlaying())
                  {
                    PlayerAgent.this.stop();
                    continue;
                    if (!paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.CAR_PLAY_PRE_CATEGORY"))
                      break label223;
                    MobclickAgent.onEvent(PlayerAgent.this._context, "fujia", "playprecat");
                    PlayerAgent.this.playPreCategory();
                    break label223;
                  }
                  Node localNode3 = InfoManager.getInstance().root().getCurrentPlayingNode();
                  if (localNode3 == null)
                    continue;
                  PlayerAgent.this.play(localNode3);
                  continue;
                }
                if (str.equalsIgnoreCase("playpre"))
                {
                  abortBroadcast();
                  Node localNode2 = InfoManager.getInstance().root().getCurrentPlayingNode();
                  if (localNode2 == null)
                    continue;
                  if (localNode2.nodeName.equalsIgnoreCase("program"))
                  {
                    ProgramNode localProgramNode2 = ((ProgramNode)localNode2).getPrevSibling();
                    if ((localProgramNode2 != null) && (localProgramNode2.getCurrPlayStatus() == 2))
                      break;
                    PlayerAgent.this.play(localProgramNode2);
                    return;
                  }
                  PlayerAgent.this.play(localNode2.prevSibling);
                  continue;
                }
                if (str.equalsIgnoreCase("playnext"))
                {
                  abortBroadcast();
                  Node localNode1 = InfoManager.getInstance().root().getCurrentPlayingNode();
                  if (localNode1 == null)
                    continue;
                  if (localNode1.nodeName.equalsIgnoreCase("program"))
                  {
                    ProgramNode localProgramNode1 = ((ProgramNode)localNode1).getNextSibling();
                    if ((localProgramNode1 != null) && (localProgramNode1.getCurrPlayStatus() == 2))
                      break;
                    PlayerAgent.this.play(localProgramNode1);
                    return;
                  }
                  PlayerAgent.this.play(localNode1.nextSibling);
                  continue;
                }
                if (str.equalsIgnoreCase("eof"))
                  abortBroadcast();
              }
            }
          }
        }
      }
    }
  }

  class QTReceiver extends BroadcastReceiver
  {
    QTReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      Bundle localBundle = paramIntent.getExtras();
      Iterator localIterator = localBundle.keySet().iterator();
      while (true)
      {
        PlayStatus localPlayStatus;
        if (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          if (str.equals("playstatus"))
          {
            localPlayStatus = (PlayStatus)localBundle.get(str);
            if (localPlayStatus != null)
            {
              int i = localPlayStatus.getPlayingState();
              int j = localPlayStatus.getState();
              if (PlayerAgent.this.currPlayState == 4096)
              {
                if (i != 0)
                  break label123;
                if ((j == 2) && (!PlayerAgent.this.liveStream))
                  PlayerAgent.this.dispatchMediaEvent(1, localPlayStatus);
              }
            }
          }
        }
        else
        {
          return;
          label123: PlayerAgent.this.dispatchMediaEvent(1, localPlayStatus);
          PlayerAgent.this.updatePlayStatus(localPlayStatus);
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.fm.PlayerAgent
 * JD-Core Version:    0.6.2
 */