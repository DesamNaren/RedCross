package in.gov.cgg.redcrossphase1.ui_officer.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import in.gov.cgg.redcrossphase1.R;
import in.gov.cgg.redcrossphase1.databinding.FragmentPhotouploadBinding;

public class PhotoUploadActivity extends Fragment {

    FragmentPhotouploadBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_photoupload, container, false);


        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  shareImage();
            }
        });

        return binding.getRoot();


    }
//
//    private void shareImage() {
//
//        binding.button.setVisibility(View.GONE);
//        ProgressDialog dialog = new ProgressDialog(getActivity());
//        dialog.setMessage("Loading...");
//        dialog.show();
//
//        Bitmap bitmap1 = getBitmapFromView(binding.idCard, binding.idCard.getChildAt(0).getHeight(),
//                binding.idCard.getChildAt(0).getWidth());
//
//        try {
//            File cachePath = new File(getActivity().getCacheDir(), "images");
//            cachePath.mkdirs(); // don't forget to make the directory
//            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png");
//            // overwrites this image every time
//            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            stream.close();
//            dialog.dismiss();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        File imagePath = new File(getActivity().getCacheDir(), "images");
//        File newFile = new File(imagePath, "image.png");
//        Uri contentUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider",
//                newFile);
//
//        if (contentUri != null) {
//            Intent shareIntent = new Intent();
//            shareIntent.setAction(Intent.ACTION_SEND);
//            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
//            shareIntent.setDataAndType(contentUri, getActivity().getContentResolver().getType(contentUri));
//            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
//            shareIntent.setType("image/png");
//            getActivity().startActivity(Intent.createChooser(shareIntent, "Share via"));
//        }
//    }
//
//    //create bitmap from the view
//    private Bitmap getBitmapFromView(View view, int height, int width) {
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        Drawable bgDrawable = view.getBackground();
//        if (bgDrawable != null)
//            bgDrawable.draw(canvas);
//        else
//            canvas.drawColor(Color.WHITE);
//        view.draw(canvas);
//        return bitmap;
//    }

}
