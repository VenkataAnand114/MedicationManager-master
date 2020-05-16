package com.example.theme.medmanager01.helpers.sql;

import android.provider.BaseColumns;


public class UserContract {

    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_USER_PHONENUMBER = "user_phonenumber";
        public static final String COLUMN_USER_PASSWORD = "user_password";
    }
}
