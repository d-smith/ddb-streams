const { SNS } = require('aws-sdk');
const AWS = require('aws-sdk');
let ddb=  new AWS.DynamoDB();

let handler = async (event) => {
    console.log(JSON.stringify(event));
    let recs = event.Records;
    if(!recs) {
        return;
    }

    //Log us some latency
    recs.forEach(r => {
        console.log(JSON.stringify(r))
        let latency = Date.now() - r.dynamodb.NewImage.ts.N;
        console.log(`quote to stream latency: ${latency}`)
    });

    //Send all the quotes through - don't conflate, etc.
    let symbols = recs.map(r => {
        return {
            symbol:  r.dynamodb.NewImage.symbol.S,
            price: r.dynamodb.NewImage.price.N,
            ts: r.dynamodb.NewImage.ts.N
        };
    });
    
    console.log(symbols);

    //Update the positions  table with the new price for position holding
    //the given symbol. This update will generate a stream of position
    //updates which can trigger market value recalculation
    for(const s of symbols) {
        console.log(`query for ${s.symbol}`)
        let positions = await ddb.query({
            KeyConditions: {
                'symbol': {
                    ComparisonOperator: 'EQ',
                    AttributeValueList:[
                        {
                            'S':s.symbol
                        }
                    ]
                }
            },
            Select: 'ALL_ATTRIBUTES',
            TableName: 'positions',
            IndexName: 'position-by-symbol'
        }).promise();

        let items = positions.Items;
        for(const item of items) {
            //console.log(`update for ${JSON.stringify(item)}`)
            let r = await ddb.updateItem({
                Key: {
                    'account-id': {
                        'S':item["account-id"].S
                    },
                    'symbol': {
                        'S':item["symbol"].S
                    }
                },
                TableName:'positions',
                ExpressionAttributeNames: {
                    '#PC':'price'
                },
                ExpressionAttributeValues: {
                    ':pc': {
                        N:s.price
                    }
                },
                UpdateExpression: "SET #PC = :pc"
            }).promise();
            
            //console.log(JSON.stringify(r));
            
        }
        console.log(`updated ${items.length} positions`);
    }
    
    

}

module.exports = {
    handler
};