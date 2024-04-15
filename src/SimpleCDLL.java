import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Simple circularly- and doubly-linked lists with fail-fast iterators.
 * 
 * @author Keely Miyamoto
 * @author Sam Rebelsky
 */
public class SimpleCDLL<T> implements SimpleList<T> {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The dummy node at the front of the list
   */
  Node<T> dummy;

  /**
   * The number of values in the list.
   */
  int size;

  /**
   * The number of changes to the list when this iterator was intialized or last modified.
   */
  int numChanges;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create an empty list.
   */
  public SimpleCDLL() {
    this.dummy = new Node<T>(null, null, null);
    this.size = 0;
    this.numChanges = 0;
  } // SimpleDLL

  // +-----------+---------------------------------------------------------
  // | Iterators |
  // +-----------+

  public Iterator<T> iterator() {
    return listIterator();
  } // iterator()

  public ListIterator<T> listIterator() {
    return new ListIterator<T>() {
      // +--------+--------------------------------------------------------
      // | Fields |
      // +--------+

      /**
       * The position in the list of the next value to be returned.
       * Included because ListIterators must provide nextIndex and
       * prevIndex.
       */
      int pos = 0;

      /**
       * The cursor is between neighboring values, so we start links
       * to the previous and next value.
       */
      Node<T> prev = SimpleCDLL.this.dummy;
      Node<T> next = SimpleCDLL.this.dummy.next;

      /**
       * The node to be updated by remove or set.  Has a value of
       * null when there is no such value.
       */
      Node<T> update = null;

      /**
       * The number of changes to the list when this iterator was intialized or last modified.
       */
      int numChanges = SimpleCDLL.this.numChanges;

      // +---------+-------------------------------------------------------
      // | Methods |
      // +---------+

      public void add(T val) throws UnsupportedOperationException {
        // Check that iterator is valid.
        failFast();

        this.prev = this.prev.insertAfter(val);

        // Note that we cannot update
        this.update = null;

        // Increase the size
        ++SimpleCDLL.this.size;

        // Update the position.
        ++this.pos;

        // Note that list has changed.
        SimpleCDLL.this.numChanges++;
        this.numChanges = SimpleCDLL.this.numChanges;
      } // add(T)

      public boolean hasNext() {
        // Check that iterator is valid.
        failFast();

        return (this.pos < SimpleCDLL.this.size);
      } // hasNext()

      public boolean hasPrevious() {
        // Check that iterator is valid.
        failFast();

        return (this.pos > 0);
      } // hasPrevious()

      public T next() {
        // Check that iterator is valid.
        failFast();

        // Check if this.next exists.
        if (!this.hasNext()) {
         throw new NoSuchElementException();
        } // if

        // Identify the node to update
        this.update = this.next;
        // Advance the cursor
        this.prev = this.next;
        this.next = this.next.next;
        // Note the movement
        ++this.pos;
        // And return the value
        return this.update.value;
      } // next()

      public int nextIndex() {
        // Check that iterator is valid.
        failFast();

        return this.pos;
      } // nextIndex()

      public int previousIndex() {
        // Check that iterator is valid.
        failFast();
        
        if (!this.hasPrevious()) {
            throw new NoSuchElementException();
        } // if

        return this.pos - 1;
      } // prevIndex

      public T previous() throws NoSuchElementException {
        // Check that iterator is valid.
        failFast();

        // Check if this.prev exists.
        if (!this.hasPrevious()) {
          throw new NoSuchElementException();
        } // if

        // Identify the node to update
        this.update = this.prev;
        // Advance the cursor
        this.next = this.prev;
        this.prev = this.prev.prev;

        // Note the movement
        --this.pos;
        // And return the value
        return this.update.value;
      } // previous()

      public void remove() {
        // Check that iterator is valid.
        failFast();

        // Check for valid state.
        if (this.update == null) {
          throw new IllegalStateException();
        } // if

        // Update the cursor
        if (this.next == this.update) {
          this.next = this.update.next;
        } // if
        if (this.prev == this.update) {
          this.prev = this.update.prev;
          --this.pos;
        } // if

        // Remove the relevant node. Update the size.
        this.update.remove();
        --SimpleCDLL.this.size;

        // Note that no more updates are possible
        this.update = null;

        // Note that list was modified.
        SimpleCDLL.this.numChanges++;
        this.numChanges = SimpleCDLL.this.numChanges;
      } // remove()

      public void set(T val) {
        // Check that iterator is valid.
        failFast();

        // Check that state is valid.
        if (this.update == null) {
          throw new IllegalStateException();
        } // if

        // Do the real work
        this.update.value = val;
      } // set(T)

      // +-----------------------------+-----------------------------------
      // | Part 2: "Fail Fast" Helper |
      // +----------------------------+

      /**
       * Check if the SimpleCDLL was changed after 'this' iterator was initialized/modified. 
       */
      void failFast() {
        if (this.numChanges != SimpleCDLL.this.numChanges) {
            throw new ConcurrentModificationException();
        } // if
      } // failFast()
    };
  } // listIterator()

} // class Simple CDLL
