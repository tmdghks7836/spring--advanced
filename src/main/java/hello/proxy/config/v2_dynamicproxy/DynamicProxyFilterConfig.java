package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceFilterHandler;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {


    private static final String[] PATTERNS = {"request*", "order*", "save*"};


    @Bean
    public OrderControllerV1 dynamicOrderController(LogTrace logTrace) {
        OrderControllerV1 target = new OrderControllerV1Impl(dynamicOrderService(logTrace));
        LogTraceFilterHandler handler = new LogTraceFilterHandler(target, logTrace, PATTERNS);

        OrderControllerV1 proxy = getProxy(OrderControllerV1.class, handler);
        return proxy;
    }

    @Bean
    public OrderServiceV1 dynamicOrderService(LogTrace logTrace) {

        OrderServiceV1 service = new OrderServiceV1Impl(dynamicOrderRepository(logTrace));
        LogTraceFilterHandler handler = new LogTraceFilterHandler(service, logTrace, PATTERNS);

        OrderServiceV1 proxy = getProxy(OrderServiceV1.class, handler);

        return proxy;
    }

    @Bean
    public OrderRepositoryV1 dynamicOrderRepository(LogTrace logTrace) {

        OrderRepositoryV1 repository = new OrderRepositoryV1Impl();
        LogTraceFilterHandler handler = new LogTraceFilterHandler(repository, logTrace, PATTERNS);

        OrderRepositoryV1 proxy = getProxy(OrderRepositoryV1.class, handler);

        return proxy;
    }

    private <T> T getProxy(Class clazz, InvocationHandler logTraceBasicHandler) {
        T proxy = (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                logTraceBasicHandler);

        return proxy;
    }

}
