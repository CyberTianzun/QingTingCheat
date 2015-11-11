package fm.qingting.framework.utils;

import java.util.ArrayList;
import java.util.List;

public class HtmlUtils
{
  public static String formatTags(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramString.indexOf(paramArrayOfString1[0]);
    int j = paramString.indexOf(paramArrayOfString1[1]);
    int k = 0;
    while (true)
    {
      if ((i < 0) && (j < 0))
      {
        localStringBuilder.append(paramString.substring(k));
        return localStringBuilder.toString();
      }
      if (((i >= 0) && (j >= i)) || (j < 0))
        break;
      localStringBuilder.append(paramString.substring(k, j));
      k = j + paramArrayOfString1[1].length();
      j = paramString.indexOf(paramArrayOfString1[1], k);
    }
    int m = i + paramArrayOfString1[0].length();
    int n = paramString.indexOf(paramArrayOfString1[0], m);
    int i1 = paramString.indexOf(paramArrayOfString1[1], m);
    int i2 = paramString.indexOf(paramArrayOfString2[0], m);
    int i3 = paramString.indexOf(paramArrayOfString2[1], m);
    int i4 = paramString.indexOf(paramArrayOfString3[0], m);
    int i5 = tagendPos(n, i1, i2, i3, i4, paramString.indexOf(paramArrayOfString3[1], m));
    label204: if (i5 != i4)
    {
      if (i5 != n)
        break label438;
      localStringBuilder.append(paramString.substring(k, n));
      localStringBuilder.append(paramArrayOfString1[1]);
    }
    for (k = n; ; k = i3)
    {
      j = paramString.indexOf(paramArrayOfString1[1], k);
      i = paramString.indexOf(paramArrayOfString1[0], k);
      break;
      List localList = getBodyPos(paramString, paramArrayOfString3[0], paramArrayOfString3[1], i4);
      n = paramString.indexOf(paramArrayOfString1[0], ((Integer)localList.get(1)).intValue());
      i1 = paramString.indexOf(paramArrayOfString1[1], ((Integer)localList.get(1)).intValue());
      i2 = paramString.indexOf(paramArrayOfString2[0], ((Integer)localList.get(1)).intValue());
      i3 = paramString.indexOf(paramArrayOfString2[1], ((Integer)localList.get(1)).intValue());
      i4 = paramString.indexOf(paramArrayOfString3[0], ((Integer)localList.get(1)).intValue());
      i5 = tagendPos(n, i1, i2, i3, i4, paramString.indexOf(paramArrayOfString3[1], ((Integer)localList.get(1)).intValue()));
      break label204;
      label438: if (i5 != i3)
        break label475;
      localStringBuilder.append(paramString.substring(k, i3));
      localStringBuilder.append(paramArrayOfString1[1]);
    }
    label475: int i6;
    if (i5 == i1)
      i6 = paramArrayOfString1[1].length();
    while (true)
    {
      localStringBuilder.append(paramString.substring(k, i5 + i6));
      k = i5 + i6;
      break;
      if (i5 == i2)
        i6 = paramArrayOfString2[0].length();
      else if (i5 == i4)
        i6 = paramArrayOfString3[0].length();
      else
        i6 = paramArrayOfString3[1].length();
    }
  }

  public static List<Integer> getBodyPos(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    int i = 0;
    int j = paramString1.indexOf(paramString2, paramInt);
    ArrayList localArrayList = null;
    int k;
    int m;
    int n;
    label57: int i1;
    label65: int i2;
    if (j >= 0)
    {
      k = paramString1.indexOf(paramString2, j + paramString2.length());
      m = paramString1.indexOf(paramString3, j + paramString2.length());
      if (k >= m)
        break label194;
      n = 1;
      if (k < 0)
        break label200;
      i1 = 1;
      i2 = n & i1;
      if (i > 100)
        break label206;
    }
    label194: label200: label206: for (int i3 = 1; ; i3 = 0)
    {
      if ((i3 & i2) == 0)
      {
        int i5 = m + paramString3.length();
        localArrayList = new ArrayList();
        localArrayList.add(Integer.valueOf(j));
        localArrayList.add(Integer.valueOf(i5));
        return localArrayList;
      }
      int i4 = ((Integer)getBodyPos(paramString1, paramString2, paramString3, k).get(1)).intValue();
      k = paramString1.indexOf(paramString2, i4);
      m = paramString1.indexOf(paramString3, i4);
      if (m < 0)
        m = paramString1.length();
      i++;
      break;
      n = 0;
      break label57;
      i1 = 0;
      break label65;
    }
  }

  public static int tagendPos(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = paramInt1;
    if (((paramInt2 < i) && (paramInt2 >= 0)) || (i < 0))
      i = paramInt2;
    if (((paramInt3 < i) && (paramInt3 >= 0)) || (i < 0))
      i = paramInt3;
    if (((paramInt4 < i) && (paramInt4 >= 0)) || (i < 0))
      i = paramInt4;
    if (((paramInt5 < i) && (paramInt5 >= 0)) || (i < 0))
      i = paramInt5;
    if (((paramInt6 < i) && (paramInt6 >= 0)) || (i < 0))
      i = paramInt6;
    return i;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.utils.HtmlUtils
 * JD-Core Version:    0.6.2
 */