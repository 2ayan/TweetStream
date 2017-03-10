
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import twitter4j.JSONException;
import twitter4j.JSONObject;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ayan
 */
public class JSONParse {

    public JSONParse() {

    }
    public String parseJSON(String JSONstring2parse, String tag2search) throws JSONException
    {
       JSONObject jobject=new JSONObject(JSONstring2parse); 
       return jobject.getString(tag2search);
        
    }
    
    public static void main(String[] args) throws JSONException, FileNotFoundException, IOException {
       JSONParse jp=new JSONParse();
       FileReader fileReader = new FileReader(new File("output.txt"));
       BufferedReader br = new BufferedReader(fileReader);
       
       String jsonstring=null;
       while ((jsonstring = br.readLine()) != null) {
      String jsonoutput=jp.parseJSON(jsonstring, "retweeted_status");
      System.err.println(jsonoutput);
      if(jsonoutput.startsWith("{"))
      {
          System.err.println(jp.parseJSON(jsonoutput, "created_at")); 
      }
           
           
       }
        
        
        
        
    }          
}
