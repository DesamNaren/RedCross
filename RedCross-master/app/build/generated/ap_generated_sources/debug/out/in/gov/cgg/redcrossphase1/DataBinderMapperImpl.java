package in.gov.cgg.redcrossphase1;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import in.gov.cgg.redcrossphase1.databinding.ActivityLoginBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.ActivityShowlistBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.ActivityTabDateBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.ActivityTabloginBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.CustomCountLayoutBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.DrilldownAdapterBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.FragmentAldistrictBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.FragmentCitizenBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.FragmentDaywiseBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.FragmentOfficerBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.FragmentPhotouploadBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.Main2BindingImpl;
import in.gov.cgg.redcrossphase1.databinding.MainBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.Mainnew2BindingImpl;
import in.gov.cgg.redcrossphase1.databinding.RecyclerViewLayoutBindingImpl;
import in.gov.cgg.redcrossphase1.databinding.Regis01BindingImpl;
import in.gov.cgg.redcrossphase1.databinding.RegisBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYLOGIN = 1;

  private static final int LAYOUT_ACTIVITYSHOWLIST = 2;

  private static final int LAYOUT_ACTIVITYTABDATE = 3;

  private static final int LAYOUT_ACTIVITYTABLOGIN = 4;

  private static final int LAYOUT_CUSTOMCOUNTLAYOUT = 5;

  private static final int LAYOUT_DRILLDOWNADAPTER = 6;

  private static final int LAYOUT_FRAGMENTALDISTRICT = 7;

  private static final int LAYOUT_FRAGMENTCITIZEN = 8;

  private static final int LAYOUT_FRAGMENTDAYWISE = 9;

  private static final int LAYOUT_FRAGMENTOFFICER = 10;

  private static final int LAYOUT_FRAGMENTPHOTOUPLOAD = 11;

  private static final int LAYOUT_MAIN = 12;

  private static final int LAYOUT_MAIN2 = 13;

  private static final int LAYOUT_MAINNEW2 = 14;

  private static final int LAYOUT_RECYCLERVIEWLAYOUT = 15;

  private static final int LAYOUT_REGIS = 16;

  private static final int LAYOUT_REGIS01 = 17;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(17);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.activity_showlist, LAYOUT_ACTIVITYSHOWLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.activity_tab_date, LAYOUT_ACTIVITYTABDATE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.activity_tablogin, LAYOUT_ACTIVITYTABLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.custom_count_layout, LAYOUT_CUSTOMCOUNTLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.drilldown_adapter, LAYOUT_DRILLDOWNADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.fragment_aldistrict, LAYOUT_FRAGMENTALDISTRICT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.fragment_citizen, LAYOUT_FRAGMENTCITIZEN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.fragment_daywise, LAYOUT_FRAGMENTDAYWISE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.fragment_officer, LAYOUT_FRAGMENTOFFICER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.fragment_photoupload, LAYOUT_FRAGMENTPHOTOUPLOAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.main, LAYOUT_MAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.main2, LAYOUT_MAIN2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.mainnew2, LAYOUT_MAINNEW2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.recycler_view_layout, LAYOUT_RECYCLERVIEWLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.regis, LAYOUT_REGIS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(in.gov.cgg.redcrossphase1.R.layout.regis_01, LAYOUT_REGIS01);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSHOWLIST: {
          if ("layout/activity_showlist_0".equals(tag)) {
            return new ActivityShowlistBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_showlist is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYTABDATE: {
          if ("layout/activity_tab_date_0".equals(tag)) {
            return new ActivityTabDateBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_tab_date is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYTABLOGIN: {
          if ("layout/activity_tablogin_0".equals(tag)) {
            return new ActivityTabloginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_tablogin is invalid. Received: " + tag);
        }
        case  LAYOUT_CUSTOMCOUNTLAYOUT: {
          if ("layout/custom_count_layout_0".equals(tag)) {
            return new CustomCountLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for custom_count_layout is invalid. Received: " + tag);
        }
        case  LAYOUT_DRILLDOWNADAPTER: {
          if ("layout/drilldown_adapter_0".equals(tag)) {
            return new DrilldownAdapterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for drilldown_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTALDISTRICT: {
          if ("layout/fragment_aldistrict_0".equals(tag)) {
            return new FragmentAldistrictBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_aldistrict is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCITIZEN: {
          if ("layout/fragment_citizen_0".equals(tag)) {
            return new FragmentCitizenBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_citizen is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTDAYWISE: {
          if ("layout/fragment_daywise_0".equals(tag)) {
            return new FragmentDaywiseBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_daywise is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTOFFICER: {
          if ("layout/fragment_officer_0".equals(tag)) {
            return new FragmentOfficerBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_officer is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTPHOTOUPLOAD: {
          if ("layout/fragment_photoupload_0".equals(tag)) {
            return new FragmentPhotouploadBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_photoupload is invalid. Received: " + tag);
        }
        case  LAYOUT_MAIN: {
          if ("layout/main_0".equals(tag)) {
            return new MainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for main is invalid. Received: " + tag);
        }
        case  LAYOUT_MAIN2: {
          if ("layout/main2_0".equals(tag)) {
            return new Main2BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for main2 is invalid. Received: " + tag);
        }
        case  LAYOUT_MAINNEW2: {
          if ("layout/mainnew2_0".equals(tag)) {
            return new Mainnew2BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for mainnew2 is invalid. Received: " + tag);
        }
        case  LAYOUT_RECYCLERVIEWLAYOUT: {
          if ("layout/recycler_view_layout_0".equals(tag)) {
            return new RecyclerViewLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for recycler_view_layout is invalid. Received: " + tag);
        }
        case  LAYOUT_REGIS: {
          if ("layout/regis_0".equals(tag)) {
            return new RegisBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for regis is invalid. Received: " + tag);
        }
        case  LAYOUT_REGIS01: {
          if ("layout/regis_01_0".equals(tag)) {
            return new Regis01BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for regis_01 is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(2);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(17);

    static {
      sKeys.put("layout/activity_login_0", in.gov.cgg.redcrossphase1.R.layout.activity_login);
      sKeys.put("layout/activity_showlist_0", in.gov.cgg.redcrossphase1.R.layout.activity_showlist);
      sKeys.put("layout/activity_tab_date_0", in.gov.cgg.redcrossphase1.R.layout.activity_tab_date);
      sKeys.put("layout/activity_tablogin_0", in.gov.cgg.redcrossphase1.R.layout.activity_tablogin);
      sKeys.put("layout/custom_count_layout_0", in.gov.cgg.redcrossphase1.R.layout.custom_count_layout);
      sKeys.put("layout/drilldown_adapter_0", in.gov.cgg.redcrossphase1.R.layout.drilldown_adapter);
      sKeys.put("layout/fragment_aldistrict_0", in.gov.cgg.redcrossphase1.R.layout.fragment_aldistrict);
      sKeys.put("layout/fragment_citizen_0", in.gov.cgg.redcrossphase1.R.layout.fragment_citizen);
      sKeys.put("layout/fragment_daywise_0", in.gov.cgg.redcrossphase1.R.layout.fragment_daywise);
      sKeys.put("layout/fragment_officer_0", in.gov.cgg.redcrossphase1.R.layout.fragment_officer);
      sKeys.put("layout/fragment_photoupload_0", in.gov.cgg.redcrossphase1.R.layout.fragment_photoupload);
      sKeys.put("layout/main_0", in.gov.cgg.redcrossphase1.R.layout.main);
      sKeys.put("layout/main2_0", in.gov.cgg.redcrossphase1.R.layout.main2);
      sKeys.put("layout/mainnew2_0", in.gov.cgg.redcrossphase1.R.layout.mainnew2);
      sKeys.put("layout/recycler_view_layout_0", in.gov.cgg.redcrossphase1.R.layout.recycler_view_layout);
      sKeys.put("layout/regis_0", in.gov.cgg.redcrossphase1.R.layout.regis);
      sKeys.put("layout/regis_01_0", in.gov.cgg.redcrossphase1.R.layout.regis_01);
    }
  }
}
