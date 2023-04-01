package Iterator;

import java.util.Hashtable;
import java.util.Iterator;

public class ObjectHashTable<I> {
	private int key;
	private Hashtable<Integer, I> hash;
	
	public ObjectHashTable() {
		key = 1;
		hash = new Hashtable<Integer, I>();
	}
	
	public void add(I item) {
		hash.put(key++, item);
	}
	
	public void remove(I item) {
		hash.remove(item);
	}
	
	public Iterator<I> getIterator(){
		return hash.values().iterator();
	}
}
