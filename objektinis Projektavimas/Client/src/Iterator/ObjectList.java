package Iterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class ObjectList<I> {
	private LinkedList<I> list;
	
	public ObjectList() {
		list = new LinkedList<>();
	}
	
	public void add(I item) {
		if (item != null) {
			list.add(item);
		}
	}
	
	public void remove(I item) {
		if (item != null) {
			list.remove(item);
		}
	}
	
	public int size() {
		return list.size();
	}
	
	public void clear() {
		list.clear();
	}
	
	public void addAll(Collection<? extends I> c) {
		list.addAll(c);
	}
	
	public Iterator<I> getIterator() {
		return list.iterator();
	}
}
