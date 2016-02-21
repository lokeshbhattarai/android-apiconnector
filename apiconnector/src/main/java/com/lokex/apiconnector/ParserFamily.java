package com.lokex.apiconnector;

/**
 * Created by lokex on 5/8/15.
 */
public abstract class ParserFamily {

    public abstract void setTaskId(int taskId);
    public abstract int getTaskId();
    public abstract void setParserCallBack(HttpTaskListener httpTaskListener);
    public abstract HttpTaskListener getParserCallBack();
    protected abstract void handleServerResponse(String response);
}
