package in.spinsoft.coursetracker.ui.course;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import in.spinsoft.coursetracker.R;
import in.spinsoft.coursetracker.adapter.CourseTopicAdapter;
import in.spinsoft.coursetracker.adapter.CourseViewAdapter;
import in.spinsoft.coursetracker.client.CourseClient;
import in.spinsoft.coursetracker.client.RestClient;
import in.spinsoft.coursetracker.model.Course;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

/**
 * An activity representing a list of Courses. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CourseDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class CourseListActivity extends AppCompatActivity {

    private static final String TAG = "CourseListActivity";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

         */

        if (findViewById(R.id.course_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.course_list);
        assert recyclerView != null;
        //setupRecyclerView((RecyclerView) recyclerView);
        //listCourses((RecyclerView) recyclerView);
        listCourses(recyclerView);
    }


    public void listCourses(final RecyclerView recyclerView) {

        findViewById(R.id.loading).setVisibility(View.VISIBLE);
        CourseClient courseClient = RestClient.getCourseInstance();


        Call<List<Course>> call = courseClient.list();


        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call1, Response<List<Course>> response) {
                List<Course> courses = response.body();

                findViewById(R.id.loading).setVisibility(View.GONE);

                nextPage( recyclerView, courses);
            }

            @Override
            public void onFailure(Call<List<Course>> call1, Throwable t) {

                Log.e(TAG, t.toString());
            }
        });
    }

    public void nextPage( RecyclerView recyclerView, List<Course> courses){

        //CourseViewAdapter adapter = new CourseViewAdapter(this, courses, mTwoPane);
        CourseViewAdapter adapter = new CourseViewAdapter(this, courses, mTwoPane);
        recyclerView.setAdapter(adapter);
    }
}
