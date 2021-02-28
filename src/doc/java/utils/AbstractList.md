### AbstractionList
这个类提供了List接口的骨干实现,来尽可能的减少实现随机访问的数据结构所需要的工作
对于顺序访问的数据结构(比如链表)，则应该优先使用这个类

如果是为了实现一个不可修改的列表,那么编程者只需要扩展这个类，并且对get()和size()方法
提供实现

如果是实现了一个可修改的列表，那么编程者需要额外提供set()方法的实现,不然set()方法会
会抛出UnsupportedException，如果列表示大小可变化的，那么需要额外提供add()和remove()方法

和集合接口一样,List需要提供一个无参的构造方法和一个以Collection为参数的构造方法

不像其他的抽象集合类实现,编程者不需要提供迭代器的实现,迭代器和列表迭代器的实现也在这个类

对于每个非抽象方法的实现,文档提供了详细的实现解释,每个方法都可以被重写来获得更高的效率

* protected AbstractList():
单独的构造器,，提供给子类构造器的隐性调用

* public boolean add(E e):
将特定的某个元素放入到链表的尾部,支持这项操作的列表,可能会对要加入的元素有限制,
特别的有些列表会拒绝添加null元素,而另外一些则会对要加入的元素的类型做出严格的限制,文档需要
对这些限制做出说明,具体实现中是调用size()方法来获得元素的末尾位置然后调用add(int index,E e)
来进行添加

* abstract public E get(int index):
抽象方法。来返回具体某个位置对应的元素

* E set(int index, E element):
将某个位置的元素替换成指定的元素,默认是抛出UnsupportedOperationException

* void add(int index, E element)
在某个位置添加元素,默认的实现是抛出UnsupportedOperationException

* E remove(int index):
移除某个位置的元素,默认实现是抛出UnsupportedOperationException

* int indexOf(Object o):
通过列表迭代器来迭代列表，直到找到第一个相等的元素,具体实现是通过迭代器来找到相等的元素
相等是通过是否是Null或者元素的equals()方法,通过调用索引previousIndex()方法返回具体的下标
如果找不到合适的元素就返回-1

* int lastIndexOf(Object o):
具体实现是从列表末尾处获取一个列表迭代器,然后向前迭代,如果能找到相等的元素,就用nextIndex()
来获得元素的索引

* void clear():
移除列表中的所有元素,在方法执行完成后,列表会被清空,这里是调用removeRange(0,size())
来将元素删除,在remove(int index) or removeRange(int fromIndex, int toIndex未
实现,那么就会抛出异常

* boolean addAll(int index, Collection<? extends E> c):
用集合c的迭代器来遍历集合,并且对每个元素都用add(int index, E e)来将元素元素添加
到集合中,很多集合类会重写这个方法来达到更好的性能

* Iterator<E> iterator():
返回一个迭代器来以合理的顺序来遍历列表,这里提供了对Iterator()的直接实现,依赖列表本身
的size(),remove(),get()这些方法,如果当前类没有提供remove()方法实现,那么Iterator()
方法会抛出UnsupportedOperationException

* ListIterator<E> listIterator():
构建一个列表迭代器返回，列表迭代器的起始位置是0

* ListIterator<E> listIterator(final int index):
这个方法直接实现了ListIterator方法,并且继承了Iterator()接口的实现方法
ListIterator()的实现依赖列表的set(),add(),get(), remove()方法，如果这些
方法未实现，就会抛出UnsupportedOperation

* ### Itr.Class
  这个类实现了Iterator()接口
  * int cursor: 下一次调用 next()方法返回的元素的索引
  * lastRet: 上一个被next()或者previous()方法返回的元素的索引,如果这个元素被方法删除了,那么
  就设成-1
  * expectedModCount: list因该有的 modCount的，如果这个数字改了，那么iterator就直到发生了并行修改
  * boolean hasNext():如果还有元素未遍历,那么就返回true，否则返回false.实现方法是比较curson是否等于size()
  * checkForComodification(): 检查当前的modCount是否和expectedModCount相等，不相等就说明发生了并行修改
  抛出ConcurrentModificationException
  * E next(): 返回列表中的下一个元素,首先是调用checkForComodification()，然后在通过
  get(int cursor)返回下一个元素,然后更新lastRet和cursor的值，返回元素,如果列表超界
  那么就现检查是否发生了并发修改,然后看抛出NoSuchElementException
  
  * void remove():先检查lastRet是否大于0,不然说明迭代器还没有迭代过，或者当前已经删除过某个元素了
  调用AbstractList的remove(int index)来删除一个元素,然后cursor减去1，重新复制expectedModCount，
  这也是唯一合法的可以在迭代器遍历过程中删除元素的方法

* ### ListItr.class
  这个类继承了Itr.class,并且实现了ListIterator的接口,这个类返回一个AbstractList的子类
  这些子类会在私有域里面存两个变量,一个是父类列表的偏移量,一个是列表的大小,还有支持列表的expectedModCount
  方法会返回两种变量, 如果一个类实现了RandomAccess接口,那么就会返回一个实现了RandomAcess接口的类的实例
  
  这些子类的各种操作方法都依赖父类，listIterator(int)的实现依赖列表本身的迭代器，本质上是一个列表迭代器
  的包装对象,子类的iterator()方法直接返回listIterator(),而size()方法返回私有域上的值
  
  所有的方法都会检查modCount是否等于expectedModCount，如果不等就直接抛出异常
  
  * List<E> subList(int fromIndex, int toIndex): 首先检查当前这个list是否实现了RandomAccess接口,实现了就返回一个
  对应类的实例,如果没有实现,那么就返回一个SubClass的实例.
  
  * boolean equals(Object o):检查两个对象是否相等,首先会检查对象是否是list对象,然后使用
  迭代器来遍历每个元素，要求两个链表对应位置的元素也相等,最后还要求两个链表的长度是等长的,不等长也会返回false
  
  * int hashCode():计算整个列表的哈希码,实现时
  ```
  int hashCode = 1;
  for (E e : this)
    hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
    return hashCode;
  ```
  * void removeRange(int fromIndex, int toIndex):将列表中的[fromIndex,toIndex)之间的元素删除将后续的元素全部左移，减少他们的index
  如果fromIndex和toIndex相等,那么就不会对列表产生影响.这个方会被这个类和子类的clear()方法所调用,可以重写来提高方法的性能.方法的实现中是采用
  是用fromIndex获取一个列表的迭代器，然后迭代删除给定的区间
  
  * int modCount:用来记录list经历过多少次结构性的修改,结构性修改是指改变列表大小的方法或者其他可能会导致列表迭代产生错误的方法
  这个属性用在iterator和listterator方法中,如果这个值发生了未预料的改动,那么就是抛出并发需改异常
  这提供了快速失败的特性,而不是返回不确定的结果,如果子类希望提供一个fast-fail的迭代器就要使用这个方法,就要使用这个属性
  在add()和remove()方法中要加不得超过1的数值,不然会产生bug.
  
  * void rangeCheckForAdd(int index):检查给定的Index是否超界,如果是就抛出异常
  * String outOfBoundsMsg(int index):返回错误的信息,想要寻找的index和当前列表的大小

* ### SubList<E>.class
    * final AbstractList<E> l: 父类AbstractList的列表实例,用来调用父类实现的方法
    * final int offset: 记录子列表从父类的起始的位置
    * int size:记录子列表的大小
    * SubList(AbstractList<E> list, int fromIndex, int toIndex):初始化子列表对象,另外记录此时父列表的modCount
    * E set(int index, E element):首先检查输入的index是否符合要求,然后检查是否存在并发修改,最后调用父列表的set()方法进行修改，在指定位置替换元素,
    这里要在原来的Index位置上加上offset
    * E get(int index):首先检查输入的index是否符合要求,然后检查是否存在并发修改,然后调用
    l.get(index+offset)来获取元素
    * int size():获取子列表的大小,直接返回属性的值就可以
    * void add(int index, E element):首先检查输入的index是否符合要求,然后检查是否存在并发修改,最后调用父列表的add(index, E e)
     进行添加元素,随后修改modCount，最后size++
    
    * E remove(int index): 首先检查输入的index是否符合要求,然后检查是否存在并发修改,最后调用父列表的remove(index + offset)来进行修改
    最后重新赋值modCount,最后修改size, size--;
    * void removeRange(int fromIndex, int toIndex):然后检查是否存在并发修改,最后调用父列表的remove(fromIndex + offset, toIndex + offset)
    来删除区间内的元素,最后重新赋值modCount,然后修改size大小
    
    * boolean addAll(Collection<? extends E> c): 这个方法实现时调用addAll(size,Collection c)
    来完成的
    
    * boolean addAll(int index, Collection<? extends E> c): 首先检查index是否合理,然后检查是否发生了并行修改
    然后调用父类的addAll方法来将集合添加到列表里,最后重新赋值modCount和size
    
    * Iterator<E> iterator(): 返回一个列表迭代器
    
    * ListIterator<E> listIterator(final int index): 提供一个列表迭代器，其中提供了具体的实现
    具体的迭代器的操作大部分依靠父类的列表迭代器来操作
    
    * List<E> subList(int fromIndex, int toIndex):构造一个SubList实例
    
    * checkForComodification():检查是佛福安生了并行修改
    
    ### RandomAccessSubList.class
    这个子类继承了SubList,并且实现了RandomAccess接口,当AbstractionList 子类实现了RandomAccess接口时
    就会构造一个这个类的实例返回
    
    * RandomAccessSubList(AbstractList<E> list, int fromIndex, int toIndex):
    构造一个实例
    
    * List<E> subList(int fromIndex, int toIndex):构造一个实例