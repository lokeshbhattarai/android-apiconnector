package com.lokex.apiconnector;

/**
 * Created by lokex on 5/8/15.
 */
public class ParserFactory {

          public enum ParserType{

              LOGIN

           }


    public static ParserFamily create(ParserType parserType){

        if(parserType==null)  return null;

        switch (parserType){

            case LOGIN:
                return new LibraryParser();

            default:
                return null;
        }


    }


}
