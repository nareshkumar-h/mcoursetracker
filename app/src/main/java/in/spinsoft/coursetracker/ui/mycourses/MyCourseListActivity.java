package in.spinsoft.coursetracker.ui.mycourses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import in.spinsoft.coursetracker.R;
import in.spinsoft.coursetracker.adapter.CourseTopicAdapter;
import in.spinsoft.coursetracker.adapter.UserCourseAdapter;
import in.spinsoft.coursetracker.client.CourseClient;
import in.spinsoft.coursetracker.client.RestClient;
import in.spinsoft.coursetracker.model.Course;
import in.spinsoft.coursetracker.model.UserCourse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCourseListActivity extends AppCompatActivity {

    private static final String TAG = "MyCourseListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("My Courses");
        if (findViewById(R.id.course_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            //mTwoPane = true;
        }

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.mycourse_list);
        assert recyclerView != null;
        //setupRecyclerView((RecyclerView) recyclerView);
        //listCourses((RecyclerView) recyclerView);
        listCourses(recyclerView);
    }

    public void listCourses(final RecyclerView recyclerView) {

        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        String username = sp.getString("username", null);
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
        CourseClient courseClient = RestClient.getCourseInstance();


        Call<List<UserCourse>> call = courseClient.list(username);


        call.enqueue(new Callback<List<UserCourse>>() {
            @Override
            public void onResponse(Call<List<UserCourse>> call1, Response<List<UserCourse>> response) {
                List<UserCourse> courses = response.body();

                findViewById(R.id.loading).setVisibility(View.GONE);

                nextPage( recyclerView, courses);
            }

            @Override
            public void onFailure(Call<List<UserCourse>> call1, Throwable t) {

                Log.e(TAG, t.toString());
            }
        });
    }

    public void nextPage( RecyclerView recyclerView, List<UserCourse> courses){

        //CourseViewAdapter adapter = new CourseViewAdapter(this, courses, mTwoPane);
        UserCourseAdapter adapter = new UserCourseAdapter(this, courses, false);
        recyclerView.setAdapter(adapter);
    }
}
