package fm.qingting.nativec;

import android.content.Context;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class NativeLaucher
{
  public static String RunExecutable(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    String str1 = "/data/data/" + paramString1;
    String str2 = str1 + "/lib/" + paramString2;
    String str3 = str1 + "/" + paramString3;
    StringBuffer localStringBuffer = new StringBuffer();
    RunLocalUserCommand(paramString1, "dd if=" + str2 + " of=" + str3, localStringBuffer);
    localStringBuffer.append(";");
    RunLocalUserCommand(paramString1, "chmod 777 " + str3, localStringBuffer);
    localStringBuffer.append(";");
    String str4 = str1 + "/" + paramString3;
    Object localObject1 = str4 + " " + paramString4;
    if (paramArrayOfString1 != null)
    {
      Object localObject2 = localObject1;
      for (int j = 0; j < paramArrayOfString1.length; j++)
        localObject2 = (String)localObject2 + " " + paramArrayOfString1[j];
      localObject1 = localObject2;
    }
    int i = 0;
    if (paramArrayOfString2 != null)
      while (i < paramArrayOfString2.length)
      {
        localObject1 = (String)localObject1 + " " + paramArrayOfString2[i];
        i++;
      }
    RunLocalUserCommand(paramString1, (String)localObject1, localStringBuffer);
    localStringBuffer.append(";");
    return localStringBuffer.toString();
  }

  private static boolean RunLocalUserCommand(String paramString1, String paramString2, StringBuffer paramStringBuffer)
  {
    try
    {
      Process localProcess = Runtime.getRuntime().exec("sh");
      DataInputStream localDataInputStream = new DataInputStream(localProcess.getInputStream());
      DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
      localDataOutputStream.writeBytes("cd /data/data/" + paramString1 + "\n");
      localDataOutputStream.writeBytes(paramString2 + " &\n");
      localDataOutputStream.writeBytes("exit\n");
      localDataOutputStream.flush();
      localProcess.waitFor();
      byte[] arrayOfByte = new byte[localDataInputStream.available()];
      localDataInputStream.read(arrayOfByte);
      String str = new String(arrayOfByte);
      if (paramStringBuffer != null)
        paramStringBuffer.append("CMD Result:\n" + str);
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      if (paramStringBuffer != null)
        paramStringBuffer.append("Exception:" + localException.getMessage());
    }
    return false;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.nativec.NativeLaucher
 * JD-Core Version:    0.6.2
 */