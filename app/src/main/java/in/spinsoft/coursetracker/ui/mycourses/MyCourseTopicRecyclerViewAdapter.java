package in.spinsoft.coursetracker.ui.mycourses;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import in.spinsoft.coursetracker.R;
import in.spinsoft.coursetracker.model.CourseTopic;
import in.spinsoft.coursetracker.ui.mycourses.CourseTopicFragment.OnListFragmentInteractionListener;
import in.spinsoft.coursetracker.ui.course.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCourseTopicRecyclerViewAdapter extends RecyclerView.Adapter<MyCourseTopicRecyclerViewAdapter.ViewHolder> {

    private final List<CourseTopic> mValues;
    private final OnListFragmentInteractionListener mListener;

    private Context context;
    public MyCourseTopicRecyclerViewAdapter(List<CourseTopic> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_coursetopic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mTopicIdView.setText(mValues.get(position).getTopicId());
        holder.mTopicNameView.setText(mValues.get(position).getTopicName());
        //holder.mModuleNameView.setText(mValues.get(position).getStatus());
        holder.mStatusView.setChecked(holder.mItem.getStatus().equals("C"));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = true;
                Toast.makeText(v.getContext(), "Clicked", Toast.LENGTH_LONG);
                String text = "a";
                Log.d("Checkbox" , "Clicked");
                if (holder.mStatusView.isChecked()) {
                    // perform logic
                    Toast.makeText(v.getContext(), "Checked" + text, Toast.LENGTH_LONG);
                } else {
                    Toast.makeText(v.getContext(), "Unchecked" + text, Toast.LENGTH_LONG);
                }

            }


        });
        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
         */
    }

    public void onStatusCheckboxClicked(View view){
        //Toast.makeText("", "Checkbox clicked", Toast.LENGTH_LONG);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView mTopicIdView;
        public final TextView mTopicNameView;
        public final TextView mModuleNameView;
        public final CheckBox mStatusView;
        public CourseTopic mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            // mTopicIdView = (TextView) view.findViewById(R.id.topicId);
            mTopicNameView = (TextView) view.findViewById(R.id.topicName);
            mModuleNameView = (TextView) view.findViewById(R.id.moduleName);
            mStatusView = (CheckBox) view.findViewById(R.id.statusTextView);

        }


        @Override
        public String toString() {
            return super.toString() + " '" + mTopicNameView.getText() + "'";
        }
    }

}
