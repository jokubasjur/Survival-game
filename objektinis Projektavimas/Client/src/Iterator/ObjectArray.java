package Iterator;

import java.util.ArrayList;
import java.util.Iterator;

public class ObjectArray<I> {
	private ArrayList<I> array;
	
	public ObjectArray() {
		array = new ArrayList<>();
	}
	
	public void add(I item) {
		if (item != null) {
			array.add(item);
		}
	}
	
	public void remove(I item) {
		if (item != null) {
			array.remove(item);
		}
	}
	
	public Iterator<I> getIterator() {
		return array.iterator();
	}
}
