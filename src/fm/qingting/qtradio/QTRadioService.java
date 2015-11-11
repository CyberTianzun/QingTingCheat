package fm.qingting.qtradio;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;
import fm.qingting.qtradio.fm.IServiceControl.Stub;
import fm.qingting.qtradio.fm.PlayStatus;
import fm.qingting.qtradio.log.PlayLog;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.notification.MediaControlReceiver;
import fm.qingting.qtradio.playlist.PlayListManager;
import fm.qingting.qtradio.simpleImageloader.SimpleImageloader;
import fm.qingting.qtradio.simpleImageloader.SimpleImageloader.IRecvBitmapEventListener;
import fm.qingting.qtradio.wo.WoInfo;
import fm.qingting.utils.NativeEnv;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import rui.lin.spectra.DualSpectraPlayer;
import rui.lin.spectra.MetadataParser;
import rui.lin.spectra.Spectra;
import rui.lin.spectra.Spectra.SpectraEvent;
import rui.lin.spectra.Spectra.SpectraEventListener;

public class QTRadioService extends Service
  implements SimpleImageloader.IRecvBitmapEventListener
{
  private static int CollectionAttentionSeconds = 0;
  private static final int DelayStoreInterval = 10000;
  private static final int EXIT_PLAYER = 4;
  public static final String IntentCollectionRemind = "fm.qingting.qtradio.collection_remind";
  public static final String IntentCollectionRemindExtra = "collection_remind_channel_id";
  private static final int MAX_PLAY_DURATION = 7200;
  private static final int MIN_PLAY_DURATION = 2;
  private static final int PLAY_ERROR = 3;
  private static final int SET_PLAY_INFO = 7;
  private static final int START_PLAY = 6;
  private static final int STOP_PLAY = 2;
  private static final String SUFFIX = ".cache";
  public static final String SetPlayChannelNode = "setplaychannelnode";
  public static final String SetPlayNextNode = "setplaynextnode";
  public static final String SetPlayNode = "setplaynode";
  private static final String TAG = "QTRadioService";
  private static final String directory = Environment.getExternalStorageDirectory() + File.separator + "QTDownloadRadio";
  private static boolean isViaWoProxy;
  private boolean checked = false;
  private Runnable clockRunnable = new Runnable()
  {
    public void run()
    {
      if ((QTRadioService.this.mContext == null) || (!QTRadioService.this.startTimer))
        return;
      long l = System.currentTimeMillis() / 1000L;
      if (GlobalCfg.getInstance(QTRadioService.this.mContext).getQuitTime() < l)
      {
        QTRadioService.this.savePlayedMeta();
        QTRadioService.this.stopRadio();
        QTRadioService.this.stopTimer();
        QTRadioService.this.exitProcess();
        return;
      }
      QTRadioService.this.handler.postDelayed(QTRadioService.this.clockRunnable, 10000L);
    }
  };
  private Runnable doUpdate = new Runnable()
  {
    public void run()
    {
      try
      {
        QTRadioService.this.updatePlayList(false);
        QTRadioService.this.refresh();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  };
  private Handler handler = new Handler();
  private boolean hasInitRemoteControll = false;
  private boolean hasPlayed = false;
  private boolean isPlayed = false;
  private boolean isRelaxationPassed = false;
  private boolean isStart = false;
  private boolean isTimingSmoothPlaying = false;
  private boolean mActivityHasDie = false;
  private final IServiceControl.Stub mBinder = new IServiceControl.Stub()
  {
    public void addABTestCategory(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      throws RemoteException
    {
      PlayLog.getInstance().addABTestCategory(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
    }

    public void addABTestProgram(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
      throws RemoteException
    {
      PlayLog.getInstance().addABTestProgram(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4);
    }

    public void addPlaySource(int paramAnonymousInt)
      throws RemoteException
    {
      QTRadioService.access$2202(QTRadioService.this, paramAnonymousInt);
      PlayLog.getInstance().addSource(QTRadioService.this.mPlaySource);
    }

    public void disableOpt()
      throws RemoteException
    {
      QTRadioService.this.mDualSpectraPlayer.disableOpt();
    }

    public void enableOpt()
      throws RemoteException
    {
      QTRadioService.this.mDualSpectraPlayer.enableOpt();
    }

    public void exit()
      throws RemoteException
    {
      QTRadioService.this.exitProcess();
    }

    public String getHttpProxy()
    {
      return NativeEnv.getenv("http_proxy");
    }

    public boolean getIsViaWoProxy()
    {
      return QTRadioService.isViaWoProxy;
    }

    public int getPlayingCatId()
      throws RemoteException
    {
      return QTRadioService.this.mCategoryId;
    }

    public int getPlayingChannelId()
      throws RemoteException
    {
      return QTRadioService.this.mChannelId;
    }

    public String getSource()
      throws RemoteException
    {
      return QTRadioService.this.getSourceUrls();
    }

    public void pause()
      throws RemoteException
    {
      QTRadioService.this.mOperationQueue.add(QTRadioService.Operation.PAUSE);
      QTRadioService.this.mPlayerOperator.sendEmptyMessage(QTRadioService.Operation.PAUSE.ordinal());
      QTRadioService.this.refreshNotifiationState(false);
    }

    public void play()
      throws RemoteException
    {
      QTRadioService.this.mOperationQueue.add(QTRadioService.Operation.PLAY);
      QTRadioService.this.mPlayerOperator.sendEmptyMessage(QTRadioService.Operation.PLAY.ordinal());
      QTRadioService.this.refreshNotifiationState(true);
    }

    public String queryContainerFormat()
      throws RemoteException
    {
      return QTRadioService.this.mDualSpectraPlayer.queryContainerFormat();
    }

    public int queryDuration()
      throws RemoteException
    {
      return QTRadioService.this.mDualSpectraPlayer.queryDuration();
    }

    public boolean queryIsLiveStream()
      throws RemoteException
    {
      return QTRadioService.this.mDualSpectraPlayer.isLiveStream();
    }

    public int queryPlayInfo(int paramAnonymousInt)
      throws RemoteException
    {
      return QTRadioService.this._queryPlayInfo(paramAnonymousInt);
    }

    public String queryPlayingUrl()
      throws RemoteException
    {
      return QTRadioService.this.mDualSpectraPlayer.querySelectedUrl();
    }

    public int queryPosition()
      throws RemoteException
    {
      return QTRadioService.this.mDualSpectraPlayer.queryPosition();
    }

    public void resume()
      throws RemoteException
    {
      QTRadioService.this.mOperationQueue.add(QTRadioService.Operation.RESUME);
      QTRadioService.this.mPlayerOperator.sendEmptyMessage(QTRadioService.Operation.RESUME.ordinal());
      QTRadioService.this.refreshNotifiationState(true);
    }

    public void seek(int paramAnonymousInt)
      throws RemoteException
    {
      QTRadioService.this.mOperationQueue.add(QTRadioService.Operation.SEEK);
      QTRadioService.this.mPlayerOperator.sendMessage(QTRadioService.this.mPlayerOperator.obtainMessage(QTRadioService.Operation.SEEK.ordinal(), paramAnonymousInt, 0));
    }

    public void setBufferTime(float paramAnonymousFloat)
      throws RemoteException
    {
    }

    public void setCategoryInfo(String paramAnonymousString1, String paramAnonymousString2)
      throws RemoteException
    {
    }

    public boolean setHttpProxy(String paramAnonymousString)
    {
      try
      {
        NativeEnv.setenv("http_proxy", paramAnonymousString, true);
        return true;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        Log.w("QTRadioService", "set env error");
      }
      return false;
    }

    public boolean setIsViaWoProxy(boolean paramAnonymousBoolean)
    {
      boolean bool = QTRadioService.isViaWoProxy;
      QTRadioService.access$3402(paramAnonymousBoolean);
      return bool;
    }

    public void setLocation(String paramAnonymousString1, String paramAnonymousString2)
      throws RemoteException
    {
      PlayLog.getInstance().setLocation(paramAnonymousString1, paramAnonymousString2);
    }

    public void setPlayingChannel(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, String paramAnonymousString1, String paramAnonymousString2, int paramAnonymousInt7)
      throws RemoteException
    {
      if ((QTRadioService.this.mChannelId != paramAnonymousInt2) || (QTRadioService.this.mThumb != paramAnonymousString1))
      {
        QTRadioService.access$2402(QTRadioService.this, paramAnonymousString1);
        QTRadioService.this.log("setplayingchannel: " + QTRadioService.this.mThumb);
      }
      while (true)
      {
        QTRadioService.access$2602(QTRadioService.this, paramAnonymousString2);
        QTRadioService.access$2702(QTRadioService.this, paramAnonymousInt1);
        QTRadioService.access$2302(QTRadioService.this, paramAnonymousInt2);
        QTRadioService.access$2502(QTRadioService.this, paramAnonymousInt3);
        QTRadioService.access$2802(QTRadioService.this, paramAnonymousInt5);
        QTRadioService.access$2202(QTRadioService.this, paramAnonymousInt6);
        QTRadioService.access$2902(QTRadioService.this, paramAnonymousInt7);
        QTRadioService.access$3002(QTRadioService.this, paramAnonymousInt4);
        QTRadioService.this.log("setPlayingChannel: " + QTRadioService.this.mCategoryId + " " + QTRadioService.this.mChannelId + " " + QTRadioService.this.mProgramId + " " + QTRadioService.this.mChannelType + " " + QTRadioService.this.mPlaySource);
        QTRadioService.this.refresh();
        return;
        if (QTRadioService.this.mProgramId == paramAnonymousInt3);
      }
    }

    public void setPlayingSourceList(String paramAnonymousString)
    {
    }

    public void setSource(String paramAnonymousString)
      throws RemoteException
    {
      QTRadioService.this.mOperationQueue.add(QTRadioService.Operation.SET_SOURCE);
      QTRadioService.this.mPlayerOperator.sendMessage(QTRadioService.this.mPlayerOperator.obtainMessage(QTRadioService.Operation.SET_SOURCE.ordinal(), paramAnonymousString));
    }

    public void setVolume(float paramAnonymousFloat)
      throws RemoteException
    {
    }

    public void startQuitTimer()
      throws RemoteException
    {
      QTRadioService.this.startTimer();
    }

    public void stop()
      throws RemoteException
    {
      QTRadioService.this.mDualSpectraPlayer.interrupt(true);
      QTRadioService.this.mOperationQueue.add(QTRadioService.Operation.STOP);
      QTRadioService.this.mPlayerOperator.sendEmptyMessage(QTRadioService.Operation.STOP.ordinal());
      QTRadioService.this.refreshNotifiationState(false);
    }

    public void stopQuitTimer()
      throws RemoteException
    {
      QTRadioService.this.stopTimer();
    }

    public boolean unsetHttpProxy()
    {
      try
      {
        NativeEnv.unsetenv("http_proxy");
        return true;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        Log.w("QTRadioService", "set env error");
      }
      return false;
    }
  };
  private int mCategoryId;
  private int mChannelId;
  private String mChannelName;
  private int mChannelType;
  private String mCity;
  private PendingIntent mContentIntent;
  private Context mContext;
  private DualSpectraPlayer mDualSpectraPlayer = new DualSpectraPlayer();
  private int mDuration;
  private Spectra.SpectraEventListener mEventListener = new Spectra.SpectraEventListener()
  {
    public void onSpectraEvent(Spectra paramAnonymousSpectra, Spectra.SpectraEvent paramAnonymousSpectraEvent)
    {
      switch (QTRadioService.6.$SwitchMap$rui$lin$spectra$Spectra$SpectraEvent[paramAnonymousSpectraEvent.ordinal()])
      {
      case 5:
      case 12:
      default:
      case 1:
      case 2:
      case 3:
      case 4:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      }
      do
      {
        return;
        QTRadioService.this.processID3v2Msg();
        return;
        QTRadioService.this.mPlayStatus.state = 4116;
        QTRadioService.this.sendMessage(QTRadioService.this.mPlayStatus);
        return;
        QTRadioService.this.mPlayStatus.state = 2;
        QTRadioService.this.mPlayStatusBak.state = QTRadioService.this.mPlayStatus.state;
        if (!QTRadioService.this.isLiveStream())
        {
          QTRadioService.this.sendIntent("eof", "");
          QTRadioService.this.log("recv eof, sendintent");
        }
        QTRadioService.this.sendMessage(QTRadioService.this.mPlayStatusBak);
        return;
        QTRadioService.this.mPlayStatus.state = 0;
        QTRadioService.this.sendMessage(QTRadioService.this.mPlayStatus);
        return;
        QTRadioService.this.mPlayStatus.state = 4096;
        QTRadioService.this.sendMessage(QTRadioService.this.mPlayStatus);
        return;
        QTRadioService.this.mPlayStatus.state = 4096;
        QTRadioService.this.sendMessage(QTRadioService.this.mPlayStatus);
        return;
        QTRadioService.this.mPlayStatus.state = 1;
        QTRadioService.this.sendMessage(QTRadioService.this.mPlayStatus);
        return;
        QTRadioService.this.mPlayStatus.state = 4113;
        QTRadioService.this.sendMessage(QTRadioService.this.mPlayStatus);
        return;
        QTRadioService.this.mPlayStatus.state = 4098;
        QTRadioService.this.sendMessage(QTRadioService.this.mPlayStatus);
        return;
      }
      while (QTRadioService.this.mDualSpectraPlayer.interrupt());
      QTRadioService.this.mPlayStatus.state = 16384;
      QTRadioService.this.sendMessage(QTRadioService.this.mPlayStatus);
    }
  };
  private MediaControlReceiver mMediaControlReceiver;
  private boolean mNewStyle = true;
  private Notification mNotification;
  private NotificationManager mNotificationManager;
  private ConcurrentLinkedQueue<Operation> mOperationQueue = new ConcurrentLinkedQueue();
  private PhoneStateReceiver mPhoneStateReceiver;
  private int mPlayId = 0;
  private List<Node> mPlayList;
  private PlayListReceiver mPlayListReceiver;
  private Node mPlayNode;
  private int mPlaySource = 13;
  private PlayStatus mPlayStatus;
  private PlayStatus mPlayStatusBak;
  private int mPlayedDuration = 0;
  private Handler mPlayerOperator;
  private HandlerThread mPlayerOperatorThread = new HandlerThread("PlayerOperatorThread");
  private int mPosition;
  private int mProgramId;
  private String mProgrameSchedule;
  private String mRegion;
  private SimpleImageloader mSimpleImageLoader;
  private final String mSpectraCrashLog = "/sdcard/.spectra_crash.log";
  private int mSpeed = 0;
  private long mStartPlay = 0L;
  private String mThumb;
  private int mUniqueId;
  private PlayLogHandler playLogHandler = null;
  private boolean remindIssued = false;
  private boolean startTimer = false;
  private HandlerThread t = new HandlerThread("play_log_thread");
  private TelephonyManager teleManager = null;
  private Handler updateHandler = new Handler();

  private int _queryPlayInfo(int paramInt)
  {
    if (this.mPlayNode == null)
      return 0;
    switch (paramInt)
    {
    case 1:
    case 2:
    case 3:
    default:
    case 4:
    case 6:
    case 5:
    }
    do
    {
      do
      {
        do
        {
          do
            return 0;
          while ((!this.mPlayNode.nodeName.equalsIgnoreCase("program")) || (((ProgramNode)this.mPlayNode).channelType != 1));
          return ((ProgramNode)this.mPlayNode).uniqueId;
          if (!this.mPlayNode.nodeName.equalsIgnoreCase("program"))
            break;
        }
        while (((ProgramNode)this.mPlayNode).channelType != 1);
        return ((ProgramNode)this.mPlayNode).channelType;
      }
      while (!this.mPlayNode.nodeName.equalsIgnoreCase("channel"));
      return ((ChannelNode)this.mPlayNode).channelType;
    }
    while ((!this.mPlayNode.nodeName.equalsIgnoreCase("program")) || (((ProgramNode)this.mPlayNode).channelType != 1));
    return ((ProgramNode)this.mPlayNode).getCurrPlayStatus();
  }

  private boolean autoPauseRadio()
  {
    this.playLogHandler.sendEmptyMessage(2);
    return this.mDualSpectraPlayer.pause();
  }

  private boolean autoStopRadio()
  {
    this.playLogHandler.sendEmptyMessage(2);
    return this.mDualSpectraPlayer.stop();
  }

  private void exitProcess()
  {
    this.playLogHandler.sendEmptyMessage(4);
  }

  private String getCacheByProgramNode(ProgramNode paramProgramNode)
  {
    if (paramProgramNode == null);
    do
    {
      do
        return null;
      while ((paramProgramNode.channelType != 1) || (paramProgramNode.getCurrPlayStatus() != 3));
      if (paramProgramNode.isDownloadProgram())
        return paramProgramNode.getSourceUrl();
    }
    while (!isCacheExisted(paramProgramNode.resId));
    String str1 = String.valueOf(paramProgramNode.resId);
    String str2 = "file://" + directory;
    String str3 = str2 + "/";
    String str4 = str3 + str1;
    return str4 + ".cache";
  }

  private PendingIntent getPlayIntent()
  {
    Intent localIntent = new Intent();
    localIntent.setAction("fm.qingting.qtradio.MEDIA_TOGGLE");
    localIntent.putExtra("type", "toggleplay");
    return PendingIntent.getBroadcast(this, 0, localIntent, 134217728);
  }

  private PendingIntent getPlayNextIntent()
  {
    Intent localIntent = new Intent();
    localIntent.setAction("fm.qingting.qtradio.MEAID_NEXT");
    localIntent.putExtra("type", "playnext");
    return PendingIntent.getBroadcast(this, 0, localIntent, 134217728);
  }

  private PendingIntent getPlayPreIntent()
  {
    Intent localIntent = new Intent();
    localIntent.setAction("fm.qingting.qtradio.MEAID_PRE");
    localIntent.putExtra("type", "playpre");
    return PendingIntent.getBroadcast(this, 0, localIntent, 134217728);
  }

  private String getSourceUrls()
  {
    ArrayList localArrayList = this.mDualSpectraPlayer.queryUrls();
    if (localArrayList != null)
    {
      Iterator localIterator = localArrayList.iterator();
      String str2;
      for (str1 = ""; localIterator.hasNext(); str1 = str1 + str2 + ";;")
        str2 = (String)localIterator.next();
    }
    String str1 = "";
    return str1;
  }

  private void init()
  {
    this.mPlayListReceiver = new PlayListReceiver();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.UPDATE_PLAY_INFO_QT");
    localIntentFilter.setPriority(1);
    registerReceiver(this.mPlayListReceiver, localIntentFilter);
  }

  private void initPlayInfo()
  {
    if (!PlayLog.getInstance().hasValidPlayInfo())
    {
      log("initplayinfo");
      setPlayInfo();
    }
  }

  @TargetApi(16)
  private void initRemoteControll()
  {
    if (this.mPlayNode == null);
    while (this.hasInitRemoteControll)
      return;
    this.hasInitRemoteControll = true;
    this.mNotificationManager = ((NotificationManager)getSystemService("notification"));
    Intent localIntent = new Intent(this, QTRadioActivity.class);
    localIntent.setFlags(536870912);
    this.mContentIntent = PendingIntent.getActivity(this, 0, localIntent, 0);
    this.mNewStyle = true;
    if (this.mNewStyle)
      if (QtApiLevelManager.isApiLevelSupported(16))
      {
        Notification.Builder localBuilder3 = new Notification.Builder(this);
        localBuilder3.setSmallIcon(2130837974).setContentIntent(this.mContentIntent).setWhen(864000000L + System.currentTimeMillis());
        this.mNotification = localBuilder3.build();
        this.mNotification.priority = 1;
        this.mNotification.flags = 34;
        RemoteViews localRemoteViews1 = new RemoteViews(getPackageName(), 2130903054);
        RemoteViews localRemoteViews2 = new RemoteViews(getPackageName(), 2130903056);
        localRemoteViews1.setTextViewText(2131230827, getString(2131492864));
        localRemoteViews1.setTextViewText(2131230828, "");
        localRemoteViews1.setOnClickPendingIntent(2131230824, getPlayNextIntent());
        localRemoteViews1.setOnClickPendingIntent(2131230756, getPlayIntent());
        localRemoteViews2.setTextViewText(2131230751, getString(2131492864));
        localRemoteViews2.setTextViewText(2131230752, "");
        localRemoteViews2.setOnClickPendingIntent(2131230757, getPlayNextIntent());
        localRemoteViews2.setOnClickPendingIntent(2131230756, getPlayIntent());
        localRemoteViews2.setOnClickPendingIntent(2131230755, getPlayPreIntent());
        this.mNotification.contentView = localRemoteViews1;
        this.mNotification.bigContentView = localRemoteViews2;
      }
    while (true)
    {
      startForeground(1234, this.mNotification);
      return;
      NotificationCompat.Builder localBuilder4 = new NotificationCompat.Builder(this);
      localBuilder4.setSmallIcon(2130837974).setContentTitle(getString(2131492864)).setWhen(System.currentTimeMillis()).setContentIntent(this.mContentIntent);
      this.mNotification = localBuilder4.build();
      continue;
      if (QtApiLevelManager.isApiLevelSupported(16))
      {
        Notification.Builder localBuilder1 = new Notification.Builder(this);
        localBuilder1.setSmallIcon(2130837974).setContentTitle(getString(2131492864)).setWhen(System.currentTimeMillis()).setContentIntent(this.mContentIntent);
        this.mNotification = localBuilder1.build();
      }
      else
      {
        NotificationCompat.Builder localBuilder2 = new NotificationCompat.Builder(this);
        localBuilder2.setSmallIcon(2130837974).setContentTitle(getString(2131492864)).setWhen(System.currentTimeMillis()).setContentIntent(this.mContentIntent);
        this.mNotification = localBuilder2.build();
      }
    }
  }

  private boolean isCacheExisted(int paramInt)
  {
    String str1 = String.valueOf(paramInt);
    String str2 = "" + directory;
    String str3 = str2 + "/";
    String str4 = str3 + str1;
    String str5 = str4 + ".cache";
    if (new File(str5).exists())
    {
      log("hit cache:" + str5);
      return true;
    }
    log("no cache:" + str5);
    return false;
  }

  private boolean isLiveStream()
  {
    return this.mDualSpectraPlayer.isLiveStream();
  }

  private boolean loadImage()
  {
    if (this.mPlayNode != null);
    return false;
  }

  private void log(String paramString)
  {
  }

  private boolean pauseRadio()
  {
    this.isPlayed = false;
    this.playLogHandler.sendEmptyMessage(2);
    return this.mDualSpectraPlayer.pause();
  }

  private void play(Node paramNode)
  {
    Object localObject;
    int i;
    if (paramNode != null)
    {
      if (!paramNode.nodeName.equalsIgnoreCase("channel"))
        break label67;
      localObject = ((ChannelNode)paramNode).getSourceUrl();
      i = ((ChannelNode)paramNode).channelId;
    }
    label66: label67: boolean bool;
    do
    {
      if (localObject != null)
      {
        stopRadio();
        setSourceUrls((String)localObject);
        playRadio();
        setPlayingId(i);
        this.mPlayNode = paramNode;
      }
      return;
      bool = paramNode.nodeName.equalsIgnoreCase("program");
      i = 0;
      localObject = null;
    }
    while (!bool);
    String str;
    if (((ProgramNode)paramNode).getCurrPlayStatus() == 3)
    {
      str = getCacheByProgramNode((ProgramNode)paramNode);
      if ((str != null) && (!str.equalsIgnoreCase("")))
        break label167;
      localObject = ((ProgramNode)paramNode).getSourceUrl();
    }
    while (true)
    {
      i = ((ProgramNode)paramNode).id;
      break;
      if (((ProgramNode)paramNode).getCurrPlayStatus() != 1)
        break label66;
      localObject = ((ProgramNode)paramNode).getSourceUrl();
      continue;
      label167: localObject = str;
    }
  }

  private void playNext()
  {
    if (this.mPlayNode != null)
    {
      if (this.mPlayNode.nextSibling == null)
        break label56;
      log("play next : " + this.mActivityHasDie);
      play(this.mPlayNode.nextSibling);
    }
    label56: 
    while (!this.mActivityHasDie)
      return;
    log("activityhasdie,ready to stopradio");
    stopRadio();
  }

  private void playPre()
  {
    if (this.mPlayNode != null)
      play(this.mPlayNode.prevSibling);
  }

  private boolean playRadio()
  {
    this.isPlayed = true;
    this.hasPlayed = true;
    this.playLogHandler.sendEmptyMessage(6);
    return this.mDualSpectraPlayer.play();
  }

  private void processID3v2Msg()
  {
    try
    {
      HashMap localHashMap = MetadataParser.parseID3v2(this.mDualSpectraPlayer.queryMetadata());
      if (localHashMap == null)
        return;
      Vector localVector1 = (Vector)localHashMap.get("T_QT_VERSION");
      if ((localVector1 != null) && (localVector1.size() > 0) && (((String)localVector1.get(0)).equals("qingting-ad-v0.1")))
      {
        Vector localVector2 = (Vector)localHashMap.get("T_QT_ID");
        if ((localVector2 != null) && (localVector2.size() > 0))
        {
          String str = (String)localVector2.get(0);
          if ((str != null) && (str.length() != 0))
          {
            Log.e("ID3V2", str);
            Intent localIntent = new Intent("fm.qingting.radio.qt_ad_appear");
            localIntent.putExtra("T_QT_ID", str);
            localIntent.setAction("fm.qingting.radio.qt_ad_appear");
            sendBroadcast(localIntent);
            return;
          }
        }
      }
    }
    catch (Exception localException)
    {
    }
  }

  private void refresh()
  {
    if (refreshPlayInfo())
    {
      initPlayInfo();
      refreshNotification(true);
      return;
    }
    refreshNotification(false);
  }

  @TargetApi(16)
  private void refreshBmp(Bitmap paramBitmap)
  {
    if ((paramBitmap == null) || (this.mNotification == null) || (this.mNotificationManager == null));
    while (!QtApiLevelManager.isApiLevelSupported(16))
      return;
    this.mNotification.bigContentView.setImageViewBitmap(2131230753, paramBitmap);
    this.mNotificationManager.notify(1234, this.mNotification);
  }

  @TargetApi(16)
  private void refreshNotifiationState(boolean paramBoolean)
  {
    initRemoteControll();
    if ((this.mNotification == null) || (this.mNotificationManager == null));
    do
    {
      do
      {
        return;
        if (!paramBoolean)
          break;
      }
      while (!QtApiLevelManager.isApiLevelSupported(16));
      if (!loadImage())
        this.mNotification.bigContentView.setImageViewResource(2131230753, 2130837898);
      this.mNotification.contentView.setImageViewResource(2131230756, 2130837874);
      this.mNotification.bigContentView.setImageViewResource(2131230756, 2130837874);
      this.mNotification.bigContentView.setImageViewResource(2131230757, 2130837873);
      this.mNotificationManager.notify(1234, this.mNotification);
      return;
    }
    while (!QtApiLevelManager.isApiLevelSupported(16));
    if (!loadImage())
      this.mNotification.bigContentView.setImageViewResource(2131230753, 2130837898);
    this.mNotification.contentView.setImageViewResource(2131230756, 2130837875);
    this.mNotification.bigContentView.setImageViewResource(2131230756, 2130837875);
    this.mNotification.bigContentView.setImageViewResource(2131230757, 2130837873);
    this.mNotification.bigContentView.setImageViewResource(2131230755, 2130837877);
    this.mNotificationManager.notify(1234, this.mNotification);
  }

  @TargetApi(16)
  private void refreshNotification(boolean paramBoolean)
  {
    initRemoteControll();
    if ((this.mNotification == null) || (this.mNotificationManager == null) || (!this.isStart) || (this.mPlayStatus == null))
      return;
    String str1;
    String str2;
    if (paramBoolean)
      if ((!this.mActivityHasDie) && (this.mChannelName != null) && (this.mProgrameSchedule != null))
      {
        str1 = this.mProgrameSchedule;
        str2 = this.mChannelName;
      }
    while (true)
    {
      if (QtApiLevelManager.isApiLevelSupported(16))
      {
        if (!loadImage())
          this.mNotification.bigContentView.setImageViewResource(2131230753, 2130837898);
        this.mNotification.contentView.setTextViewText(2131230827, str1);
        this.mNotification.bigContentView.setTextViewText(2131230751, str1);
        this.mNotification.contentView.setTextViewText(2131230828, str2);
        this.mNotification.bigContentView.setTextViewText(2131230752, str2);
      }
      while (true)
      {
        this.mNotificationManager.notify(1234, this.mNotification);
        return;
        if (this.mPlayNode != null)
        {
          if (this.mPlayNode.nodeName.equalsIgnoreCase("channel"))
          {
            str2 = ((ChannelNode)this.mPlayNode).title;
            str1 = "正在播放 ";
            break;
          }
          if (!this.mPlayNode.nodeName.equalsIgnoreCase("program"))
            break label301;
          str1 = ((ProgramNode)this.mPlayNode).title;
          str2 = this.mChannelName;
          if (str2 != null)
            break;
          str2 = "";
          break;
        }
        str1 = getString(2131492864);
        str2 = "";
        break;
        str1 = getString(2131492864);
        str2 = "";
        break;
        this.mNotification.setLatestEventInfo(this, str1, str2, this.mContentIntent);
      }
      label301: str1 = null;
      str2 = null;
    }
  }

  private boolean refreshPlayInfo()
  {
    if (this.mPlayList == null)
    {
      log("ready to restorefromdb: mplaylist == null");
      return false;
    }
    log("refreshPlayInfo:" + this.mPlayId);
    List localList = this.mPlayList;
    boolean bool = false;
    if (localList != null);
    for (int i = 0; ; i++)
    {
      int j = this.mPlayList.size();
      bool = false;
      Node localNode;
      if (i < j)
      {
        localNode = (Node)this.mPlayList.get(i);
        if (!localNode.nodeName.equalsIgnoreCase("channel"))
          break label153;
        if (this.mPlayId != ((ChannelNode)localNode).channelId)
          continue;
        this.mPlayNode = localNode;
      }
      for (bool = true; ; bool = true)
      {
        log("refresh mPlayNode:" + bool);
        return bool;
        label153: if ((!localNode.nodeName.equalsIgnoreCase("program")) || (this.mPlayId != ((ProgramNode)localNode).id))
          break;
        this.mPlayNode = localNode;
      }
    }
  }

  private void releaseReceiver()
  {
    try
    {
      if (this.mPlayListReceiver != null)
      {
        unregisterReceiver(this.mPlayListReceiver);
        this.mPlayListReceiver = null;
      }
      if (this.mPhoneStateReceiver != null)
      {
        unregisterReceiver(this.mPhoneStateReceiver);
        this.mPhoneStateReceiver = null;
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void remindCollection(int paramInt)
  {
    Intent localIntent = new Intent("fm.qingting.qtradio.collection_remind");
    localIntent.putExtra("collection_remind_channel_id", paramInt);
    sendBroadcast(localIntent);
  }

  private boolean resumeRadio()
  {
    this.isPlayed = true;
    this.playLogHandler.sendEmptyMessage(6);
    return this.mDualSpectraPlayer.resume();
  }

  private void savePlayedMeta()
  {
    if (this.mProgramId != 0)
    {
      this.mPosition = this.mDualSpectraPlayer.queryPosition();
      this.mDuration = this.mDualSpectraPlayer.queryDuration();
      GlobalCfg.getInstance(this.mContext).setPlayedMetaProgramId(String.valueOf(this.mProgramId));
      GlobalCfg.getInstance(this.mContext).setPlayedMetaProgramPos(this.mPosition);
      GlobalCfg.getInstance(this.mContext).setPlayedMetaProgramDuration(this.mDuration);
    }
  }

  private void sendIntent(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return;
    Intent localIntent = new Intent();
    localIntent.setAction("android.intent.action.UPDATE_PLAY_INFO_QT");
    localIntent.putExtra(paramString1, paramString2);
    sendOrderedBroadcast(localIntent, null);
  }

  private void sendMessage(PlayStatus paramPlayStatus)
  {
    Intent localIntent = new Intent();
    localIntent.setAction("android.intent.action.UPDATE_PLAY_STATUS");
    localIntent.putExtra("playstatus", paramPlayStatus);
    sendBroadcast(localIntent);
    switch (paramPlayStatus.state)
    {
    default:
    case 4098:
    case 4096:
      do
      {
        do
          return;
        while ((!this.isTimingSmoothPlaying) || (!this.isRelaxationPassed));
        this.isTimingSmoothPlaying = false;
        return;
      }
      while (this.isTimingSmoothPlaying);
      this.isTimingSmoothPlaying = true;
      this.isRelaxationPassed = false;
      new Thread()
      {
        public void run()
        {
          try
          {
            sleep(1000L);
            QTRadioService.access$3602(QTRadioService.this, true);
            return;
          }
          catch (InterruptedException localInterruptedException)
          {
            while (true)
              localInterruptedException.printStackTrace();
          }
        }
      }
      .start();
      return;
    case 0:
    }
    this.isTimingSmoothPlaying = false;
  }

  private void sendPlayLog()
  {
    try
    {
      if ((this.mPlayedDuration > 2) && (this.mPlayedDuration < 7200))
      {
        PlayLog.getInstance().sendPlayLog(this, this.mPlayedDuration);
        setPlayInfo();
      }
      this.mPlayedDuration = 0;
      return;
    }
    finally
    {
    }
  }

  private void setPlayInfo()
  {
    log("setplayinfo");
    if (this.mPlayNode != null)
    {
      PlayLog.getInstance().addSource(this.mPlaySource);
      if (!this.mPlayNode.nodeName.equalsIgnoreCase("program"))
        break label134;
      this.mChannelId = ((ProgramNode)this.mPlayNode).channelId;
      this.mProgramId = ((ProgramNode)this.mPlayNode).id;
      this.mUniqueId = ((ProgramNode)this.mPlayNode).uniqueId;
      this.mChannelType = ((ProgramNode)this.mPlayNode).channelType;
    }
    while (true)
    {
      log("setplayinfo,addCommnPlayLog");
      PlayLog.getInstance().addCommnPlayLog(this.mCategoryId, this.mChannelId, this.mSpeed, this.mProgramId, this.mUniqueId, this.mSpeed);
      return;
      label134: if (this.mPlayNode.nodeName.equalsIgnoreCase("channel"))
      {
        this.mChannelId = ((ChannelNode)this.mPlayNode).channelId;
        this.mCategoryId = ((ChannelNode)this.mPlayNode).categoryId;
        this.mChannelType = ((ChannelNode)this.mPlayNode).channelType;
        this.mProgramId = 0;
        this.mUniqueId = 0;
      }
    }
  }

  private void setPlayingId(int paramInt)
  {
    if (this.mPlayId == paramInt)
      return;
    this.mPlayId = paramInt;
    refresh();
  }

  private boolean setSourceUrls(String paramString)
  {
    if (paramString != null)
    {
      String[] arrayOfString = paramString.split(";;");
      ArrayList localArrayList = new ArrayList();
      int i = arrayOfString.length;
      int j = 0;
      if (j < i)
      {
        String str1 = arrayOfString[j];
        int k = str1.indexOf("://");
        String str2 = null;
        if (k > 0)
          str2 = str1.substring(0, k);
        if (str2 == null);
        String str3;
        while (true)
        {
          j++;
          break;
          str3 = str1.substring(k + 3);
          if ((str2.equals("rtspt")) || (str2.equals("rtsp")))
          {
            localArrayList.add("rtsp://" + str3);
            localArrayList.add("rtsp://" + str3);
            localArrayList.add("mmsh://" + str3);
            localArrayList.add("mmst://" + str3);
          }
          else if (str2.equals("mmsh"))
          {
            localArrayList.add("mmsh://" + str3);
            localArrayList.add("mmsh://" + str3);
            localArrayList.add("rtsp://" + str3);
            localArrayList.add("mmst://" + str3);
          }
          else if (str2.equals("mmst"))
          {
            localArrayList.add("mmst://" + str3);
            localArrayList.add("mmst://" + str3);
            localArrayList.add("rtsp://" + str3);
            localArrayList.add("mmsh://" + str3);
          }
          else if (str2.equals("mms"))
          {
            localArrayList.add("rtsp://" + str3);
            localArrayList.add("mmsh://" + str3);
            localArrayList.add("mmst://" + str3);
          }
          else
          {
            if (!str2.equals("rtmp"))
              break label619;
            localArrayList.add("rtmp://" + str3 + " live=1");
            localArrayList.add("rtmp://" + str3);
          }
        }
        label619: if ((isViaWoProxy) && ((str2.equals("http")) || (str2.equals("https"))))
        {
          int m = str1.indexOf("?");
          if (m <= 0)
            break label745;
          new StringBuilder().append(str1.substring(m)).append("&proxy=unicom").toString();
        }
        label745: 
        while (true)
        {
          str1 = str2 + "://" + WoInfo.proxyPasswd + "@" + str3 + "&proxy=unicom";
          localArrayList.add(str1);
          break;
        }
      }
      return this.mDualSpectraPlayer.load(localArrayList);
    }
    return false;
  }

  private void smartPlayRadio()
  {
    if ((isLiveStream()) || (!this.hasPlayed))
    {
      playRadio();
      return;
    }
    resumeRadio();
  }

  private void smartStopRadio()
  {
    if (isLiveStream())
    {
      stopRadio();
      return;
    }
    pauseRadio();
  }

  private void startTimer()
  {
    if (this.startTimer)
      return;
    this.startTimer = true;
    this.handler.postDelayed(this.clockRunnable, 10000L);
  }

  private boolean stopRadio()
  {
    this.isPlayed = false;
    this.hasPlayed = false;
    this.playLogHandler.sendEmptyMessage(2);
    return this.mDualSpectraPlayer.stop();
  }

  private void stopTimer()
  {
    if (this.startTimer)
    {
      GlobalCfg.getInstance(this.mContext).setQuitTime(9223372036854775807L);
      this.startTimer = false;
    }
  }

  private void updatePlayList(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      log("ready to restorefromdb: " + this.mPlayId);
      PlayListManager.getInstance().restoreFromDB();
      this.mPlayList = PlayListManager.getInstance().getPlayList();
      return;
    }
    this.updateHandler.removeCallbacks(this.doUpdate);
    this.updateHandler.postDelayed(this.doUpdate, 3000L);
  }

  private void writeLogHeartBeat()
  {
    try
    {
      if ((!this.checked) && (this.mPlayedDuration >= CollectionAttentionSeconds) && (CollectionAttentionSeconds > 10) && (!this.remindIssued))
      {
        remindCollection(this.mChannelId);
        this.remindIssued = true;
        this.checked = true;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }

  // ERROR //
  @TargetApi(16)
  public void onCreate()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: invokespecial 1086	android/app/Service:onCreate	()V
    //   6: new 145	java/io/File
    //   9: dup
    //   10: ldc 164
    //   12: invokespecial 755	java/io/File:<init>	(Ljava/lang/String;)V
    //   15: astore_2
    //   16: aload_2
    //   17: invokevirtual 758	java/io/File:exists	()Z
    //   20: ifeq +45 -> 65
    //   23: new 1088	java/io/BufferedReader
    //   26: dup
    //   27: new 1090	java/io/FileReader
    //   30: dup
    //   31: aload_2
    //   32: invokespecial 1093	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   35: invokespecial 1096	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   38: astore 13
    //   40: aload 13
    //   42: invokevirtual 1099	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   45: astore_1
    //   46: aload 13
    //   48: invokevirtual 1102	java/io/BufferedReader:close	()V
    //   51: aload_2
    //   52: invokevirtual 1105	java/io/File:delete	()Z
    //   55: pop
    //   56: aload_1
    //   57: ifnull +8 -> 65
    //   60: aload_0
    //   61: aload_1
    //   62: invokestatic 1111	com/umeng/analytics/MobclickAgent:reportError	(Landroid/content/Context;Ljava/lang/String;)V
    //   65: aload_0
    //   66: invokevirtual 1115	fm/qingting/qtradio/QTRadioService:getResources	()Landroid/content/res/Resources;
    //   69: ldc_w 1116
    //   72: invokevirtual 1122	android/content/res/Resources:openRawResource	(I)Ljava/io/InputStream;
    //   75: astore 12
    //   77: invokestatic 1127	fm/qingting/framework/data/ServerConfig:getInstance	()Lfm/qingting/framework/data/ServerConfig;
    //   80: aload 12
    //   82: invokevirtual 1131	fm/qingting/framework/data/ServerConfig:setServerConfig	(Ljava/io/InputStream;)V
    //   85: invokestatic 1136	fm/qingting/qtradio/data/DBManager:getInstance	()Lfm/qingting/qtradio/data/DBManager;
    //   88: aload_0
    //   89: invokevirtual 1138	fm/qingting/qtradio/data/DBManager:init	(Landroid/content/Context;)V
    //   92: invokestatic 1143	fm/qingting/framework/data/DataManager:getInstance	()Lfm/qingting/framework/data/DataManager;
    //   95: invokestatic 1148	fm/qingting/qtradio/data/CommonDS:getInstance	()Lfm/qingting/qtradio/data/CommonDS;
    //   98: invokevirtual 1152	fm/qingting/framework/data/DataManager:addDataSource	(Lfm/qingting/framework/data/IDataSource;)V
    //   101: invokestatic 1143	fm/qingting/framework/data/DataManager:getInstance	()Lfm/qingting/framework/data/DataManager;
    //   104: invokestatic 1157	fm/qingting/qtradio/data/PlayListDS:getInstance	()Lfm/qingting/qtradio/data/PlayListDS;
    //   107: invokevirtual 1152	fm/qingting/framework/data/DataManager:addDataSource	(Lfm/qingting/framework/data/IDataSource;)V
    //   110: invokestatic 1143	fm/qingting/framework/data/DataManager:getInstance	()Lfm/qingting/framework/data/DataManager;
    //   113: invokestatic 1162	fm/qingting/qtradio/data/MediaCenterDS:getInstance	()Lfm/qingting/qtradio/data/MediaCenterDS;
    //   116: invokevirtual 1152	fm/qingting/framework/data/DataManager:addDataSource	(Lfm/qingting/framework/data/IDataSource;)V
    //   119: invokestatic 1167	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   122: aload_0
    //   123: invokevirtual 1170	fm/qingting/qtradio/model/InfoManager:setContext	(Landroid/content/Context;)V
    //   126: invokestatic 1167	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   129: invokevirtual 1173	fm/qingting/qtradio/model/InfoManager:initDataCenter	()V
    //   132: aload_0
    //   133: new 1175	fm/qingting/qtradio/simpleImageloader/SimpleImageloader
    //   136: dup
    //   137: invokespecial 1176	fm/qingting/qtradio/simpleImageloader/SimpleImageloader:<init>	()V
    //   140: putfield 1178	fm/qingting/qtradio/QTRadioService:mSimpleImageLoader	Lfm/qingting/qtradio/simpleImageloader/SimpleImageloader;
    //   143: invokestatic 1183	fm/qingting/qtradio/log/LogModule:getInstance	()Lfm/qingting/qtradio/log/LogModule;
    //   146: aload_0
    //   147: invokevirtual 1184	fm/qingting/qtradio/log/LogModule:init	(Landroid/content/Context;)V
    //   150: aload_0
    //   151: getfield 203	fm/qingting/qtradio/QTRadioService:mPlayerOperatorThread	Landroid/os/HandlerThread;
    //   154: invokevirtual 1185	android/os/HandlerThread:start	()V
    //   157: aload_0
    //   158: new 1187	fm/qingting/qtradio/QTRadioService$PlayerOperator
    //   161: dup
    //   162: aload_0
    //   163: aload_0
    //   164: getfield 203	fm/qingting/qtradio/QTRadioService:mPlayerOperatorThread	Landroid/os/HandlerThread;
    //   167: invokevirtual 1191	android/os/HandlerThread:getLooper	()Landroid/os/Looper;
    //   170: invokespecial 1194	fm/qingting/qtradio/QTRadioService$PlayerOperator:<init>	(Lfm/qingting/qtradio/QTRadioService;Landroid/os/Looper;)V
    //   173: putfield 333	fm/qingting/qtradio/QTRadioService:mPlayerOperator	Landroid/os/Handler;
    //   176: aload_0
    //   177: getfield 215	fm/qingting/qtradio/QTRadioService:mNewStyle	Z
    //   180: ifeq +66 -> 246
    //   183: bipush 16
    //   185: invokestatic 647	fm/qingting/qtradio/manager/QtApiLevelManager:isApiLevelSupported	(I)Z
    //   188: ifeq +58 -> 246
    //   191: aload_0
    //   192: new 1196	fm/qingting/qtradio/notification/MediaControlReceiver
    //   195: dup
    //   196: invokespecial 1197	fm/qingting/qtradio/notification/MediaControlReceiver:<init>	()V
    //   199: putfield 1199	fm/qingting/qtradio/QTRadioService:mMediaControlReceiver	Lfm/qingting/qtradio/notification/MediaControlReceiver;
    //   202: new 585	android/content/IntentFilter
    //   205: dup
    //   206: invokespecial 586	android/content/IntentFilter:<init>	()V
    //   209: astore 10
    //   211: aload 10
    //   213: ldc_w 525
    //   216: invokevirtual 591	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   219: aload 10
    //   221: ldc_w 547
    //   224: invokevirtual 591	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   227: aload 10
    //   229: ldc_w 552
    //   232: invokevirtual 591	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   235: aload_0
    //   236: aload_0
    //   237: getfield 1199	fm/qingting/qtradio/QTRadioService:mMediaControlReceiver	Lfm/qingting/qtradio/notification/MediaControlReceiver;
    //   240: aload 10
    //   242: invokevirtual 598	fm/qingting/qtradio/QTRadioService:registerReceiver	(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;
    //   245: pop
    //   246: aload_0
    //   247: aload_0
    //   248: ldc_w 1201
    //   251: invokevirtual 623	fm/qingting/qtradio/QTRadioService:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   254: checkcast 1203	android/telephony/TelephonyManager
    //   257: putfield 168	fm/qingting/qtradio/QTRadioService:teleManager	Landroid/telephony/TelephonyManager;
    //   260: aload_0
    //   261: getfield 168	fm/qingting/qtradio/QTRadioService:teleManager	Landroid/telephony/TelephonyManager;
    //   264: new 1205	fm/qingting/qtradio/QTRadioService$MyPhoneStateListener
    //   267: dup
    //   268: aload_0
    //   269: invokespecial 1206	fm/qingting/qtradio/QTRadioService$MyPhoneStateListener:<init>	(Lfm/qingting/qtradio/QTRadioService;)V
    //   272: bipush 32
    //   274: invokevirtual 1210	android/telephony/TelephonyManager:listen	(Landroid/telephony/PhoneStateListener;I)V
    //   277: aload_0
    //   278: new 1212	fm/qingting/qtradio/QTRadioService$PhoneStateReceiver
    //   281: dup
    //   282: aload_0
    //   283: aconst_null
    //   284: invokespecial 1215	fm/qingting/qtradio/QTRadioService$PhoneStateReceiver:<init>	(Lfm/qingting/qtradio/QTRadioService;Lfm/qingting/qtradio/QTRadioService$1;)V
    //   287: putfield 905	fm/qingting/qtradio/QTRadioService:mPhoneStateReceiver	Lfm/qingting/qtradio/QTRadioService$PhoneStateReceiver;
    //   290: new 585	android/content/IntentFilter
    //   293: dup
    //   294: invokespecial 586	android/content/IntentFilter:<init>	()V
    //   297: astore 8
    //   299: aload 8
    //   301: ldc_w 1217
    //   304: invokevirtual 591	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   307: aload 8
    //   309: ldc_w 1219
    //   312: invokevirtual 591	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   315: aload_0
    //   316: aload_0
    //   317: getfield 905	fm/qingting/qtradio/QTRadioService:mPhoneStateReceiver	Lfm/qingting/qtradio/QTRadioService$PhoneStateReceiver;
    //   320: aload 8
    //   322: invokevirtual 598	fm/qingting/qtradio/QTRadioService:registerReceiver	(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;
    //   325: pop
    //   326: aload_0
    //   327: invokespecial 1221	fm/qingting/qtradio/QTRadioService:init	()V
    //   330: aload_0
    //   331: new 952	fm/qingting/qtradio/fm/PlayStatus
    //   334: dup
    //   335: iconst_0
    //   336: invokespecial 1223	fm/qingting/qtradio/fm/PlayStatus:<init>	(I)V
    //   339: putfield 484	fm/qingting/qtradio/QTRadioService:mPlayStatus	Lfm/qingting/qtradio/fm/PlayStatus;
    //   342: aload_0
    //   343: new 952	fm/qingting/qtradio/fm/PlayStatus
    //   346: dup
    //   347: iconst_0
    //   348: invokespecial 1223	fm/qingting/qtradio/fm/PlayStatus:<init>	(I)V
    //   351: putfield 299	fm/qingting/qtradio/QTRadioService:mPlayStatusBak	Lfm/qingting/qtradio/fm/PlayStatus;
    //   354: aload_0
    //   355: getfield 187	fm/qingting/qtradio/QTRadioService:mDualSpectraPlayer	Lrui/lin/spectra/DualSpectraPlayer;
    //   358: aload_0
    //   359: getfield 194	fm/qingting/qtradio/QTRadioService:mEventListener	Lrui/lin/spectra/Spectra$SpectraEventListener;
    //   362: invokevirtual 1227	rui/lin/spectra/DualSpectraPlayer:addEventListener	(Lrui/lin/spectra/Spectra$SpectraEventListener;)Z
    //   365: pop
    //   366: aload_0
    //   367: iconst_1
    //   368: putfield 178	fm/qingting/qtradio/QTRadioService:isStart	Z
    //   371: aload_0
    //   372: aload_0
    //   373: putfield 407	fm/qingting/qtradio/QTRadioService:mContext	Landroid/content/Context;
    //   376: aload_0
    //   377: getfield 248	fm/qingting/qtradio/QTRadioService:t	Landroid/os/HandlerThread;
    //   380: invokevirtual 1185	android/os/HandlerThread:start	()V
    //   383: aload_0
    //   384: new 486	fm/qingting/qtradio/QTRadioService$PlayLogHandler
    //   387: dup
    //   388: aload_0
    //   389: aload_0
    //   390: getfield 248	fm/qingting/qtradio/QTRadioService:t	Landroid/os/HandlerThread;
    //   393: invokevirtual 1191	android/os/HandlerThread:getLooper	()Landroid/os/Looper;
    //   396: invokespecial 1228	fm/qingting/qtradio/QTRadioService$PlayLogHandler:<init>	(Lfm/qingting/qtradio/QTRadioService;Landroid/os/Looper;)V
    //   399: putfield 250	fm/qingting/qtradio/QTRadioService:playLogHandler	Lfm/qingting/qtradio/QTRadioService$PlayLogHandler;
    //   402: aload_0
    //   403: invokestatic 930	fm/qingting/qtradio/model/GlobalCfg:getInstance	(Landroid/content/Context;)Lfm/qingting/qtradio/model/GlobalCfg;
    //   406: ldc_w 1230
    //   409: invokevirtual 1234	fm/qingting/qtradio/model/GlobalCfg:getValueFromDB	(Ljava/lang/String;)Ljava/lang/String;
    //   412: astore 6
    //   414: aload 6
    //   416: ifnull +22 -> 438
    //   419: aload 6
    //   421: ldc_w 566
    //   424: invokevirtual 271	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   427: ifne +11 -> 438
    //   430: aload 6
    //   432: invokestatic 1239	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   435: putstatic 161	fm/qingting/qtradio/QTRadioService:CollectionAttentionSeconds	I
    //   438: return
    //   439: astore 15
    //   441: aload 15
    //   443: invokevirtual 1240	java/io/FileNotFoundException:printStackTrace	()V
    //   446: goto -390 -> 56
    //   449: astore 14
    //   451: aload 14
    //   453: invokevirtual 1241	java/io/IOException:printStackTrace	()V
    //   456: goto -400 -> 56
    //   459: astore_3
    //   460: aload_3
    //   461: invokevirtual 908	java/lang/Exception:printStackTrace	()V
    //   464: goto -314 -> 150
    //   467: astore 7
    //   469: return
    //   470: astore 4
    //   472: goto -146 -> 326
    //
    // Exception table:
    //   from	to	target	type
    //   23	56	439	java/io/FileNotFoundException
    //   23	56	449	java/io/IOException
    //   65	150	459	java/lang/Exception
    //   430	438	467	java/lang/Exception
    //   246	326	470	java/lang/Exception
  }

  public void onDestroy()
  {
    this.isStart = false;
    this.mDualSpectraPlayer.stop();
    this.mDualSpectraPlayer.removeEventListener(this.mEventListener);
    if (this.mNotificationManager != null)
      this.mNotificationManager.cancel(1234);
    if ((this.mNewStyle) && (QtApiLevelManager.isApiLevelSupported(16)) && (this.mMediaControlReceiver != null))
      unregisterReceiver(this.mMediaControlReceiver);
    stopSelf();
    releaseReceiver();
    super.onDestroy();
    exitProcess();
  }

  public boolean onRecvBitmap(Bitmap paramBitmap)
  {
    if (paramBitmap != null);
    try
    {
      refreshBmp(paramBitmap);
      label9: return false;
    }
    catch (Exception localException)
    {
      break label9;
    }
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    if ((paramIntent != null) && (paramIntent.getAction() != null) && (paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.START_SERVICE")))
    {
      Bundle localBundle = paramIntent.getExtras();
      if (localBundle != null)
      {
        Set localSet = localBundle.keySet();
        if ((localSet != null) && (localSet.size() > 0))
        {
          Iterator localIterator = localSet.iterator();
          while (localIterator.hasNext())
          {
            String str = (String)localIterator.next();
            sendIntent(str, localBundle.getString(str));
          }
        }
      }
    }
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }

  public boolean onUnbind(Intent paramIntent)
  {
    return super.onUnbind(paramIntent);
  }

  class MyPhoneStateListener extends PhoneStateListener
  {
    MyPhoneStateListener()
    {
    }

    public void onCallStateChanged(int paramInt, String paramString)
    {
      switch (paramInt)
      {
      default:
        if (QTRadioService.this.isLiveStream())
          QTRadioService.this.autoStopRadio();
        break;
      case 0:
      case 1:
      case 2:
      }
      while (true)
      {
        super.onCallStateChanged(paramInt, paramString);
        return;
        if (QTRadioService.this.isPlayed)
          if (QTRadioService.this.isLiveStream())
          {
            QTRadioService.this.playRadio();
          }
          else
          {
            QTRadioService.this.resumeRadio();
            continue;
            if (QTRadioService.this.isLiveStream())
            {
              QTRadioService.this.autoStopRadio();
            }
            else
            {
              QTRadioService.this.autoPauseRadio();
              continue;
              if (QTRadioService.this.isLiveStream())
              {
                QTRadioService.this.autoStopRadio();
              }
              else
              {
                QTRadioService.this.autoPauseRadio();
                continue;
                QTRadioService.this.autoPauseRadio();
              }
            }
          }
      }
    }
  }

  private static enum Operation
  {
    static
    {
      PLAY = new Operation("PLAY", 1);
      STOP = new Operation("STOP", 2);
      PAUSE = new Operation("PAUSE", 3);
      RESUME = new Operation("RESUME", 4);
      SEEK = new Operation("SEEK", 5);
      RECONNECT = new Operation("RECONNECT", 6);
      Operation[] arrayOfOperation = new Operation[7];
      arrayOfOperation[0] = SET_SOURCE;
      arrayOfOperation[1] = PLAY;
      arrayOfOperation[2] = STOP;
      arrayOfOperation[3] = PAUSE;
      arrayOfOperation[4] = RESUME;
      arrayOfOperation[5] = SEEK;
      arrayOfOperation[6] = RECONNECT;
    }
  }

  private class PhoneStateReceiver extends BroadcastReceiver
  {
    private PhoneStateReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ((paramIntent == null) || (paramIntent.getAction() == null));
      do
      {
        TelephonyManager localTelephonyManager;
        do
        {
          return;
          if (paramIntent.getAction().equalsIgnoreCase("android.intent.action.NEW_OUTGOING_CALL"))
          {
            if (QTRadioService.this.isLiveStream())
            {
              QTRadioService.this.autoStopRadio();
              return;
            }
            QTRadioService.this.autoPauseRadio();
            return;
          }
          localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
        }
        while (localTelephonyManager == null);
        switch (localTelephonyManager.getCallState())
        {
        default:
          return;
        case 0:
        case 1:
        case 2:
        }
      }
      while (!QTRadioService.this.isPlayed);
      if (QTRadioService.this.isLiveStream())
      {
        QTRadioService.this.playRadio();
        return;
        if (QTRadioService.this.isLiveStream())
        {
          QTRadioService.this.autoStopRadio();
          return;
        }
        QTRadioService.this.autoPauseRadio();
        return;
        if (QTRadioService.this.isLiveStream())
        {
          QTRadioService.this.autoStopRadio();
          return;
        }
        QTRadioService.this.autoPauseRadio();
        return;
      }
      QTRadioService.this.resumeRadio();
    }
  }

  class PlayListReceiver extends BroadcastReceiver
  {
    PlayListReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      Bundle localBundle = paramIntent.getExtras();
      Iterator localIterator = localBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        if (str1 != null)
          if (str1.equalsIgnoreCase("playid"))
          {
            QTRadioService.access$4302(QTRadioService.this, false);
            String str5 = (String)localBundle.get(str1);
            if (str5 != null)
            {
              QTRadioService.this.log("PlayListReceiver,playid: " + str5);
              QTRadioService.this.setPlayingId(Integer.valueOf(str5).intValue());
            }
          }
          else if (str1.equalsIgnoreCase("setplaynode"))
          {
            QTRadioService.this.updatePlayList(false);
            String str4 = (String)localBundle.get(str1);
            if (str4 != null)
            {
              QTRadioService.this.log("PlayListReceiver,setplaynode: " + str4);
              QTRadioService.this.setPlayingId(Integer.valueOf(str4).intValue());
              QTRadioService.this.play(QTRadioService.this.mPlayNode);
              QTRadioService.this.refreshNotifiationState(true);
            }
          }
          else if (str1.equalsIgnoreCase("setplaynextnode"))
          {
            QTRadioService.this.updatePlayList(false);
            String str3 = (String)localBundle.get(str1);
            if (str3 == null)
            {
              if ((QTRadioService.this.mPlayNode != null) && (QTRadioService.this.mPlayNode.nextSibling != null))
                if (QTRadioService.this.mPlayNode.nextSibling.nodeName.equalsIgnoreCase("program"))
                {
                  QTRadioService.this.setPlayingId(((ProgramNode)QTRadioService.this.mPlayNode).id);
                  label322: QTRadioService.this.play(QTRadioService.this.mPlayNode.nextSibling);
                }
            }
            else
              while (true)
              {
                QTRadioService.this.refreshNotifiationState(true);
                break;
                if (!QTRadioService.this.mPlayNode.nextSibling.nodeName.equalsIgnoreCase("channel"))
                  break label322;
                QTRadioService.this.setPlayingId(((ChannelNode)QTRadioService.this.mPlayNode).channelId);
                break label322;
                QTRadioService.this.log("PlayListReceiver,setplaynextnode: " + str3);
                QTRadioService.this.setPlayingId(Integer.valueOf(str3).intValue());
                QTRadioService.this.play(QTRadioService.this.mPlayNode);
              }
          }
          else if (str1.equalsIgnoreCase("setplaychannelnode"))
          {
            QTRadioService.this.updatePlayList(false);
            String str2 = (String)localBundle.get(str1);
            if (str2 != null)
            {
              QTRadioService.this.log("PlayListReceiver,setplaychannelnode: " + str2);
              QTRadioService.this.setPlayingId(Integer.valueOf(str2).intValue());
              QTRadioService.this.play(QTRadioService.this.mPlayNode);
              QTRadioService.this.refreshNotifiationState(true);
            }
          }
          else if (str1.equalsIgnoreCase("playlistupdate"))
          {
            QTRadioService.this.updatePlayList(true);
            QTRadioService.this.refresh();
            QTRadioService.this.log("PlayListReceiver,playlistupdate: ");
          }
          else if (str1.equalsIgnoreCase("playnext"))
          {
            QTRadioService.access$4302(QTRadioService.this, true);
            QTRadioService.this.playNext();
            QTRadioService.this.log("PlayListReceiver,playnext");
          }
          else if (str1.equalsIgnoreCase("playpre"))
          {
            QTRadioService.access$4302(QTRadioService.this, true);
            QTRadioService.this.playPre();
            QTRadioService.this.log("PlayListReceiver,playpre");
          }
          else if (str1.equalsIgnoreCase("eof"))
          {
            QTRadioService.access$4302(QTRadioService.this, true);
            QTRadioService.this.playNext();
            QTRadioService.this.log("PlayListReceiver,eof");
          }
          else if (str1.equalsIgnoreCase("toggleplay"))
          {
            QTRadioService.access$4302(QTRadioService.this, true);
            if (QTRadioService.this.isPlayed)
            {
              QTRadioService.this.smartStopRadio();
              QTRadioService.this.refreshNotifiationState(false);
            }
            while (true)
            {
              QTRadioService.this.log("PlayListReceiver,toggleplay");
              break;
              QTRadioService.this.smartPlayRadio();
              QTRadioService.this.refreshNotifiationState(true);
            }
          }
      }
    }
  }

  public class PlayLogHandler extends Handler
  {
    public PlayLogHandler(Looper arg2)
    {
      super();
    }

    public void handleMessage(Message paramMessage)
    {
      if (paramMessage == null)
        return;
      switch (paramMessage.what)
      {
      case 7:
      case 5:
      default:
        return;
      case 2:
        QTRadioService.this.log("STOP_PLAY: " + QTRadioService.this.mStartPlay);
        if (QTRadioService.this.mStartPlay > 0L)
        {
          QTRadioService.access$5202(QTRadioService.this, (int)(System.currentTimeMillis() / 1000L - QTRadioService.this.mStartPlay));
          QTRadioService.access$5102(QTRadioService.this, 0L);
        }
        QTRadioService.this.sendPlayLog();
        return;
      case 6:
        QTRadioService.this.log("START_PLAY");
        if (QTRadioService.this.mStartPlay > 0L)
        {
          QTRadioService.access$5202(QTRadioService.this, (int)(System.currentTimeMillis() / 1000L - QTRadioService.this.mStartPlay));
          QTRadioService.access$5102(QTRadioService.this, 0L);
        }
        QTRadioService.this.sendPlayLog();
        QTRadioService.access$5102(QTRadioService.this, System.currentTimeMillis() / 1000L);
        return;
      case 3:
        QTRadioService.this.log("PLAY_ERROR: " + QTRadioService.this.mStartPlay);
        if (QTRadioService.this.mStartPlay > 0L)
        {
          QTRadioService.access$5202(QTRadioService.this, (int)(System.currentTimeMillis() / 1000L - QTRadioService.this.mStartPlay));
          QTRadioService.access$5102(QTRadioService.this, 0L);
        }
        QTRadioService.this.sendPlayLog();
        return;
      case 4:
      }
      QTRadioService.this.log("EXIT_PLAYER: " + QTRadioService.this.mStartPlay);
      if (QTRadioService.this.mStartPlay > 0L)
      {
        QTRadioService.access$5202(QTRadioService.this, (int)(System.currentTimeMillis() / 1000L - QTRadioService.this.mStartPlay));
        QTRadioService.access$5102(QTRadioService.this, 0L);
      }
      QTRadioService.this.sendPlayLog();
      Process.killProcess(Process.myPid());
    }
  }

  private class PlayerOperator extends Handler
  {
    public PlayerOperator(Looper arg2)
    {
      super();
    }

    public void handleMessage(Message paramMessage)
    {
      QTRadioService.this.mOperationQueue.poll();
      int i;
      switch (paramMessage.what)
      {
      default:
        return;
      case 0:
        Iterator localIterator = QTRadioService.this.mOperationQueue.iterator();
        while (localIterator.hasNext())
        {
          QTRadioService.Operation localOperation = (QTRadioService.Operation)localIterator.next();
          switch (QTRadioService.6.$SwitchMap$fm$qingting$qtradio$QTRadioService$Operation[localOperation.ordinal()])
          {
          default:
            break;
          case 1:
            i = 1;
          case 2:
          }
        }
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      }
      while (i == 0)
      {
        QTRadioService.this.setSourceUrls((String)paramMessage.obj);
        return;
        QTRadioService.this.setSourceUrls((String)paramMessage.obj);
        i = 1;
        continue;
        if ((QTRadioService.this.playRadio()) || (QTRadioService.this.mDualSpectraPlayer.interrupt()))
          break;
        QTRadioService.this.sendMessage(new PlayStatus(8192));
        return;
        QTRadioService.this.mDualSpectraPlayer.interrupt(false);
        QTRadioService.this.stopRadio();
        return;
        QTRadioService.this.pauseRadio();
        return;
        QTRadioService.this.resumeRadio();
        return;
        QTRadioService.this.mDualSpectraPlayer.seek(paramMessage.arg1);
        return;
        QTRadioService.this.mDualSpectraPlayer.reconnect();
        return;
        i = 0;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.QTRadioService
 * JD-Core Version:    0.6.2
 */