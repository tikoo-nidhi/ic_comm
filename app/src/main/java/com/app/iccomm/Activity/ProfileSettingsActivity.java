package com.app.iccomm.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.iccomm.Data.SharedPref;
import com.app.iccomm.Network.Urls;
import com.app.iccomm.R;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileSettingsActivity extends AppCompatActivity {
    ImageView imgView_profileImg, imgView_edit, left_arrow_appBar, imgView_shoppingCart;
    TextView changePass, title;
    Button bt_saveDetails;
    private static final String IMAGE_DIRECTORY = "/iccomm";
    private int GALLERY = 1, CAMERA = 2;
    EditText ed_fName, ed_lName, ed_emailId, ed_number;
    SharedPreferences preferences;
    Dialog dialog;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        requestMultiplePermissions();

        imgView_profileImg = findViewById(R.id.imgView_profileImg);
        imgView_edit = findViewById(R.id.imgView_edit);
        changePass = findViewById(R.id.changePass);
        left_arrow_appBar = findViewById(R.id.left_arrow_appBar);
        imgView_shoppingCart = findViewById(R.id.imgView_shoppingCart);
        bt_saveDetails = findViewById(R.id.bt_saveDetails);
        title = findViewById(R.id.title);
        ed_fName = findViewById(R.id.ed_fName);
        ed_lName = findViewById(R.id.ed_lName);
        ed_emailId = findViewById(R.id.ed_emailId);
        ed_number = findViewById(R.id.ed_number);

        preferences = getSharedPreferences("data", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgView_shoppingCart.setVisibility(View.GONE);
        title.setVisibility(View.GONE);

        ((ProfileSettingsActivity.this)).getSupportActionBar().setTitle("");

        dialog = new Dialog(ProfileSettingsActivity.this);
        dialog.setContentView(R.layout.progress_bar_layout);

        getUserDetails();

        left_arrow_appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ProfileSettingsActivity.this,AccountDetailsActivity.class));
            }
        });

        imgView_profileImg.setScaleType(ImageView.ScaleType.FIT_XY);

        imgView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileSettingsActivity.this, ChangePasswordActivity.class));
            }
        });

        ed_fName.setText(preferences.getString(SharedPref.USER_FIRST_NAME, null));
        ed_lName.setText(preferences.getString(SharedPref.USER_LAST_NAME, null));

        ed_emailId.setEnabled(false);
        ed_emailId.setText(preferences.getString(SharedPref.USER_EMAIL_ID, null));
        ed_number.setText(preferences.getString(SharedPref.USER_MOBILE, null));

        bt_saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ed_fName.getText()) && TextUtils.isEmpty(ed_number.getText())) {
                    ed_fName.setError("Field can't be empty");
                    ed_number.setError("Field can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(ed_lName.getText())) {
                    ed_lName.setError("Field can't be empty");
                    return;
                }
                if (ed_number.getText().length() < 10) {
                    ed_number.setError("Enter a valid number");
                    return;
                }
                if (bitmap != null) {
                    updateUser(bitmap);
                } else {
                    updateUser(bitmap);
                }
//                updateUser();
                dialog.show();
            }
        });


    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                     bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(ProfileSettingsActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imgView_profileImg.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileSettingsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imgView_profileImg.setImageBitmap(bitmap);
            saveImage(bitmap);
            Toast.makeText(ProfileSettingsActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    public void updateUser(final Bitmap picture) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.POST_UPDATE_PROFILE, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Log.d("updateResponse", String.valueOf(response));
                try {
                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    Log.d("responseImg", json);
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.optString("status").equals("true")) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(SharedPref.USERNAME, ed_fName.getText() + " " + ed_lName.getText());
                        editor.putString(SharedPref.USER_FIRST_NAME, ed_fName.getText().toString());
                        editor.putString(SharedPref.USER_LAST_NAME, ed_lName.getText().toString());
                        editor.putString(SharedPref.USER_MOBILE, ed_number.getText().toString());
                        editor.putString(SharedPref.PROFILE_IMG, jsonObject.optString("profile_image"));
                        editor.commit();
                        Glide.with(ProfileSettingsActivity.this).load(jsonObject.optString("profile_image")).into(imgView_profileImg);
                        Toast.makeText(ProfileSettingsActivity.this, "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(ProfileSettingsActivity.this, AccountDetailsActivity.class));
                    } else {
                        Toast.makeText(ProfileSettingsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }catch (JSONException e) {
                    Toast.makeText(ProfileSettingsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    Log.e("errorUpdate", String.valueOf(e));
                    dialog.dismiss();
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileSettingsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    Log.e("errorUpdate", String.valueOf(e));
                    dialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileSettingsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                Log.e("errorUpdate", String.valueOf(error));
                dialog.dismiss();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("action", "update");
                params.put("id", preferences.getString(SharedPref.USER_ID, null));
                params.put("fname", ed_fName.getText().toString());
                params.put("lname", ed_lName.getText().toString());
                params.put("email", ed_emailId.getText().toString());
                params.put("phone", ed_number.getText().toString());
//                params.put("gender", preferences.getString(SharedPref.USER_GENDER, null));
                return params;
            }
//
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> img = new HashMap<>();
            long imagename = System.currentTimeMillis();
                img.put("profile_image", new VolleyMultipartRequest.DataPart(imagename + ".png", getFileDataFromDrawable(picture)));
                Log.d("profile_image", String.valueOf(img));
                return img;
        }
    };



//        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.POST_UPDATE_PROFILE, new Response.Listener<NetworkResponse>() {
//            @Override
//            public void onResponse(NetworkResponse response) {
//                try {
//                    Log.d("updateResponse", String.valueOf(response));
//                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//                    Log.d("responseImg", json);
//                    JSONObject jsonObject = new JSONObject(json);
//                    if (jsonObject.optString("status").equals("true")) {
//                        SharedPreferences.Editor editor = preferences.edit();
//                        editor.putString(SharedPref.USERNAME, ed_fName.getText() + " " + ed_lName.getText());
//                        editor.putString(SharedPref.USER_FIRST_NAME,ed_fName.getText().toString());
//                        editor.putString(SharedPref.USER_LAST_NAME,ed_lName.getText().toString());
//                        editor.putString(SharedPref.USER_MOBILE,ed_number.getText().toString());
//                        editor.commit();
//                        Toast.makeText(ProfileSettingsActivity.this, "" + jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
//                        finish();
//                        startActivity(new Intent(ProfileSettingsActivity.this, AccountDetailsActivity.class));
//                    } else {
//                        Toast.makeText(ProfileSettingsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                    }
//                    dialog.dismiss();
//                } catch (JSONException e) {
//                    Toast.makeText(ProfileSettingsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                    Log.e("errorUpdate", String.valueOf(e));
//                    dialog.dismiss();
//                    e.printStackTrace();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                    Toast.makeText(ProfileSettingsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                    Log.e("errorUpdate", String.valueOf(e));
//                    dialog.dismiss();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ProfileSettingsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                Log.e("errorUpdate", String.valueOf(error));
//                dialog.dismiss();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
////                params.put("action", "update");
//                params.put("id", preferences.getString(SharedPref.USER_ID, null));
//                params.put("fname", ed_fName.getText().toString());
//                params.put("lname", ed_lName.getText().toString());
//                params.put("email", ed_emailId.getText().toString());
//                params.put("phone", ed_number.getText().toString());
////                params.put("gender", preferences.getString(SharedPref.USER_GENDER, null));
//                return params;
//            }
//
//            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
//                Map<String, VolleyMultipartRequest.DataPart> img = new HashMap<>();
//                long imagename = System.currentTimeMillis();
//                img.put("profile_image", new VolleyMultipartRequest.DataPart(imagename + ".png", getFileDataFromDrawable(picture)));
//                Log.d("profile_image", String.valueOf(img));
//                return img;
//            }
//        };
//
        RequestQueue queue = Volley.newRequestQueue(ProfileSettingsActivity.this);
        queue.add(volleyMultipartRequest);
    }


    public void getUserDetails() {
        dialog.setContentView(R.layout.progress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.POST_GET_USER_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseUserDetails", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equals("True")){
                        Glide.with(ProfileSettingsActivity.this).load(jsonObject.optString("image")).into(imgView_profileImg);
                        dialog.cancel();
                    }else {
                        Toast.makeText(ProfileSettingsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileSettingsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileSettingsActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                Toast.makeText(ProfileSettingsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",getSharedPreferences("data",MODE_PRIVATE).getString(SharedPref.USER_ID,null));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ProfileSettingsActivity.this);
        queue.add(stringRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap image) {
        byte[] b = new byte[0];
        if (image == null) {
            Log.d("imageExist", "false");
            return b;
        } else {
            Log.d("imageExist", "true");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }

    }


}
