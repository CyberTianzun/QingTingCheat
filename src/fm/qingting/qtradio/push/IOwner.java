package fm.qingting.qtradio.push;

import fm.qingting.qtradio.push.bean.PushBean;
import java.util.List;

public abstract interface IOwner
{
  public abstract void informComplete(List<PushBean> paramList);
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.push.IOwner
 * JD-Core Version:    0.6.2
 */