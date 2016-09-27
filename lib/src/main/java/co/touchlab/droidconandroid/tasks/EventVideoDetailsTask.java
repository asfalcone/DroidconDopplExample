package co.touchlab.droidconandroid.tasks;
import android.content.Context;

import org.apache.commons.lang3.StringUtils;

import co.touchlab.android.threading.eventbus.EventBusExt;
import co.touchlab.android.threading.tasks.Task;
import co.touchlab.droidconandroid.CrashReport;
import co.touchlab.droidconandroid.network.DataHelper;
import co.touchlab.droidconandroid.network.RefreshScheduleDataRequest;
import co.touchlab.droidconandroid.network.dao.EventVideoDetails;
import co.touchlab.droidconandroid.presenter.AppManager;
import retrofit.RestAdapter;

/**
 * Created by kgalligan on 9/14/16.
 */
public class EventVideoDetailsTask extends Task
{
    private final long eventId;
    private EventVideoDetails eventVideoDetails;

    public EventVideoDetailsTask(long eventId)
    {
        this.eventId = eventId;
    }

    public long getEventId()
    {
        return eventId;
    }

    public EventVideoDetails getEventVideoDetails()
    {
        return eventVideoDetails;
    }

    @Override
    protected void onComplete(Context context)
    {
        EventBusExt.getDefault().post(this);
    }

    @Override
    protected void run(Context context) throws Throwable
    {
        RestAdapter restAdapter = DataHelper.makeRequestAdapter(context,
                AppManager.getPlatformClient());
        RefreshScheduleDataRequest refreshScheduleDataRequest = restAdapter.create(
                RefreshScheduleDataRequest.class);
        eventVideoDetails = refreshScheduleDataRequest.getEventVideoDetails(eventId);
    }

    @Override
    protected boolean handleError(Context context, Throwable throwable)
    {
        CrashReport.logException(throwable);
        return true;
    }

    public boolean hasStream()
    {
        return eventVideoDetails != null && (StringUtils.isNotEmpty(eventVideoDetails.getStreamLink()) || StringUtils.isNotEmpty(eventVideoDetails.getStreamArchiveLink()));
    }

    public boolean isNow()
    {
        return eventVideoDetails != null && StringUtils.isNotEmpty(eventVideoDetails.getStreamLink());
    }

    public String getMergedStreamLink()
    {
        if(eventVideoDetails == null)
            return null;

        return StringUtils.isNotEmpty(eventVideoDetails.getStreamLink()) ? eventVideoDetails.getStreamLink() : eventVideoDetails.getStreamArchiveLink();
    }
}
