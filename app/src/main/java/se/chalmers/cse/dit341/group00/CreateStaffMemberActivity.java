package se.chalmers.cse.dit341.group00;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import se.chalmers.cse.dit341.group00.R;
import se.chalmers.cse.dit341.group00.model.StaffMember;

public class CreateStaffMemberActivity extends AppCompatActivity {

    JSONObject postparams;

    EditText nameInput;
    EditText salaryInput;
    EditText emailInput;

    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_staff_member);

        nameInput = findViewById(R.id.nameInput);
        salaryInput = findViewById(R.id.salaryInput);
        emailInput = findViewById(R.id.emailInput);
        }

    public void createStaffMember(View view){
        postparams = new JSONObject();
        try{
            postparams.put("name", nameInput.getText().toString());
            postparams.put("salaryPerHour", salaryInput.getText().toString());
            postparams.put("email", emailInput.getText().toString());
        }catch(JSONException e){
            Log.e("app", "unexpected JSON exception", e);
        }
        sendPostRequest();
    }


    public void sendPostRequest(){
        String url = getString(R.string.server_url) + "/api/staffMembers";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest((Request.Method.POST), url, postparams,
                new Response.Listener(){
                    @Override
                    public void onResponse(Object response) {
                        showToast("success");
                    }

                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                }

                );

        queue.add(jsonObjReq);
    }

    private void showToast(String text){
        Toast.makeText(CreateStaffMemberActivity.this, text, Toast.LENGTH_SHORT).show();
    }

}
