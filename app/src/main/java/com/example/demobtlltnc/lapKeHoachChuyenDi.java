package com.example.demobtlltnc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.location.GnssAntennaInfo;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.demobtlltnc.adapter.RecyclerViewAdapter;
import com.example.demobtlltnc.adapter.TaiXeAdapter;
import com.example.demobtlltnc.model.KeHoach;
import com.example.demobtlltnc.model.QuanLyKeHoach;
import com.example.demobtlltnc.model.TaiXe;
import com.example.demobtlltnc.model.Xe;
import com.example.demobtlltnc.model.firebase;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.android.SphericalUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class lapKeHoachChuyenDi extends AppCompatActivity implements View.OnClickListener, TaiXeAdapter.itemListener, RecyclerViewAdapter.itemListener {

    private EditText eThoiGianXuatPhat, eTaiXe, eXe;
    private MaterialSearchBar searchBarFrom, searchBarTo;
    private Button them;
    private ImageButton thoat;
    private List<TaiXe> mList;
    private List<Xe> ListXe;
    private Dialog dialog;
    private TaiXe taiXe;
    private Xe xe;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;
    private LatLng from, to;
    private double distance;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_ke_hoach_chuyen_di);
        eThoiGianXuatPhat = findViewById(R.id.edt_date);
        eTaiXe = findViewById(R.id.edt_chon_tai_xe);
        eXe = findViewById(R.id.edt_chon_xe);
        them = findViewById(R.id.btn_them);
        thoat = findViewById(R.id.btn_thoat);
        searchBarFrom = findViewById(R.id.search_diem_di);
        searchBarTo = findViewById(R.id.search_diem_den);

        searchbar(searchBarFrom);
        searchbar(searchBarTo);

        taiXe = new TaiXe();
        xe = new Xe();

        them.setOnClickListener(this);
        thoat.setOnClickListener(this);
        eThoiGianXuatPhat.setOnClickListener(this);
        eTaiXe.setOnClickListener(this);
        eXe.setOnClickListener(this);

    }

    private void searchbar(MaterialSearchBar searchBar) {

        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(lapKeHoachChuyenDi.this);
        Places.initialize(lapKeHoachChuyenDi.this, getString(R.string.api));
        placesClient = Places.createClient(this);
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString(), true, null, true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
                    //opening or closing a navigation drawer
                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    searchBar.closeSearch();
                }
            }
        });

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        .setTypeFilter(TypeFilter.REGIONS) // search theo duong, dia diem
                        .setCountry("vn") // tim kiem gioi han viet nam
                        .setSessionToken(token)
                        .setQuery(s.toString())
                        .build();

                placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                        if (task.isSuccessful()) {
                            FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                            if (predictionsResponse != null) {
                                predictionList = predictionsResponse.getAutocompletePredictions();
                                List<String> suggestionsList = new ArrayList<>();
                                for (int i = 0; i < predictionList.size(); i++) {
                                    AutocompletePrediction prediction = predictionList.get(i);
                                    suggestionsList.add(prediction.getFullText(null).toString());
                                }
                                searchBar.updateLastSuggestions(suggestionsList);
                                if (!searchBar.isSuggestionsVisible()) {
                                    searchBar.showSuggestionsList();

                                }
                            }
                        } else {
                            Log.i("mytag", "loi " + task.getException());
                        }
                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
                if (position >= predictionList.size()) {
                    return;
                }
                AutocompletePrediction selectedPrediction = predictionList.get(position);
                String suggestion = searchBar.getLastSuggestions().get(position).toString();
                searchBar.setText(suggestion);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchBar.clearSuggestions();
                    }
                }, 1000);

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(searchBar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);

                final String placeId = selectedPrediction.getPlaceId();

                List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build(); // được sử dụng để lấy chi tiết về một địa điểm cụ thể từ Google Maps Platform sử dụng Places API cho Android.

                //sử dụng Places API cho Android để lấy thông tin chi tiết về một địa điểm cụ thể
                placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                    @Override
                    public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                        Place place = fetchPlaceResponse.getPlace();
                        Log.i("mytag", "Place found: " + place.getName());
                        LatLng latLngOfPlace = place.getLatLng();

                        if (latLngOfPlace != null) {
                            if (searchBar.getId() == R.id.search_diem_di) {
                                from = latLngOfPlace;
                            } else {
                                to = latLngOfPlace;
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            apiException.printStackTrace();
                            int statusCode = apiException.getStatusCode();
                            Log.i("mytag", "place not found: " + e.getMessage());
                            Log.i("mytag", "status code: " + statusCode);
                        }
                    }
                });
            }

            @Override
            public void OnItemDeleteListener(int position, View v) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edt_date) {
            tinhkhoang();

            final Calendar c = Calendar.getInstance();

            // Lấy giờ hiện tại
            int hour = c.get(Calendar.HOUR_OF_DAY);
            // Lấy phút hiện tại
            int minute = c.get(Calendar.MINUTE);

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Log.i("time" , dateFormat.format(date));
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(lapKeHoachChuyenDi.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int y, int m, int d) {
                    String date = "";

                    if (m > 8) {
                        date = d + "/" + (m + 1) + "/" + y; //m may tinh tu 0->11 => can +1
                    } else {
                        date = d + "/" + "0" + (m + 1) + "/" + y;
                    }
                    eThoiGianXuatPhat.setText(" " + date);
                }
            }, year, month, day);

            dialog.show();
        } else if (v.getId() == R.id.edt_chon_tai_xe) {
            openDialogChonTaiXe();
        } else if (v.getId() == R.id.edt_chon_xe) {
            openDialogChonXe();
        } else if (v.getId() == R.id.btn_them) {
            openMethodThem();
        } else if (v.getId() == R.id.btn_thoat) {
            finish();
        }
    }

    private void tinhkhoang() {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyAkjueH60kK3_bCb4WQj2JecAp5Ua4WNUY")
                .build();

        DirectionsApi.newRequest(context)
                .mode(TravelMode.DRIVING)
                .origin(searchBarFrom.getText())
                .destination(searchBarTo.getText())
                .setCallback(new PendingResult.Callback<DirectionsResult>() {
                    @Override
                    public void onResult(DirectionsResult result) {
                        // Lấy thông tin về khoảng cách và thời gian từ kết quả
                        long distanceInMeters = result.routes[0].legs[0].distance.inMeters;
                        long durationInSeconds = result.routes[0].legs[0].duration.inSeconds;

                        // Chuyển đổi đơn vị
                        distance = distanceInMeters / 1000.0;
                        time = TimeUnit.SECONDS.toHours(durationInSeconds);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.e("tag", "Failed to get distance and time: " + e.getMessage());
                    }
                });
    }

    private void openMethodThem() {

        if (eThoiGianXuatPhat.getText().toString().length() == 0
                || eXe.getText().toString().length() == 0
                || eTaiXe.getText().toString().length() == 0
                || searchBarFrom.getText().length() == 0
                || searchBarTo.getText().length() == 0) {
            Toast.makeText(this, "phải nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
            return;
        }
        taiXe.setTinhTrang(getResources().getStringArray(R.array.tinhtrangTaiXe)[0]);// tinh trạng tài xế = trong chuyến
        firebase db1 = new firebase("tai xe");
        db1.update(taiXe.getId(), taiXe.toMap());

        xe.setTinhTrangXe(getResources().getStringArray(R.array.tinhtrang)[0]);//tinh trang xe = đang hoạt động
        firebase db2 = new firebase("xe");
        db2.update(xe.getBienSo(), xe.toMap());

        firebase db = new firebase("ke hoach");
        String id = db.getMyRef().push().getKey();
        tinhkhoang();

        KeHoach keHoach = new KeHoach(
                id,
                searchBarFrom.getText().toString().trim(),
                searchBarTo.getText().toString().trim(),
                eThoiGianXuatPhat.getText().toString().trim(),
                xe,
                taiXe
        );
        keHoach.setKhoangCach(distance);
        keHoach.setThoiGianToi(String.valueOf(time));

        if (xe.getLoaiXe().equals("xe tai")) {
            // 10k cho 1km
            double temp = distance * 10.0;
            keHoach.setChiPhi(temp + "");
        } else if (xe.getLoaiXe().equals("xe khach")) {
            double temp = distance * 13.0;
            keHoach.setChiPhi(temp + "");
        } else if (xe.getLoaiXe().equals("xe container")) {
            double temp = distance * 18.0;
            keHoach.setChiPhi(temp + "");
        }
        db.getMyRef().child(id).setValue(keHoach);
        
        // them vao lichSuTaiXe luon
        firebase x = new firebase("lich su tai xe");
        x.getMyRef().child(taiXe.getId()).push().setValue(keHoach);

        finish();
    }

    private String getCurDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String dayCur = "";
        if (month > 8) {
            dayCur = day + "/" + (month + 1) + "/" + year; //m may tinh tu 0->11 => can +1
        } else {
            dayCur = day + "/" + "0" + (month + 1) + "/" + year;
        }
        return dayCur;
    }

    private void openDialogChonTaiXe() {
        dialog = new Dialog(lapKeHoachChuyenDi.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.chon_tai_xe);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
        mList = new ArrayList<>();
        TaiXeAdapter adapter = new TaiXeAdapter(mList);

        firebase db = new firebase("tai xe");
        mList = db.getListTaiXeByTinhTrang();

        adapter.setList(mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(lapKeHoachChuyenDi.this, RecyclerView.VERTICAL, false));
        adapter.setItemListener(this);

        recyclerView.setAdapter(adapter);

        Button button = dialog.findViewById(R.id.btn_thoat);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    void openDialogChonXe() {
        dialog = new Dialog(lapKeHoachChuyenDi.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.chon_tai_xe);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
        ListXe = new ArrayList<>();

        //get data
        firebase db = new firebase("xe");
        ListXe = db.getListXeByTinhTrang();

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(ListXe);
        adapter.setList(ListXe);
        recyclerView.setLayoutManager(new LinearLayoutManager(lapKeHoachChuyenDi.this, RecyclerView.VERTICAL, false));
        adapter.setItemListener(this);
        recyclerView.setAdapter(adapter);

        Button button = dialog.findViewById(R.id.btn_thoat);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onItemClick(View v, int postion) {
        taiXe = mList.get(postion);
        eTaiXe.setText(taiXe.getTen());
        Toast.makeText(this, "chon tai xe thanh cong !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickXe(View v, int postion) {
        xe = ListXe.get(postion);
        eXe.setText(xe.getBienSo());
        Toast.makeText(this, "chon xe thanh cong !", Toast.LENGTH_SHORT).show();
    }

}