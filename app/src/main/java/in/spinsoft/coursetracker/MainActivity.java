package in.spinsoft.coursetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import in.spinsoft.coursetracker.ui.login.LoginActivity;
import in.spinsoft.coursetracker.ui.course.CourseListActivity;
import in.spinsoft.coursetracker.ui.mycourses.MyCourseListActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAlreadyLogin();
    }



    public void checkAlreadyLogin(){
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        String username = sp.getString("username", null);
        if (username != null){
            ((TextView)findViewById(R.id.textView2)).setText("Welcome " + username);
            ((Button)findViewById(R.id.loginButton)).setVisibility(View.GONE);
           ((Button)findViewById(R.id.logoutButton)).setVisibility(View.VISIBLE);
            ((Button)findViewById(R.id.viewMyCourseButton)).setVisibility(View.VISIBLE);
        }
        else{
           ((Button)findViewById(R.id.loginButton)).setVisibility(View.VISIBLE);
           ((Button)findViewById(R.id.logoutButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.viewMyCourseButton)).setVisibility(View.GONE);
        }

       //homePage();

    }

    public void logoutBtnAction(View view){
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.clear();
        Ed.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }

    public void viewMyCourseBtnAction(View view){
        Intent intent = new Intent(this, MyCourseListActivity.class);
        startActivity(intent);
    }


    public void viewCourseBtnAction(View view){
        Intent intent = new Intent(this, CourseListActivity.class);
        startActivity(intent);
    }
    public void loginBtnAction(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        //sideMenuBtnAction(view);
    }


    public void sideBtnAction(View view){
        Intent intent = new Intent(this, SideMenuActivity.class);
        startActivity(intent);

    }


}
