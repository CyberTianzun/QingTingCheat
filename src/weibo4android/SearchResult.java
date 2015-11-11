package weibo4android;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import weibo4android.http.Response;
import weibo4android.org.json.JSONArray;
import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;

public class SearchResult extends WeiboResponse
  implements Serializable
{
  private static final long serialVersionUID = 8227371192527300467L;
  private Date createdAt;
  private String from_user;
  private long from_user_id;
  private long id;
  private String iso_language_code;
  private String profileImageUrl;
  private String source;
  private String text;
  private String to_user;
  private long to_user_id;

  public SearchResult(JSONObject paramJSONObject)
    throws WeiboException, JSONException
  {
    this.createdAt = parseDate(paramJSONObject.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");
    this.to_user_id = paramJSONObject.getLong("to_user_id");
    this.to_user = paramJSONObject.getString("to_user");
    this.text = paramJSONObject.getString("text");
    this.source = paramJSONObject.getString("source");
    this.id = paramJSONObject.getLong("id");
    this.from_user_id = paramJSONObject.getLong("from_user_id");
    this.from_user = paramJSONObject.getString("from_user");
    this.iso_language_code = paramJSONObject.getString("iso_language_code");
    this.profileImageUrl = paramJSONObject.getString("profile_image_url");
  }

  public static List<SearchResult> constructResults(Response paramResponse)
    throws WeiboException
  {
    JSONObject localJSONObject = paramResponse.asJSONObject();
    ArrayList localArrayList;
    try
    {
      JSONArray localJSONArray = localJSONObject.getJSONArray("results");
      int i = localJSONArray.length();
      localArrayList = new ArrayList(i);
      for (int j = 0; j < i; j++)
        localArrayList.add(new SearchResult(localJSONArray.getJSONObject(j)));
    }
    catch (JSONException localJSONException)
    {
      throw new WeiboException(localJSONException);
    }
    return localArrayList;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    SearchResult localSearchResult;
    do
    {
      return true;
      if (paramObject == null)
        return false;
      if (getClass() != paramObject.getClass())
        return false;
      localSearchResult = (SearchResult)paramObject;
      if (this.from_user == null)
      {
        if (localSearchResult.from_user != null)
          return false;
      }
      else if (!this.from_user.equals(localSearchResult.from_user))
        return false;
      if (this.from_user_id != localSearchResult.from_user_id)
        return false;
      if (this.id != localSearchResult.id)
        return false;
      if (this.to_user == null)
      {
        if (localSearchResult.to_user != null)
          return false;
      }
      else if (!this.to_user.equals(localSearchResult.to_user))
        return false;
    }
    while (this.to_user_id == localSearchResult.to_user_id);
    return false;
  }

  public Date getCreatedAt()
  {
    return this.createdAt;
  }

  public String getFromUser()
  {
    return this.from_user;
  }

  public long getFromUserId()
  {
    return this.from_user_id;
  }

  public long getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.iso_language_code;
  }

  public URL getProfileImageURL()
  {
    try
    {
      URL localURL = new URL(this.profileImageUrl);
      return localURL;
    }
    catch (MalformedURLException localMalformedURLException)
    {
    }
    return null;
  }

  public String getSource()
  {
    return this.source;
  }

  public String getText()
  {
    return this.text;
  }

  public String getToUser()
  {
    return this.to_user;
  }

  public long getToUserId()
  {
    return this.to_user_id;
  }

  public int hashCode()
  {
    int i;
    int j;
    int k;
    if (this.from_user == null)
    {
      i = 0;
      j = 31 * (31 * (31 * (i + 31) + (int)(this.from_user_id ^ this.from_user_id >>> 32)) + (int)(this.id ^ this.id >>> 32));
      String str = this.to_user;
      k = 0;
      if (str != null)
        break label96;
    }
    while (true)
    {
      return 31 * (j + k) + (int)(this.to_user_id ^ this.to_user_id >>> 32);
      i = this.from_user.hashCode();
      break;
      label96: k = this.to_user.hashCode();
    }
  }

  public String toString()
  {
    return "Result{ " + this.to_user_id + "," + this.to_user + "," + this.text + "," + this.id + "," + this.from_user_id + "," + this.from_user + "," + this.iso_language_code + "," + this.source + "," + this.profileImageUrl + "," + this.createdAt + '}';
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.SearchResult
 * JD-Core Version:    0.6.2
 */