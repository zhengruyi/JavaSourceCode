### Object class

* native getClass():
用来返回运行时的对象类型,结果是静态声明时的类型本身或者声明类型的子类型
* native hashcode():
用来返回对象的哈希码,要求一个对象多次执行后得到的哈希码结果必须相等,且equals()方法返回
true时，要求两个对象的哈希码必须相等,但是并不要求当两个对象不相等时,hashcode()方法必须
返回不同的值,但是如果讷讷个做到这一点对哈希表的性能提升很重要，对象hashcode()的值一般是
将对象的内部地址转化成哈希码返回.

* equals():
主要用来判断两个非空对象是否相等,满足一下几个属性
    * x.equals(x)永远是true
    * x.equals(y)和y.equals(x)结果相等,满足对称性
    * x.equals(y) = true且 y.equals(z) = true,那么x.equals(z)一定等于true
    * equals()返回的结果在两个对象没有都没有改变时,那么无论调用多少次,返回的结果永远不变
    * 对于任何非空的x,x.equals(null)永远等于false
    * 对于Object来说，equals()方法的默认实现是比较两个对象的地址,所以当且仅当
    两个非空变量x,y指向同一个对象时才会让x.equals(y)返回false,所以一般需要重写equals()
    方法,当重写equals()时一定要重写hashcode(),是因为满足条件,当两个对象相等时,hashcode()
    的返回值也相等.
    
* protected clone():
    * 方法主要用来拷贝对象,必定会满足以下属性 x.clone() != x返回true
    * x.clone().getClass() == x.getClass() 和 x.clone().equals(x)为true并不一定满足
    这不是必须条件
    * clone()返回的对象应该和原来的对象区分开,所以在执行完super.clone()后任然需要
    对对象里面的所有可变的属性进行赋值,也就是说对于内部可变属性,必须要改变内部这些属性的
    链接对象,然后才能返回，但是对于不可遍对象,就不需要更改属性的引用
    * 所有实现clone()方法的类都不需要要实现接口Cloneable,不然就会抛出
    CloneNotSupportedException异常，特别的对于所有的arrays对象都支持Cloneable接口
    会返回一个同等类型的数组,对于其他对象,会先创建一个空对象，然后会把所有属性的值设置成
    和原来的对象一模一样,然后返回克隆对象
    
    * Object类并没有实现clone()方法，所以继承的子类如果直接调用clone()方法会抛出异常
    
* toString():
用来返回对象的简洁文本描述,建议所有子类都重写这个方法,Object类提供了默认的实现,表示形式是
getClass().getName() + '@' + Integer.toHexString(hashCode())，即"对象的类名@对象哈希码
的十六进制"的表现形式.

* final native notify()
    * 用来唤醒一个因为等待对象监视器而沉睡的线程,如果有多个线程在沉睡，那么就从中随机选择提个线程唤醒当线程通过调用wait()方法来沉睡等待获得对象的监视器来获得执行权
    * 被唤醒的线程不能获取监视器知道当前线程释放了锁,才能和其他线程一起争夺监视器
    * 唤醒的线程或者其他线程一起争夺监视器的控制权,并没有任何其他的任何的优先权
    * 该方法只能被拥有监视器的线程调用
    * 有以下三种方法获得监视器：1.执行同步实例方法 2. 执行同步块里面的方法 3. 对于Class类型，执行类的静态方法
    * 每次只能同时有一个线程拥有监视器
    * 不拥有监视器的线程执行这个方法会抛出 IllegalMonitorStateException异常
    
* final native notifyAll():
基本功能和notify()方法一模一样,唯一的区别就是notifyAll()方法会唤醒所有的等待线程
而notify()方法只是会唤醒其中的一个线程.其余特性和notify()方法一模一样

* final native wait(long timeout):
调用这个方法会导致当前线程沉睡直到以下四种情况发生: 1.被其它线程调用的notify()方法选中 2.被其余线程的NoyifyAll()方法唤醒 3.或者经过了指定的timeout
时间
    * 调用这个方法的线程必须持有这个对象的监视器
    * 调用这个方法的线程会让自己沉睡并释放所有和这个对象有关的同步资源,并且暂时不能被cpu调度
    * 线程被唤醒的四个方法：notify(),notifyAll(), 被其他线程调用了interrupted(),以及指定的超时时间
    * 当线程被唤醒后,这个线程就可以再次被调度，并且和所偶遇其他的线程争夺对象的锁
    * 当线程从wait()方法返回后，线程的状态会恢复到调用方法以前的样子
    * 我了防止线程被意外唤醒,我们的代码需要能处理这种情况,一般的结构是
    ```
     synchronized (obj) {
            while (&lt;condition does not hold&gt;)
            obj.wait(timeout);
            ... // Perform action appropriate to condition
       }
    ```
    * 如果当线程沉睡等到锁时被打断,会在等到对象的锁定状态重置后，绕出一个InterruptedException异常
    * 当该线程调用wait()方法时只会释放有关这个对象的资源，该线程持有的其他对象的资源并不会被释放
    * 如果参数timeout等于0表示会永远沉睡，知道其余的线程唤醒它

* protected finalize():
    * 当垃圾收集器确认这个对象没有其他有效引用时,会调用这个方法来清理资源
    * 这个方法可以做任何事，包括让这个对象重新对其他线程可见
    * 一般来说这个方法用来清理系统资源,比如终止链接
    * Object并没有提供方法实现,仅仅只是正常返回
    * 对于任何对象,jvm并不能保证哪个线程会来运行finalise()方法
    * 如果在finalise()方法中抛出了异常，这个异常会被忽略,对象的终止过程也会结束
    * 对于任何方法,JVM保证对于这个finalize()方法不会被调用超过一次
