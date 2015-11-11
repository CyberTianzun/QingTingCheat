package com.tencent.mm.sdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

public class AddCardToWXCardPackage
{
  public static class Req extends BaseReq
  {
    public List<AddCardToWXCardPackage.WXCardItem> cardArrary;

    public boolean checkArgs()
    {
      if ((this.cardArrary == null) || (this.cardArrary.size() == 0) || (this.cardArrary.size() > 40))
        return false;
      Iterator localIterator = this.cardArrary.iterator();
      while (localIterator.hasNext())
      {
        AddCardToWXCardPackage.WXCardItem localWXCardItem = (AddCardToWXCardPackage.WXCardItem)localIterator.next();
        if ((localWXCardItem == null) || (localWXCardItem.cardId == null) || (localWXCardItem.cardId.length() > 1024) || ((localWXCardItem.cardExtMsg != null) && (localWXCardItem.cardExtMsg.length() > 1024)))
          return false;
      }
      return true;
    }

    public int getType()
    {
      return 9;
    }

    public void toBundle(Bundle paramBundle)
    {
      super.toBundle(paramBundle);
      JSONStringer localJSONStringer = new JSONStringer();
      AddCardToWXCardPackage.WXCardItem localWXCardItem;
      String str;
      try
      {
        localJSONStringer.object();
        localJSONStringer.key("card_list");
        localJSONStringer.array();
        Iterator localIterator = this.cardArrary.iterator();
        while (true)
        {
          if (!localIterator.hasNext())
            break label145;
          localWXCardItem = (AddCardToWXCardPackage.WXCardItem)localIterator.next();
          localJSONStringer.object();
          localJSONStringer.key("card_id");
          localJSONStringer.value(localWXCardItem.cardId);
          localJSONStringer.key("card_ext");
          if (localWXCardItem.cardExtMsg != null)
            break;
          str = "";
          localJSONStringer.value(str);
          localJSONStringer.endObject();
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      while (true)
      {
        paramBundle.putString("_wxapi_add_card_to_wx_card_list", localJSONStringer.toString());
        return;
        str = localWXCardItem.cardExtMsg;
        break;
        label145: localJSONStringer.endArray();
        localJSONStringer.endObject();
      }
    }
  }

  public static class Resp extends BaseResp
  {
    public List<AddCardToWXCardPackage.WXCardItem> cardArrary;

    public Resp()
    {
    }

    public Resp(Bundle paramBundle)
    {
      fromBundle(paramBundle);
    }

    public boolean checkArgs()
    {
      return (this.cardArrary != null) && (this.cardArrary.size() != 0);
    }

    public void fromBundle(Bundle paramBundle)
    {
      super.fromBundle(paramBundle);
      if (this.cardArrary == null)
        this.cardArrary = new LinkedList();
      String str = paramBundle.getString("_wxapi_add_card_to_wx_card_list");
      if ((str != null) && (str.length() > 0))
        try
        {
          JSONArray localJSONArray = ((JSONObject)new JSONTokener(str).nextValue()).getJSONArray("card_list");
          for (int i = 0; i < localJSONArray.length(); i++)
          {
            JSONObject localJSONObject = localJSONArray.getJSONObject(i);
            AddCardToWXCardPackage.WXCardItem localWXCardItem = new AddCardToWXCardPackage.WXCardItem();
            localWXCardItem.cardId = localJSONObject.optString("card_id");
            localWXCardItem.cardExtMsg = localJSONObject.optString("card_ext");
            localWXCardItem.cardState = localJSONObject.optInt("is_succ");
            this.cardArrary.add(localWXCardItem);
          }
        }
        catch (Exception localException)
        {
        }
    }

    public int getType()
    {
      return 9;
    }

    public void toBundle(Bundle paramBundle)
    {
      super.toBundle(paramBundle);
      JSONStringer localJSONStringer = new JSONStringer();
      AddCardToWXCardPackage.WXCardItem localWXCardItem;
      String str;
      try
      {
        localJSONStringer.object();
        localJSONStringer.key("card_list");
        localJSONStringer.array();
        Iterator localIterator = this.cardArrary.iterator();
        while (true)
        {
          if (!localIterator.hasNext())
            break label163;
          localWXCardItem = (AddCardToWXCardPackage.WXCardItem)localIterator.next();
          localJSONStringer.object();
          localJSONStringer.key("card_id");
          localJSONStringer.value(localWXCardItem.cardId);
          localJSONStringer.key("card_ext");
          if (localWXCardItem.cardExtMsg != null)
            break;
          str = "";
          localJSONStringer.value(str);
          localJSONStringer.key("is_succ");
          localJSONStringer.value(localWXCardItem.cardState);
          localJSONStringer.endObject();
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      while (true)
      {
        paramBundle.putString("_wxapi_add_card_to_wx_card_list", localJSONStringer.toString());
        return;
        str = localWXCardItem.cardExtMsg;
        break;
        label163: localJSONStringer.endArray();
        localJSONStringer.endObject();
      }
    }
  }

  public static final class WXCardItem
  {
    public String cardExtMsg;
    public String cardId;
    public int cardState;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.modelbiz.AddCardToWXCardPackage
 * JD-Core Version:    0.6.2
 */