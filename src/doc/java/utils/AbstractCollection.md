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

