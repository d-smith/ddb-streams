# dd-streams

Playing around with ddb streams

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
