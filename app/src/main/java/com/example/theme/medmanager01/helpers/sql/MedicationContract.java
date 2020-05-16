package com.example.theme.medmanager01.helpers.sql;

import android.provider.BaseColumns;

public class MedicationContract {

    public static final class MedicationEntry implements BaseColumns{
        public static final String TABLE_NAME = "medication";
        public static final String COLUMN_MEDICATION_NAME = "medication_name";
        public static final String COLUMN_MEDICATION_DESCRIPTION = "medication_description";
        public static final String COLUMN_MEDICATION_TIME = "medication_time";
        public static final String COLUMN_MEDICATION_STARTDATE = "medication_start_date";
        public static final String COLUMN_MEDICATION_ENDDATE = "medication_end_date";
        public static final String COLUMN_MEDICATION_PENDING_INTENT = "medication_pending_intent";

    }
}
