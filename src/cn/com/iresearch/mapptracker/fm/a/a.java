package cn.com.iresearch.mapptracker.fm.a;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cn.com.iresearch.mapptracker.fm.a.e.e;
import cn.com.iresearch.mapptracker.fm.a.e.f;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public final class a
{
  private static HashMap<String, a> a = new HashMap();
  private SQLiteDatabase b;
  private b c;

  private a(b paramb)
  {
    if (paramb == null)
      throw new RuntimeException("daoConfig is null");
    if (paramb.a() == null)
      throw new RuntimeException("android context is null");
    this.b = new c(paramb.a().getApplicationContext(), paramb.b(), paramb.c()).getWritableDatabase();
    this.c = paramb;
  }

  public static a a(Context paramContext, String paramString)
  {
    b localb = new b();
    localb.a(paramContext);
    localb.a(paramString);
    localb.e();
    return a(localb);
  }

  private static a a(b paramb)
  {
    try
    {
      a locala = (a)a.get(paramb.b());
      if (locala == null)
      {
        locala = new a(paramb);
        a.put(paramb.b(), locala);
      }
      return locala;
    }
    finally
    {
    }
  }

  private void a(cn.com.iresearch.mapptracker.fm.a.d.a parama)
  {
    if (parama != null)
    {
      a(parama.a());
      this.b.execSQL(parama.a(), parama.b());
      return;
    }
    Log.e("FinalDb", "sava error:sqlInfo is null");
  }

  private void a(String paramString)
  {
    if ((this.c != null) && (this.c.d()))
      Log.d("Debug SQL", ">>>>>>  " + paramString);
  }

  private boolean a(f paramf)
  {
    Cursor localCursor = null;
    if (paramf.c())
      return true;
    while (true)
    {
      try
      {
        String str = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type ='table' AND name ='" + paramf.a() + "' ";
        a(str);
        localCursor = this.b.rawQuery(str, null);
        if ((localCursor != null) && (localCursor.moveToNext()) && (localCursor.getInt(0) > 0))
        {
          paramf.d();
          return true;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return false;
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
      if (localCursor != null)
        localCursor.close();
    }
  }

  private <T> List<T> b(Class<T> paramClass, String paramString)
  {
    c(paramClass);
    a(paramString);
    Cursor localCursor = this.b.rawQuery(paramString, null);
    try
    {
      ArrayList localArrayList = new ArrayList();
      while (true)
      {
        boolean bool = localCursor.moveToNext();
        if (!bool)
          return localArrayList;
        localArrayList.add(cn.com.iresearch.mapptracker.fm.dao.a.getEntity(localCursor, paramClass));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  private void c(Class<?> paramClass)
  {
    while (true)
    {
      StringBuffer localStringBuffer;
      Iterator localIterator1;
      Iterator localIterator2;
      try
      {
        if (a(f.a(paramClass)))
          break;
        f localf = f.a(paramClass);
        cn.com.iresearch.mapptracker.fm.a.e.a locala = localf.b();
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("CREATE TABLE IF NOT EXISTS ");
        localStringBuffer.append(localf.a());
        localStringBuffer.append(" ( ");
        Class localClass = locala.e();
        if ((localClass == Integer.TYPE) || (localClass == Integer.class))
        {
          localStringBuffer.append("\"").append(locala.c()).append("\"    INTEGER PRIMARY KEY AUTOINCREMENT,");
          localIterator1 = localf.a.values().iterator();
          if (!localIterator1.hasNext())
          {
            localIterator2 = localf.b.values().iterator();
            if (localIterator2.hasNext())
              break label262;
            localStringBuffer.deleteCharAt(-1 + localStringBuffer.length());
            localStringBuffer.append(" )");
            String str = localStringBuffer.toString();
            a(str);
            this.b.execSQL(str);
          }
        }
        else
        {
          localStringBuffer.append("\"").append(locala.c()).append("\"    TEXT PRIMARY KEY,");
          continue;
        }
      }
      catch (SQLException localSQLException)
      {
        localSQLException.printStackTrace();
        return;
      }
      e locale = (e)localIterator1.next();
      localStringBuffer.append("\"").append(locale.c());
      localStringBuffer.append("\",");
      continue;
      label262: cn.com.iresearch.mapptracker.fm.a.e.c localc = (cn.com.iresearch.mapptracker.fm.a.e.c)localIterator2.next();
      localStringBuffer.append("\"").append(localc.c()).append("\",");
    }
  }

  public final <T> List<T> a(Class<T> paramClass, String paramString)
  {
    c(paramClass);
    return b(paramClass, cn.com.iresearch.mapptracker.fm.dao.a.getSelectSQLByWhere(paramClass, paramString));
  }

  public final void a(Class<?> paramClass)
  {
    c(paramClass);
    String str = cn.com.iresearch.mapptracker.fm.dao.a.buildDeleteSql(paramClass, null);
    a(str);
    this.b.execSQL(str);
  }

  public final void a(Object paramObject)
  {
    c(paramObject.getClass());
    a(cn.com.iresearch.mapptracker.fm.dao.a.buildInsertSql(paramObject));
  }

  public final void a(Object paramObject, String paramString)
  {
    c(paramObject.getClass());
    a(cn.com.iresearch.mapptracker.fm.dao.a.getUpdateSqlAsSqlInfo(paramObject, paramString));
  }

  public final <T> List<T> b(Class<T> paramClass)
  {
    c(paramClass);
    return b(paramClass, cn.com.iresearch.mapptracker.fm.dao.a.getSelectSQL(paramClass));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.a.a
 * JD-Core Version:    0.6.2
 */