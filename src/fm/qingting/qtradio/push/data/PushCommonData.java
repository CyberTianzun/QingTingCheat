package fm.qingting.qtradio.push.data;

import android.content.Context;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.pushcontent.ChannelTimingDB;
import fm.qingting.qtradio.pushcontent.TimeBean;
import java.util.Map;

public class PushCommonData
{
  private static PushCommonData _ins = null;
  public Map<String, TimeBean> channelTimes = null;
  public CollectionNode collectionNode;
  public DownLoadInfoNode m_info = null;
  public ChannelTimingDB timeDB;

  private PushCommonData(Context paramContext)
  {
    init();
    this.timeDB = new ChannelTimingDB(paramContext);
    this.channelTimes = this.timeDB.load();
    this.collectionNode = new CollectionNode();
    this.collectionNode.init(this.channelTimes);
  }

  public static PushCommonData getInstance(Context paramContext)
  {
    if (_ins == null)
      _ins = new PushCommonData(paramContext);
    return _ins;
  }

  private void init()
  {
    if (this.m_info == null)
    {
      this.m_info = new DownLoadInfoNode();
      this.m_info.init();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.push.data.PushCommonData
 * JD-Core Version:    0.6.2
 */