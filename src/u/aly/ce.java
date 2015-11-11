package u.aly;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ce
{
  public static cd a(Class<? extends cd> paramClass, int paramInt)
  {
    try
    {
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = Integer.TYPE;
      Method localMethod = paramClass.getMethod("findByValue", arrayOfClass);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      cd localcd = (cd)localMethod.invoke(null, arrayOfObject);
      return localcd;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      return null;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      return null;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
    }
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     u.aly.ce
 * JD-Core Version:    0.6.2
 */