package fm.qingting.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import java.io.FileOutputStream;

public class IsNewUser
{
  private static String TAG = "UserInfo";
  private static String filename = "user-new.dat";
  private static boolean sIsNewUser = false;

  // ERROR //
  public static boolean isNewUser(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 36	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   4: aload_0
    //   5: invokevirtual 40	android/content/Context:getPackageName	()Ljava/lang/String;
    //   8: iconst_0
    //   9: invokevirtual 46	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   12: getfield 52	android/content/pm/PackageInfo:versionCode	I
    //   15: istore 10
    //   17: iload 10
    //   19: istore_2
    //   20: new 54	java/io/BufferedReader
    //   23: dup
    //   24: new 56	java/io/InputStreamReader
    //   27: dup
    //   28: aload_0
    //   29: getstatic 21	fm/qingting/utils/IsNewUser:filename	Ljava/lang/String;
    //   32: invokevirtual 60	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   35: invokespecial 63	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   38: invokespecial 66	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   41: astore_3
    //   42: aload_3
    //   43: invokevirtual 69	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   46: invokestatic 75	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   49: istore 9
    //   51: iload 9
    //   53: istore 6
    //   55: aload_3
    //   56: invokevirtual 78	java/io/BufferedReader:close	()V
    //   59: getstatic 15	fm/qingting/utils/IsNewUser:TAG	Ljava/lang/String;
    //   62: new 80	java/lang/StringBuilder
    //   65: dup
    //   66: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   69: ldc 83
    //   71: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: iload 6
    //   76: invokevirtual 90	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   79: invokevirtual 93	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   82: invokestatic 99	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   85: pop
    //   86: iload 6
    //   88: iload_2
    //   89: if_icmpne +42 -> 131
    //   92: iconst_1
    //   93: istore 8
    //   95: iload 8
    //   97: putstatic 17	fm/qingting/utils/IsNewUser:sIsNewUser	Z
    //   100: getstatic 17	fm/qingting/utils/IsNewUser:sIsNewUser	Z
    //   103: ireturn
    //   104: astore_1
    //   105: aload_1
    //   106: invokevirtual 102	android/content/pm/PackageManager$NameNotFoundException:printStackTrace	()V
    //   109: iconst_0
    //   110: istore_2
    //   111: goto -91 -> 20
    //   114: astore 4
    //   116: aload 4
    //   118: astore 5
    //   120: iconst_0
    //   121: istore 6
    //   123: aload 5
    //   125: invokevirtual 103	java/lang/Exception:printStackTrace	()V
    //   128: goto -69 -> 59
    //   131: iconst_0
    //   132: istore 8
    //   134: goto -39 -> 95
    //   137: astore 5
    //   139: goto -16 -> 123
    //
    // Exception table:
    //   from	to	target	type
    //   0	17	104	android/content/pm/PackageManager$NameNotFoundException
    //   20	51	114	java/lang/Exception
    //   55	59	137	java/lang/Exception
  }

  public static void newUserAdded(Context paramContext)
  {
    try
    {
      i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      try
      {
        while (true)
        {
          FileOutputStream localFileOutputStream = paramContext.openFileOutput(filename, 0);
          localFileOutputStream.write(String.valueOf(i).getBytes());
          localFileOutputStream.close();
          return;
          localNameNotFoundException = localNameNotFoundException;
          localNameNotFoundException.printStackTrace();
          int i = 0;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.IsNewUser
 * JD-Core Version:    0.6.2
 */