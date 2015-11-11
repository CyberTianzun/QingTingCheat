package fm.qingting.download.qtradiodownload.persistent;

import fm.qingting.download.qtradiodownload.network.filedownload.model.DownloadTask;
import fm.qingting.download.qtradiodownload.network.filedownload.model.Part;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class PartDataTool
{
  private static final String TAG = "PartDataTool";

  public static String buildPartsData(List<Part> paramList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = "";
    if ((paramList != null) && (paramList.size() > 0))
      str = ((Part)paramList.get(0)).getResource().getUUId();
    localStringBuffer.append("<parts uuid=\"" + str + "\">");
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Part localPart = (Part)localIterator.next();
      localStringBuffer.append("<part>").append("<begin>" + localPart.getBegin() + "</begin>").append("<size>" + localPart.getSize() + "</size>").append("<curlength>" + localPart.getCurLength() + "</curlength>").append("</part>");
    }
    localStringBuffer.append("</parts>");
    return localStringBuffer.toString();
  }

  public static void parsePartsData(DownloadTask paramDownloadTask, String paramString)
  {
    String str1 = paramDownloadTask.getUUId();
    List localList = paramDownloadTask.getParts();
    while (true)
    {
      String str2;
      int j;
      try
      {
        XmlPullParser localXmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
        localXmlPullParser.setInput(new StringReader(paramString));
        int i = localXmlPullParser.getEventType();
        str2 = "";
        j = i;
        int k = 0;
        int m = 0;
        int n = 0;
        break label263;
        j = localXmlPullParser.next();
        break label263;
        if (j == 2)
        {
          str2 = localXmlPullParser.getName();
          if ((!"parts".equals(str2)) || (str1.equals(localXmlPullParser.getAttributeValue(null, "uuid"))))
            continue;
          return;
        }
        if (j == 3)
        {
          if (!"part".equals(localXmlPullParser.getName()))
            break label278;
          localList.add(new Part(paramDownloadTask, n, m, k));
          break label278;
        }
        if (j != 4)
          continue;
        if ("begin".equals(str2))
        {
          n = Integer.parseInt(localXmlPullParser.getText());
          continue;
        }
        if ("size".equals(str2))
        {
          m = Integer.parseInt(localXmlPullParser.getText());
          continue;
        }
        if (!"curlength".equals(str2))
          continue;
        int i1 = Integer.parseInt(localXmlPullParser.getText());
        k = i1;
        continue;
      }
      catch (XmlPullParserException localXmlPullParserException)
      {
        localXmlPullParserException.printStackTrace();
        return;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        return;
      }
      label263: if (j != 1)
      {
        if (j != 0);
      }
      else
      {
        return;
        label278: str2 = "";
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.persistent.PartDataTool
 * JD-Core Version:    0.6.2
 */