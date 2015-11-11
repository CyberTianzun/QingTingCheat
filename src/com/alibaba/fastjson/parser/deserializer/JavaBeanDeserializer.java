package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.DefaultJSONParser.ResolveTask;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.FilterUtils;
import com.alibaba.fastjson.util.DeserializeBeanInfo;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class JavaBeanDeserializer
  implements ObjectDeserializer
{
  private DeserializeBeanInfo beanInfo;
  private final Class<?> clazz;
  private final Map<String, FieldDeserializer> feildDeserializerMap = new IdentityHashMap();
  private final List<FieldDeserializer> fieldDeserializers = new ArrayList();
  private final List<FieldDeserializer> sortedFieldDeserializers = new ArrayList();

  public JavaBeanDeserializer(ParserConfig paramParserConfig, Class<?> paramClass)
  {
    this(paramParserConfig, paramClass, paramClass);
  }

  public JavaBeanDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, Type paramType)
  {
    this.clazz = paramClass;
    this.beanInfo = DeserializeBeanInfo.computeSetters(paramClass, paramType);
    Iterator localIterator1 = this.beanInfo.getFieldList().iterator();
    while (localIterator1.hasNext())
      addFieldDeserializer(paramParserConfig, paramClass, (FieldInfo)localIterator1.next());
    Iterator localIterator2 = this.beanInfo.getSortedFieldList().iterator();
    while (localIterator2.hasNext())
    {
      FieldInfo localFieldInfo = (FieldInfo)localIterator2.next();
      FieldDeserializer localFieldDeserializer = (FieldDeserializer)this.feildDeserializerMap.get(localFieldInfo.getName().intern());
      this.sortedFieldDeserializers.add(localFieldDeserializer);
    }
  }

  private void addFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    String str = paramFieldInfo.getName().intern();
    FieldDeserializer localFieldDeserializer = createFieldDeserializer(paramParserConfig, paramClass, paramFieldInfo);
    this.feildDeserializerMap.put(str, localFieldDeserializer);
    this.fieldDeserializers.add(localFieldDeserializer);
  }

  public FieldDeserializer createFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    return paramParserConfig.createFieldDeserializer(paramParserConfig, paramClass, paramFieldInfo);
  }

  public Object createInstance(DefaultJSONParser paramDefaultJSONParser, Type paramType)
  {
    if (((paramType instanceof Class)) && (this.clazz.isInterface()))
    {
      Class localClass = (Class)paramType;
      ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
      JSONObject localJSONObject = new JSONObject();
      return Proxy.newProxyInstance(localClassLoader, new Class[] { localClass }, localJSONObject);
    }
    if (this.beanInfo.getDefaultConstructor() == null)
      return null;
    Object localObject2;
    try
    {
      Constructor localConstructor = this.beanInfo.getDefaultConstructor();
      Object localObject3;
      if (localConstructor.getParameterTypes().length == 0)
        localObject3 = localConstructor.newInstance(new Object[0]);
      Object localObject1;
      for (localObject2 = localObject3; paramDefaultJSONParser.isEnabled(Feature.InitStringFieldAsEmpty); localObject2 = localObject1)
      {
        Iterator localIterator = this.beanInfo.getFieldList().iterator();
        while (true)
        {
          if (!localIterator.hasNext())
            break label275;
          FieldInfo localFieldInfo = (FieldInfo)localIterator.next();
          if (localFieldInfo.getFieldClass() == String.class)
            try
            {
              localFieldInfo.set(localObject2, "");
            }
            catch (Exception localException2)
            {
              throw new JSONException("create instance error, class " + this.clazz.getName(), localException2);
            }
        }
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramDefaultJSONParser.getContext().getObject();
        localObject1 = localConstructor.newInstance(arrayOfObject);
      }
    }
    catch (Exception localException1)
    {
      throw new JSONException("create instance error, class " + this.clazz.getName(), localException1);
    }
    label275: return localObject2;
  }

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    return deserialze(paramDefaultJSONParser, paramType, paramObject, null);
  }

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject1, Object paramObject2)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken(16);
      return null;
    }
    ParseContext localParseContext1 = paramDefaultJSONParser.getContext();
    if (paramObject2 != null)
      localParseContext1 = localParseContext1.getParentContext();
    Object localObject1 = null;
    try
    {
      if (localJSONLexer.token() == 13)
      {
        localJSONLexer.nextToken(16);
        localObject1 = null;
        if (paramObject2 == null)
        {
          Object localObject10 = createInstance(paramDefaultJSONParser, paramType);
          paramObject2 = localObject10;
        }
        if (0 != 0)
          null.setObject(paramObject2);
        paramDefaultJSONParser.setContext(localParseContext1);
        return paramObject2;
      }
      int i = localJSONLexer.token();
      localObject1 = null;
      if (i == 14)
      {
        boolean bool = localJSONLexer.isEnabled(Feature.SupportArrayToBean);
        localObject1 = null;
        if (bool)
        {
          Object localObject4 = deserialzeArrayMapping(paramDefaultJSONParser, paramType, paramObject1, paramObject2);
          if (0 != 0)
            null.setObject(paramObject2);
          paramDefaultJSONParser.setContext(localParseContext1);
          return localObject4;
        }
      }
      int j = localJSONLexer.token();
      localObject1 = null;
      if (j == 12)
        break label312;
      int k = localJSONLexer.token();
      localObject1 = null;
      if (k == 16)
        break label312;
      StringBuffer localStringBuffer = new StringBuffer().append("syntax error, expect {, actual ").append(localJSONLexer.tokenName()).append(", pos ").append(localJSONLexer.pos());
      if ((paramObject1 instanceof String))
        localStringBuffer.append(", fieldName ").append(paramObject1);
      throw new JSONException(localStringBuffer.toString());
    }
    finally
    {
    }
    if (localObject1 != null)
      ((ParseContext)localObject1).setObject(paramObject2);
    paramDefaultJSONParser.setContext(localParseContext1);
    throw localObject2;
    label312: int m = paramDefaultJSONParser.getResolveStatus();
    Object localObject5 = null;
    localObject1 = null;
    if (m == 2)
      paramDefaultJSONParser.setResolveStatus(0);
    label339: label1365: label1372: label1387: for (Object localObject6 = localObject5; ; localObject6 = localObject5)
    {
      try
      {
        String str1 = localJSONLexer.scanSymbol(paramDefaultJSONParser.getSymbolTable());
        if (str1 == null)
          if (localJSONLexer.token() == 13)
          {
            localJSONLexer.nextToken(16);
            localObject5 = localObject6;
          }
        while (true)
          label382: if (paramObject2 == null)
            if (localObject5 == null)
            {
              paramObject2 = createInstance(paramDefaultJSONParser, paramType);
              if (localObject1 == null)
              {
                ParseContext localParseContext4 = paramDefaultJSONParser.setContext(localParseContext1, paramObject2, paramObject1);
                localObject1 = localParseContext4;
              }
              if (localObject1 != null)
                ((ParseContext)localObject1).setObject(paramObject2);
              paramDefaultJSONParser.setContext(localParseContext1);
              return paramObject2;
              if ((localJSONLexer.token() == 16) && (paramDefaultJSONParser.isEnabled(Feature.AllowArbitraryCommas)))
                break;
              if ("$ref" == str1)
              {
                localJSONLexer.nextTokenWithColon(4);
                if (localJSONLexer.token() == 4)
                {
                  String str2 = localJSONLexer.stringVal();
                  if ("@".equals(str2))
                    paramObject2 = localParseContext1.getObject();
                  while (true)
                  {
                    localJSONLexer.nextToken(13);
                    if (localJSONLexer.token() == 13)
                      break;
                    throw new JSONException("illegal ref");
                    if ("..".equals(str2))
                    {
                      ParseContext localParseContext3 = localParseContext1.getParentContext();
                      if (localParseContext3.getObject() != null)
                      {
                        paramObject2 = localParseContext3.getObject();
                      }
                      else
                      {
                        paramDefaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(localParseContext3, str2));
                        paramDefaultJSONParser.setResolveStatus(1);
                      }
                    }
                    else if ("$".equals(str2))
                    {
                      for (ParseContext localParseContext2 = localParseContext1; localParseContext2.getParentContext() != null; localParseContext2 = localParseContext2.getParentContext());
                      if (localParseContext2.getObject() != null)
                      {
                        paramObject2 = localParseContext2.getObject();
                      }
                      else
                      {
                        paramDefaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(localParseContext2, str2));
                        paramDefaultJSONParser.setResolveStatus(1);
                      }
                    }
                    else
                    {
                      paramDefaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(localParseContext1, str2));
                      paramDefaultJSONParser.setResolveStatus(1);
                    }
                  }
                }
                throw new JSONException("illegal ref, " + JSONToken.name(localJSONLexer.token()));
                localJSONLexer.nextToken(16);
                paramDefaultJSONParser.setContext(localParseContext1, paramObject2, paramObject1);
                if (localObject1 != null)
                  ((ParseContext)localObject1).setObject(paramObject2);
                paramDefaultJSONParser.setContext(localParseContext1);
                return paramObject2;
              }
              if (JSON.DEFAULT_TYPE_KEY == str1)
              {
                localJSONLexer.nextTokenWithColon(4);
                if (localJSONLexer.token() == 4)
                {
                  String str3 = localJSONLexer.stringVal();
                  localJSONLexer.nextToken(16);
                  if (((paramType instanceof Class)) && (str3.equals(((Class)paramType).getName())))
                  {
                    if (localJSONLexer.token() != 13)
                      break;
                    localJSONLexer.nextToken();
                    localObject5 = localObject6;
                    continue;
                  }
                  Class localClass = TypeUtils.loadClass(str3);
                  Object localObject9 = paramDefaultJSONParser.getConfig().getDeserializer(localClass).deserialze(paramDefaultJSONParser, localClass, paramObject1);
                  if (localObject1 != null)
                    ((ParseContext)localObject1).setObject(paramObject2);
                  paramDefaultJSONParser.setContext(localParseContext1);
                  return localObject9;
                }
                throw new JSONException("syntax error");
              }
              if ((paramObject2 != null) || (localObject6 != null))
                break label1372;
              paramObject2 = createInstance(paramDefaultJSONParser, paramType);
              if (paramObject2 != null)
                break label1365;
              localObject5 = new HashMap(this.fieldDeserializers.size());
              localObject1 = paramDefaultJSONParser.setContext(localParseContext1, paramObject2, paramObject1);
            }
        while (true)
        {
          if (!parseField(paramDefaultJSONParser, str1, paramObject2, paramType, (Map)localObject5))
          {
            if (localJSONLexer.token() != 13)
              break;
            localJSONLexer.nextToken();
            break label382;
          }
          if (localJSONLexer.token() == 16)
          {
            localObject6 = localObject5;
            break label339;
          }
          if (localJSONLexer.token() == 13)
          {
            localJSONLexer.nextToken(16);
            break label382;
          }
          if ((localJSONLexer.token() != 18) && (localJSONLexer.token() != 1))
            break label1387;
          throw new JSONException("syntax error, unexpect token " + JSONToken.name(localJSONLexer.token()));
          List localList = this.beanInfo.getFieldList();
          int n = localList.size();
          Object[] arrayOfObject = new Object[n];
          for (int i1 = 0; i1 < n; i1++)
            arrayOfObject[i1] = ((Map)localObject5).get(((FieldInfo)localList.get(i1)).getName());
          Constructor localConstructor = this.beanInfo.getCreatorConstructor();
          if (localConstructor != null);
          while (true)
          {
            try
            {
              Object localObject8 = this.beanInfo.getCreatorConstructor().newInstance(arrayOfObject);
              paramObject2 = localObject8;
              if (localObject1 != null)
                ((ParseContext)localObject1).setObject(paramObject2);
              paramDefaultJSONParser.setContext(localParseContext1);
              return paramObject2;
            }
            catch (Exception localException2)
            {
              throw new JSONException("create instance error, " + this.beanInfo.getCreatorConstructor().toGenericString(), localException2);
            }
            Method localMethod = this.beanInfo.getFactoryMethod();
            if (localMethod != null)
              try
              {
                Object localObject7 = this.beanInfo.getFactoryMethod().invoke(null, arrayOfObject);
                paramObject2 = localObject7;
              }
              catch (Exception localException1)
              {
                throw new JSONException("create factory method error, " + this.beanInfo.getFactoryMethod().toString(), localException1);
              }
          }
          localObject5 = localObject6;
          break label975;
          localObject5 = localObject6;
        }
      }
      finally
      {
      }
      break;
    }
  }

  public <T> T deserialzeArrayMapping(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject1, Object paramObject2)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() != 14)
      throw new JSONException("error");
    Object localObject = createInstance(paramDefaultJSONParser, paramType);
    int i = this.sortedFieldDeserializers.size();
    int j = 0;
    if (j < i)
    {
      char c;
      label71: FieldDeserializer localFieldDeserializer;
      Class localClass;
      if (j == i - 1)
      {
        c = ']';
        localFieldDeserializer = (FieldDeserializer)this.sortedFieldDeserializers.get(j);
        localClass = localFieldDeserializer.getFieldClass();
        if (localClass != Integer.TYPE)
          break label131;
        localFieldDeserializer.setValue(localObject, localJSONLexer.scanInt(c));
      }
      label131: label284: 
      do
        while (true)
        {
          j++;
          break;
          c = ',';
          break label71;
          if (localClass == String.class)
          {
            localFieldDeserializer.setValue(localObject, localJSONLexer.scanString(c));
          }
          else if (localClass == Long.TYPE)
          {
            localFieldDeserializer.setValue(localObject, localJSONLexer.scanLong(c));
          }
          else if (localClass.isEnum())
          {
            localFieldDeserializer.setValue(localObject, localJSONLexer.scanEnum(localClass, paramDefaultJSONParser.getSymbolTable(), c));
          }
          else
          {
            localJSONLexer.nextToken(14);
            localFieldDeserializer.setValue(localObject, paramDefaultJSONParser.parseObject(localFieldDeserializer.getFieldType()));
            if (c != ']')
              break label284;
            if (localJSONLexer.token() != 15)
              throw new JSONException("syntax error");
            localJSONLexer.nextToken(16);
          }
        }
      while ((c != ',') || (localJSONLexer.token() == 16));
      throw new JSONException("syntax error");
    }
    localJSONLexer.nextToken(16);
    return localObject;
  }

  public Class<?> getClazz()
  {
    return this.clazz;
  }

  public int getFastMatchToken()
  {
    return 12;
  }

  public Map<String, FieldDeserializer> getFieldDeserializerMap()
  {
    return this.feildDeserializerMap;
  }

  void parseExtra(DefaultJSONParser paramDefaultJSONParser, Object paramObject, String paramString)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (!localJSONLexer.isEnabled(Feature.IgnoreNotMatch))
      throw new JSONException("setter not found, class " + this.clazz.getName() + ", property " + paramString);
    localJSONLexer.nextTokenWithColon();
    Type localType = FilterUtils.getExtratype(paramDefaultJSONParser, paramObject, paramString);
    if (localType == null);
    for (Object localObject = paramDefaultJSONParser.parse(); ; localObject = paramDefaultJSONParser.parseObject(localType))
    {
      FilterUtils.processExtra(paramDefaultJSONParser, paramObject, paramString, localObject);
      return;
    }
  }

  public boolean parseField(DefaultJSONParser paramDefaultJSONParser, String paramString, Object paramObject, Type paramType, Map<String, Object> paramMap)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    FieldDeserializer localFieldDeserializer = (FieldDeserializer)this.feildDeserializerMap.get(paramString);
    if (localFieldDeserializer == null)
    {
      Iterator localIterator = this.feildDeserializerMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (((String)localEntry.getKey()).equalsIgnoreCase(paramString))
          localFieldDeserializer = (FieldDeserializer)localEntry.getValue();
      }
    }
    if (localFieldDeserializer == null)
    {
      parseExtra(paramDefaultJSONParser, paramObject, paramString);
      return false;
    }
    localJSONLexer.nextTokenWithColon(localFieldDeserializer.getFastMatchToken());
    localFieldDeserializer.parseField(paramDefaultJSONParser, paramObject, paramType, paramMap);
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer
 * JD-Core Version:    0.6.2
 */