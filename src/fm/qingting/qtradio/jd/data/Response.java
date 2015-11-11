package fm.qingting.qtradio.jd.data;

public class Response
{
  private JDApi.ErrorCode mCode;
  private Object mData;
  private JDApi.OnResponseListener mListener;

  public Object getData()
  {
    return this.mData;
  }

  public JDApi.ErrorCode getErrorCode()
  {
    return this.mCode;
  }

  public JDApi.OnResponseListener getListener()
  {
    return this.mListener;
  }

  public void setData(Object paramObject)
  {
    this.mData = paramObject;
  }

  public void setErrorCode(JDApi.ErrorCode paramErrorCode)
  {
    this.mCode = paramErrorCode;
  }

  public void setListener(JDApi.OnResponseListener paramOnResponseListener)
  {
    this.mListener = paramOnResponseListener;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.jd.data.Response
 * JD-Core Version:    0.6.2
 */