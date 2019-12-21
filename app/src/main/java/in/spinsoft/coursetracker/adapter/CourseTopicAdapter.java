package in.spinsoft.coursetracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.spinsoft.coursetracker.R;
import in.spinsoft.coursetracker.model.CourseTopic;
import in.spinsoft.coursetracker.model.UserCourse;
import in.spinsoft.coursetracker.ui.mycourses.CourseTopicFragment;
import in.spinsoft.coursetracker.ui.mycourses.MyCourseDetailActivity;

public class CourseTopicAdapter
        extends RecyclerView.Adapter<CourseTopicAdapter.ViewHolder> {

    private List<CourseTopic> mValues;

    private UserCourse userCourse;


    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CourseTopic item = (CourseTopic) view.getTag();

                Context context = view.getContext();
                Intent intent = new Intent(context, MyCourseDetailActivity.class);
                intent.putExtra(CourseTopicFragment.ARG_ITEM_ID, item.getTopicId());

                context.startActivity(intent);

        }
    };

    public CourseTopicAdapter(  UserCourse userCourse, List<CourseTopic> items
                              ) {
        mValues = items;
        this.userCourse = userCourse;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CourseTopic course = mValues.get(position);
        Log.d("Course List", course.toString());
        holder.mIdView.setText(""+course.getModuleName());
        //String text = course.getCourseName() + " ( "  +course.getPercentage() + " % - " + course.getCompletedTopics() + "/" + (course.getPendingTopics() + course.getCompletedTopics()) + ")";
        holder.mContentView.setText(course.getTopicName());

        holder.itemView.setTag(course);
        //holder.itemView.setOnClickListener(mOnClickListener);
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