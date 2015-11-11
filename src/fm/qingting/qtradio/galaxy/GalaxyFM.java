package fm.qingting.qtradio.galaxy;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import fm.qingting.qtradio.fmdriver.FMDriver;
import fm.qingting.qtradio.fmdriver.IFMEventListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class GalaxyFM extends FMDriver
{
  private static final int AIRPLANE_MODE = 5;
  public static final int BAND_76000_108000_kHz = 2;
  public static final int BAND_76000_90000_kHz = 3;
  public static final int BAND_87500_108000_kHz = 1;
  private static final int BATTERY_LOW = 6;
  public static final int CHAN_SPACING_100_kHz = 10;
  public static final int CHAN_SPACING_200_kHz = 20;
  public static final int CHAN_SPACING_50_kHz = 5;
  public static final String CMDAPPWIDGETUPDATE = "appwidgetupdate";
  public static final String CMDNAME = "command";
  public static final int DE_TIME_CONSTANT_50 = 1;
  public static final int DE_TIME_CONSTANT_75 = 0;
  private static final String FM_RADIO_SERVICE_NAME = "FMPlayer";
  private static final int HEAD_SET_IS_NOT_PLUGGED = 4;
  private static final int PLAYER_IS_NOT_ON = 1;
  private static final int PLAYER_SCANNING = 3;
  private static final int RADIO_AUDIO_STREAM = 10;
  private static final int RADIO_SERVICE_DOWN = 2;
  public static final String SAMSUNG_PACKAGE_NAME = "com.sec.android.app.fm";
  private static final String TAG = "GalaxyFM";
  private static final int TV_OUT_PLUGGED = 7;
  private boolean available = false;
  private Method cancelScanMethod;
  private ArrayList<Integer> channels = new ArrayList();
  private Method disableAFMethod;
  private Method disableRDSMethod;
  private Method enableAFMethod;
  private Method enableRDSMethod;
  private Object fmListener;
  private Method getCurrentChannelMethod;
  private Method getCurrentRSSIMethod;
  private Method getLastScanResultMethod;
  private Method getMaxVolumeMethod;
  private Method getVolumeMethod;
  private Class internalServiceClass;
  private Object internalServiceObject;
  private Method isHeadsetPluggedMethod;
  private Method isOnMethod;
  private Method isScanningMethod;
  private boolean isSpeakout = false;
  private IFMEventListener listener;
  private Class listenerClass;
  private Method offMethod;
  private Method onMethod;
  private Method removeListenerMethod;
  private Handler scanHandler = new Handler()
  {
    private static final int EVENT_AF_RECEIVED = 14;
    private static final int EVENT_AF_STARTED = 13;
    private static final int EVENT_CHANNEL_FOUND = 1;
    private static final int EVENT_EAR_PHONE_CONNECT = 8;
    private static final int EVENT_EAR_PHONE_DISCONNECT = 9;
    private static final int EVENT_OFF = 6;
    private static final int EVENT_ON = 5;
    private static final int EVENT_RDS_DISABLED = 12;
    private static final int EVENT_RDS_ENABLED = 11;
    private static final int EVENT_RDS_EVENT = 10;
    private static final int EVENT_SCAN_FINISHED = 3;
    private static final int EVENT_SCAN_STARTED = 2;
    private static final int EVENT_SCAN_STOPPED = 4;
    private static final int EVENT_TUNE = 7;

    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      case 11:
      case 12:
      case 13:
      default:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
        label151: 
        do
        {
          do
          {
            long l1;
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                      return;
                    while (GalaxyFM.this.listener == null);
                    long l2 = ((Long)paramAnonymousMessage.obj).longValue();
                    GalaxyFM.this.listener.onChannelFound((int)l2);
                    return;
                  }
                  while (GalaxyFM.this.listener == null);
                  GalaxyFM.this.listener.onScanStarted();
                  return;
                  long[] arrayOfLong2 = (long[])paramAnonymousMessage.obj;
                  int j = arrayOfLong2.length;
                  int k = 0;
                  if (k >= j)
                    GalaxyFM.this.channels.clear();
                  for (int m = 0; ; m++)
                  {
                    if (m >= arrayOfLong2.length)
                    {
                      if (GalaxyFM.this.listener == null)
                        break;
                      GalaxyFM.this.listener.onScanComplete(true);
                      return;
                      Log.d("GalaxyFMs", String.valueOf(arrayOfLong2[k]));
                      k++;
                      break label151;
                    }
                    GalaxyFM.this.channels.add(Integer.valueOf((int)arrayOfLong2[m]));
                  }
                  long[] arrayOfLong1 = (long[])paramAnonymousMessage.obj;
                  GalaxyFM.this.channels.clear();
                  for (int i = 0; ; i++)
                  {
                    if (i >= arrayOfLong1.length)
                    {
                      if (GalaxyFM.this.listener == null)
                        break;
                      GalaxyFM.this.listener.onScanComplete(true);
                      return;
                    }
                    GalaxyFM.this.channels.add(Integer.valueOf((int)arrayOfLong1[i]));
                  }
                }
                while (GalaxyFM.this.listener == null);
                GalaxyFM.this.listener.onFMOn();
                return;
              }
              while (GalaxyFM.this.listener == null);
              GalaxyFM.this.listener.onFMOff();
              return;
              l1 = ((Long)paramAnonymousMessage.obj).longValue();
            }
            while (GalaxyFM.this.listener == null);
            GalaxyFM.this.listener.onTune((int)l1);
            return;
          }
          while (GalaxyFM.this.listener != null);
          return;
        }
        while (GalaxyFM.this.listener != null);
        return;
      case 10:
      }
      Object[] arrayOfObject = (Object[])paramAnonymousMessage.obj;
      ((Long)arrayOfObject[0]).longValue();
      ((String)arrayOfObject[1]);
      ((String)arrayOfObject[2]);
    }
  };
  private Method scanMethod;
  private Method seekDownMethod;
  private Method seekUpMethod;
  private Method setBandMethod;
  private Method setChannelSpacingMethod;
  private Method setDEConstantMethod;
  private Method setListenerMethod;
  private Method setSpeakerOnMethod;
  private Method setStereoMethod;
  private Method setVolumeMethod;
  private Method tuneMethod;

  public GalaxyFM(Context paramContext)
  {
    super(paramContext);
    this.internalServiceObject = paramContext.getSystemService("FMPlayer");
    if (this.internalServiceObject != null)
      setUpInnerMethods();
  }

  private void apiDisableAF()
  {
    try
    {
      this.disableAFMethod.invoke(this.internalServiceObject, new Object[0]);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private void apiDisableRDS()
  {
    try
    {
      this.disableRDSMethod.invoke(this.internalServiceObject, new Object[0]);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private void apiEnableAF()
  {
    try
    {
      this.enableAFMethod.invoke(this.internalServiceObject, new Object[0]);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private void apiEnableRDS()
  {
    try
    {
      this.enableRDSMethod.invoke(this.internalServiceObject, new Object[0]);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private long apiGetCurrentChannel()
  {
    try
    {
      long l = ((Long)this.getCurrentChannelMethod.invoke(this.internalServiceObject, new Object[0])).longValue();
      return l;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      return -9223372036854775808L;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      break label27;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      label27: break label27;
    }
  }

  private long apiGetCurrentRSSI()
  {
    try
    {
      long l = ((Long)this.getCurrentRSSIMethod.invoke(this.internalServiceObject, new Object[0])).longValue();
      return l;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      return -2147483648L;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      break label27;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      label27: break label27;
    }
  }

  private long[] apiGetLastScanResult()
  {
    try
    {
      long[] arrayOfLong = (long[])this.getLastScanResultMethod.invoke(this.internalServiceObject, new Object[0]);
      return arrayOfLong;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      return new long[0];
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      break label24;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      label24: break label24;
    }
  }

  private boolean apiIsHeadsetPlugged()
  {
    try
    {
      boolean bool = ((Boolean)this.isHeadsetPluggedMethod.invoke(this.internalServiceObject, new Object[0])).booleanValue();
      return bool;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      return false;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      break label27;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      label27: break label27;
    }
  }

  private boolean apiIsOn()
  {
    try
    {
      boolean bool = ((Boolean)this.isOnMethod.invoke(this.internalServiceObject, new Object[0])).booleanValue();
      return bool;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      return false;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      break label27;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      label27: break label27;
    }
  }

  private boolean apiIsScanning()
  {
    try
    {
      boolean bool = ((Boolean)this.isScanningMethod.invoke(this.internalServiceObject, new Object[0])).booleanValue();
      return bool;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      return false;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      break label27;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      label27: break label27;
    }
  }

  private void apiOff()
  {
    try
    {
      this.offMethod.invoke(this.internalServiceObject, new Object[0]);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private int apiOn()
  {
    try
    {
      this.onMethod.invoke(this.internalServiceObject, new Object[0]);
      return 0;
    }
    catch (InvocationTargetException localInvocationTargetException1)
    {
      try
      {
        Class localClass = localInvocationTargetException1.getTargetException().getClass();
        if (!localClass.isAssignableFrom(Class.forName("com.samsung.media.fmradio.FMPlayerException")))
          return -2147483648;
        int i = getFMError(((Integer)localClass.getMethod("getCode", new Class[0]).invoke(localInvocationTargetException1.getTargetException(), new Object[0])).intValue());
        return i;
      }
      catch (InvocationTargetException localInvocationTargetException2)
      {
        return -2147483648;
      }
      catch (IllegalAccessException localIllegalAccessException2)
      {
        break label83;
      }
      catch (IllegalArgumentException localIllegalArgumentException2)
      {
        break label83;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        break label83;
      }
      catch (SecurityException localSecurityException)
      {
        break label83;
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        break label83;
      }
    }
    catch (IllegalAccessException localIllegalAccessException1)
    {
      break label83;
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      label83: break label83;
    }
  }

  private void apiRemoveListener(Object paramObject)
  {
    try
    {
      this.removeListenerMethod.invoke(this.internalServiceObject, new Object[] { paramObject });
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private void apiScan()
  {
    try
    {
      this.scanMethod.invoke(this.internalServiceObject, new Object[0]);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private long apiSeekDown()
  {
    try
    {
      long l = ((Long)this.seekDownMethod.invoke(this.internalServiceObject, new Object[0])).longValue();
      return l;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      return -9223372036854775808L;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      break label27;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      label27: break label27;
    }
  }

  private long apiSeekUp()
  {
    try
    {
      long l = ((Long)this.seekUpMethod.invoke(this.internalServiceObject, new Object[0])).longValue();
      return l;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      return -9223372036854775808L;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      break label27;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      label27: break label27;
    }
  }

  private void apiSetBand(int paramInt)
  {
    try
    {
      Method localMethod = this.setBandMethod;
      Object localObject = this.internalServiceObject;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      localMethod.invoke(localObject, arrayOfObject);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private void apiSetChannelSpacing(int paramInt)
  {
    try
    {
      Method localMethod = this.setChannelSpacingMethod;
      Object localObject = this.internalServiceObject;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      localMethod.invoke(localObject, arrayOfObject);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private void apiSetDEConstant(long paramLong)
  {
    try
    {
      Method localMethod = this.setDEConstantMethod;
      Object localObject = this.internalServiceObject;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Long.valueOf(paramLong);
      localMethod.invoke(localObject, arrayOfObject);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private void apiSetListener(Object paramObject)
  {
    try
    {
      this.setListenerMethod.invoke(this.internalServiceObject, new Object[] { paramObject });
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
    catch (SecurityException localSecurityException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
  }

  private void apiSetSpeakerOn(boolean paramBoolean)
  {
    try
    {
      Method localMethod = this.setSpeakerOnMethod;
      Object localObject = this.internalServiceObject;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(paramBoolean);
      localMethod.invoke(localObject, arrayOfObject);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private void apiStopScan()
  {
    try
    {
      this.cancelScanMethod.invoke(this.internalServiceObject, new Object[0]);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private void apiTune(long paramLong)
  {
    try
    {
      Method localMethod = this.tuneMethod;
      Object localObject = this.internalServiceObject;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Long.valueOf(paramLong);
      localMethod.invoke(localObject, arrayOfObject);
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
  }

  private void apisetVolume(long paramLong)
  {
    try
    {
      Method localMethod = this.setVolumeMethod;
      Object localObject = this.internalServiceObject;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Long.valueOf(paramLong);
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

  private int getFMError(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return -2147483648;
    case 1:
      return -2;
    case 2:
      return 3;
    case 3:
      return 4;
    case 4:
      return 5;
    case 5:
      return 6;
    case 6:
      return 7;
    case 7:
    }
    return 8;
  }

  private void setUpInnerMethods()
  {
    this.internalServiceClass = this.internalServiceObject.getClass();
    try
    {
      this.listenerClass = Class.forName("com.samsung.media.fmradio.FMEventListener");
      this.onMethod = this.internalServiceClass.getMethod("on", new Class[0]);
      this.offMethod = this.internalServiceClass.getMethod("off", new Class[0]);
      this.isOnMethod = this.internalServiceClass.getMethod("isOn", new Class[0]);
      this.isScanningMethod = this.internalServiceClass.getMethod("isScanning", new Class[0]);
      Class localClass1 = this.internalServiceClass;
      Class[] arrayOfClass1 = new Class[1];
      arrayOfClass1[0] = Integer.TYPE;
      this.setBandMethod = localClass1.getMethod("setBand", arrayOfClass1);
      Class localClass2 = this.internalServiceClass;
      Class[] arrayOfClass2 = new Class[1];
      arrayOfClass2[0] = Integer.TYPE;
      this.setChannelSpacingMethod = localClass2.getMethod("setChannelSpacing", arrayOfClass2);
      Class localClass3 = this.internalServiceClass;
      Class[] arrayOfClass3 = new Class[1];
      arrayOfClass3[0] = Long.TYPE;
      this.setDEConstantMethod = localClass3.getMethod("setDEConstant", arrayOfClass3);
      Class localClass4 = this.internalServiceClass;
      Class[] arrayOfClass4 = new Class[1];
      arrayOfClass4[0] = Boolean.TYPE;
      this.setSpeakerOnMethod = localClass4.getMethod("setSpeakerOn", arrayOfClass4);
      this.seekUpMethod = this.internalServiceClass.getMethod("seekUp", new Class[0]);
      this.seekDownMethod = this.internalServiceClass.getMethod("seekDown", new Class[0]);
      this.scanMethod = this.internalServiceClass.getMethod("scan", new Class[0]);
      this.cancelScanMethod = this.internalServiceClass.getMethod("cancelScan", new Class[0]);
      Class localClass5 = this.internalServiceClass;
      Class[] arrayOfClass5 = new Class[1];
      arrayOfClass5[0] = Long.TYPE;
      this.tuneMethod = localClass5.getMethod("tune", arrayOfClass5);
      this.getCurrentChannelMethod = this.internalServiceClass.getMethod("getCurrentChannel", new Class[0]);
      this.disableRDSMethod = this.internalServiceClass.getMethod("disableRDS", new Class[0]);
      this.enableRDSMethod = this.internalServiceClass.getMethod("enableRDS", new Class[0]);
      this.disableAFMethod = this.internalServiceClass.getMethod("disableAF", new Class[0]);
      this.enableAFMethod = this.internalServiceClass.getMethod("enableAF", new Class[0]);
      Class localClass6 = this.internalServiceClass;
      Class[] arrayOfClass6 = new Class[1];
      arrayOfClass6[0] = Long.TYPE;
      this.setVolumeMethod = localClass6.getMethod("setVolume", arrayOfClass6);
      this.getLastScanResultMethod = this.internalServiceClass.getMethod("getLastScanResult", new Class[0]);
      this.isHeadsetPluggedMethod = this.internalServiceClass.getMethod("isHeadsetPlugged", new Class[0]);
      Class localClass7 = this.internalServiceClass;
      Class[] arrayOfClass7 = new Class[1];
      arrayOfClass7[0] = this.listenerClass;
      this.setListenerMethod = localClass7.getMethod("setListener", arrayOfClass7);
      Class localClass8 = this.internalServiceClass;
      Class[] arrayOfClass8 = new Class[1];
      arrayOfClass8[0] = this.listenerClass;
      this.removeListenerMethod = localClass8.getMethod("removeListener", arrayOfClass8);
      this.getCurrentRSSIMethod = this.internalServiceClass.getMethod("getCurrentRSSI", new Class[0]);
      this.fmListener = this.listenerClass.newInstance();
      Field localField = this.listenerClass.getDeclaredField("mHandler");
      localField.setAccessible(true);
      localField.set(this.fmListener, this.scanHandler);
      apiSetListener(this.fmListener);
      this.available = true;
      return;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
    }
    catch (InstantiationException localInstantiationException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
    }
    catch (SecurityException localSecurityException)
    {
    }
  }

  public void cancelScanning()
  {
    apiStopScan();
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
    return "GalaxyFM";
  }

  public int getVolume()
    throws Exception
  {
    return 0;
  }

  public boolean isAvailable()
  {
    return this.available;
  }

  public boolean isHeadsetPlugged()
  {
    return apiIsHeadsetPlugged();
  }

  public boolean isMute()
    throws Exception
  {
    return !isOn();
  }

  public boolean isOn()
  {
    return apiIsOn();
  }

  public boolean isPaused()
    throws Exception
  {
    return !isOn();
  }

  public boolean isScanning()
  {
    return apiIsScanning();
  }

  public boolean isSpeakerOn()
    throws Exception
  {
    return this.isSpeakout;
  }

  public void mute(boolean paramBoolean)
    throws Exception
  {
  }

  public void pause()
    throws Exception
  {
    turnOff();
  }

  public void registerFMEventListener(IFMEventListener paramIFMEventListener)
  {
    this.listener = paramIFMEventListener;
  }

  public void scan()
  {
    apiOn();
    apiSetBand(1);
    apiSetChannelSpacing(10);
    apiSetDEConstant(0L);
    apiScan();
  }

  public void setLiveAudioQualityCallback(boolean paramBoolean, int paramInt)
  {
  }

  public int setSpeakerOn(boolean paramBoolean)
  {
    this.isSpeakout = paramBoolean;
    apiSetSpeakerOn(paramBoolean);
    return 0;
  }

  public void setVolume(int paramInt)
    throws Exception
  {
  }

  public int tune(int paramInt)
  {
    int i;
    if (!this.available)
      i = -1;
    do
    {
      return i;
      if (isOn())
        break;
      i = turnOn();
    }
    while (i != 0);
    apiTune(paramInt);
    apisetVolume(8L);
    return 0;
  }

  public void turnOff()
  {
    apiOff();
  }

  public int turnOn()
  {
    return apiOn();
  }

  public void unregisterFMEventListener()
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.galaxy.GalaxyFM
 * JD-Core Version:    0.6.2
 */