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


