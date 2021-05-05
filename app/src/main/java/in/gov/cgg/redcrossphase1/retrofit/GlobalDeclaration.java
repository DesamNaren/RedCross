package in.gov.cgg.redcrossphase1.retrofit;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.AbstractMemberbean;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.AllScreen;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenLanguagesResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenLoginResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MembersipDistResponse;
import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.MyBlooddonor;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.FinYear;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.StatelevelDistrictViewCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.StudentListBean;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.UserTypesList;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.Val;

public class GlobalDeclaration {
    public static String BASE_URL = "http://uat2.cgg.gov.in:8081/redcross/";
    public static String userID = "";
    public static String districtId = "";
    public static String role = "";
    public static String citizenrole = "";
    public static DashboardCountResponse counts;
    public static String username = "";
    public static String designation = "";
    public static String sharedUname = "";
    public static String sharedUPswd = "";
    public static boolean ischecked;
    public static boolean home;
    public static String FARG_TAG = "";
    public static Integer localDid;
    public static Integer localMid;
    public static Integer localVid;
    public static List<Integer> levCountList = new ArrayList<>();
    public static String type = "";
    public static String leveVName;
    public static String leveMName;
    public static String leveDName;
    public static CitizenLoginResponse loginresponse;
    public static List<CitizenLanguagesResponse> languageresponse;
    public static String guest;
    public static String Selection_type = "";
    public static String cordinatorType = "";
    public static int Selection_MEMbership_type = 0;
    public static StudentListBean drilldownResponse;
    public static List<String> headStringList;
    public static int typePos;
    public static int genPos;
    public static int bgPos;

    public static String Paymenturl;
    public static String encrpyt;
    public static String SELECTEDtype;
    public static int tabposition;
    public static List<StatelevelDistrictViewCountResponse> districtData;
    public static List<StatelevelDistrictViewCountResponse> mandalData;
    public static List<StatelevelDistrictViewCountResponse> villageData;
    public static List<UserTypesList> instiCounts;
    public static String unamefromReg;
    public static boolean failedcounts;
    public static List<Val> memberCounts;
    public static StudentListBean clickeddetails;
    public static String localmeberId;
    public static String localmemType;

    public static String contacttype;
    public static List<MembersipDistResponse> alldistrict;


    public static ArrayList<AbstractMemberbean> abstractList;
    public static List<FinYear> financeyears;
    public static String spn_year;
    public static String forgotuname = "";
    public static String totallist;
    public static int bloodposition;
    public static int contactposition;
    public static String globalMemDob;
    public static String globalMemId;
    public static String citizenMobile;
    public static List<MyBlooddonor> bloodonationdetails = new ArrayList<>();
    public static List<AllScreen> allScreendetails = new ArrayList<>();
    public static String profilePic = "";
    public static String LangShortCode = "";
    public static String LangShortId = "";
    public static boolean flag = false;
}
