package fm.qingting.async.callback;

import fm.qingting.async.future.Continuation;

public abstract interface ContinuationCallback
{
  public abstract void onContinue(Continuation paramContinuation, CompletedCallback paramCompletedCallback)
    throws Exception;
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.callback.ContinuationCallback
 * JD-Core Version:    0.6.2
 */