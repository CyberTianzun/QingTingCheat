package fm.qingting.qtradio.model;

import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.fmdriver.FMManager;
import fm.qingting.qtradio.fmdriver.IFMEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadioNode extends Node
  implements IFMEventListener, InfoManager.INodeEventListener
{
  private boolean hasRefreshRadio = false;
  private boolean hasRestoreByCity = false;
  private boolean hasRestoreFromDB = false;
  public transient List<Integer> lstFreqs = new ArrayList();
  public FreqChannelInfoNode mFreqChannelInfoNode = new FreqChannelInfoNode();
  public transient List<Node> mLstChannelNodes = new ArrayList();
  public String mTitle = "系统收音机";
  private Map<String, List<IRadioEventListener>> mapSubscribeEventListeners = new HashMap();

  public RadioNode()
  {
    this.nodeName = "radio";
    init();
  }

  private void addFreq(int paramInt)
  {
    this.lstFreqs.add(Integer.valueOf(paramInt));
    delDefaultNode();
    RadioChannelNode localRadioChannelNode = new RadioChannelNode();
    localRadioChannelNode.parent = this;
    localRadioChannelNode.freq = String.valueOf(paramInt);
    localRadioChannelNode.channelName = ("FM" + String.valueOf(Float.valueOf((float)(0.001D * paramInt))));
    this.mLstChannelNodes.add(localRadioChannelNode);
    if (this.parent == null)
      this.parent = InfoManager.getInstance().root().mContentCategory.mLiveNode;
  }

  private void arrangeRadios()
  {
    if (this.mLstChannelNodes == null);
    int i;
    int k;
    label22: RadioChannelNode localRadioChannelNode1;
    RadioChannelNode localRadioChannelNode2;
    do
    {
      return;
      i = 0;
      int j = this.mLstChannelNodes.size();
      k = 0;
      if (k >= j)
        break;
      localRadioChannelNode1 = (RadioChannelNode)this.mLstChannelNodes.get(k);
      localRadioChannelNode2 = (RadioChannelNode)this.mLstChannelNodes.get(i);
    }
    while (localRadioChannelNode1 == null);
    int m;
    if (!localRadioChannelNode1.channelName.startsWith("FM"))
    {
      if (k != i)
      {
        this.mLstChannelNodes.set(k, localRadioChannelNode2);
        this.mLstChannelNodes.set(i, localRadioChannelNode1);
      }
      m = k + 1;
    }
    for (int n = i + 1; ; n = i)
    {
      i = n;
      k = m;
      break label22;
      break;
      m = k + 1;
    }
  }

  private void delDefaultNode()
  {
    if ((this.mLstChannelNodes == null) || (this.mLstChannelNodes.size() == 0));
    while (((RadioChannelNode)this.mLstChannelNodes.get(0)).channelId != 0)
      return;
    this.mLstChannelNodes.remove(0);
  }

  private void dispatchSubscribeEvent(String paramString)
  {
    if (this.mapSubscribeEventListeners.containsKey(paramString))
    {
      List localList = (List)this.mapSubscribeEventListeners.get(paramString);
      int i = localList.size();
      for (int j = 0; j < i; j++)
        ((IRadioEventListener)localList.get(j)).onRadioNotification(paramString);
    }
  }

  private void init()
  {
    FMManager.getInstance().addListener(this);
  }

  private boolean isExisted(int paramInt)
  {
    for (int i = 0; ; i++)
    {
      int j = this.lstFreqs.size();
      boolean bool = false;
      if (i < j)
      {
        if (paramInt == ((Integer)this.lstFreqs.get(i)).intValue())
          bool = true;
      }
      else
        return bool;
    }
  }

  private void registerRadioEventListener(IRadioEventListener paramIRadioEventListener, String paramString)
  {
    List localList;
    if ((paramIRadioEventListener != null) && (paramString != null))
    {
      if (this.mapSubscribeEventListeners.containsKey(paramString))
        localList = (List)this.mapSubscribeEventListeners.get(paramString);
    }
    else
    {
      for (int i = 0; i < localList.size(); i++)
        if (localList.get(i) == paramIRadioEventListener)
          return;
      ((List)this.mapSubscribeEventListeners.get(paramString)).add(paramIRadioEventListener);
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramIRadioEventListener);
    this.mapSubscribeEventListeners.put(paramString, localArrayList);
  }

  public void addDefaultNode()
  {
    if ((this.mLstChannelNodes == null) || (this.mLstChannelNodes.size() > 0));
    do
    {
      return;
      RadioChannelNode localRadioChannelNode = new RadioChannelNode();
      localRadioChannelNode.channelId = 0;
      localRadioChannelNode.channelName = "暂无免流量电台";
      localRadioChannelNode.freq = String.valueOf("97700");
      localRadioChannelNode.parent = this;
      this.mLstChannelNodes.add(localRadioChannelNode);
    }
    while (this.parent != null);
    this.parent = InfoManager.getInstance().root().mContentCategory.mLiveNode;
  }

  public void addLocalChannels(List<Node> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0));
    while (true)
    {
      return;
      if ((this.mLstChannelNodes != null) && (!hasAvailableChannel()))
      {
        delDefaultNode();
        this.mLstChannelNodes.clear();
        for (int i = 0; i < paramList.size(); i++)
          if (((Node)paramList.get(i)).nodeName.equalsIgnoreCase("channel"))
          {
            RadioChannelNode localRadioChannelNode = new RadioChannelNode();
            localRadioChannelNode.parent = this;
            localRadioChannelNode.freq = String.valueOf((int)(1000.0F * Float.valueOf(((ChannelNode)paramList.get(i)).freq).floatValue()));
            localRadioChannelNode.channelName = ((ChannelNode)paramList.get(i)).title;
            this.mLstChannelNodes.add(localRadioChannelNode);
          }
      }
    }
  }

  public void deleteFreqInDB()
  {
    DataManager.getInstance().getData("deletedb_radio_node", null, null);
  }

  public boolean hasAvailableChannel()
  {
    if ((this.mLstChannelNodes == null) || (this.mLstChannelNodes.size() == 0))
      return false;
    return ((RadioChannelNode)this.mLstChannelNodes.get(0)).channelId != 0;
  }

  public boolean isAvailable()
  {
    return FMManager.getInstance().isAvailable();
  }

  public void loadRadioInfo(IRadioEventListener paramIRadioEventListener)
  {
    if (this.lstFreqs.size() == 0)
      return;
    String str1 = "";
    int k;
    for (int i = 0; ; i = k)
    {
      int j = ((Integer)this.lstFreqs.get(i)).intValue();
      String str2 = str1 + String.valueOf(Float.valueOf((float)(0.001D * j)));
      k = i + 1;
      if (k >= this.lstFreqs.size())
      {
        InfoManager.getInstance().loadRadioInfo(str2);
        if (paramIRadioEventListener == null)
          break;
        registerRadioEventListener(paramIRadioEventListener, "RRI");
        return;
      }
      str1 = str2 + ",";
    }
  }

  public void loadRadioNodes(IRadioEventListener paramIRadioEventListener)
  {
    PlayerAgent.getInstance().stop();
    FMManager.getInstance().scan();
    if (paramIRadioEventListener != null)
    {
      registerRadioEventListener(paramIRadioEventListener, "RRAC");
      registerRadioEventListener(paramIRadioEventListener, "RRACC");
      registerRadioEventListener(paramIRadioEventListener, "RHP");
      registerRadioEventListener(paramIRadioEventListener, "RHUP");
    }
  }

  public void onAudioQualityStatus(int paramInt)
  {
  }

  public void onChannelFound(int paramInt)
  {
    if (!isExisted(paramInt))
    {
      this.hasRefreshRadio = true;
      addFreq(paramInt);
      dispatchSubscribeEvent("RRAC");
    }
  }

  public void onFMOff()
  {
  }

  public void onFMOn()
  {
  }

  public void onHeadsetPlugged()
  {
    dispatchSubscribeEvent("RHP");
  }

  public void onHeadsetUnplugged()
  {
    dispatchSubscribeEvent("RHUP");
  }

  public void onNodeUpdated(Object paramObject, String paramString)
  {
    Node localNode = (Node)paramObject;
    if ((localNode == null) || (paramString == null));
    while ((!paramString.equalsIgnoreCase("AFC")) || (!localNode.nodeName.equalsIgnoreCase("freqchannelinfo")))
      return;
    this.mFreqChannelInfoNode = ((FreqChannelInfoNode)localNode);
  }

  public void onScanComplete(boolean paramBoolean)
  {
    addDefaultNode();
    dispatchSubscribeEvent("RRACC");
  }

  public void onScanStarted()
  {
  }

  public void onTune(int paramInt)
  {
  }

  public void restoreFromDB()
  {
    if (this.hasRestoreFromDB);
    do
    {
      return;
      this.hasRestoreFromDB = true;
      Result localResult = DataManager.getInstance().getData("getdb_radio_node", null, null).getResult();
      boolean bool = localResult.getSuccess();
      List localList = null;
      if (bool)
        localList = (List)localResult.getData();
      if ((localList != null) && (localList.size() != 0))
        this.mLstChannelNodes = localList;
    }
    while ((this.mLstChannelNodes == null) || (this.mLstChannelNodes.size() == 0));
    for (int i = 0; i < this.mLstChannelNodes.size(); i++)
    {
      ((Node)this.mLstChannelNodes.get(i)).parent = this;
      this.lstFreqs.add(Integer.valueOf(((RadioChannelNode)this.mLstChannelNodes.get(i)).freq));
    }
    arrangeRadios();
  }

  public void restoreFromDBByCity(String paramString)
  {
    if ((paramString == null) || (this.hasRestoreByCity));
    while (true)
    {
      return;
      if (!hasAvailableChannel())
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("city", paramString);
        Result localResult = DataManager.getInstance().getData("getdb_freq_channels", null, localHashMap).getResult();
        if (localResult.getSuccess());
        for (List localList = (List)localResult.getData(); (localList != null) && (localList.size() != 0); localList = null)
        {
          this.mLstChannelNodes.clear();
          int i = 0;
          try
          {
            while (i < localList.size())
            {
              RadioChannelNode localRadioChannelNode = new RadioChannelNode();
              localRadioChannelNode.parent = this;
              localRadioChannelNode.freq = String.valueOf(Integer.valueOf((int)(1000.0F * Float.valueOf(((FreqChannel)localList.get(i)).channelFreq).floatValue())));
              localRadioChannelNode.channelName = ((FreqChannel)localList.get(i)).channelName;
              localRadioChannelNode.channelId = ((FreqChannel)localList.get(i)).channelId;
              this.mLstChannelNodes.add(localRadioChannelNode);
              this.lstFreqs.add(Integer.valueOf(localRadioChannelNode.freq));
              i++;
            }
            this.hasRestoreByCity = true;
            return;
          }
          catch (Exception localException)
          {
            return;
          }
        }
      }
    }
  }

  public void saveFreqChannelToDB()
  {
    if (this.mFreqChannelInfoNode == null)
      return;
    HashMap localHashMap = new HashMap();
    localHashMap.put("freqs", this.mFreqChannelInfoNode);
    DataManager.getInstance().getData("insertdb_freq_channels", null, localHashMap);
  }

  public void saveToDB()
  {
    if ((this.mLstChannelNodes == null) || (this.mLstChannelNodes.size() == 0));
    while ((!hasAvailableChannel()) || (!this.hasRefreshRadio))
      return;
    delDefaultNode();
    deleteFreqInDB();
    HashMap localHashMap = new HashMap();
    localHashMap.put("nodes", this.mLstChannelNodes);
    DataManager.getInstance().getData("insertdb_radio_node", null, localHashMap);
  }

  public void updateRadioInfo(List<Node> paramList)
  {
    if (paramList == null)
      return;
    int i = 0;
    if (i < paramList.size())
    {
      String str = ((RadioChannelNode)paramList.get(i)).freq;
      for (int j = 0; ; j++)
        if (j < this.mLstChannelNodes.size())
        {
          if (((RadioChannelNode)this.mLstChannelNodes.get(j)).freq.equalsIgnoreCase(str))
          {
            ((RadioChannelNode)this.mLstChannelNodes.get(j)).channelName = ((RadioChannelNode)paramList.get(i)).channelName;
            ((RadioChannelNode)this.mLstChannelNodes.get(j)).channelId = ((RadioChannelNode)paramList.get(i)).channelId;
          }
        }
        else
        {
          i++;
          break;
        }
    }
    arrangeRadios();
    dispatchSubscribeEvent("RRI");
  }

  public static abstract interface IRadioEventListener
  {
    public static final String RECV_HEADSET_PLUGGED = "RHP";
    public static final String RECV_HEADSET_UNPLUGGED = "RHUP";
    public static final String RECV_RADIO_CHANNEL = "RRAC";
    public static final String RECV_RADIO_CHANNEL_COMPLETE = "RRACC";
    public static final String RECV_RADIO_INFO = "RRI";

    public abstract void onRadioNotification(String paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.RadioNode
 * JD-Core Version:    0.6.2
 */