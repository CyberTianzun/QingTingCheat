package weibo4android.http;

import java.io.Serializable;
import javax.crypto.spec.SecretKeySpec;
import weibo4android.WeiboException;

abstract class OAuthToken
  implements Serializable
{
  private static final long serialVersionUID = 2385887178385032767L;
  String[] responseStr = null;
  private transient SecretKeySpec secretKeySpec;
  private String token;
  private String tokenSecret;

  OAuthToken(String paramString)
  {
    this.responseStr = paramString.split("&");
    this.tokenSecret = getParameter("oauth_token_secret");
    this.token = getParameter("oauth_token");
  }

  public OAuthToken(String paramString1, String paramString2)
  {
    this.token = paramString1;
    this.tokenSecret = paramString2;
  }

  OAuthToken(Response paramResponse)
    throws WeiboException
  {
    this(paramResponse.asString());
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    OAuthToken localOAuthToken;
    do
    {
      return true;
      if (!(paramObject instanceof OAuthToken))
        return false;
      localOAuthToken = (OAuthToken)paramObject;
      if (this.secretKeySpec != null)
      {
        if (this.secretKeySpec.equals(localOAuthToken.secretKeySpec));
      }
      else
        while (localOAuthToken.secretKeySpec != null)
          return false;
      if (!this.token.equals(localOAuthToken.token))
        return false;
    }
    while (this.tokenSecret.equals(localOAuthToken.tokenSecret));
    return false;
  }

  public String getParameter(String paramString)
  {
    String[] arrayOfString = this.responseStr;
    int i = arrayOfString.length;
    for (int j = 0; ; j++)
    {
      String str1 = null;
      if (j < i)
      {
        String str2 = arrayOfString[j];
        if (str2.startsWith(paramString + '='))
          str1 = str2.split("=")[1].trim();
      }
      else
      {
        return str1;
      }
    }
  }

  SecretKeySpec getSecretKeySpec()
  {
    return this.secretKeySpec;
  }

  public String getToken()
  {
    return this.token;
  }

  public String getTokenSecret()
  {
    return this.tokenSecret;
  }

  public int hashCode()
  {
    int i = 31 * (31 * this.token.hashCode() + this.tokenSecret.hashCode());
    if (this.secretKeySpec != null);
    for (int j = this.secretKeySpec.hashCode(); ; j = 0)
      return j + i;
  }

  void setSecretKeySpec(SecretKeySpec paramSecretKeySpec)
  {
    this.secretKeySpec = paramSecretKeySpec;
  }

  public String toString()
  {
    return "OAuthToken{token='" + this.token + '\'' + ", tokenSecret='" + this.tokenSecret + '\'' + ", secretKeySpec=" + this.secretKeySpec + '}';
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.http.OAuthToken
 * JD-Core Version:    0.6.2
 */