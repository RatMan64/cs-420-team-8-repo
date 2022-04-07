package com.team8.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {

    }


    // an attempt at mocking
    /*
    @Mock(name = "DB")
    private DynamoDbTable<DBItem> DB;

    @Mock(name = "SF")
    public SiteFlow SF;


    @Autowired
    private Endpoint ep;


    @BeforeTestExecution
    public void setup() throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        var p = new ProtocolVersion("HTTP", 1, 1);
        var s = new BasicStatusLine(p, 400, "mocked");
        var r = new BasicHttpResponse(s);
        Mockito.when(SF.SubmitOrder(Mockito.anyString())).thenReturn(r);
    }

    @Test
    void retrieves_orders() throws Exception {
        var f = Files.contentOf(
                new ClassPathResource("test-order.json").getFile(),
                java.nio.charset.Charset.defaultCharset()
        );

        var o = (new ObjectMapper()).readValue(f, Order.class);
        ep.SF = SF;
        ep.submitOrder(o);

    }

    */

}
