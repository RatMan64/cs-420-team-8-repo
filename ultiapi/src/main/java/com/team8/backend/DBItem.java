package com.team8.backend;

import com.team8.backend.schema.Order;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class DBItem {
    private String user;
    private String id;
    private Order order;

    // cant use default constructor with dynamo sdk

    @DynamoDbPartitionKey
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }



    // Kevin, setid is needed so dynamo sdk parses the order class into a schema correctly
    @DynamoDbSortKey
    public String getId() { return id; }
    public void setId(String id) {this.id = id;}


    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}
