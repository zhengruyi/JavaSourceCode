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

