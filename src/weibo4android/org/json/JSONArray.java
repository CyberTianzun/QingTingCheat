package weibo4android.org.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class JSONArray
{
  private ArrayList myArrayList;

  public JSONArray()
  {
    this.myArrayList = new ArrayList();
  }

  public JSONArray(Object paramObject)
    throws JSONException
  {
    this();
    if (paramObject.getClass().isArray())
    {
      int i = Array.getLength(paramObject);
      for (int j = 0; j < i; j++)
        put(Array.get(paramObject, j));
    }
    throw new JSONException("JSONArray initial value should be a string or collection or array.");
  }

  public JSONArray(Object paramObject, boolean paramBoolean)
    throws JSONException
  {
    this();
    if (paramObject.getClass().isArray())
    {
      int i = Array.getLength(paramObject);
      for (int j = 0; j < i; j++)
        put(new JSONObject(Array.get(paramObject, j), paramBoolean));
    }
    throw new JSONException("JSONArray initial value should be a string or collection or array.");
  }

  public JSONArray(String paramString)
    throws JSONException
  {
    this(new JSONTokener(paramString));
  }

  public JSONArray(Collection paramCollection)
  {
    if (paramCollection == null);
    for (ArrayList localArrayList = new ArrayList(); ; localArrayList = new ArrayList(paramCollection))
    {
      this.myArrayList = localArrayList;
      return;
    }
  }

  public JSONArray(Collection paramCollection, boolean paramBoolean)
  {
    this.myArrayList = new ArrayList();
    if (paramCollection != null)
    {
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext())
        this.myArrayList.add(new JSONObject(localIterator.next(), paramBoolean));
    }
  }

  public JSONArray(JSONTokener paramJSONTokener)
    throws JSONException
  {
    this();
    int i = paramJSONTokener.nextClean();
    char c1;
    if (i == 91)
    {
      c1 = ']';
      if (paramJSONTokener.nextClean() != ']')
        break label47;
    }
    label47: char c2;
    do
    {
      return;
      if (i == 40)
      {
        c1 = ')';
        break;
      }
      throw paramJSONTokener.syntaxError("A JSONArray text must start with '['");
      paramJSONTokener.back();
      while (true)
      {
        if (paramJSONTokener.nextClean() == ',')
        {
          paramJSONTokener.back();
          this.myArrayList.add(null);
        }
        while (true)
        {
          c2 = paramJSONTokener.nextClean();
          switch (c2)
          {
          default:
            throw paramJSONTokener.syntaxError("Expected a ',' or ']'");
            paramJSONTokener.back();
            this.myArrayList.add(paramJSONTokener.nextValue());
          case ',':
          case ';':
          case ')':
          case ']':
          }
        }
        if (paramJSONTokener.nextClean() == ']')
          break;
        paramJSONTokener.back();
      }
    }
    while (c1 == c2);
    throw paramJSONTokener.syntaxError("Expected a '" + new Character(c1) + "'");
  }

  public Object get(int paramInt)
    throws JSONException
  {
    Object localObject = opt(paramInt);
    if (localObject == null)
      throw new JSONException("JSONArray[" + paramInt + "] not found.");
    return localObject;
  }

  public boolean getBoolean(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if ((localObject.equals(Boolean.FALSE)) || (((localObject instanceof String)) && (((String)localObject).equalsIgnoreCase("false"))))
      return false;
    if ((localObject.equals(Boolean.TRUE)) || (((localObject instanceof String)) && (((String)localObject).equalsIgnoreCase("true"))))
      return true;
    throw new JSONException("JSONArray[" + paramInt + "] is not a Boolean.");
  }

  public double getDouble(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    try
    {
      if ((localObject instanceof Number))
        return ((Number)localObject).doubleValue();
      double d = Double.valueOf((String)localObject).doubleValue();
      return d;
    }
    catch (Exception localException)
    {
    }
    throw new JSONException("JSONArray[" + paramInt + "] is not a number.");
  }

  public int getInt(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if ((localObject instanceof Number))
      return ((Number)localObject).intValue();
    return (int)getDouble(paramInt);
  }

  public JSONArray getJSONArray(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if ((localObject instanceof JSONArray))
      return (JSONArray)localObject;
    throw new JSONException("JSONArray[" + paramInt + "] is not a JSONArray.");
  }

  public JSONObject getJSONObject(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if ((localObject instanceof JSONObject))
      return (JSONObject)localObject;
    throw new JSONException("JSONArray[" + paramInt + "] is not a JSONObject.");
  }

  public long getLong(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if ((localObject instanceof Number))
      return ((Number)localObject).longValue();
    return ()getDouble(paramInt);
  }

  public String getString(int paramInt)
    throws JSONException
  {
    return get(paramInt).toString();
  }

  public boolean isNull(int paramInt)
  {
    return JSONObject.NULL.equals(opt(paramInt));
  }

  public String join(String paramString)
    throws JSONException
  {
    int i = length();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int j = 0; j < i; j++)
    {
      if (j > 0)
        localStringBuffer.append(paramString);
      localStringBuffer.append(JSONObject.valueToString(this.myArrayList.get(j)));
    }
    return localStringBuffer.toString();
  }

  public int length()
  {
    return this.myArrayList.size();
  }

  public Object opt(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= length()))
      return null;
    return this.myArrayList.get(paramInt);
  }

  public boolean optBoolean(int paramInt)
  {
    return optBoolean(paramInt, false);
  }

  public boolean optBoolean(int paramInt, boolean paramBoolean)
  {
    try
    {
      boolean bool = getBoolean(paramInt);
      return bool;
    }
    catch (Exception localException)
    {
    }
    return paramBoolean;
  }

  public double optDouble(int paramInt)
  {
    return optDouble(paramInt, (0.0D / 0.0D));
  }

  public double optDouble(int paramInt, double paramDouble)
  {
    try
    {
      double d = getDouble(paramInt);
      return d;
    }
    catch (Exception localException)
    {
    }
    return paramDouble;
  }

  public int optInt(int paramInt)
  {
    return optInt(paramInt, 0);
  }

  public int optInt(int paramInt1, int paramInt2)
  {
    try
    {
      int i = getInt(paramInt1);
      return i;
    }
    catch (Exception localException)
    {
    }
    return paramInt2;
  }

  public JSONArray optJSONArray(int paramInt)
  {
    Object localObject = opt(paramInt);
    if ((localObject instanceof JSONArray))
      return (JSONArray)localObject;
    return null;
  }

  public JSONObject optJSONObject(int paramInt)
  {
    Object localObject = opt(paramInt);
    if ((localObject instanceof JSONObject))
      return (JSONObject)localObject;
    return null;
  }

  public long optLong(int paramInt)
  {
    return optLong(paramInt, 0L);
  }

  public long optLong(int paramInt, long paramLong)
  {
    try
    {
      long l = getLong(paramInt);
      return l;
    }
    catch (Exception localException)
    {
    }
    return paramLong;
  }

  public String optString(int paramInt)
  {
    return optString(paramInt, "");
  }

  public String optString(int paramInt, String paramString)
  {
    Object localObject = opt(paramInt);
    if (localObject != null)
      paramString = localObject.toString();
    return paramString;
  }

  public JSONArray put(double paramDouble)
    throws JSONException
  {
    Double localDouble = new Double(paramDouble);
    JSONObject.testValidity(localDouble);
    put(localDouble);
    return this;
  }

  public JSONArray put(int paramInt)
  {
    put(new Integer(paramInt));
    return this;
  }

  public JSONArray put(int paramInt, double paramDouble)
    throws JSONException
  {
    put(paramInt, new Double(paramDouble));
    return this;
  }

  public JSONArray put(int paramInt1, int paramInt2)
    throws JSONException
  {
    put(paramInt1, new Integer(paramInt2));
    return this;
  }

  public JSONArray put(int paramInt, long paramLong)
    throws JSONException
  {
    put(paramInt, new Long(paramLong));
    return this;
  }

  public JSONArray put(int paramInt, Object paramObject)
    throws JSONException
  {
    JSONObject.testValidity(paramObject);
    if (paramInt < 0)
      throw new JSONException("JSONArray[" + paramInt + "] not found.");
    if (paramInt < length())
    {
      this.myArrayList.set(paramInt, paramObject);
      return this;
    }
    while (paramInt != length())
      put(JSONObject.NULL);
    put(paramObject);
    return this;
  }

  public JSONArray put(int paramInt, Collection paramCollection)
    throws JSONException
  {
    put(paramInt, new JSONArray(paramCollection));
    return this;
  }

  public JSONArray put(int paramInt, Map paramMap)
    throws JSONException
  {
    put(paramInt, new JSONObject(paramMap));
    return this;
  }

  public JSONArray put(int paramInt, boolean paramBoolean)
    throws JSONException
  {
    if (paramBoolean);
    for (Boolean localBoolean = Boolean.TRUE; ; localBoolean = Boolean.FALSE)
    {
      put(paramInt, localBoolean);
      return this;
    }
  }

  public JSONArray put(long paramLong)
  {
    put(new Long(paramLong));
    return this;
  }

  public JSONArray put(Object paramObject)
  {
    this.myArrayList.add(paramObject);
    return this;
  }

  public JSONArray put(Collection paramCollection)
  {
    put(new JSONArray(paramCollection));
    return this;
  }

  public JSONArray put(Map paramMap)
  {
    put(new JSONObject(paramMap));
    return this;
  }

  public JSONArray put(boolean paramBoolean)
  {
    if (paramBoolean);
    for (Boolean localBoolean = Boolean.TRUE; ; localBoolean = Boolean.FALSE)
    {
      put(localBoolean);
      return this;
    }
  }

  public JSONObject toJSONObject(JSONArray paramJSONArray)
    throws JSONException
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0) || (length() == 0))
      return null;
    JSONObject localJSONObject = new JSONObject();
    for (int i = 0; i < paramJSONArray.length(); i++)
      localJSONObject.put(paramJSONArray.getString(i), opt(i));
    return localJSONObject;
  }

  public String toString()
  {
    try
    {
      String str = '[' + join(",") + ']';
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public String toString(int paramInt)
    throws JSONException
  {
    return toString(paramInt, 0);
  }

  String toString(int paramInt1, int paramInt2)
    throws JSONException
  {
    int i = 0;
    int j = length();
    if (j == 0)
      return "[]";
    StringBuffer localStringBuffer = new StringBuffer("[");
    if (j == 1)
      localStringBuffer.append(JSONObject.valueToString(this.myArrayList.get(0), paramInt1, paramInt2));
    while (true)
    {
      localStringBuffer.append(']');
      return localStringBuffer.toString();
      int k = paramInt2 + paramInt1;
      localStringBuffer.append('\n');
      for (int m = 0; m < j; m++)
      {
        if (m > 0)
          localStringBuffer.append(",\n");
        for (int n = 0; n < k; n++)
          localStringBuffer.append(' ');
        localStringBuffer.append(JSONObject.valueToString(this.myArrayList.get(m), paramInt1, k));
      }
      localStringBuffer.append('\n');
      while (i < paramInt2)
      {
        localStringBuffer.append(' ');
        i++;
      }
    }
  }

  public Writer write(Writer paramWriter)
    throws JSONException
  {
    for (int i = 0; ; i = 1)
    {
      int k;
      Object localObject;
      try
      {
        int j = length();
        paramWriter.write(91);
        k = 0;
        if (k >= j)
          break label109;
        if (i != 0)
          paramWriter.write(44);
        localObject = this.myArrayList.get(k);
        if ((localObject instanceof JSONObject))
          ((JSONObject)localObject).write(paramWriter);
        else if ((localObject instanceof JSONArray))
          ((JSONArray)localObject).write(paramWriter);
      }
      catch (IOException localIOException)
      {
        throw new JSONException(localIOException);
      }
      paramWriter.write(JSONObject.valueToString(localObject));
      break label117;
      label109: paramWriter.write(93);
      return paramWriter;
      label117: k++;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.org.json.JSONArray
 * JD-Core Version:    0.6.2
 */