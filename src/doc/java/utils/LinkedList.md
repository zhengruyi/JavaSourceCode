### LinkedList.class
双向链表实现了List和Deque接口,实现了所有的List的Optional Operation并且允许
所有的元素,包括Null.所有的操作预期都是双向链表,对于Index操作，则会从头开头或者尾部
开始向特定index进行行进。

LinkedList并不是同步的，所以如果有多个线程可能同时访问这个链表,且其中有一个可能会存在
结构性修改,那么必须交进行外部同步,一般是通过外部天然存在的一个对象通过同步锁Synchronized来实现
如果外部不存在这样一个对象,那么一般使用Collections.synchronizedList(new LinkedList())来避免偶然的
未同步的访问

迭代器的fast-fail特性还是和以往一样,不能够被硬性保证,只能尽最大努力抛出,所以
任何依赖fast-fail特性编写的代码都是错误的

LinkedList类继承AbstractionList,并且实现了List,Deque,Cloneable和Serializable接口

* int size:列表的长度
* Node<E> first:指向列表的第一个节点
* Node<E> last:只想最后一个节点
* public LinkedList():空的构造方法
* public LinkedList(Collection<? extends E> c):构造一个链表来存放给定集合中的元素,链表中的元素顺序是集合的迭代器返回元素的顺序
  实现过程是先构造一个空的列表，然后调用addAll()方法将所有集合中的元素添加到链表中

* private void linkFirst(E e):将给定的元素e连接成第一个元素,实现过程中,先创建一个新的节点
然后将旧的头结点作为后继节点,最后根据原先的头节点是否是Null,分两种情况处理,如果是说明原先的链表是空的
那么就将last指针也指向这个节点,表示整个链表只有这一个节点,如果旧的节点不是null,那么将执行first.prev= newNode
将前向指针赋值,增加size = size + 1,然后调整modCount

* void linkLast(E e):创建一个包含新的元素e的节点作为链表结尾,和上一个方法也是分两种情况处理,如果原先last = null
那么表示原来的链表为空,则将first指针也指向这个新的节点,如果原先的last不是null,那么就执行l.next = newNode,然后调整size和modCount的
值

* void linkBefore(E e, Node<E> succ):将包含元素e的节点插入到succ的节点之前,首先保存succ的前直接点
用元素e创建一个新的节点Node,然后将连接到succ前面,如果succ的前置节点是null,那么就将first指针指向新的节点
不然就将新的newNode的next指针指向succ,调整size和modCount

* E unlinkFirst(Node<E> f):首先将first节点的后继节点用next保存,然后将first当前的item赋值成null,Next指针也做一样的处理
这样做是为了帮助GC可以更好收集垃圾,根据next是否为null,分两种情况处理,如果是说明当前的链表已经为空
那么将last也赋值成空,如果不是就将next.prev赋值成null,最后调整size和modCount

* E unlinkLast(Node<E> l):将链表的最后一个元素移除,和上一个方法一样，先保存前面一个节点
然后将移除的节点的item和next指针赋值成null来帮助gc更好的收集,最后如果前面一个节点是null,那么就
将first也指向这个节点来来表示链表为空,否则就将前一个节点的next指向null,来表示到达链表末尾

*  E unlink(Node<E> x):将节点x从链表中移除,实现首先将前后节点都固定一下,然后先检查前面的节点，是否为null
就将 first = next,因为前面节点为空，且当前节点也要被删除,如果不是就调整指针,对于后继节点next的处理也类似
如果next为null,那么就将last = prev，最后将item.val = null来提示GC。最调整size和modCount

*  E getFirst():返回链表的第一个节点,如果first为null,表示链表为空,抛出NoSuchElememntException
，否则返回first指针指向的节点

* E getLast():返回链表的最后一个元素,和上一个方法的实现类似,先检查last指针是否是null,如果是就抛出异常
否则直接返回last指针指向的元素

* E removeFirst():移除链表的的第一个元素,先检查first指针指向的节点是否为null,如果是就抛出异常
否则调用unLinkFirst()来移除链表的第一个元素

* E removeLast():移除链表的最后一个元素,先检查last指针指向的节点是否为null,如果是就抛出异常
否则调用unLinkLast()来移除链表的第一个元素

* void addFirst(E e):将某个元素插入到链表头部,具体实现调用linkFirst()方法来进行添加

* void addLast(E e):将某个元素插入到链表尾部，调用linkLast()来具体实现添加

* boolean contains(Object o):检查链表中是否包含有特定的元素,具体的只有链表中至少包含一个equals()方法
返回true的元素时才会返回true,实现是通过indexOf()方法的返回值不等于-1来实现的

* int size():获取链表的长度,直接返回size属性的值

* boolean add(E e):将元素e添加到链表末尾,具体是通过linkLast()方法将元素添加到链表末尾

* boolean remove(Object o):将索引最小的相等的元素移除,如果链表中没有包含相等的元素
那么对链表不会有任何改变,实现是先检查对象o是否为null,如果是遍历链表就检查链表中是否存在x.item == null
如果o不是null，就用equals()方法来检查两个元素是否相等，具体实现是用unlink()方法来
实现链表元素的移除

* boolean addAll(Collection<? extends E> c):将给定集合按迭代器返回的元素顺序将这些元素插入链表的末尾
，具体的实现是通过调用addAll(size,c)方法来实现添加。

* boolean addAll(int index, Collection<? extends E> c):将集合中的所有元素添加到链表的index位置后面,index后面的
所有元素都顺序后移,实现中先检查索引是否合法,然后找到索引位置的节点,这里分两种情况index = size时表示插入到链表末尾,这时表示
当前节点的succ为null,后续就是将集合中的元素取出,挨个插入链表,最后根据Index是否等于size
调整last指针，更新size += newNum和modCount的值

* void clear():将链表里面的所有元素的值都删除,在这行这个函数后,链表回到空状态
实现就是遍历链表,将每一个链表节点取出来将值和指针都设置成null,方便gc收集，最后将last和first
指针重置成null,来表示链表为空,size 设置成0

* E get(int index):获取链表中某个位置的元素,具体实现是检查完索引后,用node(int index)方法
来获取特定位置的节点,然后返回节点的值

* E set(int index, E element):将index位置处的链表节点的元素更新成element,并返回旧的值
具体操作是通过node()方法来定位到index处的来表节点，然后将item属性替换成新的值,注意更新值
不会涉及到modCount的改变

* void add(int index, E element): 创建一个新的链表节点,包含元素element
并将这个链表节点插入到Index处,具体实现是检查完索引后,检查index是否等于size,如果等于就直接调用
linkLast方法，如果不等于就调用linkeBefore(element,node(index))方法来将新的节点插入到某个指定的节点前面

* E remove(int index):将特定Index位置的链表节点删除,具体实现是通过node(index)来返回
特定位置的节点,然后通过unlink()方法将这个链表节点从链表中删除

* boolean isElementIndex(int index):检查index的索引处是否现存元素,检查的标准是
要求Index >=0 && index < size

* boolean isPositionIndex(int index):检查给定index是否可以插入元素,判断标准是
index >=0 && index <= size

* String outOfBoundsMsg(int index): 返回越界的信息

* void checkElementIndex(int index):检查搜索元素的索引是否合法,内部调用 isElementIndex(int index)
进行检查

* void checkPositionIndex(int index):检查这个位置是否可以插入元素,内部用isPositionIndex(index)
来进行判断

* Node<E> node(int index):将特定换位置的链表节点返回,因为内部是双向链表,所以根据Index和size/2的关系,
会考虑从头往后搜索还是从尾部向头部搜索

* int indexOf(Object o):搜索给定的元素的下标,从头部开始往后搜索，根据o是否为null分成两种情况讨论,
最后返回第一次找到的链表节点的下标,如果找不到这个元素,就返回-1

* int lastIndexOf(Object o):从后往前搜索,找到第一个相等的元素,返回对应的下标,如果找不到就返回-1

* public E peek():获取链表的第一个元素,但是不移除这个头部元素,根据first是否为null,来决定返回的元素
first为null,则返回null.不然就返回item属性的值

* public E element():和上一个方法一样,都是获取一个链表头部的第一个元素,但是不移除

* public E poll():移除链表的第一个头部元素,并且返回移除的节点的值,实际的实现是调用
unlinkFirst()方法将头结点移除的

* public E remove():移除链表头部的第一个节点,并且将被移除的节点的值返回,具体
实现是通过 removeFirst()方法来实现的

* public boolean offer(E e):将特定的元素e添加到链表的末尾,具体实现是通过调用add()方法
来将元素添加到末尾的

* public boolean offerFirst(E e):将特定的元素e添加到链表的头部,内部是通过调用addFirst()
方法来进行实现的

* public boolean offerLast(E e):将特定的元素e添加到链表的尾部,true表示插入成功

*  public E peekFirst():返回链表的第一个节点的元素，但并不移除这个节点,如果链表为空
那么就返回null

* public E peekLast():返回链表的尾部元素,但并不移除,如果链表是空的,那么就返回null
* public E pollFirst():返回链表的头部元素,并且移除这个元素,如果链表为空就返回null,具体是通过unlinkFirst()来进行
操作,将链表的元素移除的
* public E pollLast():返回链表的尾部元素,并且移除这个元素,如果链表为空就返回null,具体是通过unlinkLast()来进行
操作,将链表的元素移除的.
* public void push(E e):将数据压入栈,换句话说将元素e压入链表的头部,内部是通过addFirst(e)来进行操作的
* public E pop():将栈顶的元素移除,换句话说,移除链表头部的第一个元素,内部通过removeFirst()方法进行操作
* public boolean removeFirstOccurrence(Object o):移除数组中一个和给定元素相等的链表节点,如果链表中没有节点符合和给定目标相等的节点
那么整个链表保持不变.具体的内部实现是通过remove(o)方法来实现的。


