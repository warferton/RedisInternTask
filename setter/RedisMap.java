package application;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisMap implements Map<String,String> {

	Jedis jedis = new Jedis("localhost");
	
	@Override
	public int size() {
		return entrySet().size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return this.containsKey(key) == true;
		
	}

	@Override
	public boolean containsValue(Object value) {
		return this.containsValue(value) == true;
	}

	@Override
	public String get(Object key) {
	if(this.containsKey(key))
		return jedis.get((String)key);
	else
		return null;
	}

	@Override
	public String put(String key, String value) {
		jedis.set(key, value);
		return null;
	}

	@Override
	public String remove(Object key) {
		jedis.del((String)key);
		return (String)key;
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Entry<String, String>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
