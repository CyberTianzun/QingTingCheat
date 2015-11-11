package weibo4android;

import java.io.Serializable;
import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;

public class Trend
  implements Serializable
{
  private static final long serialVersionUID = 1925956704460743946L;
  private String name;
  private String query = null;

  public Trend(JSONObject paramJSONObject)
    throws JSONException
  {
    this.name = paramJSONObject.getString("name");
    if (!paramJSONObject.isNull("query"))
      this.query = paramJSONObject.getString("query");
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    Trend localTrend;
    do
    {
      return true;
      if (!(paramObject instanceof Trend))
        return false;
      localTrend = (Trend)paramObject;
      if (!this.name.equals(localTrend.name))
        return false;
      if (this.query == null)
        break;
    }
    while (this.query.equals(localTrend.query));
    while (true)
    {
      return false;
      if (localTrend.query == null)
        break;
    }
  }

  public String getName()
  {
    return this.name;
  }

  public String getQuery()
  {
    return this.query;
  }

  public int hashCode()
  {
    int i = 31 * this.name.hashCode();
    if (this.query != null);
    for (int j = this.query.hashCode(); ; j = 0)
      return j + i;
  }

  public String toString()
  {
    return "Trend{name='" + this.name + '\'' + ", query='" + this.query + '\'' + '}';
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.Trend
 * JD-Core Version:    0.6.2
 */