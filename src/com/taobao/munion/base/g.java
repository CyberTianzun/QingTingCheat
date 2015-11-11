package com.taobao.munion.base;

import android.os.AsyncTask;

public abstract class g<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>
{
  int a;

  public g()
  {
    if (b.a())
      this.a = b.a(this);
  }

  protected abstract void a(Result paramResult);

  protected final void onPostExecute(Result paramResult)
  {
    a(paramResult);
    if (b.a())
      b.a(this.a);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.g
 * JD-Core Version:    0.6.2
 */