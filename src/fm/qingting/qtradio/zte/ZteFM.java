package fm.qingting.qtradio.zte;

import android.content.Context;
import android.media.IFMSystemService;
import android.media.IFMSystemService.Stub;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import fm.qingting.qtradio.fmdriver.FMDriver;
import fm.qingting.qtradio.fmdriver.IFMEventListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ZteFM extends FMDriver
{
  private static final double FREQUENCY_BAND_MAX = 108001.0D;
  private static final String TAG = "ZteFM";
  public static final String ZTE_PACKAGE_NAME = "zte.com.cn.fmradio";
  private Method AcquireMethod;
  private Method SearchNextStationMethod;
  private Method SearchPrevStationMethod;
  private Class ServiceManagerClass;
  private Method SetMuteModeMethod;
  private Class amClass;
  private Object amObj;
  private Method asSetParameterMethod;
  private boolean available = false;
  private ArrayList<Integer> channels = new ArrayList();
  private int currentFreq = -1;
  private Method endSleepMethod;
  private Class fMControllerClass;
  IFMSystemService fmService;
  private Object fmServiceObject;
  private Method getFrequencyMethod;
  private Method getService;
  private Method getVolumMethod;
  private Object internalFMServiceObject;
  private Method isSleepActiveMethod;
  private Method isSleepTimerActiveMethod;
  private Context mContext;
  private int mDefaultVolum = 9;
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (ZteFM.this.onFMEventListener == null)
        return;
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
        ZteFM.this.onFMEventListener.onScanComplete(true);
        return;
      case 2:
        ZteFM.this.onFMEventListener.onChannelFound(paramAnonymousMessage.arg1);
        return;
      case 3:
        ZteFM.this.onFMEventListener.onFMOff();
        return;
      case 4:
        ZteFM.this.onFMEventListener.onFMOn();
        return;
      case 5:
        ZteFM.this.onFMEventListener.onScanStarted();
        return;
      case 6:
      }
      ZteFM.this.onFMEventListener.onTune(paramAnonymousMessage.arg1);
    }
  };
  private boolean mIsFMEnable = false;
  private boolean mIsPlay = false;
  private boolean mIsScan = false;
  private IFMEventListener onFMEventListener;
  private Method radioDisableMethod;
  private Method radioEnableMethod;
  private Method releaseMethod;
  private Method setStationMethod;
  private Method setVolumMethod;
  private Method showFmNotificationMethod;
  private Method sleepMethod;

  public ZteFM(Context paramContext)
  {
    super(paramContext);
    getServiceManager();
    CheckFMRadioService();
    this.mContext = paramContext;
    initClass();
  }

  private void CheckFMRadioService()
  {
    try
    {
      Method localMethod = this.getService;
      Class localClass = this.ServiceManagerClass;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = String.valueOf("fmradio");
      this.internalFMServiceObject = localMethod.invoke(localClass, arrayOfObject);
      if (this.internalFMServiceObject == null)
        return;
      this.fmService = IFMSystemService.Stub.asInterface((IBinder)this.internalFMServiceObject);
      if (this.fmService != null)
      {
        this.available = true;
        return;
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
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
    double d1 = 0.0D;
    try
    {
      double d2 = this.fmService.getFrequency();
      d1 = d2;
      return 1000L * ()d1;
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        localRemoteException.printStackTrace();
    }
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
    return true;
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
    Log.e("ZteFM", "in apiOff");
    try
    {
      this.fmService.radioDisable();
      apiAMsetParameter("FM", "off");
      this.mIsPlay = false;
      this.mIsFMEnable = false;
      Message localMessage = Message.obtain(this.mHandler, 3, null);
      this.mHandler.sendMessage(localMessage);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        localRemoteException.printStackTrace();
    }
  }

  private boolean apiOn()
  {
    boolean bool = false;
    if (!this.mIsFMEnable);
    try
    {
      bool = this.fmService.Acquire("/dev/radio0");
      this.fmService.radioEnable();
      if (bool)
      {
        this.mIsFMEnable = true;
        Message localMessage = Message.obtain(this.mHandler, 4, null);
        this.mHandler.sendMessage(localMessage);
      }
      return this.mIsFMEnable;
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        localRemoteException.printStackTrace();
    }
  }

  private void apiRemoveListener(Object paramObject)
  {
  }

  private double apiScanNext()
  {
    try
    {
      this.fmService.SearchNextStation();
      double d = this.fmService.getFrequency();
      return d;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -1.0D;
  }

  private double apiScanPrev()
  {
    try
    {
      this.fmService.SearchPrevStation();
      double d = this.fmService.getFrequency();
      return d;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -1.0D;
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
  }

  private void apiStopScan()
  {
  }

  private void apiTune(long paramLong)
  {
  }

  private void apisetVolume(int paramInt)
  {
    try
    {
      this.fmService.setVolum(paramInt);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  // ERROR //
  private void getServiceManager()
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc_w 286
    //   4: invokestatic 292	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   7: putfield 112	fm/qingting/qtradio/zte/ZteFM:ServiceManagerClass	Ljava/lang/Class;
    //   10: aload_0
    //   11: aload_0
    //   12: getfield 112	fm/qingting/qtradio/zte/ZteFM:ServiceManagerClass	Ljava/lang/Class;
    //   15: ldc_w 293
    //   18: iconst_1
    //   19: anewarray 288	java/lang/Class
    //   22: dup
    //   23: iconst_0
    //   24: ldc 118
    //   26: aastore
    //   27: invokevirtual 297	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   30: putfield 110	fm/qingting/qtradio/zte/ZteFM:getService	Ljava/lang/reflect/Method;
    //   33: return
    //   34: astore_2
    //   35: return
    //   36: astore_1
    //   37: return
    //   38: astore_3
    //   39: aload_3
    //   40: invokevirtual 298	java/lang/NoSuchMethodException:printStackTrace	()V
    //   43: return
    //
    // Exception table:
    //   from	to	target	type
    //   0	10	34	java/lang/SecurityException
    //   0	10	36	java/lang/ClassNotFoundException
    //   10	33	38	java/lang/NoSuchMethodException
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
      this.fMControllerClass = Class.forName("android.media.IFMSystemService", true, this.mContext.getClassLoader());
      this.amObj = this.mContext.getSystemService("audio");
      this.amClass = this.amObj.getClass();
      setUpInnerMethods();
      return;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
  }

  private void setUpInnerMethods()
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

  private void showChannels()
  {
    for (int i = 0; ; i++)
    {
      if (i >= this.channels.size())
        return;
      Log.e("ZteFM", "channels: " + this.channels.get(i));
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
    return "ZteFM";
  }

  public int getVolume()
    throws Exception
  {
    return this.mDefaultVolum;
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
    return false;
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
        if (!ZteFM.this.mIsFMEnable);
        label332: 
        while (true)
        {
          int i;
          try
          {
            boolean bool2 = ZteFM.this.fmService.Acquire("/dev/radio0");
            bool1 = bool2;
            if (bool1)
            {
              ZteFM.this.mIsFMEnable = true;
              Message localMessage4 = Message.obtain(ZteFM.this.mHandler, 4, null);
              ZteFM.this.mHandler.sendMessage(localMessage4);
              ZteFM.this.mIsPlay = false;
              ZteFM.this.apiAMsetParameter("FM", "off");
              ZteFM.this.mIsScan = true;
              d = 0.0D;
              i = 1;
              ZteFM.this.channels.clear();
              switch (i)
              {
              default:
                if ((-1.0D != d) && (ZteFM.this.mIsScan))
                  break label245;
                Message localMessage1 = Message.obtain(ZteFM.this.mHandler, 1, null);
                ZteFM.this.mHandler.sendMessage(localMessage1);
                return;
              case 1:
              case 2:
              }
            }
          }
          catch (RemoteException localRemoteException)
          {
            localRemoteException.printStackTrace();
            boolean bool1 = false;
            continue;
            Message localMessage3 = Message.obtain(ZteFM.this.mHandler, 1, null);
            ZteFM.this.mHandler.sendMessage(localMessage3);
            return;
          }
          double d = ZteFM.this.apiScanNext();
          continue;
          d = ZteFM.this.apiScanPrev();
          continue;
          label245: d *= 1000.0D;
          if (ZteFM.this.inChannelList((int)d))
          {
            if (1 == i)
              i = 2;
          }
          else
            while (true)
            {
              if (d < 108001.0D)
                break label332;
              break;
              Message localMessage2 = Message.obtain(ZteFM.this.mHandler, 2, null);
              localMessage2.arg1 = ((int)d);
              ZteFM.this.mHandler.sendMessage(localMessage2);
              ZteFM.this.channels.add(Integer.valueOf((int)d));
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
    apiSetSpeakerOn(paramBoolean);
    return 0;
  }

  public void setVolum(int paramInt)
  {
    try
    {
      this.fmService.setVolum(paramInt);
      this.mDefaultVolum = paramInt;
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  public void setVolume(int paramInt)
    throws Exception
  {
    setVolum(paramInt);
  }

  // ERROR //
  public int tune(int paramInt)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: aload_0
    //   3: getfield 71	fm/qingting/qtradio/zte/ZteFM:mIsFMEnable	Z
    //   6: ifne +142 -> 148
    //   9: aload_0
    //   10: getfield 140	fm/qingting/qtradio/zte/ZteFM:fmService	Landroid/media/IFMSystemService;
    //   13: ldc 242
    //   15: invokeinterface 246 2 0
    //   20: istore_2
    //   21: aload_0
    //   22: getfield 140	fm/qingting/qtradio/zte/ZteFM:fmService	Landroid/media/IFMSystemService;
    //   25: invokeinterface 249 1 0
    //   30: iload_2
    //   31: ifeq +29 -> 60
    //   34: aload_0
    //   35: iconst_1
    //   36: putfield 71	fm/qingting/qtradio/zte/ZteFM:mIsFMEnable	Z
    //   39: aload_0
    //   40: getfield 91	fm/qingting/qtradio/zte/ZteFM:mHandler	Landroid/os/Handler;
    //   43: iconst_4
    //   44: aconst_null
    //   45: invokestatic 233	android/os/Message:obtain	(Landroid/os/Handler;ILjava/lang/Object;)Landroid/os/Message;
    //   48: astore 9
    //   50: aload_0
    //   51: getfield 91	fm/qingting/qtradio/zte/ZteFM:mHandler	Landroid/os/Handler;
    //   54: aload 9
    //   56: invokevirtual 239	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   59: pop
    //   60: iload_2
    //   61: ifeq +75 -> 136
    //   64: iload_1
    //   65: i2d
    //   66: ldc2_w 429
    //   69: ddiv
    //   70: dstore_3
    //   71: aload_0
    //   72: getfield 140	fm/qingting/qtradio/zte/ZteFM:fmService	Landroid/media/IFMSystemService;
    //   75: dload_3
    //   76: invokeinterface 434 3 0
    //   81: aload_0
    //   82: getfield 140	fm/qingting/qtradio/zte/ZteFM:fmService	Landroid/media/IFMSystemService;
    //   85: aload_0
    //   86: getfield 73	fm/qingting/qtradio/zte/ZteFM:mDefaultVolum	I
    //   89: invokeinterface 278 2 0
    //   94: aload_0
    //   95: ldc 225
    //   97: ldc_w 435
    //   100: invokespecial 164	fm/qingting/qtradio/zte/ZteFM:apiAMsetParameter	(Ljava/lang/String;Ljava/lang/String;)V
    //   103: aload_0
    //   104: iconst_1
    //   105: putfield 67	fm/qingting/qtradio/zte/ZteFM:mIsPlay	Z
    //   108: aload_0
    //   109: getfield 91	fm/qingting/qtradio/zte/ZteFM:mHandler	Landroid/os/Handler;
    //   112: bipush 6
    //   114: aconst_null
    //   115: invokestatic 233	android/os/Message:obtain	(Landroid/os/Handler;ILjava/lang/Object;)Landroid/os/Message;
    //   118: astore 6
    //   120: aload 6
    //   122: iload_1
    //   123: putfield 438	android/os/Message:arg1	I
    //   126: aload_0
    //   127: getfield 91	fm/qingting/qtradio/zte/ZteFM:mHandler	Landroid/os/Handler;
    //   130: aload 6
    //   132: invokevirtual 239	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   135: pop
    //   136: iconst_0
    //   137: ireturn
    //   138: astore 8
    //   140: aload 8
    //   142: invokevirtual 202	android/os/RemoteException:printStackTrace	()V
    //   145: goto -115 -> 30
    //   148: iconst_1
    //   149: istore_2
    //   150: goto -90 -> 60
    //   153: astore 5
    //   155: aload 5
    //   157: invokevirtual 202	android/os/RemoteException:printStackTrace	()V
    //   160: goto -24 -> 136
    //
    // Exception table:
    //   from	to	target	type
    //   9	30	138	android/os/RemoteException
    //   71	136	153	android/os/RemoteException
  }

  public void turnOff()
    throws Exception
  {
    off();
  }

  public int turnOn()
    throws Exception
  {
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
 * Qualified Name:     fm.qingting.qtradio.zte.ZteFM
 * JD-Core Version:    0.6.2
 */