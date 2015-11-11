package org.jdom.input;

import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.jdom.JDOMException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

class JAXPParserFactory
{
  private static final String CVS_ID = "@(#) $RCSfile: JAXPParserFactory.java,v $ $Revision: 1.6 $ $Date: 2007/11/10 05:29:00 $ $Name: jdom_1_1_1 $";
  private static final String JAXP_SCHEMA_LANGUAGE_PROPERTY = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
  private static final String JAXP_SCHEMA_LOCATION_PROPERTY = "http://java.sun.com/xml/jaxp/properties/schemaSource";

  public static XMLReader createParser(boolean paramBoolean, Map paramMap1, Map paramMap2)
    throws JDOMException
  {
    try
    {
      SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
      localSAXParserFactory.setValidating(paramBoolean);
      localSAXParserFactory.setNamespaceAware(true);
      try
      {
        SAXParser localSAXParser = localSAXParserFactory.newSAXParser();
        setProperty(localSAXParser, paramMap2, "http://java.sun.com/xml/jaxp/properties/schemaLanguage");
        setProperty(localSAXParser, paramMap2, "http://java.sun.com/xml/jaxp/properties/schemaSource");
        return localSAXParser.getXMLReader();
      }
      catch (ParserConfigurationException localParserConfigurationException)
      {
        throw new JDOMException("Could not allocate JAXP SAX Parser", localParserConfigurationException);
      }
    }
    catch (SAXException localSAXException)
    {
      throw new JDOMException("Could not allocate JAXP SAX Parser", localSAXException);
    }
  }

  private static void setProperty(SAXParser paramSAXParser, Map paramMap, String paramString)
    throws JDOMException
  {
    try
    {
      if (paramMap.containsKey(paramString))
        paramSAXParser.setProperty(paramString, paramMap.get(paramString));
      return;
    }
    catch (SAXNotSupportedException localSAXNotSupportedException)
    {
      throw new JDOMException(paramString + " property not supported for JAXP parser " + paramSAXParser.getClass().getName());
    }
    catch (SAXNotRecognizedException localSAXNotRecognizedException)
    {
    }
    throw new JDOMException(paramString + " property not recognized for JAXP parser " + paramSAXParser.getClass().getName());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.input.JAXPParserFactory
 * JD-Core Version:    0.6.2
 */