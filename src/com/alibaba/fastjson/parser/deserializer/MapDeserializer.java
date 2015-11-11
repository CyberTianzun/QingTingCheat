package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapDeserializer
  implements ObjectDeserializer
{
  public static final MapDeserializer instance = new MapDeserializer();

  public static Object parseMap(DefaultJSONParser paramDefaultJSONParser, Map<Object, Object> paramMap, Type paramType1, Type paramType2, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if ((localJSONLexer.token() != 12) && (localJSONLexer.token() != 16))
      throw new JSONException("syntax error, expect {, actual " + localJSONLexer.tokenName());
    ObjectDeserializer localObjectDeserializer1 = paramDefaultJSONParser.getConfig().getDeserializer(paramType1);
    ObjectDeserializer localObjectDeserializer2 = paramDefaultJSONParser.getConfig().getDeserializer(paramType2);
    localJSONLexer.nextToken(localObjectDeserializer1.getFastMatchToken());
    ParseContext localParseContext1 = paramDefaultJSONParser.getContext();
    while (true)
    {
      String str;
      Object localObject3;
      try
      {
        if (localJSONLexer.token() == 13)
        {
          localJSONLexer.nextToken(16);
          return paramMap;
        }
        if ((localJSONLexer.token() != 4) || (!localJSONLexer.isRef()))
          break label365;
        localJSONLexer.nextTokenWithColon(4);
        if (localJSONLexer.token() != 4)
          break label311;
        str = localJSONLexer.stringVal();
        if ("..".equals(str))
        {
          localObject3 = localParseContext1.getParentContext().getObject();
          localJSONLexer.nextToken(13);
          if (localJSONLexer.token() == 13)
            break label347;
          throw new JSONException("illegal ref");
        }
      }
      finally
      {
        paramDefaultJSONParser.setContext(localParseContext1);
      }
      if ("$".equals(str))
      {
        for (ParseContext localParseContext2 = localParseContext1; localParseContext2.getParentContext() != null; localParseContext2 = localParseContext2.getParentContext());
        localObject3 = localParseContext2.getObject();
      }
      else
      {
        paramDefaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(localParseContext1, str));
        paramDefaultJSONParser.setResolveStatus(1);
        localObject3 = null;
        continue;
        label311: throw new JSONException("illegal ref, " + JSONToken.name(localJSONLexer.token()));
        label347: localJSONLexer.nextToken(16);
        paramDefaultJSONParser.setContext(localParseContext1);
        return localObject3;
        label365: if ((paramMap.size() == 0) && (localJSONLexer.token() == 4) && (JSON.DEFAULT_TYPE_KEY.equals(localJSONLexer.stringVal())))
        {
          localJSONLexer.nextTokenWithColon(4);
          localJSONLexer.nextToken(16);
          if (localJSONLexer.token() == 13)
          {
            localJSONLexer.nextToken();
            paramDefaultJSONParser.setContext(localParseContext1);
            return paramMap;
          }
          localJSONLexer.nextToken(localObjectDeserializer1.getFastMatchToken());
        }
        Object localObject2 = localObjectDeserializer1.deserialze(paramDefaultJSONParser, paramType1, null);
        if (localJSONLexer.token() != 17)
          throw new JSONException("syntax error, expect :, actual " + localJSONLexer.token());
        localJSONLexer.nextToken(localObjectDeserializer2.getFastMatchToken());
        paramMap.put(localObject2, localObjectDeserializer2.deserialze(paramDefaultJSONParser, paramType2, localObject2));
        if (localJSONLexer.token() == 16)
          localJSONLexer.nextToken(localObjectDeserializer1.getFastMatchToken());
      }
    }
  }

  public static Map parseMap(DefaultJSONParser paramDefaultJSONParser, Map<String, Object> paramMap, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() != 12)
      throw new JSONException("syntax error, expect {, actual " + localJSONLexer.token());
    ParseContext localParseContext = paramDefaultJSONParser.getContext();
    label434: label603: int j;
    do
    {
      String str;
      Class localClass;
      do
      {
        int i;
        try
        {
          localJSONLexer.skipWhitespace();
          i = localJSONLexer.getCurrent();
          if (paramDefaultJSONParser.isEnabled(Feature.AllowArbitraryCommas))
            while (i == 44)
            {
              localJSONLexer.next();
              localJSONLexer.skipWhitespace();
              i = localJSONLexer.getCurrent();
            }
          if (i == 34)
          {
            str = localJSONLexer.scanSymbol(paramDefaultJSONParser.getSymbolTable(), '"');
            localJSONLexer.skipWhitespace();
            if (localJSONLexer.getCurrent() == ':')
              break label434;
            throw new JSONException("expect ':' at " + localJSONLexer.pos());
          }
        }
        finally
        {
          paramDefaultJSONParser.setContext(localParseContext);
        }
        if (i == 125)
        {
          localJSONLexer.next();
          localJSONLexer.resetStringPosition();
          localJSONLexer.nextToken(16);
          paramDefaultJSONParser.setContext(localParseContext);
          return paramMap;
        }
        if (i == 39)
        {
          if (!paramDefaultJSONParser.isEnabled(Feature.AllowSingleQuotes))
            throw new JSONException("syntax error");
          str = localJSONLexer.scanSymbol(paramDefaultJSONParser.getSymbolTable(), '\'');
          localJSONLexer.skipWhitespace();
          if (localJSONLexer.getCurrent() != ':')
            throw new JSONException("expect ':' at " + localJSONLexer.pos());
        }
        else
        {
          if (!paramDefaultJSONParser.isEnabled(Feature.AllowUnQuotedFieldNames))
            throw new JSONException("syntax error");
          str = localJSONLexer.scanSymbolUnQuoted(paramDefaultJSONParser.getSymbolTable());
          localJSONLexer.skipWhitespace();
          char c = localJSONLexer.getCurrent();
          if (c != ':')
            throw new JSONException("expect ':' at " + localJSONLexer.pos() + ", actual " + c);
        }
        localJSONLexer.next();
        localJSONLexer.skipWhitespace();
        localJSONLexer.getCurrent();
        localJSONLexer.resetStringPosition();
        if (str != JSON.DEFAULT_TYPE_KEY)
          break label603;
        localClass = TypeUtils.loadClass(localJSONLexer.scanSymbol(paramDefaultJSONParser.getSymbolTable(), '"'));
        if (localClass != paramMap.getClass())
          break;
        localJSONLexer.nextToken(16);
      }
      while (localJSONLexer.token() != 13);
      localJSONLexer.nextToken(16);
      paramDefaultJSONParser.setContext(localParseContext);
      return paramMap;
      ObjectDeserializer localObjectDeserializer = paramDefaultJSONParser.getConfig().getDeserializer(localClass);
      localJSONLexer.nextToken(16);
      paramDefaultJSONParser.setResolveStatus(2);
      if ((localParseContext != null) && (!(paramObject instanceof Integer)))
        paramDefaultJSONParser.popContext();
      Map localMap = (Map)localObjectDeserializer.deserialze(paramDefaultJSONParser, localClass, paramObject);
      paramDefaultJSONParser.setContext(localParseContext);
      return localMap;
      localJSONLexer.nextToken();
      Object localObject2;
      if (localJSONLexer.token() == 8)
      {
        localObject2 = null;
        localJSONLexer.nextToken();
      }
      while (true)
      {
        paramMap.put(str, localObject2);
        paramDefaultJSONParser.checkMapResolve(paramMap, str);
        paramDefaultJSONParser.setContext(localParseContext, localObject2, str);
        j = localJSONLexer.token();
        if ((j != 20) && (j != 15))
          break;
        paramDefaultJSONParser.setContext(localParseContext);
        return paramMap;
        localObject2 = paramDefaultJSONParser.parseObject(paramType);
      }
    }
    while (j != 13);
    localJSONLexer.nextToken();
    paramDefaultJSONParser.setContext(localParseContext);
    return paramMap;
  }

  protected Map<Object, Object> createMap(Type paramType)
  {
    if (paramType == Properties.class)
      return new Properties();
    if (paramType == Hashtable.class)
      return new Hashtable();
    if (paramType == IdentityHashMap.class)
      return new IdentityHashMap();
    if ((paramType == SortedMap.class) || (paramType == TreeMap.class))
      return new TreeMap();
    if ((paramType == ConcurrentMap.class) || (paramType == ConcurrentHashMap.class))
      return new ConcurrentHashMap();
    if ((paramType == Map.class) || (paramType == HashMap.class))
      return new HashMap();
    if (paramType == LinkedHashMap.class)
      return new LinkedHashMap();
    if ((paramType instanceof ParameterizedType))
      return createMap(((ParameterizedType)paramType).getRawType());
    Class localClass = (Class)paramType;
    if (localClass.isInterface())
      throw new JSONException("unsupport type " + paramType);
    try
    {
      Map localMap = (Map)localClass.newInstance();
      return localMap;
    }
    catch (Exception localException)
    {
      throw new JSONException("unsupport type " + paramType, localException);
    }
  }

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken(16);
      return null;
    }
    Map localMap = createMap(paramType);
    ParseContext localParseContext = paramDefaultJSONParser.getContext();
    try
    {
      paramDefaultJSONParser.setContext(localParseContext, localMap, paramObject);
      Object localObject2 = deserialze(paramDefaultJSONParser, paramType, paramObject, localMap);
      return localObject2;
    }
    finally
    {
      paramDefaultJSONParser.setContext(localParseContext);
    }
  }

  protected Object deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject, Map paramMap)
  {
    if ((paramType instanceof ParameterizedType))
    {
      ParameterizedType localParameterizedType = (ParameterizedType)paramType;
      Type localType1 = localParameterizedType.getActualTypeArguments()[0];
      Type localType2 = localParameterizedType.getActualTypeArguments()[1];
      if (String.class == localType1)
        return parseMap(paramDefaultJSONParser, paramMap, localType2, paramObject);
      return parseMap(paramDefaultJSONParser, paramMap, localType1, localType2, paramObject);
    }
    return paramDefaultJSONParser.parseObject(paramMap, paramObject);
  }

  public int getFastMatchToken()
  {
    return 12;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.MapDeserializer
 * JD-Core Version:    0.6.2
 */