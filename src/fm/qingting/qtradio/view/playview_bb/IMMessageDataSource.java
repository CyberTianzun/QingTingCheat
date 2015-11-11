package fm.qingting.qtradio.view.playview_bb;

import fm.qingting.qtradio.im.message.IMMessage;
import java.util.List;
import master.flame.danmaku.danmaku.parser.IDataSource;

public class IMMessageDataSource
  implements IDataSource<List<IMMessage>>
{
  private List<IMMessage> mMessages;

  public IMMessageDataSource(List<IMMessage> paramList)
  {
    this.mMessages = paramList;
  }

  public List<IMMessage> data()
  {
    return this.mMessages;
  }

  public void release()
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview_bb.IMMessageDataSource
 * JD-Core Version:    0.6.2
 */