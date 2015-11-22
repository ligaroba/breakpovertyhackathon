package com.kccrtms.kccrtms.Database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by root on 10/12/15.
 */
public class DBContract {

    public static final String PATH_REGIONS = "tbl_regions";
    public static final String PATH_TERITORY = "tbl_teritoty";
    public static final String PATH_AREA = "tbl_area";
    public static final String PATH_OUTLETS = "tbl_outlets";
    public static final String PATH_USERS="tbl_users";
    public static final String PATH_RECORDS="tbl_records";
    public static final String PATH_CHANNEL = "tbl_channel";
    public static final String PATH_PRODUCT = "tbl_products";

    public static final int TYPE_WIFI=0;
    public static final int TYPE_MOBILE=1;
    public static final int TYPE_NOT_CONNECTED=2;


    public static final String DATABASE_NAME="Kccrtms";

    public static final String CONTENT_AUTHORITY="com.kccrtms.kccrtms.Kccrtms";
    public static final Uri BASE_URI=Uri.parse("content://" + CONTENT_AUTHORITY);


    public static class RegionEntry implements BaseColumns {

        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_REGIONS;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_REGIONS;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_REGIONS).build();
        public static final String REGION_TABLE=PATH_REGIONS;
        public static final String KEY_REGIONNAME="regionname";


        public static Uri getSuperById(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }






    }
    public static class TeritoryEntry implements BaseColumns{

        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_TERITORY;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_TERITORY;

        public  static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_TERITORY).build();
        public static final String TERITORY_TABLE=PATH_TERITORY;
        public static final String KEY_TERITORYNAME="teritoryname";
        public static final String  COLUMN_LOC_KEY="region_id";


        public static Uri getProductById(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }

    public static class AreaEntry implements BaseColumns{

        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_AREA;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_AREA;

        public  static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_AREA).build();
        public static final String AREA_TABLE=PATH_AREA;

        public static final String  COLUMN_LOC_KEY_REGION="region_id";
        public static final String  COLUMN_LOC_KEY_TERITORY="teritory_id";
        public static final String CAREA_NAME="area";

        public static Uri getProductById(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }
    public static class OutletEntry implements BaseColumns{

        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_OUTLETS;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_OUTLETS;
        public  static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_OUTLETS).build();
        public static final String OUTLETS_TABLE=PATH_OUTLETS;


        public static final String  COLUMN_LOC_KEY="region_id";
        public static final String  COLUMN_LOC_KEY_TERITORY="teritory_id";
        public static final String  COLUMN_LOC_KEY_AREA="area_id";
        public static final String OUTLET_NAME="outlets";
        public static final String CONTACTPERSON ="contacperson" ;
        public static final String CONTACTS ="contacts" ;
        public static final String LOCATION = "location";
        public static final String CHANNEL = "chanel";



        public static Uri getProductById(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }

    public static class RecordsEntry implements BaseColumns{

        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_RECORDS;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_RECORDS;
        public  static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_RECORDS).build();
        public static final String RECORDS_TABLE=PATH_RECORDS;



        public static final String  COLUMN_LOC_KEY="region_id";
        public static final String  COLUMN_LOC_KEY_TERITORY="teritory_id";
        public static final String  COLUMN_LOC_KEY_AREA="area_id";
        public static final String  COLUMN_LOC_KEY_OID="out_id";
        public static final String PRODUCTNAME = "productname";
        public static final String QUANTITY ="quantity" ;
        public static final String PRICE ="price" ;
        public static final String TYPE = "type";



        public static Uri getProductById(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }

    public static class UserEntry implements BaseColumns{

        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_USERS;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_USERS;
        public  static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_USERS).build();
        public static final String USERS_TABLE=PATH_USERS;
        public static final String KEY_USERNAME="username";
        public static final String KEY_FIRSTNAME="firstname";
        public static final String KEY_LASTNAME="lastname";
        public static final String KEY_EMAIL="email";
        public static final String KEY_MOBILE="mobile";
        public static final String KEY_PASSWORD="password";



        public static Uri getProductById(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }



    public static class ChannelEntry implements BaseColumns{

        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_CHANNEL;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_CHANNEL;
        public  static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_CHANNEL).build();
        public static final String CHANNEL_TABLE=PATH_CHANNEL;
        public static final String KEY_CHANNEL="channel";

        public static Uri getProductById(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }
    public static class ProductEntry implements BaseColumns{

        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_PRODUCT;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_PRODUCT;
        public  static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_PRODUCT).build();
        public static final String PRODUCT_TABLE=PATH_PRODUCT;
        public static final String KEY_PRODUCT="productname";

        public static Uri getProductById(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }
}
