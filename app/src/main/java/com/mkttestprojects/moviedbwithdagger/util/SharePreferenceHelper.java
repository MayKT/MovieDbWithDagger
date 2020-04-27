package com.mkttestprojects.moviedbwithdagger.util;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharePreferenceHelper {

	private SharedPreferences sharedPreference;

	private static String SHARE_PREFRENCE = "showtimePref";

	private static String SESSION_ID_KEY = "sessionId";

	private static String USER_NAME_KEY = "username";

	private static String USER_ID_KEY="id";

	private static String MEMBER_ID_KEY = "member_id";
	private static String MEMBER_FIREBASE_TOKEN_KEY = "member_firebase_token";
	private static String MEMBER_ACCOUNT_KIT_APP_ID_KEY = "member_account_kit_app_id";
	private static String MEMBER_PHONE_KEY = "member_phone";
	private static String MEMBER_BIRTHDAY = "member_birthday";
	private static String MEMBER_GENDER = "member_gender";
	private static String MEMBER_NAME = "member_name";
	private static String MEMBER_EMAIL = "member_email";
	private static String MEMBER_ADDRESS = "member_address";
	private static String MEMBER_PHOTO = "member_photo";

	private static String MEMBER_FB_ID = "fbid";
	private static String MEMBER_GMAIL_ID = "gmailid";

	private static String USER_FB_NAME = "fb_name";
	private static String USER_EMAIL= "email";

	public void  setFBLogin(String fbname,String  email){

		SharedPreferences.Editor editor = sharedPreference.edit();
		editor.putString(USER_FB_NAME, fbname);
		editor.putString(USER_EMAIL, email);
		editor.commit();
	}

	public String getUserFbName(){
		return sharedPreference.getString(USER_FB_NAME,"");

	}
	public String getUserEmail(){
		return sharedPreference.getString(USER_EMAIL,"");
	}

	private String name;

	@Inject
	public SharePreferenceHelper(Context context)
	{
		sharedPreference = context.getSharedPreferences(SHARE_PREFRENCE, Context.MODE_PRIVATE);
	}


	public void logoutSharePreference()
	{
		SharedPreferences.Editor editor = sharedPreference.edit();
		editor.clear();
		editor.commit();
	}

	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}



	public void setLogin(String sessionId)
	{
		SharedPreferences.Editor editor = sharedPreference.edit();
		editor.putString(SESSION_ID_KEY, sessionId);
		editor.commit();
	}

	public void setUserId_Name(String name,int id){
		SharedPreferences.Editor editor=sharedPreference.edit();
		editor.putString(USER_NAME_KEY,name);
		editor.putInt(USER_ID_KEY,id);
		editor.commit();
	}

	public void setUserName(String username){
		SharedPreferences.Editor editor=sharedPreference.edit();
		editor.putString(USER_NAME_KEY,username);
		editor.apply();
		editor.commit();
	}

	public String getSessionId() {
		return sharedPreference.getString(SESSION_ID_KEY, "");
	}

	public int getUserIdKey(){return sharedPreference.getInt(USER_ID_KEY,1);}

	public String getUserName() {
		return sharedPreference.getString(USER_NAME_KEY,"DefaultName");
	}

	public boolean isLogin()
	{
		if(sharedPreference.contains(SESSION_ID_KEY))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
