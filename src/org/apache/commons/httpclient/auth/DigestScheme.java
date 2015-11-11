package org.apache.commons.httpclient.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.httpclient.util.ParameterFormatter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DigestScheme extends RFC2617Scheme
{
  private static final char[] HEXADECIMAL;
  private static final Log LOG;
  private static final String NC = "00000001";
  private static final int QOP_AUTH = 2;
  private static final int QOP_AUTH_INT = 1;
  private static final int QOP_MISSING;
  static Class class$org$apache$commons$httpclient$auth$DigestScheme;
  private String cnonce;
  private boolean complete = false;
  private final ParameterFormatter formatter = new ParameterFormatter();
  private int qopVariant = 0;

  static
  {
    Class localClass;
    if (class$org$apache$commons$httpclient$auth$DigestScheme == null)
    {
      localClass = class$("org.apache.commons.httpclient.auth.DigestScheme");
      class$org$apache$commons$httpclient$auth$DigestScheme = localClass;
    }
    while (true)
    {
      LOG = LogFactory.getLog(localClass);
      HEXADECIMAL = new char[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
      return;
      localClass = class$org$apache$commons$httpclient$auth$DigestScheme;
    }
  }

  public DigestScheme()
  {
  }

  public DigestScheme(String paramString)
    throws MalformedChallengeException
  {
    this();
    processChallenge(paramString);
  }

  static Class class$(String paramString)
  {
    try
    {
      Class localClass = Class.forName(paramString);
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
  }

  public static String createCnonce()
  {
    LOG.trace("enter DigestScheme.createCnonce()");
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      return encode(localMessageDigest.digest(EncodingUtil.getAsciiBytes(Long.toString(System.currentTimeMillis()))));
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
    }
    throw new HttpClientError("Unsupported algorithm in HTTP Digest authentication: MD5");
  }

  private String createDigest(String paramString1, String paramString2)
    throws AuthenticationException
  {
    LOG.trace("enter DigestScheme.createDigest(String, String, Map)");
    String str1 = getParameter("uri");
    String str2 = getParameter("realm");
    String str3 = getParameter("nonce");
    String str4 = getParameter("qop");
    String str5 = getParameter("methodname");
    String str6 = getParameter("algorithm");
    if (str6 == null)
      str6 = "MD5";
    String str7 = getParameter("charset");
    if (str7 == null)
      str7 = "ISO-8859-1";
    if (this.qopVariant == 1)
    {
      LOG.warn("qop=auth-int is not supported");
      throw new AuthenticationException("Unsupported qop in HTTP Digest authentication");
    }
    while (true)
    {
      String str9;
      String str10;
      String str11;
      String str13;
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        StringBuffer localStringBuffer1 = new StringBuffer(2 + (paramString1.length() + str2.length() + paramString2.length()));
        localStringBuffer1.append(paramString1);
        localStringBuffer1.append(':');
        localStringBuffer1.append(str2);
        localStringBuffer1.append(':');
        localStringBuffer1.append(paramString2);
        String str8 = localStringBuffer1.toString();
        if (str6.equals("MD5-sess"))
        {
          String str14 = encode(localMessageDigest.digest(EncodingUtil.getBytes(str8, str7)));
          StringBuffer localStringBuffer4 = new StringBuffer(2 + (str14.length() + str3.length() + this.cnonce.length()));
          localStringBuffer4.append(str14);
          localStringBuffer4.append(':');
          localStringBuffer4.append(str3);
          localStringBuffer4.append(':');
          localStringBuffer4.append(this.cnonce);
          str8 = localStringBuffer4.toString();
          str9 = encode(localMessageDigest.digest(EncodingUtil.getBytes(str8, str7)));
          str10 = null;
          if (this.qopVariant != 1)
            break label510;
          LOG.error("Unhandled qop auth-int");
          str11 = encode(localMessageDigest.digest(EncodingUtil.getAsciiBytes(str10)));
          if (this.qopVariant != 0)
            break label539;
          LOG.debug("Using null qop method");
          StringBuffer localStringBuffer3 = new StringBuffer(str9.length() + str3.length() + str11.length());
          localStringBuffer3.append(str9);
          localStringBuffer3.append(':');
          localStringBuffer3.append(str3);
          localStringBuffer3.append(':');
          localStringBuffer3.append(str11);
          str13 = localStringBuffer3.toString();
          return encode(localMessageDigest.digest(EncodingUtil.getAsciiBytes(str13)));
        }
      }
      catch (Exception localException)
      {
        throw new AuthenticationException("Unsupported algorithm in HTTP Digest authentication: MD5");
      }
      if (!str6.equals("MD5"))
      {
        LOG.warn("Unhandled algorithm " + str6 + " requested");
        continue;
        label510: str10 = str5 + ":" + str1;
        continue;
        label539: if (LOG.isDebugEnabled())
          LOG.debug("Using qop method " + str4);
        String str12 = getQopVariantString();
        StringBuffer localStringBuffer2 = new StringBuffer(5 + (str9.length() + str3.length() + "00000001".length() + this.cnonce.length() + str12.length() + str11.length()));
        localStringBuffer2.append(str9);
        localStringBuffer2.append(':');
        localStringBuffer2.append(str3);
        localStringBuffer2.append(':');
        localStringBuffer2.append("00000001");
        localStringBuffer2.append(':');
        localStringBuffer2.append(this.cnonce);
        localStringBuffer2.append(':');
        localStringBuffer2.append(str12);
        localStringBuffer2.append(':');
        localStringBuffer2.append(str11);
        str13 = localStringBuffer2.toString();
      }
    }
  }

  private String createDigestHeader(String paramString1, String paramString2)
    throws AuthenticationException
  {
    LOG.trace("enter DigestScheme.createDigestHeader(String, Map, String)");
    String str1 = getParameter("uri");
    String str2 = getParameter("realm");
    String str3 = getParameter("nonce");
    String str4 = getParameter("opaque");
    String str5 = getParameter("algorithm");
    ArrayList localArrayList = new ArrayList(20);
    localArrayList.add(new NameValuePair("username", paramString1));
    localArrayList.add(new NameValuePair("realm", str2));
    localArrayList.add(new NameValuePair("nonce", str3));
    localArrayList.add(new NameValuePair("uri", str1));
    localArrayList.add(new NameValuePair("response", paramString2));
    if (this.qopVariant != 0)
    {
      localArrayList.add(new NameValuePair("qop", getQopVariantString()));
      localArrayList.add(new NameValuePair("nc", "00000001"));
      localArrayList.add(new NameValuePair("cnonce", this.cnonce));
    }
    if (str5 != null)
      localArrayList.add(new NameValuePair("algorithm", str5));
    if (str4 != null)
      localArrayList.add(new NameValuePair("opaque", str4));
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (i >= localArrayList.size())
      return localStringBuffer.toString();
    NameValuePair localNameValuePair = (NameValuePair)localArrayList.get(i);
    if (i > 0)
      localStringBuffer.append(", ");
    int j;
    label359: ParameterFormatter localParameterFormatter;
    if (("nc".equals(localNameValuePair.getName())) || ("qop".equals(localNameValuePair.getName())))
    {
      j = 1;
      localParameterFormatter = this.formatter;
      if (j != 0)
        break label403;
    }
    label403: for (boolean bool = true; ; bool = false)
    {
      localParameterFormatter.setAlwaysUseQuotes(bool);
      this.formatter.format(localStringBuffer, localNameValuePair);
      i++;
      break;
      j = 0;
      break label359;
    }
  }

  private static String encode(byte[] paramArrayOfByte)
  {
    LOG.trace("enter DigestScheme.encode(byte[])");
    if (paramArrayOfByte.length != 16)
      return null;
    char[] arrayOfChar = new char[32];
    for (int i = 0; ; i++)
    {
      if (i >= 16)
        return new String(arrayOfChar);
      int j = 0xF & paramArrayOfByte[i];
      int k = (0xF0 & paramArrayOfByte[i]) >> 4;
      arrayOfChar[(i * 2)] = HEXADECIMAL[k];
      arrayOfChar[(1 + i * 2)] = HEXADECIMAL[j];
    }
  }

  private String getQopVariantString()
  {
    if (this.qopVariant == 1)
      return "auth-int";
    return "auth";
  }

  public String authenticate(Credentials paramCredentials, String paramString1, String paramString2)
    throws AuthenticationException
  {
    LOG.trace("enter DigestScheme.authenticate(Credentials, String, String)");
    try
    {
      UsernamePasswordCredentials localUsernamePasswordCredentials = (UsernamePasswordCredentials)paramCredentials;
      getParameters().put("methodname", paramString1);
      getParameters().put("uri", paramString2);
      String str = createDigest(localUsernamePasswordCredentials.getUserName(), localUsernamePasswordCredentials.getPassword());
      return "Digest " + createDigestHeader(localUsernamePasswordCredentials.getUserName(), str);
    }
    catch (ClassCastException localClassCastException)
    {
    }
    throw new InvalidCredentialsException("Credentials cannot be used for digest authentication: " + paramCredentials.getClass().getName());
  }

  public String authenticate(Credentials paramCredentials, HttpMethod paramHttpMethod)
    throws AuthenticationException
  {
    LOG.trace("enter DigestScheme.authenticate(Credentials, HttpMethod)");
    try
    {
      UsernamePasswordCredentials localUsernamePasswordCredentials = (UsernamePasswordCredentials)paramCredentials;
      getParameters().put("methodname", paramHttpMethod.getName());
      StringBuffer localStringBuffer = new StringBuffer(paramHttpMethod.getPath());
      String str1 = paramHttpMethod.getQueryString();
      if (str1 != null)
      {
        if (str1.indexOf("?") != 0)
          localStringBuffer.append("?");
        localStringBuffer.append(paramHttpMethod.getQueryString());
      }
      getParameters().put("uri", localStringBuffer.toString());
      if (getParameter("charset") == null)
        getParameters().put("charset", paramHttpMethod.getParams().getCredentialCharset());
      String str2 = createDigest(localUsernamePasswordCredentials.getUserName(), localUsernamePasswordCredentials.getPassword());
      return "Digest " + createDigestHeader(localUsernamePasswordCredentials.getUserName(), str2);
    }
    catch (ClassCastException localClassCastException)
    {
    }
    throw new InvalidCredentialsException("Credentials cannot be used for digest authentication: " + paramCredentials.getClass().getName());
  }

  public String getID()
  {
    String str1 = getRealm();
    String str2 = getParameter("nonce");
    if (str2 != null)
      str1 = str1 + "-" + str2;
    return str1;
  }

  public String getSchemeName()
  {
    return "digest";
  }

  public boolean isComplete()
  {
    if ("true".equalsIgnoreCase(getParameter("stale")))
      return false;
    return this.complete;
  }

  public boolean isConnectionBased()
  {
    return false;
  }

  public void processChallenge(String paramString)
    throws MalformedChallengeException
  {
    super.processChallenge(paramString);
    if (getParameter("realm") == null)
      throw new MalformedChallengeException("missing realm in challange");
    if (getParameter("nonce") == null)
      throw new MalformedChallengeException("missing nonce in challange");
    String str1 = getParameter("qop");
    int i = 0;
    StringTokenizer localStringTokenizer;
    if (str1 != null)
      localStringTokenizer = new StringTokenizer(str1, ",");
    while (true)
    {
      if (!localStringTokenizer.hasMoreTokens());
      String str2;
      while (true)
      {
        if ((i == 0) || (this.qopVariant != 0))
          break label183;
        throw new MalformedChallengeException("None of the qop methods is supported");
        str2 = localStringTokenizer.nextToken().trim();
        if (!str2.equals("auth"))
          break;
        this.qopVariant = 2;
      }
      if (str2.equals("auth-int"))
      {
        this.qopVariant = 1;
      }
      else
      {
        i = 1;
        LOG.warn("Unsupported qop detected: " + str2);
      }
    }
    label183: this.cnonce = createCnonce();
    this.complete = true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.auth.DigestScheme
 * JD-Core Version:    0.6.2
 */