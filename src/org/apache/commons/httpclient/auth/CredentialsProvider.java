package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.Credentials;

public abstract interface CredentialsProvider
{
  public static final String PROVIDER = "http.authentication.credential-provider";

  public abstract Credentials getCredentials(AuthScheme paramAuthScheme, String paramString, int paramInt, boolean paramBoolean)
    throws CredentialsNotAvailableException;
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.auth.CredentialsProvider
 * JD-Core Version:    0.6.2
 */