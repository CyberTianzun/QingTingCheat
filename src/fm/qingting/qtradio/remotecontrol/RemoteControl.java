package fm.qingting.qtradio.remotecontrol;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.OnGetPlaybackPositionListener;
import android.media.RemoteControlClient.OnPlaybackPositionUpdateListener;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.headset.MediaButtonReceiver;
import fm.qingting.qtradio.manager.QtApiLevelManager;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.utils.RemoteBitmapLoader;
import fm.qingting.utils.RemoteBitmapLoader.IBitmapRecvListener;

public class RemoteControl
  implements RemoteBitmapLoader.IBitmapRecvListener
{
  private static RemoteControl instance;
  private ChannelNode mChannel;
  private Context mContext;
  private String mCurThumbUrl;
  private ProgramNode mProgram;
  private RemoteControlClient mRemoteControlClient;

  public static RemoteControl getInstance()
  {
    if (instance == null)
      instance = new RemoteControl();
    return instance;
  }

  private Bitmap resizeBmp(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    return Bitmap.createScaledBitmap(paramBitmap, paramInt1, paramInt2, false);
  }

  @TargetApi(14)
  private void setTransportControlFlags()
  {
    if (QtApiLevelManager.isApiLevelSupported(14))
    {
      int i = 157;
      if (QtApiLevelManager.isApiLevelSupported(19))
        i = turnOnMediaRatingFlag(i);
      this.mRemoteControlClient.setTransportControlFlags(i);
    }
  }

  @SuppressLint({"NewApi"})
  private void setupRemoteControl()
  {
    if (QtApiLevelManager.isApiLevelSupported(18))
    {
      this.mRemoteControlClient.setOnGetPlaybackPositionListener(new RemoteControlClient.OnGetPlaybackPositionListener()
      {
        public long onGetPlaybackPosition()
        {
          return 1000 * PlayerAgent.getInstance().queryPosition();
        }
      });
      this.mRemoteControlClient.setPlaybackPositionUpdateListener(new RemoteControlClient.OnPlaybackPositionUpdateListener()
      {
        public void onPlaybackPositionUpdate(long paramAnonymousLong)
        {
          PlayerAgent.getInstance().seekPosition((int)paramAnonymousLong / 1000);
        }
      });
    }
    setTransportControlFlags();
  }

  @TargetApi(19)
  private int turnOnMediaRatingFlag(int paramInt)
  {
    return paramInt | 0x200;
  }

  // ERROR //
  @SuppressLint({"InlinedApi", "NewApi"})
  public void onRecvBitmap(String paramString, Bitmap paramBitmap)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +18 -> 19
    //   4: aload_1
    //   5: aload_0
    //   6: getfield 85	fm/qingting/qtradio/remotecontrol/RemoteControl:mCurThumbUrl	Ljava/lang/String;
    //   9: invokevirtual 91	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   12: ifeq +7 -> 19
    //   15: aload_2
    //   16: ifnonnull +4 -> 20
    //   19: return
    //   20: aload_0
    //   21: getfield 51	fm/qingting/qtradio/remotecontrol/RemoteControl:mRemoteControlClient	Landroid/media/RemoteControlClient;
    //   24: iconst_1
    //   25: invokevirtual 95	android/media/RemoteControlClient:editMetadata	(Z)Landroid/media/RemoteControlClient$MetadataEditor;
    //   28: astore 4
    //   30: aload 4
    //   32: bipush 7
    //   34: aload_0
    //   35: getfield 97	fm/qingting/qtradio/remotecontrol/RemoteControl:mProgram	Lfm/qingting/qtradio/model/ProgramNode;
    //   38: getfield 102	fm/qingting/qtradio/model/ProgramNode:title	Ljava/lang/String;
    //   41: invokevirtual 108	android/media/RemoteControlClient$MetadataEditor:putString	(ILjava/lang/String;)Landroid/media/RemoteControlClient$MetadataEditor;
    //   44: pop
    //   45: aload 4
    //   47: iconst_1
    //   48: aload_0
    //   49: getfield 97	fm/qingting/qtradio/remotecontrol/RemoteControl:mProgram	Lfm/qingting/qtradio/model/ProgramNode;
    //   52: invokevirtual 112	fm/qingting/qtradio/model/ProgramNode:getChannelName	()Ljava/lang/String;
    //   55: invokevirtual 108	android/media/RemoteControlClient$MetadataEditor:putString	(ILjava/lang/String;)Landroid/media/RemoteControlClient$MetadataEditor;
    //   58: pop
    //   59: aload_0
    //   60: getfield 97	fm/qingting/qtradio/remotecontrol/RemoteControl:mProgram	Lfm/qingting/qtradio/model/ProgramNode;
    //   63: invokevirtual 115	fm/qingting/qtradio/model/ProgramNode:getBroadCasterNames	()Ljava/lang/String;
    //   66: astore 7
    //   68: aload_0
    //   69: getfield 117	fm/qingting/qtradio/remotecontrol/RemoteControl:mChannel	Lfm/qingting/qtradio/model/ChannelNode;
    //   72: ifnull +128 -> 200
    //   75: aload_0
    //   76: getfield 117	fm/qingting/qtradio/remotecontrol/RemoteControl:mChannel	Lfm/qingting/qtradio/model/ChannelNode;
    //   79: invokevirtual 122	fm/qingting/qtradio/model/ChannelNode:getAuthorNames	()Ljava/lang/String;
    //   82: astore 8
    //   84: aload 8
    //   86: ifnull +114 -> 200
    //   89: aload 8
    //   91: ldc 124
    //   93: invokevirtual 91	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   96: ifne +104 -> 200
    //   99: aload 4
    //   101: iconst_2
    //   102: aload 8
    //   104: invokevirtual 108	android/media/RemoteControlClient$MetadataEditor:putString	(ILjava/lang/String;)Landroid/media/RemoteControlClient$MetadataEditor;
    //   107: pop
    //   108: aload 4
    //   110: bipush 9
    //   112: aload_0
    //   113: getfield 97	fm/qingting/qtradio/remotecontrol/RemoteControl:mProgram	Lfm/qingting/qtradio/model/ProgramNode;
    //   116: invokevirtual 128	fm/qingting/qtradio/model/ProgramNode:getDuration	()I
    //   119: i2l
    //   120: invokevirtual 132	android/media/RemoteControlClient$MetadataEditor:putLong	(IJ)Landroid/media/RemoteControlClient$MetadataEditor;
    //   123: pop
    //   124: aload 4
    //   126: iconst_0
    //   127: aload_0
    //   128: getfield 97	fm/qingting/qtradio/remotecontrol/RemoteControl:mProgram	Lfm/qingting/qtradio/model/ProgramNode;
    //   131: getfield 136	fm/qingting/qtradio/model/ProgramNode:sequence	I
    //   134: i2l
    //   135: invokevirtual 132	android/media/RemoteControlClient$MetadataEditor:putLong	(IJ)Landroid/media/RemoteControlClient$MetadataEditor;
    //   138: pop
    //   139: aload_0
    //   140: getfield 117	fm/qingting/qtradio/remotecontrol/RemoteControl:mChannel	Lfm/qingting/qtradio/model/ChannelNode;
    //   143: invokevirtual 140	fm/qingting/qtradio/model/ChannelNode:getAllLstProgramNode	()Ljava/util/List;
    //   146: invokeinterface 145 1 0
    //   151: istore 12
    //   153: iload 12
    //   155: i2l
    //   156: lstore 13
    //   158: aload 4
    //   160: bipush 10
    //   162: lload 13
    //   164: invokevirtual 132	android/media/RemoteControlClient$MetadataEditor:putLong	(IJ)Landroid/media/RemoteControlClient$MetadataEditor;
    //   167: pop
    //   168: aload 4
    //   170: bipush 100
    //   172: aload_0
    //   173: aload_2
    //   174: sipush 630
    //   177: sipush 630
    //   180: invokespecial 147	fm/qingting/qtradio/remotecontrol/RemoteControl:resizeBmp	(Landroid/graphics/Bitmap;II)Landroid/graphics/Bitmap;
    //   183: invokevirtual 151	android/media/RemoteControlClient$MetadataEditor:putBitmap	(ILandroid/graphics/Bitmap;)Landroid/media/RemoteControlClient$MetadataEditor;
    //   186: pop
    //   187: aload 4
    //   189: invokevirtual 154	android/media/RemoteControlClient$MetadataEditor:apply	()V
    //   192: return
    //   193: astore_3
    //   194: return
    //   195: astore 15
    //   197: goto -29 -> 168
    //   200: aload 7
    //   202: astore 8
    //   204: goto -105 -> 99
    //
    // Exception table:
    //   from	to	target	type
    //   20	84	193	java/lang/Exception
    //   89	99	193	java/lang/Exception
    //   99	153	193	java/lang/Exception
    //   168	192	193	java/lang/Exception
    //   158	168	195	java/lang/Exception
  }

  @SuppressLint({"NewApi"})
  public void registerRemoteControl(Context paramContext)
  {
    if (QtApiLevelManager.isApiLevelSupported(14))
    {
      ComponentName localComponentName = new ComponentName(paramContext.getPackageName(), MediaButtonReceiver.class.getName());
      Intent localIntent = new Intent("android.intent.action.MEDIA_BUTTON");
      localIntent.setComponent(localComponentName);
      this.mRemoteControlClient = new RemoteControlClient(PendingIntent.getBroadcast(paramContext.getApplicationContext(), 0, localIntent, 0));
      ((AudioManager)paramContext.getSystemService("audio")).registerRemoteControlClient(this.mRemoteControlClient);
      setupRemoteControl();
    }
  }

  @SuppressLint({"NewApi"})
  public void unregisterRemoteControl(Context paramContext)
  {
    if (QtApiLevelManager.isApiLevelSupported(14))
      ((AudioManager)paramContext.getSystemService("audio")).unregisterRemoteControlClient(this.mRemoteControlClient);
  }

  public void updateProgramInfo(Context paramContext, ChannelNode paramChannelNode, ProgramNode paramProgramNode)
  {
    if (QtApiLevelManager.isApiLevelSupported(14))
    {
      this.mProgram = paramProgramNode;
      this.mChannel = paramChannelNode;
      this.mContext = paramContext;
      if ((this.mContext != null) && (this.mChannel != null) && (this.mProgram != null))
        break label45;
    }
    label45: 
    do
    {
      return;
      this.mCurThumbUrl = this.mChannel.getApproximativeThumb(480, 480, true);
    }
    while (this.mCurThumbUrl == null);
    RemoteBitmapLoader localRemoteBitmapLoader = new RemoteBitmapLoader(this.mCurThumbUrl);
    localRemoteBitmapLoader.setBitmapListener(this);
    localRemoteBitmapLoader.execute(new Object[0]);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.remotecontrol.RemoteControl
 * JD-Core Version:    0.6.2
 */