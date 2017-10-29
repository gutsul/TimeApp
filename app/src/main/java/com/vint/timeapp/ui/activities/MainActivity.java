package com.vint.timeapp.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.vint.timeapp.R;
import com.vint.timeapp.presenter.MainPresenter;
import com.vint.timeapp.ui.fragments.AlarmClockFragment;
import com.vint.timeapp.ui.fragments.CalendarFragment;
import com.vint.timeapp.ui.fragments.StopWatchFragment;
import com.vint.timeapp.view.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView, BottomNavigationView.OnNavigationItemSelectedListener{
    private MainPresenter presenter;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter();
        presenter.bind(this);
        init();

        navigation.setOnNavigationItemSelectedListener(this);

    }

    private void init() {
        int menuId = R.id.navigation_stopwatch;
        presenter.navigateTo(menuId);
        View view = navigation.findViewById(menuId);
        view.performClick();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        presenter.navigateTo(item.getItemId());
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbind();
    }

    @Override
    public void toStopWatch() {
        Fragment fragment = StopWatchFragment.newInstance();
        replaceFragment(R.id.content, fragment, "StopWatchFragment");
    }

    @Override
    public void toAlarmClock() {
        Fragment fragment = AlarmClockFragment.newInstance();
        replaceFragment(R.id.content, fragment, "AlarmClockFragment");
    }

    @Override
    public void toCalendar() {
        Fragment fragment = CalendarFragment.newInstance();
        replaceFragment(R.id.content, fragment, "CalendarFragment");
    }

    public void replaceFragment(int container, Fragment fragment, String tag) {

        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();

        if (count >= 1){
            FragmentManager.BackStackEntry backEntry = fm.getBackStackEntryAt(count - 1);
            String backName = backEntry.getName();
            if (backName.equals(tag)) {
                // не додавати в backStack
                fm.beginTransaction()
                        .replace(container, fragment, tag)
                        .commit();
            } else {
                // додавати в backstack
                fm.beginTransaction()
                        .replace(container, fragment, tag)
                        .addToBackStack(tag)
                        .commit();
            }
        } else {
            // add to backstack
            fm.beginTransaction()
                    .replace(container, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }
    }
}
