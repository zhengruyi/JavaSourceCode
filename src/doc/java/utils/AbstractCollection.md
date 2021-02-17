### AbstractCollection
这个类提供了Collection接口的骨架实现,尽可能的去减少实现这个接口所需要的努力

为了实现一个不可变的集合,只需要继承这个类，对提供iterator()和size()方法的实现
其中iterator()方法需要提供next()和hasNext()两个方法的实现

为了实现一个可变的集合,必须额外重写add()方法,不然抛出UnsupportedOperationException,
并且迭代器iterator()必须额外实现remove()方法

一般需要提供两个构造器,一个空参构造器,一个以Collection为参数的构造器

所有该类的所有非抽象的方法,提供了默认实现,子类可以重写来获得更高的效率

* abstract Iterator<E> iterator():返回一个迭代器
* abstract int size(): 返回集合中的元素个数
* static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8:最大的数组长度,由于一些VMs会预留
数组的头部信息,所以如果分配一个更大的数组,那么可能会导致Out Of Memory错误
* boolean isEmpty(): 判断集合是否为空,默认实现是`return size() == 0;`
* boolean contains(Object o):使用迭代器来遍历集合的元素，然后调用对象的equals()来判断元素是否相等
* Object[] toArray():返回一个连续数组,其中包含所有迭代器返回的元素,数组从下标0开始,数组的长度等于迭代器返回的元素数目
即使在遍历的过程中，元素的数目发生变化，也能返回正确的数组，这在一些允许并发修改的集合中会发生。集体实现会在开头
调用size()方法,返回元素的数目，然后将迭代返回的元素挨个填入,结束时判断迭代器是否遍历完,如果没有就扩容
扩大道原来的1.5倍,继续填入元素,结束后重新调整数组大小,本质上都调用System.arraycopy()方法来完成数组拷贝

* T[] toArray(T[] a):和上一个toArray()方法类似，但是多了一个参数,所以一开头会调用size()方法返回集合元素数目
然后比较传入的数组是否足够大,如果不是就是使用反射构造一个更大的数组的同类型数组来存入元素,如果传入的数组过长，那么会在末尾填入null

* T[] finishToArray(T[] r, Iterator<?> it):
当在toArray()方法中迭代器返回的元素数目大于给定的数组时,会尝试扩容数组为原来的1.5倍
然后继续填入元素，在最后再将数组后续多余部分切除掉.

* int hugeCapacity(int minCapacity):返回给定的数组的最大值，当发生溢出时返回错误信息,不然就
判断 minCapacity是否大于 MAX_ARR_SIZE = Integer.MAX_VALUE - 8,是就返回Integer.MAX_VALUE,不是就返回
MAX_ARR_SIZE

* boolean add(E e): 添加元素，默认实现是抛出不支持异常

* boolean remove(Object o):
运用集合的迭代器来遍历元素,并使用equals()方法来比较元素是否相等，找到相等的元素
那么就是用iterator的remove()方法来删除元素,如果集合的迭代器不支持remove()方法
那么就抛出UnsupportedOperation

* boolean containsAll(Collection<?> c):
采用循环遍历集合中的元素,对于每个元素调用contains(Object o)来判断是否在集合中存在
如果所有元素都存在,那么返回true,不然返回false

* boolean addAll(Collection<? extends E> c):
采用循环来遍历集合中的元素,对于每个元素调用add()方法来进行添加元素,如果有一个添加成功
就会返回true

* boolean retainAll(Collection<?> c):
删除所有不再集合中的元素,实现采用迭代器来遍历，如果不存在给定的集合中，那么就调用
iterator的remove()方法来移除.只要移除一个元素就返回true

* void clear():
用迭代器方法去挨个移除里面的元素,大多数实现会为了效率选择重写这个方法

* String toString():
返回字符串形式的集合情况,用方括号来包含内部元素,对于每个内部元素调用它的toString()
方法,内部元素之间用","分隔。

