package weibo4android;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import weibo4android.http.Response;
import weibo4android.org.json.JSONArray;
import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;

public class Tag extends WeiboResponse
  implements Serializable
{
  private static final long serialVersionUID = 2177657076940291492L;
  private String id;
  private String value;

  public Tag(Response paramResponse, Element paramElement)
    throws WeiboException
  {
    ensureRootNodeNameIs("tag", paramElement);
    this.id = getChildText("id", paramElement);
    this.value = getChildText("value", paramElement);
  }

  public Tag(Response paramResponse, Element paramElement, Weibo paramWeibo)
    throws WeiboException
  {
    ensureRootNodeNameIs("tagid", paramElement);
    this.id = paramElement.getNodeName();
    this.value = paramElement.getNodeValue();
  }

  public Tag(Response paramResponse, Element paramElement, Weibo paramWeibo, String paramString)
    throws WeiboException
  {
    ensureRootNodeNameIs("tagid", paramElement);
    this.id = paramElement.getNodeName();
    this.value = paramElement.getNodeValue();
  }

  public Tag(JSONObject paramJSONObject)
    throws WeiboException, JSONException
  {
    if ((paramJSONObject.getString("id") != null) && (paramJSONObject.getString("id").length() != 0));
    for (this.id = paramJSONObject.getString("id"); ; this.id = paramJSONObject.getString("tagid"))
      do
      {
        if ((paramJSONObject.getString("value") != null) && (paramJSONObject.getString("value").length() != 0))
          this.value = paramJSONObject.getString("value");
        return;
      }
      while ((paramJSONObject.getString("tagid") == null) || (paramJSONObject.getString("tagid").length() == 0));
  }

  static List<Tag> constructTags(Response paramResponse)
    throws WeiboException
  {
    ArrayList localArrayList;
    try
    {
      JSONArray localJSONArray = paramResponse.asJSONArray();
      int i = localJSONArray.length();
      localArrayList = new ArrayList(i);
      for (int j = 0; j < i; j++)
        localArrayList.add(new Tag(localJSONArray.getJSONObject(j)));
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

  public static List<Tag> constructTags(Response paramResponse, Weibo paramWeibo)
    throws WeiboException
  {
    Document localDocument = paramResponse.asDocument();
    if (isRootNodeNilClasses(localDocument))
      return new ArrayList(0);
    try
    {
      ensureRootNodeNameIs("tags", localDocument);
      NodeList localNodeList = localDocument.getDocumentElement().getElementsByTagName("tag");
      int i = localNodeList.getLength();
      ArrayList localArrayList = new ArrayList(i);
      for (int j = 0; j < i; j++)
        localArrayList.add(new Tag(paramResponse, (Element)localNodeList.item(j)));
      return localArrayList;
    }
    catch (WeiboException localWeiboException)
    {
      ensureRootNodeNameIs("nil-classes", localDocument);
    }
    return new ArrayList(0);
  }

  public static List<Tag> createTags(Response paramResponse, Weibo paramWeibo)
    throws WeiboException
  {
    Document localDocument = paramResponse.asDocument();
    if (isRootNodeNilClasses(localDocument))
      return new ArrayList(0);
    try
    {
      ensureRootNodeNameIs("tagids", localDocument);
      NodeList localNodeList = localDocument.getDocumentElement().getElementsByTagName("tagid");
      int i = localNodeList.getLength();
      ArrayList localArrayList = new ArrayList(i);
      for (int j = 0; j < i; j++)
        localArrayList.add(new Tag(paramResponse, (Element)localNodeList.item(j), null));
      return localArrayList;
    }
    catch (WeiboException localWeiboException)
    {
      ensureRootNodeNameIs("nil-classes", localDocument);
    }
    return new ArrayList(0);
  }

  public static List<Tag> destroyTags(Response paramResponse, Weibo paramWeibo)
    throws WeiboException
  {
    Document localDocument = paramResponse.asDocument();
    if (isRootNodeNilClasses(localDocument))
      return new ArrayList(0);
    try
    {
      ensureRootNodeNameIs("tags", localDocument);
      NodeList localNodeList = localDocument.getDocumentElement().getElementsByTagName("tagid");
      int i = localNodeList.getLength();
      ArrayList localArrayList = new ArrayList(i);
      for (int j = 0; j < i; j++)
        localArrayList.add(new Tag(paramResponse, (Element)localNodeList.item(j), null, null));
      return localArrayList;
    }
    catch (WeiboException localWeiboException)
    {
      ensureRootNodeNameIs("nil-classes", localDocument);
    }
    return new ArrayList(0);
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    Tag localTag;
    do
    {
      do
      {
        return true;
        if (paramObject == null)
          return false;
        if (getClass() != paramObject.getClass())
          return false;
        localTag = (Tag)paramObject;
        if (this.id == null)
        {
          if (localTag.id != null)
            return false;
        }
        else if (!this.id.equals(localTag.id))
          return false;
        if (this.value != null)
          break;
      }
      while (localTag.value == null);
      return false;
    }
    while (this.value.equals(localTag.value));
    return false;
  }

  public String getId()
  {
    return this.id;
  }

  public String getValue()
  {
    return this.value;
  }

  public int hashCode()
  {
    int i;
    int j;
    int k;
    if (this.id == null)
    {
      i = 0;
      j = 31 * (i + 31);
      String str = this.value;
      k = 0;
      if (str != null)
        break label45;
    }
    while (true)
    {
      return j + k;
      i = this.id.hashCode();
      break;
      label45: k = this.value.hashCode();
    }
  }

  public String toString()
  {
    return "tags{ " + this.id + "," + this.value + '}';
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.Tag
 * JD-Core Version:    0.6.2
 */