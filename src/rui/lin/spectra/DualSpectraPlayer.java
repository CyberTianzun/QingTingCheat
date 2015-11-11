package rui.lin.spectra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DualSpectraPlayer
{
  protected static int sLogLevel = 3;
  protected static String sLogTag = "DualSpectraPlayer";
  protected static Logger sLogger = new Logger(sLogLevel, sLogTag);
  protected DualSpectraEventFilter mEventFilter;
  protected List<Spectra.SpectraEventListener> mEventListeners;
  protected boolean mIsInitialized;
  protected Spectra mLoadedPlayer;
  protected Spectra mMainPlayer;
  protected Spectra mSpectraA = new Spectra();
  protected Spectra mSpectraB = new Spectra();
  protected Spectra mVacantPlayer;

  public DualSpectraPlayer()
  {
    if ((this.mSpectraA.queryState() != Spectra.State.UNINITIALIZED) && (this.mSpectraB.queryState() != Spectra.State.UNINITIALIZED))
    {
      this.mVacantPlayer = this.mSpectraA;
      this.mLoadedPlayer = null;
      this.mMainPlayer = null;
      this.mEventListeners = Collections.synchronizedList(new ArrayList());
      this.mEventFilter = new DualSpectraEventFilter();
      this.mSpectraA.addEventListener(this.mEventFilter);
      this.mSpectraB.addEventListener(this.mEventFilter);
      this.mIsInitialized = true;
      sLogger.info("----- player initialized ------", new Object[0]);
      return;
    }
    this.mSpectraA = null;
    this.mSpectraB = null;
    this.mVacantPlayer = null;
    this.mLoadedPlayer = null;
    this.mMainPlayer = null;
    sLogger.error("----- player initialization failed ------", new Object[0]);
    this.mIsInitialized = false;
  }

  public boolean addEventListener(Spectra.SpectraEventListener paramSpectraEventListener)
  {
    if (this.mIsInitialized)
      return this.mEventListeners.add(paramSpectraEventListener);
    return false;
  }

  public void disableOpt()
  {
    this.mSpectraA.setShouldOpt(false);
    this.mSpectraB.setShouldOpt(false);
  }

  public void enableOpt()
  {
    this.mSpectraA.setShouldOpt(true);
    this.mSpectraB.setShouldOpt(true);
  }

  public void interrupt(boolean paramBoolean)
  {
    this.mSpectraA.interrupt(paramBoolean);
    this.mSpectraB.interrupt(paramBoolean);
  }

  public boolean interrupt()
  {
    return this.mSpectraA.interrupt();
  }

  public boolean isLiveStream()
  {
    if (this.mMainPlayer != null)
      return this.mMainPlayer.isLiveStream();
    return this.mVacantPlayer.isLiveStream();
  }

  public boolean load(List<String> paramList)
  {
    try
    {
      sLogger.info("#-> load", new Object[0]);
      boolean bool1 = this.mIsInitialized;
      boolean bool2 = false;
      if (bool1)
      {
        this.mLoadedPlayer = this.mVacantPlayer;
        boolean bool3 = this.mLoadedPlayer.load(paramList);
        bool2 = bool3;
      }
      return bool2;
    }
    finally
    {
    }
  }

  public boolean pause()
  {
    try
    {
      sLogger.info("#-> pause", new Object[0]);
      boolean bool1 = this.mIsInitialized;
      boolean bool2 = false;
      if (bool1)
      {
        Spectra localSpectra = this.mMainPlayer;
        bool2 = false;
        if (localSpectra != null)
        {
          boolean bool3 = this.mMainPlayer.pause();
          bool2 = bool3;
        }
      }
      return bool2;
    }
    finally
    {
    }
  }

  public boolean play()
  {
    try
    {
      sLogger.info("#-> play", new Object[0]);
      boolean bool1 = this.mIsInitialized;
      boolean bool2 = false;
      if (bool1)
      {
        Spectra localSpectra = this.mLoadedPlayer;
        bool2 = false;
        if (localSpectra != null)
        {
          if (this.mMainPlayer != null)
            this.mMainPlayer.stop();
          this.mMainPlayer = this.mLoadedPlayer;
          if (this.mVacantPlayer == this.mMainPlayer)
            switchVacantPlayer();
          boolean bool3 = this.mMainPlayer.play();
          bool2 = bool3;
        }
      }
      return bool2;
    }
    finally
    {
    }
  }

  public int queryBitRate()
  {
    if (this.mMainPlayer != null)
      return this.mMainPlayer.queryBitRate();
    return this.mVacantPlayer.queryBitRate();
  }

  public int queryChannels()
  {
    if (this.mMainPlayer != null)
      return this.mMainPlayer.queryChannels();
    return this.mVacantPlayer.queryChannels();
  }

  public String queryCompressionFormat()
  {
    if (this.mMainPlayer != null)
      return this.mMainPlayer.queryCompressionFormat();
    return this.mVacantPlayer.queryCompressionFormat();
  }

  public String queryContainerFormat()
  {
    if (this.mMainPlayer != null)
      return this.mMainPlayer.queryContainerFormat();
    return this.mVacantPlayer.queryContainerFormat();
  }

  public int queryDuration()
  {
    if (this.mMainPlayer != null)
      return this.mMainPlayer.queryDuration();
    return this.mVacantPlayer.queryDuration();
  }

  public byte[] queryMetadata()
  {
    if (this.mMainPlayer != null)
      return this.mMainPlayer.queryMetadata();
    return this.mVacantPlayer.queryMetadata();
  }

  public int queryPosition()
  {
    if (this.mMainPlayer != null)
      return this.mMainPlayer.queryPosition();
    return this.mVacantPlayer.queryPosition();
  }

  public ArrayList<String> queryPreloadedUrls()
  {
    if (this.mLoadedPlayer != null)
      return this.mLoadedPlayer.queryUrls();
    return null;
  }

  public String querySampleFormat()
  {
    if (this.mMainPlayer != null)
      return this.mMainPlayer.querySampleFormat();
    return this.mVacantPlayer.querySampleFormat();
  }

  public int querySampleRate()
  {
    if (this.mMainPlayer != null)
      return this.mMainPlayer.querySampleRate();
    return this.mVacantPlayer.querySampleRate();
  }

  public String querySelectedUrl()
  {
    if (this.mMainPlayer != null)
      return this.mMainPlayer.querySelectedUrl();
    return this.mVacantPlayer.querySelectedUrl();
  }

  public Spectra.State queryState()
  {
    if (this.mMainPlayer != null);
    for (Spectra.State localState = this.mMainPlayer.queryState(); ; localState = this.mVacantPlayer.queryState())
    {
      if (localState == Spectra.State.LOADED)
        localState = Spectra.State.STOPPED;
      return localState;
    }
  }

  public ArrayList<String> queryUrls()
  {
    if (this.mMainPlayer != null)
      return this.mMainPlayer.queryUrls();
    return this.mVacantPlayer.queryUrls();
  }

  public boolean reconnect()
  {
    try
    {
      sLogger.info("#-> reconnect", new Object[0]);
      boolean bool1 = this.mIsInitialized;
      boolean bool2 = false;
      boolean bool4;
      if (bool1)
      {
        Spectra localSpectra = this.mMainPlayer;
        bool2 = false;
        if (localSpectra != null)
        {
          if (!this.mMainPlayer.isLiveStream())
            break label72;
          this.mMainPlayer.stop();
          bool4 = this.mMainPlayer.play();
        }
      }
      label72: boolean bool3;
      for (bool2 = bool4; ; bool2 = bool3)
      {
        return bool2;
        int i = this.mMainPlayer.queryPosition();
        this.mMainPlayer.stop();
        this.mMainPlayer.play();
        bool3 = this.mMainPlayer.seek(i);
      }
    }
    finally
    {
    }
  }

  public boolean removeEventListener(Spectra.SpectraEventListener paramSpectraEventListener)
  {
    if (this.mIsInitialized)
      return this.mEventListeners.remove(paramSpectraEventListener);
    return false;
  }

  public boolean resume()
  {
    try
    {
      sLogger.info("#-> resume", new Object[0]);
      boolean bool1 = this.mIsInitialized;
      boolean bool2 = false;
      if (bool1)
      {
        Spectra localSpectra = this.mMainPlayer;
        bool2 = false;
        if (localSpectra != null)
        {
          boolean bool3 = this.mMainPlayer.resume();
          bool2 = bool3;
        }
      }
      return bool2;
    }
    finally
    {
    }
  }

  public boolean seek(int paramInt)
  {
    try
    {
      sLogger.info("#-> seek", new Object[0]);
      boolean bool1 = this.mIsInitialized;
      boolean bool2 = false;
      if (bool1)
      {
        Spectra localSpectra = this.mMainPlayer;
        bool2 = false;
        if (localSpectra != null)
        {
          boolean bool3 = this.mMainPlayer.seek(paramInt);
          bool2 = bool3;
        }
      }
      return bool2;
    }
    finally
    {
    }
  }

  public boolean stop()
  {
    try
    {
      sLogger.info("#-> stop", new Object[0]);
      boolean bool1 = this.mIsInitialized;
      boolean bool2 = false;
      if (bool1)
      {
        Spectra localSpectra = this.mMainPlayer;
        bool2 = false;
        if (localSpectra != null)
        {
          boolean bool3 = this.mMainPlayer.stop();
          bool2 = bool3;
        }
      }
      return bool2;
    }
    finally
    {
    }
  }

  public void switchVacantPlayer()
  {
    if (this.mVacantPlayer == this.mSpectraA);
    for (Spectra localSpectra = this.mSpectraB; ; localSpectra = this.mSpectraA)
    {
      this.mVacantPlayer = localSpectra;
      return;
    }
  }

  class DualSpectraEventFilter
    implements Spectra.SpectraEventListener
  {
    DualSpectraEventFilter()
    {
    }

    public void onSpectraEvent(Spectra paramSpectra, Spectra.SpectraEvent paramSpectraEvent)
    {
      if (paramSpectra == DualSpectraPlayer.this.mMainPlayer)
      {
        Iterator localIterator = DualSpectraPlayer.this.mEventListeners.iterator();
        while (localIterator.hasNext())
          ((Spectra.SpectraEventListener)localIterator.next()).onSpectraEvent(null, paramSpectraEvent);
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     rui.lin.spectra.DualSpectraPlayer
 * JD-Core Version:    0.6.2
 */