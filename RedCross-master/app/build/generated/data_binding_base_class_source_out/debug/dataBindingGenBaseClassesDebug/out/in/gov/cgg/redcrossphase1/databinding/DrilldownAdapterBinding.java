// Generated by data binding compiler. Do not edit!
package in.gov.cgg.redcrossphase1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import in.gov.cgg.redcrossphase1.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class DrilldownAdapterBinding extends ViewDataBinding {
  @NonNull
  public final TextView tvBloodgroup;

  @NonNull
  public final TextView tvClass;

  @NonNull
  public final TextView tvDistrict;

  @NonNull
  public final TextView tvDo;

  @NonNull
  public final TextView tvEmail;

  @NonNull
  public final TextView tvEnddate;

  @NonNull
  public final TextView tvGender;

  @NonNull
  public final TextView tvHeaderbloodgroup;

  @NonNull
  public final TextView tvHeaderclass;

  @NonNull
  public final TextView tvHeaderdistrcit;

  @NonNull
  public final TextView tvHeaderdob;

  @NonNull
  public final TextView tvHeaderemail;

  @NonNull
  public final TextView tvHeaderenddate;

  @NonNull
  public final TextView tvHeadergender;

  @NonNull
  public final TextView tvHeaderinstutename;

  @NonNull
  public final TextView tvHeaderinstutetype;

  @NonNull
  public final TextView tvHeadermandal;

  @NonNull
  public final TextView tvHeadermeberId;

  @NonNull
  public final TextView tvHeadermobile;

  @NonNull
  public final TextView tvHeadername;

  @NonNull
  public final TextView tvHeadervillage;

  @NonNull
  public final TextView tvInstaname;

  @NonNull
  public final TextView tvInstatype;

  @NonNull
  public final TextView tvMandal;

  @NonNull
  public final TextView tvMemberId;

  @NonNull
  public final TextView tvMobilenumber;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView tvVillage;

  protected DrilldownAdapterBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView tvBloodgroup, TextView tvClass, TextView tvDistrict, TextView tvDo, TextView tvEmail,
      TextView tvEnddate, TextView tvGender, TextView tvHeaderbloodgroup, TextView tvHeaderclass,
      TextView tvHeaderdistrcit, TextView tvHeaderdob, TextView tvHeaderemail,
      TextView tvHeaderenddate, TextView tvHeadergender, TextView tvHeaderinstutename,
      TextView tvHeaderinstutetype, TextView tvHeadermandal, TextView tvHeadermeberId,
      TextView tvHeadermobile, TextView tvHeadername, TextView tvHeadervillage,
      TextView tvInstaname, TextView tvInstatype, TextView tvMandal, TextView tvMemberId,
      TextView tvMobilenumber, TextView tvName, TextView tvVillage) {
    super(_bindingComponent, _root, _localFieldCount);
    this.tvBloodgroup = tvBloodgroup;
    this.tvClass = tvClass;
    this.tvDistrict = tvDistrict;
    this.tvDo = tvDo;
    this.tvEmail = tvEmail;
    this.tvEnddate = tvEnddate;
    this.tvGender = tvGender;
    this.tvHeaderbloodgroup = tvHeaderbloodgroup;
    this.tvHeaderclass = tvHeaderclass;
    this.tvHeaderdistrcit = tvHeaderdistrcit;
    this.tvHeaderdob = tvHeaderdob;
    this.tvHeaderemail = tvHeaderemail;
    this.tvHeaderenddate = tvHeaderenddate;
    this.tvHeadergender = tvHeadergender;
    this.tvHeaderinstutename = tvHeaderinstutename;
    this.tvHeaderinstutetype = tvHeaderinstutetype;
    this.tvHeadermandal = tvHeadermandal;
    this.tvHeadermeberId = tvHeadermeberId;
    this.tvHeadermobile = tvHeadermobile;
    this.tvHeadername = tvHeadername;
    this.tvHeadervillage = tvHeadervillage;
    this.tvInstaname = tvInstaname;
    this.tvInstatype = tvInstatype;
    this.tvMandal = tvMandal;
    this.tvMemberId = tvMemberId;
    this.tvMobilenumber = tvMobilenumber;
    this.tvName = tvName;
    this.tvVillage = tvVillage;
  }

  @NonNull
  public static DrilldownAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.drilldown_adapter, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static DrilldownAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<DrilldownAdapterBinding>inflateInternal(inflater, R.layout.drilldown_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static DrilldownAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.drilldown_adapter, null, false, component)
   */
  @NonNull
  @Deprecated
  public static DrilldownAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<DrilldownAdapterBinding>inflateInternal(inflater, R.layout.drilldown_adapter, null, false, component);
  }

  public static DrilldownAdapterBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static DrilldownAdapterBinding bind(@NonNull View view, @Nullable Object component) {
    return (DrilldownAdapterBinding)bind(component, view, R.layout.drilldown_adapter);
  }
}