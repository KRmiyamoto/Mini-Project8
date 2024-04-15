import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.Random;
import java.util.function.Predicate;
import java.util.ConcurrentModificationException;

/**
 * Some simple experiments with SimpleLists
 * 
 * @author Samuel Rebelsky
 * @author Keely Miyamoto
 * @author Tim Yu
 * @author Nye Tenerelli
 */
public class SimpleListExpt {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  static Random rand = new Random();

  // +-----------+---------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Move list iterator to the end of the list.
   */
  static void toEnd(ListIterator<String> lit) {
    while (lit.hasNext()) {
      lit.next();
    } // while
  } // toEnd(ListIterator<String>)

  
  /**
   * Add an element using an iterator.
   */
  static void add(PrintWriter pen, ListIterator<String> it, String val)
      throws Exception {
    pen.println("Add \"" + val + "\" at position " + it.nextIndex());
    it.add(val);
  } // add(PrintWriter)

 /**
   * Add several String elements to a list using the list iterator.
   */
  static void add(PrintWriter pen, ListIterator<String> listIt, String[] vals) throws Exception {
    for (String val : vals) {
      add(pen, listIt, val);
    } // for
  } // add(PrintWriter, SimpleList<String>, String[])

  /**
   * Add several String elts to the front of a given list.
   */
  static void add(PrintWriter pen, SimpleList<String> lst, String[] vals) throws Exception {
    add(pen, lst.listIterator(), vals);
  } // add(PrintWriter, SimpleList<String>, String[])


  /**
   * Print a list.
   */
  static void printList(PrintWriter pen, SimpleList<String> lst) {
    int i = 0;
    for (String val : lst) {
      pen.print(i++ + ":" + val + " ");
    } // for
    pen.println();
  } // printList(PrintWriter, SimpleList<String>)


  /**
   * Add a variety of elements, describing what happens.
   */
  static void addExpt(PrintWriter pen, SimpleList<String> lst, 
      String[] strings) throws Exception {
    ListIterator<String> lit = lst.listIterator();

    for (String str : strings) {
      add(pen, lit, str);
      printList(pen, lst);
      pen.println();
    } // for
  } // addExpt(PrintWriter, SimpleList<String>, String[])

  /**
   * Add a variety of elements, without describing what happens
   */
  static void addStrings(PrintWriter pen, SimpleList<String> lst,
      String[] strings) throws Exception {
    ListIterator<String> lit = lst.listIterator();

    for (String str : strings) {
      lit.add(str);
    } // for
    printList(pen, lst);
    pen.println();
  } // addStrings


  /**
   * Remove a variety of elements, moving forward.
   */
  static void removeForwardExpt(PrintWriter pen, SimpleList<String> lst,
      Predicate<String> pred) throws Exception {
    ListIterator<String> lit = lst.listIterator();

    while (lit.hasNext()) {
      String str = lit.next();
      if (pred.test(str)) {
        pen.println("Remove " + str);
        lit.remove();
        printList(pen, lst);
        pen.println();
      } // if
    } // while
  } // removeForwardExpt(PrintWriter, SimpleList<String>, Predicate<String>)

  /**
   * Remove a variety of elements, moving backward.
   */
  static void removeBackwardExpt(PrintWriter pen, SimpleList<String> lst,
      Predicate<String> pred) throws Exception {
    ListIterator<String> lit = lst.listIterator();

    // Advance to the end of the list
    toEnd(lit);

    // And then back up
    while (lit.hasPrevious()) {
      String str = lit.previous();
      if (pred.test(str)) {
        pen.println("Remove " + str);
        lit.remove();
        printList(pen, lst);
        pen.println();
      } // if
    } // while
  } // removeBackwardExpt(PrintWriter, SimpleList<String>, Predicate<String>)

  /**
   * Randomly remove n elements, moving forward and backward.
   *
   * @pre n is a positive integer.
   */
  static void randomWalkRemove(PrintWriter pen, SimpleList<String> lst, int n) {
    ListIterator<String> lit = lst.listIterator();

    for (int i = 0; i < n; i++) {
      String val = "";

      // Random walk
      for (int j = 0; j < 5; j++) {
        if (!lit.hasNext() || (lit.hasPrevious() && rand.nextInt(2) == 0)) {
          pen.println("Backward to " + lit.previousIndex());
          val = lit.previous();
        } else {
          pen.println("Forward to " + lit.nextIndex());
          val = lit.next();
        } // if/else
      } // for j
      
      pen.println("Removing " + val);
      lit.remove();
      printList(pen, lst);
    } // for i
  } // randomWalkRemove(n)

  // +-------------+-------------------------------------------------
  // | Experiments |
  // +-------------+

  /**
   * Test the add method by adding several elements.
   */
  static void addExpt(PrintWriter pen, SimpleList<String> lst) throws Exception {
    pen.println("Experiment 1: Add a variety of elements.");
    addExpt(pen, lst, new String[] {"A", "B", "C"});
    addExpt(pen, lst, new String[] {"X", "Y", "Z"});
    pen.println();
  } // expt1(PrintWriter, SimpleList<String>)


  /**
   * Test the remove method while iteration foward through the list and removing predictably.
   */
  static void removeExpt(PrintWriter pen, SimpleList<String> lst) throws Exception {
    pen.println("Experiment 2: Remove alternating elements, moving forward.");
    final Counter counter = new Counter();
    addStrings(pen, lst, new String[] {"A", "B", "C", "D", "E", "F", "G"});
    removeForwardExpt(pen, lst, (str) -> (counter.get() % 2) == 0);
    pen.println();
  } // expt2(PrintWriter, SimpleList<String>)


  /**
   * Test the remove method while moving foward through the list and removing randomly.
   */
  static void removeRandomFwdExpt(PrintWriter pen, SimpleList<String> lst) throws Exception {
    pen.println("Experiment 3: Remove random elements, moving forward.");
    addStrings(pen, lst, new String[] {"A", "B", "C", "D", "E", "F", "G"});
    removeForwardExpt(pen, lst, (str) -> rand.nextInt(2) == 0);
    pen.println();
  } // expt3(PrintWriter, SimpleList<String>


  /**
   * Test the remove method while walking through the list randomly and removing.
   */
  static void removeRandomWalkExpt(PrintWriter pen, SimpleList<String> lst, int n) 
      throws Exception {
    pen.println("Experiment 4: Removing elements with a random walk.");
    addStrings(pen, lst, new String[] {"A", "B", "C", "D", "E", "F", "G"});
    try {
      randomWalkRemove(pen, lst, n);
    } catch (Exception e) {
      pen.println("Experiment ended early because " + e.toString());
    } // try/catch
    pen.println();
  } // expt4(PrintWriter, SimpleList<String>, int)


  /**
   * Test the remove (and previous) method while moving through the list backward and removing randomly.
   */
  static void removeRandomBkwdExpt(PrintWriter pen, SimpleList<String> lst) throws Exception {
    pen.println("Experiment 5: Remove random elements, moving backwards.");
    addStrings(pen, lst, new String[] {"A", "B", "C", "D", "E", "F", "G"});
    removeBackwardExpt(pen, lst, (str) -> rand.nextInt(2) == 0);
    pen.println();
  } // expt5(PrintWriter, SimpleList<String>


  /**
   * Test the remove (and previous) method while walking through the list backward and removing predictably.
   */
  static void removeAlternatingBkwdExpt(PrintWriter pen, SimpleList<String> lst) throws Exception {
    pen.println("Experiment 6: Remove alternating elements, moving backwards.");
    final Counter counter = new Counter();
    addStrings(pen, lst, new String[] {"A", "B", "C", "D", "E", "F", "G"});
    removeBackwardExpt(pen, lst, (str) -> (counter.get() % 2) == 0);
    pen.println();
  } // expt6(PrintWriter, SimpleList<String>)


  /**
   * Test failFast() method by changing list and then iterating.
   */
  static void failFastExpt(PrintWriter pen, SimpleList<String> lst) throws Exception {
    ListIterator<String> it1 = lst.listIterator();
    ListIterator<String> it2 = lst.listIterator();
    pen.println("Experiment 7: Test failFast().");
    add(pen, it1, "newElt");
    try {
        pen.println("Try to add \"newElt\" with a second iterator:");
        add(pen, it2, "newElt");
        pen.println("failFast() method did not work properly.");
    } catch (ConcurrentModificationException cme) {
        pen.println("failFast() method works properly.");
    } // catch (ConcurrentModificationException)
    pen.println();
  } // failFastExpt(PrintWriter, SimpleList<String>)


  /**
   * Test the set method moving forward.
   */
  static void setFwdExpt(PrintWriter pen, SimpleList<String> lst) {
    ListIterator<String> lit = lst.listIterator();
    pen.println("Experiment 7: Test set, moving forward.");
    pen.println("Set to 0: ");
    while (lit.hasNext()) {
      lit.next();
      lit.set("0");
    } // while
    printList(pen, lst);
    pen.println();
  } // setFwdExpt(PrintWriter, SimpleList<String>)
  

  /**
   * Test the set (and previous) method moving backward.
   */
  static void setBkwdExpt(PrintWriter pen, SimpleList<String> lst){
    ListIterator<String> lit = lst.listIterator();
    toEnd(lit);
    pen.println("Experiment 8: Test set, moving backward.");
    pen.println("Set to 1: ");
    while (lit.hasPrevious()) {
      lit.previous();
        lit.set("1");
      } // while
    printList(pen, lst);
    pen.println();
  } // setBackwardsExpt(PrintWriter, SimpleList<String>)

   /**
   * Test hasPrevious() and hasNext with dummy.
   */
  static void testDummy(PrintWriter pen, SimpleList<String> lst){
    ListIterator<String> lit = lst.listIterator();
    pen.println("Experiment 9: Test hasPrevious and hasNext with dummy.");
    if (!lit.hasPrevious()) {
      pen.println("Dummy works as intended.");
    } else {
      pen.println("Oh no!");
    } // if

    // Move to end of list.
    toEnd(lit);

    if (!lit.hasNext()) {
      pen.println("Dummy works as intended.");
    } else {
      pen.println("Oh no!");
    } // if
  } // testDummy(PrintWriter, SimpleList<String>)

} // class SimpleListExpt


/**
 * A simple counter.
 */
class Counter {
  int val = 0;
  int get() {
    return val++;
  } // get()
} // class Counter