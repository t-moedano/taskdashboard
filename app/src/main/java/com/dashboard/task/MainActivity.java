package com.dashboard.task;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.dashboard.models.TASKSDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        final Context appContext = getApplicationContext();

        AWSConfiguration awsConfiguration = new AWSConfiguration(getApplicationContext());

        AWSConfiguration awsConfig = new AWSConfiguration(appContext);
        final IdentityManager identityManager = new IdentityManager(appContext, awsConfig);
        IdentityManager.setDefaultIdentityManager(identityManager);

        final AWSCredentialsProvider credentialsProvider = IdentityManager.getDefaultIdentityManager().getCredentialsProvider();

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(awsConfig)
                .build();



    }

    public void btnCreate(View view)
    {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);

    }

    public void btnView(View view)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {


                try {

                    TASKSDO newsItem = new TASKSDO();


                    newsItem.setNAME("teste");


                    Map<String, String> filterExpressionAttributeNames =
                            new HashMap<>();

                    filterExpressionAttributeNames.put("#STATUS", "STATUS");

                    Map<String, AttributeValue> filterExpressionAttributeValues =
                            new HashMap<>();

                    filterExpressionAttributeValues.put(
                            ":expectedName", new AttributeValue().withS("processed"));

                    DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                            .withHashKeyValues(newsItem)
                            .withFilterExpression("#STATUS = :expectedName")
                            .withExpressionAttributeNames(filterExpressionAttributeNames)
                            .withExpressionAttributeValues(filterExpressionAttributeValues)
                            .withConsistentRead(false);

                    PaginatedList<TASKSDO> result =
                            dynamoDBMapper.query(TASKSDO.class, queryExpression);

                    List<TASKSDO> listTasks = new ArrayList<>();

                    for (int i = 0; i < result.size(); i++) {
                        listTasks.add(result.get(i));
                    }

                    Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
                    ViewActivity.listTasks = listTasks;
                    startActivity(intent);
                }
                     catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            }).start();
    }
}
