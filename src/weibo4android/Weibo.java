package weibo4android;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import weibo4android.http.AccessToken;
import weibo4android.http.HttpClient;
import weibo4android.http.ImageItem;
import weibo4android.http.PostParameter;
import weibo4android.http.RequestToken;
import weibo4android.http.Response;
import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;
import weibo4android.util.URLEncodeUtils;

public class Weibo extends WeiboSupport
  implements Serializable
{
  public static String CONSUMER_KEY = "";
  public static String CONSUMER_SECRET = "";
  public static final Device IM = new Device("im");
  public static final Device NONE = new Device("none");
  public static final Device SMS = new Device("sms");
  private static final long serialVersionUID = -1486360080128882436L;
  private String baseURL = Configuration.getScheme() + "api.t.sina.com.cn/";
  private SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
  private String searchBaseURL = Configuration.getScheme() + "api.t.sina.com.cn/";

  public Weibo()
  {
    this.format.setTimeZone(TimeZone.getTimeZone("GMT"));
    this.http.setRequestTokenURL(Configuration.getScheme() + "api.t.sina.com.cn/oauth/request_token");
    this.http.setAuthorizationURL(Configuration.getScheme() + "api.t.sina.com.cn/oauth/authorize");
    this.http.setAccessTokenURL(Configuration.getScheme() + "api.t.sina.com.cn/oauth/access_token");
  }

  public Weibo(String paramString)
  {
    this();
    this.baseURL = paramString;
  }

  private void addParameterToList(List<PostParameter> paramList, String paramString1, String paramString2)
  {
    if (paramString2 != null)
      paramList.add(new PostParameter(paramString1, paramString2));
  }

  private PostParameter[] generateParameterArray(Map<String, String> paramMap)
    throws WeiboException
  {
    PostParameter[] arrayOfPostParameter = new PostParameter[paramMap.size()];
    Iterator localIterator = paramMap.keySet().iterator();
    int i = 0;
    if (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (paramMap.get(str) == null)
        break label95;
      arrayOfPostParameter[i] = new PostParameter(str, (String)paramMap.get(str));
    }
    label95: for (int j = i + 1; ; j = i)
    {
      i = j;
      break;
      return arrayOfPostParameter;
    }
  }

  private Response get(String paramString, boolean paramBoolean)
    throws WeiboException
  {
    return get(paramString, null, paramBoolean);
  }

  private String toDateStr(Date paramDate)
  {
    if (paramDate == null)
      paramDate = new Date();
    return new SimpleDateFormat("yyyy-MM-dd").format(paramDate);
  }

  public User block(String paramString)
    throws WeiboException
  {
    return new User(this.http.post(getBaseURL() + "blocks/create/" + paramString + ".xml", true), this);
  }

  public User create(String paramString)
    throws WeiboException
  {
    return createFriendship(paramString);
  }

  public User createBlock(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "blocks/create.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("user_id", paramString);
    return new User(localHttpClient.post(str, arrayOfPostParameter, true).asJSONObject());
  }

  public User createBlockByScreenName(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "blocks/create.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("screen_name", paramString);
    return new User(localHttpClient.post(str, arrayOfPostParameter, true).asJSONObject());
  }

  public Status createFavorite(long paramLong)
    throws WeiboException
  {
    return new Status(this.http.post(getBaseURL() + "favorites/create/" + paramLong + ".json", true));
  }

  public User createFriendship(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "friendships/create.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    return new User(localHttpClient.post(str, arrayOfPostParameter, true).asJSONObject());
  }

  public User createFriendshipByScreenName(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "friendships/create.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("screen_name", paramString);
    return new User(localHttpClient.post(str, arrayOfPostParameter, true).asJSONObject());
  }

  public User createFriendshipByUserid(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "friendships/create.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("user_id", paramString);
    return new User(localHttpClient.post(str, arrayOfPostParameter, true).asJSONObject());
  }

  public SavedSearch createSavedSearch(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "saved_searches/create.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("query", paramString);
    return new SavedSearch(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public List<Tag> createTags(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "tags/create.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("tags", paramString);
    return Tag.constructTags(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public List<Tag> createTags(String[] paramArrayOfString)
    throws WeiboException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
      localStringBuffer.append(paramArrayOfString[j]).append(",");
    localStringBuffer.deleteCharAt(-1 + localStringBuffer.length());
    return createTags(localStringBuffer.toString());
  }

  public boolean destoryTag(String paramString)
    throws WeiboException
  {
    try
    {
      HttpClient localHttpClient = this.http;
      String str = getBaseURL() + "tags/destroy.json";
      PostParameter[] arrayOfPostParameter = new PostParameter[1];
      arrayOfPostParameter[0] = new PostParameter("tag_id", paramString);
      int i = localHttpClient.post(str, arrayOfPostParameter, true).asJSONObject().getInt("result");
      return i == 0;
    }
    catch (JSONException localJSONException)
    {
      throw new WeiboException(localJSONException);
    }
  }

  public List<Tag> destory_batchTags(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "tags/destroy_batch.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("ids", paramString);
    return Tag.constructTags(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public User destroy(String paramString)
    throws WeiboException
  {
    return destroyFriendship(paramString);
  }

  public User destroyBlock(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "blocks/destroy.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    return new User(localHttpClient.post(str, arrayOfPostParameter, true).asJSONObject());
  }

  public Comment destroyComment(long paramLong)
    throws WeiboException
  {
    return new Comment(this.http.delete(getBaseURL() + "statuses/comment_destroy/" + paramLong + ".json?source=" + CONSUMER_KEY, true));
  }

  public List<Comment> destroyComments(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "statuses/comment/destroy_batch.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("ids", paramString);
    return Comment.constructComments(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public List<Comment> destroyComments(String[] paramArrayOfString)
    throws WeiboException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
      localStringBuilder.append(paramArrayOfString[j]).append(',');
    localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "statuses/comment/destroy_batch.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("ids", localStringBuilder.toString());
    return Comment.constructComments(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public Status destroyFavorite(long paramLong)
    throws WeiboException
  {
    return new Status(this.http.post(getBaseURL() + "favorites/destroy/" + paramLong + ".json", true));
  }

  public List<Status> destroyFavorites(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "favorites/destroy_batch.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("ids", paramString);
    return Status.constructStatuses(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public List<Status> destroyFavorites(String[] paramArrayOfString)
    throws WeiboException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
      localStringBuilder.append(paramArrayOfString[j]).append(',');
    localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "favorites/destroy_batch.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("ids", localStringBuilder.toString());
    return Status.constructStatuses(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public User destroyFriendship(String paramString)
    throws WeiboException
  {
    return new User(this.http.post(getBaseURL() + "friendships/destroy.json", "id", paramString, true).asJSONObject());
  }

  public User destroyFriendshipByScreenName(String paramString)
    throws WeiboException
  {
    return new User(this.http.post(getBaseURL() + "friendships/destroy.json", "screen_name", paramString, true).asJSONObject());
  }

  public User destroyFriendshipByUserid(String paramString)
    throws WeiboException
  {
    return new User(this.http.post(getBaseURL() + "friendships/destroy.json", "user_id", "" + paramString, true).asJSONObject());
  }

  public SavedSearch destroySavedSearch(int paramInt)
    throws WeiboException
  {
    return new SavedSearch(this.http.post(getBaseURL() + "saved_searches/destroy/" + paramInt + ".json", true));
  }

  public Status destroyStatus(long paramLong)
    throws WeiboException
  {
    return destroyStatus(Long.toString(paramLong));
  }

  public Status destroyStatus(String paramString)
    throws WeiboException
  {
    return new Status(this.http.post(getBaseURL() + "statuses/destroy/" + paramString + ".json", new PostParameter[0], true));
  }

  public User disableNotification(String paramString)
    throws WeiboException
  {
    return new User(this.http.post(getBaseURL() + "notifications/leave/" + paramString + ".json", true).asJSONObject());
  }

  public User enableNotification(String paramString)
    throws WeiboException
  {
    return new User(this.http.post(getBaseURL() + "notifications/follow/" + paramString + ".json", true).asJSONObject());
  }

  public User endSession()
    throws WeiboException
  {
    return new User(get(getBaseURL() + "account/end_session.json", true).asJSONObject());
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    Weibo localWeibo;
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass()))
        return false;
      localWeibo = (Weibo)paramObject;
      if (!this.baseURL.equals(localWeibo.baseURL))
        return false;
      if (!this.format.equals(localWeibo.format))
        return false;
      if (!this.http.equals(localWeibo.http))
        return false;
      if (!this.searchBaseURL.equals(localWeibo.searchBaseURL))
        return false;
    }
    while (this.source.equals(localWeibo.source));
    return false;
  }

  public boolean exists(String paramString1, String paramString2)
    throws WeiboException
  {
    return existsFriendship(paramString1, paramString2);
  }

  public boolean existsBlock(String paramString)
    throws WeiboException
  {
    try
    {
      int i = get(getBaseURL() + "blocks/exists.json?user_id=" + paramString, true).asString().indexOf("<error>You are not blocking this user.</error>");
      return -1 == i;
    }
    catch (WeiboException localWeiboException)
    {
      if (localWeiboException.getStatusCode() == 404)
        return false;
      throw localWeiboException;
    }
  }

  public boolean existsFriendship(String paramString1, String paramString2)
    throws WeiboException
  {
    return -1 != get(getBaseURL() + "friendships/exists.json", "user_a", paramString1, "user_b", paramString2, true).asString().indexOf("true");
  }

  public List<Status> favorites()
    throws WeiboException
  {
    return getFavorites();
  }

  public List<Status> favorites(int paramInt)
    throws WeiboException
  {
    return getFavorites(paramInt);
  }

  public List<Status> favorites(String paramString)
    throws WeiboException
  {
    return getFavorites(paramString);
  }

  public List<Status> favorites(String paramString, int paramInt)
    throws WeiboException
  {
    return getFavorites(paramString, paramInt);
  }

  public User follow(String paramString)
    throws WeiboException
  {
    return enableNotification(paramString);
  }

  protected Response get(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean)
    throws WeiboException
  {
    PostParameter[] arrayOfPostParameter = new PostParameter[2];
    arrayOfPostParameter[0] = new PostParameter(paramString2, paramString3);
    arrayOfPostParameter[1] = new PostParameter(paramString4, paramString5);
    return get(paramString1, arrayOfPostParameter, paramBoolean);
  }

  protected Response get(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    throws WeiboException
  {
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter(paramString2, paramString3);
    return get(paramString1, arrayOfPostParameter, paramBoolean);
  }

  protected Response get(String paramString, PostParameter[] paramArrayOfPostParameter, Paging paramPaging, boolean paramBoolean)
    throws WeiboException
  {
    ArrayList localArrayList;
    PostParameter[] arrayOfPostParameter1;
    PostParameter[] arrayOfPostParameter2;
    if (paramPaging != null)
    {
      localArrayList = new ArrayList(4);
      if (-1L != paramPaging.getMaxId())
        localArrayList.add(new PostParameter("max_id", String.valueOf(paramPaging.getMaxId())));
      if (-1L != paramPaging.getSinceId())
        localArrayList.add(new PostParameter("since_id", String.valueOf(paramPaging.getSinceId())));
      if (-1 != paramPaging.getPage())
        localArrayList.add(new PostParameter("page", String.valueOf(paramPaging.getPage())));
      if (-1 != paramPaging.getCount())
      {
        if (-1 != paramString.indexOf("search"))
          localArrayList.add(new PostParameter("rpp", String.valueOf(paramPaging.getCount())));
      }
      else
      {
        arrayOfPostParameter1 = (PostParameter[])localArrayList.toArray(new PostParameter[localArrayList.size()]);
        if (paramArrayOfPostParameter == null)
          break label273;
        PostParameter[] arrayOfPostParameter3 = new PostParameter[paramArrayOfPostParameter.length + localArrayList.size()];
        System.arraycopy(paramArrayOfPostParameter, 0, arrayOfPostParameter3, 0, paramArrayOfPostParameter.length);
        System.arraycopy(arrayOfPostParameter1, 0, arrayOfPostParameter3, paramArrayOfPostParameter.length, localArrayList.size());
        arrayOfPostParameter2 = arrayOfPostParameter3;
      }
    }
    while (true)
    {
      return get(paramString, arrayOfPostParameter2, paramBoolean);
      localArrayList.add(new PostParameter("count", String.valueOf(paramPaging.getCount())));
      break;
      label273: if (arrayOfPostParameter1.length != 0)
      {
        String str = HttpClient.encodeParameters(arrayOfPostParameter1);
        if (-1 != paramString.indexOf("?"))
        {
          paramString = paramString + "&source=" + CONSUMER_KEY + "&" + str;
          arrayOfPostParameter2 = null;
        }
        else
        {
          paramString = paramString + "?source=" + CONSUMER_KEY + "&" + str;
          arrayOfPostParameter2 = null;
          continue;
          return get(paramString, paramArrayOfPostParameter, paramBoolean);
        }
      }
      else
      {
        arrayOfPostParameter2 = null;
      }
    }
  }

  protected Response get(String paramString, PostParameter[] paramArrayOfPostParameter, boolean paramBoolean)
    throws WeiboException
  {
    String str;
    if ((paramArrayOfPostParameter != null) && (paramArrayOfPostParameter.length > 0))
    {
      str = HttpClient.encodeParameters(paramArrayOfPostParameter);
      if (-1 != paramString.indexOf("?"))
        break label62;
    }
    label62: for (paramString = paramString + "?" + str; ; paramString = paramString + "&" + str)
      return this.http.get(paramString, paramBoolean);
  }

  public User getAuthenticatedUser()
    throws WeiboException
  {
    return new User(get(getBaseURL() + "account/verify_credentials.xml", true), this);
  }

  public String getBaseURL()
  {
    return this.baseURL;
  }

  public List<User> getBlockingUsers()
    throws WeiboException
  {
    return User.constructUsers(get(getBaseURL() + "blocks/blocking.json", true));
  }

  public List<User> getBlockingUsers(int paramInt)
    throws WeiboException
  {
    return User.constructUsers(get(getBaseURL() + "blocks/blocking.json?page=" + paramInt, true));
  }

  public IDs getBlockingUsersIDs()
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "blocks/blocking/ids.json", true), this);
  }

  public List<Comment> getComments(String paramString)
    throws WeiboException
  {
    return Comment.constructComments(get(getBaseURL() + "statuses/comments.json", "id", paramString, true));
  }

  public List<Comment> getComments(String paramString, Paging paramPaging)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/comments.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    return Comment.constructComments(get(str, arrayOfPostParameter, paramPaging, true));
  }

  public List<Comment> getCommentsByMe()
    throws WeiboException
  {
    return Comment.constructComments(get(getBaseURL() + "statuses/comments_by_me.json", true));
  }

  public List<Comment> getCommentsByMe(Paging paramPaging)
    throws WeiboException
  {
    return Comment.constructComments(get(getBaseURL() + "statuses/comments_by_me.json", null, paramPaging, true));
  }

  public List<Comment> getCommentsTimeline()
    throws WeiboException
  {
    return Comment.constructComments(get(getBaseURL() + "statuses/comments_timeline.json", true));
  }

  public List<Comment> getCommentsTimeline(Paging paramPaging)
    throws WeiboException
  {
    return Comment.constructComments(get(getBaseURL() + "statuses/comments_timeline.json", null, paramPaging, true));
  }

  public List<Comment> getCommentsToMe()
    throws WeiboException
  {
    return Comment.constructComments(get(getBaseURL() + "statuses/comments_to_me.json", true));
  }

  public List<Comment> getCommentsToMe(Paging paramPaging)
    throws WeiboException
  {
    return Comment.constructComments(get(getBaseURL() + "statuses/comments_to_me.json", null, paramPaging, true));
  }

  public List<Count> getCounts(String paramString)
    throws WeiboException
  {
    return Count.constructCounts(get(getBaseURL() + "statuses/counts.json", "ids", paramString, true));
  }

  public List<Trends> getDailyTrends()
    throws WeiboException
  {
    return Trends.constructTrendsList(get(this.searchBaseURL + "trends/daily.json", false));
  }

  public List<Trends> getDailyTrends(Date paramDate, boolean paramBoolean)
    throws WeiboException
  {
    StringBuilder localStringBuilder = new StringBuilder().append(this.searchBaseURL).append("trends/daily.json?date=").append(toDateStr(paramDate));
    if (paramBoolean);
    for (String str = "&exclude=hashtags"; ; str = "")
      return Trends.constructTrendsList(get(str, false));
  }

  public String getDowntimeSchedule()
    throws WeiboException
  {
    throw new WeiboException("this method is not supported by the Weibo API anymore", new NoSuchMethodException("this method is not supported by the Weibo API anymore"));
  }

  public List<Emotion> getEmotions()
    throws WeiboException
  {
    return getEmotions(null, null);
  }

  public List<Emotion> getEmotions(String paramString1, String paramString2)
    throws WeiboException
  {
    if (paramString1 == null)
      paramString1 = "face";
    if (paramString2 == null)
      paramString2 = "cnname";
    HashMap localHashMap = new HashMap();
    localHashMap.put("type", paramString1);
    localHashMap.put("language", paramString2);
    return Emotion.constructEmotions(get(getBaseURL() + "emotions.json", generateParameterArray(localHashMap), true));
  }

  public List<Status> getFavorites()
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "favorites.json", new PostParameter[0], true));
  }

  public List<Status> getFavorites(int paramInt)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "favorites.json", "page", String.valueOf(paramInt), true));
  }

  public List<Status> getFavorites(String paramString)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "favorites/" + paramString + ".json", new PostParameter[0], true));
  }

  public List<Status> getFavorites(String paramString, int paramInt)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "favorites/" + paramString + ".json", "page", String.valueOf(paramInt), true));
  }

  public List<User> getFeatured()
    throws WeiboException
  {
    return User.constructUsers(get(getBaseURL() + "statuses/featured.json", true));
  }

  public List<User> getFollowers()
    throws WeiboException
  {
    return getFollowersStatuses();
  }

  public List<User> getFollowers(int paramInt)
    throws WeiboException
  {
    return getFollowersStatuses(paramInt);
  }

  public List<User> getFollowers(String paramString)
    throws WeiboException
  {
    return getFollowersStatuses(paramString);
  }

  public List<User> getFollowers(String paramString, int paramInt)
    throws WeiboException
  {
    return getFollowersStatuses(paramString, paramInt);
  }

  public List<User> getFollowers(String paramString, Paging paramPaging)
    throws WeiboException
  {
    throw new IllegalStateException("The Weibo API is not supporting this method anymore");
  }

  public List<User> getFollowers(Paging paramPaging)
    throws WeiboException
  {
    throw new IllegalStateException("The Weibo API is not supporting this method anymore");
  }

  public IDs getFollowersIDSByScreenName(String paramString, int paramInt)
    throws WeiboException
  {
    String str = this.baseURL + "followers/ids.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[2];
    arrayOfPostParameter[0] = new PostParameter("screen_name", paramString);
    arrayOfPostParameter[1] = new PostParameter("cursor", paramInt);
    return new IDs(get(str, arrayOfPostParameter, true), this);
  }

  public IDs getFollowersIDSByUserId(String paramString, int paramInt)
    throws WeiboException
  {
    String str = this.baseURL + "followers/ids.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[2];
    arrayOfPostParameter[0] = new PostParameter("user_id", paramString);
    arrayOfPostParameter[1] = new PostParameter("cursor", paramInt);
    return new IDs(get(str, arrayOfPostParameter, true), this);
  }

  public IDs getFollowersIDs()
    throws WeiboException
  {
    return getFollowersIDs(-1L);
  }

  public IDs getFollowersIDs(int paramInt)
    throws WeiboException
  {
    return getFollowersIDs(paramInt, -1L);
  }

  public IDs getFollowersIDs(int paramInt, long paramLong)
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "followers/ids.xml?user_id=" + paramInt + "&cursor=" + paramLong, true));
  }

  public IDs getFollowersIDs(int paramInt, Paging paramPaging)
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "followers/ids.xml?user_id=" + paramInt, null, paramPaging, true));
  }

  public IDs getFollowersIDs(long paramLong)
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "followers/ids.json?cursor=" + paramLong, true), this);
  }

  public IDs getFollowersIDs(String paramString)
    throws WeiboException
  {
    return getFollowersIDs(paramString, -1L);
  }

  public IDs getFollowersIDs(String paramString, long paramLong)
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "followers/ids.json?screen_name=" + paramString + "&cursor=" + paramLong, true), this);
  }

  public IDs getFollowersIDs(String paramString, Paging paramPaging)
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "followers/ids.xml?screen_name=" + paramString, null, paramPaging, true));
  }

  public IDs getFollowersIDs(Paging paramPaging)
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "followers/ids.xml", null, paramPaging, true));
  }

  public List<User> getFollowersStatuses()
    throws WeiboException
  {
    return User.constructResult(get(getBaseURL() + "statuses/followers.json", true));
  }

  public List<User> getFollowersStatuses(int paramInt)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/followers.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("cursor", paramInt);
    return User.constructUser(get(str, arrayOfPostParameter, true));
  }

  public List<User> getFollowersStatuses(String paramString)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/followers.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    return User.constructUsers(get(str, arrayOfPostParameter, true));
  }

  public List<User> getFollowersStatuses(String paramString, int paramInt)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/followers.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[2];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    arrayOfPostParameter[1] = new PostParameter("cursor", paramInt);
    return User.constructUser(get(str, arrayOfPostParameter, true));
  }

  public List<User> getFollowersStatuses(String paramString, int paramInt1, int paramInt2)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/followers.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[3];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    arrayOfPostParameter[1] = new PostParameter("cursor", paramInt1);
    arrayOfPostParameter[2] = new PostParameter("count", paramInt2);
    return User.constructUsers(get(str, arrayOfPostParameter, true));
  }

  public List<User> getFriends()
    throws WeiboException
  {
    return getFriendsStatuses();
  }

  public List<User> getFriends(int paramInt)
    throws WeiboException
  {
    return getFriendsStatuses(paramInt);
  }

  public List<User> getFriends(String paramString)
    throws WeiboException
  {
    return getFriendsStatuses(paramString);
  }

  public List<User> getFriends(String paramString, int paramInt)
    throws WeiboException
  {
    return getFriendsStatuses(paramString, paramInt);
  }

  public List<User> getFriends(String paramString, Paging paramPaging)
    throws WeiboException
  {
    throw new IllegalStateException("The Weibo API is not supporting this method anymore");
  }

  public List<User> getFriends(Paging paramPaging)
    throws WeiboException
  {
    throw new IllegalStateException("The Weibo API is not supporting this method anymore");
  }

  public IDs getFriendsIDSByScreenName(String paramString, int paramInt)
    throws WeiboException
  {
    String str = this.baseURL + "friends/ids.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[2];
    arrayOfPostParameter[0] = new PostParameter("screen_name", paramString);
    arrayOfPostParameter[1] = new PostParameter("cursor", paramInt);
    return new IDs(get(str, arrayOfPostParameter, true), this);
  }

  public IDs getFriendsIDSByUserId(String paramString, int paramInt)
    throws WeiboException
  {
    String str = this.baseURL + "friends/ids.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[2];
    arrayOfPostParameter[0] = new PostParameter("user_id", paramString);
    arrayOfPostParameter[1] = new PostParameter("cursor", paramInt);
    return new IDs(get(str, arrayOfPostParameter, true), this);
  }

  public IDs getFriendsIDs()
    throws WeiboException
  {
    return getFriendsIDs(-1L);
  }

  public IDs getFriendsIDs(int paramInt)
    throws WeiboException
  {
    return getFriendsIDs(paramInt, -1L);
  }

  public IDs getFriendsIDs(int paramInt, long paramLong)
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "friends/ids.json?user_id=" + paramInt + "&cursor=" + paramLong, true), this);
  }

  public IDs getFriendsIDs(int paramInt, Paging paramPaging)
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "friends/ids.xml?user_id=" + paramInt, null, paramPaging, true));
  }

  public IDs getFriendsIDs(long paramLong)
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "friends/ids.xml?cursor=" + paramLong, true));
  }

  public IDs getFriendsIDs(String paramString)
    throws WeiboException
  {
    return getFriendsIDs(paramString, -1L);
  }

  public IDs getFriendsIDs(String paramString, long paramLong)
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "friends/ids.json?screen_name=" + paramString + "&cursor=" + paramLong, true), this);
  }

  public IDs getFriendsIDs(String paramString, Paging paramPaging)
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "friends/ids.xml?screen_name=" + paramString, null, paramPaging, true));
  }

  public IDs getFriendsIDs(Paging paramPaging)
    throws WeiboException
  {
    return new IDs(get(getBaseURL() + "friends/ids.xml", null, paramPaging, true));
  }

  public List<User> getFriendsStatuses()
    throws WeiboException
  {
    return User.constructResult(get(getBaseURL() + "statuses/friends.json", true));
  }

  public List<User> getFriendsStatuses(int paramInt)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/friends.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("cursor", paramInt);
    return User.constructUser(get(str, arrayOfPostParameter, true));
  }

  public List<User> getFriendsStatuses(String paramString)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/friends.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    return User.constructUsers(get(str, arrayOfPostParameter, true));
  }

  public List<User> getFriendsStatuses(String paramString, int paramInt)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/friends.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[2];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    arrayOfPostParameter[1] = new PostParameter("cursor", paramInt);
    return User.constructUsers(get(str, arrayOfPostParameter, true));
  }

  public List<User> getFriendsStatuses(String paramString, int paramInt1, int paramInt2)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/friends.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[3];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    arrayOfPostParameter[1] = new PostParameter("cursor", paramInt1);
    arrayOfPostParameter[2] = new PostParameter("count", paramInt2);
    return User.constructUsers(get(str, arrayOfPostParameter, true));
  }

  public List<Status> getFriendsTimeline()
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/friends_timeline.json", true));
  }

  public List<Status> getFriendsTimeline(int paramInt)
    throws WeiboException
  {
    return getFriendsTimeline(new Paging(paramInt));
  }

  public List<Status> getFriendsTimeline(long paramLong)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/friends_timeline.xml", "since_id", String.valueOf(paramLong), true), this);
  }

  public List<Status> getFriendsTimeline(long paramLong, int paramInt)
    throws WeiboException
  {
    return getFriendsTimeline(new Paging(paramInt).sinceId(paramLong));
  }

  public List<Status> getFriendsTimeline(long paramLong, String paramString, int paramInt)
    throws WeiboException
  {
    throw new IllegalStateException("The Weibo API is not supporting this method anymore");
  }

  public List<Status> getFriendsTimeline(String paramString)
    throws WeiboException
  {
    throw new IllegalStateException("The Weibo API is not supporting this method anymore");
  }

  public List<Status> getFriendsTimeline(String paramString, int paramInt)
    throws WeiboException
  {
    throw new IllegalStateException("The Weibo API is not supporting this method anymore");
  }

  public List<Status> getFriendsTimeline(String paramString, long paramLong)
    throws WeiboException
  {
    throw new IllegalStateException("The Weibo API is not supporting this method anymore");
  }

  public List<Status> getFriendsTimeline(String paramString, Date paramDate)
    throws WeiboException
  {
    throw new IllegalStateException("The Weibo API is not supporting this method anymore");
  }

  public List<Status> getFriendsTimeline(String paramString, Paging paramPaging)
    throws WeiboException
  {
    throw new IllegalStateException("The Weibo API is not supporting this method anymore");
  }

  public List<Status> getFriendsTimeline(Date paramDate)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/friends_timeline.xml", "since", this.format.format(paramDate), true), this);
  }

  public List<Status> getFriendsTimeline(Paging paramPaging)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/friends_timeline.json", null, paramPaging, true));
  }

  public List<Status> getFriendsTimelineByPage(int paramInt)
    throws WeiboException
  {
    return getFriendsTimeline(new Paging(paramInt));
  }

  public List<Status> getFriendsTimelineByPage(String paramString, int paramInt)
    throws WeiboException
  {
    throw new IllegalStateException("The Weibo API is not supporting this method anymore");
  }

  public List<Status> getHomeTimeline()
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/home_timeline.json", true));
  }

  public List<Status> getHomeTimeline(Paging paramPaging)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/home_timeline.json", null, paramPaging, true));
  }

  public List<User> getHotUsers(String paramString)
    throws WeiboException
  {
    return User.constructUsers(get(getBaseURL() + "users/hot.json", "category", paramString, true));
  }

  public UserWapper getListMembers(String paramString1, String paramString2, boolean paramBoolean)
    throws WeiboException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getBaseURL()).append(paramString1).append("/").append(paramString2).append("/members.xml").append("?source=").append(CONSUMER_KEY);
    String str = localStringBuilder.toString();
    return User.constructWapperUsers(this.http.httpRequest(str, null, paramBoolean, "GET"), this);
  }

  public List<Status> getListStatuses(String paramString1, String paramString2, boolean paramBoolean)
    throws WeiboException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getBaseURL()).append(paramString1).append("/lists/").append(paramString2).append("/statuses.xml").append("?source=").append(CONSUMER_KEY);
    String str = localStringBuilder.toString();
    return Status.constructStatuses(this.http.httpRequest(str, null, paramBoolean, "GET"), this);
  }

  public UserWapper getListSubscribers(String paramString1, String paramString2, boolean paramBoolean)
    throws WeiboException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getBaseURL()).append(paramString1).append("/").append(paramString2).append("/subscribers.xml").append("?source=").append(CONSUMER_KEY);
    String str = localStringBuilder.toString();
    return User.constructWapperUsers(this.http.httpRequest(str, null, paramBoolean, "GET"), this);
  }

  public List<Status> getMentions()
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/mentions.json", null, true));
  }

  public List<Status> getMentions(Paging paramPaging)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/mentions.json", null, paramPaging, true));
  }

  public AccessToken getOAuthAccessToken(String paramString1, String paramString2)
    throws WeiboException
  {
    try
    {
      AccessToken localAccessToken = this.http.getOAuthAccessToken(paramString1, paramString2);
      return localAccessToken;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public AccessToken getOAuthAccessToken(String paramString1, String paramString2, String paramString3)
    throws WeiboException
  {
    try
    {
      AccessToken localAccessToken = this.http.getOAuthAccessToken(paramString1, paramString2, paramString3);
      return localAccessToken;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public AccessToken getOAuthAccessToken(RequestToken paramRequestToken)
    throws WeiboException
  {
    try
    {
      AccessToken localAccessToken = this.http.getOAuthAccessToken(paramRequestToken);
      return localAccessToken;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public AccessToken getOAuthAccessToken(RequestToken paramRequestToken, String paramString)
    throws WeiboException
  {
    try
    {
      AccessToken localAccessToken = this.http.getOAuthAccessToken(paramRequestToken, paramString);
      return localAccessToken;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public RequestToken getOAuthRequestToken()
    throws WeiboException
  {
    return this.http.getOAuthRequestToken();
  }

  public RequestToken getOAuthRequestToken(String paramString)
    throws WeiboException
  {
    return this.http.getOauthRequestToken(paramString);
  }

  public List<Status> getPublicTimeline()
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/public_timeline.json", true));
  }

  public List<Status> getPublicTimeline(int paramInt)
    throws WeiboException
  {
    return getPublicTimeline(paramInt);
  }

  public List<Status> getPublicTimeline(int paramInt1, int paramInt2)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/public_timeline.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[2];
    arrayOfPostParameter[0] = new PostParameter("count", paramInt1);
    arrayOfPostParameter[1] = new PostParameter("base_app", paramInt2);
    return Status.constructStatuses(get(str, arrayOfPostParameter, false));
  }

  public List<Status> getPublicTimeline(long paramLong)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/public_timeline.json", null, new Paging(paramLong), false));
  }

  public RateLimitStatus getRateLimitStatus()
    throws WeiboException
  {
    return new RateLimitStatus(get(getBaseURL() + "account/rate_limit_status.json", true), this);
  }

  public List<Status> getReplies()
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/replies.xml", true), this);
  }

  public List<Status> getReplies(int paramInt)
    throws WeiboException
  {
    if (paramInt < 1)
      throw new IllegalArgumentException("page should be positive integer. passed:" + paramInt);
    return Status.constructStatuses(get(getBaseURL() + "statuses/replies.xml", "page", String.valueOf(paramInt), true), this);
  }

  public List<Status> getReplies(long paramLong)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/replies.xml", "since_id", String.valueOf(paramLong), true), this);
  }

  public List<Status> getReplies(long paramLong, int paramInt)
    throws WeiboException
  {
    if (paramInt < 1)
      throw new IllegalArgumentException("page should be positive integer. passed:" + paramInt);
    return Status.constructStatuses(get(getBaseURL() + "statuses/replies.xml", "since_id", String.valueOf(paramLong), "page", String.valueOf(paramInt), true), this);
  }

  public List<Status> getRepliesByPage(int paramInt)
    throws WeiboException
  {
    if (paramInt < 1)
      throw new IllegalArgumentException("page should be positive integer. passed:" + paramInt);
    return Status.constructStatuses(get(getBaseURL() + "statuses/replies.xml", "page", String.valueOf(paramInt), true), this);
  }

  public List<Status> getRetweetedByMe()
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/retweeted_by_me.json", null, true));
  }

  public List<Status> getRetweetedByMe(Paging paramPaging)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/retweeted_by_me.json", null, true));
  }

  public List<Status> getRetweetedToMe()
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/retweeted_to_me.json", null, true));
  }

  public List<Status> getRetweetedToMe(Paging paramPaging)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/retweeted_to_me.json", null, paramPaging, true));
  }

  public List<Status> getRetweetsOfMe()
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/retweets_of_me.json", null, true));
  }

  public List<Status> getRetweetsOfMe(Paging paramPaging)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/retweets_of_me.json", null, paramPaging, true));
  }

  public List<SavedSearch> getSavedSearches()
    throws WeiboException
  {
    return SavedSearch.constructSavedSearches(get(getBaseURL() + "saved_searches.json", true));
  }

  public String getSearchBaseURL()
  {
    return this.searchBaseURL;
  }

  public List<User> getSuggestionUsers()
    throws WeiboException
  {
    return User.constructUsers(get(getBaseURL() + "users/suggestions.json", "with_reason", "0", true));
  }

  public List<Tag> getSuggestions()
    throws WeiboException
  {
    return Tag.constructTags(get(getBaseURL() + "tags/suggestions.json", true));
  }

  public List<Tag> getSuggestionsTags()
    throws WeiboException
  {
    return Tag.constructTags(get(getBaseURL() + "tags/suggestions.json", true));
  }

  public List<Tag> getTags(String paramString, Paging paramPaging)
    throws WeiboException
  {
    String str = getBaseURL() + "tags.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("user_id", paramString);
    return Tag.constructTags(get(str, arrayOfPostParameter, paramPaging, true));
  }

  public List<Status> getTrendStatus(String paramString, Paging paramPaging)
    throws WeiboException
  {
    String str = this.baseURL + "trends/statuses.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("trend_name", paramString);
    return Status.constructStatuses(get(str, arrayOfPostParameter, paramPaging, true));
  }

  public List<UserTrend> getTrends(String paramString, Paging paramPaging)
    throws WeiboException
  {
    String str = this.baseURL + "trends.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("user_id", paramString);
    return UserTrend.constructTrendList(get(str, arrayOfPostParameter, paramPaging, true));
  }

  public List<Trends> getTrendsDaily(Integer paramInteger)
    throws WeiboException
  {
    if (paramInteger == null)
      paramInteger = Integer.valueOf(0);
    return Trends.constructTrendsList(get(this.baseURL + "trends/daily.json", "base_app", paramInteger.toString(), true));
  }

  public List<Trends> getTrendsHourly(Integer paramInteger)
    throws WeiboException
  {
    if (paramInteger == null)
      paramInteger = Integer.valueOf(0);
    return Trends.constructTrendsList(get(this.baseURL + "trends/hourly.json", "base_app", paramInteger.toString(), true));
  }

  public List<Trends> getTrendsWeekly(Integer paramInteger)
    throws WeiboException
  {
    if (paramInteger == null)
      paramInteger = Integer.valueOf(0);
    return Trends.constructTrendsList(get(this.baseURL + "trends/weekly.json", "base_app", paramInteger.toString(), true));
  }

  public Count getUnread()
    throws WeiboException
  {
    return new Count(get(getBaseURL() + "statuses/unread.json", true));
  }

  public Count getUnread(Integer paramInteger, Long paramLong)
    throws WeiboException, JSONException
  {
    HashMap localHashMap = new HashMap();
    if (paramInteger != null)
      localHashMap.put("with_new_status", Integer.toString(paramInteger.intValue()));
    if (paramLong != null)
      localHashMap.put("since_id", Long.toString(paramLong.longValue()));
    return new Count(get(getBaseURL() + "statuses/unread.json", generateParameterArray(localHashMap), true).asJSONObject());
  }

  public User getUserDetail(String paramString)
    throws WeiboException
  {
    return showUser(paramString);
  }

  public List<Status> getUserTimeline()
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/user_timeline.json", true));
  }

  public List<Status> getUserTimeline(int paramInt, long paramLong)
    throws WeiboException
  {
    return getUserTimeline(new Paging(paramLong).count(paramInt));
  }

  public List<Status> getUserTimeline(int paramInt, Date paramDate)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/user_timeline.xml", "since", this.format.format(paramDate), "count", String.valueOf(paramInt), true), this);
  }

  public List<Status> getUserTimeline(long paramLong)
    throws WeiboException
  {
    return getUserTimeline(new Paging(paramLong));
  }

  public List<Status> getUserTimeline(Integer paramInteger1, Integer paramInteger2, Paging paramPaging)
    throws WeiboException
  {
    return getUserTimeline(null, paramInteger1, paramInteger2, paramPaging);
  }

  public List<Status> getUserTimeline(String paramString)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/user_timeline.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    return Status.constructStatuses(get(str, arrayOfPostParameter, this.http.isAuthenticationEnabled()));
  }

  public List<Status> getUserTimeline(String paramString, int paramInt)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/user_timeline/" + paramString + ".xml", "count", String.valueOf(paramInt), this.http.isAuthenticationEnabled()), this);
  }

  public List<Status> getUserTimeline(String paramString, int paramInt, long paramLong)
    throws WeiboException
  {
    return getUserTimeline(paramString, new Paging(paramLong).count(paramInt));
  }

  public List<Status> getUserTimeline(String paramString, int paramInt, Date paramDate)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/user_timeline/" + paramString + ".xml", "since", this.format.format(paramDate), "count", String.valueOf(paramInt), this.http.isAuthenticationEnabled()), this);
  }

  public List<Status> getUserTimeline(String paramString, long paramLong)
    throws WeiboException
  {
    return getUserTimeline(paramString, new Paging(paramLong));
  }

  public List<Status> getUserTimeline(String paramString, Integer paramInteger1, Integer paramInteger2, Paging paramPaging)
    throws WeiboException
  {
    HashMap localHashMap = new HashMap();
    if (paramString != null)
      localHashMap.put("id", paramString);
    if (paramInteger1 != null)
      localHashMap.put("base_app", paramInteger1.toString());
    if (paramInteger2 != null)
      localHashMap.put("feature", paramInteger2.toString());
    return Status.constructStatuses(get(getBaseURL() + "statuses/user_timeline.json", generateParameterArray(localHashMap), paramPaging, this.http.isAuthenticationEnabled()));
  }

  public List<Status> getUserTimeline(String paramString, Date paramDate)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/user_timeline/" + paramString + ".xml", "since", this.format.format(paramDate), this.http.isAuthenticationEnabled()), this);
  }

  public List<Status> getUserTimeline(String paramString, Paging paramPaging)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/user_timeline.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    return Status.constructStatuses(get(str, arrayOfPostParameter, paramPaging, this.http.isAuthenticationEnabled()));
  }

  public List<Status> getUserTimeline(Paging paramPaging)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/user_timeline.json", null, paramPaging, true));
  }

  public List<Trends> getWeeklyTrends()
    throws WeiboException
  {
    return Trends.constructTrendsList(get(this.searchBaseURL + "trends/weekly.json", false));
  }

  public List<Trends> getWeeklyTrends(Date paramDate, boolean paramBoolean)
    throws WeiboException
  {
    StringBuilder localStringBuilder = new StringBuilder().append(this.searchBaseURL).append("trends/weekly.json?date=").append(toDateStr(paramDate));
    if (paramBoolean);
    for (String str = "&exclude=hashtags"; ; str = "")
      return Trends.constructTrendsList(get(str, false));
  }

  public AccessToken getXAuthAccessToken(String paramString1, String paramString2)
    throws WeiboException
  {
    try
    {
      AccessToken localAccessToken = getXAuthAccessToken(paramString1, paramString2, "client_auth");
      return localAccessToken;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public AccessToken getXAuthAccessToken(String paramString1, String paramString2, String paramString3)
    throws WeiboException
  {
    try
    {
      AccessToken localAccessToken = this.http.getXAuthAccessToken(paramString1, paramString2, paramString3);
      return localAccessToken;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public List<Status> getrepostbyme(String paramString)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/repost_by_me.json", "id", paramString, true));
  }

  public List<Status> getrepostbyme(String paramString, Paging paramPaging)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/repost_by_me.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    return Status.constructStatuses(get(str, arrayOfPostParameter, paramPaging, true));
  }

  public List<Status> getreposttimeline(String paramString)
    throws WeiboException
  {
    return Status.constructStatuses(get(getBaseURL() + "statuses/repost_timeline.json", "id", paramString, true));
  }

  public List<Status> getreposttimeline(String paramString, Paging paramPaging)
    throws WeiboException
  {
    String str = getBaseURL() + "statuses/repost_timeline.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    return Status.constructStatuses(get(str, arrayOfPostParameter, paramPaging, true));
  }

  public List<Tag> gettags(String paramString)
    throws WeiboException
  {
    return Tag.constructTags(this.http.get(getBaseURL() + "tags.json?" + "user_id=" + paramString, true));
  }

  public int hashCode()
  {
    return 31 * (31 * (31 * (31 * this.http.hashCode() + this.baseURL.hashCode()) + this.searchBaseURL.hashCode()) + this.source.hashCode()) + this.format.hashCode();
  }

  public boolean isListMember(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    throws WeiboException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getBaseURL()).append(paramString1).append("/").append(paramString2).append("/members/").append(paramString3).append(".xml").append("?source=").append(CONSUMER_KEY);
    String str = localStringBuilder.toString();
    return "true".equals(this.http.httpRequest(str, null, paramBoolean, "GET").asDocument().getDocumentElement().getNodeValue());
  }

  public boolean isListSubscriber(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    throws WeiboException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getBaseURL()).append(paramString1).append("/").append(paramString2).append("/subscribers/").append(paramString3).append(".xml").append("?source=").append(CONSUMER_KEY);
    String str = localStringBuilder.toString();
    return "true".equals(this.http.httpRequest(str, null, paramBoolean, "GET").asDocument().getDocumentElement().getNodeValue());
  }

  public User leave(String paramString)
    throws WeiboException
  {
    return disableNotification(paramString);
  }

  public RateLimitStatus rateLimitStatus()
    throws WeiboException
  {
    return new RateLimitStatus(this.http.get(getBaseURL() + "account/rate_limit_status.json", true), this);
  }

  public JSONObject register(String paramString, String[] paramArrayOfString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "account/register.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[5];
    arrayOfPostParameter[0] = new PostParameter("nick", paramArrayOfString[2]);
    arrayOfPostParameter[1] = new PostParameter("gender", paramArrayOfString[3]);
    arrayOfPostParameter[2] = new PostParameter("password", paramArrayOfString[4]);
    arrayOfPostParameter[3] = new PostParameter("email", paramArrayOfString[5]);
    arrayOfPostParameter[4] = new PostParameter("ip", paramString);
    return localHttpClient.post(str, arrayOfPostParameter, true).asJSONObject();
  }

  public Comment reply(String paramString1, String paramString2, String paramString3)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "statuses/reply.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[3];
    arrayOfPostParameter[0] = new PostParameter("id", paramString1);
    arrayOfPostParameter[1] = new PostParameter("cid", paramString2);
    arrayOfPostParameter[2] = new PostParameter("comment", paramString3);
    return new Comment(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public Status repost(String paramString1, String paramString2)
    throws WeiboException
  {
    return repost(paramString1, paramString2, 0);
  }

  public Status repost(String paramString1, String paramString2, int paramInt)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "statuses/repost.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[3];
    arrayOfPostParameter[0] = new PostParameter("id", paramString1);
    arrayOfPostParameter[1] = new PostParameter("status", paramString2);
    arrayOfPostParameter[2] = new PostParameter("is_comment", paramInt);
    return new Status(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public Boolean resetCount(int paramInt)
    throws WeiboException
  {
    JSONObject localJSONObject = null;
    try
    {
      HttpClient localHttpClient = this.http;
      String str = getBaseURL() + "statuses/reset_count.json";
      PostParameter[] arrayOfPostParameter = new PostParameter[1];
      arrayOfPostParameter[0] = new PostParameter("type", paramInt);
      localJSONObject = localHttpClient.post(str, arrayOfPostParameter, true).asJSONObject();
      boolean bool = localJSONObject.getBoolean("result");
      return Boolean.valueOf(bool);
    }
    catch (JSONException localJSONException)
    {
      throw new WeiboException(localJSONException.getMessage() + ":" + localJSONObject, localJSONException);
    }
  }

  public Status retweetStatus(long paramLong)
    throws WeiboException
  {
    return new Status(this.http.post(getBaseURL() + "statuses/retweet/" + paramLong + ".json", new PostParameter[0], true));
  }

  public void setBaseURL(String paramString)
  {
    this.baseURL = paramString;
  }

  public void setOAuthAccessToken(String paramString1, String paramString2)
  {
    setOAuthAccessToken(new AccessToken(paramString1, paramString2));
  }

  public void setOAuthAccessToken(AccessToken paramAccessToken)
  {
    this.http.setOAuthAccessToken(paramAccessToken);
  }

  public void setOAuthConsumer(String paramString1, String paramString2)
  {
    try
    {
      this.http.setOAuthConsumer(paramString1, paramString2);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setSearchBaseURL(String paramString)
  {
    this.searchBaseURL = paramString;
  }

  public void setToken(String paramString1, String paramString2)
  {
    this.http.setToken(paramString1, paramString2);
  }

  public void setToken(AccessToken paramAccessToken)
  {
    setToken(paramAccessToken.getToken(), paramAccessToken.getTokenSecret());
  }

  public Status show(int paramInt)
    throws WeiboException
  {
    return showStatus(paramInt);
  }

  public Status show(long paramLong)
    throws WeiboException
  {
    return new Status(get(getBaseURL() + "statuses/show/" + paramLong + ".xml", false), this);
  }

  public JSONObject showFriendships(String paramString)
    throws WeiboException
  {
    return get(getBaseURL() + "friendships/show.json?target_id=" + paramString, true).asJSONObject();
  }

  public JSONObject showFriendships(String paramString1, String paramString2)
    throws WeiboException
  {
    return get(getBaseURL() + "friendships/show.json?target_id=" + paramString2 + "&source_id=" + paramString1 + "&source=" + CONSUMER_KEY, true).asJSONObject();
  }

  public SavedSearch showSavedSearch(int paramInt)
    throws WeiboException
  {
    return new SavedSearch(get(getBaseURL() + "saved_searches/show/" + paramInt + ".json", true));
  }

  public Status showStatus(long paramLong)
    throws WeiboException
  {
    return showStatus(Long.toString(paramLong));
  }

  public Status showStatus(String paramString)
    throws WeiboException
  {
    return new Status(get(getBaseURL() + "statuses/show/" + paramString + ".json", true));
  }

  public User showUser(String paramString)
    throws WeiboException
  {
    String str = getBaseURL() + "users/show.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("id", paramString);
    return new User(get(str, arrayOfPostParameter, this.http.isAuthenticationEnabled()).asJSONObject());
  }

  public boolean test()
    throws WeiboException
  {
    int i = get(getBaseURL() + "help/test.json", false).asString().indexOf("ok");
    boolean bool = false;
    if (-1 != i)
      bool = true;
    return bool;
  }

  public String toString()
  {
    return "Weibo{http=" + this.http + ", baseURL='" + this.baseURL + '\'' + ", searchBaseURL='" + this.searchBaseURL + '\'' + ", source='" + this.source + '\'' + ", format=" + this.format + '}';
  }

  public boolean trendsDestroy(String paramString)
    throws WeiboException
  {
    JSONObject localJSONObject = this.http.delete(this.baseURL + "trends/destroy.json?trend_id=" + paramString + "&source=" + CONSUMER_KEY, true).asJSONObject();
    try
    {
      boolean bool = localJSONObject.getBoolean("result");
      return bool;
    }
    catch (JSONException localJSONException)
    {
    }
    throw new WeiboException("e");
  }

  public UserTrend trendsFollow(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = this.baseURL + "trends/follow.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("trend_name", paramString);
    return new UserTrend(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public User unblock(String paramString)
    throws WeiboException
  {
    return new User(this.http.post(getBaseURL() + "blocks/destroy/" + paramString + ".xml", true), this);
  }

  public Status update(String paramString)
    throws WeiboException
  {
    return updateStatus(paramString);
  }

  public Status update(String paramString, long paramLong)
    throws WeiboException
  {
    return updateStatus(paramString, paramLong);
  }

  public Comment updateComment(String paramString1, String paramString2, String paramString3)
    throws WeiboException
  {
    PostParameter[] arrayOfPostParameter;
    if (paramString3 == null)
    {
      arrayOfPostParameter = new PostParameter[2];
      arrayOfPostParameter[0] = new PostParameter("comment", paramString1);
      arrayOfPostParameter[1] = new PostParameter("id", paramString2);
    }
    while (true)
    {
      return new Comment(this.http.post(getBaseURL() + "statuses/comment.json", arrayOfPostParameter, true));
      arrayOfPostParameter = new PostParameter[3];
      arrayOfPostParameter[0] = new PostParameter("comment", paramString1);
      arrayOfPostParameter[1] = new PostParameter("cid", paramString3);
      arrayOfPostParameter[2] = new PostParameter("id", paramString2);
    }
  }

  public User updateLocation(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "account/update_location.xml";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("location", paramString);
    return new User(localHttpClient.post(str, arrayOfPostParameter, true), this);
  }

  public User updatePrivacy(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "account/update_privacy.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("comment", paramString);
    return new User(localHttpClient.post(str, arrayOfPostParameter, true).asJSONObject());
  }

  public User updateProfile(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws WeiboException
  {
    ArrayList localArrayList = new ArrayList(5);
    addParameterToList(localArrayList, "name", paramString1);
    addParameterToList(localArrayList, "email", paramString2);
    addParameterToList(localArrayList, "url", paramString3);
    addParameterToList(localArrayList, "location", paramString4);
    addParameterToList(localArrayList, "description", paramString5);
    return new User(this.http.post(getBaseURL() + "account/update_profile.json", (PostParameter[])localArrayList.toArray(new PostParameter[localArrayList.size()]), true).asJSONObject());
  }

  public User updateProfileImage(File paramFile)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "account/update_profile_image.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("source", CONSUMER_KEY);
    return new User(localHttpClient.multPartURL("image", str, arrayOfPostParameter, paramFile, true).asJSONObject());
  }

  public User updateRemark(Long paramLong, String paramString)
    throws WeiboException
  {
    return updateRemark(Long.toString(paramLong.longValue()), paramString);
  }

  public User updateRemark(String paramString1, String paramString2)
    throws WeiboException
  {
    if (!URLEncodeUtils.isURLEncoded(paramString2))
      paramString2 = URLEncodeUtils.encodeURL(paramString2);
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "user/friends/update_remark.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[2];
    arrayOfPostParameter[0] = new PostParameter("user_id", paramString1);
    arrayOfPostParameter[1] = new PostParameter("remark", paramString2);
    return new User(localHttpClient.post(str, arrayOfPostParameter, true).asJSONObject());
  }

  public Status updateStatus(String paramString)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "statuses/update.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[1];
    arrayOfPostParameter[0] = new PostParameter("status", paramString);
    return new Status(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public Status updateStatus(String paramString, double paramDouble1, double paramDouble2)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "statuses/update.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[3];
    arrayOfPostParameter[0] = new PostParameter("status", paramString);
    arrayOfPostParameter[1] = new PostParameter("lat", paramDouble1);
    arrayOfPostParameter[2] = new PostParameter("long", paramDouble2);
    return new Status(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public Status updateStatus(String paramString, long paramLong)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "statuses/update.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[3];
    arrayOfPostParameter[0] = new PostParameter("status", paramString);
    arrayOfPostParameter[1] = new PostParameter("in_reply_to_status_id", String.valueOf(paramLong));
    arrayOfPostParameter[2] = new PostParameter("source", this.source);
    return new Status(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public Status updateStatus(String paramString, long paramLong, double paramDouble1, double paramDouble2)
    throws WeiboException
  {
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "statuses/update.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[5];
    arrayOfPostParameter[0] = new PostParameter("status", paramString);
    arrayOfPostParameter[1] = new PostParameter("lat", paramDouble1);
    arrayOfPostParameter[2] = new PostParameter("long", paramDouble2);
    arrayOfPostParameter[3] = new PostParameter("in_reply_to_status_id", String.valueOf(paramLong));
    arrayOfPostParameter[4] = new PostParameter("source", this.source);
    return new Status(localHttpClient.post(str, arrayOfPostParameter, true));
  }

  public Status uploadStatus(String paramString, File paramFile)
    throws WeiboException
  {
    if (!URLEncodeUtils.isURLEncoded(paramString))
      paramString = URLEncodeUtils.encodeURL(paramString);
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "statuses/upload.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[2];
    arrayOfPostParameter[0] = new PostParameter("status", paramString);
    arrayOfPostParameter[1] = new PostParameter("source", this.source);
    return new Status(localHttpClient.multPartURL("pic", str, arrayOfPostParameter, paramFile, true));
  }

  public Status uploadStatus(String paramString, File paramFile, double paramDouble1, double paramDouble2)
    throws WeiboException
  {
    if (!URLEncodeUtils.isURLEncoded(paramString))
      paramString = URLEncodeUtils.encodeURL(paramString);
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "statuses/upload.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[4];
    arrayOfPostParameter[0] = new PostParameter("status", paramString);
    arrayOfPostParameter[1] = new PostParameter("source", this.source);
    arrayOfPostParameter[2] = new PostParameter("lat", paramDouble1);
    arrayOfPostParameter[3] = new PostParameter("long", paramDouble2);
    return new Status(localHttpClient.multPartURL("pic", str, arrayOfPostParameter, paramFile, true));
  }

  public Status uploadStatus(String paramString, ImageItem paramImageItem)
    throws WeiboException
  {
    if (!URLEncodeUtils.isURLEncoded(paramString))
      paramString = URLEncodeUtils.encodeURL(paramString);
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "statuses/upload.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[2];
    arrayOfPostParameter[0] = new PostParameter("status", paramString);
    arrayOfPostParameter[1] = new PostParameter("source", this.source);
    return new Status(localHttpClient.multPartURL(str, arrayOfPostParameter, paramImageItem, true));
  }

  public Status uploadStatus(String paramString, ImageItem paramImageItem, double paramDouble1, double paramDouble2)
    throws WeiboException
  {
    if (!URLEncodeUtils.isURLEncoded(paramString))
      paramString = URLEncodeUtils.encodeURL(paramString);
    HttpClient localHttpClient = this.http;
    String str = getBaseURL() + "statuses/upload.json";
    PostParameter[] arrayOfPostParameter = new PostParameter[4];
    arrayOfPostParameter[0] = new PostParameter("status", paramString);
    arrayOfPostParameter[1] = new PostParameter("source", this.source);
    arrayOfPostParameter[2] = new PostParameter("lat", paramDouble1);
    arrayOfPostParameter[3] = new PostParameter("long", paramDouble2);
    return new Status(localHttpClient.multPartURL(str, arrayOfPostParameter, paramImageItem, true));
  }

  public User verifyCredentials()
    throws WeiboException
  {
    return new User(get(getBaseURL() + "account/verify_credentials.json", true).asJSONObject());
  }

  static class Device
  {
    final String DEVICE;

    public Device(String paramString)
    {
      this.DEVICE = paramString;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.Weibo
 * JD-Core Version:    0.6.2
 */