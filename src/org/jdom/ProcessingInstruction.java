package org.jdom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jdom.output.XMLOutputter;

public class ProcessingInstruction extends Content
{
  private static final String CVS_ID = "@(#) $RCSfile: ProcessingInstruction.java,v $ $Revision: 1.47 $ $Date: 2007/11/10 05:28:59 $ $Name: jdom_1_1_1 $";
  protected Map mapData;
  protected String rawData;
  protected String target;

  protected ProcessingInstruction()
  {
  }

  public ProcessingInstruction(String paramString1, String paramString2)
  {
    setTarget(paramString1);
    setData(paramString2);
  }

  public ProcessingInstruction(String paramString, Map paramMap)
  {
    setTarget(paramString);
    setData(paramMap);
  }

  private static int[] extractQuotedString(String paramString)
  {
    int i = 0;
    int j = 34;
    int k = 0;
    int m = 0;
    if (m < paramString.length())
    {
      int n = paramString.charAt(m);
      if ((n == 34) || (n == 39))
      {
        if (i != 0)
          break label61;
        j = n;
        i = 1;
        k = m + 1;
      }
      label61: 
      while (j != n)
      {
        m++;
        break;
      }
      return new int[] { k, m };
    }
    return null;
  }

  private Map parseData(String paramString)
  {
    HashMap localHashMap = new HashMap();
    String str1 = paramString.trim();
    String str2;
    String str3;
    int i;
    char c1;
    if (!str1.trim().equals(""))
    {
      str2 = "";
      str3 = "";
      i = 0;
      c1 = str1.charAt(0);
    }
    for (int j = 1; ; j++)
    {
      char c2;
      if (j < str1.length())
      {
        c2 = str1.charAt(j);
        if (c2 == '=')
        {
          str2 = str1.substring(i, j).trim();
          int[] arrayOfInt = extractQuotedString(str1.substring(j + 1));
          if (arrayOfInt == null)
          {
            localHashMap = new HashMap();
            return localHashMap;
          }
          str3 = str1.substring(1 + (j + arrayOfInt[0]), 1 + (j + arrayOfInt[1]));
          j += 1 + arrayOfInt[1];
        }
      }
      else
      {
        str1 = str1.substring(j);
        if ((str2.length() <= 0) || (str3 == null))
          break;
        localHashMap.put(str2, str3);
        break;
      }
      if ((Character.isWhitespace(c1)) && (!Character.isWhitespace(c2)))
        i = j;
      c1 = c2;
    }
  }

  private String toString(Map paramMap)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)paramMap.get(str1);
      localStringBuffer.append(str1).append("=\"").append(str2).append("\" ");
    }
    if (localStringBuffer.length() > 0)
      localStringBuffer.setLength(-1 + localStringBuffer.length());
    return localStringBuffer.toString();
  }

  public Object clone()
  {
    ProcessingInstruction localProcessingInstruction = (ProcessingInstruction)super.clone();
    if (this.mapData != null)
      localProcessingInstruction.mapData = parseData(this.rawData);
    return localProcessingInstruction;
  }

  public String getData()
  {
    return this.rawData;
  }

  public List getPseudoAttributeNames()
  {
    Set localSet = this.mapData.entrySet();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      String str = localIterator.next().toString();
      localArrayList.add(str.substring(0, str.indexOf("=")));
    }
    return localArrayList;
  }

  public String getPseudoAttributeValue(String paramString)
  {
    return (String)this.mapData.get(paramString);
  }

  public String getTarget()
  {
    return this.target;
  }

  public String getValue()
  {
    return this.rawData;
  }

  public boolean removePseudoAttribute(String paramString)
  {
    if (this.mapData.remove(paramString) != null)
    {
      this.rawData = toString(this.mapData);
      return true;
    }
    return false;
  }

  public ProcessingInstruction setData(String paramString)
  {
    String str = Verifier.checkProcessingInstructionData(paramString);
    if (str != null)
      throw new IllegalDataException(paramString, str);
    this.rawData = paramString;
    this.mapData = parseData(paramString);
    return this;
  }

  public ProcessingInstruction setData(Map paramMap)
  {
    String str1 = toString(paramMap);
    String str2 = Verifier.checkProcessingInstructionData(str1);
    if (str2 != null)
      throw new IllegalDataException(str1, str2);
    this.rawData = str1;
    this.mapData = paramMap;
    return this;
  }

  public ProcessingInstruction setPseudoAttribute(String paramString1, String paramString2)
  {
    String str1 = Verifier.checkProcessingInstructionData(paramString1);
    if (str1 != null)
      throw new IllegalDataException(paramString1, str1);
    String str2 = Verifier.checkProcessingInstructionData(paramString2);
    if (str2 != null)
      throw new IllegalDataException(paramString2, str2);
    this.mapData.put(paramString1, paramString2);
    this.rawData = toString(this.mapData);
    return this;
  }

  public ProcessingInstruction setTarget(String paramString)
  {
    String str = Verifier.checkProcessingInstructionTarget(paramString);
    if (str != null)
      throw new IllegalTargetException(paramString, str);
    this.target = paramString;
    return this;
  }

  public String toString()
  {
    return "[ProcessingInstruction: " + new XMLOutputter().outputString(this) + "]";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.ProcessingInstruction
 * JD-Core Version:    0.6.2
 */