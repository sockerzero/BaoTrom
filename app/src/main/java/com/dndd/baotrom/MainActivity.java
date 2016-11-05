package com.dndd.baotrom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtSoXe, edtBienSoXe;
    Button btnSend;
    ListView lv;
    String longitude, latitude;
    GPSTracker gps;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private MobileServiceClient mClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSoXe = (EditText) findViewById(R.id.edtSoXe);
        edtBienSoXe = (EditText) findViewById(R.id.edtBienSoXe);
        btnSend = (Button) findViewById(R.id.btnSend);
        lv = (ListView) findViewById(R.id.listview);


        final ArrayList<ChiTietXe> mangChiTietXe = new ArrayList<ChiTietXe>();

        final ChiTietXeAdaptor adaptor = new ChiTietXeAdaptor(
                MainActivity.this,
                R.layout.activity_dong_chi_tiet_xe,
                mangChiTietXe
        );

        lv.setAdapter(adaptor);


        try {
            mClient = new MobileServiceClient(
                    "https://pcapp.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    latitude = Double.toString(gps.getLatitude());
                    longitude = Double.toString(gps.getLongitude());

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

                if (TextUtils.isEmpty(edtSoXe.getText().toString())) {
                    edtSoXe.setError("Không được để trống số xe");
                    return;
                }


                ChiTietXe chiTietXe = new ChiTietXe(
                        edtSoXe.getText().toString(),
                        edtBienSoXe.getText().toString(),
                        longitude,
                        latitude
                );

                mangChiTietXe.add(chiTietXe);
                adaptor.notifyDataSetChanged();
                AdditemtoCloud(chiTietXe);
            }
        });
    }


    public void AdditemtoCloud(ChiTietXe chiTietXe) {
        TodoItem item = new TodoItem();
        item.SoXe = chiTietXe.SoXe;
        item.BienSoXe = chiTietXe.BienSoXe;
        item.Kinhdo = chiTietXe.Kinhdo;
        item.Vido = chiTietXe.Vido;
        mClient.getTable(TodoItem.class).insert(item, new TableOperationCallback<TodoItem>() {
            public void onCompleted(TodoItem entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {

                } else {

                }
            }
        });
    }
}
