package fm.qingting.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.SpannableString;
import fm.qingting.qtradio.R.drawable;
import fm.qingting.qtradio.view.chatroom.ExpressionSpan;
import fm.qingting.qtradio.view.chatroom.expression.ExpressionItem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionUtil
{
  private static ExpressionUtil instance;
  private List<ExpressionItem> expressionList;
  private HashMap<String, String> expressionMap;
  private int mOwenerId;

  private void getExpressionList(Context paramContext)
  {
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramContext.getResources().openRawResource(2131165185), "UTF-8"));
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          break;
        String[] arrayOfString = str.split(",");
        if (this.expressionList == null)
          this.expressionList = new ArrayList();
        try
        {
          this.expressionList.add(new ExpressionItem(arrayOfString[0], arrayOfString[1]));
        }
        catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
        {
          localIndexOutOfBoundsException.printStackTrace();
        }
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      this.expressionMap = list2hash();
    }
  }

  public static ExpressionUtil getInstance()
  {
    if (instance == null)
      instance = new ExpressionUtil();
    return instance;
  }

  private List<ExpressionItem> hash2List()
  {
    if (this.expressionMap == null)
      return null;
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.expressionMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localArrayList.add(new ExpressionItem((String)localEntry.getKey(), (String)localEntry.getValue()));
    }
    return localArrayList;
  }

  private HashMap<String, String> list2hash()
  {
    if (this.expressionList == null)
      return null;
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.expressionList.iterator();
    while (localIterator.hasNext())
    {
      ExpressionItem localExpressionItem = (ExpressionItem)localIterator.next();
      localHashMap.put(localExpressionItem.getExpressionName(), localExpressionItem.getExpressionIcon());
    }
    return localHashMap;
  }

  public void dealExpression(Context paramContext, SpannableString paramSpannableString, Pattern paramPattern, int paramInt1, int paramInt2, int paramInt3)
    throws SecurityException, NoSuchFieldException, NumberFormatException, IllegalArgumentException, IllegalAccessException, StackOverflowError
  {
    Matcher localMatcher = paramPattern.matcher(paramSpannableString);
    while (true)
    {
      ExpressionSpan localExpressionSpan;
      int j;
      int k;
      if (localMatcher.find())
      {
        String str1 = localMatcher.group();
        if (localMatcher.start() >= paramInt1)
        {
          String str2 = (String)this.expressionMap.get(str1);
          if (str2 == null)
          {
            int i1 = 1 + localMatcher.start();
            if (i1 < paramSpannableString.length())
              dealExpression(paramContext, paramSpannableString, paramPattern, i1, paramInt2, paramInt3);
          }
          else
          {
            int i = Integer.parseInt(R.drawable.class.getDeclaredField(str2).get(null).toString());
            if (i != 0)
            {
              localExpressionSpan = new ExpressionSpan(paramContext.getResources(), i, paramInt2, paramInt3, this.mOwenerId);
              j = str1.length();
              k = localMatcher.start();
              if (j <= 0);
            }
          }
        }
      }
      else
      {
        while (true)
        {
          int m = k + j;
          paramSpannableString.setSpan(localExpressionSpan, localMatcher.start(), m, 17);
          if (m < paramSpannableString.length())
            dealExpression(paramContext, paramSpannableString, paramPattern, m, paramInt2, paramInt3);
          return;
          j = 1;
        }
        int n = 1 + localMatcher.start();
        if (n < paramSpannableString.length())
          dealExpression(paramContext, paramSpannableString, paramPattern, n, paramInt2, paramInt3);
      }
    }
  }

  public int getExpressionCnt()
  {
    if (this.expressionList == null)
      return 0;
    return this.expressionList.size();
  }

  public SpannableString getExpressionString(Context paramContext, String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    SpannableString localSpannableString = new SpannableString(paramString1);
    if (this.expressionMap == null)
      return localSpannableString;
    Pattern localPattern = Pattern.compile(paramString2, 2);
    try
    {
      dealExpression(paramContext, localSpannableString, localPattern, 0, paramInt1, paramInt2);
      return localSpannableString;
    }
    catch (Exception localException)
    {
      return localSpannableString;
    }
    catch (Error localError)
    {
    }
    return localSpannableString;
  }

  public List<ExpressionItem> getExpressionSubList(int paramInt1, int paramInt2)
  {
    if (this.expressionList == null);
    while ((paramInt1 < 0) || (paramInt2 > this.expressionList.size()))
      return null;
    return this.expressionList.subList(paramInt1, paramInt2);
  }

  public int getOwnerId()
  {
    return this.mOwenerId;
  }

  public void init(Context paramContext, int paramInt)
  {
    if (this.expressionMap == null)
      getExpressionList(paramContext);
    this.mOwenerId = paramInt;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.ExpressionUtil
 * JD-Core Version:    0.6.2
 */