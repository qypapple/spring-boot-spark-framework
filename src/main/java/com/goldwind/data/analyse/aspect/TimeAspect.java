package com.goldwind.data.analyse.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Author: yuqing
 *
 *
 */

@Aspect
@Component
public class TimeAspect {
   @Around("execution(* com.goldwind.data.analyse.controller.WordController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable{
       System.out.println("Time Aspect start.");
       Object[] args = pjp.getArgs();
       for(Object arg:args){
           System.out.println("arg is "+arg);
       }
       long start = new Date().getTime();

       Object obj = pjp.proceed();
       System.out.println("Time Aspect 耗时："+(new Date().getTime()-start));
       System.out.println("Time Aspect end.");

       return obj;
   }
}
