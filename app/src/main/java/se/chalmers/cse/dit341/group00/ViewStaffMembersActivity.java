package se.chalmers.cse.dit341.group00;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import se.chalmers.cse.dit341.group00.model.StaffMember;

public class ViewStaffMembersActivity extends AppCompatActivity {
ArrayList<String> arrayList = new ArrayList<>();
ArrayList<StaffMember> staffMemberArrayList = new ArrayList<>();
StaffMember selectedStaffMember;

    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_staff_members);

           onClickGetStaffMembers();
           arrayAdapter =  new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);


    }
    public void onClickGetStaffMembers () {
        // Get the text view in which we will show the result.
        final ListView mCamelView = findViewById(R.id.listOfStaffMembers);

        String url = getString(R.string.server_url) + "/api/staffMembers";
        // This uses Volley (Threading and a request queue is automatically handled in the background)
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                        Gson gson = new Gson();

                        String dataArray = null;

                        try {

                            dataArray = response.getString("staffMembers");
                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                       mCamelView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                                selectedStaffMember = staffMemberArrayList.get(i);
                               editStaffMember();

                           }
                       });


                        StaffMember[] staffMembers = gson.fromJson(dataArray, StaffMember[].class);

                        for (StaffMember current : staffMembers) {
                            staffMemberArrayList.add(current);
                            StringBuilder staffMembersString = new StringBuilder();
                            staffMembersString.append("Name: " + current.name + "\n" + "Salary: "
                                    + current.salaryPerHour + "\n" + "Email: " + current.email + "\n" + "ID: " +
                                    current._id +"\n \n \n " );
                            arrayList.add(staffMembersString.toString());
                            mCamelView.setAdapter(arrayAdapter);
                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsonObjectRequest);
}
//creates an intent and puts the selectedStaffMembers ID and its other values so
// it can be accessed in the edit staff members screen
public void editStaffMember(){
    Intent intent = new Intent(this, EditStaffMemberActivity.class);
    intent.putExtra("SelectedStaffMemberID", selectedStaffMember._id);
    intent.putExtra("SelectedStaffMemberName", selectedStaffMember.name);
    intent.putExtra("SelectedStaffMemberSalary", selectedStaffMember.salaryPerHour);
    intent.putExtra("SelectedStaffMemberEmail", selectedStaffMember.email);
    startActivity(intent);
}
public void deleteAllStaffMembers(View view) {
    // Get the text view in which we will show the result.


    String url = getString(R.string.server_url) + "/api/staffMembers";
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


    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
}










}



