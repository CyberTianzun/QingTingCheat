package org.apache.commons.httpclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.Locale;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.httpclient.util.EncodingUtil;

public class URI
  implements Cloneable, Comparable, Serializable
{
  protected static final BitSet IPv4address;
  protected static final BitSet IPv6address;
  protected static final BitSet IPv6reference;
  protected static final BitSet URI_reference;
  protected static final BitSet abs_path;
  protected static final BitSet absoluteURI;
  public static final BitSet allowed_IPv6reference;
  public static final BitSet allowed_abs_path;
  public static final BitSet allowed_authority;
  public static final BitSet allowed_fragment;
  public static final BitSet allowed_host;
  public static final BitSet allowed_opaque_part;
  public static final BitSet allowed_query;
  public static final BitSet allowed_reg_name;
  public static final BitSet allowed_rel_path;
  public static final BitSet allowed_userinfo;
  public static final BitSet allowed_within_authority;
  public static final BitSet allowed_within_path;
  public static final BitSet allowed_within_query;
  public static final BitSet allowed_within_userinfo;
  protected static final BitSet alpha;
  protected static final BitSet alphanum;
  protected static final BitSet authority;
  public static final BitSet control;
  protected static String defaultDocumentCharset;
  protected static String defaultDocumentCharsetByLocale;
  protected static String defaultDocumentCharsetByPlatform;
  protected static String defaultProtocolCharset = "UTF-8";
  public static final BitSet delims;
  protected static final BitSet digit;
  public static final BitSet disallowed_opaque_part;
  public static final BitSet disallowed_rel_path;
  protected static final BitSet domainlabel;
  protected static final BitSet escaped;
  protected static final BitSet fragment;
  protected static final BitSet hex;
  protected static final BitSet hier_part;
  protected static final BitSet host;
  protected static final BitSet hostname;
  protected static final BitSet hostport;
  protected static final BitSet mark;
  protected static final BitSet net_path;
  protected static final BitSet opaque_part;
  protected static final BitSet param;
  protected static final BitSet path;
  protected static final BitSet path_segments;
  protected static final BitSet pchar;
  protected static final BitSet percent;
  protected static final BitSet port;
  protected static final BitSet query;
  protected static final BitSet reg_name;
  protected static final BitSet rel_path;
  protected static final BitSet rel_segment;
  protected static final BitSet relativeURI;
  protected static final BitSet reserved;
  protected static char[] rootPath;
  protected static final BitSet scheme;
  protected static final BitSet segment;
  static final long serialVersionUID = 604752400577948726L;
  protected static final BitSet server;
  public static final BitSet space;
  protected static final BitSet toplabel;
  protected static final BitSet unreserved;
  public static final BitSet unwise;
  protected static final BitSet uric;
  protected static final BitSet uric_no_slash;
  protected static final BitSet userinfo;
  public static final BitSet within_userinfo;
  protected char[] _authority = null;
  protected char[] _fragment = null;
  protected char[] _host = null;
  protected boolean _is_IPv4address;
  protected boolean _is_IPv6reference;
  protected boolean _is_abs_path;
  protected boolean _is_hier_part;
  protected boolean _is_hostname;
  protected boolean _is_net_path;
  protected boolean _is_opaque_part;
  protected boolean _is_reg_name;
  protected boolean _is_rel_path;
  protected boolean _is_server;
  protected char[] _opaque = null;
  protected char[] _path = null;
  protected int _port = -1;
  protected char[] _query = null;
  protected char[] _scheme = null;
  protected char[] _uri = null;
  protected char[] _userinfo = null;
  protected int hash = 0;
  protected String protocolCharset = null;

  static
  {
    defaultDocumentCharset = null;
    defaultDocumentCharsetByLocale = null;
    defaultDocumentCharsetByPlatform = null;
    Locale localLocale = Locale.getDefault();
    if (localLocale != null)
    {
      defaultDocumentCharsetByLocale = LocaleToCharsetMap.getCharset(localLocale);
      defaultDocumentCharset = defaultDocumentCharsetByLocale;
    }
    try
    {
      defaultDocumentCharsetByPlatform = System.getProperty("file.encoding");
      label46: if (defaultDocumentCharset == null)
        defaultDocumentCharset = defaultDocumentCharsetByPlatform;
      rootPath = new char[] { '/' };
      percent = new BitSet(256);
      percent.set(37);
      digit = new BitSet(256);
      int i = 48;
      int j;
      label128: int k;
      label138: int m;
      label202: int n;
      if (i > 57)
      {
        alpha = new BitSet(256);
        j = 97;
        if (j <= 122)
          break label2583;
        k = 65;
        if (k <= 90)
          break label2596;
        alphanum = new BitSet(256);
        alphanum.or(alpha);
        alphanum.or(digit);
        hex = new BitSet(256);
        hex.or(digit);
        m = 97;
        if (m <= 102)
          break label2610;
        n = 65;
        label213: if (n <= 70)
          break label2624;
        escaped = new BitSet(256);
        escaped.or(percent);
        escaped.or(hex);
        mark = new BitSet(256);
        mark.set(45);
        mark.set(95);
        mark.set(46);
        mark.set(33);
        mark.set(126);
        mark.set(42);
        mark.set(39);
        mark.set(40);
        mark.set(41);
        unreserved = new BitSet(256);
        unreserved.or(alphanum);
        unreserved.or(mark);
        reserved = new BitSet(256);
        reserved.set(59);
        reserved.set(47);
        reserved.set(63);
        reserved.set(58);
        reserved.set(64);
        reserved.set(38);
        reserved.set(61);
        reserved.set(43);
        reserved.set(36);
        reserved.set(44);
        uric = new BitSet(256);
        uric.or(reserved);
        uric.or(unreserved);
        uric.or(escaped);
        fragment = uric;
        query = uric;
        pchar = new BitSet(256);
        pchar.or(unreserved);
        pchar.or(escaped);
        pchar.set(58);
        pchar.set(64);
        pchar.set(38);
        pchar.set(61);
        pchar.set(43);
        pchar.set(36);
        pchar.set(44);
        param = pchar;
        segment = new BitSet(256);
        segment.or(pchar);
        segment.set(59);
        segment.or(param);
        path_segments = new BitSet(256);
        path_segments.set(47);
        path_segments.or(segment);
        abs_path = new BitSet(256);
        abs_path.set(47);
        abs_path.or(path_segments);
        uric_no_slash = new BitSet(256);
        uric_no_slash.or(unreserved);
        uric_no_slash.or(escaped);
        uric_no_slash.set(59);
        uric_no_slash.set(63);
        uric_no_slash.set(59);
        uric_no_slash.set(64);
        uric_no_slash.set(38);
        uric_no_slash.set(61);
        uric_no_slash.set(43);
        uric_no_slash.set(36);
        uric_no_slash.set(44);
        opaque_part = new BitSet(256);
        opaque_part.or(uric_no_slash);
        opaque_part.or(uric);
        path = new BitSet(256);
        path.or(abs_path);
        path.or(opaque_part);
        port = digit;
        IPv4address = new BitSet(256);
        IPv4address.or(digit);
        IPv4address.set(46);
        IPv6address = new BitSet(256);
        IPv6address.or(hex);
        IPv6address.set(58);
        IPv6address.or(IPv4address);
        IPv6reference = new BitSet(256);
        IPv6reference.set(91);
        IPv6reference.or(IPv6address);
        IPv6reference.set(93);
        toplabel = new BitSet(256);
        toplabel.or(alphanum);
        toplabel.set(45);
        domainlabel = toplabel;
        hostname = new BitSet(256);
        hostname.or(toplabel);
        hostname.set(46);
        host = new BitSet(256);
        host.or(hostname);
        host.or(IPv6reference);
        hostport = new BitSet(256);
        hostport.or(host);
        hostport.set(58);
        hostport.or(port);
        userinfo = new BitSet(256);
        userinfo.or(unreserved);
        userinfo.or(escaped);
        userinfo.set(59);
        userinfo.set(58);
        userinfo.set(38);
        userinfo.set(61);
        userinfo.set(43);
        userinfo.set(36);
        userinfo.set(44);
        within_userinfo = new BitSet(256);
        within_userinfo.or(userinfo);
        within_userinfo.clear(59);
        within_userinfo.clear(58);
        within_userinfo.clear(64);
        within_userinfo.clear(63);
        within_userinfo.clear(47);
        server = new BitSet(256);
        server.or(userinfo);
        server.set(64);
        server.or(hostport);
        reg_name = new BitSet(256);
        reg_name.or(unreserved);
        reg_name.or(escaped);
        reg_name.set(36);
        reg_name.set(44);
        reg_name.set(59);
        reg_name.set(58);
        reg_name.set(64);
        reg_name.set(38);
        reg_name.set(61);
        reg_name.set(43);
        authority = new BitSet(256);
        authority.or(server);
        authority.or(reg_name);
        scheme = new BitSet(256);
        scheme.or(alpha);
        scheme.or(digit);
        scheme.set(43);
        scheme.set(45);
        scheme.set(46);
        rel_segment = new BitSet(256);
        rel_segment.or(unreserved);
        rel_segment.or(escaped);
        rel_segment.set(59);
        rel_segment.set(64);
        rel_segment.set(38);
        rel_segment.set(61);
        rel_segment.set(43);
        rel_segment.set(36);
        rel_segment.set(44);
        rel_path = new BitSet(256);
        rel_path.or(rel_segment);
        rel_path.or(abs_path);
        net_path = new BitSet(256);
        net_path.set(47);
        net_path.or(authority);
        net_path.or(abs_path);
        hier_part = new BitSet(256);
        hier_part.or(net_path);
        hier_part.or(abs_path);
        hier_part.or(query);
        relativeURI = new BitSet(256);
        relativeURI.or(net_path);
        relativeURI.or(abs_path);
        relativeURI.or(rel_path);
        relativeURI.or(query);
        absoluteURI = new BitSet(256);
        absoluteURI.or(scheme);
        absoluteURI.set(58);
        absoluteURI.or(hier_part);
        absoluteURI.or(opaque_part);
        URI_reference = new BitSet(256);
        URI_reference.or(absoluteURI);
        URI_reference.or(relativeURI);
        URI_reference.set(35);
        URI_reference.or(fragment);
        control = new BitSet(256);
      }
      for (int i1 = 0; ; i1++)
      {
        if (i1 > 31)
        {
          control.set(127);
          space = new BitSet(256);
          space.set(32);
          delims = new BitSet(256);
          delims.set(60);
          delims.set(62);
          delims.set(35);
          delims.set(37);
          delims.set(34);
          unwise = new BitSet(256);
          unwise.set(123);
          unwise.set(125);
          unwise.set(124);
          unwise.set(92);
          unwise.set(94);
          unwise.set(91);
          unwise.set(93);
          unwise.set(96);
          disallowed_rel_path = new BitSet(256);
          disallowed_rel_path.or(uric);
          disallowed_rel_path.andNot(rel_path);
          disallowed_opaque_part = new BitSet(256);
          disallowed_opaque_part.or(uric);
          disallowed_opaque_part.andNot(opaque_part);
          allowed_authority = new BitSet(256);
          allowed_authority.or(authority);
          allowed_authority.clear(37);
          allowed_opaque_part = new BitSet(256);
          allowed_opaque_part.or(opaque_part);
          allowed_opaque_part.clear(37);
          allowed_reg_name = new BitSet(256);
          allowed_reg_name.or(reg_name);
          allowed_reg_name.clear(37);
          allowed_userinfo = new BitSet(256);
          allowed_userinfo.or(userinfo);
          allowed_userinfo.clear(37);
          allowed_within_userinfo = new BitSet(256);
          allowed_within_userinfo.or(within_userinfo);
          allowed_within_userinfo.clear(37);
          allowed_IPv6reference = new BitSet(256);
          allowed_IPv6reference.or(IPv6reference);
          allowed_IPv6reference.clear(91);
          allowed_IPv6reference.clear(93);
          allowed_host = new BitSet(256);
          allowed_host.or(hostname);
          allowed_host.or(allowed_IPv6reference);
          allowed_within_authority = new BitSet(256);
          allowed_within_authority.or(server);
          allowed_within_authority.or(reg_name);
          allowed_within_authority.clear(59);
          allowed_within_authority.clear(58);
          allowed_within_authority.clear(64);
          allowed_within_authority.clear(63);
          allowed_within_authority.clear(47);
          allowed_abs_path = new BitSet(256);
          allowed_abs_path.or(abs_path);
          allowed_abs_path.andNot(percent);
          allowed_rel_path = new BitSet(256);
          allowed_rel_path.or(rel_path);
          allowed_rel_path.clear(37);
          allowed_within_path = new BitSet(256);
          allowed_within_path.or(abs_path);
          allowed_within_path.clear(47);
          allowed_within_path.clear(59);
          allowed_within_path.clear(61);
          allowed_within_path.clear(63);
          allowed_query = new BitSet(256);
          allowed_query.or(uric);
          allowed_query.clear(37);
          allowed_within_query = new BitSet(256);
          allowed_within_query.or(allowed_query);
          allowed_within_query.andNot(reserved);
          allowed_fragment = new BitSet(256);
          allowed_fragment.or(uric);
          allowed_fragment.clear(37);
          return;
          digit.set(i);
          i++;
          break;
          label2583: alpha.set(j);
          j++;
          break label128;
          label2596: alpha.set(k);
          k++;
          break label138;
          label2610: hex.set(m);
          m++;
          break label202;
          label2624: hex.set(n);
          n++;
          break label213;
        }
        control.set(i1);
      }
    }
    catch (SecurityException localSecurityException)
    {
      break label46;
    }
  }

  protected URI()
  {
  }

  public URI(String paramString)
    throws URIException
  {
    parseUriReference(paramString, false);
  }

  public URI(String paramString1, String paramString2)
    throws URIException
  {
    this.protocolCharset = paramString2;
    parseUriReference(paramString1, false);
  }

  public URI(String paramString1, String paramString2, String paramString3)
    throws URIException
  {
    if (paramString1 == null)
      throw new URIException(1, "scheme required");
    char[] arrayOfChar1 = paramString1.toLowerCase().toCharArray();
    char[] arrayOfChar2;
    if (validate(arrayOfChar1, scheme))
    {
      this._scheme = arrayOfChar1;
      this._opaque = encode(paramString2, allowed_opaque_part, getProtocolCharset());
      this._is_opaque_part = true;
      arrayOfChar2 = null;
      if (paramString3 != null)
        break label157;
    }
    while (true)
    {
      this._fragment = arrayOfChar2;
      setURI();
      return;
      throw new URIException(1, "incorrect scheme");
      label157: arrayOfChar2 = paramString3.toCharArray();
    }
  }

  public URI(String paramString1, String paramString2, String paramString3, int paramInt)
    throws URIException
  {
    this(paramString1, paramString2, paramString3, paramInt, null, null, null);
  }

  public URI(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4)
    throws URIException
  {
    this(paramString1, paramString2, paramString3, paramInt, paramString4, null, null);
  }

  public URI(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5)
    throws URIException
  {
    this(paramString1, paramString2, paramString3, paramInt, paramString4, paramString5, null);
  }

  public URI(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6)
    throws URIException
  {
  }

  public URI(String paramString1, String paramString2, String paramString3, String paramString4)
    throws URIException
  {
    this(paramString1, paramString2, paramString3, null, paramString4);
  }

  public URI(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws URIException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString1 != null)
    {
      localStringBuffer.append(paramString1);
      localStringBuffer.append(':');
    }
    if (paramString2 != null)
    {
      localStringBuffer.append("//");
      localStringBuffer.append(paramString2);
    }
    if (paramString3 != null)
    {
      if (((paramString1 != null) || (paramString2 != null)) && (!paramString3.startsWith("/")))
        throw new URIException(1, "abs_path requested");
      localStringBuffer.append(paramString3);
    }
    if (paramString4 != null)
    {
      localStringBuffer.append('?');
      localStringBuffer.append(paramString4);
    }
    if (paramString5 != null)
    {
      localStringBuffer.append('#');
      localStringBuffer.append(paramString5);
    }
    parseUriReference(localStringBuffer.toString(), false);
  }

  public URI(String paramString, boolean paramBoolean)
    throws URIException, NullPointerException
  {
    parseUriReference(paramString, paramBoolean);
  }

  public URI(String paramString1, boolean paramBoolean, String paramString2)
    throws URIException, NullPointerException
  {
    this.protocolCharset = paramString2;
    parseUriReference(paramString1, paramBoolean);
  }

  public URI(URI paramURI, String paramString)
    throws URIException
  {
    this(paramURI, new URI(paramString));
  }

  public URI(URI paramURI, String paramString, boolean paramBoolean)
    throws URIException
  {
    this(paramURI, new URI(paramString, paramBoolean));
  }

  public URI(URI paramURI1, URI paramURI2)
    throws URIException
  {
    if (paramURI1._scheme == null)
      throw new URIException(1, "base URI required");
    if (paramURI1._scheme != null)
    {
      this._scheme = paramURI1._scheme;
      this._authority = paramURI1._authority;
    }
    if ((paramURI1._is_opaque_part) || (paramURI2._is_opaque_part))
    {
      this._scheme = paramURI1._scheme;
      boolean bool1;
      if (!paramURI1._is_opaque_part)
      {
        boolean bool2 = paramURI2._is_opaque_part;
        bool1 = false;
        if (!bool2);
      }
      else
      {
        bool1 = true;
      }
      this._is_opaque_part = bool1;
      this._opaque = paramURI2._opaque;
      this._fragment = paramURI2._fragment;
      setURI();
      return;
    }
    if (paramURI2._scheme != null)
    {
      this._scheme = paramURI2._scheme;
      this._is_net_path = paramURI2._is_net_path;
      this._authority = paramURI2._authority;
      if (paramURI2._is_server)
      {
        this._is_server = paramURI2._is_server;
        this._userinfo = paramURI2._userinfo;
        this._host = paramURI2._host;
        this._port = paramURI2._port;
        this._is_abs_path = paramURI2._is_abs_path;
        this._is_rel_path = paramURI2._is_rel_path;
        this._path = paramURI2._path;
        label270: if (paramURI2._authority != null)
        {
          this._is_net_path = paramURI2._is_net_path;
          this._authority = paramURI2._authority;
          if (!paramURI2._is_server)
            break label567;
          this._is_server = paramURI2._is_server;
          this._userinfo = paramURI2._userinfo;
          this._host = paramURI2._host;
          this._port = paramURI2._port;
          label332: this._is_abs_path = paramURI2._is_abs_path;
          this._is_rel_path = paramURI2._is_rel_path;
          this._path = paramURI2._path;
        }
        if ((paramURI2._scheme == null) && (paramURI2._authority == null))
        {
          if (((paramURI2._path != null) && (paramURI2._path.length != 0)) || (paramURI2._query != null))
            break label585;
          this._path = paramURI1._path;
          this._query = paramURI1._query;
        }
      }
    }
    while (true)
    {
      if (paramURI2._query != null)
        this._query = paramURI2._query;
      if (paramURI2._fragment != null)
        this._fragment = paramURI2._fragment;
      setURI();
      parseUriReference(new String(this._uri), true);
      return;
      if (!paramURI2._is_reg_name)
        break;
      this._is_reg_name = paramURI2._is_reg_name;
      break;
      if ((paramURI1._authority == null) || (paramURI2._scheme != null))
        break label270;
      this._is_net_path = paramURI1._is_net_path;
      this._authority = paramURI1._authority;
      if (paramURI1._is_server)
      {
        this._is_server = paramURI1._is_server;
        this._userinfo = paramURI1._userinfo;
        this._host = paramURI1._host;
        this._port = paramURI1._port;
        break label270;
      }
      if (!paramURI1._is_reg_name)
        break label270;
      this._is_reg_name = paramURI1._is_reg_name;
      break label270;
      label567: if (!paramURI2._is_reg_name)
        break label332;
      this._is_reg_name = paramURI2._is_reg_name;
      break label332;
      label585: this._path = resolvePath(paramURI1._path, paramURI2._path);
    }
  }

  public URI(char[] paramArrayOfChar)
    throws URIException, NullPointerException
  {
    parseUriReference(new String(paramArrayOfChar), true);
  }

  public URI(char[] paramArrayOfChar, String paramString)
    throws URIException, NullPointerException
  {
    this.protocolCharset = paramString;
    parseUriReference(new String(paramArrayOfChar), true);
  }

  protected static String decode(String paramString1, String paramString2)
    throws URIException
  {
    if (paramString1 == null)
      throw new IllegalArgumentException("Component array of chars may not be null");
    try
    {
      byte[] arrayOfByte = URLCodec.decodeUrl(EncodingUtil.getAsciiBytes(paramString1));
      return EncodingUtil.getString(arrayOfByte, paramString2);
    }
    catch (DecoderException localDecoderException)
    {
      throw new URIException(localDecoderException.getMessage());
    }
  }

  protected static String decode(char[] paramArrayOfChar, String paramString)
    throws URIException
  {
    if (paramArrayOfChar == null)
      throw new IllegalArgumentException("Component array of chars may not be null");
    return decode(new String(paramArrayOfChar), paramString);
  }

  protected static char[] encode(String paramString1, BitSet paramBitSet, String paramString2)
    throws URIException
  {
    if (paramString1 == null)
      throw new IllegalArgumentException("Original string may not be null");
    if (paramBitSet == null)
      throw new IllegalArgumentException("Allowed bitset may not be null");
    return EncodingUtil.getAsciiString(URLCodec.encodeUrl(paramBitSet, EncodingUtil.getBytes(paramString1, paramString2))).toCharArray();
  }

  public static String getDefaultDocumentCharset()
  {
    return defaultDocumentCharset;
  }

  public static String getDefaultDocumentCharsetByLocale()
  {
    return defaultDocumentCharsetByLocale;
  }

  public static String getDefaultDocumentCharsetByPlatform()
  {
    return defaultDocumentCharsetByPlatform;
  }

  public static String getDefaultProtocolCharset()
  {
    return defaultProtocolCharset;
  }

  public static void setDefaultDocumentCharset(String paramString)
    throws URI.DefaultCharsetChanged
  {
    defaultDocumentCharset = paramString;
    throw new DefaultCharsetChanged(2, "the default document charset changed");
  }

  public static void setDefaultProtocolCharset(String paramString)
    throws URI.DefaultCharsetChanged
  {
    defaultProtocolCharset = paramString;
    throw new DefaultCharsetChanged(1, "the default protocol charset changed");
  }

  public Object clone()
  {
    try
    {
      URI localURI = new URI();
      localURI._uri = this._uri;
      localURI._scheme = this._scheme;
      localURI._opaque = this._opaque;
      localURI._authority = this._authority;
      localURI._userinfo = this._userinfo;
      localURI._host = this._host;
      localURI._port = this._port;
      localURI._path = this._path;
      localURI._query = this._query;
      localURI._fragment = this._fragment;
      localURI.protocolCharset = this.protocolCharset;
      localURI._is_hier_part = this._is_hier_part;
      localURI._is_opaque_part = this._is_opaque_part;
      localURI._is_net_path = this._is_net_path;
      localURI._is_abs_path = this._is_abs_path;
      localURI._is_rel_path = this._is_rel_path;
      localURI._is_reg_name = this._is_reg_name;
      localURI._is_server = this._is_server;
      localURI._is_hostname = this._is_hostname;
      localURI._is_IPv4address = this._is_IPv4address;
      localURI._is_IPv6reference = this._is_IPv6reference;
      return localURI;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    URI localURI = (URI)paramObject;
    if (!equals(this._authority, localURI.getRawAuthority()))
      return -1;
    return toString().compareTo(localURI.toString());
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this);
    URI localURI;
    do
    {
      return true;
      if (!(paramObject instanceof URI))
        return false;
      localURI = (URI)paramObject;
      if (!equals(this._scheme, localURI._scheme))
        return false;
      if (!equals(this._opaque, localURI._opaque))
        return false;
      if (!equals(this._authority, localURI._authority))
        return false;
      if (!equals(this._path, localURI._path))
        return false;
      if (!equals(this._query, localURI._query))
        return false;
    }
    while (equals(this._fragment, localURI._fragment));
    return false;
  }

  protected boolean equals(char[] paramArrayOfChar1, char[] paramArrayOfChar2)
  {
    if ((paramArrayOfChar1 == null) && (paramArrayOfChar2 == null));
    while (true)
    {
      return true;
      if ((paramArrayOfChar1 == null) || (paramArrayOfChar2 == null))
        return false;
      if (paramArrayOfChar1.length != paramArrayOfChar2.length)
        return false;
      for (int i = 0; i < paramArrayOfChar1.length; i++)
        if (paramArrayOfChar1[i] != paramArrayOfChar2[i])
          return false;
    }
  }

  public String getAboveHierPath()
    throws URIException
  {
    char[] arrayOfChar = getRawAboveHierPath();
    if (arrayOfChar == null)
      return null;
    return decode(arrayOfChar, getProtocolCharset());
  }

  public String getAuthority()
    throws URIException
  {
    if (this._authority == null)
      return null;
    return decode(this._authority, getProtocolCharset());
  }

  public String getCurrentHierPath()
    throws URIException
  {
    char[] arrayOfChar = getRawCurrentHierPath();
    if (arrayOfChar == null)
      return null;
    return decode(arrayOfChar, getProtocolCharset());
  }

  public String getEscapedAboveHierPath()
    throws URIException
  {
    char[] arrayOfChar = getRawAboveHierPath();
    if (arrayOfChar == null)
      return null;
    return new String(arrayOfChar);
  }

  public String getEscapedAuthority()
  {
    if (this._authority == null)
      return null;
    return new String(this._authority);
  }

  public String getEscapedCurrentHierPath()
    throws URIException
  {
    char[] arrayOfChar = getRawCurrentHierPath();
    if (arrayOfChar == null)
      return null;
    return new String(arrayOfChar);
  }

  public String getEscapedFragment()
  {
    if (this._fragment == null)
      return null;
    return new String(this._fragment);
  }

  public String getEscapedName()
  {
    char[] arrayOfChar = getRawName();
    if (arrayOfChar == null)
      return null;
    return new String(arrayOfChar);
  }

  public String getEscapedPath()
  {
    char[] arrayOfChar = getRawPath();
    if (arrayOfChar == null)
      return null;
    return new String(arrayOfChar);
  }

  public String getEscapedPathQuery()
  {
    char[] arrayOfChar = getRawPathQuery();
    if (arrayOfChar == null)
      return null;
    return new String(arrayOfChar);
  }

  public String getEscapedQuery()
  {
    if (this._query == null)
      return null;
    return new String(this._query);
  }

  public String getEscapedURI()
  {
    if (this._uri == null)
      return null;
    return new String(this._uri);
  }

  public String getEscapedURIReference()
  {
    char[] arrayOfChar = getRawURIReference();
    if (arrayOfChar == null)
      return null;
    return new String(arrayOfChar);
  }

  public String getEscapedUserinfo()
  {
    if (this._userinfo == null)
      return null;
    return new String(this._userinfo);
  }

  public String getFragment()
    throws URIException
  {
    if (this._fragment == null)
      return null;
    return decode(this._fragment, getProtocolCharset());
  }

  public String getHost()
    throws URIException
  {
    if (this._host != null)
      return decode(this._host, getProtocolCharset());
    return null;
  }

  public String getName()
    throws URIException
  {
    if (getRawName() == null)
      return null;
    return decode(getRawName(), getProtocolCharset());
  }

  public String getPath()
    throws URIException
  {
    char[] arrayOfChar = getRawPath();
    if (arrayOfChar == null)
      return null;
    return decode(arrayOfChar, getProtocolCharset());
  }

  public String getPathQuery()
    throws URIException
  {
    char[] arrayOfChar = getRawPathQuery();
    if (arrayOfChar == null)
      return null;
    return decode(arrayOfChar, getProtocolCharset());
  }

  public int getPort()
  {
    return this._port;
  }

  public String getProtocolCharset()
  {
    if (this.protocolCharset != null)
      return this.protocolCharset;
    return defaultProtocolCharset;
  }

  public String getQuery()
    throws URIException
  {
    if (this._query == null)
      return null;
    return decode(this._query, getProtocolCharset());
  }

  public char[] getRawAboveHierPath()
    throws URIException
  {
    char[] arrayOfChar = getRawCurrentHierPath();
    if (arrayOfChar == null)
      return null;
    return getRawCurrentHierPath(arrayOfChar);
  }

  public char[] getRawAuthority()
  {
    return this._authority;
  }

  public char[] getRawCurrentHierPath()
    throws URIException
  {
    if (this._path == null)
      return null;
    return getRawCurrentHierPath(this._path);
  }

  protected char[] getRawCurrentHierPath(char[] paramArrayOfChar)
    throws URIException
  {
    if (this._is_opaque_part)
      throw new URIException(1, "no hierarchy level");
    if (paramArrayOfChar == null)
      throw new URIException(1, "empty path");
    String str = new String(paramArrayOfChar);
    int i = str.indexOf('/');
    int j = str.lastIndexOf('/');
    if (j == 0)
      paramArrayOfChar = rootPath;
    while ((i == j) || (j == -1))
      return paramArrayOfChar;
    return str.substring(0, j).toCharArray();
  }

  public char[] getRawFragment()
  {
    return this._fragment;
  }

  public char[] getRawHost()
  {
    return this._host;
  }

  public char[] getRawName()
  {
    if (this._path == null)
      return null;
    for (int i = -1 + this._path.length; ; i--)
    {
      int j = 0;
      if (i < 0);
      while (true)
      {
        int k = this._path.length - j;
        char[] arrayOfChar = new char[k];
        System.arraycopy(this._path, j, arrayOfChar, 0, k);
        return arrayOfChar;
        if (this._path[i] != '/')
          break;
        j = i + 1;
      }
    }
  }

  public char[] getRawPath()
  {
    if (this._is_opaque_part)
      return this._opaque;
    return this._path;
  }

  public char[] getRawPathQuery()
  {
    if ((this._path == null) && (this._query == null))
      return null;
    StringBuffer localStringBuffer = new StringBuffer();
    if (this._path != null)
      localStringBuffer.append(this._path);
    if (this._query != null)
    {
      localStringBuffer.append('?');
      localStringBuffer.append(this._query);
    }
    return localStringBuffer.toString().toCharArray();
  }

  public char[] getRawQuery()
  {
    return this._query;
  }

  public char[] getRawScheme()
  {
    return this._scheme;
  }

  public char[] getRawURI()
  {
    return this._uri;
  }

  public char[] getRawURIReference()
  {
    if (this._fragment == null)
      return this._uri;
    if (this._uri == null)
      return this._fragment;
    return (new String(this._uri) + "#" + new String(this._fragment)).toCharArray();
  }

  public char[] getRawUserinfo()
  {
    return this._userinfo;
  }

  public String getScheme()
  {
    if (this._scheme == null)
      return null;
    return new String(this._scheme);
  }

  public String getURI()
    throws URIException
  {
    if (this._uri == null)
      return null;
    return decode(this._uri, getProtocolCharset());
  }

  public String getURIReference()
    throws URIException
  {
    char[] arrayOfChar = getRawURIReference();
    if (arrayOfChar == null)
      return null;
    return decode(arrayOfChar, getProtocolCharset());
  }

  public String getUserinfo()
    throws URIException
  {
    if (this._userinfo == null)
      return null;
    return decode(this._userinfo, getProtocolCharset());
  }

  public boolean hasAuthority()
  {
    return (this._authority != null) || (this._is_net_path);
  }

  public boolean hasFragment()
  {
    return this._fragment != null;
  }

  public boolean hasQuery()
  {
    return this._query != null;
  }

  public boolean hasUserinfo()
  {
    return this._userinfo != null;
  }

  public int hashCode()
  {
    char[] arrayOfChar1;
    int k;
    char[] arrayOfChar2;
    int i;
    int j;
    if (this.hash == 0)
    {
      arrayOfChar1 = this._uri;
      if (arrayOfChar1 != null)
      {
        k = 0;
        int m = arrayOfChar1.length;
        if (k < m)
          break label56;
      }
      arrayOfChar2 = this._fragment;
      if (arrayOfChar2 != null)
      {
        i = 0;
        j = arrayOfChar2.length;
      }
    }
    while (true)
    {
      if (i >= j)
      {
        return this.hash;
        label56: this.hash = (31 * this.hash + arrayOfChar1[k]);
        k++;
        break;
      }
      this.hash = (31 * this.hash + arrayOfChar2[i]);
      i++;
    }
  }

  protected int indexFirstOf(String paramString1, String paramString2)
  {
    return indexFirstOf(paramString1, paramString2, -1);
  }

  protected int indexFirstOf(String paramString1, String paramString2, int paramInt)
  {
    if ((paramString1 == null) || (paramString1.length() == 0));
    while ((paramString2 == null) || (paramString2.length() == 0))
      return -1;
    int i;
    char[] arrayOfChar;
    if (paramInt < 0)
    {
      paramInt = 0;
      i = paramString1.length();
      arrayOfChar = paramString2.toCharArray();
    }
    for (int j = 0; ; j++)
    {
      if (j >= arrayOfChar.length)
      {
        if (i == paramString1.length())
          i = -1;
        return i;
        if (paramInt <= paramString1.length())
          break;
        return -1;
      }
      int k = paramString1.indexOf(arrayOfChar[j], paramInt);
      if ((k >= 0) && (k < i))
        i = k;
    }
  }

  protected int indexFirstOf(char[] paramArrayOfChar, char paramChar)
  {
    return indexFirstOf(paramArrayOfChar, paramChar, 0);
  }

  protected int indexFirstOf(char[] paramArrayOfChar, char paramChar, int paramInt)
  {
    if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0))
    {
      i = -1;
      return i;
    }
    if (paramInt < 0)
      paramInt = 0;
    label21: for (int i = paramInt; ; i++)
    {
      if (i >= paramArrayOfChar.length)
      {
        return -1;
        if (paramInt <= paramArrayOfChar.length)
          break label21;
        return -1;
      }
      if (paramArrayOfChar[i] == paramChar)
        break;
    }
  }

  public boolean isAbsPath()
  {
    return this._is_abs_path;
  }

  public boolean isAbsoluteURI()
  {
    return this._scheme != null;
  }

  public boolean isHierPart()
  {
    return this._is_hier_part;
  }

  public boolean isHostname()
  {
    return this._is_hostname;
  }

  public boolean isIPv4address()
  {
    return this._is_IPv4address;
  }

  public boolean isIPv6reference()
  {
    return this._is_IPv6reference;
  }

  public boolean isNetPath()
  {
    return (this._is_net_path) || (this._authority != null);
  }

  public boolean isOpaquePart()
  {
    return this._is_opaque_part;
  }

  public boolean isRegName()
  {
    return this._is_reg_name;
  }

  public boolean isRelPath()
  {
    return this._is_rel_path;
  }

  public boolean isRelativeURI()
  {
    return this._scheme == null;
  }

  public boolean isServer()
  {
    return this._is_server;
  }

  public void normalize()
    throws URIException
  {
    if (isAbsPath())
    {
      this._path = normalize(this._path);
      setURI();
    }
  }

  protected char[] normalize(char[] paramArrayOfChar)
    throws URIException
  {
    if (paramArrayOfChar == null)
      return null;
    String str = new String(paramArrayOfChar);
    int i;
    int j;
    label69: int k;
    int i1;
    if (str.startsWith("./"))
    {
      str = str.substring(1);
      i = str.indexOf("/./");
      if (i != -1)
        break label214;
      if (str.endsWith("/."))
        str = str.substring(0, -1 + str.length());
      j = 0;
      k = str.indexOf("/../", j);
      if (k != -1)
        break label247;
      if (str.endsWith("/.."))
      {
        i1 = str.lastIndexOf('/', -4 + str.length());
        if (i1 < 0);
      }
    }
    int n;
    for (str = str.substring(0, i1 + 1); ; str = str.substring(n + 3))
    {
      n = str.indexOf("/../");
      if (n == -1);
      label214: label247: 
      while (str.lastIndexOf('/', n - 1) >= 0)
      {
        if ((str.endsWith("/..")) && (str.lastIndexOf('/', -4 + str.length()) < 0))
          str = "/";
        return str.toCharArray();
        if (str.startsWith("../"))
        {
          str = str.substring(2);
          break;
        }
        if (!str.startsWith(".."))
          break;
        str = str.substring(2);
        break;
        str = str.substring(0, i) + str.substring(i + 2);
        break;
        int m = str.lastIndexOf('/', k - 1);
        if (m >= 0)
        {
          str = str.substring(0, m) + str.substring(k + 3);
          break label69;
        }
        j = k + 3;
        break label69;
      }
    }
  }

  protected void parseAuthority(String paramString, boolean paramBoolean)
    throws URIException
  {
    this._is_IPv6reference = false;
    this._is_IPv4address = false;
    this._is_hostname = false;
    this._is_server = false;
    this._is_reg_name = false;
    String str = getProtocolCharset();
    int i = 1;
    int j = paramString.indexOf('@');
    int k = 0;
    if (j != -1)
      if (!paramBoolean)
        break label119;
    int i1;
    label119: for (char[] arrayOfChar3 = paramString.substring(0, j).toCharArray(); ; arrayOfChar3 = encode(paramString.substring(0, j), allowed_userinfo, str))
    {
      this._userinfo = arrayOfChar3;
      k = j + 1;
      if (paramString.indexOf('[', k) < k)
        break label239;
      i1 = paramString.indexOf(']', k);
      if (i1 != -1)
        break;
      throw new URIException(1, "IPv6reference");
    }
    int m = i1 + 1;
    char[] arrayOfChar2;
    if (paramBoolean)
    {
      arrayOfChar2 = paramString.substring(k, m).toCharArray();
      this._host = arrayOfChar2;
      this._is_IPv6reference = true;
      label172: if (!this._is_reg_name)
        break label347;
      this._is_IPv6reference = false;
      this._is_IPv4address = false;
      this._is_hostname = false;
      this._is_server = false;
      if (!paramBoolean)
        break label331;
    }
    label331: for (char[] arrayOfChar1 = paramString.toString().toCharArray(); ; arrayOfChar1 = encode(paramString.toString(), allowed_reg_name, str))
    {
      this._authority = arrayOfChar1;
      return;
      arrayOfChar2 = encode(paramString.substring(k, m), allowed_IPv6reference, str);
      break;
      label239: m = paramString.indexOf(':', k);
      if (m == -1)
      {
        m = paramString.length();
        i = 0;
      }
      this._host = paramString.substring(k, m).toCharArray();
      if (validate(this._host, IPv4address))
      {
        this._is_IPv4address = true;
        break label172;
      }
      if (validate(this._host, hostname))
      {
        this._is_hostname = true;
        break label172;
      }
      this._is_reg_name = true;
      break label172;
    }
    label347: int n;
    if ((-1 + paramString.length() > m) && (i != 0) && (paramString.charAt(m) == ':'))
      n = m + 1;
    try
    {
      this._port = Integer.parseInt(paramString.substring(n));
      StringBuffer localStringBuffer = new StringBuffer();
      if (this._userinfo != null)
      {
        localStringBuffer.append(this._userinfo);
        localStringBuffer.append('@');
      }
      if (this._host != null)
      {
        localStringBuffer.append(this._host);
        if (this._port != -1)
        {
          localStringBuffer.append(':');
          localStringBuffer.append(this._port);
        }
      }
      this._authority = localStringBuffer.toString().toCharArray();
      this._is_server = true;
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
    }
    throw new URIException(1, "invalid port number");
  }

  protected void parseUriReference(String paramString, boolean paramBoolean)
    throws URIException
  {
    if (paramString == null)
      throw new URIException("URI-Reference required");
    String str1 = paramString.trim();
    int i = str1.length();
    if (i > 0)
    {
      char[] arrayOfChar4 = new char[1];
      arrayOfChar4[0] = str1.charAt(0);
      if ((validate(arrayOfChar4, delims)) && (i >= 2))
      {
        char[] arrayOfChar5 = new char[1];
        arrayOfChar5[0] = str1.charAt(i - 1);
        if (validate(arrayOfChar5, delims))
        {
          str1 = str1.substring(1, i - 1);
          i -= 2;
        }
      }
    }
    int j = str1.indexOf(':');
    int k = str1.indexOf('/');
    int m;
    if (j > 0)
    {
      m = 0;
      if (k >= 0)
      {
        m = 0;
        if (k >= j);
      }
    }
    else
    {
      m = 1;
    }
    String str2;
    int n;
    int i1;
    int i4;
    label362: int i3;
    label492: label511: String str3;
    int i2;
    if (m != 0)
    {
      str2 = "/?#";
      n = indexFirstOf(str1, str2, 0);
      if (n == -1)
        n = 0;
      i1 = 0;
      if (n > 0)
      {
        i1 = 0;
        if (n < i)
        {
          int i5 = str1.charAt(n);
          i1 = 0;
          if (i5 == 58)
          {
            char[] arrayOfChar3 = str1.substring(0, n).toLowerCase().toCharArray();
            if (!validate(arrayOfChar3, scheme))
              break label656;
            this._scheme = arrayOfChar3;
            n++;
            i1 = n;
          }
        }
      }
      this._is_hier_part = false;
      this._is_rel_path = false;
      this._is_abs_path = false;
      this._is_net_path = false;
      if ((n >= 0) && (n < i) && (str1.charAt(n) == '/'))
      {
        this._is_hier_part = true;
        if ((n + 2 < i) && (str1.charAt(n + 1) == '/'))
        {
          i4 = indexFirstOf(str1, "/?#", n + 2);
          if (i4 == -1)
          {
            if (str1.substring(n + 2).length() != 0)
              break label667;
            i4 = n + 2;
          }
          parseAuthority(str1.substring(n + 2, i4), paramBoolean);
          n = i4;
          i1 = i4;
          this._is_net_path = true;
        }
        if (i1 == n)
          this._is_abs_path = true;
      }
      if (i1 < i)
      {
        i3 = indexFirstOf(str1, "?#", i1);
        if (i3 == -1)
          i3 = str1.length();
        if (!this._is_abs_path)
        {
          if (((paramBoolean) || (!prevalidate(str1.substring(i1, i3), disallowed_rel_path))) && ((!paramBoolean) || (!validate(str1.substring(i1, i3).toCharArray(), rel_path))))
            break label676;
          this._is_rel_path = true;
        }
        if (!paramBoolean)
          break label739;
        setRawPath(str1.substring(i1, i3).toCharArray());
        n = i3;
      }
      str3 = getProtocolCharset();
      if ((n >= 0) && (n + 1 < i) && (str1.charAt(n) == '?'))
      {
        i2 = str1.indexOf('#', n + 1);
        if (i2 == -1)
          i2 = str1.length();
        if (!paramBoolean)
          break label754;
      }
    }
    label656: label667: label676: label739: label754: for (char[] arrayOfChar2 = str1.substring(n + 1, i2).toCharArray(); ; arrayOfChar2 = encode(str1.substring(n + 1, i2), allowed_query, str3))
    {
      this._query = arrayOfChar2;
      n = i2;
      if ((n >= 0) && (n + 1 <= i) && (str1.charAt(n) == '#'))
      {
        if (n + 1 != i)
          break label777;
        this._fragment = "".toCharArray();
      }
      setURI();
      return;
      str2 = ":/?#";
      break;
      throw new URIException("incorrect scheme");
      i4 = str1.length();
      break label362;
      if (((!paramBoolean) && (prevalidate(str1.substring(i1, i3), disallowed_opaque_part))) || ((paramBoolean) && (validate(str1.substring(i1, i3).toCharArray(), opaque_part))))
      {
        this._is_opaque_part = true;
        break label492;
      }
      this._path = null;
      break label492;
      setPath(str1.substring(i1, i3));
      break label511;
    }
    label777: if (paramBoolean);
    for (char[] arrayOfChar1 = str1.substring(n + 1).toCharArray(); ; arrayOfChar1 = encode(str1.substring(n + 1), allowed_fragment, str3))
    {
      this._fragment = arrayOfChar1;
      break;
    }
  }

  protected boolean prevalidate(String paramString, BitSet paramBitSet)
  {
    if (paramString == null)
      return false;
    char[] arrayOfChar = paramString.toCharArray();
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfChar.length)
        return true;
      if (paramBitSet.get(arrayOfChar[i]))
        break;
    }
  }

  protected void readObject(ObjectInputStream paramObjectInputStream)
    throws ClassNotFoundException, IOException
  {
    paramObjectInputStream.defaultReadObject();
  }

  protected char[] removeFragmentIdentifier(char[] paramArrayOfChar)
  {
    if (paramArrayOfChar == null)
      return null;
    int i = new String(paramArrayOfChar).indexOf('#');
    if (i != -1)
      paramArrayOfChar = new String(paramArrayOfChar).substring(0, i).toCharArray();
    return paramArrayOfChar;
  }

  protected char[] resolvePath(char[] paramArrayOfChar1, char[] paramArrayOfChar2)
    throws URIException
  {
    if (paramArrayOfChar1 == null);
    int i;
    for (String str1 = ""; ; str1 = new String(paramArrayOfChar1))
    {
      i = str1.lastIndexOf('/');
      if (i != -1)
        paramArrayOfChar1 = str1.substring(0, i + 1).toCharArray();
      if ((paramArrayOfChar2 != null) && (paramArrayOfChar2.length != 0))
        break;
      return normalize(paramArrayOfChar1);
    }
    if (paramArrayOfChar2[0] == '/')
      return normalize(paramArrayOfChar2);
    StringBuffer localStringBuffer = new StringBuffer(str1.length() + paramArrayOfChar2.length);
    if (i != -1);
    for (String str2 = str1.substring(0, i + 1); ; str2 = "/")
    {
      localStringBuffer.append(str2);
      localStringBuffer.append(paramArrayOfChar2);
      return normalize(localStringBuffer.toString().toCharArray());
    }
  }

  public void setEscapedAuthority(String paramString)
    throws URIException
  {
    parseAuthority(paramString, true);
    setURI();
  }

  public void setEscapedFragment(String paramString)
    throws URIException
  {
    if (paramString == null)
    {
      this._fragment = null;
      this.hash = 0;
      return;
    }
    setRawFragment(paramString.toCharArray());
  }

  public void setEscapedPath(String paramString)
    throws URIException
  {
    if (paramString == null)
    {
      this._opaque = null;
      this._path = null;
      setURI();
      return;
    }
    setRawPath(paramString.toCharArray());
  }

  public void setEscapedQuery(String paramString)
    throws URIException
  {
    if (paramString == null)
    {
      this._query = null;
      setURI();
      return;
    }
    setRawQuery(paramString.toCharArray());
  }

  public void setFragment(String paramString)
    throws URIException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      if (paramString == null);
      for (char[] arrayOfChar = null; ; arrayOfChar = paramString.toCharArray())
      {
        this._fragment = arrayOfChar;
        this.hash = 0;
        return;
      }
    }
    this._fragment = encode(paramString, allowed_fragment, getProtocolCharset());
    this.hash = 0;
  }

  public void setPath(String paramString)
    throws URIException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      if (paramString == null);
      for (char[] arrayOfChar = null; ; arrayOfChar = paramString.toCharArray())
      {
        this._opaque = arrayOfChar;
        this._path = arrayOfChar;
        setURI();
        return;
      }
    }
    String str = getProtocolCharset();
    if ((this._is_net_path) || (this._is_abs_path))
      this._path = encode(paramString, allowed_abs_path, str);
    while (true)
    {
      setURI();
      return;
      if (this._is_rel_path)
      {
        StringBuffer localStringBuffer1 = new StringBuffer(paramString.length());
        int i = paramString.indexOf('/');
        if (i == 0)
          throw new URIException(1, "incorrect relative path");
        if (i > 0)
        {
          localStringBuffer1.append(encode(paramString.substring(0, i), allowed_rel_path, str));
          localStringBuffer1.append(encode(paramString.substring(i), allowed_abs_path, str));
        }
        while (true)
        {
          this._path = localStringBuffer1.toString().toCharArray();
          break;
          localStringBuffer1.append(encode(paramString, allowed_rel_path, str));
        }
      }
      if (!this._is_opaque_part)
        break;
      StringBuffer localStringBuffer2 = new StringBuffer();
      localStringBuffer2.insert(0, encode(paramString.substring(0, 1), uric_no_slash, str));
      localStringBuffer2.insert(1, encode(paramString.substring(1), uric, str));
      this._opaque = localStringBuffer2.toString().toCharArray();
    }
    throw new URIException(1, "incorrect path");
  }

  public void setQuery(String paramString)
    throws URIException
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      if (paramString == null);
      for (char[] arrayOfChar = null; ; arrayOfChar = paramString.toCharArray())
      {
        this._query = arrayOfChar;
        setURI();
        return;
      }
    }
    setRawQuery(encode(paramString, allowed_query, getProtocolCharset()));
  }

  public void setRawAuthority(char[] paramArrayOfChar)
    throws URIException, NullPointerException
  {
    parseAuthority(new String(paramArrayOfChar), true);
    setURI();
  }

  public void setRawFragment(char[] paramArrayOfChar)
    throws URIException
  {
    if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0))
    {
      this._fragment = paramArrayOfChar;
      this.hash = 0;
      return;
    }
    if (!validate(paramArrayOfChar, fragment))
      throw new URIException(3, "escaped fragment not valid");
    this._fragment = paramArrayOfChar;
    this.hash = 0;
  }

  public void setRawPath(char[] paramArrayOfChar)
    throws URIException
  {
    if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0))
    {
      this._opaque = paramArrayOfChar;
      this._path = paramArrayOfChar;
      setURI();
      return;
    }
    char[] arrayOfChar = removeFragmentIdentifier(paramArrayOfChar);
    if ((this._is_net_path) || (this._is_abs_path))
    {
      if (arrayOfChar[0] != '/')
        throw new URIException(1, "not absolute path");
      if (!validate(arrayOfChar, abs_path))
        throw new URIException(3, "escaped absolute path not valid");
      this._path = arrayOfChar;
    }
    while (true)
    {
      setURI();
      return;
      if (this._is_rel_path)
      {
        int i = indexFirstOf(arrayOfChar, '/');
        if (i == 0)
          throw new URIException(1, "incorrect path");
        if (((i > 0) && (!validate(arrayOfChar, 0, i - 1, rel_segment)) && (!validate(arrayOfChar, i, -1, abs_path))) || ((i < 0) && (!validate(arrayOfChar, 0, -1, rel_segment))))
          throw new URIException(3, "escaped relative path not valid");
        this._path = arrayOfChar;
      }
      else
      {
        if (!this._is_opaque_part)
          break;
        if ((!uric_no_slash.get(arrayOfChar[0])) && (!validate(arrayOfChar, 1, -1, uric)))
          throw new URIException(3, "escaped opaque part not valid");
        this._opaque = arrayOfChar;
      }
    }
    throw new URIException(1, "incorrect path");
  }

  public void setRawQuery(char[] paramArrayOfChar)
    throws URIException
  {
    if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0))
    {
      this._query = paramArrayOfChar;
      setURI();
      return;
    }
    char[] arrayOfChar = removeFragmentIdentifier(paramArrayOfChar);
    if (!validate(arrayOfChar, query))
      throw new URIException(3, "escaped query not valid");
    this._query = arrayOfChar;
    setURI();
  }

  protected void setURI()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this._scheme != null)
    {
      localStringBuffer.append(this._scheme);
      localStringBuffer.append(':');
    }
    if (this._is_net_path)
    {
      localStringBuffer.append("//");
      if (this._authority != null)
        localStringBuffer.append(this._authority);
    }
    if ((this._opaque != null) && (this._is_opaque_part))
      localStringBuffer.append(this._opaque);
    while (true)
    {
      if (this._query != null)
      {
        localStringBuffer.append('?');
        localStringBuffer.append(this._query);
      }
      this._uri = localStringBuffer.toString().toCharArray();
      this.hash = 0;
      return;
      if ((this._path != null) && (this._path.length != 0))
        localStringBuffer.append(this._path);
    }
  }

  public String toString()
  {
    return getEscapedURI();
  }

  protected boolean validate(char[] paramArrayOfChar, int paramInt1, int paramInt2, BitSet paramBitSet)
  {
    if (paramInt2 == -1)
      paramInt2 = -1 + paramArrayOfChar.length;
    for (int i = paramInt1; ; i++)
    {
      if (i > paramInt2)
        return true;
      if (!paramBitSet.get(paramArrayOfChar[i]))
        return false;
    }
  }

  protected boolean validate(char[] paramArrayOfChar, BitSet paramBitSet)
  {
    return validate(paramArrayOfChar, 0, -1, paramBitSet);
  }

  protected void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
  }

  public static class DefaultCharsetChanged extends RuntimeException
  {
    public static final int DOCUMENT_CHARSET = 2;
    public static final int PROTOCOL_CHARSET = 1;
    public static final int UNKNOWN;
    private String reason;
    private int reasonCode;

    public DefaultCharsetChanged(int paramInt, String paramString)
    {
      super();
      this.reason = paramString;
      this.reasonCode = paramInt;
    }

    public String getReason()
    {
      return this.reason;
    }

    public int getReasonCode()
    {
      return this.reasonCode;
    }
  }

  public static class LocaleToCharsetMap
  {
    private static final Hashtable LOCALE_TO_CHARSET_MAP = new Hashtable();

    static
    {
      LOCALE_TO_CHARSET_MAP.put("ar", "ISO-8859-6");
      LOCALE_TO_CHARSET_MAP.put("be", "ISO-8859-5");
      LOCALE_TO_CHARSET_MAP.put("bg", "ISO-8859-5");
      LOCALE_TO_CHARSET_MAP.put("ca", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("cs", "ISO-8859-2");
      LOCALE_TO_CHARSET_MAP.put("da", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("de", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("el", "ISO-8859-7");
      LOCALE_TO_CHARSET_MAP.put("en", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("es", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("et", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("fi", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("fr", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("hr", "ISO-8859-2");
      LOCALE_TO_CHARSET_MAP.put("hu", "ISO-8859-2");
      LOCALE_TO_CHARSET_MAP.put("is", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("it", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("iw", "ISO-8859-8");
      LOCALE_TO_CHARSET_MAP.put("ja", "Shift_JIS");
      LOCALE_TO_CHARSET_MAP.put("ko", "EUC-KR");
      LOCALE_TO_CHARSET_MAP.put("lt", "ISO-8859-2");
      LOCALE_TO_CHARSET_MAP.put("lv", "ISO-8859-2");
      LOCALE_TO_CHARSET_MAP.put("mk", "ISO-8859-5");
      LOCALE_TO_CHARSET_MAP.put("nl", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("no", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("pl", "ISO-8859-2");
      LOCALE_TO_CHARSET_MAP.put("pt", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("ro", "ISO-8859-2");
      LOCALE_TO_CHARSET_MAP.put("ru", "ISO-8859-5");
      LOCALE_TO_CHARSET_MAP.put("sh", "ISO-8859-5");
      LOCALE_TO_CHARSET_MAP.put("sk", "ISO-8859-2");
      LOCALE_TO_CHARSET_MAP.put("sl", "ISO-8859-2");
      LOCALE_TO_CHARSET_MAP.put("sq", "ISO-8859-2");
      LOCALE_TO_CHARSET_MAP.put("sr", "ISO-8859-5");
      LOCALE_TO_CHARSET_MAP.put("sv", "ISO-8859-1");
      LOCALE_TO_CHARSET_MAP.put("tr", "ISO-8859-9");
      LOCALE_TO_CHARSET_MAP.put("uk", "ISO-8859-5");
      LOCALE_TO_CHARSET_MAP.put("zh", "GB2312");
      LOCALE_TO_CHARSET_MAP.put("zh_TW", "Big5");
    }

    public static String getCharset(Locale paramLocale)
    {
      String str = (String)LOCALE_TO_CHARSET_MAP.get(paramLocale.toString());
      if (str != null)
        return str;
      return (String)LOCALE_TO_CHARSET_MAP.get(paramLocale.getLanguage());
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.URI
 * JD-Core Version:    0.6.2
 */