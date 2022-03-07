package com.team8.backend;

import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DataBase {
    public static DynamoDbTable<DBItem> setup(){

        var ddb = DynamoDbClient.builder()
                .credentialsProvider(InstanceProfileCredentialsProvider.create())
                .region(Region.US_WEST_2)
                .build();
        var eddb = DynamoDbEnhancedClient
                .builder()
                .dynamoDbClient(ddb)
                .build();

        if(eddb == null)
            throw new RuntimeException("aws dynamo db connection was not created");

        return eddb.table("hp-siteflow-orders", TableSchema.fromBean(DBItem.class));

    }



}
