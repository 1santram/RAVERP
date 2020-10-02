package com.rav.raverp.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.rav.raverp.MyApplication;
import com.rav.raverp.R;
import com.rav.raverp.data.interfaces.ArrowBackPressed;
import com.rav.raverp.data.interfaces.ImageCompressTaskListener;
import com.rav.raverp.data.interfaces.StoragePermissionListener;
import com.rav.raverp.data.model.api.Login;
import com.rav.raverp.databinding.ActivityEditProfileBinding;
import com.rav.raverp.databinding.DialogPlotFilterBinding;
import com.rav.raverp.network.ApiClient;
import com.rav.raverp.network.ApiClientlocal;
import com.rav.raverp.network.ApiHelper;
import com.rav.raverp.utils.ScreenUtils;
import com.rav.raverp.utils.ViewUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class EditProfileActivity extends BaseActivity implements ArrowBackPressed{

    private static final String TAG = EditProfileActivity.class.getSimpleName();
    private Context mContext = EditProfileActivity.this;
    private ActivityEditProfileBinding binding;
    private ApiHelper apiHelper;
    private boolean isDialogHided;

    private boolean isGetProfile, isUpdateProfile;
    private Dialog filterDialog;
    private String profilePicPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_edit_profile);
        binding.setEditProfileActivity(this);
        setToolbarTitle("Profile");
        showBackArrow();
        setArrowBackPressed(this);
        ScreenUtils.setupUI(binding.parentLayout, EditProfileActivity.this);

        Login login= MyApplication.getLoginModel();
        binding.setLogin(login);

        binding.editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFilterDialog();
            }
        });



        ImageView imageView=(ImageView) findViewById(R.id.profile_image_view);
        Glide.with(EditProfileActivity.this).load("http://192.168.29.136"+login.getStrProfilePic())
                .apply(RequestOptions.errorOf(R.drawable.account))
                .into(imageView);


    }

    private void showFilterDialog() {
        filterDialog = new Dialog(EditProfileActivity.this);
        final DialogPlotFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(EditProfileActivity.this),
                R.layout.edit_email_profile_layout, null, false);
        filterDialog.setContentView(binding.getRoot());
        filterDialog.setCancelable(true);
        filterDialog.setCanceledOnTouchOutside(true);


        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 70);
        Window window = filterDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(inset);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        filterDialog.show();



    }




    @Override
    public void arrowBackPressed() {

    }
}
