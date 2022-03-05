package com.team8.backend;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("callback")
public class SFCallbacks {

    private static final DynamoDbTable<DBItem> DB = DataBase.setup();


    @PostMapping("/generalStatus")
    public ResponseEntity<Integer> generalStatus(@RequestBody JSONObject order)throws NoSuchAlgorithmException, IOException, InvalidKeyException {
//      String Customer = order.getString("customer");
        //todo remove when properly imeplemnted
        String Customer = "TESTING";
      String ID = order.getString("orderid");
      String status = order.getString("orderstatus");

      var ordertoupdate = DB.getItem(Key.builder().partitionValue(Customer).sortValue(ID).build());
      ordertoupdate.getOrder().getOrderData().setStatus(status);
      DB.putItem(ordertoupdate);
      return ResponseEntity.ok().build();
    }


  @PostMapping("/ShipmentShipped")
  public void ShipShipped(@RequestBody JSONObject shipped_order)throws NoSuchAlgorithmException, IOException, InvalidKeyException {



  }


}
