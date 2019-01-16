package com.example.lenovossd.parentwatch.Common;

import com.example.lenovossd.parentwatch.Model.User;
import com.example.lenovossd.parentwatch.Remote.IGoogleAPI;
import com.example.lenovossd.parentwatch.Remote.RetrofitClient;

public class Common {

    public static final String Parent_information_tb1 = "ParentInformation";
    public static final String Parent_user_tb1 = "User";
    public static final String Child_user_tb1 = "User";
    public static final String user_field ="usr";
    public static final String pwd_field ="pwd";
    public static User currentUser = new User(  ) ;
    public static final String Child_information_tb1 = "ChildInformation";
    public static final String baseURL = "https://maps.googleapis.com";
    public static final int PICK_IMAGE_REQUEST =9999 ;

    public static IGoogleAPI getGooogleAPI()
    {
        return RetrofitClient.getClient( baseURL ).create(IGoogleAPI.class );

    }
}
