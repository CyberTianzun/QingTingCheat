package fm.qingting.qtradio.model;

import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;
import fm.qingting.downloadnew.DownloadListener;
import fm.qingting.downloadnew.DownloadService;
import fm.qingting.downloadnew.DownloadState;
import fm.qingting.downloadnew.DownloadTask;
import fm.qingting.downloadnew.DownloadUtils;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.QTApplication;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.utils.LifeTime;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.TimeUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DownLoadInfoNode extends Node
  implements DownloadListener
{
  public static final int EXCEED_DURATION = 1;
  public static final int HAS_EXISTED = 2;
  private static final String SUFFIX = ".cache";
  public static int allVoiceId = 82;
  public static int legacyId;
  public static int mDownloadId = 71;
  private String directory;
  private String downloadDat = "download.dat";
  private transient boolean hasRestoreDownloading = false;
  private boolean hasRestored = false;
  private List<Node> lstDownLoadedRings = new ArrayList();
  private List<Node> lstDownLoadingNodes = new ArrayList();
  private CategoryNode mCategory;
  private boolean mNeedSync = false;
  private int mTotalProgramCnt = 0;
  private long mTotalProgramSize = 0L;
  private Map<Integer, ChannelNode> mapChannelNodes = new HashMap();
  private Map<String, String> mapMetaInfo = new HashMap();
  private List<IDownloadInfoEventListener> mlstDLEventListeners = new ArrayList();
  private int refuseErrorCode = 0;

  static
  {
    legacyId = 81;
  }

  public DownLoadInfoNode()
  {
    this.nodeName = "downloadinfo";
    buildCategory();
  }

  private void addChannelNodeToCategory(ChannelNode paramChannelNode)
  {
    if ((paramChannelNode == null) || (this.mCategory == null));
    while (this.mCategory.mLstChannelNodes == null)
      return;
    paramChannelNode.parent = this.mCategory;
    this.mCategory.mLstChannelNodes.add(paramChannelNode);
    this.mapChannelNodes.put(Integer.valueOf(paramChannelNode.channelId), paramChannelNode);
  }

  private void attachProgram(ProgramNode paramProgramNode)
  {
    if (paramProgramNode == null)
      return;
    ChannelNode localChannelNode1 = (ChannelNode)this.mapChannelNodes.get(Integer.valueOf(paramProgramNode.channelId));
    if (localChannelNode1 != null)
      if (localChannelNode1.addDownloadProgramNode(paramProgramNode))
        localChannelNode1.programCnt = (1 + localChannelNode1.programCnt);
    label150: 
    while (true)
    {
      this.mTotalProgramCnt = (1 + this.mTotalProgramCnt);
      this.mTotalProgramSize += paramProgramNode.downloadInfo.fileSize;
      return;
      ChannelNode localChannelNode2;
      if (paramProgramNode.channelId == 360)
      {
        localChannelNode2 = buildChannelNode("电脑下载", 360);
        if (localChannelNode2 != null)
          addChannelNodeToCategory(localChannelNode2);
      }
      while (true)
      {
        if (localChannelNode2 == null)
          break label150;
        localChannelNode2.addDownloadProgramNode(paramProgramNode);
        localChannelNode2.programCnt = (1 + localChannelNode2.programCnt);
        break;
        localChannelNode2 = buildChannelNode(paramProgramNode.getChannelName(), paramProgramNode.channelId);
        if (localChannelNode2 != null)
          addChannelNodeToCategory(localChannelNode2);
      }
    }
  }

  private void buildCategory()
  {
    if (this.mCategory == null)
    {
      this.mCategory = new CategoryNode();
      this.mCategory.parentId = mDownloadId;
      this.mCategory.categoryId = mDownloadId;
      this.mCategory.categoryId = this.mCategory.categoryId;
      this.mCategory.name = "我的下载";
      this.mCategory.type = 2;
      this.mCategory.hasChild = 0;
      this.mCategory.forbidUseDB();
      ChannelNode localChannelNode = buildLegacyChannelNode();
      localChannelNode.parent = this.mCategory;
      this.mCategory.mLstChannelNodes = new ArrayList();
      this.mCategory.mLstChannelNodes.add(localChannelNode);
      this.mapChannelNodes.put(Integer.valueOf(localChannelNode.channelId), localChannelNode);
    }
  }

  private ChannelNode buildChannelNode(String paramString, int paramInt)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return null;
    ChannelNode localChannelNode = new ChannelNode();
    localChannelNode.channelId = paramInt;
    localChannelNode.parent = this.mCategory;
    localChannelNode.categoryId = this.mCategory.categoryId;
    localChannelNode.title = paramString;
    localChannelNode.channelType = 1;
    return localChannelNode;
  }

  private String buildFileNameByNode(Node paramNode, long paramLong)
  {
    String str1;
    if (paramNode == null)
      str1 = null;
    label486: 
    do
    {
      return str1;
      str1 = "";
      if (paramNode.nodeName.equalsIgnoreCase("program"))
      {
        ProgramNode localProgramNode = (ProgramNode)paramNode;
        String str18 = str1 + "o";
        String str19 = str18 + "_";
        String str21;
        String str23;
        String str34;
        if ((localProgramNode.parent != null) && (localProgramNode.parent.nodeName.equalsIgnoreCase("channel")))
        {
          String str35 = str19 + ((ChannelNode)localProgramNode.parent).channelId;
          str21 = str35 + "_";
          String str22 = str21 + localProgramNode.id;
          str23 = str22 + "_";
          if (!localProgramNode.title.contains("_"))
            break label486;
          str34 = localProgramNode.title.replace('_', '.');
        }
        String str29;
        for (String str24 = str23 + str34; ; str24 = str23 + localProgramNode.title)
        {
          String str25 = str24 + "_";
          String str26 = str25 + localProgramNode.getDuration();
          String str27 = str26 + "_";
          String str28 = str27 + String.valueOf(paramLong);
          str29 = str28 + "_";
          if (localProgramNode.downloadInfo != null)
            break label514;
          String str32 = str29 + String.valueOf(2);
          String str33 = str32 + "_";
          return str33 + "0";
          String str20 = str19 + "0";
          str21 = str20 + "_";
          break;
        }
        String str30 = str29 + String.valueOf(localProgramNode.downloadInfo.state);
        String str31 = str30 + "_";
        return str31 + String.valueOf(localProgramNode.downloadInfo.progress);
      }
    }
    while (!paramNode.nodeName.equalsIgnoreCase("ringtone"));
    label514: RingToneNode localRingToneNode = (RingToneNode)paramNode;
    String str2 = str1 + "r";
    String str3 = str2 + "_";
    String str4 = str3 + localRingToneNode.ringToneAlbumId;
    String str5 = str4 + "_";
    String str6 = str5 + localRingToneNode.ringToneId;
    String str7 = str6 + "_";
    String str8 = str7 + localRingToneNode.title;
    String str9 = str8 + "_";
    String str10 = str9 + localRingToneNode.duration;
    String str11 = str10 + "_";
    String str12 = str11 + String.valueOf(paramLong);
    String str13 = str12 + "_";
    if (localRingToneNode.downloadInfo == null)
    {
      String str16 = str13 + String.valueOf(2);
      String str17 = str16 + "_";
      return str17 + "0";
    }
    String str14 = str13 + String.valueOf(localRingToneNode.downloadInfo.state);
    String str15 = str14 + "_";
    return str15 + String.valueOf(localRingToneNode.downloadInfo.progress);
  }

  private ChannelNode buildLegacyChannelNode()
  {
    ChannelNode localChannelNode = new ChannelNode();
    localChannelNode.channelId = Integer.valueOf(legacyId).intValue();
    localChannelNode.categoryId = this.mCategory.categoryId;
    localChannelNode.parent = this.mCategory;
    localChannelNode.title = "以前的下载";
    localChannelNode.channelType = 1;
    return localChannelNode;
  }

  private Node buildNodeByFileName(String paramString, int paramInt, long paramLong)
  {
    Object localObject;
    if (paramString == null)
      localObject = null;
    String[] arrayOfString;
    label69: 
    do
    {
      return localObject;
      int i = paramString.charAt(0);
      if (i == 111);
      for (localObject = new ProgramNode(); ; localObject = new RingToneNode())
      {
        arrayOfString = paramString.split("_");
        if (arrayOfString != null)
          break label69;
        return null;
        if (i != 114)
          break;
      }
      return null;
      if (((Node)localObject).nodeName.equalsIgnoreCase("program"))
      {
        if (arrayOfString.length < 8)
          return null;
        ProgramNode localProgramNode = (ProgramNode)localObject;
        localProgramNode.channelId = Integer.valueOf(legacyId).intValue();
        localProgramNode.id = Integer.valueOf(arrayOfString[2]).intValue();
        localProgramNode.uniqueId = localProgramNode.id;
        localProgramNode.title = arrayOfString[3];
        localProgramNode.duration = Integer.valueOf(arrayOfString[4]).intValue();
        localProgramNode.dayOfWeek = paramInt;
        localProgramNode.isDownloadProgram = true;
        localProgramNode.endTime = "00:01";
        localProgramNode.channelType = 1;
        String str3 = "file://" + getDownLoadPath();
        String str4 = str3 + "/";
        localProgramNode.setSourceUrls(str4 + paramString);
        String str5 = "file://" + getDownLoadPath();
        String str6 = str5 + "/";
        localProgramNode.setSourceUrls(str6 + localProgramNode.id);
        localProgramNode.downloadInfo = new Download();
        localProgramNode.downloadInfo.id = paramString;
        try
        {
          localProgramNode.downloadInfo.state = Integer.valueOf(arrayOfString[6]).intValue();
          localProgramNode.downloadInfo.progress = Integer.valueOf(arrayOfString[7]).intValue();
          localProgramNode.downloadInfo.fileSize = (125 * (24 * (int)localProgramNode.duration));
          localProgramNode.downloadInfo.updateTime = Integer.valueOf(arrayOfString[5]).intValue();
          localProgramNode.downloadInfo.downloadTime = paramLong;
          localProgramNode.downloadInfo.fileName = paramString;
          return localObject;
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
          return localObject;
        }
      }
    }
    while (!((Node)localObject).nodeName.equalsIgnoreCase("ringtone"));
    if (arrayOfString.length < 8)
      return null;
    RingToneNode localRingToneNode = (RingToneNode)localObject;
    localRingToneNode.ringToneAlbumId = arrayOfString[1];
    localRingToneNode.ringToneId = arrayOfString[2];
    localRingToneNode.title = arrayOfString[3];
    if (localRingToneNode.ringToneId.equalsIgnoreCase("0"))
    {
      if ((localRingToneNode.title != null) && (localRingToneNode.title.equalsIgnoreCase("liji")))
        return null;
      localRingToneNode.ringDesc = "有声世界无限精彩";
      localRingToneNode.title = "蜻蜓娘娘";
    }
    if ((localRingToneNode.ringDesc == null) || (localRingToneNode.ringDesc.equalsIgnoreCase("")))
      localRingToneNode.ringDesc = localRingToneNode.title;
    localRingToneNode.duration = Integer.valueOf(arrayOfString[4]).intValue();
    localRingToneNode.updatetime = arrayOfString[5];
    localRingToneNode.broadcast_time = String.valueOf(1.0F * localRingToneNode.duration);
    localRingToneNode.type = "DownloadRingTone";
    String str1 = "file://" + getDownLoadPath();
    String str2 = str1 + "/";
    localRingToneNode.setListenOnLineUrl(str2 + paramString);
    localRingToneNode.downloadInfo = new Download();
    localRingToneNode.downloadInfo.id = paramString;
    try
    {
      localRingToneNode.downloadInfo.state = Integer.valueOf(arrayOfString[6]).intValue();
      localRingToneNode.downloadInfo.progress = Integer.valueOf(arrayOfString[7]).intValue();
      localRingToneNode.downloadInfo.fileSize = (125 * (24 * localRingToneNode.duration));
      localRingToneNode.downloadInfo.updateTime = Integer.valueOf(localRingToneNode.updatetime).intValue();
      localRingToneNode.downloadInfo.fileName = paramString;
      return localObject;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return localObject;
  }

  private Node buildNodeBySimpleFileName(String paramString, int paramInt1, int paramInt2)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    ProgramNode localProgramNode;
    String[] arrayOfString;
    do
    {
      return null;
      localProgramNode = new ProgramNode();
      arrayOfString = paramString.split("_");
    }
    while (arrayOfString == null);
    localProgramNode.channelId = 360;
    localProgramNode.id = 360;
    localProgramNode.uniqueId = 360;
    localProgramNode.title = arrayOfString[0];
    localProgramNode.duration = Integer.valueOf(arrayOfString[1]).intValue();
    localProgramNode.dayOfWeek = paramInt2;
    localProgramNode.channelType = 1;
    localProgramNode.isDownloadProgram = true;
    localProgramNode.startTime = "00:01";
    String str1 = "file://" + getDownLoadPath();
    String str2 = str1 + "/";
    localProgramNode.setSourceUrls(str2 + paramString);
    localProgramNode.downloadInfo = new Download();
    localProgramNode.downloadInfo.id = paramString;
    try
    {
      localProgramNode.downloadInfo.state = 3;
      localProgramNode.downloadInfo.progress = 100;
      localProgramNode.downloadInfo.fileSize = (125 * (24 * (int)localProgramNode.duration));
      localProgramNode.downloadInfo.updateTime = paramInt1;
      localProgramNode.downloadInfo.downloadTime = paramInt1;
      localProgramNode.downloadInfo.sequence = 0;
      return localProgramNode;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private void connectAllVoice()
  {
    Object localObject1;
    int i;
    List localList;
    if (this.mCategory.mLstChannelNodes != null)
    {
      localObject1 = null;
      i = 0;
      if (i < this.mCategory.mLstChannelNodes.size())
      {
        localList = ((ChannelNode)this.mCategory.mLstChannelNodes.get(i)).getAllLstProgramNode();
        if ((localList == null) || (localList.size() <= 0))
          break label124;
        if (localObject1 != null)
        {
          localObject1.nextSibling = ((Node)localList.get(0));
          ((ProgramNode)localList.get(0)).prevSibling = localObject1;
        }
      }
    }
    label124: for (Object localObject2 = (Node)localList.get(-1 + localList.size()); ; localObject2 = localObject1)
    {
      i++;
      localObject1 = localObject2;
      break;
      return;
    }
  }

  private void dispatchDownLoadEvent(Node paramNode, int paramInt)
  {
    if (paramNode == null);
    while (true)
    {
      return;
      for (int i = 0; i < this.mlstDLEventListeners.size(); i++)
        ((IDownloadInfoEventListener)this.mlstDLEventListeners.get(i)).onDownLoadInfoUpdated(paramInt, paramNode);
    }
  }

  private void downloadFailedTips(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    Toast.makeText(InfoManager.getInstance().getContext(), paramString + " 下载失败,可能是网络连不上或者无法在sd卡上创建文件", 1).show();
  }

  private int downloadState(Node paramNode)
  {
    if (paramNode == null);
    do
    {
      do
      {
        return 0;
        this.refuseErrorCode = 0;
      }
      while (!paramNode.nodeName.equalsIgnoreCase("program"));
      if (hasInDownLoadList(paramNode) != -1)
        return 3;
    }
    while (hasInDownLoading(paramNode) == -1);
    return 1;
  }

  private void downloadSuccessTips(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    Toast.makeText(InfoManager.getInstance().getContext(), paramString + " 下载成功.", 1).show();
  }

  private void endDownloadTime(String paramString, boolean paramBoolean)
  {
    if (paramString == null);
    String str;
    do
    {
      Node localNode;
      do
      {
        return;
        localNode = findNodeByName(paramString);
      }
      while ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("program")) || (((ProgramNode)localNode).downloadInfo == null));
      ((ProgramNode)localNode).downloadInfo.downloadEndTime = (System.currentTimeMillis() / 1000L);
      str = QTLogger.getInstance().buildDownloadLog(localNode, paramBoolean);
    }
    while (str == null);
    LogModule.getInstance().send("DownloadProgram", str);
  }

  private Node findNodeByName(String paramString)
  {
    Object localObject;
    if ((paramString != null) && (this.lstDownLoadingNodes != null))
    {
      String str = getRealDownloadId(paramString);
      for (int j = 0; j < this.lstDownLoadingNodes.size(); j++)
      {
        localObject = (ProgramNode)this.lstDownLoadingNodes.get(j);
        if ((((ProgramNode)localObject).downloadInfo != null) && (str != null) && (((ProgramNode)localObject).downloadInfo.id.equalsIgnoreCase(str)))
          return localObject;
      }
    }
    if (paramString != null)
    {
      List localList = this.lstDownLoadedRings;
      int i = 0;
      if (localList != null)
        while (true)
        {
          if (i >= this.lstDownLoadedRings.size())
            break label161;
          localObject = (RingToneNode)this.lstDownLoadedRings.get(i);
          if ((((RingToneNode)localObject).downloadInfo != null) && (((RingToneNode)localObject).downloadInfo.id.equalsIgnoreCase(paramString)))
            break;
          i++;
        }
    }
    label161: return null;
  }

  private int getDayOfWeek()
  {
    return 0;
  }

  private String getDownLoadPath()
  {
    if (this.directory == null)
      this.directory = GlobalCfg.getInstance(null).getDownloadPath();
    return this.directory;
  }

  private String getDownloadMeta(ProgramNode paramProgramNode, int paramInt)
  {
    if (paramProgramNode == null)
      return null;
    String str1 = "n@" + paramProgramNode.id;
    String str2 = str1 + "@";
    String str3 = str2 + 0;
    String str4 = str3 + "@";
    String str5;
    String str8;
    String str9;
    String str10;
    label213: String str28;
    label230: String str11;
    label252: long l2;
    label296: String str23;
    String str24;
    label636: String str26;
    if (paramProgramNode.channelType == 1)
    {
      str5 = str4 + paramInt;
      String str6 = str5 + "@";
      String str7 = str6 + paramProgramNode.id;
      str8 = str7 + "@";
      str9 = paramProgramNode.getChannelName();
      if (paramProgramNode.title != null)
        break label752;
      str10 = "最新下载";
      if (paramProgramNode.channelType != 1)
        break label816;
      if (str9 != null)
        break label785;
      str28 = str10;
      str11 = str8 + str28;
      String str12 = str11 + "@";
      long l1 = paramProgramNode.getUpdateTime();
      if (l1 == 0L)
        break label841;
      l2 = l1 / 1000L;
      if (l2 == 0L)
        l2 = System.currentTimeMillis() / 1000L;
      String str13 = TimeUtil.msToDate7(l2 * 1000L);
      String str14 = str12 + str10;
      if ((str13 != null) && (paramProgramNode.channelType == 0))
      {
        str14 = str14 + str13;
        str10 = str10 + str13;
      }
      String str15 = str14 + "@";
      String str16 = str15 + paramProgramNode.getDuration();
      String str17 = str16 + "@";
      String str18 = str17 + l2;
      String str19 = str18 + "@";
      String str20 = str19 + paramProgramNode.getSharedSourceUrl();
      String str21 = str20 + "@";
      String str22 = str21 + paramInt;
      str23 = str22 + "@";
      if (!paramProgramNode.isNovelProgram())
        break label850;
      str24 = str23 + "0";
      String str25 = str24 + "@";
      str26 = str25 + String.valueOf(paramProgramNode.sequence);
      if (paramProgramNode.channelType != 1)
        break label917;
    }
    label785: label917: for (String str27 = String.valueOf(paramProgramNode.id); ; str27 = paramProgramNode.id + "@" + str10)
    {
      this.mapMetaInfo.put(String.valueOf(paramProgramNode.id), str26);
      return str27;
      str5 = str4 + paramProgramNode.uniqueId;
      break;
      label752: str10 = paramProgramNode.title.replaceAll("(\r\n|\r|\n|\n\r)", "").replaceAll("(@)", "").replaceAll("(/)", "_");
      break label213;
      str28 = str9.replaceAll("(\r\n|\r|\n|\n\r)", "").replaceAll("(@)", "").replaceAll("(/)", "_");
      break label230;
      label816: str11 = str8 + str10;
      break label252;
      label841: l2 = paramProgramNode.getAbsoluteStartTime();
      break label296;
      label850: if ((paramProgramNode.channelType == 0) && (paramProgramNode.getCurrPlayStatus() == 3))
      {
        str24 = str23 + "2";
        break label636;
      }
      str24 = str23 + "1";
      break label636;
    }
  }

  private Node getNodeByDownloadId(String paramString, int paramInt, long paramLong)
  {
    Object localObject;
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      localObject = null;
    while (true)
    {
      return localObject;
      String str1 = getRealDownloadId(paramString);
      String str2 = (String)this.mapMetaInfo.get(str1);
      if ((str2 == null) || (str2.equalsIgnoreCase("")))
        return null;
      String[] arrayOfString = str2.split("@");
      if ((arrayOfString == null) || (arrayOfString.length < 10))
        return null;
      localObject = new ProgramNode();
      ((ProgramNode)localObject).channelId = Integer.valueOf(arrayOfString[3]).intValue();
      ((ProgramNode)localObject).id = Integer.valueOf(arrayOfString[4]).intValue();
      ((ProgramNode)localObject).uniqueId = ((ProgramNode)localObject).id;
      ((ProgramNode)localObject).setChannelName(arrayOfString[5]);
      ((ProgramNode)localObject).title = arrayOfString[6];
      ((ProgramNode)localObject).duration = Integer.valueOf(arrayOfString[7]).intValue();
      ((ProgramNode)localObject).dayOfWeek = paramInt;
      ((ProgramNode)localObject).isDownloadProgram = true;
      ((ProgramNode)localObject).channelType = 1;
      String str3 = "file://" + getDownLoadPath();
      String str4 = str3 + "/";
      ((ProgramNode)localObject).setSourceUrls(str4 + paramString);
      String str5 = "file://" + getDownLoadPath();
      String str6 = str5 + "/";
      ((ProgramNode)localObject).setSourceUrls(str6 + ((ProgramNode)localObject).id);
      ((ProgramNode)localObject).setSharedSourceUrl(arrayOfString[9]);
      ((ProgramNode)localObject).downloadInfo = new Download();
      ((ProgramNode)localObject).downloadInfo.id = paramString;
      try
      {
        ((ProgramNode)localObject).downloadInfo.state = 3;
        ((ProgramNode)localObject).downloadInfo.progress = 100;
        ((ProgramNode)localObject).downloadInfo.channelId = Integer.valueOf(arrayOfString[3]).intValue();
        ((ProgramNode)localObject).downloadInfo.fileSize = (125 * (24 * (int)((ProgramNode)localObject).duration));
        ((ProgramNode)localObject).downloadInfo.updateTime = Integer.valueOf(arrayOfString[8]).intValue();
        ((ProgramNode)localObject).setUpdateTime(Integer.valueOf(arrayOfString[8]).intValue());
        ((ProgramNode)localObject).downloadInfo.downloadTime = paramLong;
        ((ProgramNode)localObject).downloadInfo.fileName = paramString;
        if (arrayOfString.length > 11)
          ((ProgramNode)localObject).downloadInfo.contentType = Integer.valueOf(arrayOfString[11]).intValue();
        if (arrayOfString.length > 12)
        {
          ((ProgramNode)localObject).downloadInfo.sequence = Integer.valueOf(arrayOfString[12]).intValue();
          ((ProgramNode)localObject).sequence = ((ProgramNode)localObject).downloadInfo.sequence;
        }
        if (arrayOfString.length > 10)
        {
          ((ProgramNode)localObject).downloadInfo.channelId = Integer.valueOf(arrayOfString[10]).intValue();
          return localObject;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return localObject;
  }

  private String getRealDownloadId(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    String[] arrayOfString;
    do
    {
      return null;
      arrayOfString = paramString.split("@");
    }
    while ((arrayOfString == null) || (arrayOfString.length <= 0));
    return arrayOfString[0];
  }

  private DownloadTask getTask(String paramString)
  {
    return DownloadService.getInstance(QTApplication.appContext).getTask(paramString);
  }

  private void log(String paramString)
  {
  }

  private void onDownloadFailed(String paramString)
  {
    if (paramString == null);
    Node localNode;
    do
    {
      do
        return;
      while (paramString.endsWith(".cache"));
      localNode = findNodeByName(paramString);
      if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
      {
        pauseDownLoad(localNode, false);
        downloadFailedTips(((ProgramNode)localNode).title);
        ((ProgramNode)localNode).downloadInfo.state = 4;
        dispatchDownLoadEvent(localNode, 2);
        QTMSGManage.getInstance().sendStatistcsMessage("NewDownload1", "failed");
        QTMSGManage.getInstance().sendStatistcsMessage("NewDownload2", "failed:" + InfoManager.getInstance().getNetWorkType());
        return;
      }
    }
    while ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("ringtone")));
    ((RingToneNode)localNode).downloadInfo.state = 4;
    dispatchDownLoadEvent(localNode, 2);
  }

  private void onDownloadProcessing(String paramString, int paramInt)
  {
    if (paramString == null);
    Node localNode;
    do
    {
      do
        return;
      while (paramString.endsWith(".cache"));
      localNode = findNodeByName(paramString);
      if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("ondemandprogram")))
      {
        int k = (int)(100.0D * (paramInt / ((ProgramNode)localNode).downloadInfo.fileSize));
        ((OnDemandProgramNode)localNode).downloadInfo.state = 1;
        ((OnDemandProgramNode)localNode).downloadInfo.progress = k;
        dispatchDownLoadEvent(localNode, 0);
        return;
      }
      if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
      {
        int j = (int)(100.0D * (paramInt / ((ProgramNode)localNode).downloadInfo.fileSize));
        ((ProgramNode)localNode).downloadInfo.state = 1;
        ((ProgramNode)localNode).downloadInfo.progress = j;
        dispatchDownLoadEvent(localNode, 0);
        return;
      }
    }
    while ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("ringtone")));
    int i = (int)(100.0D * (paramInt / ((ProgramNode)localNode).downloadInfo.fileSize));
    ((RingToneNode)localNode).downloadInfo.state = 1;
    ((RingToneNode)localNode).downloadInfo.progress = i;
    dispatchDownLoadEvent(localNode, 0);
  }

  private void onDownloadSuccess(String paramString)
  {
    if (paramString == null);
    Node localNode;
    do
    {
      do
        return;
      while (paramString.endsWith(".cache"));
      localNode = findNodeByName(paramString);
      if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
      {
        ((ProgramNode)localNode).downloadInfo.state = 3;
        ((ProgramNode)localNode).downloadInfo.progress = 100;
        downloadSuccessTips(((ProgramNode)localNode).title);
        endDownloadTime(paramString, true);
        attachProgram((ProgramNode)localNode);
        connectAllVoice();
        removeDownloading(localNode);
        dispatchDownLoadEvent(localNode, 1);
        writeToDAT(paramString);
        QTMSGManage.getInstance().sendStatistcsMessage("NewDownload1", "success");
        return;
      }
    }
    while ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("ringtone")));
    ((RingToneNode)localNode).downloadInfo.state = 3;
    ((RingToneNode)localNode).downloadInfo.progress = 100;
    ((RingToneNode)localNode).ringType = "local";
    this.lstDownLoadedRings.add(localNode);
    InfoManager.getInstance().root().mRingToneInfoNode.addToRingTone(localNode);
    dispatchDownLoadEvent(localNode, 1);
    QTMSGManage.getInstance().sendStatisticsMessageOnceADay("DownloadADay", "success");
  }

  private void parseDAT(String paramString)
  {
    FileReader localFileReader;
    try
    {
      localFileReader = new FileReader(paramString + "/" + this.downloadDat);
      BufferedReader localBufferedReader = new BufferedReader(localFileReader);
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          break;
        parseItem(str);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    localFileReader.close();
  }

  private boolean parseDAT()
  {
    String str1 = getDownLoadPath();
    String str2 = Environment.getExternalStorageDirectory() + File.separator + "QTDownloadRadio";
    if (!TextUtils.equals(str1, str2))
      parseDAT(str2);
    parseDAT(str1);
    return false;
  }

  private boolean parseItem(String paramString)
  {
    if (paramString == null);
    String[] arrayOfString;
    do
    {
      return false;
      arrayOfString = paramString.split("@", 7);
    }
    while (arrayOfString == null);
    String str = arrayOfString[1];
    this.mapMetaInfo.put(str, paramString);
    int i = Integer.valueOf(arrayOfString[3]).intValue();
    if (this.mapChannelNodes.get(Integer.valueOf(i)) == null)
    {
      ChannelNode localChannelNode = buildChannelNode(arrayOfString[5], i);
      if (localChannelNode != null)
        addChannelNodeToCategory(localChannelNode);
    }
    return true;
  }

  private void recordDownloadTime(String paramString)
  {
    if (paramString == null);
    Node localNode;
    do
    {
      return;
      localNode = findNodeByName(paramString);
    }
    while ((localNode == null) || (!localNode.nodeName.equalsIgnoreCase("program")) || (((ProgramNode)localNode).downloadInfo == null));
    ((ProgramNode)localNode).downloadInfo.downloadStartTime = (System.currentTimeMillis() / 1000L);
    ((ProgramNode)localNode).downloadInfo.lastProgress = ((ProgramNode)localNode).downloadInfo.progress;
  }

  private void refineDownloadChannels()
  {
    for (int i = 0; i < this.mCategory.mLstChannelNodes.size(); i++)
      if (((ChannelNode)this.mCategory.mLstChannelNodes.get(i)).hasEmptyProgramSchedule())
      {
        this.mapChannelNodes.remove(Integer.valueOf(((ChannelNode)this.mCategory.mLstChannelNodes.get(i)).channelId));
        this.mCategory.mLstChannelNodes.remove(i);
        i--;
      }
  }

  private void removeDownloading(Node paramNode)
  {
    int i = hasInDownLoading(paramNode);
    if (i == -1)
      return;
    this.lstDownLoadingNodes.remove(i);
    this.mNeedSync = true;
  }

  private boolean restoreDownloading()
  {
    if (this.hasRestoreDownloading)
      return true;
    this.hasRestoreDownloading = true;
    Result localResult = DataManager.getInstance().getData("getdb_downloading_program_node", null, null).getResult();
    List localList = null;
    if (localResult != null)
    {
      boolean bool = localResult.getSuccess();
      localList = null;
      if (bool)
        localList = (List)localResult.getData();
    }
    if ((localList == null) || (localList.size() == 0))
      return false;
    this.lstDownLoadingNodes = localList;
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      Node localNode = (Node)localIterator.next();
      if ((localNode instanceof ProgramNode))
        ((ProgramNode)localNode).downloadInfo.state = 2;
    }
    return true;
  }

  // ERROR //
  private void restoreFromDir(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +24 -> 25
    //   4: new 678	java/io/File
    //   7: dup
    //   8: aload_1
    //   9: invokespecial 750	java/io/File:<init>	(Ljava/lang/String;)V
    //   12: astore_2
    //   13: aload_2
    //   14: invokevirtual 753	java/io/File:exists	()Z
    //   17: ifne +9 -> 26
    //   20: aload_2
    //   21: invokevirtual 756	java/io/File:mkdir	()Z
    //   24: pop
    //   25: return
    //   26: aload_2
    //   27: invokevirtual 759	java/io/File:isDirectory	()Z
    //   30: ifeq -5 -> 25
    //   33: aload_2
    //   34: invokevirtual 763	java/io/File:listFiles	()[Ljava/io/File;
    //   37: astore 5
    //   39: iconst_m1
    //   40: aload 5
    //   42: arraylength
    //   43: iadd
    //   44: istore 6
    //   46: iload 6
    //   48: iflt +261 -> 309
    //   51: aload 5
    //   53: iload 6
    //   55: aaload
    //   56: astore 7
    //   58: aload 7
    //   60: invokevirtual 766	java/io/File:isFile	()Z
    //   63: ifeq +263 -> 326
    //   66: aload 7
    //   68: invokevirtual 769	java/io/File:length	()J
    //   71: lstore 8
    //   73: lload 8
    //   75: ldc2_w 448
    //   78: lcmp
    //   79: ifle +247 -> 326
    //   82: aload 7
    //   84: invokevirtual 772	java/io/File:getName	()Ljava/lang/String;
    //   87: ldc_w 774
    //   90: invokevirtual 569	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   93: ifeq +52 -> 145
    //   96: aload_0
    //   97: aload 7
    //   99: invokevirtual 772	java/io/File:getName	()Ljava/lang/String;
    //   102: aload 7
    //   104: invokevirtual 777	java/io/File:lastModified	()J
    //   107: l2i
    //   108: aload_0
    //   109: invokespecial 779	fm/qingting/qtradio/model/DownLoadInfoNode:getDayOfWeek	()I
    //   112: invokespecial 781	fm/qingting/qtradio/model/DownLoadInfoNode:buildNodeBySimpleFileName	(Ljava/lang/String;II)Lfm/qingting/qtradio/model/Node;
    //   115: astore 11
    //   117: aload 11
    //   119: ifnull +101 -> 220
    //   122: aload 11
    //   124: getfield 209	fm/qingting/qtradio/model/Node:nodeName	Ljava/lang/String;
    //   127: ldc_w 600
    //   130: invokevirtual 198	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   133: ifeq +87 -> 220
    //   136: aload 11
    //   138: aload_0
    //   139: putfield 782	fm/qingting/qtradio/model/Node:parent	Lfm/qingting/qtradio/model/Node;
    //   142: goto +184 -> 326
    //   145: aload 7
    //   147: invokevirtual 772	java/io/File:getName	()Ljava/lang/String;
    //   150: ldc 15
    //   152: invokevirtual 569	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   155: ifeq +17 -> 172
    //   158: invokestatic 787	fm/qingting/qtradio/fm/PlayCacheAgent:getInstance	()Lfm/qingting/qtradio/fm/PlayCacheAgent;
    //   161: aload 7
    //   163: invokevirtual 772	java/io/File:getName	()Ljava/lang/String;
    //   166: invokevirtual 790	fm/qingting/qtradio/fm/PlayCacheAgent:addCacheByName	(Ljava/lang/String;)V
    //   169: goto +157 -> 326
    //   172: aload_0
    //   173: aload 7
    //   175: invokevirtual 772	java/io/File:getName	()Ljava/lang/String;
    //   178: aload_0
    //   179: invokespecial 779	fm/qingting/qtradio/model/DownLoadInfoNode:getDayOfWeek	()I
    //   182: aload 7
    //   184: invokevirtual 777	java/io/File:lastModified	()J
    //   187: invokespecial 792	fm/qingting/qtradio/model/DownLoadInfoNode:buildNodeByFileName	(Ljava/lang/String;IJ)Lfm/qingting/qtradio/model/Node;
    //   190: astore 11
    //   192: aload 11
    //   194: ifnonnull -77 -> 117
    //   197: aload_0
    //   198: aload 7
    //   200: invokevirtual 772	java/io/File:getName	()Ljava/lang/String;
    //   203: aload_0
    //   204: invokespecial 779	fm/qingting/qtradio/model/DownLoadInfoNode:getDayOfWeek	()I
    //   207: aload 7
    //   209: invokevirtual 777	java/io/File:lastModified	()J
    //   212: invokespecial 794	fm/qingting/qtradio/model/DownLoadInfoNode:getNodeByDownloadId	(Ljava/lang/String;IJ)Lfm/qingting/qtradio/model/Node;
    //   215: astore 11
    //   217: goto -100 -> 117
    //   220: aload 11
    //   222: ifnull +28 -> 250
    //   225: aload 11
    //   227: getfield 209	fm/qingting/qtradio/model/Node:nodeName	Ljava/lang/String;
    //   230: ldc 211
    //   232: invokevirtual 198	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   235: ifeq +15 -> 250
    //   238: aload_0
    //   239: aload 11
    //   241: checkcast 132	fm/qingting/qtradio/model/ProgramNode
    //   244: invokespecial 612	fm/qingting/qtradio/model/DownLoadInfoNode:attachProgram	(Lfm/qingting/qtradio/model/ProgramNode;)V
    //   247: goto +79 -> 326
    //   250: aload 11
    //   252: ifnull +74 -> 326
    //   255: aload 11
    //   257: getfield 209	fm/qingting/qtradio/model/Node:nodeName	Ljava/lang/String;
    //   260: ldc_w 263
    //   263: invokevirtual 198	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   266: ifeq +60 -> 326
    //   269: aload 11
    //   271: checkcast 265	fm/qingting/qtradio/model/RingToneNode
    //   274: ldc_w 625
    //   277: putfield 628	fm/qingting/qtradio/model/RingToneNode:ringType	Ljava/lang/String;
    //   280: aload_0
    //   281: getfield 62	fm/qingting/qtradio/model/DownLoadInfoNode:lstDownLoadedRings	Ljava/util/List;
    //   284: aload 11
    //   286: invokeinterface 113 2 0
    //   291: pop
    //   292: invokestatic 409	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   295: invokevirtual 632	fm/qingting/qtradio/model/InfoManager:root	()Lfm/qingting/qtradio/model/RootNode;
    //   298: getfield 638	fm/qingting/qtradio/model/RootNode:mRingToneInfoNode	Lfm/qingting/qtradio/model/RingToneInfoNode;
    //   301: aload 11
    //   303: invokevirtual 643	fm/qingting/qtradio/model/RingToneInfoNode:addToRingTone	(Lfm/qingting/qtradio/model/Node;)V
    //   306: goto +20 -> 326
    //   309: aload_0
    //   310: invokespecial 796	fm/qingting/qtradio/model/DownLoadInfoNode:refineDownloadChannels	()V
    //   313: aload_0
    //   314: invokespecial 614	fm/qingting/qtradio/model/DownLoadInfoNode:connectAllVoice	()V
    //   317: return
    //   318: astore 13
    //   320: return
    //   321: astore 4
    //   323: return
    //   324: astore_3
    //   325: return
    //   326: iinc 6 255
    //   329: goto -283 -> 46
    //   332: astore 10
    //   334: goto -8 -> 326
    //
    // Exception table:
    //   from	to	target	type
    //   309	317	318	java/lang/Exception
    //   4	13	321	java/lang/Exception
    //   26	46	321	java/lang/Exception
    //   51	73	321	java/lang/Exception
    //   13	25	324	java/lang/Exception
    //   82	117	332	java/lang/Exception
    //   122	142	332	java/lang/Exception
    //   145	169	332	java/lang/Exception
    //   172	192	332	java/lang/Exception
    //   197	217	332	java/lang/Exception
    //   225	247	332	java/lang/Exception
    //   255	306	332	java/lang/Exception
  }

  private boolean startDownLoad(Node paramNode)
  {
    if (paramNode == null)
      return false;
    if (!paramNode.nodeName.equalsIgnoreCase("program"))
      return false;
    int i;
    int j;
    int k;
    String str1;
    if (paramNode.nodeName.equalsIgnoreCase("program"))
    {
      if (((ProgramNode)paramNode).getCurrPlayStatus() != 3)
        return false;
      i = ((ProgramNode)paramNode).getDuration();
      j = ((ProgramNode)paramNode).channelId;
      if (((ProgramNode)paramNode).channelType == 1);
      for (k = 1; ; k = 0)
      {
        str1 = ((ProgramNode)paramNode).getDownLoadUrlPath();
        if ((str1 != null) && (!str1.equalsIgnoreCase("")))
          break;
        return false;
      }
    }
    return false;
    String str2 = getDownloadMeta((ProgramNode)paramNode, j);
    String str3 = getRealDownloadId(str2);
    int m = ((ProgramNode)paramNode).resId;
    String str4 = DownloadUtils.escapeFileName(str2);
    Node localNode1;
    if ((str4 != null) && (!str4.equalsIgnoreCase("")))
    {
      localNode1 = getNodeByDownloadId(str4, getDayOfWeek(), System.currentTimeMillis() / 1000L);
      if (localNode1 == null)
        return false;
      if (!localNode1.nodeName.equalsIgnoreCase("program"))
        break label657;
      ((ProgramNode)localNode1).resId = m;
      if (((ProgramNode)localNode1).downloadInfo == null)
        break label657;
      ((ProgramNode)localNode1).downloadInfo.channelId = j;
      ((ProgramNode)localNode1).downloadInfo.type = k;
      ((ProgramNode)localNode1).downloadInfo.downloadPath = str1;
      ((ProgramNode)localNode1).downloadInfo.state = 0;
      ((ProgramNode)localNode1).downloadInfo.progress = 0;
      ((ProgramNode)localNode1).downloadInfo.fileSize = (125 * (24 * ((ProgramNode)localNode1).getDuration()));
      ((ProgramNode)localNode1).downloadInfo.sequence = ((ProgramNode)paramNode).sequence;
      ((ProgramNode)localNode1).downloadInfo.fileName = str4;
      ((ProgramNode)localNode1).downloadInfo.id = str3;
    }
    label657: for (String str5 = ((ProgramNode)localNode1).getNextDownLoadUrl(); ; str5 = null)
    {
      if ((str5 == null) || (str5.equalsIgnoreCase("")))
        return false;
      localNode1.parent = this;
      if (this.lstDownLoadingNodes.size() == 0)
        this.lstDownLoadingNodes.add(localNode1);
      Downloadobject localDownloadobject;
      ArrayList localArrayList;
      while (true)
      {
        this.mNeedSync = true;
        int n = 125 * (i * 24);
        localDownloadobject = new Downloadobject(str3, str4, str5);
        localDownloadobject.setFileSize(n);
        localArrayList = new ArrayList();
        localArrayList.add(str5);
        while (true)
        {
          String str6 = ((ProgramNode)paramNode).getNextDownLoadUrl();
          if (TextUtils.isEmpty(str6))
            break;
          localArrayList.add(str6);
        }
        Node localNode2 = (Node)this.lstDownLoadingNodes.get(-1 + this.lstDownLoadingNodes.size());
        if (localNode2 != null)
        {
          localNode1.prevSibling = localNode2;
          localNode2.nextSibling = localNode1;
        }
        this.lstDownLoadingNodes.add(localNode1);
      }
      File localFile = new File(getDownLoadPath(), localDownloadobject.programName);
      DownloadTask localDownloadTask = new DownloadTask(localDownloadobject.uuid, (String[])localArrayList.toArray(new String[0]), localFile.getAbsolutePath());
      DownloadService.getInstance(QTApplication.appContext).addTask(localDownloadTask);
      if (!DownloadService.getInstance(QTApplication.appContext).isDownloading())
        DownloadService.getInstance(QTApplication.appContext).start();
      QTMSGManage.getInstance().sendStatisticsMessageOnceADay("DownloadADay", "startLoad");
      recordDownloadTime(str4);
      return true;
      return false;
    }
  }

  private void syncMetaInfo()
  {
    for (int i = 0; i < this.lstDownLoadingNodes.size(); i++)
    {
      ProgramNode localProgramNode = (ProgramNode)this.lstDownLoadingNodes.get(i);
      if ((localProgramNode != null) && (localProgramNode.downloadInfo != null))
      {
        String str = localProgramNode.downloadInfo.id;
        if ((str != null) && ((String)this.mapMetaInfo.get(str) == null))
          getDownloadMeta(localProgramNode, localProgramNode.downloadInfo.channelId);
      }
    }
  }

  private void writeDownloadingToDB()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("nodes", this.lstDownLoadingNodes);
    DataManager.getInstance().getData("updatedb_downloading_program_node", null, localHashMap);
  }

  private void writeDownloadingToDB(ProgramNode paramProgramNode)
  {
    if (paramProgramNode == null)
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("node", paramProgramNode);
    DataManager.getInstance().getData("insertdb_downloading_program_node", null, localHashMap);
  }

  private boolean writeToDAT(String paramString)
  {
    if (paramString == null)
      return false;
    String str1 = getRealDownloadId(paramString);
    String str2 = (String)this.mapMetaInfo.get(str1);
    if ((str2 == null) || (str2.equalsIgnoreCase("")))
      return false;
    try
    {
      String str3 = getDownLoadPath();
      FileWriter localFileWriter = new FileWriter(str3 + "/" + this.downloadDat, true);
      localFileWriter.write(str2);
      localFileWriter.write("\r\n");
      localFileWriter.flush();
      localFileWriter.close();
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public boolean addToDownloadList(Node paramNode)
  {
    if (paramNode == null)
      return false;
    if (!paramNode.nodeName.equalsIgnoreCase("program"))
      return false;
    if ((hasInDownLoadList(paramNode) != -1) || (hasInDownLoading(paramNode) != -1))
      return false;
    if ((((ProgramNode)paramNode).isMusicChannel()) && (!InfoManager.getInstance().allowDownloadMusic(((ProgramNode)paramNode).channelId)))
    {
      Toast.makeText(InfoManager.getInstance().getContext(), "该节目无法下载", 0).show();
      return false;
    }
    if (startDownLoad(paramNode))
    {
      dispatchDownLoadEvent(paramNode, 8);
      return true;
    }
    return false;
  }

  public boolean allowDownLoad(Node paramNode)
  {
    this.refuseErrorCode = 0;
    boolean bool1 = paramNode.nodeName.equalsIgnoreCase("program");
    boolean bool2 = false;
    if (bool1)
    {
      int i = ((ProgramNode)paramNode).getCurrPlayStatus();
      bool2 = false;
      if (i == 3)
        bool2 = true;
    }
    return bool2;
  }

  public void delDownLoad(ChannelNode paramChannelNode, boolean paramBoolean)
  {
    if (paramChannelNode == null);
    ChannelNode localChannelNode;
    do
    {
      return;
      localChannelNode = (ChannelNode)this.mapChannelNodes.get(Integer.valueOf(paramChannelNode.channelId));
    }
    while (localChannelNode == null);
    if (!localChannelNode.hasEmptyProgramSchedule())
    {
      List localList = localChannelNode.getAllLstProgramNode();
      if (localList != null)
      {
        for (int i = 0; i < localList.size(); i++)
        {
          ProgramNode localProgramNode = (ProgramNode)localList.get(i);
          if ((localProgramNode != null) && (localProgramNode.downloadInfo != null))
          {
            String str = getRealDownloadId(localProgramNode.downloadInfo.id);
            if (!DownloadService.getInstance(QTApplication.appContext).removeTask(str))
              DownloadUtils.deleteFile(new File(getDownLoadPath(), localProgramNode.downloadInfo.fileName).getAbsolutePath());
            this.mTotalProgramCnt = (-1 + this.mTotalProgramCnt);
            this.mTotalProgramSize -= localProgramNode.downloadInfo.fileSize;
          }
        }
        localList.clear();
      }
      localChannelNode.programCnt = 0;
    }
    this.mapChannelNodes.remove(Integer.valueOf(localChannelNode.channelId));
    this.mCategory.mLstChannelNodes.remove(localChannelNode);
    dispatchDownLoadEvent(paramChannelNode, 4);
  }

  public void delDownLoad(ProgramNode paramProgramNode, boolean paramBoolean)
  {
    if (paramProgramNode == null);
    do
    {
      do
        return;
      while (hasInDownLoadList(paramProgramNode) == -1);
      if ((paramProgramNode != null) && (paramProgramNode.downloadInfo != null))
      {
        String str = getRealDownloadId(paramProgramNode.downloadInfo.id);
        if (!DownloadService.getInstance(QTApplication.appContext).removeTask(str))
          DownloadUtils.deleteFile(new File(getDownLoadPath(), paramProgramNode.downloadInfo.fileName).getAbsolutePath());
      }
      ChannelNode localChannelNode = (ChannelNode)this.mapChannelNodes.get(Integer.valueOf(paramProgramNode.channelId));
      if ((localChannelNode != null) && (!localChannelNode.hasEmptyProgramSchedule()))
      {
        localChannelNode.delProgramNode(paramProgramNode.id);
        localChannelNode.programCnt = (-1 + localChannelNode.programCnt);
      }
      this.mTotalProgramCnt = (-1 + this.mTotalProgramCnt);
    }
    while (paramProgramNode.downloadInfo == null);
    this.mTotalProgramSize -= paramProgramNode.downloadInfo.fileSize;
  }

  public void delDownLoading(Node paramNode, boolean paramBoolean)
  {
    if (paramNode == null);
    int i;
    do
    {
      return;
      i = hasInDownLoading(paramNode);
    }
    while (i == -1);
    ProgramNode localProgramNode = (ProgramNode)this.lstDownLoadingNodes.get(i);
    if ((localProgramNode != null) && (localProgramNode.downloadInfo != null))
    {
      String str = getRealDownloadId(localProgramNode.downloadInfo.id);
      if (!DownloadService.getInstance(QTApplication.appContext).removeTask(str))
        DownloadUtils.deleteFile(localProgramNode.downloadInfo.fileName);
    }
    this.lstDownLoadingNodes.remove(i);
    dispatchDownLoadEvent(localProgramNode, 4);
    this.mNeedSync = true;
  }

  public int getAllDownloadCount()
  {
    return this.lstDownLoadingNodes.size();
  }

  public List<Node> getAllDownloadingNodes()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(this.lstDownLoadingNodes);
    return localArrayList;
  }

  public Node getCategory()
  {
    return this.mCategory;
  }

  public ChannelNode getChannelNode(int paramInt)
  {
    if ((this.mCategory != null) && (this.mCategory.mLstChannelNodes != null))
      for (int i = 0; i < this.mCategory.mLstChannelNodes.size(); i++)
        if (((ChannelNode)this.mCategory.mLstChannelNodes.get(i)).channelId == paramInt)
          return (ChannelNode)this.mCategory.mLstChannelNodes.get(i);
    return null;
  }

  public List<Node> getDownLoadedList()
  {
    if (!this.hasRestored)
      restoreFromDir();
    return null;
  }

  public int getDownloadErrorCode()
  {
    return this.refuseErrorCode;
  }

  public String getDownloadedProgramSource(ProgramNode paramProgramNode)
  {
    if (paramProgramNode == null)
      return null;
    int i;
    List localList;
    if (paramProgramNode.channelType == 1)
    {
      i = paramProgramNode.channelId;
      ChannelNode localChannelNode = (ChannelNode)this.mapChannelNodes.get(Integer.valueOf(i));
      if ((localChannelNode != null) && (!localChannelNode.hasEmptyProgramSchedule()))
      {
        localList = localChannelNode.getAllLstProgramNode();
        if (localList == null);
      }
    }
    else
    {
      for (int j = 0; ; j++)
      {
        if (j >= localList.size())
          break label125;
        if (paramProgramNode.id == ((ProgramNode)localList.get(j)).id)
        {
          return ((ProgramNode)localList.get(j)).getSourceUrl();
          i = paramProgramNode.uniqueId;
          break;
        }
      }
    }
    label125: return null;
  }

  public List<ChannelNode> getLstChannelNodes()
  {
    if (this.mCategory != null)
      return this.mCategory.mLstChannelNodes;
    return null;
  }

  public List<Node> getLstDownloadingNodes()
  {
    if (!this.hasRestoreDownloading)
      restoreDownloading();
    return this.lstDownLoadingNodes;
  }

  public int getTotalProgramCnt()
  {
    return this.mTotalProgramCnt;
  }

  public long getTotalProgramSize()
  {
    return this.mTotalProgramSize;
  }

  public int hasDownLoad(Node paramNode)
  {
    if ((paramNode != null) && (paramNode.nodeName.equalsIgnoreCase("program")))
      return downloadState(paramNode);
    return 0;
  }

  public int hasInDownLoadList(Node paramNode)
  {
    int i = 0;
    if (paramNode == null)
      return -1;
    this.refuseErrorCode = 0;
    if (paramNode.nodeName.equalsIgnoreCase("program"))
    {
      ProgramNode localProgramNode = (ProgramNode)paramNode;
      int j;
      List localList;
      int k;
      if (((ProgramNode)paramNode).channelType == 1)
      {
        j = ((ProgramNode)paramNode).channelId;
        ChannelNode localChannelNode = (ChannelNode)this.mapChannelNodes.get(Integer.valueOf(j));
        if ((localChannelNode != null) && (!localChannelNode.hasEmptyProgramSchedule()))
        {
          localList = localChannelNode.getAllLstProgramNode();
          if (localList != null)
            k = localList.size();
        }
      }
      else
      {
        while (true)
        {
          if (i >= k)
            break label186;
          if (localProgramNode.id == ((ProgramNode)localList.get(i)).id)
          {
            if ((localProgramNode.channelType == 0) && (localProgramNode.getAbsoluteStartTime() != ((ProgramNode)localList.get(i)).getUpdateTime()))
            {
              return -1;
              j = ((ProgramNode)paramNode).uniqueId;
              break;
            }
            this.refuseErrorCode = 2;
            return i;
          }
          i++;
        }
      }
      label186: return -1;
    }
    return -1;
  }

  public int hasInDownLoading(Node paramNode)
  {
    int i = 0;
    if (paramNode == null)
      return -1;
    this.refuseErrorCode = 0;
    if (paramNode.nodeName.equalsIgnoreCase("program"))
    {
      int j = this.lstDownLoadingNodes.size();
      while (i < j)
      {
        if (((ProgramNode)paramNode).id == ((ProgramNode)this.lstDownLoadingNodes.get(i)).id)
        {
          this.refuseErrorCode = 2;
          return i;
        }
        i++;
      }
    }
    return -1;
  }

  public boolean hasNodeDownloaded(Node paramNode)
  {
    boolean bool1 = paramNode instanceof ProgramNode;
    boolean bool2 = false;
    if (bool1)
    {
      int i = ((ProgramNode)paramNode).downloadInfo.state;
      bool2 = false;
      if (i == 3)
        bool2 = true;
    }
    return bool2;
  }

  public void init()
  {
    DownloadService.getInstance(QTApplication.appContext).addDownloadListener(this);
    parseDAT();
    getDownLoadedList();
    if (!LifeTime.isFirstLaunchAfterInstall)
      getLstDownloadingNodes();
    syncMetaInfo();
  }

  public boolean isSDCardAvailable()
  {
    return DownloadUtils.isSDCardAvailable();
  }

  public void onDownloadEvent(String paramString, DownloadState paramDownloadState, Object paramObject)
  {
    try
    {
      switch (1.$SwitchMap$fm$qingting$downloadnew$DownloadState[paramDownloadState.ordinal()])
      {
      case 1:
        onDownloadFailed(paramString);
        if (paramObject != null)
        {
          QTMSGManage.getInstance().sendStatistcsMessage("NewDownload4", "failed:" + InfoManager.getInstance().getNetWorkType() + "_" + paramObject.toString());
          return;
        }
        break;
      case 4:
        onDownloadProcessing(paramString, 0);
        return;
      case 5:
        DownloadTask localDownloadTask = getTask(paramString);
        if (localDownloadTask != null)
        {
          onDownloadProcessing(paramString, (int)localDownloadTask.getCurSize());
          return;
        }
        break;
      case 6:
        onDownloadSuccess(paramString);
      case 2:
      case 3:
      }
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void pauseDownLoad(Node paramNode, boolean paramBoolean)
  {
    if (paramNode == null);
    ProgramNode localProgramNode;
    do
    {
      int i;
      do
      {
        return;
        i = hasInDownLoading(paramNode);
      }
      while (i == -1);
      localProgramNode = (ProgramNode)this.lstDownLoadingNodes.get(i);
    }
    while ((localProgramNode == null) || (localProgramNode.downloadInfo == null));
    DownloadService.getInstance(QTApplication.appContext).pauseTask(localProgramNode.downloadInfo.id);
    endDownloadTime(localProgramNode.downloadInfo.id, paramBoolean);
    writeDownloadingToDB(localProgramNode);
  }

  public void registerListener(IDownloadInfoEventListener paramIDownloadInfoEventListener)
  {
    if (paramIDownloadInfoEventListener == null)
      return;
    Iterator localIterator = this.mlstDLEventListeners.iterator();
    while (localIterator.hasNext())
      if ((IDownloadInfoEventListener)localIterator.next() == paramIDownloadInfoEventListener)
        return;
    this.mlstDLEventListeners.add(paramIDownloadInfoEventListener);
  }

  public void restoreFromDir()
  {
    if (this.hasRestored)
      return;
    this.hasRestored = true;
    String str1 = getDownLoadPath();
    String str2 = Environment.getExternalStorageDirectory() + File.separator + "QTDownloadRadio";
    if (!TextUtils.equals(str1, str2))
      restoreFromDir(str2);
    restoreFromDir(str1);
  }

  public void resumeDownLoad(Node paramNode)
  {
    if (paramNode == null);
    ProgramNode localProgramNode;
    do
    {
      int i;
      do
      {
        return;
        i = hasInDownLoading(paramNode);
      }
      while (i == -1);
      localProgramNode = (ProgramNode)this.lstDownLoadingNodes.get(i);
    }
    while ((localProgramNode == null) || (localProgramNode.downloadInfo == null));
    localProgramNode.downloadInfo.state = 0;
    DownloadService.getInstance(QTApplication.appContext).resumeTask(localProgramNode.downloadInfo.id);
    if (!DownloadService.getInstance(QTApplication.appContext).isDownloading())
      DownloadService.getInstance(QTApplication.appContext).start();
    recordDownloadTime(localProgramNode.downloadInfo.id);
  }

  public boolean ringHasDownloaded(Node paramNode)
  {
    if (paramNode == null);
    while (true)
    {
      return false;
      if (paramNode.nodeName.equalsIgnoreCase("ringtone"))
      {
        this.refuseErrorCode = 0;
        for (int i = 0; i < this.lstDownLoadedRings.size(); i++)
          if (((RingToneNode)this.lstDownLoadedRings.get(i)).ringToneId.equalsIgnoreCase(((RingToneNode)paramNode).ringToneId))
          {
            this.refuseErrorCode = 2;
            return true;
          }
      }
    }
  }

  public void saveDownloading()
  {
    for (int i = 0; i < this.lstDownLoadingNodes.size(); i++)
    {
      ProgramNode localProgramNode = (ProgramNode)this.lstDownLoadingNodes.get(i);
      if ((localProgramNode != null) && (localProgramNode.downloadInfo != null))
        DownloadService.getInstance(QTApplication.appContext).pauseTask(localProgramNode.downloadInfo.id);
    }
    writeDownloadingToDB();
  }

  public boolean startDownLoadRing(Node paramNode)
  {
    if (paramNode == null);
    String str4;
    do
    {
      do
        return false;
      while ((ringHasDownloaded(paramNode)) || (!paramNode.nodeName.equalsIgnoreCase("ringtone")));
      l = 0L;
      if (!paramNode.nodeName.equalsIgnoreCase("ringtone"))
        break;
      str4 = ((RingToneNode)paramNode).getDownLoadUrl();
    }
    while ((str4 == null) || (str4.equalsIgnoreCase("")));
    int j = ((RingToneNode)paramNode).getDuration();
    long l = Long.valueOf(((RingToneNode)paramNode).updatetime).longValue();
    String str1 = str4;
    int i = j;
    while (true)
    {
      String str2 = buildFileNameByNode(paramNode, l);
      if ((str2 == null) || (str2.equalsIgnoreCase("")) || (str1 == null) || (str1.equalsIgnoreCase("")))
        break;
      Node localNode = buildNodeByFileName(str2, getDayOfWeek(), System.currentTimeMillis() / 1000L);
      if (localNode == null)
        break;
      ((RingToneNode)localNode).mDownLoadPath = ((RingToneNode)paramNode).mDownLoadPath;
      ((RingToneNode)localNode).mTranscode = ((RingToneNode)paramNode).mTranscode;
      ((RingToneNode)localNode).ringDesc = ((RingToneNode)paramNode).ringDesc;
      ((RingToneNode)localNode).ringType = ((RingToneNode)paramNode).ringType;
      ((RingToneNode)localNode).type = ((RingToneNode)paramNode).type;
      localNode.parent = this;
      this.lstDownLoadedRings.add(localNode);
      Downloadobject localDownloadobject = new Downloadobject(str2, str2, str1);
      localDownloadobject.setFileSize(125 * (i * 24));
      File localFile = new File(getDownLoadPath(), localDownloadobject.programName);
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(str1);
      while (true)
      {
        String str3 = ((ProgramNode)paramNode).getNextDownLoadUrl();
        if (TextUtils.isEmpty(str3))
          break;
        localArrayList.add(str3);
      }
      DownloadTask localDownloadTask = new DownloadTask(localDownloadobject.uuid, (String[])localArrayList.toArray(new String[0]), localFile.getAbsolutePath());
      DownloadService.getInstance(QTApplication.appContext).addTask(localDownloadTask);
      if (!DownloadService.getInstance(QTApplication.appContext).isDownloading())
        DownloadService.getInstance(QTApplication.appContext).start();
      QTMSGManage.getInstance().sendStatisticsMessageOnceADay("DownloadADay", "startLoad");
      return false;
      i = 0;
      str1 = null;
    }
  }

  public void syncDownloadingToDB()
  {
    if (this.mNeedSync)
      writeDownloadingToDB();
    this.mNeedSync = false;
  }

  public void unregisterListener(IDownloadInfoEventListener paramIDownloadInfoEventListener)
  {
    if (paramIDownloadInfoEventListener == null);
    IDownloadInfoEventListener localIDownloadInfoEventListener;
    do
    {
      return;
      Iterator localIterator;
      while (!localIterator.hasNext())
        localIterator = this.mlstDLEventListeners.iterator();
      localIDownloadInfoEventListener = (IDownloadInfoEventListener)localIterator.next();
    }
    while (localIDownloadInfoEventListener != paramIDownloadInfoEventListener);
    this.mlstDLEventListeners.remove(localIDownloadInfoEventListener);
  }

  public static abstract interface IDownloadInfoEventListener
  {
    public static final int DOWNLOAD_ADDED = 8;
    public static final int DOWNLOAD_COMPLETE = 1;
    public static final int DOWNLOAD_DELETED = 4;
    public static final int DOWNLOAD_FAILED = 2;
    public static final int DOWNLOAD_PROGRESS;

    public abstract void onDownLoadInfoUpdated(int paramInt, Node paramNode);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.DownLoadInfoNode
 * JD-Core Version:    0.6.2
 */