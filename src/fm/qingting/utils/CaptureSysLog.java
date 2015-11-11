package fm.qingting.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

public class CaptureSysLog
{
  private static CaptureSysLog instance;

  public static CaptureSysLog getInstance()
  {
    if (instance == null)
      instance = new CaptureSysLog();
    return instance;
  }

  public static void getLog()
  {
    System.out.println("--------func start--------");
    String str;
    try
    {
      ArrayList localArrayList1 = new ArrayList();
      localArrayList1.add("logcat");
      localArrayList1.add("-d");
      ArrayList localArrayList2 = new ArrayList();
      localArrayList2.add("logcat");
      localArrayList2.add("-c");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec((String[])localArrayList1.toArray(new String[localArrayList1.size()])).getInputStream()));
      while (true)
      {
        str = localBufferedReader.readLine();
        if (str == null)
          break;
        Runtime.getRuntime().exec((String[])localArrayList2.toArray(new String[localArrayList2.size()]));
        System.out.println(str);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    while (true)
    {
      System.out.println("--------func end--------");
      return;
      if (str == null)
        System.out.println("-- is null --");
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.CaptureSysLog
 * JD-Core Version:    0.6.2
 */