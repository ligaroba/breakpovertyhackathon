package root.com.jiranimmicrocredit.providers;

import android.net.Uri;
import android.provider.BaseColumns;

import root.com.jiranimmicrocredit.utils.Constants;

/**
 * Created by root on 11/1/15.
 */
public class DBContract {


    public static final String PATH_PROFILE = "tbl_profile";
    public static final String PATH_BUSINESS = "tbl_business";
    public static final String PATH_PROPERTY = "tbl_property";
    public static final String PATH_OTHEROCCUPATION ="tbl_otheroccupation" ;
    public static final String PATH_EMPLOYMENT = "tbl_employment";
    public static final String PATH_LOAN = "tbl_loan";
    public static final String PATH_LOANAPPLICATION ="tbl_loanapplication" ;
    public static final String PATH_BUSINESSTYPE ="tbl_businesstype" ;
    public static final String PATH_GURANTORS ="tbl_gurantors" ;
    public static final String PATH_LOAN_CLUSTER="tbl_loan_cluster" ;
    public static final String PATH_LOAN_PAYMENT="tbl_loanpayment" ;

    public static final String DATABASE_NAME="Jiranimicrocredit";
    public static final int DATABASE_VERSION=1;






    public static final String CONTENT_AUTHORITY="root.com.jiranimmicrocredit.Jiranimicrocredit";
    public static final Uri BASE_URI=Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class Profile implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_PROFILE;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_PROFILE;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_PROFILE).build();
        public static final String PROFILE_TABLE=PATH_PROFILE;
        public static final String KEY_FIRSTNAME=Constants.PARAM_FIRSTNAME;
        public static final String KEY_SECONDNAME=Constants.PARAM_SECONDNAME;
        public static final String KEY_LASTNAME=Constants.PARAM_LASTNAME;
        public static final String KEY_AVATAR=Constants.PARAM_AVATAR;
        public static final String KEY_IDNUMBER=Constants.PARAM_IDNUMBER;
        public static final String KEY_EMAIL = Constants.PARAM_EMAIL;
        public static final String KEY_MOBILE =Constants.PARAM_PHONE;
        public static final String KEY_STATUS =Constants.PARAM_RANKSTATUS;
        public static final String KEY_TIMEADDED =Constants.PARAM_TIMEADDED;

    }

    public static class Gurantors implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_GURANTORS;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_GURANTORS;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_GURANTORS).build();
        public static final String GURANTORS_TABLE=PATH_GURANTORS;
        public static final String KEY_FIRSTNAME=Constants.PARAM_FIRSTNAME;
        public static final String KEY_SECONDNAME=Constants.PARAM_SECONDNAME;
        public static final String KEY_LASTNAME=Constants.PARAM_LASTNAME;
        public static final String KEY_AVATAR=Constants.PARAM_AVATAR;
        public static final String KEY_IDNUMBER=Constants.PARAM_IDNUMBER;
        public static final String KEY_EMAIL = Constants.PARAM_EMAIL;
        public static final String KEY_MOBILE =Constants.PARAM_PHONE;
        public static final String KEY_GUARANTORTYPE =Constants.PARAM_GUARANTORTYPE;
        public static final String KEY_RESIDENCE =Constants.PARAM_RESIDENCE;
        public static final String KEY_TIMEADDED =Constants.PARAM_TIMEADDED;


    }

    public static class Business implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_BUSINESS;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_BUSINESS;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_BUSINESS).build();
        public static final String BUSINESS_TABLE=PATH_BUSINESS;

        public static final String KEY_IDAPPLICANT=Constants.PARAM_ID_APPLICANT;
        public static final String KEY_BUSINESSNAME=Constants.PARAM_BUSINESS_NAME;
        public static final String KEY_BUSINESSTYPE=Constants.PARAM_ID_BUSINES_TYPE;
        public static final String KEY_AVATAR=Constants.PARAM_AVATAR;
        public static final String KEY_TURNOVER=Constants.PARAM_TURNOVER;
        public static final String KEY_LOCATION= Constants.PARAM_LOCATION;
        public static final String KEY_YEAR_OF_BUSINESS =Constants.PARAM_YEAR_OF_BUSINESS;
        public static final String KEY_INCOME =Constants.PARAM_INCOME;
        public static final String KEY_TIMEADDED =Constants.PARAM_TIMEADDED;



    }

    public static class Employment implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_EMPLOYMENT;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_EMPLOYMENT;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_EMPLOYMENT).build();
        public static final String EMPLOYMENT_TABLE=PATH_EMPLOYMENT;

        public static final String KEY_IDAPPLICANT=Constants.PARAM_ID_APPLICANT;
        public static final String KEY_EMPLOYERNAME=Constants.PARAM_EMPLOYER_NAME;
        public static final String KEY_DEPARTMENT=Constants.PARAM_DEPARTMENT;
        public static final String KEY_AVATAR=Constants.PARAM_AVATAR;
        public static final String KEY_PARAM_TERMS_OF_EMPLOYMENT=Constants.PARAM_TERMS_OF_EMPLOYMENT;
        public static final String KEY_LOCATION= Constants.PARAM_LOCATION;
        public static final String KEY_INCOME =Constants.PARAM_INCOME;
        public static final String KEY_TIMEADDED =Constants.PARAM_TIMEADDED;


    }

    public static class OtherOccupations implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_OTHEROCCUPATION;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_OTHEROCCUPATION;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_OTHEROCCUPATION).build();
        public static final String OTHEROCCUPATION_TABLE=PATH_OTHEROCCUPATION;
        public static final String KEY_IDAPPLICANT=Constants.PARAM_ID_APPLICANT;
        public static final String KEY_OCCUPATIONAME=Constants.PARAM_OCCUPATIONAME;
        public static final String KEY_AVATAR=Constants.PARAM_AVATAR;
        public static final String KEY_LOCATION= Constants.PARAM_LOCATION;
        public static final String KEY_INCOME =Constants.PARAM_INCOME;
        public static final String KEY_TIMEADDED =Constants.PARAM_TIMEADDED;


    }

    public static class  LoanApplication implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_LOANAPPLICATION;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_LOANAPPLICATION;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_LOANAPPLICATION).build();
        public static final String LOANAPPLICATION_TABLE=PATH_LOANAPPLICATION;
        public static final String KEY_INSTALLMENT=Constants.PARAM_INSTALMENTAMOUNT;
        public static final String KEY_REPAYMENTPERIOD=Constants.PARAM_LOAN_REPAYMENTDATE;
        public static final String KEY_PAYMENTDURATION=Constants.PARAM_LOAN_PAYMENTDURATION;
        public static final String KEY_STATUS= Constants.PARAM_STATUS;
        public static final String KEY_LOANAMOUNT =Constants.PARAM_LOANAMOUNT;
        public static final String KEY_PURPOSE=Constants.PARAM_LOAN_PURPOSE;
        public static final String KEY_TIMEADDED =Constants.PARAM_TIMEADDED;

    }

    public static class  LoanPaid implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_LOAN_PAYMENT;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_LOAN_PAYMENT;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_LOAN_PAYMENT).build();
        public static final String LOANPAYMENT_TABLE=PATH_LOAN_PAYMENT;
        public static final String KEY_IDLOANAPPLICATION=Constants.PARAM_ID_LOANAPPLICATION;
        public static final String KEY_STATUS= Constants.PARAM_STATUS;
        public static final String KEY_AMOUNTPAID =Constants.PARAM_AMOUNTPAID;
        public static final String KEY_TIMEADDED =Constants.PARAM_TIMEADDED;

    }

    public static class Property implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_PROPERTY;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_PROPERTY;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_PROPERTY).build();
        public static final String PROPERTY_TABLE=PATH_PROPERTY;

        public static final String KEY_IDAPPLICANT=Constants.PARAM_ID_APPLICANT;
        public static final String KEY_PROPERTYNAME=Constants.PARAM_PROPERTYNAME;
        public static final String KEY_PLOTNUMBER=Constants.PARAM_PLOTNUMBER;
        public static final String KEY_SPECIFYOWNERSHIP=Constants.PARAM_SPECIFYOWNERSHIP;
        public static final String KEY_AVATAR=Constants.PARAM_AVATAR;
        public static final String KEY_PARAM_OCCUPANCY=Constants.PARAM_OCCUPANCY;
        public static final String KEY_LOCATION= Constants.PARAM_LOCATION;
        public static final String KEY_INCOME =Constants.PARAM_INCOME;
        public static final String KEY_TIMEADDED =Constants.PARAM_TIMEADDED;

    }


    public static class Loans implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_LOAN;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_LOAN;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_LOAN).build();
        public static final String LOAN_TABLE=PATH_LOAN;

        public static final String KEY_LOANAME=Constants.PARAM_LOANNAME;
        public static final String KEY_INSTALMENTAMOUNT=Constants.PARAM_INSTALMENTAMOUNT;
        public static final String KEY_REPAYMENTPERIOD=Constants.PARAM_PAYMENTPERIOD;
        public static final String KEY_LOANTYPE=Constants.PARAM_LOANTYPE;
        public static final String KEY_STATUS= Constants.PARAM_STATUS;
        public static final String KEY_LOANAMOUNT =Constants.PARAM_LOANAMOUNT;
        public static final String KEY_TIMEADDED =Constants.PARAM_TIMEADDED;


    }
    public static class BusinessType implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_BUSINESSTYPE;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_BUSINESSTYPE;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_BUSINESSTYPE).build();
        public static final String BUSINESSTYPE_TABLE=PATH_BUSINESSTYPE;
        public static final String KEY_NATUREOFBUSINESS=Constants.PARAM_NATURE_OF_BUSINESS;
        public static final String KEY_TIMEADDED =Constants.PARAM_TIMEADDED;


    }

    public static class LoanCluster implements BaseColumns{

        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_LOAN_CLUSTER;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_LOAN_CLUSTER;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_LOAN_CLUSTER).build();
        public static final String LOAN_CLUSTER_TABLE=PATH_LOAN_CLUSTER;

        public static final String KEY_LOANAME=Constants.PARAM_LOANNAME;
        public static final String KEY_INSTALMENTAMOUNT=Constants.PARAM_INSTALMENTAMOUNT;
        public static final String KEY_REPAYMENTPERIOD=Constants.PARAM_PAYMENTPERIOD;
        public static final String KEY_LOANTYPE=Constants.PARAM_LOANTYPE;
        public static final String KEY_STATUS= Constants.PARAM_STATUS;
        public static final String KEY_LOANAMOUNT =Constants.PARAM_LOANAMOUNT;
        public static final String KEY_TIMEADDED =Constants.PARAM_TIMEADDED;
    }
}
