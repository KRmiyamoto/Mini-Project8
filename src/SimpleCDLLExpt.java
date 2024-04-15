import java.io.PrintWriter;

/**
 * Experiments with the SimpleCDLL class. Adapted from lab
 * with partners Nye Tenerelli and Tim Yu.
 * 
 * @author Keely Miyamoto
 */
public class SimpleCDLLExpt  {

  public static void main(String[] args) throws Exception {
    PrintWriter pen = new PrintWriter(System.out, true);
    // Test 'add'.
    SimpleListExpt.addExpt(pen, new SimpleCDLL<String>());

    // Test 'remove' moving forward, backward, and randomly.
    SimpleListExpt.removeExpt(pen, new SimpleCDLL<String>());
    SimpleListExpt.removeRandomFwdExpt(pen, new SimpleCDLL<String>());
    SimpleListExpt.removeRandomWalkExpt(pen, new SimpleCDLL<String>(), 3);
    SimpleListExpt.removeRandomBkwdExpt(pen, new SimpleCDLL<String>());
    SimpleListExpt.removeAlternatingBkwdExpt(pen, new SimpleCDLL<String>());
    
    // Test 'failFast'.
    SimpleCDLL<String> lst = new SimpleCDLL<String>();       
    SimpleListExpt.add(pen, lst, new String[] {"alpha", "bravo", "charlie", "delta", "echo"});
    SimpleListExpt.failFastExpt(pen, lst);

    // Test 'set' moving forward and backward.
    SimpleCDLL<String> lst2 = new SimpleCDLL<String>(); 
    SimpleListExpt.add(pen, lst2, new String[] {"alpha", "bravo", "charlie", "delta", "echo"});
    SimpleListExpt.printList(pen, lst2);
    SimpleListExpt.setFwdExpt(pen, lst2);
    SimpleListExpt.setBkwdExpt(pen, lst2);

    // Test that the dummy node cannot be accessed.
    SimpleListExpt.testDummy(pen, lst2);
  } // main(String[])
  
} // class SimpleCDLLExpt
