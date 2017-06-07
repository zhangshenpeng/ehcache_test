package com.inveno.ehcache_test;
import net.sf.ehcache.Cache;  
import net.sf.ehcache.CacheManager;  
import net.sf.ehcache.Element;  


import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.junit.Test;

public class EhcacheTest {
    private static CacheManager cacheManager;  
    static String               cacheName = "cache1";  
    
    @Test
    public void test() {
        cacheManager = CacheManager.create(); 
        cacheManager.addCache(cacheName); 
        Cache cache = cacheManager.getCache(cacheName);  
        String key = "key1";  
        String value = "value1"; 
        
        cache.put(new Element(key, value)); 
        
        Element e = cache.get(key);
        System.out.println(e);
        
    }
}
