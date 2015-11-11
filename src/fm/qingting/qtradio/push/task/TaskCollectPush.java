package fm.qingting.qtradio.push.task;

import fm.qingting.qtradio.push.IOwner;
import fm.qingting.qtradio.push.bean.PushBean;
import java.util.List;

public abstract class TaskCollectPush
{
  public IOwner owner;
  private String task_name;

  public TaskCollectPush()
  {
    this.task_name = "no_name";
  }

  public TaskCollectPush(String paramString)
  {
    this.task_name = paramString;
  }

  public abstract void begin();

  protected void inform(List<PushBean> paramList)
  {
    if (this.owner == null)
    {
      log("[Error!]owner is null.");
      return;
    }
    this.owner.informComplete(paramList);
  }

  protected void log(String paramString)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.push.task.TaskCollectPush
 * JD-Core Version:    0.6.2
 */