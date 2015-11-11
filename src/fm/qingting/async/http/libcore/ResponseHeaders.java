package fm.qingting.async.http.libcore;

import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public final class ResponseHeaders
{
  private static final String RECEIVED_MILLIS = "X-Android-Received-Millis";
  private static final String SENT_MILLIS = "X-Android-Sent-Millis";
  private int ageSeconds = -1;
  private String connection;
  private String contentEncoding;
  private int contentLength = -1;
  private String etag;
  private Date expires;
  private final RawHeaders headers;
  private boolean isPublic;
  private Date lastModified;
  private int maxAgeSeconds = -1;
  private boolean mustRevalidate;
  private boolean noCache;
  private boolean noStore;
  private String proxyAuthenticate;
  private long receivedResponseMillis;
  private int sMaxAgeSeconds = -1;
  private long sentRequestMillis;
  private Date servedDate;
  private String transferEncoding;
  private final URI uri;
  private Set<String> varyFields = Collections.emptySet();
  private String wwwAuthenticate;

  public ResponseHeaders(URI paramURI, RawHeaders paramRawHeaders)
  {
    this.uri = paramURI;
    this.headers = paramRawHeaders;
    HeaderParser.CacheControlHandler local1 = new HeaderParser.CacheControlHandler()
    {
      public void handle(String paramAnonymousString1, String paramAnonymousString2)
      {
        if (paramAnonymousString1.equalsIgnoreCase("no-cache"))
          ResponseHeaders.access$002(ResponseHeaders.this, true);
        do
        {
          return;
          if (paramAnonymousString1.equalsIgnoreCase("no-store"))
          {
            ResponseHeaders.access$102(ResponseHeaders.this, true);
            return;
          }
          if (paramAnonymousString1.equalsIgnoreCase("max-age"))
          {
            ResponseHeaders.access$202(ResponseHeaders.this, HeaderParser.parseSeconds(paramAnonymousString2));
            return;
          }
          if (paramAnonymousString1.equalsIgnoreCase("s-maxage"))
          {
            ResponseHeaders.access$302(ResponseHeaders.this, HeaderParser.parseSeconds(paramAnonymousString2));
            return;
          }
          if (paramAnonymousString1.equalsIgnoreCase("public"))
          {
            ResponseHeaders.access$402(ResponseHeaders.this, true);
            return;
          }
        }
        while (!paramAnonymousString1.equalsIgnoreCase("must-revalidate"));
        ResponseHeaders.access$502(ResponseHeaders.this, true);
      }
    };
    int i = 0;
    if (i < paramRawHeaders.length())
    {
      String str1 = paramRawHeaders.getFieldName(i);
      String str2 = paramRawHeaders.getValue(i);
      if ("Cache-Control".equalsIgnoreCase(str1))
        HeaderParser.parseCacheControl(str2, local1);
      while (true)
      {
        i++;
        break;
        if ("Date".equalsIgnoreCase(str1))
        {
          this.servedDate = HttpDate.parse(str2);
        }
        else if ("Expires".equalsIgnoreCase(str1))
        {
          this.expires = HttpDate.parse(str2);
        }
        else if ("Last-Modified".equalsIgnoreCase(str1))
        {
          this.lastModified = HttpDate.parse(str2);
        }
        else if ("ETag".equalsIgnoreCase(str1))
        {
          this.etag = str2;
        }
        else if ("Pragma".equalsIgnoreCase(str1))
        {
          if (str2.equalsIgnoreCase("no-cache"))
            this.noCache = true;
        }
        else if ("Age".equalsIgnoreCase(str1))
        {
          this.ageSeconds = HeaderParser.parseSeconds(str2);
        }
        else if ("Vary".equalsIgnoreCase(str1))
        {
          if (this.varyFields.isEmpty())
            this.varyFields = new TreeSet(String.CASE_INSENSITIVE_ORDER);
          for (String str3 : str2.split(","))
            this.varyFields.add(str3.trim());
        }
        else if ("Content-Encoding".equalsIgnoreCase(str1))
        {
          this.contentEncoding = str2;
        }
        else if ("Transfer-Encoding".equalsIgnoreCase(str1))
        {
          this.transferEncoding = str2;
        }
        else if ("Content-Length".equalsIgnoreCase(str1))
        {
          try
          {
            this.contentLength = Integer.parseInt(str2);
          }
          catch (NumberFormatException localNumberFormatException)
          {
          }
        }
        else if ("Connection".equalsIgnoreCase(str1))
        {
          this.connection = str2;
        }
        else if ("Proxy-Authenticate".equalsIgnoreCase(str1))
        {
          this.proxyAuthenticate = str2;
        }
        else if ("WWW-Authenticate".equalsIgnoreCase(str1))
        {
          this.wwwAuthenticate = str2;
        }
        else if ("X-Android-Sent-Millis".equalsIgnoreCase(str1))
        {
          this.sentRequestMillis = Long.parseLong(str2);
        }
        else if ("X-Android-Received-Millis".equalsIgnoreCase(str1))
        {
          this.receivedResponseMillis = Long.parseLong(str2);
        }
      }
    }
  }

  private long computeAge(long paramLong)
  {
    long l1 = 0L;
    if (this.servedDate != null)
      l1 = Math.max(l1, this.receivedResponseMillis - this.servedDate.getTime());
    if (this.ageSeconds != -1)
      l1 = Math.max(l1, TimeUnit.SECONDS.toMillis(this.ageSeconds));
    long l2 = this.receivedResponseMillis - this.sentRequestMillis;
    return paramLong - this.receivedResponseMillis + (l1 + l2);
  }

  private long computeFreshnessLifetime()
  {
    long l1 = 0L;
    if (this.maxAgeSeconds != -1)
      l1 = TimeUnit.SECONDS.toMillis(this.maxAgeSeconds);
    label78: 
    do
    {
      return l1;
      if (this.expires != null)
      {
        long l4;
        long l5;
        if (this.servedDate != null)
        {
          l4 = this.servedDate.getTime();
          l5 = this.expires.getTime() - l4;
          if (l5 <= l1)
            break label78;
        }
        while (true)
        {
          return l5;
          l4 = this.receivedResponseMillis;
          break;
          l5 = l1;
        }
      }
    }
    while ((this.lastModified == null) || (this.uri.getRawQuery() != null));
    if (this.servedDate != null);
    for (long l2 = this.servedDate.getTime(); ; l2 = this.sentRequestMillis)
    {
      long l3 = l2 - this.lastModified.getTime();
      if (l3 <= l1)
        break;
      return l3 / 10L;
    }
  }

  private static boolean isEndToEnd(String paramString)
  {
    return (!paramString.equalsIgnoreCase("Connection")) && (!paramString.equalsIgnoreCase("Keep-Alive")) && (!paramString.equalsIgnoreCase("Proxy-Authenticate")) && (!paramString.equalsIgnoreCase("Proxy-Authorization")) && (!paramString.equalsIgnoreCase("TE")) && (!paramString.equalsIgnoreCase("Trailers")) && (!paramString.equalsIgnoreCase("Transfer-Encoding")) && (!paramString.equalsIgnoreCase("Upgrade"));
  }

  private boolean isFreshnessLifetimeHeuristic()
  {
    return (this.maxAgeSeconds == -1) && (this.expires == null);
  }

  public ResponseSource chooseResponseSource(long paramLong, RequestHeaders paramRequestHeaders)
  {
    long l1 = 0L;
    if (!isCacheable(paramRequestHeaders))
      return ResponseSource.NETWORK;
    if ((paramRequestHeaders.isNoCache()) || (paramRequestHeaders.hasConditions()))
      return ResponseSource.NETWORK;
    long l2 = computeAge(paramLong);
    long l3 = computeFreshnessLifetime();
    if (paramRequestHeaders.getMaxAgeSeconds() != -1)
      l3 = Math.min(l3, TimeUnit.SECONDS.toMillis(paramRequestHeaders.getMaxAgeSeconds()));
    if (paramRequestHeaders.getMinFreshSeconds() != -1);
    for (long l4 = TimeUnit.SECONDS.toMillis(paramRequestHeaders.getMinFreshSeconds()); ; l4 = l1)
    {
      if ((!this.mustRevalidate) && (paramRequestHeaders.getMaxStaleSeconds() != -1))
        l1 = TimeUnit.SECONDS.toMillis(paramRequestHeaders.getMaxStaleSeconds());
      if ((!this.noCache) && (l2 + l4 < l1 + l3))
      {
        if (l4 + l2 >= l3)
          this.headers.add("Warning", "110 HttpURLConnection \"Response is stale\"");
        if ((l2 > 86400000L) && (isFreshnessLifetimeHeuristic()))
          this.headers.add("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
        return ResponseSource.CACHE;
      }
      if (this.etag != null)
        paramRequestHeaders.setIfNoneMatch(this.etag);
      while (paramRequestHeaders.hasConditions())
      {
        return ResponseSource.CONDITIONAL_CACHE;
        if (this.lastModified != null)
          paramRequestHeaders.setIfModifiedSince(this.lastModified);
        else if (this.servedDate != null)
          paramRequestHeaders.setIfModifiedSince(this.servedDate);
      }
      return ResponseSource.NETWORK;
    }
  }

  public ResponseHeaders combine(ResponseHeaders paramResponseHeaders)
  {
    RawHeaders localRawHeaders = new RawHeaders();
    int i = 0;
    int j = this.headers.length();
    int k = 0;
    if (i < j)
    {
      String str2 = this.headers.getFieldName(i);
      String str3 = this.headers.getValue(i);
      if ((str2.equals("Warning")) && (str3.startsWith("1")));
      while (true)
      {
        i++;
        break;
        if ((!isEndToEnd(str2)) || (paramResponseHeaders.headers.get(str2) == null))
          localRawHeaders.add(str2, str3);
      }
    }
    while (k < paramResponseHeaders.headers.length())
    {
      String str1 = paramResponseHeaders.headers.getFieldName(k);
      if (isEndToEnd(str1))
        localRawHeaders.add(str1, paramResponseHeaders.headers.getValue(k));
      k++;
    }
    return new ResponseHeaders(this.uri, localRawHeaders);
  }

  public String getConnection()
  {
    return this.connection;
  }

  public String getContentEncoding()
  {
    return this.contentEncoding;
  }

  public int getContentLength()
  {
    return this.contentLength;
  }

  public String getEtag()
  {
    return this.etag;
  }

  public Date getExpires()
  {
    return this.expires;
  }

  public RawHeaders getHeaders()
  {
    return this.headers;
  }

  public Date getLastModified()
  {
    return this.lastModified;
  }

  public int getMaxAgeSeconds()
  {
    return this.maxAgeSeconds;
  }

  public String getProxyAuthenticate()
  {
    return this.proxyAuthenticate;
  }

  public int getSMaxAgeSeconds()
  {
    return this.sMaxAgeSeconds;
  }

  public Date getServedDate()
  {
    return this.servedDate;
  }

  public URI getUri()
  {
    return this.uri;
  }

  public Set<String> getVaryFields()
  {
    return this.varyFields;
  }

  public String getWwwAuthenticate()
  {
    return this.wwwAuthenticate;
  }

  public boolean hasConnectionClose()
  {
    return "close".equalsIgnoreCase(this.connection);
  }

  public boolean hasVaryAll()
  {
    return this.varyFields.contains("*");
  }

  public boolean isCacheable(RequestHeaders paramRequestHeaders)
  {
    int i = this.headers.getResponseCode();
    if ((i != 200) && (i != 203) && (i != 300) && (i != 301) && (i != 410));
    while (((paramRequestHeaders.hasAuthorization()) && (!this.isPublic) && (!this.mustRevalidate) && (this.sMaxAgeSeconds == -1)) || (this.noStore))
      return false;
    return true;
  }

  public boolean isChunked()
  {
    return "chunked".equalsIgnoreCase(this.transferEncoding);
  }

  public boolean isContentEncodingGzip()
  {
    return "gzip".equalsIgnoreCase(this.contentEncoding);
  }

  public boolean isMustRevalidate()
  {
    return this.mustRevalidate;
  }

  public boolean isNoCache()
  {
    return this.noCache;
  }

  public boolean isNoStore()
  {
    return this.noStore;
  }

  public boolean isPublic()
  {
    return this.isPublic;
  }

  public void setLocalTimestamps(long paramLong1, long paramLong2)
  {
    this.sentRequestMillis = paramLong1;
    this.headers.add("X-Android-Sent-Millis", Long.toString(paramLong1));
    this.receivedResponseMillis = paramLong2;
    this.headers.add("X-Android-Received-Millis", Long.toString(paramLong2));
  }

  public void stripContentEncoding()
  {
    this.contentEncoding = null;
    this.headers.removeAll("Content-Encoding");
  }

  public boolean validate(ResponseHeaders paramResponseHeaders)
  {
    if (paramResponseHeaders.headers.getResponseCode() == 304);
    while ((this.lastModified != null) && (paramResponseHeaders.lastModified != null) && (paramResponseHeaders.lastModified.getTime() < this.lastModified.getTime()))
      return true;
    return false;
  }

  public boolean varyMatches(Map<String, List<String>> paramMap1, Map<String, List<String>> paramMap2)
  {
    Iterator localIterator = this.varyFields.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (!Objects.equal(paramMap1.get(str), paramMap2.get(str)))
        return false;
    }
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.libcore.ResponseHeaders
 * JD-Core Version:    0.6.2
 */