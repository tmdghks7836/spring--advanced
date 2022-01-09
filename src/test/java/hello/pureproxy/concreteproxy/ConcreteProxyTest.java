package hello.pureproxy.concreteproxy;

import hello.pureproxy.concreteproxy.code.ConcreteClient;
import hello.pureproxy.concreteproxy.code.ConcreteLogic;
import hello.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient concreteClient = new ConcreteClient(concreteLogic);

        concreteClient.execute();
    }

    @Test
    void addProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        TimeProxy proxy = new TimeProxy(concreteLogic);
        ConcreteClient concreteClient = new ConcreteClient(proxy);
        concreteClient.execute();
    }
}
