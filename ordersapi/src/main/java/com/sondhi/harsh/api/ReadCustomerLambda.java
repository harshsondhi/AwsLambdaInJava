package com.sondhi.harsh.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sondhi.harsh.api.dto.Customer;
import com.sondhi.harsh.api.dto.Order;

import java.util.List;
import java.util.stream.Collectors;

public class ReadCustomerLambda {


    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
    public APIGatewayProxyResponseEvent getCustomer(APIGatewayProxyRequestEvent request) throws JsonProcessingException {


        ScanResult scanResult = dynamoDB.scan(new ScanRequest().withTableName(System.getenv("CUSTOMERS_TABLE")));
        List<Customer> customers = scanResult.getItems().stream().map(cust -> new Customer(Integer.parseInt(cust.get("id").getN()),
                cust.get("firstName").getS(),
                cust.get("lastName").getS(),
                Integer.parseInt(cust.get("reward").getN()) )).collect(Collectors.toList());
        String jonOutput = objectMapper.writeValueAsString(customers);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jonOutput);
    }
}
