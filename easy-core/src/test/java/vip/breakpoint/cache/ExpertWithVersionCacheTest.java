package vip.breakpoint.cache;

import org.junit.jupiter.api.Test;

/**
 * @author : breakpoint/赵先生
 * create on 2022/11/01
 * 欢迎关注公众号:代码废柴
 */
class ExpertWithVersionCacheTest {

    @Test
    public void test01() throws Exception {

        VersionCache<Integer> cache = CacheFactory.newVersionCacheInstance(() -> 100);
        cache.putObject("1", 1);
        cache.putObject("2", 2);
        cache.putObject("3", 3);
        cache.putObject("4", 4);
        cache.putObject("5", 5);

        Thread.sleep(10000);

        System.out.println(cache.getObject("1"));
        System.out.println(cache.getObject("2"));
        System.out.println(cache.getObject("3"));
        System.out.println(cache.getObject("4"));
        System.out.println(cache.getObject("5"));
        System.out.println(cache.getObject("1"));
        Thread.sleep(500);
        cache.putObject("1", 1);
        cache.putObject("2", 2);
        cache.putObject("3", 3);
        cache.putObject("4", 4);
        cache.putObject("5", 5);
        Thread.sleep(500);
        cache.putObject("1", 1);
        cache.putObject("2", 2);
        cache.putObject("3", 3);
        cache.putObject("4", 4);
        cache.putObject("5", 5);
        System.out.println(cache.getObject("1"));
        System.out.println(cache.getObject("2"));
        System.out.println(cache.getObject("3"));
        System.out.println(cache.getObject("4"));
        System.out.println(cache.getObject("5"));
        System.out.println(cache.getObject("1"));

    }

}