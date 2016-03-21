package com.lokex.apiconnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WebUtil {

    public static String convertStreamToString(final InputStream is){

        BufferedReader br = null;
        StringBuilder sb = null;
        String line = null;

        try {
            br = new BufferedReader(new InputStreamReader(is, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }


        try {

            sb = new StringBuilder();
            while((line = br.readLine())!=null){
                sb.append(line+"\n");

            }

        } catch (IOException e) {

            e.printStackTrace();
            return "";

        }finally{

            try {

                is.close();

            } catch (Exception e2) {

                e2.printStackTrace();
            }


        }

        return sb.toString();

    }


    public static String getParams(Map<String,String> dataParams){

        String params = "";

        if(dataParams==null) return "";

        Iterator itr = dataParams.keySet().iterator();

        while (itr.hasNext()){
            String key = (String)itr.next();

            String value = dataParams.get(key);

            params = params + key + "=" + value;

            if(itr.hasNext()){
                params = params + "&";
            }
        }
        return params;// for url encoded data no query string '?'
//        return "?"+params;
    }
}
