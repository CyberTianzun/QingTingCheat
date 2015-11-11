package org.jdom.input;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import org.jdom.DefaultJDOMFactory;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.JDOMFactory;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

public class SAXBuilder
{
  private static final String CVS_ID = "@(#) $RCSfile: SAXBuilder.java,v $ $Revision: 1.93 $ $Date: 2009/07/23 06:26:26 $ $Name: jdom_1_1_1 $";
  private static final String DEFAULT_SAX_DRIVER = "org.apache.xerces.parsers.SAXParser";
  static Class class$java$util$Map;
  private boolean expand = true;
  private JDOMFactory factory = new DefaultJDOMFactory();
  private boolean fastReconfigure = false;
  private HashMap features = new HashMap(5);
  private boolean ignoringBoundaryWhite = false;
  private boolean ignoringWhite = false;
  private HashMap properties = new HashMap(5);
  private boolean reuseParser = true;
  private DTDHandler saxDTDHandler = null;
  private String saxDriverClass;
  private EntityResolver saxEntityResolver = null;
  private ErrorHandler saxErrorHandler = null;
  private XMLReader saxParser = null;
  private XMLFilter saxXMLFilter = null;
  private boolean skipNextEntityExpandConfig = false;
  private boolean skipNextLexicalReportingConfig = false;
  private boolean validate;

  public SAXBuilder()
  {
    this(false);
  }

  public SAXBuilder(String paramString)
  {
    this(paramString, false);
  }

  public SAXBuilder(String paramString, boolean paramBoolean)
  {
    this.saxDriverClass = paramString;
    this.validate = paramBoolean;
  }

  public SAXBuilder(boolean paramBoolean)
  {
    this.validate = paramBoolean;
  }

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

  private static URL fileToURL(File paramFile)
    throws MalformedURLException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = paramFile.getAbsolutePath();
    if (File.separatorChar != '/')
      str = str.replace(File.separatorChar, '/');
    if (!str.startsWith("/"))
      localStringBuffer.append('/');
    int i = str.length();
    int j = 0;
    if (j < i)
    {
      char c = str.charAt(j);
      if (c == ' ')
        localStringBuffer.append("%20");
      while (true)
      {
        j++;
        break;
        if (c == '#')
          localStringBuffer.append("%23");
        else if (c == '%')
          localStringBuffer.append("%25");
        else if (c == '&')
          localStringBuffer.append("%26");
        else if (c == ';')
          localStringBuffer.append("%3B");
        else if (c == '<')
          localStringBuffer.append("%3C");
        else if (c == '=')
          localStringBuffer.append("%3D");
        else if (c == '>')
          localStringBuffer.append("%3E");
        else if (c == '?')
          localStringBuffer.append("%3F");
        else if (c == '~')
          localStringBuffer.append("%7E");
        else
          localStringBuffer.append(c);
      }
    }
    if ((!str.endsWith("/")) && (paramFile.isDirectory()))
      localStringBuffer.append('/');
    return new URL("file", "", localStringBuffer.toString());
  }

  private void internalSetFeature(XMLReader paramXMLReader, String paramString1, boolean paramBoolean, String paramString2)
    throws JDOMException
  {
    try
    {
      paramXMLReader.setFeature(paramString1, paramBoolean);
      return;
    }
    catch (SAXNotSupportedException localSAXNotSupportedException)
    {
      throw new JDOMException(paramString2 + " feature not supported for SAX driver " + paramXMLReader.getClass().getName());
    }
    catch (SAXNotRecognizedException localSAXNotRecognizedException)
    {
    }
    throw new JDOMException(paramString2 + " feature not recognized for SAX driver " + paramXMLReader.getClass().getName());
  }

  private void internalSetProperty(XMLReader paramXMLReader, String paramString1, Object paramObject, String paramString2)
    throws JDOMException
  {
    try
    {
      paramXMLReader.setProperty(paramString1, paramObject);
      return;
    }
    catch (SAXNotSupportedException localSAXNotSupportedException)
    {
      throw new JDOMException(paramString2 + " property not supported for SAX driver " + paramXMLReader.getClass().getName());
    }
    catch (SAXNotRecognizedException localSAXNotRecognizedException)
    {
    }
    throw new JDOMException(paramString2 + " property not recognized for SAX driver " + paramXMLReader.getClass().getName());
  }

  // ERROR //
  private void setFeaturesAndProperties(XMLReader paramXMLReader, boolean paramBoolean)
    throws JDOMException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 75	org/jdom/input/SAXBuilder:features	Ljava/util/HashMap;
    //   4: invokevirtual 231	java/util/HashMap:keySet	()Ljava/util/Set;
    //   7: invokeinterface 237 1 0
    //   12: astore_3
    //   13: aload_3
    //   14: invokeinterface 242 1 0
    //   19: ifeq +41 -> 60
    //   22: aload_3
    //   23: invokeinterface 246 1 0
    //   28: checkcast 126	java/lang/String
    //   31: astore 9
    //   33: aload_0
    //   34: aload_1
    //   35: aload 9
    //   37: aload_0
    //   38: getfield 75	org/jdom/input/SAXBuilder:features	Ljava/util/HashMap;
    //   41: aload 9
    //   43: invokevirtual 250	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   46: checkcast 252	java/lang/Boolean
    //   49: invokevirtual 255	java/lang/Boolean:booleanValue	()Z
    //   52: aload 9
    //   54: invokespecial 257	org/jdom/input/SAXBuilder:internalSetFeature	(Lorg/xml/sax/XMLReader;Ljava/lang/String;ZLjava/lang/String;)V
    //   57: goto -44 -> 13
    //   60: aload_0
    //   61: getfield 77	org/jdom/input/SAXBuilder:properties	Ljava/util/HashMap;
    //   64: invokevirtual 231	java/util/HashMap:keySet	()Ljava/util/Set;
    //   67: invokeinterface 237 1 0
    //   72: astore 4
    //   74: aload 4
    //   76: invokeinterface 242 1 0
    //   81: ifeq +36 -> 117
    //   84: aload 4
    //   86: invokeinterface 246 1 0
    //   91: checkcast 126	java/lang/String
    //   94: astore 8
    //   96: aload_0
    //   97: aload_1
    //   98: aload 8
    //   100: aload_0
    //   101: getfield 77	org/jdom/input/SAXBuilder:properties	Ljava/util/HashMap;
    //   104: aload 8
    //   106: invokevirtual 250	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   109: aload 8
    //   111: invokespecial 259	org/jdom/input/SAXBuilder:internalSetProperty	(Lorg/xml/sax/XMLReader;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)V
    //   114: goto -40 -> 74
    //   117: iload_2
    //   118: ifeq +42 -> 160
    //   121: aload_0
    //   122: aload_1
    //   123: ldc_w 261
    //   126: aload_0
    //   127: getfield 91	org/jdom/input/SAXBuilder:validate	Z
    //   130: ldc_w 263
    //   133: invokespecial 257	org/jdom/input/SAXBuilder:internalSetFeature	(Lorg/xml/sax/XMLReader;Ljava/lang/String;ZLjava/lang/String;)V
    //   136: aload_0
    //   137: aload_1
    //   138: ldc_w 265
    //   141: iconst_1
    //   142: ldc_w 267
    //   145: invokespecial 257	org/jdom/input/SAXBuilder:internalSetFeature	(Lorg/xml/sax/XMLReader;Ljava/lang/String;ZLjava/lang/String;)V
    //   148: aload_0
    //   149: aload_1
    //   150: ldc_w 269
    //   153: iconst_1
    //   154: ldc_w 271
    //   157: invokespecial 257	org/jdom/input/SAXBuilder:internalSetFeature	(Lorg/xml/sax/XMLReader;Ljava/lang/String;ZLjava/lang/String;)V
    //   160: aload_1
    //   161: ldc_w 273
    //   164: invokeinterface 276 2 0
    //   169: aload_0
    //   170: getfield 51	org/jdom/input/SAXBuilder:expand	Z
    //   173: if_icmpeq +16 -> 189
    //   176: aload_1
    //   177: ldc_w 273
    //   180: aload_0
    //   181: getfield 51	org/jdom/input/SAXBuilder:expand	Z
    //   184: invokeinterface 203 3 0
    //   189: return
    //   190: astore 7
    //   192: aload_0
    //   193: getfield 91	org/jdom/input/SAXBuilder:validate	Z
    //   196: ifeq -60 -> 136
    //   199: aload 7
    //   201: athrow
    //   202: astore 6
    //   204: return
    //   205: astore 5
    //   207: return
    //
    // Exception table:
    //   from	to	target	type
    //   121	136	190	org/jdom/JDOMException
    //   160	189	202	org/xml/sax/SAXNotSupportedException
    //   160	189	205	org/xml/sax/SAXNotRecognizedException
  }

  public Document build(File paramFile)
    throws JDOMException, IOException
  {
    try
    {
      Document localDocument = build(fileToURL(paramFile));
      return localDocument;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      throw new JDOMException("Error in building", localMalformedURLException);
    }
  }

  public Document build(InputStream paramInputStream)
    throws JDOMException, IOException
  {
    return build(new InputSource(paramInputStream));
  }

  public Document build(InputStream paramInputStream, String paramString)
    throws JDOMException, IOException
  {
    InputSource localInputSource = new InputSource(paramInputStream);
    localInputSource.setSystemId(paramString);
    return build(localInputSource);
  }

  public Document build(Reader paramReader)
    throws JDOMException, IOException
  {
    return build(new InputSource(paramReader));
  }

  public Document build(Reader paramReader, String paramString)
    throws JDOMException, IOException
  {
    InputSource localInputSource = new InputSource(paramReader);
    localInputSource.setSystemId(paramString);
    return build(localInputSource);
  }

  public Document build(String paramString)
    throws JDOMException, IOException
  {
    return build(new InputSource(paramString));
  }

  public Document build(URL paramURL)
    throws JDOMException, IOException
  {
    return build(new InputSource(paramURL.toExternalForm()));
  }

  // ERROR //
  public Document build(InputSource paramInputSource)
    throws JDOMException, IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: invokevirtual 321	org/jdom/input/SAXBuilder:createContentHandler	()Lorg/jdom/input/SAXHandler;
    //   6: astore_2
    //   7: aload_0
    //   8: aload_2
    //   9: invokevirtual 325	org/jdom/input/SAXBuilder:configureContentHandler	(Lorg/jdom/input/SAXHandler;)V
    //   12: aload_0
    //   13: getfield 87	org/jdom/input/SAXBuilder:saxParser	Lorg/xml/sax/XMLReader;
    //   16: astore 8
    //   18: aload 8
    //   20: ifnonnull +98 -> 118
    //   23: aload_0
    //   24: invokevirtual 329	org/jdom/input/SAXBuilder:createParser	()Lorg/xml/sax/XMLReader;
    //   27: astore 8
    //   29: aload_0
    //   30: getfield 59	org/jdom/input/SAXBuilder:saxXMLFilter	Lorg/xml/sax/XMLFilter;
    //   33: ifnull +52 -> 85
    //   36: aload_0
    //   37: getfield 59	org/jdom/input/SAXBuilder:saxXMLFilter	Lorg/xml/sax/XMLFilter;
    //   40: astore 9
    //   42: aload 9
    //   44: invokeinterface 334 1 0
    //   49: instanceof 331
    //   52: ifeq +18 -> 70
    //   55: aload 9
    //   57: invokeinterface 334 1 0
    //   62: checkcast 331	org/xml/sax/XMLFilter
    //   65: astore 9
    //   67: goto -25 -> 42
    //   70: aload 9
    //   72: aload 8
    //   74: invokeinterface 338 2 0
    //   79: aload_0
    //   80: getfield 59	org/jdom/input/SAXBuilder:saxXMLFilter	Lorg/xml/sax/XMLFilter;
    //   83: astore 8
    //   85: aload_0
    //   86: aload 8
    //   88: aload_2
    //   89: invokevirtual 342	org/jdom/input/SAXBuilder:configureParser	(Lorg/xml/sax/XMLReader;Lorg/jdom/input/SAXHandler;)V
    //   92: aload_0
    //   93: getfield 85	org/jdom/input/SAXBuilder:reuseParser	Z
    //   96: ifeq +9 -> 105
    //   99: aload_0
    //   100: aload 8
    //   102: putfield 87	org/jdom/input/SAXBuilder:saxParser	Lorg/xml/sax/XMLReader;
    //   105: aload 8
    //   107: aload_1
    //   108: invokeinterface 346 2 0
    //   113: aload_2
    //   114: invokevirtual 352	org/jdom/input/SAXHandler:getDocument	()Lorg/jdom/Document;
    //   117: areturn
    //   118: aload_0
    //   119: aload 8
    //   121: aload_2
    //   122: invokevirtual 342	org/jdom/input/SAXBuilder:configureParser	(Lorg/xml/sax/XMLReader;Lorg/jdom/input/SAXHandler;)V
    //   125: goto -20 -> 105
    //   128: astore 5
    //   130: aload_2
    //   131: invokevirtual 352	org/jdom/input/SAXHandler:getDocument	()Lorg/jdom/Document;
    //   134: astore 6
    //   136: aload 6
    //   138: invokevirtual 357	org/jdom/Document:hasRootElement	()Z
    //   141: ifne +6 -> 147
    //   144: aconst_null
    //   145: astore 6
    //   147: aload 5
    //   149: invokevirtual 360	org/xml/sax/SAXParseException:getSystemId	()Ljava/lang/String;
    //   152: astore 7
    //   154: aload 7
    //   156: ifnull +55 -> 211
    //   159: new 362	org/jdom/input/JDOMParseException
    //   162: dup
    //   163: new 114	java/lang/StringBuffer
    //   166: dup
    //   167: invokespecial 115	java/lang/StringBuffer:<init>	()V
    //   170: ldc_w 364
    //   173: invokevirtual 153	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   176: aload 5
    //   178: invokevirtual 367	org/xml/sax/SAXParseException:getLineNumber	()I
    //   181: invokevirtual 370	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   184: ldc_w 372
    //   187: invokevirtual 153	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   190: aload 7
    //   192: invokevirtual 153	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   195: invokevirtual 187	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   198: aload 5
    //   200: aload 6
    //   202: invokespecial 375	org/jdom/input/JDOMParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Lorg/jdom/Document;)V
    //   205: athrow
    //   206: astore 4
    //   208: aload 4
    //   210: athrow
    //   211: new 362	org/jdom/input/JDOMParseException
    //   214: dup
    //   215: new 114	java/lang/StringBuffer
    //   218: dup
    //   219: invokespecial 115	java/lang/StringBuffer:<init>	()V
    //   222: ldc_w 364
    //   225: invokevirtual 153	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   228: aload 5
    //   230: invokevirtual 367	org/xml/sax/SAXParseException:getLineNumber	()I
    //   233: invokevirtual 370	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   236: invokevirtual 187	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   239: aload 5
    //   241: aload 6
    //   243: invokespecial 375	org/jdom/input/JDOMParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Lorg/jdom/Document;)V
    //   246: athrow
    //   247: astore_3
    //   248: new 362	org/jdom/input/JDOMParseException
    //   251: dup
    //   252: new 114	java/lang/StringBuffer
    //   255: dup
    //   256: invokespecial 115	java/lang/StringBuffer:<init>	()V
    //   259: ldc_w 377
    //   262: invokevirtual 153	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   265: aload_3
    //   266: invokevirtual 378	org/xml/sax/SAXException:getMessage	()Ljava/lang/String;
    //   269: invokevirtual 153	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   272: invokevirtual 187	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   275: aload_3
    //   276: aload_2
    //   277: invokevirtual 352	org/jdom/input/SAXHandler:getDocument	()Lorg/jdom/Document;
    //   280: invokespecial 375	org/jdom/input/JDOMParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;Lorg/jdom/Document;)V
    //   283: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   2	18	128	org/xml/sax/SAXParseException
    //   23	42	128	org/xml/sax/SAXParseException
    //   42	67	128	org/xml/sax/SAXParseException
    //   70	85	128	org/xml/sax/SAXParseException
    //   85	105	128	org/xml/sax/SAXParseException
    //   105	118	128	org/xml/sax/SAXParseException
    //   118	125	128	org/xml/sax/SAXParseException
    //   2	18	206	finally
    //   23	42	206	finally
    //   42	67	206	finally
    //   70	85	206	finally
    //   85	105	206	finally
    //   105	118	206	finally
    //   118	125	206	finally
    //   130	144	206	finally
    //   147	154	206	finally
    //   159	206	206	finally
    //   211	247	206	finally
    //   248	284	206	finally
    //   2	18	247	org/xml/sax/SAXException
    //   23	42	247	org/xml/sax/SAXException
    //   42	67	247	org/xml/sax/SAXException
    //   70	85	247	org/xml/sax/SAXException
    //   85	105	247	org/xml/sax/SAXException
    //   105	118	247	org/xml/sax/SAXException
    //   118	125	247	org/xml/sax/SAXException
  }

  protected void configureContentHandler(SAXHandler paramSAXHandler)
  {
    paramSAXHandler.setExpandEntities(this.expand);
    paramSAXHandler.setIgnoringElementContentWhitespace(this.ignoringWhite);
    paramSAXHandler.setIgnoringBoundaryWhitespace(this.ignoringBoundaryWhite);
  }

  protected void configureParser(XMLReader paramXMLReader, SAXHandler paramSAXHandler)
    throws JDOMException
  {
    paramXMLReader.setContentHandler(paramSAXHandler);
    if (this.saxEntityResolver != null)
      paramXMLReader.setEntityResolver(this.saxEntityResolver);
    if (this.saxDTDHandler != null)
      paramXMLReader.setDTDHandler(this.saxDTDHandler);
    while (true)
    {
      if (this.saxErrorHandler != null)
      {
        paramXMLReader.setErrorHandler(this.saxErrorHandler);
        if (this.skipNextLexicalReportingConfig);
      }
      try
      {
        paramXMLReader.setProperty("http://xml.org/sax/handlers/LexicalHandler", paramSAXHandler);
        j = 1;
        if (j == 0);
        try
        {
          paramXMLReader.setProperty("http://xml.org/sax/properties/lexical-handler", paramSAXHandler);
          j = 1;
          label96: if ((j == 0) && (this.fastReconfigure))
            this.skipNextLexicalReportingConfig = true;
          int i;
          if (!this.skipNextEntityExpandConfig)
          {
            boolean bool = this.expand;
            i = 0;
            if (bool);
          }
          try
          {
            paramXMLReader.setProperty("http://xml.org/sax/properties/declaration-handler", paramSAXHandler);
            i = 1;
            if ((i == 0) && (this.fastReconfigure))
              this.skipNextEntityExpandConfig = true;
            return;
            paramXMLReader.setDTDHandler(paramSAXHandler);
            continue;
            paramXMLReader.setErrorHandler(new BuilderErrorHandler());
          }
          catch (SAXNotRecognizedException localSAXNotRecognizedException1)
          {
            while (true)
              i = 0;
          }
          catch (SAXNotSupportedException localSAXNotSupportedException1)
          {
            while (true)
              i = 0;
          }
        }
        catch (SAXNotRecognizedException localSAXNotRecognizedException2)
        {
          break label96;
        }
        catch (SAXNotSupportedException localSAXNotSupportedException3)
        {
          break label96;
        }
      }
      catch (SAXNotRecognizedException localSAXNotRecognizedException3)
      {
        while (true)
          j = 0;
      }
      catch (SAXNotSupportedException localSAXNotSupportedException2)
      {
        while (true)
          int j = 0;
      }
    }
  }

  protected SAXHandler createContentHandler()
  {
    return new SAXHandler(this.factory);
  }

  // ERROR //
  protected XMLReader createParser()
    throws JDOMException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: getfield 89	org/jdom/input/SAXBuilder:saxDriverClass	Ljava/lang/String;
    //   6: ifnull +81 -> 87
    //   9: aload_0
    //   10: getfield 89	org/jdom/input/SAXBuilder:saxDriverClass	Ljava/lang/String;
    //   13: invokestatic 423	org/xml/sax/helpers/XMLReaderFactory:createXMLReader	(Ljava/lang/String;)Lorg/xml/sax/XMLReader;
    //   16: astore_1
    //   17: aload_0
    //   18: aload_1
    //   19: iconst_1
    //   20: invokespecial 425	org/jdom/input/SAXBuilder:setFeaturesAndProperties	(Lorg/xml/sax/XMLReader;Z)V
    //   23: aload_1
    //   24: ifnonnull +26 -> 50
    //   27: ldc 11
    //   29: invokestatic 423	org/xml/sax/helpers/XMLReaderFactory:createXMLReader	(Ljava/lang/String;)Lorg/xml/sax/XMLReader;
    //   32: astore_1
    //   33: aload_0
    //   34: aload_1
    //   35: invokevirtual 209	java/lang/Object:getClass	()Ljava/lang/Class;
    //   38: invokevirtual 212	java/lang/Class:getName	()Ljava/lang/String;
    //   41: putfield 89	org/jdom/input/SAXBuilder:saxDriverClass	Ljava/lang/String;
    //   44: aload_0
    //   45: aload_1
    //   46: iconst_1
    //   47: invokespecial 425	org/jdom/input/SAXBuilder:setFeaturesAndProperties	(Lorg/xml/sax/XMLReader;Z)V
    //   50: aload_1
    //   51: areturn
    //   52: astore 14
    //   54: new 194	org/jdom/JDOMException
    //   57: dup
    //   58: new 114	java/lang/StringBuffer
    //   61: dup
    //   62: invokespecial 115	java/lang/StringBuffer:<init>	()V
    //   65: ldc_w 427
    //   68: invokevirtual 153	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   71: aload_0
    //   72: getfield 89	org/jdom/input/SAXBuilder:saxDriverClass	Ljava/lang/String;
    //   75: invokevirtual 153	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   78: invokevirtual 187	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   81: aload 14
    //   83: invokespecial 290	org/jdom/JDOMException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   86: athrow
    //   87: ldc_w 429
    //   90: invokestatic 100	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   93: astore 6
    //   95: iconst_3
    //   96: anewarray 97	java/lang/Class
    //   99: astore 7
    //   101: aload 7
    //   103: iconst_0
    //   104: getstatic 432	java/lang/Boolean:TYPE	Ljava/lang/Class;
    //   107: aastore
    //   108: getstatic 434	org/jdom/input/SAXBuilder:class$java$util$Map	Ljava/lang/Class;
    //   111: ifnonnull +131 -> 242
    //   114: ldc_w 436
    //   117: invokestatic 438	org/jdom/input/SAXBuilder:class$	(Ljava/lang/String;)Ljava/lang/Class;
    //   120: astore 8
    //   122: aload 8
    //   124: putstatic 434	org/jdom/input/SAXBuilder:class$java$util$Map	Ljava/lang/Class;
    //   127: aload 7
    //   129: iconst_1
    //   130: aload 8
    //   132: aastore
    //   133: getstatic 434	org/jdom/input/SAXBuilder:class$java$util$Map	Ljava/lang/Class;
    //   136: ifnonnull +114 -> 250
    //   139: ldc_w 436
    //   142: invokestatic 438	org/jdom/input/SAXBuilder:class$	(Ljava/lang/String;)Ljava/lang/Class;
    //   145: astore 9
    //   147: aload 9
    //   149: putstatic 434	org/jdom/input/SAXBuilder:class$java$util$Map	Ljava/lang/Class;
    //   152: aload 7
    //   154: iconst_2
    //   155: aload 9
    //   157: aastore
    //   158: aload 6
    //   160: ldc_w 439
    //   163: aload 7
    //   165: invokevirtual 443	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   168: astore 10
    //   170: iconst_3
    //   171: anewarray 4	java/lang/Object
    //   174: astore 11
    //   176: aload_0
    //   177: getfield 91	org/jdom/input/SAXBuilder:validate	Z
    //   180: istore 12
    //   182: aconst_null
    //   183: astore_1
    //   184: iload 12
    //   186: ifeq +72 -> 258
    //   189: getstatic 447	java/lang/Boolean:TRUE	Ljava/lang/Boolean;
    //   192: astore 13
    //   194: aload 11
    //   196: iconst_0
    //   197: aload 13
    //   199: aastore
    //   200: aload 11
    //   202: iconst_1
    //   203: aload_0
    //   204: getfield 75	org/jdom/input/SAXBuilder:features	Ljava/util/HashMap;
    //   207: aastore
    //   208: aload 11
    //   210: iconst_2
    //   211: aload_0
    //   212: getfield 77	org/jdom/input/SAXBuilder:properties	Ljava/util/HashMap;
    //   215: aastore
    //   216: aload 10
    //   218: aconst_null
    //   219: aload 11
    //   221: invokevirtual 453	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   224: checkcast 200	org/xml/sax/XMLReader
    //   227: astore_1
    //   228: aload_0
    //   229: aload_1
    //   230: iconst_0
    //   231: invokespecial 425	org/jdom/input/SAXBuilder:setFeaturesAndProperties	(Lorg/xml/sax/XMLReader;Z)V
    //   234: goto -211 -> 23
    //   237: astore 5
    //   239: aload 5
    //   241: athrow
    //   242: getstatic 434	org/jdom/input/SAXBuilder:class$java$util$Map	Ljava/lang/Class;
    //   245: astore 8
    //   247: goto -120 -> 127
    //   250: getstatic 434	org/jdom/input/SAXBuilder:class$java$util$Map	Ljava/lang/Class;
    //   253: astore 9
    //   255: goto -103 -> 152
    //   258: getstatic 456	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
    //   261: astore 13
    //   263: goto -69 -> 194
    //   266: astore_3
    //   267: new 194	org/jdom/JDOMException
    //   270: dup
    //   271: ldc_w 458
    //   274: aload_3
    //   275: invokespecial 290	org/jdom/JDOMException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   278: athrow
    //   279: astore 4
    //   281: goto -258 -> 23
    //   284: astore_2
    //   285: goto -262 -> 23
    //
    // Exception table:
    //   from	to	target	type
    //   9	23	52	org/xml/sax/SAXException
    //   87	127	237	org/jdom/JDOMException
    //   127	152	237	org/jdom/JDOMException
    //   152	182	237	org/jdom/JDOMException
    //   189	194	237	org/jdom/JDOMException
    //   194	234	237	org/jdom/JDOMException
    //   242	247	237	org/jdom/JDOMException
    //   250	255	237	org/jdom/JDOMException
    //   258	263	237	org/jdom/JDOMException
    //   27	50	266	org/xml/sax/SAXException
    //   87	127	279	java/lang/Exception
    //   127	152	279	java/lang/Exception
    //   152	182	279	java/lang/Exception
    //   189	194	279	java/lang/Exception
    //   194	234	279	java/lang/Exception
    //   242	247	279	java/lang/Exception
    //   250	255	279	java/lang/Exception
    //   258	263	279	java/lang/Exception
    //   87	127	284	java/lang/NoClassDefFoundError
    //   127	152	284	java/lang/NoClassDefFoundError
    //   152	182	284	java/lang/NoClassDefFoundError
    //   189	194	284	java/lang/NoClassDefFoundError
    //   194	234	284	java/lang/NoClassDefFoundError
    //   242	247	284	java/lang/NoClassDefFoundError
    //   250	255	284	java/lang/NoClassDefFoundError
    //   258	263	284	java/lang/NoClassDefFoundError
  }

  public DTDHandler getDTDHandler()
  {
    return this.saxDTDHandler;
  }

  public String getDriverClass()
  {
    return this.saxDriverClass;
  }

  public EntityResolver getEntityResolver()
  {
    return this.saxEntityResolver;
  }

  public ErrorHandler getErrorHandler()
  {
    return this.saxErrorHandler;
  }

  public boolean getExpandEntities()
  {
    return this.expand;
  }

  public JDOMFactory getFactory()
  {
    return this.factory;
  }

  public boolean getIgnoringBoundaryWhitespace()
  {
    return this.ignoringBoundaryWhite;
  }

  public boolean getIgnoringElementContentWhitespace()
  {
    return this.ignoringWhite;
  }

  public boolean getReuseParser()
  {
    return this.reuseParser;
  }

  public boolean getValidation()
  {
    return this.validate;
  }

  public XMLFilter getXMLFilter()
  {
    return this.saxXMLFilter;
  }

  public void setDTDHandler(DTDHandler paramDTDHandler)
  {
    this.saxDTDHandler = paramDTDHandler;
  }

  public void setEntityResolver(EntityResolver paramEntityResolver)
  {
    this.saxEntityResolver = paramEntityResolver;
  }

  public void setErrorHandler(ErrorHandler paramErrorHandler)
  {
    this.saxErrorHandler = paramErrorHandler;
  }

  public void setExpandEntities(boolean paramBoolean)
  {
    this.expand = paramBoolean;
  }

  public void setFactory(JDOMFactory paramJDOMFactory)
  {
    this.factory = paramJDOMFactory;
  }

  public void setFastReconfigure(boolean paramBoolean)
  {
    if (this.reuseParser)
      this.fastReconfigure = paramBoolean;
  }

  public void setFeature(String paramString, boolean paramBoolean)
  {
    HashMap localHashMap = this.features;
    if (paramBoolean);
    for (Boolean localBoolean = Boolean.TRUE; ; localBoolean = Boolean.FALSE)
    {
      localHashMap.put(paramString, localBoolean);
      return;
    }
  }

  public void setIgnoringBoundaryWhitespace(boolean paramBoolean)
  {
    this.ignoringBoundaryWhite = paramBoolean;
  }

  public void setIgnoringElementContentWhitespace(boolean paramBoolean)
  {
    this.ignoringWhite = paramBoolean;
  }

  public void setProperty(String paramString, Object paramObject)
  {
    this.properties.put(paramString, paramObject);
  }

  public void setReuseParser(boolean paramBoolean)
  {
    this.reuseParser = paramBoolean;
    this.saxParser = null;
  }

  public void setValidation(boolean paramBoolean)
  {
    this.validate = paramBoolean;
  }

  public void setXMLFilter(XMLFilter paramXMLFilter)
  {
    this.saxXMLFilter = paramXMLFilter;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.input.SAXBuilder
 * JD-Core Version:    0.6.2
 */