package com.inveno.ehcache_test;
import net.sf.ehcache.Cache;  
import net.sf.ehcache.CacheManager;  
import net.sf.ehcache.Element; 
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

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
 
    
    @Test
    public void testSimple() {
        CacheManager cacheManager;  
        String cacheName = "cache1"; 
        cacheManager = CacheManager.create(); 
        cacheManager.addCache(cacheName); 
        Cache cache = cacheManager.getCache(cacheName);  
        String key = "key1";  
        String value = "value1"; 
        
        cache.put(new Element(key, value)); 
        
        Element e = cache.get(key);
        System.out.println(e);
        cacheManager.shutdown();
    }
    
    @Test
    public void testWithConfiguration() {
    	int maxElements = 10;
    	Cache cache = new Cache(  
    			  new CacheConfiguration("testCache", maxElements)  
    			    .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)  
    			    .eternal(false)  
    			    .timeToLiveSeconds(60)  
    			    .timeToIdleSeconds(30)  
    			    .diskExpiryThreadIntervalSeconds(0)); 
        String key = "key1";  
        String value = "value1"; 
        
        cache.put(new Element(key, value)); 
        
        Element e = cache.get(key);
        System.out.println(e);
    }
    
    @Test
    public void testWithXml() {
    	System.out.println(System.getProperty("java.io.tmpdir")); 
    	//CacheManager cacheManager=CacheManager.getInstance(); // 从class path获取
    	CacheManager manager = CacheManager.newInstance("src/test/resources/ehcache.xml"); 
    	String cacheName = "userCache";
    	String key = "key";
    	String value = "value";
    	Cache cache = manager.getCache(cacheName);  
    	
    	// set/get
    	cache.put(new Element(key, value));  
        Element e = cache.get(key);
        System.out.println("value:" + e.getObjectValue());
        
        // expire
		try {
			Thread.sleep(5000);
		} catch(Exception exception) {
			System.out.println("sleep exception as " + exception);
		}
        e = cache.get(key);
        System.out.println("value:" + e);
		System.out.println("done");
        //System.out.println("value list:" + cache.getKeys());
        manager.shutdown();  
    }
    
    @Test
    public void testDisk() {
    	System.out.println(System.getProperty("java.io.tmpdir")); 
    	//CacheManager cacheManager=CacheManager.getInstance(); // 从class path获取
    	CacheManager manager = CacheManager.newInstance("src/test/resources/ehcache_disk.xml"); 
    	String cacheName = "userCache2";
    	Cache cache = manager.getCache(cacheName);  
   
        
        // lru
        for(int i = 0; i < 1000; ++i) {
        	String keyTmp = "key" + Integer.toString(i);
        	String valueTmp = "value" + Integer.toString(i);
        	cache.put(new Element(keyTmp,valueTmp));
        	cache.flush();
        }
        // expire
		try {
			Thread.sleep(1500);
		} catch(Exception exception) {
			System.out.println("sleep exception as " + exception);
		}
		System.out.println("done");
        //System.out.println("value list:" + cache.getKeys());
        manager.shutdown();  
    }
    
    @Test
    public void testLru() {
    	System.out.println(System.getProperty("java.io.tmpdir")); 
    	//CacheManager cacheManager=CacheManager.getInstance(); // 从class path获取
    	CacheManager manager = CacheManager.newInstance("src/test/resources/ehcache_lru.xml"); 
    	String cacheName = "userCache3";
    	Cache cache = manager.getCache(cacheName);  
   
        
        // lru
        for(int i = 0; i < 20; ++i) {
        	String keyTmp = "key" + Integer.toString(i);
        	String valueTmp = "value" + Integer.toString(i);
        	cache.put(new Element(keyTmp,valueTmp));
        }

		System.out.println("done");
        System.out.println("value list:" + cache.getKeys());
        manager.shutdown();  
    }
    
}
