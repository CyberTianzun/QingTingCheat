package fm.qingting.qtradio.fmdriver;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import fm.qingting.framework.controller.StatisticsFMManage;
import fm.qingting.qtradio.broadcom.BroadcomFM;
import fm.qingting.qtradio.fmreceive.FmReceiver;
import fm.qingting.qtradio.galaxy.GalaxyFM;
import fm.qingting.qtradio.miui.MiuiFM;
import fm.qingting.qtradio.miui2.MiuiFMReceiver;
import fm.qingting.qtradio.motorola.MotorolaFM;
import fm.qingting.qtradio.zte.ZteFM;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class FMManager
  implements IFMEventListener, IFMControlEventListener
{
  private static final int FM_PAUSED_TAG = 2;
  private static final int FM_SCAN_COMPLETE = 9;
  private static final int FM_SCAN_NONE = 7;
  private static final int FM_SCAN_SCAN = 8;
  private static final int FM_SCAN_TAG = 3;
  private static final int FM_STOPED_TAG = 5;
  private static final int FM_STOPSCAN_TAG = 4;
  private static final int FM_TUNED_TAG = 1;
  private static final String TAG = "FMManager";
  private static FMManager instance;
  private int SCANSTATE = 7;
  private int currentFreq = 0;
  private FMDriver fmDriver = null;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
        FMManager.this.stopTuneThread = null;
        FMManager.this.scanThread = null;
        FMManager.this.stopScanThread = null;
        return;
      case 2:
        FMManager.this.tuneThread = null;
        FMManager.this.scanThread = null;
        FMManager.this.stopScanThread = null;
        return;
      case 3:
        FMManager.this.tuneThread = null;
        FMManager.this.stopTuneThread = null;
        FMManager.this.stopScanThread = null;
        return;
      case 4:
        FMManager.this.tuneThread = null;
        FMManager.this.stopTuneThread = null;
        FMManager.this.scanThread = null;
        return;
      case 5:
      }
      FMManager.this.tuneThread = null;
      FMManager.this.stopTuneThread = null;
      FMManager.this.scanThread = null;
      FMManager.this.stopScanThread = null;
      FMManager.this.offFMThread = null;
    }
  };
  private boolean isPlaying = false;
  private HashSet<WeakReference<IFMEventListener>> listeners = new HashSet();
  private Context mContext;
  private int mCurrentChannel = 0;
  private OffFMThread offFMThread;
  private long playFMBegin = 0L;
  private long playFMEnd = 0L;
  private ScanThread scanThread;
  private boolean scanning = false;
  private StopScanThread stopScanThread;
  private StopTuneThread stopTuneThread;
  private TuneThread tuneThread;

  public static FMManager getInstance()
  {
    if (instance == null)
      instance = new FMManager();
    return instance;
  }

  private void pausedThread()
  {
    if (isStoping())
    {
      this.stopTuneThread = new StopTuneThread(null);
      this.stopTuneThread.start();
    }
  }

  private void removeUnavailableListener()
  {
    Iterator localIterator = this.listeners.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      if ((IFMControlEventListener)((WeakReference)localIterator.next()).get() == null)
        localIterator.remove();
    }
  }

  private void setMute(boolean paramBoolean)
  {
    if (isAvailable());
    try
    {
      if (this.fmDriver.isOn())
        this.fmDriver.mute(paramBoolean);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void startTune()
  {
    if ((this.tuneThread == null) || (!this.tuneThread.isAlive()))
    {
      this.tuneThread = new TuneThread(null);
      this.tuneThread.start();
    }
  }

  public void addListener(IFMEventListener paramIFMEventListener)
  {
    Iterator localIterator = this.listeners.iterator();
    do
      if (!localIterator.hasNext())
      {
        this.listeners.add(new WeakReference(paramIFMEventListener));
        return;
      }
    while (((WeakReference)localIterator.next()).get() != paramIFMEventListener);
  }

  public void cancelScanning()
  {
    onScanComplete(true);
    if (this.stopScanThread == null)
    {
      this.stopScanThread = new StopScanThread(null);
      this.stopScanThread.start();
    }
  }

  public long getCurrentFmtime()
  {
    long l = 0L;
    if ((this.isPlaying) && (this.playFMBegin != l))
      l = System.currentTimeMillis() - this.playFMBegin;
    return l;
  }

  public int getCurrentRSSI()
  {
    if (isAvailable())
      try
      {
        int i = this.fmDriver.getCurrentRSSI();
        return i;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return 0;
  }

  public int getMaxVolumn()
  {
    return ((AudioManager)this.mContext.getSystemService("audio")).getStreamMaxVolume(this.fmDriver.getAudioStreamType());
  }

  public String getName()
  {
    String str = "";
    if (isAvailable())
      str = this.fmDriver.getName();
    return str;
  }

  public int getVolume()
  {
    boolean bool = isAvailable();
    int i = 0;
    if (bool);
    try
    {
      int j = this.fmDriver.getVolume();
      i = j;
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  public void initDrivers(Context paramContext)
  {
    this.mContext = paramContext;
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(MiuiFM.class);
    localArrayList.add(BroadcomFM.class);
    localArrayList.add(FmReceiver.class);
    localArrayList.add(GalaxyFM.class);
    localArrayList.add(MotorolaFM.class);
    localArrayList.add(ZteFM.class);
    localArrayList.add(MiuiFMReceiver.class);
    FMcontrol.getInstance().registerHeadsetPlugReceiver(paramContext);
    FMcontrol.getInstance().registerPhoneState(paramContext);
    FMcontrol.getInstance().addListener(this);
    Class[] arrayOfClass = { Context.class };
    Object[] arrayOfObject = { paramContext };
    Iterator localIterator = localArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      Class localClass = (Class)localIterator.next();
      try
      {
        Constructor localConstructor = localClass.getConstructor(arrayOfClass);
        if (localConstructor != null)
        {
          FMDriver localFMDriver = (FMDriver)localConstructor.newInstance(arrayOfObject);
          if ((localFMDriver != null) && (localFMDriver.isAvailable()))
          {
            if (this.fmDriver != null)
              this.fmDriver.registerFMEventListener(null);
            this.fmDriver = localFMDriver;
            this.fmDriver.registerFMEventListener(this);
            return;
          }
        }
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        localIllegalAccessException.printStackTrace();
      }
      catch (InstantiationException localInstantiationException)
      {
        localInstantiationException.printStackTrace();
      }
      catch (SecurityException localSecurityException)
      {
        localSecurityException.printStackTrace();
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        localNoSuchMethodException.printStackTrace();
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        localIllegalArgumentException.printStackTrace();
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        localInvocationTargetException.printStackTrace();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public boolean isAvailable()
  {
    return this.fmDriver != null;
  }

  public boolean isMute()
  {
    if (isAvailable())
      try
      {
        boolean bool = this.fmDriver.isMute();
        return bool;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return false;
  }

  public boolean isOn()
  {
    if (isAvailable())
      try
      {
        boolean bool = this.fmDriver.isOn();
        return bool;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return false;
  }

  public boolean isPaused()
  {
    if (isAvailable())
      try
      {
        boolean bool = this.fmDriver.isPaused();
        return bool;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return false;
  }

  public boolean isPlaying()
  {
    return this.isPlaying;
  }

  public boolean isScanning()
  {
    if (isAvailable())
      try
      {
        boolean bool = this.fmDriver.isScanning();
        return bool;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return false;
  }

  public boolean isSpeakerOn()
  {
    if (isAvailable())
      try
      {
        boolean bool = this.fmDriver.isSpeakerOn();
        return bool;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return false;
  }

  public boolean isStoping()
  {
    return this.stopTuneThread == null;
  }

  public void onAudioQualityStatus(int paramInt)
  {
  }

  public void onChannelFound(int paramInt)
  {
    if (!this.scanning);
    while (true)
    {
      return;
      removeUnavailableListener();
      Iterator localIterator = this.listeners.iterator();
      while (localIterator.hasNext())
      {
        IFMEventListener localIFMEventListener = (IFMControlEventListener)((WeakReference)localIterator.next()).get();
        if (localIFMEventListener != null)
          localIFMEventListener.onChannelFound(paramInt);
      }
    }
  }

  public void onFMOff()
  {
    removeUnavailableListener();
    Iterator localIterator = this.listeners.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      IFMEventListener localIFMEventListener = (IFMControlEventListener)((WeakReference)localIterator.next()).get();
      if (localIFMEventListener != null)
        localIFMEventListener.onFMOff();
    }
  }

  public void onFMOn()
  {
    removeUnavailableListener();
    Iterator localIterator = this.listeners.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      IFMEventListener localIFMEventListener = (IFMControlEventListener)((WeakReference)localIterator.next()).get();
      if (localIFMEventListener != null)
        localIFMEventListener.onFMOn();
    }
  }

  public void onHeadsetPlugged()
  {
    removeUnavailableListener();
    Iterator localIterator = this.listeners.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      IFMEventListener localIFMEventListener = (IFMControlEventListener)((WeakReference)localIterator.next()).get();
      if (localIFMEventListener != null)
        localIFMEventListener.onHeadsetPlugged();
    }
  }

  public void onHeadsetUnplugged()
  {
    if (isScanning())
      cancelScanning();
    removeUnavailableListener();
    Iterator localIterator = this.listeners.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      IFMEventListener localIFMEventListener = (IFMControlEventListener)((WeakReference)localIterator.next()).get();
      if (localIFMEventListener != null)
        localIFMEventListener.onHeadsetUnplugged();
    }
  }

  public void onMobilesState(boolean paramBoolean)
  {
    setMute(paramBoolean);
  }

  public void onScanComplete(boolean paramBoolean)
  {
    if (this.SCANSTATE == 7);
    while (true)
    {
      return;
      this.SCANSTATE = 7;
      try
      {
        if (this.scanThread != null)
          this.scanThread.interrupt();
        this.scanning = false;
        removeUnavailableListener();
        Iterator localIterator = this.listeners.iterator();
        while (localIterator.hasNext())
        {
          IFMEventListener localIFMEventListener = (IFMControlEventListener)((WeakReference)localIterator.next()).get();
          if (localIFMEventListener != null)
            localIFMEventListener.onScanComplete(paramBoolean);
        }
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  public void onScanStarted()
  {
    removeUnavailableListener();
    Iterator localIterator = this.listeners.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      IFMEventListener localIFMEventListener = (IFMControlEventListener)((WeakReference)localIterator.next()).get();
      if (localIFMEventListener != null)
        localIFMEventListener.onScanStarted();
    }
  }

  public void onTune(int paramInt)
  {
  }

  public void pause()
  {
    pausedThread();
  }

  public void poweroff()
  {
    if (isAvailable());
    try
    {
      this.fmDriver.turnOff();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void removeListener(IFMEventListener paramIFMEventListener)
  {
    Iterator localIterator = this.listeners.iterator();
    do
      if (!localIterator.hasNext())
        return;
    while (((WeakReference)localIterator.next()).get() != paramIFMEventListener);
    localIterator.remove();
  }

  public boolean scan()
  {
    if (this.scanThread == null)
    {
      this.scanThread = new ScanThread(null);
      this.scanThread.start();
    }
    return true;
  }

  public int setSpeakerOn(boolean paramBoolean)
  {
    boolean bool = isAvailable();
    int i = 0;
    if (bool);
    try
    {
      int j = this.fmDriver.setSpeakerOn(paramBoolean);
      i = j;
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  public void setVolume(int paramInt)
  {
    if (isAvailable());
    try
    {
      this.fmDriver.setVolume(paramInt);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void tune(int paramInt)
  {
    this.currentFreq = paramInt;
    startTune();
  }

  public void turnOff()
  {
    FMcontrol.getInstance().unregisterHeadsetPlugReceiver(this.mContext);
    unregisterFMEventListener();
    if ((this.offFMThread == null) || (!this.offFMThread.isAlive()))
    {
      this.offFMThread = new OffFMThread(null);
      this.offFMThread.start();
    }
  }

  public int turnOn()
  {
    if (isAvailable())
      try
      {
        int i = this.fmDriver.turnOn();
        return i;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return 0;
  }

  public void unregisterFMEventListener()
  {
    if (isAvailable())
      this.fmDriver.unregisterFMEventListener();
    FMcontrol.getInstance().unregisterHeadsetPlugReceiver(this.mContext);
  }

  private class OffFMThread extends Thread
  {
    private OffFMThread()
    {
    }

    public void run()
    {
      if (FMManager.this.isAvailable());
      try
      {
        FMManager.this.scanning = false;
        FMManager.this.fmDriver.turnOff();
        if (FMManager.this.playFMBegin != 0L)
        {
          FMManager.this.playFMEnd = System.currentTimeMillis();
          StatisticsFMManage.getInstance(FMManager.this.mContext).setFMtime(FMManager.this.playFMEnd - FMManager.this.playFMBegin);
          FMManager.this.playFMBegin = 0L;
        }
        FMManager.this.handler.sendEmptyMessage(5);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  private class ScanThread extends Thread
  {
    private ScanThread()
    {
    }

    public void run()
    {
      if (FMManager.this.fmDriver == null)
        return;
      if (FMManager.this.tuneThread != null)
        FMManager.this.tuneThread.interrupt();
      if (FMManager.this.tuneThread != null);
      try
      {
        FMManager.this.tuneThread.join();
        FMManager.this.fmDriver.registerFMEventListener(FMManager.this);
        try
        {
          if (!FMManager.this.fmDriver.isOn())
            FMManager.this.fmDriver.turnOn();
          FMManager.this.scanning = true;
          FMManager.this.fmDriver.scan();
          FMManager.this.SCANSTATE = 8;
          FMManager.this.handler.sendEmptyMessage(3);
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        while (true)
          localInterruptedException.printStackTrace();
      }
    }
  }

  private class StopScanThread extends Thread
  {
    private StopScanThread()
    {
    }

    public void run()
    {
      if (FMManager.this.scanThread != null)
        FMManager.this.scanThread.interrupt();
      if (FMManager.this.isAvailable());
      try
      {
        FMManager.this.scanning = false;
        FMManager.this.fmDriver.cancelScanning();
        FMManager.this.handler.sendEmptyMessage(4);
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  private class StopTuneThread extends Thread
  {
    private StopTuneThread()
    {
    }

    public void run()
    {
      if (FMManager.this.tuneThread != null)
        FMManager.this.tuneThread.interrupt();
      if (FMManager.this.tuneThread != null);
      try
      {
        FMManager.this.tuneThread.join();
        if (!FMManager.this.isAvailable());
      }
      catch (InterruptedException localInterruptedException)
      {
        try
        {
          FMManager.this.scanning = false;
          FMManager.this.fmDriver.pause();
          if (FMManager.this.playFMBegin != 0L)
          {
            FMManager.this.playFMEnd = System.currentTimeMillis();
            StatisticsFMManage.getInstance(FMManager.this.mContext).setFMtime(FMManager.this.playFMEnd - FMManager.this.playFMBegin);
            FMManager.this.playFMBegin = 0L;
          }
          FMManager.this.isPlaying = false;
          FMManager.this.handler.sendEmptyMessage(2);
          return;
          localInterruptedException = localInterruptedException;
          localInterruptedException.printStackTrace();
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    }
  }

  private class TuneThread extends Thread
  {
    private TuneThread()
    {
    }

    public void run()
    {
      try
      {
        if (!FMManager.this.fmDriver.isOn())
        {
          Log.e("fmDriver", "not on");
          FMManager.this.fmDriver.turnOn();
        }
        FMManager.this.scanning = false;
        FMManager.this.fmDriver.tune(FMManager.this.currentFreq);
        if (FMManager.this.playFMBegin == 0L);
        for (FMManager.this.playFMBegin = System.currentTimeMillis(); ; FMManager.this.playFMBegin = System.currentTimeMillis())
        {
          FMManager.this.mCurrentChannel = FMManager.this.currentFreq;
          StatisticsFMManage.getInstance(FMManager.this.mContext).setUsed(true);
          FMManager.this.isPlaying = true;
          FMManager.this.handler.sendEmptyMessage(1);
          return;
          FMManager.this.playFMEnd = System.currentTimeMillis();
          StatisticsFMManage.getInstance(FMManager.this.mContext).setFMtime(FMManager.this.playFMEnd - FMManager.this.playFMBegin);
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.fmdriver.FMManager
 * JD-Core Version:    0.6.2
 */