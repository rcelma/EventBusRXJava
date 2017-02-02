package net.rcelma.feb1st12_eb_2;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
	private EditText et;
	private TextView tv;
	private Subscription subs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et = (EditText) findViewById(R.id.et);
		tv = (TextView) findViewById(R.id.tv);
	}

	public void onHandleEvent(View view) {

		int tts = Integer.parseInt(et.getText().toString());
		Intent intent = new Intent(this, EventBusService.class);
		intent.putExtra("tts", tts);
		startService(intent);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void fuuu(Messenger messenger) {

		if (messenger.getC() == 700) {
			tv.setText(messenger.getM());
		}
	}

	@Override
	protected void onStart() {

		super.onStart();
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onStop() {

		super.onStop();
		EventBus.getDefault().unregister(this);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void onRX(View view) {

		doRX(4);
	}

	public void doRX(final int i) {

		Observable<Integer> observable = Observable.fromCallable(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {

				Thread.sleep(i * 1000);
				//tv.setText("Fer está loco!!");
				return i;
			}
		});
		subs = observable.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Integer>() {
					@Override
					public void onCompleted() {


					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(Integer integer) {

						tv.setText("Fer está loco!! i = " + integer);
					}
				});
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		if (subs != null && !subs.isUnsubscribed()) {
			subs.unsubscribe();
		}
	}
}