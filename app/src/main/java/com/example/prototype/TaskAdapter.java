package com.example.prototype;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskAdapterVh> implements Filterable {

    private List<TaskModel> taskModelList;
    private List<TaskModel> getTaskModelListFiltered;
    private Context context;
    private SelectedTask selectedTask;

    ParseUser currentUser = ParseUser.getCurrentUser();
    String user = currentUser.getUsername();

    public TaskAdapter(List<TaskModel> taskModelList, SelectedTask selectedTask) {
        this.taskModelList = taskModelList;
        this.getTaskModelListFiltered = taskModelList;
        this.selectedTask = selectedTask;
    }

    @NonNull
    @Override
    public TaskAdapter.TaskAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TaskAdapterVh(LayoutInflater.from(context)
                .inflate(R.layout.task_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskAdapterVh holder, int position) {

        TaskModel taskModel = taskModelList.get(position);

        String id = taskModel.getId();
        String description = taskModel.getDescription();
        String createdAt = taskModel.getCreatedAt();

        holder.tvId.setText(id);
        holder.tvDescription.setText(description);
        holder.tvCreatedAt.setText(createdAt);
    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null | constraint.length() == 0) {
                    filterResults.count = getTaskModelListFiltered.size();
                    filterResults.values = getTaskModelListFiltered;
                }
                else {
                    String searched = constraint.toString().toLowerCase();
                    List<TaskModel> resultData = new ArrayList<>();

                    for (TaskModel taskModel: getTaskModelListFiltered) {
                        if (taskModel.getDescription().toLowerCase().contains(searched)) {
                            resultData.add(taskModel);
                        }
                    }

                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                taskModelList = (List<TaskModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }


    public interface SelectedTask {
        void selectedTask(TaskModel taskModel);
    }

    public class TaskAdapterVh extends RecyclerView.ViewHolder {

        TextView tvId;
        TextView tvDescription;
        TextView tvCreatedAt;
        ImageView ivPriority;
        Button accept;

        public TaskAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.task_id);
            tvDescription = itemView.findViewById(R.id.task_description);
            tvCreatedAt = itemView.findViewById(R.id.task_createdAt);

            itemView.findViewById(R.id.task_accept).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String taskId = (String) tvId.getText();
                    if (taskId != null) {
                        Log.d("OBJECT", taskId);
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
                        query.getInBackground(taskId, new GetCallback<ParseObject>() {
                            public void done(ParseObject task, ParseException e) {
                                if (e == null) {
                                    Snackbar.make(v, "Task accepted.", Snackbar.LENGTH_LONG).show();
                                    task.put("assignedTo", user);
                                    task.saveInBackground();
                                }
                                else {
                                    Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG).show();
                                }
                                notifyDataSetChanged();
                            }
                        });
                    }
                    else {
                        Log.d("OBJECT", "Object is null");
                    }
                }
            });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    selectedTask.selectedTask(taskModelList.get(getAdapterPosition()));
//                }
//            });
        }
    }
}
