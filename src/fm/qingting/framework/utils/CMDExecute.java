package fm.qingting.framework.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class CMDExecute
{
  public String run(String[] paramArrayOfString, String paramString)
    throws IOException
  {
    Object localObject1 = "";
    try
    {
      ProcessBuilder localProcessBuilder = new ProcessBuilder(paramArrayOfString);
      if (paramString != null)
        localProcessBuilder.directory(new File(paramString));
      localProcessBuilder.redirectErrorStream(true);
      InputStream localInputStream = localProcessBuilder.start().getInputStream();
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        if (localInputStream.read(arrayOfByte) == -1)
        {
          localInputStream.close();
          return localObject1;
        }
        System.out.println(new String(arrayOfByte));
        String str = localObject1 + new String(arrayOfByte);
        localObject1 = str;
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
    finally
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.CMDExecute
 * JD-Core Version:    0.6.2
 */