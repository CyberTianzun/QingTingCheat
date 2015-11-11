package com.alibaba.fastjson;

import java.io.IOException;

public abstract interface JSONStreamAware
{
  public abstract void writeJSONString(Appendable paramAppendable)
    throws IOException;
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.JSONStreamAware
 * JD-Core Version:    0.6.2
 */