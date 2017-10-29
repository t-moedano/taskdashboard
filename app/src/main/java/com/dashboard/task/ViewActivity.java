package com.dashboard.task;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class ViewActivity extends AppCompatActivity {


    static List<TASKSDO> listTasks;
    ListView listTasksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<TASKSDO> tasksItem = listTasks;
        listTasks = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

                    listTasksView = (ListView) findViewById(R.id.listItems);

                    ArrayAdapter<TASKSDO> adapter = new ArrayAdapter<TASKSDO>(this,
                            android.R.layout.simple_list_item_1, tasksItem);

                    listTasksView.setAdapter(adapter);




                }

    }

