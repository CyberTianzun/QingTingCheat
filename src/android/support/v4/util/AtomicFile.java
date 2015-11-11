package android.support.v4.util;

import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile
{
  private final File mBackupName;
  private final File mBaseName;

  public AtomicFile(File paramFile)
  {
    this.mBaseName = paramFile;
    this.mBackupName = new File(paramFile.getPath() + ".bak");
  }

  static boolean sync(FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream != null);
    try
    {
      paramFileOutputStream.getFD().sync();
      return true;
    }
    catch (IOException localIOException)
    {
    }
    return false;
  }

  public void delete()
  {
    this.mBaseName.delete();
    this.mBackupName.delete();
  }

  public void failWrite(FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream != null)
      sync(paramFileOutputStream);
    try
    {
      paramFileOutputStream.close();
      this.mBaseName.delete();
      this.mBackupName.renameTo(this.mBaseName);
      return;
    }
    catch (IOException localIOException)
    {
      Log.w("AtomicFile", "failWrite: Got exception:", localIOException);
    }
  }

  public void finishWrite(FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream != null)
      sync(paramFileOutputStream);
    try
    {
      paramFileOutputStream.close();
      this.mBackupName.delete();
      return;
    }
    catch (IOException localIOException)
    {
      Log.w("AtomicFile", "finishWrite: Got exception:", localIOException);
    }
  }

  public File getBaseFile()
  {
    return this.mBaseName;
  }

  public FileInputStream openRead()
    throws FileNotFoundException
  {
    if (this.mBackupName.exists())
    {
      this.mBaseName.delete();
      this.mBackupName.renameTo(this.mBaseName);
    }
    return new FileInputStream(this.mBaseName);
  }

  public byte[] readFully()
    throws IOException
  {
    FileInputStream localFileInputStream = openRead();
    int i = 0;
    try
    {
      Object localObject2 = new byte[localFileInputStream.available()];
      while (true)
      {
        int j = localFileInputStream.read((byte[])localObject2, i, localObject2.length - i);
        if (j <= 0)
          return localObject2;
        i += j;
        int k = localFileInputStream.available();
        if (k > localObject2.length - i)
        {
          byte[] arrayOfByte = new byte[i + k];
          System.arraycopy(localObject2, 0, arrayOfByte, 0, i);
          localObject2 = arrayOfByte;
        }
      }
    }
    finally
    {
      localFileInputStream.close();
    }
  }

  public FileOutputStream startWrite()
    throws IOException
  {
    if (this.mBaseName.exists())
    {
      if (this.mBackupName.exists())
        break label88;
      if (!this.mBaseName.renameTo(this.mBackupName))
        Log.w("AtomicFile", "Couldn't rename file " + this.mBaseName + " to backup file " + this.mBackupName);
    }
    try
    {
      while (true)
      {
        FileOutputStream localFileOutputStream1 = new FileOutputStream(this.mBaseName);
        return localFileOutputStream1;
        label88: this.mBaseName.delete();
      }
    }
    catch (FileNotFoundException localFileNotFoundException1)
    {
      if (!this.mBaseName.getParentFile().mkdir())
        throw new IOException("Couldn't create directory " + this.mBaseName);
      try
      {
        FileOutputStream localFileOutputStream2 = new FileOutputStream(this.mBaseName);
        return localFileOutputStream2;
      }
      catch (FileNotFoundException localFileNotFoundException2)
      {
      }
    }
    throw new IOException("Couldn't create " + this.mBaseName);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     android.support.v4.util.AtomicFile
 * JD-Core Version:    0.6.2
 */