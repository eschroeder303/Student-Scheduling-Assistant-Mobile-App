package android.C868.Capstone.All.GUI;

import android.content.Context;
import android.content.Intent;
import android.C868.Capstone.All.Model.Course;
import android.C868.Capstone.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class courseAdapter extends RecyclerView.Adapter<courseAdapter.CourseViewHolder> {

    private List<Course> mCourse;
    private final Context context;
    private final LayoutInflater mInflater;

    class CourseViewHolder extends RecyclerView.ViewHolder{

        private final TextView rowItemCourse;

        public CourseViewHolder(View itemView) {
            super(itemView);
            rowItemCourse = itemView.findViewById(R.id.rowItem_course);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourse.get(position);
                    Intent intent = new Intent(context, courseDetail.class);
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("name", current.getCourseName());
                    intent.putExtra("start", current.getCourseStart());
                    intent.putExtra("end", current.getCourseEnd());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructor", current.getCourseInstructorName());
                    intent.putExtra("phone", current.getInstructorPhone());
                    intent.putExtra("email", current.getInstructorEmail());
                    intent.putExtra("note", current.getCourseNote());
                    intent.putExtra("term", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }

    public courseAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
        this.context = context;

    }

    @NonNull
    @Override
    public courseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.course_item_row, parent, false);

        return new CourseViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull courseAdapter.CourseViewHolder holder, int position) {

        if (mCourse != null) {

            Course current = mCourse.get(position);
            holder.rowItemCourse.setText(current.getCourseName());;

        }

        else {

            holder.rowItemCourse.setText("No Courses Available");

        }

    }

    public void setCourse(List<Course> courses) {

        mCourse = courses;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        return mCourse.size();

    }
}
