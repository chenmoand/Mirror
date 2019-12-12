## Mirror - 方便的反射工具

> 现处于```Beta```阶段, 可能出现不肯抗拒的**Bug**

#### 如何是使用?

* 假设方法接收的参数是基础类型

  请使用``` com.brageast.mirror.util.Convert.is()```方法 

* 我想传入一个```null```值怎么办

  请使用``` com.brageast.mirror.util.Null.is(Class<T> cls)```方法

```java
import com.brageast.mirror.Mirror;
import static com.brageast.mirror.util.Convert.is;

Mirror.just(user) // 可以传入一个 实例化的对象 字符串 一个类
    .doOneMethod("setName", "沉默")
    .invoke()
    .doOneMethod("setAge", is(18))
    .invoke()
    .doOneMethod("setSex", "男")
    .invoke();
```

