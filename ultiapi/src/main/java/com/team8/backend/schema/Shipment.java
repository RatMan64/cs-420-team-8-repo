
package com.team8.backend.schema;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
@DynamoDbBean
public class Shipment {

    private ShipTo shipTo;
    private Carrier carrier;
    private List<Item> items = null;

    public ShipTo getShipTo() {
        return shipTo;
    }

    public void setShipTo(ShipTo shipTo) {
        this.shipTo = shipTo;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


}
