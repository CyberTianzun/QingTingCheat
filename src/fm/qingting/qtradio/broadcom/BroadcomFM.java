package fm.qingting.qtradio.broadcom;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import fm.qingting.qtradio.fmdriver.FMDriver;
import fm.qingting.qtradio.fmdriver.IFMEventListener;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class BroadcomFM extends FMDriver
{
  public static final int AF_MODE_DEFAULT = 0;
  public static final int AF_MODE_OFF = 0;
  public static final int AF_MODE_ON = 1;
  public static final int AUDIO_MODE_AUTO = 0;
  public static final int AUDIO_MODE_BLEND = 3;
  public static final int AUDIO_MODE_MONO = 2;
  public static final int AUDIO_MODE_STEREO = 1;
  public static final int AUDIO_MODE_SWITCH = 3;
  public static final int AUDIO_PATH_DIGITAL = 3;
  public static final int AUDIO_PATH_NONE = 0;
  public static final int AUDIO_PATH_SPEAKER = 1;
  public static final int AUDIO_PATH_WIRE_HEADSET = 2;
  public static final int AUDIO_QUALITY_BLEND = 4;
  public static final int AUDIO_QUALITY_MONO = 2;
  public static final int AUDIO_QUALITY_STEREO = 1;
  public static final int FREQ_STEP_50KHZ = 16;
  public static final int FREQ_STEP_DEFAULT = 0;
  public static final int FREQ_STEP_UNIT_WAITING_TIMEKHZ = 0;
  public static final int MIN_SIGNAL_STRENGTH_DEFAULT = 105;
  private static final int RADIO_OP_STATE_NONE = 0;
  private static final int RADIO_OP_STATE_STAGE_1 = 1;
  private static final int RADIO_OP_STATE_STAGE_2 = 2;
  private static final int RADIO_OP_STATE_STAGE_3 = 3;
  private static final int RADIO_OP_STATE_STAGE_4 = 4;
  private static final int RADIO_OP_STATE_STAGE_5 = 5;
  private static final int RADIO_STATE_BUSY = 4;
  private static final int RADIO_STATE_OFF = 0;
  private static final int RADIO_STATE_READY_FOR_COMMAND = 2;
  private static final int RADIO_STATE_SEEK_STATION = 5;
  private static final int RADIO_STATE_STARTING = 1;
  private static final int RADIO_STATE_STOPPING = 3;
  public static final int SCAN_MODE_DOWN = 0;
  public static final int SCAN_MODE_FAST = 129;
  public static final int SCAN_MODE_FULL = 130;
  public static final int SCAN_MODE_NORMAL = 0;
  public static final int SCAN_MODE_UP = 128;
  public static final int SIGNAL_POLL_INTERVAL_DEFAULT = 100;
  public static final String TAG = "BroadcomFM";
  private static int UNIT_WAITING_TIME;
  private static int[] Volume;
  static Class<?> mBluetoothProxyManagerCls;
  static Method mFinish;
  static Class<?> mFmReceiverCls;
  static Method mGetStatus;
  static Class<?> mIBluetoothProxyCallbackCls;
  static Class<?> mIFmReceiverEventHandlerCls;
  static Method mMuteAudio;
  private static boolean mReflectionSucceeded;
  static Method mRegisterEventHandler;
  static Method mSeekStation;
  static Method mSeekStationAbort;
  static Method mSetAudioMode;
  static Method mSetAudioPath;
  static Method mSetFMVolume;
  static Method mSetLiveAudioPolling;
  static Method mTuneRadio;
  static Method mTurnOffRadio;
  static Method mTurnOnRadio;
  static Method mUnregisterEventHandler;
  ArrayList<Integer> channels = new ArrayList();
  int mAudioMode = 1;
  int mAudioPath = 2;
  Object mFmReceiver;
  int mFreq;
  IFMEventListener mIFMEventListener;
  Object mIFmReceiverEventHandler;
  InvocationHandler mInvocationHandler = new InvocationHandler()
  {
    public Object invoke(Object paramAnonymousObject, Method paramAnonymousMethod, Object[] paramAnonymousArrayOfObject)
      throws Throwable
    {
      String str = paramAnonymousMethod.getName();
      try
      {
        if (!str.equals("onStatusEvent"))
          break label285;
        Log.e("BroadcomFM", "onStatusEvent: freq=" + (Integer)paramAnonymousArrayOfObject[0] + " rssi=" + (Integer)paramAnonymousArrayOfObject[1] + " radioIsOn=" + (Boolean)paramAnonymousArrayOfObject[2] + " isMute=" + (Boolean)paramAnonymousArrayOfObject[7]);
        BroadcomFM.this.mFreq = (10 * ((Integer)paramAnonymousArrayOfObject[0]).intValue());
        BroadcomFM.this.mRssi = ((Integer)paramAnonymousArrayOfObject[1]).intValue();
        if (((Boolean)paramAnonymousArrayOfObject[2]).booleanValue())
        {
          if (BroadcomFM.this.mRadioStatus == 1)
          {
            BroadcomFM.this.mRadioStatus = 2;
            if (BroadcomFM.this.mIFMEventListener == null)
              break label724;
            BroadcomFM.this.mIFMEventListener.onFMOn();
            return null;
          }
          BroadcomFM.this.mRadioStatus = 2;
          return null;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      BroadcomFM.this.mRadioOperatorThread.mRadioOperator.removeCallbacksAndMessages(null);
      BroadcomFM.this.mRadioStatus = 0;
      BroadcomFM.mUnregisterEventHandler.invoke(BroadcomFM.this.mFmReceiver, new Object[0]);
      BroadcomFM.mFinish.invoke(BroadcomFM.this.mFmReceiver, new Object[0]);
      BroadcomFM.this.mFmReceiver = null;
      if (BroadcomFM.this.mIFMEventListener != null)
      {
        BroadcomFM.this.mIFMEventListener.onFMOff();
        return null;
        label285: if (str.equals("onLiveAudioQualityEvent"))
        {
          Log.e("BroadcomFM", "onLiveAudioQualityEvent: rssi=" + (Integer)paramAnonymousArrayOfObject[0]);
          BroadcomFM.this.mRssi = ((Integer)paramAnonymousArrayOfObject[0]).intValue();
          if (BroadcomFM.this.mIFMEventListener != null)
          {
            BroadcomFM.this.mIFMEventListener.onAudioQualityStatus(((Integer)paramAnonymousArrayOfObject[0]).intValue());
            return null;
          }
        }
        else
        {
          if (str.equals("onSeekCompleteEvent"))
          {
            Log.e("BroadcomFM", "onSeekComplete: freq=" + (Integer)paramAnonymousArrayOfObject[0] + "  rssi=" + (Integer)paramAnonymousArrayOfObject[1] + "  seeksuccess=" + (Boolean)paramAnonymousArrayOfObject[2]);
            if (BroadcomFM.this.mIFMEventListener != null)
              BroadcomFM.this.mIFMEventListener.onChannelFound(10 * ((Integer)paramAnonymousArrayOfObject[0]).intValue());
            if ((((Integer)paramAnonymousArrayOfObject[0]).intValue() >= 10800) && (BroadcomFM.this.mRadioStatus == 5))
            {
              BroadcomFM.this.mRadioStatus = 2;
              if (BroadcomFM.this.mIFMEventListener != null)
                BroadcomFM.this.mIFMEventListener.onScanComplete(true);
            }
            BroadcomFM.this.channels.add(Integer.valueOf(10 * ((Integer)paramAnonymousArrayOfObject[0]).intValue()));
            return null;
          }
          if (str.equals("onAudioPathEvent"))
          {
            Log.e("BroadcomFM", "onAudioPathEvent: audio_path=" + (Integer)paramAnonymousArrayOfObject[0]);
            BroadcomFM.this.mRadioStatus = 2;
            return null;
          }
          if (str.equals("onAudioModeEvent"))
          {
            Log.e("BroadcomFM", "onAudioModeEvent: audio_mode=" + (Integer)paramAnonymousArrayOfObject[0]);
            BroadcomFM.this.mAudioMode = ((Integer)paramAnonymousArrayOfObject[0]).intValue();
            BroadcomFM.this.mRadioStatus = 2;
            return null;
          }
          if (str.equals("onVolumeEvent"))
          {
            Log.e("BroadcomFM", "onVolumeEvent: paramInt1=" + (Integer)paramAnonymousArrayOfObject[0] + " paramInt2=" + (Integer)paramAnonymousArrayOfObject[1]);
            BroadcomFM.this.mRadioStatus = 2;
          }
        }
      }
      label724: return null;
    }
  };
  boolean mIsMute = false;
  boolean mIsPaused = false;
  RadioOperatorThread mRadioOperatorThread = new RadioOperatorThread();
  volatile int mRadioStatus = 0;
  Object mRadioStatusLock;
  int mRssi;
  int mVolume = 6;

  static
  {
    int[] arrayOfInt = new int[16];
    arrayOfInt[1] = 2;
    arrayOfInt[2] = 3;
    arrayOfInt[3] = 4;
    arrayOfInt[4] = 6;
    arrayOfInt[5] = 8;
    arrayOfInt[6] = 11;
    arrayOfInt[7] = 16;
    arrayOfInt[8] = 22;
    arrayOfInt[9] = 31;
    arrayOfInt[10] = 43;
    arrayOfInt[11] = 61;
    arrayOfInt[12] = 86;
    arrayOfInt[13] = 121;
    arrayOfInt[14] = 170;
    arrayOfInt[15] = 240;
    Volume = arrayOfInt;
    UNIT_WAITING_TIME = 100;
    mReflectionSucceeded = false;
    try
    {
      mFmReceiverCls = Class.forName("com.broadcom.bt.service.fm.FmReceiver");
      mIFmReceiverEventHandlerCls = Class.forName("com.broadcom.bt.service.fm.IFmReceiverEventHandler");
      mBluetoothProxyManagerCls = Class.forName("com.broadcom.bt.service.framework.BluetoothProxyManager");
      mIBluetoothProxyCallbackCls = Class.forName("com.broadcom.bt.service.framework.IBluetoothProxyCallback");
      mTurnOnRadio = mFmReceiverCls.getMethod("turnOnRadio", new Class[0]);
      mTurnOffRadio = mFmReceiverCls.getMethod("turnOffRadio", new Class[0]);
      Class localClass1 = mFmReceiverCls;
      Class[] arrayOfClass1 = new Class[2];
      arrayOfClass1[0] = Integer.TYPE;
      arrayOfClass1[1] = Integer.TYPE;
      mSeekStation = localClass1.getMethod("seekStation", arrayOfClass1);
      Class localClass2 = mFmReceiverCls;
      Class[] arrayOfClass2 = new Class[1];
      arrayOfClass2[0] = Integer.TYPE;
      mTuneRadio = localClass2.getMethod("tuneRadio", arrayOfClass2);
      Class localClass3 = mFmReceiverCls;
      Class[] arrayOfClass3 = new Class[1];
      arrayOfClass3[0] = Integer.TYPE;
      mSetFMVolume = localClass3.getMethod("setFMVolume", arrayOfClass3);
      Class localClass4 = mFmReceiverCls;
      Class[] arrayOfClass4 = new Class[1];
      arrayOfClass4[0] = Integer.TYPE;
      mSetAudioPath = localClass4.getMethod("setAudioPath", arrayOfClass4);
      Class localClass5 = mFmReceiverCls;
      Class[] arrayOfClass5 = new Class[1];
      arrayOfClass5[0] = Integer.TYPE;
      mSetAudioMode = localClass5.getMethod("setAudioMode", arrayOfClass5);
      Class localClass6 = mFmReceiverCls;
      Class[] arrayOfClass6 = new Class[2];
      arrayOfClass6[0] = Boolean.TYPE;
      arrayOfClass6[1] = Integer.TYPE;
      mSetLiveAudioPolling = localClass6.getMethod("setLiveAudioPolling", arrayOfClass6);
      Class localClass7 = mFmReceiverCls;
      Class[] arrayOfClass7 = new Class[1];
      arrayOfClass7[0] = mIFmReceiverEventHandlerCls;
      mRegisterEventHandler = localClass7.getMethod("registerEventHandler", arrayOfClass7);
      mFinish = mFmReceiverCls.getMethod("finish", new Class[0]);
      mSeekStationAbort = mFmReceiverCls.getMethod("seekStationAbort", new Class[0]);
      mGetStatus = mFmReceiverCls.getMethod("getStatus", new Class[0]);
      Class localClass8 = mFmReceiverCls;
      Class[] arrayOfClass8 = new Class[1];
      arrayOfClass8[0] = Boolean.TYPE;
      mMuteAudio = localClass8.getMethod("muteAudio", arrayOfClass8);
      mUnregisterEventHandler = mFmReceiverCls.getMethod("unregisterEventHandler", new Class[0]);
      mReflectionSucceeded = true;
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      mReflectionSucceeded = false;
    }
  }

  public BroadcomFM(Context paramContext)
  {
    super(paramContext);
    Class localClass = mIFmReceiverEventHandlerCls;
    ClassLoader localClassLoader = mIFmReceiverEventHandlerCls.getClassLoader();
    Class[] arrayOfClass = new Class[1];
    arrayOfClass[0] = mIFmReceiverEventHandlerCls;
    this.mIFmReceiverEventHandler = localClass.cast(Proxy.newProxyInstance(localClassLoader, arrayOfClass, this.mInvocationHandler));
    this.mRadioOperatorThread.start();
  }

  private void getStatus()
  {
    this.mRadioOperatorThread.mRadioOperator.sendEmptyMessage(11);
  }

  private void muteAudio(boolean paramBoolean)
  {
    Handler localHandler1 = this.mRadioOperatorThread.mRadioOperator;
    Handler localHandler2 = this.mRadioOperatorThread.mRadioOperator;
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      localHandler1.sendMessage(localHandler2.obtainMessage(12, i, 0));
      return;
    }
  }

  private void obtainFmReceiver()
  {
    try
    {
      this.mFmReceiver = mBluetoothProxyManagerCls.getMethod("getSystemProxy", new Class[] { String.class }).invoke(null, new Object[] { "bluetooth_fm_receiver_service" });
      if (this.mFmReceiver != null)
      {
        Log.d("BroadcomFM", "FmReceiver obtained from BluetoothProxyManager");
        return;
      }
    }
    catch (Exception localException1)
    {
      while (true)
        localException1.printStackTrace();
      Log.d("BroadcomFM", "null returned from BluetoothProxyManager, try to create a new instance of FmReceiver");
      try
      {
        InvocationHandler local2 = new InvocationHandler()
        {
          public Object invoke(Object paramAnonymousObject, Method paramAnonymousMethod, Object[] paramAnonymousArrayOfObject)
            throws Throwable
          {
            if (paramAnonymousMethod.getName().equals("onProxyAvailable"))
              BroadcomFM.this.mFmReceiver = paramAnonymousArrayOfObject[0];
            return null;
          }
        };
        Class localClass1 = mIBluetoothProxyCallbackCls;
        ClassLoader localClassLoader = mIBluetoothProxyCallbackCls.getClassLoader();
        Class[] arrayOfClass1 = new Class[1];
        arrayOfClass1[0] = mIBluetoothProxyCallbackCls;
        Object localObject = localClass1.cast(Proxy.newProxyInstance(localClassLoader, arrayOfClass1, local2));
        Class localClass2 = mFmReceiverCls;
        Class[] arrayOfClass2 = new Class[2];
        arrayOfClass2[0] = Context.class;
        arrayOfClass2[1] = mIBluetoothProxyCallbackCls;
        Method localMethod = localClass2.getMethod("getProxy", arrayOfClass2);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = this.context;
        arrayOfObject[1] = localObject;
        localMethod.invoke(null, arrayOfObject);
        return;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
  }

  private void seekStation(int paramInt1, int paramInt2)
  {
    this.mRadioOperatorThread.mRadioOperator.sendMessage(this.mRadioOperatorThread.mRadioOperator.obtainMessage(2, paramInt1, paramInt2));
  }

  private void seekStationAbort()
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
  {
    try
    {
      if (this.mRadioStatus == 5)
        mSeekStationAbort.invoke(this.mFmReceiver, new Object[0]);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void setAudioMode(int paramInt)
  {
    this.mRadioOperatorThread.mRadioOperator.sendMessage(this.mRadioOperatorThread.mRadioOperator.obtainMessage(6, paramInt, 0));
  }

  private void setAudioPath(int paramInt)
  {
    this.mRadioOperatorThread.mRadioOperator.sendMessage(this.mRadioOperatorThread.mRadioOperator.obtainMessage(4, paramInt, 0));
  }

  private void setFMVolume(int paramInt)
  {
    this.mRadioOperatorThread.mRadioOperator.sendMessage(this.mRadioOperatorThread.mRadioOperator.obtainMessage(5, paramInt, 0));
  }

  private void setLiveAudioPolling(boolean paramBoolean, int paramInt)
  {
    Handler localHandler1 = this.mRadioOperatorThread.mRadioOperator;
    Handler localHandler2 = this.mRadioOperatorThread.mRadioOperator;
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      localHandler1.sendMessage(localHandler2.obtainMessage(7, i, paramInt));
      return;
    }
  }

  private void tuneRadio(int paramInt)
  {
    this.mRadioOperatorThread.mRadioOperator.sendMessage(this.mRadioOperatorThread.mRadioOperator.obtainMessage(3, paramInt, 0));
  }

  private void turnOffRadio()
  {
    this.mRadioOperatorThread.mRadioOperator.sendEmptyMessage(1);
  }

  private void turnOnRadio()
  {
    this.mRadioOperatorThread.mRadioOperator.sendEmptyMessage(0);
  }

  public void cancelScanning()
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InterruptedException
  {
    seekStationAbort();
  }

  public ArrayList<Integer> getAvailableChannels()
  {
    return this.channels;
  }

  public int getCurrentChannel()
    throws IllegalArgumentException, InterruptedException, IllegalAccessException, InvocationTargetException
  {
    getStatus();
    return this.mFreq;
  }

  public int getCurrentRSSI()
    throws InterruptedException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
  {
    getStatus();
    return this.mRssi;
  }

  public String getName()
  {
    return "BroadcomFM";
  }

  public int getVolume()
    throws Exception
  {
    return this.mVolume;
  }

  public boolean isAvailable()
  {
    try
    {
      if (mReflectionSucceeded)
        if (mBluetoothProxyManagerCls.getMethod("getSystemProxy", new Class[] { String.class }) == null)
        {
          Class localClass = mFmReceiverCls;
          Class[] arrayOfClass = new Class[2];
          arrayOfClass[0] = Context.class;
          arrayOfClass[1] = mIBluetoothProxyCallbackCls;
          Method localMethod = localClass.getMethod("getProxy", arrayOfClass);
          if (localMethod == null);
        }
        else
        {
          return true;
        }
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
      return false;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
        localNoSuchMethodException.printStackTrace();
    }
  }

  public boolean isMute()
    throws IllegalArgumentException, InterruptedException, IllegalAccessException, InvocationTargetException
  {
    getStatus();
    return this.mIsMute;
  }

  public boolean isOn()
  {
    if (this.mFmReceiver == null);
    while (this.mRadioStatus == 0)
      return false;
    return true;
  }

  public boolean isPaused()
    throws Exception
  {
    return this.mIsPaused;
  }

  public boolean isScanning()
  {
    return this.mRadioStatus == 5;
  }

  public boolean isSpeakerOn()
    throws Exception
  {
    return this.mAudioPath == 1;
  }

  public void mute(boolean paramBoolean)
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InterruptedException
  {
    this.mIsMute = paramBoolean;
    if ((!this.mIsMute) && (!this.mIsPaused));
    for (boolean bool = false; ; bool = true)
    {
      muteAudio(bool);
      return;
    }
  }

  public void pause()
    throws Exception
  {
    this.mIsPaused = true;
    muteAudio(true);
    setAudioPath(0);
  }

  public void registerFMEventListener(IFMEventListener paramIFMEventListener)
  {
    this.mIFMEventListener = paramIFMEventListener;
  }

  public void scan()
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InterruptedException
  {
    seekStation(130, 105);
  }

  public void setLiveAudioQualityCallback(boolean paramBoolean, int paramInt)
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InterruptedException
  {
    setLiveAudioPolling(paramBoolean, paramInt);
  }

  public int setSpeakerOn(boolean paramBoolean)
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InterruptedException
  {
    if (paramBoolean);
    for (this.mAudioPath = 1; ; this.mAudioPath = 2)
    {
      setAudioPath(this.mAudioPath);
      return 0;
    }
  }

  public void setVolume(int paramInt)
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InterruptedException
  {
    this.mVolume = paramInt;
    if (this.mVolume < 16)
    {
      setFMVolume(Volume[paramInt]);
      return;
    }
    setFMVolume(this.mVolume);
  }

  public int tune(int paramInt)
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InterruptedException
  {
    this.mFreq = paramInt;
    this.mIsPaused = false;
    tuneRadio(this.mFreq / 10);
    if ((!this.mIsMute) && (!this.mIsPaused));
    for (boolean bool = false; ; bool = true)
    {
      muteAudio(bool);
      setAudioPath(this.mAudioPath);
      return 0;
    }
  }

  public void turnOff()
    throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InterruptedException
  {
    turnOffRadio();
  }

  public int turnOn()
    throws Exception
  {
    turnOnRadio();
    pause();
    setAudioMode(this.mAudioMode);
    setAudioPath(this.mAudioPath);
    if (this.mVolume < 16)
      setFMVolume(Volume[this.mVolume]);
    while (true)
    {
      return 0;
      setFMVolume(this.mVolume);
    }
  }

  public void unregisterFMEventListener()
  {
    this.mIFMEventListener = null;
  }

  class RadioOperatorThread extends Thread
  {
    public Handler mRadioOperator;

    RadioOperatorThread()
    {
    }

    public void run()
    {
      Looper.prepare();
      Log.d("BroadcomFM", "RadioOperatorThread start");
      this.mRadioOperator = new Handler()
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          int i = 1;
          Log.d("BroadcomFM", "RadioOperator: " + paramAnonymousMessage);
          if ((BroadcomFM.this.mRadioStatus == 0) && (paramAnonymousMessage.what != 0))
            Log.w("BroadcomFM", "radio device off, ignore command msg " + paramAnonymousMessage);
          while (true)
          {
            label66: return;
            while (true)
            {
              try
              {
                switch (paramAnonymousMessage.what)
                {
                case 0:
                  if (BroadcomFM.this.mFmReceiver == null)
                    BroadcomFM.this.obtainFmReceiver();
                  if (BroadcomFM.this.mFmReceiver != null)
                  {
                    Method localMethod8 = BroadcomFM.mRegisterEventHandler;
                    Object localObject8 = BroadcomFM.this.mFmReceiver;
                    Object[] arrayOfObject8 = new Object[1];
                    arrayOfObject8[0] = BroadcomFM.this.mIFmReceiverEventHandler;
                    localMethod8.invoke(localObject8, arrayOfObject8);
                    Log.d("BroadcomFM", "IFmEventHandler registered");
                    if (BroadcomFM.this.mRadioStatus != 0)
                      break label324;
                    BroadcomFM.this.mRadioStatus = 1;
                    BroadcomFM.mTurnOnRadio.invoke(BroadcomFM.this.mFmReceiver, new Object[0]);
                    if (BroadcomFM.this.mRadioStatus == 2)
                      break label66;
                    Thread.sleep(BroadcomFM.UNIT_WAITING_TIME);
                    continue;
                  }
                  break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 11:
                case 12:
                case 8:
                case 9:
                case 10:
                }
              }
              catch (Exception localException)
              {
                localException.printStackTrace();
                return;
              }
              try
              {
                BroadcomFM.RadioOperatorThread.sleep(BroadcomFM.UNIT_WAITING_TIME);
              }
              catch (InterruptedException localInterruptedException)
              {
                localInterruptedException.printStackTrace();
              }
            }
            label324: Log.d("BroadcomFM", "Radio is on. Ignore turnOnRadio call");
            BroadcomFM.this.mRadioStatus = 2;
            return;
            if (BroadcomFM.this.mRadioStatus != 0)
            {
              BroadcomFM.this.mRadioStatus = 3;
              BroadcomFM.mTurnOffRadio.invoke(BroadcomFM.this.mFmReceiver, new Object[0]);
              while (BroadcomFM.this.mRadioStatus != 0)
                Thread.sleep(BroadcomFM.UNIT_WAITING_TIME);
            }
            else
            {
              Log.d("BroadcomFM", "Radio is off. Ignore turnOffRadio call");
              BroadcomFM.this.mRadioStatus = 0;
              return;
              BroadcomFM.this.mRadioStatus = 5;
              BroadcomFM.this.channels.clear();
              Method localMethod7 = BroadcomFM.mSeekStation;
              Object localObject7 = BroadcomFM.this.mFmReceiver;
              Object[] arrayOfObject7 = new Object[2];
              arrayOfObject7[0] = Integer.valueOf(paramAnonymousMessage.arg1);
              arrayOfObject7[1] = Integer.valueOf(paramAnonymousMessage.arg2);
              localMethod7.invoke(localObject7, arrayOfObject7);
              while (BroadcomFM.this.mRadioStatus != 2)
                Thread.sleep(BroadcomFM.UNIT_WAITING_TIME);
              continue;
              BroadcomFM.this.mRadioStatus = 4;
              Method localMethod6 = BroadcomFM.mTuneRadio;
              Object localObject6 = BroadcomFM.this.mFmReceiver;
              Object[] arrayOfObject6 = new Object[1];
              arrayOfObject6[0] = Integer.valueOf(paramAnonymousMessage.arg1);
              localMethod6.invoke(localObject6, arrayOfObject6);
              while (BroadcomFM.this.mRadioStatus != 2)
                Thread.sleep(BroadcomFM.UNIT_WAITING_TIME);
              continue;
              BroadcomFM.this.mRadioStatus = 4;
              Method localMethod5 = BroadcomFM.mSetAudioPath;
              Object localObject5 = BroadcomFM.this.mFmReceiver;
              Object[] arrayOfObject5 = new Object[1];
              arrayOfObject5[0] = Integer.valueOf(paramAnonymousMessage.arg1);
              localMethod5.invoke(localObject5, arrayOfObject5);
              while (BroadcomFM.this.mRadioStatus != 2)
                Thread.sleep(BroadcomFM.UNIT_WAITING_TIME);
              continue;
              BroadcomFM.this.mRadioStatus = 4;
              Method localMethod4 = BroadcomFM.mSetFMVolume;
              Object localObject4 = BroadcomFM.this.mFmReceiver;
              Object[] arrayOfObject4 = new Object[1];
              arrayOfObject4[0] = Integer.valueOf(paramAnonymousMessage.arg1);
              localMethod4.invoke(localObject4, arrayOfObject4);
              while (BroadcomFM.this.mRadioStatus != 2)
                Thread.sleep(BroadcomFM.UNIT_WAITING_TIME);
              continue;
              BroadcomFM.this.mRadioStatus = 4;
              Method localMethod3 = BroadcomFM.mSetAudioMode;
              Object localObject3 = BroadcomFM.this.mFmReceiver;
              Object[] arrayOfObject3 = new Object[1];
              arrayOfObject3[0] = Integer.valueOf(paramAnonymousMessage.arg1);
              localMethod3.invoke(localObject3, arrayOfObject3);
              while (BroadcomFM.this.mRadioStatus != 2)
                Thread.sleep(BroadcomFM.UNIT_WAITING_TIME);
            }
          }
          Method localMethod2 = BroadcomFM.mSetLiveAudioPolling;
          Object localObject2 = BroadcomFM.this.mFmReceiver;
          Object[] arrayOfObject2 = new Object[2];
          if (paramAnonymousMessage.arg1 == i);
          while (true)
          {
            arrayOfObject2[0] = Boolean.valueOf(i);
            arrayOfObject2[1] = Integer.valueOf(paramAnonymousMessage.arg2);
            localMethod2.invoke(localObject2, arrayOfObject2);
            return;
            BroadcomFM.this.mRadioStatus = 4;
            BroadcomFM.mGetStatus.invoke(BroadcomFM.this.mFmReceiver, new Object[0]);
            while (BroadcomFM.this.mRadioStatus != 2)
              Thread.sleep(BroadcomFM.UNIT_WAITING_TIME);
            break;
            BroadcomFM.this.mRadioStatus = 4;
            Method localMethod1 = BroadcomFM.mMuteAudio;
            Object localObject1 = BroadcomFM.this.mFmReceiver;
            Object[] arrayOfObject1 = new Object[1];
            if (paramAnonymousMessage.arg1 == i);
            while (true)
            {
              arrayOfObject1[0] = Boolean.valueOf(i);
              localMethod1.invoke(localObject1, arrayOfObject1);
              while (BroadcomFM.this.mRadioStatus != 2)
                Thread.sleep(100L);
              break;
              j = 0;
            }
            return;
            int j = 0;
          }
        }
      };
      Looper.loop();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.broadcom.BroadcomFM
 * JD-Core Version:    0.6.2
 */