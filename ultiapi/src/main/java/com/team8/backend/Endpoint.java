package com.team8.backend;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Endpoint {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/products")
    public Map<String, Object> getProducts() throws KeyException, NoSuchAlgorithmException, IOException {
        var sf = new SiteFlow();
        var response = sf.GetProducts();
        System.out.println(response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase());
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity, "UTF-8");
        return new JSONObject(body).toMap();
    }

    @GetMapping("/product")
    public Map<String, Object> getComponents( @RequestParam String productID) throws KeyException, NoSuchAlgorithmException, IOException {
      var sf = new SiteFlow();
      var response = sf.GetComponents(productID);
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
        var sf = new SiteFlow();
        var response = sf.GetSkus();
        System.out.println(response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase());
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity, "UTF-8");
        return new JSONObject(body).toMap();
    }

    @GetMapping("/orders")
    public Map<String, Object> getOrders() throws KeyException, NoSuchAlgorithmException, IOException {
        var sf = new SiteFlow();
        var response = sf.GetAllOrders();
        System.out.println(response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase());
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity, "UTF-8");
        return new JSONObject(body).toMap();
    }

    @PostMapping("/order")
    public ResponseEntity<Integer> submitOrder(@RequestParam String skuID, @RequestParam String productID){

        System.out.println("skuID = " + skuID);
        System.out.println("productID = " +productID);
        //todo build order entity

        return ResponseEntity.ok().build();
    }

}
