package com.tencent.stat.event;

import android.content.Context;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.json.JSONException;
import org.json.JSONObject;

public class ErrorEvent extends Event
{
  private String error;
  private int errorAttr;
  private int maxErrorLength = 100;

  public ErrorEvent(Context paramContext, int paramInt1, int paramInt2, Throwable paramThrowable)
  {
    super(paramContext, paramInt1);
    StackTraceElement[] arrayOfStackTraceElement1 = paramThrowable.getStackTrace();
    if ((arrayOfStackTraceElement1 != null) && (arrayOfStackTraceElement1.length > this.maxErrorLength))
    {
      StackTraceElement[] arrayOfStackTraceElement2 = new StackTraceElement[this.maxErrorLength];
      for (int i = 0; i < this.maxErrorLength; i++)
        arrayOfStackTraceElement2[i] = arrayOfStackTraceElement1[i];
      paramThrowable.setStackTrace(arrayOfStackTraceElement2);
    }
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    paramThrowable.printStackTrace(localPrintWriter);
    this.error = localStringWriter.toString();
    this.errorAttr = paramInt2;
    localPrintWriter.close();
  }

  public ErrorEvent(Context paramContext, int paramInt, String paramString)
  {
    super(paramContext, paramInt);
    this.error = paramString;
    this.errorAttr = 0;
  }

  public EventType getType()
  {
    return EventType.ERROR;
  }

  public boolean onEncode(JSONObject paramJSONObject)
    throws JSONException
  {
    paramJSONObject.put("er", this.error);
    paramJSONObject.put("ea", this.errorAttr);
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.event.ErrorEvent
 * JD-Core Version:    0.6.2
 */