package root.com.jiranimmicrocredit.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import root.com.jiranimmicrocredit.storage.SqlDBHandler;

/**
 * Created by root on 11/1/15.
 */
public class DBProvider extends ContentProvider{
    SQLiteDatabase db;
    SqlDBHandler sdb;


    private static final int PROFILE=500;
    private static final int PROFILEID=501;

    private static final int BUSINESS=400;
    private static final int BUSINESSID=401;

    private static final int BUSINESSTYPE=300;
    private static final int BUSINESSTYPEID=301;

    private static final int LOAN=100;
    private static final int LOANID=101;

    private static final int EMPLOYMENT=200;
    private static final int EMPLOYMENTID=201;

    private static final int PROPERTY=700;
    private static final int PROPERTYID=701;

    private static final int LOANAPPLICATION=800;
    private static final int LOANAPPLICATIONID=801;

    private static final int OTHEROCCUPATION=900;
    private static final int OTHEROCCUPATIONID=901;

    private static final int GURANTORS=1000;
    private static final int GURANTORSID=1001;

    private static final int LOANCLUSTER=1100;
    private static final int LOANCLUSTERID=1101;

    private static final UriMatcher matcher =new UriMatcher(-1);

  static {

      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_PROFILE,PROFILE);
      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_PROFILE+"/#",PROFILEID);

      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_BUSINESS,BUSINESS);
      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_BUSINESS+"/#",BUSINESSID);

      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_BUSINESSTYPE,BUSINESSTYPE);
      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_BUSINESSTYPE+"/#",BUSINESSTYPEID);


      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_EMPLOYMENT,EMPLOYMENT);
      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_EMPLOYMENT+"/#",EMPLOYMENTID);

      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_GURANTORS,GURANTORS);
      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_GURANTORS+"/#",GURANTORSID);

      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_LOAN,LOAN);
      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_LOAN+"/#",LOANID);

      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_LOANAPPLICATION,LOANAPPLICATION);
      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_LOANAPPLICATION+"/#",LOANAPPLICATIONID);

      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_PROPERTY,PROPERTY);
      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_PROPERTY+"/#",PROPERTYID);

      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_OTHEROCCUPATION,OTHEROCCUPATION);
      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_OTHEROCCUPATION+"/#",OTHEROCCUPATIONID);


      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_LOAN_CLUSTER,LOANCLUSTER);
      matcher.addURI(DBContract.CONTENT_AUTHORITY,DBContract.PATH_LOAN_CLUSTER+"/#",LOANCLUSTERID);
  }

    
    @Override
    public boolean onCreate() {

        sdb=new SqlDBHandler(getContext());
        db=sdb.getWritableDatabase();
        return db!=null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {


        int match = matcher.match(uri);
        long rowinserted;
        Uri _uri = null;
        switch (match) {

            case PROFILE:
                rowinserted=db.insert(DBContract.Profile.PROFILE_TABLE,null,values);
                _uri= ContentUris.withAppendedId(DBContract.Profile.CONTENT_URI, rowinserted);
                getContext().getContentResolver().notifyChange(_uri,null);

                return _uri;

            case BUSINESS:
               rowinserted=db.insert(DBContract.Business.BUSINESS_TABLE,null,values);
                _uri= ContentUris.withAppendedId(DBContract.Business.CONTENT_URI, rowinserted);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;

           case BUSINESSTYPE:
               rowinserted=db.insert(DBContract.BusinessType.BUSINESSTYPE_TABLE,null,values);
               _uri= ContentUris.withAppendedId(DBContract.BusinessType.CONTENT_URI, rowinserted);
               getContext().getContentResolver().notifyChange(_uri, null);
               return _uri;

            case EMPLOYMENT:
                rowinserted=db.insert(DBContract.Employment.EMPLOYMENT_TABLE,null,values);
                _uri= ContentUris.withAppendedId(DBContract.Employment.CONTENT_URI, rowinserted);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;


            case LOAN:

                rowinserted=db.insert(DBContract.Loans.LOAN_TABLE,null,values);
                _uri= ContentUris.withAppendedId(DBContract.Loans.CONTENT_URI, rowinserted);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;


            case LOANAPPLICATION:

                rowinserted=db.insert(DBContract.LoanApplication.LOANAPPLICATION_TABLE,null,values);
                _uri= ContentUris.withAppendedId(DBContract.LoanApplication.CONTENT_URI, rowinserted);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;

             case PROPERTY:
                 rowinserted=db.insert(DBContract.Property.PROPERTY_TABLE,null,values);
                _uri= ContentUris.withAppendedId(DBContract.Property.CONTENT_URI, rowinserted);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;

            case GURANTORS:

                rowinserted=db.insert(DBContract.Gurantors.GURANTORS_TABLE,null,values);
                _uri= ContentUris.withAppendedId(DBContract.Gurantors.CONTENT_URI, rowinserted);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;


            case OTHEROCCUPATION:

                rowinserted=db.insert(DBContract.OtherOccupations.OTHEROCCUPATION_TABLE,null,values);
                _uri= ContentUris.withAppendedId(DBContract.OtherOccupations.CONTENT_URI, rowinserted);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;

            case LOANCLUSTER:

                rowinserted=db.insert(DBContract.LoanCluster.LOAN_CLUSTER_TABLE,null,values);
                _uri= ContentUris.withAppendedId(DBContract.LoanCluster.CONTENT_URI, rowinserted);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;


            default:
                throw new IllegalArgumentException("Uri not supported: " + uri);


        }

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();

        Cursor cursor = null;
        int match = matcher.match(uri);
        switch (match) {

            case PROFILE:
                sqlBuilder.setTables(DBContract.Profile.PROFILE_TABLE);
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                return cursor;
            case PROFILEID:

                sqlBuilder.setTables(DBContract.Profile.PROFILE_TABLE);
                sqlBuilder.appendWhere(DBContract.Profile._ID + " = " + uri.getPathSegments().get(1));
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);


                return cursor;
            case BUSINESS:
                sqlBuilder.setTables(DBContract.Business.BUSINESS_TABLE);
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;

            case BUSINESSID:

                sqlBuilder.setTables(DBContract.Business.BUSINESS_TABLE);
                sqlBuilder.appendWhere(DBContract.Business._ID + " = " + uri.getPathSegments().get(1));
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);


                return cursor;


            case BUSINESSTYPE:
                sqlBuilder.setTables(DBContract.BusinessType.BUSINESSTYPE_TABLE);
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                break;

            case BUSINESSTYPEID:

                sqlBuilder.setTables(DBContract.BusinessType.BUSINESSTYPE_TABLE);
                sqlBuilder.appendWhere(DBContract.BusinessType._ID + " = " + uri.getPathSegments().get(1));
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);


                return cursor;

            case EMPLOYMENT:
                sqlBuilder.setTables(DBContract.Employment.EMPLOYMENT_TABLE);
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                return cursor;

            case EMPLOYMENTID:

                sqlBuilder.setTables(DBContract.Employment.EMPLOYMENT_TABLE);
                sqlBuilder.appendWhere(DBContract.Employment._ID + " = " + uri.getPathSegments().get(1));
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);


                return cursor;

            case LOAN:
                sqlBuilder.setTables(DBContract.Loans.LOAN_TABLE);
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                return cursor;

            case LOANID:

                sqlBuilder.setTables(DBContract.Loans.LOAN_TABLE);
                sqlBuilder.appendWhere(DBContract.Loans._ID + " = " + uri.getPathSegments().get(1));
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);


                return cursor;


            case LOANAPPLICATION:
                sqlBuilder.setTables(DBContract.LoanApplication.LOANAPPLICATION_TABLE);
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                return cursor;

            case LOANAPPLICATIONID:

                sqlBuilder.setTables(DBContract.LoanApplication.LOANAPPLICATION_TABLE);
                sqlBuilder.appendWhere(DBContract.LoanApplication._ID + " = " + uri.getPathSegments().get(1));
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);


                return cursor;

            case PROPERTY:
                sqlBuilder.setTables(DBContract.Property.PROPERTY_TABLE);
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                return cursor;

            case PROPERTYID:

                sqlBuilder.setTables(DBContract.Property.PROPERTY_TABLE);
                sqlBuilder.appendWhere(DBContract.Property._ID + " = " + uri.getPathSegments().get(1));
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);


                return cursor;


            case GURANTORS:
                sqlBuilder.setTables(DBContract.Gurantors.GURANTORS_TABLE);
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                return cursor;

            case GURANTORSID:

                sqlBuilder.setTables(DBContract.Gurantors.GURANTORS_TABLE);
                sqlBuilder.appendWhere(DBContract.Gurantors._ID + " = " + uri.getPathSegments().get(1));
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);


                return cursor;
            case OTHEROCCUPATION:
                sqlBuilder.setTables(DBContract.OtherOccupations.OTHEROCCUPATION_TABLE);
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                return cursor;

            case OTHEROCCUPATIONID:

                sqlBuilder.setTables(DBContract.OtherOccupations.OTHEROCCUPATION_TABLE);
                sqlBuilder.appendWhere(DBContract.OtherOccupations._ID + " = " + uri.getPathSegments().get(1));
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);


                return cursor;

            case LOANCLUSTER:
                sqlBuilder.setTables(DBContract.LoanCluster.LOAN_CLUSTER_TABLE);
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                return cursor;

            case LOANCLUSTERID:

                sqlBuilder.setTables(DBContract.LoanCluster.LOAN_CLUSTER_TABLE);
                sqlBuilder.appendWhere(DBContract.LoanCluster._ID + " = " + uri.getPathSegments().get(1));
                cursor = sqlBuilder.query(db, projection, selection, selectionArgs, sortOrder, null, null, null);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);


                return cursor;


            default:
                throw new IllegalArgumentException("Uri not supported: " + uri);


        }
        return null;
    }





    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
