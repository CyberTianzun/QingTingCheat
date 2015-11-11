package weibo4android;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import weibo4android.http.Response;
import weibo4android.org.json.JSONArray;
import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;

public class Count
  implements Serializable
{
  private static final long serialVersionUID = 9076424494907778181L;
  private long comments;
  private long dm;
  private long followers;
  private long id;
  private long mentions;
  private long rt;

  Count(Response paramResponse)
    throws WeiboException
  {
    JSONObject localJSONObject = paramResponse.asJSONObject();
    try
    {
      this.id = localJSONObject.getLong("id");
      this.comments = localJSONObject.getLong("comments");
      this.rt = localJSONObject.getLong("rt");
      this.dm = localJSONObject.getLong("dm");
      this.mentions = localJSONObject.getLong("mentions");
      this.followers = localJSONObject.getLong("followers");
      return;
    }
    catch (JSONException localJSONException)
    {
      throw new WeiboException(localJSONException.getMessage() + ":" + localJSONObject.toString(), localJSONException);
    }
  }

  public Count(JSONObject paramJSONObject)
    throws WeiboException, JSONException
  {
    this.id = paramJSONObject.getLong("id");
    this.comments = paramJSONObject.getLong("comments");
    this.rt = paramJSONObject.getLong("rt");
    this.dm = paramJSONObject.getLong("dm");
    this.mentions = paramJSONObject.getLong("mentions");
    this.followers = paramJSONObject.getLong("followers");
  }

  static List<Count> constructCounts(Response paramResponse)
    throws WeiboException
  {
    ArrayList localArrayList;
    try
    {
      System.out.println(paramResponse.asString());
      JSONArray localJSONArray = paramResponse.asJSONArray();
      int i = localJSONArray.length();
      localArrayList = new ArrayList(i);
      for (int j = 0; j < i; j++)
        localArrayList.add(new Count(localJSONArray.getJSONObject(j)));
    }
    catch (JSONException localJSONException)
    {
      throw new WeiboException(localJSONException);
    }
    catch (WeiboException localWeiboException)
    {
      throw localWeiboException;
    }
    return localArrayList;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    Count localCount;
    do
    {
      return true;
      if (paramObject == null)
        return false;
      if (getClass() != paramObject.getClass())
        return false;
      localCount = (Count)paramObject;
    }
    while (this.id == localCount.id);
    return false;
  }

  public long getComments()
  {
    return this.comments;
  }

  public long getDm()
  {
    return this.dm;
  }

  public long getFollowers()
  {
    return this.followers;
  }

  public long getId()
  {
    return this.id;
  }

  public long getMentions()
  {
    return this.mentions;
  }

  public long getRt()
  {
    return this.rt;
  }

  public int hashCode()
  {
    return 31 + (int)(this.id ^ this.id >>> 32);
  }

  public String toString()
  {
    return "Count{ id=" + this.id + ", comments=" + this.comments + ", rt=" + this.rt + ", dm=" + this.dm + ", mentions=" + this.mentions + ", followers=" + this.followers + '}';
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.Count
 * JD-Core Version:    0.6.2
 */