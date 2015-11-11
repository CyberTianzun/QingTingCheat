package org.json;

import java.util.HashMap;

public class XMLTokener extends JSONTokener
{
  public static final HashMap entity = new HashMap(8);

  static
  {
    entity.put("amp", XML.AMP);
    entity.put("apos", XML.APOS);
    entity.put("gt", XML.GT);
    entity.put("lt", XML.LT);
    entity.put("quot", XML.QUOT);
  }

  public XMLTokener(String paramString)
  {
    super(paramString);
  }

  public String nextCDATA()
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i;
    do
    {
      char c = next();
      if (c == 0)
        throw syntaxError("Unclosed CDATA");
      localStringBuffer.append(c);
      i = -3 + localStringBuffer.length();
    }
    while ((i < 0) || (localStringBuffer.charAt(i) != ']') || (localStringBuffer.charAt(i + 1) != ']') || (localStringBuffer.charAt(i + 2) != '>'));
    localStringBuffer.setLength(i);
    return localStringBuffer.toString();
  }

  public Object nextContent()
    throws JSONException
  {
    char c;
    do
      c = next();
    while (Character.isWhitespace(c));
    if (c == 0)
      return null;
    if (c == '<')
      return XML.LT;
    StringBuffer localStringBuffer = new StringBuffer();
    if ((c == '<') || (c == 0))
    {
      back();
      return localStringBuffer.toString().trim();
    }
    if (c == '&')
      localStringBuffer.append(nextEntity(c));
    while (true)
    {
      c = next();
      break;
      localStringBuffer.append(c);
    }
  }

  public Object nextEntity(char paramChar)
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    char c;
    while (true)
    {
      c = next();
      if ((!Character.isLetterOrDigit(c)) && (c != '#'))
        break;
      localStringBuffer.append(Character.toLowerCase(c));
    }
    String str;
    if (c == ';')
    {
      str = localStringBuffer.toString();
      Object localObject = entity.get(str);
      if (localObject != null)
        return localObject;
    }
    else
    {
      throw syntaxError("Missing ';' in XML entity: &" + localStringBuffer);
    }
    return paramChar + str + ";";
  }

  public Object nextMeta()
    throws JSONException
  {
    char c1;
    do
      c1 = next();
    while (Character.isWhitespace(c1));
    switch (c1)
    {
    default:
    case '\000':
    case '<':
    case '>':
    case '/':
    case '=':
    case '!':
    case '?':
    case '"':
    case '\'':
    }
    while (true)
    {
      char c3 = next();
      if (Character.isWhitespace(c3))
      {
        return Boolean.TRUE;
        throw syntaxError("Misshaped meta tag");
        return XML.LT;
        return XML.GT;
        return XML.SLASH;
        return XML.EQ;
        return XML.BANG;
        return XML.QUEST;
        char c2;
        do
        {
          c2 = next();
          if (c2 == 0)
            throw syntaxError("Unterminated string");
        }
        while (c2 != c1);
        return Boolean.TRUE;
      }
      switch (c3)
      {
      default:
      case '\000':
      case '!':
      case '"':
      case '\'':
      case '/':
      case '<':
      case '=':
      case '>':
      case '?':
      }
    }
    back();
    return Boolean.TRUE;
  }

  public Object nextToken()
    throws JSONException
  {
    char c1;
    do
      c1 = next();
    while (Character.isWhitespace(c1));
    StringBuffer localStringBuffer2;
    switch (c1)
    {
    default:
      localStringBuffer2 = new StringBuffer();
    case '\000':
    case '<':
    case '>':
    case '/':
    case '=':
    case '!':
    case '?':
    case '"':
    case '\'':
    }
    while (true)
    {
      localStringBuffer2.append(c1);
      c1 = next();
      if (Character.isWhitespace(c1))
      {
        return localStringBuffer2.toString();
        throw syntaxError("Misshaped element");
        throw syntaxError("Misplaced '<'");
        return XML.GT;
        return XML.SLASH;
        return XML.EQ;
        return XML.BANG;
        return XML.QUEST;
        StringBuffer localStringBuffer1 = new StringBuffer();
        while (true)
        {
          char c2 = next();
          if (c2 == 0)
            throw syntaxError("Unterminated string");
          if (c2 == c1)
            return localStringBuffer1.toString();
          if (c2 == '&')
            localStringBuffer1.append(nextEntity(c2));
          else
            localStringBuffer1.append(c2);
        }
      }
      switch (c1)
      {
      default:
      case '\000':
      case '!':
      case '/':
      case '=':
      case '>':
      case '?':
      case '[':
      case ']':
      case '"':
      case '\'':
      case '<':
      }
    }
    back();
    return localStringBuffer2.toString();
    throw syntaxError("Bad character in a name");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.json.XMLTokener
 * JD-Core Version:    0.6.2
 */