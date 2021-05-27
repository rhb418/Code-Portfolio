# API Lab Discussion
# Collections API Discussion

## Names and NetIDs
Bill Guo (wg78)
Billy Luqiu (wyl6)
Robert Barnette (rhb19)
Tomas Esber (tee9)

### What is the purpose of each interface implemented by LinkedList?
Linked List implements List<E> and Deque<E>. Deque's support insertion/removal at both ends making a double ended queue essentially. List supports element retrieval and insertion by index.
 
### What is the purpose of each superclass of HashMap?
HashMap extends AbstractMap and Object. AbstractMap provides a basic interface for maps in Java that map key value pairs and provide methods for insertion/deletion/retrieval. Object has methods such as equals, hashcode, and toString that can be used on HashMaps. 

### How many different implementations are there for a Set?
AbstractSet, ConcurrentSkipListSet, CopyOnWriteArraySet, EnumSet, HashSet, JobStateReasons, LinkedHashSet, and TreeSet all implement Set for a total of 8. 

### What methods are common to all collections?
Methods that are common to all collections are add, addAll, clear, contains, containsALl, equals, hashCode, isEmpty, iterator, parallelStream, remove, removeALl, removeIf, retainAll, size, spliterator, stream, and toArray. It also implements for each from iterable. However, some methods will throw an exception such as remove/add on an immutable collection. 

### What methods are common to all Queues?
Methods that are common to all queues are: add, element, offer, peek, poll, and remove as well as all the collections and object methods. 


### What is the purpose of the collection utility classes?
They provide methods to modify existing collections and implementations of collections in a useful way for the user. 