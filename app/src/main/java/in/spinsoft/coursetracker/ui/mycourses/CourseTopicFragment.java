package in.spinsoft.coursetracker.ui.mycourses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import in.spinsoft.coursetracker.R;
import in.spinsoft.coursetracker.adapter.CourseTopicAdapter;
import in.spinsoft.coursetracker.client.RestClient;
import in.spinsoft.coursetracker.model.CourseTopic;
import in.spinsoft.coursetracker.model.UserCourse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CourseTopicFragment extends Fragment {

    private static final String TAG = "CourseTopicFragment";

    public static final String ARG_ITEM_ID = "id";
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 3;
    private OnListFragmentInteractionListener mListener;

    String userId;
    private String courseId;

    private UserCourse userCourse;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CourseTopicFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CourseTopicFragment newInstance(int columnCount) {
        CourseTopicFragment fragment = new CourseTopicFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userCourse = (UserCourse)getArguments().getSerializable(ARG_ITEM_ID);
       this.courseId = userCourse.getCourseId();
        mColumnCount = 1;
        if (getArguments() != null) {
            //mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        SharedPreferences sp = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        userId = sp.getString("username", null);

        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) this.getActivity().findViewById(R.id.toolbar_layout1);
        if (appBarLayout != null) {

            appBarLayout.setTitle("Topics");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coursetopic_list, container, false);

        loadCourse(courseId,view, this.getActivity());


        /*
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyCourseTopicRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }
        */
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(CourseTopic item);
    }

    public void loadCourse(final String courseId , final View rootView, final Activity activity){

        // rootView.findViewById(R.id.loading1).setVisibility(View.VISIBLE);
        Call<List<CourseTopic>> call = RestClient.getCourseInstance().listTopics(courseId, userId);

        Callback<List<CourseTopic>> callback = new Callback<List<CourseTopic>>() {
            @Override
            public void onResponse(Call<List<CourseTopic>> call, Response<List<CourseTopic>> response) {
                List<CourseTopic> courses = response.body();
                gotoNextPage(rootView, courses);
            }

            @Override
            public void onFailure(Call<List<CourseTopic>> call, Throwable t) {

                Log.e(TAG, t.toString());
            }
        };

        call.enqueue(callback);


    }



    public void gotoNextPage(View rootView, List<CourseTopic> courses){
        RecyclerView recyclerView  =  rootView.findViewById(R.id.list);

        CourseTopicAdapter adapter = new CourseTopicAdapter( userCourse,courses);
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), mColumnCount));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new MyCourseTopicRecyclerViewAdapter(courses, mListener));
    }

}
