package in.gov.cgg.redcrossphase1.ui_citiguest;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.gov.cgg.redcrossphase1.R;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class MembershipRegFormActivity extends AppCompatActivity {

    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final String IMAGE_DIRECTORY_NAME = "RED_CROSS_IMAGE";
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 100;
    public Uri fileUri;
    Button Preview, UploadPhoto, Edit, Next;
    LinearLayout memberRegistration_edit, memberRegistration_view;
    EditText Full_name, fatherHusbName, DOB, mob_num, email, occupation, house_no, locatlity, street, pincode, hours;
    RadioGroup rg;
    EditText ageResult;
    ImageView Photo;
    Spinner Bloodgroup, education, district, mandal, village, activities;
    String Selected_gender = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
    String EMailResult;
    String FilePath;
    TextView Full_name_View, fatherHusbName_View, Gender_View, DOB_View, Age_View;
    TextView mob_num_View, email_View, BloodGroup_View, Education_View, occupation_View;
    TextView house_no_View, locatlity_View, street_View, District_View, Mandal_View;
    TextView Village_View, pincode_View, Activities_View, hours_View;
    File imageFile;

    //CONVERT IMAGE TO BASE 64 CODE
    public static String encodeToBase64(Bitmap image,
                                        Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_reg_form);

        memberRegistration_view = findViewById(R.id.memberRegistration_view);
        memberRegistration_edit = findViewById(R.id.memberRegistration_edit);

        //IDS of Editing Form

        Full_name = findViewById(R.id.FullName_res);
        fatherHusbName = findViewById(R.id.FathersHusbandName_res);
        DOB = findViewById(R.id.DOB_res);
        mob_num = findViewById(R.id.Mob_num_res);
        email = findViewById(R.id.Email_res);
        occupation = findViewById(R.id.Occupation_res);
        house_no = findViewById(R.id.House_no_res);
        locatlity = findViewById(R.id.Locality_res);
        street = findViewById(R.id.Street_area_res);
        pincode = findViewById(R.id.Pincode_res);
        hours = findViewById(R.id.No_of_hours_res);
        rg = findViewById(R.id.radio_group);
        ageResult = findViewById(R.id.Age_res);
        Photo = findViewById(R.id.image_res);
        Bloodgroup = findViewById(R.id.blood_group_spin_res);
        education = findViewById(R.id.Education_spin_res);
        district = findViewById(R.id.District_spin_res);
        mandal = findViewById(R.id.Mandal_spin_res);
        village = findViewById(R.id.Town_village_spin_res);
        activities = findViewById(R.id.Activities_interested_spin_res);
        UploadPhoto = findViewById(R.id.choose_bt);

        Preview = findViewById(R.id.preview_bt);

        //IDS of View Form
        Full_name_View = findViewById(R.id.FullName_res_view);
        fatherHusbName_View = findViewById(R.id.FathersHusbandName_res_view);
        Gender_View = findViewById(R.id.Gender_res_view);
        DOB_View = findViewById(R.id.DOB_res_view);
        Age_View = findViewById(R.id.Age_res_view);
        mob_num_View = findViewById(R.id.Mob_num_res_view);
        email_View = findViewById(R.id.Email_res_view);
        BloodGroup_View = findViewById(R.id.Blood_group_res_view);
        Education_View = findViewById(R.id.Education_res_view);
        occupation_View = findViewById(R.id.Occupation_res_view);
        house_no_View = findViewById(R.id.House_no_res_view);
        locatlity_View = findViewById(R.id.Locality_res_view);
        street_View = findViewById(R.id.Street_area_res_view);
        District_View = findViewById(R.id.District_res_view);
        Mandal_View = findViewById(R.id.mandal_res_view);
        Village_View = findViewById(R.id.Town_village_res_view);
        pincode_View = findViewById(R.id.Pincode_res_view);
        Activities_View = findViewById(R.id.Activities_res_view);
        hours_View = findViewById(R.id.No_of_hours_res_view);
        Edit = findViewById(R.id.Edit);
        Next = findViewById(R.id.Next);


        enablePermissions();

        //Preview button click listener
        Preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMailResult = email.getText().toString().trim();
                Log.d("email", "" + EMailResult);
                if (validateFields()) {

                    Full_name_View.setText(Full_name.getText().toString().trim());
                    fatherHusbName_View.setText(fatherHusbName.getText().toString().trim());
                    Gender_View.setText(Selected_gender);
                    DOB_View.setText(DOB.getText().toString().trim());
                    Age_View.setText(ageResult.getText().toString().trim());
                    mob_num_View.setText(mob_num.getText().toString().trim());
                    email_View.setText(email.getText().toString().trim());
                    BloodGroup_View.setText(Bloodgroup.getSelectedItem().toString().trim());
                    Education_View.setText(education.getSelectedItem().toString().trim());
                    occupation_View.setText(occupation.getText().toString().trim());
                    house_no_View.setText(house_no.getText().toString().trim());
                    locatlity_View.setText(locatlity.getText().toString().trim());
                    street_View.setText(street.getText().toString().trim());
                    District_View.setText(district.getSelectedItem().toString().trim());
                    Mandal_View.setText(mandal.getSelectedItem().toString().trim());
                    Village_View.setText(village.getSelectedItem().toString().trim());
                    pincode_View.setText(pincode.getText().toString().trim());
                    Activities_View.setText(activities.getSelectedItem().toString().trim());
                    hours_View.setText(hours.getText().toString().trim());

                    memberRegistration_edit.setVisibility(View.GONE);
                    memberRegistration_view.setVisibility(View.VISIBLE);
                } else {

                }
            }
        });

        //Edit Button Click listener
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memberRegistration_edit.setVisibility(View.VISIBLE);
                memberRegistration_view.setVisibility(View.GONE);
            }
        });


        //Photo selection code
        UploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        //radio button click listener
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radiobutton1 = findViewById(checkedId);
                Selected_gender = radiobutton1.getText().toString();
            }
        });
        //spinner code


        //Datepicker and age calculation
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);
                String format = new SimpleDateFormat("dd MMM YYYY").format(c.getTime());
                DOB.setText(format);
                ageResult.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
            }
        };
        DOB.setOnClickListener(new View.OnClickListener() {
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

    }

    //Permissions Code
    private boolean enablePermissions() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("Access Read Phone State");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Access Write External Storage");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Access Camera");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                                }
                            }
                        });
                return false;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            return false;
        } else {
            return true;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MembershipRegFormActivity.this)
                .setMessage(message)
                .setPositiveButton("Ok", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                return shouldShowRequestPermissionRationale(permission);
            }
        }
        return true;
    }

    int calculateAge(long date) {
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

    //Validate fields
    protected boolean validateFields() {

        if (Full_name.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter your full name", Toast.LENGTH_LONG).show();
            return false;
        } else if (fatherHusbName.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter your Father or husband name", Toast.LENGTH_LONG).show();
            return false;
        } else if (rg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(MembershipRegFormActivity.this, "Select your gender", Toast.LENGTH_LONG).show();
            return false;
        } else if (DOB.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter your Date of Birth", Toast.LENGTH_LONG).show();
            return false;
        } else if (mob_num.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter Mobile number", Toast.LENGTH_LONG).show();
            return false;
        } else if (mob_num.getText().toString().trim().length() < 10) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter valid Mobile number", Toast.LENGTH_LONG).show();
            return false;
        } else if (!(mob_num.getText().toString().trim().startsWith("9") || mob_num.getText().toString().trim().startsWith("8") || mob_num.getText().toString().trim().startsWith("7") || mob_num.getText().toString().trim().startsWith("6") || mob_num.getText().toString().trim().startsWith("5"))) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter valid Mobile number", Toast.LENGTH_LONG).show();
            return false;
        } else if (!EMailResult.matches(emailPattern)) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter valid Email ID", Toast.LENGTH_LONG).show();
            return false;
        } else if (EMailResult.length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter Email ID", Toast.LENGTH_LONG).show();
            return false;
        } else if (Bloodgroup.getSelectedItem().toString().trim().equals("Select Blood Group")) {
            Toast.makeText(MembershipRegFormActivity.this, "Select Blood group", Toast.LENGTH_LONG).show();
            return false;
        } else if (education.getSelectedItem().toString().trim().equals("Select Educational Qualification")) {
            Toast.makeText(MembershipRegFormActivity.this, "Select Educational Qualification", Toast.LENGTH_LONG).show();
            return false;
        } else if (occupation.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter Occupation", Toast.LENGTH_LONG).show();
            return false;
        } else if (house_no.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter House No.", Toast.LENGTH_LONG).show();
            return false;
        } else if (locatlity.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter locatlity", Toast.LENGTH_LONG).show();
            return false;
        } else if (street.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter Street/Area", Toast.LENGTH_LONG).show();
            return false;
        } else if (district.getSelectedItem().toString().trim().equals("Select District")) {
            Toast.makeText(MembershipRegFormActivity.this, "Select District", Toast.LENGTH_LONG).show();
            return false;
        } else if (mandal.getSelectedItem().toString().trim().equals("Select Mandal")) {
            Toast.makeText(MembershipRegFormActivity.this, "Select Mandal", Toast.LENGTH_LONG).show();
            return false;
        } else if (village.getSelectedItem().toString().trim().equals("Select Town/Village")) {
            Toast.makeText(MembershipRegFormActivity.this, "Select Town/Village", Toast.LENGTH_LONG).show();
            return false;
        } else if (pincode.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter Pincode", Toast.LENGTH_LONG).show();
            return false;
        } else if (pincode.getText().toString().length() < 6) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter valid Pincode", Toast.LENGTH_LONG).show();
            return false;
        } else if (activities.getSelectedItem().toString().trim().equals("Select Activities interested")) {
            Toast.makeText(MembershipRegFormActivity.this, "Select Activities interested", Toast.LENGTH_LONG).show();
            return false;
        } else if (hours.getText().toString().trim().length() == 0) {
            Toast.makeText(MembershipRegFormActivity.this, "Enter number of hours", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MembershipRegFormActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public Uri getOutputMediaFileUri(int type) {

        imageFile = getOutputMediaFile(type);
        Uri imageUri = FileProvider.getUriForFile(
                MembershipRegFormActivity.this,
                "in.gov.cgg.redcrossphase1.ui_citiguest.provider", //(use your app signature + ".provider" )
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {


                FilePath = Environment.getExternalStorageDirectory() + "/Android/data/"
                        + "Files/" + IMAGE_DIRECTORY_NAME;

                String Image_name = 1 + ".jpg";
                FilePath = FilePath + "/" + Image_name;

                File file = new File(FilePath);
                long length = file.length() / 1024;
                Log.d("BEFORE", " = " + length);

                Bitmap bitmap = saveBitmapToFile(imageFile);
                String myBase64Image = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG,
                        100);
                Photo.setImageBitmap(bitmap);


            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor currsor = getContentResolver().query(selectedImage, filePath, null, null, null);
                currsor.moveToFirst();
                int columnIndex = currsor.getColumnIndex(filePath[0]);
                String picturePath = currsor.getString(columnIndex);
                currsor.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image", picturePath + "");
                Photo.setImageBitmap(thumbnail);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
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

}
