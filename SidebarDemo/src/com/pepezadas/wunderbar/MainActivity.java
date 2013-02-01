package com.pepezadas.wunderbar;

import com.pepezadas.wunderbar.R;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class MainActivity extends Activity {
	// ,OnGesturePerformedListener

	View viewSidebar, viewContent;
	final int sdkVersion = Build.VERSION.SDK_INT;
	boolean sidebarIsShown = false;

	MyGestureListener mGestureListener = new MyGestureListener();
	private GestureDetector gestureScanner;

	final static int MINIMUM_SWIPE_DIST = 50; // arbitrary swipe distance

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewSidebar = (View) findViewById(R.id.menu_list);
		viewContent = (View) findViewById(R.id.main_content);

		gestureScanner = new GestureDetector(this, mGestureListener);

		viewContent.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return gestureScanner.onTouchEvent(event);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	void showSidebar() {
		if (sdkVersion > Build.VERSION_CODES.HONEYCOMB) {
			viewContent.animate().translationXBy(viewSidebar.getWidth());
			// viewSidebar.animate().translationX(+viewSidebar.getWidth());

		} else {
			viewContent.scrollBy(-viewSidebar.getWidth(), 0);
		}
	}

	void hideSidebar() {
		if (sdkVersion > Build.VERSION_CODES.HONEYCOMB) {
			viewContent.animate().translationXBy(-viewSidebar.getWidth());

		} else {
			viewContent.scrollBy(viewSidebar.getWidth(), 0);

		}
	}

	

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
		public boolean onDown(MotionEvent event) {
			return true;
		}
		public boolean onFling(MotionEvent event1, MotionEvent event2,
				float velocityX, float velocityY) {

			int dx = (int) (event2.getX() - event1.getX());
			
			if (Math.abs(dx) > MINIMUM_SWIPE_DIST) {
				if (event2.getX() - event1.getX() > MINIMUM_SWIPE_DIST
						&& !sidebarIsShown) {
					sidebarIsShown = true;
					showSidebar();
				} else if (event2.getX() - event1.getX() < -MINIMUM_SWIPE_DIST
						&& sidebarIsShown) {
					sidebarIsShown = false;
					hideSidebar();
				}
			}
			return true;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// return gestureScanner.onTouchEvent(event);
		return true;
	}
}
