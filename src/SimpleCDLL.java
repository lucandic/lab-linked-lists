import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Simple circular doubly-linked lists that supports the fail fast policy.
 *
 * @author Candice Lu
 * @author Samuel A. Rebelsky
 */
public class SimpleCDLL<T> implements SimpleList<T> {
  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The number of values in the list.
   */
  int size;

  /**
   * The number of values in the list.
   */
  Node2<T> dummy;

  /**
   * The number of times the CDLL was moderated
   */
  int timesModified;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create an empty list.
   */
  public SimpleCDLL() {
    this.timesModified = 0;
    this.size = 0;
    this.dummy = new Node2<T>(null);
    this.dummy.prev = this.dummy;
    this.dummy.next = this.dummy;
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
       * to the previous and next value..
       */
      Node2<T> prev = SimpleCDLL.this.dummy;
      Node2<T> next = SimpleCDLL.this.dummy.next;

      /**
       * The node to be updated by remove or set.  Has a value of
       * null when there is no such value.
       */
      Node2<T> update = null;

      /**
       * Number of times that the iterator has modified the list
       */
      int timesModified = SimpleCDLL.this.timesModified;

      // +---------+-------------------------------------------------------
      // | Methods |
      // +---------+

      /*
       * 
       */
      public void checkModify() {
        if (this.timesModified != SimpleCDLL.this.timesModified) throw new ConcurrentModificationException();
      }

      public void add(T val) throws UnsupportedOperationException {

        // check for modifications from other iterators
        checkModify();

        this.prev = this.prev.insertAfter(val);
        this.next = this.prev.next;

        // Note that we cannot update
        this.update = null;

        // Increase the size
        ++SimpleCDLL.this.size;

        // Update the position. 
        ++this.pos;

        // Update both timesModified
        ++this.timesModified;
        ++SimpleCDLL.this.timesModified;
      } // add(T)

      public boolean hasNext() {
        // check for modifications from other iterators
        checkModify();
        return (this.pos < SimpleCDLL.this.size);
      } // hasNext()

      public boolean hasPrevious() {
        // check for modifications from other iterators
        checkModify();
        return (this.pos > 0);
      } // hasPrevious()

      public T next() {
        // check for modifications from other iterators
        checkModify();
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
        // check for modifications from other iterators
        checkModify();
        return this.pos;
      } // nextIndex()

      public int previousIndex() {
        // check for modifications from other iterators
        checkModify();
        return this.pos - 1;
      } // prevIndex

      public T previous() throws NoSuchElementException {
        // check for modifications from other iterators
        checkModify();
        if (!this.hasPrevious())
          throw new NoSuchElementException();
 
        // update the update
        this.update = this.prev;

        // reassign node pointers
        this.next = this.prev;
        this.prev = this.prev.prev;

        // update position
        --this.pos;
        
        return this.update.value;
      } // previous()

      public void remove() {
        // check for modifications from other iterators
        checkModify();
        // Sanity check
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

        // Do the real work
        this.update.remove();
        --SimpleCDLL.this.size;

        // Note that no more updates are possible
        this.update = null;

        // update timesModified
        ++this.timesModified;
        ++SimpleCDLL.this.timesModified;
      } // remove()

      public void set(T val) {
        checkModify();
        // Sanity check
        if (this.update == null) {
          throw new IllegalStateException();
        } // if
        // Do the real work
        this.update.value = val;
        // Note that no more updates are possible
        this.update = null;
      } // set(T)
    };
  } // listIterator()

} // class SimpleDLL<T>

