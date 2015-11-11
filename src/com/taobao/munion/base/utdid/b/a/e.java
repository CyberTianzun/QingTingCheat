package com.taobao.munion.base.utdid.b.a;

import android.util.Xml;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

class e
{
  public static final int a(CharSequence paramCharSequence)
  {
    int i = 16;
    int j = 1;
    String str = paramCharSequence.toString();
    int k = str.length();
    if ('0' == str.charAt(0))
    {
      if (k - 1 == 0)
        return 0;
      int m = str.charAt(j);
      if ((120 == m) || (88 == m))
        j = 2;
    }
    while (true)
    {
      return (int)Long.parseLong(str.substring(j), i);
      i = 8;
      continue;
      if ('#' != str.charAt(0))
      {
        i = 10;
        j = 0;
      }
    }
  }

  public static final int a(CharSequence paramCharSequence, int paramInt)
  {
    int i = 1;
    if (paramCharSequence == null)
      return paramInt;
    String str = paramCharSequence.toString();
    int j = str.length();
    int k;
    if ('-' == str.charAt(0))
      k = -1;
    while (true)
    {
      int m;
      int n;
      if ('0' == str.charAt(i))
      {
        if (i == j - 1)
          return 0;
        int i1 = str.charAt(i + 1);
        if ((120 == i1) || (88 == i1))
        {
          m = i + 2;
          n = 16;
        }
      }
      while (true)
      {
        return k * Integer.parseInt(str.substring(m), n);
        m = i + 1;
        n = 8;
        continue;
        if ('#' == str.charAt(i))
        {
          m = i + 1;
          n = 16;
        }
        else
        {
          m = i;
          n = 10;
        }
      }
      k = i;
      i = 0;
    }
  }

  public static final int a(CharSequence paramCharSequence, String[] paramArrayOfString, int paramInt)
  {
    if (paramCharSequence != null)
      for (int i = 0; i < paramArrayOfString.length; i++)
        if (paramCharSequence.equals(paramArrayOfString[i]))
          return i;
    return paramInt;
  }

  public static final int a(String paramString, int paramInt)
  {
    if (paramString == null)
      return paramInt;
    return a(paramString);
  }

  public static final Object a(XmlPullParser paramXmlPullParser, String[] paramArrayOfString)
    throws XmlPullParserException, IOException
  {
    int i = paramXmlPullParser.getEventType();
    while (true)
    {
      if (i == 2)
        return b(paramXmlPullParser, paramArrayOfString);
      if (i == 3)
        throw new XmlPullParserException("Unexpected end tag at: " + paramXmlPullParser.getName());
      if (i == 4)
        throw new XmlPullParserException("Unexpected text: " + paramXmlPullParser.getText());
      try
      {
        int j = paramXmlPullParser.next();
        i = j;
        if (i == 1)
          throw new XmlPullParserException("Unexpected end of document");
      }
      catch (Exception localException)
      {
      }
    }
    throw new XmlPullParserException("Unexpected call next(): " + paramXmlPullParser.getName());
  }

  public static final HashMap a(InputStream paramInputStream)
    throws XmlPullParserException, IOException
  {
    XmlPullParser localXmlPullParser = Xml.newPullParser();
    localXmlPullParser.setInput(paramInputStream, null);
    return (HashMap)a(localXmlPullParser, new String[1]);
  }

  public static final HashMap a(XmlPullParser paramXmlPullParser, String paramString, String[] paramArrayOfString)
    throws XmlPullParserException, IOException
  {
    HashMap localHashMap = new HashMap();
    int i = paramXmlPullParser.getEventType();
    if (i == 2)
    {
      localObject = b(paramXmlPullParser, paramArrayOfString);
      if (paramArrayOfString[0] != null)
        localHashMap.put(paramArrayOfString[0], localObject);
    }
    while (i != 3)
    {
      Object localObject;
      i = paramXmlPullParser.next();
      if (i != 1)
        break;
      throw new XmlPullParserException("Document ended before " + paramString + " end tag");
      throw new XmlPullParserException("Map value without name attribute: " + paramXmlPullParser.getName());
    }
    if (paramXmlPullParser.getName().equals(paramString))
      return localHashMap;
    throw new XmlPullParserException("Expected " + paramString + " end tag at: " + paramXmlPullParser.getName());
  }

  public static final void a(Object paramObject, String paramString, XmlSerializer paramXmlSerializer)
    throws XmlPullParserException, IOException
  {
    if (paramObject == null)
    {
      paramXmlSerializer.startTag(null, "null");
      if (paramString != null)
        paramXmlSerializer.attribute(null, "name", paramString);
      paramXmlSerializer.endTag(null, "null");
      return;
    }
    if ((paramObject instanceof String))
    {
      paramXmlSerializer.startTag(null, "string");
      if (paramString != null)
        paramXmlSerializer.attribute(null, "name", paramString);
      paramXmlSerializer.text(paramObject.toString());
      paramXmlSerializer.endTag(null, "string");
      return;
    }
    String str;
    if ((paramObject instanceof Integer))
      str = "int";
    while (true)
    {
      paramXmlSerializer.startTag(null, str);
      if (paramString != null)
        paramXmlSerializer.attribute(null, "name", paramString);
      paramXmlSerializer.attribute(null, "value", paramObject.toString());
      paramXmlSerializer.endTag(null, str);
      return;
      if ((paramObject instanceof Long))
      {
        str = "long";
      }
      else if ((paramObject instanceof Float))
      {
        str = "float";
      }
      else if ((paramObject instanceof Double))
      {
        str = "double";
      }
      else
      {
        if (!(paramObject instanceof Boolean))
          break;
        str = "boolean";
      }
    }
    if ((paramObject instanceof byte[]))
    {
      a((byte[])paramObject, paramString, paramXmlSerializer);
      return;
    }
    if ((paramObject instanceof int[]))
    {
      a((int[])paramObject, paramString, paramXmlSerializer);
      return;
    }
    if ((paramObject instanceof Map))
    {
      a((Map)paramObject, paramString, paramXmlSerializer);
      return;
    }
    if ((paramObject instanceof List))
    {
      a((List)paramObject, paramString, paramXmlSerializer);
      return;
    }
    if ((paramObject instanceof CharSequence))
    {
      paramXmlSerializer.startTag(null, "string");
      if (paramString != null)
        paramXmlSerializer.attribute(null, "name", paramString);
      paramXmlSerializer.text(paramObject.toString());
      paramXmlSerializer.endTag(null, "string");
      return;
    }
    throw new RuntimeException("writeValueXml: unable to write value " + paramObject);
  }

  public static final void a(List paramList, OutputStream paramOutputStream)
    throws XmlPullParserException, IOException
  {
    XmlSerializer localXmlSerializer = Xml.newSerializer();
    localXmlSerializer.setOutput(paramOutputStream, "utf-8");
    localXmlSerializer.startDocument(null, Boolean.valueOf(true));
    localXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
    a(paramList, null, localXmlSerializer);
    localXmlSerializer.endDocument();
  }

  public static final void a(List paramList, String paramString, XmlSerializer paramXmlSerializer)
    throws XmlPullParserException, IOException
  {
    if (paramList == null)
    {
      paramXmlSerializer.startTag(null, "null");
      paramXmlSerializer.endTag(null, "null");
      return;
    }
    paramXmlSerializer.startTag(null, "list");
    if (paramString != null)
      paramXmlSerializer.attribute(null, "name", paramString);
    int i = paramList.size();
    for (int j = 0; j < i; j++)
      a(paramList.get(j), null, paramXmlSerializer);
    paramXmlSerializer.endTag(null, "list");
  }

  public static final void a(Map paramMap, OutputStream paramOutputStream)
    throws XmlPullParserException, IOException
  {
    a locala = new a();
    locala.setOutput(paramOutputStream, "utf-8");
    locala.startDocument(null, Boolean.valueOf(true));
    locala.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
    a(paramMap, null, locala);
    locala.endDocument();
  }

  public static final void a(Map paramMap, String paramString, XmlSerializer paramXmlSerializer)
    throws XmlPullParserException, IOException
  {
    if (paramMap == null)
    {
      paramXmlSerializer.startTag(null, "null");
      paramXmlSerializer.endTag(null, "null");
      return;
    }
    Iterator localIterator = paramMap.entrySet().iterator();
    paramXmlSerializer.startTag(null, "map");
    if (paramString != null)
      paramXmlSerializer.attribute(null, "name", paramString);
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      a(localEntry.getValue(), (String)localEntry.getKey(), paramXmlSerializer);
    }
    paramXmlSerializer.endTag(null, "map");
  }

  public static void a(XmlPullParser paramXmlPullParser)
    throws XmlPullParserException, IOException
  {
    int i = paramXmlPullParser.getDepth();
    int j;
    do
      j = paramXmlPullParser.next();
    while ((j != 1) && ((j != 3) || (paramXmlPullParser.getDepth() > i)));
  }

  public static final void a(XmlPullParser paramXmlPullParser, String paramString)
    throws XmlPullParserException, IOException
  {
    int i;
    do
      i = paramXmlPullParser.next();
    while ((i != 2) && (i != 1));
    if (i != 2)
      throw new XmlPullParserException("No start tag found");
    if (!paramXmlPullParser.getName().equals(paramString))
      throw new XmlPullParserException("Unexpected start tag: found " + paramXmlPullParser.getName() + ", expected " + paramString);
  }

  public static final void a(byte[] paramArrayOfByte, String paramString, XmlSerializer paramXmlSerializer)
    throws XmlPullParserException, IOException
  {
    if (paramArrayOfByte == null)
    {
      paramXmlSerializer.startTag(null, "null");
      paramXmlSerializer.endTag(null, "null");
      return;
    }
    paramXmlSerializer.startTag(null, "byte-array");
    if (paramString != null)
      paramXmlSerializer.attribute(null, "name", paramString);
    int i = paramArrayOfByte.length;
    paramXmlSerializer.attribute(null, "num", Integer.toString(i));
    StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
    int j = 0;
    if (j < i)
    {
      int k = paramArrayOfByte[j];
      int m = k >> 4;
      int n;
      label123: int i1;
      if (m >= 10)
      {
        n = -10 + (m + 97);
        localStringBuilder.append(n);
        i1 = k & 0xFF;
        if (i1 < 10)
          break label180;
      }
      label180: for (int i2 = -10 + (i1 + 97); ; i2 = i1 + 48)
      {
        localStringBuilder.append(i2);
        j++;
        break;
        n = m + 48;
        break label123;
      }
    }
    paramXmlSerializer.text(localStringBuilder.toString());
    paramXmlSerializer.endTag(null, "byte-array");
  }

  public static final void a(int[] paramArrayOfInt, String paramString, XmlSerializer paramXmlSerializer)
    throws XmlPullParserException, IOException
  {
    if (paramArrayOfInt == null)
    {
      paramXmlSerializer.startTag(null, "null");
      paramXmlSerializer.endTag(null, "null");
      return;
    }
    paramXmlSerializer.startTag(null, "int-array");
    if (paramString != null)
      paramXmlSerializer.attribute(null, "name", paramString);
    int i = paramArrayOfInt.length;
    paramXmlSerializer.attribute(null, "num", Integer.toString(i));
    for (int j = 0; j < i; j++)
    {
      paramXmlSerializer.startTag(null, "item");
      paramXmlSerializer.attribute(null, "value", Integer.toString(paramArrayOfInt[j]));
      paramXmlSerializer.endTag(null, "item");
    }
    paramXmlSerializer.endTag(null, "int-array");
  }

  public static final boolean a(CharSequence paramCharSequence, boolean paramBoolean)
  {
    if (paramCharSequence == null)
      return paramBoolean;
    boolean bool1;
    if ((!paramCharSequence.equals("1")) && (!paramCharSequence.equals("true")))
    {
      boolean bool2 = paramCharSequence.equals("TRUE");
      bool1 = false;
      if (!bool2);
    }
    else
    {
      bool1 = true;
    }
    return bool1;
  }

  private static final Object b(XmlPullParser paramXmlPullParser, String[] paramArrayOfString)
    throws XmlPullParserException, IOException
  {
    String str1 = paramXmlPullParser.getAttributeValue(null, "name");
    String str2 = paramXmlPullParser.getName();
    boolean bool = str2.equals("null");
    Object localObject = null;
    if (bool);
    int i;
    label235: 
    do
    {
      while (true)
      {
        i = paramXmlPullParser.next();
        if (i == 1)
          break label654;
        if (i != 3)
          break label556;
        if (!paramXmlPullParser.getName().equals(str2))
          break label513;
        paramArrayOfString[0] = str1;
        return localObject;
        if (str2.equals("string"))
        {
          String str3 = "";
          int j;
          do
            while (true)
            {
              j = paramXmlPullParser.next();
              if (j == 1)
                break label235;
              if (j == 3)
              {
                if (paramXmlPullParser.getName().equals("string"))
                {
                  paramArrayOfString[0] = str1;
                  return str3;
                }
                throw new XmlPullParserException("Unexpected end tag in <string>: " + paramXmlPullParser.getName());
              }
              if (j != 4)
                break;
              str3 = str3 + paramXmlPullParser.getText();
            }
          while (j != 2);
          throw new XmlPullParserException("Unexpected start tag in <string>: " + paramXmlPullParser.getName());
          throw new XmlPullParserException("Unexpected end of document in <string>");
        }
        if (str2.equals("int"))
        {
          localObject = Integer.valueOf(Integer.parseInt(paramXmlPullParser.getAttributeValue(null, "value")));
        }
        else if (str2.equals("long"))
        {
          localObject = Long.valueOf(paramXmlPullParser.getAttributeValue(null, "value"));
        }
        else if (str2.equals("float"))
        {
          localObject = new Float(paramXmlPullParser.getAttributeValue(null, "value"));
        }
        else if (str2.equals("double"))
        {
          localObject = new Double(paramXmlPullParser.getAttributeValue(null, "value"));
        }
        else
        {
          if (!str2.equals("boolean"))
            break;
          localObject = Boolean.valueOf(paramXmlPullParser.getAttributeValue(null, "value"));
        }
      }
      if (str2.equals("int-array"))
      {
        paramXmlPullParser.next();
        int[] arrayOfInt = c(paramXmlPullParser, "int-array", paramArrayOfString);
        paramArrayOfString[0] = str1;
        return arrayOfInt;
      }
      if (str2.equals("map"))
      {
        paramXmlPullParser.next();
        HashMap localHashMap = a(paramXmlPullParser, "map", paramArrayOfString);
        paramArrayOfString[0] = str1;
        return localHashMap;
      }
      if (str2.equals("list"))
      {
        paramXmlPullParser.next();
        ArrayList localArrayList = b(paramXmlPullParser, "list", paramArrayOfString);
        paramArrayOfString[0] = str1;
        return localArrayList;
      }
      throw new XmlPullParserException("Unknown tag: " + str2);
      throw new XmlPullParserException("Unexpected end tag in <" + str2 + ">: " + paramXmlPullParser.getName());
      if (i == 4)
        throw new XmlPullParserException("Unexpected text in <" + str2 + ">: " + paramXmlPullParser.getName());
    }
    while (i != 2);
    label513: label556: throw new XmlPullParserException("Unexpected start tag in <" + str2 + ">: " + paramXmlPullParser.getName());
    label654: throw new XmlPullParserException("Unexpected end of document in <" + str2 + ">");
  }

  public static final ArrayList b(InputStream paramInputStream)
    throws XmlPullParserException, IOException
  {
    XmlPullParser localXmlPullParser = Xml.newPullParser();
    localXmlPullParser.setInput(paramInputStream, null);
    return (ArrayList)a(localXmlPullParser, new String[1]);
  }

  public static final ArrayList b(XmlPullParser paramXmlPullParser, String paramString, String[] paramArrayOfString)
    throws XmlPullParserException, IOException
  {
    ArrayList localArrayList = new ArrayList();
    int i = paramXmlPullParser.getEventType();
    if (i == 2)
      localArrayList.add(b(paramXmlPullParser, paramArrayOfString));
    while (i != 3)
    {
      i = paramXmlPullParser.next();
      if (i != 1)
        break;
      throw new XmlPullParserException("Document ended before " + paramString + " end tag");
    }
    if (paramXmlPullParser.getName().equals(paramString))
      return localArrayList;
    throw new XmlPullParserException("Expected " + paramString + " end tag at: " + paramXmlPullParser.getName());
  }

  public static final void b(XmlPullParser paramXmlPullParser)
    throws XmlPullParserException, IOException
  {
    int i;
    do
      i = paramXmlPullParser.next();
    while ((i != 2) && (i != 1));
  }

  // ERROR //
  public static final int[] c(XmlPullParser paramXmlPullParser, String paramString, String[] paramArrayOfString)
    throws XmlPullParserException, IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: aconst_null
    //   2: ldc_w 283
    //   5: invokeinterface 306 3 0
    //   10: invokestatic 317	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   13: istore 5
    //   15: iload 5
    //   17: newarray int
    //   19: astore 6
    //   21: aload_0
    //   22: invokeinterface 62 1 0
    //   27: istore 7
    //   29: iconst_0
    //   30: istore 8
    //   32: iload 7
    //   34: istore 9
    //   36: iload 9
    //   38: iconst_2
    //   39: if_icmpne +165 -> 204
    //   42: aload_0
    //   43: invokeinterface 77 1 0
    //   48: ldc_w 295
    //   51: invokevirtual 121	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   54: ifeq +117 -> 171
    //   57: aload 6
    //   59: iload 8
    //   61: aload_0
    //   62: aconst_null
    //   63: ldc 154
    //   65: invokeinterface 306 3 0
    //   70: invokestatic 317	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   73: iastore
    //   74: aload_0
    //   75: invokeinterface 89 1 0
    //   80: istore 9
    //   82: iload 9
    //   84: iconst_1
    //   85: if_icmpne -49 -> 36
    //   88: new 53	org/xmlpull/v1/XmlPullParserException
    //   91: dup
    //   92: new 67	java/lang/StringBuilder
    //   95: dup
    //   96: invokespecial 68	java/lang/StringBuilder:<init>	()V
    //   99: ldc 116
    //   101: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   104: aload_1
    //   105: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: ldc 118
    //   110: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   116: invokespecial 81	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   119: athrow
    //   120: astore 4
    //   122: new 53	org/xmlpull/v1/XmlPullParserException
    //   125: dup
    //   126: ldc_w 364
    //   129: invokespecial 81	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   132: athrow
    //   133: astore_3
    //   134: new 53	org/xmlpull/v1/XmlPullParserException
    //   137: dup
    //   138: ldc_w 366
    //   141: invokespecial 81	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   144: athrow
    //   145: astore 11
    //   147: new 53	org/xmlpull/v1/XmlPullParserException
    //   150: dup
    //   151: ldc_w 368
    //   154: invokespecial 81	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   157: athrow
    //   158: astore 10
    //   160: new 53	org/xmlpull/v1/XmlPullParserException
    //   163: dup
    //   164: ldc_w 370
    //   167: invokespecial 81	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   170: athrow
    //   171: new 53	org/xmlpull/v1/XmlPullParserException
    //   174: dup
    //   175: new 67	java/lang/StringBuilder
    //   178: dup
    //   179: invokespecial 68	java/lang/StringBuilder:<init>	()V
    //   182: ldc_w 372
    //   185: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: aload_0
    //   189: invokeinterface 77 1 0
    //   194: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   200: invokespecial 81	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   203: athrow
    //   204: iload 9
    //   206: iconst_3
    //   207: if_icmpne -133 -> 74
    //   210: aload_0
    //   211: invokeinterface 77 1 0
    //   216: aload_1
    //   217: invokevirtual 121	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   220: ifeq +6 -> 226
    //   223: aload 6
    //   225: areturn
    //   226: aload_0
    //   227: invokeinterface 77 1 0
    //   232: ldc_w 295
    //   235: invokevirtual 121	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   238: ifeq +9 -> 247
    //   241: iinc 8 1
    //   244: goto -170 -> 74
    //   247: new 53	org/xmlpull/v1/XmlPullParserException
    //   250: dup
    //   251: new 67	java/lang/StringBuilder
    //   254: dup
    //   255: invokespecial 68	java/lang/StringBuilder:<init>	()V
    //   258: ldc 123
    //   260: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   263: aload_1
    //   264: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   267: ldc 125
    //   269: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   272: aload_0
    //   273: invokeinterface 77 1 0
    //   278: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   281: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   284: invokespecial 81	org/xmlpull/v1/XmlPullParserException:<init>	(Ljava/lang/String;)V
    //   287: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   0	15	120	java/lang/NullPointerException
    //   0	15	133	java/lang/NumberFormatException
    //   57	74	145	java/lang/NullPointerException
    //   57	74	158	java/lang/NumberFormatException
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.utdid.b.a.e
 * JD-Core Version:    0.6.2
 */