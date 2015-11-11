package fm.qingting.qtradio.playlist;

import android.os.Handler;
import android.os.Message;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.helper.ProgramHelper;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.ProgramScheduleList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayListManager
{
  private static PlayListManager _instance;
  private transient boolean hasRestoredItem = false;
  private transient boolean hasRestoredSuccess = false;
  private List<Node> mLstPlayNodes;
  private int mPlayId = 0;
  private transient boolean needToWrite = false;

  public static PlayListManager getInstance()
  {
    if (_instance == null)
      _instance = new PlayListManager();
    return _instance;
  }

  private void log(String paramString)
  {
  }

  public void asyncUpdate()
  {
    Message localMessage = new Message();
    localMessage.what = 10;
    localMessage.obj = this;
    InfoManager.getInstance().getDataStoreHandler().removeMessages(localMessage.what);
    InfoManager.getInstance().getDataStoreHandler().sendMessageDelayed(localMessage, 2000L);
  }

  public List<Node> getPlayList()
  {
    if (this.mLstPlayNodes == null)
    {
      restoreFromDB();
      if (this.mLstPlayNodes == null)
        this.mLstPlayNodes = new ArrayList();
    }
    return this.mLstPlayNodes;
  }

  public void init()
  {
    restoreFromDB();
  }

  public boolean restoreFromDB()
  {
    int i = 0;
    Object localObject = null;
    Result localResult = DataManager.getInstance().getData("getdb_playlist", null, null).getResult();
    if ((localResult != null) && (localResult.getSuccess()));
    for (List localList = (List)localResult.getData(); ; localList = null)
    {
      if ((localList == null) || (localList.size() == 0))
        return false;
      this.mLstPlayNodes = localList;
      while (i < this.mLstPlayNodes.size())
      {
        if (localObject != null)
        {
          localObject.nextSibling = ((Node)this.mLstPlayNodes.get(i));
          ((Node)this.mLstPlayNodes.get(i)).prevSibling = localObject;
        }
        Node localNode = (Node)this.mLstPlayNodes.get(i);
        i++;
        localObject = localNode;
      }
      return true;
    }
  }

  public void setPlayList(Node paramNode, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramNode == null);
    do
    {
      return;
      if (!paramNode.nodeName.equalsIgnoreCase("program"))
        break;
    }
    while (this.mPlayId == ((ProgramNode)paramNode).id);
    this.mPlayId = ((ProgramNode)paramNode).id;
    while (true)
    {
      if ((paramNode.nextSibling == null) && (paramNode.prevSibling == null) && (!paramBoolean) && (paramNode.nodeName.equalsIgnoreCase("program")))
      {
        ProgramScheduleList localProgramScheduleList = ProgramHelper.getInstance().getProgramSchedule(((ProgramNode)paramNode).channelId, ((ProgramNode)paramNode).channelType, true);
        if (localProgramScheduleList != null)
        {
          ProgramNode localProgramNode = localProgramScheduleList.getProgramNode(((ProgramNode)paramNode).id);
          if (localProgramNode != null)
            paramNode = localProgramNode;
        }
      }
      if (this.mLstPlayNodes == null)
        this.mLstPlayNodes = new ArrayList();
      this.mLstPlayNodes.clear();
      int i = 0;
      Node localNode1 = paramNode;
      while ((localNode1 != null) && (i < paramInt2))
      {
        this.mLstPlayNodes.add(localNode1);
        localNode1 = localNode1.nextSibling;
        i++;
      }
      if (paramNode.nodeName.equalsIgnoreCase("channel"))
      {
        if (this.mPlayId == paramInt1)
          break;
        this.mPlayId = paramInt1;
      }
    }
    Node localNode2 = paramNode.prevSibling;
    for (int j = 0; (localNode2 != null) && (j < paramInt2); j++)
    {
      this.mLstPlayNodes.add(0, localNode2);
      localNode2 = localNode2.prevSibling;
    }
    this.needToWrite = true;
    if (paramBoolean)
    {
      updateToDB(false);
      return;
    }
    asyncUpdate();
  }

  public void updateToDB(boolean paramBoolean)
  {
    if ((this.mLstPlayNodes == null) || (!this.needToWrite));
    do
    {
      return;
      this.needToWrite = false;
      log("updateToDB: " + this.mPlayId);
      HashMap localHashMap = new HashMap();
      localHashMap.put("nodes", this.mLstPlayNodes);
      DataManager.getInstance().getData("updatedb_playlist", null, localHashMap);
    }
    while (!paramBoolean);
    InfoManager.getInstance().sendPlayList("playid", String.valueOf(this.mPlayId));
    InfoManager.getInstance().sendPlayList("playlistupdate", "true");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.playlist.PlayListManager
 * JD-Core Version:    0.6.2
 */