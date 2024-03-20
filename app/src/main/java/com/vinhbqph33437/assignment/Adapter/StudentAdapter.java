package com.vinhbqph33437.assignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinhbqph33437.assignment.Model.Student;
import com.vinhbqph33437.assignment.R;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends BaseAdapter {


    Context context;
    List<Student> mList = new ArrayList<>();


    public StudentAdapter(Context context, List<Student> mList) {

        this.context = context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }


    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.student_list_item, parent, false);

        TextView tvID = (TextView) rowView.findViewById(R.id.tvId);
//        ImageView imgAvatar = (ImageView) rowView.findViewById(R.id.imgAvatatr);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);

        TextView tvAge = (TextView) rowView.findViewById(R.id.tvAge);

        TextView tvMssv = (TextView) rowView.findViewById(R.id.tvMssv);

//        String imageUrl = mList.get(position).getThumbnailUrl();
//        Picasso.get().load(imageUrl).into(imgAvatar);
////        imgAvatar.setImageResource(imageId[position]);
        tvID.setText(String.valueOf(mList.get(position).get_id()));
        tvName.setText(String.valueOf(mList.get(position).getName()));

        tvAge.setText(String.valueOf(mList.get(position).getTuoi()));

        tvMssv.setText(String.valueOf(mList.get(position).getMssv()));

        return rowView;
    }
}
