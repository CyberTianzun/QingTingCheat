package fm.qingting.async.http;

import android.net.Uri;
import fm.qingting.async.http.libcore.RawHeaders;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Multimap extends Hashtable<String, List<String>>
  implements Iterable<NameValuePair>
{
  public Multimap()
  {
  }

  public Multimap(RawHeaders paramRawHeaders)
  {
    paramRawHeaders.toMultimap().putAll(this);
  }

  public Multimap(List<NameValuePair> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      NameValuePair localNameValuePair = (NameValuePair)localIterator.next();
      add(localNameValuePair.getName(), localNameValuePair.getValue());
    }
  }

  public static Multimap parseHeader(RawHeaders paramRawHeaders, String paramString)
  {
    return parseHeader(paramRawHeaders.get(paramString));
  }

  public static Multimap parseHeader(String paramString)
  {
    Multimap localMultimap = new Multimap();
    String[] arrayOfString1 = paramString.split(";");
    int i = arrayOfString1.length;
    for (int j = 0; j < i; j++)
    {
      String[] arrayOfString2 = arrayOfString1[j].split("=", 2);
      String str1 = arrayOfString2[0].trim();
      int k = arrayOfString2.length;
      String str2 = null;
      if (k > 1)
        str2 = arrayOfString2[1];
      if ((str2 != null) && (str2.endsWith("\"")) && (str2.startsWith("\"")))
        str2 = str2.substring(1, -1 + str2.length());
      localMultimap.add(str1, str2);
    }
    return localMultimap;
  }

  public static Multimap parseQuery(String paramString)
  {
    Multimap localMultimap = new Multimap();
    String[] arrayOfString1 = paramString.split("&");
    int i = arrayOfString1.length;
    int j = 0;
    if (j < i)
    {
      String[] arrayOfString2 = arrayOfString1[j].split("=", 2);
      if (arrayOfString2.length == 0);
      while (true)
      {
        j++;
        break;
        String str1 = Uri.decode(arrayOfString2[0]);
        int k = arrayOfString2.length;
        String str2 = null;
        if (k == 2)
          str2 = Uri.decode(arrayOfString2[1]);
        localMultimap.add(str1, str2);
      }
    }
    return localMultimap;
  }

  public void add(String paramString1, String paramString2)
  {
    Object localObject = (List)get(paramString1);
    if (localObject == null)
    {
      localObject = new ArrayList();
      put(paramString1, localObject);
    }
    ((List)localObject).add(paramString2);
  }

  public String getString(String paramString)
  {
    List localList = (List)get(paramString);
    if ((localList == null) || (localList.size() == 0))
      return null;
    return (String)localList.get(0);
  }

  public Iterator<NameValuePair> iterator()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str = (String)localIterator1.next();
      Iterator localIterator2 = ((List)get(str)).iterator();
      while (localIterator2.hasNext())
        localArrayList.add(new BasicNameValuePair(str, (String)localIterator2.next()));
    }
    return localArrayList.iterator();
  }

  public void put(String paramString1, String paramString2)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramString2);
    put(paramString1, localArrayList);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.Multimap
 * JD-Core Version:    0.6.2
 */