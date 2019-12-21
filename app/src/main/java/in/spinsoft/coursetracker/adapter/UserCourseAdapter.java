package in.spinsoft.coursetracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.spinsoft.coursetracker.R;
import in.spinsoft.coursetracker.model.UserCourse;
import in.spinsoft.coursetracker.ui.mycourses.CourseTopicFragment;
import in.spinsoft.coursetracker.ui.mycourses.MyCourseDetailActivity;
import in.spinsoft.coursetracker.ui.mycourses.MyCourseListActivity;

public class UserCourseAdapter         extends RecyclerView.Adapter<UserCourseAdapter.ViewHolder> {

    private final MyCourseListActivity mParentActivity;
    private List<UserCourse> mValues;
    private final boolean mTwoPane;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UserCourse item = (UserCourse) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
               // arguments.putExtra(CourseTopicFragment.ARG_ITEM_ID, item);
                //CourseDetailFragment fragment = new CourseDetailFragment();
                CourseTopicFragment fragment = new CourseTopicFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mycourse_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, MyCourseDetailActivity.class);
                intent.putExtra(CourseTopicFragment.ARG_ITEM_ID, item);

                context.startActivity(intent);
            }
        }
    };

    public UserCourseAdapter(MyCourseListActivity parent, List<UserCourse> items,
                              boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }


    @Override
    public UserCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_list_content, parent, false);
        return new UserCourseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserCourseAdapter.ViewHolder holder, int position) {
        UserCourse course = mValues.get(position);
        Log.d("Course List", course.toString());
        holder.mIdView.setText(""+course.getCourseId());
        String text = course.getCourseName() + " ( "  +course.getPercentage() + " % - " + course.getCompletedTopics() + "/" + (course.getPendingTopics() + course.getCompletedTopics()) + ")";
        holder.mContentView.setText(text);

        holder.itemView.setTag(course);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id_text);
            mContentView = (TextView) view.findViewById(R.id.title);
        }
    }
}
