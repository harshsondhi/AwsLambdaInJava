package com.sondhi.harsh.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondhi.harsh.api.dto.Customer;
import com.sondhi.harsh.api.dto.Order;

public class CreateCustomerLambda {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    public APIGatewayProxyResponseEvent createCustomer(APIGatewayProxyRequestEvent request) throws JsonProcessingException {

        Customer customer = objectMapper.readValue(request.getBody(), Customer.class);

        Table table = dynamoDB.getTable(System.getenv("CUSTOMERS_TABLE"));
        Item item = new Item().withPrimaryKey("id", customer.id)
                .withString("firstName", customer.firstName)
                .withString("lastName", customer.lastName)
                .withInt("reward", customer.reward);
        table.putItem(item);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("OrderId:= " + customer.id);
    }
}
