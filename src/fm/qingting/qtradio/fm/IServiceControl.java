package fm.qingting.qtradio.fm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IServiceControl extends IInterface
{
  public abstract void addABTestCategory(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract void addABTestProgram(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;

  public abstract void addPlaySource(int paramInt)
    throws RemoteException;

  public abstract void disableOpt()
    throws RemoteException;

  public abstract void enableOpt()
    throws RemoteException;

  public abstract void exit()
    throws RemoteException;

  public abstract String getHttpProxy()
    throws RemoteException;

  public abstract boolean getIsViaWoProxy()
    throws RemoteException;

  public abstract int getPlayingCatId()
    throws RemoteException;

  public abstract int getPlayingChannelId()
    throws RemoteException;

  public abstract String getSource()
    throws RemoteException;

  public abstract void pause()
    throws RemoteException;

  public abstract void play()
    throws RemoteException;

  public abstract String queryContainerFormat()
    throws RemoteException;

  public abstract int queryDuration()
    throws RemoteException;

  public abstract boolean queryIsLiveStream()
    throws RemoteException;

  public abstract int queryPlayInfo(int paramInt)
    throws RemoteException;

  public abstract String queryPlayingUrl()
    throws RemoteException;

  public abstract int queryPosition()
    throws RemoteException;

  public abstract void resume()
    throws RemoteException;

  public abstract void seek(int paramInt)
    throws RemoteException;

  public abstract void setBufferTime(float paramFloat)
    throws RemoteException;

  public abstract void setCategoryInfo(String paramString1, String paramString2)
    throws RemoteException;

  public abstract boolean setHttpProxy(String paramString)
    throws RemoteException;

  public abstract boolean setIsViaWoProxy(boolean paramBoolean)
    throws RemoteException;

  public abstract void setLocation(String paramString1, String paramString2)
    throws RemoteException;

  public abstract void setPlayingChannel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString1, String paramString2, int paramInt7)
    throws RemoteException;

  public abstract void setPlayingSourceList(String paramString)
    throws RemoteException;

  public abstract void setSource(String paramString)
    throws RemoteException;

  public abstract void setVolume(float paramFloat)
    throws RemoteException;

  public abstract void startQuitTimer()
    throws RemoteException;

  public abstract void stop()
    throws RemoteException;

  public abstract void stopQuitTimer()
    throws RemoteException;

  public abstract boolean unsetHttpProxy()
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IServiceControl
  {
    private static final String DESCRIPTOR = "fm.qingting.qtradio.fm.IServiceControl";
    static final int TRANSACTION_addABTestCategory = 26;
    static final int TRANSACTION_addABTestProgram = 27;
    static final int TRANSACTION_addPlaySource = 17;
    static final int TRANSACTION_disableOpt = 2;
    static final int TRANSACTION_enableOpt = 1;
    static final int TRANSACTION_exit = 3;
    static final int TRANSACTION_getHttpProxy = 30;
    static final int TRANSACTION_getIsViaWoProxy = 34;
    static final int TRANSACTION_getPlayingCatId = 19;
    static final int TRANSACTION_getPlayingChannelId = 18;
    static final int TRANSACTION_getSource = 12;
    static final int TRANSACTION_pause = 7;
    static final int TRANSACTION_play = 5;
    static final int TRANSACTION_queryContainerFormat = 23;
    static final int TRANSACTION_queryDuration = 20;
    static final int TRANSACTION_queryIsLiveStream = 21;
    static final int TRANSACTION_queryPlayInfo = 24;
    static final int TRANSACTION_queryPlayingUrl = 25;
    static final int TRANSACTION_queryPosition = 22;
    static final int TRANSACTION_resume = 8;
    static final int TRANSACTION_seek = 9;
    static final int TRANSACTION_setBufferTime = 10;
    static final int TRANSACTION_setCategoryInfo = 14;
    static final int TRANSACTION_setHttpProxy = 31;
    static final int TRANSACTION_setIsViaWoProxy = 33;
    static final int TRANSACTION_setLocation = 16;
    static final int TRANSACTION_setPlayingChannel = 15;
    static final int TRANSACTION_setPlayingSourceList = 13;
    static final int TRANSACTION_setSource = 4;
    static final int TRANSACTION_setVolume = 11;
    static final int TRANSACTION_startQuitTimer = 28;
    static final int TRANSACTION_stop = 6;
    static final int TRANSACTION_stopQuitTimer = 29;
    static final int TRANSACTION_unsetHttpProxy = 32;

    public Stub()
    {
      attachInterface(this, "fm.qingting.qtradio.fm.IServiceControl");
    }

    public static IServiceControl asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("fm.qingting.qtradio.fm.IServiceControl");
      if ((localIInterface != null) && ((localIInterface instanceof IServiceControl)))
        return (IServiceControl)localIInterface;
      return new Proxy(paramIBinder);
    }

    public IBinder asBinder()
    {
      return this;
    }

    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default:
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902:
        paramParcel2.writeString("fm.qingting.qtradio.fm.IServiceControl");
        return true;
      case 1:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        enableOpt();
        paramParcel2.writeNoException();
        return true;
      case 2:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        disableOpt();
        paramParcel2.writeNoException();
        return true;
      case 3:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        exit();
        paramParcel2.writeNoException();
        return true;
      case 4:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        setSource(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 5:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        play();
        paramParcel2.writeNoException();
        return true;
      case 6:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        stop();
        paramParcel2.writeNoException();
        return true;
      case 7:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        pause();
        paramParcel2.writeNoException();
        return true;
      case 8:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        resume();
        paramParcel2.writeNoException();
        return true;
      case 9:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        seek(paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 10:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        setBufferTime(paramParcel1.readFloat());
        paramParcel2.writeNoException();
        return true;
      case 11:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        setVolume(paramParcel1.readFloat());
        paramParcel2.writeNoException();
        return true;
      case 12:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        String str4 = getSource();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str4);
        return true;
      case 13:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        setPlayingSourceList(paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 14:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        setCategoryInfo(paramParcel1.readString(), paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 15:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        setPlayingChannel(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 16:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        setLocation(paramParcel1.readString(), paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 17:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        addPlaySource(paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 18:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        int i5 = getPlayingChannelId();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i5);
        return true;
      case 19:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        int i4 = getPlayingCatId();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i4);
        return true;
      case 20:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        int i3 = queryDuration();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i3);
        return true;
      case 21:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        boolean bool6 = queryIsLiveStream();
        paramParcel2.writeNoException();
        int i2 = 0;
        if (bool6)
          i2 = 1;
        paramParcel2.writeInt(i2);
        return true;
      case 22:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        int i1 = queryPosition();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i1);
        return true;
      case 23:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        String str3 = queryContainerFormat();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str3);
        return true;
      case 24:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        int n = queryPlayInfo(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(n);
        return true;
      case 25:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        String str2 = queryPlayingUrl();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str2);
        return true;
      case 26:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        addABTestCategory(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 27:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        addABTestProgram(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 28:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        startQuitTimer();
        paramParcel2.writeNoException();
        return true;
      case 29:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        stopQuitTimer();
        paramParcel2.writeNoException();
        return true;
      case 30:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        String str1 = getHttpProxy();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str1);
        return true;
      case 31:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        boolean bool5 = setHttpProxy(paramParcel1.readString());
        paramParcel2.writeNoException();
        int m = 0;
        if (bool5)
          m = 1;
        paramParcel2.writeInt(m);
        return true;
      case 32:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        boolean bool4 = unsetHttpProxy();
        paramParcel2.writeNoException();
        int k = 0;
        if (bool4)
          k = 1;
        paramParcel2.writeInt(k);
        return true;
      case 33:
        paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
        if (paramParcel1.readInt() != 0);
        for (boolean bool2 = true; ; bool2 = false)
        {
          boolean bool3 = setIsViaWoProxy(bool2);
          paramParcel2.writeNoException();
          int j = 0;
          if (bool3)
            j = 1;
          paramParcel2.writeInt(j);
          return true;
        }
      case 34:
      }
      paramParcel1.enforceInterface("fm.qingting.qtradio.fm.IServiceControl");
      boolean bool1 = getIsViaWoProxy();
      paramParcel2.writeNoException();
      int i = 0;
      if (bool1)
        i = 1;
      paramParcel2.writeInt(i);
      return true;
    }

    private static class Proxy
      implements IServiceControl
    {
      private IBinder mRemote;

      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }

      public void addABTestCategory(int paramInt1, int paramInt2, int paramInt3)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeInt(paramInt3);
          this.mRemote.transact(26, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void addABTestProgram(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeInt(paramInt3);
          localParcel1.writeInt(paramInt4);
          this.mRemote.transact(27, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void addPlaySource(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(17, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public IBinder asBinder()
      {
        return this.mRemote;
      }

      public void disableOpt()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void enableOpt()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void exit()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String getHttpProxy()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(30, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String getInterfaceDescriptor()
      {
        return "fm.qingting.qtradio.fm.IServiceControl";
      }

      public boolean getIsViaWoProxy()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(34, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0)
            bool = true;
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public int getPlayingCatId()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(19, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public int getPlayingChannelId()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(18, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String getSource()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(12, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void pause()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void play()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String queryContainerFormat()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(23, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public int queryDuration()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(20, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public boolean queryIsLiveStream()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(21, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0)
            bool = true;
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public int queryPlayInfo(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(24, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String queryPlayingUrl()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(25, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public int queryPosition()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(22, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void resume()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(8, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void seek(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(9, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void setBufferTime(float paramFloat)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeFloat(paramFloat);
          this.mRemote.transact(10, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void setCategoryInfo(String paramString1, String paramString2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeString(paramString1);
          localParcel1.writeString(paramString2);
          this.mRemote.transact(14, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public boolean setHttpProxy(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeString(paramString);
          this.mRemote.transact(31, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0)
            bool = true;
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public boolean setIsViaWoProxy(boolean paramBoolean)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          if (paramBoolean)
          {
            int j = i;
            localParcel1.writeInt(j);
            this.mRemote.transact(33, localParcel1, localParcel2, 0);
            localParcel2.readException();
            int m = localParcel2.readInt();
            if (m == 0)
              break label80;
          }
          while (true)
          {
            return i;
            int k = 0;
            break;
            label80: i = 0;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void setLocation(String paramString1, String paramString2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeString(paramString1);
          localParcel1.writeString(paramString2);
          this.mRemote.transact(16, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void setPlayingChannel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString1, String paramString2, int paramInt7)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeInt(paramInt3);
          localParcel1.writeInt(paramInt4);
          localParcel1.writeInt(paramInt5);
          localParcel1.writeInt(paramInt6);
          localParcel1.writeString(paramString1);
          localParcel1.writeString(paramString2);
          localParcel1.writeInt(paramInt7);
          this.mRemote.transact(15, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void setPlayingSourceList(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeString(paramString);
          this.mRemote.transact(13, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void setSource(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeString(paramString);
          this.mRemote.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void setVolume(float paramFloat)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          localParcel1.writeFloat(paramFloat);
          this.mRemote.transact(11, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void startQuitTimer()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(28, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void stop()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(6, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void stopQuitTimer()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(29, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public boolean unsetHttpProxy()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("fm.qingting.qtradio.fm.IServiceControl");
          this.mRemote.transact(32, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0)
            bool = true;
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.fm.IServiceControl
 * JD-Core Version:    0.6.2
 */