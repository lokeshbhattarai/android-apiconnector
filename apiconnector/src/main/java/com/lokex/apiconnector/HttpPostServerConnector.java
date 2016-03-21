package com.lokex.apiconnector;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by lokex on 12/23/14.
 */
final public class HttpPostServerConnector {

    private final String TAG = HttpPostServerConnector.class.getSimpleName();

    private String serverResponse;

    private ServerConnectorDTO serverConnectorDto;

    public HttpPostServerConnector(ServerConnectorDTO connector){

        this.serverConnectorDto = connector;

    }


    public String connectServerWithHttpPostRequest() throws Exception {


        AppLogger.showLog(TAG, "*************CONNECTING SERVER*******");
        AppLogger.showLog(TAG, "url to connect:: " + serverConnectorDto.getUrlToConnect());


        URL url  = new URL(serverConnectorDto.getUrlToConnect());

        String postData = "";
        boolean isJsonData = false;

        if(serverConnectorDto.getDataJsonString()!=null){

            postData = serverConnectorDto.getDataJsonString();
            isJsonData = true;
        }else{
            postData = WebUtil.getParams(serverConnectorDto.getDataListNameValuePair());
        }

        AppLogger.showLog(TAG, "post data :: " + postData);

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty( "Content-Type", isJsonData?"application/json":"application/x-www-form-urlencoded;charset="+"UTF-8");
        conn.setRequestProperty( "Accept", "application/json");//assuming the response is json data
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setUseCaches(false);

        try {
            DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
            wr.write( postDataBytes );
            wr.flush();;
            wr.close();

            int responseCode = conn.getResponseCode();
            AppLogger.showLog(TAG,"response code::"+responseCode);

            if(responseCode>0){//ignoring negative response code if so ever. You can check against any other response codes.
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                AppLogger.showLog(TAG,"response from server::"+response);

                return response.toString();

            }else{
                AppLogger.showLog(TAG,"Ignoring the connection since response code is not valid");
                return "";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "";
        }



    }

/*
    public String connectServerWithHttpPostRequest() throws Exception {


        HttpPost httpPostRequest = new HttpPost(serverConnectorDto.getUrlToConnect());

        HttpClient httpClient = new DefaultHttpClient();

        HttpResponse httpResponse;

        if(serverConnectorDto.getDataListNameValuePair()!=null)

        {

            try {


                httpPostRequest.setEntity(new UrlEncodedFormEntity(serverConnectorDto.getDataListNameValuePair()));


            }catch(UnsupportedEncodingException e){

                serverResponse = "";

                e.printStackTrace();

            }catch (Exception e) {

                e.printStackTrace();

                serverResponse = "";

            }


        }else{

            AppLog.showLog(TAG, "NAME VALUE PAIR IS NULL ");

        }

        if(serverConnectorDto.getDataJsonString()!=null)

        {


            try {

                StringEntity entity;


                entity = new StringEntity(serverConnectorDto.getDataJsonString());


                AppLog.showLog(TAG, "json obj entity:: " + entity);

                httpPostRequest.setHeader("Content-Type", "application/json");


                httpPostRequest.setEntity(entity);


            }catch(UnsupportedEncodingException e){

                serverResponse = "";

                e.printStackTrace();

            }catch (Exception e) {

                e.printStackTrace();

                serverResponse = "";

            }


        }else{

            AppLog.showLog(TAG, "Json IS NULL ");

        }


        AppLog.showLog(TAG, "http Request::" + httpPostRequest.toString());

        AppLog.showLog(TAG, "http Request Entity ::" + httpPostRequest.getEntity().toString());



        try {
            httpPostRequest.setHeader("Accept", "application/json");
            httpResponse = httpClient.execute(httpPostRequest);

            AppLog.showLog(TAG,"Http Response:"+ httpResponse.toString());



            int responseCode = httpResponse.getStatusLine().getStatusCode();


            String message = httpResponse.getStatusLine().getReasonPhrase();

            AppLog.showLog(TAG, "server response code::" + responseCode + ":: message:: " + message);


            HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {

                InputStream is = entity.getContent();

                String streamResponse = WebUtil.convertStreamToString(is);

                serverResponse = streamResponse;

                is.close();


            } else {

                AppLog.showLog(TAG, " httpresponse entity null");

                serverResponse= "";

            }

        } catch (ClientProtocolException e) {

            e.printStackTrace();

            serverResponse= "";

        } catch (IOException e) {

            e.printStackTrace();

            serverResponse= "";

        }


        return serverResponse;

    }
*/
}
