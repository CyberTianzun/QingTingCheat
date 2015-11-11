package com.motorola.android.fmradio;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IFMRadioService extends IInterface
{
  public abstract boolean getAudioMode()
    throws RemoteException;

  public abstract boolean getAudioType()
    throws RemoteException;

  public abstract int getBand()
    throws RemoteException;

  public abstract boolean getCurrentFreq()
    throws RemoteException;

  public abstract int getMaxFrequence()
    throws RemoteException;

  public abstract int getMinFrequence()
    throws RemoteException;

  public abstract String getRDSStationName()
    throws RemoteException;

  public abstract boolean getRSSI()
    throws RemoteException;

  public abstract int getRdsPI()
    throws RemoteException;

  public abstract String getRdsPS()
    throws RemoteException;

  public abstract int getRdsPTY()
    throws RemoteException;

  public abstract String getRdsRT()
    throws RemoteException;

  public abstract String getRdsRTPLUS()
    throws RemoteException;

  public abstract int getStepUnit()
    throws RemoteException;

  public abstract boolean getVolume()
    throws RemoteException;

  public abstract boolean isMute()
    throws RemoteException;

  public abstract boolean isRdsEnable()
    throws RemoteException;

  public abstract void registerCallback(IFMRadioServiceCallback paramIFMRadioServiceCallback)
    throws RemoteException;

  public abstract boolean scan()
    throws RemoteException;

  public abstract boolean seek(int paramInt)
    throws RemoteException;

  public abstract boolean setAudioMode(int paramInt)
    throws RemoteException;

  public abstract boolean setBand(int paramInt)
    throws RemoteException;

  public abstract boolean setMute(int paramInt)
    throws RemoteException;

  public abstract boolean setRSSI(int paramInt)
    throws RemoteException;

  public abstract boolean setRdsEnable(boolean paramBoolean, int paramInt)
    throws RemoteException;

  public abstract boolean setVolume(int paramInt)
    throws RemoteException;

  public abstract boolean stopScan()
    throws RemoteException;

  public abstract boolean stopSeek()
    throws RemoteException;

  public abstract boolean tune(int paramInt)
    throws RemoteException;

  public abstract void unregisterCallback(IFMRadioServiceCallback paramIFMRadioServiceCallback)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IFMRadioService
  {
    private static final String DESCRIPTOR = "com.motorola.android.fmradio.IFMRadioService";
    static final int TRANSACTION_getAudioMode = 4;
    static final int TRANSACTION_getAudioType = 22;
    static final int TRANSACTION_getBand = 13;
    static final int TRANSACTION_getCurrentFreq = 2;
    static final int TRANSACTION_getMaxFrequence = 16;
    static final int TRANSACTION_getMinFrequence = 15;
    static final int TRANSACTION_getRDSStationName = 30;
    static final int TRANSACTION_getRSSI = 23;
    static final int TRANSACTION_getRdsPI = 27;
    static final int TRANSACTION_getRdsPS = 24;
    static final int TRANSACTION_getRdsPTY = 28;
    static final int TRANSACTION_getRdsRT = 25;
    static final int TRANSACTION_getRdsRTPLUS = 26;
    static final int TRANSACTION_getStepUnit = 17;
    static final int TRANSACTION_getVolume = 12;
    static final int TRANSACTION_isMute = 6;
    static final int TRANSACTION_isRdsEnable = 21;
    static final int TRANSACTION_registerCallback = 18;
    static final int TRANSACTION_scan = 8;
    static final int TRANSACTION_seek = 7;
    static final int TRANSACTION_setAudioMode = 3;
    static final int TRANSACTION_setBand = 14;
    static final int TRANSACTION_setMute = 5;
    static final int TRANSACTION_setRSSI = 29;
    static final int TRANSACTION_setRdsEnable = 20;
    static final int TRANSACTION_setVolume = 11;
    static final int TRANSACTION_stopScan = 10;
    static final int TRANSACTION_stopSeek = 9;
    static final int TRANSACTION_tune = 1;
    static final int TRANSACTION_unregisterCallback = 19;

    public Stub()
    {
      attachInterface(this, "com.motorola.android.fmradio.IFMRadioService");
    }

    public static IFMRadioService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.motorola.android.fmradio.IFMRadioService");
      if ((localIInterface != null) && ((localIInterface instanceof IFMRadioService)))
        return (IFMRadioService)localIInterface;
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
        paramParcel2.writeString("com.motorola.android.fmradio.IFMRadioService");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool19 = tune(paramParcel1.readInt());
        paramParcel2.writeNoException();
        int i19 = 0;
        if (bool19)
          i19 = 1;
        paramParcel2.writeInt(i19);
        return true;
      case 2:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool18 = getCurrentFreq();
        paramParcel2.writeNoException();
        int i18 = 0;
        if (bool18)
          i18 = 1;
        paramParcel2.writeInt(i18);
        return true;
      case 3:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool17 = setAudioMode(paramParcel1.readInt());
        paramParcel2.writeNoException();
        int i17 = 0;
        if (bool17)
          i17 = 1;
        paramParcel2.writeInt(i17);
        return true;
      case 4:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool16 = getAudioMode();
        paramParcel2.writeNoException();
        int i16 = 0;
        if (bool16)
          i16 = 1;
        paramParcel2.writeInt(i16);
        return true;
      case 5:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool15 = setMute(paramParcel1.readInt());
        paramParcel2.writeNoException();
        int i15 = 0;
        if (bool15)
          i15 = 1;
        paramParcel2.writeInt(i15);
        return true;
      case 6:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool14 = isMute();
        paramParcel2.writeNoException();
        int i14 = 0;
        if (bool14)
          i14 = 1;
        paramParcel2.writeInt(i14);
        return true;
      case 7:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool13 = seek(paramParcel1.readInt());
        paramParcel2.writeNoException();
        int i13 = 0;
        if (bool13)
          i13 = 1;
        paramParcel2.writeInt(i13);
        return true;
      case 8:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool12 = scan();
        paramParcel2.writeNoException();
        int i12 = 0;
        if (bool12)
          i12 = 1;
        paramParcel2.writeInt(i12);
        return true;
      case 9:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool11 = stopSeek();
        paramParcel2.writeNoException();
        int i11 = 0;
        if (bool11)
          i11 = 1;
        paramParcel2.writeInt(i11);
        return true;
      case 10:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool10 = stopScan();
        paramParcel2.writeNoException();
        int i10 = 0;
        if (bool10)
          i10 = 1;
        paramParcel2.writeInt(i10);
        return true;
      case 11:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool9 = setVolume(paramParcel1.readInt());
        paramParcel2.writeNoException();
        int i9 = 0;
        if (bool9)
          i9 = 1;
        paramParcel2.writeInt(i9);
        return true;
      case 12:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool8 = getVolume();
        paramParcel2.writeNoException();
        int i8 = 0;
        if (bool8)
          i8 = 1;
        paramParcel2.writeInt(i8);
        return true;
      case 13:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        int i7 = getBand();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i7);
        return true;
      case 14:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool7 = setBand(paramParcel1.readInt());
        paramParcel2.writeNoException();
        int i6 = 0;
        if (bool7)
          i6 = 1;
        paramParcel2.writeInt(i6);
        return true;
      case 15:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        int i5 = getMinFrequence();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i5);
        return true;
      case 16:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        int i4 = getMaxFrequence();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i4);
        return true;
      case 17:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        int i3 = getStepUnit();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i3);
        return true;
      case 18:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        registerCallback(IFMRadioServiceCallback.Stub.asInterface(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 19:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        unregisterCallback(IFMRadioServiceCallback.Stub.asInterface(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 20:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        if (paramParcel1.readInt() != 0);
        for (boolean bool5 = true; ; bool5 = false)
        {
          boolean bool6 = setRdsEnable(bool5, paramParcel1.readInt());
          paramParcel2.writeNoException();
          int i2 = 0;
          if (bool6)
            i2 = 1;
          paramParcel2.writeInt(i2);
          return true;
        }
      case 21:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool4 = isRdsEnable();
        paramParcel2.writeNoException();
        int i1 = 0;
        if (bool4)
          i1 = 1;
        paramParcel2.writeInt(i1);
        return true;
      case 22:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool3 = getAudioType();
        paramParcel2.writeNoException();
        int n = 0;
        if (bool3)
          n = 1;
        paramParcel2.writeInt(n);
        return true;
      case 23:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool2 = getRSSI();
        paramParcel2.writeNoException();
        int m = 0;
        if (bool2)
          m = 1;
        paramParcel2.writeInt(m);
        return true;
      case 24:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        String str4 = getRdsPS();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str4);
        return true;
      case 25:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        String str3 = getRdsRT();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str3);
        return true;
      case 26:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        String str2 = getRdsRTPLUS();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str2);
        return true;
      case 27:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        int k = getRdsPI();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(k);
        return true;
      case 28:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        int j = getRdsPTY();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(j);
        return true;
      case 29:
        paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
        boolean bool1 = setRSSI(paramParcel1.readInt());
        paramParcel2.writeNoException();
        int i = 0;
        if (bool1)
          i = 1;
        paramParcel2.writeInt(i);
        return true;
      case 30:
      }
      paramParcel1.enforceInterface("com.motorola.android.fmradio.IFMRadioService");
      String str1 = getRDSStationName();
      paramParcel2.writeNoException();
      paramParcel2.writeString(str1);
      return true;
    }

    private static class Proxy
      implements IFMRadioService
    {
      private IBinder mRemote;

      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }

      public IBinder asBinder()
      {
        return this.mRemote;
      }

      public boolean getAudioMode()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(4, localParcel1, localParcel2, 0);
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

      public boolean getAudioType()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(22, localParcel1, localParcel2, 0);
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

      public int getBand()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(13, localParcel1, localParcel2, 0);
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

      public boolean getCurrentFreq()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
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

      public String getInterfaceDescriptor()
      {
        return "com.motorola.android.fmradio.IFMRadioService";
      }

      public int getMaxFrequence()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(16, localParcel1, localParcel2, 0);
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

      public int getMinFrequence()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(15, localParcel1, localParcel2, 0);
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

      public String getRDSStationName()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
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

      public boolean getRSSI()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(23, localParcel1, localParcel2, 0);
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

      public int getRdsPI()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(27, localParcel1, localParcel2, 0);
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

      public String getRdsPS()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(24, localParcel1, localParcel2, 0);
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

      public int getRdsPTY()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(28, localParcel1, localParcel2, 0);
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

      public String getRdsRT()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
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

      public String getRdsRTPLUS()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(26, localParcel1, localParcel2, 0);
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

      public int getStepUnit()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(17, localParcel1, localParcel2, 0);
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

      public boolean getVolume()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(12, localParcel1, localParcel2, 0);
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

      public boolean isMute()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(6, localParcel1, localParcel2, 0);
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

      public boolean isRdsEnable()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
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

      public void registerCallback(IFMRadioServiceCallback paramIFMRadioServiceCallback)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          if (paramIFMRadioServiceCallback != null);
          for (IBinder localIBinder = paramIFMRadioServiceCallback.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.mRemote.transact(18, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public boolean scan()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(8, localParcel1, localParcel2, 0);
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

      public boolean seek(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(7, localParcel1, localParcel2, 0);
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

      public boolean setAudioMode(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(3, localParcel1, localParcel2, 0);
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

      public boolean setBand(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(14, localParcel1, localParcel2, 0);
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

      public boolean setMute(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
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

      public boolean setRSSI(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(29, localParcel1, localParcel2, 0);
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

      public boolean setRdsEnable(boolean paramBoolean, int paramInt)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          if (paramBoolean)
          {
            int j = i;
            localParcel1.writeInt(j);
            localParcel1.writeInt(paramInt);
            this.mRemote.transact(20, localParcel1, localParcel2, 0);
            localParcel2.readException();
            int m = localParcel2.readInt();
            if (m == 0)
              break label91;
          }
          while (true)
          {
            return i;
            int k = 0;
            break;
            label91: i = 0;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public boolean setVolume(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(11, localParcel1, localParcel2, 0);
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

      public boolean stopScan()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(10, localParcel1, localParcel2, 0);
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

      public boolean stopSeek()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          this.mRemote.transact(9, localParcel1, localParcel2, 0);
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

      public boolean tune(int paramInt)
        throws RemoteException
      {
        boolean bool = true;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0)
            return bool;
          bool = false;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void unregisterCallback(IFMRadioServiceCallback paramIFMRadioServiceCallback)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.motorola.android.fmradio.IFMRadioService");
          if (paramIFMRadioServiceCallback != null);
          for (IBinder localIBinder = paramIFMRadioServiceCallback.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.mRemote.transact(19, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
          }
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
 * Qualified Name:     com.motorola.android.fmradio.IFMRadioService
 * JD-Core Version:    0.6.2
 */