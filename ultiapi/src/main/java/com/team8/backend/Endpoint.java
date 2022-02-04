package com.team8.backend;

import com.team8.backend.schema.Order;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.io.IOException;
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
    public ResponseEntity<Integer> submitOrder(@RequestParam String name, @RequestParam String skuID, @RequestParam String productID){

        System.out.println("name = " + name);
        System.out.println("skuID = " + skuID);
        System.out.println("productID = " +productID);
        //todo build order entity

        return ResponseEntity.ok().build();
    }

    @PostMapping("/t")
    public void test(@RequestBody Order o){
        var item = new DBItem();


        DB.putItem(item);

    }

}
