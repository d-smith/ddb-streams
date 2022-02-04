package org.ds.ddbstreams.quotes;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.Map;

public class QuoteProducer {

    public static void main(String... args) throws Exception {

        String[] symbols = {
                "DWAC",
                "SNDL",
                "CEI",
                "FAMI",
                "KMDN"
        };

        Region region = Region.US_WEST_2;
        DynamoDbClient dynamoDBClient = DynamoDbClient.builder()
                .region(region)
                .build();


        for (; ; ) {

            int idx = (int) (Math.random() * symbols.length);
            String symbol = symbols[idx];
            String price = String.valueOf((Math.random() * 600));
            String epochTime = String.valueOf(System.currentTimeMillis());

            PutItemResponse pir = dynamoDBClient.putItem(
                    PutItemRequest.builder()
                            .item(
                                    Map.of("symbol", AttributeValue.builder().s(symbol).build(),
                                            "price", AttributeValue.builder().n(price).build(),
                                            "ts", AttributeValue.builder().n(epochTime).build()
                                    )
                            )
                            .tableName("quotes")
                            .build()
            );

            System.out.println(String.format("%s %s %s", symbol, price, epochTime));
            Thread.sleep(1000);
        }
    }
}
