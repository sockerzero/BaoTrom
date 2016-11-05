package com.dndd.baotrom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Asus on 11/1/2016.
 */


public class ChiTietXeAdaptor extends ArrayAdapter<ChiTietXe> {


    public ChiTietXeAdaptor(Context context, int resource, List<ChiTietXe> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.activity_dong_chi_tiet_xe, null);
        }
        ChiTietXe chiTietXe = getItem(position);

        if (chiTietXe != null) {
            // Anh xa + Gan gia tri
            TextView txtSoXe = (TextView) view.findViewById(R.id.txtSoXe);
            TextView txtBienSoXe = (TextView) view.findViewById(R.id.txtBienSoXe);
            TextView txtToaDo = (TextView) view.findViewById(R.id.txtToaDo);

            txtSoXe.setText(chiTietXe.SoXe);
            txtBienSoXe.setText(chiTietXe.BienSoXe);

            String KinhDo = chiTietXe.Kinhdo;
            String ViDo = chiTietXe.Vido;
            String ToaDo = KinhDo.toString() + " , " + ViDo.toString();
            txtToaDo.setText(ToaDo.toString());
        }
        return view;
    }
}
