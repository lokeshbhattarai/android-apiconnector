package com.lokex.apiconnector;


/**
 * Created by lokex on 5/8/15.
 */
public class LibraryParser extends ParserFamily{

    private final String TAG =  LibraryParser.class.getSimpleName();

    private HttpTaskListener mCallback;
    private int taskId;

    @Override
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public int getTaskId() {
        return this.taskId;
    }

    @Override
    public void setParserCallBack(HttpTaskListener httpTaskListener) {

        this.mCallback = httpTaskListener;
    }

    @Override
    public HttpTaskListener getParserCallBack() {
       return this.mCallback;
    }

    @Override
    public void handleServerResponse(String response) {

        AppLogger.showLog(TAG,"serverResponse to handle::"+response);

        //todo callback should be invoked after successful parsing
        //for now just calling one of the possible methods
        if(mCallback!=null){
            mCallback.onAPiResponseObtained(taskId, response);
        }

    }
}
