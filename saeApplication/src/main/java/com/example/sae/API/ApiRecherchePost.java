package com.example.sae.API;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ApiRecherchePost{
    /*
    private String URL;
    JSONObject json=null;
    Map<String,String> parametres ;

     */
    private String USER_AGENT = "Mozilla/5.0";

    private String POST_URL;

    private String POST_PARAMS;

    private JSONObject jsonReslt=null;

    /**
     * premare la requet api
     * @param Url l'url de l'api
     * @param parametres les paramettre a rentrer pour l'api
     * @param _jsonReslt c'est le json qui vas etre retourné par la requet qui est envoier en reference
     */
    public ApiRecherchePost(String Url, Map<String,String> parametres, JSONObject _jsonReslt){
        /*
        URL=Url;
        this.parametres=parametres;

         */
        try {
            POST_URL=Url;
            POST_PARAMS=ParameterStringBuilder.getParamsString(parametres);
            jsonReslt=_jsonReslt;
            //_jsonReslt= new JSONObject("{\"token\":\"d99cda5825ddc34bda0ff76b86674726\",\"type\":\"educateur\"}");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * execute la requete api préparer et encrit le resultat dans le JSONObject jsonReslt dans une cle resultat.
     */
    public void start(){
        URL obj = null;
        try {
            obj = new URL(POST_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
            httpURLConnection.setConnectTimeout(10000); // 10 secondes
            httpURLConnection.setReadTimeout(30000); // 30 secondes

            // For POST only - START
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(POST_PARAMS.getBytes());
            os.flush();
            os.close();
            // For POST only - END

            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                } in .close();

                httpURLConnection.disconnect();

                // print result

                Log.e("resultat", response.toString());

                if(!response.toString().equals("null")) {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    jsonReslt.put("resultat",jsonObject);
                }
                else jsonReslt.put("resultat","null");







            } else {
                System.out.println("POST request not worked");
            }

        } catch (Exception e) {
            try {
                jsonReslt.put("resultat","null");
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }
}
