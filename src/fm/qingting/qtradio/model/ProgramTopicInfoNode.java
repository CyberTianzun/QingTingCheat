package fm.qingting.qtradio.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramTopicInfoNode extends Node
{
  private Map<String, List<Node>> mapProgramTopics = new HashMap();

  public ProgramTopicInfoNode()
  {
    this.nodeName = "programtopicinfo";
  }

  private Node getCurrentProgramTopicById(String paramString1, String paramString2, long paramLong)
  {
    Object localObject1 = null;
    if (paramString1 != null)
    {
      localObject1 = null;
      if (paramString2 != null)
        break label17;
    }
    label17: int i;
    List localList;
    do
    {
      boolean bool;
      do
      {
        return localObject1;
        i = -1;
        bool = this.mapProgramTopics.containsKey(paramString1);
        localObject1 = null;
      }
      while (!bool);
      localList = (List)this.mapProgramTopics.get(paramString1);
      localObject1 = null;
    }
    while (localList == null);
    int j = 0;
    label66: Object localObject2;
    int k;
    if (j < localList.size())
    {
      Node localNode = (Node)localList.get(j);
      if (!((ProgramTopicNode)localNode).isValid(paramString1, paramString2, paramLong))
        break label145;
      int m = ((ProgramTopicNode)localNode).getScore();
      if (m <= i)
        break label145;
      localObject2 = localNode;
      k = m;
    }
    while (true)
    {
      j++;
      localObject1 = localObject2;
      i = k;
      break label66;
      break;
      label145: k = i;
      localObject2 = localObject1;
    }
  }

  private void updateProgramTopic(List<Node> paramList, String paramString)
  {
    if ((paramString == null) || (paramList == null));
    List localList;
    do
    {
      return;
      if (!this.mapProgramTopics.containsKey(paramString))
        break;
      localList = (List)this.mapProgramTopics.get(paramString);
    }
    while (localList == null);
    int i = 0;
    label45: ProgramTopicNode localProgramTopicNode;
    if (i < paramList.size())
      localProgramTopicNode = (ProgramTopicNode)paramList.get(i);
    for (int j = 0; ; j++)
      if ((j >= localList.size()) || (((ProgramTopicNode)localList.get(j)).updateTopicInfo(localProgramTopicNode)))
      {
        if (j == localList.size())
          localList.add(localProgramTopicNode.cloneNode());
        i++;
        break label45;
        break;
      }
    this.mapProgramTopics.put(paramString, paramList);
  }

  public Node getCurrentProgramTopic(String paramString1, String paramString2, long paramLong)
  {
    if ((paramString1 == null) || (paramString2 == null));
    Node localNode;
    do
    {
      return null;
      localNode = getCurrentProgramTopicById(paramString2, paramString1, paramLong);
    }
    while (localNode == null);
    return localNode;
  }

  public boolean isExist(String paramString)
  {
    return this.mapProgramTopics.containsKey(paramString);
  }

  public void onNodeUpdated(Object paramObject, String paramString)
  {
    List localList = (List)paramObject;
    if ((localList == null) || (paramString == null));
    while ((!paramString.equalsIgnoreCase("ACPT")) || (localList.size() == 0))
      return;
    updateProgramTopic(localList, ((ProgramTopicNode)localList.get(0)).channelId);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ProgramTopicInfoNode
 * JD-Core Version:    0.6.2
 */