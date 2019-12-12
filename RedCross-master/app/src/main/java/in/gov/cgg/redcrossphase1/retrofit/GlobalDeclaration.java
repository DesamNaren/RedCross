package in.gov.cgg.redcrossphase1.retrofit;

import java.util.ArrayList;
import java.util.List;

import in.gov.cgg.redcrossphase1.ui_citiguest.Beans.CitizenLoginResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.DashboardCountResponse;
import in.gov.cgg.redcrossphase1.ui_officer.modelbeans.StudentListBean;

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
    public static String guest;
    public static String Selection_type = "";
    public static String cordinatorType = "";
    public static String Selection_MEMbership_type = "";
    public static StudentListBean drilldownResponse;
    public static List<String> headStringList;
    public static int typePos;
    public static int genPos;
    public static int bgPos;

    public static String Paymenturl;
    public static String encrpyt;
    public static String SELECTEDtype;
    public static int tabposition;

    // public static String BASE_URL="http://qa2.cgg.gov.in:8081/redcross/";

    //Test Commit
}
