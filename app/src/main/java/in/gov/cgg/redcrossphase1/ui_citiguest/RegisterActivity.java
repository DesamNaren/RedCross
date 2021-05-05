package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import in.gov.cgg.redcrossphase1.BuildConfig;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.RegisBinding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class RegisterActivity extends LocActivity {

    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final String IMAGE_DIRECTORY_NAME = "RED_CROSS_IMAGE";
    private static final int PICK_IMAGE_REQUEST = 101;
    CustomProgressDialog progressDialog;
    RegisBinding binding;
    TextInputEditText et_name;
    TextInputEditText et_email;
    TextInputEditText et_mobile;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
    BottomSheetDialog dialog;
    String newfile;
    private int selectedThemeColor = -1;
    private String FilePath = "";
    private Bitmap bm;
    private File imageFile;
    private Uri fileUri;

    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(img, selectedImage);
        return img;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        binding = DataBindingUtil.setContentView(RegisterActivity.this, R.layout.regis);

        et_name = binding.nameEditText;
        et_email = binding.emailEditText;
        et_mobile = binding.mobileEditText;


        try {
            selectedThemeColor = this.getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF), MODE_PRIVATE).
                    getInt(getResources().getString(R.string.theme_color1), -1);
            if (selectedThemeColor != -1) {
                binding.tvSignUp.setTextColor(getResources().getColor(selectedThemeColor));
                binding.btnRegister.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.btnRegister.setTextColor(getResources().getColor(R.color.white));

                if (selectedThemeColor == R.color.redcroosbg_1) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross1_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_2) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross2_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_3) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross3_bg));
                } else if (selectedThemeColor == R.color.redcroosbg_4) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross4_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_5) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross5_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_6) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_7) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));

                } else if (selectedThemeColor == R.color.redcroosbg_8) {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross8_bg));

                } else {
                    binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                    binding.tvSignUp.setTextColor(getResources().getColor(R.color.colorPrimary));
                    binding.btnRegister.setTextColor(getResources().getColor(R.color.white));
                    binding.btnRegister.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                }
            } else {
                binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross6_bg));
                binding.tvSignUp.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.btnRegister.setTextColor(getResources().getColor(R.color.white));
                binding.btnRegister.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            }
        } catch (Exception e) {
            e.printStackTrace();
            binding.llmain.setBackground(getResources().getDrawable(R.drawable.redcross7_bg));
            binding.tvSignUp.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.btnRegister.setTextColor(getResources().getColor(R.color.white));
            binding.btnRegister.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }

        progressDialog = new CustomProgressDialog(RegisterActivity.this);

        binding.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(RegisterActivity.this)) {
                    if (validate()) {
                        callUpload(bm);

                    }
                } else {
                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void chooseImage() {
        View view = getLayoutInflater().inflate(R.layout.dialog_choose_image, null);
        dialog = new BottomSheetDialog(RegisterActivity.this, R.style.AppBottomSheetDialogTheme);
        dialog.setContentView(view);
        dialog.show();
        TextView take_photo, gallery, title;
        take_photo = view.findViewById(R.id.take_photo);
        gallery = view.findViewById(R.id.gallery);
        title = view.findViewById(R.id.title);

        title.setText(getResources().getString(R.string.choose_from));

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                takeCameraImage();

            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openFileChooser();
            }
        });

    }

    private void openFileChooser() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.camera_permission), Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }

    }


    private boolean validate() {

        if (et_name.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.please_enter_name), Toast.LENGTH_LONG).show();
            return false;
        }
        if (et_mobile.getText().toString().trim().equalsIgnoreCase("") ||
                et_mobile.getText().toString().startsWith("1") || et_mobile.getText().toString().startsWith("2") || et_mobile.getText().toString().startsWith("3") || et_mobile.getText().toString().startsWith("0") ||
                et_mobile.getText().toString().startsWith("4")) {
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.please_enter_validmno), Toast.LENGTH_LONG).show();
            return false;
        } else if (et_email.getText().toString().length() != 0 && !et_email.getText().toString().matches(emailPattern)) {
            et_email.setError(getResources().getString(R.string.enter_valid_email));
            et_email.requestFocus();
            return false;
        } else if (et_mobile.getText().toString().length() < 10) {
            et_mobile.setError(getResources().getString(R.string.enter_mobile_number_digits));
            et_mobile.requestFocus();
            return false;
        }
        if (et_email.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.please_enter_valid_email), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                FilePath = Environment.getExternalStorageDirectory() + "/Android/data/"
                        + "Files/" + IMAGE_DIRECTORY_NAME;

                String Image_name = 1 + ".jpg";
                FilePath = FilePath + "/" + Image_name;

                bm = saveBitmapToFile(imageFile);
                binding.imgUser.setImageDrawable(null);


                if (binding.imgUser.getDrawable() == null) {
//                    binding.imgUser.setImageBitmap(bm);
                    Picasso.get().load(imageFile).into(binding.imgUser);
                } else {
                    binding.imgUser.setImageDrawable(getResources().getDrawable(R.drawable.edituser2));
                }


                FilePath = Environment.getExternalStorageDirectory() + "/Android/data/" + "Files/" +
                        IMAGE_DIRECTORY_NAME + "/" + Image_name;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(RegisterActivity.this,
                        getResources().getString(R.string.user_cancelled_imgcapture), Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(RegisterActivity.this,
                        getResources().getString(R.string.failed_to_capture), Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == PICK_IMAGE_REQUEST)

            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri mProfilePicUri = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor currsor = getContentResolver().query(mProfilePicUri, filePath,
                        null, null, null);
                currsor.moveToFirst();
                int columnIndex = currsor.getColumnIndex(filePath[0]);
                String picturePath = currsor.getString(columnIndex);
                currsor.close();
                bm = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image", picturePath + "");
                binding.imgUser.setImageDrawable(null);

                if (binding.imgUser.getDrawable() == null) {
                    //Glide.with(RegisterActivity.this).load(bm);
                    // binding.imgUser.setImageBitmap(bm);
                    Picasso.get().load(mProfilePicUri).into(binding.imgUser);

                } else {
                    binding.imgUser.setImageDrawable(getResources().getDrawable(R.drawable.edituser2));
                }
                FilePath = picturePath;


            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.user_cancelled_choose_file), Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(RegisterActivity.this,
                        getResources().getString(R.string.failed_tochoose_file), Toast.LENGTH_SHORT)
                        .show();
            }


    }

    private void takeCameraImage() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.camera_permission), Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }


    }

    private void callUpload(Bitmap bmp) {
//        if (bmp == null) {
//            Toast.makeText(RegisterActivity.this, "Image can't be empty", Toast.LENGTH_SHORT).show();
//        } else {


        File file = new File(FilePath);

//            newfile=file.getAbsolutePath();
//
//            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            MultipartBody.Part PhotoBody = MultipartBody.Part.createFormData("photoUpload", file.getName(), requestFile);

        if (CheckInternet.isOnline(RegisterActivity.this)) {

            callLoginRequest(file);

        } else {
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    //  }

    private void callLoginRequest(File imageFile1) {

        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        try {

            GlobalDeclaration.unamefromReg = et_name.getText().toString();


            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile1);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", imageFile1.getName(), requestFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), et_name.getText().toString());
            RequestBody emailid = RequestBody.create(MediaType.parse("text/plain"), et_email.getText().toString());
            RequestBody mobilenumber = RequestBody.create(MediaType.parse("text/plain"), et_mobile.getText().toString());


            Call<JsonObject> call;

            if (FilePath.equalsIgnoreCase("")) {
                call = apiInterface.citizenRegistrationwithout(name, emailid, mobilenumber);

            } else {
                call = apiInterface.citizenRegistration(name, emailid, mobilenumber, body);
            }

            Log.e("picupload", call.request().url() + "");
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {

                        String message = response.body().get("message").toString();
                        String status = response.body().get("status").toString();

                        if (status.equals("200")) {
                            String role = response.body().get("role").toString();
                            GlobalDeclaration.citizenrole = role;
                            GlobalDeclaration.profilePic = FilePath;
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, CitiGuestMainActivity.class));
                            finish();
                        } else if (status.equals("100")) {
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

                        }
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {

            progressDialog.dismiss();
            e.printStackTrace();
        }

    }

    public Bitmap saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return selectedBitmap;
        } catch (Exception e) {
            return null;
        }
    }

    private Uri getOutputMediaFileUri(int type) {

        imageFile = getOutputMediaFile(type);
        Uri imageUri = FileProvider.getUriForFile(RegisterActivity.this,
                BuildConfig.APPLICATION_ID + getResources().getString(R.string.file_provider), //(use your app signature + ".provider" )
                imageFile);
        return imageUri;
    }

    //DIRECTORY CREATION CODE
    private File getOutputMediaFile(int type) {

        //File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Android File Upload");

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" + "Files/" + IMAGE_DIRECTORY_NAME);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TAG", "Oops! Failed create " + "Android File Upload"
                        + " directory");
                return null;
            }
        }

        /*timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date()) + ".jpg";*/

        File mediaFile;

        if (type == MEDIA_TYPE_IMAGE) {

            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "1.jpg");


        } else {
            return null;
        }

        return mediaFile;
    }


}