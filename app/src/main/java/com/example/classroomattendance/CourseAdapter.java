package com.example.classroomattendance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    Context mCtx;
    List<Course> courseList;

    public CourseAdapter(Context mCtx, List<Course> courseList) {
        this.mCtx = mCtx;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.course_layout,parent,false);

        CourseViewHolder courseViewHolder=new CourseViewHolder(view);
        return courseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course=courseList.get(position);

        holder.coursename.setText( course.getCoursename());

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder{
        TextView coursename;

        public TextView getCoursename() {
            return coursename;
        }

        public void setCoursename(TextView coursename) {
            this.coursename = coursename;
        }

        public CourseViewHolder(@NonNull final View itemView) {
            super(itemView);
            coursename=itemView.findViewById(R.id.tv_course_item);

        }

        private void gototakeattendance() {
            Intent intent=new Intent(itemView.getContext(),TakeAttendance.class);
            mCtx.startActivity(intent);
        }
    }
}
