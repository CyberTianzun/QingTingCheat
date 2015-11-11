package org.android.agoo.service;

import android.os.RemoteException;

class ElectionService$1 extends IElectionService.Stub
{
  ElectionService$1(ElectionService paramElectionService)
  {
  }

  public void election(String paramString1, long paramLong, String paramString2)
    throws RemoteException
  {
    if (ElectionService.a(this.a))
      return;
    ElectionService.a(this.a, paramString1, paramLong);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.service.ElectionService.1
 * JD-Core Version:    0.6.2
 */