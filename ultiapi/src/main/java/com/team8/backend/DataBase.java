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

    public static AwsCredentialsProvider prov = System.getProperty("os.name").equals("Linux") ?
            InstanceProfileCredentialsProvider.create() :
            EnvironmentVariableCredentialsProvider.create();

    private final DynamoDbTable<DBItem> client = setup_client();
    public DynamoDbTable<DBItem> get_client(){
        return client;
    }

    private DynamoDbTable<DBItem> setup_client(){
        var ddb = DynamoDbClient.builder()
                .credentialsProvider(prov)
                .region(Region.US_WEST_2)
                .build();
        var eddb = DynamoDbEnhancedClient .builder()
                .dynamoDbClient(ddb)
                .build();
        return eddb.table("hp-siteflow-orders", TableSchema.fromBean(DBItem.class));
    }



}
