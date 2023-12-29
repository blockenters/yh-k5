package com.block.employer.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.block.employer.MainActivity;
import com.block.employer.R;
import com.block.employer.UpdateActivity;
import com.block.employer.model.Employee;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    Context context;
    ArrayList<Employee> employeeArrayList;

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeArrayList) {
        this.context = context;
        this.employeeArrayList = employeeArrayList;
    }

    @NonNull
    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_row, parent, false);
        return new EmployeeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = employeeArrayList.get(position);
        holder.txtName.setText(employee.name);
        holder.txtAge.setText( "나이 : "+employee.age+"세" );

        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String strSalary = decimalFormat.format(employee.salary);

        holder.txtSalary.setText("연봉 : $"+strSalary);
    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtAge;
        TextView txtSalary;
        CardView cardView;
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtAge = itemView.findViewById(R.id.txtAge);
            txtSalary = itemView.findViewById(R.id.txtSalary);
            cardView = itemView.findViewById(R.id.cardView);
            imgDelete = itemView.findViewById(R.id.imgDelete);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // UpdateActivity 실행

                    int index = getAdapterPosition();

                    Employee employee = employeeArrayList.get(index);

                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("index" , index);
                    intent.putExtra("employee", employee);

                    ((MainActivity)context).launcher.launch(intent);



                }
            });

        }
    }
}





