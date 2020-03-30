package application;

import java.util.List;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisMap implements Map<String,String> {
//mainkey
	private String mainKey;
	
	//redisconnect
	private static Jedis jedis;
	
	RedisMap()
	{
		super();
	}
	
	
	RedisMap(String name)
	{
		super();
		mainKey = name;
	}
	
	@Override
	public int size() {
		return this.keySet().size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return (jedis.hget(mainKey,(String)key)!=null) == true;
		
	}

	@Override
	public boolean containsValue(Object value) {
		return this.containsValue(value) == true;
	}

	@Override
	public String get(Object key) {
	if(this.containsKey(key))
		return jedis.hget(mainKey,(String)key);
	else
		return null;
	}

	@Override
	public String put(String key, String value) {
		jedis.hset(mainKey,key, value);
		return null;
	}

	@Override
	public String remove(Object key) {
		jedis.hdel(mainKey,(String)key);
		return (String)key;
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		for(String s : m.keySet())
			this.put(s, (String)m.get(s));
	}

	@Override
	public void clear() {
		jedis.flushAll();
	}
	@Override
	public Set<String> keySet() {
		return jedis.hkeys(mainKey);
	}
	@Override
	public Collection<String> values() {
		String[] arr = new String[this.size()];
		int i =0;
		for(String s : this.keySet())
			{
			arr[i] = (this.get(s));
			i++;
			}	
		List<String> col = Arrays.asList(arr) ;
		return (Collection<String>) col;
	}

	@Override
	public Set<Entry<String, String>> entrySet() {
		Set<Entry<String,String>> set = new HashSet<Entry<String,String>>();
		
		for(String s : this.keySet())
		{
			Entry<String, String> entr =
	    new AbstractMap.SimpleEntry<String, String>(s, this.get(s));
			System.out.println(entr);
			if(entr != null)
				set.add(entr);
		}
		
		return set;
	}
	//test
//	public static void main(String[]x)
//	{
//		jedis = new Jedis("localhost");
//		RedisMap mappa = new RedisMap("main");
//		RedisMap mapT = new RedisMap("scnd");
//		mapT.put("Bpop", "RWTE");
//		mappa.put("test", "valTset");
//		System.out.println(mappa.get("test"));
//		System.out.println(mappa.get("test"));
//		System.out.println(mappa.keySet());
//		System.out.println(mappa.values());
//		System.out.println(mappa.entrySet());
//		mappa.putAll(mapT);
//		System.out.println(mappa.remove("test"));
//		System.out.println(mappa.entrySet());
//
//		
//	}

}
