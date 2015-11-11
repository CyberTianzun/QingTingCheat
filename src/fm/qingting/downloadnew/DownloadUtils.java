package fm.qingting.downloadnew;

import android.os.Environment;
import java.io.File;
import java.io.IOException;

public class DownloadUtils
{
  private static String EMPTY_STRING = "";
  private static char[] ILLEGAL_CHARS = { 58, 32, 63, 34, 60, 62, 33, 92, 47, 9, 13, 10 };
  private static char[] SUB_CHARS = { -230, 95, -225, 95, 12298, 12299, -255, 95, 95, 95, 95, 95 };

  public static File createFile(String paramString)
    throws IOException
  {
    File localFile = new File(paramString);
    if (localFile.exists());
    while ((!createFolder(localFile.getParentFile())) || (!localFile.createNewFile()))
      return localFile;
    return localFile;
  }

  private static boolean createFolder(File paramFile)
  {
    if (paramFile.exists())
      return true;
    if (createFolder(paramFile.getParentFile()))
      return paramFile.mkdir();
    return false;
  }

  public static void deleteFile(String paramString)
  {
    File localFile = new File(paramString);
    if (localFile.exists())
      localFile.delete();
  }

  public static String escapeFileName(String paramString)
  {
    if (paramString == null)
      return null;
    String str = paramString.trim();
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < str.length(); i++)
      localStringBuilder.append(getLegalChar(str.charAt(i)));
    return localStringBuilder.toString();
  }

  public static String formatSize(long paramLong, boolean paramBoolean)
  {
    if (paramBoolean);
    for (int i = 1000; paramLong < i; i = 1024)
      return paramLong + " B";
    int j = (int)(Math.log(paramLong) / Math.log(i));
    StringBuilder localStringBuilder1 = new StringBuilder();
    String str1;
    StringBuilder localStringBuilder2;
    if (paramBoolean)
    {
      str1 = "kMGTPE";
      localStringBuilder2 = localStringBuilder1.append(str1.charAt(j - 1));
      if (!paramBoolean)
        break label154;
    }
    label154: for (String str2 = ""; ; str2 = "i")
    {
      String str3 = str2;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Double.valueOf(paramLong / Math.pow(i, j));
      arrayOfObject[1] = str3;
      return String.format("%.1f%sB", arrayOfObject);
      str1 = "KMGTPE";
      break;
    }
  }

  public static String getDownloadFileName(String paramString)
  {
    if (paramString == null)
      return "";
    return paramString.substring(1 + paramString.lastIndexOf('/'), paramString.length());
  }

  private static char getLegalChar(char paramChar)
  {
    int i = 0;
    if (i < ILLEGAL_CHARS.length)
      if (paramChar != ILLEGAL_CHARS[i]);
    while (true)
    {
      if (i != -1)
        paramChar = SUB_CHARS[i];
      return paramChar;
      i++;
      break;
      i = -1;
    }
  }

  public static boolean isSDCardAvailable()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }

  public static String notNull(String paramString)
  {
    if (paramString == null)
      paramString = EMPTY_STRING;
    return paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.downloadnew.DownloadUtils
 * JD-Core Version:    0.6.2
 */