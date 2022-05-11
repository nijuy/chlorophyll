package com.example.myapplication.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class FragmentAddPlant extends Fragment {
    final private static String TAG = "GILBOMI";
    private View view;
    private Button button;
    private ImageView imageView = null;

    final static int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    Context ct;
    final static int REQUEST_TAKE_PHOTO = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_plant, container, false);
        imageView = (ImageView)view.findViewById(R.id.addPhoto_image);
        //imageView.setImageResource(R.drawable.ic_baseline_camera_alt_24);
        button = (Button)view.findViewById(R.id.addPhoto_btn_upload);

        ct = container.getContext();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(ct, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(ct, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(getActivity(), new String[]
                        {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.add_plant_button:
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, TAKE_PICTURE);
                            break;
                    }
                }
            });

        }

        // https://everyshare.tistory.com/41
        // https://ebbnflow.tistory.com/177


        //button.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //    }
        //});
        return view;
    }
}
