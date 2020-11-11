package com.gc.analysis.homework;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;


public class GCLogAnalysis {
    private static Random random = new Random();
    public static void main(String[] args) throws IOException {
        //当前毫秒时间戳
        long startMillis = System.currentTimeMillis();
        //持续运行时间数
        long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
        //结束时间
        long endMillis = startMillis + timeoutMillis;
        LongAdder counter = new LongAdder();
        System.out.println("正在执行...");
        //缓存部分对象进入老年代
        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];
        //在此时间段循环生成
        while (System.currentTimeMillis() < endMillis) {
            //生成垃圾对象
            Object garbage = generateGarbage(100*1024);
            counter.increment();
            int randomIndex = random.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }
        System.out.println("ִ执行结束，共生成对象数" + counter.longValue());
        //System.in.read();
    }

    //生成对象
    private static Object generateGarbage(int max) {
        int randomSize = random.nextInt(max);
        int type = randomSize % 4;
        Object result = null;
        switch (type) {
            case 0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "randomString-Anything";
                while (builder.length() < randomSize) {
                    builder.append(randomString);
                    builder.append(max);
                    builder.append(randomSize);
                }
                result = builder.toString();
                break;
        }
        return result;
    }
}

/**
 * 1.基本分配
 * 8：1：1
 * 2:1
 * 符合预期
 * Heap Usage:
 * PS Young Generation
 * Eden Space:
 *    capacity = 991952896 (946.0MB)
 *    used     = 157850560 (150.53802490234375MB)
 *    free     = 834102336 (795.4619750976562MB)
 *    15.913110454793209% used
 * From Space:
 *    capacity = 148373504 (141.5MB)
 *    used     = 148127816 (141.26569366455078MB)
 *    free     = 245688 (0.23430633544921875MB)
 *    99.83441248378148% used
 * To Space:
 *    capacity = 193462272 (184.5MB)
 *    used     = 0 (0.0MB)
 *    free     = 193462272 (184.5MB)
 *    0.0% used
 * PS Old Generation
 *    capacity = 584581120 (557.5MB)
 *    used     = 275522896 (262.7591094970703MB)
 *    free     = 309058224 (294.7408905029297MB)
 *    47.13167883355521% used
 *--------------------------------------------------------
 * 1串行CG
 * using thread-local object allocation.
 * Mark Sweep Compact GC
 *
 * Heap Configuration:
 *    MinHeapFreeRatio         = 40
 *    MaxHeapFreeRatio         = 70
 *    MaxHeapSize              = 8539602944 (8144.0MB)
 *    NewSize                  = 178257920 (170.0MB)
 *    MaxNewSize               = 2846490624 (2714.625MB)
 *    OldSize                  = 356515840 (340.0MB)
 *    NewRatio                 = 2
 *    SurvivorRatio            = 8
 *    MetaspaceSize            = 21807104 (20.796875MB)
 *    CompressedClassSpaceSize = 1073741824 (1024.0MB)
 *    MaxMetaspaceSize         = 17592186044415 MB
 *    G1HeapRegionSize         = 0 (0.0MB)
 *
 * Heap Usage:
 * New Generation (Eden + 1 Survivor Space):
 *    capacity = 259915776 (247.875MB)
 *    used     = 150383784 (143.41715240478516MB)
 *    free     = 109531992 (104.45784759521484MB)
 *    57.858659568244136% used
 * Eden Space:
 *    capacity = 231079936 (220.375MB)
 *    used     = 121547952 (115.91716003417969MB)
 *    free     = 109531984 (104.45783996582031MB)
 *    52.599959176031625% used
 * From Space:
 *    capacity = 28835840 (27.5MB)
 *    used     = 28835832 (27.49999237060547MB)
 *    free     = 8 (7.62939453125E-6MB)
 *    99.99997225674716% used
 * To Space:
 *    capacity = 28835840 (27.5MB)
 *    used     = 0 (0.0MB)
 *    free     = 28835840 (27.5MB)
 *    0.0% used
 * tenured generation:
 *    capacity = 577474560 (550.72265625MB)
 *    used     = 515593656 (491.7084274291992MB)
 *    free     = 61880904 (59.01422882080078MB)
 *    89.28421989706352% used
 *
 *  2.并行GC
 *  using thread-local object allocation.
 * Parallel GC with 8 thread(s)
 *
 * Heap Configuration:
 *    MinHeapFreeRatio         = 0
 *    MaxHeapFreeRatio         = 100
 *    MaxHeapSize              = 8539602944 (8144.0MB)
 *    NewSize                  = 178257920 (170.0MB)
 *    MaxNewSize               = 2846359552 (2714.5MB)
 *    OldSize                  = 356515840 (340.0MB)
 *    NewRatio                 = 2
 *    SurvivorRatio            = 8
 *    MetaspaceSize            = 21807104 (20.796875MB)
 *    CompressedClassSpaceSize = 1073741824 (1024.0MB)
 *    MaxMetaspaceSize         = 17592186044415 MB
 *    G1HeapRegionSize         = 0 (0.0MB)
 *
 * Heap Usage:
 * PS Young Generation
 * Eden Space:
 *    capacity = 536870912 (512.0MB)
 *    used     = 493970104 (471.0866012573242MB)
 *    free     = 42900808 (40.91339874267578MB)
 *    92.00910180807114% used
 * From Space:
 *    capacity = 22020096 (21.0MB)
 *    used     = 22011024 (20.991348266601562MB)
 *    free     = 9072 (0.0086517333984375MB)
 *    99.95880126953125% used
 * To Space:
 *    capacity = 22020096 (21.0MB)
 *    used     = 0 (0.0MB)
 *    free     = 22020096 (21.0MB)
 *    0.0% used
 * PS Old Generation
 *    capacity = 356515840 (340.0MB)
 *    used     = 218822472 (208.68537139892578MB)
 *    free     = 137693368 (131.31462860107422MB)
 *    61.37805041144876% used
 *
 *    3.cms
 *    using parallel threads in the new generation.
 * using thread-local object allocation.
 * Concurrent Mark-Sweep GC
 *
 * Heap Configuration:
 *    MinHeapFreeRatio         = 40
 *    MaxHeapFreeRatio         = 70
 *    MaxHeapSize              = 8539602944 (8144.0MB)
 *    NewSize                  = 178257920 (170.0MB)
 *    MaxNewSize               = 697892864 (665.5625MB)
 *    OldSize                  = 356515840 (340.0MB)
 *    NewRatio                 = 2
 *    SurvivorRatio            = 8
 *    MetaspaceSize            = 21807104 (20.796875MB)
 *    CompressedClassSpaceSize = 1073741824 (1024.0MB)
 *    MaxMetaspaceSize         = 17592186044415 MB
 *    G1HeapRegionSize         = 0 (0.0MB)
 *
 * Heap Usage:
 * New Generation (Eden + 1 Survivor Space):
 *    capacity = 160432128 (153.0MB)
 *    used     = 23869136 (22.763381958007812MB)
 *    free     = 136562992 (130.2366180419922MB)
 *    14.878027423534517% used
 * Eden Space:
 *    capacity = 142606336 (136.0MB)
 *    used     = 6043904 (5.763916015625MB)
 *    free     = 136562432 (130.236083984375MB)
 *    4.2381735409007355% used
 * From Space:
 *    capacity = 17825792 (17.0MB)
 *    used     = 17825232 (16.999465942382812MB)
 *    free     = 560 (5.340576171875E-4MB)
 *    99.99685848460479% used
 * To Space:
 *    capacity = 17825792 (17.0MB)
 *    used     = 0 (0.0MB)
 *    free     = 17825792 (17.0MB)
 *    0.0% used
 * concurrent mark-sweep generation:
 *    capacity = 878686208 (837.98046875MB)
 *    used     = 294883256 (281.2225875854492MB)
 *    free     = 583802952 (556.7578811645508MB)
 *    33.55956350688504% used
 *
 *    4.G1
 *using thread-local object allocation.
 * Garbage-First (G1) GC with 8 thread(s)
 *
 * Heap Configuration:
 *    MinHeapFreeRatio         = 40
 *    MaxHeapFreeRatio         = 70
 *    MaxHeapSize              = 8539602944 (8144.0MB)
 *    NewSize                  = 1363144 (1.2999954223632812MB)
 *    MaxNewSize               = 5123342336 (4886.0MB)
 *    OldSize                  = 5452592 (5.1999969482421875MB)
 *    NewRatio                 = 2
 *    SurvivorRatio            = 8
 *    MetaspaceSize            = 21807104 (20.796875MB)
 *    CompressedClassSpaceSize = 1073741824 (1024.0MB)
 *    MaxMetaspaceSize         = 17592186044415 MB
 *    G1HeapRegionSize         = 2097152 (2.0MB)
 *
 * Heap Usage:
 * G1 Heap:
 *    regions  = 4072
 *    capacity = 8539602944 (8144.0MB)
 *    used     = 572200960 (545.693359375MB)
 *    free     = 7967401984 (7598.306640625MB)
 *    6.7005569667853635% used
 * G1 Young Generation:
 * Eden Space:
 *    regions  = 0
 *    capacity = 497025024 (474.0MB)
 *    used     = 0 (0.0MB)
 *    free     = 497025024 (474.0MB)
 *    0.0% used
 * Survivor Space:
 *    regions  = 28
 *    capacity = 58720256 (56.0MB)
 *    used     = 58720256 (56.0MB)
 *    free     = 0 (0.0MB)
 *    100.0% used
 * G1 Old Generation:
 *    regions  = 245
 *    capacity = 5366611968 (5118.0MB)
 *    used     = 513480704 (489.693359375MB)
 *    free     = 4853131264 (4628.306640625MB)
 *    9.568060949101211% used
 *
 * */