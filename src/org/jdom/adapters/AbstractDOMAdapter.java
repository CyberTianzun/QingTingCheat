package org.jdom.adapters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import org.jdom.DocType;
import org.jdom.JDOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

public abstract class AbstractDOMAdapter
  implements DOMAdapter
{
  private static final String CVS_ID = "@(#) $RCSfile: AbstractDOMAdapter.java,v $ $Revision: 1.21 $ $Date: 2007/11/10 05:28:59 $ $Name: jdom_1_1_1 $";
  static Class class$java$lang$String;

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

  public abstract Document createDocument()
    throws JDOMException;

  public Document createDocument(DocType paramDocType)
    throws JDOMException
  {
    if (paramDocType == null)
      return createDocument();
    DOMImplementation localDOMImplementation = createDocument().getImplementation();
    DocumentType localDocumentType = localDOMImplementation.createDocumentType(paramDocType.getElementName(), paramDocType.getPublicID(), paramDocType.getSystemID());
    setInternalSubset(localDocumentType, paramDocType.getInternalSubset());
    return localDOMImplementation.createDocument("http://temporary", paramDocType.getElementName(), localDocumentType);
  }

  public Document getDocument(File paramFile, boolean paramBoolean)
    throws IOException, JDOMException
  {
    return getDocument(new FileInputStream(paramFile), paramBoolean);
  }

  public abstract Document getDocument(InputStream paramInputStream, boolean paramBoolean)
    throws IOException, JDOMException;

  protected void setInternalSubset(DocumentType paramDocumentType, String paramString)
  {
    if ((paramDocumentType == null) || (paramString == null))
      return;
    try
    {
      Class localClass1 = paramDocumentType.getClass();
      Class[] arrayOfClass = new Class[1];
      Class localClass2;
      if (class$java$lang$String == null)
      {
        localClass2 = class$("java.lang.String");
        class$java$lang$String = localClass2;
      }
      while (true)
      {
        arrayOfClass[0] = localClass2;
        localClass1.getMethod("setInternalSubset", arrayOfClass).invoke(paramDocumentType, new Object[] { paramString });
        return;
        localClass2 = class$java$lang$String;
      }
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.adapters.AbstractDOMAdapter
 * JD-Core Version:    0.6.2
 */