package fm.qingting.qtradio.model;

import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.helper.ChannelHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayHistoryInfoNode extends Node
{
  private static final int MAX_PLAY_COUNT = 20;
  private List<PlayHistoryNode> mLstPlayNodes = null;
  public String mTitle = "最近收听";
  private boolean needToWriteToDB = false;

  public PlayHistoryInfoNode()
  {
    this.nodeName = "playhistoryinfo";
  }

  private void deletePlayHistory()
  {
    DataManager.getInstance().getData("deletedb_play_history", null, null);
  }

  private int findCatIdByNode(Node paramNode)
  {
    if (paramNode == null);
    while (!paramNode.nodeName.equalsIgnoreCase("program"))
      return 0;
    return ((ProgramNode)paramNode).getCategoryId();
  }

  private String findChannelNameByNode(Node paramNode)
  {
    if (paramNode == null);
    while (!paramNode.nodeName.equalsIgnoreCase("program"))
      return null;
    return ((ProgramNode)paramNode).getChannelName();
  }

  private String findChannelThumbByNode(ProgramNode paramProgramNode)
  {
    ChannelNode localChannelNode = ChannelHelper.getInstance().getChannel(paramProgramNode.channelId, paramProgramNode.channelType);
    if (localChannelNode != null)
      return localChannelNode.getApproximativeThumb();
    return null;
  }

  public void WriteToDB()
  {
    if (!needToWriteToDB());
    while (this.mLstPlayNodes == null)
      return;
    this.needToWriteToDB = false;
    HashMap localHashMap = new HashMap();
    localHashMap.put("playhistory", this.mLstPlayNodes);
    DataManager.getInstance().getData("delinsertdb_play_history", null, localHashMap);
  }

  public void addPlayHistoryNode(Node paramNode, String paramString)
  {
    if (paramNode == null);
    do
    {
      return;
      if (this.mLstPlayNodes == null)
        this.mLstPlayNodes = new ArrayList();
    }
    while (!paramNode.nodeName.equalsIgnoreCase("program"));
    if (this.mLstPlayNodes.size() == 20)
      this.mLstPlayNodes.remove(19);
    int i = isExisted(paramNode);
    if (i != -1)
      this.mLstPlayNodes.remove(i);
    PlayHistoryNode localPlayHistoryNode = new PlayHistoryNode();
    localPlayHistoryNode.playNode = paramNode;
    localPlayHistoryNode.playTime = (System.currentTimeMillis() / 1000L);
    localPlayHistoryNode.categoryId = findCatIdByNode(paramNode);
    localPlayHistoryNode.channelName = findChannelNameByNode(paramNode);
    localPlayHistoryNode.channelId = ((ProgramNode)paramNode).channelId;
    localPlayHistoryNode.playContent = ((ProgramNode)paramNode).channelType;
    String str = findChannelThumbByNode((ProgramNode)paramNode);
    if (str == null);
    while (true)
    {
      localPlayHistoryNode.channelThumb = paramString;
      this.mLstPlayNodes.add(0, localPlayHistoryNode);
      this.needToWriteToDB = true;
      return;
      paramString = str;
    }
  }

  public void delPlayHistoryNode(Node paramNode)
  {
    if (paramNode == null);
    while ((!paramNode.nodeName.equalsIgnoreCase("program")) && (!paramNode.nodeName.equalsIgnoreCase("ondemandprogram")))
      return;
    int i = isExisted(paramNode);
    if (i != -1)
      this.mLstPlayNodes.remove(i);
    this.needToWriteToDB = true;
  }

  public void deleteAll()
  {
    if (this.mLstPlayNodes != null)
      this.mLstPlayNodes.clear();
    deletePlayHistory();
    this.needToWriteToDB = true;
  }

  public String getLatestHistoryInfo()
  {
    String str;
    if ((this.mLstPlayNodes == null) || (this.mLstPlayNodes.size() == 0))
      str = "暂无记录，快去收听喜欢的内容吧";
    PlayHistoryNode localPlayHistoryNode;
    do
    {
      return str;
      str = "";
      localPlayHistoryNode = (PlayHistoryNode)this.mLstPlayNodes.get(0);
      if ((localPlayHistoryNode.playNode != null) && (localPlayHistoryNode.playNode.nodeName.equalsIgnoreCase("program")))
        str = str + ((ProgramNode)localPlayHistoryNode.playNode).title;
    }
    while (localPlayHistoryNode.channelName == null);
    return str + "-" + localPlayHistoryNode.channelName;
  }

  public List<PlayHistoryNode> getPlayHistoryNodes()
  {
    if (this.mLstPlayNodes == null)
    {
      Result localResult = DataManager.getInstance().getData("getdb_play_history", null, null).getResult();
      if (localResult.getSuccess())
        this.mLstPlayNodes = ((List)localResult.getData());
      if (this.mLstPlayNodes == null)
        this.mLstPlayNodes = new ArrayList();
    }
    return this.mLstPlayNodes;
  }

  public Node getPlayNode(int paramInt1, int paramInt2)
  {
    if (this.mLstPlayNodes != null)
      for (int i = 0; i < this.mLstPlayNodes.size(); i++)
        if ((((PlayHistoryNode)this.mLstPlayNodes.get(i)).channelId == paramInt1) && (((PlayHistoryNode)this.mLstPlayNodes.get(i)).playNode != null))
          if (((PlayHistoryNode)this.mLstPlayNodes.get(i)).playNode.nodeName.equalsIgnoreCase("program"))
          {
            if (((ProgramNode)((PlayHistoryNode)this.mLstPlayNodes.get(i)).playNode).id == paramInt2)
              return ((PlayHistoryNode)this.mLstPlayNodes.get(i)).playNode;
          }
          else if (((PlayHistoryNode)this.mLstPlayNodes.get(i)).playNode.nodeName.equalsIgnoreCase("channel"))
            return ((PlayHistoryNode)this.mLstPlayNodes.get(i)).playNode;
    return null;
  }

  public void init()
  {
    getPlayHistoryNodes();
  }

  public int isExisted(Node paramNode)
  {
    int i;
    if (paramNode == null)
      i = -1;
    do
    {
      return i;
      if (this.mLstPlayNodes == null)
        break label194;
      i = 0;
      if (i >= this.mLstPlayNodes.size())
        break label194;
      if (((PlayHistoryNode)this.mLstPlayNodes.get(i)).playNode != null)
        break;
    }
    while (((PlayHistoryNode)this.mLstPlayNodes.get(i)).channelId == ((ProgramNode)paramNode).channelId);
    label160: 
    do
    {
      do
      {
        do
        {
          i++;
          break;
        }
        while ((!((PlayHistoryNode)this.mLstPlayNodes.get(i)).playNode.nodeName.equalsIgnoreCase(paramNode.nodeName)) || (!paramNode.nodeName.equalsIgnoreCase("program")));
        if (!((ProgramNode)paramNode).isLiveProgram())
          break label160;
      }
      while (((PlayHistoryNode)this.mLstPlayNodes.get(i)).channelId != ((ProgramNode)paramNode).channelId);
      return i;
    }
    while (((ProgramNode)((PlayHistoryNode)this.mLstPlayNodes.get(i)).playNode).channelId != ((ProgramNode)paramNode).channelId);
    return i;
    label194: return -1;
  }

  public boolean needToWriteToDB()
  {
    return this.needToWriteToDB;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.PlayHistoryInfoNode
 * JD-Core Version:    0.6.2
 */