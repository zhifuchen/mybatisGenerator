package mybatisGenerator.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PageData extends HashMap<Object, Object> implements Map<Object, Object>{
	
	private static final long serialVersionUID = 1L;
	
	Map<Object, Object> map = null;

	
	public PageData() {
		map = new HashMap<Object, Object>();
	}


	@Override
	public Object get(Object key) {
		Object obj = null;
		obj = map.get(key);
		return obj;
	}

	public String getString(Object key) {
		if(get(key)==null){
			return null;
		}
		return get(key).toString();
	}



	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}
	
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Set<Entry<Object, Object>> entrySet() {
		return map.entrySet();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<Object> keySet() {
		return map.keySet();
	}

	public void putAll(Map<?, ?> t) {
		map.putAll(t);
	}

	public int size() {
		return map.size();
	}

	public Collection<Object> values() {
		return map.values();
	}
	
}
