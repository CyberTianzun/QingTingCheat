package fm.qingting.utils;

import fm.qingting.qtradio.model.Node;
import java.util.Collections;
import java.util.List;

public class FuncUtils
{
  public static void revertNodesList(Object paramObject)
  {
    if (paramObject == null);
    while (true)
    {
      return;
      List localList = (List)paramObject;
      if (localList.size() > 0)
      {
        Collections.reverse(localList);
        Node localNode1 = (Node)localList.get(0);
        localNode1.prevSibling = null;
        int i = 1;
        Node localNode2;
        for (Object localObject = localNode1; i < localList.size(); localObject = localNode2)
        {
          ((Node)localObject).nextSibling = ((Node)localList.get(i));
          ((Node)localList.get(i)).prevSibling = ((Node)localObject);
          localNode2 = (Node)localList.get(i);
          localNode2.nextSibling = null;
          i++;
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.FuncUtils
 * JD-Core Version:    0.6.2
 */