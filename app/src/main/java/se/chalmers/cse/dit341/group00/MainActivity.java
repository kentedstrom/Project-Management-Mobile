package se.chalmers.cse.dit341.group00;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import se.chalmers.cse.dit341.group00.Handlers.RequestHandler;


public class MainActivity extends AppCompatActivity {

    // Field for parameter name
    public static final String HTTP_PARAM = "httpResponse";

    //Reference to RequestHandler to do query for EmailActivity
    RequestHandler myRequestHandler = RequestHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Doing query to get projects for EmailActivity
        myRequestHandler.getProjects(this);
        myRequestHandler.getStaffMembers(this);
    }

    public void onClickNewStaffMember(View view){
        Intent intent = new Intent(this,CreateStaffMemberActivity.class);
        startActivity(intent);
    }

    public void onClickNewActivity (View view) {

        // Starts a new activity, providing the text from my HTTP text field as an input
        Intent intent = new Intent(this, ViewStaffMembersActivity.class);
       // Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }


        // Get the text view in which we will show the result.




        // This uses Volley (Threading and a request queue is automatically handled in the background)


        // The request queue makes sure that HTTP requests are processed in the right order.


    public void onClickSendEmail(View view) {
        Intent intent = new Intent(this, EmailActivity.class);
        startActivity(intent);
    }
}
