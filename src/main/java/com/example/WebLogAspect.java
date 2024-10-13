package com.example;

import com.example.logAspect.enity.LogAspect;
import com.example.logAspect.service.LogAspectService;
import com.example.util.*;
import com.example.util.annotion.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志监听切面
 * @author Enoki
 * @date 2021/12/25
 */
@Aspect
@Component
@Order(1)
public class WebLogAspect {

    @Resource
    private LogAspectService logAspectService;

    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    /** 以 example 包下定义的所有请求为切入点 */
    @Pointcut("execution(public * com.example..*.*(..)) " +
            "&& !execution(public * com.example.WebLogAspect.doBefore()) " +
            "&& !execution(public * com.example.logAspect..*.*(..)) " +
            "&& !execution(public * com.example.token..*.*(..)) " +
            "&& !execution(public * com.example.util..*.*(..)) ")
    public void webLog() {}

    /**
     * 在切点之前织入
     * @param joinPoint
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {}

    @AfterReturning(returning = "result",pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint,ResultBody result) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(!StringBlack.isBlackObject(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            // 打印请求 url
            logger.info("URL            : {}", request.getRequestURL().toString());
            // 打印 Http method
            logger.info("HTTP Method    : {}", request.getMethod());
            // 打印调用 controller 的全路径以及执行方法
            logger.info("Class Method   : [{}][{}]", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            // 获取实际的ip
            logger.info("Heal HeaderIp   : [{}][{}]", request.getHeader("X-Real-IP"), request.getHeader("X-Forwarded-For"));
            Log log = getAnnotationLog(joinPoint);
            if (log != null){
                LogResultBody res = new LogResultBody(result.getCode(), result.getReslutMsg(), result.getTotal());
                LogAspect logAspect = new LogAspect(joinPoint.getSignature().getName() ,request.getRequestURL().toString(), request.getMethod(),
                        joinPoint.getSignature().getDeclaringTypeName(),
                        request.getHeader("X-Real-IP") == null ? request.getHeader("X-Forwarded-For") : request.getHeader("X-Real-IP"), res,
                        log.type().getCode().toString(), log.title());
                logAspectService.insertLog(logAspect);
            }
        }
    }

    @After("webLog()")
    public void after(){}

    /**
     * 是否存在注解，如果存在就获取
     */
    private static Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

}
