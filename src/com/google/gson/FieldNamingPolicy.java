package com.google.gson;

import java.lang.reflect.Field;

public enum FieldNamingPolicy
  implements FieldNamingStrategy
{
  static
  {
    // Byte code:
    //   0: new 19	com/google/gson/FieldNamingPolicy$1
    //   3: dup
    //   4: ldc 20
    //   6: iconst_0
    //   7: invokespecial 24	com/google/gson/FieldNamingPolicy$1:<init>	(Ljava/lang/String;I)V
    //   10: putstatic 26	com/google/gson/FieldNamingPolicy:IDENTITY	Lcom/google/gson/FieldNamingPolicy;
    //   13: new 28	com/google/gson/FieldNamingPolicy$2
    //   16: dup
    //   17: ldc 29
    //   19: iconst_1
    //   20: invokespecial 30	com/google/gson/FieldNamingPolicy$2:<init>	(Ljava/lang/String;I)V
    //   23: putstatic 32	com/google/gson/FieldNamingPolicy:UPPER_CAMEL_CASE	Lcom/google/gson/FieldNamingPolicy;
    //   26: new 34	com/google/gson/FieldNamingPolicy$3
    //   29: dup
    //   30: ldc 35
    //   32: iconst_2
    //   33: invokespecial 36	com/google/gson/FieldNamingPolicy$3:<init>	(Ljava/lang/String;I)V
    //   36: putstatic 38	com/google/gson/FieldNamingPolicy:UPPER_CAMEL_CASE_WITH_SPACES	Lcom/google/gson/FieldNamingPolicy;
    //   39: new 40	com/google/gson/FieldNamingPolicy$4
    //   42: dup
    //   43: ldc 41
    //   45: iconst_3
    //   46: invokespecial 42	com/google/gson/FieldNamingPolicy$4:<init>	(Ljava/lang/String;I)V
    //   49: putstatic 44	com/google/gson/FieldNamingPolicy:LOWER_CASE_WITH_UNDERSCORES	Lcom/google/gson/FieldNamingPolicy;
    //   52: new 46	com/google/gson/FieldNamingPolicy$5
    //   55: dup
    //   56: ldc 47
    //   58: iconst_4
    //   59: invokespecial 48	com/google/gson/FieldNamingPolicy$5:<init>	(Ljava/lang/String;I)V
    //   62: putstatic 50	com/google/gson/FieldNamingPolicy:LOWER_CASE_WITH_DASHES	Lcom/google/gson/FieldNamingPolicy;
    //   65: iconst_5
    //   66: anewarray 2	com/google/gson/FieldNamingPolicy
    //   69: astore_0
    //   70: aload_0
    //   71: iconst_0
    //   72: getstatic 26	com/google/gson/FieldNamingPolicy:IDENTITY	Lcom/google/gson/FieldNamingPolicy;
    //   75: aastore
    //   76: aload_0
    //   77: iconst_1
    //   78: getstatic 32	com/google/gson/FieldNamingPolicy:UPPER_CAMEL_CASE	Lcom/google/gson/FieldNamingPolicy;
    //   81: aastore
    //   82: aload_0
    //   83: iconst_2
    //   84: getstatic 38	com/google/gson/FieldNamingPolicy:UPPER_CAMEL_CASE_WITH_SPACES	Lcom/google/gson/FieldNamingPolicy;
    //   87: aastore
    //   88: aload_0
    //   89: iconst_3
    //   90: getstatic 44	com/google/gson/FieldNamingPolicy:LOWER_CASE_WITH_UNDERSCORES	Lcom/google/gson/FieldNamingPolicy;
    //   93: aastore
    //   94: aload_0
    //   95: iconst_4
    //   96: getstatic 50	com/google/gson/FieldNamingPolicy:LOWER_CASE_WITH_DASHES	Lcom/google/gson/FieldNamingPolicy;
    //   99: aastore
    //   100: aload_0
    //   101: putstatic 52	com/google/gson/FieldNamingPolicy:$VALUES	[Lcom/google/gson/FieldNamingPolicy;
    //   104: return
  }

  private static String modifyString(char paramChar, String paramString, int paramInt)
  {
    if (paramInt < paramString.length())
      return paramChar + paramString.substring(paramInt);
    return String.valueOf(paramChar);
  }

  private static String separateCamelCase(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < paramString1.length(); i++)
    {
      char c = paramString1.charAt(i);
      if ((Character.isUpperCase(c)) && (localStringBuilder.length() != 0))
        localStringBuilder.append(paramString2);
      localStringBuilder.append(c);
    }
    return localStringBuilder.toString();
  }

  private static String upperCaseFirstLetter(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    char c = paramString.charAt(0);
    if ((i >= -1 + paramString.length()) || (Character.isLetter(c)))
    {
      if (i != paramString.length())
        break label66;
      paramString = localStringBuilder.toString();
    }
    label66: 
    while (Character.isUpperCase(c))
    {
      return paramString;
      localStringBuilder.append(c);
      i++;
      c = paramString.charAt(i);
      break;
    }
    return modifyString(Character.toUpperCase(c), paramString, i + 1);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.gson.FieldNamingPolicy
 * JD-Core Version:    0.6.2
 */