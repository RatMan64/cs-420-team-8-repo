package com.team8.backend;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DataBase {
    public static DynamoDbTable<DBItem> setup(){

        AwsCredentialsProvider prov;
        if (System.getProperty("os.name").equals("Linux")){
            System.out.println("running on linux using EC2 credentials");
            prov = InstanceProfileCredentialsProvider.create();
        } else {
            System.out.println("running on windows using env credentials");
            prov = EnvironmentVariableCredentialsProvider.create();
        }
        var ddb = DynamoDbClient.builder()
                .credentialsProvider(prov)
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
