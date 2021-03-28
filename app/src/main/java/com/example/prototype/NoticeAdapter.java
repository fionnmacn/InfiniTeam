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
    private SelectedNotice selectedNotice;

    public NoticeAdapter(List<NoticeModel> noticeModelList, SelectedNotice selectedNotice) {
        this.noticeModelList = noticeModelList;
        this. selectedNotice = selectedNotice;
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

        String id = noticeModel.getId();
        String subject = noticeModel.getSubject();
        String content = noticeModel.getContent();

        holder.tvId.setText(id);
        holder.tvSubject.setText(subject);
        holder.tvContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return noticeModelList.size();
    }

    public interface SelectedNotice{
        void selectedNotice(NoticeModel noticeModel);
    }

    public class NoticeAdapterVh extends RecyclerView.ViewHolder {

        TextView tvId;
        TextView tvSubject;
        TextView tvContent;

        public NoticeAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.notice_id);
            tvSubject = itemView.findViewById(R.id.notice_subject);
            tvContent = itemView.findViewById(R.id.notice_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedNotice.selectedNotice(noticeModelList.get(getAdapterPosition()));
                }
            });
        }
    }
}
