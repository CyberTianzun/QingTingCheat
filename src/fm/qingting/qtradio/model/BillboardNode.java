package fm.qingting.qtradio.model;

import android.os.Handler;
import android.os.Message;
import fm.qingting.framework.data.DataManager;
import fm.qingting.framework.data.IResultToken;
import fm.qingting.framework.data.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillboardNode extends Node
{
  private transient boolean hasRestoredChannelItems = false;
  private transient boolean hasRestoredChannelSuccess = false;
  private transient boolean hasRestoredProgramItems = false;
  private transient boolean hasRestoredProgramSuccess = false;
  private transient List<BillboardItemNode> mLstChannelNodes;
  private transient List<BillboardItemNode> mLstProgramNodes;

  public BillboardNode()
  {
    this.nodeName = "billboard";
  }

  public List<BillboardItemNode> getLstBillboardChannel()
  {
    return this.mLstChannelNodes;
  }

  public List<BillboardItemNode> getLstBillboardProgram()
  {
    return this.mLstProgramNodes;
  }

  public void onNodeUpdated(Object paramObject, String paramString)
  {
    if (paramString == null);
    List localList1;
    do
    {
      do
      {
        List localList2;
        do
        {
          return;
          if (!paramString.equalsIgnoreCase("ABCS"))
            break;
          localList2 = (List)paramObject;
        }
        while ((localList2 == null) || (localList2.size() == 0));
        this.mLstChannelNodes = localList2;
        for (int j = 0; j < this.mLstChannelNodes.size(); j++)
          ((BillboardItemNode)this.mLstChannelNodes.get(j)).parent = this;
        Message localMessage2 = new Message();
        localMessage2.what = 1;
        localMessage2.obj = this;
        InfoManager.getInstance().getDataStoreHandler().sendMessage(localMessage2);
        return;
      }
      while (!paramString.equalsIgnoreCase("ABPS"));
      localList1 = (List)paramObject;
    }
    while ((localList1 == null) || (localList1.size() == 0));
    this.mLstProgramNodes = localList1;
    for (int i = 0; i < this.mLstProgramNodes.size(); i++)
      ((BillboardItemNode)this.mLstProgramNodes.get(i)).parent = this;
    Message localMessage1 = new Message();
    localMessage1.what = 2;
    localMessage1.obj = this;
    InfoManager.getInstance().getDataStoreHandler().sendMessage(localMessage1);
  }

  public boolean restoreChannelFromDB()
  {
    int i;
    if (this.hasRestoredChannelItems)
      i = this.hasRestoredChannelSuccess;
    int j;
    do
    {
      List localList2;
      do
      {
        return i;
        this.hasRestoredChannelItems = true;
        HashMap localHashMap = new HashMap();
        localHashMap.put("type", Integer.valueOf(1));
        Result localResult = DataManager.getInstance().getData("getdb_billboarditem", null, localHashMap).getResult();
        boolean bool = localResult.getSuccess();
        List localList1 = null;
        if (bool)
          localList1 = (List)localResult.getData();
        if ((localList1 != null) && (localList1.size() != 0))
        {
          this.hasRestoredChannelSuccess = true;
          this.mLstChannelNodes = localList1;
        }
        localList2 = this.mLstChannelNodes;
        i = 0;
      }
      while (localList2 == null);
      j = this.mLstChannelNodes.size();
      i = 0;
    }
    while (j == 0);
    while (i < this.mLstChannelNodes.size())
    {
      ((BillboardItemNode)this.mLstChannelNodes.get(i)).parent = this;
      i++;
    }
    return true;
  }

  public boolean restoreProgramFromDB()
  {
    int i;
    if (this.hasRestoredProgramItems)
      i = this.hasRestoredProgramSuccess;
    int j;
    do
    {
      List localList2;
      do
      {
        return i;
        this.hasRestoredProgramItems = true;
        HashMap localHashMap = new HashMap();
        localHashMap.put("type", Integer.valueOf(2));
        Result localResult = DataManager.getInstance().getData("getdb_billboarditem", null, localHashMap).getResult();
        boolean bool = localResult.getSuccess();
        List localList1 = null;
        if (bool)
          localList1 = (List)localResult.getData();
        if ((localList1 != null) && (localList1.size() != 0))
        {
          this.hasRestoredProgramSuccess = true;
          this.mLstProgramNodes = localList1;
        }
        localList2 = this.mLstProgramNodes;
        i = 0;
      }
      while (localList2 == null);
      j = this.mLstProgramNodes.size();
      i = 0;
    }
    while (j == 0);
    while (i < this.mLstProgramNodes.size())
    {
      ((BillboardItemNode)this.mLstProgramNodes.get(i)).parent = this;
      i++;
    }
    return true;
  }

  public void updateToDB(int paramInt)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("type", Integer.valueOf(paramInt));
    if (paramInt == 1)
      localHashMap.put("nodes", this.mLstChannelNodes);
    while (true)
    {
      DataManager.getInstance().getData("updatedb_billboarditem", null, localHashMap);
      do
        return;
      while (paramInt != 2);
      localHashMap.put("nodes", this.mLstProgramNodes);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.BillboardNode
 * JD-Core Version:    0.6.2
 */