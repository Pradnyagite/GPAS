package com.NRB.gpas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class VisitorAdaptor extends RecyclerView.Adapter<VisitorAdaptor.VisitorsViewHolder> {
//    private Context mContext;
    private List<VisitorInfo> visitorInfoList;

    public VisitorAdaptor( List<VisitorInfo> visitorInfoList) {
//        this.mContext = mContext;
        this.visitorInfoList = visitorInfoList;
    }
    @Override
    public VisitorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View view = inflater.inflate(R.layout.visitors_list, null);
//        return new VisitorsViewHolder(view);

        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.visitors_list,parent,false);
        VisitorsViewHolder viewHolder=new VisitorsViewHolder(view) ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VisitorsViewHolder holder, int position) {
        VisitorInfo visitor = visitorInfoList.get(position);
        holder.textViewTitle.setText(visitor.getName());
        holder.textViewShortDesc.setText(visitor.getPurpose());
        holder.textViewRating.setText(String.valueOf(visitor.getConcernPerson()));
        holder.textViewPrice.setText(String.valueOf(visitor.getVDate()));
    }
    @Override
    public int getItemCount() {
        return visitorInfoList.size();
    }

    class VisitorsViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
//        ImageView imageView;
//
        public VisitorsViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
//            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
