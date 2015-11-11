package weibo4android.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BareBonesBrowserLaunch
{
  private static void browse(String paramString)
    throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InterruptedException, InvocationTargetException, IOException, NoSuchMethodException
  {
    Object localObject = null;
    String str = System.getProperty("os.name", "");
    if (str.startsWith("Mac OS"))
    {
      Class.forName("com.apple.eio.FileManager").getDeclaredMethod("openURL", new Class[] { String.class }).invoke(null, new Object[] { paramString });
      return;
    }
    if (str.startsWith("Windows"))
    {
      Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + paramString);
      return;
    }
    String[] arrayOfString1 = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
    for (int i = 0; (i < arrayOfString1.length) && (localObject == null); i++)
    {
      Runtime localRuntime = Runtime.getRuntime();
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = "which";
      arrayOfString2[1] = arrayOfString1[i];
      if (localRuntime.exec(arrayOfString2).waitFor() == 0)
        localObject = arrayOfString1[i];
    }
    if (localObject == null)
      throw new NoSuchMethodException("Could not find web browser");
    Runtime.getRuntime().exec(new String[] { localObject, paramString });
  }

  public static void openURL(String paramString)
  {
    try
    {
      browse(paramString);
      return;
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.util.BareBonesBrowserLaunch
 * JD-Core Version:    0.6.2
 */