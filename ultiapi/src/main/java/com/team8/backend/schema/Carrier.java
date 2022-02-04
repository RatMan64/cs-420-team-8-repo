
package com.team8.backend.schema;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
@DynamoDbBean
public class Carrier {

    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
