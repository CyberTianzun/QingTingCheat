package fm.qingting.qtradio.parser;

import fm.qingting.framework.data.DataParserImpl;
import fm.qingting.framework.data.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import weibo4android.Comment;
import weibo4android.Status;
import weibo4android.User;
import weibo4android.WeiboException;
import weibo4android.org.json.JSONArray;
import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;

public class WeiboParser extends DataParserImpl
{
  private boolean errorWeibo(String paramString)
  {
    try
    {
      String str = new JSONObject(paramString).getString("error_code");
      if (str != null)
      {
        boolean bool = str.equalsIgnoreCase("");
        if (bool);
      }
      else
      {
        return false;
      }
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return false;
    }
    return true;
  }

  private Result parseWeiboComments(String paramString)
  {
    Result localResult;
    if (!errorWeibo(paramString))
      localResult = parserError(paramString);
    JSONObject localJSONObject;
    ArrayList localArrayList;
    while (true)
    {
      return localResult;
      try
      {
        localJSONObject = new JSONObject(paramString);
        JSONArray localJSONArray = localJSONObject.getJSONArray("comments");
        localResult = null;
        if (localJSONArray != null)
        {
          int i = localJSONArray.length();
          localArrayList = new ArrayList(i);
          for (int j = 0; j < i; j++)
            localArrayList.add(new Comment(localJSONArray.getJSONObject(j)));
        }
      }
      catch (JSONException localJSONException1)
      {
        localJSONException1.printStackTrace();
        return null;
      }
      catch (WeiboException localWeiboException)
      {
        localWeiboException.printStackTrace();
        return null;
      }
    }
    HashMap localHashMap = new HashMap();
    try
    {
      localHashMap.put("previous_curson", localJSONObject.getString("previous_curson"));
      localHashMap.put("next_cursor", localJSONObject.getString("next_cursor"));
      localHashMap.put("total_number", localJSONObject.getString("total_number"));
      localHashMap.put("comments", localArrayList);
      return new Result(true, localHashMap);
    }
    catch (JSONException localJSONException2)
    {
      localJSONException2.printStackTrace();
    }
    return null;
  }

  private Result parseWeiboCreatComments(String paramString)
  {
    if (!errorWeibo(paramString))
      return parserError(paramString);
    try
    {
      Comment localComment = new Comment(new JSONObject(paramString));
      return new Result(true, localComment);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return null;
    }
    catch (WeiboException localWeiboException)
    {
      localWeiboException.printStackTrace();
    }
    return null;
  }

  private Result parseWeiboGetUid(String paramString)
  {
    if (!errorWeibo(paramString))
      return parserError(paramString);
    try
    {
      String str = new JSONObject(paramString).getString("uid");
      return new Result(true, str);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  private Result parseWeiboID(String paramString)
  {
    if (!errorWeibo(paramString))
      return parserError(paramString);
    if (paramString != null)
      return new Result(true, paramString);
    return new Result(false, paramString);
  }

  private Result parseWeiboRepost(String paramString)
  {
    if (!errorWeibo(paramString))
      return parserError(paramString);
    try
    {
      Status localStatus = new Status(new JSONObject(paramString));
      return new Result(true, localStatus);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return null;
    }
    catch (WeiboException localWeiboException)
    {
      localWeiboException.printStackTrace();
    }
    return null;
  }

  private Result parseWeiboSearchTopic(String paramString)
  {
    Result localResult;
    if (!errorWeibo(paramString))
      localResult = parserError(paramString);
    JSONObject localJSONObject;
    ArrayList localArrayList;
    while (true)
    {
      return localResult;
      try
      {
        localJSONObject = new JSONObject(paramString);
        localResult = null;
        if (localJSONObject != null)
        {
          JSONArray localJSONArray = localJSONObject.getJSONArray("statuses");
          localResult = null;
          if (localJSONArray != null)
          {
            int i = localJSONArray.length();
            localArrayList = new ArrayList(i);
            for (int j = 0; j < i; j++)
              localArrayList.add(new Status(localJSONArray.getJSONObject(j)));
          }
        }
      }
      catch (JSONException localJSONException1)
      {
        localJSONException1.printStackTrace();
        return null;
      }
      catch (WeiboException localWeiboException)
      {
        localWeiboException.printStackTrace();
        return null;
      }
    }
    HashMap localHashMap = new HashMap();
    try
    {
      localHashMap.put("previous_curson", localJSONObject.getString("previous_curson"));
      localHashMap.put("next_cursor", localJSONObject.getString("next_cursor"));
      localHashMap.put("total_number", localJSONObject.getString("total_number"));
      localHashMap.put("statuses", localArrayList);
      return new Result(true, localHashMap);
    }
    catch (JSONException localJSONException2)
    {
      localJSONException2.printStackTrace();
    }
    return null;
  }

  private Result parseWeiboSearchTopicNoParser(String paramString)
  {
    if (!errorWeibo(paramString))
      return parserError(paramString);
    return new Result(true, paramString);
  }

  private Result parseWeiboStatus(String paramString)
  {
    if (!errorWeibo(paramString))
      return parserError(paramString);
    try
    {
      Status localStatus = new Status(new JSONObject(paramString));
      return new Result(true, localStatus);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return null;
    }
    catch (WeiboException localWeiboException)
    {
      localWeiboException.printStackTrace();
    }
    return null;
  }

  private Result parseWeiboUpdateStatue(String paramString)
  {
    if (!errorWeibo(paramString))
      return parserError(paramString);
    Status localStatus;
    try
    {
      localStatus = new Status(new JSONObject(paramString));
      if (localStatus != null)
        return new Result(true, localStatus);
    }
    catch (WeiboException localWeiboException)
    {
      localWeiboException.printStackTrace();
      return null;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return null;
    }
    return new Result(false, localStatus);
  }

  private Result parseWeiboUserInfo(String paramString)
  {
    if (!errorWeibo(paramString))
      return parserError(paramString);
    try
    {
      User localUser = new User(new JSONObject(paramString));
      return new Result(true, localUser);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return null;
    }
    catch (WeiboException localWeiboException)
    {
      localWeiboException.printStackTrace();
    }
    return null;
  }

  private Result parserError(String paramString)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Result localResult = new Result(false, null, localJSONObject.getString("error_code"), localJSONObject.getString("error"));
      return localResult;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  public Result parse(String paramString, Object paramObject1, Object paramObject2)
  {
    return super.parse(paramString, paramObject1, paramObject2);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.parser.WeiboParser
 * JD-Core Version:    0.6.2
 */