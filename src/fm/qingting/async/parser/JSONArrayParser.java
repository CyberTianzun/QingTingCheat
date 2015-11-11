package fm.qingting.async.parser;

import fm.qingting.async.DataEmitter;
import fm.qingting.async.DataSink;
import fm.qingting.async.callback.CompletedCallback;
import fm.qingting.async.future.Future;
import fm.qingting.async.future.TransformFuture;
import org.json.JSONArray;

public class JSONArrayParser
  implements AsyncParser<JSONArray>
{
  public Future<JSONArray> parse(DataEmitter paramDataEmitter)
  {
    return new TransformFuture()
    {
      protected void transform(String paramAnonymousString)
        throws Exception
      {
        setComplete(new JSONArray(paramAnonymousString));
      }
    }
    .from(new StringParser().parse(paramDataEmitter));
  }

  public void write(DataSink paramDataSink, JSONArray paramJSONArray, CompletedCallback paramCompletedCallback)
  {
    new StringParser().write(paramDataSink, paramJSONArray.toString(), paramCompletedCallback);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.parser.JSONArrayParser
 * JD-Core Version:    0.6.2
 */