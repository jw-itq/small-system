package cn.swm.common.annotation;

/*
* java用  @interface Annotation{ } 定义一个注解 @Annotation，一个注解是一个类
@Override，@Deprecated，@SuppressWarnings为常见的3个注解。
注解相当于一种标记，在程序中加上了注解就等于为程序加上了某种标记，以后，
JAVAC编译器，开发工具和其他程序可以用反射来了解你的类以及各种元素上有无任何标记，看你有什么标记，就去干相应的事
* */

import java.lang.annotation.*;

/**
 * Target 说明了Annotation所修饰的对象范围
 * 取值(ElementType)有：
 * 　　　　1.CONSTRUCTOR:用于描述构造器
 * 　　　　2.FIELD:用于描述域
 * 　　　　3.LOCAL_VARIABLE:用于描述局部变量
 * 　　　　4.METHOD:用于描述方法
 * 　　　　5.PACKAGE:用于描述包
 * 　　　　6.PARAMETER:用于描述参数
 * 　　　　7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 *
 * Retention 可以用来修饰注解，是注解的注解，称为元注解。
 * 按生命周期来划分可分为3类：
 * 1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
 * 2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
 * 3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
 */
@Target({ElementType.PARAMETER, ElementType.METHOD}) // 作用在方法或者参数上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {
    String description()
            default "";
}
