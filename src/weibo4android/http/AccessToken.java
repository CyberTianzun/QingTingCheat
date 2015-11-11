package weibo4android.http;

import weibo4android.WeiboException;

public class AccessToken extends OAuthToken
{
  private static final long serialVersionUID = -8344528374458826291L;
  private String screenName;
  private long userId;

  AccessToken(String paramString)
  {
    super(paramString);
    this.screenName = getParameter("screen_name");
    String str = getParameter("user_id");
    if (str != null)
      this.userId = Long.parseLong(str);
  }

  public AccessToken(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }

  AccessToken(Response paramResponse)
    throws WeiboException
  {
    this(paramResponse.asString());
  }

  public String getScreenName()
  {
    return this.screenName;
  }

  public long getUserId()
  {
    return this.userId;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.http.AccessToken
 * JD-Core Version:    0.6.2
 */