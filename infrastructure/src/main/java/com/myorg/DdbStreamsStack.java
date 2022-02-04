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
    private boolean buildPositionsTable;
    private boolean buildQuotesTable;
    public DdbStreamsStack(final Construct scope, final String id) {
        this(scope, id, null, false, false);
    }

    private void createPositionsTable(final Construct scope) {
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

    private void createQuotesTables(final Construct scope) {
        Table table = Table.Builder.create(this, "quotes")
                .tableName("quotes")
                .partitionKey(Attribute.builder()
                        .name("symbol")
                        .type(AttributeType.STRING)
                        .build())
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();
    }

    public DdbStreamsStack(final Construct scope, final String id, final StackProps props,
                           boolean buildPositionsTable, boolean buildQuotesTable) {
        super(scope, id, props);

        this.buildPositionsTable = buildPositionsTable;
        this.buildQuotesTable = buildQuotesTable;

        if(buildPositionsTable) {
            createPositionsTable(scope);
        }

        if(buildQuotesTable) {
            createQuotesTables(scope);
        }

    }
}
