package com.taobao.munion.base.download;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.taobao.munion.base.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class e
{
  private static final String a = e.class.getName();
  private static final String b = "umeng_download_task_list";
  private static final String c = "UMENG_DATA";
  private static final String d = "cp";
  private static final String e = "url";
  private static final String f = "progress";
  private static final String g = "last_modified";
  private static final String h = "extra";
  private static Context i;
  private static final String j = "yyyy-MM-dd HH:mm:ss";
  private a k = new a(i);

  public static e a(Context paramContext)
  {
    if ((i == null) && (paramContext == null))
      throw new NullPointerException();
    if (i == null)
      i = paramContext;
    return b.a;
  }

  public List<String> a(String paramString)
  {
    String[] arrayOfString = { paramString };
    Cursor localCursor = this.k.getReadableDatabase().query("umeng_download_task_list", new String[] { "url" }, "cp=?", arrayOfString, null, null, null, "1");
    ArrayList localArrayList = new ArrayList();
    localCursor.moveToFirst();
    while (!localCursor.isAfterLast())
    {
      localArrayList.add(localCursor.getString(0));
      localCursor.moveToNext();
    }
    localCursor.close();
    return localArrayList;
  }

  public void a(int paramInt)
  {
    try
    {
      Date localDate = new Date(new Date().getTime() - paramInt * 1000);
      String str2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate);
      String str3 = " DELETE FROM umeng_download_task_list WHERE strftime('yyyy-MM-dd HH:mm:ss', last_modified)<=strftime('yyyy-MM-dd HH:mm:ss', '" + str2 + "')";
      this.k.getWritableDatabase().execSQL(str3);
      Log.d("clearOverdueTasks(" + paramInt + ")" + " remove all tasks before " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate), new Object[0]);
      return;
    }
    catch (Exception localException)
    {
      String str1 = a;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localException.getMessage();
      Log.e(str1, arrayOfObject);
    }
  }

  public void a(String paramString1, String paramString2, int paramInt)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("progress", Integer.valueOf(paramInt));
    localContentValues.put("last_modified", f.a());
    String[] arrayOfString = { paramString1, paramString2 };
    this.k.getWritableDatabase().update("umeng_download_task_list", localContentValues, "cp=? and url=?", arrayOfString);
    Log.d("updateProgress(" + paramString1 + ", " + paramString2 + ", " + paramInt + ")", new Object[0]);
  }

  public void a(String paramString1, String paramString2, String paramString3)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("extra", paramString3);
    localContentValues.put("last_modified", f.a());
    String[] arrayOfString = { paramString1, paramString2 };
    this.k.getWritableDatabase().update("umeng_download_task_list", localContentValues, "cp=? and url=?", arrayOfString);
    Log.d("updateExtra(" + paramString1 + ", " + paramString2 + ", " + paramString3 + ")", new Object[0]);
  }

  // ERROR //
  public boolean a(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 176	android/content/ContentValues
    //   3: dup
    //   4: invokespecial 177	android/content/ContentValues:<init>	()V
    //   7: astore_3
    //   8: aload_3
    //   9: ldc 15
    //   11: aload_1
    //   12: invokevirtual 194	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   15: aload_3
    //   16: ldc 21
    //   18: iconst_0
    //   19: invokestatic 183	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   22: invokevirtual 187	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   25: aload_3
    //   26: ldc 24
    //   28: invokestatic 191	com/taobao/munion/base/download/f:a	()Ljava/lang/String;
    //   31: invokevirtual 194	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   34: iconst_2
    //   35: anewarray 70	java/lang/String
    //   38: dup
    //   39: iconst_0
    //   40: aload_1
    //   41: aastore
    //   42: dup
    //   43: iconst_1
    //   44: aload_2
    //   45: aastore
    //   46: astore 4
    //   48: aload_0
    //   49: getfield 56	com/taobao/munion/base/download/e:k	Lcom/taobao/munion/base/download/e$a;
    //   52: invokevirtual 74	com/taobao/munion/base/download/e$a:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   55: ldc 9
    //   57: iconst_1
    //   58: anewarray 70	java/lang/String
    //   61: dup
    //   62: iconst_0
    //   63: ldc 21
    //   65: aastore
    //   66: ldc 196
    //   68: aload 4
    //   70: aconst_null
    //   71: aconst_null
    //   72: aconst_null
    //   73: ldc 78
    //   75: invokevirtual 84	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   78: astore 8
    //   80: aload 8
    //   82: invokeinterface 212 1 0
    //   87: ifle +61 -> 148
    //   90: new 136	java/lang/StringBuilder
    //   93: dup
    //   94: invokespecial 137	java/lang/StringBuilder:<init>	()V
    //   97: ldc 214
    //   99: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: aload_1
    //   103: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   106: ldc 204
    //   108: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: aload_2
    //   112: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: ldc 216
    //   117: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   120: ldc 218
    //   122: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   125: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   128: iconst_0
    //   129: anewarray 4	java/lang/Object
    //   132: invokestatic 168	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   135: iconst_0
    //   136: istore 7
    //   138: aload 8
    //   140: invokeinterface 112 1 0
    //   145: iload 7
    //   147: ireturn
    //   148: aload_0
    //   149: getfield 56	com/taobao/munion/base/download/e:k	Lcom/taobao/munion/base/download/e$a;
    //   152: invokevirtual 151	com/taobao/munion/base/download/e$a:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   155: ldc 9
    //   157: aconst_null
    //   158: aload_3
    //   159: invokevirtual 222	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   162: lstore 9
    //   164: lload 9
    //   166: ldc2_w 223
    //   169: lcmp
    //   170: ifne +63 -> 233
    //   173: iconst_0
    //   174: istore 11
    //   176: new 136	java/lang/StringBuilder
    //   179: dup
    //   180: invokespecial 137	java/lang/StringBuilder:<init>	()V
    //   183: ldc 214
    //   185: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: aload_1
    //   189: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: ldc 204
    //   194: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: aload_2
    //   198: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   201: ldc 216
    //   203: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   206: ldc 226
    //   208: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   211: lload 9
    //   213: invokevirtual 229	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   216: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   219: iconst_0
    //   220: anewarray 4	java/lang/Object
    //   223: invokestatic 168	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   226: iload 11
    //   228: istore 7
    //   230: goto -92 -> 138
    //   233: iconst_1
    //   234: istore 11
    //   236: goto -60 -> 176
    //   239: astore 5
    //   241: aload 5
    //   243: astore 6
    //   245: iconst_0
    //   246: istore 7
    //   248: new 136	java/lang/StringBuilder
    //   251: dup
    //   252: invokespecial 137	java/lang/StringBuilder:<init>	()V
    //   255: ldc 214
    //   257: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   260: aload_1
    //   261: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   264: ldc 204
    //   266: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: aload_2
    //   270: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   273: ldc 216
    //   275: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   278: aload 6
    //   280: invokevirtual 171	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   283: invokevirtual 143	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   286: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   289: iconst_1
    //   290: anewarray 4	java/lang/Object
    //   293: dup
    //   294: iconst_0
    //   295: aload 6
    //   297: aastore
    //   298: invokestatic 168	com/taobao/munion/base/Log:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   301: iload 7
    //   303: ireturn
    //   304: astore 12
    //   306: iload 11
    //   308: istore 7
    //   310: aload 12
    //   312: astore 6
    //   314: goto -66 -> 248
    //   317: astore 6
    //   319: goto -71 -> 248
    //
    // Exception table:
    //   from	to	target	type
    //   34	135	239	java/lang/Exception
    //   148	164	239	java/lang/Exception
    //   176	226	304	java/lang/Exception
    //   138	145	317	java/lang/Exception
  }

  public int b(String paramString1, String paramString2)
  {
    String[] arrayOfString = { paramString1, paramString2 };
    Cursor localCursor = this.k.getReadableDatabase().query("umeng_download_task_list", new String[] { "progress" }, "cp=? and url=?", arrayOfString, null, null, null, "1");
    if (localCursor.getCount() > 0)
      localCursor.moveToFirst();
    for (int m = localCursor.getInt(0); ; m = -1)
    {
      localCursor.close();
      return m;
    }
  }

  public String c(String paramString1, String paramString2)
  {
    String[] arrayOfString = { paramString1, paramString2 };
    Cursor localCursor = this.k.getReadableDatabase().query("umeng_download_task_list", new String[] { "extra" }, "cp=? and url=?", arrayOfString, null, null, null, "1");
    int m = localCursor.getCount();
    String str = null;
    if (m > 0)
    {
      localCursor.moveToFirst();
      str = localCursor.getString(0);
    }
    localCursor.close();
    return str;
  }

  public Date d(String paramString1, String paramString2)
  {
    String[] arrayOfString = { paramString1, paramString2 };
    Cursor localCursor = this.k.getReadableDatabase().query("umeng_download_task_list", new String[] { "last_modified" }, "cp=? and url=?", arrayOfString, null, null, null, null);
    int m = localCursor.getCount();
    Object localObject = null;
    String str;
    if (m > 0)
    {
      localCursor.moveToFirst();
      str = localCursor.getString(0);
      Log.d("getLastModified(" + paramString1 + ", " + paramString2 + "): " + str, new Object[0]);
    }
    try
    {
      Date localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
      localObject = localDate;
      localCursor.close();
      return localObject;
    }
    catch (Exception localException)
    {
      while (true)
      {
        Log.d(localException.getMessage(), new Object[0]);
        localObject = null;
      }
    }
  }

  public void e(String paramString1, String paramString2)
  {
    String[] arrayOfString = { paramString1, paramString2 };
    this.k.getWritableDatabase().delete("umeng_download_task_list", "cp=? and url=?", arrayOfString);
    Log.d("delete(" + paramString1 + ", " + paramString2 + ")", new Object[0]);
  }

  public void finalize()
  {
    this.k.close();
  }

  class a extends SQLiteOpenHelper
  {
    private static final int b = 2;
    private static final String c = "CREATE TABLE umeng_download_task_list (cp TEXT, url TEXT, progress INTEGER, extra TEXT, last_modified TEXT, UNIQUE (cp,url) ON CONFLICT ABORT);";

    a(Context arg2)
    {
      super("UMENG_DATA", null, 2);
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      Log.d("CREATE TABLE umeng_download_task_list (cp TEXT, url TEXT, progress INTEGER, extra TEXT, last_modified TEXT, UNIQUE (cp,url) ON CONFLICT ABORT);", new Object[0]);
      paramSQLiteDatabase.execSQL("CREATE TABLE umeng_download_task_list (cp TEXT, url TEXT, progress INTEGER, extra TEXT, last_modified TEXT, UNIQUE (cp,url) ON CONFLICT ABORT);");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
    }
  }

  private static class b
  {
    public static final e a = new e(null);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.munion.base.download.e
 * JD-Core Version:    0.6.2
 */