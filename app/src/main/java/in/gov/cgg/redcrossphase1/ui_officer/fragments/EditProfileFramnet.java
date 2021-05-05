package in.gov.cgg.redcrossphase1.ui_officer.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import in.gov.cgg.redcrossphase1.BuildConfig;
import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.ActivityEditprofile1Binding;
import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.ExceptionHandler;
import in.gov.cgg.redcrossphase1.ui_citiguest.Fragments.LocBaseFragment;
import in.gov.cgg.redcrossphase1.ui_officer.activities.NewOfficerMainActivity;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.UploadResponse;
import in.gov.cgg.redcrossphase1.utils.CheckInternet;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class EditProfileFramnet extends LocBaseFragment {


    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final String IMAGE_DIRECTORY_NAME = "RED_CROSS_IMAGE";
    private static final int PICK_IMAGE_REQUEST = 101;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 200;
    ActivityEditprofile1Binding binding;
    String timeStamp;
    BottomSheetDialog dialog;
    String id = "", mobile = "", email = "", dob = "", bloodgroup = "";
    Bitmap bm;
    CustomProgressDialog progressDialog;
    private Uri fileUri;
    private String FilePath;
    private String imgFilePath;
    private int selectedThemeColor = -1;
    private ContentValues values;
    private Uri imageUri;
    private File imageFile;
    private Fragment selectedFragment;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.activity_editprofile1, container, false);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));

        progressDialog = new CustomProgressDialog(getActivity());

        //enablePermissions();

        if (GlobalDeclaration.clickeddetails != null) {
            binding.inputUsername.setText(GlobalDeclaration.clickeddetails.getMemberId());
            binding.inputMbl.setText(GlobalDeclaration.clickeddetails.getPhone());
            binding.inputEmail.setText(GlobalDeclaration.clickeddetails.getEmail());
            binding.datePickerDateofBirth.setText(GlobalDeclaration.clickeddetails.getDob());
            try {
                if (GlobalDeclaration.clickeddetails.getPhoto() != null) {
                    if (!GlobalDeclaration.clickeddetails.getPhoto().isEmpty()) {
                        Glide.with(getActivity())
//                                .load(GlobalDeclaration.clickeddetails.getPhoto())
                                .load(Uri.parse(CheckInternet.getRightAngleImage(GlobalDeclaration.clickeddetails.getPhoto())))
                                .placeholder(R.drawable.loader_black1)
                                .into(binding.profilePic);
                    } else {
                        binding.profilePic.setImageDrawable(getResources().getDrawable(R.drawable.edituser2));

                    }
                } else {
                    binding.profilePic.setImageDrawable(getResources().getDrawable(R.drawable.edituser2));

                }
            } catch (Exception e) {
                binding.profilePic.setImageDrawable(getResources().getDrawable(R.drawable.edituser2));
            }

            id = GlobalDeclaration.clickeddetails.getMemberId();

        } else {
            Toast.makeText(getActivity(), "Member id is empty", Toast.LENGTH_SHORT).show();
        }

        //Datepicker and age calculation
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);
                String format = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());


                Calendar userAge = new GregorianCalendar(year, month, day);
                Calendar minAdultAge = new GregorianCalendar();
                minAdultAge.add(Calendar.YEAR, -18);
                if (minAdultAge.before(userAge)) {
                    Toast.makeText(getActivity(), "minimum age should be 18 years", Toast.LENGTH_SHORT).show();
                    binding.datePickerDateofBirth.setText("");
                } else {
                    binding.datePickerDateofBirth.setText(format);
                    //  SHOW_ERROR_MESSAGE;
                }

            }
        };


        binding.datePickerDateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(), datePickerListener, mYear, mMonth, mDay);
                dateDialog.getDatePicker().setMaxDate(new Date().getTime());
                dateDialog.show();
            }
        });

        binding.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isOnline(getActivity())) {
                    updateChanges();
                } else {
                    Toast.makeText(getActivity(), "Please check internet internet", Toast.LENGTH_SHORT).show();

                }
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = (FragmentActivity) v.getContext();
                Fragment frag = new EnrolledMemberEditFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                        frag).addToBackStack(null).commit();
            }
        });


        loadThemes();


        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.activity_backpress, menu);
        MenuItem logout = menu.findItem(R.id.home);
        logout.setIcon(R.drawable.ic_home_white_48dp);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(getActivity(), NewOfficerMainActivity.class));
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void callupload(Bitmap bmp, String id) {

        if (bmp == null) {
            Toast.makeText(getActivity(), "Image can't be empty", Toast.LENGTH_SHORT).show();
        } else {

            //FilePath = Environment.getExternalStorageDirectory() + "/Android/data/" + "Files/" + IMAGE_DIRECTORY_NAME + "/" + Image_name;


            File file = new File(FilePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part PhotoBody = MultipartBody.Part.createFormData("photoUpload", file.getName(), requestFile);
            //TypedFile file = new TypedFile("multipart/form-data", new File(path));


            if (CheckInternet.isOnline(getActivity())) {

                calluploadPhotoMembAdd(PhotoBody, id);

            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void calluploadPhotoMembAdd(MultipartBody.Part photoBody, String id) {

        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        Call<UploadResponse> call = apiInterface.updateDetails(photoBody, id, mobile, dob, bloodgroup, email);


        Log.i("LOGIN_URL", call.request().url().toString());
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                if (response.body() != null) {

                    progressDialog.dismiss();


                    if (response.body().getStatus() != null) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setMessage(response.body().getStatus());
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                getResources().getString(R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
//                                        getActivity().finish();
                                        FragmentActivity activity = (FragmentActivity) getContext();
                                        Fragment frag = new GetDrilldownFragment();
                                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_officer,
                                                frag).addToBackStack(null).commit();
                                    }
                                });


                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getStatus(),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_occur),
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Log.e("TESTfail", "onResponse: " + t.getMessage());
                //updateLocationInterface.SubmitData(null);
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }


    private void loadThemes() {
        try {
            selectedThemeColor = getActivity().getSharedPreferences(getResources().getString(R.string.THEMECOLOR_PREF),
                    MODE_PRIVATE).getInt(getResources().getString(R.string.theme_color1), -1);

            if (selectedThemeColor != -1) {
                binding.background.setBackgroundColor(getResources().getColor(selectedThemeColor));
                binding.btnSignup.setTextColor(getResources().getColor(selectedThemeColor));
                binding.btnCancel.setTextColor(getResources().getColor(selectedThemeColor));
            } else {
                binding.background.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnSignup.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCancel.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            binding.background.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            binding.btnSignup.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.btnCancel.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void updateChanges() {

        email = binding.inputEmail.getText().toString();
        mobile = binding.inputMbl.getText().toString();
        dob = binding.datePickerDateofBirth.getText().toString();
        callupload(bm, id);


    }

    private void openFileChooser() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(getActivity(), getResources().getString(R.string.camera_permission), Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }

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
                binding.profilePic.setImageDrawable(null);

                if (binding.profilePic.getDrawable() == null) {
                    binding.profilePic.setImageBitmap(bm);
                } else {
                    binding.profilePic.setImageDrawable(getResources().getDrawable(R.drawable.edituser2));
                }


                FilePath = Environment.getExternalStorageDirectory() + "/Android/data/" + "Files/" +
                        IMAGE_DIRECTORY_NAME + "/" + Image_name;

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(),
                        getResources().getString(R.string.user_cancelled_imgcapture), Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getActivity(),
                        getResources().getString(R.string.failed_to_capture), Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == PICK_IMAGE_REQUEST)

            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri mProfilePicUri = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor currsor = getActivity().getContentResolver().query(mProfilePicUri, filePath,
                        null, null, null);
                currsor.moveToFirst();
                int columnIndex = currsor.getColumnIndex(filePath[0]);
                String picturePath = currsor.getString(columnIndex);
                currsor.close();
                bm = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image", picturePath + "");
                binding.profilePic.setImageDrawable(null);

                if (binding.profilePic.getDrawable() == null) {
                    binding.profilePic.setImageBitmap(bm);
                } else {
                    binding.profilePic.setImageDrawable(getResources().getDrawable(R.drawable.edituser2));
                }
                FilePath = picturePath;


            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity().getApplicationContext(),
                        getResources().getString(R.string.user_cancelled_choose_file), Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getActivity(),
                        "Sorry! Failed to choose file", Toast.LENGTH_SHORT)
                        .show();
            }


    }

    private void chooseImage() {
        View view = getLayoutInflater().inflate(R.layout.dialog_choose_image, null);
        dialog = new BottomSheetDialog(getActivity(), R.style.AppBottomSheetDialogTheme);
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

    private void takeCameraImage() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(getActivity(), getResources().getString(R.string.camera_permission), Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }


    }


    private Uri getOutputMediaFileUri(int type) {

        imageFile = getOutputMediaFile(type);
        Uri imageUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", //(use your app signature + ".provider" )
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


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.ok), okListener)
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .create()
                .show();
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

}