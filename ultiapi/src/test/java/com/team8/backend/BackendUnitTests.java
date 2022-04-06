package com.team8.backend;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BackendUnitTests {
    @Test
    public void siteflow_connection() throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        SiteFlow SF = new SiteFlow();
        var resp = SF.GetAllOrders();
        assertEquals(resp.getStatusLine().getStatusCode(), 200);
    }


    @Test
    public void aws_dynamo_db_connection(){
        DynamoDbTable<DBItem> DB = (new DataBase()).get_client();
        assertNotNull(DB);
    }


}
