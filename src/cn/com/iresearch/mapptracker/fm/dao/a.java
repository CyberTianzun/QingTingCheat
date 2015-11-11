package cn.com.iresearch.mapptracker.fm.dao;

import android.database.Cursor;
import android.text.TextUtils;
import cn.com.iresearch.mapptracker.fm.a.e.c;
import cn.com.iresearch.mapptracker.fm.a.e.d;
import cn.com.iresearch.mapptracker.fm.a.e.f;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class a
{
  public static String buildDeleteSql(Class<?> paramClass, String paramString)
  {
    String str = f.a(paramClass).a();
    StringBuffer localStringBuffer = new StringBuffer("DELETE FROM " + str);
    if (!TextUtils.isEmpty(paramString))
    {
      localStringBuffer.append(" WHERE ");
      localStringBuffer.append(paramString);
    }
    return localStringBuffer.toString();
  }

  public static cn.com.iresearch.mapptracker.fm.a.d.a buildInsertSql(Object paramObject)
  {
    LinkedList localLinkedList = new LinkedList();
    f localf = f.a(paramObject.getClass());
    Object localObject1 = localf.b().a(paramObject);
    if ((!(localObject1 instanceof Integer)) && ((localObject1 instanceof String)) && (localObject1 != null))
      localLinkedList.add(new cn.com.iresearch.mapptracker.fm.a.e.b(localf.b().c(), localObject1));
    Iterator localIterator1 = localf.a.values().iterator();
    Iterator localIterator2;
    label103: StringBuffer localStringBuffer;
    Object localObject2;
    cn.com.iresearch.mapptracker.fm.a.d.a locala;
    Iterator localIterator3;
    label187: int j;
    if (!localIterator1.hasNext())
    {
      localIterator2 = localf.b.values().iterator();
      if (localIterator2.hasNext())
        break label307;
      localStringBuffer = new StringBuffer();
      int i = localLinkedList.size();
      localObject2 = null;
      if (i > 0)
      {
        locala = new cn.com.iresearch.mapptracker.fm.a.d.a();
        localStringBuffer.append("INSERT INTO ");
        localStringBuffer.append(f.a(paramObject.getClass()).a());
        localStringBuffer.append(" (");
        localIterator3 = localLinkedList.iterator();
        if (localIterator3.hasNext())
          break label340;
        localStringBuffer.deleteCharAt(-1 + localStringBuffer.length());
        localStringBuffer.append(") VALUES ( ");
        j = localLinkedList.size();
      }
    }
    for (int k = 0; ; k++)
    {
      if (k >= j)
      {
        localStringBuffer.deleteCharAt(-1 + localStringBuffer.length());
        localStringBuffer.append(")");
        locala.a(localStringBuffer.toString());
        localObject2 = locala;
        return localObject2;
        cn.com.iresearch.mapptracker.fm.a.e.b localb1 = property2KeyValue((cn.com.iresearch.mapptracker.fm.a.e.e)localIterator1.next(), paramObject);
        if (localb1 == null)
          break;
        localLinkedList.add(localb1);
        break;
        label307: cn.com.iresearch.mapptracker.fm.a.e.b localb3 = manyToOne2KeyValue((c)localIterator2.next(), paramObject);
        if (localb3 == null)
          break label103;
        localLinkedList.add(localb3);
        break label103;
        label340: cn.com.iresearch.mapptracker.fm.a.e.b localb2 = (cn.com.iresearch.mapptracker.fm.a.e.b)localIterator3.next();
        localStringBuffer.append(localb2.a()).append(",");
        locala.a(localb2.b());
        break label187;
      }
      localStringBuffer.append("?,");
    }
  }

  public static String getCreatTableSQL(Class<?> paramClass)
  {
    f localf = f.a(paramClass);
    cn.com.iresearch.mapptracker.fm.a.e.a locala = localf.b();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("CREATE TABLE IF NOT EXISTS ");
    localStringBuffer.append(localf.a());
    localStringBuffer.append(" ( ");
    Class localClass = locala.e();
    Iterator localIterator1;
    label95: Iterator localIterator2;
    if ((localClass == Integer.TYPE) || (localClass == Integer.class))
    {
      localStringBuffer.append("\"").append(locala.c()).append("\"    INTEGER PRIMARY KEY AUTOINCREMENT,");
      localIterator1 = localf.a.values().iterator();
      if (localIterator1.hasNext())
        break label174;
      localIterator2 = localf.b.values().iterator();
    }
    while (true)
    {
      if (!localIterator2.hasNext())
      {
        localStringBuffer.deleteCharAt(-1 + localStringBuffer.length());
        localStringBuffer.append(" )");
        return localStringBuffer.toString();
        localStringBuffer.append("\"").append(locala.c()).append("\"    TEXT PRIMARY KEY,");
        break;
        label174: cn.com.iresearch.mapptracker.fm.a.e.e locale = (cn.com.iresearch.mapptracker.fm.a.e.e)localIterator1.next();
        localStringBuffer.append("\"").append(locale.c());
        localStringBuffer.append("\",");
        break label95;
      }
      c localc = (c)localIterator2.next();
      localStringBuffer.append("\"").append(localc.c()).append("\",");
    }
  }

  private static String getDeleteSqlBytableName(String paramString)
  {
    return "DELETE FROM " + paramString;
  }

  public static <T> T getEntity(Cursor paramCursor, Class<T> paramClass)
  {
    if (paramCursor != null);
    while (true)
    {
      int i;
      Object localObject;
      int j;
      try
      {
        f localf = f.a(paramClass);
        i = paramCursor.getColumnCount();
        if (i > 0)
        {
          localObject = paramClass.newInstance();
          j = 0;
          break label123;
          String str = paramCursor.getColumnName(j);
          cn.com.iresearch.mapptracker.fm.a.e.e locale = (cn.com.iresearch.mapptracker.fm.a.e.e)localf.a.get(str);
          if (locale != null)
            locale.a(localObject, paramCursor.getString(j));
          else if (localf.b().c().equals(str))
            localf.b().a(localObject, paramCursor.getString(j));
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return null;
      label123: 
      while (j >= i)
      {
        return localObject;
        j++;
      }
    }
  }

  public static List<c> getManyToOneList(Class<?> paramClass)
  {
    ArrayList localArrayList = new ArrayList();
    int i;
    int j;
    do
      try
      {
        Field[] arrayOfField = paramClass.getDeclaredFields();
        i = arrayOfField.length;
        j = 0;
        continue;
        Field localField = arrayOfField[j];
        if ((!cn.com.iresearch.mapptracker.fm.a.c.a.c(localField)) && (cn.com.iresearch.mapptracker.fm.a.c.a.d(localField)))
        {
          c localc = new c();
          localField.getType();
          c.a();
          localc.a(cn.com.iresearch.mapptracker.fm.a.c.a.a(localField));
          localField.getName();
          c.b();
          localc.a(localField.getType());
          localc.b(cn.com.iresearch.mapptracker.fm.a.c.a.b(paramClass, localField));
          localc.a(cn.com.iresearch.mapptracker.fm.a.c.a.a(paramClass, localField));
          localArrayList.add(localc);
        }
        j++;
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException.getMessage(), localException);
      }
    while (j < i);
    return localArrayList;
  }

  public static List<d> getOneToManyList(Class<?> paramClass)
  {
    ArrayList localArrayList = new ArrayList();
    while (true)
    {
      int i;
      int j;
      try
      {
        Field[] arrayOfField = paramClass.getDeclaredFields();
        i = arrayOfField.length;
        j = 0;
        break label201;
        Field localField = arrayOfField[j];
        if ((!cn.com.iresearch.mapptracker.fm.a.c.a.c(localField)) && (cn.com.iresearch.mapptracker.fm.a.c.a.e(localField)))
        {
          d locald = new d();
          locald.a(cn.com.iresearch.mapptracker.fm.a.c.a.a(localField));
          localField.getName();
          d.b();
          if ((localField.getGenericType() instanceof ParameterizedType))
          {
            if ((Class)((ParameterizedType)localField.getGenericType()).getActualTypeArguments()[0] != null)
              d.a();
            locald.a(localField.getClass());
            locald.b(cn.com.iresearch.mapptracker.fm.a.c.a.b(paramClass, localField));
            locald.a(cn.com.iresearch.mapptracker.fm.a.c.a.a(paramClass, localField));
            localArrayList.add(locald);
          }
          else
          {
            throw new cn.com.iresearch.mapptracker.fm.a.b.b("getOneToManyList Exception:" + localField.getName() + "'s type is null");
          }
        }
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException.getMessage(), localException);
      }
      label201: 
      while (j >= i)
      {
        return localArrayList;
        j++;
      }
    }
  }

  public static Field getPrimaryKeyField(Class<?> paramClass)
  {
    Field[] arrayOfField = paramClass.getDeclaredFields();
    label22: label34: Field localField2;
    if (arrayOfField != null)
    {
      int i = arrayOfField.length;
      int j = 0;
      Field localField1;
      int i1;
      label45: int k;
      if (j >= i)
      {
        localField1 = null;
        if (localField1 == null)
        {
          int n = arrayOfField.length;
          i1 = 0;
          if (i1 < n)
            break label93;
        }
        localField2 = localField1;
        if (localField2 != null)
          break label176;
        k = arrayOfField.length;
      }
      for (int m = 0; ; m++)
      {
        Field localField3;
        if (m >= k)
          localField3 = localField2;
        label93: 
        do
        {
          return localField3;
          localField1 = arrayOfField[j];
          if (localField1.getAnnotation(cn.com.iresearch.mapptracker.fm.a.a.a.a.class) != null)
            break label22;
          j++;
          break;
          localField2 = arrayOfField[i1];
          if ("_id".equals(localField2.getName()))
            break label45;
          i1++;
          break label34;
          localField3 = arrayOfField[m];
        }
        while ("id".equals(localField3.getName()));
      }
    }
    else
    {
      throw new RuntimeException("this model[" + paramClass + "] has no field");
    }
    label176: return localField2;
  }

  public static String getPrimaryKeyFieldName(Class<?> paramClass)
  {
    Field localField = getPrimaryKeyField(paramClass);
    if (localField == null)
      return null;
    return localField.getName();
  }

  public static List<cn.com.iresearch.mapptracker.fm.a.e.e> getPropertyList(Class<?> paramClass)
  {
    ArrayList localArrayList = new ArrayList();
    int i;
    int j;
    do
      try
      {
        Field[] arrayOfField = paramClass.getDeclaredFields();
        String str = getPrimaryKeyFieldName(paramClass);
        i = arrayOfField.length;
        j = 0;
        continue;
        Field localField = arrayOfField[j];
        if ((!cn.com.iresearch.mapptracker.fm.a.c.a.c(localField)) && (cn.com.iresearch.mapptracker.fm.a.c.a.f(localField)) && (!localField.getName().equals(str)))
        {
          cn.com.iresearch.mapptracker.fm.a.e.e locale = new cn.com.iresearch.mapptracker.fm.a.e.e();
          locale.a(cn.com.iresearch.mapptracker.fm.a.c.a.a(localField));
          localField.getName();
          cn.com.iresearch.mapptracker.fm.a.e.e.b();
          locale.a(localField.getType());
          locale.b(cn.com.iresearch.mapptracker.fm.a.c.a.b(localField));
          locale.b(cn.com.iresearch.mapptracker.fm.a.c.a.b(paramClass, localField));
          locale.a(cn.com.iresearch.mapptracker.fm.a.c.a.a(paramClass, localField));
          locale.a(localField);
          localArrayList.add(locale);
        }
        j++;
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException.getMessage(), localException);
      }
    while (j < i);
    return localArrayList;
  }

  public static List<cn.com.iresearch.mapptracker.fm.a.e.b> getSaveKeyValueListByEntity(Object paramObject)
  {
    LinkedList localLinkedList = new LinkedList();
    f localf = f.a(paramObject.getClass());
    Object localObject = localf.b().a(paramObject);
    if ((!(localObject instanceof Integer)) && ((localObject instanceof String)) && (localObject != null))
      localLinkedList.add(new cn.com.iresearch.mapptracker.fm.a.e.b(localf.b().c(), localObject));
    Iterator localIterator1 = localf.a.values().iterator();
    Iterator localIterator2;
    if (!localIterator1.hasNext())
      localIterator2 = localf.b.values().iterator();
    while (true)
    {
      if (!localIterator2.hasNext())
      {
        return localLinkedList;
        cn.com.iresearch.mapptracker.fm.a.e.b localb1 = property2KeyValue((cn.com.iresearch.mapptracker.fm.a.e.e)localIterator1.next(), paramObject);
        if (localb1 == null)
          break;
        localLinkedList.add(localb1);
        break;
      }
      cn.com.iresearch.mapptracker.fm.a.e.b localb2 = manyToOne2KeyValue((c)localIterator2.next(), paramObject);
      if (localb2 != null)
        localLinkedList.add(localb2);
    }
  }

  public static String getSelectSQL(Class<?> paramClass)
  {
    return getSelectSqlByTableName(f.a(paramClass).a());
  }

  public static String getSelectSQLByWhere(Class<?> paramClass, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer(getSelectSqlByTableName(f.a(paramClass).a()));
    if (!TextUtils.isEmpty(paramString))
      localStringBuffer.append(" WHERE ").append(paramString);
    return localStringBuffer.toString();
  }

  private static String getSelectSqlByTableName(String paramString)
  {
    return "SELECT * FROM " + paramString;
  }

  public static String getTableName(Class<?> paramClass)
  {
    cn.com.iresearch.mapptracker.fm.a.a.a.e locale = (cn.com.iresearch.mapptracker.fm.a.a.a.e)paramClass.getAnnotation(cn.com.iresearch.mapptracker.fm.a.a.a.e.class);
    if ((locale == null) || (locale.a().trim().length() == 0))
      return paramClass.getName().replace('.', '_');
    return locale.a();
  }

  public static cn.com.iresearch.mapptracker.fm.a.d.a getUpdateSqlAsSqlInfo(Object paramObject, String paramString)
  {
    f localf = f.a(paramObject.getClass());
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = localf.a.values().iterator();
    Iterator localIterator2;
    if (!localIterator1.hasNext())
      localIterator2 = localf.b.values().iterator();
    while (true)
    {
      if (!localIterator2.hasNext())
      {
        if (localArrayList.size() != 0)
          break label173;
        throw new cn.com.iresearch.mapptracker.fm.a.b.b("this entity[" + paramObject.getClass() + "] has no property");
        cn.com.iresearch.mapptracker.fm.a.e.b localb1 = property2KeyValue((cn.com.iresearch.mapptracker.fm.a.e.e)localIterator1.next(), paramObject);
        if (localb1 == null)
          break;
        localArrayList.add(localb1);
        break;
      }
      cn.com.iresearch.mapptracker.fm.a.e.b localb2 = manyToOne2KeyValue((c)localIterator2.next(), paramObject);
      if (localb2 != null)
        localArrayList.add(localb2);
    }
    label173: cn.com.iresearch.mapptracker.fm.a.d.a locala = new cn.com.iresearch.mapptracker.fm.a.d.a();
    StringBuffer localStringBuffer = new StringBuffer("UPDATE ");
    localStringBuffer.append(localf.a());
    localStringBuffer.append(" SET ");
    Iterator localIterator3 = localArrayList.iterator();
    while (true)
    {
      if (!localIterator3.hasNext())
      {
        localStringBuffer.deleteCharAt(-1 + localStringBuffer.length());
        if (!TextUtils.isEmpty(paramString))
          localStringBuffer.append(" WHERE ").append(paramString);
        locala.a(localStringBuffer.toString());
        return locala;
      }
      cn.com.iresearch.mapptracker.fm.a.e.b localb3 = (cn.com.iresearch.mapptracker.fm.a.e.b)localIterator3.next();
      localStringBuffer.append(localb3.a()).append("=?,");
      locala.a(localb3.b());
    }
  }

  private static cn.com.iresearch.mapptracker.fm.a.e.b manyToOne2KeyValue(c paramc, Object paramObject)
  {
    String str = paramc.c();
    Object localObject1 = paramc.a(paramObject);
    cn.com.iresearch.mapptracker.fm.a.e.b localb = null;
    if (localObject1 != null)
    {
      Object localObject2 = f.a(localObject1.getClass()).b().a(localObject1);
      localb = null;
      if (str != null)
      {
        localb = null;
        if (localObject2 != null)
          localb = new cn.com.iresearch.mapptracker.fm.a.e.b(str, localObject2);
      }
    }
    return localb;
  }

  private static cn.com.iresearch.mapptracker.fm.a.e.b property2KeyValue(cn.com.iresearch.mapptracker.fm.a.e.e parame, Object paramObject)
  {
    String str1 = parame.c();
    Object localObject = parame.a(paramObject);
    cn.com.iresearch.mapptracker.fm.a.e.b localb;
    if (localObject != null)
      localb = new cn.com.iresearch.mapptracker.fm.a.e.b(str1, localObject);
    int i;
    do
    {
      String str2;
      do
      {
        return localb;
        str2 = parame.d();
        localb = null;
      }
      while (str2 == null);
      i = parame.d().trim().length();
      localb = null;
    }
    while (i == 0);
    return new cn.com.iresearch.mapptracker.fm.a.e.b(str1, parame.d());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.dao.a
 * JD-Core Version:    0.6.2
 */