package weibo4android.org.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class JSONObject
{
  public static final Object NULL = new Null(null);
  private Map map;

  public JSONObject()
  {
    this.map = new HashMap();
  }

  public JSONObject(Object paramObject)
  {
    this();
    populateInternalMap(paramObject, false);
  }

  public JSONObject(Object paramObject, boolean paramBoolean)
  {
    this();
    populateInternalMap(paramObject, paramBoolean);
  }

  public JSONObject(Object paramObject, String[] paramArrayOfString)
  {
    this();
    Class localClass = paramObject.getClass();
    int i = 0;
    while (true)
    {
      String str;
      if (i < paramArrayOfString.length)
        str = paramArrayOfString[i];
      try
      {
        putOpt(str, localClass.getField(str).get(paramObject));
        label42: i++;
        continue;
        return;
      }
      catch (Exception localException)
      {
        break label42;
      }
    }
  }

  public JSONObject(String paramString)
    throws JSONException
  {
    this(new JSONTokener(paramString));
  }

  public JSONObject(Map paramMap)
  {
    if (paramMap == null)
      paramMap = new HashMap();
    this.map = paramMap;
  }

  public JSONObject(Map paramMap, boolean paramBoolean)
  {
    this.map = new HashMap();
    if (paramMap != null)
    {
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        this.map.put(localEntry.getKey(), new JSONObject(localEntry.getValue(), paramBoolean));
      }
    }
  }

  public JSONObject(JSONObject paramJSONObject, String[] paramArrayOfString)
    throws JSONException
  {
    this();
    for (int i = 0; i < paramArrayOfString.length; i++)
      putOnce(paramArrayOfString[i], paramJSONObject.opt(paramArrayOfString[i]));
  }

  public JSONObject(JSONTokener paramJSONTokener)
    throws JSONException
  {
    this();
    if (paramJSONTokener.nextClean() != '{')
      throw paramJSONTokener.syntaxError("A JSONObject text must begin with '{'");
    do
    {
      paramJSONTokener.back();
      String str;
      int i;
      switch (paramJSONTokener.nextClean())
      {
      default:
        paramJSONTokener.back();
        str = paramJSONTokener.nextValue().toString();
        i = paramJSONTokener.nextClean();
        if (i == 61)
          if (paramJSONTokener.next() != '>')
            paramJSONTokener.back();
      case '\000':
        while (i == 58)
        {
          putOnce(str, paramJSONTokener.nextValue());
          switch (paramJSONTokener.nextClean())
          {
          default:
            throw paramJSONTokener.syntaxError("Expected a ',' or '}'");
            throw paramJSONTokener.syntaxError("A JSONObject text must end with '}'");
          case ',':
          case ';':
          case '}':
          }
        }
        throw paramJSONTokener.syntaxError("Expected a ':' after a key");
      case '}':
      }
    }
    while (paramJSONTokener.nextClean() != '}');
  }

  public static String doubleToString(double paramDouble)
  {
    String str;
    if ((Double.isInfinite(paramDouble)) || (Double.isNaN(paramDouble)))
      str = "null";
    do
    {
      do
      {
        return str;
        str = Double.toString(paramDouble);
      }
      while ((str.indexOf('.') <= 0) || (str.indexOf('e') >= 0) || (str.indexOf('E') >= 0));
      while (str.endsWith("0"))
        str = str.substring(0, -1 + str.length());
    }
    while (!str.endsWith("."));
    return str.substring(0, -1 + str.length());
  }

  public static String[] getNames(Object paramObject)
  {
    String[] arrayOfString = null;
    if (paramObject == null);
    while (true)
    {
      return arrayOfString;
      Field[] arrayOfField = paramObject.getClass().getFields();
      int i = arrayOfField.length;
      arrayOfString = null;
      if (i != 0)
      {
        arrayOfString = new String[i];
        for (int j = 0; j < i; j++)
          arrayOfString[j] = arrayOfField[j].getName();
      }
    }
  }

  public static String[] getNames(JSONObject paramJSONObject)
  {
    int i = paramJSONObject.length();
    if (i == 0)
      return null;
    Iterator localIterator = paramJSONObject.keys();
    String[] arrayOfString = new String[i];
    for (int j = 0; localIterator.hasNext(); j++)
      arrayOfString[j] = ((String)localIterator.next());
    return arrayOfString;
  }

  private boolean isStandardProperty(Class paramClass)
  {
    return (paramClass.isPrimitive()) || (paramClass.isAssignableFrom(Byte.class)) || (paramClass.isAssignableFrom(Short.class)) || (paramClass.isAssignableFrom(Integer.class)) || (paramClass.isAssignableFrom(Long.class)) || (paramClass.isAssignableFrom(Float.class)) || (paramClass.isAssignableFrom(Double.class)) || (paramClass.isAssignableFrom(Character.class)) || (paramClass.isAssignableFrom(String.class)) || (paramClass.isAssignableFrom(Boolean.class));
  }

  public static String numberToString(Number paramNumber)
    throws JSONException
  {
    if (paramNumber == null)
      throw new JSONException("Null pointer");
    testValidity(paramNumber);
    String str = paramNumber.toString();
    if ((str.indexOf('.') > 0) && (str.indexOf('e') < 0) && (str.indexOf('E') < 0))
    {
      while (str.endsWith("0"))
        str = str.substring(0, -1 + str.length());
      if (str.endsWith("."))
        str = str.substring(0, -1 + str.length());
    }
    return str;
  }

  private void populateInternalMap(Object paramObject, boolean paramBoolean)
  {
    int i = 0;
    Class localClass = paramObject.getClass();
    if (localClass.getClassLoader() == null)
      paramBoolean = false;
    Method[] arrayOfMethod;
    if (paramBoolean)
    {
      arrayOfMethod = localClass.getMethods();
      if (i >= arrayOfMethod.length)
        break label462;
    }
    while (true)
    {
      String str2;
      String str3;
      Object localObject;
      try
      {
        Method localMethod = arrayOfMethod[i];
        String str1 = localMethod.getName();
        str2 = "";
        if (str1.startsWith("get"))
        {
          str2 = str1.substring(3);
          if ((str2.length() > 0) && (Character.isUpperCase(str2.charAt(0))) && (localMethod.getParameterTypes().length == 0))
          {
            if (str2.length() != 1)
              continue;
            str3 = str2.toLowerCase();
            localObject = localMethod.invoke(paramObject, (Object[])null);
            if (localObject != null)
              continue;
            this.map.put(str3, NULL);
          }
          i++;
          break;
          arrayOfMethod = localClass.getDeclaredMethods();
          i = 0;
          break;
        }
        if (!str1.startsWith("is"))
          continue;
        str2 = str1.substring(2);
        continue;
        if (Character.isUpperCase(str2.charAt(1)))
          break label463;
        str3 = str2.substring(0, 1).toLowerCase() + str2.substring(1);
        continue;
        if (localObject.getClass().isArray())
        {
          this.map.put(str3, new JSONArray(localObject, paramBoolean));
          continue;
        }
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException);
      }
      if ((localObject instanceof Collection))
      {
        this.map.put(str3, new JSONArray((Collection)localObject, paramBoolean));
      }
      else if ((localObject instanceof Map))
      {
        this.map.put(str3, new JSONObject((Map)localObject, paramBoolean));
      }
      else if (isStandardProperty(localObject.getClass()))
      {
        this.map.put(str3, localObject);
      }
      else if ((localObject.getClass().getPackage().getName().startsWith("java")) || (localObject.getClass().getClassLoader() == null))
      {
        this.map.put(str3, localObject.toString());
      }
      else
      {
        this.map.put(str3, new JSONObject(localObject, paramBoolean));
        continue;
        label462: return;
        label463: str3 = str2;
      }
    }
  }

  public static String quote(String paramString)
  {
    int i = 0;
    if ((paramString == null) || (paramString.length() == 0))
      return "\"\"";
    int j = paramString.length();
    StringBuffer localStringBuffer = new StringBuffer(j + 4);
    localStringBuffer.append('"');
    int k = 0;
    if (i < j)
    {
      int m = paramString.charAt(i);
      switch (m)
      {
      default:
        if ((m < 32) || ((m >= 128) && (m < 160)) || ((m >= 8192) && (m < 8448)))
        {
          String str = "000" + Integer.toHexString(m);
          localStringBuffer.append("\\u" + str.substring(-4 + str.length()));
        }
        break;
      case 34:
      case 92:
      case 47:
      case 8:
      case 9:
      case 10:
      case 12:
      case 13:
      }
      while (true)
      {
        i++;
        k = m;
        break;
        localStringBuffer.append('\\');
        localStringBuffer.append(m);
        continue;
        if (k == 60)
          localStringBuffer.append('\\');
        localStringBuffer.append(m);
        continue;
        localStringBuffer.append("\\b");
        continue;
        localStringBuffer.append("\\t");
        continue;
        localStringBuffer.append("\\n");
        continue;
        localStringBuffer.append("\\f");
        continue;
        localStringBuffer.append("\\r");
        continue;
        localStringBuffer.append(m);
      }
    }
    localStringBuffer.append('"');
    return localStringBuffer.toString();
  }

  public static Object stringToValue(String paramString)
  {
    if (paramString.equals(""));
    int i;
    do
    {
      return paramString;
      if (paramString.equalsIgnoreCase("true"))
        return Boolean.TRUE;
      if (paramString.equalsIgnoreCase("false"))
        return Boolean.FALSE;
      if (paramString.equalsIgnoreCase("null"))
        return NULL;
      i = paramString.charAt(0);
    }
    while (((i < 48) || (i > 57)) && (i != 46) && (i != 45) && (i != 43));
    if ((i != 48) || ((paramString.length() > 2) && ((paramString.charAt(1) == 'x') || (paramString.charAt(1) == 'X'))));
    try
    {
      Integer localInteger3 = new Integer(Integer.parseInt(paramString.substring(2), 16));
      return localInteger3;
      try
      {
        Integer localInteger2 = new Integer(Integer.parseInt(paramString, 8));
        return localInteger2;
      }
      catch (Exception localException4)
      {
      }
      try
      {
        label164: Integer localInteger1 = new Integer(paramString);
        return localInteger1;
      }
      catch (Exception localException1)
      {
        try
        {
          Long localLong = new Long(paramString);
          return localLong;
        }
        catch (Exception localException2)
        {
          try
          {
            Double localDouble = new Double(paramString);
            return localDouble;
          }
          catch (Exception localException3)
          {
            return paramString;
          }
        }
      }
    }
    catch (Exception localException5)
    {
      break label164;
    }
  }

  static void testValidity(Object paramObject)
    throws JSONException
  {
    if (paramObject != null)
      if ((paramObject instanceof Double))
      {
        if ((((Double)paramObject).isInfinite()) || (((Double)paramObject).isNaN()))
          throw new JSONException("JSON does not allow non-finite numbers.");
      }
      else if (((paramObject instanceof Float)) && ((((Float)paramObject).isInfinite()) || (((Float)paramObject).isNaN())))
        throw new JSONException("JSON does not allow non-finite numbers.");
  }

  static String valueToString(Object paramObject)
    throws JSONException
  {
    if ((paramObject == null) || (paramObject.equals(null)))
      return "null";
    if ((paramObject instanceof JSONString))
    {
      String str;
      try
      {
        str = ((JSONString)paramObject).toJSONString();
        if ((str instanceof String))
          return (String)str;
      }
      catch (Exception localException)
      {
        throw new JSONException(localException);
      }
      throw new JSONException("Bad value from toJSONString: " + str);
    }
    if ((paramObject instanceof Number))
      return numberToString((Number)paramObject);
    if (((paramObject instanceof Boolean)) || ((paramObject instanceof JSONObject)) || ((paramObject instanceof JSONArray)))
      return paramObject.toString();
    if ((paramObject instanceof Map))
      return new JSONObject((Map)paramObject).toString();
    if ((paramObject instanceof Collection))
      return new JSONArray((Collection)paramObject).toString();
    if (paramObject.getClass().isArray())
      return new JSONArray(paramObject).toString();
    return quote(paramObject.toString());
  }

  static String valueToString(Object paramObject, int paramInt1, int paramInt2)
    throws JSONException
  {
    if ((paramObject == null) || (paramObject.equals(null)))
      return "null";
    try
    {
      if ((paramObject instanceof JSONString))
      {
        String str1 = ((JSONString)paramObject).toJSONString();
        if ((str1 instanceof String))
        {
          String str2 = (String)str1;
          return str2;
        }
      }
    }
    catch (Exception localException)
    {
      if ((paramObject instanceof Number))
        return numberToString((Number)paramObject);
      if ((paramObject instanceof Boolean))
        return paramObject.toString();
      if ((paramObject instanceof JSONObject))
        return ((JSONObject)paramObject).toString(paramInt1, paramInt2);
      if ((paramObject instanceof JSONArray))
        return ((JSONArray)paramObject).toString(paramInt1, paramInt2);
      if ((paramObject instanceof Map))
        return new JSONObject((Map)paramObject).toString(paramInt1, paramInt2);
      if ((paramObject instanceof Collection))
        return new JSONArray((Collection)paramObject).toString(paramInt1, paramInt2);
      if (paramObject.getClass().isArray())
        return new JSONArray(paramObject).toString(paramInt1, paramInt2);
    }
    return quote(paramObject.toString());
  }

  public JSONObject accumulate(String paramString, Object paramObject)
    throws JSONException
  {
    testValidity(paramObject);
    Object localObject = opt(paramString);
    if (localObject == null)
    {
      if ((paramObject instanceof JSONArray))
        paramObject = new JSONArray().put(paramObject);
      put(paramString, paramObject);
      return this;
    }
    if ((localObject instanceof JSONArray))
    {
      ((JSONArray)localObject).put(paramObject);
      return this;
    }
    put(paramString, new JSONArray().put(localObject).put(paramObject));
    return this;
  }

  public JSONObject append(String paramString, Object paramObject)
    throws JSONException
  {
    testValidity(paramObject);
    Object localObject = opt(paramString);
    if (localObject == null)
    {
      put(paramString, new JSONArray().put(paramObject));
      return this;
    }
    if ((localObject instanceof JSONArray))
    {
      put(paramString, ((JSONArray)localObject).put(paramObject));
      return this;
    }
    throw new JSONException("JSONObject[" + paramString + "] is not a JSONArray.");
  }

  public Object get(String paramString)
    throws JSONException
  {
    return opt(paramString);
  }

  public boolean getBoolean(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if (localObject == null)
      return false;
    if ((localObject.equals(Boolean.FALSE)) || (((localObject instanceof String)) && (((String)localObject).equalsIgnoreCase("false"))))
      return false;
    if ((localObject.equals(Boolean.TRUE)) || (((localObject instanceof String)) && (((String)localObject).equalsIgnoreCase("true"))))
      return true;
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a Boolean.");
  }

  public double getDouble(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if (localObject == null)
      return 0.0D;
    try
    {
      if ((localObject instanceof Number))
        return ((Number)localObject).doubleValue();
      if (localObject.toString().length() > 0)
      {
        double d = Double.valueOf(localObject.toString()).doubleValue();
        return d;
      }
      return 0.0D;
    }
    catch (Exception localException)
    {
    }
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a number.");
  }

  public int getInt(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if (localObject == null)
      return 0;
    if ((localObject instanceof Number))
      return ((Number)localObject).intValue();
    return (int)getDouble(paramString);
  }

  public JSONArray getJSONArray(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if (localObject == null)
      return null;
    if ((localObject instanceof JSONArray))
      return (JSONArray)localObject;
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a JSONArray.");
  }

  public JSONObject getJSONObject(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if (localObject == null)
      return null;
    if ((localObject instanceof JSONObject))
      return (JSONObject)localObject;
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a JSONObject.");
  }

  public long getLong(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if (localObject == null)
      return 0L;
    if ((localObject instanceof String))
    {
      if (localObject.toString().length() > 0)
        return Long.valueOf(localObject.toString()).longValue();
      return 0L;
    }
    if ((localObject instanceof Number))
      return ((Number)localObject).longValue();
    return ()getDouble(paramString);
  }

  public String getString(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if (localObject == null)
      return "";
    return localObject.toString();
  }

  public boolean has(String paramString)
  {
    return this.map.containsKey(paramString);
  }

  public boolean isNull(String paramString)
  {
    return NULL.equals(opt(paramString));
  }

  public Iterator keys()
  {
    return this.map.keySet().iterator();
  }

  public int length()
  {
    return this.map.size();
  }

  public JSONArray names()
  {
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = keys();
    while (localIterator.hasNext())
      localJSONArray.put(localIterator.next());
    if (localJSONArray.length() == 0)
      localJSONArray = null;
    return localJSONArray;
  }

  public Object opt(String paramString)
  {
    if (paramString == null)
      return null;
    return this.map.get(paramString);
  }

  public boolean optBoolean(String paramString)
  {
    return optBoolean(paramString, false);
  }

  public boolean optBoolean(String paramString, boolean paramBoolean)
  {
    try
    {
      boolean bool = getBoolean(paramString);
      return bool;
    }
    catch (Exception localException)
    {
    }
    return paramBoolean;
  }

  public double optDouble(String paramString)
  {
    return optDouble(paramString, (0.0D / 0.0D));
  }

  public double optDouble(String paramString, double paramDouble)
  {
    try
    {
      Object localObject = opt(paramString);
      if ((localObject instanceof Number))
        return ((Number)localObject).doubleValue();
      double d = new Double((String)localObject).doubleValue();
      return d;
    }
    catch (Exception localException)
    {
    }
    return paramDouble;
  }

  public int optInt(String paramString)
  {
    return optInt(paramString, 0);
  }

  public int optInt(String paramString, int paramInt)
  {
    try
    {
      int i = getInt(paramString);
      return i;
    }
    catch (Exception localException)
    {
    }
    return paramInt;
  }

  public JSONArray optJSONArray(String paramString)
  {
    Object localObject = opt(paramString);
    if ((localObject instanceof JSONArray))
      return (JSONArray)localObject;
    return null;
  }

  public JSONObject optJSONObject(String paramString)
  {
    Object localObject = opt(paramString);
    if ((localObject instanceof JSONObject))
      return (JSONObject)localObject;
    return null;
  }

  public long optLong(String paramString)
  {
    return optLong(paramString, 0L);
  }

  public long optLong(String paramString, long paramLong)
  {
    try
    {
      long l = getLong(paramString);
      return l;
    }
    catch (Exception localException)
    {
    }
    return paramLong;
  }

  public String optString(String paramString)
  {
    return optString(paramString, "");
  }

  public String optString(String paramString1, String paramString2)
  {
    Object localObject = opt(paramString1);
    if (localObject != null)
      paramString2 = localObject.toString();
    return paramString2;
  }

  public JSONObject put(String paramString, double paramDouble)
    throws JSONException
  {
    put(paramString, new Double(paramDouble));
    return this;
  }

  public JSONObject put(String paramString, int paramInt)
    throws JSONException
  {
    put(paramString, new Integer(paramInt));
    return this;
  }

  public JSONObject put(String paramString, long paramLong)
    throws JSONException
  {
    put(paramString, new Long(paramLong));
    return this;
  }

  public JSONObject put(String paramString, Object paramObject)
    throws JSONException
  {
    if (paramString == null)
      throw new JSONException("Null key.");
    if (paramObject != null)
    {
      testValidity(paramObject);
      this.map.put(paramString, paramObject);
      return this;
    }
    remove(paramString);
    return this;
  }

  public JSONObject put(String paramString, Collection paramCollection)
    throws JSONException
  {
    put(paramString, new JSONArray(paramCollection));
    return this;
  }

  public JSONObject put(String paramString, Map paramMap)
    throws JSONException
  {
    put(paramString, new JSONObject(paramMap));
    return this;
  }

  public JSONObject put(String paramString, boolean paramBoolean)
    throws JSONException
  {
    if (paramBoolean);
    for (Boolean localBoolean = Boolean.TRUE; ; localBoolean = Boolean.FALSE)
    {
      put(paramString, localBoolean);
      return this;
    }
  }

  public JSONObject putOnce(String paramString, Object paramObject)
    throws JSONException
  {
    if ((paramString != null) && (paramObject != null))
    {
      if (opt(paramString) != null)
        throw new JSONException("Duplicate key \"" + paramString + "\"");
      put(paramString, paramObject);
    }
    return this;
  }

  public JSONObject putOpt(String paramString, Object paramObject)
    throws JSONException
  {
    if ((paramString != null) && (paramObject != null))
      put(paramString, paramObject);
    return this;
  }

  public Object remove(String paramString)
  {
    return this.map.remove(paramString);
  }

  public Iterator sortedKeys()
  {
    return new TreeSet(this.map.keySet()).iterator();
  }

  public JSONArray toJSONArray(JSONArray paramJSONArray)
    throws JSONException
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0))
      return null;
    JSONArray localJSONArray = new JSONArray();
    for (int i = 0; i < paramJSONArray.length(); i++)
      localJSONArray.put(opt(paramJSONArray.getString(i)));
    return localJSONArray;
  }

  public String toString()
  {
    try
    {
      Iterator localIterator = keys();
      StringBuffer localStringBuffer = new StringBuffer("{");
      while (localIterator.hasNext())
      {
        if (localStringBuffer.length() > 1)
          localStringBuffer.append(',');
        Object localObject = localIterator.next();
        localStringBuffer.append(quote(localObject.toString()));
        localStringBuffer.append(':');
        localStringBuffer.append(valueToString(this.map.get(localObject)));
      }
      localStringBuffer.append('}');
      String str = localStringBuffer.toString();
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
      return "{}";
    Iterator localIterator = sortedKeys();
    StringBuffer localStringBuffer = new StringBuffer("{");
    int k = paramInt2 + paramInt1;
    if (j == 1)
    {
      Object localObject2 = localIterator.next();
      localStringBuffer.append(quote(localObject2.toString()));
      localStringBuffer.append(": ");
      localStringBuffer.append(valueToString(this.map.get(localObject2), paramInt1, paramInt2));
    }
    while (true)
    {
      localStringBuffer.append('}');
      return localStringBuffer.toString();
      Object localObject1;
      localStringBuffer.append(quote(localObject1.toString()));
      localStringBuffer.append(": ");
      localStringBuffer.append(valueToString(this.map.get(localObject1), paramInt1, k));
      if (localIterator.hasNext())
      {
        localObject1 = localIterator.next();
        if (localStringBuffer.length() > 1)
          localStringBuffer.append(",\n");
        while (true)
        {
          for (int m = 0; m < k; m++)
            localStringBuffer.append(' ');
          break;
          localStringBuffer.append('\n');
        }
      }
      if (localStringBuffer.length() > 1)
      {
        localStringBuffer.append('\n');
        while (i < paramInt2)
        {
          localStringBuffer.append(' ');
          i++;
        }
      }
    }
  }

  public Writer write(Writer paramWriter)
    throws JSONException
  {
    for (int i = 0; ; i = 1)
    {
      Object localObject2;
      try
      {
        Iterator localIterator = keys();
        paramWriter.write(123);
        if (!localIterator.hasNext())
          break label138;
        if (i != 0)
          paramWriter.write(44);
        Object localObject1 = localIterator.next();
        paramWriter.write(quote(localObject1.toString()));
        paramWriter.write(58);
        localObject2 = this.map.get(localObject1);
        if ((localObject2 instanceof JSONObject))
          ((JSONObject)localObject2).write(paramWriter);
        else if ((localObject2 instanceof JSONArray))
          ((JSONArray)localObject2).write(paramWriter);
      }
      catch (IOException localIOException)
      {
        throw new JSONException(localIOException);
      }
      paramWriter.write(valueToString(localObject2));
      continue;
      label138: paramWriter.write(125);
      return paramWriter;
    }
  }

  private static final class Null
  {
    protected final Object clone()
    {
      return this;
    }

    public boolean equals(Object paramObject)
    {
      return (paramObject == null) || (paramObject == this);
    }

    public String toString()
    {
      return "null";
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.org.json.JSONObject
 * JD-Core Version:    0.6.2
 */