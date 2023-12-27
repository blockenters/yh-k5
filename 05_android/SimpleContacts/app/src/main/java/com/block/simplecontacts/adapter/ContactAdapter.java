package com.block.simplecontacts.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.block.simplecontacts.R;
import com.block.simplecontacts.model.Contact;

import java.util.ArrayList;

// 2. RecyclerView.Adapter 상속한다.
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    // 3. 멤버변수와 생성자를 만든다.
    Context context;
    ArrayList<Contact> contactArrayList;

    public ContactAdapter(Context context, ArrayList<Contact> contactArrayList) {
        this.context = context;
        this.contactArrayList = contactArrayList;
    }

    // 4. 오버라이딩 함수를 구현한다.
    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.contact_row, parent, false);
        return new ContactAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Contact contact = contactArrayList.get(position);

       holder.txtName.setText(contact.name);
       holder.txtPhone.setText(contact.phone);

    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }

    // 1. 메모리에 있는 데이터를 연결할 ViewHolder를 만든다.
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName;
        TextView txtPhone;
        ImageView imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imgDelete = itemView.findViewById(R.id.imgDelete);

        }
    }

}







