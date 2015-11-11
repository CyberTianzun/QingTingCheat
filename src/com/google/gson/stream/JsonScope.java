package com.google.gson.stream;

 enum JsonScope
{
  static
  {
    EMPTY_OBJECT = new JsonScope("EMPTY_OBJECT", 2);
    DANGLING_NAME = new JsonScope("DANGLING_NAME", 3);
    NONEMPTY_OBJECT = new JsonScope("NONEMPTY_OBJECT", 4);
    EMPTY_DOCUMENT = new JsonScope("EMPTY_DOCUMENT", 5);
    NONEMPTY_DOCUMENT = new JsonScope("NONEMPTY_DOCUMENT", 6);
    CLOSED = new JsonScope("CLOSED", 7);
    JsonScope[] arrayOfJsonScope = new JsonScope[8];
    arrayOfJsonScope[0] = EMPTY_ARRAY;
    arrayOfJsonScope[1] = NONEMPTY_ARRAY;
    arrayOfJsonScope[2] = EMPTY_OBJECT;
    arrayOfJsonScope[3] = DANGLING_NAME;
    arrayOfJsonScope[4] = NONEMPTY_OBJECT;
    arrayOfJsonScope[5] = EMPTY_DOCUMENT;
    arrayOfJsonScope[6] = NONEMPTY_DOCUMENT;
    arrayOfJsonScope[7] = CLOSED;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.gson.stream.JsonScope
 * JD-Core Version:    0.6.2
 */