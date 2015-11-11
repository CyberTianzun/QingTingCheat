package fm.qingting.qtradio.push.task;

import android.content.Context;
import fm.qingting.qtradio.stat.PlayRecord;
import java.util.List;

public class TaskCollectNovelPush extends TaskCollectPush
{
  private Context _context;

  public TaskCollectNovelPush(Context paramContext)
  {
    super("TaskCollectNovelPush");
    this._context = paramContext;
  }

  private List<PlayRecord> mergeDuplicateNovel(List<PlayRecord> paramList)
  {
    for (int i = 0; i < paramList.size(); i++)
    {
      String str = ((PlayRecord)paramList.get(i)).cid;
      for (int j = -1 + paramList.size(); j > i; j--)
        if (((PlayRecord)paramList.get(j)).cid.equalsIgnoreCase(str))
          paramList.remove(j);
    }
    return paramList;
  }

  public void begin()
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.push.task.TaskCollectNovelPush
 * JD-Core Version:    0.6.2
 */