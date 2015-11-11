package fm.qingting.qtradio.miui2;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import fm.qingting.qtradio.fmdriver.FMDriver;
import fm.qingting.qtradio.fmdriver.IFMEventListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class MiuiFMReceiver extends FMDriver
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
  private static final String TAG = "fm";
  private Class ServiceManagerClass;
  private Class amClass;
  private Object amObj;
  private Method asRequestAudioFocusMethod = null;
  private Method asSetForceUseMethod = null;
  private Method asSetParameterMethod;
  private Class audioSystem = null;
  private boolean availableFirst = false;
  private boolean availableLast = false;
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
  private Method cmRegisterRdsGroupProcessingMethod;
  private Method cmSearchStationListMethod;
  private Method cmSearchStationsMethod;
  private Method cmSetInternalAntennaMethod;
  private Method cmSetMuteModeMethod;
  private Method cmSetPowerModeMethod;
  private Method cmSetStationMethod;
  private Method cmSetStereoModeMethod;
  private Method cmSetVolumMethod;
  private Method cmUnRegisterClientMethod;
  private int currentFreq = -1;
  private Class fmConfigClass;
  private Object fmConfigObj;
  private Class fmReceiverClass;
  private Object fmReceiverObj;
  private Class fmReceiverSuperClass;
  private Class fmRxEvCallbackAdaptorClass;
  private Object fmRxEvCallbackAdaptorObj;
  private Class fmRxEvCallbacksClass;
  private Object fmRxEvCallbacksObj;
  private Object fmServiceObject;
  private Method getService;
  private boolean hasStart = false;
  private Class huaweiFM = null;
  private Class internalServiceClass;
  private Object internalServiceObject;
  private InvocationHandler invocationHandler = new InvocationHandler()
  {
    public Object invoke(Object paramAnonymousObject, Method paramAnonymousMethod, Object[] paramAnonymousArrayOfObject)
      throws Throwable
    {
      Log.e("fm", " in invoke : " + paramAnonymousMethod.getName() + " " + paramAnonymousArrayOfObject.length);
      if ("FmRxEvEnableReceiver".equals(paramAnonymousMethod.getName()))
      {
        Thread.sleep(5000L);
        Log.e("fm", " in FmRxEvEnableReceiver ");
      }
      int i;
      do
      {
        return null;
        if ("FmRxEvDisableReceiver".equals(paramAnonymousMethod.getName()))
        {
          Log.e("fm", " in FmRxEvDisableReceiver ");
          return null;
        }
        if (!"FmRxEvRadioTuneStatus".equals(paramAnonymousMethod.getName()))
          break;
        Log.e("fm", " in FmRxEvRadioTuneStatus " + paramAnonymousArrayOfObject[0] + " " + paramAnonymousArrayOfObject.length);
        i = ((Integer)paramAnonymousArrayOfObject[0]).intValue();
      }
      while (MiuiFMReceiver.this.inChannelList(i));
      MiuiFMReceiver.this.channels.add(Integer.valueOf(i));
      Message localMessage2 = Message.obtain(MiuiFMReceiver.this.mHandler, 2, null);
      localMessage2.arg1 = i;
      MiuiFMReceiver.this.mHandler.sendMessage(localMessage2);
      return null;
      if ("FmRxEvRdsLockStatus".equals(paramAnonymousMethod.getName()))
      {
        Log.e("fm", " in FmRxEvRdsLockStatus ");
        return null;
      }
      if ("FmRxEvStereoStatus".equals(paramAnonymousMethod.getName()))
      {
        Log.e("fm", " in FmRxEvStereoStatus ");
        return null;
      }
      if ("FmRxEvServiceAvailable".equals(paramAnonymousMethod.getName()))
      {
        Log.e("fm", " in FmRxEvServiceAvailable ");
        return null;
      }
      if ("FmRxEvSearchInProgress".equals(paramAnonymousMethod.getName()))
      {
        Log.e("fm", " in FmRxEvSearchInProgress ");
        return null;
      }
      if ("FmRxEvSearchComplete".equals(paramAnonymousMethod.getName()))
      {
        Log.e("fm", " in FmRxEvSearchComplete " + paramAnonymousArrayOfObject.length);
        Message localMessage1 = Message.obtain(MiuiFMReceiver.this.mHandler, 1, null);
        MiuiFMReceiver.this.mHandler.sendMessage(localMessage1);
        return null;
      }
      if ("FmRxEvSearchListComplete".equals(paramAnonymousMethod.getName()))
      {
        Log.e("fm", " in FmRxEvSearchListComplete " + paramAnonymousArrayOfObject.length);
        return null;
      }
      if ("FmRxEvRdsGroupData".equals(paramAnonymousMethod.getName()))
      {
        Log.e("fm", " in FmRxEvRdsGroupData ");
        return null;
      }
      if ("FmRxEvRdsPsInfo".equals(paramAnonymousMethod.getName()))
      {
        Log.e("fm", " in FmRxEvRdsPsInfo ");
        return null;
      }
      if ("FmRxEvRdsRtInfo".equals(paramAnonymousMethod.getName()))
      {
        Log.e("fm", " in FmRxEvRdsRtInfo ");
        return null;
      }
      if ("FmRxEvRdsAfInfo".equals(paramAnonymousMethod.getName()))
      {
        Log.e("fm", " in FmRxEvRdsAfInfo ");
        return null;
      }
      Log.e("fm", "receiver what???");
      return null;
    }
  };
  private boolean isClientRegistered = false;
  private Class listenerClass;
  private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener()
  {
    public void onAudioFocusChange(int paramAnonymousInt)
    {
      Log.e("FmReceiver.java", "onAudioFocusChaned, " + paramAnonymousInt);
    }
  };
  private Context mContext;
  private int mCurrentFreq = 0;
  private int mDefaultVolum = 9;
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (MiuiFMReceiver.this.onFMEventListener == null)
        return;
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
        MiuiFMReceiver.this.onFMEventListener.onScanComplete(true);
        return;
      case 2:
        MiuiFMReceiver.this.onFMEventListener.onChannelFound(paramAnonymousMessage.arg1);
        return;
      case 3:
        MiuiFMReceiver.this.onFMEventListener.onFMOff();
        return;
      case 4:
        MiuiFMReceiver.this.onFMEventListener.onFMOn();
        return;
      case 5:
        MiuiFMReceiver.this.onFMEventListener.onScanStarted();
        return;
      case 6:
      }
      MiuiFMReceiver.this.onFMEventListener.onTune(paramAnonymousMessage.arg1);
    }
  };
  private boolean mIsFMEnable = false;
  private boolean mIsPlay = false;
  private boolean mIsScan = false;
  private boolean mMute = false;
  private boolean mOpenBlueTooth = false;
  private Class motorolaService;
  private boolean needRegisterClient = false;
  private IFMEventListener onFMEventListener;
  private Context paramContext = null;
  private Method setChSpacingMethod;
  private Method setEmphasisMethod;
  private Method setLowerLimitMedthod;
  private Method setRadioBandMethod;
  private Method setRdsStdMethod;
  private Method setUpperLimitMethod;

  public MiuiFMReceiver(Context paramContext1)
  {
    super(paramContext1);
    this.mContext = paramContext1;
    initClass();
  }

  private Boolean acquireReceiver(String paramString)
  {
    return Boolean.valueOf(true);
  }

  private void apiAMsetParameter(String paramString1, String paramString2)
  {
    try
    {
      Method localMethod = this.asSetParameterMethod;
      Object localObject = this.amObj;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = String.valueOf(paramString1);
      arrayOfObject[1] = String.valueOf(paramString2);
      localMethod.invoke(localObject, arrayOfObject);
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

  private void apiDisableAF()
  {
  }

  private void apiDisableRDS()
  {
  }

  private void apiEnableAF()
  {
  }

  private void apiEnableRDS()
  {
  }

  private long apiGetCurrentChannel()
  {
    return 10 * getTunedFrequencyReceiver();
  }

  private long apiGetCurrentRSSI()
  {
    return -2147483648L;
  }

  private long[] apiGetLastScanResult()
  {
    return new long[0];
  }

  private boolean apiIsHeadsetPlugged()
  {
    return false;
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
    if (this.needRegisterClient);
    try
    {
      mute(true);
      stopFM();
      if (this.mIsPlay)
      {
        this.mIsFMEnable = false;
        disableReceiver();
        apiAMsetParameter("FM", "off");
      }
      this.mIsPlay = false;
      this.mIsFMEnable = false;
      Message localMessage = Message.obtain(this.mHandler, 3, null);
      this.mHandler.sendMessage(localMessage);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private boolean apiOn()
  {
    if (!this.mIsFMEnable)
    {
      startFM();
      if (acquireReceiver("/dev/radio0").booleanValue())
      {
        enableReceiver();
        this.mIsFMEnable = true;
        Message localMessage = Message.obtain(this.mHandler, 4, null);
        this.mHandler.sendMessage(localMessage);
      }
    }
    while (true)
      return this.mIsFMEnable;
  }

  private void apiRemoveListener(Object paramObject)
  {
  }

  private int apiScan()
  {
    if (!searchStationsReceiver());
    return -1;
  }

  private long apiSeekDown()
  {
    return -9223372036854775808L;
  }

  private long apiSeekUp()
  {
    return -9223372036854775808L;
  }

  private void apiSetBand(int paramInt)
  {
  }

  private void apiSetChannelSpacing(int paramInt)
  {
  }

  private void apiSetDEConstant(long paramLong)
  {
  }

  private void apiSetListener(Object paramObject)
  {
  }

  private void apiSetSpeakerOn(boolean paramBoolean)
  {
    if (this.asSetForceUseMethod == null)
      return;
    if (paramBoolean);
    try
    {
      Method localMethod2 = this.asSetForceUseMethod;
      Class localClass2 = this.audioSystem;
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = Integer.valueOf(1);
      arrayOfObject2[1] = Integer.valueOf(1);
      localMethod2.invoke(localClass2, arrayOfObject2);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return;
      Method localMethod1 = this.asSetForceUseMethod;
      Class localClass1 = this.audioSystem;
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = Integer.valueOf(1);
      arrayOfObject1[1] = Integer.valueOf(0);
      localMethod1.invoke(localClass1, arrayOfObject1);
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

  private void apiStopScan()
  {
    cancelSearchStationReceiver();
    this.mIsScan = false;
  }

  private void apiTune(long paramLong)
  {
  }

  private void apisetVolume(long paramLong)
  {
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
      arrayOfObject3[0] = Integer.valueOf(4);
      localMethod3.invoke(localObject3, arrayOfObject3);
      Method localMethod4 = this.setRdsStdMethod;
      Object localObject4 = this.fmConfigObj;
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = Integer.valueOf(1);
      localMethod4.invoke(localObject4, arrayOfObject4);
      Method localMethod5 = this.setLowerLimitMedthod;
      Object localObject5 = this.fmConfigObj;
      Object[] arrayOfObject5 = new Object[1];
      arrayOfObject5[0] = Integer.valueOf(87000);
      localMethod5.invoke(localObject5, arrayOfObject5);
      Method localMethod6 = this.setUpperLimitMethod;
      Object localObject6 = this.fmConfigObj;
      Object[] arrayOfObject6 = new Object[1];
      arrayOfObject6[0] = Integer.valueOf(108000);
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

  private void buildSetVolumMethod()
  {
    this.availableLast = true;
    try
    {
      this.cmSetVolumMethod = null;
      this.availableLast = true;
      return;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
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

  private boolean configureReceiver()
  {
    try
    {
      Method localMethod = this.cmConfigureMethod;
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

  private boolean enableOtherReceiver()
  {
    boolean bool1 = false;
    try
    {
      Method localMethod1 = this.cmSetPowerModeMethod;
      Object localObject1 = this.fmReceiverObj;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(1);
      bool1 = ((Boolean)localMethod1.invoke(localObject1, arrayOfObject1)).booleanValue();
      Method localMethod2 = this.cmRegisterRdsGroupProcessingMethod;
      Object localObject2 = this.fmReceiverObj;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(23);
      bool1 = ((Boolean)localMethod2.invoke(localObject2, arrayOfObject2)).booleanValue();
      Method localMethod3 = this.cmSetInternalAntennaMethod;
      Object localObject3 = this.fmReceiverObj;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Boolean.valueOf(true);
      boolean bool2 = ((Boolean)localMethod3.invoke(localObject3, arrayOfObject3)).booleanValue();
      return bool2;
    }
    catch (Exception localException)
    {
    }
    return bool1;
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

  private boolean getAudioSystem()
  {
    if (this.audioSystem == null);
    try
    {
      this.audioSystem = Class.forName("android.media.AudioSystem");
      return true;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    return false;
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
      if (!isMIUIROM())
        return;
      this.fmConfigClass = Class.forName("android.hardware.fmradio.FmConfig", true, this.mContext.getClassLoader());
      this.fmConfigObj = this.fmConfigClass.getDeclaredConstructor(null).newInstance(null);
      this.amObj = this.mContext.getSystemService("audio");
      this.amClass = this.amObj.getClass();
      this.fmRxEvCallbackAdaptorClass = Class.forName("android.hardware.fmradio.FmRxEvCallbacksAdaptor");
      this.fmRxEvCallbackAdaptorObj = this.fmRxEvCallbackAdaptorClass.getDeclaredConstructor(null).newInstance(null);
      this.fmRxEvCallbacksClass = Class.forName("android.hardware.fmradio.FmRxEvCallbacks");
      ClassLoader localClassLoader = this.fmRxEvCallbacksClass.getClassLoader();
      Class[] arrayOfClass1 = new Class[1];
      arrayOfClass1[0] = this.fmRxEvCallbacksClass;
      this.fmRxEvCallbacksObj = Proxy.newProxyInstance(localClassLoader, arrayOfClass1, this.invocationHandler);
      this.fmReceiverClass = Class.forName("android.hardware.fmradio.FmReceiver", true, this.mContext.getClassLoader());
      Class localClass = this.fmReceiverClass;
      Class[] arrayOfClass2 = new Class[2];
      arrayOfClass2[0] = String.class;
      arrayOfClass2[1] = this.fmRxEvCallbackAdaptorClass;
      Constructor localConstructor = localClass.getDeclaredConstructor(arrayOfClass2);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = String.valueOf("/dev/radio0");
      arrayOfObject[1] = null;
      this.fmReceiverObj = localConstructor.newInstance(arrayOfObject);
      setUpInnerMethodsFromAM();
      setUpInnerMethodFromReceiver();
      setUpInnerMethodFromConfig();
      setUpInnerMethodFromTransceiver();
      buildDefaultFMConfig();
      return;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
        localIllegalArgumentException.printStackTrace();
    }
    catch (InstantiationException localInstantiationException)
    {
      while (true)
        localInstantiationException.printStackTrace();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        localIllegalAccessException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        localInvocationTargetException.printStackTrace();
    }
    catch (SecurityException localSecurityException)
    {
      while (true)
        localSecurityException.printStackTrace();
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
        localNoSuchMethodException.printStackTrace();
    }
  }

  private boolean initService()
  {
    return false;
  }

  private boolean isMIUIROM()
  {
    try
    {
      Context localContext = this.mContext.createPackageContext("com.miui.fmradio", 3);
      if (localContext != null)
        return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private boolean isNeedRegisterClient()
  {
    this.needRegisterClient = true;
    return true;
  }

  private void registerClientReceiver()
  {
    if (this.isClientRegistered)
      return;
    try
    {
      Method localMethod = this.cmRegisterClientMethod;
      Object localObject = this.fmReceiverObj;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.fmRxEvCallbacksObj;
      boolean bool = ((Boolean)localMethod.invoke(localObject, arrayOfObject)).booleanValue();
      this.isClientRegistered = true;
      Log.e("test.java", "Miui register client ret = " + bool);
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

  private boolean searchStationListReceiver(int paramInt)
  {
    try
    {
      Method localMethod = this.cmSearchStationListMethod;
      Object localObject = this.fmReceiverObj;
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = Integer.valueOf(2);
      arrayOfObject[1] = Integer.valueOf(1);
      arrayOfObject[2] = Integer.valueOf(paramInt);
      arrayOfObject[3] = Integer.valueOf(0);
      boolean bool2 = ((Boolean)localMethod.invoke(localObject, arrayOfObject)).booleanValue();
      bool1 = bool2;
      Log.e("fm", "searchStationListReceiver: " + bool1);
      return bool1;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
      {
        localIllegalArgumentException.printStackTrace();
        bool1 = false;
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
      {
        localIllegalAccessException.printStackTrace();
        bool1 = false;
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
      {
        localInvocationTargetException.printStackTrace();
        boolean bool1 = false;
      }
    }
  }

  private boolean searchStationsReceiver()
  {
    try
    {
      registerClientReceiver();
      Method localMethod = this.cmSearchStationsMethod;
      Object localObject = this.fmReceiverObj;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(1);
      arrayOfObject[1] = Integer.valueOf(3);
      arrayOfObject[2] = Integer.valueOf(1);
      boolean bool2 = ((Boolean)localMethod.invoke(localObject, arrayOfObject)).booleanValue();
      bool1 = bool2;
      Log.e("fm", "search: " + bool1);
      return bool1;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
      {
        localIllegalArgumentException.printStackTrace();
        bool1 = false;
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
      {
        localIllegalAccessException.printStackTrace();
        bool1 = false;
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
      {
        localInvocationTargetException.printStackTrace();
        boolean bool1 = false;
      }
    }
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

  private void setUpInnerMethodFromReceiver()
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
      Class localClass3 = this.fmReceiverClass;
      Class[] arrayOfClass3 = new Class[4];
      arrayOfClass3[0] = Integer.TYPE;
      arrayOfClass3[1] = Integer.TYPE;
      arrayOfClass3[2] = Integer.TYPE;
      arrayOfClass3[3] = Integer.TYPE;
      this.cmSearchStationListMethod = localClass3.getDeclaredMethod("searchStationList", arrayOfClass3);
      this.cmCancelSearchMethod = this.fmReceiverClass.getDeclaredMethod("cancelSearch", new Class[0]);
      this.cmGetRssiMethod = this.fmReceiverClass.getDeclaredMethod("getRssi", new Class[0]);
      Class localClass4 = this.fmReceiverClass;
      Class[] arrayOfClass4 = new Class[1];
      arrayOfClass4[0] = this.fmRxEvCallbacksClass;
      this.cmRegisterClientMethod = localClass4.getDeclaredMethod("registerClient", arrayOfClass4);
      this.cmUnRegisterClientMethod = this.fmReceiverClass.getDeclaredMethod("unregisterClient", new Class[0]);
      Class localClass5 = this.fmReceiverClass;
      Class[] arrayOfClass5 = new Class[1];
      arrayOfClass5[0] = Integer.TYPE;
      this.cmSetMuteModeMethod = localClass5.getMethod("setMuteMode", arrayOfClass5);
      Class localClass6 = this.fmReceiverClass;
      Class[] arrayOfClass6 = new Class[1];
      arrayOfClass6[0] = Boolean.TYPE;
      this.cmSetStereoModeMethod = localClass6.getDeclaredMethod("setStereoMode", arrayOfClass6);
      Class localClass7 = this.fmReceiverClass;
      Class[] arrayOfClass7 = new Class[1];
      arrayOfClass7[0] = Integer.TYPE;
      this.cmSetPowerModeMethod = localClass7.getDeclaredMethod("setPowerMode", arrayOfClass7);
      Class localClass8 = this.fmReceiverClass;
      Class[] arrayOfClass8 = new Class[1];
      arrayOfClass8[0] = Integer.TYPE;
      this.cmRegisterRdsGroupProcessingMethod = localClass8.getDeclaredMethod("registerRdsGroupProcessing", arrayOfClass8);
      this.availableFirst = true;
      buildSetVolumMethod();
      return;
    }
    catch (SecurityException localSecurityException)
    {
      while (true)
        localSecurityException.printStackTrace();
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
        localNoSuchMethodException.printStackTrace();
    }
  }

  private void setUpInnerMethodFromTransceiver()
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
      Class localClass3 = this.fmReceiverSuperClass;
      Class[] arrayOfClass3 = new Class[1];
      arrayOfClass3[0] = Boolean.TYPE;
      this.cmSetInternalAntennaMethod = localClass3.getMethod("setInternalAntenna", arrayOfClass3);
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

  private void setUpInnerMethodsFromAM()
  {
    try
    {
      this.asSetParameterMethod = this.amClass.getMethod("setParameter", new Class[] { String.class, String.class });
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

  private void setVolumReceiver(int paramInt)
  {
    if (this.cmSetVolumMethod == null)
      return;
    try
    {
      Method localMethod = this.cmSetVolumMethod;
      Object localObject = this.fmReceiverObj;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      localMethod.invoke(localObject, arrayOfObject);
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

  private void showChannels()
  {
    for (int i = 0; ; i++)
      if (i >= this.channels.size())
        return;
  }

  private void startFM()
  {
    registerClientReceiver();
    Intent localIntent = new Intent("qualcomm.intent.action.FM");
    localIntent.putExtra("state", 1);
    this.mContext.getApplicationContext().sendBroadcast(localIntent);
    this.hasStart = true;
  }

  private void startMiUiService()
  {
  }

  private void stopFM()
  {
    unRegisterClientReceiver();
    Intent localIntent = new Intent("qualcomm.intent.action.FM");
    localIntent.putExtra("state", 0);
    this.mContext.getApplicationContext().sendBroadcast(localIntent);
    this.hasStart = false;
  }

  private boolean turnOffBlueTooth()
  {
    boolean bool1 = this.mOpenBlueTooth;
    boolean bool2 = false;
    if (bool1)
    {
      BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
      bool2 = false;
      if (localBluetoothAdapter != null)
      {
        boolean bool3 = localBluetoothAdapter.isEnabled();
        bool2 = false;
        if (bool3)
          bool2 = localBluetoothAdapter.disable();
      }
    }
    return bool2;
  }

  private boolean turnOnBlueTooth()
  {
    BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    boolean bool1 = false;
    if (localBluetoothAdapter != null)
    {
      boolean bool2 = localBluetoothAdapter.isEnabled();
      bool1 = false;
      if (!bool2)
      {
        bool1 = localBluetoothAdapter.enable();
        if (bool1)
          this.mOpenBlueTooth = true;
      }
    }
    return bool1;
  }

  private void unRegisterClientReceiver()
  {
    if (!this.isClientRegistered)
      return;
    try
    {
      this.cmUnRegisterClientMethod.invoke(this.fmReceiverObj, new Object[0]);
      this.isClientRegistered = false;
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

  public void cancelScan()
  {
    apiStopScan();
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
    return "FmReceiver";
  }

  public int getVolume()
    throws Exception
  {
    return this.mDefaultVolum;
  }

  public boolean isAvailable()
  {
    return (this.availableFirst) && (this.availableLast);
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
    int i;
    if (paramBoolean)
      i = 1;
    try
    {
      while (true)
      {
        Method localMethod = this.cmSetMuteModeMethod;
        Object localObject = this.fmReceiverObj;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(i);
        ((Boolean)localMethod.invoke(localObject, arrayOfObject)).booleanValue();
        return;
        i = 0;
      }
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

  public void off()
  {
    apiOff();
  }

  public int on()
  {
    if (apiOn())
      return 1;
    return 0;
  }

  public void pause()
    throws Exception
  {
    if (this.needRegisterClient)
    {
      Intent localIntent = new Intent("android.intent.action.FM");
      localIntent.putExtra("state", 0);
      this.mContext.sendBroadcast(localIntent);
      this.mIsPlay = false;
      return;
    }
    off();
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
        if (!MiuiFMReceiver.this.mIsFMEnable)
        {
          if (MiuiFMReceiver.this.acquireReceiver("/dev/radio0").booleanValue())
          {
            MiuiFMReceiver.this.enableReceiver();
            MiuiFMReceiver.this.mIsFMEnable = true;
            Message localMessage3 = Message.obtain(MiuiFMReceiver.this.mHandler, 4, null);
            MiuiFMReceiver.this.mHandler.sendMessage(localMessage3);
          }
        }
        else
        {
          MiuiFMReceiver.this.mIsPlay = false;
          MiuiFMReceiver.this.apiAMsetParameter("FM", "off");
          MiuiFMReceiver.this.mIsScan = true;
          MiuiFMReceiver.this.channels.clear();
        }
        try
        {
          MiuiFMReceiver.this.mute(true);
          if (!MiuiFMReceiver.this.searchStationsReceiver())
          {
            Message localMessage1 = Message.obtain(MiuiFMReceiver.this.mHandler, 1, null);
            MiuiFMReceiver.this.mHandler.sendMessage(localMessage1);
          }
          return;
          Message localMessage2 = Message.obtain(MiuiFMReceiver.this.mHandler, 1, null);
          MiuiFMReceiver.this.mHandler.sendMessage(localMessage2);
          return;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
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
    if (getAudioSystem())
    {
      setUpMethodFromAudioSystem();
      apiSetSpeakerOn(paramBoolean);
    }
    return 0;
  }

  public void setVolum(int paramInt)
  {
    if (!this.needRegisterClient)
      setVolumReceiver(paramInt);
    this.mDefaultVolum = paramInt;
  }

  public void setVolume(int paramInt)
    throws Exception
  {
    setVolum(paramInt);
  }

  public int tune(int paramInt)
  {
    boolean bool;
    if (!this.mIsFMEnable)
      if (this.needRegisterClient)
      {
        bool = acquireReceiver("/dev/radio0").booleanValue();
        enableReceiver();
      }
    while (true)
    {
      if (bool)
      {
        this.mIsFMEnable = true;
        Message localMessage2 = Message.obtain(this.mHandler, 4, null);
        this.mHandler.sendMessage(localMessage2);
      }
      startFM();
      try
      {
        mute(false);
        if (bool)
        {
          this.mIsPlay = true;
          setStationReceiver(paramInt);
          apiAMsetParameter("FM", "on");
          Message localMessage1 = Message.obtain(this.mHandler, 6, null);
          localMessage1.arg1 = paramInt;
          this.mHandler.sendMessage(localMessage1);
          return 0;
          bool = enableReceiver();
          continue;
          bool = true;
        }
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
    return -1;
  }

  public void turnOff()
    throws Exception
  {
    if (this.needRegisterClient)
      turnOffBlueTooth();
    off();
  }

  public int turnOn()
    throws Exception
  {
    if (this.needRegisterClient)
      turnOnBlueTooth();
    return on();
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
 * Qualified Name:     fm.qingting.qtradio.miui2.MiuiFMReceiver
 * JD-Core Version:    0.6.2
 */