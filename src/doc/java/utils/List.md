### List
list是一个有序集合，这个集合允许对每个要插入的元素有更加精细的控制，使用者可以通过整数
下标来访问元素，或者在list中搜索这个元素

与Set不同,列表一般允许重复元素,更具体的来说,list允许存在两个元素e1,e2.他们之间满足
e1.equals(e2). 如果List允许存放null元素,那么list可以包含多个null元素,当然可以通过
抛出运行时异常这些方法来组织插入相同的元素,但是比较少见

相比较Collection接口,List接口在iterator(),add(),remove(),equals()和hashcode()
这些方法上添加了一些附加规定,其他继承方法的申明也为了方便放在这里

List接口提供了四个方法来通过定位来获取信息，列表和数组相似都是下标从0开始,在某些实现中
所需要的时间可能和下标值成比例,比如LinkedList,所以当调用者不知道细节时,可以通过迭代器
来访问逐个访问列表元素

List提供了一个特殊的迭代器ListIterator,在普通迭代器的基础上，新的迭代器允许允许元素的插入
和替换,以及双向搜索列表.有一个方法可以从某个特定位置开始提供ListIterator

List接口提供了两个方法去搜索对象,出于性能的考虑，方法需要谨慎使用,因为在大多数列表中
搜索都是开销很大的线性搜索

List接口提供了两个方法来高效的插入和高效的在列表的任意节点上移除多个元素.

list里面包括的元素有很多限制,有些获取不允许Null元素出现,有些对元素类型有限制，
尝试加入不合格的元素会抛出UncheckedException一般来说是NullPointerException和
ClassCastException,尝试查询不合格元素可能导致抛出异常或者直接返回false.


* int size():返回列表的大小,如果列表的长度大于Integer.MAX_VALUE,那么就返回Integer.MAX_VALUE.
不然就直接返回实际的长度

* boolean isEmpty(): 如果列表包含元素就返回false,列表为空就返回true
* boolean contains(Object o):检查列表是否有元素来满足(o==null ? e==null :o.equals(e))
* Iterator<E> iterator():返回一个迭代器,来以合适的顺序来返回列表元素
* Object[] toArray():将列表元素以数组形式返回
* T[] toArray(T[] a):将列表元素以特定形式的数组返回,和Collection接口相同
* boolean add(E e): 将元素e添加到列表末尾,有些list的实现对元素有限制,比如有些列表不允许添加Null元素
有些实现对要插入的元素类型有严格的限制

* remove(Object o):移除满足条件(o==null ? e==null :o.equals(e))的下标最小的一个元素,如果不存在
这么个元素,那么列表没有改变.如果移除了元素就返回true,否则返回false

* boolean containsAll(Collection<?> c):
如果集合c中的元素都存在列表中,那么就返回true,否则返回false

* boolean addAll(Collection<? extends E> c):
将集合c的所有元素都插入到列表末尾，元素的插入顺序和集合c的迭代器的实现有关

* boolean addAll(int index, Collection<? extends E> c):
将集合c中的元素插入到特定的位置,这个位置的原来的元素和所有后续元素都会向右移动

* boolean removeAll(Collection<?> c):
将集合c中的所有元素从当前集合中移除,成功就返回true

* boolean retainAll(Collection<?> c):
将集合中的所有不在集合c中的元素删除

* void replaceAll(UnaryOperator<E> operator):
通过对集合的每一个元素应用operator来替换掉集合的每一个元素,错误或者运行时异常会延迟到调动着抛出

* void sort(Comparator<? super E> c):
    * 根据给定的特殊的Comparator对List进行排序
    * 对于list的任意两个元素，必须可以互相比较,也就是不会抛出ClassCastException
    * 如果列表的Comparator是null，那么所有的元素必须实现了Comparable接口,那么就会使用元素的自然排序来排序
    * 列表必须是可以更改的，但不是必须是可以调整大小的
    * 默认实现是创建一个数组，然后对数组进行排序，然后使用迭代器对列表进行遍历，将对应位置的元素
    设置成数组对应位置的元素,这样可以避免对于LinkedList排序的N^2long(n)的时间复杂度
    * 以上的实现是一个稳定的适配的可迭代的归并排序,当数组部分有序时，需要的比较次数远小于
    nlog(n)，当数组是随机乱序的，那么会提供传统的归并排序的效能,如果数组几乎是有序的，那么这种实现
    需要近似n次比较
    * 这种实现需要几乎对升序和降序的排序有同样的优势,并且可以很好利用输入数组中的不同的升序或者降序序列
    所以这种实现很适合合并多个有序的序列,简单的将数组合并起来，然后对结果数组进行排序

* void clear():
移除列表中的所有元素,在执行完这个方法后，列表会变成空的

* boolean equals(Object o):
只有两个列表以相同的顺序保存了相同的元素,那么这两个列表可以被认为是相等的,这种列表
可以保证对所有的List实现来说,equals()方法可以正确的工作

* int hashCode():
针对列表返回具体的哈希值,计算方法如下
```
int hashCode = 1
for (E e : list)
    hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
```
这能保证对于任意两个list1.equals(list2)返回true的方法,他们的哈希码的返回值也相同

* E get(int index):
返回特定位置的元素

* E set(int index, E element):
将特定位置index处的元素设置成element

* void add(int index, E element):
在制定的位置index处，插入元素,所有后续的元素都需要顺序往后移动

* E remove(int index):
将指定位置的元素移除,然后所有后续的元素都将左移,并且返回被移除的元素

* int indexOf(Object o):
返回数组中第一次出现指定元素的下标位置,如果指定的元素不存在数组中，那么返回-1，如果
这个元素出现了多次,那么返回的最小的那个下标

* int lastIndexOf(Object o):
返回数组中第一次出现指定元素的下标位置,如果指定的元素不存在数组中，那么返回-1，如果
这个元素出现了多次,那么返回的最大的那个下标

*  ListIterator<E> listIterator():
返回一个列表迭代器来比那里列表中的元素

*  ListIterator<E> listIterator(int index):
返回一个从某个特殊位置开始的列表迭代器,这个特殊的位置代表迭代器第一次第一次调用next()方法返回的元素
而调用previous()方法会返回这个索引的前一个元素

* List<E> subList(int fromIndex, int toIndex):
返回一个原列表的部分视图,范围是[fromIndex, toIndex],如果fromIndex和toIndex是相等的
那么返回的列表就是一个空的列表，由于返回的列表还是建立在原链表的基础上,所以对原链表的修改也会
反应到这个链表上，反之亦然. 如果在返回视图后，原链表发生了结构性改变(比如说结构性的改变),那么方法的
运行结果无法定义

* Spliterator<E> spliterator():
返回一个Spliterator来遍历集合的元素,默认实现从迭代器创建了一个懒绑定的spliterator()
它继承了迭代器的fast-fail属性.