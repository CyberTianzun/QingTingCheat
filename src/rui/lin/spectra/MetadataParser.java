package rui.lin.spectra;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class MetadataParser
{
  public static final HashMap<String, String> ID3V2_TAGS = new HashMap()
  {
  };
  protected static int sLogLevel = 4;
  protected static String sLogTag = "MetadataParser";
  protected static Logger sLogger = new Logger(sLogLevel, sLogTag);

  public static HashMap<String, Vector<String>> parseID3v2(byte[] paramArrayOfByte)
  {
    int i = 1;
    if (paramArrayOfByte != null);
    int j;
    int k;
    label136: int i1;
    int i2;
    label188: byte[] arrayOfByte;
    HashMap localHashMap;
    int i3;
    label275: String str1;
    int i4;
    label484: Charset localCharset2;
    while (true)
    {
      try
      {
        if ((paramArrayOfByte[0] == 73) && (paramArrayOfByte[1] == 68) && (paramArrayOfByte[2] == 51))
        {
          sLogger.info("ID3v2 mark detected", new Object[0]);
          if ((paramArrayOfByte[3] >= 3) && (paramArrayOfByte[3] <= 4))
          {
            Logger localLogger1 = sLogger;
            Object[] arrayOfObject1 = new Object[1];
            arrayOfObject1[0] = Byte.valueOf(paramArrayOfByte[3]);
            localLogger1.info("ID3v2 version %d", arrayOfObject1);
            j = paramArrayOfByte[6] << 21 | paramArrayOfByte[7] << 14 | paramArrayOfByte[8] << 7 | paramArrayOfByte[9];
            if ((0x80 & paramArrayOfByte[5]) == 0)
              break label1034;
            k = i;
            if ((0x40 & paramArrayOfByte[5]) == 0)
              break label1040;
            if (i == 0)
              break label1045;
            int m = paramArrayOfByte[10] << 21 | paramArrayOfByte[11] << 14 | paramArrayOfByte[12] << 7 | paramArrayOfByte[13];
            int n = m + 10;
            i1 = j - m;
            i2 = n;
            arrayOfByte = new byte[i1];
            if (k != 0)
            {
              int i7 = 0;
              if (i2 < i1)
              {
                if ((paramArrayOfByte[i2] == 0) && (paramArrayOfByte[(i2 - 1)] == 255))
                {
                  i2++;
                  continue;
                }
                arrayOfByte[i7] = paramArrayOfByte[i2];
                i7++;
                i2++;
                continue;
              }
            }
            else
            {
              System.arraycopy(paramArrayOfByte, i2, arrayOfByte, 0, i1);
            }
            localHashMap = new HashMap();
            i3 = 0;
            if ((i1 - i3 <= 10) || (arrayOfByte[i3] < 65) || (arrayOfByte[i3] > 90))
              break;
            str1 = new String(arrayOfByte, i3, 4);
            i4 = arrayOfByte[(i3 + 4)] << 21 | arrayOfByte[(i3 + 5)] << 14 | arrayOfByte[(i3 + 6)] << 7 | arrayOfByte[(i3 + 7)];
            Logger localLogger2 = sLogger;
            Object[] arrayOfObject2 = new Object[2];
            arrayOfObject2[0] = str1;
            arrayOfObject2[1] = Integer.valueOf(i4);
            localLogger2.info("TAG %s found with %d byte payload", arrayOfObject2);
            if (!ID3V2_TAGS.containsKey(str1))
              break label1056;
            switch (arrayOfByte[i3])
            {
            case 84:
              switch (arrayOfByte[(i3 + 10)])
              {
              case 0:
                if (localCharset2 == null)
                  break label1056;
                if (!str1.equals("TXXX"))
                  break label641;
                String[] arrayOfString3 = localCharset2.decode(ByteBuffer.wrap(arrayOfByte, i3 + 11, i4 - 1)).toString().split("");
                localObject1 = (String)ID3V2_TAGS.get(str1) + arrayOfString3[0];
                localObject2 = new Vector(1);
                ((Vector)localObject2).add(arrayOfString3[1]);
                localHashMap.put(localObject1, localObject2);
              case 1:
              case 2:
              case 3:
              }
              break;
            case 87:
            case 85:
            case 86:
            }
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return null;
      localCharset2 = Charset.forName("ISO-8859-1");
      continue;
      localCharset2 = Charset.forName("UTF-16");
      continue;
      localCharset2 = Charset.forName("UTF-16BE");
      continue;
      localCharset2 = Charset.forName("UTF-8");
      continue;
      label641: String[] arrayOfString2 = localCharset2.decode(ByteBuffer.wrap(arrayOfByte, i3 + 11, i4 - 1)).toString().split("");
      String str6 = (String)ID3V2_TAGS.get(str1);
      Vector localVector3 = new Vector(Arrays.asList(arrayOfString2));
      Object localObject1 = str6;
      Object localObject2 = localVector3;
    }
    if (str1.equals("WXXX"))
      switch (arrayOfByte[(i3 + 10)])
      {
      case 0:
      case 1:
      case 2:
      case 3:
      }
    while (true)
    {
      if (localCharset1 != null)
      {
        String str3 = localCharset1.decode(ByteBuffer.wrap(arrayOfByte, i3 + 11, i4 - 1)).toString();
        int i5 = str3.indexOf("");
        String str4 = str3.substring(0, i5);
        int i6 = localCharset1.encode(str3.substring(0, i5 + 1)).limit();
        String str5 = (String)ID3V2_TAGS.get(str1) + str4;
        Vector localVector2 = new Vector(1);
        localVector2.add(Charset.forName("ISO-8859-1").decode(ByteBuffer.wrap(arrayOfByte, i6 + (i3 + 11), i4 - 1 - i6)).toString());
        localHashMap.put(str5, localVector2);
        break label1056;
        localCharset1 = Charset.forName("ISO-8859-1");
        continue;
        localCharset1 = Charset.forName("UTF-16");
        continue;
        localCharset1 = Charset.forName("UTF-16BE");
        continue;
        localCharset1 = Charset.forName("UTF-8");
        continue;
        String[] arrayOfString1 = Charset.forName("ISO-8859-1").decode(ByteBuffer.wrap(arrayOfByte, i3 + 10, i4)).toString().split("");
        String str2 = (String)ID3V2_TAGS.get(str1);
        Vector localVector1 = new Vector(1);
        localVector1.add(arrayOfString1[0]);
        localHashMap.put(str2, localVector1);
        break label1056;
        return localHashMap;
        label1034: k = 0;
        break;
        label1040: i = 0;
        break label136;
        label1045: i1 = j;
        i2 = 10;
        break label188;
      }
      label1056: i3 += i4 + 10;
      break label275;
      localCharset2 = null;
      break label484;
      Charset localCharset1 = null;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     rui.lin.spectra.MetadataParser
 * JD-Core Version:    0.6.2
 */