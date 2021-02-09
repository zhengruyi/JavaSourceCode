###Iterable
Iterable接口是允许实现这个接口的类可以被foreach遍历

* iterator():
返回类型的遍历器，用来遍历所有的元素

* forEach(action):
对所有的元素按照遍历的顺序都采用相同的action,直到所有的元素都被应用了相同的action，
或者action抛出了异常,异常会被拖延到caller抛出

* spliterator():
spliterator()方法则提供了一个可以并行遍历元素的迭代器，以适应现在cpu多核时代并行遍历的需求。
