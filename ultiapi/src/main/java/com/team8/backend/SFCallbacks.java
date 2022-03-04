package com.team8.backend;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("callback")
public class SFCallbacks {

    private static final DynamoDbTable<DBItem> DB = DataBase.setup();

    /**
     * this is what needs to happen in this upadate api call
     * 1. Either the partition key for the order needs to ge fished out of the json that siteflow gives
     * or you use the order id that siteflow gives (this will need to be ironed out on how orderID is created for siteflow
     * 2. update the item that corisponds to that id or uuid (specifically the status of the order)
     **/
    @PostMapping("/update")
    public void update(@RequestBody JSONObject order_to_update)throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        //todo grab order id from site flow response json  and update that orders status



    }

    @GetMapping("/t")
    public String test(){
        System.out.println("called");
        return "works";
    }
}
