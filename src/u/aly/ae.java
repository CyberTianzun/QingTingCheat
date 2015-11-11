package u.aly;

import org.json.JSONArray;

public class ae extends ay
{
  public ae()
  {
  }

  public ae(String paramString)
    throws Exception
  {
    a(new JSONArray(paramString));
  }

  public ae(JSONArray paramJSONArray)
    throws Exception
  {
    a(paramJSONArray);
  }

  private void a(JSONArray paramJSONArray)
    throws Exception
  {
    a(paramJSONArray.getString(0));
    a(paramJSONArray.getInt(1));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.ae
 * JD-Core Version:    0.6.2
 */