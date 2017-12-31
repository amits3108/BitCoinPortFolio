package com.firstproject.amit.bitcoinportfolio.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.firstproject.amit.bitcoinportfolio.model.InvestmentModel;

import java.util.ArrayList;


public class DbManager {
    private DbOpenHelper sqliteHelperImage;

    private SQLiteDatabase sqliteDataBase = null;


    public DbManager(Context mContext) {
        sqliteHelperImage = new DbOpenHelper(mContext);
    }


    public boolean isDbOpen() {
        if (sqliteDataBase != null && sqliteDataBase.isOpen())
            return true;
        else
            return false;
    }

    public void open() throws SQLException {
        try {
            try {
                sqliteDataBase = sqliteHelperImage.getWritableDatabase();
            } catch (Exception e) {
                sqliteDataBase = sqliteHelperImage.getReadableDatabase();
            }
        } catch (Exception e) {


            e.printStackTrace();
        }
    }

    public void close() {
        try {
            sqliteHelperImage.close();
        } catch (Exception e) {


            e.printStackTrace();
        }
    }

    public void clearAllDb() {
        try {
            sqliteDataBase.delete(DbOpenHelper.TABLE_NAME_INVESTMENTS_DETAILS, null, null);
        } catch (Exception e) {
            System.out.print("DELETE DROP ANOMALY");
            e.printStackTrace();
        }
    }

    public long saveInvestmentsDetails(InvestmentModel investmentModel) {
        ContentValues values = new ContentValues();
        long insertId = 0;

        values.put(DbOpenHelper.COLUMN_INVESTMENT_AMOUNT, investmentModel.getAmount());
        values.put(DbOpenHelper.COLUMN_INVESTMENT_RATE, investmentModel.getRate());
        values.put(DbOpenHelper.COLUMN_INVESTMENT_TOTAL_PRICE, investmentModel.getTotalPrice());
        values.put(DbOpenHelper.COLUMN_INVESTMENT_IS_BUY, investmentModel.isBuy() ? 1 : 0);
        values.put(DbOpenHelper.COLUMN_INVESTMENT_TIMESTAMP, investmentModel.getTimeStamp());

        insertId = sqliteDataBase.insert(DbOpenHelper.TABLE_NAME_INVESTMENTS_DETAILS, null, values);

        return insertId;
    }

    public ArrayList<InvestmentModel> getInvestMentList() {
        ArrayList<InvestmentModel> investmentModelArrayList = new ArrayList<>();
        Cursor cursor = sqliteDataBase.query(DbOpenHelper.TABLE_NAME_INVESTMENTS_DETAILS, null, null, null, null, null, DbOpenHelper.COLUMN_INVESTMENT_TIMESTAMP + " desc ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            InvestmentModel investmentModel = evaluateInvestMentModel(cursor);
            investmentModelArrayList.add(investmentModel);
            cursor.moveToNext();
        }
        cursor.close();
        return investmentModelArrayList;
    }

    public InvestmentModel evaluateInvestMentModel(Cursor cursor) {
        InvestmentModel investmentModel = new InvestmentModel();
        try {
//            cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_INVESTMENT_ID));
            investmentModel.setAmount(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.COLUMN_INVESTMENT_AMOUNT)));
            investmentModel.setRate(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.COLUMN_INVESTMENT_RATE)));
            investmentModel.setTotalPrice(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.COLUMN_INVESTMENT_TOTAL_PRICE)));
            investmentModel.setBuy(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.COLUMN_INVESTMENT_IS_BUY)) != 0);
            investmentModel.setTimeStamp(cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_INVESTMENT_TIMESTAMP)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return investmentModel;
    }

    /////////////////////// NOTIFICATION TABLE QUERIES  ///////////////////////////////
    /*public long saveOrUpdateNotification(NotificationModel notificationModel) {
        ContentValues values = new ContentValues();
        long insertId = 0;
        try {
            values.put(DbOpenHelper.COLUMN_NOTIFICATION_TEMPLATE_NAME, notificationModel.getTemplateName());
            values.put(DbOpenHelper.COLUMN_NOTIFICATION_DESCRIPTION, notificationModel.getDesc());
            values.put(DbOpenHelper.COLUMN_NOTIFICATION_KPI_NAME, notificationModel.getKpiName());
            values.put(DbOpenHelper.COLUMN_NOTIFICATION_PUSH_TYPE, notificationModel.getPushType());
            values.put(DbOpenHelper.COLUMN_NOTIFICATION_TIMESTAMP, notificationModel.getTimeStamp());
            values.put(DbOpenHelper.COLUMN_NOTIFICATION_SERVER_URL, notificationModel.getServerUrl());
            values.put(DbOpenHelper.COLUMN_NOTIFICATION_DELETE, notificationModel.isDelete() ? 1 : 0);
            values.put(DbOpenHelper.COLUMN_NOTIFICATION_READ, notificationModel.isRead() ? 1 : 0);

            if (!hasNotificationExists(notificationModel.getTimeStamp(), notificationModel.getServerUrl())) {
                insertId = sqliteDataBase.insert(DbOpenHelper.TABLE_NAME_NOTIFICATION, null, values);
            } else {
                String whereClause = DbOpenHelper.COLUMN_NOTIFICATION_TIMESTAMP + "=" + notificationModel.getTimeStamp() + " AND " + DbOpenHelper.COLUMN_NOTIFICATION_SERVER_URL + " like '%" + notificationModel.getServerUrl() + "%'";
                insertId = sqliteDataBase.update(DbOpenHelper.TABLE_NAME_NOTIFICATION, values, whereClause, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertId;
    }

    public boolean hasNotificationExists(String timeStamp, String serverUrl) {
        boolean hasNotificationExists = false;
        try {
            String whereClause = "";
            if (timeStamp != null) {
                whereClause = DbOpenHelper.COLUMN_NOTIFICATION_TIMESTAMP + "=?" + " AND " + DbOpenHelper.COLUMN_NOTIFICATION_SERVER_URL + " like '%" + serverUrl + "%'";
            }
            Cursor cursor = sqliteDataBase.query(DbOpenHelper.TABLE_NAME_NOTIFICATION, null, whereClause, new String[]{timeStamp}, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                hasNotificationExists = true;
//                Log.e("NOTIFICATION_TIMESTAMP", cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_NOTIFICATION_TIMESTAMP)));
//                Log.e("NOTIFICATION_DELETE", cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_NOTIFICATION_DELETE)));
//                Log.e("NOTIFICATION_READ", cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_NOTIFICATION_READ)));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNotificationExists;
    }

    public ArrayList<NotificationModel> getNotifications(String timeStamp, boolean isShowUnreadOnly, String serverUrl) {
        //NOTE: Pass the timeStamp null for get All notifications. Otherwise seletected timeStamp notifications is fetched from the DB.
        ArrayList<NotificationModel> notificatonList = new ArrayList<>();
        String whereClause = "";
        if (timeStamp != null) {
            whereClause = DbOpenHelper.COLUMN_NOTIFICATION_TIMESTAMP + "=" + timeStamp + " AND ";
        }

        if (serverUrl != null) {
            whereClause = whereClause + DbOpenHelper.COLUMN_NOTIFICATION_SERVER_URL + " like '%" + serverUrl + "%'";
        }

        *//*if (timeStamp != null && isShowUnreadOnly) {
            whereClause = whereClause + " AND ";
        }*//*

        if (isShowUnreadOnly) {
            whereClause = whereClause + " AND " + DbOpenHelper.COLUMN_NOTIFICATION_READ + "=0";
        }

        Cursor cursor = sqliteDataBase.query(DbOpenHelper.TABLE_NAME_NOTIFICATION, null, whereClause, null, null, null, DbOpenHelper.COLUMN_NOTIFICATION_TIMESTAMP + " desc ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NotificationModel notificationModel = evaluateNotificationModel(cursor);
            notificatonList.add(notificationModel);
            cursor.moveToNext();
        }
        cursor.close();
        return notificatonList;
    }

    private NotificationModel evaluateNotificationModel(Cursor cursor) {
        NotificationModel notificationModel = new NotificationModel();
        try {
//            cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_NOTIFICATION_ID));
            notificationModel.setTemplateName(cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_NOTIFICATION_TEMPLATE_NAME)));
            notificationModel.setDesc(cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_NOTIFICATION_DESCRIPTION)));
            notificationModel.setKpiName(cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_NOTIFICATION_KPI_NAME)));
            notificationModel.setPushType(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.COLUMN_NOTIFICATION_PUSH_TYPE)));
            notificationModel.setTimeStamp(cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_NOTIFICATION_TIMESTAMP)));
            notificationModel.setServerUrl(cursor.getString(cursor.getColumnIndex(DbOpenHelper.COLUMN_NOTIFICATION_SERVER_URL)));
            notificationModel.setDelete(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.COLUMN_NOTIFICATION_DELETE)) != 0);
            notificationModel.setRead(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.COLUMN_NOTIFICATION_READ)) != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notificationModel;
    }

    public int deleteNotification(NotificationModel notificationModel) {
        //NOTE: Pass the null in timeStamp all the notifications will be deleted from the DB. Otherwise Selected timeStamp is deleted.
        String whereClause = null;
        if (notificationModel.getTimeStamp() != null) {
            whereClause = DbOpenHelper.COLUMN_NOTIFICATION_TIMESTAMP + "=" + notificationModel.getTimeStamp() + " AND " + DbOpenHelper.COLUMN_NOTIFICATION_SERVER_URL + " like '%" + notificationModel.getServerUrl() + "%'";
        }

        int rowsAffected = sqliteDataBase.delete(DbOpenHelper.TABLE_NAME_NOTIFICATION, whereClause, null);
        Log.e("Rows deleted ", rowsAffected + "");
        return rowsAffected;
    }
*/
    ///////////////////////////////////////////////////////////////////////////////////////////////


}
