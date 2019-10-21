package se.chalmers.cse.dit341.group00.Handlers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestHandler {

    // static variable single_instance of type Singleton
    private static RequestHandler single_instance = null;


    private String jsonStringProjects;
    private String jsonStringStaffMembers;


    // private constructor restricted to this class itself
    private RequestHandler()
    {

    }

    // static method to create instance of Singleton class
    public static RequestHandler getInstance()
    {
        if (single_instance == null)
            single_instance = new RequestHandler();

        return single_instance;
    }


    public void getProjects(Context context){

            String url = "https://frozen-waters-65147.herokuapp.com/api/projects";

            // This uses Volley (Threading and a request queue is automatically handled in the background)
            RequestQueue queue = Volley.newRequestQueue(context);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest

                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {


                            String dataArray = null;

                            try {
                                dataArray = response.getString("projects");

                            } catch (JSONException e) {
                                Log.e(this.getClass().toString(), e.getMessage());
                            }

                            setJsonStringProjects(dataArray);


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);

        }


    public void getStaffMembers(Context context){

        String url = "https://frozen-waters-65147.herokuapp.com/api/staffmembers";

        // This uses Volley (Threading and a request queue is automatically handled in the background)
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest

                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        String dataArray = null;

                        try {
                            dataArray = response.getString("staffMembers");

                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        setJsonStringStaffMembers(dataArray);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(jsonObjectRequest);

    }



    public String getJsonStringProjects() {
        return jsonStringProjects;
    }

    public void setJsonStringProjects(String jsonStringProjects) {
        this.jsonStringProjects = jsonStringProjects;
    }

    public String getJsonStringStaffMembers() {
        return jsonStringStaffMembers;
    }

    public void setJsonStringStaffMembers(String jsonStringStaffMembers) {
        this.jsonStringStaffMembers = jsonStringStaffMembers;
    }


}
