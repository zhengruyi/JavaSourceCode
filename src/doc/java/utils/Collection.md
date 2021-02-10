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