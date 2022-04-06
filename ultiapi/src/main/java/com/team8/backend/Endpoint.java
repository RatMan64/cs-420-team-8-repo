package com.team8.backend;

import com.team8.backend.schema.Order;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

@RestController
public class Endpoint {

    private static final DynamoDbTable<DBItem> DB = (new DataBase()).get_client();
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
     *skus api call will ask site-flow for all the skus available
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
    public Map<String, Object> getOrders(){

        var result = DB.scan().items();

        JSONObject jo = new JSONObject();
        jo.put("data", new JSONArray(result));
        return jo.toMap();
    }


    @PostMapping("/order")
    public ResponseEntity<Integer> submitOrder(@RequestBody Order order) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        order.getOrderData().setCustomerName("wsu-test-team-8");
        var o = new JSONObject(order);

        var id = UUID.randomUUID().toString();
        // set necessary values (postback, destination, etc)
        o.getJSONObject("destination").put("name", "wsu-test-team-8");
        o.getJSONObject("orderData").put("sourceOrderId", id);
        o.getJSONObject("orderData").put("status", "pending");


        //debugging
//        System.out.println(SF.ValidateOrder(o.toString(1)));

//        System.out.println("called with: " + o.toString(1));
        var response = SF.SubmitOrder(o.toString()).getStatusLine();
        System.out.println(response.toString());

        if (response.getStatusCode() != 201){
            System.out.println(response);
        } else { // only submit on ok status?
            var item = new DBItem();
            item.setUser(order.getOrderData().getCustomerName());
            item.setOrder(order);
            item.setId(id);
            DB.putItem(item);
        }

        // pass status from siteflow to front end
        return ResponseEntity.status(response.getStatusCode()).build();
    }

}
