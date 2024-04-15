import java.util.Iterator;
import java.util.ListIterator;

/**
 * An interface for very simple lists.
 * 
 * @author Sam Rebelsky
 */
public interface SimpleList<T> extends Iterable<T> {
  public Iterator<T> iterator();

  public ListIterator<T> listIterator();
} // interface SimpleList<T>