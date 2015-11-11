package fm.qingting.async.http.libcore;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public final class RawHeaders
{
  private static final Comparator<String> FIELD_NAME_COMPARATOR = new Comparator()
  {
    public int compare(String paramAnonymousString1, String paramAnonymousString2)
    {
      if (paramAnonymousString1 == paramAnonymousString2)
        return 0;
      if (paramAnonymousString1 == null)
        return -1;
      if (paramAnonymousString2 == null)
        return 1;
      return String.CASE_INSENSITIVE_ORDER.compare(paramAnonymousString1, paramAnonymousString2);
    }
  };
  private int httpMinorVersion = 1;
  private final List<String> namesAndValues = new ArrayList(20);
  private int responseCode = -1;
  private String responseMessage;
  private String statusLine;

  public RawHeaders()
  {
  }

  public RawHeaders(RawHeaders paramRawHeaders)
  {
    this.namesAndValues.addAll(paramRawHeaders.namesAndValues);
    this.statusLine = paramRawHeaders.statusLine;
    this.httpMinorVersion = paramRawHeaders.httpMinorVersion;
    this.responseCode = paramRawHeaders.responseCode;
    this.responseMessage = paramRawHeaders.responseMessage;
  }

  public static RawHeaders fromMultimap(Map<String, List<String>> paramMap)
  {
    RawHeaders localRawHeaders = new RawHeaders();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      List localList = (List)localEntry.getValue();
      if (str != null)
        localRawHeaders.addAll(str, localList);
      else if (!localList.isEmpty())
        localRawHeaders.setStatusLine((String)localList.get(-1 + localList.size()));
    }
    return localRawHeaders;
  }

  public void add(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      throw new IllegalArgumentException("fieldName == null");
    if (paramString2 == null)
    {
      System.err.println("Ignoring HTTP header field '" + paramString1 + "' because its value is null");
      return;
    }
    this.namesAndValues.add(paramString1);
    this.namesAndValues.add(paramString2.trim());
  }

  public void addAll(String paramString, List<String> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
      add(paramString, (String)localIterator.next());
  }

  public void addLine(String paramString)
  {
    int i = paramString.indexOf(":");
    if (i == -1)
    {
      add("", paramString);
      return;
    }
    add(paramString.substring(0, i), paramString.substring(i + 1));
  }

  public String get(String paramString)
  {
    for (int i = -2 + this.namesAndValues.size(); i >= 0; i -= 2)
      if (paramString.equalsIgnoreCase((String)this.namesAndValues.get(i)))
        return (String)this.namesAndValues.get(i + 1);
    return null;
  }

  public RawHeaders getAll(Set<String> paramSet)
  {
    RawHeaders localRawHeaders = new RawHeaders();
    for (int i = 0; i < this.namesAndValues.size(); i += 2)
    {
      String str = (String)this.namesAndValues.get(i);
      if (paramSet.contains(str))
        localRawHeaders.add(str, (String)this.namesAndValues.get(i + 1));
    }
    return localRawHeaders;
  }

  public String getFieldName(int paramInt)
  {
    int i = paramInt * 2;
    if ((i < 0) || (i >= this.namesAndValues.size()))
      return null;
    return (String)this.namesAndValues.get(i);
  }

  public int getHttpMinorVersion()
  {
    if (this.httpMinorVersion != -1)
      return this.httpMinorVersion;
    return 1;
  }

  public int getResponseCode()
  {
    return this.responseCode;
  }

  public String getResponseMessage()
  {
    return this.responseMessage;
  }

  public String getStatusLine()
  {
    return this.statusLine;
  }

  public String getValue(int paramInt)
  {
    int i = 1 + paramInt * 2;
    if ((i < 0) || (i >= this.namesAndValues.size()))
      return null;
    return (String)this.namesAndValues.get(i);
  }

  public int length()
  {
    return this.namesAndValues.size() / 2;
  }

  public void removeAll(String paramString)
  {
    for (int i = 0; i < this.namesAndValues.size(); i += 2)
      if (paramString.equalsIgnoreCase((String)this.namesAndValues.get(i)))
      {
        this.namesAndValues.remove(i);
        this.namesAndValues.remove(i);
      }
  }

  public void set(String paramString1, String paramString2)
  {
    removeAll(paramString1);
    add(paramString1, paramString2);
  }

  public void setStatusLine(String paramString)
  {
    String str1 = paramString.trim();
    this.statusLine = str1;
    if ((str1 == null) || (!str1.startsWith("HTTP/")));
    String str2;
    int j;
    do
    {
      int i;
      do
      {
        return;
        str2 = str1.trim();
        i = 1 + str2.indexOf(" ");
      }
      while (i == 0);
      if (str2.charAt(i - 2) != '1')
        this.httpMinorVersion = 0;
      j = i + 3;
      if (j > str2.length())
        j = str2.length();
      this.responseCode = Integer.parseInt(str2.substring(i, j));
    }
    while (j + 1 > str2.length());
    this.responseMessage = str2.substring(j + 1);
  }

  public String toHeaderString()
  {
    StringBuilder localStringBuilder = new StringBuilder(256);
    localStringBuilder.append(this.statusLine).append("\r\n");
    for (int i = 0; i < this.namesAndValues.size(); i += 2)
      localStringBuilder.append((String)this.namesAndValues.get(i)).append(": ").append((String)this.namesAndValues.get(i + 1)).append("\r\n");
    localStringBuilder.append("\r\n");
    return localStringBuilder.toString();
  }

  public Map<String, List<String>> toMultimap()
  {
    TreeMap localTreeMap = new TreeMap(FIELD_NAME_COMPARATOR);
    for (int i = 0; i < this.namesAndValues.size(); i += 2)
    {
      String str1 = (String)this.namesAndValues.get(i);
      String str2 = (String)this.namesAndValues.get(i + 1);
      ArrayList localArrayList = new ArrayList();
      List localList = (List)localTreeMap.get(str1);
      if (localList != null)
        localArrayList.addAll(localList);
      localArrayList.add(str2);
      localTreeMap.put(str1, Collections.unmodifiableList(localArrayList));
    }
    if (this.statusLine != null)
      localTreeMap.put(null, Collections.unmodifiableList(Collections.singletonList(this.statusLine)));
    return Collections.unmodifiableMap(localTreeMap);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.libcore.RawHeaders
 * JD-Core Version:    0.6.2
 */