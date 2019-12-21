package in.spinsoft.coursetracker.ui.course;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.spinsoft.coursetracker.R;
import in.spinsoft.coursetracker.client.RestClient;
import in.spinsoft.coursetracker.model.Course;
import in.spinsoft.coursetracker.ui.course.CourseDetailActivity;
import in.spinsoft.coursetracker.ui.course.CourseListActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A fragment representing a single Course detail screen.
 * This fragment is either contained in a {@link CourseListActivity}
 * in two-pane mode (on tablets) or a {@link CourseDetailActivity}
 * on handsets.
 */
public class CourseDetailFragment extends Fragment {

    private static final String TAG = "CourseDetailFragment";
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Course mItem;

    private String id;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CourseDetailFragment() {
    }

    SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getArguments().getString(ARG_ITEM_ID);
        this.id = id;
        Log.d("CDF-> onCreate" , ""+ id);
        //loadCourse(id, this.getActivity());
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) this.getActivity().findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle("");
        }

        sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);



    }

    public void loadCourse(final String courseId , final View rootView, final Activity activity){

       // rootView.findViewById(R.id.loading1).setVisibility(View.VISIBLE);
        Call<Course> call = RestClient.getCourseInstance().find(courseId);

        Callback<Course> callback = new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                Course course = response.body();
                String username =sp.getString("username", null);
                StringBuilder content = new StringBuilder();
                content.append("Code: " ).append(course.getCode()).append("\n\n");
                content.append("Name: " ).append(course.getTitle()).append("\n\n").append("Category: ") .append(course.getCategory()).
                append("\n\n").append("Semester: ").append(course.getSemester());
               // rootView.findViewById(R.id.loading1).setVisibility(View.GONE);
                ((TextView) rootView.findViewById(R.id.course_detail)).setText(content.toString());

                    Log.d("course" ,course.toString());


                CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                if (appBarLayout != null) {
                   // appBarLayout.setTitle(course.getTitle());
                }

            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {

                Log.e(TAG, t.toString());
            }
        };

        call.enqueue(callback);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.course_detail, container, false);

        loadCourse(id,rootView, this.getActivity());
        return rootView;
    }
}
