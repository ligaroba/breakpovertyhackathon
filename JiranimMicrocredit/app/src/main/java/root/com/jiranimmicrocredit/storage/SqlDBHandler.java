package root.com.jiranimmicrocredit.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import root.com.jiranimmicrocredit.providers.DBContract;
import root.com.jiranimmicrocredit.utils.SetterGetters;

/**
 * Created by root on 11/1/15.
 */
public class SqlDBHandler  extends SQLiteOpenHelper{
    private String TAG="SqlDBHandler";

    public SqlDBHandler(Context context) {
        super(context, DBContract.DATABASE_NAME, null,  DBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String [] queries={
                SQLstatement.profileSQL,
                SQLstatement.businessSQL,
                SQLstatement.businessTypeSQL,
                SQLstatement.employmentSQL,
                SQLstatement.gurantorsSQL,
                SQLstatement.LoanApplicationSQL,
                SQLstatement.LoansSQL,
                SQLstatement.OtherOccupationsSQL,
                SQLstatement.PropertySQL,
                SQLstatement.LoansClusterSQL,
                SQLstatement.LoanPaymentSQL
        };


                for (int i=0;i<queries.length;i++){

                   db.execSQL(queries[i]);

                    Log.e("SQLITEDB", queries[i]);
                }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
                String[]tables={
                        DBContract.PATH_PROFILE,
                        DBContract.PATH_BUSINESSTYPE,
                        DBContract.PATH_BUSINESS,
                        DBContract.PATH_EMPLOYMENT,
                        DBContract.PATH_LOAN,
                        DBContract.PATH_PROPERTY,
                        DBContract.PATH_GURANTORS,
                        DBContract.PATH_LOANAPPLICATION,
                        DBContract.PATH_PROPERTY,
                        DBContract.PATH_LOAN_CLUSTER,
                        DBContract.PATH_LOAN_PAYMENT

                };


                    for (int j=0;j<tables.length;j++){
                        db.execSQL("DROP TABLE " + tables[j]);
                    }
                 onCreate(db);
    }


    public SetterGetters getProfile(){
        SetterGetters list=new SetterGetters();
        SQLiteDatabase db=getReadableDatabase();

        return list;


    }
    public void resetTables(String tablename) {
        // TODO Auto-generated method stub
        SQLiteDatabase db =this.getWritableDatabase();
        try {
            db.delete(tablename, null,null);
            // db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + tablename + "'");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        db.close();

    }
    public int getUserId() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT "+DBContract.Profile._ID+" FROM "+ DBContract.Profile.PROFILE_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            return cursor.getInt(cursor.getColumnIndex(DBContract.Profile._ID));
        else
            return -1; // not found
    }


    public List<SetterGetters> getLoanPlans() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ DBContract.Loans.LOAN_TABLE;

        List<SetterGetters> list=new ArrayList<SetterGetters>();
        Cursor cursor=null;

        cursor= db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
                SetterGetters setta =new SetterGetters();
                setta.setLoanname(cursor.getString(cursor.getColumnIndex(DBContract.Loans.KEY_LOANAME)));
                setta.setLoanAmount(cursor.getString(cursor.getColumnIndex(DBContract.Loans.KEY_LOANTYPE)));
               setta.setPaymentPeriod(cursor.getString(cursor.getColumnIndex(DBContract.Loans.KEY_REPAYMENTPERIOD)));
                setta.setInstalmentAmount(cursor.getString(cursor.getColumnIndex(DBContract.Loans.KEY_INSTALMENTAMOUNT)));
                setta.setTimeAdded(cursor.getString(cursor.getColumnIndex(DBContract.Loans.KEY_TIMEADDED)));
                setta.setId(cursor.getInt(cursor.getColumnIndex(DBContract.Loans._ID)));
                list.add(setta);
                cursor.moveToNext();

            }
        }
        return list;

    }

    public List<SetterGetters> getMyLoan() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ DBContract.LoanApplication.LOANAPPLICATION_TABLE;
        String statusName="";
        String repaymentdate="";
        int repaymentdays=0;
        int repaymentperiod=0;
        String paymentstartdate="";
        List<SetterGetters> list=new ArrayList<SetterGetters>();
        Cursor cursor=null;

        cursor= db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
                SetterGetters setta =new SetterGetters();
                repaymentperiod=cursor.getInt(cursor.getColumnIndex(DBContract.LoanApplication.KEY_PAYMENTDURATION));
                paymentstartdate=cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_REPAYMENTPERIOD));

                setta.setPaymentStartDate(paymentstartdate);
                setta.setPaymentPeriod(String.valueOf(repaymentperiod));
                setta.setLoanAmount(cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_LOANAMOUNT)));
                if(!paymentstartdate.equalsIgnoreCase("Not assigned")||!paymentstartdate.equalsIgnoreCase("")) {
                    repaymentdays = repaymentperiod * 7;
                    Date dt = new Date();
                    Log.e(TAG, cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_REPAYMENTPERIOD)));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                    try {
                        Date date = sdf.parse(paymentstartdate);
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.DAY_OF_MONTH, repaymentdays);
                        repaymentdate = sdf.format(c.getTime());
                        setta.setPaymentEndDate(repaymentdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    setta.setPaymentEndDate("Not Set");
                }

             //   '1-pending: 2-approved: 3-rejected with a reason: 4-ongoing:5-paid'
                int status =cursor.getInt(cursor.getColumnIndex(DBContract.LoanApplication.KEY_STATUS));
                switch(status){
                    case 1:
                        statusName="Pending";
                    break;
                    case 2:
                        statusName="Approved";
                        break;
                    case 3:
                        statusName="Rejected with a reason";
                        break;
                    case 4:
                        statusName="Ongoing";
                        break;
                    case 5:
                        statusName="Paid";
                        break;
                }

                setta.setLoanStatus(statusName);


                setta.setTimeAdded(cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_TIMEADDED)));

                setta.setId(cursor.getInt(cursor.getColumnIndex(DBContract.LoanApplication._ID)));
                list.add(setta);
                cursor.moveToNext();

            }
        }
        return list;

    }

    public List<SetterGetters> getMyLoanDetails(int idloanapplicant) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "Select "+"sum("+DBContract.LoanPaid.KEY_AMOUNTPAID+") as totalAmountPaid," +
                DBContract.LoanApplication.KEY_PAYMENTDURATION +","+
        DBContract.LoanApplication.KEY_REPAYMENTPERIOD+","+
                DBContract.LoanApplication.KEY_LOANAMOUNT+","+
                DBContract.LoanApplication.KEY_INSTALLMENT+","+
                DBContract.LoanApplication.KEY_REPAYMENTPERIOD+","+
                "lpt."+DBContract.LoanApplication.KEY_STATUS+","+
                "lpt."+DBContract.LoanApplication.KEY_TIMEADDED+","+
                DBContract.LoanApplication.KEY_PURPOSE +" From "
                +DBContract.LoanApplication.LOANAPPLICATION_TABLE+" as lpt " +
                "inner join "+ DBContract.LoanPaid.LOANPAYMENT_TABLE + "" +
                " Where lpt." + DBContract.LoanApplication._ID+"="+ idloanapplicant ;

Log.e(TAG,"sql"+ query);
        List<SetterGetters> list=new ArrayList<SetterGetters>();
        Cursor cursor=null;

        String statusName="";
        String repaymentdate="";
        int repaymentdays=0;
        double loanamount=0.00;
        double amounttobepaid=0.00;
        int repaymentperiod=0;
        String paymentstartdate="";
        double totalAmountPaid=0.00;
        SetterGetters setta=null;
        cursor= db.rawQuery(query,null);
      //  cursor= db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
                setta =new SetterGetters();
               // totalAmountPaid+=cursor.getInt(cursor.getColumnIndex(DBContract.LoanPaid.KEY_AMOUNTPAID));
                repaymentperiod=cursor.getInt(cursor.getColumnIndex(DBContract.LoanApplication.KEY_PAYMENTDURATION));
                paymentstartdate=cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_REPAYMENTPERIOD));
                loanamount = cursor.getInt(cursor.getColumnIndex(DBContract.LoanApplication.KEY_LOANAMOUNT));
                Log.e(TAG, "repaymentperiod" + repaymentperiod);
                setta.setPaymentStartDate(paymentstartdate);
                if(!paymentstartdate.equalsIgnoreCase("Not assigned")||!paymentstartdate.equalsIgnoreCase("")) {
                    setta.setPaymentPeriod(String.valueOf(repaymentperiod));
                    setta.setLoanAmount(cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_LOANAMOUNT)));

                    repaymentdays = repaymentperiod * 7;
                    Date dt = new Date();
                    Log.e(TAG, cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_REPAYMENTPERIOD)));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                    try {
                        Date date = sdf.parse(paymentstartdate);
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.DAY_OF_MONTH, repaymentdays);
                        repaymentdate = sdf.format(c.getTime());
                        setta.setPaymentEndDate(repaymentdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    setta.setPaymentEndDate("Not Set");
                }

                //   '1-pending: 2-approved: 3-rejected with a reason: 4-ongoing:5-paid'
                int status =cursor.getInt(cursor.getColumnIndex(DBContract.LoanApplication.KEY_STATUS));
                switch(status){
                    case 1:
                        statusName="Pending";
                        break;
                    case 2:
                        statusName="Approved";
                        break;
                    case 3:
                        statusName="Rejected with a reason";
                        break;
                    case 4:
                        statusName="Ongoing";
                        break;
                    case 5:
                        statusName="Paid";
                        break;
                }

                setta.setLoanStatus(statusName);
                // setta.setPaymentEndDate(cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_TIMEADDED)));
                setta.setPurpose(cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_PURPOSE)));
                setta.setInstalmentAmount(cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_INSTALLMENT)));
                setta.setTimeAdded(cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_TIMEADDED)));

                amounttobepaid+=loanamount- cursor.getDouble(cursor.getColumnIndex("totalAmountPaid"));

                setta.setTotalAmounttobePaid(String.valueOf(amounttobepaid));
                setta.setTotalAmountPaid(cursor.getString(cursor.getColumnIndex("totalAmountPaid")));
                Log.e(TAG, "totalAmountPaid" + totalAmountPaid);
                list.add(setta);
                cursor.moveToNext();

            }




        }
        return list;

    }




}
