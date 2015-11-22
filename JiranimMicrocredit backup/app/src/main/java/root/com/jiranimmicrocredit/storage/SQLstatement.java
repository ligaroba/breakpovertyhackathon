package root.com.jiranimmicrocredit.storage;

import root.com.jiranimmicrocredit.providers.DBContract;

/**
 * Created by root on 11/1/15.
 */
public class SQLstatement {




    public static final String profileSQL="CREATE TABLE " + DBContract.Profile.PROFILE_TABLE + "  ( "+
    DBContract.Profile._ID + " INTEGER PRIMARY KEY ," +
    DBContract.Profile.KEY_FIRSTNAME + " varchar(250) NULL," +
    DBContract.Profile.KEY_SECONDNAME + " varchar(250) NULL," +
    DBContract.Profile.KEY_LASTNAME + " varchar(250) NULL," +
    DBContract.Profile.KEY_MOBILE + " varchar(250) NULL," +
    DBContract.Profile.KEY_EMAIL + " varchar(250) NULL," +
    DBContract.Profile.KEY_IDNUMBER + " varchar(250) NULL," +
    DBContract.Profile.KEY_STATUS + " int(1) NULL," +
    DBContract.Profile.KEY_TIMEADDED + " varchar(250) NULL," +
    DBContract.Profile.KEY_AVATAR + " varchar(250) NULL" +
            ");";

    public static final String gurantorsSQL="CREATE TABLE " + DBContract.Gurantors.GURANTORS_TABLE + "  ( "+
            DBContract.Gurantors._ID + " INTEGER PRIMARY KEY ," +
            DBContract.Gurantors.KEY_FIRSTNAME + " varchar(250) NULL," +
            DBContract.Gurantors.KEY_SECONDNAME + " varchar(250) NULL," +
            DBContract.Gurantors.KEY_LASTNAME + " varchar(250) NULL," +
            DBContract.Gurantors.KEY_MOBILE + " varchar(250) NULL," +
            DBContract.Gurantors.KEY_EMAIL + " varchar(250) NULL," +
            DBContract.Gurantors.KEY_IDNUMBER + " varchar(250) NULL," +
            DBContract.Gurantors.KEY_GUARANTORTYPE + " varchar(250) NULL," +
            DBContract.Gurantors.KEY_RESIDENCE + " varchar(250) NULL," +
            DBContract.Gurantors.KEY_TIMEADDED + " varchar(250) NULL," +
            DBContract.Gurantors.KEY_AVATAR + " varchar(250) NULL" +
            ");";


    public static final String businessSQL="CREATE TABLE " + DBContract.Business.BUSINESS_TABLE + "  ( "+
            DBContract.Business._ID + " INTEGER PRIMARY KEY ," +
            DBContract.Business.KEY_BUSINESSNAME + " varchar(250) NULL," +
            DBContract.Business.KEY_BUSINESSTYPE + " varchar(250) NULL," +
            DBContract.Business.KEY_YEAR_OF_BUSINESS + " varchar(250) NULL," +
            DBContract.Business.KEY_TURNOVER + " int(5) NULL," +
            DBContract.Business.KEY_INCOME + " int(5) NULL," +
            DBContract.Business.KEY_LOCATION + " varchar(250) NULL," +
            DBContract.Business.KEY_TIMEADDED + " varchar(250) NULL," +
            DBContract.Business.KEY_AVATAR + " varchar(250) NULL" +
            ");";


    public static final String employmentSQL="CREATE TABLE " + DBContract.Employment.EMPLOYMENT_TABLE + "  ( "+
            DBContract.Employment._ID + " INTEGER PRIMARY KEY ," +
            DBContract.Employment.KEY_EMPLOYERNAME + " varchar(250) NULL," +
            DBContract.Employment.KEY_DEPARTMENT + " varchar(250) NULL," +
            DBContract.Employment.KEY_PARAM_TERMS_OF_EMPLOYMENT + " varchar(250) NULL," +
            DBContract.Employment.KEY_INCOME + " int(5) NULL," +
            DBContract.Employment.KEY_LOCATION + " varchar(250) NULL," +
            DBContract.Employment.KEY_TIMEADDED + " varchar(250) NULL," +
            DBContract.Employment.KEY_AVATAR + " varchar(250) NULL" +
            ");";

    public static final String OtherOccupationsSQL="CREATE TABLE " + DBContract.OtherOccupations.OTHEROCCUPATION_TABLE + "  ( "+
            DBContract.OtherOccupations._ID + " INTEGER PRIMARY KEY ," +
            DBContract.OtherOccupations.KEY_OCCUPATIONAME + " varchar(250) NULL," +
            DBContract.OtherOccupations.KEY_LOCATION + " varchar(250) NULL," +
            DBContract.OtherOccupations.KEY_INCOME + " int(5) NULL," +
            DBContract.OtherOccupations.KEY_TIMEADDED + " varchar(250) NULL," +
            DBContract.OtherOccupations.KEY_AVATAR + " varchar(250) NULL" +
            ");";

    public static final String LoanApplicationSQL="CREATE TABLE " + DBContract.LoanApplication.LOANAPPLICATION_TABLE + "  ( "+
            DBContract.LoanApplication._ID + " INTEGER PRIMARY KEY ," +
            DBContract.LoanApplication.KEY_LOANAME + " varchar(250) NULL," +
            DBContract.LoanApplication.KEY_INSTALMENTAMOUNT + " int(5) NULL," +
            DBContract.LoanApplication.KEY_LOANAMOUNT + " int(5) NULL," +
            DBContract.LoanApplication.KEY_REPAYMENTPERIOD + " varchar(250) NULL," +
            DBContract.LoanApplication.KEY_LOANTYPE + " varchar(250) NULL," +
            DBContract.LoanApplication.KEY_STATUS+ " int(5) NULL," +
            DBContract.LoanApplication.KEY_PURPOSE + " varchar(250) NULL," +
            DBContract.LoanApplication.KEY_TIMEADDED + " varchar(250) NULL" +
            ");";

    public static final String PropertySQL="CREATE TABLE " + DBContract.Property.PROPERTY_TABLE + "  ( "+
            DBContract.Property._ID + " INTEGER PRIMARY KEY ," +
            DBContract.Property.KEY_PROPERTYNAME + " varchar(250) NULL," +
            DBContract.Property.KEY_LOCATION + " varchar(250) NULL," +
            DBContract.Property.KEY_PLOTNUMBER + " varchar(250) NULL," +
            DBContract.Property.KEY_SPECIFYOWNERSHIP + " varchar(250) NULL," +
            DBContract.Property.KEY_PARAM_OCCUPANCY + " int(5) NULL," +
            DBContract.Property.KEY_INCOME + " varchar(250) NULL," +
            DBContract.Property.KEY_TIMEADDED + " varchar(250) NULL," +
            DBContract.Property.KEY_AVATAR + " varchar(250) NULL" +
            ");";

    public static final String LoansSQL="CREATE TABLE " + DBContract.Loans.LOAN_TABLE + "  ( "+
            DBContract.Loans._ID + " INTEGER PRIMARY KEY ," +
            DBContract.Loans.KEY_LOANAME + " varchar(250) NULL," +
            DBContract.Loans.KEY_INSTALMENTAMOUNT + " int(5) NULL," +
            DBContract.Loans.KEY_LOANAMOUNT + " int(5) NULL," +
            DBContract.Loans.KEY_REPAYMENTPERIOD + " varchar(250) NULL," +
            DBContract.Loans.KEY_LOANTYPE + " varchar(250) NULL," +
            DBContract.Loans.KEY_STATUS+ " int(5) NULL," +
            DBContract.Loans.KEY_TIMEADDED + " varchar(250) NULL" +
            ");";
    public static final String LoansClusterSQL="CREATE TABLE " + DBContract.LoanCluster.LOAN_CLUSTER_TABLE + "  ( "+
            DBContract.Loans._ID + " INTEGER PRIMARY KEY ," +
            DBContract.Loans.KEY_LOANAME + " varchar(250) NULL," +
            DBContract.Loans.KEY_INSTALMENTAMOUNT + " int(5) NULL," +
            DBContract.Loans.KEY_LOANAMOUNT + " int(5) NULL," +
            DBContract.Loans.KEY_REPAYMENTPERIOD + " varchar(250) NULL," +
            DBContract.Loans.KEY_LOANTYPE + " varchar(250) NULL," +
            DBContract.Loans.KEY_STATUS+ " int(5) NULL," +
            DBContract.Loans.KEY_TIMEADDED + " varchar(250) NULL" +
            ");";

    public static final String businessTypeSQL="CREATE TABLE " + DBContract.BusinessType.BUSINESSTYPE_TABLE + "  ( "+
            DBContract.BusinessType._ID + " INTEGER PRIMARY KEY ," +
            DBContract.BusinessType.KEY_NATUREOFBUSINESS + " varchar(250) NULL," +
            DBContract.BusinessType.KEY_TIMEADDED + " varchar(250) NULL" +

            ");";





}
