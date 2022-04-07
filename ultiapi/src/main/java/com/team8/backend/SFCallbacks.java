package com.team8.backend;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@RestController
@RequestMapping("callback")
public class SFCallbacks {

    private static final DynamoDbTable<DBItem> DB = DataBase.setup();


    @PostMapping("/generalStatus")
    public ResponseEntity<Integer> generalStatus(@RequestBody String o){

        System.out.println("o");
        var order = new JSONObject(o);
      String Customer = order.getString("customer");
      String ID = order.getString("orderid");
      String status = order.getString("orderstatus");


      System.out.println("updating order for: " + Customer + " id: " + ID + " status: " + status);

      var ordertoupdate = DB.getItem(Key.builder().partitionValue(Customer).sortValue(ID).build());
      ordertoupdate.getOrder().getOrderData().setStatus(status);
      DB.putItem(ordertoupdate);
      return ResponseEntity.ok().build();
    }


  @PostMapping("/ShipmentShipped")
  public ResponseEntity<Integer> ShipShipped(@RequestBody String o){
      var order = new JSONObject(o);
      String Customer = order.getString("customer");
      String ID = order.getString("orderId");
      String status = order.getString("status");
      String tracUrl = order.getString("trackingNUM");
      String trackNum = order.getString("trackingURL");
      System.out.println("updating order for: " + Customer + " id: " + ID + " to status: " + status + " traking url: "+ tracUrl + " tracking Number: " + trackNum);
      var ordershippupdate = DB.getItem(Key.builder().partitionValue(Customer).sortValue(ID).build());
      ordershippupdate.setTrackingnum(trackNum);
      ordershippupdate.setTrackingurl(tracUrl);
      DB.putItem(ordershippupdate);
      return ResponseEntity.ok().build();
  }


}
