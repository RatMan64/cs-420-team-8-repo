package com.team8.backend;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.UUID;

@DynamoDbBean
public class Order {
    private String user;
    private final String id = UUID.randomUUID().toString();
    private String order;
    private Shipping shipping;

    // cant use default constructor with dynamo sdk

    @DynamoDbPartitionKey
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }


    // Kevin, setid is needed so dynamo sdk parses the order class into a schema correctly
    public String getId() { return id; }
    public void setId(String id) {}


    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    @DynamoDbBean
    public static class Shipping{
        private String dest;
        private String name;

        public String getDest() {
            return dest;
        }

        public void setDest(String dest) {
            this.dest = dest;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }
}
