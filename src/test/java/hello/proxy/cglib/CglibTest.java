package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    void cglib(){

        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(ConcreteService.class); //생성할 프록시의 클래스형을 지정
        enhancer.setCallback(new TimeMethodInterceptor(target)); //프록시 인터셉터 주입

        ConcreteService proxy = (ConcreteService) enhancer.create(); // 프록시 생성

        log.info("targetClass={}", target.getClass());
        log.info("proxy={}", proxy.getClass());

        proxy.call(); //해당 프록시 함수 호출
    }
}
