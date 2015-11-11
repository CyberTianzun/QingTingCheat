package org.jdom.output;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.EntityRef;
import org.jdom.IllegalDataException;
import org.jdom.Namespace;
import org.jdom.ProcessingInstruction;
import org.jdom.Text;
import org.jdom.Verifier;

public class XMLOutputter
  implements Cloneable
{
  private static final String CVS_ID = "@(#) $RCSfile: XMLOutputter.java,v $ $Revision: 1.117 $ $Date: 2009/07/23 05:54:23 $ $Name: jdom_1_1_1 $";
  protected static final Format preserveFormat = Format.getRawFormat();
  protected Format currentFormat = this.userFormat;
  private boolean escapeOutput = true;
  private Format userFormat = Format.getRawFormat();

  public XMLOutputter()
  {
  }

  public XMLOutputter(Format paramFormat)
  {
    this.userFormat = ((Format)paramFormat.clone());
    this.currentFormat = this.userFormat;
  }

  public XMLOutputter(XMLOutputter paramXMLOutputter)
  {
    this.userFormat = ((Format)paramXMLOutputter.userFormat.clone());
    this.currentFormat = this.userFormat;
  }

  private NamespaceStack createNamespaceStack()
  {
    return new NamespaceStack();
  }

  private boolean endsWithWhite(String paramString)
  {
    return (paramString != null) && (paramString.length() > 0) && (Verifier.isXMLWhitespace(paramString.charAt(-1 + paramString.length())));
  }

  private void indent(Writer paramWriter, int paramInt)
    throws IOException
  {
    if ((this.currentFormat.indent == null) || (this.currentFormat.indent.equals("")));
    while (true)
    {
      return;
      for (int i = 0; i < paramInt; i++)
        paramWriter.write(this.currentFormat.indent);
    }
  }

  private boolean isAllWhitespace(Object paramObject)
  {
    String str;
    if ((paramObject instanceof String))
      str = (String)paramObject;
    for (int i = 0; ; i++)
    {
      if (i >= str.length())
        break label68;
      if (!Verifier.isXMLWhitespace(str.charAt(i)))
      {
        do
        {
          return false;
          if ((paramObject instanceof Text))
          {
            str = ((Text)paramObject).getText();
            break;
          }
        }
        while (!(paramObject instanceof EntityRef));
        return false;
      }
    }
    label68: return true;
  }

  private Writer makeWriter(OutputStream paramOutputStream)
    throws UnsupportedEncodingException
  {
    return makeWriter(paramOutputStream, this.userFormat.encoding);
  }

  private static Writer makeWriter(OutputStream paramOutputStream, String paramString)
    throws UnsupportedEncodingException
  {
    if ("UTF-8".equals(paramString))
      paramString = "UTF8";
    return new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(paramOutputStream), paramString));
  }

  private void newline(Writer paramWriter)
    throws IOException
  {
    if (this.currentFormat.indent != null)
      paramWriter.write(this.currentFormat.lineSeparator);
  }

  private static int nextNonText(List paramList, int paramInt)
  {
    if (paramInt < 0)
      paramInt = 0;
    int i = paramInt;
    int j = paramList.size();
    while (i < j)
    {
      Object localObject = paramList.get(i);
      if ((!(localObject instanceof Text)) && (!(localObject instanceof EntityRef)))
        return i;
      i++;
    }
    return j;
  }

  private void printAdditionalNamespaces(Writer paramWriter, Element paramElement, NamespaceStack paramNamespaceStack)
    throws IOException
  {
    List localList = paramElement.getAdditionalNamespaces();
    if (localList != null)
      for (int i = 0; i < localList.size(); i++)
        printNamespace(paramWriter, (Namespace)localList.get(i), paramNamespaceStack);
  }

  private void printContentRange(Writer paramWriter, List paramList, int paramInt1, int paramInt2, int paramInt3, NamespaceStack paramNamespaceStack)
    throws IOException
  {
    int i = paramInt1;
    if (i < paramInt2)
    {
      if (i == paramInt1);
      Object localObject;
      for (int j = 1; ; j = 0)
      {
        localObject = paramList.get(i);
        if ((!(localObject instanceof Text)) && (!(localObject instanceof EntityRef)))
          break label105;
        int k = skipLeadingWhite(paramList, i);
        i = nextNonText(paramList, k);
        if (k >= i)
          break;
        if (j == 0)
          newline(paramWriter);
        indent(paramWriter, paramInt3);
        printTextRange(paramWriter, paramList, k, i);
        break;
      }
      label105: if (j == 0)
        newline(paramWriter);
      indent(paramWriter, paramInt3);
      if ((localObject instanceof Comment))
        printComment(paramWriter, (Comment)localObject);
      while (true)
      {
        i++;
        break;
        if ((localObject instanceof Element))
          printElement(paramWriter, (Element)localObject, paramInt3, paramNamespaceStack);
        else if ((localObject instanceof ProcessingInstruction))
          printProcessingInstruction(paramWriter, (ProcessingInstruction)localObject);
      }
    }
  }

  private void printElementNamespace(Writer paramWriter, Element paramElement, NamespaceStack paramNamespaceStack)
    throws IOException
  {
    Namespace localNamespace = paramElement.getNamespace();
    if (localNamespace == Namespace.XML_NAMESPACE);
    while ((localNamespace == Namespace.NO_NAMESPACE) && (paramNamespaceStack.getURI("") == null))
      return;
    printNamespace(paramWriter, localNamespace, paramNamespaceStack);
  }

  private void printNamespace(Writer paramWriter, Namespace paramNamespace, NamespaceStack paramNamespaceStack)
    throws IOException
  {
    String str1 = paramNamespace.getPrefix();
    String str2 = paramNamespace.getURI();
    if (str2.equals(paramNamespaceStack.getURI(str1)))
      return;
    paramWriter.write(" xmlns");
    if (!str1.equals(""))
    {
      paramWriter.write(":");
      paramWriter.write(str1);
    }
    paramWriter.write("=\"");
    paramWriter.write(escapeAttributeEntities(str2));
    paramWriter.write("\"");
    paramNamespaceStack.push(paramNamespace);
  }

  private void printQualifiedName(Writer paramWriter, Attribute paramAttribute)
    throws IOException
  {
    String str = paramAttribute.getNamespace().getPrefix();
    if ((str != null) && (!str.equals("")))
    {
      paramWriter.write(str);
      paramWriter.write(58);
      paramWriter.write(paramAttribute.getName());
      return;
    }
    paramWriter.write(paramAttribute.getName());
  }

  private void printQualifiedName(Writer paramWriter, Element paramElement)
    throws IOException
  {
    if (paramElement.getNamespace().getPrefix().length() == 0)
    {
      paramWriter.write(paramElement.getName());
      return;
    }
    paramWriter.write(paramElement.getNamespace().getPrefix());
    paramWriter.write(58);
    paramWriter.write(paramElement.getName());
  }

  private void printString(Writer paramWriter, String paramString)
    throws IOException
  {
    if (this.currentFormat.mode == Format.TextMode.NORMALIZE)
      paramString = Text.normalizeString(paramString);
    while (true)
    {
      paramWriter.write(escapeElementEntities(paramString));
      return;
      if (this.currentFormat.mode == Format.TextMode.TRIM)
        paramString = paramString.trim();
    }
  }

  private void printTextRange(Writer paramWriter, List paramList, int paramInt1, int paramInt2)
    throws IOException
  {
    Object localObject1 = null;
    int i = skipLeadingWhite(paramList, paramInt1);
    if (i < paramList.size())
    {
      int j = skipTrailingWhite(paramList, paramInt2);
      int k = i;
      if (k < j)
      {
        Object localObject2 = paramList.get(k);
        String str;
        if ((localObject2 instanceof Text))
          str = ((Text)localObject2).getText();
        while (true)
          if ((str == null) || ("".equals(str)))
          {
            k++;
            break;
            if ((localObject2 instanceof EntityRef))
              str = "&" + ((EntityRef)localObject2).getValue() + ";";
            else
              throw new IllegalStateException("Should see only CDATA, Text, or EntityRef");
          }
        if ((localObject1 != null) && ((this.currentFormat.mode == Format.TextMode.NORMALIZE) || (this.currentFormat.mode == Format.TextMode.TRIM)) && ((endsWithWhite((String)localObject1)) || (startsWithWhite(str))))
          paramWriter.write(" ");
        if ((localObject2 instanceof CDATA))
          printCDATA(paramWriter, (CDATA)localObject2);
        while (true)
        {
          localObject1 = str;
          break;
          if ((localObject2 instanceof EntityRef))
            printEntityRef(paramWriter, (EntityRef)localObject2);
          else
            printString(paramWriter, str);
        }
      }
    }
  }

  private int skipLeadingWhite(List paramList, int paramInt)
  {
    if (paramInt < 0)
      paramInt = 0;
    int i = paramInt;
    int j = paramList.size();
    if ((this.currentFormat.mode == Format.TextMode.TRIM_FULL_WHITE) || (this.currentFormat.mode == Format.TextMode.NORMALIZE) || (this.currentFormat.mode == Format.TextMode.TRIM))
      while (i < j)
      {
        if (!isAllWhitespace(paramList.get(i)))
          return i;
        i++;
      }
    return i;
  }

  private int skipTrailingWhite(List paramList, int paramInt)
  {
    int i = paramList.size();
    if (paramInt > i)
      paramInt = i;
    int j = paramInt;
    if ((this.currentFormat.mode == Format.TextMode.TRIM_FULL_WHITE) || (this.currentFormat.mode == Format.TextMode.NORMALIZE) || (this.currentFormat.mode == Format.TextMode.TRIM));
    while (true)
    {
      if ((j < 0) || (!isAllWhitespace(paramList.get(j - 1))))
        return j;
      j--;
    }
  }

  private boolean startsWithWhite(String paramString)
  {
    boolean bool1 = false;
    if (paramString != null)
    {
      int i = paramString.length();
      bool1 = false;
      if (i > 0)
      {
        boolean bool2 = Verifier.isXMLWhitespace(paramString.charAt(0));
        bool1 = false;
        if (bool2)
          bool1 = true;
      }
    }
    return bool1;
  }

  public Object clone()
  {
    try
    {
      Object localObject = super.clone();
      return localObject;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      throw new RuntimeException(localCloneNotSupportedException.toString());
    }
  }

  public String escapeAttributeEntities(String paramString)
  {
    EscapeStrategy localEscapeStrategy = this.currentFormat.escapeStrategy;
    StringBuffer localStringBuffer = null;
    int i = 0;
    if (i < paramString.length())
    {
      int j = paramString.charAt(i);
      int k = i;
      char c;
      String str;
      switch (j)
      {
      default:
        if (localEscapeStrategy.shouldEscape((char)j))
          if (Verifier.isHighSurrogate((char)j))
          {
            i++;
            if (i >= paramString.length())
              break label346;
            c = paramString.charAt(i);
            if (!Verifier.isLowSurrogate(c))
              throw new IllegalDataException("Could not decode surrogate pair 0x" + Integer.toHexString(j) + " / 0x" + Integer.toHexString(c));
          }
        break;
      case 60:
        str = "&lt;";
        label204: if (localStringBuffer == null)
          if (str != null)
          {
            localStringBuffer = new StringBuffer(20 + paramString.length());
            localStringBuffer.append(paramString.substring(0, k));
            localStringBuffer.append(str);
          }
        break;
      case 62:
      case 34:
      case 38:
      case 13:
      case 9:
      case 10:
      }
      while (true)
      {
        i++;
        break;
        str = "&gt;";
        break label204;
        str = "&quot;";
        break label204;
        str = "&amp;";
        break label204;
        str = "&#xD;";
        break label204;
        str = "&#x9;";
        break label204;
        str = "&#xA;";
        break label204;
        j = Verifier.decodeSurrogatePair((char)j, c);
        str = "&#x" + Integer.toHexString(j) + ";";
        break label204;
        label346: throw new IllegalDataException("Surrogate pair 0x" + Integer.toHexString(j) + " truncated");
        str = null;
        break label204;
        if (str == null)
          localStringBuffer.append((char)j);
        else
          localStringBuffer.append(str);
      }
    }
    if (localStringBuffer == null)
      return paramString;
    return localStringBuffer.toString();
  }

  public String escapeElementEntities(String paramString)
  {
    if (!this.escapeOutput);
    StringBuffer localStringBuffer;
    label196: label326: 
    do
    {
      return paramString;
      EscapeStrategy localEscapeStrategy = this.currentFormat.escapeStrategy;
      localStringBuffer = null;
      int i = 0;
      if (i < paramString.length())
      {
        int j = paramString.charAt(i);
        int k = i;
        char c;
        String str;
        switch (j)
        {
        default:
          if (localEscapeStrategy.shouldEscape((char)j))
            if (Verifier.isHighSurrogate((char)j))
            {
              i++;
              if (i >= paramString.length())
                break label326;
              c = paramString.charAt(i);
              if (!Verifier.isLowSurrogate(c))
                throw new IllegalDataException("Could not decode surrogate pair 0x" + Integer.toHexString(j) + " / 0x" + Integer.toHexString(c));
            }
          break;
        case 60:
          str = "&lt;";
          if (localStringBuffer == null)
            if (str != null)
            {
              localStringBuffer = new StringBuffer(20 + paramString.length());
              localStringBuffer.append(paramString.substring(0, k));
              localStringBuffer.append(str);
            }
          break;
        case 62:
        case 38:
        case 13:
        case 10:
        }
        while (true)
        {
          i++;
          break;
          str = "&gt;";
          break label196;
          str = "&amp;";
          break label196;
          str = "&#xD;";
          break label196;
          str = this.currentFormat.lineSeparator;
          break label196;
          j = Verifier.decodeSurrogatePair((char)j, c);
          str = "&#x" + Integer.toHexString(j) + ";";
          break label196;
          throw new IllegalDataException("Surrogate pair 0x" + Integer.toHexString(j) + " truncated");
          str = null;
          break label196;
          if (str == null)
            localStringBuffer.append((char)j);
          else
            localStringBuffer.append(str);
        }
      }
    }
    while (localStringBuffer == null);
    return localStringBuffer.toString();
  }

  public Format getFormat()
  {
    return (Format)this.userFormat.clone();
  }

  public void output(List paramList, OutputStream paramOutputStream)
    throws IOException
  {
    output(paramList, makeWriter(paramOutputStream));
  }

  public void output(List paramList, Writer paramWriter)
    throws IOException
  {
    printContentRange(paramWriter, paramList, 0, paramList.size(), 0, createNamespaceStack());
    paramWriter.flush();
  }

  public void output(CDATA paramCDATA, OutputStream paramOutputStream)
    throws IOException
  {
    output(paramCDATA, makeWriter(paramOutputStream));
  }

  public void output(CDATA paramCDATA, Writer paramWriter)
    throws IOException
  {
    printCDATA(paramWriter, paramCDATA);
    paramWriter.flush();
  }

  public void output(Comment paramComment, OutputStream paramOutputStream)
    throws IOException
  {
    output(paramComment, makeWriter(paramOutputStream));
  }

  public void output(Comment paramComment, Writer paramWriter)
    throws IOException
  {
    printComment(paramWriter, paramComment);
    paramWriter.flush();
  }

  public void output(DocType paramDocType, OutputStream paramOutputStream)
    throws IOException
  {
    output(paramDocType, makeWriter(paramOutputStream));
  }

  public void output(DocType paramDocType, Writer paramWriter)
    throws IOException
  {
    printDocType(paramWriter, paramDocType);
    paramWriter.flush();
  }

  public void output(Document paramDocument, OutputStream paramOutputStream)
    throws IOException
  {
    output(paramDocument, makeWriter(paramOutputStream));
  }

  public void output(Document paramDocument, Writer paramWriter)
    throws IOException
  {
    printDeclaration(paramWriter, paramDocument, this.userFormat.encoding);
    List localList = paramDocument.getContent();
    int i = localList.size();
    int j = 0;
    if (j < i)
    {
      Object localObject = localList.get(j);
      if ((localObject instanceof Element))
        printElement(paramWriter, paramDocument.getRootElement(), 0, createNamespaceStack());
      while (true)
      {
        newline(paramWriter);
        indent(paramWriter, 0);
        j++;
        break;
        if ((localObject instanceof Comment))
        {
          printComment(paramWriter, (Comment)localObject);
        }
        else if ((localObject instanceof ProcessingInstruction))
        {
          printProcessingInstruction(paramWriter, (ProcessingInstruction)localObject);
        }
        else if ((localObject instanceof DocType))
        {
          printDocType(paramWriter, paramDocument.getDocType());
          paramWriter.write(this.currentFormat.lineSeparator);
        }
      }
    }
    paramWriter.write(this.currentFormat.lineSeparator);
    paramWriter.flush();
  }

  public void output(Element paramElement, OutputStream paramOutputStream)
    throws IOException
  {
    output(paramElement, makeWriter(paramOutputStream));
  }

  public void output(Element paramElement, Writer paramWriter)
    throws IOException
  {
    printElement(paramWriter, paramElement, 0, createNamespaceStack());
    paramWriter.flush();
  }

  public void output(EntityRef paramEntityRef, OutputStream paramOutputStream)
    throws IOException
  {
    output(paramEntityRef, makeWriter(paramOutputStream));
  }

  public void output(EntityRef paramEntityRef, Writer paramWriter)
    throws IOException
  {
    printEntityRef(paramWriter, paramEntityRef);
    paramWriter.flush();
  }

  public void output(ProcessingInstruction paramProcessingInstruction, OutputStream paramOutputStream)
    throws IOException
  {
    output(paramProcessingInstruction, makeWriter(paramOutputStream));
  }

  public void output(ProcessingInstruction paramProcessingInstruction, Writer paramWriter)
    throws IOException
  {
    boolean bool = this.currentFormat.ignoreTrAXEscapingPIs;
    this.currentFormat.setIgnoreTrAXEscapingPIs(true);
    printProcessingInstruction(paramWriter, paramProcessingInstruction);
    this.currentFormat.setIgnoreTrAXEscapingPIs(bool);
    paramWriter.flush();
  }

  public void output(Text paramText, OutputStream paramOutputStream)
    throws IOException
  {
    output(paramText, makeWriter(paramOutputStream));
  }

  public void output(Text paramText, Writer paramWriter)
    throws IOException
  {
    printText(paramWriter, paramText);
    paramWriter.flush();
  }

  public void outputElementContent(Element paramElement, OutputStream paramOutputStream)
    throws IOException
  {
    outputElementContent(paramElement, makeWriter(paramOutputStream));
  }

  public void outputElementContent(Element paramElement, Writer paramWriter)
    throws IOException
  {
    List localList = paramElement.getContent();
    printContentRange(paramWriter, localList, 0, localList.size(), 0, createNamespaceStack());
    paramWriter.flush();
  }

  public String outputString(List paramList)
  {
    StringWriter localStringWriter = new StringWriter();
    try
    {
      output(paramList, localStringWriter);
      label14: return localStringWriter.toString();
    }
    catch (IOException localIOException)
    {
      break label14;
    }
  }

  public String outputString(CDATA paramCDATA)
  {
    StringWriter localStringWriter = new StringWriter();
    try
    {
      output(paramCDATA, localStringWriter);
      label14: return localStringWriter.toString();
    }
    catch (IOException localIOException)
    {
      break label14;
    }
  }

  public String outputString(Comment paramComment)
  {
    StringWriter localStringWriter = new StringWriter();
    try
    {
      output(paramComment, localStringWriter);
      label14: return localStringWriter.toString();
    }
    catch (IOException localIOException)
    {
      break label14;
    }
  }

  public String outputString(DocType paramDocType)
  {
    StringWriter localStringWriter = new StringWriter();
    try
    {
      output(paramDocType, localStringWriter);
      label14: return localStringWriter.toString();
    }
    catch (IOException localIOException)
    {
      break label14;
    }
  }

  public String outputString(Document paramDocument)
  {
    StringWriter localStringWriter = new StringWriter();
    try
    {
      output(paramDocument, localStringWriter);
      label14: return localStringWriter.toString();
    }
    catch (IOException localIOException)
    {
      break label14;
    }
  }

  public String outputString(Element paramElement)
  {
    StringWriter localStringWriter = new StringWriter();
    try
    {
      output(paramElement, localStringWriter);
      label14: return localStringWriter.toString();
    }
    catch (IOException localIOException)
    {
      break label14;
    }
  }

  public String outputString(EntityRef paramEntityRef)
  {
    StringWriter localStringWriter = new StringWriter();
    try
    {
      output(paramEntityRef, localStringWriter);
      label14: return localStringWriter.toString();
    }
    catch (IOException localIOException)
    {
      break label14;
    }
  }

  public String outputString(ProcessingInstruction paramProcessingInstruction)
  {
    StringWriter localStringWriter = new StringWriter();
    try
    {
      output(paramProcessingInstruction, localStringWriter);
      label14: return localStringWriter.toString();
    }
    catch (IOException localIOException)
    {
      break label14;
    }
  }

  public String outputString(Text paramText)
  {
    StringWriter localStringWriter = new StringWriter();
    try
    {
      output(paramText, localStringWriter);
      label14: return localStringWriter.toString();
    }
    catch (IOException localIOException)
    {
      break label14;
    }
  }

  protected void printAttributes(Writer paramWriter, List paramList, Element paramElement, NamespaceStack paramNamespaceStack)
    throws IOException
  {
    for (int i = 0; i < paramList.size(); i++)
    {
      Attribute localAttribute = (Attribute)paramList.get(i);
      Namespace localNamespace = localAttribute.getNamespace();
      if ((localNamespace != Namespace.NO_NAMESPACE) && (localNamespace != Namespace.XML_NAMESPACE))
        printNamespace(paramWriter, localNamespace, paramNamespaceStack);
      paramWriter.write(" ");
      printQualifiedName(paramWriter, localAttribute);
      paramWriter.write("=");
      paramWriter.write("\"");
      paramWriter.write(escapeAttributeEntities(localAttribute.getValue()));
      paramWriter.write("\"");
    }
  }

  protected void printCDATA(Writer paramWriter, CDATA paramCDATA)
    throws IOException
  {
    String str;
    if (this.currentFormat.mode == Format.TextMode.NORMALIZE)
      str = paramCDATA.getTextNormalize();
    while (true)
    {
      paramWriter.write("<![CDATA[");
      paramWriter.write(str);
      paramWriter.write("]]>");
      return;
      if (this.currentFormat.mode == Format.TextMode.TRIM)
        str = paramCDATA.getText().trim();
      else
        str = paramCDATA.getText();
    }
  }

  protected void printComment(Writer paramWriter, Comment paramComment)
    throws IOException
  {
    paramWriter.write("<!--");
    paramWriter.write(paramComment.getText());
    paramWriter.write("-->");
  }

  protected void printDeclaration(Writer paramWriter, Document paramDocument, String paramString)
    throws IOException
  {
    if (!this.userFormat.omitDeclaration)
    {
      paramWriter.write("<?xml version=\"1.0\"");
      if (!this.userFormat.omitEncoding)
        paramWriter.write(" encoding=\"" + paramString + "\"");
      paramWriter.write("?>");
      paramWriter.write(this.currentFormat.lineSeparator);
    }
  }

  protected void printDocType(Writer paramWriter, DocType paramDocType)
    throws IOException
  {
    String str1 = paramDocType.getPublicID();
    String str2 = paramDocType.getSystemID();
    String str3 = paramDocType.getInternalSubset();
    paramWriter.write("<!DOCTYPE ");
    paramWriter.write(paramDocType.getElementName());
    int i = 0;
    if (str1 != null)
    {
      paramWriter.write(" PUBLIC \"");
      paramWriter.write(str1);
      paramWriter.write("\"");
      i = 1;
    }
    if (str2 != null)
    {
      if (i == 0)
        paramWriter.write(" SYSTEM");
      paramWriter.write(" \"");
      paramWriter.write(str2);
      paramWriter.write("\"");
    }
    if ((str3 != null) && (!str3.equals("")))
    {
      paramWriter.write(" [");
      paramWriter.write(this.currentFormat.lineSeparator);
      paramWriter.write(paramDocType.getInternalSubset());
      paramWriter.write("]");
    }
    paramWriter.write(">");
  }

  protected void printElement(Writer paramWriter, Element paramElement, int paramInt, NamespaceStack paramNamespaceStack)
    throws IOException
  {
    List localList1 = paramElement.getAttributes();
    List localList2 = paramElement.getContent();
    String str = null;
    if (localList1 != null)
      str = paramElement.getAttributeValue("space", Namespace.XML_NAMESPACE);
    Format localFormat = this.currentFormat;
    int i;
    int j;
    int k;
    if ("default".equals(str))
    {
      this.currentFormat = this.userFormat;
      paramWriter.write("<");
      printQualifiedName(paramWriter, paramElement);
      i = paramNamespaceStack.size();
      printElementNamespace(paramWriter, paramElement, paramNamespaceStack);
      printAdditionalNamespaces(paramWriter, paramElement, paramNamespaceStack);
      if (localList1 != null)
        printAttributes(paramWriter, localList1, paramElement, paramNamespaceStack);
      j = skipLeadingWhite(localList2, 0);
      k = localList2.size();
      if (j < k)
        break label213;
      if (!this.currentFormat.expandEmptyElements)
        break label203;
      paramWriter.write("></");
      printQualifiedName(paramWriter, paramElement);
      paramWriter.write(">");
    }
    while (true)
    {
      if (paramNamespaceStack.size() <= i)
        break label301;
      paramNamespaceStack.pop();
      continue;
      if (!"preserve".equals(str))
        break;
      this.currentFormat = preserveFormat;
      break;
      label203: paramWriter.write(" />");
    }
    label213: paramWriter.write(">");
    if (nextNonText(localList2, j) < k)
    {
      newline(paramWriter);
      printContentRange(paramWriter, localList2, j, k, paramInt + 1, paramNamespaceStack);
      newline(paramWriter);
      indent(paramWriter, paramInt);
    }
    while (true)
    {
      paramWriter.write("</");
      printQualifiedName(paramWriter, paramElement);
      paramWriter.write(">");
      break;
      printTextRange(paramWriter, localList2, j, k);
    }
    label301: this.currentFormat = localFormat;
  }

  protected void printEntityRef(Writer paramWriter, EntityRef paramEntityRef)
    throws IOException
  {
    paramWriter.write("&");
    paramWriter.write(paramEntityRef.getName());
    paramWriter.write(";");
  }

  protected void printProcessingInstruction(Writer paramWriter, ProcessingInstruction paramProcessingInstruction)
    throws IOException
  {
    String str1 = paramProcessingInstruction.getTarget();
    boolean bool1 = this.currentFormat.ignoreTrAXEscapingPIs;
    int i = 0;
    if (!bool1)
    {
      if (!str1.equals("javax.xml.transform.disable-output-escaping"))
        break label94;
      this.escapeOutput = false;
      i = 1;
    }
    while (true)
    {
      if (i == 0)
      {
        String str2 = paramProcessingInstruction.getData();
        if ("".equals(str2))
          break;
        paramWriter.write("<?");
        paramWriter.write(str1);
        paramWriter.write(" ");
        paramWriter.write(str2);
        paramWriter.write("?>");
      }
      return;
      label94: boolean bool2 = str1.equals("javax.xml.transform.enable-output-escaping");
      i = 0;
      if (bool2)
      {
        this.escapeOutput = true;
        i = 1;
      }
    }
    paramWriter.write("<?");
    paramWriter.write(str1);
    paramWriter.write("?>");
  }

  protected void printText(Writer paramWriter, Text paramText)
    throws IOException
  {
    String str;
    if (this.currentFormat.mode == Format.TextMode.NORMALIZE)
      str = paramText.getTextNormalize();
    while (true)
    {
      paramWriter.write(escapeElementEntities(str));
      return;
      if (this.currentFormat.mode == Format.TextMode.TRIM)
        str = paramText.getText().trim();
      else
        str = paramText.getText();
    }
  }

  public void setFormat(Format paramFormat)
  {
    this.userFormat = ((Format)paramFormat.clone());
    this.currentFormat = this.userFormat;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (i < this.userFormat.lineSeparator.length())
    {
      int j = this.userFormat.lineSeparator.charAt(i);
      switch (j)
      {
      case 11:
      case 12:
      default:
        localStringBuffer.append("[" + j + "]");
      case 13:
      case 10:
      case 9:
      }
      while (true)
      {
        i++;
        break;
        localStringBuffer.append("\\r");
        continue;
        localStringBuffer.append("\\n");
        continue;
        localStringBuffer.append("\\t");
      }
    }
    return "XMLOutputter[omitDeclaration = " + this.userFormat.omitDeclaration + ", " + "encoding = " + this.userFormat.encoding + ", " + "omitEncoding = " + this.userFormat.omitEncoding + ", " + "indent = '" + this.userFormat.indent + "'" + ", " + "expandEmptyElements = " + this.userFormat.expandEmptyElements + ", " + "lineSeparator = '" + localStringBuffer.toString() + "', " + "textMode = " + this.userFormat.mode + "]";
  }

  protected class NamespaceStack extends NamespaceStack
  {
    protected NamespaceStack()
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.output.XMLOutputter
 * JD-Core Version:    0.6.2
 */