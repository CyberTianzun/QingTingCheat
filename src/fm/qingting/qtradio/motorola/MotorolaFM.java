package fm.qingting.qtradio.motorola;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.util.Log;
import com.motorola.android.fmradio.IFMRadioService;
import com.motorola.android.fmradio.IFMRadioService.Stub;
import com.motorola.android.fmradio.IFMRadioServiceCallback;
import com.motorola.android.fmradio.IFMRadioServiceCallback.Stub;
import com.motorola.android.fmradio.Preferences;
import fm.qingting.qtradio.fmdriver.FMDriver;
import fm.qingting.qtradio.fmdriver.FMcontrol;
import fm.qingting.qtradio.fmdriver.IFMEventListener;
import java.util.ArrayList;

public class MotorolaFM extends FMDriver
{
  private static final String ACTION_AUDIOPATH_BUSY = "android.intent.action.AudioPathBusy";
  private static final String ACTION_AUDIOPATH_FREE = "android.intent.action.AudioPathFree";
  public static final String ACTION_FM_COMMAND = "com.motorola.fmradio.SERVICE_COMMAND";
  public static int FM_ROUTING_HEADSET = 0;
  public static int FM_ROUTING_SPEAKER = 0;
  public static int FM_ROUTING_SPEAKER_ONLY = 0;
  private static final String LAUNCH_KEY = "FM_launch";
  private static final String LAUNCH_VALUE_OFF = "off";
  private static final String LAUNCH_VALUE_ON = "on";
  private static final int MSG_ABORT_COMPLETE = 7;
  private static final int MSG_RDS_PS_UPDATE = 9;
  private static final int MSG_RDS_PTY_UPDATE = 11;
  private static final int MSG_RDS_RT_UPDATE = 10;
  private static final int MSG_RESTORE_AUDIO_AFTER_FOCUS_LOSS = 12;
  private static final int MSG_RISS = 15;
  private static final int MSG_SCAN_COMPLETE = 6;
  private static final int MSG_SCAN_UPDATE = 4;
  private static final int MSG_SEEK_CHANNEL = 1;
  private static final int MSG_SEEK_COMPLETE = 5;
  private static final int MSG_SET_ROUTING = 13;
  private static final int MSG_SHOW_NOTICE = 2;
  private static final int MSG_SHUTDOWN = 14;
  private static final int MSG_TUNE_COMPLETE = 3;
  private static final int MSG_UPDATE_AUDIOMODE = 8;
  private static final String ROUTING_KEY = "FM_routing";
  private static final String ROUTING_VALUE_HEADSET = "DEVICE_OUT_WIRED_HEADPHONE";
  private static final String ROUTING_VALUE_SPEAKER = "DEVICE_OUT_SPEAKER";
  private static final String TAG = "MotorolaFM";
  private int RISS = 1;
  private boolean available = false;
  private ArrayList<Integer> channels = new ArrayList();
  private String cmdtype = "";
  private IFMEventListener listener;
  private AudioManager mAM;
  private int mAudioMode = 0;
  private int mAudioRouting = FM_ROUTING_HEADSET;
  private boolean mBound = false;
  protected IFMRadioServiceCallback mCallback = new IFMRadioServiceCallback.Stub()
  {
    public void onCommandComplete(int paramAnonymousInt1, int paramAnonymousInt2, String paramAnonymousString)
      throws RemoteException
    {
      Log.v("MotorolaFM", "Got radio service event: cmd " + paramAnonymousInt1 + " status " + paramAnonymousInt2 + " value " + paramAnonymousString);
      switch (paramAnonymousInt1)
      {
      case 8:
      default:
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 9:
      case 10:
      case 15:
      case 23:
      case 20:
        do
        {
          do
          {
            do
            {
              do
              {
                return;
                Message localMessage12 = Message.obtain(MotorolaFM.this.mHandler, 3, paramAnonymousInt2, Integer.parseInt(paramAnonymousString), null);
                MotorolaFM.this.mHandler.sendMessage(localMessage12);
                return;
                Message localMessage11 = Message.obtain(MotorolaFM.this.mHandler, 5, paramAnonymousInt2, Integer.parseInt(paramAnonymousString), null);
                MotorolaFM.this.mHandler.sendMessage(localMessage11);
                return;
                Message localMessage10 = Message.obtain(MotorolaFM.this.mHandler, 6, paramAnonymousInt2, 0, null);
                MotorolaFM.this.mHandler.sendMessage(localMessage10);
                return;
                Message localMessage9 = Message.obtain(MotorolaFM.this.mHandler, 7, paramAnonymousInt2, Integer.parseInt(paramAnonymousString), null);
                MotorolaFM.this.mHandler.sendMessage(localMessage9);
                return;
                Message localMessage8 = Message.obtain(MotorolaFM.this.mHandler, 9, paramAnonymousString);
                MotorolaFM.this.mHandler.sendMessage(localMessage8);
                return;
                Message localMessage7 = Message.obtain(MotorolaFM.this.mHandler, 10, paramAnonymousString);
                MotorolaFM.this.mHandler.sendMessage(localMessage7);
                return;
              }
              while (!MotorolaFM.this.mUSBand);
              String str = MotorolaFM.this.mIFMRadioService.getRDSStationName();
              Message localMessage6 = Message.obtain(MotorolaFM.this.mHandler, 9, str);
              MotorolaFM.this.mHandler.sendMessage(localMessage6);
              return;
              int i = Integer.parseInt(paramAnonymousString);
              if (MotorolaFM.this.mUSBand);
              for (int j = 32; ; j = 0)
              {
                int k = i + j;
                Message localMessage5 = Message.obtain(MotorolaFM.this.mHandler, 11, k, 0, null);
                MotorolaFM.this.mHandler.sendMessage(localMessage5);
                return;
              }
            }
            while (paramAnonymousInt2 != 0);
            MotorolaFM.this.notifyEnableChangeComplete(true, false);
            return;
            MotorolaFM.this.handlePowerOff();
            return;
            Message localMessage4 = Message.obtain(MotorolaFM.this.mHandler, 8, Integer.parseInt(paramAnonymousString), 0, null);
            MotorolaFM.this.mHandler.sendMessage(localMessage4);
          }
          while ((!MotorolaFM.this.mState.isInitializing()) || (!MotorolaFM.this.mState.isInitializing()) || (MotorolaFM.this.enableRds()));
          MotorolaFM.this.notifyTuneResult(false);
          return;
        }
        while (!MotorolaFM.this.mState.isInitializing());
        Log.d("MotorolaFM", "Finished powering on the FM radio");
        MotorolaFM.this.mAM.setParameters("FM_launch=on");
        MotorolaFM.this.audioPrepare(MotorolaFM.this.mAudioRouting);
        MotorolaFM.this.transitionToState(MotorolaFM.State.PLAYING);
        MotorolaFM.this.notifyEnableChangeComplete(true, true);
        return;
      case 24:
        Message localMessage3 = Message.obtain(MotorolaFM.this.mHandler, 8, Integer.parseInt(paramAnonymousString), 0, null);
        MotorolaFM.this.mHandler.sendMessage(localMessage3);
        return;
      case 25:
        Message localMessage2 = Message.obtain(MotorolaFM.this.mHandler, 4, Integer.parseInt(paramAnonymousString), 0, null);
        MotorolaFM.this.mHandler.sendMessage(localMessage2);
        return;
      case 16:
        Message localMessage1 = Message.obtain(MotorolaFM.this.mHandler, 15, Integer.parseInt(paramAnonymousString), 0, null);
        MotorolaFM.this.mHandler.sendMessage(localMessage1);
        return;
      case 11:
      case 12:
      case 13:
      case 14:
      case 17:
      case 18:
      case 19:
      case 21:
      case 22:
      }
      Log.e("MotorolaFM", "report error");
    }
  };
  protected ServiceConnection mConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      Log.v("MotorolaFM", "Connected to FM radio service");
      MotorolaFM.this.mIFMRadioService = IFMRadioService.Stub.asInterface(paramAnonymousIBinder);
      try
      {
        MotorolaFM.this.mInUse = true;
        MotorolaFM.this.registerBroadcastReceiver();
        MotorolaFM.this.mIFMRadioService.registerCallback(MotorolaFM.this.mCallback);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        Log.e("MotorolaFM", "Could not register radio service callbacks", localRemoteException);
      }
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      try
      {
        MotorolaFM.this.mIFMRadioService.unregisterCallback(MotorolaFM.this.mCallback);
        MotorolaFM.this.mInUse = false;
        MotorolaFM.this.mIFMRadioService = null;
        MotorolaFM.this.handlePowerOff();
        Log.v("MotorolaFM", "Disconnected from FM radio service");
        return;
      }
      catch (RemoteException localRemoteException)
      {
        while (true)
          Log.e("MotorolaFM", "Unregistering radio service callbacks failed", localRemoteException);
      }
    }
  };
  private Context mContext;
  private int mCurFreq = 0;
  private int mCurrentVolum = 0;
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      int i = 1;
      switch (paramAnonymousMessage.what)
      {
      case 11:
      case 14:
      default:
      case 1:
      case 2:
      case 3:
      case 4:
      case 6:
      case 5:
      case 7:
      case 8:
      case 9:
      case 10:
      case 15:
      case 12:
      case 13:
      }
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                return;
                try
                {
                  MotorolaFM.this.mIFMRadioService.seek(paramAnonymousMessage.arg1);
                  return;
                }
                catch (RemoteException localRemoteException)
                {
                  Log.e("MotorolaFM", "Seeking failed", localRemoteException);
                  MotorolaFM.this.notifySeekResult(false);
                  return;
                }
                Log.e("MotorolaFM", String.valueOf(paramAnonymousMessage.arg1));
                return;
                MotorolaFM localMotorolaFM2 = MotorolaFM.this;
                if (paramAnonymousMessage.arg1 != 0);
                while (true)
                {
                  localMotorolaFM2.handleTuneComplete(i, paramAnonymousMessage.arg2);
                  if ((MotorolaFM.this.listener == null) || (MotorolaFM.this.listener == null))
                    break;
                  MotorolaFM.this.listener.onTune(MotorolaFM.this.mCurFreq);
                  return;
                  i = 0;
                }
                MotorolaFM.this.mScanning = i;
                Log.e("MotorolaFM", "report scan update  " + String.valueOf(paramAnonymousMessage.arg1));
                MotorolaFM.this.channels.add(Integer.valueOf(paramAnonymousMessage.arg1));
              }
              while (MotorolaFM.this.listener == null);
              MotorolaFM.this.listener.onChannelFound(paramAnonymousMessage.arg1);
              return;
              Log.e("MotorolaFM", " report scan complete");
              MotorolaFM.this.mScanning = false;
            }
            while (MotorolaFM.this.listener == null);
            MotorolaFM.this.listener.onScanComplete(i);
            return;
            StringBuilder localStringBuilder = new StringBuilder("Seek completed, success ");
            int k = paramAnonymousMessage.arg1;
            boolean bool = false;
            if (k != 0)
              bool = i;
            Log.v("MotorolaFM", bool + " frequency " + MotorolaFM.this.mCurFreq);
            MotorolaFM.this.notifySeekResult(i);
            return;
            if (paramAnonymousMessage.arg1 == 0)
            {
              MotorolaFM.this.notifyTuneResult(false);
              return;
            }
            if (paramAnonymousMessage.arg1 != i)
              break;
          }
          while (MotorolaFM.this.listener == null);
          MotorolaFM.this.listener.onScanComplete(i);
          return;
          Log.e("MotorolaFM", "report abort complete  " + String.valueOf(paramAnonymousMessage.arg2));
          return;
          MotorolaFM.this.mAudioMode = paramAnonymousMessage.arg1;
          Log.e("MotorolaFM", "report audio mode change " + String.valueOf(paramAnonymousMessage.arg2));
          return;
          ((String)paramAnonymousMessage.obj);
          return;
          ((String)paramAnonymousMessage.obj);
          return;
          MotorolaFM.this.RISS = paramAnonymousMessage.arg1;
          return;
          MotorolaFM.this.setFMMuteState(false);
          MotorolaFM localMotorolaFM1 = MotorolaFM.this;
          if (MotorolaFM.this.mAudioRouting == MotorolaFM.FM_ROUTING_HEADSET);
          for (int j = MotorolaFM.FM_ROUTING_SPEAKER; ; j = MotorolaFM.FM_ROUTING_HEADSET)
          {
            localMotorolaFM1.audioPrepare(j);
            MotorolaFM.this.audioPrepare(MotorolaFM.this.mAudioRouting);
            MotorolaFM.this.setFMVolume(Preferences.getVolume(MotorolaFM.this.mContext));
            return;
          }
        }
        while ((paramAnonymousMessage.arg1 != MotorolaFM.FM_ROUTING_HEADSET) && (paramAnonymousMessage.arg1 != MotorolaFM.FM_ROUTING_SPEAKER));
        MotorolaFM.this.mAudioRouting = paramAnonymousMessage.arg1;
      }
      while (!MotorolaFM.this.mState.isActive());
      MotorolaFM.this.audioPrepare(MotorolaFM.this.mAudioRouting);
    }
  };
  private int mHeadsetState = -1;
  private IFMRadioService mIFMRadioService = null;
  private boolean mInUse = false;
  private boolean mIsBound = false;
  private boolean mLostAudioFocus = false;
  private boolean mMuted = false;
  private BroadcastReceiver mReceiver = null;
  private boolean mScanning = false;
  private State mState = State.POWERDOWN;
  private boolean mUSBand = false;
  private PowerManager.WakeLock mWakeLock;
  private Class<?> motorolaClass;
  private Class motorolaService;

  public MotorolaFM(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    ((Activity)this.mContext).setVolumeControlStream(1);
    this.mAM = ((AudioManager)paramContext.getSystemService("audio"));
    try
    {
      this.motorolaService = Class.forName("com.motorola.android.fmradio.IFMRadioService", true, paramContext.createPackageContext("com.motorola.android.fmradio", 3).getClassLoader());
      this.available = true;
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
      return;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
  }

  private void audioPrepare(int paramInt)
  {
    if (paramInt == FM_ROUTING_SPEAKER);
    for (String str = "DEVICE_OUT_SPEAKER"; ; str = "DEVICE_OUT_WIRED_HEADPHONE")
    {
      Log.d("MotorolaFM", "Setting FM audio routing to " + str);
      this.mAM.setParameters("FM_routing=" + str);
      return;
    }
  }

  private boolean enableRds()
  {
    try
    {
      if (this.mIFMRadioService.getBand() == 0)
      {
        bool2 = true;
        this.mUSBand = bool2;
        label21: StringBuilder localStringBuilder = new StringBuilder("Enabling RDS in ");
        if (!this.mUSBand)
          break label116;
        str = "RBDS";
        label44: Log.v("MotorolaFM", str + " mode");
      }
    }
    catch (RemoteException localRemoteException1)
    {
      try
      {
        boolean bool2;
        String str;
        IFMRadioService localIFMRadioService = this.mIFMRadioService;
        if (this.mUSBand);
        for (int i = 1; ; i = 0)
        {
          boolean bool1 = localIFMRadioService.setRdsEnable(true, i);
          return bool1;
          bool2 = false;
          break;
          localRemoteException1 = localRemoteException1;
          Log.e("MotorolaFM", "Could not determine FM radio band", localRemoteException1);
          break label21;
          label116: str = "RDS";
          break label44;
        }
      }
      catch (RemoteException localRemoteException2)
      {
        Log.e("MotorolaFM", "Enabling RDS failed", localRemoteException2);
      }
    }
    return false;
  }

  private void handlePowerOff()
  {
    Log.v("MotorolaFM", "FM radio hardware powered down");
    transitionToState(State.POWERDOWN);
    if (!this.mInUse)
      shutdownFM();
  }

  private void handleTuneComplete(boolean paramBoolean, int paramInt)
  {
    Log.v("MotorolaFM", "FM tune complete, success " + paramBoolean + " frequency " + paramInt);
    if (!paramBoolean)
    {
      notifyTuneResult(false);
      return;
    }
    if (this.mState.isInitializing())
    {
      Log.v("MotorolaFM", "Finished first tuning, initializing volume");
      try
      {
        this.mIFMRadioService.getAudioMode();
        this.mAM.setStreamVolume(1, Preferences.getVolume(this.mContext), 0);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        while (true)
        {
          Log.e("MotorolaFM", "Failed getting audio mode", localRemoteException);
          this.mAudioMode = 0;
          notifyTuneResult(false);
        }
      }
    }
    notifyTuneResult(true);
  }

  private void notifyEnableChangeComplete(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1)
    {
      if (this.cmdtype.equalsIgnoreCase("scan"));
      do
      {
        fmscan();
        do
          return;
        while (!this.cmdtype.equalsIgnoreCase("tune"));
        this.listener.onFMOn();
        fmtune(this.mCurFreq);
        getRISS();
      }
      while (!this.mMuted);
      setFMMuteState(false);
      return;
    }
    this.listener.onFMOff();
  }

  private void notifySeekResult(boolean paramBoolean)
  {
    Log.e("MotorolaFM", "report seek result " + String.valueOf(paramBoolean) + String.valueOf(this.mCurFreq));
  }

  private void notifyTuneResult(boolean paramBoolean)
  {
    Log.e("MotorolaFM", "report tune change" + String.valueOf(paramBoolean) + String.valueOf(this.mCurFreq));
  }

  private void registerBroadcastReceiver()
  {
    if (this.mReceiver != null)
      return;
    this.mReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        String str = paramAnonymousIntent.getAction();
        Log.d("MotorolaFM", "Received broadcast: " + str);
        if (str.equals("android.intent.action.AudioPathFree"))
        {
          Log.v("MotorolaFM", "Audio path is available again");
          MotorolaFM.this.setFMMuteState(false);
          return;
        }
        if (str.equals("android.intent.action.AudioPathBusy"))
        {
          Log.d("MotorolaFM", "Audio path is busy");
          MotorolaFM.this.setFMMuteState(true);
          return;
        }
        if (str.equals("android.media.VOLUME_CHANGED_ACTION"))
        {
          int j = paramAnonymousIntent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
          Log.d("MotorolaFM", "Received FM volume change intent, setting volume to " + j);
          Preferences.setVolume(MotorolaFM.this.mContext, j);
          MotorolaFM.this.setFMVolume(j);
          return;
        }
        int i = paramAnonymousIntent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
        Log.d("MotorolaFM", "Received FM volume change intent, setting volume to " + i);
        Preferences.setVolume(MotorolaFM.this.mContext, i);
        MotorolaFM.this.setFMVolume(i);
      }
    };
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
    localIntentFilter.addAction("android.intent.action.AudioPathFree");
    localIntentFilter.addAction("android.intent.action.AudioPathBusy");
    this.mContext.registerReceiver(this.mReceiver, localIntentFilter);
  }

  private void restoreAudioRoute()
  {
    this.mAM.setParameters("FM_launch=off");
    this.mAM.setMode(0);
  }

  private boolean setFMFrequency(int paramInt)
  {
    try
    {
      boolean bool = this.mIFMRadioService.tune(paramInt);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      Log.e("MotorolaFM", "Tuning failed", localRemoteException);
    }
    return false;
  }

  private void setFMMuteState(boolean paramBoolean)
  {
    Log.v("MotorolaFM", "setFMMuteState (" + paramBoolean + ")");
    while (true)
    {
      try
      {
        IFMRadioService localIFMRadioService = this.mIFMRadioService;
        int i;
        if (!paramBoolean)
          if (this.mLostAudioFocus)
          {
            break label83;
            localIFMRadioService.setMute(i);
            this.mMuted = paramBoolean;
          }
          else
          {
            i = 0;
            continue;
          }
      }
      catch (RemoteException localRemoteException)
      {
        Log.e("MotorolaFM", "Setting FM mute state failed", localRemoteException);
        return;
      }
      label83: i = 1;
    }
  }

  private void setFMVolume(int paramInt)
  {
    Log.v("MotorolaFM", "setFMVolume (" + paramInt + ")");
    try
    {
      this.mIFMRadioService.setVolume(paramInt);
      this.mCurrentVolum = paramInt;
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.e("MotorolaFM", "Setting FM volume failed", localRemoteException);
    }
  }

  private void shutdownFM()
  {
    if (this.mBound)
    {
      this.mContext.unbindService(this.mConnection);
      this.mBound = false;
    }
    if (!this.mState.isIdle())
    {
      restoreAudioRoute();
      transitionToState(State.POWERDOWN);
    }
    notifyEnableChangeComplete(false, true);
  }

  private boolean startupFM()
  {
    if (!this.mState.isIdle())
      return true;
    this.mBound = this.mContext.bindService(new Intent("com.motorola.android.fmradio.FMRADIO_SERVICE"), this.mConnection, 1);
    if (!this.mBound)
    {
      Log.w("MotorolaFM", "Powering on FM radio failed");
      this.mHandler.sendEmptyMessage(14);
      return false;
    }
    transitionToState(State.POWERING_UP);
    return true;
  }

  private void transitionToState(State paramState)
  {
    if (this.mState != paramState)
    {
      Log.v("MotorolaFM", "Transitioning state: " + this.mState + " -> " + paramState);
      this.mState = paramState;
    }
  }

  public void cancelScanning()
  {
    if (isScanning())
      stopScan();
  }

  public boolean fmscan()
  {
    Log.d("MotorolaFM", "Got scan request");
    if (this.mState.isActive())
      try
      {
        setFMMuteState(true);
        boolean bool = this.mIFMRadioService.scan();
        return bool;
      }
      catch (RemoteException localRemoteException)
      {
        Log.e("MotorolaFM", "Initiating scan failed", localRemoteException);
      }
    return false;
  }

  public boolean fmtune(int paramInt)
  {
    Log.d("MotorolaFM", "Got tune request, frequency " + paramInt);
    boolean bool1 = this.mState.isActive();
    boolean bool2 = false;
    if (bool1)
      bool2 = setFMFrequency(paramInt);
    return bool2;
  }

  public int getAudioRouting()
  {
    if ((!FMcontrol.getInstance().isHeadsetConnected()) && (this.mAudioRouting == FM_ROUTING_SPEAKER))
      return FM_ROUTING_SPEAKER_ONLY;
    return this.mAudioRouting;
  }

  public ArrayList<Integer> getAvailableChannels()
  {
    return this.channels;
  }

  public int getCurrentChannel()
  {
    return this.mCurFreq;
  }

  public int getCurrentRSSI()
  {
    if (this.mState.isActive())
      getRISS();
    return this.RISS;
  }

  public String getName()
  {
    return "MotorolaFM";
  }

  public boolean getRISS()
  {
    if (this.mState.isActive())
      try
      {
        boolean bool = this.mIFMRadioService.getRSSI();
        return bool;
      }
      catch (RemoteException localRemoteException)
      {
        Log.e("MotorolaFM", "Stopping seek failed", localRemoteException);
      }
    return false;
  }

  public int getVolume()
    throws Exception
  {
    return this.mCurrentVolum;
  }

  public boolean isAvailable()
  {
    return this.available;
  }

  public boolean isHeadsetPlugged()
  {
    return FMcontrol.getInstance().isHeadsetConnected();
  }

  public boolean isMute()
    throws Exception
  {
    return this.mMuted;
  }

  public boolean isOn()
  {
    return this.mState.isActive();
  }

  public boolean isPaused()
    throws Exception
  {
    return isMute();
  }

  public boolean isScanning()
  {
    return this.mScanning;
  }

  public boolean isSpeakerOn()
    throws Exception
  {
    return false;
  }

  public void mute(boolean paramBoolean)
    throws Exception
  {
    setFMMuteState(paramBoolean);
  }

  public void pause()
    throws Exception
  {
    if (this.mState.isActive())
      setFMMuteState(true);
  }

  public void powerOff()
  {
    Log.d("MotorolaFM", "Got FM radio power off request");
    if (this.mBound)
    {
      this.mContext.unbindService(this.mConnection);
      this.mBound = false;
    }
    handlePowerOff();
  }

  public boolean powerOn()
  {
    Log.d("MotorolaFM", "Got FM radio power on request");
    if (this.mState.isInitializing())
      return true;
    if (this.mState.isActive())
    {
      this.mHandler.post(new Runnable()
      {
        public void run()
        {
          MotorolaFM.this.notifyEnableChangeComplete(true, true);
        }
      });
      return true;
    }
    return startupFM();
  }

  public void registerFMEventListener(IFMEventListener paramIFMEventListener)
  {
    this.listener = paramIFMEventListener;
  }

  public void scan()
  {
    this.cmdtype = "scan";
    powerOn();
  }

  public boolean seek(int paramInt, boolean paramBoolean)
  {
    Log.d("MotorolaFM", "Got seek request, frequency " + paramInt + " upward " + paramBoolean);
    if (this.mState.isActive())
    {
      Handler localHandler = this.mHandler;
      if (paramBoolean);
      for (int i = 0; ; i = 1)
      {
        Message localMessage = Message.obtain(localHandler, 1, i, 0, null);
        this.mHandler.sendMessage(localMessage);
        return true;
      }
    }
    return false;
  }

  public void setAudioRouting(int paramInt)
  {
    Log.d("MotorolaFM", "Got request for setting audio routing to " + paramInt);
    Message localMessage = Message.obtain(this.mHandler, 13, paramInt, 0, null);
    this.mHandler.sendMessage(localMessage);
  }

  public void setLiveAudioQualityCallback(boolean paramBoolean, int paramInt)
  {
  }

  public int setSpeakerOn(boolean paramBoolean)
  {
    return 0;
  }

  public void setVolume(int paramInt)
    throws Exception
  {
    if (this.mState.isActive())
      setFMVolume(paramInt);
  }

  public boolean stopScan()
  {
    Log.d("MotorolaFM", "Got stop scan request");
    if (this.mState.isActive())
      try
      {
        boolean bool = this.mIFMRadioService.stopScan();
        return bool;
      }
      catch (RemoteException localRemoteException)
      {
        Log.e("MotorolaFM", "Stopping scan failed", localRemoteException);
      }
    return false;
  }

  public boolean stopSeek()
  {
    Log.d("MotorolaFM", "Got stop seek request");
    if (this.mState.isActive())
      try
      {
        boolean bool = this.mIFMRadioService.stopSeek();
        return bool;
      }
      catch (RemoteException localRemoteException)
      {
        Log.e("MotorolaFM", "Stopping seek failed", localRemoteException);
      }
    return false;
  }

  public int tune(int paramInt)
  {
    this.cmdtype = "tune";
    this.mCurFreq = paramInt;
    powerOn();
    if (this.mMuted)
      setFMMuteState(false);
    return 0;
  }

  public void turnOff()
  {
    powerOff();
  }

  public int turnOn()
  {
    powerOn();
    return 0;
  }

  public void unregisterFMEventListener()
  {
    try
    {
      if (this.mReceiver != null)
      {
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mReceiver = null;
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private static enum State
  {
    static
    {
      PLAYING = new State("PLAYING", 2);
      State[] arrayOfState = new State[3];
      arrayOfState[0] = POWERDOWN;
      arrayOfState[1] = POWERING_UP;
      arrayOfState[2] = PLAYING;
    }

    public boolean isActive()
    {
      return this == PLAYING;
    }

    public boolean isIdle()
    {
      return this == POWERDOWN;
    }

    public boolean isInitializing()
    {
      return this == POWERING_UP;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.motorola.MotorolaFM
 * JD-Core Version:    0.6.2
 */