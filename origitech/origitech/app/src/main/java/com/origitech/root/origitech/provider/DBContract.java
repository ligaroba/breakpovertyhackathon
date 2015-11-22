package com.origitech.root.origitech.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by root on 9/30/15.
 */
public class DBContract{

    public static final String PATH_PROFILE = "tbl_profile";
    public static final String PATH_VERIFICATION = "tbl_verification";
    public static final String PATH_REPORT = "tbl_report";
    private static final String PATH_BACKUP ="tbl_backup" ;
    private static final String PATH_BLACKLIST ="tbl_blacklist" ;





    public static final String DATABASE_NAME="Origitech";
    public static final int DATABASE_VERSION=1;

    public static final String CONTENT_AUTHORITY="com.origitech.root.origitech.Origitech";
    public static final Uri BASE_URI=Uri.parse("content://" + CONTENT_AUTHORITY);


    public static class Profile implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_PROFILE;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_PROFILE;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_PROFILE).build();
        public static final String PROFILE_TABLE=PATH_PROFILE;

        public static final String KEY_FIRSTNAME="firstname";
        public static final String KEY_SECONDNAME="secondname";
        public static final String KEY_AVATAR="avatar";
        public static final String COLUMN_LOC_KEY="id_user";

        public static final String KEY_EMAIL = "email";
        public static final String KEY_MOBILE ="phone" ;
    }

    public static class Verification implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_VERIFICATION;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_VERIFICATION;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_VERIFICATION).build();
        public static final String VERIFY_TABLE=PATH_VERIFICATION;

        public static final String KEY_VERIFICATION_CODE="verificationcode";
        public static final String KEY_PRODUCT_NAME="productname";
        public static final String KEY_PRODUCT_TYPE="producttype";
        public static final String KEY_PRODUCT_MODEL="productmodel";
        public static final String KEY_PRODUCT_OWNER="productowner";
        public static final String KEY_PRODUCT_OWNER_ID="clientid";
        public static final String KEY_PRODUCT_SERIAL="productserial";
        public static final String KEY_AVATAR="avatar";
        public static final String COLUMN_LOC_KEY="id_verify";
        public static final String KEY_TIME_ADDED="timereported";

    }
    public static class Report implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_REPORT;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_REPORT;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_REPORT).build();
        public static final String REPORT_TABLE=PATH_REPORT;
        public static final String KEY_PRODUCT_NAME="productname";
        public static final String KEY_PRODUCT_MODEL="productmodel";
        public static final String KEY_LOCATION="location";
        public static final String KEY_DEALERNAME="dealername";
        public static final String KEY_PRODUCT_SHOP="shopname";
        public static final String KEY_TIME_ADDED="timereported";
        public static final String KEY_STATUS = "status";
        public static final String KEY_CASESTATUS ="casesatatus" ;
    }

    public static class Backup implements BaseColumns {

        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_BACKUP;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_BACKUP;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_BACKUP).build();

        public static final String BACKUP_TABLE=PATH_BACKUP;
        public static final String KEY_TYPE="backtype";
        public static final String KEY_AVATAR="avatar";
        public static final String KEY_TIME_ADDED="timebackuped";
        public static final String KEY_STATUS = "status";

    }



    public static class Blacklist implements BaseColumns{
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd."+ CONTENT_AUTHORITY + "/" + PATH_BLACKLIST;
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd."+ CONTENT_AUTHORITY + "/" + PATH_BLACKLIST;
        public static final Uri CONTENT_URI=BASE_URI.buildUpon().appendPath(PATH_BLACKLIST).build();
        public static final String BLACKLIST_TABLE=PATH_BLACKLIST;
        public static final String KEY_PRODUCT_NAME="productname";
        public static final String KEY_PRODUCT_MODEL="productmodel";
        public static final String KEY_LOCATION="location";
        public static final String KEY_DEALERNAME="dealername";
        public static final String KEY_PRODUCT_SHOP="shopname";
        public static final String KEY_TIME_ADDED="timereported";
        public static final String KEY_STATUS = "status";
        public static final String KEY_CASESTATUS ="casesatatus" ;
    }
}
