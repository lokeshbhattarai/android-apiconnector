package com.lokex.apiconnector;

import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ServerTask extends AsyncTask<Void,Void,String>{

    public enum RequestMethod{
        GET,
        POST
    }

    private final String TAG = ServerTask.class.getSimpleName();

    private ServerConnectorDTO mServerConnectorDTO;

    private RequestMethod method;

    private ParserFamily parser;

    /**
     * @param serverConnectorDTO instance of {@link ServerConnectorDTO}. Can not be null
     *
     * */

    public ServerTask(ParserFamily parser, ServerConnectorDTO serverConnectorDTO){

        this(parser,serverConnectorDTO,-1);

    }

    /**
     * @param serverConnectorDTO instance of {@link ServerConnectorDTO}. Can not be null
     *
     * */

    public ServerTask(ParserFamily parser, ServerConnectorDTO serverConnectorDTO, int taskId){

        if(serverConnectorDTO!=null){
            mServerConnectorDTO = serverConnectorDTO;
        }else {
            throw new NullPointerException(serverConnectorDTO.getClass().toString()+" cannot be null");
        }

        this.parser = parser;
        this.parser.setTaskId(taskId);

    }

    public void execute(RequestMethod method){
        if(method!=null){
            this.method = method;

            this.execute();
        }else {
            throw new NullPointerException("Request cannot be null");
        }

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if(parser.getParserCallBack()!=null)parser.getParserCallBack().onTaskStarted(parser.getTaskId());

    }

    @Override
    protected String doInBackground(Void... params) {

        String serverResponse = null;

        switch (method){
            case GET:
                try {
                    serverResponse = new HttpGetServerConnector(mServerConnectorDTO).connectWithGetRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                    serverResponse = "";
                }
                break;

            case POST:
                try {
                    serverResponse = new HttpPostServerConnector(mServerConnectorDTO).connectServerWithHttpPostRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                    serverResponse =  "";
                }
                break;

            default:
                throw new IllegalArgumentException("Request method can be either GET or POST only");
        }


        return serverResponse;
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(parser.getParserCallBack()!=null)parser.getParserCallBack().onTaskStarted(parser.getTaskId());

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(this.parser!=null){
            this.parser.handleServerResponse(result);
        }

    }



}
