package vip.breakpoint.utils;

import vip.breakpoint.base.BaseDataSupport;

import java.util.UUID;

/**
 * 生成ID的工具类
 */
public abstract class GenerateIDUtils extends BaseDataSupport {

    // get key from UUID 36 bits
    public static String getUniqueID32Length() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // get key from UUID 32 bits
    public static String getUniqueID36Length() {
        return UUID.randomUUID().toString();
    }

    // 雪花算法的操作
    private static final class SnowAlgorithm {
        private long twepoch = 1288834974657L;
        private long workerIdBits = 5L;
        private long datacenterIdBits = 5L;
        private long maxWorkerId = -1L ^ (-1L << workerIdBits);
        private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
        private long sequenceBits = 12L;
        private long workerIdShift = sequenceBits;
        private long datacenterIdShift = sequenceBits + workerIdBits;
        private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        private long sequenceMask = -1L ^ (-1L << sequenceBits);
        private long lastTimestamp = -1L;
        private final long workerId;
        private final long datacenterId;
        private long sequence;

        // constructor
        private SnowAlgorithm(long workerId, long datacenterId, long sequence) {
            // sanity check for workerId
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",
                        maxWorkerId));
            }
            if (datacenterId > maxDatacenterId || datacenterId < 0) {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0",
                        maxDatacenterId));
            }
            System.out.printf("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d," +
                            " sequence bits %d, workerid %d%n",
                    timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);
            this.workerId = workerId;
            this.datacenterId = datacenterId;
            this.sequence = sequence;
        }

        private long getWorkerId() {
            return workerId;
        }

        private long getDatacenterId() {
            return datacenterId;
        }

        private long getTimestamp() {
            return System.currentTimeMillis();
        }

        private synchronized long nextId() {
            long timestamp = timeGen();

            if (timestamp < lastTimestamp) {
                System.err.printf("clock is moving backwards.  Rejecting requests until %d.\n", lastTimestamp);
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                        lastTimestamp - timestamp));
            }

            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask;
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0;
            }

            lastTimestamp = timestamp;
            return ((timestamp - twepoch) << timestampLeftShift) |
                    (datacenterId << datacenterIdShift) |
                    (workerId << workerIdShift) |
                    sequence;
        }

        private long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        private long timeGen() {
            return System.currentTimeMillis();
        }
    }

    //
    private static final SnowAlgorithm s = new SnowAlgorithm(1L, 1L, 1L);

    // get key from snow ,type long
    public static long generateId() {
        return s.nextId();
    }

    // get key from snow
    public static String generateIdStr() {
        return String.valueOf(s.nextId());
    }

    // test
    public static void main(String[] args) {
        System.out.println(generateId());
    }
}
