
package com.team8.backend.schema;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
@DynamoDbBean
public class Component {

    private Boolean fetch;
    private String code;
    private String path;

    public Boolean getFetch() {
        return fetch;
    }

    public void setFetch(Boolean fetch) {
        this.fetch = fetch;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
