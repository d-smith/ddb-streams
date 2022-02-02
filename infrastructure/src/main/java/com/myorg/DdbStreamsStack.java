package com.myorg;

import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.GlobalSecondaryIndexProps;
import software.amazon.awscdk.services.dynamodb.Table;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class DdbStreamsStack extends Stack {
    public DdbStreamsStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public DdbStreamsStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        Table table = Table.Builder.create(this, "positions")
                .tableName("positions")
                .partitionKey(Attribute.builder()
                        .name("account-id")
                        .type(AttributeType.STRING)
                        .build())
                .sortKey(Attribute.builder()
                        .name("symbol")
                        .type(AttributeType.STRING)
                        .build())
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        table.addGlobalSecondaryIndex(GlobalSecondaryIndexProps
                .builder()
                .indexName("position-by-symbol")
                .partitionKey(Attribute.builder()
                        .name("symbol")
                        .type(AttributeType.STRING)
                        .build())
                .build());
    }
}
