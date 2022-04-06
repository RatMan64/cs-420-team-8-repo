package com.team8.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BackendApplicationTests {



    @Mock(name = "DB")
    private DynamoDbTable<DBItem> DB;

    @Mock
    private Iterable<DBItem> res;

    @Mock
    PageIterable<DBItem> pi;

    @InjectMocks private Endpoint ep;

    @BeforeEach
    public void setup(){
        DBItem order = new DBItem();
        ArrayList<DBItem> orders = new ArrayList<>();
        orders.add(order);


        Mockito.when(pi.items()).thenReturn((SdkIterable<DBItem>) orders.iterator());
        Mockito.when(DB.scan()).thenReturn(pi);

    }

    @Test
	void contextLoads() {

	}

    @Test
    void retrieves_orders(){
        assertNotNull(ep.getOrders());
    }

}
