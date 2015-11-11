package fm.qingting.qtradio.fmreceive;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
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

public class FmReceiver extends FMDriver
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
  private static final String TAG = "FmReceiver";
  private Class ServiceManagerClass;
  private Class amClass;
  private Object amObj;
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
  private Method cmSearchStationListMethod;
  private Method cmSearchStationsMethod;
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
  private Class huaweiFM = null;
  private Class internalServiceClass;
  private Object internalServiceObject;
  private InvocationHandler invocationHandler = new InvocationHandler()
  {
    public Object invoke(Object paramAnonymousObject, Method paramAnonymousMethod, Object[] paramAnonymousArrayOfObject)
      throws Throwable
    {
      if ("FmRxEvEnableReceiver".equals(paramAnonymousMethod.getName()))
        Thread.sleep(5000L);
      do
      {
        do
        {
          int i;
          do
          {
            do
              return null;
            while ("FmRxEvDisableReceiver".equals(paramAnonymousMethod.getName()));
            if (!"FmRxEvRadioTuneStatus".equals(paramAnonymousMethod.getName()))
              break;
            i = ((Integer)paramAnonymousArrayOfObject[0]).intValue();
          }
          while (FmReceiver.this.inChannelList(i));
          FmReceiver.this.channels.add(Integer.valueOf(i));
          Message localMessage2 = Message.obtain(FmReceiver.this.mHandler, 2, null);
          localMessage2.arg1 = i;
          FmReceiver.this.mHandler.sendMessage(localMessage2);
          return null;
        }
        while (("FmRxEvRdsLockStatus".equals(paramAnonymousMethod.getName())) || ("FmRxEvStereoStatus".equals(paramAnonymousMethod.getName())) || ("FmRxEvServiceAvailable".equals(paramAnonymousMethod.getName())) || ("FmRxEvSearchInProgress".equals(paramAnonymousMethod.getName())));
        if ("FmRxEvSearchComplete".equals(paramAnonymousMethod.getName()))
        {
          Message localMessage1 = Message.obtain(FmReceiver.this.mHandler, 1, null);
          FmReceiver.this.mHandler.sendMessage(localMessage1);
          return null;
        }
      }
      while (("FmRxEvSearchListComplete".equals(paramAnonymousMethod.getName())) || ("FmRxEvRdsGroupData".equals(paramAnonymousMethod.getName())) || ("FmRxEvRdsPsInfo".equals(paramAnonymousMethod.getName())) || ("FmRxEvRdsRtInfo".equals(paramAnonymousMethod.getName())) || (!"FmRxEvRdsAfInfo".equals(paramAnonymousMethod.getName())));
      return null;
    }
  };
  private Class listenerClass;
  private Context mContext;
  private int mCurrentFreq = 0;
  private int mDefaultVolum = 9;
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (FmReceiver.this.onFMEventListener == null)
        return;
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
        FmReceiver.this.onFMEventListener.onScanComplete(true);
        return;
      case 2:
        FmReceiver.this.onFMEventListener.onChannelFound(paramAnonymousMessage.arg1);
        return;
      case 3:
        FmReceiver.this.onFMEventListener.onFMOff();
        return;
      case 4:
        FmReceiver.this.onFMEventListener.onFMOn();
        return;
      case 5:
        FmReceiver.this.onFMEventListener.onScanStarted();
        return;
      case 6:
      }
      FmReceiver.this.onFMEventListener.onTune(paramAnonymousMessage.arg1);
    }
  };
  private boolean mIsFMEnable = false;
  private boolean mIsPlay = false;
  private boolean mIsScan = false;
  private boolean mMute = false;
  private boolean mOpenBlueTooth = false;
  private boolean needRegisterClient = false;
  private IFMEventListener onFMEventListener;
  private Method setChSpacingMethod;
  private Method setEmphasisMethod;
  private Method setLowerLimitMedthod;
  private Method setRadioBandMethod;
  private Method setRdsStdMethod;
  private Method setUpperLimitMethod;

  public FmReceiver(Context paramContext)
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
      if (acquireReceiver("/dev/radio0").booleanValue())
      {
        enableReceiver();
        this.mIsFMEnable = true;
        Message localMessage = Message.obtain(this.mHandler, 4, null);
        this.mHandler.sendMessage(localMessage);
      }
    while (true)
      return this.mIsFMEnable;
  }

  private void apiRemoveListener(Object paramObject)
  {
  }

  private int apiScan()
  {
    if (!searchStationsReceiver())
      return -1;
    return getTunedFrequencyReceiver();
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

  private void buildSetVolumMethod()
  {
    try
    {
      Class localClass = this.fmReceiverClass;
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = Integer.TYPE;
      this.cmSetVolumMethod = localClass.getDeclaredMethod("setVolum", arrayOfClass);
      this.availableLast = true;
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
      if (isNeedRegisterClient())
        registerClientReceiver();
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

  private boolean isNeedRegisterClient()
  {
    Context localContext = null;
    try
    {
      localContext = this.mContext.createPackageContext("com.huawei.android.FMRadio", 3);
      this.availableLast = true;
      if (localContext == null)
        return false;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
        localNameNotFoundException.printStackTrace();
      this.needRegisterClient = true;
    }
    return true;
  }

  private void registerClientReceiver()
  {
    try
    {
      Method localMethod = this.cmRegisterClientMethod;
      Object localObject = this.fmReceiverObj;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.fmRxEvCallbacksObj;
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

  private boolean searchStationsReceiver()
  {
    try
    {
      Method localMethod = this.cmSearchStationsMethod;
      Object localObject = this.fmReceiverObj;
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(1);
      arrayOfObject[1] = Integer.valueOf(1);
      arrayOfObject[2] = Integer.valueOf(1);
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
      this.cmCancelSearchMethod = this.fmReceiverClass.getDeclaredMethod("cancelSearch", new Class[0]);
      this.cmGetRssiMethod = this.fmReceiverClass.getDeclaredMethod("getRssi", new Class[0]);
      Class localClass3 = this.fmReceiverClass;
      Class[] arrayOfClass3 = new Class[1];
      arrayOfClass3[0] = this.fmRxEvCallbacksClass;
      this.cmRegisterClientMethod = localClass3.getDeclaredMethod("registerClient", arrayOfClass3);
      this.cmUnRegisterClientMethod = this.fmReceiverClass.getDeclaredMethod("unregisterClient", new Class[0]);
      Class localClass4 = this.fmReceiverClass;
      Class[] arrayOfClass4 = new Class[1];
      arrayOfClass4[0] = Boolean.TYPE;
      this.cmSetStereoModeMethod = localClass4.getDeclaredMethod("setStereoMode", arrayOfClass4);
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
    try
    {
      this.cmUnRegisterClientMethod.invoke(this.fmReceiverObj, new Object[0]);
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
    if (paramBoolean)
    {
      this.mMute = true;
      if (this.needRegisterClient)
      {
        Intent localIntent = new Intent("android.intent.action.FM");
        localIntent.putExtra("state", 0);
        this.mContext.sendBroadcast(localIntent);
      }
    }
    else
    {
      return;
    }
    off();
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
        if (!FmReceiver.this.mIsFMEnable)
        {
          if (FmReceiver.this.acquireReceiver("/dev/radio0").booleanValue())
          {
            FmReceiver.this.enableReceiver();
            FmReceiver.this.mIsFMEnable = true;
            Message localMessage5 = Message.obtain(FmReceiver.this.mHandler, 4, null);
            FmReceiver.this.mHandler.sendMessage(localMessage5);
          }
        }
        else
        {
          FmReceiver.this.mIsPlay = false;
          FmReceiver.this.apiAMsetParameter("FM", "off");
          FmReceiver.this.mIsScan = true;
          FmReceiver.this.channels.clear();
          if (!FmReceiver.this.needRegisterClient)
            break label181;
          if (!FmReceiver.this.searchStationsReceiver())
          {
            Message localMessage3 = Message.obtain(FmReceiver.this.mHandler, 1, null);
            FmReceiver.this.mHandler.sendMessage(localMessage3);
          }
          return;
        }
        Message localMessage4 = Message.obtain(FmReceiver.this.mHandler, 1, null);
        FmReceiver.this.mHandler.sendMessage(localMessage4);
        return;
        label181: int i = FmReceiver.this.apiScan();
        if ((-1 == i) || (!FmReceiver.this.mIsScan));
        while (true)
        {
          Message localMessage1 = Message.obtain(FmReceiver.this.mHandler, 1, null);
          FmReceiver.this.mHandler.sendMessage(localMessage1);
          return;
          int j = i * 10;
          if (!FmReceiver.this.inChannelList(j))
          {
            Message localMessage2 = Message.obtain(FmReceiver.this.mHandler, 2, null);
            localMessage2.arg1 = j;
            FmReceiver.this.mHandler.sendMessage(localMessage2);
            FmReceiver.this.channels.add(Integer.valueOf(j));
            if (j < 108001)
              break;
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
        if (bool)
        {
          this.mIsFMEnable = true;
          Message localMessage2 = Message.obtain(this.mHandler, 4, null);
          this.mHandler.sendMessage(localMessage2);
        }
        label60: if (!bool)
          break label217;
        this.mIsPlay = true;
        if (this.needRegisterClient)
          break label208;
        setVolumReceiver(this.mDefaultVolum);
        setStationReceiver(paramInt / 10);
      }
    while (true)
    {
      Log.e("test.java", "tune, freq = " + paramInt);
      apiAMsetParameter("FM", "on");
      Message localMessage1 = Message.obtain(this.mHandler, 6, null);
      localMessage1.arg1 = paramInt;
      this.mHandler.sendMessage(localMessage1);
      if (this.needRegisterClient)
      {
        Intent localIntent = new Intent("android.intent.action.FM");
        localIntent.putExtra("state", 1);
        this.mContext.sendBroadcast(localIntent);
      }
      return 0;
      bool = enableReceiver();
      break;
      bool = true;
      break label60;
      label208: setStationReceiver(paramInt);
    }
    label217: return -1;
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
 * Qualified Name:     fm.qingting.qtradio.fmreceive.FmReceiver
 * JD-Core Version:    0.6.2
 */