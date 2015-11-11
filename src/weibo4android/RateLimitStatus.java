package weibo4android;

import java.util.Date;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import weibo4android.http.Response;
import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;

public class RateLimitStatus extends WeiboResponse
{
  private static final long serialVersionUID = 933996804168952707L;
  private int hourlyLimit;
  private int remainingHits;
  private Date resetTime;
  private int resetTimeInSeconds;

  RateLimitStatus(Response paramResponse)
    throws WeiboException
  {
    super(paramResponse);
    Element localElement = paramResponse.asDocument().getDocumentElement();
    this.remainingHits = getChildInt("remaining-hits", localElement);
    this.hourlyLimit = getChildInt("hourly-limit", localElement);
    this.resetTimeInSeconds = getChildInt("reset-time-in-seconds", localElement);
    this.resetTime = getChildDate("reset-time", localElement, "EEE MMM d HH:mm:ss z yyyy");
  }

  RateLimitStatus(Response paramResponse, Weibo paramWeibo)
    throws WeiboException
  {
    super(paramResponse);
    JSONObject localJSONObject = paramResponse.asJSONObject();
    try
    {
      this.remainingHits = localJSONObject.getInt("remaining_hits");
      this.hourlyLimit = localJSONObject.getInt("hourly_limit");
      this.resetTimeInSeconds = localJSONObject.getInt("reset_time_in_seconds");
      this.resetTime = parseDate(localJSONObject.getString("reset_time"), "EEE MMM dd HH:mm:ss z yyyy");
      return;
    }
    catch (JSONException localJSONException)
    {
      throw new WeiboException(localJSONException.getMessage() + ":" + localJSONObject.toString(), localJSONException);
    }
  }

  public Date getDateTime()
  {
    return this.resetTime;
  }

  public int getHourlyLimit()
  {
    return this.hourlyLimit;
  }

  public int getRemainingHits()
  {
    return this.remainingHits;
  }

  public Date getResetTime()
  {
    return this.resetTime;
  }

  public int getResetTimeInSeconds()
  {
    return this.resetTimeInSeconds;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("RateLimitStatus{remainingHits:");
    localStringBuilder.append(this.remainingHits);
    localStringBuilder.append(";hourlyLimit:");
    localStringBuilder.append(this.hourlyLimit);
    localStringBuilder.append(";resetTimeInSeconds:");
    localStringBuilder.append(this.resetTimeInSeconds);
    localStringBuilder.append(";resetTime:");
    localStringBuilder.append(this.resetTime);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.RateLimitStatus
 * JD-Core Version:    0.6.2
 */