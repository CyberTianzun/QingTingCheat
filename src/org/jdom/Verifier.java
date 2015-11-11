package org.jdom;

import java.util.Iterator;
import java.util.List;

public final class Verifier
{
  private static final String CVS_ID = "@(#) $RCSfile: Verifier.java,v $ $Revision: 1.57 $ $Date: 2009/07/23 05:54:23 $ $Name: jdom_1_1_1 $";

  public static String checkAttributeName(String paramString)
  {
    String str = checkXMLName(paramString);
    if (str != null)
      return str;
    if (paramString.indexOf(":") != -1)
      return "Attribute names cannot contain colons";
    if (paramString.equals("xmlns"))
      return "An Attribute name may not be \"xmlns\"; use the Namespace class to manage namespaces";
    return null;
  }

  public static String checkCDATASection(String paramString)
  {
    String str = checkCharacterData(paramString);
    if (str != null)
      return str;
    if (paramString.indexOf("]]>") != -1)
      return "CDATA cannot internally contain a CDATA ending delimiter (]]>)";
    return null;
  }

  public static String checkCharacterData(String paramString)
  {
    if (paramString == null)
      return "A null is not a legal XML value";
    int i = 0;
    int j = paramString.length();
    while (i < j)
    {
      int k = paramString.charAt(i);
      if (isHighSurrogate((char)k))
      {
        i++;
        if (i < j)
        {
          char c = paramString.charAt(i);
          if (!isLowSurrogate(c))
            return "Illegal Surrogate Pair";
          k = decodeSurrogatePair((char)k, c);
        }
      }
      else
      {
        if (isXMLCharacter(k))
          break label105;
        return "0x" + Integer.toHexString(k) + " is not a legal XML character";
      }
      return "Surrogate Pair Truncated";
      label105: i++;
    }
    return null;
  }

  public static String checkCommentData(String paramString)
  {
    String str = checkCharacterData(paramString);
    if (str != null)
      return str;
    if (paramString.indexOf("--") != -1)
      return "Comments cannot contain double hyphens (--)";
    if (paramString.endsWith("-"))
      return "Comment data cannot end with a hyphen.";
    return null;
  }

  public static String checkElementName(String paramString)
  {
    String str = checkXMLName(paramString);
    if (str != null)
      return str;
    if (paramString.indexOf(":") != -1)
      return "Element names cannot contain colons";
    return null;
  }

  public static String checkNamespaceCollision(Attribute paramAttribute, Element paramElement)
  {
    Namespace localNamespace = paramAttribute.getNamespace();
    if ("".equals(localNamespace.getPrefix()))
      return null;
    return checkNamespaceCollision(localNamespace, paramElement);
  }

  public static String checkNamespaceCollision(Namespace paramNamespace, List paramList)
  {
    String str;
    if (paramList == null)
      str = null;
    while (true)
    {
      return str;
      str = null;
      Iterator localIterator = paramList.iterator();
      while ((str == null) && (localIterator.hasNext()))
      {
        Object localObject = localIterator.next();
        if ((localObject instanceof Attribute))
        {
          str = checkNamespaceCollision(paramNamespace, (Attribute)localObject);
        }
        else if ((localObject instanceof Element))
        {
          str = checkNamespaceCollision(paramNamespace, (Element)localObject);
        }
        else if ((localObject instanceof Namespace))
        {
          str = checkNamespaceCollision(paramNamespace, (Namespace)localObject);
          if (str != null)
            str = str + " with an additional namespace declared by the element";
        }
      }
    }
  }

  public static String checkNamespaceCollision(Namespace paramNamespace, Attribute paramAttribute)
  {
    boolean bool = paramAttribute.getNamespace().equals(Namespace.NO_NAMESPACE);
    String str = null;
    if (!bool)
    {
      str = checkNamespaceCollision(paramNamespace, paramAttribute.getNamespace());
      if (str != null)
        str = str + " with an attribute namespace prefix on the element";
    }
    return str;
  }

  public static String checkNamespaceCollision(Namespace paramNamespace, Element paramElement)
  {
    String str1 = checkNamespaceCollision(paramNamespace, paramElement.getNamespace());
    if (str1 != null)
      return str1 + " with the element namespace prefix";
    String str2 = checkNamespaceCollision(paramNamespace, paramElement.getAdditionalNamespaces());
    if (str2 != null)
      return str2;
    String str3 = checkNamespaceCollision(paramNamespace, paramElement.getAttributes());
    if (str3 != null)
      return str3;
    return null;
  }

  public static String checkNamespaceCollision(Namespace paramNamespace1, Namespace paramNamespace2)
  {
    String str1 = paramNamespace1.getPrefix();
    String str2 = paramNamespace1.getURI();
    String str3 = paramNamespace2.getPrefix();
    String str4 = paramNamespace2.getURI();
    boolean bool1 = str1.equals(str3);
    String str5 = null;
    if (bool1)
    {
      boolean bool2 = str2.equals(str4);
      str5 = null;
      if (!bool2)
        str5 = "The namespace prefix \"" + str1 + "\" collides";
    }
    return str5;
  }

  public static String checkNamespacePrefix(String paramString)
  {
    if ((paramString == null) || (paramString.equals("")));
    do
    {
      return null;
      char c1 = paramString.charAt(0);
      if (isXMLDigit(c1))
        return "Namespace prefixes cannot begin with a number";
      if (c1 == '$')
        return "Namespace prefixes cannot begin with a dollar sign ($)";
      if (c1 == '-')
        return "Namespace prefixes cannot begin with a hyphen (-)";
      if (c1 == '.')
        return "Namespace prefixes cannot begin with a period (.)";
      if (paramString.toLowerCase().startsWith("xml"))
        return "Namespace prefixes cannot begin with \"xml\" in any combination of case";
      int i = 0;
      int j = paramString.length();
      while (i < j)
      {
        char c2 = paramString.charAt(i);
        if (!isXMLNameCharacter(c2))
          return "Namespace prefixes cannot contain the character \"" + c2 + "\"";
        i++;
      }
    }
    while (paramString.indexOf(":") == -1);
    return "Namespace prefixes cannot contain colons";
  }

  public static String checkNamespaceURI(String paramString)
  {
    if ((paramString == null) || (paramString.equals("")));
    char c;
    do
    {
      return null;
      c = paramString.charAt(0);
      if (Character.isDigit(c))
        return "Namespace URIs cannot begin with a number";
      if (c == '$')
        return "Namespace URIs cannot begin with a dollar sign ($)";
    }
    while (c != '-');
    return "Namespace URIs cannot begin with a hyphen (-)";
  }

  public static String checkProcessingInstructionData(String paramString)
  {
    String str = checkCharacterData(paramString);
    if ((str == null) && (paramString.indexOf("?>") >= 0))
      str = "Processing instructions cannot contain the string \"?>\"";
    return str;
  }

  public static String checkProcessingInstructionTarget(String paramString)
  {
    String str = checkXMLName(paramString);
    if (str != null)
      return str;
    if (paramString.indexOf(":") != -1)
      return "Processing instruction targets cannot contain colons";
    if (paramString.equalsIgnoreCase("xml"))
      return "Processing instructions cannot have a target of \"xml\" in any combination of case. (Note that the \"<?xml ... ?>\" declaration at the beginning of a document is not a processing instruction and should not be added as one; it is written automatically during output, e.g. by XMLOutputter.)";
    return null;
  }

  public static String checkPublicID(String paramString)
  {
    if (paramString == null)
      return null;
    for (int i = 0; ; i++)
    {
      int j = paramString.length();
      String str = null;
      if (i < j)
      {
        char c = paramString.charAt(i);
        if (!isXMLPublicIDCharacter(c))
          str = c + " is not a legal character in public IDs";
      }
      else
      {
        return str;
      }
    }
  }

  public static String checkSystemLiteral(String paramString)
  {
    if (paramString == null)
      return null;
    if ((paramString.indexOf('\'') != -1) && (paramString.indexOf('"') != -1));
    for (String str = "System literals cannot simultaneously contain both single and double quotes."; ; str = checkCharacterData(paramString))
      return str;
  }

  public static String checkURI(String paramString)
  {
    if ((paramString == null) || (paramString.equals("")));
    while (true)
    {
      return null;
      for (int i = 0; i < paramString.length(); i++)
      {
        char c1 = paramString.charAt(i);
        if (!isURICharacter(c1))
        {
          String str = "0x" + Integer.toHexString(c1);
          if (c1 <= '\t')
            str = "0x0" + Integer.toHexString(c1);
          return "URIs cannot contain " + str;
        }
        if (c1 == '%')
        {
          int j = i + 1;
          try
          {
            char c2 = paramString.charAt(j);
            char c3 = paramString.charAt(i + 2);
            if ((!isHexDigit(c2)) || (!isHexDigit(c3)))
              return "Percent signs in URIs must be followed by exactly two hexadecimal digits.";
          }
          catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException)
          {
            return "Percent signs in URIs must be followed by exactly two hexadecimal digits.";
          }
        }
      }
    }
  }

  public static String checkXMLName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0) || (paramString.trim().equals("")))
      return "XML names cannot be null or empty";
    char c1 = paramString.charAt(0);
    if (!isXMLNameStartCharacter(c1))
      return "XML names cannot begin with the character \"" + c1 + "\"";
    int i = 1;
    int j = paramString.length();
    while (i < j)
    {
      char c2 = paramString.charAt(i);
      if (!isXMLNameCharacter(c2))
        return "XML names cannot contain the character \"" + c2 + "\"";
      i++;
    }
    return null;
  }

  public static int decodeSurrogatePair(char paramChar1, char paramChar2)
  {
    return 65536 + 1024 * (paramChar1 - 55296) + (paramChar2 - 56320);
  }

  public static boolean isHexDigit(char paramChar)
  {
    if ((paramChar >= '0') && (paramChar <= '9'));
    while (((paramChar >= 'A') && (paramChar <= 'F')) || ((paramChar >= 'a') && (paramChar <= 'f')))
      return true;
    return false;
  }

  public static boolean isHighSurrogate(char paramChar)
  {
    return (paramChar >= 55296) && (paramChar <= 56319);
  }

  public static boolean isLowSurrogate(char paramChar)
  {
    return (paramChar >= 56320) && (paramChar <= 57343);
  }

  public static boolean isURICharacter(char paramChar)
  {
    if ((paramChar >= 'a') && (paramChar <= 'z'));
    while (((paramChar >= 'A') && (paramChar <= 'Z')) || ((paramChar >= '0') && (paramChar <= '9')) || (paramChar == '/') || (paramChar == '-') || (paramChar == '.') || (paramChar == '?') || (paramChar == ':') || (paramChar == '@') || (paramChar == '&') || (paramChar == '=') || (paramChar == '+') || (paramChar == '$') || (paramChar == ',') || (paramChar == '%') || (paramChar == '_') || (paramChar == '!') || (paramChar == '~') || (paramChar == '*') || (paramChar == '\'') || (paramChar == '(') || (paramChar == ')'))
      return true;
    return false;
  }

  public static boolean isXMLCharacter(int paramInt)
  {
    if (paramInt == 10);
    do
    {
      do
      {
        do
        {
          do
            return true;
          while ((paramInt == 13) || (paramInt == 9));
          if (paramInt < 32)
            return false;
        }
        while (paramInt <= 55295);
        if (paramInt < 57344)
          return false;
      }
      while (paramInt <= 65533);
      if (paramInt < 65536)
        return false;
    }
    while (paramInt <= 1114111);
    return false;
  }

  public static boolean isXMLCombiningChar(char paramChar)
  {
    if (paramChar < '̀');
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                    {
                      do
                      {
                        do
                        {
                          do
                          {
                            do
                            {
                              do
                              {
                                do
                                {
                                  do
                                  {
                                    do
                                    {
                                      do
                                      {
                                        do
                                        {
                                          do
                                          {
                                            do
                                            {
                                              do
                                              {
                                                do
                                                {
                                                  do
                                                  {
                                                    do
                                                    {
                                                      do
                                                      {
                                                        do
                                                        {
                                                          do
                                                          {
                                                            do
                                                            {
                                                              do
                                                              {
                                                                do
                                                                {
                                                                  do
                                                                  {
                                                                    do
                                                                    {
                                                                      do
                                                                      {
                                                                        do
                                                                        {
                                                                          do
                                                                          {
                                                                            do
                                                                            {
                                                                              do
                                                                              {
                                                                                do
                                                                                {
                                                                                  do
                                                                                  {
                                                                                    do
                                                                                    {
                                                                                      do
                                                                                      {
                                                                                        do
                                                                                        {
                                                                                          do
                                                                                          {
                                                                                            do
                                                                                            {
                                                                                              do
                                                                                              {
                                                                                                do
                                                                                                {
                                                                                                  do
                                                                                                  {
                                                                                                    do
                                                                                                    {
                                                                                                      do
                                                                                                      {
                                                                                                        do
                                                                                                        {
                                                                                                          do
                                                                                                          {
                                                                                                            do
                                                                                                            {
                                                                                                              do
                                                                                                              {
                                                                                                                do
                                                                                                                {
                                                                                                                  do
                                                                                                                  {
                                                                                                                    do
                                                                                                                    {
                                                                                                                      do
                                                                                                                      {
                                                                                                                        do
                                                                                                                        {
                                                                                                                          do
                                                                                                                          {
                                                                                                                            do
                                                                                                                            {
                                                                                                                              do
                                                                                                                              {
                                                                                                                                do
                                                                                                                                {
                                                                                                                                  do
                                                                                                                                  {
                                                                                                                                    do
                                                                                                                                    {
                                                                                                                                      do
                                                                                                                                      {
                                                                                                                                        return false;
                                                                                                                                        if (paramChar <= 'ͅ')
                                                                                                                                          return true;
                                                                                                                                      }
                                                                                                                                      while (paramChar < '͠');
                                                                                                                                      if (paramChar <= '͡')
                                                                                                                                        return true;
                                                                                                                                    }
                                                                                                                                    while (paramChar < '҃');
                                                                                                                                    if (paramChar <= '҆')
                                                                                                                                      return true;
                                                                                                                                  }
                                                                                                                                  while (paramChar < '֑');
                                                                                                                                  if (paramChar <= '֡')
                                                                                                                                    return true;
                                                                                                                                }
                                                                                                                                while (paramChar < '֣');
                                                                                                                                if (paramChar <= 'ֹ')
                                                                                                                                  return true;
                                                                                                                              }
                                                                                                                              while (paramChar < 'ֻ');
                                                                                                                              if (paramChar <= 'ֽ')
                                                                                                                                return true;
                                                                                                                              if (paramChar == 'ֿ')
                                                                                                                                return true;
                                                                                                                            }
                                                                                                                            while (paramChar < 'ׁ');
                                                                                                                            if (paramChar <= 'ׂ')
                                                                                                                              return true;
                                                                                                                            if (paramChar == 'ׄ')
                                                                                                                              return true;
                                                                                                                          }
                                                                                                                          while (paramChar < 'ً');
                                                                                                                          if (paramChar <= 'ْ')
                                                                                                                            return true;
                                                                                                                          if (paramChar == 'ٰ')
                                                                                                                            return true;
                                                                                                                        }
                                                                                                                        while (paramChar < 'ۖ');
                                                                                                                        if (paramChar <= 'ۜ')
                                                                                                                          return true;
                                                                                                                      }
                                                                                                                      while (paramChar < '۝');
                                                                                                                      if (paramChar <= '۟')
                                                                                                                        return true;
                                                                                                                    }
                                                                                                                    while (paramChar < '۠');
                                                                                                                    if (paramChar <= 'ۤ')
                                                                                                                      return true;
                                                                                                                  }
                                                                                                                  while (paramChar < 'ۧ');
                                                                                                                  if (paramChar <= 'ۨ')
                                                                                                                    return true;
                                                                                                                }
                                                                                                                while (paramChar < '۪');
                                                                                                                if (paramChar <= 'ۭ')
                                                                                                                  return true;
                                                                                                              }
                                                                                                              while (paramChar < 'ँ');
                                                                                                              if (paramChar <= 'ः')
                                                                                                                return true;
                                                                                                              if (paramChar == '़')
                                                                                                                return true;
                                                                                                            }
                                                                                                            while (paramChar < 'ा');
                                                                                                            if (paramChar <= 'ौ')
                                                                                                              return true;
                                                                                                            if (paramChar == '्')
                                                                                                              return true;
                                                                                                          }
                                                                                                          while (paramChar < '॑');
                                                                                                          if (paramChar <= '॔')
                                                                                                            return true;
                                                                                                        }
                                                                                                        while (paramChar < 'ॢ');
                                                                                                        if (paramChar <= 'ॣ')
                                                                                                          return true;
                                                                                                      }
                                                                                                      while (paramChar < 'ঁ');
                                                                                                      if (paramChar <= 'ঃ')
                                                                                                        return true;
                                                                                                      if (paramChar == '়')
                                                                                                        return true;
                                                                                                      if (paramChar == 'া')
                                                                                                        return true;
                                                                                                      if (paramChar == 'ি')
                                                                                                        return true;
                                                                                                    }
                                                                                                    while (paramChar < 'ী');
                                                                                                    if (paramChar <= 'ৄ')
                                                                                                      return true;
                                                                                                  }
                                                                                                  while (paramChar < 'ে');
                                                                                                  if (paramChar <= 'ৈ')
                                                                                                    return true;
                                                                                                }
                                                                                                while (paramChar < 'ো');
                                                                                                if (paramChar <= '্')
                                                                                                  return true;
                                                                                                if (paramChar == 'ৗ')
                                                                                                  return true;
                                                                                              }
                                                                                              while (paramChar < 'ৢ');
                                                                                              if (paramChar <= 'ৣ')
                                                                                                return true;
                                                                                              if (paramChar == 'ਂ')
                                                                                                return true;
                                                                                              if (paramChar == '਼')
                                                                                                return true;
                                                                                              if (paramChar == 'ਾ')
                                                                                                return true;
                                                                                              if (paramChar == 'ਿ')
                                                                                                return true;
                                                                                            }
                                                                                            while (paramChar < 'ੀ');
                                                                                            if (paramChar <= 'ੂ')
                                                                                              return true;
                                                                                          }
                                                                                          while (paramChar < 'ੇ');
                                                                                          if (paramChar <= 'ੈ')
                                                                                            return true;
                                                                                        }
                                                                                        while (paramChar < 'ੋ');
                                                                                        if (paramChar <= '੍')
                                                                                          return true;
                                                                                      }
                                                                                      while (paramChar < 'ੰ');
                                                                                      if (paramChar <= 'ੱ')
                                                                                        return true;
                                                                                    }
                                                                                    while (paramChar < 'ઁ');
                                                                                    if (paramChar <= 'ઃ')
                                                                                      return true;
                                                                                    if (paramChar == '઼')
                                                                                      return true;
                                                                                  }
                                                                                  while (paramChar < 'ા');
                                                                                  if (paramChar <= 'ૅ')
                                                                                    return true;
                                                                                }
                                                                                while (paramChar < 'ે');
                                                                                if (paramChar <= 'ૉ')
                                                                                  return true;
                                                                              }
                                                                              while (paramChar < 'ો');
                                                                              if (paramChar <= '્')
                                                                                return true;
                                                                            }
                                                                            while (paramChar < 'ଁ');
                                                                            if (paramChar <= 'ଃ')
                                                                              return true;
                                                                            if (paramChar == '଼')
                                                                              return true;
                                                                          }
                                                                          while (paramChar < 'ା');
                                                                          if (paramChar <= 'ୃ')
                                                                            return true;
                                                                        }
                                                                        while (paramChar < 'େ');
                                                                        if (paramChar <= 'ୈ')
                                                                          return true;
                                                                      }
                                                                      while (paramChar < 'ୋ');
                                                                      if (paramChar <= '୍')
                                                                        return true;
                                                                    }
                                                                    while (paramChar < 'ୖ');
                                                                    if (paramChar <= 'ୗ')
                                                                      return true;
                                                                  }
                                                                  while (paramChar < 'ஂ');
                                                                  if (paramChar <= 'ஃ')
                                                                    return true;
                                                                }
                                                                while (paramChar < 'ா');
                                                                if (paramChar <= 'ூ')
                                                                  return true;
                                                              }
                                                              while (paramChar < 'ெ');
                                                              if (paramChar <= 'ை')
                                                                return true;
                                                            }
                                                            while (paramChar < 'ொ');
                                                            if (paramChar <= '்')
                                                              return true;
                                                            if (paramChar == 'ௗ')
                                                              return true;
                                                          }
                                                          while (paramChar < 'ఁ');
                                                          if (paramChar <= 'ః')
                                                            return true;
                                                        }
                                                        while (paramChar < 'ా');
                                                        if (paramChar <= 'ౄ')
                                                          return true;
                                                      }
                                                      while (paramChar < 'ె');
                                                      if (paramChar <= 'ై')
                                                        return true;
                                                    }
                                                    while (paramChar < 'ొ');
                                                    if (paramChar <= '్')
                                                      return true;
                                                  }
                                                  while (paramChar < 'ౕ');
                                                  if (paramChar <= 'ౖ')
                                                    return true;
                                                }
                                                while (paramChar < 'ಂ');
                                                if (paramChar <= 'ಃ')
                                                  return true;
                                              }
                                              while (paramChar < 'ಾ');
                                              if (paramChar <= 'ೄ')
                                                return true;
                                            }
                                            while (paramChar < 'ೆ');
                                            if (paramChar <= 'ೈ')
                                              return true;
                                          }
                                          while (paramChar < 'ೊ');
                                          if (paramChar <= '್')
                                            return true;
                                        }
                                        while (paramChar < 'ೕ');
                                        if (paramChar <= 'ೖ')
                                          return true;
                                      }
                                      while (paramChar < 'ം');
                                      if (paramChar <= 'ഃ')
                                        return true;
                                    }
                                    while (paramChar < 'ാ');
                                    if (paramChar <= 'ൃ')
                                      return true;
                                  }
                                  while (paramChar < 'െ');
                                  if (paramChar <= 'ൈ')
                                    return true;
                                }
                                while (paramChar < 'ൊ');
                                if (paramChar <= '്')
                                  return true;
                                if (paramChar == 'ൗ')
                                  return true;
                                if (paramChar == 'ั')
                                  return true;
                              }
                              while (paramChar < 'ิ');
                              if (paramChar <= 'ฺ')
                                return true;
                            }
                            while (paramChar < '็');
                            if (paramChar <= '๎')
                              return true;
                            if (paramChar == 'ັ')
                              return true;
                          }
                          while (paramChar < 'ິ');
                          if (paramChar <= 'ູ')
                            return true;
                        }
                        while (paramChar < 'ົ');
                        if (paramChar <= 'ຼ')
                          return true;
                      }
                      while (paramChar < '່');
                      if (paramChar <= 'ໍ')
                        return true;
                    }
                    while (paramChar < '༘');
                    if (paramChar <= '༙')
                      return true;
                    if (paramChar == '༵')
                      return true;
                    if (paramChar == '༷')
                      return true;
                    if (paramChar == '༹')
                      return true;
                    if (paramChar == '༾')
                      return true;
                    if (paramChar == '༿')
                      return true;
                  }
                  while (paramChar < 'ཱ');
                  if (paramChar <= '྄')
                    return true;
                }
                while (paramChar < '྆');
                if (paramChar <= 'ྋ')
                  return true;
              }
              while (paramChar < 'ྐ');
              if (paramChar <= 'ྕ')
                return true;
              if (paramChar == 'ྗ')
                return true;
            }
            while (paramChar < 'ྙ');
            if (paramChar <= 'ྭ')
              return true;
          }
          while (paramChar < 'ྱ');
          if (paramChar <= 'ྷ')
            return true;
          if (paramChar == 'ྐྵ')
            return true;
        }
        while (paramChar < '⃐');
        if (paramChar <= '⃜')
          return true;
        if (paramChar == '⃡')
          return true;
      }
      while (paramChar < '〪');
      if (paramChar <= '〯')
        return true;
      if (paramChar == '゙')
        return true;
    }
    while (paramChar != '゚');
    return true;
  }

  public static boolean isXMLDigit(char paramChar)
  {
    if (paramChar < '0');
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                    {
                      do
                      {
                        do
                        {
                          do
                          {
                            do
                            {
                              do
                              {
                                return false;
                                if (paramChar <= '9')
                                  return true;
                              }
                              while (paramChar < '٠');
                              if (paramChar <= '٩')
                                return true;
                            }
                            while (paramChar < '۰');
                            if (paramChar <= '۹')
                              return true;
                          }
                          while (paramChar < '०');
                          if (paramChar <= '९')
                            return true;
                        }
                        while (paramChar < '০');
                        if (paramChar <= '৯')
                          return true;
                      }
                      while (paramChar < '੦');
                      if (paramChar <= '੯')
                        return true;
                    }
                    while (paramChar < '૦');
                    if (paramChar <= '૯')
                      return true;
                  }
                  while (paramChar < '୦');
                  if (paramChar <= '୯')
                    return true;
                }
                while (paramChar < '௧');
                if (paramChar <= '௯')
                  return true;
              }
              while (paramChar < '౦');
              if (paramChar <= '౯')
                return true;
            }
            while (paramChar < '೦');
            if (paramChar <= '೯')
              return true;
          }
          while (paramChar < '൦');
          if (paramChar <= '൯')
            return true;
        }
        while (paramChar < '๐');
        if (paramChar <= '๙')
          return true;
      }
      while (paramChar < '໐');
      if (paramChar <= '໙')
        return true;
    }
    while ((paramChar < '༠') || (paramChar > '༩'));
    return true;
  }

  public static boolean isXMLExtender(char paramChar)
  {
    if (paramChar < '¶');
    do
    {
      do
      {
        do
        {
          return false;
          if (paramChar == '·')
            return true;
          if (paramChar == 'ː')
            return true;
          if (paramChar == 'ˑ')
            return true;
          if (paramChar == '·')
            return true;
          if (paramChar == 'ـ')
            return true;
          if (paramChar == 'ๆ')
            return true;
          if (paramChar == 'ໆ')
            return true;
          if (paramChar == '々')
            return true;
        }
        while (paramChar < '〱');
        if (paramChar <= '〵')
          return true;
      }
      while (paramChar < 'ゝ');
      if (paramChar <= 'ゞ')
        return true;
    }
    while ((paramChar < 'ー') || (paramChar > 'ヾ'));
    return true;
  }

  public static boolean isXMLLetter(char paramChar)
  {
    if (paramChar < 'A');
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                    {
                      do
                      {
                        do
                        {
                          do
                          {
                            do
                            {
                              do
                              {
                                do
                                {
                                  do
                                  {
                                    do
                                    {
                                      do
                                      {
                                        do
                                        {
                                          do
                                          {
                                            do
                                            {
                                              do
                                              {
                                                do
                                                {
                                                  do
                                                  {
                                                    do
                                                    {
                                                      do
                                                      {
                                                        do
                                                        {
                                                          do
                                                          {
                                                            do
                                                            {
                                                              do
                                                              {
                                                                do
                                                                {
                                                                  do
                                                                  {
                                                                    do
                                                                    {
                                                                      do
                                                                      {
                                                                        do
                                                                        {
                                                                          do
                                                                          {
                                                                            do
                                                                            {
                                                                              do
                                                                              {
                                                                                do
                                                                                {
                                                                                  do
                                                                                  {
                                                                                    do
                                                                                    {
                                                                                      do
                                                                                      {
                                                                                        do
                                                                                        {
                                                                                          do
                                                                                          {
                                                                                            do
                                                                                            {
                                                                                              do
                                                                                              {
                                                                                                do
                                                                                                {
                                                                                                  do
                                                                                                  {
                                                                                                    do
                                                                                                    {
                                                                                                      do
                                                                                                      {
                                                                                                        do
                                                                                                        {
                                                                                                          do
                                                                                                          {
                                                                                                            do
                                                                                                            {
                                                                                                              do
                                                                                                              {
                                                                                                                do
                                                                                                                {
                                                                                                                  do
                                                                                                                  {
                                                                                                                    do
                                                                                                                    {
                                                                                                                      do
                                                                                                                      {
                                                                                                                        do
                                                                                                                        {
                                                                                                                          do
                                                                                                                          {
                                                                                                                            do
                                                                                                                            {
                                                                                                                              do
                                                                                                                              {
                                                                                                                                do
                                                                                                                                {
                                                                                                                                  do
                                                                                                                                  {
                                                                                                                                    do
                                                                                                                                    {
                                                                                                                                      do
                                                                                                                                      {
                                                                                                                                        do
                                                                                                                                        {
                                                                                                                                          do
                                                                                                                                          {
                                                                                                                                            do
                                                                                                                                            {
                                                                                                                                              do
                                                                                                                                              {
                                                                                                                                                do
                                                                                                                                                {
                                                                                                                                                  do
                                                                                                                                                  {
                                                                                                                                                    do
                                                                                                                                                    {
                                                                                                                                                      do
                                                                                                                                                      {
                                                                                                                                                        do
                                                                                                                                                        {
                                                                                                                                                          do
                                                                                                                                                          {
                                                                                                                                                            do
                                                                                                                                                            {
                                                                                                                                                              do
                                                                                                                                                              {
                                                                                                                                                                do
                                                                                                                                                                {
                                                                                                                                                                  do
                                                                                                                                                                  {
                                                                                                                                                                    do
                                                                                                                                                                    {
                                                                                                                                                                      do
                                                                                                                                                                      {
                                                                                                                                                                        do
                                                                                                                                                                        {
                                                                                                                                                                          do
                                                                                                                                                                          {
                                                                                                                                                                            do
                                                                                                                                                                            {
                                                                                                                                                                              do
                                                                                                                                                                              {
                                                                                                                                                                                do
                                                                                                                                                                                {
                                                                                                                                                                                  do
                                                                                                                                                                                  {
                                                                                                                                                                                    do
                                                                                                                                                                                    {
                                                                                                                                                                                      do
                                                                                                                                                                                      {
                                                                                                                                                                                        do
                                                                                                                                                                                        {
                                                                                                                                                                                          do
                                                                                                                                                                                          {
                                                                                                                                                                                            do
                                                                                                                                                                                            {
                                                                                                                                                                                              do
                                                                                                                                                                                              {
                                                                                                                                                                                                do
                                                                                                                                                                                                {
                                                                                                                                                                                                  do
                                                                                                                                                                                                  {
                                                                                                                                                                                                    do
                                                                                                                                                                                                    {
                                                                                                                                                                                                      do
                                                                                                                                                                                                      {
                                                                                                                                                                                                        do
                                                                                                                                                                                                        {
                                                                                                                                                                                                          do
                                                                                                                                                                                                          {
                                                                                                                                                                                                            do
                                                                                                                                                                                                            {
                                                                                                                                                                                                              do
                                                                                                                                                                                                              {
                                                                                                                                                                                                                do
                                                                                                                                                                                                                {
                                                                                                                                                                                                                  do
                                                                                                                                                                                                                  {
                                                                                                                                                                                                                    do
                                                                                                                                                                                                                    {
                                                                                                                                                                                                                      do
                                                                                                                                                                                                                      {
                                                                                                                                                                                                                        do
                                                                                                                                                                                                                        {
                                                                                                                                                                                                                          do
                                                                                                                                                                                                                          {
                                                                                                                                                                                                                            do
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                              do
                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                do
                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                  do
                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                    do
                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                      do
                                                                                                                                                                                                                                      {
                                                                                                                                                                                                                                        do
                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                          do
                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                            do
                                                                                                                                                                                                                                            {
                                                                                                                                                                                                                                              do
                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                do
                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                  do
                                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                                    do
                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                      do
                                                                                                                                                                                                                                                      {
                                                                                                                                                                                                                                                        do
                                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                                          do
                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                            do
                                                                                                                                                                                                                                                            {
                                                                                                                                                                                                                                                              do
                                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                                do
                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                  do
                                                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                                                    do
                                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                                      do
                                                                                                                                                                                                                                                                      {
                                                                                                                                                                                                                                                                        do
                                                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                                                          do
                                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                                            do
                                                                                                                                                                                                                                                                            {
                                                                                                                                                                                                                                                                              do
                                                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                                                do
                                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                                  do
                                                                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                                                                    do
                                                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                                                      do
                                                                                                                                                                                                                                                                                      {
                                                                                                                                                                                                                                                                                        do
                                                                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                                                                          do
                                                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                                                            do
                                                                                                                                                                                                                                                                                            {
                                                                                                                                                                                                                                                                                              do
                                                                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                                                                do
                                                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                                                  do
                                                                                                                                                                                                                                                                                                  {
                                                                                                                                                                                                                                                                                                    do
                                                                                                                                                                                                                                                                                                    {
                                                                                                                                                                                                                                                                                                      do
                                                                                                                                                                                                                                                                                                      {
                                                                                                                                                                                                                                                                                                        do
                                                                                                                                                                                                                                                                                                        {
                                                                                                                                                                                                                                                                                                          do
                                                                                                                                                                                                                                                                                                          {
                                                                                                                                                                                                                                                                                                            do
                                                                                                                                                                                                                                                                                                            {
                                                                                                                                                                                                                                                                                                              do
                                                                                                                                                                                                                                                                                                              {
                                                                                                                                                                                                                                                                                                                do
                                                                                                                                                                                                                                                                                                                {
                                                                                                                                                                                                                                                                                                                  return false;
                                                                                                                                                                                                                                                                                                                  if (paramChar <= 'Z')
                                                                                                                                                                                                                                                                                                                    return true;
                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                while (paramChar < 'a');
                                                                                                                                                                                                                                                                                                                if (paramChar <= 'z')
                                                                                                                                                                                                                                                                                                                  return true;
                                                                                                                                                                                                                                                                                                              }
                                                                                                                                                                                                                                                                                                              while (paramChar < 'À');
                                                                                                                                                                                                                                                                                                              if (paramChar <= 'Ö')
                                                                                                                                                                                                                                                                                                                return true;
                                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                                            while (paramChar < 'Ø');
                                                                                                                                                                                                                                                                                                            if (paramChar <= 'ö')
                                                                                                                                                                                                                                                                                                              return true;
                                                                                                                                                                                                                                                                                                          }
                                                                                                                                                                                                                                                                                                          while (paramChar < 'ø');
                                                                                                                                                                                                                                                                                                          if (paramChar <= 'ÿ')
                                                                                                                                                                                                                                                                                                            return true;
                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                        while (paramChar < 'Ā');
                                                                                                                                                                                                                                                                                                        if (paramChar <= 'ı')
                                                                                                                                                                                                                                                                                                          return true;
                                                                                                                                                                                                                                                                                                      }
                                                                                                                                                                                                                                                                                                      while (paramChar < 'Ĵ');
                                                                                                                                                                                                                                                                                                      if (paramChar <= 'ľ')
                                                                                                                                                                                                                                                                                                        return true;
                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                    while (paramChar < 'Ł');
                                                                                                                                                                                                                                                                                                    if (paramChar <= 'ň')
                                                                                                                                                                                                                                                                                                      return true;
                                                                                                                                                                                                                                                                                                  }
                                                                                                                                                                                                                                                                                                  while (paramChar < 'Ŋ');
                                                                                                                                                                                                                                                                                                  if (paramChar <= 'ž')
                                                                                                                                                                                                                                                                                                    return true;
                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                while (paramChar < 'ƀ');
                                                                                                                                                                                                                                                                                                if (paramChar <= 'ǃ')
                                                                                                                                                                                                                                                                                                  return true;
                                                                                                                                                                                                                                                                                              }
                                                                                                                                                                                                                                                                                              while (paramChar < 'Ǎ');
                                                                                                                                                                                                                                                                                              if (paramChar <= 'ǰ')
                                                                                                                                                                                                                                                                                                return true;
                                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                                            while (paramChar < 'Ǵ');
                                                                                                                                                                                                                                                                                            if (paramChar <= 'ǵ')
                                                                                                                                                                                                                                                                                              return true;
                                                                                                                                                                                                                                                                                          }
                                                                                                                                                                                                                                                                                          while (paramChar < 'Ǻ');
                                                                                                                                                                                                                                                                                          if (paramChar <= 'ȗ')
                                                                                                                                                                                                                                                                                            return true;
                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                        while (paramChar < 'ɐ');
                                                                                                                                                                                                                                                                                        if (paramChar <= 'ʨ')
                                                                                                                                                                                                                                                                                          return true;
                                                                                                                                                                                                                                                                                      }
                                                                                                                                                                                                                                                                                      while (paramChar < 'ʻ');
                                                                                                                                                                                                                                                                                      if (paramChar <= 'ˁ')
                                                                                                                                                                                                                                                                                        return true;
                                                                                                                                                                                                                                                                                      if (paramChar == 'Ά')
                                                                                                                                                                                                                                                                                        return true;
                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                    while (paramChar < 'Έ');
                                                                                                                                                                                                                                                                                    if (paramChar <= 'Ί')
                                                                                                                                                                                                                                                                                      return true;
                                                                                                                                                                                                                                                                                    if (paramChar == 'Ό')
                                                                                                                                                                                                                                                                                      return true;
                                                                                                                                                                                                                                                                                  }
                                                                                                                                                                                                                                                                                  while (paramChar < 'Ύ');
                                                                                                                                                                                                                                                                                  if (paramChar <= 'Ρ')
                                                                                                                                                                                                                                                                                    return true;
                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                while (paramChar < 'Σ');
                                                                                                                                                                                                                                                                                if (paramChar <= 'ώ')
                                                                                                                                                                                                                                                                                  return true;
                                                                                                                                                                                                                                                                              }
                                                                                                                                                                                                                                                                              while (paramChar < 'ϐ');
                                                                                                                                                                                                                                                                              if (paramChar <= 'ϖ')
                                                                                                                                                                                                                                                                                return true;
                                                                                                                                                                                                                                                                              if (paramChar == 'Ϛ')
                                                                                                                                                                                                                                                                                return true;
                                                                                                                                                                                                                                                                              if (paramChar == 'Ϝ')
                                                                                                                                                                                                                                                                                return true;
                                                                                                                                                                                                                                                                              if (paramChar == 'Ϟ')
                                                                                                                                                                                                                                                                                return true;
                                                                                                                                                                                                                                                                              if (paramChar == 'Ϡ')
                                                                                                                                                                                                                                                                                return true;
                                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                                            while (paramChar < 'Ϣ');
                                                                                                                                                                                                                                                                            if (paramChar <= 'ϳ')
                                                                                                                                                                                                                                                                              return true;
                                                                                                                                                                                                                                                                          }
                                                                                                                                                                                                                                                                          while (paramChar < 'Ё');
                                                                                                                                                                                                                                                                          if (paramChar <= 'Ќ')
                                                                                                                                                                                                                                                                            return true;
                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                        while (paramChar < 'Ў');
                                                                                                                                                                                                                                                                        if (paramChar <= 'я')
                                                                                                                                                                                                                                                                          return true;
                                                                                                                                                                                                                                                                      }
                                                                                                                                                                                                                                                                      while (paramChar < 'ё');
                                                                                                                                                                                                                                                                      if (paramChar <= 'ќ')
                                                                                                                                                                                                                                                                        return true;
                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                    while (paramChar < 'ў');
                                                                                                                                                                                                                                                                    if (paramChar <= 'ҁ')
                                                                                                                                                                                                                                                                      return true;
                                                                                                                                                                                                                                                                  }
                                                                                                                                                                                                                                                                  while (paramChar < 'Ґ');
                                                                                                                                                                                                                                                                  if (paramChar <= 'ӄ')
                                                                                                                                                                                                                                                                    return true;
                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                while (paramChar < 'Ӈ');
                                                                                                                                                                                                                                                                if (paramChar <= 'ӈ')
                                                                                                                                                                                                                                                                  return true;
                                                                                                                                                                                                                                                              }
                                                                                                                                                                                                                                                              while (paramChar < 'Ӌ');
                                                                                                                                                                                                                                                              if (paramChar <= 'ӌ')
                                                                                                                                                                                                                                                                return true;
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                            while (paramChar < 'Ӑ');
                                                                                                                                                                                                                                                            if (paramChar <= 'ӫ')
                                                                                                                                                                                                                                                              return true;
                                                                                                                                                                                                                                                          }
                                                                                                                                                                                                                                                          while (paramChar < 'Ӯ');
                                                                                                                                                                                                                                                          if (paramChar <= 'ӵ')
                                                                                                                                                                                                                                                            return true;
                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                        while (paramChar < 'Ӹ');
                                                                                                                                                                                                                                                        if (paramChar <= 'ӹ')
                                                                                                                                                                                                                                                          return true;
                                                                                                                                                                                                                                                      }
                                                                                                                                                                                                                                                      while (paramChar < 'Ա');
                                                                                                                                                                                                                                                      if (paramChar <= 'Ֆ')
                                                                                                                                                                                                                                                        return true;
                                                                                                                                                                                                                                                      if (paramChar == 'ՙ')
                                                                                                                                                                                                                                                        return true;
                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                    while (paramChar < 'ա');
                                                                                                                                                                                                                                                    if (paramChar <= 'ֆ')
                                                                                                                                                                                                                                                      return true;
                                                                                                                                                                                                                                                  }
                                                                                                                                                                                                                                                  while (paramChar < 'א');
                                                                                                                                                                                                                                                  if (paramChar <= 'ת')
                                                                                                                                                                                                                                                    return true;
                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                while (paramChar < 'װ');
                                                                                                                                                                                                                                                if (paramChar <= 'ײ')
                                                                                                                                                                                                                                                  return true;
                                                                                                                                                                                                                                              }
                                                                                                                                                                                                                                              while (paramChar < 'ء');
                                                                                                                                                                                                                                              if (paramChar <= 'غ')
                                                                                                                                                                                                                                                return true;
                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                            while (paramChar < 'ف');
                                                                                                                                                                                                                                            if (paramChar <= 'ي')
                                                                                                                                                                                                                                              return true;
                                                                                                                                                                                                                                          }
                                                                                                                                                                                                                                          while (paramChar < 'ٱ');
                                                                                                                                                                                                                                          if (paramChar <= 'ڷ')
                                                                                                                                                                                                                                            return true;
                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                        while (paramChar < 'ں');
                                                                                                                                                                                                                                        if (paramChar <= 'ھ')
                                                                                                                                                                                                                                          return true;
                                                                                                                                                                                                                                      }
                                                                                                                                                                                                                                      while (paramChar < 'ۀ');
                                                                                                                                                                                                                                      if (paramChar <= 'ێ')
                                                                                                                                                                                                                                        return true;
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                    while (paramChar < 'ې');
                                                                                                                                                                                                                                    if (paramChar <= 'ۓ')
                                                                                                                                                                                                                                      return true;
                                                                                                                                                                                                                                    if (paramChar == 'ە')
                                                                                                                                                                                                                                      return true;
                                                                                                                                                                                                                                  }
                                                                                                                                                                                                                                  while (paramChar < 'ۥ');
                                                                                                                                                                                                                                  if (paramChar <= 'ۦ')
                                                                                                                                                                                                                                    return true;
                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                while (paramChar < 'अ');
                                                                                                                                                                                                                                if (paramChar <= 'ह')
                                                                                                                                                                                                                                  return true;
                                                                                                                                                                                                                                if (paramChar == 'ऽ')
                                                                                                                                                                                                                                  return true;
                                                                                                                                                                                                                              }
                                                                                                                                                                                                                              while (paramChar < 'क़');
                                                                                                                                                                                                                              if (paramChar <= 'ॡ')
                                                                                                                                                                                                                                return true;
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                            while (paramChar < 'অ');
                                                                                                                                                                                                                            if (paramChar <= 'ঌ')
                                                                                                                                                                                                                              return true;
                                                                                                                                                                                                                          }
                                                                                                                                                                                                                          while (paramChar < 'এ');
                                                                                                                                                                                                                          if (paramChar <= 'ঐ')
                                                                                                                                                                                                                            return true;
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                        while (paramChar < 'ও');
                                                                                                                                                                                                                        if (paramChar <= 'ন')
                                                                                                                                                                                                                          return true;
                                                                                                                                                                                                                      }
                                                                                                                                                                                                                      while (paramChar < 'প');
                                                                                                                                                                                                                      if (paramChar <= 'র')
                                                                                                                                                                                                                        return true;
                                                                                                                                                                                                                      if (paramChar == 'ল')
                                                                                                                                                                                                                        return true;
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                    while (paramChar < 'শ');
                                                                                                                                                                                                                    if (paramChar <= 'হ')
                                                                                                                                                                                                                      return true;
                                                                                                                                                                                                                  }
                                                                                                                                                                                                                  while (paramChar < 'ড়');
                                                                                                                                                                                                                  if (paramChar <= 'ঢ়')
                                                                                                                                                                                                                    return true;
                                                                                                                                                                                                                }
                                                                                                                                                                                                                while (paramChar < 'য়');
                                                                                                                                                                                                                if (paramChar <= 'ৡ')
                                                                                                                                                                                                                  return true;
                                                                                                                                                                                                              }
                                                                                                                                                                                                              while (paramChar < 'ৰ');
                                                                                                                                                                                                              if (paramChar <= 'ৱ')
                                                                                                                                                                                                                return true;
                                                                                                                                                                                                            }
                                                                                                                                                                                                            while (paramChar < 'ਅ');
                                                                                                                                                                                                            if (paramChar <= 'ਊ')
                                                                                                                                                                                                              return true;
                                                                                                                                                                                                          }
                                                                                                                                                                                                          while (paramChar < 'ਏ');
                                                                                                                                                                                                          if (paramChar <= 'ਐ')
                                                                                                                                                                                                            return true;
                                                                                                                                                                                                        }
                                                                                                                                                                                                        while (paramChar < 'ਓ');
                                                                                                                                                                                                        if (paramChar <= 'ਨ')
                                                                                                                                                                                                          return true;
                                                                                                                                                                                                      }
                                                                                                                                                                                                      while (paramChar < 'ਪ');
                                                                                                                                                                                                      if (paramChar <= 'ਰ')
                                                                                                                                                                                                        return true;
                                                                                                                                                                                                    }
                                                                                                                                                                                                    while (paramChar < 'ਲ');
                                                                                                                                                                                                    if (paramChar <= 'ਲ਼')
                                                                                                                                                                                                      return true;
                                                                                                                                                                                                  }
                                                                                                                                                                                                  while (paramChar < 'ਵ');
                                                                                                                                                                                                  if (paramChar <= 'ਸ਼')
                                                                                                                                                                                                    return true;
                                                                                                                                                                                                }
                                                                                                                                                                                                while (paramChar < 'ਸ');
                                                                                                                                                                                                if (paramChar <= 'ਹ')
                                                                                                                                                                                                  return true;
                                                                                                                                                                                              }
                                                                                                                                                                                              while (paramChar < 'ਖ਼');
                                                                                                                                                                                              if (paramChar <= 'ੜ')
                                                                                                                                                                                                return true;
                                                                                                                                                                                              if (paramChar == 'ਫ਼')
                                                                                                                                                                                                return true;
                                                                                                                                                                                            }
                                                                                                                                                                                            while (paramChar < 'ੲ');
                                                                                                                                                                                            if (paramChar <= 'ੴ')
                                                                                                                                                                                              return true;
                                                                                                                                                                                          }
                                                                                                                                                                                          while (paramChar < 'અ');
                                                                                                                                                                                          if (paramChar <= 'ઋ')
                                                                                                                                                                                            return true;
                                                                                                                                                                                          if (paramChar == 'ઍ')
                                                                                                                                                                                            return true;
                                                                                                                                                                                        }
                                                                                                                                                                                        while (paramChar < 'એ');
                                                                                                                                                                                        if (paramChar <= 'ઑ')
                                                                                                                                                                                          return true;
                                                                                                                                                                                      }
                                                                                                                                                                                      while (paramChar < 'ઓ');
                                                                                                                                                                                      if (paramChar <= 'ન')
                                                                                                                                                                                        return true;
                                                                                                                                                                                    }
                                                                                                                                                                                    while (paramChar < 'પ');
                                                                                                                                                                                    if (paramChar <= 'ર')
                                                                                                                                                                                      return true;
                                                                                                                                                                                  }
                                                                                                                                                                                  while (paramChar < 'લ');
                                                                                                                                                                                  if (paramChar <= 'ળ')
                                                                                                                                                                                    return true;
                                                                                                                                                                                }
                                                                                                                                                                                while (paramChar < 'વ');
                                                                                                                                                                                if (paramChar <= 'હ')
                                                                                                                                                                                  return true;
                                                                                                                                                                                if (paramChar == 'ઽ')
                                                                                                                                                                                  return true;
                                                                                                                                                                                if (paramChar == 'ૠ')
                                                                                                                                                                                  return true;
                                                                                                                                                                              }
                                                                                                                                                                              while (paramChar < 'ଅ');
                                                                                                                                                                              if (paramChar <= 'ଌ')
                                                                                                                                                                                return true;
                                                                                                                                                                            }
                                                                                                                                                                            while (paramChar < 'ଏ');
                                                                                                                                                                            if (paramChar <= 'ଐ')
                                                                                                                                                                              return true;
                                                                                                                                                                          }
                                                                                                                                                                          while (paramChar < 'ଓ');
                                                                                                                                                                          if (paramChar <= 'ନ')
                                                                                                                                                                            return true;
                                                                                                                                                                        }
                                                                                                                                                                        while (paramChar < 'ପ');
                                                                                                                                                                        if (paramChar <= 'ର')
                                                                                                                                                                          return true;
                                                                                                                                                                      }
                                                                                                                                                                      while (paramChar < 'ଲ');
                                                                                                                                                                      if (paramChar <= 'ଳ')
                                                                                                                                                                        return true;
                                                                                                                                                                    }
                                                                                                                                                                    while (paramChar < 'ଶ');
                                                                                                                                                                    if (paramChar <= 'ହ')
                                                                                                                                                                      return true;
                                                                                                                                                                    if (paramChar == 'ଽ')
                                                                                                                                                                      return true;
                                                                                                                                                                  }
                                                                                                                                                                  while (paramChar < 'ଡ଼');
                                                                                                                                                                  if (paramChar <= 'ଢ଼')
                                                                                                                                                                    return true;
                                                                                                                                                                }
                                                                                                                                                                while (paramChar < 'ୟ');
                                                                                                                                                                if (paramChar <= 'ୡ')
                                                                                                                                                                  return true;
                                                                                                                                                              }
                                                                                                                                                              while (paramChar < 'அ');
                                                                                                                                                              if (paramChar <= 'ஊ')
                                                                                                                                                                return true;
                                                                                                                                                            }
                                                                                                                                                            while (paramChar < 'எ');
                                                                                                                                                            if (paramChar <= 'ஐ')
                                                                                                                                                              return true;
                                                                                                                                                          }
                                                                                                                                                          while (paramChar < 'ஒ');
                                                                                                                                                          if (paramChar <= 'க')
                                                                                                                                                            return true;
                                                                                                                                                        }
                                                                                                                                                        while (paramChar < 'ங');
                                                                                                                                                        if (paramChar <= 'ச')
                                                                                                                                                          return true;
                                                                                                                                                        if (paramChar == 'ஜ')
                                                                                                                                                          return true;
                                                                                                                                                      }
                                                                                                                                                      while (paramChar < 'ஞ');
                                                                                                                                                      if (paramChar <= 'ட')
                                                                                                                                                        return true;
                                                                                                                                                    }
                                                                                                                                                    while (paramChar < 'ண');
                                                                                                                                                    if (paramChar <= 'த')
                                                                                                                                                      return true;
                                                                                                                                                  }
                                                                                                                                                  while (paramChar < 'ந');
                                                                                                                                                  if (paramChar <= 'ப')
                                                                                                                                                    return true;
                                                                                                                                                }
                                                                                                                                                while (paramChar < 'ம');
                                                                                                                                                if (paramChar <= 'வ')
                                                                                                                                                  return true;
                                                                                                                                              }
                                                                                                                                              while (paramChar < 'ஷ');
                                                                                                                                              if (paramChar <= 'ஹ')
                                                                                                                                                return true;
                                                                                                                                            }
                                                                                                                                            while (paramChar < 'అ');
                                                                                                                                            if (paramChar <= 'ఌ')
                                                                                                                                              return true;
                                                                                                                                          }
                                                                                                                                          while (paramChar < 'ఎ');
                                                                                                                                          if (paramChar <= 'ఐ')
                                                                                                                                            return true;
                                                                                                                                        }
                                                                                                                                        while (paramChar < 'ఒ');
                                                                                                                                        if (paramChar <= 'న')
                                                                                                                                          return true;
                                                                                                                                      }
                                                                                                                                      while (paramChar < 'ప');
                                                                                                                                      if (paramChar <= 'ళ')
                                                                                                                                        return true;
                                                                                                                                    }
                                                                                                                                    while (paramChar < 'వ');
                                                                                                                                    if (paramChar <= 'హ')
                                                                                                                                      return true;
                                                                                                                                  }
                                                                                                                                  while (paramChar < 'ౠ');
                                                                                                                                  if (paramChar <= 'ౡ')
                                                                                                                                    return true;
                                                                                                                                }
                                                                                                                                while (paramChar < 'ಅ');
                                                                                                                                if (paramChar <= 'ಌ')
                                                                                                                                  return true;
                                                                                                                              }
                                                                                                                              while (paramChar < 'ಎ');
                                                                                                                              if (paramChar <= 'ಐ')
                                                                                                                                return true;
                                                                                                                            }
                                                                                                                            while (paramChar < 'ಒ');
                                                                                                                            if (paramChar <= 'ನ')
                                                                                                                              return true;
                                                                                                                          }
                                                                                                                          while (paramChar < 'ಪ');
                                                                                                                          if (paramChar <= 'ಳ')
                                                                                                                            return true;
                                                                                                                        }
                                                                                                                        while (paramChar < 'ವ');
                                                                                                                        if (paramChar <= 'ಹ')
                                                                                                                          return true;
                                                                                                                        if (paramChar == 'ೞ')
                                                                                                                          return true;
                                                                                                                      }
                                                                                                                      while (paramChar < 'ೠ');
                                                                                                                      if (paramChar <= 'ೡ')
                                                                                                                        return true;
                                                                                                                    }
                                                                                                                    while (paramChar < 'അ');
                                                                                                                    if (paramChar <= 'ഌ')
                                                                                                                      return true;
                                                                                                                  }
                                                                                                                  while (paramChar < 'എ');
                                                                                                                  if (paramChar <= 'ഐ')
                                                                                                                    return true;
                                                                                                                }
                                                                                                                while (paramChar < 'ഒ');
                                                                                                                if (paramChar <= 'ന')
                                                                                                                  return true;
                                                                                                              }
                                                                                                              while (paramChar < 'പ');
                                                                                                              if (paramChar <= 'ഹ')
                                                                                                                return true;
                                                                                                            }
                                                                                                            while (paramChar < 'ൠ');
                                                                                                            if (paramChar <= 'ൡ')
                                                                                                              return true;
                                                                                                          }
                                                                                                          while (paramChar < 'ก');
                                                                                                          if (paramChar <= 'ฮ')
                                                                                                            return true;
                                                                                                          if (paramChar == 'ะ')
                                                                                                            return true;
                                                                                                        }
                                                                                                        while (paramChar < 'า');
                                                                                                        if (paramChar <= 'ำ')
                                                                                                          return true;
                                                                                                      }
                                                                                                      while (paramChar < 'เ');
                                                                                                      if (paramChar <= 'ๅ')
                                                                                                        return true;
                                                                                                    }
                                                                                                    while (paramChar < 'ກ');
                                                                                                    if (paramChar <= 'ຂ')
                                                                                                      return true;
                                                                                                    if (paramChar == 'ຄ')
                                                                                                      return true;
                                                                                                  }
                                                                                                  while (paramChar < 'ງ');
                                                                                                  if (paramChar <= 'ຈ')
                                                                                                    return true;
                                                                                                  if (paramChar == 'ຊ')
                                                                                                    return true;
                                                                                                  if (paramChar == 'ຍ')
                                                                                                    return true;
                                                                                                }
                                                                                                while (paramChar < 'ດ');
                                                                                                if (paramChar <= 'ທ')
                                                                                                  return true;
                                                                                              }
                                                                                              while (paramChar < 'ນ');
                                                                                              if (paramChar <= 'ຟ')
                                                                                                return true;
                                                                                            }
                                                                                            while (paramChar < 'ມ');
                                                                                            if (paramChar <= 'ຣ')
                                                                                              return true;
                                                                                            if (paramChar == 'ລ')
                                                                                              return true;
                                                                                            if (paramChar == 'ວ')
                                                                                              return true;
                                                                                          }
                                                                                          while (paramChar < 'ສ');
                                                                                          if (paramChar <= 'ຫ')
                                                                                            return true;
                                                                                        }
                                                                                        while (paramChar < 'ອ');
                                                                                        if (paramChar <= 'ຮ')
                                                                                          return true;
                                                                                        if (paramChar == 'ະ')
                                                                                          return true;
                                                                                      }
                                                                                      while (paramChar < 'າ');
                                                                                      if (paramChar <= 'ຳ')
                                                                                        return true;
                                                                                      if (paramChar == 'ຽ')
                                                                                        return true;
                                                                                    }
                                                                                    while (paramChar < 'ເ');
                                                                                    if (paramChar <= 'ໄ')
                                                                                      return true;
                                                                                  }
                                                                                  while (paramChar < 'ཀ');
                                                                                  if (paramChar <= 'ཇ')
                                                                                    return true;
                                                                                }
                                                                                while (paramChar < 'ཉ');
                                                                                if (paramChar <= 'ཀྵ')
                                                                                  return true;
                                                                              }
                                                                              while (paramChar < 'Ⴀ');
                                                                              if (paramChar <= 'Ⴥ')
                                                                                return true;
                                                                            }
                                                                            while (paramChar < 'ა');
                                                                            if (paramChar <= 'ჶ')
                                                                              return true;
                                                                            if (paramChar == 'ᄀ')
                                                                              return true;
                                                                          }
                                                                          while (paramChar < 'ᄂ');
                                                                          if (paramChar <= 'ᄃ')
                                                                            return true;
                                                                        }
                                                                        while (paramChar < 'ᄅ');
                                                                        if (paramChar <= 'ᄇ')
                                                                          return true;
                                                                        if (paramChar == 'ᄉ')
                                                                          return true;
                                                                      }
                                                                      while (paramChar < 'ᄋ');
                                                                      if (paramChar <= 'ᄌ')
                                                                        return true;
                                                                    }
                                                                    while (paramChar < 'ᄎ');
                                                                    if (paramChar <= 'ᄒ')
                                                                      return true;
                                                                    if (paramChar == 'ᄼ')
                                                                      return true;
                                                                    if (paramChar == 'ᄾ')
                                                                      return true;
                                                                    if (paramChar == 'ᅀ')
                                                                      return true;
                                                                    if (paramChar == 'ᅌ')
                                                                      return true;
                                                                    if (paramChar == 'ᅎ')
                                                                      return true;
                                                                    if (paramChar == 'ᅐ')
                                                                      return true;
                                                                  }
                                                                  while (paramChar < 'ᅔ');
                                                                  if (paramChar <= 'ᅕ')
                                                                    return true;
                                                                  if (paramChar == 'ᅙ')
                                                                    return true;
                                                                }
                                                                while (paramChar < 'ᅟ');
                                                                if (paramChar <= 'ᅡ')
                                                                  return true;
                                                                if (paramChar == 'ᅣ')
                                                                  return true;
                                                                if (paramChar == 'ᅥ')
                                                                  return true;
                                                                if (paramChar == 'ᅧ')
                                                                  return true;
                                                                if (paramChar == 'ᅩ')
                                                                  return true;
                                                              }
                                                              while (paramChar < 'ᅭ');
                                                              if (paramChar <= 'ᅮ')
                                                                return true;
                                                            }
                                                            while (paramChar < 'ᅲ');
                                                            if (paramChar <= 'ᅳ')
                                                              return true;
                                                            if (paramChar == 'ᅵ')
                                                              return true;
                                                            if (paramChar == 'ᆞ')
                                                              return true;
                                                            if (paramChar == 'ᆨ')
                                                              return true;
                                                            if (paramChar == 'ᆫ')
                                                              return true;
                                                          }
                                                          while (paramChar < 'ᆮ');
                                                          if (paramChar <= 'ᆯ')
                                                            return true;
                                                        }
                                                        while (paramChar < 'ᆷ');
                                                        if (paramChar <= 'ᆸ')
                                                          return true;
                                                        if (paramChar == 'ᆺ')
                                                          return true;
                                                      }
                                                      while (paramChar < 'ᆼ');
                                                      if (paramChar <= 'ᇂ')
                                                        return true;
                                                      if (paramChar == 'ᇫ')
                                                        return true;
                                                      if (paramChar == 'ᇰ')
                                                        return true;
                                                      if (paramChar == 'ᇹ')
                                                        return true;
                                                    }
                                                    while (paramChar < 'Ḁ');
                                                    if (paramChar <= 'ẛ')
                                                      return true;
                                                  }
                                                  while (paramChar < 'Ạ');
                                                  if (paramChar <= 'ỹ')
                                                    return true;
                                                }
                                                while (paramChar < 'ἀ');
                                                if (paramChar <= 'ἕ')
                                                  return true;
                                              }
                                              while (paramChar < 'Ἐ');
                                              if (paramChar <= 'Ἕ')
                                                return true;
                                            }
                                            while (paramChar < 'ἠ');
                                            if (paramChar <= 'ὅ')
                                              return true;
                                          }
                                          while (paramChar < 'Ὀ');
                                          if (paramChar <= 'Ὅ')
                                            return true;
                                        }
                                        while (paramChar < 'ὐ');
                                        if (paramChar <= 'ὗ')
                                          return true;
                                        if (paramChar == 'Ὑ')
                                          return true;
                                        if (paramChar == 'Ὓ')
                                          return true;
                                        if (paramChar == 'Ὕ')
                                          return true;
                                      }
                                      while (paramChar < 'Ὗ');
                                      if (paramChar <= 'ώ')
                                        return true;
                                    }
                                    while (paramChar < 'ᾀ');
                                    if (paramChar <= 'ᾴ')
                                      return true;
                                  }
                                  while (paramChar < 'ᾶ');
                                  if (paramChar <= 'ᾼ')
                                    return true;
                                  if (paramChar == 'ι')
                                    return true;
                                }
                                while (paramChar < 'ῂ');
                                if (paramChar <= 'ῄ')
                                  return true;
                              }
                              while (paramChar < 'ῆ');
                              if (paramChar <= 'ῌ')
                                return true;
                            }
                            while (paramChar < 'ῐ');
                            if (paramChar <= 'ΐ')
                              return true;
                          }
                          while (paramChar < 'ῖ');
                          if (paramChar <= 'Ί')
                            return true;
                        }
                        while (paramChar < 'ῠ');
                        if (paramChar <= 'Ῥ')
                          return true;
                      }
                      while (paramChar < 'ῲ');
                      if (paramChar <= 'ῴ')
                        return true;
                    }
                    while (paramChar < 'ῶ');
                    if (paramChar <= 'ῼ')
                      return true;
                    if (paramChar == 'Ω')
                      return true;
                  }
                  while (paramChar < 'K');
                  if (paramChar <= 'Å')
                    return true;
                  if (paramChar == '℮')
                    return true;
                }
                while (paramChar < 'ↀ');
                if (paramChar <= 'ↂ')
                  return true;
                if (paramChar == '〇')
                  return true;
              }
              while (paramChar < '〡');
              if (paramChar <= '〩')
                return true;
            }
            while (paramChar < 'ぁ');
            if (paramChar <= 'ゔ')
              return true;
          }
          while (paramChar < 'ァ');
          if (paramChar <= 'ヺ')
            return true;
        }
        while (paramChar < 'ㄅ');
        if (paramChar <= 'ㄬ')
          return true;
      }
      while (paramChar < '一');
      if (paramChar <= 40869)
        return true;
    }
    while ((paramChar < 44032) || (paramChar > 55203));
    return true;
  }

  public static boolean isXMLLetterOrDigit(char paramChar)
  {
    return (isXMLLetter(paramChar)) || (isXMLDigit(paramChar));
  }

  public static boolean isXMLNameCharacter(char paramChar)
  {
    return (isXMLLetter(paramChar)) || (isXMLDigit(paramChar)) || (paramChar == '.') || (paramChar == '-') || (paramChar == '_') || (paramChar == ':') || (isXMLCombiningChar(paramChar)) || (isXMLExtender(paramChar));
  }

  public static boolean isXMLNameStartCharacter(char paramChar)
  {
    return (isXMLLetter(paramChar)) || (paramChar == '_') || (paramChar == ':');
  }

  public static boolean isXMLPublicIDCharacter(char paramChar)
  {
    if ((paramChar >= 'a') && (paramChar <= 'z'));
    while (((paramChar >= '?') && (paramChar <= 'Z')) || ((paramChar >= '\'') && (paramChar <= ';')) || (paramChar == ' ') || (paramChar == '!') || (paramChar == '=') || (paramChar == '#') || (paramChar == '$') || (paramChar == '_') || (paramChar == '%') || (paramChar == '\n') || (paramChar == '\r') || (paramChar == '\t'))
      return true;
    return false;
  }

  public static boolean isXMLWhitespace(char paramChar)
  {
    return (paramChar == ' ') || (paramChar == '\n') || (paramChar == '\t') || (paramChar == '\r');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.Verifier
 * JD-Core Version:    0.6.2
 */