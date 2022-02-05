
Deployment

export env value for DDB_STREAM_ARN then sls deploy --region us-west-2

Fanout of quote update to positions via single proc DDB stream is a losing proposition...


```
2022-02-05T01:13:41.229Z	bdb31cd0-1ba2-4e4c-87f4-9e667e7a6674	INFO	
{
    "Records": [
        {
            "eventID": "7b88d38ec9ae44dceb8a384cb2173b23",
            "eventName": "MODIFY",
            "eventVersion": "1.1",
            "eventSource": "aws:dynamodb",
            "awsRegion": "us-west-2",
            "dynamodb": {
                "ApproximateCreationDateTime": 1644014501,
                "Keys": {
                    "symbol": {
                        "S": "CEI"
                    }
                },
                "NewImage": {
                    "symbol": {
                        "S": "CEI"
                    },
                    "price": {
                        "N": "139.2675643770462"
                    },
                    "ts": {
                        "N": "1644014501478"
                    }
                },
                "SequenceNumber": "238800000000027629714443",
                "SizeBytes": 43,
                "StreamViewType": "NEW_IMAGE"
            },
            "eventSourceARN": "arn:aws:dynamodb:us-west-2:nnnnnnnnnnnn:table/quotes/stream/2022-02-04T21:35:20.029"
        },
        {
            "eventID": "a80b8279becb8775869571b88c06e8bb",
            "eventName": "MODIFY",
            "eventVersion": "1.1",
            "eventSource": "aws:dynamodb",
            "awsRegion": "us-west-2",
            "dynamodb": {
                "ApproximateCreationDateTime": 1644014501,
                "Keys": {
                    "symbol": {
                        "S": "DWAC"
                    }
                },
                "NewImage": {
                    "symbol": {
                        "S": "DWAC"
                    },
                    "price": {
                        "N": "277.3260952221545"
                    },
                    "ts": {
                        "N": "1644014501603"
                    }
                },
                "SequenceNumber": "238900000000027629714583",
                "SizeBytes": 45,
                "StreamViewType": "NEW_IMAGE"
            },
            "eventSourceARN": "arn:aws:dynamodb:us-west-2:nnnnnnnnnnnn:table/quotes/stream/2022-02-04T21:35:20.029"
        }
    ]
}

2022-02-05T01:13:41.229Z	bdb31cd0-1ba2-4e4c-87f4-9e667e7a6674	INFO	
{
    "eventID": "7b88d38ec9ae44dceb8a384cb2173b23",
    "eventName": "MODIFY",
    "eventVersion": "1.1",
    "eventSource": "aws:dynamodb",
    "awsRegion": "us-west-2",
    "dynamodb": {
        "ApproximateCreationDateTime": 1644014501,
        "Keys": {
            "symbol": {
                "S": "CEI"
            }
        },
        "NewImage": {
            "symbol": {
                "S": "CEI"
            },
            "price": {
                "N": "139.2675643770462"
            },
            "ts": {
                "N": "1644014501478"
            }
        },
        "SequenceNumber": "238800000000027629714443",
        "SizeBytes": 43,
        "StreamViewType": "NEW_IMAGE"
    },
    "eventSourceARN": "arn:aws:dynamodb:us-west-2:nnnnnnnnnnnn:table/quotes/stream/2022-02-04T21:35:20.029"
}

2022-02-05T01:13:41.229Z	bdb31cd0-1ba2-4e4c-87f4-9e667e7a6674	INFO	quote to stream latency: 9119751
2022-02-05T01:13:41.229Z	bdb31cd0-1ba2-4e4c-87f4-9e667e7a6674	INFO	
{
    "eventID": "a80b8279becb8775869571b88c06e8bb",
    "eventName": "MODIFY",
    "eventVersion": "1.1",
    "eventSource": "aws:dynamodb",
    "awsRegion": "us-west-2",
    "dynamodb": {
        "ApproximateCreationDateTime": 1644014501,
        "Keys": {
            "symbol": {
                "S": "DWAC"
            }
        },
        "NewImage": {
            "symbol": {
                "S": "DWAC"
            },
            "price": {
                "N": "277.3260952221545"
            },
            "ts": {
                "N": "1644014501603"
            }
        },
        "SequenceNumber": "238900000000027629714583",
        "SizeBytes": 45,
        "StreamViewType": "NEW_IMAGE"
    },
    "eventSourceARN": "arn:aws:dynamodb:us-west-2:nnnnnnnnnnnn:table/quotes/stream/2022-02-04T21:35:20.029"
}

2022-02-05T01:13:41.229Z	bdb31cd0-1ba2-4e4c-87f4-9e667e7a6674	INFO	quote to stream latency: 9119626
2022-02-05T01:13:41.231Z	bdb31cd0-1ba2-4e4c-87f4-9e667e7a6674	INFO	[
  { symbol: 'CEI', price: '139.2675643770462', ts: '1644014501478' },
  { symbol: 'DWAC', price: '277.3260952221545', ts: '1644014501603' }
]
2022-02-05T01:13:41.231Z	bdb31cd0-1ba2-4e4c-87f4-9e667e7a6674	INFO	query for CEI
2022-02-05T01:17:06.981Z	bdb31cd0-1ba2-4e4c-87f4-9e667e7a6674	ERROR	Invoke Error 	
{
    "errorType": "ProvisionedThroughputExceededException",
    "errorMessage": "The level of configured provisioned throughput for the table was exceeded. Consider increasing your provisioning level with the UpdateTable API.",
    "code": "ProvisionedThroughputExceededException",
    "message": "The level of configured provisioned throughput for the table was exceeded. Consider increasing your provisioning level with the UpdateTable API.",
    "time": "2022-02-05T01:17:06.980Z",
    "requestId": "654D0E5NT5C8M0NBQJ26O95UJRVV4KQNSO5AEMVJF66Q9ASUAAJG",
    "statusCode": 400,
    "retryable": true,
    "stack": [
        "ProvisionedThroughputExceededException: The level of configured provisioned throughput for the table was exceeded. Consider increasing your provisioning level with the UpdateTable API.",
        "    at Request.extractError (/var/runtime/node_modules/aws-sdk/lib/protocol/json.js:52:27)",
        "    at Request.callListeners (/var/runtime/node_modules/aws-sdk/lib/sequential_executor.js:106:20)",
        "    at Request.emit (/var/runtime/node_modules/aws-sdk/lib/sequential_executor.js:78:10)",
        "    at Request.emit (/var/runtime/node_modules/aws-sdk/lib/request.js:688:14)",
        "    at Request.transition (/var/runtime/node_modules/aws-sdk/lib/request.js:22:10)",
        "    at AcceptorStateMachine.runTo (/var/runtime/node_modules/aws-sdk/lib/state_machine.js:14:12)",
        "    at /var/runtime/node_modules/aws-sdk/lib/state_machine.js:26:10",
        "    at Request.<anonymous> (/var/runtime/node_modules/aws-sdk/lib/request.js:38:9)",
        "    at Request.<anonymous> (/var/runtime/node_modules/aws-sdk/lib/request.js:690:12)",
        "    at Request.callListeners (/var/runtime/node_modules/aws-sdk/lib/sequential_executor.js:116:18)"
    ]
}

END RequestId: bdb31cd0-1ba2-4e4c-87f4-9e667e7a6674
REPORT RequestId: bdb31cd0-1ba2-4e4c-87f4-9e667e7a6674	Duration: 205754.57 ms	Billed Duration: 205755 ms	Memory Size: 1024 MB	Max Memory Used: 123 MB	Init Duration: 429.74 ms	
START RequestId: 5a4b814c-66e9-4a78-a358-aa4010aea5bb Version: $LATEST
2022-02-05T01:19:35.549Z	5a4b814c-66e9-4a78-a358-aa4010aea5bb	INFO	
{
    "Records": [
        {
            "eventID": "7b88d38ec9ae44dceb8a384cb2173b23",
            "eventName": "MODIFY",
            "eventVersion": "1.1",
            "eventSource": "aws:dynamodb",
            "awsRegion": "us-west-2",
            "dynamodb": {
                "ApproximateCreationDateTime": 1644014501,
                "Keys": {
                    "symbol": {
                        "S": "CEI"
                    }
                },
                "NewImage": {
                    "symbol": {
                        "S": "CEI"
                    },
                    "price": {
                        "N": "139.2675643770462"
                    },
                    "ts": {
                        "N": "1644014501478"
                    }
                },
                "SequenceNumber": "238800000000027629714443",
                "SizeBytes": 43,
                "StreamViewType": "NEW_IMAGE"
            },
            "eventSourceARN": "arn:aws:dynamodb:us-west-2:nnnnnnnnnnnn:table/quotes/stream/2022-02-04T21:35:20.029"
        },
        {
            "eventID": "a80b8279becb8775869571b88c06e8bb",
            "eventName": "MODIFY",
            "eventVersion": "1.1",
            "eventSource": "aws:dynamodb",
            "awsRegion": "us-west-2",
            "dynamodb": {
                "ApproximateCreationDateTime": 1644014501,
                "Keys": {
                    "symbol": {
                        "S": "DWAC"
                    }
                },
                "NewImage": {
                    "symbol": {
                        "S": "DWAC"
                    },
                    "price": {
                        "N": "277.3260952221545"
                    },
                    "ts": {
                        "N": "1644014501603"
                    }
                },
                "SequenceNumber": "238900000000027629714583",
                "SizeBytes": 45,
                "StreamViewType": "NEW_IMAGE"
            },
            "eventSourceARN": "arn:aws:dynamodb:us-west-2:nnnnnnnnnnnn:table/quotes/stream/2022-02-04T21:35:20.029"
        }
    ]
}

2022-02-05T01:19:35.549Z	5a4b814c-66e9-4a78-a358-aa4010aea5bb	INFO	
{
    "eventID": "7b88d38ec9ae44dceb8a384cb2173b23",
    "eventName": "MODIFY",
    "eventVersion": "1.1",
    "eventSource": "aws:dynamodb",
    "awsRegion": "us-west-2",
    "dynamodb": {
        "ApproximateCreationDateTime": 1644014501,
        "Keys": {
            "symbol": {
                "S": "CEI"
            }
        },
        "NewImage": {
            "symbol": {
                "S": "CEI"
            },
            "price": {
                "N": "139.2675643770462"
            },
            "ts": {
                "N": "1644014501478"
            }
        },
        "SequenceNumber": "238800000000027629714443",
        "SizeBytes": 43,
        "StreamViewType": "NEW_IMAGE"
    },
    "eventSourceARN": "arn:aws:dynamodb:us-west-2:nnnnnnnnnnnn:table/quotes/stream/2022-02-04T21:35:20.029"
}

2022-02-05T01:19:35.549Z	5a4b814c-66e9-4a78-a358-aa4010aea5bb	INFO	quote to stream latency: 9474071
2022-02-05T01:19:35.549Z	5a4b814c-66e9-4a78-a358-aa4010aea5bb	INFO	
{
    "eventID": "a80b8279becb8775869571b88c06e8bb",
    "eventName": "MODIFY",
    "eventVersion": "1.1",
    "eventSource": "aws:dynamodb",
    "awsRegion": "us-west-2",
    "dynamodb": {
        "ApproximateCreationDateTime": 1644014501,
        "Keys": {
            "symbol": {
                "S": "DWAC"
            }
        },
        "NewImage": {
            "symbol": {
                "S": "DWAC"
            },
            "price": {
                "N": "277.3260952221545"
            },
            "ts": {
                "N": "1644014501603"
            }
        },
        "SequenceNumber": "238900000000027629714583",
        "SizeBytes": 45,
        "StreamViewType": "NEW_IMAGE"
    },
    "eventSourceARN": "arn:aws:dynamodb:us-west-2:nnnnnnnnnnnn:table/quotes/stream/2022-02-04T21:35:20.029"
}

2022-02-05T01:19:35.549Z	5a4b814c-66e9-4a78-a358-aa4010aea5bb	INFO	quote to stream latency: 9473946
2022-02-05T01:19:35.551Z	5a4b814c-66e9-4a78-a358-aa4010aea5bb	INFO	[
  { symbol: 'CEI', price: '139.2675643770462', ts: '1644014501478' },
  { symbol: 'DWAC', price: '277.3260952221545', ts: '1644014501603' }
]
2022-02-05T01:19:35.551Z	5a4b814c-66e9-4a78-a358-aa4010aea5bb	INFO	query for CEI
2022-02-05T01:22:59.080Z	5a4b814c-66e9-4a78-a358-aa4010aea5bb	INFO	updated 14329 positions
2022-02-05T01:22:59.080Z	5a4b814c-66e9-4a78-a358-aa4010aea5bb	INFO	query for DWAC
END RequestId: 5a4b814c-66e9-4a78-a358-aa4010aea5bb
REPORT RequestId: 5a4b814c-66e9-4a78-a358-aa4010aea5bb	Duration: 300100.10 ms	Billed Duration: 300000 ms	Memory Size: 1024 MB	Max Memory Used: 137 MB	
2022-02-05T01:24:35.650Z 5a4b814c-66e9-4a78-a358-aa4010aea5bb Task timed out after 300.10 seconds
```