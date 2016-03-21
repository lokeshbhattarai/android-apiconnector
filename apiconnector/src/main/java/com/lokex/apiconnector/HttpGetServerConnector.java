package com.lokex.apiconnector;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lokex on 12/23/14.
 */
final public class HttpGetServerConnector {

    private final String TAG = HttpGetServerConnector.class.getName();
    private String serverResponse;

    private ServerConnectorDTO serverConnectorDto;

    public HttpGetServerConnector(ServerConnectorDTO connector){
        this.serverConnectorDto = connector;

    }

    public String connectWithGetRequest() throws Exception {

        AppLogger.showLog(TAG, "*************CONNECTING SERVER*******");

        AppLogger.showLog(TAG, "url to connect:: " + serverConnectorDto.getUrlToConnect());


        URL obj = new URL(serverConnectorDto.getUrlToConnect());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Accept", "application/json");

        int responseCode = con.getResponseCode();

        AppLogger.showLog(TAG, "Response Code : " + responseCode);

        if (responseCode==200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            AppLogger.showLog(TAG, "server response:: "+response.toString());

            return response.toString();
        }else{
            AppLogger.showLog(TAG, " Ignoring connection since response code is not 200");
            return "";
        }



    }

/*
    public String connectServerWithHttpGetRequest(){

        AppLog.showLog(TAG, "*************CONNECTING SERVER*******");

        AppLog.showLog(TAG, "url to connect:: " + serverConnectorDto.getUrlToConnect());

        AppLog.showLog(TAG, "name value pair:: " + serverConnectorDto.getDataListNameValuePair());

        AppLog.showLog(TAG, "json obj :: " + serverConnectorDto.getDataJsonString());

        String urlToConnect = serverConnectorDto.getUrlToConnect();

        HttpGet httpGetRequest = new HttpGet(urlToConnect.replaceAll(" ","%20"));

        HttpClient httpClient = new DefaultHttpClient();

        HttpResponse httpResponse;



        try {

            httpGetRequest.addHeader("Accept", "application/json");

            httpResponse = httpClient.execute(httpGetRequest);

            int responseCode = httpResponse.getStatusLine().getStatusCode();


            String message = httpResponse.getStatusLine().getReasonPhrase();

            AppLog.showLog(TAG, "server response code::" + responseCode + ":: message:: " + message);


            if (responseCode==200) {

                HttpEntity entity = httpResponse.getEntity();

                if (entity != null) {

                    AppLog.showLog(TAG, "response entity CONTENT LENGTH::" + entity.getContentLength());
                    AppLog.showLog(TAG, "response entity CONTENT TYPE::" + entity.getContentType());
                    AppLog.showLog(TAG, "response entity CONTENT ENCODING::" + entity.getContentEncoding());


                    InputStream is = entity.getContent();

                    String streamResponse = WebUtil.convertStreamToString(is);

                    serverResponse = streamResponse;

                    is.close();


                } else {

                    AppLog.showLog(TAG," httpresponse entity null");

                    serverResponse= "";

                }

            }else{

                serverResponse= "";

            }

        } catch (ClientProtocolException e) {

            e.printStackTrace();

            serverResponse= "";

        } catch (IOException e) {

            e.printStackTrace();

            serverResponse= "";

        }catch (Exception e) {

            e.printStackTrace();

            serverResponse= "";

        }

        return serverResponse;

    }
    */

}
