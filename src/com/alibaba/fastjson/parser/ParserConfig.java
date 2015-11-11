package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.asm.ASMException;
import com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory;
import com.alibaba.fastjson.parser.deserializer.ASMJavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ASMJavaBeanDeserializer.InnerJavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ArrayDeserializer;
import com.alibaba.fastjson.parser.deserializer.ArrayListTypeFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.AtomicIntegerArrayDeserializer;
import com.alibaba.fastjson.parser.deserializer.AtomicLongArrayDeserializer;
import com.alibaba.fastjson.parser.deserializer.AutowiredObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.BigDecimalDeserializer;
import com.alibaba.fastjson.parser.deserializer.BigIntegerDeserializer;
import com.alibaba.fastjson.parser.deserializer.BooleanDeserializer;
import com.alibaba.fastjson.parser.deserializer.BooleanFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.CalendarDeserializer;
import com.alibaba.fastjson.parser.deserializer.CharArrayDeserializer;
import com.alibaba.fastjson.parser.deserializer.CharacterDeserializer;
import com.alibaba.fastjson.parser.deserializer.CharsetDeserializer;
import com.alibaba.fastjson.parser.deserializer.ClassDerializer;
import com.alibaba.fastjson.parser.deserializer.CollectionDeserializer;
import com.alibaba.fastjson.parser.deserializer.ColorDeserializer;
import com.alibaba.fastjson.parser.deserializer.DateDeserializer;
import com.alibaba.fastjson.parser.deserializer.DateFormatDeserializer;
import com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FileDeserializer;
import com.alibaba.fastjson.parser.deserializer.FloatDeserializer;
import com.alibaba.fastjson.parser.deserializer.FontDeserializer;
import com.alibaba.fastjson.parser.deserializer.InetAddressDeserializer;
import com.alibaba.fastjson.parser.deserializer.InetSocketAddressDeserializer;
import com.alibaba.fastjson.parser.deserializer.IntegerDeserializer;
import com.alibaba.fastjson.parser.deserializer.IntegerFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JSONArrayDeserializer;
import com.alibaba.fastjson.parser.deserializer.JSONObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.LocaleDeserializer;
import com.alibaba.fastjson.parser.deserializer.LongDeserializer;
import com.alibaba.fastjson.parser.deserializer.LongFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.MapDeserializer;
import com.alibaba.fastjson.parser.deserializer.NumberDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.PatternDeserializer;
import com.alibaba.fastjson.parser.deserializer.PointDeserializer;
import com.alibaba.fastjson.parser.deserializer.RectangleDeserializer;
import com.alibaba.fastjson.parser.deserializer.ReferenceDeserializer;
import com.alibaba.fastjson.parser.deserializer.SqlDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.StackTraceElementDeserializer;
import com.alibaba.fastjson.parser.deserializer.StringDeserializer;
import com.alibaba.fastjson.parser.deserializer.StringFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer;
import com.alibaba.fastjson.parser.deserializer.TimeDeserializer;
import com.alibaba.fastjson.parser.deserializer.TimeZoneDeserializer;
import com.alibaba.fastjson.parser.deserializer.TimestampDeserializer;
import com.alibaba.fastjson.parser.deserializer.URIDeserializer;
import com.alibaba.fastjson.parser.deserializer.URLDeserializer;
import com.alibaba.fastjson.parser.deserializer.UUIDDeserializer;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.DeserializeBeanInfo;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.ServiceLoader;
import java.io.Closeable;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class ParserConfig
{
  private static ParserConfig global = new ParserConfig();
  private boolean asmEnable;
  private final IdentityHashMap<Type, ObjectDeserializer> derializers = new IdentityHashMap();
  private final Set<Class<?>> primitiveClasses = new HashSet();
  protected final SymbolTable symbolTable;

  public ParserConfig()
  {
    boolean bool;
    if (!ASMUtils.isAndroid())
      bool = true;
    while (true)
    {
      this.asmEnable = bool;
      this.symbolTable = new SymbolTable();
      this.primitiveClasses.add(Boolean.TYPE);
      this.primitiveClasses.add(Boolean.class);
      this.primitiveClasses.add(Character.TYPE);
      this.primitiveClasses.add(Character.class);
      this.primitiveClasses.add(Byte.TYPE);
      this.primitiveClasses.add(Byte.class);
      this.primitiveClasses.add(Short.TYPE);
      this.primitiveClasses.add(Short.class);
      this.primitiveClasses.add(Integer.TYPE);
      this.primitiveClasses.add(Integer.class);
      this.primitiveClasses.add(Long.TYPE);
      this.primitiveClasses.add(Long.class);
      this.primitiveClasses.add(Float.TYPE);
      this.primitiveClasses.add(Float.class);
      this.primitiveClasses.add(Double.TYPE);
      this.primitiveClasses.add(Double.class);
      this.primitiveClasses.add(BigInteger.class);
      this.primitiveClasses.add(BigDecimal.class);
      this.primitiveClasses.add(String.class);
      this.primitiveClasses.add(java.util.Date.class);
      this.primitiveClasses.add(java.sql.Date.class);
      this.primitiveClasses.add(Time.class);
      this.primitiveClasses.add(Timestamp.class);
      this.derializers.put(SimpleDateFormat.class, DateFormatDeserializer.instance);
      this.derializers.put(Timestamp.class, TimestampDeserializer.instance);
      this.derializers.put(java.sql.Date.class, SqlDateDeserializer.instance);
      this.derializers.put(Time.class, TimeDeserializer.instance);
      this.derializers.put(java.util.Date.class, DateDeserializer.instance);
      this.derializers.put(Calendar.class, CalendarDeserializer.instance);
      this.derializers.put(JSONObject.class, JSONObjectDeserializer.instance);
      this.derializers.put(JSONArray.class, JSONArrayDeserializer.instance);
      this.derializers.put(Map.class, MapDeserializer.instance);
      this.derializers.put(HashMap.class, MapDeserializer.instance);
      this.derializers.put(LinkedHashMap.class, MapDeserializer.instance);
      this.derializers.put(TreeMap.class, MapDeserializer.instance);
      this.derializers.put(ConcurrentMap.class, MapDeserializer.instance);
      this.derializers.put(ConcurrentHashMap.class, MapDeserializer.instance);
      this.derializers.put(Collection.class, CollectionDeserializer.instance);
      this.derializers.put(List.class, CollectionDeserializer.instance);
      this.derializers.put(ArrayList.class, CollectionDeserializer.instance);
      this.derializers.put(Object.class, JavaObjectDeserializer.instance);
      this.derializers.put(String.class, StringDeserializer.instance);
      this.derializers.put(Character.TYPE, CharacterDeserializer.instance);
      this.derializers.put(Character.class, CharacterDeserializer.instance);
      this.derializers.put(Byte.TYPE, NumberDeserializer.instance);
      this.derializers.put(Byte.class, NumberDeserializer.instance);
      this.derializers.put(Short.TYPE, NumberDeserializer.instance);
      this.derializers.put(Short.class, NumberDeserializer.instance);
      this.derializers.put(Integer.TYPE, IntegerDeserializer.instance);
      this.derializers.put(Integer.class, IntegerDeserializer.instance);
      this.derializers.put(Long.TYPE, LongDeserializer.instance);
      this.derializers.put(Long.class, LongDeserializer.instance);
      this.derializers.put(BigInteger.class, BigIntegerDeserializer.instance);
      this.derializers.put(BigDecimal.class, BigDecimalDeserializer.instance);
      this.derializers.put(Float.TYPE, FloatDeserializer.instance);
      this.derializers.put(Float.class, FloatDeserializer.instance);
      this.derializers.put(Double.TYPE, NumberDeserializer.instance);
      this.derializers.put(Double.class, NumberDeserializer.instance);
      this.derializers.put(Boolean.TYPE, BooleanDeserializer.instance);
      this.derializers.put(Boolean.class, BooleanDeserializer.instance);
      this.derializers.put(Class.class, ClassDerializer.instance);
      this.derializers.put([C.class, CharArrayDeserializer.instance);
      this.derializers.put(AtomicBoolean.class, BooleanDeserializer.instance);
      this.derializers.put(AtomicInteger.class, IntegerDeserializer.instance);
      this.derializers.put(AtomicLong.class, LongDeserializer.instance);
      this.derializers.put(AtomicReference.class, ReferenceDeserializer.instance);
      this.derializers.put(WeakReference.class, ReferenceDeserializer.instance);
      this.derializers.put(SoftReference.class, ReferenceDeserializer.instance);
      this.derializers.put(UUID.class, UUIDDeserializer.instance);
      this.derializers.put(TimeZone.class, TimeZoneDeserializer.instance);
      this.derializers.put(Locale.class, LocaleDeserializer.instance);
      this.derializers.put(InetAddress.class, InetAddressDeserializer.instance);
      this.derializers.put(Inet4Address.class, InetAddressDeserializer.instance);
      this.derializers.put(Inet6Address.class, InetAddressDeserializer.instance);
      this.derializers.put(InetSocketAddress.class, InetSocketAddressDeserializer.instance);
      this.derializers.put(File.class, FileDeserializer.instance);
      this.derializers.put(URI.class, URIDeserializer.instance);
      this.derializers.put(URL.class, URLDeserializer.instance);
      this.derializers.put(Pattern.class, PatternDeserializer.instance);
      this.derializers.put(Charset.class, CharsetDeserializer.instance);
      this.derializers.put(Number.class, NumberDeserializer.instance);
      this.derializers.put(AtomicIntegerArray.class, AtomicIntegerArrayDeserializer.instance);
      this.derializers.put(AtomicLongArray.class, AtomicLongArrayDeserializer.instance);
      this.derializers.put(StackTraceElement.class, StackTraceElementDeserializer.instance);
      this.derializers.put(Serializable.class, JavaObjectDeserializer.instance);
      this.derializers.put(Cloneable.class, JavaObjectDeserializer.instance);
      this.derializers.put(Comparable.class, JavaObjectDeserializer.instance);
      this.derializers.put(Closeable.class, JavaObjectDeserializer.instance);
      try
      {
        this.derializers.put(Class.forName("java.awt.Point"), PointDeserializer.instance);
        this.derializers.put(Class.forName("java.awt.Font"), FontDeserializer.instance);
        this.derializers.put(Class.forName("java.awt.Rectangle"), RectangleDeserializer.instance);
        this.derializers.put(Class.forName("java.awt.Color"), ColorDeserializer.instance);
        return;
        bool = false;
      }
      catch (Throwable localThrowable)
      {
      }
    }
  }

  public static Field getField(Class<?> paramClass, String paramString)
  {
    Field localField = getField0(paramClass, paramString);
    if (localField == null)
      localField = getField0(paramClass, "_" + paramString);
    if (localField == null)
      localField = getField0(paramClass, "m_" + paramString);
    return localField;
  }

  private static Field getField0(Class<?> paramClass, String paramString)
  {
    for (Field localField : paramClass.getDeclaredFields())
      if (paramString.equals(localField.getName()))
        return localField;
    if ((paramClass.getSuperclass() != null) && (paramClass.getSuperclass() != Object.class))
      return getField(paramClass.getSuperclass(), paramString);
    return null;
  }

  public static ParserConfig getGlobalInstance()
  {
    return global;
  }

  public FieldDeserializer createFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    boolean bool = this.asmEnable;
    if (!Modifier.isPublic(paramClass.getModifiers()))
      bool = false;
    if (paramFieldInfo.getFieldClass() == Class.class)
      bool = false;
    if (ASMDeserializerFactory.getInstance().isExternalClass(paramClass))
      bool = false;
    if (!bool)
      return createFieldDeserializerWithoutASM(paramParserConfig, paramClass, paramFieldInfo);
    try
    {
      FieldDeserializer localFieldDeserializer = ASMDeserializerFactory.getInstance().createFieldDeserializer(paramParserConfig, paramClass, paramFieldInfo);
      return localFieldDeserializer;
    }
    catch (Throwable localThrowable)
    {
    }
    return createFieldDeserializerWithoutASM(paramParserConfig, paramClass, paramFieldInfo);
  }

  public FieldDeserializer createFieldDeserializerWithoutASM(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    Class localClass = paramFieldInfo.getFieldClass();
    if ((localClass == Boolean.TYPE) || (localClass == Boolean.class))
      return new BooleanFieldDeserializer(paramParserConfig, paramClass, paramFieldInfo);
    if ((localClass == Integer.TYPE) || (localClass == Integer.class))
      return new IntegerFieldDeserializer(paramParserConfig, paramClass, paramFieldInfo);
    if ((localClass == Long.TYPE) || (localClass == Long.class))
      return new LongFieldDeserializer(paramParserConfig, paramClass, paramFieldInfo);
    if (localClass == String.class)
      return new StringFieldDeserializer(paramParserConfig, paramClass, paramFieldInfo);
    if ((localClass == List.class) || (localClass == ArrayList.class))
      return new ArrayListTypeFieldDeserializer(paramParserConfig, paramClass, paramFieldInfo);
    return new DefaultFieldDeserializer(paramParserConfig, paramClass, paramFieldInfo);
  }

  public ObjectDeserializer createJavaBeanDeserializer(Class<?> paramClass, Type paramType)
  {
    boolean bool = this.asmEnable;
    if ((bool) && (!Modifier.isPublic(paramClass.getModifiers())))
      bool = false;
    if (paramClass.getTypeParameters().length != 0)
      bool = false;
    if (ASMDeserializerFactory.getInstance().isExternalClass(paramClass))
      bool = false;
    Iterator localIterator;
    if (bool)
    {
      if (paramClass.isInterface())
        bool = false;
      DeserializeBeanInfo localDeserializeBeanInfo = DeserializeBeanInfo.computeSetters(paramClass, paramType);
      if (localDeserializeBeanInfo.getFieldList().size() > 200)
        bool = false;
      if ((localDeserializeBeanInfo.getDefaultConstructor() == null) && (!paramClass.isInterface()))
        bool = false;
      localIterator = localDeserializeBeanInfo.getFieldList().iterator();
    }
    while (true)
    {
      FieldInfo localFieldInfo;
      if (localIterator.hasNext())
      {
        localFieldInfo = (FieldInfo)localIterator.next();
        if (!localFieldInfo.isGetOnly())
          break label180;
      }
      label180: Class localClass;
      for (bool = false; ; bool = false)
      {
        if ((bool) && (paramClass.isMemberClass()) && (!Modifier.isStatic(paramClass.getModifiers())))
          bool = false;
        if (bool)
          break label227;
        return new JavaBeanDeserializer(this, paramClass, paramType);
        localClass = localFieldInfo.getFieldClass();
        if (Modifier.isPublic(localClass.getModifiers()))
          break;
      }
      if ((localClass.isMemberClass()) && (!Modifier.isStatic(localClass.getModifiers())))
        bool = false;
    }
    try
    {
      label227: ObjectDeserializer localObjectDeserializer = ASMDeserializerFactory.getInstance().createJavaBeanDeserializer(this, paramClass, paramType);
      return localObjectDeserializer;
    }
    catch (ASMException localASMException)
    {
      return new JavaBeanDeserializer(this, paramClass, paramType);
    }
    catch (Exception localException)
    {
      throw new JSONException("create asm deserializer error, " + paramClass.getName(), localException);
    }
  }

  public IdentityHashMap<Type, ObjectDeserializer> getDerializers()
  {
    return this.derializers;
  }

  public ObjectDeserializer getDeserializer(FieldInfo paramFieldInfo)
  {
    return getDeserializer(paramFieldInfo.getFieldClass(), paramFieldInfo.getFieldType());
  }

  public ObjectDeserializer getDeserializer(Class<?> paramClass, Type paramType)
  {
    ObjectDeserializer localObjectDeserializer1 = (ObjectDeserializer)this.derializers.get(paramType);
    if (localObjectDeserializer1 != null)
      return localObjectDeserializer1;
    if (paramType == null)
      paramType = paramClass;
    ObjectDeserializer localObjectDeserializer2 = (ObjectDeserializer)this.derializers.get(paramType);
    if (localObjectDeserializer2 != null)
      return localObjectDeserializer2;
    JSONType localJSONType = (JSONType)paramClass.getAnnotation(JSONType.class);
    if (localJSONType != null)
    {
      Class localClass = localJSONType.mappingTo();
      if (localClass != Void.class)
        return getDeserializer(localClass, localClass);
    }
    if (((paramType instanceof WildcardType)) || ((paramType instanceof TypeVariable)) || ((paramType instanceof ParameterizedType)))
      localObjectDeserializer2 = (ObjectDeserializer)this.derializers.get(paramClass);
    if (localObjectDeserializer2 != null)
      return localObjectDeserializer2;
    ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
    try
    {
      Iterator localIterator1 = ServiceLoader.load(AutowiredObjectDeserializer.class, localClassLoader).iterator();
      while (localIterator1.hasNext())
      {
        AutowiredObjectDeserializer localAutowiredObjectDeserializer = (AutowiredObjectDeserializer)localIterator1.next();
        Iterator localIterator2 = localAutowiredObjectDeserializer.getAutowiredFor().iterator();
        while (localIterator2.hasNext())
        {
          Type localType = (Type)localIterator2.next();
          this.derializers.put(localType, localAutowiredObjectDeserializer);
        }
      }
    }
    catch (Exception localException)
    {
      ObjectDeserializer localObjectDeserializer3 = (ObjectDeserializer)this.derializers.get(paramType);
      if (localObjectDeserializer3 != null)
        return localObjectDeserializer3;
      if (!paramClass.isEnum())
        break label276;
    }
    Object localObject = new EnumDeserializer(paramClass);
    while (true)
    {
      putDeserializer(paramType, (ObjectDeserializer)localObject);
      return localObject;
      label276: if (paramClass.isArray())
        return ArrayDeserializer.instance;
      if ((paramClass == Set.class) || (paramClass == HashSet.class) || (paramClass == Collection.class) || (paramClass == List.class) || (paramClass == ArrayList.class))
        localObject = CollectionDeserializer.instance;
      else if (Collection.class.isAssignableFrom(paramClass))
        localObject = CollectionDeserializer.instance;
      else if (Map.class.isAssignableFrom(paramClass))
        localObject = MapDeserializer.instance;
      else if (Throwable.class.isAssignableFrom(paramClass))
        localObject = new ThrowableDeserializer(this, paramClass);
      else
        localObject = createJavaBeanDeserializer(paramClass, paramType);
    }
  }

  public ObjectDeserializer getDeserializer(Type paramType)
  {
    ObjectDeserializer localObjectDeserializer = (ObjectDeserializer)this.derializers.get(paramType);
    if (localObjectDeserializer != null)
      return localObjectDeserializer;
    if ((paramType instanceof Class))
      return getDeserializer((Class)paramType, paramType);
    if ((paramType instanceof ParameterizedType))
    {
      Type localType = ((ParameterizedType)paramType).getRawType();
      if ((localType instanceof Class))
        return getDeserializer((Class)localType, paramType);
      return getDeserializer(localType);
    }
    return JavaObjectDeserializer.instance;
  }

  public Map<String, FieldDeserializer> getFieldDeserializers(Class<?> paramClass)
  {
    ObjectDeserializer localObjectDeserializer = getDeserializer(paramClass);
    if ((localObjectDeserializer instanceof JavaBeanDeserializer))
      return ((JavaBeanDeserializer)localObjectDeserializer).getFieldDeserializerMap();
    if ((localObjectDeserializer instanceof ASMJavaBeanDeserializer))
      return ((ASMJavaBeanDeserializer)localObjectDeserializer).getInnterSerializer().getFieldDeserializerMap();
    return Collections.emptyMap();
  }

  public SymbolTable getSymbolTable()
  {
    return this.symbolTable;
  }

  public boolean isAsmEnable()
  {
    return this.asmEnable;
  }

  public boolean isPrimitive(Class<?> paramClass)
  {
    return this.primitiveClasses.contains(paramClass);
  }

  public void putDeserializer(Type paramType, ObjectDeserializer paramObjectDeserializer)
  {
    this.derializers.put(paramType, paramObjectDeserializer);
  }

  public void setAsmEnable(boolean paramBoolean)
  {
    this.asmEnable = paramBoolean;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.ParserConfig
 * JD-Core Version:    0.6.2
 */