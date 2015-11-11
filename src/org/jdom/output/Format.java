package org.jdom.output;

import java.lang.reflect.Method;
import org.jdom.Verifier;

public class Format
  implements Cloneable
{
  private static final String CVS_ID = "@(#) $RCSfile: Format.java,v $ $Revision: 1.14 $ $Date: 2009/07/23 05:54:23 $ $Name: jdom_1_1_1 $";
  private static final String STANDARD_ENCODING = "UTF-8";
  private static final String STANDARD_INDENT = "  ";
  private static final String STANDARD_LINE_SEPARATOR = "\r\n";
  static Class class$java$lang$String;
  String encoding = "UTF-8";
  EscapeStrategy escapeStrategy = new DefaultEscapeStrategy(this.encoding);
  boolean expandEmptyElements = false;
  boolean ignoreTrAXEscapingPIs = false;
  String indent = null;
  String lineSeparator = "\r\n";
  TextMode mode = TextMode.PRESERVE;
  boolean omitDeclaration = false;
  boolean omitEncoding = false;

  static Class class$(String paramString)
  {
    try
    {
      Class localClass = Class.forName(paramString);
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
  }

  public static Format getCompactFormat()
  {
    Format localFormat = new Format();
    localFormat.setTextMode(TextMode.NORMALIZE);
    return localFormat;
  }

  public static Format getPrettyFormat()
  {
    Format localFormat = new Format();
    localFormat.setIndent("  ");
    localFormat.setTextMode(TextMode.TRIM);
    return localFormat;
  }

  public static Format getRawFormat()
  {
    return new Format();
  }

  protected Object clone()
  {
    try
    {
      Format localFormat = (Format)super.clone();
      return localFormat;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
    }
    return null;
  }

  public String getEncoding()
  {
    return this.encoding;
  }

  public EscapeStrategy getEscapeStrategy()
  {
    return this.escapeStrategy;
  }

  public boolean getExpandEmptyElements()
  {
    return this.expandEmptyElements;
  }

  public boolean getIgnoreTrAXEscapingPIs()
  {
    return this.ignoreTrAXEscapingPIs;
  }

  public String getIndent()
  {
    return this.indent;
  }

  public String getLineSeparator()
  {
    return this.lineSeparator;
  }

  public boolean getOmitDeclaration()
  {
    return this.omitDeclaration;
  }

  public boolean getOmitEncoding()
  {
    return this.omitEncoding;
  }

  public TextMode getTextMode()
  {
    return this.mode;
  }

  public Format setEncoding(String paramString)
  {
    this.encoding = paramString;
    this.escapeStrategy = new DefaultEscapeStrategy(paramString);
    return this;
  }

  public Format setEscapeStrategy(EscapeStrategy paramEscapeStrategy)
  {
    this.escapeStrategy = paramEscapeStrategy;
    return this;
  }

  public Format setExpandEmptyElements(boolean paramBoolean)
  {
    this.expandEmptyElements = paramBoolean;
    return this;
  }

  public void setIgnoreTrAXEscapingPIs(boolean paramBoolean)
  {
    this.ignoreTrAXEscapingPIs = paramBoolean;
  }

  public Format setIndent(String paramString)
  {
    this.indent = paramString;
    return this;
  }

  public Format setLineSeparator(String paramString)
  {
    this.lineSeparator = paramString;
    return this;
  }

  public Format setOmitDeclaration(boolean paramBoolean)
  {
    this.omitDeclaration = paramBoolean;
    return this;
  }

  public Format setOmitEncoding(boolean paramBoolean)
  {
    this.omitEncoding = paramBoolean;
    return this;
  }

  public Format setTextMode(TextMode paramTextMode)
  {
    this.mode = paramTextMode;
    return this;
  }

  class DefaultEscapeStrategy
    implements EscapeStrategy
  {
    private int bits;
    Method canEncode;
    Object encoder;

    public DefaultEscapeStrategy(String arg2)
    {
      String str;
      if (("UTF-8".equalsIgnoreCase(str)) || ("UTF-16".equalsIgnoreCase(str)))
      {
        this.bits = 16;
        return;
      }
      if (("ISO-8859-1".equalsIgnoreCase(str)) || ("Latin1".equalsIgnoreCase(str)))
      {
        this.bits = 8;
        return;
      }
      if (("US-ASCII".equalsIgnoreCase(str)) || ("ASCII".equalsIgnoreCase(str)))
      {
        this.bits = 7;
        return;
      }
      this.bits = 0;
      try
      {
        Class localClass1 = Class.forName("java.nio.charset.Charset");
        Class localClass2 = Class.forName("java.nio.charset.CharsetEncoder");
        Class[] arrayOfClass1 = new Class[1];
        Class localClass3;
        if (Format.class$java$lang$String == null)
        {
          localClass3 = Format.class$("java.lang.String");
          Format.class$java$lang$String = localClass3;
        }
        while (true)
        {
          arrayOfClass1[0] = localClass3;
          Object localObject = localClass1.getMethod("forName", arrayOfClass1).invoke(null, new Object[] { str });
          this.encoder = localClass1.getMethod("newEncoder", null).invoke(localObject, null);
          Class[] arrayOfClass2 = new Class[1];
          arrayOfClass2[0] = Character.TYPE;
          this.canEncode = localClass2.getMethod("canEncode", arrayOfClass2);
          return;
          localClass3 = Format.class$java$lang$String;
        }
      }
      catch (Exception localException)
      {
      }
    }

    public boolean shouldEscape(char paramChar)
    {
      if (this.bits == 16)
        if (!Verifier.isHighSurrogate(paramChar));
      while (true)
      {
        return true;
        return false;
        if (this.bits == 8)
        {
          if (paramChar <= 'ÿ')
            return false;
        }
        else if (this.bits == 7)
        {
          if (paramChar <= '')
            return false;
        }
        else if (!Verifier.isHighSurrogate(paramChar))
          if ((this.canEncode != null) && (this.encoder != null))
            try
            {
              Method localMethod = this.canEncode;
              Object localObject = this.encoder;
              Object[] arrayOfObject = new Object[1];
              arrayOfObject[0] = new Character(paramChar);
              boolean bool = ((Boolean)localMethod.invoke(localObject, arrayOfObject)).booleanValue();
              if (bool)
                return false;
            }
            catch (Exception localException)
            {
            }
      }
      return false;
    }
  }

  public static class TextMode
  {
    public static final TextMode NORMALIZE = new TextMode("NORMALIZE");
    public static final TextMode PRESERVE = new TextMode("PRESERVE");
    public static final TextMode TRIM = new TextMode("TRIM");
    public static final TextMode TRIM_FULL_WHITE = new TextMode("TRIM_FULL_WHITE");
    private final String name;

    private TextMode(String paramString)
    {
      this.name = paramString;
    }

    public String toString()
    {
      return this.name;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.output.Format
 * JD-Core Version:    0.6.2
 */