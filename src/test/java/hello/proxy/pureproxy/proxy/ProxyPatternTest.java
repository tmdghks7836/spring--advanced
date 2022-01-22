package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest(){
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);

        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest(){

        RealSubject subject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(subject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();
    }
}
