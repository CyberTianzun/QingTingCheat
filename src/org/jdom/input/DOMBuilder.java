package org.jdom.input;

import org.jdom.Attribute;
import org.jdom.DefaultJDOMFactory;
import org.jdom.DocType;
import org.jdom.EntityRef;
import org.jdom.JDOMFactory;
import org.jdom.Namespace;
import org.w3c.dom.Attr;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMBuilder
{
  private static final String CVS_ID = "@(#) $RCSfile: DOMBuilder.java,v $ $Revision: 1.60 $ $Date: 2007/11/10 05:29:00 $ $Name: jdom_1_1_1 $";
  private String adapterClass;
  private JDOMFactory factory = new DefaultJDOMFactory();

  public DOMBuilder()
  {
  }

  public DOMBuilder(String paramString)
  {
    this.adapterClass = paramString;
  }

  private void buildTree(Node paramNode, org.jdom.Document paramDocument, org.jdom.Element paramElement, boolean paramBoolean)
  {
    switch (paramNode.getNodeType())
    {
    case 2:
    case 6:
    default:
    case 9:
    case 1:
      org.jdom.Element localElement;
      NodeList localNodeList1;
      do
      {
        while (true)
        {
          return;
          NodeList localNodeList2 = paramNode.getChildNodes();
          int i4 = 0;
          int i5 = localNodeList2.getLength();
          while (i4 < i5)
          {
            buildTree(localNodeList2.item(i4), paramDocument, paramElement, true);
            i4++;
          }
        }
        String str6 = paramNode.getNodeName();
        String str7 = "";
        String str8 = str6;
        int i = str6.indexOf(':');
        if (i >= 0)
        {
          str7 = str6.substring(0, i);
          str8 = str6.substring(i + 1);
        }
        String str9 = paramNode.getNamespaceURI();
        Namespace localNamespace1;
        NamedNodeMap localNamedNodeMap;
        int j;
        int k;
        Namespace localNamespace3;
        if (str9 == null)
          if (paramElement == null)
          {
            localNamespace1 = Namespace.NO_NAMESPACE;
            localElement = this.factory.element(str8, localNamespace1);
            if (!paramBoolean)
              break label360;
            paramDocument.setRootElement(localElement);
            localNamedNodeMap = paramNode.getAttributes();
            j = localNamedNodeMap.getLength();
            k = 0;
            if (k >= j)
              break label391;
            Attr localAttr2 = (Attr)localNamedNodeMap.item(k);
            String str14 = localAttr2.getName();
            if (str14.startsWith("xmlns"))
            {
              String str15 = "";
              int i3 = str14.indexOf(':');
              if (i3 >= 0)
                str15 = str14.substring(i3 + 1);
              localNamespace3 = Namespace.getNamespace(str15, localAttr2.getValue());
              if (!str7.equals(str15))
                break label375;
              localElement.setNamespace(localNamespace3);
            }
          }
        while (true)
        {
          k++;
          break label230;
          localNamespace1 = paramElement.getNamespace(str7);
          break;
          localNamespace1 = Namespace.getNamespace(str7, str9);
          break;
          this.factory.addContent(paramElement, localElement);
          break label210;
          this.factory.addNamespaceDeclaration(localElement, localNamespace3);
        }
        int m = 0;
        if (m < j)
        {
          Attr localAttr1 = (Attr)localNamedNodeMap.item(m);
          String str10 = localAttr1.getName();
          String str11;
          String str12;
          String str13;
          if (!str10.startsWith("xmlns"))
          {
            str11 = "";
            str12 = str10;
            int i2 = str10.indexOf(':');
            if (i2 >= 0)
            {
              str11 = str10.substring(0, i2);
              str12 = str10.substring(i2 + 1);
            }
            str13 = localAttr1.getValue();
            if (!"".equals(str11))
              break label537;
          }
          for (Namespace localNamespace2 = Namespace.NO_NAMESPACE; ; localNamespace2 = localElement.getNamespace(str11))
          {
            Attribute localAttribute = this.factory.attribute(str12, str13, localNamespace2);
            this.factory.setAttribute(localElement, localAttribute);
            m++;
            break;
          }
        }
        localNodeList1 = paramNode.getChildNodes();
      }
      while (localNodeList1 == null);
      int n = localNodeList1.getLength();
      for (int i1 = 0; i1 < n; i1++)
      {
        Node localNode = localNodeList1.item(i1);
        if (localNode != null)
          buildTree(localNode, paramDocument, localElement, false);
      }
    case 3:
      String str5 = paramNode.getNodeValue();
      this.factory.addContent(paramElement, this.factory.text(str5));
      return;
    case 4:
      String str4 = paramNode.getNodeValue();
      this.factory.addContent(paramElement, this.factory.cdata(str4));
      return;
    case 7:
      if (paramBoolean)
      {
        this.factory.addContent(paramDocument, this.factory.processingInstruction(paramNode.getNodeName(), paramNode.getNodeValue()));
        return;
      }
      this.factory.addContent(paramElement, this.factory.processingInstruction(paramNode.getNodeName(), paramNode.getNodeValue()));
      return;
    case 8:
      if (paramBoolean)
      {
        this.factory.addContent(paramDocument, this.factory.comment(paramNode.getNodeValue()));
        return;
      }
      this.factory.addContent(paramElement, this.factory.comment(paramNode.getNodeValue()));
      return;
    case 5:
      label210: label230: label360: label375: EntityRef localEntityRef = this.factory.entityRef(paramNode.getNodeName());
      label391: label537: this.factory.addContent(paramElement, localEntityRef);
      return;
    case 10:
    }
    DocumentType localDocumentType = (DocumentType)paramNode;
    String str1 = localDocumentType.getPublicId();
    String str2 = localDocumentType.getSystemId();
    String str3 = localDocumentType.getInternalSubset();
    DocType localDocType = this.factory.docType(localDocumentType.getName());
    localDocType.setPublicID(str1);
    localDocType.setSystemID(str2);
    localDocType.setInternalSubset(str3);
    this.factory.addContent(paramDocument, localDocType);
  }

  public org.jdom.Document build(org.w3c.dom.Document paramDocument)
  {
    org.jdom.Document localDocument = this.factory.document(null);
    buildTree(paramDocument, localDocument, null, true);
    return localDocument;
  }

  public org.jdom.Element build(org.w3c.dom.Element paramElement)
  {
    org.jdom.Document localDocument = this.factory.document(null);
    buildTree(paramElement, localDocument, null, true);
    return localDocument.getRootElement();
  }

  public JDOMFactory getFactory()
  {
    return this.factory;
  }

  public void setFactory(JDOMFactory paramJDOMFactory)
  {
    this.factory = paramJDOMFactory;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.input.DOMBuilder
 * JD-Core Version:    0.6.2
 */