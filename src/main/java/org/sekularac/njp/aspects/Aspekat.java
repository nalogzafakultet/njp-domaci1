package org.sekularac.njp.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Date;
import java.util.logging.Logger;

@Aspect
public class Aspekat {

    Logger logger = Logger.getLogger("ASPECT TIME LOGGER");

    @Pointcut("call(public * org.sekularac.njp.entitymanager.EntityManager.*())")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object handler(ProceedingJoinPoint jp) {
        long start = System.currentTimeMillis();
        Object ret = null;
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
             ret = jp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long diff = System.currentTimeMillis() - start;

        logger.info("Time needed for the transaction " + jp.getSignature() + " is : " + diff + " millies.");
        return ret;
    }

}
