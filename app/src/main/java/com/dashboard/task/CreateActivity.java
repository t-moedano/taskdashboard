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

/**
 * This activity handles the logic to create a new task.
 * TO DO - refactor logic of Dynamo DB Mapper
 */
public class CreateActivity extends AppCompatActivity
{

    DynamoDBMapper dynamoDBMapper;

    String userId = "";
    static String INITIAL_STATUS  = "processed";
    static String INITIAL_USER_DONE = "not done";
    EditText taskName;
    EditText descriptionName;
    EditText priorityName;

    /**
     * When the activity is created, the dynamo db mapper is loaded.
     * TO DO - refactor this logic since this piece of code is beeing duplicated.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

    /**
     * When clicking the button to create a new task, get the information of the task from views and create a new item
     * to save in database.
     * TO DO - fix the name of the colunms (colunm NAME should be the name of the tasks and USER_SENT the user who sent the tasks
     * but for now the names are changed)
     * @param view
     */
    public void btnCreateTask(View view)
    {
        final TASKSDO taskItem = new TASKSDO();

        taskItem.setNAME(taskName.getText().toString());
        taskItem.setUSERDONE(INITIAL_USER_DONE);
        taskItem.setPRIORITY(priorityName.getText().toString());
        taskItem.setUSERSENT(userId);
        taskItem.setSTATUS(INITIAL_STATUS);
        taskItem.setDESCRIPTION(descriptionName.getText().toString());


        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    dynamoDBMapper.save(taskItem);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
