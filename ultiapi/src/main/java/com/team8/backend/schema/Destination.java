
package com.team8.backend.schema;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
@DynamoDbBean
public class Destination {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
