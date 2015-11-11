package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class l
{
  private final String a = "umeng_event_snapshot";
  private boolean b = false;
  private SharedPreferences c;
  private Map<String, ArrayList<aa>> d = new HashMap();

  public l(Context paramContext)
  {
    this.c = u.a(paramContext, "umeng_event_snapshot");
  }

  private void c(String paramString)
  {
    boolean bool = this.d.containsKey(paramString);
    String str = null;
    if (bool)
    {
      ArrayList localArrayList = (ArrayList)this.d.get(paramString);
      while (localArrayList.size() > 4)
        localArrayList.remove(0);
      str = s.a(localArrayList);
    }
    this.c.edit().putString(paramString, str).commit();
  }

  private boolean d(String paramString)
  {
    if (this.d.containsKey(paramString))
      return true;
    String str = this.c.getString(paramString, null);
    if (str != null)
    {
      ArrayList localArrayList = (ArrayList)s.a(str);
      if (localArrayList != null)
      {
        this.d.put(paramString, localArrayList);
        return true;
      }
    }
    return false;
  }

  public int a(String paramString)
  {
    if (this.d.containsKey(paramString))
      return ((ArrayList)this.d.get(paramString)).size();
    return 0;
  }

  public void a(String paramString, aa paramaa)
  {
    if (this.b)
      d(paramString);
    if (this.d.containsKey(paramString))
      ((ArrayList)this.d.get(paramString)).add(paramaa);
    while (true)
    {
      if (this.b)
        c(paramString);
      return;
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(paramaa);
      this.d.put(paramString, localArrayList);
    }
  }

  public void a(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }

  public aa b(String paramString)
  {
    if (this.b)
      d(paramString);
    ArrayList localArrayList;
    if (this.d.containsKey(paramString))
    {
      localArrayList = (ArrayList)this.d.get(paramString);
      if (localArrayList.size() <= 0);
    }
    for (aa localaa = (aa)localArrayList.remove(-1 + localArrayList.size()); ; localaa = null)
    {
      if (this.b)
        c(paramString);
      return localaa;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.l
 * JD-Core Version:    0.6.2
 */