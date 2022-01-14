package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("")
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target); //프록시 팩토리에 원하는 클래스를 주입

        proxyFactory.addAdvice(new TimeAdvice()); // 공통으로 실행할 advice 객체 주입

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy(); //동적으로 ServiceInterface 프록시가 생성됨
        log.info("target class={}", target.getClass());
        log.info("proxy class={}", proxy.getClass());
        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("")
    void concreteProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target); //프록시 팩토리에 원하는 클래스를 주입

        proxyFactory.addAdvice(new TimeAdvice()); // 공통으로 실행할 advice 객체 주입

        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy(); //동적으로 ServiceInterface 프록시가 생성됨
        log.info("target class={}", target.getClass());
        log.info("proxy class={}", proxy.getClass());
        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("Proxy TargetClass 옵션을 사용하면 이터페이스가 있어도 CGLIB 사용하고 클래스 기반 프록시 사용 ")
    void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target); //프록시 팩토리에 원하는 클래스를 주입
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new TimeAdvice()); // 공통으로 실행할 advice 객체 주입

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy(); //동적으로 ServiceInterface 프록시가 생성됨
        log.info("target class={}", target.getClass());
        log.info("proxy class={}", proxy.getClass());
        proxy.save();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }
}
