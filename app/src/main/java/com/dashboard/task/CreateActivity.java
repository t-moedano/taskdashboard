package com.dashboard.task;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.dashboard.models.TASKSDO;

import static com.amazonaws.auth.policy.actions.DynamoDBv2Actions.Query;

public class CreateActivity extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;

    String userId = "";
    EditText taskName;
    EditText descriptionName;
    EditText priorityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Context appContext = getApplicationContext();

        AWSConfiguration awsConfiguration = new AWSConfiguration(getApplicationContext());

        AWSConfiguration awsConfig = new AWSConfiguration(appContext);
        final IdentityManager identityManager = new IdentityManager(appContext, awsConfig);
        IdentityManager.setDefaultIdentityManager(identityManager);

        final AWSCredentialsProvider credentialsProvider = IdentityManager.getDefaultIdentityManager().getCredentialsProvider();

        userId = identityManager.getCachedUserID();
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(awsConfig)
                .build();

        taskName = (EditText) findViewById(R.id.txtName);
        descriptionName = (EditText) findViewById(R.id.txtDescription);
        priorityName = (EditText) findViewById(R.id.numPriority);


    }

    public void btnCreateTask(View view)
    {
        final TASKSDO taskItem = new TASKSDO();

        // Use IdentityManager to get the user identity id.
        taskItem.setNAME(taskName.getText().toString());
        taskItem.setUSERDONE("not done");
        taskItem.setPRIORITY(priorityName.getText().toString());
        taskItem.setUSERSENT(userId);
        taskItem.setSTATUS("processed");
        taskItem.setDESCRIPTION(descriptionName.getText().toString());


        new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    dynamoDBMapper.save(taskItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
