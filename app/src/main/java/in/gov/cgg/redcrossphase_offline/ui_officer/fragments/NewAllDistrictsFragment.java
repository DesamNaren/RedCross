package in.gov.cgg.redcrossphase_offline.ui_officer.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import in.gov.cgg.redcrossphase_offline.BuildConfig;
import in.gov.cgg.redcrossphase_offline.R;
import in.gov.cgg.redcrossphase_offline.TabLoginActivity;
import in.gov.cgg.redcrossphase_offline.databinding.FragmentAldistrictBinding;
import in.gov.cgg.redcrossphase_offline.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase_offline.ui_officer.adapters.LevelAdapter;
import in.gov.cgg.redcrossphase_offline.ui_officer.custom_officer.CustomDistricClass;
import in.gov.cgg.redcrossphase_offline.ui_officer.modelbeans.StatelevelDistrictViewCountResponse;
import in.gov.cgg.redcrossphase_offline.ui_officer.viewmodels.AllDistrictsViewModel;
import in.gov.cgg.redcrossphase_offline.utils.CustomProgressDialog;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import static android.content.Context.MODE_PRIVATE;

public class NewAllDistrictsFragment extends Fragment {


    private final ArrayList byNameList = new ArrayList();
    private final Set<String> byNameListSet = new LinkedHashSet<>();
    LevelAdapter adapter1;
    int selectedThemeColor = -1;
    private AllDistrictsViewModel allDistrictsViewModel;
    private FragmentAldistrictBinding binding;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    private String D_TYPE;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_aldistrict, container, false);

        GlobalDeclaration.home = false;
        Objects.requireNonNull(getActivity()).setTitle("Dashboard");


        Log.d("Test", "..................");
        try {
            selectedThemeColor = getActivity().getSharedPreferences("THEMECOLOR_PREF",
                    MODE_PRIVATE).getInt("theme_color", -1);


        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
//        if (!GlobalDeclaration.role.contains("D")) {
        binding.tvNodata.setVisibility(View.VISIBLE);
        // }
        allDistrictsViewModel =
                ViewModelProviders.of(this, new CustomDistricClass(getActivity(),
                        "alldistrict")).get(AllDistrictsViewModel.class);

        if (!GlobalDeclaration.role.contains("D")) {
            allDistrictsViewModel.getAllDistrcts("DistrictWise", "3").
                    observe(getActivity(), new Observer<List<StatelevelDistrictViewCountResponse>>() {
                        @Override
                        public void onChanged(@Nullable List<StatelevelDistrictViewCountResponse> allDistrictList) {
                            if (allDistrictList != null) {
                                setDataforRVDistrict(allDistrictList);
                            }
                        }
                    });
        } else {
            allDistrictsViewModel.getAllMandals("MandalWise", "3", Integer.parseInt(GlobalDeclaration.districtId)).

                    observe(getActivity(), new Observer<List<StatelevelDistrictViewCountResponse>>() {
                        @Override
                        public void onChanged
                                (@Nullable List<StatelevelDistrictViewCountResponse> allDistrictList) {
                            if (allDistrictList != null) {
                                setDataforRV(allDistrictList);
                                //  setCountsForDashboard(allDistrictList);
                            }
                        }
                    });
        }


        return binding.getRoot();

    }


    private void setDataforRVDistrict(List<StatelevelDistrictViewCountResponse> allDistrictList) {

        if (allDistrictList.size() > 0) {

            for (int i = 0; i < allDistrictList.size(); i++) {
                allDistrictList.get(i).setTotalCounts(allDistrictList.get(i).getJRC() +
                        allDistrictList.get(i).getYRC() +
                        allDistrictList.get(i).getMembership());
            }

            List<StatelevelDistrictViewCountResponse> newlist = new ArrayList<>();
            newlist.addAll(allDistrictList);

            Collections.sort(newlist, new Comparator<StatelevelDistrictViewCountResponse>() {
                @Override
                public int compare(StatelevelDistrictViewCountResponse lhs, StatelevelDistrictViewCountResponse rhs) {
                    return lhs.getTotalCounts().compareTo(rhs.getTotalCounts());
                }
            });
            binding.tvNodata.setVisibility(View.GONE);

            Collections.reverse(newlist);
            binding.rvAlldistrictwise.setHasFixedSize(true);
            binding.rvAlldistrictwise.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter1 = new LevelAdapter(getActivity(), newlist, "d", selectedThemeColor);
            binding.rvAlldistrictwise.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();

        } else {
            Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
            binding.tvNodata.setText("No data available");

        }
    }

    private void setDataforRV(List<StatelevelDistrictViewCountResponse> allDistrictList) {

        if (allDistrictList.size() > 0) {

            for (int i = 0; i < allDistrictList.size(); i++) {
                allDistrictList.get(i).setTotalCounts((allDistrictList.get(i).getJRC() +
                        allDistrictList.get(i).getYRC() +
                        allDistrictList.get(i).getMembership()));
            }

            List<StatelevelDistrictViewCountResponse> newlist = new ArrayList<>();
            newlist.addAll(allDistrictList);

            Collections.sort(newlist, new Comparator<StatelevelDistrictViewCountResponse>() {
                @Override
                public int compare(StatelevelDistrictViewCountResponse lhs, StatelevelDistrictViewCountResponse rhs) {
                    return lhs.getTotalCounts().compareTo(rhs.getTotalCounts());
                }
            });

            Collections.reverse(newlist);
            binding.tvNodata.setVisibility(View.GONE);

            binding.rvAlldistrictwise.setHasFixedSize(true);
            binding.rvAlldistrictwise.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter1 = new LevelAdapter(getActivity(), allDistrictList, "m", selectedThemeColor);
            binding.rvAlldistrictwise.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
        } else {
            binding.tvNodata.setText("No data available");

        }


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear(); // Remove all existing items from the menu, leaving it empty as if it had just been created.
        inflater.inflate(R.menu.activity_searchmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        //menu.findItem(R.id.logout_search).setIcon(R.drawable.ic_home_white_48dp);
        menu.findItem(R.id.logout_search).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final PrettyDialog dialog = new PrettyDialog(getActivity());
                dialog
                        .setTitle("Red cross")
                        .setMessage("Do you want to Logout?")
                        .setIcon(R.drawable.pdlg_icon_info, R.color.pdlg_color_blue, null)
                        .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_green, new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                dialog.dismiss();
                                startActivity(new Intent(getActivity(), TabLoginActivity.class));
                                getActivity().finish();
                            }
                        })
                        .addButton("Cancel", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                dialog.dismiss();
                                // Toast.makeText(OfficerMainActivity.this, "Cancel selected", Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog.show();
                return true;
            }
        });

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    if (adapter1 != null) {
                        adapter1.filter(newText);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
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
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }


    private void shareImage() {

        // binding.llShare.setVisibility(View.GONE);
        CustomProgressDialog dialog = new CustomProgressDialog(getActivity());
        // dialog.setMessage("Loading...");
        dialog.show();

//        Bitmap bitmap1 = getBitmapFromView(binding.rvAlldistrictwise, binding.rvAlldistrictwise.getChildAt(0).getHeight(),
//                binding.rvAlldistrictwise.getChildAt(0).getWidth());

        Bitmap bitmap1 = getScreenshotFromRecyclerView(binding.rvAlldistrictwise);

        try {
            File cachePath = new File(getActivity().getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png");
            // overwrites this image every time
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            dialog.dismiss();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagePath = new File(getActivity().getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider",
                newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getActivity().getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png");
            getActivity().startActivity(Intent.createChooser(shareIntent, "Share via"));
        }
    }

    //create bitmap from the view
    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    public Bitmap getScreenshotFromRecyclerView(RecyclerView view) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
//                holder.itemView.setDrawingCacheEnabled(false);
//                holder.itemView.destroyDrawingCache();
                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            bigCanvas.drawColor(Color.WHITE);

            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }

        }
        return bigBitmap;
    }


}
