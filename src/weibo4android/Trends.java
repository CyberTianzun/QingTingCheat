package weibo4android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import weibo4android.http.Response;
import weibo4android.org.json.JSONArray;
import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;

public class Trends extends WeiboResponse
  implements Comparable<Trends>
{
  private static final long serialVersionUID = -7151479143843312309L;
  private Date asOf;
  private Date trendAt;
  private Trend[] trends;

  Trends(Response paramResponse, Date paramDate1, Date paramDate2, Trend[] paramArrayOfTrend)
    throws WeiboException
  {
    super(paramResponse);
    this.asOf = paramDate1;
    this.trendAt = paramDate2;
    this.trends = paramArrayOfTrend;
  }

  static Trends constructTrends(Response paramResponse)
    throws WeiboException
  {
    JSONObject localJSONObject = paramResponse.asJSONObject();
    try
    {
      Date localDate = parseDate(localJSONObject.getString("as_of"));
      Trends localTrends = new Trends(paramResponse, localDate, localDate, jsonArrayToTrendArray(localJSONObject.getJSONArray("trends")));
      return localTrends;
    }
    catch (JSONException localJSONException)
    {
      throw new WeiboException(localJSONException.getMessage() + ":" + paramResponse.asString(), localJSONException);
    }
  }

  static List<Trends> constructTrendsList(Response paramResponse)
    throws WeiboException
  {
    JSONObject localJSONObject1 = paramResponse.asJSONObject();
    ArrayList localArrayList;
    while (true)
    {
      Date localDate;
      String str;
      Trend[] arrayOfTrend;
      try
      {
        localDate = parseDate(localJSONObject1.getString("as_of"));
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("trends");
        localArrayList = new ArrayList(localJSONObject2.length());
        Iterator localIterator = localJSONObject2.keys();
        if (!localIterator.hasNext())
          break;
        str = (String)localIterator.next();
        arrayOfTrend = jsonArrayToTrendArray(localJSONObject2.getJSONArray(str));
        if (str.length() == 19)
        {
          localArrayList.add(new Trends(paramResponse, localDate, parseDate(str, "yyyy-MM-dd HH:mm:ss"), arrayOfTrend));
          continue;
        }
      }
      catch (JSONException localJSONException)
      {
        throw new WeiboException(localJSONException.getMessage() + ":" + paramResponse.asString(), localJSONException);
      }
      if (str.length() == 16)
        localArrayList.add(new Trends(paramResponse, localDate, parseDate(str, "yyyy-MM-dd HH:mm"), arrayOfTrend));
      else if (str.length() == 10)
        localArrayList.add(new Trends(paramResponse, localDate, parseDate(str, "yyyy-MM-dd"), arrayOfTrend));
    }
    Collections.sort(localArrayList);
    return localArrayList;
  }

  private static Trend[] jsonArrayToTrendArray(JSONArray paramJSONArray)
    throws JSONException
  {
    Trend[] arrayOfTrend = new Trend[paramJSONArray.length()];
    for (int i = 0; i < paramJSONArray.length(); i++)
      arrayOfTrend[i] = new Trend(paramJSONArray.getJSONObject(i));
    return arrayOfTrend;
  }

  private static Date parseDate(String paramString)
    throws WeiboException
  {
    if (paramString.length() == 10)
      return new Date(1000L * Long.parseLong(paramString));
    return WeiboResponse.parseDate(paramString, "EEE, d MMM yyyy HH:mm:ss z");
  }

  public int compareTo(Trends paramTrends)
  {
    return this.trendAt.compareTo(paramTrends.trendAt);
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    Trends localTrends;
    do
    {
      return true;
      if (!(paramObject instanceof Trends))
        return false;
      localTrends = (Trends)paramObject;
      if (this.asOf != null)
      {
        if (this.asOf.equals(localTrends.asOf));
      }
      else
        while (localTrends.asOf != null)
          return false;
      if (this.trendAt != null)
      {
        if (this.trendAt.equals(localTrends.trendAt));
      }
      else
        while (localTrends.trendAt != null)
          return false;
    }
    while (Arrays.equals(this.trends, localTrends.trends));
    return false;
  }

  public Date getAsOf()
  {
    return this.asOf;
  }

  public Date getTrendAt()
  {
    return this.trendAt;
  }

  public Trend[] getTrends()
  {
    return this.trends;
  }

  public int hashCode()
  {
    int i;
    int j;
    if (this.asOf != null)
    {
      i = this.asOf.hashCode();
      j = i * 31;
      if (this.trendAt == null)
        break label77;
    }
    label77: for (int k = this.trendAt.hashCode(); ; k = 0)
    {
      int m = 31 * (k + j);
      Trend[] arrayOfTrend = this.trends;
      int n = 0;
      if (arrayOfTrend != null)
        n = Arrays.hashCode(this.trends);
      return m + n;
      i = 0;
      break;
    }
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("Trends{asOf=").append(this.asOf).append(", trendAt=").append(this.trendAt).append(", trends=");
    if (this.trends == null);
    for (Object localObject = null; ; localObject = Arrays.asList(this.trends))
      return localObject + '}';
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.Trends
 * JD-Core Version:    0.6.2
 */