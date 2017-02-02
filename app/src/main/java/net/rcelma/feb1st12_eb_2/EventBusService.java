package net.rcelma.feb1st12_eb_2;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusService extends IntentService {
	public EventBusService() {
		super("EventBusService");
	}

	@Override
	public void onHandleIntent(final Intent intent) {

		new Thread(){
			@Override
			public void run() {

				android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_LOWEST);
				try {
					Thread.sleep(intent.getIntExtra("tts", 0) * 1000);
					EventBus.getDefault().post(new Messenger(700, "Niki is really HOT!"));
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}.start();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
