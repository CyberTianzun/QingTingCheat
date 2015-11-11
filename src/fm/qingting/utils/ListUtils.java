package fm.qingting.utils;

import java.util.List;

public class ListUtils
{
  public static List<Object> convertToObjectList(List<?> paramList)
  {
    try
    {
      List localList = (List)paramList;
      return localList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.ListUtils
 * JD-Core Version:    0.6.2
 */