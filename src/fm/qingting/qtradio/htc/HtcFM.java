package fm.qingting.qtradio.htc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import fm.qingting.qtradio.fmdriver.FMDriver;
import fm.qingting.qtradio.fmdriver.IFMEventListener;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class HtcFM extends FMDriver
{
  private static final String TAG = "HtcFM";
  private Object FMBtsUtils;
  private Object OnEventChangedListener;
  private Object OnRemoteEventControlListener;
  private Object OnServiceStateChangedListener;
  private Object OnStateChangedListener;
  private boolean available = false;
  private Method bindServiceMethod;
  private Method cancelNotificationMethod;
  private ArrayList<Integer> channels = new ArrayList();
  private Method clearAllPresetsMethod;
  private Method clearFirstChannelMethod;
  private String cmdtype = "";
  private Method disableAudioMethod;
  private Class fMBtsUtilsClass;
  private Object fMController;
  private Class fMControllerClass;
  private Class fMUtilsClass;
  private Constructor fmControlConstructor;
  private int freqQ = 0;
  private Method getAirModeWarningMessageMethod;
  private Method getAudioPathMethod;
  private Method getBandMethod;
  private Method getChannelListMethod;
  private Method getCmdStateMethod;
  private Method getFirstPresetIdMethod;
  private Method getFrequencyMethod;
  private Method getHeadsetWarningMessageMethod;
  private Method getRdsMethod;
  private Method getRssiMethod;
  private Method getStateMessageMethod;
  private Method getStateMethod;
  private InvocationHandler invocationHandler = new InvocationHandler()
  {
    public Object invoke(Object paramAnonymousObject, Method paramAnonymousMethod, Object[] paramAnonymousArrayOfObject)
      throws Throwable
    {
      if (paramAnonymousMethod.getName().equalsIgnoreCase("onServiceBinded"))
        HtcFM.this.apituneon(1);
      if (paramAnonymousMethod.getName().equalsIgnoreCase("onTurnOff"))
      {
        HtcFM.this.listener.onFMOff();
        if ((!paramAnonymousMethod.getName().equalsIgnoreCase("onFrequencyChanged")) && (!paramAnonymousMethod.getName().equalsIgnoreCase("onHeadsetPlugged")) && (!paramAnonymousMethod.getName().equalsIgnoreCase("onHeadsetUnPlugged")) && (paramAnonymousMethod.getName().equalsIgnoreCase("onRssiChanged")))
          HtcFM.this.riss = ((Integer)Array.get(paramAnonymousArrayOfObject, 1)).intValue();
        if (!paramAnonymousMethod.getName().equalsIgnoreCase("onScanComplete"))
          break label254;
        HtcFM.this.cmdtype = "";
        Log.d("onScanComplete", "onScanComplete");
        HtcFM.this.listener.onScanComplete(true);
        HtcFM.this.apiturnOff();
      }
      while (true)
      {
        return null;
        if (!paramAnonymousMethod.getName().equalsIgnoreCase("onTurnOn"))
          break;
        HtcFM.this.listener.onFMOn();
        if (HtcFM.this.cmdtype.equalsIgnoreCase("scan"))
          HtcFM.this.apiscan();
        while (true)
        {
          HtcFM.this.cmdtype = "";
          break;
          if (HtcFM.this.cmdtype.equalsIgnoreCase("play"))
            HtcFM.this.apitune(HtcFM.this.freqQ);
        }
        label254: if (paramAnonymousMethod.getName().equalsIgnoreCase("onSeekUpComplete"))
        {
          int i = ((Integer)Array.get(paramAnonymousArrayOfObject, 0)).intValue();
          HtcFM.this.channels.add(Integer.valueOf(i));
          HtcFM.this.listener.onChannelFound(i);
        }
        else if (paramAnonymousMethod.getName().equalsIgnoreCase("onStartScan"))
        {
          HtcFM.this.cmdtype = "";
          HtcFM.this.listener.onScanStarted();
          HtcFM.this.channels.clear();
        }
        else if (!paramAnonymousMethod.getName().equalsIgnoreCase("onTuneComplete"))
        {
          if (paramAnonymousMethod.getName().equalsIgnoreCase("onTuning"))
            ((Integer)Array.get(paramAnonymousArrayOfObject, 0)).intValue();
          else
            paramAnonymousMethod.getName().equalsIgnoreCase("onVolumeChanged");
        }
      }
    }
  };
  private Method isFMPlayingMethod;
  private Method isFMReadyMethod;
  private Method isHeadsetPluggedMethod;
  private Method isRdsOnMethod;
  private Method isRssiOnMethod;
  private Method isScanningMethod;
  private IFMEventListener listener;
  private AudioManager mAM;
  private Context mContext;
  private BroadcastReceiver mReceiver = null;
  private PowerManager.WakeLock mWakeLock;
  private Class onEventChangedListenerclaClass;
  private Method onPresetUpdatedMethod;
  private Class onRemoteEventControlListenerClass;
  private Class onServiceStateChangedListenerClass;
  private Class onStateChangedListenerClass;
  private Class presetClass;
  private int riss = 0;
  private Method scanMethod;
  private Method seekDownMethod;
  private Method seekUpMethod;
  private Method serviceBindedMethod;
  private Method setAutoPreScanEnabledMethod;
  private Method setBandMethod;
  private Method setHeadsetOutMethod;
  private Method setMonoMethod;
  private Method setMuteMethod;
  private Method setOnEventChangedListenerMethod;
  private Method setOnRemoteEventControlListenerMethod;
  private Method setOnServiceStateChangedListenerMethod;
  private Method setOnStateChangedListenerMethod;
  private Method setRdsOffMethod;
  private Method setRdsOnMethod;
  private Method setRssiOffMethod;
  private Method setRssiOnMethod;
  private Method setSpeakerOutMethod;
  private Method setStereoMethod;
  private Method setUnMuteMethod;
  private Method startMethod;
  private Method stopScanMethod;
  private Method tuneMethod;
  private Method turnOffMethod;
  private Method turnOn2Method;
  private Method turnOnMethod;
  private Method unbindServiceMethod;

  public HtcFM(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mAM = ((AudioManager)paramContext.getSystemService("audio"));
    this.mWakeLock = ((PowerManager)paramContext.getSystemService("power")).newWakeLock(6, getClass().getName());
    this.mWakeLock.setReferenceCounted(false);
    initClass(paramContext);
  }

  private boolean apiaudioOutDisable()
  {
    try
    {
      boolean bool = ((Boolean)this.disableAudioMethod.invoke(this.fMController, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
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
  }

  private String apiaudioOutHeadset()
  {
    try
    {
      String str = (String)this.getAirModeWarningMessageMethod.invoke(this.fMController, new Object[0]);
      return str;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return "null";
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
  }

  private boolean apibindService()
  {
    try
    {
      boolean bool = ((Boolean)this.bindServiceMethod.invoke(this.fMController, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
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
  }

  private void apiclearAllPresets(Object paramObject)
  {
    try
    {
      this.clearAllPresetsMethod.invoke(this.fMUtilsClass, new Object[] { paramObject });
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

  private int apigetBand()
  {
    try
    {
      int i = ((Integer)this.getBandMethod.invoke(this.fMController, new Object[0])).intValue();
      return i;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return 0;
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
  }

  private ArrayList<Preset> apigetChannlelist(Context paramContext)
  {
    try
    {
      ArrayList localArrayList = (ArrayList)this.getChannelListMethod.invoke(this.fMUtilsClass, new Object[] { paramContext });
      return localArrayList;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return null;
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
  }

  private int apigetFrequency()
  {
    try
    {
      int i = ((Integer)this.getFrequencyMethod.invoke(this.fMController, new Object[0])).intValue();
      return i;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return 0;
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
  }

  private int apigetRssi()
  {
    try
    {
      int i = ((Integer)this.getRssiMethod.invoke(this.fMController, new Object[0])).intValue();
      return i;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return 0;
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
  }

  private boolean apiisFMPlaying()
  {
    try
    {
      boolean bool = ((Boolean)this.isFMPlayingMethod.invoke(this.fMController, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
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
  }

  private boolean apiisFMReady()
  {
    try
    {
      boolean bool = ((Boolean)this.isFMReadyMethod.invoke(this.fMController, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
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
  }

  private boolean apiisHeadsetPlugged()
  {
    try
    {
      boolean bool = ((Boolean)this.isHeadsetPluggedMethod.invoke(this.fMUtilsClass, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
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
  }

  private boolean apiisScanning()
  {
    try
    {
      boolean bool = ((Boolean)this.isScanningMethod.invoke(this.fMController, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
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
  }

  private void apiscan()
  {
    try
    {
      this.scanMethod.invoke(this.fMController, new Object[0]);
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

  private void apisetBand(int paramInt)
  {
    try
    {
      Method localMethod = this.setBandMethod;
      Object localObject = this.fMController;
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

  private void apisetOnEventChangedListener(Object paramObject)
  {
    try
    {
      this.setOnEventChangedListenerMethod.invoke(this.fMController, new Object[] { paramObject });
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

  private void apisetOnRemoteEventControlListener(Object paramObject)
  {
    try
    {
      this.setOnRemoteEventControlListenerMethod.invoke(this.fMController, new Object[] { paramObject });
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

  private void apisetOnServiceStateChangedListener(Object paramObject)
  {
    try
    {
      this.setOnServiceStateChangedListenerMethod.invoke(this.fMController, new Object[] { paramObject });
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

  private void apisetOnStateChangedListener(Object paramObject)
  {
    try
    {
      this.setOnStateChangedListenerMethod.invoke(this.fMController, new Object[] { paramObject });
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

  private boolean apisetSpeakerOut()
  {
    try
    {
      boolean bool = ((Boolean)this.setSpeakerOutMethod.invoke(this.fMController, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
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
  }

  private boolean apistart()
  {
    try
    {
      boolean bool = ((Boolean)this.startMethod.invoke(this.fMController, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
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
  }

  private void apistopScan()
  {
    try
    {
      this.stopScanMethod.invoke(this.fMController, new Object[0]);
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

  private boolean apitune(int paramInt)
  {
    try
    {
      Method localMethod = this.tuneMethod;
      Object localObject = this.fMController;
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
      while (true)
        localIllegalAccessException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        localInvocationTargetException.printStackTrace();
    }
  }

  private boolean apituneon(int paramInt)
  {
    try
    {
      Method localMethod = this.turnOnMethod;
      Object localObject = this.fMController;
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
      while (true)
        localIllegalAccessException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        localInvocationTargetException.printStackTrace();
    }
  }

  private boolean apituneon(int paramInt1, int paramInt2)
  {
    try
    {
      Method localMethod = this.turnOn2Method;
      Object localObject = this.fMController;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt1);
      arrayOfObject[1] = Integer.valueOf(paramInt2);
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
      while (true)
        localIllegalAccessException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        localInvocationTargetException.printStackTrace();
    }
  }

  private boolean apiturnOff()
  {
    try
    {
      boolean bool = ((Boolean)this.turnOffMethod.invoke(this.fMController, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
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
  }

  private boolean apiunbindService()
  {
    try
    {
      boolean bool = ((Boolean)this.unbindServiceMethod.invoke(this.fMController, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
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
  }

  private void initClass(Context paramContext)
  {
    try
    {
      Context localContext = paramContext.createPackageContext("com.htc.fm", 3);
      this.fMControllerClass = Class.forName("com.htc.fm.FMController", true, localContext.getClassLoader());
      this.onEventChangedListenerclaClass = Class.forName("com.htc.fm.OnEventChangedListener", true, localContext.getClassLoader());
      this.onRemoteEventControlListenerClass = Class.forName("com.htc.fm.OnRemoteEventControlListener", true, localContext.getClassLoader());
      this.onServiceStateChangedListenerClass = Class.forName("com.htc.fm.OnServiceStateChangedListener", true, localContext.getClassLoader());
      this.onStateChangedListenerClass = Class.forName("com.htc.fm.OnStateChangedListener", true, localContext.getClassLoader());
      this.fMBtsUtilsClass = Class.forName("com.htc.fm.FMBtsUtils", true, localContext.getClassLoader());
      this.fMUtilsClass = Class.forName("com.htc.fm.FMUtils", true, localContext.getClassLoader());
      this.presetClass = Class.forName("com.htc.fm.Preset", true, localContext.getClassLoader());
      this.getChannelListMethod = this.fMUtilsClass.getMethod("getPresetList", new Class[] { Context.class });
      this.getChannelListMethod = this.fMUtilsClass.getMethod("getPresetList", new Class[] { Context.class });
      this.isHeadsetPluggedMethod = this.fMUtilsClass.getMethod("isHeadsetPlugged", new Class[0]);
      this.clearFirstChannelMethod = this.fMUtilsClass.getMethod("clearFirstChannel", new Class[0]);
      this.clearAllPresetsMethod = this.fMUtilsClass.getDeclaredMethod("clearAllPresets", new Class[] { Context.class });
      ClassLoader localClassLoader1 = this.onEventChangedListenerclaClass.getClassLoader();
      Class[] arrayOfClass1 = new Class[1];
      arrayOfClass1[0] = this.onEventChangedListenerclaClass;
      this.OnEventChangedListener = Proxy.newProxyInstance(localClassLoader1, arrayOfClass1, this.invocationHandler);
      ClassLoader localClassLoader2 = this.onRemoteEventControlListenerClass.getClassLoader();
      Class[] arrayOfClass2 = new Class[1];
      arrayOfClass2[0] = this.onRemoteEventControlListenerClass;
      this.OnRemoteEventControlListener = Proxy.newProxyInstance(localClassLoader2, arrayOfClass2, this.invocationHandler);
      ClassLoader localClassLoader3 = this.onServiceStateChangedListenerClass.getClassLoader();
      Class[] arrayOfClass3 = new Class[1];
      arrayOfClass3[0] = this.onServiceStateChangedListenerClass;
      this.OnServiceStateChangedListener = Proxy.newProxyInstance(localClassLoader3, arrayOfClass3, this.invocationHandler);
      ClassLoader localClassLoader4 = this.onStateChangedListenerClass.getClassLoader();
      Class[] arrayOfClass4 = new Class[1];
      arrayOfClass4[0] = this.onStateChangedListenerClass;
      this.OnStateChangedListener = Proxy.newProxyInstance(localClassLoader4, arrayOfClass4, this.invocationHandler);
      this.bindServiceMethod = this.fMControllerClass.getMethod("bindService", new Class[0]);
      this.disableAudioMethod = this.fMControllerClass.getMethod("disableAudio", new Class[0]);
      this.getAirModeWarningMessageMethod = this.fMControllerClass.getMethod("getAirModeWarningMessage", new Class[0]);
      this.getAudioPathMethod = this.fMControllerClass.getMethod("getAudioPath", new Class[0]);
      this.getBandMethod = this.fMControllerClass.getMethod("getBand", new Class[0]);
      this.getCmdStateMethod = this.fMControllerClass.getMethod("getCmdState", new Class[0]);
      this.getFirstPresetIdMethod = this.fMControllerClass.getMethod("getFirstPresetId", new Class[0]);
      this.getFrequencyMethod = this.fMControllerClass.getMethod("getFrequency", new Class[0]);
      this.getHeadsetWarningMessageMethod = this.fMControllerClass.getMethod("getHeadsetWarningMessage", new Class[0]);
      this.getRdsMethod = this.fMControllerClass.getMethod("getRds", new Class[0]);
      this.getRssiMethod = this.fMControllerClass.getMethod("getRssi", new Class[0]);
      this.getStateMethod = this.fMControllerClass.getMethod("getState", new Class[0]);
      this.getStateMessageMethod = this.fMControllerClass.getMethod("getStateMessage", new Class[0]);
      this.isFMPlayingMethod = this.fMControllerClass.getMethod("isFMPlaying", new Class[0]);
      this.isFMReadyMethod = this.fMControllerClass.getMethod("isFMReady", new Class[0]);
      this.isRdsOnMethod = this.fMControllerClass.getMethod("isRdsOn", new Class[0]);
      this.isRssiOnMethod = this.fMControllerClass.getMethod("isRssiOn", new Class[0]);
      this.isScanningMethod = this.fMControllerClass.getMethod("isScanning", new Class[0]);
      Class localClass1 = this.fMControllerClass;
      Class[] arrayOfClass5 = new Class[1];
      arrayOfClass5[0] = Integer.TYPE;
      this.onPresetUpdatedMethod = localClass1.getMethod("onPresetUpdated", arrayOfClass5);
      this.scanMethod = this.fMControllerClass.getMethod("scan", new Class[0]);
      this.seekDownMethod = this.fMControllerClass.getMethod("seekDown", new Class[0]);
      this.seekUpMethod = this.fMControllerClass.getMethod("seekUp", new Class[0]);
      this.serviceBindedMethod = this.fMControllerClass.getMethod("serviceBinded", new Class[0]);
      Class localClass2 = this.fMControllerClass;
      Class[] arrayOfClass6 = new Class[1];
      arrayOfClass6[0] = Boolean.TYPE;
      this.setAutoPreScanEnabledMethod = localClass2.getMethod("setAutoPreScanEnabled", arrayOfClass6);
      Class localClass3 = this.fMControllerClass;
      Class[] arrayOfClass7 = new Class[1];
      arrayOfClass7[0] = Integer.TYPE;
      this.setBandMethod = localClass3.getMethod("setBand", arrayOfClass7);
      this.setHeadsetOutMethod = this.fMControllerClass.getMethod("setHeadsetOut", new Class[0]);
      this.setMonoMethod = this.fMControllerClass.getMethod("setMono", new Class[0]);
      Class localClass4 = this.fMControllerClass;
      Class[] arrayOfClass8 = new Class[1];
      arrayOfClass8[0] = this.onEventChangedListenerclaClass;
      this.setOnEventChangedListenerMethod = localClass4.getMethod("setOnEventChangedListener", arrayOfClass8);
      Class localClass5 = this.fMControllerClass;
      Class[] arrayOfClass9 = new Class[1];
      arrayOfClass9[0] = this.onServiceStateChangedListenerClass;
      this.setOnServiceStateChangedListenerMethod = localClass5.getMethod("setOnServiceStateChangedListener", arrayOfClass9);
      Class localClass6 = this.fMControllerClass;
      Class[] arrayOfClass10 = new Class[1];
      arrayOfClass10[0] = this.onStateChangedListenerClass;
      this.setOnStateChangedListenerMethod = localClass6.getMethod("setOnStateChangedListener", arrayOfClass10);
      Class localClass7 = this.fMControllerClass;
      Class[] arrayOfClass11 = new Class[1];
      arrayOfClass11[0] = this.onRemoteEventControlListenerClass;
      this.setOnRemoteEventControlListenerMethod = localClass7.getMethod("setOnRemoteEventControlListener", arrayOfClass11);
      this.setRdsOffMethod = this.fMControllerClass.getMethod("setRdsOff", new Class[0]);
      this.setRdsOnMethod = this.fMControllerClass.getMethod("setRdsOn", new Class[0]);
      this.setRssiOffMethod = this.fMControllerClass.getMethod("setRssiOff", new Class[0]);
      this.setRssiOnMethod = this.fMControllerClass.getMethod("setRssiOn", new Class[0]);
      this.setSpeakerOutMethod = this.fMControllerClass.getMethod("setSpeakerOut", new Class[0]);
      this.setStereoMethod = this.fMControllerClass.getMethod("setStereo", new Class[0]);
      this.startMethod = this.fMControllerClass.getMethod("start", new Class[0]);
      this.stopScanMethod = this.fMControllerClass.getMethod("stopScan", new Class[0]);
      Class localClass8 = this.fMControllerClass;
      Class[] arrayOfClass12 = new Class[1];
      arrayOfClass12[0] = Integer.TYPE;
      this.tuneMethod = localClass8.getMethod("tune", arrayOfClass12);
      this.turnOffMethod = this.fMControllerClass.getMethod("turnOff", new Class[0]);
      Class localClass9 = this.fMControllerClass;
      Class[] arrayOfClass13 = new Class[1];
      arrayOfClass13[0] = Integer.TYPE;
      this.turnOnMethod = localClass9.getMethod("turnOn", arrayOfClass13);
      Class localClass10 = this.fMControllerClass;
      Class[] arrayOfClass14 = new Class[2];
      arrayOfClass14[0] = Integer.TYPE;
      arrayOfClass14[1] = Integer.TYPE;
      this.turnOn2Method = localClass10.getMethod("turnOn", arrayOfClass14);
      this.unbindServiceMethod = this.fMControllerClass.getMethod("unbindService", new Class[0]);
      this.cancelNotificationMethod = this.fMBtsUtilsClass.getMethod("cancelNotification", new Class[] { Context.class });
      this.fmControlConstructor = this.fMControllerClass.getDeclaredConstructor(new Class[] { Context.class });
      this.FMBtsUtils = this.fMBtsUtilsClass.newInstance();
      this.fMController = this.fmControlConstructor.newInstance(new Object[] { localContext });
      apisetOnEventChangedListener(this.OnEventChangedListener);
      apisetOnRemoteEventControlListener(this.OnRemoteEventControlListener);
      apisetOnServiceStateChangedListener(this.OnServiceStateChangedListener);
      apisetOnStateChangedListener(this.OnStateChangedListener);
      this.available = true;
      if (this.available)
        registerBroadcastReceiver();
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
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return;
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
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

  private void registerBroadcastReceiver()
  {
    if (this.mReceiver != null)
      return;
    this.mReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        if (paramAnonymousIntent.getAction().equals("android.intent.action.HEADSET_PLUG"))
          paramAnonymousIntent.getIntExtra("state", 0);
      }
    };
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.HEADSET_PLUG");
    this.mContext.registerReceiver(this.mReceiver, localIntentFilter);
  }

  public void cancelScanning()
  {
    apistopScan();
    apiunbindService();
    apiturnOff();
  }

  public ArrayList<Integer> getAvailableChannels()
  {
    return this.channels;
  }

  public int getCurrentChannel()
  {
    return apigetFrequency();
  }

  public int getCurrentRSSI()
  {
    return this.riss;
  }

  public String getName()
  {
    return "HtcFM";
  }

  public int getVolume()
    throws Exception
  {
    return 0;
  }

  public boolean isAvailable()
  {
    return false;
  }

  public boolean isHeadsetPlugged()
  {
    return apiisHeadsetPlugged();
  }

  public boolean isMute()
    throws Exception
  {
    return false;
  }

  public boolean isOn()
  {
    if (apiisFMReady())
      return apiisFMPlaying();
    return false;
  }

  public boolean isPaused()
    throws Exception
  {
    return false;
  }

  public boolean isScanning()
  {
    return apiisScanning();
  }

  public boolean isSpeakerOn()
    throws Exception
  {
    return false;
  }

  public void mute(boolean paramBoolean)
    throws Exception
  {
  }

  public void pause()
    throws Exception
  {
  }

  public void registerFMEventListener(IFMEventListener paramIFMEventListener)
  {
    this.listener = paramIFMEventListener;
  }

  public void scan()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        HtcFM.this.apiclearAllPresets(HtcFM.this.mContext);
        if (HtcFM.this.apiisFMPlaying())
          HtcFM.this.apiscan();
        do
        {
          return;
          HtcFM.this.cmdtype = "scan";
          HtcFM.this.apibindService();
        }
        while (!HtcFM.this.apiisFMReady());
        HtcFM.this.apituneon(1);
      }
    }).start();
  }

  public void setLiveAudioQualityCallback(boolean paramBoolean, int paramInt)
  {
  }

  public int setSpeakerOn(boolean paramBoolean)
  {
    apisetSpeakerOut();
    return 0;
  }

  public void setVolume(int paramInt)
    throws Exception
  {
  }

  public int tune(int paramInt)
  {
    this.freqQ = paramInt;
    new Thread(new Runnable()
    {
      public void run()
      {
        HtcFM.this.apiclearAllPresets(HtcFM.this.mContext);
        if (HtcFM.this.apiisFMPlaying())
        {
          HtcFM.this.apitune(HtcFM.this.freqQ);
          return;
        }
        HtcFM.this.apibindService();
        HtcFM.this.apituneon(HtcFM.this.freqQ);
        HtcFM.this.apitune(HtcFM.this.freqQ);
      }
    }).start();
    return 0;
  }

  public void turnOff()
  {
    apiturnOff();
  }

  public int turnOn()
  {
    apibindService();
    return 0;
  }

  public void unregisterFMEventListener()
  {
    if (this.mReceiver != null)
      this.mContext.unregisterReceiver(this.mReceiver);
    apiturnOff();
    apiunbindService();
  }

  public class Preset
  {
    int freq = 0;
    int id;
    int rawRssi = 0;
    String rds = "";
    int rds_PTY = 0;
    int rowId = -1;
    int rssi = 0;
    String title = "";

    public Preset()
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.htc.HtcFM
 * JD-Core Version:    0.6.2
 */