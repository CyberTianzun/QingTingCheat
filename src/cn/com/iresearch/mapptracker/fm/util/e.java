package cn.com.iresearch.mapptracker.fm.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import cn.com.iresearch.mapptracker.fm.base64.org.apache.commons.codec.binary.Base64;
import java.util.Random;

public final class e
{
  private static byte[] a = "Tvb!@#RS".getBytes();

  private static String a()
  {
    int i = 0;
    char[] arrayOfChar = { 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57 };
    StringBuffer localStringBuffer = new StringBuffer("");
    Random localRandom = new Random();
    while (true)
    {
      if (i >= 8)
        return localStringBuffer.toString();
      int j = Math.abs(localRandom.nextInt(36));
      if ((j >= 0) && (j < arrayOfChar.length))
      {
        localStringBuffer.append(arrayOfChar[j]);
        i++;
      }
    }
  }

  public static String a(Context paramContext, String paramString)
  {
    int i = 0;
    String str1 = "";
    while (true)
    {
      StringBuffer localStringBuffer;
      String str3;
      try
      {
        SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("MATSharedPreferences", 0);
        Object localObject = "yoT2EXOyrvuPcbkUpAeYpkVxrBIBKhHJRg3s_hAayePTDtJCz-MDs1NF-54_jLQds4_jvB809t4130oO2yZIeOCCyDTaKO3gi0ZxjviiWtgHz_OO6knr7e29JNi7_IYZG8Iz21UByMdkiPeGU0XeS5XAqgrsDs_yY8UQvv6wvz_VYCq50zpIvlMOlucqgLwweZlbryAz8GXR6uAzsRowCj_Ms236MbzQ";
        try
        {
          String str4 = DataProvider.pd();
          localObject = str4;
          String str2 = localSharedPreferences.getString("Pd", (String)localObject);
          localStringBuffer = new StringBuffer();
          if (TextUtils.isEmpty(str2))
            break;
          str3 = a();
          char c = (char)d.a(str2, a)[0];
          localStringBuffer.append(str3.substring(0, 3) + c + str3.substring(3));
          int j = paramString.length();
          int k = j / 400;
          int m = j % 400;
          if (i >= k)
          {
            localStringBuffer.append(Base64.encodeBase64URLSafeString(d.a(paramString.substring(j - m).getBytes(), str3)));
            str1 = localStringBuffer.toString();
            if (!str1.endsWith("$"))
              break;
            return str1.substring(0, str1.lastIndexOf("$"));
          }
        }
        catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
        {
          localUnsatisfiedLinkError.printStackTrace();
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return str1;
      }
      int n = i * 400;
      int i1 = 400 * (i + 1);
      localStringBuffer.append(Base64.encodeBase64URLSafeString(d.a(paramString.substring(n, i1).getBytes(), str3))).append("$");
      i++;
    }
    return str1;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.util.e
 * JD-Core Version:    0.6.2
 */