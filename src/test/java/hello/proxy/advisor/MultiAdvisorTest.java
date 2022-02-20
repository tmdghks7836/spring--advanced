package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

@Slf4j
public class MultiAdvisorTest {

    @Test
    @DisplayName("여러 프록시")
    void multiAdvisorTest1(){

        //client ->
        //create proxy 1
        ServiceInterface target = new ServiceImpl(); //declare interface
        ProxyFactory factory1 = new ProxyFactory(target); // proxy factory
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1()); // advice
        factory1.addAdvisor(advisor1);
        ServiceInterface proxy1 = (ServiceInterface) factory1.getProxy();

        //create proxy 2
        ProxyFactory factory2 = new ProxyFactory(proxy1); // proxy factory
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2()); // advice
        factory2.addAdvisor(advisor2);
        ServiceInterface proxy2 = (ServiceInterface) factory2.getProxy();

        proxy2.save();

    }

    @Test
    @DisplayName("하나의 프록시, 여러 advisor")
    void multiAdvisorTest2(){

        //client ->
        //create proxy -> advisor2 -> advisor1 -> target
        ServiceInterface target = new ServiceImpl(); //declare interface
        ProxyFactory factory1 = new ProxyFactory(target); // proxy factory
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1()); // advice
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2()); // advice
        factory1.addAdvisor(advisor1);
        factory1.addAdvisor(advisor2);

        ServiceInterface proxy = (ServiceInterface) factory1.getProxy();
        proxy.save();

    }

    static class Advice1 implements MethodInterceptor{

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("call advice1");
            return invocation.proceed();
        }
    }

    static class Advice2 implements MethodInterceptor{

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("call advice2");
            return invocation.proceed();
        }
    }
}
