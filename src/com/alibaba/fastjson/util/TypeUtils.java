package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import java.beans.Introspector;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TypeUtils
{
  public static boolean compatibleWithJavaBean = false;
  private static ConcurrentMap<String, Class<?>> mappings = new ConcurrentHashMap();

  static
  {
    addBaseClassMappings();
  }

  public static void addBaseClassMappings()
  {
    mappings.put("byte", Byte.TYPE);
    mappings.put("short", Short.TYPE);
    mappings.put("int", Integer.TYPE);
    mappings.put("long", Long.TYPE);
    mappings.put("float", Float.TYPE);
    mappings.put("double", Double.TYPE);
    mappings.put("boolean", Boolean.TYPE);
    mappings.put("char", Character.TYPE);
    mappings.put("[byte", [B.class);
    mappings.put("[short", [S.class);
    mappings.put("[int", [I.class);
    mappings.put("[long", [J.class);
    mappings.put("[float", [F.class);
    mappings.put("[double", [D.class);
    mappings.put("[boolean", [Z.class);
    mappings.put("[char", [C.class);
    mappings.put(HashMap.class.getName(), HashMap.class);
  }

  public static void addClassMapping(String paramString, Class<?> paramClass)
  {
    if (paramString == null)
      paramString = paramClass.getName();
    mappings.put(paramString, paramClass);
  }

  public static final <T> T cast(Object paramObject, Class<T> paramClass, ParserConfig paramParserConfig)
  {
    if (paramObject == null)
      paramObject = null;
    do
    {
      Map localMap;
      do
      {
        do
        {
          do
          {
            return paramObject;
            if (paramClass == null)
              throw new IllegalArgumentException("clazz is null");
          }
          while (paramClass == paramObject.getClass());
          if (!(paramObject instanceof Map))
            break;
        }
        while (paramClass == Map.class);
        localMap = (Map)paramObject;
      }
      while ((paramClass == Object.class) && (!localMap.containsKey(JSON.DEFAULT_TYPE_KEY)));
      return castToJavaBean((Map)paramObject, paramClass, paramParserConfig);
      if ((paramClass.isArray()) && ((paramObject instanceof Collection)))
      {
        Collection localCollection = (Collection)paramObject;
        int i = 0;
        Object localObject = Array.newInstance(paramClass.getComponentType(), localCollection.size());
        Iterator localIterator = localCollection.iterator();
        while (localIterator.hasNext())
        {
          Array.set(localObject, i, cast(localIterator.next(), paramClass.getComponentType(), paramParserConfig));
          i++;
        }
        return localObject;
      }
    }
    while (paramClass.isAssignableFrom(paramObject.getClass()));
    if ((paramClass == Boolean.TYPE) || (paramClass == Boolean.class))
      return castToBoolean(paramObject);
    if ((paramClass == Byte.TYPE) || (paramClass == Byte.class))
      return castToByte(paramObject);
    if ((paramClass == Short.TYPE) || (paramClass == Short.class))
      return castToShort(paramObject);
    if ((paramClass == Integer.TYPE) || (paramClass == Integer.class))
      return castToInt(paramObject);
    if ((paramClass == Long.TYPE) || (paramClass == Long.class))
      return castToLong(paramObject);
    if ((paramClass == Float.TYPE) || (paramClass == Float.class))
      return castToFloat(paramObject);
    if ((paramClass == Double.TYPE) || (paramClass == Double.class))
      return castToDouble(paramObject);
    if (paramClass == String.class)
      return castToString(paramObject);
    if (paramClass == BigDecimal.class)
      return castToBigDecimal(paramObject);
    if (paramClass == BigInteger.class)
      return castToBigInteger(paramObject);
    if (paramClass == java.util.Date.class)
      return castToDate(paramObject);
    if (paramClass == java.sql.Date.class)
      return castToSqlDate(paramObject);
    if (paramClass == Timestamp.class)
      return castToTimestamp(paramObject);
    if (paramClass.isEnum())
      return castToEnum(paramObject, paramClass, paramParserConfig);
    if (Calendar.class.isAssignableFrom(paramClass))
    {
      java.util.Date localDate = castToDate(paramObject);
      Calendar localCalendar;
      if (paramClass == Calendar.class)
        localCalendar = Calendar.getInstance();
      while (true)
      {
        localCalendar.setTime(localDate);
        return localCalendar;
        try
        {
          localCalendar = (Calendar)paramClass.newInstance();
        }
        catch (Exception localException)
        {
          throw new JSONException("can not cast to : " + paramClass.getName(), localException);
        }
      }
    }
    if (((paramObject instanceof String)) && (((String)paramObject).length() == 0))
      return null;
    throw new JSONException("can not cast to : " + paramClass.getName());
  }

  public static final <T> T cast(Object paramObject, ParameterizedType paramParameterizedType, ParserConfig paramParserConfig)
  {
    Type localType1 = paramParameterizedType.getRawType();
    Object localObject;
    if ((localType1 == List.class) || (localType1 == ArrayList.class))
    {
      Type localType2 = paramParameterizedType.getActualTypeArguments()[0];
      if ((paramObject instanceof Iterable))
      {
        localObject = new ArrayList();
        Iterator localIterator1 = ((Iterable)paramObject).iterator();
        while (localIterator1.hasNext())
          ((List)localObject).add(cast(localIterator1.next(), localType2, paramParserConfig));
      }
    }
    if ((localType1 == Map.class) || (localType1 == HashMap.class))
    {
      Type localType3 = paramParameterizedType.getActualTypeArguments()[0];
      Type localType4 = paramParameterizedType.getActualTypeArguments()[1];
      if ((paramObject instanceof Map))
      {
        HashMap localHashMap = new HashMap();
        Iterator localIterator2 = ((Map)paramObject).entrySet().iterator();
        while (localIterator2.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator2.next();
          localHashMap.put(cast(localEntry.getKey(), localType3, paramParserConfig), cast(localEntry.getValue(), localType4, paramParserConfig));
        }
        localObject = localHashMap;
        return localObject;
      }
    }
    if (((paramObject instanceof String)) && (((String)paramObject).length() == 0))
      return null;
    if ((paramParameterizedType.getActualTypeArguments().length == 1) && ((paramParameterizedType.getActualTypeArguments()[0] instanceof WildcardType)))
      return cast(paramObject, localType1, paramParserConfig);
    throw new JSONException("can not cast to : " + paramParameterizedType);
  }

  public static final <T> T cast(Object paramObject, Type paramType, ParserConfig paramParserConfig)
  {
    if (paramObject == null)
      paramObject = null;
    do
    {
      return paramObject;
      if ((paramType instanceof Class))
        return cast(paramObject, (Class)paramType, paramParserConfig);
      if ((paramType instanceof ParameterizedType))
        return cast(paramObject, (ParameterizedType)paramType, paramParserConfig);
      if (((paramObject instanceof String)) && (((String)paramObject).length() == 0))
        return null;
    }
    while ((paramType instanceof TypeVariable));
    throw new JSONException("can not cast to : " + paramType);
  }

  public static final BigDecimal castToBigDecimal(Object paramObject)
  {
    if (paramObject == null)
      return null;
    if ((paramObject instanceof BigDecimal))
      return (BigDecimal)paramObject;
    if ((paramObject instanceof BigInteger))
      return new BigDecimal((BigInteger)paramObject);
    String str = paramObject.toString();
    if (str.length() == 0)
      return null;
    return new BigDecimal(str);
  }

  public static final BigInteger castToBigInteger(Object paramObject)
  {
    if (paramObject == null)
      return null;
    if ((paramObject instanceof BigInteger))
      return (BigInteger)paramObject;
    if (((paramObject instanceof Float)) || ((paramObject instanceof Double)))
      return BigInteger.valueOf(((Number)paramObject).longValue());
    String str = paramObject.toString();
    if (str.length() == 0)
      return null;
    return new BigInteger(str);
  }

  public static final Boolean castToBoolean(Object paramObject)
  {
    int i = 1;
    if (paramObject == null)
      return null;
    if ((paramObject instanceof Boolean))
      return (Boolean)paramObject;
    if ((paramObject instanceof Number))
    {
      if (((Number)paramObject).intValue() == i);
      while (true)
      {
        return Boolean.valueOf(i);
        int j = 0;
      }
    }
    if ((paramObject instanceof String))
    {
      String str = (String)paramObject;
      if (str.length() == 0)
        return null;
      if ("true".equals(str))
        return Boolean.TRUE;
      if ("false".equals(str))
        return Boolean.FALSE;
      if ("1".equals(str))
        return Boolean.TRUE;
    }
    throw new JSONException("can not cast to int, value : " + paramObject);
  }

  public static final Byte castToByte(Object paramObject)
  {
    if (paramObject == null);
    String str;
    do
    {
      return null;
      if ((paramObject instanceof Number))
        return Byte.valueOf(((Number)paramObject).byteValue());
      if (!(paramObject instanceof String))
        break;
      str = (String)paramObject;
    }
    while (str.length() == 0);
    return Byte.valueOf(Byte.parseByte(str));
    throw new JSONException("can not cast to byte, value : " + paramObject);
  }

  public static final byte[] castToBytes(Object paramObject)
  {
    if ((paramObject instanceof byte[]))
      return (byte[])paramObject;
    if ((paramObject instanceof String))
      return Base64.decodeFast((String)paramObject);
    throw new JSONException("can not cast to int, value : " + paramObject);
  }

  public static final Character castToChar(Object paramObject)
  {
    if (paramObject == null)
      return null;
    if ((paramObject instanceof Character))
      return (Character)paramObject;
    if ((paramObject instanceof String))
    {
      String str = (String)paramObject;
      if (str.length() == 0)
        return null;
      if (str.length() != 1)
        throw new JSONException("can not cast to byte, value : " + paramObject);
      return Character.valueOf(str.charAt(0));
    }
    throw new JSONException("can not cast to byte, value : " + paramObject);
  }

  public static final java.util.Date castToDate(Object paramObject)
  {
    if (paramObject == null)
      return null;
    if ((paramObject instanceof Calendar))
      return ((Calendar)paramObject).getTime();
    if ((paramObject instanceof java.util.Date))
      return (java.util.Date)paramObject;
    long l = -1L;
    if ((paramObject instanceof Number))
      l = ((Number)paramObject).longValue();
    if ((paramObject instanceof String))
    {
      String str1 = (String)paramObject;
      if (str1.indexOf('-') != -1)
      {
        String str2;
        if (str1.length() == JSON.DEFFAULT_DATE_FORMAT.length())
          str2 = JSON.DEFFAULT_DATE_FORMAT;
        while (true)
        {
          SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(str2);
          try
          {
            java.util.Date localDate = localSimpleDateFormat.parse(str1);
            return localDate;
            if (str1.length() == 10)
              str2 = "yyyy-MM-dd";
            else if (str1.length() == "yyyy-MM-dd HH:mm:ss".length())
              str2 = "yyyy-MM-dd HH:mm:ss";
            else
              str2 = "yyyy-MM-dd HH:mm:ss.SSS";
          }
          catch (ParseException localParseException)
          {
            throw new JSONException("can not cast to Date, value : " + str1);
          }
        }
      }
      if (str1.length() == 0)
        return null;
      l = Long.parseLong(str1);
    }
    if (l < 0L)
      throw new JSONException("can not cast to Date, value : " + paramObject);
    return new java.util.Date(l);
  }

  public static final Double castToDouble(Object paramObject)
  {
    if (paramObject == null);
    String str;
    do
    {
      return null;
      if ((paramObject instanceof Number))
        return Double.valueOf(((Number)paramObject).doubleValue());
      if (!(paramObject instanceof String))
        break;
      str = paramObject.toString();
    }
    while (str.length() == 0);
    return Double.valueOf(Double.parseDouble(str));
    throw new JSONException("can not cast to double, value : " + paramObject);
  }

  public static final <T> T castToEnum(Object paramObject, Class<T> paramClass, ParserConfig paramParserConfig)
  {
    Enum localEnum;
    try
    {
      if ((paramObject instanceof String))
      {
        String str = (String)paramObject;
        if (str.length() == 0)
          return null;
        return Enum.valueOf(paramClass, str);
      }
      if ((paramObject instanceof Number))
      {
        int i = ((Number)paramObject).intValue();
        Object[] arrayOfObject = (Object[])paramClass.getMethod("values", new Class[0]).invoke(null, new Object[0]);
        int j = arrayOfObject.length;
        for (int k = 0; k < j; k++)
        {
          localEnum = (Enum)arrayOfObject[k];
          int m = localEnum.ordinal();
          if (m == i)
            break label182;
        }
      }
    }
    catch (Exception localException)
    {
      throw new JSONException("can not cast to : " + paramClass.getName(), localException);
    }
    throw new JSONException("can not cast to : " + paramClass.getName());
    label182: return localEnum;
  }

  public static final Float castToFloat(Object paramObject)
  {
    if (paramObject == null);
    String str;
    do
    {
      return null;
      if ((paramObject instanceof Number))
        return Float.valueOf(((Number)paramObject).floatValue());
      if (!(paramObject instanceof String))
        break;
      str = paramObject.toString();
    }
    while (str.length() == 0);
    return Float.valueOf(Float.parseFloat(str));
    throw new JSONException("can not cast to float, value : " + paramObject);
  }

  public static final Integer castToInt(Object paramObject)
  {
    if (paramObject == null)
      return null;
    if ((paramObject instanceof Integer))
      return (Integer)paramObject;
    if ((paramObject instanceof Number))
      return Integer.valueOf(((Number)paramObject).intValue());
    if ((paramObject instanceof String))
    {
      String str = (String)paramObject;
      if (str.length() == 0)
        return null;
      return Integer.valueOf(Integer.parseInt(str));
    }
    throw new JSONException("can not cast to int, value : " + paramObject);
  }

  public static final <T> T castToJavaBean(Object paramObject, Class<T> paramClass)
  {
    return cast(paramObject, paramClass, ParserConfig.getGlobalInstance());
  }

  public static final <T> T castToJavaBean(Map<String, Object> paramMap, Class<T> paramClass, ParserConfig paramParserConfig)
  {
    if (paramClass == StackTraceElement.class);
    Class localClass;
    try
    {
      String str1 = (String)paramMap.get("className");
      String str2 = (String)paramMap.get("methodName");
      String str3 = (String)paramMap.get("fileName");
      Number localNumber = (Number)paramMap.get("lineNumber");
      if (localNumber == null);
      for (int i = 0; ; i = localNumber.intValue())
      {
        StackTraceElement localStackTraceElement = new StackTraceElement(str1, str2, str3, i);
        return localStackTraceElement;
      }
      Object localObject1 = paramMap.get(JSON.DEFAULT_TYPE_KEY);
      if (!(localObject1 instanceof String))
        break label203;
      String str5 = (String)localObject1;
      localClass = loadClass(str5);
      if (localClass == null)
        throw new ClassNotFoundException(str5 + " not found");
    }
    catch (Exception localException)
    {
      JSONException localJSONException = new JSONException(localException.getMessage(), localException);
      throw localJSONException;
    }
    if (!localClass.equals(paramClass))
      return castToJavaBean(paramMap, localClass, paramParserConfig);
    label203: if (paramClass.isInterface())
    {
      if ((paramMap instanceof JSONObject));
      for (JSONObject localJSONObject = (JSONObject)paramMap; ; localJSONObject = new JSONObject(paramMap))
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { paramClass }, localJSONObject);
    }
    if (paramParserConfig == null)
      paramParserConfig = ParserConfig.getGlobalInstance();
    Map localMap = paramParserConfig.getFieldDeserializers(paramClass);
    Constructor localConstructor = paramClass.getDeclaredConstructor(new Class[0]);
    if (!localConstructor.isAccessible())
      localConstructor.setAccessible(true);
    Object localObject2 = localConstructor.newInstance(new Object[0]);
    Iterator localIterator = localMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str4 = (String)localEntry.getKey();
      FieldDeserializer localFieldDeserializer = (FieldDeserializer)localEntry.getValue();
      if (paramMap.containsKey(str4))
      {
        Object localObject3 = paramMap.get(str4);
        Method localMethod = localFieldDeserializer.getMethod();
        if (localMethod != null)
        {
          localMethod.invoke(localObject2, new Object[] { cast(localObject3, localMethod.getGenericParameterTypes()[0], paramParserConfig) });
        }
        else
        {
          Field localField = localFieldDeserializer.getField();
          localField.set(localObject2, cast(localObject3, localField.getGenericType(), paramParserConfig));
        }
      }
    }
    return localObject2;
  }

  public static final Long castToLong(Object paramObject)
  {
    if (paramObject == null);
    String str;
    do
    {
      return null;
      if ((paramObject instanceof Number))
        return Long.valueOf(((Number)paramObject).longValue());
      if (!(paramObject instanceof String))
        break;
      str = (String)paramObject;
    }
    while (str.length() == 0);
    try
    {
      Long localLong = Long.valueOf(Long.parseLong(str));
      return localLong;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      JSONScanner localJSONScanner = new JSONScanner(str);
      boolean bool = localJSONScanner.scanISO8601DateIfMatch(false);
      Calendar localCalendar = null;
      if (bool)
        localCalendar = localJSONScanner.getCalendar();
      localJSONScanner.close();
      if (localCalendar != null)
        return Long.valueOf(localCalendar.getTimeInMillis());
    }
    throw new JSONException("can not cast to long, value : " + paramObject);
  }

  public static final Short castToShort(Object paramObject)
  {
    if (paramObject == null);
    String str;
    do
    {
      return null;
      if ((paramObject instanceof Number))
        return Short.valueOf(((Number)paramObject).shortValue());
      if (!(paramObject instanceof String))
        break;
      str = (String)paramObject;
    }
    while (str.length() == 0);
    return Short.valueOf(Short.parseShort(str));
    throw new JSONException("can not cast to short, value : " + paramObject);
  }

  public static final java.sql.Date castToSqlDate(Object paramObject)
  {
    if (paramObject == null)
      return null;
    if ((paramObject instanceof Calendar))
      return new java.sql.Date(((Calendar)paramObject).getTimeInMillis());
    if ((paramObject instanceof java.sql.Date))
      return (java.sql.Date)paramObject;
    if ((paramObject instanceof java.util.Date))
      return new java.sql.Date(((java.util.Date)paramObject).getTime());
    long l = 0L;
    if ((paramObject instanceof Number))
      l = ((Number)paramObject).longValue();
    if ((paramObject instanceof String))
    {
      String str = (String)paramObject;
      if (str.length() == 0)
        return null;
      l = Long.parseLong(str);
    }
    if (l <= 0L)
      throw new JSONException("can not cast to Date, value : " + paramObject);
    return new java.sql.Date(l);
  }

  public static final String castToString(Object paramObject)
  {
    if (paramObject == null)
      return null;
    return paramObject.toString();
  }

  public static final Timestamp castToTimestamp(Object paramObject)
  {
    if (paramObject == null)
      return null;
    if ((paramObject instanceof Calendar))
      return new Timestamp(((Calendar)paramObject).getTimeInMillis());
    if ((paramObject instanceof Timestamp))
      return (Timestamp)paramObject;
    if ((paramObject instanceof java.util.Date))
      return new Timestamp(((java.util.Date)paramObject).getTime());
    long l = 0L;
    if ((paramObject instanceof Number))
      l = ((Number)paramObject).longValue();
    if ((paramObject instanceof String))
    {
      String str = (String)paramObject;
      if (str.length() == 0)
        return null;
      l = Long.parseLong(str);
    }
    if (l <= 0L)
      throw new JSONException("can not cast to Date, value : " + paramObject);
    return new Timestamp(l);
  }

  public static void clearClassMapping()
  {
    mappings.clear();
    addBaseClassMappings();
  }

  public static List<FieldInfo> computeGetters(Class<?> paramClass, Map<String, String> paramMap)
  {
    return computeGetters(paramClass, paramMap, true);
  }

  public static List<FieldInfo> computeGetters(Class<?> paramClass, Map<String, String> paramMap, boolean paramBoolean)
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    Method[] arrayOfMethod = paramClass.getMethods();
    int i = arrayOfMethod.length;
    int j = 0;
    if (j < i)
    {
      Method localMethod = arrayOfMethod[j];
      String str2 = localMethod.getName();
      if (Modifier.isStatic(localMethod.getModifiers()));
      label238: char c2;
      String str4;
      label302: label444: 
      do
      {
        Field localField3;
        do
        {
          do
          {
            JSONField localJSONField4;
            do
            {
              do
              {
                while (true)
                {
                  j++;
                  break;
                  if ((!localMethod.getReturnType().equals(Void.TYPE)) && (localMethod.getParameterTypes().length == 0) && (localMethod.getReturnType() != ClassLoader.class) && ((!localMethod.getName().equals("getMetaClass")) || (!localMethod.getReturnType().getName().equals("groovy.lang.MetaClass"))))
                  {
                    JSONField localJSONField2 = (JSONField)localMethod.getAnnotation(JSONField.class);
                    if (localJSONField2 == null)
                      localJSONField2 = getSupperMethodAnnotation(paramClass, localMethod);
                    if (localJSONField2 == null)
                      break label238;
                    if (localJSONField2.serialize())
                    {
                      if (localJSONField2.name().length() == 0)
                        break label238;
                      String str5 = localJSONField2.name();
                      if (paramMap != null)
                      {
                        str5 = (String)paramMap.get(str5);
                        if (str5 == null);
                      }
                      else
                      {
                        FieldInfo localFieldInfo4 = new FieldInfo(str5, localMethod, null);
                        localLinkedHashMap.put(str5, localFieldInfo4);
                      }
                    }
                  }
                }
                if (!str2.startsWith("get"))
                  break label444;
              }
              while ((str2.length() < 4) || (str2.equals("getClass")));
              c2 = str2.charAt(3);
              if (!Character.isUpperCase(c2))
                break label682;
              if (!compatibleWithJavaBean)
                break label646;
              str4 = Introspector.decapitalize(str2.substring(3));
              if (isJSONTypeIgnore(paramClass, str4))
                break label716;
              localField3 = ParserConfig.getField(paramClass, str4);
              if (localField3 == null)
                break label396;
              localJSONField4 = (JSONField)localField3.getAnnotation(JSONField.class);
              if (localJSONField4 == null)
                break label396;
            }
            while (!localJSONField4.serialize());
            if (localJSONField4.name().length() == 0)
              break label396;
            str4 = localJSONField4.name();
            if (paramMap == null)
              break label396;
            str4 = (String)paramMap.get(str4);
          }
          while (str4 == null);
          if (paramMap == null)
            break label418;
          str4 = (String)paramMap.get(str4);
        }
        while (str4 == null);
        FieldInfo localFieldInfo3 = new FieldInfo(str4, localMethod, localField3);
        localLinkedHashMap.put(str4, localFieldInfo3);
      }
      while ((!str2.startsWith("is")) || (str2.length() < 3));
      label396: label418: char c1 = str2.charAt(2);
      String str3;
      if (Character.isUpperCase(c1))
        if (compatibleWithJavaBean)
          str3 = Introspector.decapitalize(str2.substring(2));
      while (true)
      {
        Field localField2 = ParserConfig.getField(paramClass, str3);
        if (localField2 == null)
          localField2 = ParserConfig.getField(paramClass, str2);
        if (localField2 != null)
        {
          JSONField localJSONField3 = (JSONField)localField2.getAnnotation(JSONField.class);
          if (localJSONField3 != null)
          {
            if (!localJSONField3.serialize())
              break;
            if (localJSONField3.name().length() != 0)
            {
              str3 = localJSONField3.name();
              if (paramMap != null)
              {
                str3 = (String)paramMap.get(str3);
                if (str3 == null)
                  break;
              }
            }
          }
        }
        if (paramMap != null)
        {
          str3 = (String)paramMap.get(str3);
          if (str3 == null)
            break;
        }
        FieldInfo localFieldInfo2 = new FieldInfo(str3, localMethod, localField2);
        localLinkedHashMap.put(str3, localFieldInfo2);
        break;
        label646: str4 = Character.toLowerCase(str2.charAt(3)) + str2.substring(4);
        break label302;
        label682: if (c2 == '_')
        {
          str4 = str2.substring(4);
          break label302;
        }
        if (c2 != 'f')
          break;
        str4 = str2.substring(3);
        break label302;
        label716: break;
        str3 = Character.toLowerCase(str2.charAt(2)) + str2.substring(3);
        continue;
        if (c1 == '_')
        {
          str3 = str2.substring(3);
        }
        else
        {
          if (c1 != 'f')
            break;
          str3 = str2.substring(2);
        }
      }
    }
    Field[] arrayOfField = paramClass.getFields();
    int k = arrayOfField.length;
    int m = 0;
    if (m < k)
    {
      Field localField1 = arrayOfField[m];
      if (Modifier.isStatic(localField1.getModifiers()));
      while (true)
      {
        m++;
        break;
        JSONField localJSONField1 = (JSONField)localField1.getAnnotation(JSONField.class);
        String str1 = localField1.getName();
        if (localJSONField1 != null)
        {
          if (localJSONField1.serialize())
            if (localJSONField1.name().length() != 0)
              str1 = localJSONField1.name();
        }
        else if (paramMap != null)
        {
          str1 = (String)paramMap.get(str1);
          if (str1 == null);
        }
        else if (!localLinkedHashMap.containsKey(str1))
        {
          FieldInfo localFieldInfo1 = new FieldInfo(str1, null, localField1);
          localLinkedHashMap.put(str1, localFieldInfo1);
        }
      }
    }
    ArrayList localArrayList = new ArrayList();
    JSONType localJSONType = (JSONType)paramClass.getAnnotation(JSONType.class);
    int n = 0;
    String[] arrayOfString1 = null;
    int i4;
    if (localJSONType != null)
    {
      arrayOfString1 = localJSONType.orders();
      if ((arrayOfString1 == null) || (arrayOfString1.length != localLinkedHashMap.size()))
        break label1108;
      n = 1;
      String[] arrayOfString3 = arrayOfString1;
      int i3 = arrayOfString3.length;
      i4 = 0;
      if (i4 < i3)
        if (localLinkedHashMap.containsKey(arrayOfString3[i4]))
          break label1102;
    }
    label1102: label1108: for (n = 0; ; n = 0)
    {
      if (n == 0)
        break label1114;
      String[] arrayOfString2 = arrayOfString1;
      int i1 = arrayOfString2.length;
      for (int i2 = 0; i2 < i1; i2++)
        localArrayList.add((FieldInfo)localLinkedHashMap.get(arrayOfString2[i2]));
      i4++;
      break;
    }
    label1114: Iterator localIterator = localLinkedHashMap.values().iterator();
    while (localIterator.hasNext())
      localArrayList.add((FieldInfo)localIterator.next());
    if (paramBoolean)
      Collections.sort(localArrayList);
    return localArrayList;
  }

  public static Class<?> getClass(Type paramType)
  {
    if (paramType.getClass() == Class.class)
      return (Class)paramType;
    if ((paramType instanceof ParameterizedType))
      return getClass(((ParameterizedType)paramType).getRawType());
    return Object.class;
  }

  public static JSONField getSupperMethodAnnotation(Class<?> paramClass, Method paramMethod)
  {
    Class[] arrayOfClass = paramClass.getInterfaces();
    int i = arrayOfClass.length;
    for (int j = 0; j < i; j++)
    {
      Method[] arrayOfMethod = arrayOfClass[j].getMethods();
      int k = arrayOfMethod.length;
      int m = 0;
      if (m < k)
      {
        Method localMethod = arrayOfMethod[m];
        if (!localMethod.getName().equals(paramMethod.getName()));
        while (localMethod.getParameterTypes().length != paramMethod.getParameterTypes().length)
        {
          m++;
          break;
        }
        int n = 1;
        for (int i1 = 0; ; i1++)
          if (i1 < localMethod.getParameterTypes().length)
          {
            if (!localMethod.getParameterTypes()[i1].equals(paramMethod.getParameterTypes()[i1]))
              n = 0;
          }
          else
          {
            if (n == 0)
              break;
            JSONField localJSONField = (JSONField)localMethod.getAnnotation(JSONField.class);
            if (localJSONField == null)
              break;
            return localJSONField;
          }
      }
    }
    return null;
  }

  private static boolean isJSONTypeIgnore(Class<?> paramClass, String paramString)
  {
    JSONType localJSONType = (JSONType)paramClass.getAnnotation(JSONType.class);
    int j;
    if ((localJSONType != null) && (localJSONType.ignores() != null))
    {
      String[] arrayOfString = localJSONType.ignores();
      int i = arrayOfString.length;
      j = 0;
      if (j < i)
        if (!paramString.equalsIgnoreCase(arrayOfString[j]));
    }
    while ((paramClass.getSuperclass() != Object.class) && (paramClass.getSuperclass() != null) && (isJSONTypeIgnore(paramClass.getSuperclass(), paramString)))
    {
      return true;
      j++;
      break;
    }
    return false;
  }

  public static Class<?> loadClass(String paramString)
  {
    Object localObject;
    if ((paramString == null) || (paramString.length() == 0))
      localObject = null;
    do
    {
      return localObject;
      localObject = (Class)mappings.get(paramString);
    }
    while (localObject != null);
    if (paramString.charAt(0) == '[')
      return Array.newInstance(loadClass(paramString.substring(1)), 0).getClass();
    if ((paramString.startsWith("L")) && (paramString.endsWith(";")))
      return loadClass(paramString.substring(1, -1 + paramString.length()));
    try
    {
      ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
      if (localClassLoader != null)
      {
        localObject = localClassLoader.loadClass(paramString);
        addClassMapping(paramString, (Class)localObject);
        return localObject;
      }
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        localObject = Class.forName(paramString);
        addClassMapping(paramString, (Class)localObject);
        return localObject;
      }
      catch (Throwable localThrowable2)
      {
      }
    }
    return localObject;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.util.TypeUtils
 * JD-Core Version:    0.6.2
 */