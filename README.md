# com.brageast.KMirror.Mirror - 方便的反射工具

> 用最少的操作完成你想要的结果

### 安装方法

* 方法一

  ```git clone https://github.com/chenmoand/com.brageast.KMirror.Mirror.git```

* 方法二 (暂时不可用

  ``` xml
  <dependency>
    <groupId>com.brageast</groupId>
    <artifactId>mirror</artifactId>
    <version>1.0</version>
  </dependency>
  ```

* 方法三

  [Releases](https://github.com/chenmoand/Mirror/releases) 下载

### 使用方法

本方法都以操作, user 是一个普通的JavaBean;

```java
User user = new User();
```
操作```User.class```的构造器

```java
User user1 = com.brageast.KMirror.Mirror.just(user)
    .doConstructor("兰陵王", Convert.conver(16), "女") // 预操作
    .newInstance() // 实例化这个
    // .off() 如果是私有的请打开off()
    .getInstance(); // 获得这个实例
```

操作```user```的一个属性

```java
com.brageast.KMirror.Mirror.just(user) // 传入的实例对象
    .doOneField("name", "哈哈哈") //操作一个属性
    // .off() 如果是私有的请打开off()
    .invoke(); // 执行
```

操作```user```的一个方法

```java
com.brageast.KMirror.Mirror.just(user)
    .doOneMethod("setName", "java") //获得一个方法
    // .off() 如果是私有的请打开off()
    .invoke(); // 执行
```

方法返回值为```String.class```的集合

```java
com.brageast.KMirror.Mirror.just(user)
    .withReturnTypeMethod(String.class); 
//返回一个List 数组 可以通过forEach操作
```

无视掉所有权限, 不管是构造器还是方法或者属性

```java
com.brageast.KMirror.Mirror.just(user)
    .defaultOffAll(); //相当于为每个都执行off()操作
```

获得所有属性类型是```String.class```的属性

```java
com.brageast.KMirror.Mirror.just(user)
    .withTypeField(String.class); //返回一个List
```

属性注解操作

```java
com.brageast.KMirror.Mirror.just(user)
    .defaultOffAll() // 关闭所有权限验证
    .allField(userObjectMirrorField -> userObjectMirrorField.hasAnntation(Boom.class))
    .forEach(mirrorField ->
             mirrorField.invoke(new InvokeFunction() {
                 private Boom boom; // 自动将上面筛选的注解实例注入

                 @Override
                 public Object onFieldModify(Object entity) {
                     System.out.println(boom);
                     return boom.value();
                 }

                 @Override
                 public void onModifyResult(Object entity) {
                     System.out.println(entity);
                 }
             }));
```

方法注解操作

```java
com.brageast.KMirror.Mirror.just(user)
    .doOneMethod("setName") // 预处理 setName
    .doParameter("hhaha") // 处理参数
    .doAnnotations(Boom.class) // 找到Boom注解
    .invoke(new InvokeFunction() { 
        private Boom boom; // 自动注入
        @Override
        public Object[] onMethodModify(Object[] parameters) { // parameters是上面的参数
            return new Object[]{parameters[0] + boom.value()}; // 返回新的参数
        }
        @Override
        public void onModifyResult(Object entity) {
            System.out.println(entity); //得到返回 
        }
    });
```

更多骚操作看你们了

### 注意事项

1. 假设我想传入一个方法```null```值怎吗办?

   请使用```com.brageast.mirror.entity.Null```中的```isNull()```方法

2. 如果我想传入的是基本数据类型怎吗办?

   请使用```com.brageast.mirror.entity.Convert```中的```cover()```方法,因为在默认解析的时候```int```类型会自动解析成```Integer```类型,使用这个方法可以保留原先属性;