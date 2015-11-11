package com.taobao.newxp.net;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.taobao.newxp.a;
import com.taobao.newxp.common.AlimmContext;
import com.taobao.newxp.common.ExchangeConstants;
import com.taobao.newxp.controller.ExchangeDataService;
import com.taobao.newxp.controller.TabsDiskCache;
import com.taobao.newxp.view.handler.waketaobao.h;
import com.taobao.newxp.view.handler.waketaobao.j;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class MMEntity extends d
  implements Parcelable, Cloneable
{
  public static Parcelable.Creator<MMEntity> CREATOR = new Parcelable.Creator()
  {
    public MMEntity a(Parcel paramAnonymousParcel)
    {
      return new MMEntity(paramAnonymousParcel, null);
    }

    public MMEntity[] a(int paramAnonymousInt)
    {
      return new MMEntity[paramAnonymousInt];
    }
  };
  protected int a;
  protected long b;
  protected boolean c;
  public int cache = -1;
  public int displayStyle = 0;
  public String displayType = "bigImg";
  public HashMap<String, Object> ecomparms;
  public String entryStr;
  public long expire;
  public boolean filterInstalledApp;
  public int image_type = 0;
  public String iscache;
  public String ispreload;
  public String landingUrl = "";
  public String landing_image = "";
  public String landing_size;
  public a module = a.a;
  public int newTips = -1;
  public String new_image = "";
  public String opensize = "";
  public int sid_expired;
  public long[] timeline;
  public boolean wallSwitch;

  public MMEntity()
  {
    this.filterInstalledApp = true;
    this.wallSwitch = false;
    this.expire = 0L;
    this.sid_expired = 1;
    this.entryStr = "";
    this.timeline = new long[4];
    this.ecomparms = new HashMap();
    this.a = 0;
    this.b = -1L;
    this.c = false;
    this.ispreload = "";
    this.iscache = "";
  }

  private MMEntity(Parcel paramParcel)
  {
    super(paramParcel);
    this.filterInstalledApp = i;
    this.wallSwitch = false;
    this.expire = 0L;
    this.sid_expired = i;
    this.entryStr = "";
    this.timeline = new long[4];
    this.ecomparms = new HashMap();
    this.a = 0;
    this.b = -1L;
    this.c = false;
    this.ispreload = "";
    this.iscache = "";
    int j = paramParcel.readInt();
    a locala;
    boolean bool1;
    label238: boolean bool2;
    if (j == -1)
    {
      locala = null;
      this.module = locala;
      this.opensize = paramParcel.readString();
      this.landing_image = paramParcel.readString();
      this.landingUrl = paramParcel.readString();
      this.new_image = paramParcel.readString();
      this.image_type = paramParcel.readInt();
      this.displayStyle = paramParcel.readInt();
      this.displayType = paramParcel.readString();
      this.newTips = paramParcel.readInt();
      this.cache = paramParcel.readInt();
      if (paramParcel.readByte() == 0)
        break label368;
      bool1 = i;
      this.filterInstalledApp = bool1;
      if (paramParcel.readByte() == 0)
        break label374;
      bool2 = i;
      label254: this.wallSwitch = bool2;
      this.a = paramParcel.readInt();
      this.b = paramParcel.readLong();
      if (paramParcel.readByte() == 0)
        break label380;
    }
    while (true)
    {
      this.c = i;
      this.expire = paramParcel.readLong();
      this.sid_expired = paramParcel.readInt();
      this.entryStr = paramParcel.readString();
      this.ispreload = paramParcel.readString();
      this.iscache = paramParcel.readString();
      this.landing_size = paramParcel.readString();
      this.timeline = paramParcel.createLongArray();
      this.ecomparms = paramParcel.readHashMap(HashMap.class.getClassLoader());
      return;
      locala = a.values()[j];
      break;
      label368: bool1 = false;
      break label238;
      label374: bool2 = false;
      break label254;
      label380: i = 0;
    }
  }

  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }

  public int describeContents()
  {
    return 0;
  }

  public void extendFields(MMEntity paramMMEntity)
  {
    this.tabId = paramMMEntity.tabId;
    this.slot_act_params = paramMMEntity.slot_act_params;
    this.urlParams = paramMMEntity.urlParams;
  }

  public String getTimeConsuming()
  {
    if ((this.timeline != null) && (this.timeline.length == 4) && (this.timeline[0] > 0L))
    {
      long l1 = this.timeline[0];
      long l2 = this.timeline[1];
      long l3 = this.timeline[2];
      long l4 = this.timeline[3];
      long l5 = l2 - l1;
      long l6 = l4 - l3;
      if (l5 > 0L)
        return l5 + "_" + l6;
    }
    return null;
  }

  public void warp(JSONObject paramJSONObject)
  {
    super.warp(paramJSONObject);
    this.landing_size = paramJSONObject.optString("landing_size");
    boolean bool1;
    if (paramJSONObject.optInt("filter", 1) == 1)
      bool1 = true;
    while (true)
    {
      this.filterInstalledApp = bool1;
      boolean bool2;
      if (paramJSONObject.optInt("show_size", 1) == 1)
      {
        bool2 = true;
        label46: ExchangeConstants.show_size = bool2;
        this.cache = paramJSONObject.optInt("cache", -1);
        this.sid_expired = paramJSONObject.optInt("sid_expire", 1);
        this.expire = paramJSONObject.optLong("expire", 0L);
        this.landing_image = paramJSONObject.optString("landing_image", "");
        this.landingUrl = paramJSONObject.optString("landing_url", "");
        this.new_image = paramJSONObject.optString("new_img", "");
        this.displayType = paramJSONObject.optString("display_type", "bigImg");
        String str1 = paramJSONObject.optString("module", "");
        if (!TextUtils.isEmpty(str1))
        {
          a locala = a.a(str1);
          if (locala == null)
            locala = a.a;
          this.module = locala;
        }
        this.image_type = paramJSONObject.optInt("img_type", 0);
        JSONArray localJSONArray1 = paramJSONObject.optJSONArray("walls");
        if ((localJSONArray1 != null) && (localJSONArray1.length() > 1))
          this.wallSwitch = true;
        if (!paramJSONObject.has("opensize"));
      }
      try
      {
        float f = Float.parseFloat(paramJSONObject.getString("opensize")) / 100.0F;
        this.opensize = ("" + f);
        label258: JSONObject localJSONObject1 = paramJSONObject.optJSONObject("dm");
        label307: Context localContext;
        JSONArray localJSONArray2;
        if (localJSONObject1 != null)
        {
          this.a = localJSONObject1.optInt("on");
          this.b = (1000 * (60 * (60 * localJSONObject1.optInt("tm"))));
          int i = paramJSONObject.optInt("interval", 0);
          if ((i > 3) && (!ExchangeConstants.IGNORE_SERVER_INTERVAL))
            ExchangeConstants.REFRESH_INTERVAL = i * 1000;
          this.newTips = paramJSONObject.optInt("new_num", -1);
          this.entryStr = paramJSONObject.optString("landing_text", "");
          this.ispreload = paramJSONObject.optString("ispreload", "");
          this.iscache = paramJSONObject.optString("iscache", "");
          localContext = AlimmContext.getAliContext().getAppContext();
          if (paramJSONObject.has("tabs"))
          {
            localJSONArray2 = paramJSONObject.optJSONArray("tabs");
            if (!TextUtils.isEmpty(this.slotId))
              break label550;
          }
        }
        label550: for (String str4 = this.appkey; ; str4 = this.slotId)
        {
          if (!TextUtils.isEmpty(str4))
            TabsDiskCache.a(localContext, str4).a(localJSONArray2);
          JSONObject localJSONObject2 = paramJSONObject.optJSONObject("ecom");
          if (localJSONObject2 != null)
          {
            String str2 = localJSONObject2.optString("refpid");
            String str3 = localJSONObject2.optString("e");
            j.a().a(h.a(localJSONObject2.optJSONObject("wakelist")));
            ExchangeDataService.getVerInfo().a(str3);
            ExchangeDataService.getVerInfo().b(str2);
          }
          return;
          bool1 = false;
          break;
          bool2 = false;
          break label46;
          this.a = 0;
          this.b = -1L;
          break label307;
        }
      }
      catch (Exception localException)
      {
        break label258;
      }
    }
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    byte b1 = 1;
    super.writeToParcel(paramParcel, paramInt);
    int i;
    byte b2;
    label106: byte b3;
    if (this.module == null)
    {
      i = -1;
      paramParcel.writeInt(i);
      paramParcel.writeString(this.opensize);
      paramParcel.writeString(this.landing_image);
      paramParcel.writeString(this.landingUrl);
      paramParcel.writeString(this.new_image);
      paramParcel.writeInt(this.image_type);
      paramParcel.writeInt(this.displayStyle);
      paramParcel.writeString(this.displayType);
      paramParcel.writeInt(this.newTips);
      paramParcel.writeInt(this.cache);
      if (!this.filterInstalledApp)
        break label233;
      b2 = b1;
      paramParcel.writeByte(b2);
      if (!this.wallSwitch)
        break label239;
      b3 = b1;
      label122: paramParcel.writeByte(b3);
      paramParcel.writeInt(this.a);
      paramParcel.writeLong(this.b);
      if (!this.c)
        break label245;
    }
    while (true)
    {
      paramParcel.writeByte(b1);
      paramParcel.writeLong(this.expire);
      paramParcel.writeInt(this.sid_expired);
      paramParcel.writeString(this.entryStr);
      paramParcel.writeString(this.ispreload);
      paramParcel.writeString(this.iscache);
      paramParcel.writeString(this.landing_size);
      paramParcel.writeLongArray(this.timeline);
      paramParcel.writeMap(this.ecomparms);
      return;
      i = this.module.ordinal();
      break;
      label233: b2 = 0;
      break label106;
      label239: b3 = 0;
      break label122;
      label245: b1 = 0;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.net.MMEntity
 * JD-Core Version:    0.6.2
 */