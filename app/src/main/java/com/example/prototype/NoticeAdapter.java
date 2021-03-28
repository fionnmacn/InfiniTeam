package com.example.prototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeAdapterVh> {

    private List<NoticeModel> noticeModelList;
    private Context context;

    public NoticeAdapter(List<NoticeModel> noticeModelList) {
        this.noticeModelList = noticeModelList;
    }

    @NonNull
    @Override
    public NoticeAdapter.NoticeAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new NoticeAdapterVh(LayoutInflater.from(context).inflate(R.layout.notices_list, null));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.NoticeAdapterVh holder, int position) {

        NoticeModel noticeModel = noticeModelList.get(position);

        String subject = noticeModel.getSubject();

        holder.tvSubjectId.setText(subject);
    }

    @Override
    public int getItemCount() {
        return noticeModelList.size();
    }

    public class NoticeAdapterVh extends RecyclerView.ViewHolder {

        TextView tvSubjectId;

        public NoticeAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvSubjectId = itemView.findViewById(R.id.notice_subject);
        }
    }
}
