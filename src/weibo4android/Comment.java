package weibo4android;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import weibo4android.http.Response;
import weibo4android.org.json.JSONArray;
import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;

public class Comment extends WeiboResponse
  implements Serializable
{
  private static final long serialVersionUID = 1608000492860584608L;
  private Date createdAt;
  private long id;
  private String inReplyToScreenName;
  private long inReplyToStatusId;
  private long inReplyToUserId;
  private boolean isFavorited;
  private boolean isTruncated;
  private double latitude = -1.0D;
  private double longitude = -1.0D;
  private String mid;
  private Comment replycomment = null;
  private String source;
  private Status status = null;
  private String text;
  private User user = null;

  public Comment()
  {
  }

  public Comment(String paramString)
    throws WeiboException, JSONException
  {
    JSONObject localJSONObject = new JSONObject(paramString);
    this.id = localJSONObject.getLong("id");
    this.text = localJSONObject.getString("text");
    this.source = localJSONObject.getString("source");
    this.createdAt = parseDate(localJSONObject.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");
    this.status = new Status(localJSONObject.getJSONObject("status"));
    this.user = new User(localJSONObject.getJSONObject("user"));
    this.mid = localJSONObject.getString("mid");
  }

  Comment(Response paramResponse)
    throws WeiboException
  {
    super(paramResponse);
    JSONObject localJSONObject = paramResponse.asJSONObject();
    try
    {
      this.id = localJSONObject.getLong("id");
      this.text = localJSONObject.getString("text");
      this.source = localJSONObject.getString("source");
      this.mid = localJSONObject.getString("mid");
      this.createdAt = parseDate(localJSONObject.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");
      if (!localJSONObject.isNull("user"))
        this.user = new User(localJSONObject.getJSONObject("user"));
      if (!localJSONObject.isNull("status"))
        this.status = new Status(localJSONObject.getJSONObject("status"));
      if (!localJSONObject.isNull("reply_comment"))
        this.replycomment = new Comment(localJSONObject.getJSONObject("reply_comment"));
      return;
    }
    catch (JSONException localJSONException)
    {
      throw new WeiboException(localJSONException.getMessage() + ":" + localJSONObject.toString(), localJSONException);
    }
  }

  Comment(Response paramResponse, Element paramElement, Weibo paramWeibo)
    throws WeiboException
  {
    super(paramResponse);
    init(paramResponse, paramElement, paramWeibo);
  }

  Comment(Response paramResponse, Weibo paramWeibo)
    throws WeiboException
  {
    super(paramResponse);
    init(paramResponse, paramResponse.asDocument().getDocumentElement(), paramWeibo);
  }

  public Comment(JSONObject paramJSONObject)
    throws WeiboException, JSONException
  {
    this.id = paramJSONObject.getLong("id");
    this.text = paramJSONObject.getString("text");
    this.source = paramJSONObject.getString("source");
    this.mid = paramJSONObject.getString("mid");
    this.createdAt = parseDate(paramJSONObject.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");
    if (!paramJSONObject.isNull("user"))
      this.user = new User(paramJSONObject.getJSONObject("user"));
    if (!paramJSONObject.isNull("status"))
      this.status = new Status(paramJSONObject.getJSONObject("status"));
  }

  static List<Comment> constructComments(Response paramResponse)
    throws WeiboException
  {
    ArrayList localArrayList;
    try
    {
      JSONArray localJSONArray = paramResponse.asJSONArray();
      int i = localJSONArray.length();
      localArrayList = new ArrayList(i);
      for (int j = 0; j < i; j++)
        localArrayList.add(new Comment(localJSONArray.getJSONObject(j)));
    }
    catch (JSONException localJSONException)
    {
      throw new WeiboException(localJSONException);
    }
    catch (WeiboException localWeiboException)
    {
      throw localWeiboException;
    }
    return localArrayList;
  }

  static List<Comment> constructComments(Response paramResponse, Weibo paramWeibo)
    throws WeiboException
  {
    Document localDocument = paramResponse.asDocument();
    if (isRootNodeNilClasses(localDocument))
      return new ArrayList(0);
    try
    {
      ensureRootNodeNameIs("comments", localDocument);
      NodeList localNodeList = localDocument.getDocumentElement().getElementsByTagName("comment");
      int i = localNodeList.getLength();
      ArrayList localArrayList = new ArrayList(i);
      for (int j = 0; j < i; j++)
        localArrayList.add(new Comment(paramResponse, (Element)localNodeList.item(j), paramWeibo));
      return localArrayList;
    }
    catch (WeiboException localWeiboException)
    {
      ensureRootNodeNameIs("nil-classes", localDocument);
    }
    return new ArrayList(0);
  }

  private void init(Response paramResponse, Element paramElement, Weibo paramWeibo)
    throws WeiboException
  {
    ensureRootNodeNameIs("comment", paramElement);
    this.user = new User(paramResponse, (Element)paramElement.getElementsByTagName("user").item(0), paramWeibo);
    this.status = new Status(paramResponse, (Element)paramElement.getElementsByTagName("status").item(0), paramWeibo);
    this.id = getChildLong("id", paramElement);
    this.text = getChildText("text", paramElement);
    this.source = getChildText("source", paramElement);
    this.createdAt = getChildDate("created_at", paramElement);
    this.mid = getChildText("mid", paramElement);
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    Comment localComment;
    do
    {
      return true;
      if (paramObject == null)
        return false;
      if (getClass() != paramObject.getClass())
        return false;
      localComment = (Comment)paramObject;
    }
    while (this.id == localComment.id);
    return false;
  }

  public Date getCreatedAt()
  {
    return this.createdAt;
  }

  public long getId()
  {
    return this.id;
  }

  public String getInReplyToScreenName()
  {
    return this.inReplyToScreenName;
  }

  public long getInReplyToStatusId()
  {
    return this.inReplyToStatusId;
  }

  public long getInReplyToUserId()
  {
    return this.inReplyToUserId;
  }

  public double getLatitude()
  {
    return this.latitude;
  }

  public double getLongitude()
  {
    return this.longitude;
  }

  public String getMid()
  {
    return this.mid;
  }

  public Comment getReplyComment()
  {
    return this.replycomment;
  }

  public String getSource()
  {
    return this.source;
  }

  public Status getStatus()
  {
    return this.status;
  }

  public String getText()
  {
    return this.text;
  }

  public User getUser()
  {
    return this.user;
  }

  public int hashCode()
  {
    return 31 + (int)(this.id ^ this.id >>> 32);
  }

  public boolean isFavorited()
  {
    return this.isFavorited;
  }

  public boolean isTruncated()
  {
    return this.isTruncated;
  }

  public void setMid(String paramString)
  {
    this.mid = paramString;
  }

  public String toString()
  {
    return "Comment{createdAt=" + this.createdAt + ", id=" + this.id + ", text='" + this.text + '\'' + ", source='" + this.source + '\'' + ", isTruncated=" + this.isTruncated + ", inReplyToStatusId=" + this.inReplyToStatusId + ", inReplyToUserId=" + this.inReplyToUserId + ", isFavorited=" + this.isFavorited + ", inReplyToScreenName='" + this.inReplyToScreenName + '\'' + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", user=" + this.user + ", status=" + this.status + '}';
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.Comment
 * JD-Core Version:    0.6.2
 */