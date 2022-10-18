package vip.breakpoint.bean;

/**
 * time interval
 *
 * @author : breakpoint
 * create on 2022/08/30
 * 欢迎关注公众号 《代码废柴》
 */
public class ClickIntervalInfo {
    /**
     * 上一次点击时间
     */
    private long preClickTime;
    /**
     * 第一次点击时间
     */
    private long firstClickTime;
    /**
     * 点击次数
     */
    private int clickCount;

    public long getPreClickTime() {
        return preClickTime;
    }

    public void setPreClickTime(long preClickTime) {
        this.preClickTime = preClickTime;
    }

    public long getFirstClickTime() {
        return firstClickTime;
    }

    public void setFirstClickTime(long firstClickTime) {
        this.firstClickTime = firstClickTime;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
}
