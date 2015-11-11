package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class EnumDeserializer
  implements ObjectDeserializer
{
  private final Class<?> enumClass;
  private final Map<String, Enum> nameMap = new HashMap();
  private final Map<Integer, Enum> ordinalMap = new HashMap();

  public EnumDeserializer(Class<?> paramClass)
  {
    this.enumClass = paramClass;
    try
    {
      Object[] arrayOfObject = (Object[])paramClass.getMethod("values", new Class[0]).invoke(null, new Object[0]);
      int i = arrayOfObject.length;
      for (int j = 0; j < i; j++)
      {
        Enum localEnum = (Enum)arrayOfObject[j];
        this.ordinalMap.put(Integer.valueOf(localEnum.ordinal()), localEnum);
        this.nameMap.put(localEnum.name(), localEnum);
      }
    }
    catch (Exception localException)
    {
      throw new JSONException("init enum values error, " + paramClass.getName());
    }
  }

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    Object localObject2;
    try
    {
      localJSONLexer = paramDefaultJSONParser.getLexer();
      if (localJSONLexer.token() == 2)
      {
        Integer localInteger = Integer.valueOf(localJSONLexer.intValue());
        localJSONLexer.nextToken(16);
        localObject2 = this.ordinalMap.get(localInteger);
        if (localObject2 != null)
          break label257;
        throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + localInteger);
      }
    }
    catch (JSONException localJSONException)
    {
      JSONLexer localJSONLexer;
      throw localJSONException;
      if (localJSONLexer.token() == 4)
      {
        String str = localJSONLexer.stringVal();
        localJSONLexer.nextToken(16);
        if (str.length() == 0)
          return (Object)null;
        this.nameMap.get(str);
        return Enum.valueOf(this.enumClass, str);
      }
      if (localJSONLexer.token() == 8)
      {
        localJSONLexer.nextToken(16);
        return null;
      }
    }
    catch (Throwable localThrowable)
    {
      throw new JSONException(localThrowable.getMessage(), localThrowable);
    }
    Object localObject1 = paramDefaultJSONParser.parse();
    throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + localObject1);
    label257: return localObject2;
  }

  public int getFastMatchToken()
  {
    return 2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.EnumDeserializer
 * JD-Core Version:    0.6.2
 */