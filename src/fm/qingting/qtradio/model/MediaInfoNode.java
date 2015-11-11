package fm.qingting.qtradio.model;

import java.util.List;
import java.util.Map;

public class MediaInfoNode extends Node
{
  public List<String> originalSource;
  public Map<String, String> replaySource;
  public Map<String, String> transcodeSource;

  public MediaInfoNode()
  {
    this.nodeName = "mediainfo";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.MediaInfoNode
 * JD-Core Version:    0.6.2
 */