package kafoor.users.user_service.aops;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

//@Aspect
//@Component
//@Slf4j
//public class LoggingAspect {
//    @Pointcut("execution(public * kafoor.users.user_service.controllers.*.*(..))")
//    private void controllerLog(){}
//
//    @Pointcut("execution(public * kafoor.users.user_service.services.*.*(..))")
//    private void serviceLog(){}
//
//    @Before("controllerLog()")
//    public void doBeforeController(JoinPoint joinPoint) throws UnsupportedEncodingException {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = null;
//        if(attributes != null){
//            request = attributes.getRequest();
//        }
//        if(request != null){
//            log.info("""
//
//                    ====REQUEST TO CONTROLLER====
//                    IP:                {}
//                    URL:               {}
//                    HTTP_METHOD:       {}
//                    CONTROLLER_CLASS:  {}
//                    CONTROLLER_METHOD: {}\n""", request.getRemoteAddr(),
//                    request.getRequestURL().toString(),
//                    request.getMethod(),
//                    joinPoint.getSignature().getDeclaringTypeName(),
//                    joinPoint.getSignature().getName());
//        }
//    }
//
//    @Around("controllerLog()")
//    public Object executionTimeController(ProceedingJoinPoint joinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//        Object proceed = joinPoint.proceed();
//        long executionTime = System.currentTimeMillis() - start;
//
//        log.info("""
//
//                ====EXECUTION OF THE CONTROLLER====
//                CONTROLLER_CLASS:  {}
//                CONTROLLER_METHOD: {}
//                TIME: {} ms\n""", joinPoint.getSignature().getDeclaringTypeName(),
//                joinPoint.getSignature().getName(),
//                executionTime);
//        return proceed;
//    }
//
//    @AfterReturning(returning = "returnObject", pointcut = "controllerLog()")
//    public void doAfterControllerReturning(JoinPoint joinPoint, Object returnObject){
//        log.info("""
//
//                ====SUCCESS RESPONSE FROM CONTROLLER====
//                CONTROLLER_CLASS:  {}
//                CONTROLLER_METHOD: {}
//                VALUE:             {}\n""", joinPoint.getSignature().getDeclaringTypeName(),
//                joinPoint.getSignature().getName(), returnObject);
//    }
//
//    @AfterThrowing(throwing = "ex", pointcut = "controllerLog()")
//    public void doAfterControllerThrowing(JoinPoint joinPoint, Exception ex){
//        log.info("""
//
//                ====ERROR RESPONSE FROM CONTROLLER===
//                CONTROLLER_CLASS:  {}
//                CONTROLLER_METHOD: {}
//                ARGUMENTS:         {}
//                EXCEPTION:         {}\n""", joinPoint.getSignature().getDeclaringTypeName(),
//                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()) , ex.getMessage());
//    }
//
//    @Before("serviceLog()")
//    public void doBeforeService(JoinPoint joinPoint){
//        Object[] args = joinPoint.getArgs();
//        String argsString = args.length > 0 ? Arrays.toString(args) : "METHOD HAS NO ARGUMENTS";
//        log.info("""
//
//                ====REQUEST TO SERVICE====
//                SERVICE_CLASS:  {}
//                SERVICE_METHOD: {}
//                ARGUMENTS:      {}\n""", joinPoint.getSignature().getDeclaringTypeName(),
//                joinPoint.getSignature().getName(),
//                argsString);
//    }
//
//    @Around("serviceLog()")
//    public Object executionTimeService(ProceedingJoinPoint joinPoint) throws Throwable {
//        long start = System.currentTimeMillis();
//        Object proceed = joinPoint.proceed();
//        long executionTime = System.currentTimeMillis() - start;
//
//        log.info("""
//
//                ====EXECUTION OF THE SERVICE====
//                SERVICE_CLASS:  {}
//                SERVICE_METHOD: {}
//                TIME:           {} ms \n""", joinPoint.getSignature().getDeclaringTypeName(),
//                joinPoint.getSignature().getName(),
//                executionTime);
//        return proceed;
//    }
//
//    @AfterReturning(returning = "returnObject", pointcut = "controllerLog()")
//    public void doAfterServiceReturning(JoinPoint joinPoint, Object returnObject){
//        log.info("""
//
//                ====SUCCESS RESPONSE FROM SERVICE===
//                SERVICE_CLASS:  {}
//                SERVICE_METHOD: {}
//                VALUE:          {}\n""", joinPoint.getSignature().getDeclaringTypeName(),
//                joinPoint.getSignature().getName(), returnObject);
//    }
//
//    @AfterThrowing(throwing = "ex", pointcut = "serviceLog()")
//    public void doAfterServiceThrowing(JoinPoint joinPoint, Exception ex){
//        log.info("""
//
//                ====ERROR RESPONSE FROM SERVICE===
//                SERVICE_CLASS:  {}
//                SERVICE_METHOD: {}
//                ARGUMENTS:      {}
//                EXCEPTION:      {}\n""", joinPoint.getSignature().getDeclaringTypeName(),
//                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()) , ex.getMessage());
//    }
//}
