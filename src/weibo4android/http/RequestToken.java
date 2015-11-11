package weibo4android.http;

import weibo4android.WeiboException;

public class RequestToken extends OAuthToken
{
  private static final long serialVersionUID = -8214365845469757952L;
  private HttpClient httpClient;

  RequestToken(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }

  RequestToken(Response paramResponse, HttpClient paramHttpClient)
    throws WeiboException
  {
    super(paramResponse);
    this.httpClient = paramHttpClient;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    RequestToken localRequestToken;
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass()))
        return false;
      if (!super.equals(paramObject))
        return false;
      localRequestToken = (RequestToken)paramObject;
      if (this.httpClient == null)
        break;
    }
    while (this.httpClient.equals(localRequestToken.httpClient));
    while (true)
    {
      return false;
      if (localRequestToken.httpClient == null)
        break;
    }
  }

  public AccessToken getAccessToken(String paramString)
    throws WeiboException
  {
    return this.httpClient.getOAuthAccessToken(this, paramString);
  }

  public String getAuthenticationURL()
  {
    return this.httpClient.getAuthenticationRL() + "?oauth_token=" + getToken();
  }

  public String getAuthorizationURL()
  {
    return this.httpClient.getAuthorizationURL() + "?oauth_token=" + getToken();
  }

  public HttpClient getHttpClient()
  {
    return this.httpClient;
  }

  public int hashCode()
  {
    int i = 31 * super.hashCode();
    if (this.httpClient != null);
    for (int j = this.httpClient.hashCode(); ; j = 0)
      return j + i;
  }

  public void setHttpClient(HttpClient paramHttpClient)
  {
    this.httpClient = paramHttpClient;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.http.RequestToken
 * JD-Core Version:    0.6.2
 */