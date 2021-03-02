### ArrayList.class
一个可改变大小的实现了List接口的数组,实现了列表的所有可选操作,允许添加所有的元素
包括null,这个类也提供了方法来操作存储列表元素的数组大小的方法，和Vector类相比，ArrayList
并不是线程安全的

size(),isEmpty(),get(),set()这些方法的运行时间是个常数,add()方法的运行时间是
摊销固定时间,所有其他的操作都需要线性时间,ArrayList的factor比LinkedList的factor要低

每个ArrayList的都有容量,这个容量是Arraylist内部用来存储列表元素的数组的大小,它一般至少是和
list size一样大,当元素被加入到list中时,list会自动增加容量,容量增长的细节依赖摊薄固定时间上

一个应用可以在加入大量数字前,使用ensureCapacity()方法来提升数组容量,这可以帮助减少扩容的次数

ArrayList不是线程安全的，所以如果多个线程希望同时操作一个实例,且其中至少有一个会做出结构性的改变时
则必须要进行同步,可以进行外部同步,一般通过对自然封装列表的某个对象进行同步来完成此操作

如果没有这么一个对象存在,那么这个对象需要被Collections.synchronizedList包裹,一般是在创建时包裹
来防止意外的不同步 `List list = Collections.synchronizedList(new ArrayList(...));`

这个类返回的iterator()和listIterator()方法都是fast-fail的，如果在迭代器创建后发生了除Iterator和ListIterator
的add()和remove()方法以外的结构性修改,那么迭代器就会抛出fast-fail失败,来避免做出不确定的行为

此外迭代器的fast-fail不能被保证,换句话说不再非同步并发修改时硬性保证fast-fail出现,迭代器会尽最大努力来
保证抛出并发修改异常,随意依赖这个异常来写代码是不对的,它只能用来检测bug

这个类继承了AbstractionList,实现了RandomAccess,Cloneable和Serializable接口

* static final int DEFAULT_CAPACITY = 10:默认的初始容量

* static final Object[] EMPTY_ELEMENTDATA = {}:所有空的列表实例所共享的一个空的实例

* static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {}:空的数组实例用来给默认大小的空的实例所共享的
我们把它和EMPTY_ELEMENTDATA区分开为了知道当地一个元素添加时，需要扩充的容量

* transient Object[] elementData: 数组缓存池来保存列表中的元素,ArraysList的容量就是这个数组的长度
任何DEFAULTCAPACITY_EMPTY_ELEMENTDATA类型会在第一个元素被添加时自动扩容到默认的容量大小Default_CAPACITY

* int size:列表中的元素数目

* public ArrayList(int initialCapacity):创建一个具有特定其实容量的空的列表
如果这个其实容量大于0那么就将创建一个指定大小的Object数组赋值给elementData,如果设定的其实容量等于0，那么
将elementData赋值成Empty_ELEEMNTDATA

* public ArrayList():创建一个默认初始容量为10的空的列表

* ArrayList(Collection<? extends E> c):根据给定集合的迭代器的返回元素的顺序创建一个新的Object数组,首先用toArray()
方法装换成数组,然后如果数组长度为0，也就是集合为空,那么将数组赋值成EMPTY_ELEMENTDATA，不然在判断装成的数组后会将元素拷贝到ElementData
中来获得一个指定大小的Object数组

* void trimToSize(): 将ArrayList的实例的容量调整为实际的元素数目,如果实际的元素size = 0,
那么就使用共享的EMPTY_ELEMENTDATA,如果size > 0,那么就用Arrays.copyof将原来的数组拷贝一份,缩小成固定的大小

* void ensureCapacity(int minCapacity):首先确定minCpacity,检查当前的elementData是不是DEFAULTCAPACITY_EMPTY_ELEMENTDATA,
不是的话minCapacity是0,是的话默认取最小容量10.判断给的参数和求的minCapacity哪个更大,取其中的最大值

* int calculateCapacity(Object[] elementData, int minCapacity):取默认容量和minCapacity中的最大值就可以

* void ensureCapacityInternal(int minCapacity):取默认容量和minCapacity中的最大值,然后调用ensureExplicitCapacity
取调整数组的容量

* void ensureExplicitCapacity(int minCapacity):将用于记录数组结构性改变的modCount加1,然后在这个最小容量大于数组的长度时,
调用grow()方法去进行数组扩容

* final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8:记录的数组的最大容量,由于某些vm会给数组预留头部,
分配更长的数组会导致内存溢出错误

*  void grow(int minCapacity):给数组扩容,保证数组的容量至少是数组中的元素数目
实现过程中会首先获取当前的数组容量,然后扩容1.5倍,比较扩容后的数组容量和给定的最小容量，取两者的最大值
判断扩容后的容量是否超出了最大的容量上限,如果给定的minCapacity小于0，那么就发生了溢出,需要抛出异常
如果计算后的newCapacity 大于容量上限就取上限,然后扩容数组到指定容量

* int hugeCapacity(int minCapacity):如果minCapacity 小于0就说明发生了溢出,那么抛出内存溢出异常
如果minCapacity 大于虽大的长度就返回整数的最大值,否则返回最大的数组长度

* int size():返回数组的长度

* boolean isEmpty():判断数组是否为空

* boolean contains(Object o):当列表中包含至少一个相同的元素时返回true,实现过程中是
通过indexOf()方法来实现,判断indexOf()返回的下标索引是否大于等于0

* int indexOf(Object o): 在数组中搜索和给定元素相等的元素,并且返回指定的下标
如果不存在就返回-1

* int lastIndexOf(Object o):反向搜索列表,从列表末尾开始搜索指定的元素,如果存在就返回指定的下标
不存在就返回-1

* Object clone():返回一份数组的拷贝,实现是由父类创建一份列表的拷贝,然后在将原列表的数组
拷贝一份赋值给新的列表的数组属性,同时将新的列表的modCount属性设置成0

* Object[] toArray():返回一个以合理顺序包含全部列表元素的数组,返回的数组必须是和原来的数组完全没有关系的
换句话说,必须创新分配一个新的数组

* T[] toArray(T[] a):根据给定的数组类型返回一个特定顺序返回的包含所有列表元素的数组
如果给定的数组长度小于实际的数组长度,那么就返回列表的数组长度,如果给定的列表长度更长,就在
数目填满后将size位置设置成null

* E elementData(int index):返回某个数组某个位置的元素