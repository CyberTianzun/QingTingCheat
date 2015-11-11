package fm.qingting.qtradio.vollo.vx96;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import fm.qingting.qtradio.fmdriver.FMDriver;
import fm.qingting.qtradio.fmdriver.IFMEventListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Vollo_Vx96_FMDriver extends FMDriver
{
  private static final int FM_RX_DWELL_PERIOD_1S = 0;
  private static final int FM_RX_DWELL_PERIOD_2S = 1;
  private static final int FM_RX_DWELL_PERIOD_3S = 2;
  private static final int FM_RX_DWELL_PERIOD_4S = 3;
  private static final int FM_RX_DWELL_PERIOD_5S = 4;
  private static final int FM_RX_DWELL_PERIOD_6S = 5;
  private static final int FM_RX_DWELL_PERIOD_7S = 6;
  private static final int FM_RX_SEARCHDIR_DOWN = 0;
  private static final int FM_RX_SEARCHDIR_UP = 1;
  private static final int FM_RX_SRCH_MODE_SCAN = 1;
  private static final int FM_RX_SRCH_MODE_SEEK = 0;
  private static final int FREQUENCY_BAND_MAX = 108001;
  private static final int MaxVolume = 15;
  private static final int MsgChannelFound = 2;
  private static final int MsgFmOff = 3;
  private static final int MsgFmOn = 4;
  private static final int MsgScanComplete = 1;
  private static final int MsgScanStarted = 5;
  private static final int MsgTune = 6;
  public static final String TAG = "FmReceiver";
  private AudioManager amObj;
  private Method asSetForceUseMethod = null;
  private Class audioSystem = null;
  private ArrayList<Integer> channels = new ArrayList();
  private Method cmAcquireMethod;
  private Method cmCancelSearchMethod;
  private Method cmConfigureMethod;
  private Method cmDisableMethod;
  private Method cmEnableMethod;
  private Method cmGetRssiLimitMethod;
  private Method cmGetRssiMethod;
  private Method cmGetTunedFrequencyMethod;
  private Method cmRegisterClientMethod;
  private Method cmSearchStationsMethod;
  private Method cmSetMuteModeMethod;
  private Method cmSetPowerModeMethod;
  private Method cmSetStationMethod;
  private Method cmSetStereoModeMethod;
  private Method cmSetVolumMethod;
  private Method cmUnRegisterClientMethod;
  private Class fmConfigClass;
  private Object fmConfigObj;
  private Class fmReceiverClass;
  private Object fmReceiverObj;
  private Class fmReceiverSuperClass;
  private Class huaweiFM = null;
  private boolean isAvailable = false;
  private Context mContext;
  private int mCurrentFreq = 1;
  private int mDefaultVolum = 1;
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (Vollo_Vx96_FMDriver.this.onFMEventListener == null)
        return;
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
        Vollo_Vx96_FMDriver.this.onFMEventListener.onScanComplete(true);
        return;
      case 2:
        Vollo_Vx96_FMDriver.this.onFMEventListener.onChannelFound(100 * paramAnonymousMessage.arg1);
        return;
      case 3:
        Vollo_Vx96_FMDriver.this.onFMEventListener.onFMOff();
        return;
      case 4:
        Vollo_Vx96_FMDriver.this.onFMEventListener.onFMOn();
        return;
      case 5:
        Vollo_Vx96_FMDriver.this.onFMEventListener.onScanStarted();
        return;
      case 6:
      }
      Vollo_Vx96_FMDriver.this.onFMEventListener.onTune(paramAnonymousMessage.arg1);
    }
  };
  private boolean mIsFMEnable = false;
  private boolean mIsPlay = false;
  private boolean mIsScan = false;
  private boolean mMute = false;
  private IFMEventListener onFMEventListener;
  private Method setChSpacingMethod;
  private Method setEmphasisMethod;
  private Method setLowerLimitMedthod;
  private Method setRadioBandMethod;
  private Method setRdsStdMethod;
  private Method setUpperLimitMethod;

  public Vollo_Vx96_FMDriver(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    initClass();
  }

  private Boolean acquireReceiver(String paramString)
  {
    Boolean localBoolean1 = Boolean.valueOf(false);
    try
    {
      Method localMethod = this.cmAcquireMethod;
      Object localObject = this.fmReceiverObj;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = String.valueOf(paramString);
      Boolean localBoolean2 = (Boolean)localMethod.invoke(localObject, arrayOfObject);
      return localBoolean2;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return localBoolean1;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return localBoolean1;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return localBoolean1;
  }

  private long apiGetCurrentChannel()
  {
    return getTunedFrequencyReceiver();
  }

  private long apiGetCurrentRSSI()
  {
    try
    {
      long l = ((Long)this.cmGetRssiMethod.invoke(this.fmReceiverObj, new Object[0])).longValue();
      return l;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -2147483648L;
  }

  private boolean apiIsOn()
  {
    return this.mIsFMEnable;
  }

  private boolean apiIsScanning()
  {
    return this.mIsScan;
  }

  private void apiOff()
  {
    if (this.mIsScan)
      cancelScan();
    if (this.mIsPlay)
    {
      this.mIsFMEnable = false;
      disableReceiver();
    }
    this.mIsPlay = false;
    this.mIsFMEnable = false;
    Message localMessage = Message.obtain(this.mHandler, 3, null);
    this.mHandler.sendMessage(localMessage);
  }

  private boolean apiOn()
  {
    int i;
    if (!this.mIsFMEnable)
      if ((acquireReceiver("/dev/radio0").booleanValue()) && (enableReceiver()))
      {
        i = 1;
        if (i != 0)
        {
          this.mIsFMEnable = true;
          Message localMessage2 = Message.obtain(this.mHandler, 4, null);
          this.mHandler.sendMessage(localMessage2);
        }
      }
    while (true)
    {
      return this.mIsFMEnable;
      i = 0;
      break;
      Message localMessage1 = Message.obtain(this.mHandler, 3, null);
      this.mHandler.sendMessage(localMessage1);
    }
  }

  private int apiScan()
  {
    if (!searchStationsReceiver())
      return -1;
    return getTunedFrequencyReceiver();
  }

  private void apiStopScan()
  {
    if (this.mIsScan)
    {
      cancelSearchStationReceiver();
      this.mIsScan = false;
    }
  }

  private boolean buildDefaultFMConfig()
  {
    try
    {
      Method localMethod1 = this.setChSpacingMethod;
      Object localObject1 = this.fmConfigObj;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(1);
      localMethod1.invoke(localObject1, arrayOfObject1);
      Method localMethod2 = this.setEmphasisMethod;
      Object localObject2 = this.fmConfigObj;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(1);
      localMethod2.invoke(localObject2, arrayOfObject2);
      Method localMethod3 = this.setRadioBandMethod;
      Object localObject3 = this.fmConfigObj;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(2);
      localMethod3.invoke(localObject3, arrayOfObject3);
      Method localMethod4 = this.setRdsStdMethod;
      Object localObject4 = this.fmConfigObj;
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = Integer.valueOf(1);
      localMethod4.invoke(localObject4, arrayOfObject4);
      Method localMethod5 = this.setLowerLimitMedthod;
      Object localObject5 = this.fmConfigObj;
      Object[] arrayOfObject5 = new Object[1];
      arrayOfObject5[0] = Integer.valueOf(8750);
      localMethod5.invoke(localObject5, arrayOfObject5);
      Method localMethod6 = this.setUpperLimitMethod;
      Object localObject6 = this.fmConfigObj;
      Object[] arrayOfObject6 = new Object[1];
      arrayOfObject6[0] = Integer.valueOf(10800);
      localMethod6.invoke(localObject6, arrayOfObject6);
      return true;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return false;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return false;
  }

  private void cancelScan()
  {
    apiStopScan();
  }

  private boolean cancelSearchStationReceiver()
  {
    try
    {
      boolean bool = ((Boolean)this.cmCancelSearchMethod.invoke(this.fmReceiverObj, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return false;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return false;
  }

  private boolean disableReceiver()
  {
    try
    {
      boolean bool = ((Boolean)this.cmDisableMethod.invoke(this.fmReceiverObj, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return false;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return false;
  }

  private boolean enableReceiver()
  {
    try
    {
      Method localMethod = this.cmEnableMethod;
      Object localObject = this.fmReceiverObj;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.fmConfigObj;
      boolean bool = ((Boolean)localMethod.invoke(localObject, arrayOfObject)).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return false;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return false;
  }

  private void getAudioSystem()
  {
    try
    {
      this.audioSystem = Class.forName("android.media.AudioSystem");
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static int getMaxvolume()
  {
    return 15;
  }

  private int getTunedFrequencyReceiver()
  {
    try
    {
      int i = ((Integer)this.cmGetTunedFrequencyMethod.invoke(this.fmReceiverObj, new Object[0])).intValue();
      return i;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return -1;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return -1;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return -1;
  }

  private boolean inChannelList(int paramInt)
  {
    for (int i = 0; ; i++)
    {
      if (i >= this.channels.size())
        return false;
      if (((Integer)this.channels.get(i)).intValue() == paramInt)
        return true;
    }
  }

  private void initClass()
  {
    try
    {
      this.fmConfigClass = Class.forName("android.hardware.fmradio.FmConfig", true, this.mContext.getClassLoader());
      this.fmConfigObj = this.fmConfigClass.getDeclaredConstructor(null).newInstance(null);
      this.amObj = ((AudioManager)this.mContext.getSystemService("audio"));
      this.fmReceiverClass = Class.forName("android.hardware.fmradio.FmReceiver", true, this.mContext.getClassLoader());
      this.fmReceiverObj = this.fmReceiverClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
      if ((setUpInnerMethodFromTransceiver()) && (setUpInnerMethodFromReceiver()))
        this.isAvailable = true;
      setUpInnerMethodFromConfig();
      buildDefaultFMConfig();
      getAudioSystem();
      setUpMethodFromAudioSystem();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private boolean isAudioSystemAvailable()
  {
    return this.audioSystem != null;
  }

  private boolean searchStationsReceiver()
  {
    try
    {
      Method localMethod = this.cmSearchStationsMethod;
      Object localObject = this.fmReceiverObj;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(this.mCurrentFreq);
      arrayOfObject[1] = Integer.valueOf(1);
      arrayOfObject[2] = Integer.valueOf(3000);
      boolean bool = ((Boolean)localMethod.invoke(localObject, arrayOfObject)).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return false;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return false;
  }

  private boolean setStationReceiver(int paramInt)
  {
    try
    {
      Method localMethod = this.cmSetStationMethod;
      Object localObject = this.fmReceiverObj;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      boolean bool = ((Boolean)localMethod.invoke(localObject, arrayOfObject)).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return false;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return false;
  }

  private void setUpInnerMethodFromConfig()
  {
    try
    {
      Class localClass1 = this.fmConfigClass;
      Class[] arrayOfClass1 = new Class[1];
      arrayOfClass1[0] = Integer.TYPE;
      this.setChSpacingMethod = localClass1.getDeclaredMethod("setChSpacing", arrayOfClass1);
      Class localClass2 = this.fmConfigClass;
      Class[] arrayOfClass2 = new Class[1];
      arrayOfClass2[0] = Integer.TYPE;
      this.setEmphasisMethod = localClass2.getDeclaredMethod("setEmphasis", arrayOfClass2);
      Class localClass3 = this.fmConfigClass;
      Class[] arrayOfClass3 = new Class[1];
      arrayOfClass3[0] = Integer.TYPE;
      this.setRadioBandMethod = localClass3.getDeclaredMethod("setRadioBand", arrayOfClass3);
      Class localClass4 = this.fmConfigClass;
      Class[] arrayOfClass4 = new Class[1];
      arrayOfClass4[0] = Integer.TYPE;
      this.setRdsStdMethod = localClass4.getDeclaredMethod("setRdsStd", arrayOfClass4);
      Class localClass5 = this.fmConfigClass;
      Class[] arrayOfClass5 = new Class[1];
      arrayOfClass5[0] = Integer.TYPE;
      this.setLowerLimitMedthod = localClass5.getDeclaredMethod("setLowerLimit", arrayOfClass5);
      Class localClass6 = this.fmConfigClass;
      Class[] arrayOfClass6 = new Class[1];
      arrayOfClass6[0] = Integer.TYPE;
      this.setUpperLimitMethod = localClass6.getDeclaredMethod("setUpperLimit", arrayOfClass6);
      return;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
    }
  }

  private boolean setUpInnerMethodFromReceiver()
  {
    try
    {
      Class localClass1 = this.fmReceiverClass;
      Class[] arrayOfClass1 = new Class[1];
      arrayOfClass1[0] = this.fmConfigClass;
      this.cmEnableMethod = localClass1.getDeclaredMethod("enable", arrayOfClass1);
      this.cmDisableMethod = this.fmReceiverClass.getDeclaredMethod("disable", new Class[0]);
      this.cmGetTunedFrequencyMethod = this.fmReceiverClass.getDeclaredMethod("getTunedFrequency", new Class[0]);
      Class localClass2 = this.fmReceiverClass;
      Class[] arrayOfClass2 = new Class[3];
      arrayOfClass2[0] = Integer.TYPE;
      arrayOfClass2[1] = Integer.TYPE;
      arrayOfClass2[2] = Integer.TYPE;
      this.cmSearchStationsMethod = localClass2.getDeclaredMethod("searchStations", arrayOfClass2);
      this.cmCancelSearchMethod = this.fmReceiverClass.getDeclaredMethod("cancelSearch", new Class[0]);
      this.cmGetRssiMethod = this.fmReceiverClass.getDeclaredMethod("getRssi", new Class[0]);
      this.cmUnRegisterClientMethod = this.fmReceiverClass.getDeclaredMethod("unregisterClient", new Class[0]);
      this.cmGetRssiLimitMethod = this.fmReceiverClass.getDeclaredMethod("getRssiLimit", new Class[0]);
      Class localClass3 = this.fmReceiverClass;
      Class[] arrayOfClass3 = new Class[1];
      arrayOfClass3[0] = Integer.TYPE;
      this.cmSetMuteModeMethod = localClass3.getMethod("setMuteMode", arrayOfClass3);
      Class localClass4 = this.fmReceiverClass;
      Class[] arrayOfClass4 = new Class[1];
      arrayOfClass4[0] = Boolean.TYPE;
      this.cmSetStereoModeMethod = localClass4.getDeclaredMethod("setStereoMode", arrayOfClass4);
      Class localClass5 = this.fmReceiverClass;
      Class[] arrayOfClass5 = new Class[1];
      arrayOfClass5[0] = Integer.TYPE;
      this.cmSetPowerModeMethod = localClass5.getDeclaredMethod("setPowerMode", arrayOfClass5);
      Class localClass6 = this.fmReceiverClass;
      Class[] arrayOfClass6 = new Class[1];
      arrayOfClass6[0] = Integer.TYPE;
      this.cmSetVolumMethod = localClass6.getDeclaredMethod("setVolume", arrayOfClass6);
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  private boolean setUpInnerMethodFromTransceiver()
  {
    if ((this.fmReceiverClass != null) && (this.fmReceiverClass.getGenericSuperclass() != null))
      this.fmReceiverSuperClass = this.fmReceiverClass.getSuperclass();
    try
    {
      this.cmAcquireMethod = this.fmReceiverSuperClass.getDeclaredMethod("acquire", new Class[] { String.class });
      this.cmAcquireMethod.setAccessible(true);
      Class localClass1 = this.fmReceiverSuperClass;
      Class[] arrayOfClass1 = new Class[1];
      arrayOfClass1[0] = Integer.TYPE;
      this.cmSetStationMethod = localClass1.getDeclaredMethod("setStation", arrayOfClass1);
      Class localClass2 = this.fmReceiverSuperClass;
      Class[] arrayOfClass2 = new Class[1];
      arrayOfClass2[0] = this.fmConfigClass;
      this.cmConfigureMethod = localClass2.getDeclaredMethod("configure", arrayOfClass2);
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  private void setUpMethodFromAudioSystem()
  {
    if (this.asSetForceUseMethod == null);
    try
    {
      Class localClass = this.audioSystem;
      Class[] arrayOfClass = new Class[2];
      arrayOfClass[0] = Integer.TYPE;
      arrayOfClass[1] = Integer.TYPE;
      this.asSetForceUseMethod = localClass.getMethod("setForceUse", arrayOfClass);
      return;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
    }
  }

  private void setVolumViaReceiver(int paramInt)
  {
    try
    {
      Method localMethod = this.cmSetVolumMethod;
      Object localObject = this.fmReceiverObj;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      boolean bool = ((Boolean)localMethod.invoke(localObject, arrayOfObject)).booleanValue();
      Log.e("FmReceiver", "set volume:" + paramInt + " " + bool);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
  }

  private void showAudioStatus()
  {
    Log.e("FmReceiver", "audio mode:" + this.amObj.getMode());
    if (this.amObj.isWiredHeadsetOn())
      Log.e("FmReceiver", "wired head set on");
    if (this.amObj.isBluetoothA2dpOn())
      Log.e("FmReceiver", "bluetooth on");
    if (this.amObj.isSpeakerphoneOn())
      Log.e("FmReceiver", "speaker phone on");
    if (this.amObj.isMicrophoneMute())
      Log.e("FmReceiver", "microphone is mute");
    while (true)
    {
      if (this.amObj.isMusicActive())
        Log.e("FmReceiver", "music is active");
      return;
      Log.e("FmReceiver", "microphone is not mute");
    }
  }

  private void showChannels()
  {
    for (int i = 0; ; i++)
    {
      if (i >= this.channels.size())
        return;
      Log.e("FmReceiver", "channle: " + this.channels.get(i));
    }
  }

  private void showVolloLib()
  {
    int i = 0;
    Log.w("fmradio", "FmReceiver----------------");
    Method[] arrayOfMethod1 = this.fmReceiverClass.getDeclaredMethods();
    int j = arrayOfMethod1.length;
    int k = 0;
    Method[] arrayOfMethod2;
    int n;
    label64: Method[] arrayOfMethod3;
    int i1;
    if (k >= j)
    {
      Log.w("fmradio", "FmTransceiver-------------");
      arrayOfMethod2 = this.fmReceiverClass.getSuperclass().getDeclaredMethods();
      int m = arrayOfMethod2.length;
      n = 0;
      if (n < m)
        break label129;
      if (isAudioSystemAvailable())
      {
        Log.w("fmradio", "android.media.AudioSystem methods------");
        arrayOfMethod3 = this.audioSystem.getDeclaredMethods();
        i1 = arrayOfMethod3.length;
      }
    }
    while (true)
    {
      if (i >= i1)
      {
        return;
        Log.w("fmradio", arrayOfMethod1[k].toString());
        k++;
        break;
        label129: Log.w("fmradio", arrayOfMethod2[n].toString());
        n++;
        break label64;
      }
      Log.w("fmradio", arrayOfMethod3[i].toString());
      i++;
    }
  }

  public void cancelScanning()
    throws Exception
  {
    cancelScan();
  }

  public ArrayList<Integer> getAvailableChannels()
  {
    return new ArrayList(this.channels);
  }

  public int getCurrentChannel()
  {
    return (int)apiGetCurrentChannel();
  }

  public int getCurrentRSSI()
  {
    return (int)apiGetCurrentRSSI();
  }

  public String getName()
  {
    return "VolloFMDriver-FmReceiver";
  }

  public int getVolume()
    throws Exception
  {
    return this.mDefaultVolum;
  }

  public boolean isAvailable()
  {
    return this.isAvailable;
  }

  public boolean isHeadsetPlugged()
  {
    return true;
  }

  public boolean isMute()
    throws Exception
  {
    return this.mMute;
  }

  public boolean isOn()
  {
    return apiIsOn();
  }

  public boolean isPaused()
    throws Exception
  {
    return !this.mIsPlay;
  }

  public boolean isScanning()
  {
    return apiIsScanning();
  }

  public boolean isSpeakerOn()
    throws Exception
  {
    return false;
  }

  public void mute(boolean paramBoolean)
    throws Exception
  {
    int i = 1;
    this.mMute = paramBoolean;
    Method localMethod = this.cmSetMuteModeMethod;
    Object localObject = this.fmReceiverObj;
    Object[] arrayOfObject = new Object[i];
    if (paramBoolean);
    while (true)
    {
      arrayOfObject[0] = Integer.valueOf(i);
      boolean bool = ((Boolean)localMethod.invoke(localObject, arrayOfObject)).booleanValue();
      if (bool)
        this.mMute = paramBoolean;
      Log.e("FmReceiver", "mute:" + paramBoolean + ";result:" + bool);
      return;
      i = 0;
    }
  }

  public void off()
  {
    apiOff();
  }

  public void pause()
    throws Exception
  {
    Intent localIntent = new Intent("android.intent.action.FM");
    localIntent.putExtra("state", 0);
    this.mContext.sendBroadcast(localIntent);
    this.mIsPlay = false;
  }

  public void registerFMEventListener(IFMEventListener paramIFMEventListener)
  {
    setListener(paramIFMEventListener);
  }

  public void scan()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        Message localMessage1 = Message.obtain(Vollo_Vx96_FMDriver.this.mHandler, 5, null);
        Vollo_Vx96_FMDriver.this.mHandler.sendMessage(localMessage1);
        int j;
        label129: int i;
        if (!Vollo_Vx96_FMDriver.this.mIsFMEnable)
        {
          if ((Vollo_Vx96_FMDriver.this.acquireReceiver("/dev/radio0").booleanValue()) && (Vollo_Vx96_FMDriver.this.enableReceiver()))
          {
            j = 1;
            if (j == 0)
              break label194;
            Vollo_Vx96_FMDriver.this.mIsFMEnable = true;
            Message localMessage5 = Message.obtain(Vollo_Vx96_FMDriver.this.mHandler, 4, null);
            Vollo_Vx96_FMDriver.this.mHandler.sendMessage(localMessage5);
          }
        }
        else
        {
          Vollo_Vx96_FMDriver.this.mIsPlay = false;
          Vollo_Vx96_FMDriver.this.mIsScan = true;
          Vollo_Vx96_FMDriver.this.channels.clear();
          i = Vollo_Vx96_FMDriver.this.apiScan();
          if ((-1 != i) && (Vollo_Vx96_FMDriver.this.mIsScan))
            break label222;
        }
        while (true)
        {
          Vollo_Vx96_FMDriver.this.mIsScan = false;
          Message localMessage2 = Message.obtain(Vollo_Vx96_FMDriver.this.mHandler, 1, null);
          Vollo_Vx96_FMDriver.this.mHandler.sendMessage(localMessage2);
          return;
          j = 0;
          break;
          label194: Message localMessage4 = Message.obtain(Vollo_Vx96_FMDriver.this.mHandler, 1, null);
          Vollo_Vx96_FMDriver.this.mHandler.sendMessage(localMessage4);
          return;
          label222: Vollo_Vx96_FMDriver.this.mCurrentFreq = i;
          if (!Vollo_Vx96_FMDriver.this.inChannelList(i))
          {
            Message localMessage3 = Message.obtain(Vollo_Vx96_FMDriver.this.mHandler, 2, null);
            localMessage3.arg1 = i;
            Vollo_Vx96_FMDriver.this.mHandler.sendMessage(localMessage3);
            Vollo_Vx96_FMDriver.this.channels.add(Integer.valueOf(i));
            if (i < 108001)
              break label129;
          }
        }
      }
    }).start();
  }

  public void setListener(IFMEventListener paramIFMEventListener)
  {
    this.onFMEventListener = paramIFMEventListener;
  }

  public void setLiveAudioQualityCallback(boolean paramBoolean, int paramInt)
    throws Exception
  {
  }

  public int setSpeakerOn(boolean paramBoolean)
  {
    Intent localIntent = new Intent("android.intent.action.FM");
    localIntent.putExtra("state", 1);
    if (paramBoolean)
      localIntent.putExtra("speaker", 2);
    while (true)
    {
      this.mContext.sendBroadcast(localIntent);
      return 0;
      localIntent.putExtra("speaker", 0);
    }
  }

  public void setVolume(int paramInt)
    throws Exception
  {
    if (paramInt < 0)
      paramInt = 0;
    if (paramInt > getMaxvolume())
      paramInt = getMaxvolume();
    setVolumViaReceiver(paramInt);
    this.mDefaultVolum = paramInt;
  }

  public int tune(int paramInt)
  {
    if (paramInt > 10000)
      paramInt /= 100;
    Log.e("FmReceiver", "tune, freq = " + paramInt);
    int i;
    if (!apiOn())
      i = -1;
    boolean bool;
    do
    {
      return i;
      this.mIsPlay = true;
      setStationReceiver(paramInt);
      Message localMessage = Message.obtain(this.mHandler, 6, null);
      localMessage.arg1 = paramInt;
      this.mHandler.sendMessage(localMessage);
      Intent localIntent = new Intent("android.intent.action.FM");
      localIntent.putExtra("state", 1);
      this.mContext.sendBroadcast(localIntent);
      bool = this.mMute;
      i = 0;
    }
    while (!bool);
    try
    {
      mute(false);
      return 0;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  public void turnOff()
    throws Exception
  {
    Log.e("FmReceiver", "turn off");
    off();
  }

  public int turnOn()
    throws Exception
  {
    if (apiOn())
      return 1;
    return 0;
  }

  public void unregister()
  {
    setListener(null);
  }

  public void unregisterFMEventListener()
  {
    unregister();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.vollo.vx96.Vollo_Vx96_FMDriver
 * JD-Core Version:    0.6.2
 */