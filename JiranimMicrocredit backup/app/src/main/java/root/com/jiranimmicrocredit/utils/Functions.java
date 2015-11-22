package root.com.jiranimmicrocredit.utils;

import android.content.ContentValues;
import android.widget.EditText;

/**
 * Created by root on 11/1/15.
 */
public class Functions {


    public ContentValues getLoans(){

        ContentValues values=new ContentValues();
        values.put(Constants.ACTION,Constants.ACTION_GET_ALL);
        values.put(Constants.USERACTION,Constants.USERACTION_LOANS);
        return values;

    }


    public ContentValues getUserLoans(int id){

        ContentValues values=new ContentValues();
        values.put(Constants.ACTION,Constants.ACTION_QUERY);
        values.put(Constants.USERACTION,Constants.USERACTION_LOANCATEGORY);
        values.put(Constants.PARAM_ID_APPLICANT,id);
        return values;

    }
    public ContentValues getMyLoans(int id){

        ContentValues values=new ContentValues();
        values.put(Constants.ACTION,Constants.ACTION_QUERY);
        values.put(Constants.USERACTION,Constants.USERACTION_LOANAPPLICATION);
        values.put(Constants.PARAM_ID_APPLICANT,id);
        return values;

    }

    public ContentValues profile(int id,String fname,String lname,String sname,String phone,String email,String idnumber,String password,String avatar){
        ContentValues values=new ContentValues();
        values.put(Constants.ACTION,Constants.ACTION_USERS);

        if(id>0){
            values.put(Constants.USERACTION,Constants.USERACTION_UPDATE);
            values.put(Constants.PARAM_ID_APPLICANT,id);
        }else{
            values.put(Constants.USERACTION,Constants.USERACTION_INSERT);
        }

        values.put(Constants.PARAM_FIRSTNAME,fname);
        values.put(Constants.PARAM_SECONDNAME,lname);
        values.put(Constants.PARAM_LASTNAME,sname);
        values.put(Constants.PARAM_PHONE,phone);
        values.put(Constants.PARAM_EMAIL,email);
        values.put(Constants.PARAM_IDNUMBER,idnumber);
        values.put(Constants.PARAM_PASSWORD,password);
        values.put(Constants.PARAM_AVATAR,avatar);
         return values;
    }



    public ContentValues login(String username,String password){
        ContentValues values=new ContentValues();
        values.put(Constants.ACTION,Constants.ACTION_QUERY_USERS);
        values.put(Constants.USERACTION,Constants.USERACTION_LOGINWITHID);
        values.put(Constants.PARAM_USERNAME,username);
        values.put(Constants.PARAM_PASSWORD,password);     ;
        return values;
    }


    public ContentValues business(int id,int idapplicant,String businessname,String id_businesstype,String turnover,String locationbusiness,String yearofBusiness,String income,String avatar){
        ContentValues values=new ContentValues();
        values.put(Constants.ACTION,Constants.ACTION_BUSINESS);
        if(id>0){
            values.put(Constants.USERACTION,Constants.USERACTION_UPDATE);
            values.put(Constants.PARAM_ID_BUSINESS,id);
        }else {
            values.put(Constants.USERACTION,Constants.USERACTION_INSERT);
        }


        values.put(Constants.PARAM_ID_APPLICANT,idapplicant);
        values.put(Constants.PARAM_BUSINESS_NAME,businessname);
        values.put(Constants.PARAM_ID_BUSINES_TYPE,id_businesstype);
        values.put(Constants.PARAM_TURNOVER,turnover);
        values.put(Constants.PARAM_LOCATION,locationbusiness);
        values.put(Constants.PARAM_YEAR_OF_BUSINESS,yearofBusiness);
        values.put(Constants.PARAM_INCOME,income);
        values.put(Constants.PARAM_AVATAR,avatar);
        return values;
    }



    public ContentValues employment(int id,int idapplicant,String employername,String department,String location,String termsOfemployment,String dateofEmployement,String income,String avatar){
        ContentValues values=new ContentValues();
        values.put(Constants.ACTION,Constants.ACTION_EMPLOYMENT);
        if(id>0){
            values.put(Constants.USERACTION,Constants.USERACTION_UPDATE);
            values.put(Constants.PARAM_ID_EMPLOYMENT,id);

        }else {
            values.put(Constants.USERACTION,Constants.USERACTION_INSERT);
        }

        values.put(Constants.PARAM_ID_APPLICANT,idapplicant);
        values.put(Constants.PARAM_EMPLOYER_NAME,employername);
        values.put(Constants.PARAM_LOCATION,location);
        values.put(Constants.PARAM_DEPARTMENT,department);
        values.put(Constants.PARAM_TERMS_OF_EMPLOYMENT,termsOfemployment);
        values.put(Constants.PARAM_DATE_OF_EMPLOYMENT,dateofEmployement);
        values.put(Constants.PARAM_INCOME,income);
        values.put(Constants.PARAM_AVATAR,avatar);
        return values;


    }

    public ContentValues loanApplication(int id_loanapplication,int idloan,int id_applicant,int loanAmount,String repaymentdate,String purpose){
        ContentValues values=new ContentValues();
        values.put(Constants.ACTION,Constants.ACTION_LOANAPPLICATION);
        if(id_loanapplication>0){
            values.put(Constants.USERACTION,Constants.USERACTION_UPDATE);
            values.put(Constants.PARAM_ID_LOANAPPLICATION,id_loanapplication);
        }else {
            values.put(Constants.USERACTION,Constants.USERACTION_INSERT);
        }
        values.put(Constants.PARAM_ID_LOAN,idloan);
        values.put(Constants.PARAM_LOANAMOUNT,loanAmount);
        values.put(Constants.PARAM_LOAN_REPAYMENTDATE,repaymentdate);
        values.put(Constants.PARAM_LOAN_PURPOSE,purpose);
        values.put(Constants.PARAM_ID_APPLICANT,id_applicant);
        return values;


    }



    public ContentValues gurantors(int id,String fname,String lname,String sname,String phone,String email,String idnumber,String idapplicant,String residence,String avatar){
        ContentValues values=new ContentValues();
        values.put(Constants.ACTION,Constants.ACTION_USERS);

        if(id>0){
            values.put(Constants.USERACTION,Constants.USERACTION_UPDATE);
            values.put(Constants.PARAM_ID_GURANTORS,id);
        }else{
            values.put(Constants.USERACTION,Constants.USERACTION_INSERT);
        }

        values.put(Constants.PARAM_FIRSTNAME,fname);
        values.put(Constants.PARAM_SECONDNAME,lname);
        values.put(Constants.PARAM_LASTNAME,sname);
        values.put(Constants.PARAM_PHONE,phone);
        values.put(Constants.PARAM_EMAIL,email);
        values.put(Constants.PARAM_IDNUMBER,idnumber);
        values.put(Constants.PARAM_ID_APPLICANT,idapplicant);
        values.put(Constants.PARAM_RESIDENCE,residence);
        values.put(Constants.PARAM_AVATAR,avatar);
        return values;
    }

    public ContentValues property(int id,String location,String propertyname,String plotnumber,String specifyOwnership,String occupancy,String income,String idapplicant,String avatar){
        ContentValues values=new ContentValues();
        values.put(Constants.ACTION,Constants.ACTION_PROPERTY);

        if(id>0){
            values.put(Constants.USERACTION,Constants.USERACTION_UPDATE);
            values.put(Constants.PARAM_ID_PROPERTY,id);
        }else{
            values.put(Constants.USERACTION,Constants.USERACTION_INSERT);
        }
        values.put(Constants.PARAM_PROPERTYNAME,propertyname);
        values.put(Constants.PARAM_LOCATION,location);
        values.put(Constants.PARAM_PLOTNUMBER,plotnumber);
        values.put(Constants.PARAM_SPECIFYOWNERSHIP,specifyOwnership);
        values.put(Constants.PARAM_OCCUPANCY,occupancy);
        values.put(Constants.PARAM_ID_APPLICANT,idapplicant);
        values.put(Constants.PARAM_INCOME,income);
        values.put(Constants.PARAM_AVATAR,avatar);
        return values;
    }


    public ContentValues otherOcupation(int id,String occupationname,String income,String otherDescription,String idapplicant,String avatar){
        ContentValues values=new ContentValues();
        values.put(Constants.ACTION,Constants.ACTION_USERS);

        if(id>0){
            values.put(Constants.USERACTION,Constants.USERACTION_UPDATE);
            values.put(Constants.PARAM_ID_OTHEROCUPATION,id);
        }else{
            values.put(Constants.USERACTION,Constants.USERACTION_INSERT);
        }

        values.put(Constants.PARAM_OCCUPATIONAME,occupationname);
        values.put(Constants.PARAM_ID_APPLICANT,idapplicant);
        values.put(Constants.PARAM_INCOME,income);
        values.put(Constants.PARAM_OTHER_DESCRIPTION,otherDescription);
        values.put(Constants.PARAM_AVATAR,avatar);
        return values;
    }



    public boolean checkNullity(String value){
        if (value ==null || value.equalsIgnoreCase(" ")){


            return true;
        }else {

            return false;
        }

    }
    public String getText(EditText editText){
        return editText.getText().toString();

    }
    public void setERROR(EditText editText,String msg){
        editText.setError(msg);
    }





}
