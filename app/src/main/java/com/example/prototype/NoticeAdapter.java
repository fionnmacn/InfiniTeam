package com.example.prototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeAdapterVh> implements Filterable {

    private List<NoticeModel> noticeModelList;
    private List<NoticeModel> getNoticeModelListFiltered;
    private Context context;
    private SelectedNotice selectedNotice;

    public NoticeAdapter(List<NoticeModel> noticeModelList, SelectedNotice selectedNotice) {
        this.noticeModelList = noticeModelList;
        this.getNoticeModelListFiltered = noticeModelList;
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

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null | constraint.length() == 0) {
                    filterResults.count = getNoticeModelListFiltered.size();
                    filterResults.values = getNoticeModelListFiltered;
                }
                else {
                    String searched = constraint.toString().toLowerCase();
                    List<NoticeModel> resultData = new ArrayList<>();

                    for (NoticeModel noticeModel: getNoticeModelListFiltered) {
                        if (noticeModel.getContent().toLowerCase().contains(searched)) {
                            resultData.add(noticeModel);
                        }
                    }

                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                noticeModelList = (List<NoticeModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
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
