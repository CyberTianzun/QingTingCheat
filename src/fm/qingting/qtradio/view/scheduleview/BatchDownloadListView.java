package fm.qingting.qtradio.view.scheduleview;

import android.content.Context;
import android.widget.Toast;
import fm.qingting.download.QTRadioDownloadAgent;
import fm.qingting.framework.adapter.IAdapterIViewFactory;
import fm.qingting.framework.adapter.ItemParam;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.qtradio.model.DownLoadInfoNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.MutiCheckAdapter;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.view.LoadMoreListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BatchDownloadListView extends LoadMoreListView
  implements IEventHandler
{
  private MutiCheckAdapter adapter = new MutiCheckAdapter(new ArrayList(), this.factory);
  private IAdapterIViewFactory factory = new IAdapterIViewFactory()
  {
    public IView createView(int paramAnonymousInt)
    {
      return new BatchDownloadItemView(BatchDownloadListView.this.getContext(), this.val$hash);
    }
  };

  public BatchDownloadListView(Context paramContext)
  {
    super(paramContext);
    this.adapter.setEventHandler(this);
    setVerticalScrollBarEnabled(false);
    setVerticalFadingEdgeEnabled(false);
    setCacheColorHint(0);
    setDivider(null);
    setHeaderDividersEnabled(false);
    setSelector(17170445);
    setAdapter(this.adapter);
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public Object getValue(String paramString, Object paramObject)
  {
    Iterator localIterator;
    List localList;
    if (paramString.equalsIgnoreCase("getSizeInfo"))
    {
      localIterator = this.adapter.getCheckList();
      localList = this.adapter.getData();
      if ((localIterator != null) && (localList != null))
        break label239;
      return null;
    }
    while (true)
    {
      int i2;
      int m;
      if (localIterator.hasNext())
      {
        int k = ((Integer)localIterator.next()).intValue();
        if ((k < 0) || (k >= localList.size()))
          break label228;
        Node localNode = (Node)localList.get(k);
        if (!localNode.nodeName.equalsIgnoreCase("program"))
          break label228;
        ProgramNode localProgramNode = (ProgramNode)localNode;
        if (InfoManager.getInstance().root().mDownLoadInfoNode.hasDownLoad(localProgramNode) != 0)
          break label228;
        int i1 = localProgramNode.getDuration();
        if (i1 <= 0)
          break label228;
        i2 = j + 1;
        m = i + 125 * (i1 * 24);
      }
      for (int n = i2; ; n = j)
      {
        j = n;
        i = m;
        break;
        if ((j > 0) && (i > 0))
        {
          SizeInfo localSizeInfo = new SizeInfo();
          localSizeInfo.mCnt = j;
          localSizeInfo.mFileSize = i;
          localSizeInfo.mSizeString = SizeInfo.getFileSize(i);
          return localSizeInfo;
        }
        return super.getValue(paramString, paramObject);
        label228: m = i;
      }
      label239: int i = 0;
      int j = 0;
    }
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("itemCallback"))
    {
      i = ((ItemParam)paramObject2).position;
      this.adapter.checkIndex(i);
    }
    while (!paramString.equalsIgnoreCase("stateChanged"))
    {
      int i;
      return;
    }
    dispatchActionEvent(paramString, null);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("refreshList"))
      this.adapter.notifyDataSetChanged();
    do
    {
      Iterator localIterator;
      List localList1;
      do
      {
        List localList2;
        do
        {
          int m;
          do
          {
            do
            {
              return;
              if (!paramString.equalsIgnoreCase("setData"))
                break;
            }
            while (paramObject == null);
            List localList3 = (List)paramObject;
            this.adapter.setData(localList3);
            return;
            if (!paramString.equalsIgnoreCase("setIndex"))
              break;
            m = ((Integer)paramObject).intValue();
          }
          while ((m == -1) || (m >= this.adapter.getCount()));
          setSelection(m);
          this.adapter.checkIndex(m);
          return;
          if (!paramString.equalsIgnoreCase("checklist"))
            break;
          localList2 = (List)paramObject;
        }
        while (localList2.size() <= 0);
        setSelection(((Integer)localList2.get(0)).intValue());
        this.adapter.checkIndexs(localList2);
        return;
        if (!paramString.equalsIgnoreCase("startDownload"))
          break;
        localIterator = this.adapter.getCheckList();
        localList1 = this.adapter.getData();
      }
      while ((localIterator == null) || (localList1 == null));
      if (!InfoManager.getInstance().root().mDownLoadInfoNode.isSDCardAvailable())
      {
        Toast.makeText(getContext(), "无法开始下载，请检查您的SD卡", 0).show();
        return;
      }
      String str = QTRadioDownloadAgent.getInstance().getDownLoadPath();
      long l = QTRadioDownloadAgent.getInstance().getAvailableExternalMemorySize(str);
      ArrayList localArrayList = new ArrayList();
      int i = 0;
      while (localIterator.hasNext())
      {
        int k = ((Integer)localIterator.next()).intValue();
        if ((k >= 0) && (k < localList1.size()))
        {
          ProgramNode localProgramNode = (ProgramNode)localList1.get(k);
          if (InfoManager.getInstance().root().mDownLoadInfoNode.hasDownLoad(localProgramNode) == 0)
          {
            i += 125 * (24 * localProgramNode.getDuration());
            if (i > l)
            {
              Toast.makeText(getContext(), "存储空间不足，请重新选择下载", 0).show();
              return;
            }
            localArrayList.add(Integer.valueOf(k));
          }
        }
      }
      Collections.sort(localArrayList);
      for (int j = 0; j < localArrayList.size(); j++)
        InfoManager.getInstance().root().mDownLoadInfoNode.addToDownloadList((Node)localList1.get(((Integer)localArrayList.get(j)).intValue()));
      Toast.makeText(getContext(), "开始下载...", 0).show();
      this.adapter.resetCheck();
      return;
    }
    while (!paramString.equalsIgnoreCase("selectAll"));
    if (((Boolean)paramObject).booleanValue())
    {
      this.adapter.checkAll();
      return;
    }
    this.adapter.resetCheck();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.scheduleview.BatchDownloadListView
 * JD-Core Version:    0.6.2
 */