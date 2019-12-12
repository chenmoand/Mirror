# Mirror - 方便的反射工具

> 现处于```Beta```阶段, 只完善了部分功能
>
> 现在的反射工具大部分都是从遍历类, 它只会给你一个例如```Field```的集合, 并不会给你接下来的操作, 我们想实现它的操作还是很繁琐, 也有一些类似的工具类, 但他们并不能满足, 简称不方便, 所有就诞生了```Mirror```

### 安装方法

* 方法一

  ```git clone https://github.com/chenmoand/Mirror.git```

* 方法二

  .....

### 使用方法 / 对比

使用```Mirror```享受纵享丝滑操作

```java
import com.brageast.mirror.Mirror;

public class MirrorTest {
    public static void main(String[] args) {
        User user = new User();
        Mirror.just(user) // 传入的实例对象
                .doOneField("name", "哈哈哈") //操作一个方法
                .invoke(); // 执行

        Mirror.just(user)
                .withReturnTypeMethod(String.class) // 获取返回String的方法
                .forEach(mm -> mm.invoke(System.out::println)); // 循环打印执行
    }
}
```

没使用```Mirror``` 代码臃肿

```java 
 public void test() throws Exception {
        User user = new User();
        Class<? extends User> aClass = user.getClass();
        Field name = aClass.getDeclaredField("name");
        name.setAccessible(true);
        name.set(user, "哈哈哈");
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                System.out.println(field.get(user));
            }
        }

}
```

### 注意事项

1. 假设我想传入一个方法```null```值怎吗办?

   请使用```com.brageast.mirror.util.Null```中的is()方法

2. 如果我想传入的是基本数据类型怎吗办?

   请使用```com.brageast.mirror.util.Convert```中的is()方法, 因为在解析的时候int类型会自动解析成```Integer```类型,防止一个方法接收的基本类型, 导致找不到方法或者属性;