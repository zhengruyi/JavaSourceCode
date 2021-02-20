### ListIterator
提供了一个迭代器给编程者来双向遍历链表，在遍历过程中修改元素，或者获得迭代器当前
在链表中的位置，迭代器没有当前位置,迭代器的指针总数处于next()将要返回的元素和previous()
返回的元素之间,对于一个有n个元素的列表，迭代器总共有0..n总共n+1元素位置

但是remove()和add()方法不是定义在当前位置的基础上,他们操作的是next()或者previous()
方法返回的最后一个元素

* boolean hasNext():
如果列表在前进方向上还有元素时,就返回true,否则返回false,换句话说当next()
方法返回一个人元素时，那么返回true,不然就返回false

* E next():
返回前进方向上的下一个元素，并且会调整迭代器的游标,也可以通过previous()方法
来回到上一个位置,如果间隔性调用next()和previous()方法，那么会返回同一个元素

* boolean hasPrevious():
如果在前进方向上的反方向上存在元素，那么就会返回true，否则返回false。换句话说
previous()返回元素时为true，否则返回false

* E previous():
返回前进方向的反方向上的第一个元素,并且会移动迭代器上的游标，这个方法可以被多次调用

* int nextIndex():
返回next()下一次调用返回的元素的在列表中的位置,在列表末尾时返回的是列表的大小

* int previousIndex()：
返回previous()下一次调用返回的元素的在列表中的位置,在列表开头时返回的是-1

* void remove():
移除最后一次被next()或者previuos()方法返回的元素,每次调用Next()或者previous()
方法调用后才可以调用一次remove()方法,只有在next()或者previous()方法调用后
没有调用add()方法时才可以调用

* void set(E e):
将Next()或者previous()方法返回的某个元素替换成新元素,只有在next()或者previous()方法
执行后，没有任何add()或者remove()方法执行后才可以调用

* void add(E e)：
将元素立刻插入到链表中,这个元素会被立刻插入到Next()将要返回的元素之前，所以next()方法的结果不会被
影响而previous()方法会返回新的元素,但是nextIndex()和previousIndex()返回的结果会加1


 
