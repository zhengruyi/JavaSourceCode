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
* E get(int index): 返回列表某个位置的元素,首先需要验证索引范围是否合理
* E set(int index, E element):将某个位置上的元素替换成某个新的元素
* boolean add(E e): 首先会检查数组是否容量足够,这里会调用ensureCapacity(int minCapacity)
来确保容量,然后将新的元素添加到数组末尾,modCount++

* void add(int index, E element): 将元素添加到指定位置,实现中首先先进行数组范围检查，然后检查数组是否剩余容量
然后用System.arraycoppy()方法将后半段数组拷贝一下,最后在指定位置放入元素,结束操作,modCount++

* E remove(int index):移除指定位置的元素,首先进行rangeCheck(),modCount++,计算后续要移动的元素
数目,将数组的后半段元素往前拷贝一步,最后将末尾的元素赋值成null来通知GC进行回收

* boolean remove(Object o):移除数组中的某个元素,如果数组中不包含指定元素,那么什么都不会改变,
如果列表中存在多个元素,那么就移除数组下标最小的那个元素,在实现实现过程中,先在列表中找到那个特定的元素
再调用fastRemove()方法来删除指定位置的元素,本质上就是先增大modCount,然后调用System.arraycopy()来
来批量移动元素,最后将末尾的元素改成null，来通知来及收集器收掉这个对象

* void fastRemove(int index):移除操作的实际执行着,本质上就是先增大modCount,然后调用System.arraycopy()来
来批量移动元素,最后将末尾的元素改成null，来通知来及收集器收掉这个对象

* void clear():将数组的元素全部清空,实现使用for循环将数组的每个位置都变成null,将size变成0

* boolean addAll(Collection<? extends E> c):将集合中的元素都放入列表末尾,顺序使集合迭代器返回元素的顺序
实现是首先将集合转化成数组,然后获取数组容量,检查数组容量是否足够,不够就扩容,最后用System.arraycopy()
方法拷贝元素放入数组末尾,更新数组的长度。

* boolean addAll(int index, Collection<? extends E> c):将集合的元素全部插在指定位置后面,所偶遇后续元素
往右移动,计算后半段要移动的元素,实现过程是将后半段元素移动,然后将集合的元素拷贝到指定的位置

* void removeRange(int fromIndex, int toIndex):移除区间[fromIndex,toIndex)之间的元素
实现是计算数组删除后剩下的元素,然后直接调用system.arraycopy()将元素覆盖掉,最后用将数组末尾无用的元素
全部赋值成null,调整数组大小

* void rangeCheck(int index):检查索引是不是超出数组界限,大于时抛出异常IndexOutOfBoundsException
* void rangeCheckForAdd(int index):给add和addAll()方法进行范围检查,这里Index == size是可以的，表示将元素加到数组末尾
* String outOfBoundsMsg(int index):构建数组越界IndexOutOfBoundsException的错误信息
* removeAll(Collection<?> c):将集合的所有元素都从列表中移除,实现中首先检查Collection是否为null,然后
调用batchRemove()方法来实现批量移除操作

* boolean retainAll(Collection<?> c):将列表中所有不再集合中的元素都移除,内部实现也是交给batchRemove()方法移除的

* boolean batchRemove(Collection<?> c, boolean complement):删除的真正的实现方法,桌和遍历数组中的元素,挨个去检查集合c
中是否存在这个元素,根据存不存在来决定元素是否删除,用一个变量来只是有多少个元素保留下来,简化末尾的元素全部置成null来让gv进行收集
最后调整modCount, modCount += size - w;调整size = w,然后返回结果

* void writeObject(java.io.ObjectOutputStream s):将数组的状态保存为数据流,用循环将列表的元素
挨个写入列表中,最后检查是否发生了并发修改

* void readObject(java.io.ObjectInputStream s):从数据流中重建一个ArrayList
* ListIterator<E> listIterator(int index):从Index位置构建一个列表迭代器，Index代表迭代器第一次要返回的元素,previous()
将会返回Index - 1的元素

* ListIterator<E> listIterator():返回一份从Index = 0开始的列表迭代器
* Iterator<E> iterator():返回一个迭代器来遍历列表
* ###Itr.class
    * 这是一个AbstractList.Itr的优化的迭代器
    * int cursor:下一个要返回的元素坐标
    * int lastRet:上一个返回元素的下标,如果没有就返回-1
    * int expectedModCount:创建时的结构化修改的次数
    * boolean hasNext():判断cursor是否等于size
    * E next():返回迭代器的下一个元素,首先检查并发修改,然后检查是否到了数组末尾,如果是就抛出NoSuchElementException
    检查当前下标是否超过列表内部的数组的长度,是则发生了并发修改,最后更新lastRet和cursor，返回元素
    * void remove():使用迭代器来删除返回的元素,首先检查并发修改,然后用remove()删除某个元素,然后用
    cursor 修改成lasrRet,将lastRet修改成-1,因为每次next()方法后只能调用一次remove()
    重新复制expectedModCount = modCount
    
    * void forEachRemaining(Consumer<? super E> consumer):对每个迭代器中的剩余为遍历的元素,都应用consumer的accept()的方法
    首先检查consumer不是null,在数组还有剩余元素且没发生并发修改时,就对每个剩余的元素都应用consumer接口的accept方法
    
    * void checkForComodification():检查是否发生了并发修改
    
* ###ListItr.class
    * 这是对AbstractList.ListItr的优化实现
    * boolean hasPrevious():看前方是否还有元素,实现方法是判断cursor是否等于0
    * int nextIndex():返回下一个返回元素的下标,实现是返回cursor的值
    * int previousIndex():返回上一个返回元素的下标,值等于cursor - 1
    * E previous():返回上一个元素,实现是检查边界，检查并发修改,然后修改cursor的值和lastRet的值,
    将数组中上一个位置的元素返回
    * void set(E e):讲一个返回的元素修改成给定的元素,实现是先检查边界范围,然后检查并发修改,最后调用本类中的
    set方法来修改某个元素
    
    * void add(E e):将某个元素加到当前位置,然后调整cursor+=1的位置，并且将lastRet设置成-1
    重新赋值expectedModCount

*  public List<E> subList(int fromIndex, int toIndex):返回一个列表的部分视图,返回的部分视图基于原来的链表,
所以远啊来链表的任何非结构性改变，都会反馈到部分列表的视图上,部分列表的视图消除了明确的范围操作,任何的范围操作都可以通过将
整个列表用部分列表来替代来达到正常的操作,视图支持所有的Collection的算法,但是如果支持列表发生了结构性变化,那么视图的操作
就会变的未知

* static void subListRangeCheck(int fromIndex, int toIndex, int size):检查要生成的视图范围是否合法

* ###SubList.class
    * 这是一个继承AbstractList并且实现了RandomAccess接口的类,只要作用就是生成部分列表的视图
    * final AbstractList<E> parent:指向父类AbstractList对象的指针
    * final int parentOffset:当前部分视图相对于父类视图的偏移量
    * final int offset:部分列表内的偏移量
    * int size:部分列表内部的元素数目
    * SubList(AbstractList<E> parent,int offset, int fromIndex, int toIndex):构造方法
    * E set(int index, E e):和父类实现一样,只不过内部把index替换成了offset + index
    * E get(int index):内部把index加上offset来获取对应位置的元素
    * int size():返回视图内部的元素数目
    * void add(int index, E e):添加一个元素,在内部将index 加上了parentOffset
    * E remove(int index):移除Index位置处的元素，调用父类的移除方法,并在Index上增加parentOffset
    size -= 1和调整ModCount
    * void removeRange(int fromIndex, int toIndex):移除范围内的所有元素,本质还是调用父类方法加上固定偏移量,随后调整
    modCount和size。




