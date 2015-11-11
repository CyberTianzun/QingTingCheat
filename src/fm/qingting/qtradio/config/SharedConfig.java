package fm.qingting.qtradio.config;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class SharedConfig
{
  private static SharedConfig mInstance;
  private SharedPreferences.Editor mEditor;
  private SharedPreferences mSharedPref;

  public static SharedConfig getInstance()
  {
    if (mInstance == null)
      mInstance = new SharedConfig();
    return mInstance;
  }

  public boolean getBoolean(String paramString)
  {
    return this.mSharedPref.getBoolean(paramString, true);
  }

  public float getFloat(String paramString)
  {
    return this.mSharedPref.getFloat(paramString, 0.0F);
  }

  public int getInt(String paramString)
  {
    return this.mSharedPref.getInt(paramString, 0);
  }

  public long getLong(String paramString)
  {
    return this.mSharedPref.getLong(paramString, 0L);
  }

  public String getString(String paramString)
  {
    return this.mSharedPref.getString(paramString, "");
  }

  public Set<String> getStringSet(String paramString)
  {
    return this.mSharedPref.getStringSet(paramString, null);
  }

  public void init(Activity paramActivity)
  {
    this.mSharedPref = paramActivity.getPreferences(0);
    this.mEditor = this.mSharedPref.edit();
  }

  public void putInt(String paramString, int paramInt)
  {
    this.mEditor.putInt(paramString, paramInt);
    this.mEditor.commit();
  }

  public void putIntPreferences(HashMap<String, Integer> paramHashMap)
  {
    putPreferences(paramHashMap, null, null, null, null, null);
  }

  public void putPreferences(HashMap<String, Integer> paramHashMap, HashMap<String, Long> paramHashMap1, HashMap<String, Boolean> paramHashMap2, HashMap<String, Float> paramHashMap3, HashMap<String, String> paramHashMap4, HashMap<String, Set<String>> paramHashMap5)
  {
    if (paramHashMap != null)
    {
      Iterator localIterator6 = paramHashMap.entrySet().iterator();
      while (localIterator6.hasNext())
      {
        Map.Entry localEntry6 = (Map.Entry)localIterator6.next();
        this.mEditor.putInt((String)localEntry6.getKey(), ((Integer)localEntry6.getValue()).intValue());
      }
    }
    if (paramHashMap1 != null)
    {
      Iterator localIterator5 = paramHashMap1.entrySet().iterator();
      while (localIterator5.hasNext())
      {
        Map.Entry localEntry5 = (Map.Entry)localIterator5.next();
        this.mEditor.putLong((String)localEntry5.getKey(), ((Long)localEntry5.getValue()).longValue());
      }
    }
    if (paramHashMap2 != null)
    {
      Iterator localIterator4 = paramHashMap2.entrySet().iterator();
      while (localIterator4.hasNext())
      {
        Map.Entry localEntry4 = (Map.Entry)localIterator4.next();
        this.mEditor.putBoolean((String)localEntry4.getKey(), ((Boolean)localEntry4.getValue()).booleanValue());
      }
    }
    if (paramHashMap3 != null)
    {
      Iterator localIterator3 = paramHashMap3.entrySet().iterator();
      while (localIterator3.hasNext())
      {
        Map.Entry localEntry3 = (Map.Entry)localIterator3.next();
        this.mEditor.putFloat((String)localEntry3.getKey(), ((Float)localEntry3.getValue()).floatValue());
      }
    }
    if (paramHashMap4 != null)
    {
      Iterator localIterator2 = paramHashMap4.entrySet().iterator();
      while (localIterator2.hasNext())
      {
        Map.Entry localEntry2 = (Map.Entry)localIterator2.next();
        this.mEditor.putString((String)localEntry2.getKey(), (String)localEntry2.getValue());
      }
    }
    if (paramHashMap5 != null)
    {
      Iterator localIterator1 = paramHashMap5.entrySet().iterator();
      while (localIterator1.hasNext())
      {
        Map.Entry localEntry1 = (Map.Entry)localIterator1.next();
        this.mEditor.putStringSet((String)localEntry1.getKey(), (Set)localEntry1.getValue());
      }
    }
    this.mEditor.commit();
  }

  public void putStringPreferences(HashMap<String, String> paramHashMap)
  {
    putPreferences(null, null, null, null, paramHashMap, null);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.config.SharedConfig
 * JD-Core Version:    0.6.2
 */