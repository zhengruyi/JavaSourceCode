### Collection
Collection表示一组数据的集合,可能包含重复元素也可能不包含,元素可能有序或者无序,java没有
提供任何直接的实现，更多的是提供了对它的子接口的实现,当我们需要获取集合最大的泛用性时
才会使用这个集合接口

实现这个接口的类需要提供两个标准的构造函数,一个空参的构造函数用来构建一个空的集合，一个
构造函数使用一个Collection类型的参数用来用造一个和Collection类型参数类型相同且所有元素
都相同的集合的拷贝,因为是集合所有没有办法强制要求,但是所有实现这个接口的类都应该遵循这个规则

一般来说,对于修改集合的方法,如果Collection不支持这个方法会抛出UnsupportedOperationException
但是对于一些不影响集合元素的方法调用,就不一定要求抛出异常,比如说对于不可变集合对象,以空集合作为
参数调用addAll()方法，就不强求抛出异常

不同的集合实现对于里面的元素拥有不用的限制,有些集合不允许加入null元素,有些集合对他们
元素的类型有限制,强制加入不合法的的元素可能会导致 NullPointerException或者ClassCastException
尝试去查询不合法的元素可能会导致抛出异常或者简单的返回false.总而言之尝试插入非法元素不会导入非法元素
被成功插入,至于是否抛出异常,这部分在集合的说明中是可选项


每个集合自己决定同步策略,如果没有一个强力的同步策略保证,那么可能会导致在集合上调用的方法
会导致未定义的行为,包括直接调用,把集合传递给一个方法或者使用迭代器遍历集合(fast-fail)

集合的许多方法建立在Object方法的equals()方法上,比如对于contains(Object o)方法,当集合中存在
至少一个元素时就会返回true,判断相等的条件是(o==null ? e==null : o.equals(e)),但是不应该这么做
因为这会导入对集合中的每个元素都应用o.equals(e),实现过程中可以对这部分自由的进行优化,
比如先用hashcode()方法进行比较,因为如果两个对象相等那么他们的哈希码则一定相等。总之集合的实现可以自由
的采用Object方法的优秀实现

大部分集合在遇到迭代遍历过程中的自我引用的问题时会抛出异常,实现接口的类可以选择性的处理
这种情况,但是当前的大部分实现并没有处理这种情况.

默认的方法实现并不支持各种同步协议,如果集合要实现某种同步协议,那么就需要重写全部的默认
实现来支持这个协议.

* int size():
返回集合内部的元素数目,如果集合内部的元素数目超过Integer.MAX_VALUE,那么就返回Integer.MAX_VALUE

* boolean isEmpty():
如果是空集合就返回true,不然就返回false

* boolean contains(Object o):
如果集合中至少存在一个元素满足以下: (o==null ? e==null : o.equals(e)),如果以上表达式返回true
那么函数最终返回true.

* iterator():
返回一个迭代器用来遍历元素,但是不保证元素的返回顺序(除非集合实例提供了迭代的顺序保证像list)

* Object[] toArray():
返回一个包含集合内部所有元素的数组，如果这个集合提供了迭代器的元素返回顺序,那么数组内部的元素顺序必须和迭代器的返回顺序一致
返回的数组必须保证引用安全,换句话说，即使集合内部用数组来保存数据，你需要创建一个全新的数组来保存元素
这样用户可以随便修改元素的值

* T[] toArray(T[] a):
    * 如果给定的类型满足集合的运行时类型那么就会返回一个特定类型数组,否则就会分配一个和集合大小相同的
运行时类型的新数组
    * 如果提供的特定类型的数组大于集合内部的元素数量时,那么会在安排完集合内部的所有元素后
在末尾填入Null,在确定集合内部不包含null时，这可以用来确定确定集合内部的元素数量

    * 如果这个集合提供了迭代器的元素返回顺序,那么数组内部的元素顺序必须和迭代器的返回顺序一致
    * 和toArray()方法类似，这个方法用来作为桥梁来沟通依赖数组的api和集合依赖的api,未来这个这个方法用来精细化控制返回的数组类型,
      或者在某种情况下用来节省分配开销
    * 如果集合中只包含string类型的元素,那么以下的代码可以用来将这个集合转变成一个新的分配的String数组 `String[] y = x.toArray(new String[0]);`
    * 如果给定数组的运行时类型不是集合中所有元素运行时类型的父类型,那么就会抛出ArrayStoreException
    如果给定的数字是Null,那么就会抛出NullPointerException

* boolean add(E e):
    * 确保集合包含了特定的元素(可选)，如果这个操作改变了集合,那么返回true,返回false,如果集合不允许重复或者已经包含了特定的元素
    * 集合可以对要加入集合的元素进行限制,有些集合可能不允许添加null，另外的集合可能对要加入集合的元素的类型加以限制,集合需要对任意的限制条件加以文档说明
    * 如果集合是因为除了集合已经包含元素外的其他原因而拒绝加入特定的元素类型，那么就必须要抛出异常而不是返回false
    这能保证在成功运行这个方法后集合中一定包含这个特定的元素
    * 集合不支持add则抛出UnsupportedOperationException，如果元素的类型不能被加入时抛出ClassCastException
    * 如果集合不支持null,但加入的元素是Null时，抛出NullPointerException，如果元素的属性导入不能被加入集合抛出IllegalArgumentException
    * 如果由于插入限制导致这个元素不能被加入需要抛出IllegalStateException

* boolean remove(Object o):
从这个集合中移除某个元素的单个实例，更一般的情况是移除满足一个以下的条件的
元素`(o==null ? e==null : o.equals(e))`,如果集合中存在一个或者多个这样的元素
那么方法返回true

*  boolean containsAll(Collection<?> c):
如果这个集合中包含所有集合c中存在的元素，那么这个方法就会返回true

* boolean addAll(Collection<? extends E> c):
将特定集合中的元素添加到这个集合中,当在方法的运行过程中这个特定集合被修改时
方法的返回结果无法预知,比如当前这个集合非空，那么在调用方法添加自己时，这个方法的
返回值无法预知

* boolean removeAll(Collection<?> c):
将这个集合中所有在集合c中出现的元素删除,在这个方法运行后这个集合中不包含任何在集合c中出现的元素

* boolean removeIf(Predicate<? super E> filter):
移除集合中所有满足给定的predicate 过滤器的元素,由于迭代器或者过滤器抛出的异常会被延迟到调用者抛出。
默认的实现是使用迭代器来遍历元素,毛哥满足过滤条件的元素会被迭代器使用remove()方法删除掉
但如果iterator不支持删除方法，那么在遇到的第一个匹配的元素时就会抛出UnsupportedOperation异常

* boolean retainAll(Collection<?> c):
遍历集合，只保留集合c中出现的元素,换句话说，移除集合中所有不在集合c中出现过的元素

* void clear():
一处集合中的所有元素,在方法调用完成后,集合将会变成一个空的集合

* boolean equals(Object o):
    * Collection并没有对Object的equals方法做出改变,对于那么直接实现Collection接口的类
还是只需要实现reference comparison,那么就可以直接依赖object的比较方法,而对于那些
子接口比如list或者set,则需要实现特定的值比较接口,那么就必须重写这个方法

    * equals()方法要遵守对称性，list和set的equals()方法只能参数是list或者set时才能返回true
    所以当一个集合既没有实现list或者set接口，那么他的自定义equals()方法在被调用和list或者set比较时一定会返回false
    所以一个类不可以同时实现list和set接口

* int hashCode():
Collection 类没有对Object的哈希码方案做出修改,但是任何修改了equals()方法的就必须要重写hashcode()方法

* Spliterator<E> spliterator():
    * 实现应该记录下Spliterator返回的特征值,但是当集合里面不包含元素时,我们不需要记录这个特征值
    * 子类需要重写这个方法来返回一个更加高效的分割器来支持stream()和parallelStream()
    * 分隔符应该有IMMUTABLE  or CONCURRENT特征,如果这些都不可行，那么上层的类应该描述分流器的书面约束和结构干扰政策，
    并且应该重写stream()和parallelStream()方法,并且利用spliterator.Supplier来创建一个流
    `Stream<E> s = StreamSupport.stream(() -> spliterator(), spliteratorCharacteristics)`
a
    * 这就能确保由stream()和parallelStream()方法产生的流能反应创建流时集合的内容
    * Collection的spliterator继承自集合的迭代器,所以它也继承了迭代器的fast-fail特性
    * 如果分隔符不包含元素,那么就报告其他的特征值,对于空的集合,可以使用空的且不可变的spliterator实例
    
* Stream<E> stream():
返回一个顺序流,使用这个集合作为源头,当集合的spliterator()方法不能返回一个immutable and concurrent
的spliterator时,这个方法需要被重写

* Stream<E> parallelStream():
把集合作为源头,返回一个集合作为源头的可能的并行流,这个方法也允许返回一个顺序流，默认的实现是从Spliterator中分割出
并行流

    