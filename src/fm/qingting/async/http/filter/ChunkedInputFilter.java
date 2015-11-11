package fm.qingting.async.http.filter;

import fm.qingting.async.ByteBufferList;
import fm.qingting.async.DataEmitter;
import fm.qingting.async.FilteredDataEmitter;
import fm.qingting.async.Util;

public class ChunkedInputFilter extends FilteredDataEmitter
{
  private int mChunkLength = 0;
  private int mChunkLengthRemaining = 0;
  private State mState = State.CHUNK_LEN;

  static
  {
    if (!ChunkedInputFilter.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  private boolean checkByte(char paramChar1, char paramChar2)
  {
    if (paramChar1 != paramChar2)
    {
      report(new Exception(paramChar2 + " was expeceted, got " + paramChar1));
      return false;
    }
    return true;
  }

  private boolean checkCR(char paramChar)
  {
    return checkByte(paramChar, '\r');
  }

  private boolean checkLF(char paramChar)
  {
    return checkByte(paramChar, '\n');
  }

  public void onDataAvailable(DataEmitter paramDataEmitter, ByteBufferList paramByteBufferList)
  {
    while (true)
    {
      char c;
      try
      {
        if (paramByteBufferList.remaining() > 0)
          switch (1.$SwitchMap$fm$qingting$async$http$filter$ChunkedInputFilter$State[this.mState.ordinal()])
          {
          case 1:
            c = paramByteBufferList.getByteChar();
            if (c == '\r')
            {
              this.mState = State.CHUNK_LEN_CR;
              this.mChunkLengthRemaining = this.mChunkLength;
              continue;
            }
            break;
          case 2:
          case 3:
          case 4:
          case 5:
          case 6:
          }
      }
      catch (Exception localException)
      {
        report(localException);
      }
      do
      {
        do
        {
          do
          {
            do
            {
              return;
              this.mChunkLength = (16 * this.mChunkLength);
              if ((c >= 'a') && (c <= 'f'))
              {
                this.mChunkLength += 10 + (c - 'a');
                break;
              }
              if ((c >= '0') && (c <= '9'))
              {
                this.mChunkLength += c - '0';
                break;
              }
              if ((c >= 'A') && (c <= 'F'))
              {
                this.mChunkLength += 10 + (c - 'A');
                break;
              }
              report(new Exception("invalid chunk length: " + c));
              return;
            }
            while (!checkLF(paramByteBufferList.getByteChar()));
            this.mState = State.CHUNK;
            break;
            int i = paramByteBufferList.remaining();
            int j = Math.min(this.mChunkLengthRemaining, i);
            this.mChunkLengthRemaining -= j;
            if (this.mChunkLengthRemaining == 0)
              this.mState = State.CHUNK_CR;
            if (j == 0)
              break;
            ByteBufferList localByteBufferList = paramByteBufferList.get(j);
            int k = paramByteBufferList.remaining();
            assert (i == localByteBufferList.remaining() + paramByteBufferList.remaining());
            assert (j == localByteBufferList.remaining());
            Util.emitAllData(this, localByteBufferList);
            if (($assertionsDisabled) || (k == paramByteBufferList.remaining()))
              break;
            throw new AssertionError();
          }
          while (!checkCR(paramByteBufferList.getByteChar()));
          this.mState = State.CHUNK_CRLF;
          break;
        }
        while (!checkLF(paramByteBufferList.getByteChar()));
        if (this.mChunkLength > 0)
          this.mState = State.CHUNK_LEN;
        while (true)
        {
          this.mChunkLength = 0;
          break;
          this.mState = State.COMPLETE;
          report(null);
        }
      }
      while ($assertionsDisabled);
      throw new AssertionError();
    }
  }

  private static enum State
  {
    static
    {
      CHUNK = new State("CHUNK", 3);
      CHUNK_CR = new State("CHUNK_CR", 4);
      CHUNK_CRLF = new State("CHUNK_CRLF", 5);
      COMPLETE = new State("COMPLETE", 6);
      State[] arrayOfState = new State[7];
      arrayOfState[0] = CHUNK_LEN;
      arrayOfState[1] = CHUNK_LEN_CR;
      arrayOfState[2] = CHUNK_LEN_CRLF;
      arrayOfState[3] = CHUNK;
      arrayOfState[4] = CHUNK_CR;
      arrayOfState[5] = CHUNK_CRLF;
      arrayOfState[6] = COMPLETE;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.filter.ChunkedInputFilter
 * JD-Core Version:    0.6.2
 */