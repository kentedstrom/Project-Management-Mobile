package se.chalmers.cse.dit341.group00;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import java.util.Arrays;
import java.util.List;

import se.chalmers.cse.dit341.group00.Handlers.RequestHandler;
import se.chalmers.cse.dit341.group00.model.Project;
import se.chalmers.cse.dit341.group00.model.StaffMember;
import se.chalmers.cse.dit341.group00.model.Task;

public class EmailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private TextInputEditText mTextInputLayout;

    private String[] staffMemberEmail;
    private String subjectLine;
    private String emailContent;

    RequestHandler myRequestHandler = RequestHandler.getInstance();

    Gson gson = new Gson();

    List<Project> projectList;
    List<StaffMember> staffMemberList;
    ArrayList<Task> tasksList = new ArrayList<>();

    Spinner projectSpinner;
    Spinner staffMemberSpinner;
    Spinner taskSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_email);

        mTextInputLayout = findViewById(R.id.textInputEdit);

        //Creating a Project Spinner with content
        projectSpinner = findViewById(R.id.spinner_project);
        projectList = Arrays.asList(gson.fromJson(myRequestHandler.getJsonStringProjects(), Project[].class));         //The jsonString with Projects is already initialized to prevent null pointers
        ArrayAdapter<Project> projectAdapter = new ArrayAdapter<Project>(this,
                android.R.layout.simple_spinner_item, projectList);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectSpinner.setAdapter(projectAdapter);
        projectSpinner.setOnItemSelectedListener(this);


        //Creating a StaffMember Spinner
        staffMemberSpinner = findViewById(R.id.spinner_staffMember);
        staffMemberList = Arrays.asList(gson.fromJson(myRequestHandler.getJsonStringStaffMembers(), StaffMember[].class));
        ArrayAdapter<StaffMember> staffMemberAdapter = new ArrayAdapter<StaffMember>(this,
                android.R.layout.simple_spinner_item, staffMemberList);
        staffMemberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staffMemberSpinner.setAdapter(staffMemberAdapter);

    }

    //https://codinginflow.com/tutorials/android/email-intent
    public void sendEmail(View view) {
        emailContent = mTextInputLayout.getText().toString(); //prevent null pointer later on

        Project selectedProject = (Project) projectSpinner.getSelectedItem();
        StaffMember selectedStaffMember = (StaffMember) staffMemberSpinner.getSelectedItem();
        Task selectedTask = (Task) taskSpinner.getSelectedItem();

        staffMemberEmail = new String[]{selectedStaffMember.email};
        subjectLine = "Regarding: " + selectedProject.name + "  Task: " + selectedTask.name;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, staffMemberEmail);
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectLine);
        intent.putExtra(Intent.EXTRA_TEXT, emailContent);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        //Clearing list if a new item is selected
        if(tasksList != null){
            tasksList.clear();
        }

        Project selectedProject = (Project) projectSpinner.getSelectedItem();

        for(int j = 0; j<selectedProject.tasks.length; j++){

            String url = "https://frozen-waters-65147.herokuapp.com/api/tasks/" + selectedProject.tasks[j];

            // This uses Volley (Threading and a request queue is automatically handled in the background)
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest

                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            String dataArray = null;

                            try {
                                dataArray = response.getString("name");

                            } catch (JSONException e) {
                                Log.e(this.getClass().toString(), e.getMessage());
                            }

                            Task newTask = new Task();
                            newTask.name = dataArray;

                            tasksList.add(newTask);

                            taskSpinner = findViewById(R.id.spinner_task);

                            ArrayAdapter<Task> taskAdapter = new ArrayAdapter<Task>(getApplicationContext(),
                                    android.R.layout.simple_spinner_item, tasksList);
                            taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            taskSpinner.setAdapter(taskAdapter);

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }

                    );

            // The request queue makes sure that HTTP requests are processed in the right order.
            queue.add(jsonObjectRequest);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        //This method has not been implemented since it doesn't seem to be a requirement for the assignment to save data

    }
}
