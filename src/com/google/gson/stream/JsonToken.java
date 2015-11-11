package com.google.gson.stream;

public enum JsonToken
{
  static
  {
    BEGIN_OBJECT = new JsonToken("BEGIN_OBJECT", 2);
    END_OBJECT = new JsonToken("END_OBJECT", 3);
    NAME = new JsonToken("NAME", 4);
    STRING = new JsonToken("STRING", 5);
    NUMBER = new JsonToken("NUMBER", 6);
    BOOLEAN = new JsonToken("BOOLEAN", 7);
    NULL = new JsonToken("NULL", 8);
    END_DOCUMENT = new JsonToken("END_DOCUMENT", 9);
    JsonToken[] arrayOfJsonToken = new JsonToken[10];
    arrayOfJsonToken[0] = BEGIN_ARRAY;
    arrayOfJsonToken[1] = END_ARRAY;
    arrayOfJsonToken[2] = BEGIN_OBJECT;
    arrayOfJsonToken[3] = END_OBJECT;
    arrayOfJsonToken[4] = NAME;
    arrayOfJsonToken[5] = STRING;
    arrayOfJsonToken[6] = NUMBER;
    arrayOfJsonToken[7] = BOOLEAN;
    arrayOfJsonToken[8] = NULL;
    arrayOfJsonToken[9] = END_DOCUMENT;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.gson.stream.JsonToken
 * JD-Core Version:    0.6.2
 */