package weibo4android;

import java.util.ArrayList;
import java.util.List;
import weibo4android.http.Response;
import weibo4android.org.json.JSONArray;
import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;

public class UserTrend extends WeiboResponse
{
  private static final long serialVersionUID = 1925956704460743946L;
  private String hotword = null;
  private String num;
  private String trend_id = null;

  public UserTrend()
  {
  }

  public UserTrend(Response paramResponse)
    throws WeiboException
  {
    super(paramResponse);
    JSONObject localJSONObject = paramResponse.asJSONObject();
    try
    {
      this.num = localJSONObject.getString("num");
      this.hotword = localJSONObject.getString("hotword");
      this.trend_id = localJSONObject.getString("trend_id");
      if (localJSONObject.getString("topicid") != null)
        this.trend_id = localJSONObject.getString("topicid");
      return;
    }
    catch (JSONException localJSONException)
    {
      throw new WeiboException(localJSONException.getMessage() + ":" + localJSONObject.toString(), localJSONException);
    }
  }

  public UserTrend(JSONObject paramJSONObject)
    throws WeiboException
  {
    try
    {
      this.num = paramJSONObject.getString("num");
      this.hotword = paramJSONObject.getString("hotword");
      this.trend_id = paramJSONObject.getString("trend_id");
      return;
    }
    catch (JSONException localJSONException)
    {
      throw new WeiboException(localJSONException.getMessage() + ":" + paramJSONObject.toString(), localJSONException);
    }
  }

  static List<UserTrend> constructTrendList(Response paramResponse)
    throws WeiboException
  {
    ArrayList localArrayList;
    try
    {
      JSONArray localJSONArray = paramResponse.asJSONArray();
      int i = localJSONArray.length();
      localArrayList = new ArrayList(i);
      for (int j = 0; j < i; j++)
        localArrayList.add(new UserTrend(localJSONArray.getJSONObject(j)));
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

  public String getHotword()
  {
    return this.hotword;
  }

  public String getNum()
  {
    return this.num;
  }

  public String getTrend_id()
  {
    return this.trend_id;
  }

  public void setHotword(String paramString)
  {
    this.hotword = paramString;
  }

  public void setNum(String paramString)
  {
    this.num = paramString;
  }

  public void setTrend_id(String paramString)
  {
    this.trend_id = paramString;
  }

  public String toString()
  {
    return "Trend [num=" + this.num + ", hotword=" + this.hotword + ", trend_id=" + this.trend_id + "]";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.UserTrend
 * JD-Core Version:    0.6.2
 */