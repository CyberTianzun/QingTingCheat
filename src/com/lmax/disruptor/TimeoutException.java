package com.lmax.disruptor;

public class TimeoutException extends Exception
{
  public static final TimeoutException INSTANCE = new TimeoutException();

  public Throwable fillInStackTrace()
  {
    return this;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.lmax.disruptor.TimeoutException
 * JD-Core Version:    0.6.2
 */