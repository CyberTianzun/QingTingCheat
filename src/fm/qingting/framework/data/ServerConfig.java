package fm.qingting.framework.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ServerConfig
{
  private static ServerConfig instance = null;
  private HashMap<String, String> allDefaultProxies = new HashMap();
  private HashMap<String, List<String>> allDefaultRoots = new HashMap();
  private HashMap<String, MutableRequestTrait> requests = new HashMap();

  public static ServerConfig getInstance()
  {
    try
    {
      if (instance == null)
        instance = new ServerConfig();
      ServerConfig localServerConfig = instance;
      return localServerConfig;
    }
    finally
    {
    }
  }

  // ERROR //
  private void parse(InputStream paramInputStream)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 25	fm/qingting/framework/data/ServerConfig:allDefaultRoots	Ljava/util/HashMap;
    //   4: invokevirtual 41	java/util/HashMap:clear	()V
    //   7: aload_0
    //   8: getfield 27	fm/qingting/framework/data/ServerConfig:requests	Ljava/util/HashMap;
    //   11: invokevirtual 41	java/util/HashMap:clear	()V
    //   14: new 43	java/io/ByteArrayOutputStream
    //   17: dup
    //   18: invokespecial 44	java/io/ByteArrayOutputStream:<init>	()V
    //   21: astore_2
    //   22: sipush 1024
    //   25: newarray byte
    //   27: astore 9
    //   29: aload_1
    //   30: aload 9
    //   32: iconst_0
    //   33: aload 9
    //   35: arraylength
    //   36: invokevirtual 50	java/io/InputStream:read	([BII)I
    //   39: istore 10
    //   41: iload 10
    //   43: iconst_m1
    //   44: if_icmpne +25 -> 69
    //   47: aload_0
    //   48: new 52	java/lang/String
    //   51: dup
    //   52: aload_2
    //   53: invokevirtual 56	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   56: ldc 58
    //   58: invokespecial 61	java/lang/String:<init>	([BLjava/lang/String;)V
    //   61: invokespecial 65	fm/qingting/framework/data/ServerConfig:parseJson	(Ljava/lang/String;)V
    //   64: aload_1
    //   65: invokevirtual 68	java/io/InputStream:close	()V
    //   68: return
    //   69: aload_2
    //   70: aload 9
    //   72: iconst_0
    //   73: iload 10
    //   75: invokevirtual 72	java/io/ByteArrayOutputStream:write	([BII)V
    //   78: goto -49 -> 29
    //   81: astore 7
    //   83: aload 7
    //   85: invokevirtual 75	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   88: aload_1
    //   89: invokevirtual 68	java/io/InputStream:close	()V
    //   92: return
    //   93: astore 8
    //   95: aload 8
    //   97: invokevirtual 76	java/io/IOException:printStackTrace	()V
    //   100: return
    //   101: astore 5
    //   103: aload 5
    //   105: invokevirtual 76	java/io/IOException:printStackTrace	()V
    //   108: aload_1
    //   109: invokevirtual 68	java/io/InputStream:close	()V
    //   112: return
    //   113: astore 6
    //   115: aload 6
    //   117: invokevirtual 76	java/io/IOException:printStackTrace	()V
    //   120: return
    //   121: astore_3
    //   122: aload_1
    //   123: invokevirtual 68	java/io/InputStream:close	()V
    //   126: aload_3
    //   127: athrow
    //   128: astore 4
    //   130: aload 4
    //   132: invokevirtual 76	java/io/IOException:printStackTrace	()V
    //   135: goto -9 -> 126
    //   138: astore 11
    //   140: aload 11
    //   142: invokevirtual 76	java/io/IOException:printStackTrace	()V
    //   145: return
    //
    // Exception table:
    //   from	to	target	type
    //   14	29	81	java/io/UnsupportedEncodingException
    //   29	41	81	java/io/UnsupportedEncodingException
    //   47	64	81	java/io/UnsupportedEncodingException
    //   69	78	81	java/io/UnsupportedEncodingException
    //   88	92	93	java/io/IOException
    //   14	29	101	java/io/IOException
    //   29	41	101	java/io/IOException
    //   47	64	101	java/io/IOException
    //   69	78	101	java/io/IOException
    //   108	112	113	java/io/IOException
    //   14	29	121	finally
    //   29	41	121	finally
    //   47	64	121	finally
    //   69	78	121	finally
    //   83	88	121	finally
    //   103	108	121	finally
    //   122	126	128	java/io/IOException
    //   64	68	138	java/io/IOException
  }

  private void parseJson(String paramString)
  {
    try
    {
      JSONObject localJSONObject1 = (JSONObject)JSON.parse(paramString);
      JSONArray localJSONArray1 = localJSONObject1.getJSONArray("defaultroots");
      ArrayList localArrayList1 = new ArrayList();
      JSONArray localJSONArray2;
      int j;
      for (int i = 0; ; i++)
      {
        if (i >= localJSONArray1.size())
        {
          setDefaultRoots(localArrayList1, "net");
          localJSONArray2 = localJSONObject1.getJSONArray("requests");
          j = 0;
          if (j < localJSONArray2.size())
            break;
          return;
        }
        localArrayList1.add(localJSONArray1.getJSONObject(i).getString("root").trim());
      }
      JSONObject localJSONObject2 = localJSONArray2.getJSONObject(j);
      String str1 = localJSONObject2.getString("type");
      String str2 = localJSONObject2.getString("command");
      String str3 = localJSONObject2.getString("datatype");
      String str4 = localJSONObject2.getString("datasource");
      String str5 = localJSONObject2.getString("method");
      String str6 = localJSONObject2.getString("encoding");
      if (str6 == null)
        str6 = "utf-8";
      String str7 = localJSONObject2.getString("commandparamencode");
      String str8 = localJSONObject2.getString("proxy");
      ArrayList localArrayList2 = new ArrayList();
      if (str2 != null)
        localArrayList2.add(str2);
      ArrayList localArrayList3 = new ArrayList();
      JSONArray localJSONArray3 = localJSONObject2.getJSONArray("roots");
      if (localJSONArray3 != null);
      for (int k = 0; ; k++)
      {
        int m = localJSONArray3.size();
        if (k >= m)
        {
          addRequest(str1, str4, localArrayList2, str5, str3, localArrayList3, str6, str8, str7);
          j++;
          break;
        }
        String str9 = localJSONArray3.getJSONObject(k).getString("root");
        if (str9 != null)
          localArrayList3.add(str9);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void addRequest(String paramString1, String paramString2, List<String> paramList1, String paramString3, String paramString4, List<String> paramList2, String paramString5, String paramString6, String paramString7)
  {
    MutableRequestTrait localMutableRequestTrait = new MutableRequestTrait(paramString1, paramString2, paramList1, paramString3, paramString4, paramList2, (List)this.allDefaultRoots.get(paramString2), paramString5, paramString6, paramString7);
    this.requests.put(paramString1, localMutableRequestTrait);
  }

  public String getDefaultProxy(String paramString)
  {
    if (paramString == null)
      return null;
    return (String)this.allDefaultProxies.get(paramString);
  }

  public RequestTrait getRequestTrait(String paramString)
  {
    return (RequestTrait)this.requests.get(paramString);
  }

  public void setDefaultProxy(String paramString1, String paramString2)
  {
    this.allDefaultProxies.put(paramString1, paramString2);
  }

  public void setDefaultRoot(String paramString1, String paramString2)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramString1);
    setDefaultRoots(localArrayList, paramString2);
  }

  public void setDefaultRoots(List<String> paramList, String paramString)
  {
    Object localObject = (List)this.allDefaultRoots.get(paramString);
    if (localObject == null)
    {
      localObject = new ArrayList();
      this.allDefaultRoots.put(paramString, localObject);
    }
    ((List)localObject).clear();
    Iterator localIterator1 = paramList.iterator();
    Iterator localIterator2;
    if (!localIterator1.hasNext())
      localIterator2 = this.requests.values().iterator();
    while (true)
    {
      if (!localIterator2.hasNext())
      {
        return;
        ((List)localObject).add(new String((String)localIterator1.next()));
        break;
      }
      MutableRequestTrait localMutableRequestTrait = (MutableRequestTrait)localIterator2.next();
      if (localMutableRequestTrait.dataSource.equalsIgnoreCase(paramString))
        localMutableRequestTrait.setDefaultRoots((List)localObject);
    }
  }

  public void setEncoding(String paramString1, String paramString2)
  {
    Iterator localIterator = this.requests.values().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      MutableRequestTrait localMutableRequestTrait = (MutableRequestTrait)localIterator.next();
      if (localMutableRequestTrait.dataSource.equalsIgnoreCase(paramString2))
        localMutableRequestTrait.setEncoding(paramString1);
    }
  }

  public void setRequestCommands(List<String> paramList, String paramString)
  {
    MutableRequestTrait localMutableRequestTrait = (MutableRequestTrait)this.requests.get(paramString);
    if (localMutableRequestTrait == null)
      return;
    localMutableRequestTrait.setCommands(paramList);
  }

  public void setRequestRoots(List<String> paramList, String paramString)
  {
    MutableRequestTrait localMutableRequestTrait = (MutableRequestTrait)this.requests.get(paramString);
    if (localMutableRequestTrait == null)
      return;
    localMutableRequestTrait.setRoots(paramList);
  }

  public void setServerConfig(InputStream paramInputStream)
  {
    try
    {
      parse(paramInputStream);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.data.ServerConfig
 * JD-Core Version:    0.6.2
 */