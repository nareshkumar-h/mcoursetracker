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

import in.spinsoft.coursetracker.ui.course.CourseDetailActivity;
import in.spinsoft.coursetracker.ui.course.CourseDetailFragment;
import in.spinsoft.coursetracker.ui.course.CourseListActivity;
import in.spinsoft.coursetracker.R;
import in.spinsoft.coursetracker.model.Course;

public class CourseViewAdapter
        extends RecyclerView.Adapter<CourseViewAdapter.ViewHolder> {

    private final CourseListActivity mParentActivity;
    private List<Course> mValues;
    private final boolean mTwoPane;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Course item = (Course) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(CourseDetailFragment.ARG_ITEM_ID, item.getCode());
                CourseDetailFragment fragment = new CourseDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mycourse_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, CourseDetailActivity.class);
                intent.putExtra(CourseDetailFragment.ARG_ITEM_ID, item.getCode());

                context.startActivity(intent);
            }
        }
    };

    public CourseViewAdapter(CourseListActivity parent, List<Course> items,
                             boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycourse_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Course course = mValues.get(position);
        Log.d("Course List", course.toString());
        holder.mIdView.setText(""+course.getCode());
        holder.mContentView.setText( course.getTitle());

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