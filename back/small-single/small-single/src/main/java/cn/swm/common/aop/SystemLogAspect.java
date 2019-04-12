package cn.swm.common.aop;

import cn.swm.common.annotation.SystemControllerLog;
import cn.swm.common.annotation.SystemServiceLog;
import cn.swm.pojo.TbLog;
import cn.swm.service.SystemService;
import cn.swm.utils.IPInfoUtil;
import cn.swm.utils.ObjectUtil;
import cn.swm.utils.ThreadPoolUtil;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/*
* 现在想在实现类中的每个方法执行前、后、以及是否发生异常等信息打印出来，
* 需要把日志信息抽取出来，写到对应的切面的类中 SystemLogAspect.java 中
要想把一个类变成切面类，需要两步，
① 在类上使用 @Component 注解 把切面类加入到IOC容器中
② 在类上使用 @Aspect 注解 使之成为切面类
* */
@Aspect
@Component // （把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>）
public class SystemLogAspect {

    private Logger log = LoggerFactory.getLogger(SystemLogAspect.class);

    /*
     * ThreadLocal是一个本地线程副本变量工具类。
     * 主要用于将私有线程和该线程存放的副本对象做一个映射，
     * 各个线程之间的变量互不干扰，在高并发场景下，可以实现无状态的调用，
     * 特别适用于各个线程依赖不通的变量值完成操作的场景。
     */
    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");

    @Autowired
    private SystemService systemService;

    @Autowired(required = false) // 表示忽略当前要注入的bean，如果有直接注入，没有跳过，不会报错。
    private HttpServletRequest request;

    /**
     *  Controller层的切点，注解方式
     */
    // @Pointcut("execution(* *..controller..*Controller*.*(..))")
    @Pointcut("@annotation(cn.swm.common.annotation.SystemControllerLog)")
    public void controllerAspect(){
        log.info("========================================controllerAspect=============================================");
    }

    @Pointcut("@annotation(cn.swm.common.annotation.SystemServiceLog)")
    public void serviceAspect(){
        log.info("=================================================ServiceAspect=======================================================");
    }

    /**
     * 前置通知（在方法执行之前返回）用户拦截Controller层记录用户的操作的开始时间
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{

        // 线程绑定变量（该数据只有当前请求的线程可见）
        Date beginTime = new Date();
        beginTimeThreadLocal.set(beginTime);
    }

    @After("controllerAspect()")
    public void after(JoinPoint joinPoint){
        try {
            String username = SecurityUtils.getSubject().getPrincipal().toString();

            if(username != null){

                TbLog tbLog = new TbLog();

                //日志标题
                tbLog.setName(getControllerMethodDescription(joinPoint));
                //日志类型
                tbLog.setType(1);
                //请求的方式
                tbLog.setRequestType(request.getMethod());
                //日志请求的url
                tbLog.setUrl(request.getRequestURI());
                //请求参数
                Map<String,String[]> logParams = request.getParameterMap();
                tbLog.setMapToParams(logParams);
                IPInfoUtil.getInfo(request, ObjectUtil.mapToStringAll(logParams));
                //请求用户
                tbLog.setUser(username);
                //请求ip
                tbLog.setIp(IPInfoUtil.getIpCity(IPInfoUtil.getIpAddr(request)));
                //请求开始时间
                Date logStartTime = beginTimeThreadLocal.get();

                long beginTime = beginTimeThreadLocal.get().getTime();
                long endTime = System.currentTimeMillis();
                //请求耗时
                Long logElapsedTime = endTime - beginTime;
                tbLog.setTime(logElapsedTime.intValue());
                tbLog.setCreateDate(logStartTime);

                //调用线程保存到数据库
                ThreadPoolUtil.getPool().execute(new SaveSystemLogThread(tbLog, systemService));
            }
        }catch (Exception e){
            log.error("AOP后置通知异常" ,e);
        }
    }

    /**
     * 异常通知，用户拦截service层记录异常日志
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e){
        try {
            String username = SecurityUtils.getSubject().getPrincipal().toString();

            if(username != null){

                TbLog tbLog = new TbLog();

                //日志标题
                tbLog.setName(getControllerMethodDescription(joinPoint));
                //日志类型
                tbLog.setType(0);
                //请求的方式
                tbLog.setRequestType(request.getMethod());
                //日志请求的url
                tbLog.setUrl(request.getRequestURI());
                //请求参数
                Map<String,String[]> logParams = request.getParameterMap();
                tbLog.setMapToParams(logParams);
                IPInfoUtil.getInfo(request, ObjectUtil.mapToStringAll(logParams));
                //请求用户
                tbLog.setUser(username);
                //请求ip
                tbLog.setIp(IPInfoUtil.getIpCity(IPInfoUtil.getIpAddr(request)));
                //请求开始时间
                Date logStartTime = beginTimeThreadLocal.get();

                long beginTime = beginTimeThreadLocal.get().getTime();
                long endTime = System.currentTimeMillis();
                //请求耗时
                Long logElapsedTime = endTime - beginTime;
                tbLog.setTime(logElapsedTime.intValue());
                tbLog.setCreateDate(logStartTime);

                //调用线程保存到数据库
                ThreadPoolUtil.getPool().execute(new SaveSystemLogThread(tbLog, systemService));
            }
        }catch (Exception el){
            log.error("AOP异常通知异常" ,el);
        }
    }

    /**
     * 保存日志信息
     */
    private static class SaveSystemLogThread implements Runnable{
        private TbLog tbLog;
        private SystemService systemService;

        public SaveSystemLogThread(TbLog tbLog, SystemService systemService) {
            this.tbLog = tbLog;
            this.systemService = systemService;
        }

        @Override
        public void run() {
            systemService.addLog(tbLog);
        }
    }

    /**
     * 获取注解中对方法的描述信息，用户controller层的注解
     * @param joinPoint 切点
     * @return 方法描述信息
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception{
        //获取目标类名
        String targetName = joinPoint.getTarget().getClass().getName();

        //获取方法名字
        String methodName = joinPoint.getSignature().getName();

        //获取相关参数
        Object[] arguments = joinPoint.getArgs();

        //生成类对象
        Class targetClass = Class.forName(targetName);

        Method[] methods = targetClass.getMethods();

        String description = "";

        for(Method method : methods){
            //主要是找到那个跟切入点中的那个方法一样的
            if(!method.getName().equals(methodName)){
                continue;
            }
            Class[] clazzs = method.getParameterTypes();
            if(clazzs.length != arguments.length){// 比较方法的参数的个数是否跟切入点中获取的参数的个数相同，作者也说了，其实我页明白，有重载嘛！
                continue;
            }
            description = method.getAnnotation(SystemControllerLog.class).description();
        }

        return description;
    }

    /**
     * 获取注解中对方法的描述信息，用户service层的注解
     * @param joinPoint 切点
     * @return 方法描述信息
     * @throws Exception
     */
    public static String getServiceMethodDescription(JoinPoint joinPoint) throws Exception{
        //获取目标类名
        String targetName = joinPoint.getTarget().getClass().getName();

        //获取方法名字
        String methodName = joinPoint.getSignature().getName();

        //获取相关参数
        Object[] arguments = joinPoint.getArgs();

        //生成类对象
        Class targetClass = Class.forName(targetName);

        Method[] methods = targetClass.getMethods();

        String description = "";

        for(Method method : methods){
            //主要是找到那个跟切入点中的那个方法一样的
            if(!method.getName().equals(methodName)){
                continue;
            }
            Class[] clazzs = method.getParameterTypes();
            if(clazzs.length != arguments.length){// 比较方法的参数的个数是否跟切入点中获取的参数的个数相同，作者也说了，其实我页明白，有重载嘛！
                continue;
            }
            description = method.getAnnotation(SystemServiceLog.class).description();
        }

        return description;
    }
}
