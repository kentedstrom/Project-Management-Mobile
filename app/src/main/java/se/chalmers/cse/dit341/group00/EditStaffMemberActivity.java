package se.chalmers.cse.dit341.group00;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import se.chalmers.cse.dit341.group00.model.StaffMember;

public class EditStaffMemberActivity extends AppCompatActivity {
    String staffIDString;
    StaffMember selectedStaffMember;
    StaffMember[] staffMemberArrayList;
    String intialEmail;
    int intialSalary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff_member);

        //gets the information in a bundle
        Bundle staffmemberID = getIntent().getExtras();
        //gets the values and assigns them to strings/ints
        staffIDString = staffmemberID.getString("SelectedStaffMemberID");
        getRequest();
        //finds the text views and then sets their texts to the correct values as assigned above.
        final EditText displayName = (EditText) findViewById(R.id.staffNameTextField);




        /*

        final EditText displaySalary = (EditText) findViewById(R.id.staffSalaryTextField);
        displaySalary.setText(String.valueOf(selectedStaffMember.salaryPerHour));
        final EditText displayEmail = (EditText) findViewById(R.id.staffEmailTextField);
        displayEmail.setText(selectedStaffMember.email);

*/


    }
public void deleteStaffMember(View view){
    String url = getString(R.string.server_url) + "/api/staffMembers/" + staffIDString;
    // This uses Volley (Threading and a request queue is automatically handled in the background)
    RequestQueue queue = Volley.newRequestQueue(this);
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {

                public void onResponse(JSONObject response) {
                    // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                    Gson gson = new Gson();

                    String dataArray = null;

                    try {
                        dataArray = response.getString("staffMembers");
                    } catch (JSONException e) {
                        Log.e(this.getClass().toString(), e.getMessage());
                    }




                    StaffMember[] staffMembers = gson.fromJson(dataArray, StaffMember[].class);





                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
    queue.add(jsonObjectRequest);


    Intent intent = new Intent(this, ViewStaffMembersActivity.class);
    startActivity(intent);
}

    public void getRequest() {
        String url = getString(R.string.server_url) + "/api/staffMembers/" + staffIDString;
        // This uses Volley (Threading and a request queue is automatically handled in the background)
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                        Gson gson = new Gson();

                        System.out.println("Response:" + response.toString());
                        selectedStaffMember = gson.fromJson(response.toString(), StaffMember.class);


                        final EditText displayName = (EditText) findViewById(R.id.staffNameTextField);
                        displayName.setText(selectedStaffMember.name.toString());
                        final EditText displaySalary = (EditText) findViewById(R.id.staffSalaryTextField);
                        displaySalary.setText(String.valueOf(selectedStaffMember.salaryPerHour));
                        final EditText displayEmail = (EditText) findViewById(R.id.staffEmailTextField);
                        displayEmail.setText(selectedStaffMember.email);
                        intialEmail = displayEmail.getText().toString();
                        intialSalary = selectedStaffMember.salaryPerHour;

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsonObjectRequest);

    }


    public void sendPostRequest(View view) {
        final EditText displayName = (EditText) findViewById(R.id.staffNameTextField);
        final EditText displaySalary = (EditText) findViewById(R.id.staffSalaryTextField);
        final EditText displayEmail = (EditText) findViewById(R.id.staffEmailTextField);
        String getName = displayName.getText().toString();
        int getSalary = Integer.parseInt(displaySalary.getText().toString());
        String getEmail = displayEmail.getText().toString();
        final String stringJSON =
                "{'_id':" + "'" + staffIDString + "'" + "," +
                        "'name':" + "'" + getName + "'" + "," +
                        "'salaryPerHour':" + "'" + getSalary + "'" + "," +
                        "'email':" + "'" + getEmail + "'" + "}";
        final String stringJSONForPut =
                "{'name':" + "'" + getName + "'" + "}";

        String url = getString(R.string.server_url) + "/api/staffMembers/" + staffIDString;

        Gson gson = new Gson();
        System.out.println("Heres my JSON" + stringJSON);
        JSONObject convertedObject = new JSONObject();
        JSONObject convertedObjectforPatch = new JSONObject();
        try {
            convertedObject = new JSONObject(stringJSON);
            System.out.println("success for wholechange");
            System.out.println("Heres the object" + convertedObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failure");
        }
        try {
            convertedObjectforPatch = new JSONObject(stringJSONForPut);
            System.out.println("success for only changing name");
            System.out.println("Heres the object" + convertedObjectforPatch.toString());
        } catch (Exception e) {

            e.printStackTrace();
        }

        System.out.println(getEmail + "vs intial" + intialEmail);
        if (intialEmail.equals(getEmail)) {
            System.out.println("same email");
        }
        if (intialSalary == getSalary){
            System.out.println("same salary");
        }
        System.out.println(getSalary + "v.s. intial salary" + intialSalary);
        if (intialEmail.equals (getEmail) && intialSalary == getSalary) {

            System.out.println("Should be PATCH");
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PATCH, url, convertedObjectforPatch, new Response.Listener<JSONObject>() {

                        public void onResponse(JSONObject response) {

                            // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                            Gson gson = new Gson();
                            System.out.println("Server response" + response.toString());
                            String dataArray = null;


                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            queue.add(jsonObjectRequest);
            Intent intent = new Intent(this, ViewStaffMembersActivity.class);
            startActivity(intent);
        } else {

            System.out.println("PUT should be");

            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, convertedObject, new Response.Listener<JSONObject>() {

                        public void onResponse(JSONObject response) {
                            // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                            Gson gson = new Gson();
                            System.out.println("Server response" + response.toString());
                            String dataArray = null;


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            queue.add(jsonObjectRequest);


            Intent intent = new Intent(this, ViewStaffMembersActivity.class);
            startActivity(intent);
        }
    }

}

