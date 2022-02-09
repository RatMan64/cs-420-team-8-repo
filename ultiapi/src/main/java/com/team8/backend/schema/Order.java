
package com.team8.backend.schema;

import org.json.JSONObject;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
@DynamoDbBean
public class Order {

    private Destination destination;
    private OrderData orderData;

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public OrderData getOrderData() {
        return orderData;
    }

    public void setOrderData(OrderData orderData) {
        this.orderData = orderData;
    }



    /**
     * Creates an order for submission or validation
     *
     * @return JSON string of an order
     */
    public String toString(){
        return createOrder(this);
    }
    private String createOrder(Order order){

        //set needed ones
        String componentCode = order.getOrderData().getItems().get(0).getComponents().get(0).getCode();
        String destination = "wsu-test-team-8";
        String itemID = order.getOrderData().getItems().get(0).getSourceItemId();
        String orderId = order.getOrderData().getSourceOrderId();
        String sku = order.getOrderData().getItems().get(0).getSku();

        JSONObject shipTo = new JSONObject();
        shipTo.put("name", order.getOrderData().getShipments().get(0).getShipTo().getName());
        shipTo.put("companyName", order.getOrderData().getShipments().get(0).getShipTo().getCompanyName());
        shipTo.put("address1", order.getOrderData().getShipments().get(0).getShipTo().getAddress1());
        shipTo.put("town", order.getOrderData().getShipments().get(0).getShipTo().getTown());
        shipTo.put("postcode", order.getOrderData().getShipments().get(0).getShipTo().getPostcode());
        shipTo.put("isoCountry", order.getOrderData().getShipments().get(0).getShipTo().getIsoCountry());

        //check on this one
        JSONObject carrier = new JSONObject();
        carrier.put("alias", "shipping");

        JSONObject item = new JSONObject();
        item.put("sourceItemId", itemID);
        item.put("sku", sku);

        JSONObject components = new JSONObject();
        components.put("code", componentCode);
        components.put("path", order.getOrderData().getItems().get(0).getComponents().get(0).getPath());
        components.put("fetch", "true");

        item.append("components", components);

        JSONObject shipment = new JSONObject();
        shipment.put("shipTo", shipTo);
        shipment.put("carrier", carrier);

        JSONObject dest = new JSONObject();
        dest.put("name", destination);

        JSONObject orderData = new JSONObject();
        orderData.put("sourceOrderId", orderId);
        orderData.append("items", item);
        orderData.append("shipments", shipment);

        JSONObject postOrder = new JSONObject();
        postOrder.put("destination", dest);
        postOrder.put("orderData", orderData);

        return postOrder.toString();
    }

}
