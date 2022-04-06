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

    private static final DynamoDbTable<DBItem> DB = (new DataBase()).get_client();


    @PostMapping("/generalStatus")
    public ResponseEntity<Integer> generalStatus(@RequestBody String o){

        var order = new JSONObject(o);
        String Customer = order.getString("customer");
        String ID = order.getString("orderid");
        String status = order.getString("orderstatus");;


        System.out.println("updating order for: " + Customer + " id: " + ID + " status: " + status);

        var ordertoupdate = DB.getItem(Key.builder().partitionValue(Customer).sortValue(ID).build());
        ordertoupdate.getOrder().getOrderData().setStatus(status);
        DB.putItem(ordertoupdate);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/ShipmentShipped")
    public void ShipShipped(@RequestBody JSONObject shipping_update){
        // TODO: 3/8/2022 figureout what needs to be done interms of shipping information from siteflow
        String Customer = shipping_update.getString("customer");
        String ID =  shipping_update.getString("orderId");
        String status = shipping_update.getString("status");
        String tracUrl = shipping_update.getString("trackingNUM");
        String trackNum = shipping_update.getString("trackingURL");
        System.out.println("updating order for: " + Customer + " id: " + ID + " to status: " + status + " traking url: "+ tracUrl + " tracking Number: " + trackNum);
        var ordershippupdate = DB.getItem(Key.builder().partitionValue(Customer).sortValue(ID).build());
        //todo figureout if a new entry to the db is needed
//      return ResponseEntity.ok().build();
    }


}
