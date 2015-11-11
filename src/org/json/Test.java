package org.json;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Iterator;

public class Test
{
  public static void main(String[] paramArrayOfString)
  {
    JSONString local1Obj = new JSONString()
    {
      public boolean aBoolean;
      public double aNumber;

      public String getBENT()
      {
        return "All uppercase key";
      }

      public double getNumber()
      {
        return this.aNumber;
      }

      public String getString()
      {
        return Test.this;
      }

      public String getX()
      {
        return "x";
      }

      public boolean isBoolean()
      {
        return this.aBoolean;
      }

      public String toJSONString()
      {
        return "{" + JSONObject.quote(Test.this) + ":" + JSONObject.doubleToString(this.aNumber) + "}";
      }

      public String toString()
      {
        return getString() + " " + getNumber() + " " + isBoolean() + "." + getBENT() + " " + getX();
      }
    };
    try
    {
      JSONObject localJSONObject1 = XML.toJSONObject("<![CDATA[This is a collection of test patterns and examples for org.json.]]>  Ignore the stuff past the end.  ");
      System.out.println(localJSONObject1.toString());
      JSONObject localJSONObject2 = new JSONObject(local1Obj);
      System.out.println(localJSONObject2.toString());
      String str1 = new JSONStringer().object().key("foo").value("bar").key("baz").array().object().key("quux").value("Thanks, Josh!").endObject().endArray().key("obj keys").value(JSONObject.getNames(local1Obj)).endObject().toString();
      System.out.println(str1);
      System.out.println(new JSONStringer().object().key("a").array().array().array().value("b").endArray().endArray().endArray().endObject().toString());
      JSONStringer localJSONStringer = new JSONStringer();
      localJSONStringer.array();
      localJSONStringer.value(1L);
      localJSONStringer.array();
      localJSONStringer.value(null);
      localJSONStringer.array();
      localJSONStringer.object();
      localJSONStringer.key("empty-array").array().endArray();
      localJSONStringer.key("answer").value(42L);
      localJSONStringer.key("null").value(null);
      localJSONStringer.key("false").value(false);
      localJSONStringer.key("true").value(true);
      localJSONStringer.key("big").value(1.23456789E+96D);
      localJSONStringer.key("small").value(1.23456789E-80D);
      localJSONStringer.key("empty-object").object().endObject();
      localJSONStringer.key("long");
      localJSONStringer.value(9223372036854775807L);
      localJSONStringer.endObject();
      localJSONStringer.value("two");
      localJSONStringer.endArray();
      localJSONStringer.value(true);
      localJSONStringer.endArray();
      localJSONStringer.value(98.599999999999994D);
      localJSONStringer.value(-100.0D);
      localJSONStringer.object();
      localJSONStringer.endObject();
      localJSONStringer.object();
      localJSONStringer.key("one");
      localJSONStringer.value(1.0D);
      localJSONStringer.endObject();
      localJSONStringer.value(local1Obj);
      localJSONStringer.endArray();
      System.out.println(localJSONStringer.toString());
      System.out.println(new JSONArray(localJSONStringer.toString()).toString(4));
      JSONArray localJSONArray1 = new JSONArray(new int[] { 1, 2, 3 });
      System.out.println(localJSONArray1.toString());
      JSONObject localJSONObject3 = new JSONObject(local1Obj, new String[] { "aString", "aNumber", "aBoolean" });
      localJSONObject3.put("Testing JSONString interface", local1Obj);
      System.out.println(localJSONObject3.toString(4));
      JSONObject localJSONObject4 = new JSONObject("{slashes: '///', closetag: '</script>', backslash:'\\\\', ei: {quotes: '\"\\''},eo: {a: '\"quoted\"', b:\"don't\"}, quotes: [\"'\", '\"']}");
      System.out.println(localJSONObject4.toString(2));
      System.out.println(XML.toString(localJSONObject4));
      System.out.println("");
      JSONObject localJSONObject5 = new JSONObject("/*comment*/{foo: [true, false,9876543210,    0.0, 1.00000001,  1.000000000001, 1.00000000000000001, .00000000000000001, 2.00, 0.1, 2e100, -32,[],{}, \"string\"],   to   : null, op : 'Good',ten:10} postfix comment");
      localJSONObject5.put("String", "98.6");
      localJSONObject5.put("JSONObject", new JSONObject());
      localJSONObject5.put("JSONArray", new JSONArray());
      localJSONObject5.put("int", 57);
      localJSONObject5.put("double", 1.234567890123457E+29D);
      localJSONObject5.put("true", true);
      localJSONObject5.put("false", false);
      localJSONObject5.put("null", JSONObject.NULL);
      localJSONObject5.put("bool", "true");
      localJSONObject5.put("zero", 0.0D);
      localJSONObject5.put("\\u2028", " ");
      localJSONObject5.put("\\u2029", " ");
      JSONArray localJSONArray2 = localJSONObject5.getJSONArray("foo");
      localJSONArray2.put(666);
      localJSONArray2.put(2001.99D);
      localJSONArray2.put("so \"fine\".");
      localJSONArray2.put("so <fine>.");
      localJSONArray2.put(true);
      localJSONArray2.put(false);
      localJSONArray2.put(new JSONArray());
      localJSONArray2.put(new JSONObject());
      localJSONObject5.put("keys", JSONObject.getNames(localJSONObject5));
      System.out.println(localJSONObject5.toString(4));
      System.out.println(XML.toString(localJSONObject5));
      System.out.println("String: " + localJSONObject5.getDouble("String"));
      System.out.println("  bool: " + localJSONObject5.getBoolean("bool"));
      System.out.println("    to: " + localJSONObject5.getString("to"));
      System.out.println("  true: " + localJSONObject5.getString("true"));
      System.out.println("   foo: " + localJSONObject5.getJSONArray("foo"));
      System.out.println("    op: " + localJSONObject5.getString("op"));
      System.out.println("   ten: " + localJSONObject5.getInt("ten"));
      System.out.println("  oops: " + localJSONObject5.optBoolean("oops"));
      JSONObject localJSONObject6 = XML.toJSONObject("<xml one = 1 two=' \"2\" '><five></five>First \t&lt;content&gt;<five></five> This is \"content\". <three>  3  </three>JSON does not preserve the sequencing of elements and contents.<three>  III  </three>  <three>  T H R E E</three><four/>Content text is an implied structure in XML. <six content=\"6\"/>JSON does not have implied structure:<seven>7</seven>everything is explicit.<![CDATA[CDATA blocks<are><supported>!]]></xml>");
      System.out.println(localJSONObject6.toString(2));
      System.out.println(XML.toString(localJSONObject6));
      System.out.println("");
      JSONObject localJSONObject7 = XML.toJSONObject("<mapping><empty/>   <class name = \"Customer\">      <field name = \"ID\" type = \"string\">         <bind-xml name=\"ID\" node=\"attribute\"/>      </field>      <field name = \"FirstName\" type = \"FirstName\"/>      <field name = \"MI\" type = \"MI\"/>      <field name = \"LastName\" type = \"LastName\"/>   </class>   <class name = \"FirstName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"MI\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"LastName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class></mapping>");
      System.out.println(localJSONObject7.toString(2));
      System.out.println(XML.toString(localJSONObject7));
      System.out.println("");
      JSONObject localJSONObject8 = XML.toJSONObject("<?xml version=\"1.0\" ?><Book Author=\"Anonymous\"><Title>Sample Book</Title><Chapter id=\"1\">This is chapter 1. It is not very long or interesting.</Chapter><Chapter id=\"2\">This is chapter 2. Although it is longer than chapter 1, it is not any more interesting.</Chapter></Book>");
      System.out.println(localJSONObject8.toString(2));
      System.out.println(XML.toString(localJSONObject8));
      System.out.println("");
      JSONObject localJSONObject9 = XML.toJSONObject("<!DOCTYPE bCard 'http://www.cs.caltech.edu/~adam/schemas/bCard'><bCard><?xml default bCard        firstname = ''        lastname  = '' company   = '' email = '' homepage  = ''?><bCard        firstname = 'Rohit'        lastname  = 'Khare'        company   = 'MCI'        email     = 'khare@mci.net'        homepage  = 'http://pest.w3.org/'/><bCard        firstname = 'Adam'        lastname  = 'Rifkin'        company   = 'Caltech Infospheres Project'        email     = 'adam@cs.caltech.edu'        homepage  = 'http://www.cs.caltech.edu/~adam/'/></bCard>");
      System.out.println(localJSONObject9.toString(2));
      System.out.println(XML.toString(localJSONObject9));
      System.out.println("");
      JSONObject localJSONObject10 = XML.toJSONObject("<?xml version=\"1.0\"?><customer>    <firstName>        <text>Fred</text>    </firstName>    <ID>fbs0001</ID>    <lastName> <text>Scerbo</text>    </lastName>    <MI>        <text>B</text>    </MI></customer>");
      System.out.println(localJSONObject10.toString(2));
      System.out.println(XML.toString(localJSONObject10));
      System.out.println("");
      JSONObject localJSONObject11 = XML.toJSONObject("<!ENTITY tp-address PUBLIC '-//ABC University::Special Collections Library//TEXT (titlepage: name and address)//EN' 'tpspcoll.sgm'><list type='simple'><head>Repository Address </head><item>Special Collections Library</item><item>ABC University</item><item>Main Library, 40 Circle Drive</item><item>Ourtown, Pennsylvania</item><item>17654 USA</item></list>");
      System.out.println(localJSONObject11.toString());
      System.out.println(XML.toString(localJSONObject11));
      System.out.println("");
      JSONObject localJSONObject12 = XML.toJSONObject("<test intertag status=ok><empty/>deluxe<blip sweet=true>&amp;&quot;toot&quot;&toot;&#x41;</blip><x>eks</x><w>bonus</w><w>bonus2</w></test>");
      System.out.println(localJSONObject12.toString(2));
      System.out.println(XML.toString(localJSONObject12));
      System.out.println("");
      JSONObject localJSONObject13 = HTTP.toJSONObject("GET / HTTP/1.0\nAccept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*\nAccept-Language: en-us\nUser-Agent: Mozilla/4.0 (compatible; MSIE 5.5; Windows 98; Win 9x 4.90; T312461; Q312461)\nHost: www.nokko.com\nConnection: keep-alive\nAccept-encoding: gzip, deflate\n");
      System.out.println(localJSONObject13.toString(2));
      System.out.println(HTTP.toString(localJSONObject13));
      System.out.println("");
      JSONObject localJSONObject14 = HTTP.toJSONObject("HTTP/1.1 200 Oki Doki\nDate: Sun, 26 May 2002 17:38:52 GMT\nServer: Apache/1.3.23 (Unix) mod_perl/1.26\nKeep-Alive: timeout=15, max=100\nConnection: Keep-Alive\nTransfer-Encoding: chunked\nContent-Type: text/html\n");
      System.out.println(localJSONObject14.toString(2));
      System.out.println(HTTP.toString(localJSONObject14));
      System.out.println("");
      JSONObject localJSONObject15 = new JSONObject("{nix: null, nux: false, null: 'null', 'Request-URI': '/', Method: 'GET', 'HTTP-Version': 'HTTP/1.0'}");
      System.out.println(localJSONObject15.toString(2));
      System.out.println("isNull: " + localJSONObject15.isNull("nix"));
      System.out.println("   has: " + localJSONObject15.has("nix"));
      System.out.println(XML.toString(localJSONObject15));
      System.out.println(HTTP.toString(localJSONObject15));
      System.out.println("");
      JSONObject localJSONObject16 = XML.toJSONObject("<?xml version='1.0' encoding='UTF-8'?>\n\n<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"><SOAP-ENV:Body><ns1:doGoogleSearch xmlns:ns1=\"urn:GoogleSearch\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><key xsi:type=\"xsd:string\">GOOGLEKEY</key> <q xsi:type=\"xsd:string\">'+search+'</q> <start xsi:type=\"xsd:int\">0</start> <maxResults xsi:type=\"xsd:int\">10</maxResults> <filter xsi:type=\"xsd:boolean\">true</filter> <restrict xsi:type=\"xsd:string\"></restrict> <safeSearch xsi:type=\"xsd:boolean\">false</safeSearch> <lr xsi:type=\"xsd:string\"></lr> <ie xsi:type=\"xsd:string\">latin1</ie> <oe xsi:type=\"xsd:string\">latin1</oe></ns1:doGoogleSearch></SOAP-ENV:Body></SOAP-ENV:Envelope>");
      System.out.println(localJSONObject16.toString(2));
      System.out.println(XML.toString(localJSONObject16));
      System.out.println("");
      JSONObject localJSONObject17 = new JSONObject("{Envelope: {Body: {\"ns1:doGoogleSearch\": {oe: \"latin1\", filter: true, q: \"'+search+'\", key: \"GOOGLEKEY\", maxResults: 10, \"SOAP-ENV:encodingStyle\": \"http://schemas.xmlsoap.org/soap/encoding/\", start: 0, ie: \"latin1\", safeSearch:false, \"xmlns:ns1\": \"urn:GoogleSearch\"}}}}");
      System.out.println(localJSONObject17.toString(2));
      System.out.println(XML.toString(localJSONObject17));
      System.out.println("");
      JSONObject localJSONObject18 = CookieList.toJSONObject("  f%oo = b+l=ah  ; o;n%40e = t.wo ");
      System.out.println(localJSONObject18.toString(2));
      System.out.println(CookieList.toString(localJSONObject18));
      System.out.println("");
      JSONObject localJSONObject19 = Cookie.toJSONObject("f%oo=blah; secure ;expires = April 24, 2002");
      System.out.println(localJSONObject19.toString(2));
      System.out.println(Cookie.toString(localJSONObject19));
      System.out.println("");
      JSONObject localJSONObject20 = new JSONObject("{script: 'It is not allowed in HTML to send a close script tag in a string<script>because it confuses browsers</script>so we insert a backslash before the /'}");
      System.out.println(localJSONObject20.toString());
      System.out.println("");
      JSONTokener localJSONTokener = new JSONTokener("{op:'test', to:'session', pre:1}{op:'test', to:'session', pre:2}");
      JSONObject localJSONObject21 = new JSONObject(localJSONTokener);
      System.out.println(localJSONObject21.toString());
      System.out.println("pre: " + localJSONObject21.optInt("pre"));
      int i = localJSONTokener.skipTo('{');
      System.out.println(i);
      JSONObject localJSONObject22 = new JSONObject(localJSONTokener);
      System.out.println(localJSONObject22.toString());
      System.out.println("");
      JSONArray localJSONArray3 = CDL.toJSONArray("No quotes, 'Single Quotes', \"Double Quotes\"\n1,'2',\"3\"\n,'It is \"good,\"', \"It works.\"\n\n");
      System.out.println(CDL.toString(localJSONArray3));
      System.out.println("");
      System.out.println(localJSONArray3.toString(4));
      System.out.println("");
      JSONArray localJSONArray4 = new JSONArray(" [\"<escape>\", next is an implied null , , ok,] ");
      System.out.println(localJSONArray4.toString());
      System.out.println("");
      System.out.println(XML.toString(localJSONArray4));
      System.out.println("");
      JSONObject localJSONObject23 = new JSONObject("{ fun => with non-standard forms ; forgiving => This package can be used to parse formats that are similar to but not stricting conforming to JSON; why=To make it easier to migrate existing data to JSON,one = [[1.00]]; uno=[[{1=>1}]];'+':+6e66 ;pluses=+++;empty = '' , 'double':0.666,true: TRUE, false: FALSE, null=NULL;[true] = [[!,@;*]]; string=>  o. k. ; # comment\r oct=0666; hex=0x666; dec=666; o=0999; noh=0x0x}");
      System.out.println(localJSONObject23.toString(4));
      System.out.println("");
      if ((localJSONObject23.getBoolean("true")) && (!localJSONObject23.getBoolean("false")))
        System.out.println("It's all good");
      System.out.println("");
      JSONObject localJSONObject24 = new JSONObject(localJSONObject23, new String[] { "dec", "oct", "hex", "missing" });
      System.out.println(localJSONObject24.toString(4));
      System.out.println("");
      System.out.println(new JSONStringer().array().value(localJSONArray4).value(localJSONObject24).endArray());
      JSONObject localJSONObject25 = new JSONObject("{string: \"98.6\", long: 2147483648, int: 2147483647, longer: 9223372036854775807, double: 9223372036854775808}");
      System.out.println(localJSONObject25.toString(4));
      System.out.println("\ngetInt");
      System.out.println("int    " + localJSONObject25.getInt("int"));
      System.out.println("long   " + localJSONObject25.getInt("long"));
      System.out.println("longer " + localJSONObject25.getInt("longer"));
      System.out.println("double " + localJSONObject25.getInt("double"));
      System.out.println("string " + localJSONObject25.getInt("string"));
      System.out.println("\ngetLong");
      System.out.println("int    " + localJSONObject25.getLong("int"));
      System.out.println("long   " + localJSONObject25.getLong("long"));
      System.out.println("longer " + localJSONObject25.getLong("longer"));
      System.out.println("double " + localJSONObject25.getLong("double"));
      System.out.println("string " + localJSONObject25.getLong("string"));
      System.out.println("\ngetDouble");
      System.out.println("int    " + localJSONObject25.getDouble("int"));
      System.out.println("long   " + localJSONObject25.getDouble("long"));
      System.out.println("longer " + localJSONObject25.getDouble("longer"));
      System.out.println("double " + localJSONObject25.getDouble("double"));
      System.out.println("string " + localJSONObject25.getDouble("string"));
      localJSONObject25.put("good sized", 9223372036854775807L);
      System.out.println(localJSONObject25.toString(4));
      JSONArray localJSONArray5 = new JSONArray("[2147483647, 2147483648, 9223372036854775807, 9223372036854775808]");
      System.out.println(localJSONArray5.toString(4));
      System.out.println("\nKeys: ");
      Iterator localIterator = localJSONObject25.keys();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        System.out.println(str2 + ": " + localJSONObject25.getString(str2));
      }
    }
    catch (Exception localException1)
    {
      System.out.println(localException1.toString());
      return;
    }
    System.out.println("\naccumulate: ");
    JSONObject localJSONObject26 = new JSONObject();
    localJSONObject26.accumulate("stooge", "Curly");
    localJSONObject26.accumulate("stooge", "Larry");
    localJSONObject26.accumulate("stooge", "Moe");
    localJSONObject26.getJSONArray("stooge").put(5, "Shemp");
    System.out.println(localJSONObject26.toString(4));
    System.out.println("\nwrite:");
    System.out.println(localJSONObject26.write(new StringWriter()));
    JSONObject localJSONObject27 = XML.toJSONObject("<xml empty><a></a><a>1</a><a>22</a><a>333</a></xml>");
    System.out.println(localJSONObject27.toString(4));
    System.out.println(XML.toString(localJSONObject27));
    JSONObject localJSONObject28 = XML.toJSONObject("<book><chapter>Content of the first chapter</chapter><chapter>Content of the second chapter      <chapter>Content of the first subchapter</chapter>      <chapter>Content of the second subchapter</chapter></chapter><chapter>Third Chapter</chapter></book>");
    System.out.println(localJSONObject28.toString(4));
    System.out.println(XML.toString(localJSONObject28));
    JSONObject localJSONObject29 = new JSONObject(null);
    JSONArray localJSONArray6 = new JSONArray(null);
    localJSONObject29.append("stooge", "Joe DeRita");
    localJSONObject29.append("stooge", "Shemp");
    localJSONObject29.accumulate("stooges", "Curly");
    localJSONObject29.accumulate("stooges", "Larry");
    localJSONObject29.accumulate("stooges", "Moe");
    localJSONObject29.accumulate("stoogearray", localJSONObject29.get("stooges"));
    localJSONObject29.put("map", null);
    localJSONObject29.put("collection", null);
    localJSONObject29.put("array", localJSONArray6);
    localJSONArray6.put(null);
    localJSONArray6.put(null);
    System.out.println(localJSONObject29.toString(4));
    System.out.println("\nTesting Exceptions: ");
    System.out.print("Exception: ");
    try
    {
      System.out.println(localJSONObject29.getDouble("stooge"));
      System.out.print("Exception: ");
    }
    catch (Exception localException9)
    {
      try
      {
        System.out.println(localJSONObject29.getDouble("howard"));
        System.out.print("Exception: ");
      }
      catch (Exception localException9)
      {
        try
        {
          System.out.println(localJSONObject29.put(null, "howard"));
          System.out.print("Exception: ");
        }
        catch (Exception localException9)
        {
          try
          {
            System.out.println(localJSONArray6.getDouble(0));
            System.out.print("Exception: ");
          }
          catch (Exception localException9)
          {
            try
            {
              System.out.println(localJSONArray6.get(-1));
              System.out.print("Exception: ");
            }
            catch (Exception localException9)
            {
              try
              {
                System.out.println(localJSONArray6.put((0.0D / 0.0D)));
                System.out.print("Exception: ");
              }
              catch (Exception localException9)
              {
                try
                {
                  XML.toJSONObject("<a><b>    ");
                  System.out.print("Exception: ");
                }
                catch (Exception localException9)
                {
                  try
                  {
                    XML.toJSONObject("<a></b>    ");
                    System.out.print("Exception: ");
                  }
                  catch (Exception localException9)
                  {
                    try
                    {
                      while (true)
                      {
                        XML.toJSONObject("<a></a    ");
                        System.out.print("Exception: ");
                        try
                        {
                          JSONArray localJSONArray7 = new JSONArray(new Object());
                          System.out.println(localJSONArray7.toString());
                          return;
                        }
                        catch (Exception localException11)
                        {
                          System.out.println(localException11);
                          return;
                        }
                        localException2 = localException2;
                        System.out.println(localException2);
                        continue;
                        localException3 = localException3;
                        System.out.println(localException3);
                        continue;
                        localException4 = localException4;
                        System.out.println(localException4);
                        continue;
                        localException5 = localException5;
                        System.out.println(localException5);
                        continue;
                        localException6 = localException6;
                        System.out.println(localException6);
                        continue;
                        localException7 = localException7;
                        System.out.println(localException7);
                        continue;
                        localException8 = localException8;
                        System.out.println(localException8);
                      }
                      localException9 = localException9;
                      System.out.println(localException9);
                    }
                    catch (Exception localException10)
                    {
                      while (true)
                        System.out.println(localException10);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.json.Test
 * JD-Core Version:    0.6.2
 */