package root.com.jiranimmicrocredit.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import root.com.jiranimmicrocredit.providers.DBContract;
import root.com.jiranimmicrocredit.utils.SetterGetters;

/**
 * Created by root on 11/1/15.
 */
public class SqlDBHandler  extends SQLiteOpenHelper{
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
                SQLstatement.LoansClusterSQL
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
                        DBContract.PATH_LOAN_CLUSTER
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

        List<SetterGetters> list=new ArrayList<SetterGetters>();
        Cursor cursor=null;

        cursor= db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            while (cursor.isAfterLast()==false){
                SetterGetters setta =new SetterGetters();
                setta.setLoanname(cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_LOANAME)));
                setta.setLoanAmount(cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_LOANTYPE)));
                setta.setPaymentPeriod(cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_REPAYMENTPERIOD)));
                setta.setInstalmentAmount(cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_INSTALMENTAMOUNT)));
                setta.setStatus(cursor.getInt(cursor.getColumnIndex(DBContract.LoanApplication.KEY_STATUS)));
                setta.setTimeAdded(cursor.getString(cursor.getColumnIndex(DBContract.LoanApplication.KEY_TIMEADDED)));
                setta.setId(cursor.getInt(cursor.getColumnIndex(DBContract.LoanApplication._ID)));
                list.add(setta);
                cursor.moveToNext();

            }
        }
        return list;

    }




}
