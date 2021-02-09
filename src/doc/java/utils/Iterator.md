### Iterator
这个遍历器用来替代java框架中的枚举方法,与枚举方法相比,迭代器的方法名被改进了
且迭代器可以在遍历过程中使用明确地语义来删除集合中的元素

* hasNext():
如果集合还拥有的元素,那么就返回true,否则返回false;

* next():
如果集合中还有元素,返回集合中的下一个元素.否则抛出一个 NoSuchElementException异常

* remove():
用来移除next()方法返回的最后一个元素,每次调用完next()后只能调用一次remove()
方法，如果在遍历过程中如果通过其他方法修改了集合,那么iterator的行为无法预料

* forEachRemaining(action):
对所有的剩余的元素应用action直到所有元素都应用了或者action中抛出了异常,应用的
顺序等于遍历的顺序
