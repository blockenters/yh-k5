package com.block.memoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.block.memoapp.R;
import com.block.memoapp.UpdateActivity;
import com.block.memoapp.model.Memo;

import java.util.ArrayList;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder>{

    Context context;
    ArrayList<Memo> memoArrayList;

    public MemoAdapter(Context context, ArrayList<Memo> memoArrayList) {
        this.context = context;
        this.memoArrayList = memoArrayList;
    }

    @NonNull
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memo_row, parent, false);
        return new MemoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Memo memo = memoArrayList.get(position);
        holder.txtTitle.setText(memo.title);
        // 날짜는 ISO 포맷으로 온다
        // 따라서, 날짜를 가공해서 화면에 표시할 것이다.

        // "2023-08-03T11:30:00"
        // "2023-08-03 11:30"
        String date = memo.date.replace("T", " ").substring(0, 15+1);

        holder.txtDate.setText( date );
        holder.txtContent.setText(memo.content);
    }

    @Override
    public int getItemCount() {
        return memoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDate;
        TextView txtContent;
        ImageView imgDelete;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtContent = itemView.findViewById(R.id.txtContent);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 코드 작성.
                    int index = getAdapterPosition();

                    Memo memo = memoArrayList.get(index);

                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("memo", memo);
                    context.startActivity(intent);
                }
            });
        }
    }
}
