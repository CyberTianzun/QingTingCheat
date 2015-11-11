package fm.qingting.qtradio.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import fm.qingting.framework.data.DataCommand;
import fm.qingting.framework.data.DataToken;
import fm.qingting.framework.data.IDataParser;
import fm.qingting.framework.data.IDataRecvHandler;
import fm.qingting.framework.data.IDataSource;
import fm.qingting.framework.data.IDataToken;
import fm.qingting.framework.data.ListData;
import fm.qingting.framework.data.Navigation;
import fm.qingting.framework.data.Result;
import fm.qingting.qtradio.model.SearchBasic;
import fm.qingting.qtradio.model.SearchChannel;
import fm.qingting.qtradio.model.SearchDJ;
import fm.qingting.qtradio.model.SearchOndemand;
import fm.qingting.qtradio.model.SearchOndemandProgram;
import fm.qingting.qtradio.model.SearchProgram;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SearchDS
  implements IDataSource
{
  private static SearchDS instance;
  private ArrayList<Object> temporaryListData;

  private DataToken doAddHistroyCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setData(new Result(true, null));
    ListData localListData = getHistroys();
    Object localObject1 = paramDataCommand.getParam().get("histroy");
    if (localObject1 == null)
      return localDataToken;
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(localObject1);
    Iterator localIterator = localListData.data.iterator();
    do
    {
      if (!localIterator.hasNext())
        break;
      Object localObject2 = localIterator.next();
      SearchBasic localSearchBasic1 = (SearchBasic)localObject2;
      SearchBasic localSearchBasic2 = (SearchBasic)localObject1;
      if ((!localSearchBasic1.getCid().equalsIgnoreCase(localSearchBasic2.getCid())) || (!localSearchBasic1.getCatid().equalsIgnoreCase(localSearchBasic2.getCatid())) || (!localSearchBasic1.getName().endsWith(localSearchBasic2.getName())))
        localArrayList.add(localObject2);
    }
    while (localArrayList.size() < 10);
    updateHistroy(localArrayList);
    return localDataToken;
  }

  private DataToken doGetHistroyCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setDataInfo(paramDataCommand);
    localDataToken.setData(new Result(true, getHistroys()));
    return localDataToken;
  }

  private DataToken doRemoveHistroyCommand(DataCommand paramDataCommand)
  {
    DataToken localDataToken = new DataToken();
    localDataToken.setData(new Result(true, null));
    ListData localListData = getHistroys();
    Object localObject = paramDataCommand.getParam().get("histroy");
    if (localObject == null)
      return localDataToken;
    List localList = localListData.data;
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      SearchBasic localSearchBasic1 = (SearchBasic)localIterator.next();
      SearchBasic localSearchBasic2 = (SearchBasic)localObject;
      if ((localSearchBasic1.getCid().equalsIgnoreCase(localSearchBasic2.getCid())) || (localSearchBasic1.getCatid().equalsIgnoreCase(localSearchBasic2.getCatid())) || (localSearchBasic1.getName().endsWith(localSearchBasic2.getName())))
        localIterator.remove();
    }
    updateHistroy(localList);
    localDataToken.setData(new Result(true, getHistroys()));
    return localDataToken;
  }

  private ListData getHistroys()
  {
    ArrayList localArrayList1;
    if (this.temporaryListData == null)
    {
      localArrayList1 = new ArrayList();
      Cursor localCursor = DBManager.getInstance().getReadableDB("searchhistroy").rawQuery("select type, data from histroylist order by id", null);
      Gson localGson = new Gson();
      while (true)
        if (localCursor.moveToNext())
        {
          String str1 = localCursor.getString(0);
          String str2 = localCursor.getString(1);
          try
          {
            if (str1.equalsIgnoreCase("SearchBasic"))
            {
              Object localObject3 = localGson.fromJson(str2, SearchBasic.class);
              localObject1 = localObject3;
            }
            while ((localObject1 != null) && (localObject1 != null))
            {
              localArrayList1.add(localObject1);
              break;
              if (str1.equalsIgnoreCase("SearchChannel"))
              {
                localObject1 = localGson.fromJson(str2, SearchChannel.class);
              }
              else if (str1.equalsIgnoreCase("SearchDJ"))
              {
                localObject1 = localGson.fromJson(str2, SearchDJ.class);
              }
              else if (str1.equalsIgnoreCase("SearchOndemand"))
              {
                localObject1 = localGson.fromJson(str2, SearchOndemand.class);
              }
              else if (str1.equalsIgnoreCase("SearchOndemandProgram"))
              {
                localObject1 = localGson.fromJson(str2, SearchOndemandProgram.class);
              }
              else
              {
                if (!str1.equalsIgnoreCase("SearchProgram"))
                  break label235;
                Object localObject2 = localGson.fromJson(str2, SearchProgram.class);
                localObject1 = localObject2;
              }
            }
          }
          catch (Exception localException)
          {
            while (true)
            {
              localException.printStackTrace();
              label235: Object localObject1 = null;
            }
          }
        }
      localCursor.close();
      this.temporaryListData = new ArrayList(localArrayList1);
    }
    for (ArrayList localArrayList2 = localArrayList1; ; localArrayList2 = new ArrayList(this.temporaryListData))
    {
      Navigation localNavigation = new Navigation();
      localNavigation.count = localArrayList2.size();
      localNavigation.page = 1;
      localNavigation.size = 2147483647;
      return new ListData(localArrayList2, localNavigation);
    }
  }

  public static SearchDS getInstance()
  {
    if (instance == null)
      instance = new SearchDS();
    return instance;
  }

  private String getSearchClass(Object paramObject)
  {
    String str = "";
    if ((paramObject instanceof SearchChannel))
      str = "SearchChannel";
    do
    {
      return str;
      if ((paramObject instanceof SearchProgram))
        return "SearchProgram";
      if ((paramObject instanceof SearchDJ))
        return "SearchDJ";
      if ((paramObject instanceof SearchOndemand))
        return "SearchOndemand";
      if ((paramObject instanceof SearchOndemandProgram))
        return "SearchOndemandProgram";
    }
    while (!(paramObject instanceof SearchBasic));
    return "SearchBasic";
  }

  private void updateHistroy(List<Object> paramList)
  {
    SQLiteDatabase localSQLiteDatabase = DBManager.getInstance().getWritableDB("searchhistroy");
    localSQLiteDatabase.beginTransaction();
    localSQLiteDatabase.execSQL("delete from histroylist");
    Iterator localIterator = paramList.iterator();
    Gson localGson = new Gson();
    for (int i = 1; localIterator.hasNext(); i++)
    {
      Object localObject = localIterator.next();
      String str = localGson.toJson(localObject);
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = Integer.valueOf(i);
      arrayOfObject[1] = str;
      arrayOfObject[2] = getSearchClass(localObject);
      localSQLiteDatabase.execSQL("insert into histroylist(id, data, type) values(?, ?, ?)", arrayOfObject);
    }
    localSQLiteDatabase.setTransactionSuccessful();
    localSQLiteDatabase.endTransaction();
    this.temporaryListData = new ArrayList(paramList);
  }

  public void addParser(IDataParser paramIDataParser)
  {
  }

  public String dataSourceName()
  {
    return "Search";
  }

  public IDataToken doCommand(DataCommand paramDataCommand, IDataRecvHandler paramIDataRecvHandler)
  {
    String str = paramDataCommand.getCurrentCommand();
    if (str.equalsIgnoreCase("getSearchHistroy"))
      return doGetHistroyCommand(paramDataCommand);
    if (str.equalsIgnoreCase("addSearchHistroy"))
      return doAddHistroyCommand(paramDataCommand);
    if (str.equalsIgnoreCase("deleteSearchHistroy"))
      return doRemoveHistroyCommand(paramDataCommand);
    return null;
  }

  public boolean isSynchronous(String paramString, Map<String, Object> paramMap)
  {
    return true;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.data.SearchDS
 * JD-Core Version:    0.6.2
 */