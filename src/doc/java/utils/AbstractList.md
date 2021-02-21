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
通过列表迭代器来迭代列表，直到找到第一个相等的元素,具体实现是通过