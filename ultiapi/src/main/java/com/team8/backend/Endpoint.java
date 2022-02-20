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
import java.util.UUID;

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
    public Map<String, Object> getOrders() throws KeyException, NoSuchAlgorithmException, IOException {
        var response = SF.GetAllOrders();
        System.out.println(response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase());
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity, "UTF-8");
        return new JSONObject(body).toMap();
    }


    @PostMapping("/order")
    public ResponseEntity<Integer> submitOrder(@RequestBody Order order) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        var o = new JSONObject(order);

        // set necessary values (postback, destination, etc)
        o.getJSONObject("destination").put("name", "wsu-test-team-8");
        o.getJSONObject("orderData").put("sourceOrderId", UUID.randomUUID().toString());
        o.getJSONObject("orderData").put("status", "pending");


        //debugging
//        System.out.println(SF.ValidateOrder(o.toString(1)));

        System.out.println("called with: " + o.toString());
        var response = SF.SubmitOrder(o.toString()).getStatusLine();
        System.out.println(response.toString());

        if (response.getStatusCode() != 200){
            System.out.println(response);
        } else { // only submit on ok status?
            var item = new DBItem();
            item.setOrder(order);
            DB.putItem(item);
        }

        // pass status from siteflow to front end
        return ResponseEntity.status(response.getStatusCode()).build();
    }

    @PostMapping("/t")
    public void test(@RequestBody Order o){
        var item = new DBItem();


        DB.putItem(item);

    }
    /**
     * this is what needs to happen in this upadate api call
     * 1. Either the partition key for the order needs to ge fished out of the json that siteflow gives
     * or you use the order id that siteflow gives (this will need to be ironed out on how orderID is created for siteflow
     * 2. update the item that corisponds to that id or uuid (specifically the status of the order)
     **/
    @PostMapping("/update")
    public void update(@RequestBody JSONObject order_to_update)throws  NoSuchAlgorithmException, IOException, InvalidKeyException{
        //todo grab order id from site flow response json  and update that orders status



    }

}
