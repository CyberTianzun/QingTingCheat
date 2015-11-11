package org.jdom;

public class Text extends Content
{
  private static final String CVS_ID = "@(#) $RCSfile: Text.java,v $ $Revision: 1.25 $ $Date: 2007/11/10 05:28:59 $ $Name: jdom_1_1_1 $";
  static final String EMPTY_STRING = "";
  protected String value;

  protected Text()
  {
  }

  public Text(String paramString)
  {
    setText(paramString);
  }

  public static String normalizeString(String paramString)
  {
    if (paramString == null)
      return "";
    char[] arrayOfChar1 = paramString.toCharArray();
    char[] arrayOfChar2 = new char[arrayOfChar1.length];
    int i = 1;
    int j = 0;
    int k = 0;
    if (k < arrayOfChar1.length)
    {
      if (" \t\n\r".indexOf(arrayOfChar1[k]) != -1)
        if (i == 0)
        {
          int n = j + 1;
          arrayOfChar2[j] = ' ';
          i = 1;
          j = n;
        }
      while (true)
      {
        k++;
        break;
        int m = j + 1;
        arrayOfChar2[j] = arrayOfChar1[k];
        j = m;
        i = 0;
      }
    }
    if ((i != 0) && (j > 0))
      j--;
    return new String(arrayOfChar2, 0, j);
  }

  public void append(String paramString)
  {
    if (paramString == null)
      return;
    String str = Verifier.checkCharacterData(paramString);
    if (str != null)
      throw new IllegalDataException(paramString, "character content", str);
    if (paramString == "")
    {
      this.value = paramString;
      return;
    }
    this.value += paramString;
  }

  public void append(Text paramText)
  {
    if (paramText == null)
      return;
    this.value += paramText.getText();
  }

  public Object clone()
  {
    Text localText = (Text)super.clone();
    localText.value = this.value;
    return localText;
  }

  public String getText()
  {
    return this.value;
  }

  public String getTextNormalize()
  {
    return normalizeString(getText());
  }

  public String getTextTrim()
  {
    return getText().trim();
  }

  public String getValue()
  {
    return this.value;
  }

  public Text setText(String paramString)
  {
    if (paramString == null)
    {
      this.value = "";
      return this;
    }
    String str = Verifier.checkCharacterData(paramString);
    if (str != null)
      throw new IllegalDataException(paramString, "character content", str);
    this.value = paramString;
    return this;
  }

  public String toString()
  {
    return 64 + "[Text: " + getText() + "]";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.Text
 * JD-Core Version:    0.6.2
 */