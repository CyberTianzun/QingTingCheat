package fm.qingting.qtradio.model;

import android.graphics.Bitmap;
import android.widget.ImageView;
import cn.com.mma.mobile.tracking.api.Countly;
import com.miaozhen.mzmonitor.MZMonitor;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.framework.utils.ImageLoader;
import fm.qingting.framework.utils.ImageLoaderHandler;
import fm.qingting.qtradio.ad.AdPos;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class AdvertisementItemNode extends Node
  implements ImageLoaderHandler
{
  public String audioPath;
  public String desc;
  public int duration;
  public long endTime;
  public int height;
  public String id;
  public String image;
  public int internal_catid;
  public int internal_channelid;
  public String internal_landing;
  public int interval;
  public int interval_channeltype;
  public int interval_programid;
  public boolean isSplash = false;
  public String landing;
  private final String logType = "ad_track_v6";
  private String mDescription;
  private transient Node mNode;
  private transient RecommendItemNode mRecommendItemNode;
  private String mTitle;
  public AdTrackers mTracker = new AdTrackers();
  public int resType;
  public String skin;
  public String splash_landing = null;
  public long startTime;
  public boolean useLocalWebview = false;
  public int width;

  public AdvertisementItemNode()
  {
    this.nodeName = "advertisementitem";
  }

  // ERROR //
  private void restoreImage(Bitmap paramBitmap, String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +28 -> 29
    //   4: aload_2
    //   5: ifnull +24 -> 29
    //   8: invokestatic 80	fm/qingting/qtradio/model/SharedCfg:getInstance	()Lfm/qingting/qtradio/model/SharedCfg;
    //   11: aload_2
    //   12: invokevirtual 84	fm/qingting/qtradio/model/SharedCfg:getAdvertisementImage	(Ljava/lang/String;)Ljava/lang/String;
    //   15: astore_3
    //   16: aload_3
    //   17: ifnull +13 -> 30
    //   20: aload_3
    //   21: ldc 86
    //   23: invokevirtual 92	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   26: ifne +4 -> 30
    //   29: return
    //   30: invokestatic 97	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   33: invokevirtual 101	fm/qingting/qtradio/model/InfoManager:getContext	()Landroid/content/Context;
    //   36: invokevirtual 106	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   39: invokevirtual 110	android/content/Context:getFilesDir	()Ljava/io/File;
    //   42: invokevirtual 116	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   45: astore 4
    //   47: new 118	java/lang/StringBuilder
    //   50: dup
    //   51: invokespecial 119	java/lang/StringBuilder:<init>	()V
    //   54: aload 4
    //   56: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: ldc 125
    //   61: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: invokevirtual 128	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   67: astore 5
    //   69: new 112	java/io/File
    //   72: dup
    //   73: aload 5
    //   75: invokespecial 131	java/io/File:<init>	(Ljava/lang/String;)V
    //   78: astore 6
    //   80: aload 6
    //   82: invokevirtual 135	java/io/File:createNewFile	()Z
    //   85: pop
    //   86: new 137	java/io/FileOutputStream
    //   89: dup
    //   90: aload 6
    //   92: invokespecial 140	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   95: astore 10
    //   97: aload_1
    //   98: getstatic 146	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   101: bipush 50
    //   103: aload 10
    //   105: invokevirtual 152	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   108: pop
    //   109: aload 10
    //   111: invokevirtual 155	java/io/FileOutputStream:flush	()V
    //   114: aload 10
    //   116: invokevirtual 158	java/io/FileOutputStream:close	()V
    //   119: invokestatic 80	fm/qingting/qtradio/model/SharedCfg:getInstance	()Lfm/qingting/qtradio/model/SharedCfg;
    //   122: aload_2
    //   123: aload 5
    //   125: invokevirtual 162	fm/qingting/qtradio/model/SharedCfg:setAdvertisementImage	(Ljava/lang/String;Ljava/lang/String;)V
    //   128: aload_0
    //   129: getfield 52	fm/qingting/qtradio/model/AdvertisementItemNode:isSplash	Z
    //   132: ifeq -103 -> 29
    //   135: invokestatic 80	fm/qingting/qtradio/model/SharedCfg:getInstance	()Lfm/qingting/qtradio/model/SharedCfg;
    //   138: aload 5
    //   140: invokevirtual 165	fm/qingting/qtradio/model/SharedCfg:setLocalAdvertisementPic	(Ljava/lang/String;)V
    //   143: return
    //   144: astore 7
    //   146: aload 7
    //   148: invokevirtual 168	java/io/IOException:printStackTrace	()V
    //   151: return
    //   152: astore 14
    //   154: aload 14
    //   156: invokevirtual 169	java/io/FileNotFoundException:printStackTrace	()V
    //   159: return
    //   160: astore 12
    //   162: aload 12
    //   164: invokevirtual 168	java/io/IOException:printStackTrace	()V
    //   167: return
    //   168: astore 13
    //   170: aload 13
    //   172: invokevirtual 168	java/io/IOException:printStackTrace	()V
    //   175: return
    //   176: astore 8
    //   178: return
    //
    // Exception table:
    //   from	to	target	type
    //   80	86	144	java/io/IOException
    //   86	97	152	java/io/FileNotFoundException
    //   109	114	160	java/io/IOException
    //   114	119	168	java/io/IOException
    //   69	80	176	java/lang/Exception
    //   80	86	176	java/lang/Exception
    //   86	97	176	java/lang/Exception
    //   97	109	176	java/lang/Exception
    //   109	114	176	java/lang/Exception
    //   114	119	176	java/lang/Exception
    //   119	143	176	java/lang/Exception
    //   146	151	176	java/lang/Exception
    //   154	159	176	java/lang/Exception
    //   162	167	176	java/lang/Exception
    //   170	175	176	java/lang/Exception
  }

  private void sendLog(String paramString1, String paramString2)
  {
    String str1 = QTLogger.getInstance().buildCommonLog();
    String str2;
    AdPos localAdPos;
    label82: String str7;
    ChannelNode localChannelNode;
    String str10;
    if (str1 != null)
    {
      StringBuilder localStringBuilder = new StringBuilder().append(str1).append("\"");
      if (paramString1 != "click")
        break label406;
      str2 = "2";
      String str3 = str2 + "\"";
      if ((this.parent == null) || (!(this.parent instanceof AdPos)))
        break label413;
      localAdPos = (AdPos)this.parent;
      String str4 = str3 + ",\"" + localAdPos.adtype + "\"";
      String str5 = str4 + ",\"" + this.id + "\"";
      String str6 = str5 + ",\"" + localAdPos.posdesc + "\"";
      str7 = str6 + ",\"" + localAdPos.posid + "\"";
      Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("program")))
        break label445;
      str8 = str7 + ",\"" + ((ProgramNode)localNode).id + "\"";
      localChannelNode = InfoManager.getInstance().root().getCurrentPlayingChannelNode();
      if (localChannelNode != null)
        str10 = str8 + ",\"" + localChannelNode.channelId;
    }
    label406: label413: label445: for (String str8 = str10 + ",\"" + localChannelNode.categoryId; ; str8 = str7 + ",\"\",\"\",\"\"")
    {
      String str9 = str8 + ",\"" + paramString2 + "\"";
      LogModule.getInstance().send("ad_track_v6", str9);
      return;
      str2 = "1";
      break;
      localAdPos = new AdPos();
      localAdPos.adtype = 0;
      localAdPos.posdesc = "";
      localAdPos.posid = "";
      break label82;
    }
  }

  public RecommendItemNode convertToRecommendItem(int paramInt)
  {
    if (this.mRecommendItemNode != null)
      return this.mRecommendItemNode;
    this.mRecommendItemNode = new RecommendItemNode();
    this.mRecommendItemNode.id = this.id;
    this.mRecommendItemNode.isAds = true;
    this.mRecommendItemNode.mAdNode = this;
    this.mRecommendItemNode.name = this.desc;
    this.mRecommendItemNode.briefName = "推广";
    this.mRecommendItemNode.desc = this.desc;
    this.mRecommendItemNode.setSmallThumb(this.image);
    this.mRecommendItemNode.setMediumThumb(null);
    this.mRecommendItemNode.setLargeThumb(null);
    this.mRecommendItemNode.isweb = true;
    this.mRecommendItemNode.categoryPos = 0;
    this.mRecommendItemNode.mCategoryId = paramInt;
    ProgramNode localProgramNode;
    if ((this.internal_landing != null) && (!this.internal_landing.equalsIgnoreCase("")))
    {
      localProgramNode = new ProgramNode();
      localProgramNode.uniqueId = this.interval_programid;
      localProgramNode.id = localProgramNode.uniqueId;
      localProgramNode.channelId = this.internal_channelid;
      localProgramNode.title = this.desc;
      localProgramNode.channelType = this.interval_channeltype;
      this.mRecommendItemNode.mCategoryId = this.internal_catid;
    }
    ActivityNode localActivityNode;
    for (this.mRecommendItemNode.mNode = localProgramNode; ; this.mRecommendItemNode.mNode = localActivityNode)
    {
      return this.mRecommendItemNode;
      localActivityNode = new ActivityNode();
      localActivityNode.id = 1;
      localActivityNode.name = this.desc;
      localActivityNode.type = "1";
      localActivityNode.updatetime = 25200;
      localActivityNode.infoUrl = this.image;
      localActivityNode.infoTitle = this.desc;
      localActivityNode.desc = this.desc;
      localActivityNode.titleIconUrl = null;
      localActivityNode.network = null;
      localActivityNode.putUserInfo = false;
      localActivityNode.contentUrl = this.landing;
      localActivityNode.useLocalWebview = this.useLocalWebview;
    }
  }

  public Node getAdvNode()
  {
    if (this.mNode != null)
      return this.mNode;
    ProgramNode localProgramNode;
    if ((this.internal_landing != null) && (!this.internal_landing.equalsIgnoreCase("")))
    {
      localProgramNode = new ProgramNode();
      localProgramNode.uniqueId = this.interval_programid;
      localProgramNode.id = localProgramNode.uniqueId;
      localProgramNode.channelId = this.internal_channelid;
      localProgramNode.title = this.desc;
      localProgramNode.channelType = this.interval_channeltype;
    }
    ActivityNode localActivityNode;
    for (this.mNode = localProgramNode; ; this.mNode = localActivityNode)
    {
      return this.mNode;
      localActivityNode = new ActivityNode();
      localActivityNode.id = 1;
      localActivityNode.name = this.desc;
      localActivityNode.type = "1";
      localActivityNode.updatetime = 25200;
      localActivityNode.infoUrl = this.image;
      localActivityNode.infoTitle = this.desc;
      localActivityNode.desc = this.desc;
      localActivityNode.titleIconUrl = null;
      localActivityNode.network = null;
      localActivityNode.putUserInfo = false;
      localActivityNode.contentUrl = this.landing;
      localActivityNode.useLocalWebview = this.useLocalWebview;
    }
  }

  public String getDescription()
  {
    if (this.mDescription == null)
      this.mDescription = "";
    return this.mDescription;
  }

  public Bitmap getImage()
  {
    if ((this.image == null) || (this.image.equalsIgnoreCase("")))
      return null;
    if ((this.parent != null) && (((AdPos)this.parent).isSplash()))
    {
      this.isSplash = true;
      String str1 = SharedCfg.getInstance().getNetAdvertisementPic();
      if (str1 != null)
      {
        if (str1.equalsIgnoreCase(this.image))
          break label154;
        SharedCfg.getInstance().setLocalAdvertisementPic("");
      }
    }
    label154: String str2;
    do
    {
      SharedCfg.getInstance().setNetAdvertisementPic(this.image);
      SharedCfg.getInstance().setSplashLanding(this.splash_landing);
      Bitmap localBitmap = ImageLoader.getInstance(InfoManager.getInstance().getContext()).getImage(this.image, this.width, this.height);
      if (localBitmap == null)
        ImageLoader.getInstance(InfoManager.getInstance().getContext()).loadImage(this.image, null, this.width, this.height, false, this);
      return localBitmap;
      str2 = SharedCfg.getInstance().getLocalAdvertisementPic();
    }
    while ((str2 == null) || (str2.equalsIgnoreCase("")));
    return null;
  }

  public int getInternalLandingCatid()
  {
    return this.internal_catid;
  }

  public String getTitle()
  {
    if (this.mTitle == null)
      this.mTitle = "";
    return this.mTitle;
  }

  public boolean isSplashLanding()
  {
    return (this.isSplash) && (this.splash_landing != null) && (!this.splash_landing.equalsIgnoreCase(""));
  }

  public void loadImageFinish(boolean paramBoolean, String paramString, Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    if ((paramBitmap != null) && (paramString != null) && (this.image != null) && (paramString.equalsIgnoreCase(this.image)))
      restoreImage(paramBitmap, paramString);
  }

  public void onClick()
  {
    sendLog("click", this.mTracker.trackClick(true));
  }

  public void onShow()
  {
    sendLog("display", this.mTracker.trackDisplay(true));
  }

  public void setDescription(String paramString)
  {
    this.mDescription = paramString;
  }

  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
  }

  public void trackAdMaster()
  {
    String str = SharedCfg.getInstance().getAdMasterUrl();
    if ((str != null) && (!str.equalsIgnoreCase("")))
      Countly.sharedInstance().onExpose(str);
  }

  public void updateImageViewFinish(boolean paramBoolean, ImageView paramImageView, String paramString, Bitmap paramBitmap)
  {
  }

  public void updateTracker()
  {
    this.mTracker.update();
  }

  public void zeusClick()
  {
    this.mTracker.trackClick(false);
  }

  public void zeusShow()
  {
    this.mTracker.trackDisplay(false);
  }

  public class AdTrackers
  {
    public List<String> lstEventType;
    public List<String> lstProvider;
    public List<String> lstTrackerUrl;

    public AdTrackers()
    {
    }

    // ERROR //
    private String macroReplace(String paramString)
    {
      // Byte code:
      //   0: invokestatic 45	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
      //   3: invokevirtual 49	fm/qingting/qtradio/model/InfoManager:getCurrentLocation	()Lfm/qingting/qtradio/model/QTLocation;
      //   6: ifnull +105 -> 111
      //   9: invokestatic 45	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
      //   12: invokevirtual 49	fm/qingting/qtradio/model/InfoManager:getCurrentLocation	()Lfm/qingting/qtradio/model/QTLocation;
      //   15: getfield 55	fm/qingting/qtradio/model/QTLocation:ip	Ljava/lang/String;
      //   18: astore_3
      //   19: ldc 57
      //   21: ldc 59
      //   23: invokestatic 65	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   26: astore 10
      //   28: aload 10
      //   30: astore 5
      //   32: ldc 67
      //   34: astore 6
      //   36: invokestatic 45	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
      //   39: invokevirtual 71	fm/qingting/qtradio/model/InfoManager:getContext	()Landroid/content/Context;
      //   42: invokestatic 77	fm/qingting/utils/DeviceInfo:getDeviceIMEI	(Landroid/content/Context;)Ljava/lang/String;
      //   45: astore 9
      //   47: aload 9
      //   49: astore 6
      //   51: aload_1
      //   52: ifnull +65 -> 117
      //   55: aload_1
      //   56: ldc 67
      //   58: invokevirtual 83	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
      //   61: ifeq +5 -> 66
      //   64: aload_1
      //   65: areturn
      //   66: aload_1
      //   67: ldc 85
      //   69: ldc 87
      //   71: invokevirtual 91	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      //   74: ldc 93
      //   76: aload_3
      //   77: invokevirtual 91	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      //   80: ldc 95
      //   82: aload 5
      //   84: invokevirtual 91	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      //   87: ldc 97
      //   89: aload_0
      //   90: aload 6
      //   92: invokespecial 100	fm/qingting/qtradio/model/AdvertisementItemNode$AdTrackers:md5	(Ljava/lang/String;)Ljava/lang/String;
      //   95: invokevirtual 91	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
      //   98: astore 8
      //   100: aload 8
      //   102: areturn
      //   103: astore_2
      //   104: aload_1
      //   105: areturn
      //   106: astore 7
      //   108: goto -57 -> 51
      //   111: ldc 67
      //   113: astore_3
      //   114: goto -95 -> 19
      //   117: aload_1
      //   118: areturn
      //   119: astore 4
      //   121: ldc 57
      //   123: astore 5
      //   125: goto -93 -> 32
      //
      // Exception table:
      //   from	to	target	type
      //   0	19	103	java/lang/Exception
      //   19	28	103	java/lang/Exception
      //   55	64	103	java/lang/Exception
      //   66	100	103	java/lang/Exception
      //   36	47	106	java/lang/Exception
      //   19	28	119	java/io/UnsupportedEncodingException
    }

    private String md5(String paramString)
    {
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(paramString.getBytes());
        byte[] arrayOfByte = localMessageDigest.digest();
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 0; i < arrayOfByte.length; i++)
          localStringBuffer.append(Integer.toHexString(0xFF & arrayOfByte[i]));
        String str = localStringBuffer.toString();
        return str;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        localNoSuchAlgorithmException.printStackTrace();
      }
      return "";
    }

    private String trackClick(boolean paramBoolean)
    {
      Object localObject1 = "";
      int i;
      if ((this.lstProvider != null) && (this.lstTrackerUrl != null))
      {
        i = 0;
        if ((i < this.lstEventType.size()) && (i < this.lstEventType.size()) && (i < this.lstTrackerUrl.size()));
      }
      else
      {
        return localObject1;
      }
      if (!paramBoolean);
      for (String str1 = "ZeusTracker"; ; str1 = "RealTracker")
      {
        String str2 = (String)this.lstProvider.get(i);
        String str3 = (String)this.lstEventType.get(i);
        String str4 = (String)this.lstTrackerUrl.get(i);
        String str6;
        label159: Object localObject2;
        if (str3.equalsIgnoreCase("click"))
          if (str2.equalsIgnoreCase("miaozhen"))
          {
            StringBuilder localStringBuilder2 = new StringBuilder().append((String)localObject1);
            if (((String)localObject1).equals(""))
            {
              str6 = "1";
              localObject2 = str6;
              String str7 = macroReplace(str4);
              MZMonitor.eventTrack(InfoManager.getInstance().getContext(), str7, str3);
              MobclickAgent.onEvent(InfoManager.getInstance().getContext(), str1, "mzclick:" + str7);
            }
          }
        while (true)
        {
          i++;
          localObject1 = localObject2;
          break;
          str6 = "|1";
          break label159;
          if (str2.equalsIgnoreCase("AdMaster"))
          {
            StringBuilder localStringBuilder1 = new StringBuilder().append((String)localObject1);
            if (((String)localObject1).equals(""));
            for (String str5 = "2"; ; str5 = "|2")
            {
              localObject2 = str5;
              Countly.sharedInstance().onClick(str4);
              MobclickAgent.onEvent(InfoManager.getInstance().getContext(), str1, "adclick:" + str4);
              break;
            }
          }
          localObject2 = localObject1;
        }
      }
    }

    private String trackDisplay(boolean paramBoolean)
    {
      Object localObject1 = "";
      if ((this.lstProvider != null) && (this.lstTrackerUrl != null))
        if (paramBoolean)
          break label359;
      label175: label359: for (String str1 = "ZeusTracker"; ; str1 = "RealTracker")
      {
        int i = 0;
        if ((i >= this.lstProvider.size()) || (i >= this.lstEventType.size()) || (i >= this.lstTrackerUrl.size()))
          return localObject1;
        String str2 = (String)this.lstProvider.get(i);
        String str3 = (String)this.lstEventType.get(i);
        String str4 = (String)this.lstTrackerUrl.get(i);
        String str7;
        Object localObject2;
        if ((str3 != null) && (str3.equalsIgnoreCase("display")))
          if ((str2 != null) && (str2.equalsIgnoreCase("AdMaster")))
          {
            StringBuilder localStringBuilder2 = new StringBuilder().append((String)localObject1);
            if (((String)localObject1).equals(""))
            {
              str7 = "2";
              localObject2 = str7;
              Countly.sharedInstance().onExpose(str4);
              MobclickAgent.onEvent(InfoManager.getInstance().getContext(), str1, "addisplay:" + str4);
            }
          }
        while (true)
        {
          i++;
          localObject1 = localObject2;
          break;
          str7 = "|2";
          break label175;
          if ((str2 != null) && (str2.equalsIgnoreCase("miaozhen")))
          {
            StringBuilder localStringBuilder1 = new StringBuilder().append((String)localObject1);
            if (((String)localObject1).equals(""));
            for (String str5 = "1"; ; str5 = "|1")
            {
              localObject2 = str5;
              String str6 = macroReplace(str4);
              MZMonitor.adTrack(InfoManager.getInstance().getContext(), str6);
              MobclickAgent.onEvent(InfoManager.getInstance().getContext(), str1, "mzdisplay:" + str6);
              break;
            }
          }
          localObject2 = localObject1;
        }
      }
    }

    private void update()
    {
      if ((this.lstProvider != null) && (this.lstTrackerUrl != null))
        SharedCfg.getInstance().setAdMasterUrl(null);
      for (int i = 0; ; i++)
        if (i < this.lstProvider.size())
        {
          if ((((String)this.lstProvider.get(i)).equalsIgnoreCase("AdMaster")) && (i < this.lstTrackerUrl.size()))
            SharedCfg.getInstance().setAdMasterUrl((String)this.lstTrackerUrl.get(i));
        }
        else
          return;
    }
  }

  class TrackType
  {
    public static final String click = "click";
    public static final String display = "display";

    TrackType()
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.AdvertisementItemNode
 * JD-Core Version:    0.6.2
 */