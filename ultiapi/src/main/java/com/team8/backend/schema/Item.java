
package com.team8.backend.schema;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
@DynamoDbBean
public class Item {

    private String sku;
    private String sourceItemId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private String productId;
    private List<Component> components = null;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSourceItemId() {
        return sourceItemId;
    }

    public void setSourceItemId(String sourceItemId) {
        this.sourceItemId = sourceItemId;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

}
