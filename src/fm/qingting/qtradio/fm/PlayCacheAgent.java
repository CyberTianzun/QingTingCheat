package fm.qingting.qtradio.fm;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.download.QTRadioDownloadAgent;
import fm.qingting.download.qtradiodownload.IDownLoadEventListener;
import fm.qingting.qtradio.model.Download;
import fm.qingting.qtradio.model.Downloadobject;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PlayCacheAgent
  implements IDownLoadEventListener
{
  private static final int CLEAR_CACHE = 2;
  private static final int DELAYSTARTCACHE = 5000;
  private static final int DELAYTIME = 500;
  public static final int EXCEED_DURATION = 1;
  public static final int HAS_EXISTED = 2;
  private static final int SELECT_CACHE = 0;
  private static final int SMART_CLEAR_CACHE = 3;
  private static final int START_CACHE = 1;
  private static final String SUFFIX = ".cache";
  private static PlayCacheAgent _instance = null;
  private static HandlerThread cache = new HandlerThread("PlayCacheAgent_Cache_Thread");
  private int MAX_CACHE_DURATION = 3600;
  private int MAX_DURATION = 5400;
  private CacheHandler cacheHandler = new CacheHandler(cache.getLooper());
  private boolean hasRestored = false;
  private List<ProgramNode> lstDownloadingNodes = new ArrayList();
  private ProgramNode mNode;
  private Map<Integer, String> mapDownloadedNodes = new HashMap();
  private Map<Integer, String> mapDownloadingNodes = new HashMap();
  private List<IDownloadInfoEventListener> mlstDLEventListeners = new ArrayList();
  private int refuseErrorCode = 0;

  static
  {
    cache.start();
  }

  public PlayCacheAgent()
  {
    init();
  }

  private void _clearCache()
  {
    Iterator localIterator = this.mapDownloadedNodes.entrySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)((Map.Entry)localIterator.next()).getKey();
      String str2 = str1 + ".cache";
      QTRadioDownloadAgent.getInstance().deleteDownLoadFile(str2);
    }
    this.mapDownloadedNodes.clear();
  }

  private void _smartClearCache()
  {
    int i = 0;
    if (this.mapDownloadedNodes.size() == 0);
    while (true)
    {
      return;
      Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
      {
        ProgramNode localProgramNode = (ProgramNode)localNode;
        ArrayList localArrayList = new ArrayList();
        while (localProgramNode != null)
        {
          if (this.mapDownloadedNodes.get(Integer.valueOf(localProgramNode.resId)) != null)
            localArrayList.add(Integer.valueOf(localProgramNode.resId));
          localProgramNode = (ProgramNode)localProgramNode.nextSibling;
        }
        Iterator localIterator = this.mapDownloadedNodes.entrySet().iterator();
        if (localIterator.hasNext())
        {
          int j = ((Integer)((Map.Entry)localIterator.next()).getKey()).intValue();
          for (int k = 0; ; k++)
            if ((k >= localArrayList.size()) || (((Integer)localArrayList.get(k)).intValue() == j))
            {
              if (k < localArrayList.size())
                break;
              QTRadioDownloadAgent.getInstance().deleteDownLoadFile(String.valueOf(j) + ".cache");
              break;
            }
        }
        this.mapDownloadedNodes.clear();
        while (i < localArrayList.size())
        {
          addCacheByName(localArrayList.get(i) + ".cache");
          i++;
        }
      }
    }
  }

  private void clearCache()
  {
    if (this.cacheHandler.hasMessages(2))
      this.cacheHandler.removeMessages(2);
    this.cacheHandler.sendEmptyMessageDelayed(2, 500L);
  }

  public static PlayCacheAgent getInstance()
  {
    if (_instance == null)
      _instance = new PlayCacheAgent();
    return _instance;
  }

  private void selectCache()
  {
    if (this.cacheHandler.hasMessages(0))
      this.cacheHandler.removeMessages(0);
    this.cacheHandler.sendEmptyMessageDelayed(0, 500L);
  }

  private void selectDownloadingNodes(ProgramNode paramProgramNode)
  {
    if (paramProgramNode == null)
      break label4;
    while (true)
    {
      label4: return;
      if ((this.mapDownloadedNodes.get(paramProgramNode) == null) && (this.mapDownloadingNodes.get(paramProgramNode) == null))
      {
        this.mapDownloadingNodes.clear();
        this.lstDownloadingNodes.clear();
        int i = paramProgramNode.getDuration();
        int j = this.MAX_DURATION;
        int k = 0;
        if (i > j)
          break;
        while ((paramProgramNode != null) && (k < this.MAX_CACHE_DURATION) && (paramProgramNode.getCurrPlayStatus() == 3))
        {
          k += paramProgramNode.getDuration();
          if (this.mapDownloadedNodes.get(Integer.valueOf(paramProgramNode.resId)) == null)
          {
            this.lstDownloadingNodes.add(paramProgramNode);
            this.mapDownloadingNodes.put(Integer.valueOf(paramProgramNode.resId), paramProgramNode.getDownLoadUrlPath());
          }
          paramProgramNode = paramProgramNode.getNextSibling();
        }
      }
    }
  }

  private void smartClearCache()
  {
    if (this.cacheHandler.hasMessages(3))
      this.cacheHandler.removeMessages(3);
    this.cacheHandler.sendEmptyMessageDelayed(3, 500L);
  }

  private void startCache()
  {
    if (this.cacheHandler.hasMessages(1))
      this.cacheHandler.removeMessages(1);
    this.cacheHandler.sendEmptyMessageDelayed(1, 5000L);
  }

  private boolean startCache(ProgramNode paramProgramNode)
  {
    this.mNode = paramProgramNode;
    selectCache();
    return true;
  }

  private boolean startDownLoad(Node paramNode)
  {
    if (paramNode == null)
      return false;
    if (hasInDownloadList(paramNode))
      return false;
    if (!paramNode.nodeName.equalsIgnoreCase("program"))
      return false;
    if (((ProgramNode)paramNode).channelType != 1)
      return false;
    if (((ProgramNode)paramNode).getCurrPlayStatus() != 3)
      return false;
    int i = ((ProgramNode)paramNode).getDuration();
    String str1 = ((ProgramNode)paramNode).getDownLoadUrlPath();
    if (((ProgramNode)paramNode).downloadInfo == null)
      ((ProgramNode)paramNode).downloadInfo = new Download();
    if (((ProgramNode)paramNode).downloadInfo != null)
    {
      ((ProgramNode)paramNode).downloadInfo.type = 1;
      ((ProgramNode)paramNode).downloadInfo.downloadPath = str1;
      ((ProgramNode)paramNode).downloadInfo.fileSize = (125 * (24 * ((ProgramNode)paramNode).getDuration()));
    }
    String str2 = ((ProgramNode)paramNode).getNextDownLoadUrl();
    if ((str2 == null) || (str2.equalsIgnoreCase("")))
      return false;
    String str3 = String.valueOf(((ProgramNode)paramNode).resId);
    this.mapDownloadingNodes.put(Integer.valueOf(((ProgramNode)paramNode).resId), str2);
    int j = 125 * (i * 24);
    String str4 = str3 + ".cache";
    Downloadobject localDownloadobject = new Downloadobject(str4, str4, str2);
    localDownloadobject.setFileSize(j);
    QTRadioDownloadAgent.getInstance().createTask(localDownloadobject);
    return true;
  }

  private void startDownload(int paramInt)
  {
    for (int i = 0; ; i++)
    {
      if (i < this.lstDownloadingNodes.size())
      {
        if (((ProgramNode)this.lstDownloadingNodes.get(i)).resId != paramInt)
          continue;
        if (!startDownLoad((Node)this.lstDownloadingNodes.get(i)))
        {
          this.mapDownloadingNodes.remove(Integer.valueOf(paramInt));
          this.lstDownloadingNodes.remove(i);
        }
      }
      return;
    }
  }

  private void useCache(int paramInt)
  {
    if (PlayerAgent.getInstance().isPlaying())
    {
      Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
      if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")) && (paramInt == ((ProgramNode)localNode).resId))
      {
        String str = (String)this.mapDownloadedNodes.get(Integer.valueOf(paramInt));
        if ((str != null) && (!str.equalsIgnoreCase("")))
          PlayerAgent.getInstance().playCurrCache(paramInt, str);
      }
    }
  }

  public void addCacheByName(String paramString)
  {
    if (paramString == null);
    String str1;
    do
    {
      String[] arrayOfString;
      do
      {
        return;
        arrayOfString = paramString.split("\\.");
      }
      while ((arrayOfString == null) || (arrayOfString.length == 0));
      str1 = arrayOfString[0];
    }
    while ((this.mapDownloadedNodes.containsKey(str1)) || (str1 == null) || (str1.equalsIgnoreCase("")));
    String str2 = "file://" + QTRadioDownloadAgent.getInstance().getDownLoadPath();
    String str3 = str2 + "/";
    String str4 = str3 + str1;
    String str5 = str4 + ".cache";
    this.mapDownloadedNodes.put(Integer.valueOf(str1), str5);
  }

  public boolean cacheNode(Node paramNode)
  {
    if ((paramNode != null) && (paramNode.nodeName.equalsIgnoreCase("program")));
    return false;
  }

  public void delCache()
  {
    clearCache();
  }

  public void delDownloading(String paramString, boolean paramBoolean)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    do
    {
      return;
      this.mapDownloadingNodes.remove(paramString);
      QTRadioDownloadAgent.getInstance().deleteTask(paramString, paramBoolean);
    }
    while (!paramBoolean);
    QTRadioDownloadAgent.getInstance().deleteDownLoadFile(paramString);
  }

  public String getCache(ProgramNode paramProgramNode)
  {
    String str;
    if (paramProgramNode == null)
      str = null;
    do
    {
      return str;
      str = (String)this.mapDownloadedNodes.get(Integer.valueOf(paramProgramNode.resId));
    }
    while (str == null);
    MobclickAgent.onEvent(InfoManager.getInstance().getContext(), "HitPlayCache");
    return str;
  }

  public long getCacheSize()
  {
    long l1 = 0L;
    Iterator localIterator = this.mapDownloadedNodes.entrySet().iterator();
    File localFile;
    if (localIterator.hasNext())
    {
      int i = ((Integer)((Map.Entry)localIterator.next()).getKey()).intValue();
      String str = i + ".cache";
      localFile = new File(QTRadioDownloadAgent.getInstance().getDownLoadPath() + "/" + str);
      if (localFile == null)
        break label131;
    }
    label131: for (long l2 = l1 + localFile.length(); ; l2 = l1)
    {
      l1 = l2;
      break;
      return l1;
    }
  }

  public String getDownloadedProgramSource(String paramString)
  {
    if (paramString == null)
      return null;
    return (String)this.mapDownloadedNodes.get(paramString);
  }

  public boolean hasCached(ProgramNode paramProgramNode)
  {
    if (paramProgramNode == null)
      return false;
    String str = (String)this.mapDownloadedNodes.get(Integer.valueOf(paramProgramNode.resId));
    return (str != null) && (!str.equalsIgnoreCase(""));
  }

  public boolean hasInDownloadList(Node paramNode)
  {
    if (paramNode == null);
    while ((!paramNode.nodeName.equalsIgnoreCase("program")) || (this.mapDownloadedNodes.get(Integer.valueOf(((ProgramNode)paramNode).resId)) == null))
      return false;
    return true;
  }

  public boolean hasInDownloadingNodes(Node paramNode)
  {
    if (paramNode == null);
    while ((!paramNode.nodeName.equalsIgnoreCase("program")) || (this.mapDownloadingNodes.get(Integer.valueOf(((ProgramNode)paramNode).resId)) == null))
      return false;
    return true;
  }

  public void init()
  {
    QTRadioDownloadAgent.getInstance().addListener(this);
  }

  public boolean isSDCardAvailable()
  {
    return QTRadioDownloadAgent.getInstance().isSDCardAvailable();
  }

  public void onDownloadFailed(String paramString)
  {
    if (paramString == null);
    while (!paramString.endsWith(".cache"))
      return;
    this.mapDownloadingNodes.clear();
    this.lstDownloadingNodes.clear();
    delDownloading(paramString, true);
  }

  public void onDownloadProcessing(String paramString, int paramInt)
  {
  }

  public void onDownloadSuccess(String paramString)
  {
    if (paramString == null);
    String str3;
    String[] arrayOfString;
    do
    {
      File localFile;
      do
      {
        do
          return;
        while (!paramString.endsWith(".cache"));
        String str1 = "file://" + QTRadioDownloadAgent.getInstance().getDownLoadPath();
        String str2 = str1 + "/";
        str3 = str2 + paramString;
        localFile = new File(QTRadioDownloadAgent.getInstance().getDownLoadPath() + "/" + paramString);
      }
      while ((localFile != null) && (localFile.exists()) && (localFile.length() < 1000L));
      arrayOfString = paramString.split("\\.");
    }
    while ((arrayOfString == null) || (arrayOfString.length == 0));
    int i = Integer.valueOf(arrayOfString[0]).intValue();
    this.mapDownloadedNodes.put(Integer.valueOf(i), str3);
    this.mapDownloadingNodes.remove(Integer.valueOf(i));
    for (int j = 0; ; j++)
      if (j < this.lstDownloadingNodes.size())
      {
        if (((ProgramNode)this.lstDownloadingNodes.get(j)).resId == i)
          this.lstDownloadingNodes.remove(j);
      }
      else
      {
        useCache(i);
        startCache();
        return;
      }
  }

  public void pauseDownLoad(Node paramNode, boolean paramBoolean)
  {
    if ((paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("program")));
    String str;
    do
    {
      return;
      int i = ((ProgramNode)paramNode).resId;
      str = (String)this.mapDownloadingNodes.get(Integer.valueOf(i));
    }
    while (str == null);
    QTRadioDownloadAgent.getInstance().pauseTask(str);
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

  public void resumeDownLoad(Node paramNode)
  {
    if ((paramNode == null) || (!paramNode.nodeName.equalsIgnoreCase("program")));
    String str;
    do
    {
      return;
      int i = ((ProgramNode)paramNode).resId;
      str = (String)this.mapDownloadingNodes.get(Integer.valueOf(i));
    }
    while (str == null);
    QTRadioDownloadAgent.getInstance().resumeTask(str);
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

  public class CacheHandler extends Handler
  {
    public CacheHandler(Looper arg2)
    {
      super();
    }

    public void handleMessage(Message paramMessage)
    {
      if (paramMessage == null);
      while (true)
      {
        return;
        try
        {
          switch (paramMessage.what)
          {
          case 0:
            PlayCacheAgent.this.selectDownloadingNodes(PlayCacheAgent.this.mNode);
            PlayCacheAgent.this.startCache();
            return;
          case 1:
            if (PlayCacheAgent.this.lstDownloadingNodes.size() > 0)
            {
              ProgramNode localProgramNode = (ProgramNode)PlayCacheAgent.this.lstDownloadingNodes.get(0);
              PlayCacheAgent.this.startDownload(localProgramNode.resId);
              return;
            }
            break;
          case 2:
            PlayCacheAgent.this._clearCache();
            return;
          case 3:
            PlayCacheAgent.this._smartClearCache();
            return;
          }
        }
        catch (Exception localException)
        {
        }
      }
    }
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
 * Qualified Name:     fm.qingting.qtradio.fm.PlayCacheAgent
 * JD-Core Version:    0.6.2
 */