package com.block.postingapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.block.postingapp.R;
import com.block.postingapp.api.LikeApi;
import com.block.postingapp.api.NetworkClient;
import com.block.postingapp.config.Config;
import com.block.postingapp.model.Posting;
import com.block.postingapp.model.Res;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostingAdapter extends RecyclerView.Adapter<PostingAdapter.ViewHolder> {

    Context context;
    ArrayList<Posting> postingArrayList;

    SimpleDateFormat sf;
    SimpleDateFormat df;

    public PostingAdapter(Context context, ArrayList<Posting> postingArrayList) {
        this.context = context;
        this.postingArrayList = postingArrayList;

        sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df = new SimpleDateFormat("yyyy년MM월dd일 HH:mm");
        sf.setTimeZone(TimeZone.getTimeZone("UTC"));
        df.setTimeZone(TimeZone.getDefault());
    }

    @NonNull
    @Override
    public PostingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posting_row, parent, false);
        return new PostingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Posting posting = postingArrayList.get(position);

        Glide.with(context).load(posting.imgUrl).into(holder.imgPhoto);

        holder.txtContent.setText( posting.content );
        holder.txtEmail.setText( posting.email );

        // 서버의 시간(글로벌 표준시)을, 로컬의 시간으로 변환해야 한다.

        try {
            Date date = sf.parse( posting.createdAt );
            String localTime = df.format(date);
            holder.txtCreatedAt.setText(localTime);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // 좋아요 처리
        if( posting.isLike == 1){
            holder.imgLike.setImageResource(R.drawable.baseline_thumb_up_blue_24);
        }else {
            holder.imgLike.setImageResource(R.drawable.outline_thumb_up_24);
        }

    }

    @Override
    public int getItemCount() {
        return postingArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPhoto;
        TextView txtContent;
        TextView txtEmail;
        TextView txtCreatedAt;
        ImageView imgLike;

        Posting posting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            imgLike = itemView.findViewById(R.id.imgLike);

            imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // 1. 어떤 포스팅을 좋아요 눌렀는지 확인.
                    int index = getAdapterPosition();

                    posting = postingArrayList.get(index);

                    // 2. 해당 포스팅의 좋아요가 어떤 상태인지 확인후
                    //    좋아요가 되어있으면, 좋아요 해제
                    //    그렇지 않으면, 좋아요 실행.

                    Retrofit retrofit = NetworkClient.getRetrofitClient(context);

                    LikeApi api = retrofit.create(LikeApi.class);

                    SharedPreferences sp = context.getSharedPreferences(Config.PREFERENCE_NAME, Context.MODE_PRIVATE);
                    String token = sp.getString("token", "");
                    token = "Bearer " + token;

                    if(posting.isLike == 0){
                        // 좋아요 API
                        Call<Res> call = api.setLike(posting.postId, token);
                        call.enqueue(new Callback<Res>() {
                            @Override
                            public void onResponse(Call<Res> call, Response<Res> response) {
                                if(response.isSuccessful()){

                                    posting.isLike = 1;
                                    notifyDataSetChanged();

                                }else{

                                }
                            }

                            @Override
                            public void onFailure(Call<Res> call, Throwable t) {

                            }
                        });
                    }else{
                        // 좋아요 해지 API
                        Call<Res> call = api.deleteLike(posting.postId, token);
                        call.enqueue(new Callback<Res>() {
                            @Override
                            public void onResponse(Call<Res> call, Response<Res> response) {
                                if(response.isSuccessful()){

                                    posting.isLike = 0;
                                    notifyDataSetChanged();

                                }else{

                                }
                            }

                            @Override
                            public void onFailure(Call<Res> call, Throwable t) {

                            }
                        });
                    }

                }
            });

        }
    }
}
