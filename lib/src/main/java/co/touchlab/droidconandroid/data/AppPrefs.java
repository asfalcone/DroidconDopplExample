package co.touchlab.droidconandroid.data;

import android.content.Context;
import android.content.SharedPreferences;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Created by kgalligan on 6/28/14.
 */
public class AppPrefs
{
    public static final String SEEN_WELCOME     = "seen_welcome";
    public static final String EVENTBRITE_EMAIL            = "EVENTBRITE_EMAIL";
    public static final String CONVENTION_START = "convention_start";
    public static final String CONVENTION_END  = "convention_end";
    public static final String REFRESH_TIME    = "refresh_time";
    public static final String ALLOW_NOTIFS    = "allow_notifs";
    public static final String SHOW_NOTIF_CARD = "show_notif_card";
    public static final String VIDEO_DEVICE_ID = "VIDEO_DEVICE_ID";
    public static final String SHOW_SLACK_DIALOG = "show_slack_dialog";

    private static AppPrefs instance;

    private SharedPreferences prefs;

    @NotNull
    public static synchronized AppPrefs getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new AppPrefs();
            instance.prefs = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        }

        return instance;
    }

    public boolean once(String key)
    {
        Boolean shouldOnce = prefs.getBoolean(key, true);
        setBoolean(key, false);
        return shouldOnce;
    }

    public boolean getHasSeenWelcome()
    {
        return prefs.getBoolean(SEEN_WELCOME, false);
    }

    public void setHasSeenWelcome()
    {
        setBoolean(SEEN_WELCOME, true);
    }

    public void setEventbriteEmail(String email)
    {
        if(StringUtils.isEmpty(email))
            prefs.edit().remove(EVENTBRITE_EMAIL).apply();
        else
            setString(EVENTBRITE_EMAIL, email);
    }

    public String getEventbriteEmail()
    {
        return prefs.getString(EVENTBRITE_EMAIL, null);
    }

    public void setConventionStartDate(@NotNull String startDate)
    {
        setString(CONVENTION_START, startDate);
    }

    public String getConventionStartDate()
    {
        return prefs.getString(CONVENTION_START, null);
    }

    public void setConventionEndDate(@NotNull String endDate)
    {
        setString(CONVENTION_END, endDate);
    }

    public String getVideoDeviceId()
    {
        String deviceId = prefs.getString(VIDEO_DEVICE_ID, null);
        if(deviceId == null)
        {
            deviceId = UUID.randomUUID().toString();
            setString(VIDEO_DEVICE_ID, deviceId);
        }
        return deviceId;
    }

    public String getConventionEndDate()
    {
        return prefs.getString(CONVENTION_END, null);
    }

    public void setRefreshTime(@NotNull long time)
    {
        setLong(REFRESH_TIME, time);
    }

    public long getRefreshTime()
    {
        return prefs.getLong(REFRESH_TIME, 0);
    }

    public boolean getShowSlackDialog()
    {
        return prefs.getBoolean(SHOW_SLACK_DIALOG, true);
    }

    public void setShowSlackDialog(boolean show)
    {
        setBoolean(SHOW_SLACK_DIALOG, show);
    }

    public boolean getAllowNotifications(){return prefs.getBoolean(ALLOW_NOTIFS, false);}
    public void setAllowNotifications(boolean allow){prefs.edit().putBoolean(ALLOW_NOTIFS, allow).apply();}

    public boolean getShowNotifCard(){return prefs.getBoolean(SHOW_NOTIF_CARD, true);}
    public void setShowNotifCard(boolean show){prefs.edit().putBoolean(SHOW_NOTIF_CARD, show).apply();}

    //helper methods
    private void setBoolean(String key, Boolean value)
    {
        prefs.edit().putBoolean(key, value).apply();
    }

    private void setString(String key, String value)
    {
        prefs.edit().putString(key, value).apply();
    }

    private void setLong(String key, Long value)
    {
        prefs.edit().putLong(key, value).apply();
    }
}
