package fm.qingting.qtradio.miui;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.util.Log;
import fm.qingting.qtradio.fmdriver.FMDriver;
import fm.qingting.qtradio.fmdriver.IFMEventListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class MiuiFM extends FMDriver
{
  private static final Boolean FM_AUDIO_ENABLED = Boolean.valueOf(true);
  private static final String FM_CMD_AUDIO_MODE_GET = "getAudioMode";
  private static final String FM_CMD_AUDIO_MODE_SET = "setAudioMode";
  private static final String FM_CMD_AUDIO_TARGET_DISABLE = "disableAudioTarget";
  private static final String FM_CMD_AUDIO_TARGET_ENABLE = "enableAudioTarget";
  private static final String FM_CMD_BAND_GET = "getBand";
  private static final String FM_CMD_BAND_SET = "setBand";
  private static final String FM_CMD_CLOSE = "close";
  private static final String FM_CMD_COMPLETE_LISTENER_SET = "setOnCommandCompleteListener";
  private static final String FM_CMD_CURRFREQ = "currentFreq";
  private static final String FM_CMD_EMPHASIS_GET = "setEmphasisFilter";
  private static final String FM_CMD_EMPHASIS_SET = "getEmphasisFilter";
  private static final String FM_CMD_ERROR_LISTENER_SET = "setOnErrorListener";
  private static final String FM_CMD_FINALIZE = "finalizeSelf";
  private static final String FM_CMD_MUTE_GET = "isMute";
  private static final String FM_CMD_MUTE_SET = "setMute";
  private static final String FM_CMD_OPEN = "open";
  private static final String FM_CMD_POWEROFF = "powerOffDevice";
  private static final String FM_CMD_POWERON = "powerOnDevice";
  private static final String FM_CMD_SEEK = "seek";
  private static final String FM_CMD_SEEK_STOP = "stopSeek";
  private static final String FM_CMD_TUNE = "tune";
  private static final String FM_CMD_VOLUME_GET = "getVolume";
  private static final String FM_CMD_VOLUME_SET = "setVolume";
  private static final int FM_FREQ_END = 108000;
  private static final int FM_FREQ_START = 87500;
  private static final int FM_MSG_CLOSE = 1;
  private static final int FM_MSG_CURRENT_FREQ = 5;
  private static final int FM_MSG_DISABLE_AUDIO_TARGET = 17;
  private static final int FM_MSG_ENABLE_AUDIO_TARGET = 16;
  private static final int FM_MSG_GET_AUDIO_MODE = 7;
  private static final int FM_MSG_GET_BAND = 2;
  private static final int FM_MSG_GET_DEEMPHASIS_FILTER = 13;
  private static final int FM_MSG_GET_VOLUME = 11;
  private static final int FM_MSG_IS_MUTE = 15;
  private static final int FM_MSG_NONE = -1;
  private static final int FM_MSG_OPEN = 0;
  private static final int FM_MSG_POWEROFF = 19;
  private static final int FM_MSG_POWERON = 18;
  private static final int FM_MSG_SEEK = 8;
  private static final int FM_MSG_SET_AUDIO_MODE = 6;
  private static final int FM_MSG_SET_BAND = 3;
  private static final int FM_MSG_SET_DEEMPHASIS_FILTER = 12;
  private static final int FM_MSG_SET_MUTE = 14;
  private static final int FM_MSG_SET_VOLUME = 10;
  private static final int FM_MSG_STOP_SEEK = 9;
  private static final int FM_MSG_TUNE = 4;
  private static final String TAG = "MiuiFM";
  private InvocationHandler commandCompleteInvocationHandler = new InvocationHandler()
  {
    public Object invoke(Object paramAnonymousObject, Method paramAnonymousMethod, Object[] paramAnonymousArrayOfObject)
      throws Throwable
    {
      if (paramAnonymousArrayOfObject == null);
      while (true)
      {
        return null;
        try
        {
          int i = ((Integer)paramAnonymousArrayOfObject[0]).intValue();
          int j = ((Integer)paramAnonymousArrayOfObject[2]).intValue();
          switch (i)
          {
          case 18:
            MiuiFM.this.open();
            return null;
          case 19:
            MiuiFM.this.powerOffSuccess();
            return null;
          case 0:
            MiuiFM.this.openSuccess();
            return null;
          case 1:
            MiuiFM.this.powerOff();
            return null;
          case 4:
            if (MiuiFM.this.mListener != null)
            {
              MiuiFM.this.mListener.onTune(j);
              return null;
            }
            break;
          case 8:
            MiuiFM.this.seekSuccess(j);
            return null;
          case 9:
            MiuiFM.this.seekStopSuccess();
            return null;
          case 17:
            return null;
          default:
            return null;
          case -1:
            return null;
          case 5:
          case 14:
          case 15:
          case 2:
          case 7:
          case 13:
          case 11:
          case 3:
          case 6:
          case 12:
          case 10:
          case 16:
          }
        }
        catch (Exception localException)
        {
          return null;
        }
      }
      return null;
      return null;
      return null;
      return null;
      return null;
      return null;
      return null;
      return null;
      return null;
      return null;
      return null;
    }
  };
  private InvocationHandler errorInvocationHandler = new InvocationHandler()
  {
    public Object invoke(Object paramAnonymousObject, Method paramAnonymousMethod, Object[] paramAnonymousArrayOfObject)
      throws Throwable
    {
      return null;
    }
  };
  private AudioManager mAM;
  private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener()
  {
    public void onAudioFocusChange(int paramAnonymousInt)
    {
    }
  };
  private Method mAudioManageMethod;
  private Context mContext;
  private int mCurrentFreq = 0;
  private boolean mIsAvailable = false;
  private boolean mIsMute = true;
  private boolean mIsOpening = false;
  private boolean mIsPlaying = false;
  private boolean mIsReady = false;
  private boolean mIsScanning = false;
  private IFMEventListener mListener = null;
  private Object mPlayerObject = null;
  private Route mRoute = Route.HEADSET;
  private Object mServiceObject = null;
  private int mVolume = 7;

  public MiuiFM(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mAM = ((AudioManager)paramContext.getSystemService("audio"));
    init();
  }

  private void close()
  {
    this.mIsReady = false;
    this.mIsPlaying = false;
    this.mCurrentFreq = 0;
    this.mIsOpening = false;
    if (FM_AUDIO_ENABLED.booleanValue());
    for (boolean bool = false; ; bool = true)
    {
      switchAudioAndRoute(Boolean.valueOf(bool), Route.NONE);
      invoke("close", new Object[0]);
      return;
    }
  }

  private void init()
  {
    try
    {
      Context localContext = this.mContext.createPackageContext("com.miui.fmradio", 3);
      this.mPlayerObject = Class.forName("com.miui.fmradio.FMRadioPlayer", true, localContext.getClassLoader()).getConstructor(new Class[0]).newInstance(new Object[0]);
      if (this.mServiceObject == null)
        this.mServiceObject = Class.forName("com.miui.fmradio.FMRadioPlayerService", true, localContext.getClassLoader()).getConstructor(new Class[0]).newInstance(new Object[0]);
      Class localClass1 = Class.forName("com.miui.fmradio.FMRadioPlayer$OnCommandCompleteListener", true, localContext.getClassLoader());
      Object localObject1 = Proxy.newProxyInstance(localClass1.getClassLoader(), new Class[] { localClass1 }, this.commandCompleteInvocationHandler);
      Class localClass2 = Class.forName("com.miui.fmradio.FMRadioPlayer$OnErrorListener", true, localContext.getClassLoader());
      Object localObject2 = Proxy.newProxyInstance(localClass2.getClassLoader(), new Class[] { localClass2 }, this.errorInvocationHandler);
      this.mPlayerObject.getClass().getDeclaredMethod("setOnCommandCompleteListener", new Class[] { localClass1 }).invoke(this.mPlayerObject, new Object[] { localObject1 });
      this.mPlayerObject.getClass().getDeclaredMethod("setOnErrorListener", new Class[] { localClass2 }).invoke(this.mPlayerObject, new Object[] { localObject2 });
      Field localField1 = this.mServiceObject.getClass().getDeclaredField("mAudioManager");
      Field localField2 = this.mServiceObject.getClass().getDeclaredField("mAudioFocusListener");
      localField1.setAccessible(true);
      localField2.setAccessible(true);
      localField1.set(this.mServiceObject, this.mAM);
      localField2.set(this.mServiceObject, this.mAudioFocusListener);
      Class localClass3 = this.mServiceObject.getClass();
      Class[] arrayOfClass = new Class[2];
      arrayOfClass[0] = Boolean.TYPE;
      arrayOfClass[1] = Integer.TYPE;
      this.mAudioManageMethod = localClass3.getDeclaredMethod("turnOnOffFmAudio", arrayOfClass);
      this.mAudioManageMethod.setAccessible(true);
      this.mIsAvailable = true;
      return;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
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
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
      return;
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
      return;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      localNoSuchFieldException.printStackTrace();
    }
  }

  private void invoke(String paramString, Object[] paramArrayOfObject)
  {
    if (this.mPlayerObject == null)
      return;
    Class[] arrayOfClass = new Class[paramArrayOfObject.length];
    int i = 0;
    while (true)
    {
      if (i >= paramArrayOfObject.length);
      try
      {
        this.mPlayerObject.getClass().getDeclaredMethod(paramString, arrayOfClass).invoke(this.mPlayerObject, paramArrayOfObject);
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        localIllegalArgumentException.printStackTrace();
        return;
        if ((paramArrayOfObject[i] instanceof Integer))
          arrayOfClass[i] = Integer.TYPE;
        while (true)
        {
          i++;
          break;
          arrayOfClass[i] = paramArrayOfObject[i].getClass();
        }
      }
      catch (SecurityException localSecurityException)
      {
        localSecurityException.printStackTrace();
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
        return;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        localNoSuchMethodException.printStackTrace();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  private boolean isValidFreq(int paramInt)
  {
    return (paramInt >= 87500) && (paramInt <= 108000);
  }

  private void open()
  {
    if ((this.mIsReady) || (!this.mIsOpening))
      return;
    this.mIsOpening = true;
    invoke("open", new Object[0]);
  }

  private void openSuccess()
  {
    if (this.mIsReady)
      return;
    if (this.mListener != null)
      this.mListener.onFMOn();
    this.mIsOpening = false;
    this.mIsReady = true;
    if (isValidFreq(this.mCurrentFreq))
    {
      if (this.mCurrentFreq == 87500)
        try
        {
          scan();
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
      play();
      return;
    }
    boolean bool1 = FM_AUDIO_ENABLED.booleanValue();
    boolean bool2 = false;
    if (bool1);
    while (true)
    {
      switchAudioAndRoute(Boolean.valueOf(bool2), Route.NONE);
      return;
      bool2 = true;
    }
  }

  private int play()
  {
    if (!isValidFreq(this.mCurrentFreq))
      return 1;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(this.mCurrentFreq);
    invoke("tune", arrayOfObject);
    switchAudioAndRoute(FM_AUDIO_ENABLED, this.mRoute);
    this.mIsPlaying = true;
    return 0;
  }

  private void powerOff()
  {
    invoke("powerOffDevice", new Object[0]);
  }

  private void powerOffSuccess()
  {
    if (this.mListener != null)
      this.mListener.onFMOff();
    this.mIsReady = false;
    invoke("finalizeSelf", new Object[0]);
    this.mPlayerObject = null;
  }

  private void powerOn()
  {
    if ((this.mIsReady) || (this.mIsOpening))
      return;
    if (this.mPlayerObject == null)
      init();
    invoke("powerOnDevice", new Object[0]);
    this.mIsOpening = true;
  }

  private void seekStopSuccess()
  {
    this.mListener.onScanComplete(true);
  }

  private void seekSuccess(int paramInt)
  {
    if (paramInt == 108000)
    {
      this.mIsScanning = false;
      this.mListener.onScanComplete(true);
      return;
    }
    this.mListener.onChannelFound(paramInt);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    invoke("seek", arrayOfObject);
  }

  private void switchAudioAndRoute(Boolean paramBoolean, Route paramRoute)
  {
    try
    {
      if (paramBoolean.booleanValue())
      {
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = Integer.valueOf(1);
        invoke("setMute", arrayOfObject4);
        Object[] arrayOfObject5 = new Object[1];
        arrayOfObject5[0] = Integer.valueOf(this.mVolume);
        invoke("setVolume", arrayOfObject5);
        Object[] arrayOfObject6 = new Object[1];
        arrayOfObject6[0] = Integer.valueOf(0);
        invoke("setAudioMode", arrayOfObject6);
        Object[] arrayOfObject7 = new Object[1];
        arrayOfObject7[0] = Integer.valueOf(0);
        invoke("setBand", arrayOfObject7);
      }
      for (this.mIsMute = false; ; this.mIsMute = true)
      {
        Route localRoute1 = Route.HEADSET;
        i = 0;
        if (paramRoute != localRoute1)
          break;
        Method localMethod = this.mAudioManageMethod;
        Object localObject = this.mServiceObject;
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = Boolean.valueOf(paramBoolean.booleanValue());
        arrayOfObject3[1] = Integer.valueOf(i);
        localMethod.invoke(localObject, arrayOfObject3);
        return;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(0);
        invoke("setMute", arrayOfObject1);
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(0);
        invoke("setVolume", arrayOfObject2);
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
      {
        int i;
        localIllegalArgumentException.printStackTrace();
        return;
        Route localRoute2 = Route.SPEAKER;
        if (paramRoute == localRoute2)
          i = 1;
        else
          i = -1;
      }
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

  public void cancelScanning()
    throws Exception
  {
    this.mIsScanning = false;
    this.mListener.onScanComplete(true);
    invoke("stopSeek", new Object[0]);
  }

  public ArrayList<Integer> getAvailableChannels()
  {
    return null;
  }

  public int getCurrentChannel()
  {
    return this.mCurrentFreq;
  }

  public int getCurrentRSSI()
  {
    return 0;
  }

  public String getName()
  {
    return "MiuiFM";
  }

  public int getVolume()
    throws Exception
  {
    return this.mVolume;
  }

  public boolean isAvailable()
  {
    return this.mIsAvailable;
  }

  public boolean isMute()
    throws Exception
  {
    return this.mIsMute;
  }

  public boolean isOn()
  {
    return this.mIsReady;
  }

  public boolean isPaused()
    throws Exception
  {
    return !this.mIsPlaying;
  }

  public boolean isScanning()
  {
    return this.mIsScanning;
  }

  public boolean isSpeakerOn()
    throws Exception
  {
    return this.mRoute == Route.SPEAKER;
  }

  public void mute(boolean paramBoolean)
    throws Exception
  {
    if (!this.mIsReady)
      return;
    boolean bool;
    Boolean localBoolean;
    if (paramBoolean)
    {
      bool = false;
      localBoolean = Boolean.valueOf(bool);
      if (!paramBoolean)
        break label41;
    }
    label41: for (Route localRoute = Route.NONE; ; localRoute = this.mRoute)
    {
      switchAudioAndRoute(localBoolean, localRoute);
      return;
      bool = true;
      break;
    }
  }

  public void pause()
    throws Exception
  {
    mute(true);
    this.mIsPlaying = false;
  }

  public void registerFMEventListener(IFMEventListener paramIFMEventListener)
  {
    this.mListener = paramIFMEventListener;
  }

  public void scan()
    throws Exception
  {
    if (this.mIsScanning)
      invoke("stopSeek", new Object[0]);
    tune(87500);
    if (FM_AUDIO_ENABLED.booleanValue());
    for (boolean bool = false; ; bool = true)
    {
      switchAudioAndRoute(Boolean.valueOf(bool), Route.NONE);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(87500);
      invoke("seek", arrayOfObject);
      this.mIsPlaying = false;
      this.mIsScanning = true;
      return;
    }
  }

  public void setLiveAudioQualityCallback(boolean paramBoolean, int paramInt)
    throws Exception
  {
  }

  public int setSpeakerOn(boolean paramBoolean)
  {
    if (!this.mIsReady)
      return 1;
    if (paramBoolean);
    for (Route localRoute = Route.SPEAKER; ; localRoute = Route.HEADSET)
    {
      this.mRoute = localRoute;
      play();
      return 0;
    }
  }

  public void setVolume(int paramInt)
    throws Exception
  {
    Log.d("MiuiFM", "manager set volume to " + String.valueOf(paramInt));
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    invoke("setVolume", arrayOfObject);
    this.mVolume = paramInt;
  }

  public int tune(int paramInt)
    throws Exception
  {
    if (this.mIsScanning)
      invoke("stopSeek", new Object[0]);
    if (!isValidFreq(paramInt))
    {
      this.mListener.onTune(0);
      return 1;
    }
    if ((this.mCurrentFreq == paramInt) && (this.mIsPlaying))
    {
      this.mListener.onTune(this.mCurrentFreq);
      return 0;
    }
    this.mCurrentFreq = paramInt;
    if (!this.mIsReady)
    {
      powerOn();
      return 0;
    }
    return play();
  }

  public void turnOff()
    throws Exception
  {
    close();
  }

  public int turnOn()
    throws Exception
  {
    powerOn();
    return 0;
  }

  public void unregisterFMEventListener()
  {
    this.mListener = null;
  }

  private static enum Route
  {
    static
    {
      NONE = new Route("NONE", 2);
      Route[] arrayOfRoute = new Route[3];
      arrayOfRoute[0] = HEADSET;
      arrayOfRoute[1] = SPEAKER;
      arrayOfRoute[2] = NONE;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.miui.MiuiFM
 * JD-Core Version:    0.6.2
 */