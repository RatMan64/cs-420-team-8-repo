
package com.team8.backend.schema;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
@DynamoDbBean
public class Shipment {

    private ShipTo shipTo;
    private Carrier carrier;

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

}
