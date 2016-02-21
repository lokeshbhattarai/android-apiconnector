package com.lokex.apiconnector.sample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.lokex.apiconnector.FailureReason;
import com.lokex.apiconnector.HttpTaskListener;
import com.lokex.apiconnector.ParserFactory;
import com.lokex.apiconnector.ParserFamily;
import com.lokex.apiconnector.ServerConnectorDTO;
import com.lokex.apiconnector.ServerTask;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnGetRequest,btnPostRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    void initViews(){

        btnGetRequest = (Button)findViewById(R.id.button_initiate_get);
        btnGetRequest.setOnClickListener(this);
        btnPostRequest = (Button)findViewById(R.id.button_initiate_post);
        btnPostRequest.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.button_initiate_get:


                ServerConnectorDTO getServerConnector = new ServerConnectorDTO();
                getServerConnector.setUrlToConnect("http://jsonplaceholder.typicode.com/posts/1");//todo add url


                ParserFamily parser = ParserFactory.create(ParserFactory.ParserType.LOGIN);
                parser.setParserCallBack(new HttpTaskListener() {
                    @Override
                    public void onAPiResponseObtained(int taskId, Object response) {

                    }

                    @Override
                    public void onApiResponseFailed(int taskId, FailureReason reason) {

                    }

                    @Override
                    public void onTaskStarted(int taskId) {

                    }

                    @Override
                    public void onTaskCancelled(int taskId) {

                    }
                });

                ServerTask serverTask = new ServerTask(parser,getServerConnector);
                serverTask.execute(ServerTask.RequestMethod.GET);

                break;


            case R.id.button_initiate_post:

                Map<String,String> dataParams = new HashMap<>();
                dataParams.put("key1","value1");
                dataParams.put("key2","value2");

                ServerConnectorDTO serverConnector = new ServerConnectorDTO();
                serverConnector.setUrlToConnect("");//todo add url
                serverConnector.setDataListNameValuePair(dataParams);


                ParserFamily parser1 = ParserFactory.create(ParserFactory.ParserType.LOGIN);
                parser1.setParserCallBack(new HttpTaskListener() {
                    @Override
                    public void onAPiResponseObtained(int taskId, Object response) {

                    }

                    @Override
                    public void onApiResponseFailed(int taskId, FailureReason reason) {

                    }

                    @Override
                    public void onTaskStarted(int taskId) {

                    }

                    @Override
                    public void onTaskCancelled(int taskId) {

                    }
                });

                ServerTask postServerTask = new ServerTask(parser1,serverConnector);
                postServerTask.execute(ServerTask.RequestMethod.POST);

                break;

        }


    }
}
