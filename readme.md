# ddb-streams

Playing around with ddb streams, specifically around using inserts to a table to drive processing downstream for related data in another table.

Sub-projects

* infrastructure - CDK project to create two tables (positions, quotes) and a change stream for the quote table
* positions - code to seed a positions table with 100,000 positions, some example query code, and code to generate quotes for the symbols associated with the positions
* quotestream - serverless project to process the ddb quote change stream using a lambda to update the positions table

## Misc cli stuff

Scan the tables

```
aws dynamodb scan --table positions
```

Query

```
aws dynamodb query --table-name positions \
--key-condition-expression "symbol=:s" \
--expression-attribute-values '{":s":{"S":"AAPL"}}' \
--index-name position-by-symbol
```
