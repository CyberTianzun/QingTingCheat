package fm.qingting.qtradio.logger;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.MultiThreadedLowContentionClaimStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;
import java.io.PrintStream;

public class DisruptorHelper
{
  private static final int BUFFER_SIZE = 1024;
  private static DisruptorHelper instance = null;
  private BatchEventProcessor<ValueEvent> batchEventProcessor = new BatchEventProcessor(this.ringBuffer, this.sequenceBarrier, this.handler);
  private LogEventHandler handler = new LogEventHandler();
  private RingBuffer<ValueEvent> ringBuffer = new RingBuffer(ValueEvent.EVENT_FACTORY, new MultiThreadedLowContentionClaimStrategy(1024), new BlockingWaitStrategy());
  private SequenceBarrier sequenceBarrier = this.ringBuffer.newBarrier(new Sequence[0]);

  private DisruptorHelper()
  {
    RingBuffer localRingBuffer = this.ringBuffer;
    Sequence[] arrayOfSequence = new Sequence[1];
    arrayOfSequence[0] = this.batchEventProcessor.getSequence();
    localRingBuffer.setGatingSequences(arrayOfSequence);
  }

  public static DisruptorHelper getInstance()
  {
    try
    {
      if (instance == null)
      {
        instance = new DisruptorHelper();
        initAndStart();
      }
      DisruptorHelper localDisruptorHelper = instance;
      return localDisruptorHelper;
    }
    finally
    {
    }
  }

  public static void initAndStart()
  {
    new Thread(instance.batchEventProcessor).start();
  }

  public static void produce(String paramString1, String paramString2)
  {
    getInstance().produce0(paramString1, paramString2);
  }

  private void produce0(String paramString1, String paramString2)
  {
    long l = this.ringBuffer.next();
    ((ValueEvent)this.ringBuffer.get(l)).setValue(paramString2);
    ((ValueEvent)this.ringBuffer.get(l)).setType(paramString1);
    this.ringBuffer.publish(l);
    if (!this.ringBuffer.hasAvailableCapacity(10))
      System.out.println("no enough space exist.");
  }

  public static void shutdown()
  {
    getInstance().shutdown0();
  }

  private void shutdown0()
  {
    this.batchEventProcessor.halt();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.logger.DisruptorHelper
 * JD-Core Version:    0.6.2
 */