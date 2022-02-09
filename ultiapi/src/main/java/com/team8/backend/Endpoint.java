package com.team8.backend;

import com.team8.backend.schema.Order;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class Endpoint {

    private static final DynamoDbTable<DBItem> DB = DataBase.setup();
    private static final SiteFlow SF = new SiteFlow();


    @GetMapping("/products")
    public Map<String, Object> getProducts() throws KeyException, NoSuchAlgorithmException, IOException {
        var response = SF.GetProducts();
        System.out.println(response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase());
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity, "UTF-8");
        return new JSONObject(body).toMap();
    }

    @GetMapping("/product")
    public Map<String, Object> getComponents( @RequestParam String productID) throws KeyException, NoSuchAlgorithmException, IOException {
      var response = SF.GetComponents(productID);
      System.out.println(response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase());
      HttpEntity entity = response.getEntity();
      String body = EntityUtils.toString(entity, "UTF-8");
      return new JSONObject(body).toMap();
    }

    /**
     *skus api call will ask siteflow for all the skus aviabliable
     */
    @GetMapping("/skus")
    public Map<String, Object> getSkus() throws KeyException, NoSuchAlgorithmException, IOException{
        var response = SF.GetSkus();
        System.out.println(response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase());
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity, "UTF-8");
        return new JSONObject(body).toMap();
    }

    @GetMapping("/orders")
    public Map<String, Object> getOrders() throws KeyException, NoSuchAlgorithmException, IOException {
        var response = SF.GetAllOrders();
        System.out.println(response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase());
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity, "UTF-8");
        return new JSONObject(body).toMap();
    }

    @PostMapping("/order")
    public ResponseEntity<Integer> submitOrder(@RequestBody Order order) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        //todo build order entity
        var item = new DBItem();
        item.setOrder(order);
        var response = SF.SubmitOrder(createOrder(order));
        DB.putItem(item);
        return (ResponseEntity<Integer>) response;
    }

    @PostMapping("/t")
    public void test(@RequestBody Order o){
        var item = new DBItem();


        DB.putItem(item);

    }
    /**
    * Creates an order for submission or validation
    *
    * @return JSON string of an order
    */
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
