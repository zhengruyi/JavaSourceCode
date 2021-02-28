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
