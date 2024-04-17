## Description
Revisiting implementation of doubly-linked lists. 
Considering the effects of the circularly-linked variant and fail-fast iterators.

Link: https://github.com/KRmiyamoto/Mini-Project8


## Author
Keely Miyamoto


## Resources
-Mini-Project 8: Linked lists, revisited (Instructions for assignment completion.)

-Lab: Doubly-linked lists and circularly-linked lists (Source code for SimpleList 
interface and initial implementation of DLLs.) Note: my lab partners for this assignment were Nye Tenerelli and Tim Yu.

-Eboard 29 (In-class discussion of Mini-Project 8 strategies.)


## Effects of circular-linking and using a dummy node
By adapting the code from the simple doubly-linked list class that we implemented in lab, it was easy to identify 
some effects of circular-linking with a dummy node. Circular linking simplified the implementation of several methods 
by eliminating the need to consider multiple special cases. In particular, our original 'add' method separately 
considered instances in which the list was empty or the iterator was at the beginning of this list. However, after 
circularly linking our list, this was no longer necessary because when the iterator was situated before the first element 
of the list, 'this.prev' still existed (due to the presence of the dummy node). Similarly, in the 'remove' method, it was 
no longer necessary to update the 'front' of the list. Not only did introducing the dummy node elminate the 'front' field 
of the ListIterator class, but because the dummy has an invariable position ('next' of the last element and 'prev' of the 
first element), it does not need to be modified as the original 'front' field did. 


## Effects of fail-fast iterators
I implemented fail-fast iterators by introducing a field in the SimpleCDLL class to track the total number of changes to a given SimpleCDLL. Each ListIterator also had a 'numChanges' field that tracked the number of changes to the list since that iterator was instantiated. Then, I wrote a 'failFast' method that would check if an iterator's 'numChanges' was equal to the 'numChanges' of the list. If not, the method threw the appropriate Exception. This helper method was called within each of the methods in my ListIterator inner class, so as to ensure that no two iterators were differently and simultaneously modifying the list. 

The trickiest part of implmenting fail-fast iterators was ensuring that each of my methods appropriately updated the 'numChanges' fields in the relevant iterator and for the list overall. For instance, after either 'add' or 'remove' is called, it is necessary to increment both SimpleCDLL.this.numChanges and this.numChanges (which can be achieved by writing 'SimpleCDLL.this.numChanges++;' and then setting 'this.numChanges = SimpleCDLL.this.numChange;'). 