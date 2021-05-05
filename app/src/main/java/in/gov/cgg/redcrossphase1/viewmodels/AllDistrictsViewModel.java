package in.gov.cgg.redcrossphase1.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import in.gov.cgg.redcrossphase1.retrofit.ApiClient;
import in.gov.cgg.redcrossphase1.retrofit.ApiInterface;
import in.gov.cgg.redcrossphase1.retrofit.GlobalDeclaration;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.ContactBean;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Age;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.AgeResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.BloodGroups;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.BloodResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DayWiseReportCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.FinYear;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.GenderResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Genders;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.GovtVsPvtResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Last10day;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.MainFinance;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.MembershipCounts;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.StatelevelDistrictViewCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.UserTypesList;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Val;
import in.gov.cgg.redcrossphase1.utils.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllDistrictsViewModel extends ViewModel {

    private final CustomProgressDialog pd;
    Context context;
    private MutableLiveData<List<StatelevelDistrictViewCountResponse>> alListMutableLiveData1;
    private MutableLiveData<List<StatelevelDistrictViewCountResponse>> alListMutableLiveData2;
    private MutableLiveData<List<StatelevelDistrictViewCountResponse>> alListMutableLiveData3;
    private MutableLiveData<List<Val>> memberListMutableLiveData;
    private MutableLiveData<List<UserTypesList>> alListInsti;
    private MutableLiveData<List<Age>> ageListMutableLiveData;
    private MutableLiveData<List<BloodGroups>> bloodResponseMutableLiveData;
    private MutableLiveData<List<DayWiseReportCountResponse>> dayListMutableLiveData;
    private MutableLiveData<DashboardCountResponse> dashboardCountResponseMutableLiveData;
    private MutableLiveData<List<Genders>> genderResponseMutableLiveData;
    private MutableLiveData<List<Last10day>> goListMutableLiveData;

    private MutableLiveData<ContactBean> contactListMutableLiveData;

    private MutableLiveData<List<FinYear>> financialYearsData;


    public AllDistrictsViewModel(Context application) {
        this.context = application;
//        pd = new ProgressDialog(context);
//        pd.setMessage("Loading ,Please wait");
        pd = new CustomProgressDialog(context);


    }

    public LiveData<List<StatelevelDistrictViewCountResponse>> getAllDistrcts(String level, String fyid) {

        if (alListMutableLiveData1 == null) {
            alListMutableLiveData1 = new MutableLiveData<>();
//            if (GlobalDeclaration.districtData == null) {
            loadAllDistricts(level, fyid);
//            } else {
//                alListMutableLiveData1.setValue(GlobalDeclaration.districtData);
//            }
        }
        return alListMutableLiveData1;
    }


    public LiveData<List<FinYear>> getfinancialYears() {

        if (financialYearsData == null) {
            financialYearsData = new MutableLiveData<>();
            if (GlobalDeclaration.financeyears == null) {

                loadfinancialYears();
            } else {
                financialYearsData.setValue(GlobalDeclaration.financeyears);
            }
        }
        return financialYearsData;
    }

    private void loadfinancialYears() {
        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MainFinance> call = apiInterface.financialYears();
        Log.e(" fin url", call.request().url().toString());

        call.enqueue(new Callback<MainFinance>() {
            @Override
            public void onResponse(Call<MainFinance> call, Response<MainFinance> response) {

                if (response.body() != null) {
                    financialYearsData.setValue(response.body().getFinYears());
                    GlobalDeclaration.financeyears = response.body().getFinYears();
                    pd.dismiss();

                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }

            }

            @Override
            public void onFailure(Call<MainFinance> call, Throwable t) {

                t.printStackTrace();
                pd.dismiss();
            }
        });

    }

    public LiveData<List<Val>> getMemberCountsForDashboardMobile(String level) {

        if (memberListMutableLiveData == null) {
            memberListMutableLiveData = new MutableLiveData<>();
            if (GlobalDeclaration.memberCounts == null) {
                loadmemberListMutableLiveData(level);
            } else {
                memberListMutableLiveData.setValue(GlobalDeclaration.memberCounts);
            }
        }
        return memberListMutableLiveData;
    }

    public LiveData<List<StatelevelDistrictViewCountResponse>> getAllMandals(String level, String fyid, int districtId) {

//        if (alListMutableLiveData2 == null) {
        alListMutableLiveData2 = new MutableLiveData<>();
//            if (GlobalDeclaration.mandalData == null) {
        loadAllMandals(level, fyid, districtId);
//            } else {
//                alListMutableLiveData2.setValue(GlobalDeclaration.mandalData);
//            }
        //  }
        return alListMutableLiveData2;
    }

    public LiveData<List<StatelevelDistrictViewCountResponse>> getAllVillages(String level, String fyid, int mandalId) {

//        if (alListMutableLiveData3 == null) {
        alListMutableLiveData3 = new MutableLiveData<>();
//            if (GlobalDeclaration.villageData == null) {
        loadAllVillages(level, fyid, mandalId);
//            } else {
//                alListMutableLiveData3.setValue(GlobalDeclaration.villageData);
//            }
//        }
        return alListMutableLiveData3;
    }

    public LiveData<List<UserTypesList>> getInstitutionCounts(String spn_year) {

        alListInsti = new MutableLiveData<>();
        loadInstitutionCounts(spn_year);


        return alListInsti;
    }

    public LiveData<List<Age>> getAges(String role, String distid, String uid) {

        ageListMutableLiveData = new MutableLiveData<>();
        loadAges(role, distid, uid);
        return ageListMutableLiveData;
    }

    public LiveData<List<BloodGroups>> getBlood(String role, String districtsid, String userid) {

        bloodResponseMutableLiveData = new MutableLiveData<>();
        loadBlood(districtsid, role, userid);
        return bloodResponseMutableLiveData;
    }

    public LiveData<DashboardCountResponse> getDashboardCounts(String districtId, String spn_year) {

//        if (dashboardCountResponseMutableLiveData == null) {
        dashboardCountResponseMutableLiveData = new MutableLiveData<>();
        // if (GlobalDeclaration.counts == null) {
        loadDashboardCounts(districtId, spn_year);
//            } else {
//                dashboardCountResponseMutableLiveData.setValue(GlobalDeclaration.counts);
//            }
        // }
        return dashboardCountResponseMutableLiveData;

    }

    public LiveData<List<DayWiseReportCountResponse>> getDaysCount(String finanyear, String distid, String monthid) {

        dayListMutableLiveData = new MutableLiveData<>();
        if (distid.equalsIgnoreCase("0")) {
            loadDaywiseCounts(finanyear, "", monthid);
        } else {
            loadDaywiseCounts(finanyear, distid, monthid);
        }
        return dayListMutableLiveData;
    }

    public LiveData<List<Genders>> getGender(String role, String districtid, String userid) {

        // if (genderResponseMutableLiveData == null) {
        genderResponseMutableLiveData = new MutableLiveData<>();
        loadGenders(userid, districtid, role);
        //}
        return genderResponseMutableLiveData;
    }

    public LiveData<List<Last10day>> getGovtPvt(String districtid) {

        // if (genderResponseMutableLiveData == null) {
        goListMutableLiveData = new MutableLiveData<>();
        loadGovtPvt(districtid);
        //}
        return goListMutableLiveData;
    }

    public LiveData<ContactBean> getContacts(String type) {

        // if (genderResponseMutableLiveData == null) {
        contactListMutableLiveData = new MutableLiveData<>();
        loadContacts(type);
        //}
        return contactListMutableLiveData;
    }


    private void loadContacts(String type) {
        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ContactBean> call = apiInterface.additionsCentersServiceContact(type);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<ContactBean>() {
            @Override
            public void onResponse(Call<ContactBean> call, Response<ContactBean> response) {

                pd.dismiss();
                if (response.body() != null) {

                    contactListMutableLiveData.setValue(response.body());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ContactBean> call, Throwable t) {

                pd.dismiss();
                t.printStackTrace();
            }
        });

    }


    private void loadGovtPvt(String districtid) {

        // pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GovtVsPvtResponse> call = apiInterface.GovPvtService(districtid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<GovtVsPvtResponse>() {
            @Override
            public void onResponse(Call<GovtVsPvtResponse> call, Response<GovtVsPvtResponse> response) {

                //pd.dismiss();
                if (response.body() != null) {

                    goListMutableLiveData.setValue(response.body().getLast10days());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GovtVsPvtResponse> call, Throwable t) {

                // pd.dismiss();
                t.printStackTrace();
            }
        });


    }

    private void loadDaywiseCounts(String finanyear, String distid, String monthid) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<DayWiseReportCountResponse>> call = apiInterface.DayWiseReportDataWS(distid, finanyear, monthid);

        Log.e(" day url", call.request().url().toString());


        call.enqueue(new Callback<List<DayWiseReportCountResponse>>() {
            @Override
            public void onResponse(Call<List<DayWiseReportCountResponse>> call, Response<List<DayWiseReportCountResponse>> response) {
                pd.dismiss();
                if (response.body() != null) {

                    //ageList.addAll(response.body().getAges());
                    dayListMutableLiveData.setValue(response.body());


                } else {
                    // Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<DayWiseReportCountResponse>> call, Throwable t) {

                pd.dismiss();
                t.printStackTrace();
            }
        });


    }

    private void loadAges(String role, String distid, String uid) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AgeResponse> call = apiInterface.ageWiseService(distid, role, GlobalDeclaration.spn_year, uid);

        Log.e("  url", call.request().url().toString());


        call.enqueue(new Callback<AgeResponse>() {
            @Override
            public void onResponse(Call<AgeResponse> call, Response<AgeResponse> response) {

                if (response.body() != null) {

                    //ageList.addAll(response.body().getAges());
                    ageListMutableLiveData.setValue(response.body().getAges());
                    pd.dismiss();


                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }

            }

            @Override
            public void onFailure(Call<AgeResponse> call, Throwable t) {

                t.printStackTrace();
                pd.dismiss();
            }
        });


    }

    private void loadBlood(String districtsid, String role, String userid) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BloodResponse> call = apiInterface.BloodGroupWiseService(districtsid, role, GlobalDeclaration.spn_year, userid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<BloodResponse>() {
            @Override
            public void onResponse(Call<BloodResponse> call, Response<BloodResponse> response) {

                if (response.body() != null) {
                    bloodResponseMutableLiveData.setValue(response.body().getBloodGroups());
                    pd.dismiss();
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }

            }

            @Override
            public void onFailure(Call<BloodResponse> call, Throwable t) {

                pd.dismiss();
                t.printStackTrace();
            }
        });


    }

    private void loadDashboardCounts(String districtId, String spn_year) {
        // customProgressDialog.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DashboardCountResponse> call = apiInterface.getMemberCountsForDashboard(districtId, spn_year);

        Log.e("counts url", call.request().url().toString());

        call.enqueue(new Callback<DashboardCountResponse>() {
            @Override
            public void onResponse(Call<DashboardCountResponse> call, Response<DashboardCountResponse> response) {

                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("200")) {
                        // customProgressDialog.dismiss();
                        dashboardCountResponseMutableLiveData.setValue(response.body());
                        GlobalDeclaration.counts = response.body();
                    } else {
                        //Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                        Log.e("district view model", "onResponse: Response null");
                        //  customProgressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardCountResponse> call, Throwable t) {

                // Toast.makeText(getActivity(), "error failure", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                GlobalDeclaration.failedcounts = true;
                Log.e("vm counts", "onFailure");
                //    customProgressDialog.dismiss();

            }
        });
    }

    private void loadmemberListMutableLiveData(String level) {
        //pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MembershipCounts> call = apiInterface.getMemberCountsForDashboardMobile(level);
        Log.e("mem counts url", call.request().url().toString());

        call.enqueue(new Callback<MembershipCounts>() {


            @Override
            public void onResponse(Call<MembershipCounts> call,
                                   retrofit2.Response<MembershipCounts> response) {
                // pd.dismiss();
                if (response.body() != null) {
                    memberListMutableLiveData.setValue(response.body().getVals());
                    GlobalDeclaration.memberCounts = response.body().getVals();
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MembershipCounts> call, Throwable t) {

                t.printStackTrace();
                //pd.dismiss();
            }
        });
    }

    private void loadInstitutionCounts(String spn_year) {
        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<UserTypesList>> call = apiInterface.
                getInstitutesWiseCount(spn_year);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<UserTypesList>>() {


            @Override
            public void onResponse(Call<List<UserTypesList>> call,
                                   retrofit2.Response<List<UserTypesList>> response) {
                pd.dismiss();
                if (response.body() != null) {
                    alListInsti.setValue(response.body());
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserTypesList>> call, Throwable t) {

                t.printStackTrace();
                pd.dismiss();
            }
        });
    }

    private void loadAllDistricts(String role, String fyid) {

        //  pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StatelevelDistrictViewCountResponse>> call = apiInterface.getDrillDownCountLevelWiseWs1(role, fyid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<StatelevelDistrictViewCountResponse>>() {

            @Override
            public void onResponse(Call<List<StatelevelDistrictViewCountResponse>> call, retrofit2.Response<List<StatelevelDistrictViewCountResponse>> response) {
                pd.dismiss();
                if (response.body() != null) {
                    alListMutableLiveData1.setValue(response.body());
                    GlobalDeclaration.districtData = response.body();
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<StatelevelDistrictViewCountResponse>> call, Throwable t) {

                t.printStackTrace();
                // pd.dismiss();
            }
        });


    }

    private void loadAllMandals(String role, String fyid, int districtId) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StatelevelDistrictViewCountResponse>> call = apiInterface.getDrillDownCountLevelWiseWs2(role, fyid, districtId);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<StatelevelDistrictViewCountResponse>>() {

            @Override
            public void onResponse(Call<List<StatelevelDistrictViewCountResponse>> call, retrofit2.Response<List<StatelevelDistrictViewCountResponse>> response) {

                pd.dismiss();
                if (response.body() != null) {
                    alListMutableLiveData2.setValue(response.body());
                    GlobalDeclaration.mandalData = response.body();
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<StatelevelDistrictViewCountResponse>> call, Throwable t) {

                t.printStackTrace();
                pd.dismiss();
            }
        });


    }

    private void loadAllVillages(String role, String fyid, int mandalId) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StatelevelDistrictViewCountResponse>> call = apiInterface.getDrillDownCountLevelWiseWs3(role, fyid, mandalId);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<List<StatelevelDistrictViewCountResponse>>() {


            @Override
            public void onResponse(Call<List<StatelevelDistrictViewCountResponse>> call, retrofit2.Response<List<StatelevelDistrictViewCountResponse>> response) {
                pd.dismiss();
                if (response.body() != null) {
                    alListMutableLiveData3.setValue(response.body());
                    GlobalDeclaration.villageData = response.body();
                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<StatelevelDistrictViewCountResponse>> call, Throwable t) {

                t.printStackTrace();
                pd.dismiss();
            }
        });


    }

    private void loadGenders(String userid, String districtid, String role) {

        pd.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GenderResponse> call = apiInterface.GenderWiseService(districtid, role, GlobalDeclaration.spn_year, userid);
        Log.e("  url", call.request().url().toString());

        call.enqueue(new Callback<GenderResponse>() {
            @Override
            public void onResponse(Call<GenderResponse> call, Response<GenderResponse> response) {

                if (response.body() != null) {
                    genderResponseMutableLiveData.setValue(response.body().getGenders());
                    pd.dismiss();

                } else {
                    //Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }

            }

            @Override
            public void onFailure(Call<GenderResponse> call, Throwable t) {

                t.printStackTrace();
                pd.dismiss();
            }
        });


    }


}