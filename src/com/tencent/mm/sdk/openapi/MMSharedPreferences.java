package com.tencent.mm.sdk.openapi;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.net.Uri;
import com.tencent.mm.sdk.b.a;
import com.tencent.mm.sdk.c.a.a;
import com.tencent.mm.sdk.c.a.b;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class MMSharedPreferences
  implements SharedPreferences
{
  private final String[] columns = { "_id", "key", "type", "value" };
  private final ContentResolver cr;
  private REditor editor = null;
  private final HashMap<String, Object> values = new HashMap();

  public MMSharedPreferences(Context paramContext)
  {
    this.cr = paramContext.getContentResolver();
  }

  private Object getValue(String paramString)
  {
    while (true)
    {
      try
      {
        Cursor localCursor = this.cr.query(a.b.CONTENT_URI, this.columns, "key = ?", new String[] { paramString }, null);
        if (localCursor == null)
          return null;
        int i = localCursor.getColumnIndex("type");
        int j = localCursor.getColumnIndex("value");
        if (localCursor.moveToFirst())
        {
          localObject = a.a.a(localCursor.getInt(i), localCursor.getString(j));
          localCursor.close();
          return localObject;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      Object localObject = null;
    }
  }

  public boolean contains(String paramString)
  {
    return getValue(paramString) != null;
  }

  public SharedPreferences.Editor edit()
  {
    if (this.editor == null)
      this.editor = new REditor(this.cr);
    return this.editor;
  }

  public Map<String, ?> getAll()
  {
    Cursor localCursor;
    try
    {
      localCursor = this.cr.query(a.b.CONTENT_URI, this.columns, null, null, null);
      if (localCursor == null)
        return null;
      int i = localCursor.getColumnIndex("key");
      int j = localCursor.getColumnIndex("type");
      int k = localCursor.getColumnIndex("value");
      while (localCursor.moveToNext())
      {
        Object localObject = a.a.a(localCursor.getInt(j), localCursor.getString(k));
        this.values.put(localCursor.getString(i), localObject);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return this.values;
    }
    localCursor.close();
    HashMap localHashMap = this.values;
    return localHashMap;
  }

  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    Object localObject = getValue(paramString);
    if ((localObject != null) && ((localObject instanceof Boolean)))
      paramBoolean = ((Boolean)localObject).booleanValue();
    return paramBoolean;
  }

  public float getFloat(String paramString, float paramFloat)
  {
    Object localObject = getValue(paramString);
    if ((localObject != null) && ((localObject instanceof Float)))
      paramFloat = ((Float)localObject).floatValue();
    return paramFloat;
  }

  public int getInt(String paramString, int paramInt)
  {
    Object localObject = getValue(paramString);
    if ((localObject != null) && ((localObject instanceof Integer)))
      paramInt = ((Integer)localObject).intValue();
    return paramInt;
  }

  public long getLong(String paramString, long paramLong)
  {
    Object localObject = getValue(paramString);
    if ((localObject != null) && ((localObject instanceof Long)))
      paramLong = ((Long)localObject).longValue();
    return paramLong;
  }

  public String getString(String paramString1, String paramString2)
  {
    Object localObject = getValue(paramString1);
    if ((localObject != null) && ((localObject instanceof String)))
      return (String)localObject;
    return paramString2;
  }

  public Set<String> getStringSet(String paramString, Set<String> paramSet)
  {
    return null;
  }

  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
  }

  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
  }

  private static class REditor
    implements SharedPreferences.Editor
  {
    private boolean clear = false;
    private ContentResolver cr;
    private Set<String> remove = new HashSet();
    private Map<String, Object> values = new HashMap();

    public REditor(ContentResolver paramContentResolver)
    {
      this.cr = paramContentResolver;
    }

    public void apply()
    {
    }

    public SharedPreferences.Editor clear()
    {
      this.clear = true;
      return this;
    }

    public boolean commit()
    {
      ContentValues localContentValues = new ContentValues();
      if (this.clear)
      {
        this.cr.delete(a.b.CONTENT_URI, null, null);
        this.clear = false;
      }
      Iterator localIterator1 = this.remove.iterator();
      while (localIterator1.hasNext())
      {
        String str = (String)localIterator1.next();
        this.cr.delete(a.b.CONTENT_URI, "key = ?", new String[] { str });
      }
      Iterator localIterator2 = this.values.entrySet().iterator();
      label147: label326: label352: 
      while (localIterator2.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator2.next();
        Object localObject = localEntry.getValue();
        int i;
        if (localObject == null)
        {
          a.a("MicroMsg.SDK.PluginProvider.Resolver", "unresolve failed, null value");
          i = 0;
          if (i != 0)
            break label326;
        }
        for (int j = 0; ; j = 1)
        {
          if (j == 0)
            break label352;
          ContentResolver localContentResolver = this.cr;
          Uri localUri = a.b.CONTENT_URI;
          String[] arrayOfString = new String[1];
          arrayOfString[0] = ((String)localEntry.getKey());
          localContentResolver.update(localUri, localContentValues, "key = ?", arrayOfString);
          break;
          if ((localObject instanceof Integer))
          {
            i = 1;
            break label147;
          }
          if ((localObject instanceof Long))
          {
            i = 2;
            break label147;
          }
          if ((localObject instanceof String))
          {
            i = 3;
            break label147;
          }
          if ((localObject instanceof Boolean))
          {
            i = 4;
            break label147;
          }
          if ((localObject instanceof Float))
          {
            i = 5;
            break label147;
          }
          if ((localObject instanceof Double))
          {
            i = 6;
            break label147;
          }
          a.a("MicroMsg.SDK.PluginProvider.Resolver", "unresolve failed, unknown type=" + localObject.getClass().toString());
          i = 0;
          break label147;
          localContentValues.put("type", Integer.valueOf(i));
          localContentValues.put("value", localObject.toString());
        }
      }
      return true;
    }

    public SharedPreferences.Editor putBoolean(String paramString, boolean paramBoolean)
    {
      this.values.put(paramString, Boolean.valueOf(paramBoolean));
      this.remove.remove(paramString);
      return this;
    }

    public SharedPreferences.Editor putFloat(String paramString, float paramFloat)
    {
      this.values.put(paramString, Float.valueOf(paramFloat));
      this.remove.remove(paramString);
      return this;
    }

    public SharedPreferences.Editor putInt(String paramString, int paramInt)
    {
      this.values.put(paramString, Integer.valueOf(paramInt));
      this.remove.remove(paramString);
      return this;
    }

    public SharedPreferences.Editor putLong(String paramString, long paramLong)
    {
      this.values.put(paramString, Long.valueOf(paramLong));
      this.remove.remove(paramString);
      return this;
    }

    public SharedPreferences.Editor putString(String paramString1, String paramString2)
    {
      this.values.put(paramString1, paramString2);
      this.remove.remove(paramString1);
      return this;
    }

    public SharedPreferences.Editor putStringSet(String paramString, Set<String> paramSet)
    {
      return null;
    }

    public SharedPreferences.Editor remove(String paramString)
    {
      this.remove.add(paramString);
      return this;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.openapi.MMSharedPreferences
 * JD-Core Version:    0.6.2
 */