package master.flame.danmaku.danmaku.parser.android;

import android.text.TextUtils;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Locale;
import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class BiliDanmukuParser extends BaseDanmakuParser
{
  private float mDispScaleX;
  private float mDispScaleY;

  static
  {
    System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
  }

  public Danmakus parse()
  {
    AndroidFileSource localAndroidFileSource;
    if (this.mDataSource != null)
      localAndroidFileSource = (AndroidFileSource)this.mDataSource;
    try
    {
      XMLReader localXMLReader = XMLReaderFactory.createXMLReader();
      XmlContentHandler localXmlContentHandler = new XmlContentHandler();
      localXMLReader.setContentHandler(localXmlContentHandler);
      localXMLReader.parse(new InputSource(localAndroidFileSource.data()));
      Danmakus localDanmakus = localXmlContentHandler.getResult();
      return localDanmakus;
    }
    catch (SAXException localSAXException)
    {
      localSAXException.printStackTrace();
      return null;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public BaseDanmakuParser setDisplayer(IDisplayer paramIDisplayer)
  {
    super.setDisplayer(paramIDisplayer);
    this.mDispScaleX = (this.mDispWidth / 682.0F);
    this.mDispScaleY = (this.mDispHeight / 438.0F);
    return this;
  }

  public class XmlContentHandler extends DefaultHandler
  {
    private static final String TRUE_STRING = "true";
    public boolean completed = false;
    public int index = 0;
    public BaseDanmaku item = null;
    public Danmakus result = null;

    public XmlContentHandler()
    {
    }

    private String decodeXmlString(String paramString)
    {
      if (paramString.contains("&amp;"))
        paramString = paramString.replace("&amp;", "&");
      if (paramString.contains("&quot;"))
        paramString = paramString.replace("&quot;", "\"");
      if (paramString.contains("&gt;"))
        paramString = paramString.replace("&gt;", ">");
      if (paramString.contains("&lt;"))
        paramString = paramString.replace("&lt;", "<");
      return paramString;
    }

    public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str;
      String[] arrayOfString1;
      if (this.item != null)
      {
        DanmakuFactory.fillText(this.item, decodeXmlString(new String(paramArrayOfChar, paramInt1, paramInt2)));
        BaseDanmaku localBaseDanmaku1 = this.item;
        int i = this.index;
        this.index = (i + 1);
        localBaseDanmaku1.index = i;
        str = this.item.text.trim();
        if ((this.item.getType() == 7) && (str.startsWith("[")) && (str.endsWith("]")))
          arrayOfString1 = null;
      }
      String[] arrayOfString2;
      float f1;
      float f2;
      String[] arrayOfString3;
      int j;
      try
      {
        JSONArray localJSONArray = new JSONArray(str);
        arrayOfString1 = new String[localJSONArray.length()];
        for (int n = 0; n < arrayOfString1.length; n++)
          arrayOfString1[n] = localJSONArray.getString(n);
        arrayOfString2 = arrayOfString1;
        if ((arrayOfString2 == null) || (arrayOfString2.length < 5))
        {
          this.item = null;
          return;
        }
      }
      catch (JSONException localJSONException)
      {
        while (true)
        {
          localJSONException.printStackTrace();
          arrayOfString2 = arrayOfString1;
        }
        this.item.text = arrayOfString2[4];
        f1 = Float.parseFloat(arrayOfString2[0]);
        f2 = Float.parseFloat(arrayOfString2[1]);
        arrayOfString3 = arrayOfString2[2].split("-");
        j = (int)(AlphaValue.MAX * Float.parseFloat(arrayOfString3[0]));
        if (arrayOfString3.length <= 1);
      }
      for (int k = (int)(AlphaValue.MAX * Float.parseFloat(arrayOfString3[1])); ; k = j)
      {
        long l1 = ()(1000.0F * Float.parseFloat(arrayOfString2[3]));
        long l2 = 0L;
        float f3;
        if (arrayOfString2.length >= 7)
          f3 = Float.parseFloat(arrayOfString2[5]);
        for (float f4 = Float.parseFloat(arrayOfString2[6]); ; f4 = 0.0F)
        {
          float f7;
          float f8;
          long l4;
          label360: float f5;
          float f6;
          long l3;
          if (arrayOfString2.length >= 11)
          {
            f7 = Float.parseFloat(arrayOfString2[7]);
            f8 = Float.parseFloat(arrayOfString2[8]);
            if (!"".equals(arrayOfString2[9]))
            {
              l4 = Integer.parseInt(arrayOfString2[9]);
              if (!"".equals(arrayOfString2[10]))
              {
                l2 = ()Float.parseFloat(arrayOfString2[10]);
                long l6 = l4;
                f5 = f8;
                f6 = f7;
                l3 = l6;
              }
            }
          }
          while (true)
          {
            BaseDanmaku localBaseDanmaku2 = this.item;
            Duration localDuration = new Duration(l1);
            localBaseDanmaku2.duration = localDuration;
            this.item.rotationZ = f3;
            this.item.rotationY = f4;
            DanmakuFactory.fillTranslationData(this.item, f1, f2, f6, f5, l3, l2, BiliDanmukuParser.this.mDispScaleX, BiliDanmukuParser.this.mDispScaleY);
            DanmakuFactory.fillAlphaData(this.item, j, k, l1);
            if ((arrayOfString2.length >= 12) && (!TextUtils.isEmpty(arrayOfString2[11])) && ("true".equals(arrayOfString2[11])))
              this.item.textShadowColor = 0;
            if ((arrayOfString2.length >= 13) && ((arrayOfString2.length >= 14) && ((arrayOfString2.length < 15) || ("".equals(arrayOfString2[14])))))
              break;
            String[] arrayOfString4 = arrayOfString2[14].substring(1).split("L");
            if ((arrayOfString4 == null) || (arrayOfString4.length <= 0))
              break;
            int[] arrayOfInt = { arrayOfString4.length, 2 };
            float[][] arrayOfFloat = (float[][])Array.newInstance(Float.TYPE, arrayOfInt);
            for (int m = 0; m < arrayOfString4.length; m++)
            {
              String[] arrayOfString5 = arrayOfString4[m].split(",");
              arrayOfFloat[m][0] = Float.parseFloat(arrayOfString5[0]);
              arrayOfFloat[m][1] = Float.parseFloat(arrayOfString5[1]);
            }
            DanmakuFactory.fillLinePathData(this.item, arrayOfFloat, BiliDanmukuParser.this.mDispScaleX, BiliDanmukuParser.this.mDispScaleY);
            return;
            long l5 = l4;
            f5 = f8;
            f6 = f7;
            l3 = l5;
            continue;
            l4 = l1;
            break label360;
            l3 = l1;
            f5 = f2;
            f6 = f1;
          }
          f3 = 0.0F;
        }
      }
    }

    public void endDocument()
      throws SAXException
    {
      this.completed = true;
    }

    public void endElement(String paramString1, String paramString2, String paramString3)
      throws SAXException
    {
      if (this.item != null)
        if (this.item.duration != null)
          if (paramString2.length() == 0)
            break label65;
      while (true)
      {
        if (paramString2.equalsIgnoreCase("d"))
        {
          this.item.setTimer(BiliDanmukuParser.this.mTimer);
          this.result.addItem(this.item);
        }
        this.item = null;
        return;
        label65: paramString2 = paramString3;
      }
    }

    public Danmakus getResult()
    {
      return this.result;
    }

    public void startDocument()
      throws SAXException
    {
      this.result = new Danmakus();
    }

    public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
      throws SAXException
    {
      int i = -16777216;
      if (paramString2.length() != 0);
      while (true)
      {
        if (paramString2.toLowerCase(Locale.getDefault()).trim().equals("d"))
        {
          String[] arrayOfString = paramAttributes.getValue("p").split(",");
          if (arrayOfString.length > 0)
          {
            long l = ()(1000.0F * Float.parseFloat(arrayOfString[0]));
            int j = Integer.parseInt(arrayOfString[1]);
            float f = Float.parseFloat(arrayOfString[2]);
            int k = i | Integer.parseInt(arrayOfString[3]);
            this.item = DanmakuFactory.createDanmaku(j, BiliDanmukuParser.this.mDisp);
            if (this.item != null)
            {
              this.item.time = l;
              this.item.textSize = (f * (BiliDanmukuParser.this.mDispDensity - 0.6F));
              this.item.textColor = -11908534;
              BaseDanmaku localBaseDanmaku = this.item;
              if (k <= i)
                i = -1;
              localBaseDanmaku.textShadowColor = i;
            }
          }
        }
        return;
        paramString2 = paramString3;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser
 * JD-Core Version:    0.6.2
 */