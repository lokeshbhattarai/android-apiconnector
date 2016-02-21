package com.lokex.apiconnector;

/**
 * Created by lokex on 12/23/14.
 */
public interface HttpTaskListener {

    /**method is invoked when response from rest-api is successfully obtained
     *
     * @param taskId the id for the task that was started with. The value will be -1 if no any value was used to distinguish the task
     * @param response the server response obtained with communication with rest-api. It will contain either {@link org.json.JSONObject} or {@link org.json.JSONArray}
     * */
    public void onAPiResponseObtained(int taskId, Object response);

    /**method is invoked when no response could be obtained from rest-api
     * @param taskId the id for the task that was started with. The value will be -1 if no any value was used to distinguish the task
     *
     *@param reason the reason explaining the cause for api communication failure
     * */
    public void onApiResponseFailed(int taskId, FailureReason reason);

    /**method is invoked when the server communication task is initiated.
     * @param taskId the id for the task that was started with. The value will be -1 if no any value was used to distinguish the task
     * */
    public void onTaskStarted(int taskId);

    /**method is invoked when the async task for server communication is cancelled.
     * @param taskId the id for the task that was started with. The value will be -1 if no any value was used to distinguish the task
     *
     * */
    public void onTaskCancelled(int taskId);


}
