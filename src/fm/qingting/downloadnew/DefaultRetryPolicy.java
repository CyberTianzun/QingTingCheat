package fm.qingting.downloadnew;

class DefaultRetryPolicy
  implements RetryPolicy
{
  public static final float DEFAULT_BACKOFF_MULT = 1.0F;
  public static final int DEFAULT_MAX_RETRIES = 2;
  public static final int DEFAULT_TIMEOUT_MS = 2500;
  private final float mBackoffMultiplier = 1.0F;
  private int mCurrentRetryCount = 0;
  private int mCurrentTimeoutMs = 2500;
  private final int mMaxNumRetries = 2;
  private int mUrlIndex = 0;
  private String[] mUrls;

  public DefaultRetryPolicy(String[] paramArrayOfString)
  {
    if (paramArrayOfString != null)
    {
      this.mUrls = paramArrayOfString;
      return;
    }
    this.mUrls = new String[0];
  }

  public int getCurrentRetryCount()
  {
    return this.mCurrentRetryCount;
  }

  public int getCurrentTimeout()
  {
    return this.mCurrentTimeoutMs;
  }

  public String getCurrentUrl()
  {
    if ((this.mUrls == null) || (this.mUrls.length == 0) || (this.mUrlIndex >= this.mUrls.length))
      return "";
    return this.mUrls[this.mUrlIndex];
  }

  public void retryTimeout(DownloadError paramDownloadError)
    throws DownloadError
  {
    this.mCurrentRetryCount = (1 + this.mCurrentRetryCount);
    this.mCurrentTimeoutMs = ((int)(this.mCurrentTimeoutMs + this.mCurrentTimeoutMs * this.mBackoffMultiplier));
    if (this.mCurrentRetryCount > this.mMaxNumRetries)
      throw paramDownloadError;
  }

  public void retryUrl(DownloadError paramDownloadError)
    throws DownloadError
  {
    this.mUrlIndex = (1 + this.mUrlIndex);
    this.mCurrentRetryCount = 0;
    this.mCurrentTimeoutMs = 2500;
    if ((this.mUrls == null) || (this.mUrlIndex >= this.mUrls.length))
      throw paramDownloadError;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.downloadnew.DefaultRetryPolicy
 * JD-Core Version:    0.6.2
 */