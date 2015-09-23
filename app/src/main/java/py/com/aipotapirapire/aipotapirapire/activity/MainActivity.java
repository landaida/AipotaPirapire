package py.com.aipotapirapire.aipotapirapire.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.List;

import py.com.aipotapirapire.aipotapirapire.ListActivity;
import py.com.aipotapirapire.aipotapirapire.R;
import py.com.aipotapirapire.aipotapirapire.ViewPagerAdapter;
import py.com.aipotapirapire.aipotapirapire.dao.UserDao;
import py.com.aipotapirapire.aipotapirapire.databinding.Tab1Binding;
import py.com.aipotapirapire.aipotapirapire.model.User;
import py.com.aipotapirapire.aipotapirapire.util.HttpUtility;
import py.com.aipotapirapire.aipotapirapire.util.JsonUtils;
import py.com.aipotapirapire.aipotapirapire.util.LogUtil;
import py.com.aipotapirapire.aipotapirapire.util.SlidingTabLayout;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    // Declaring Your View and Variables


    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = new CharSequence[2];
    int Numboftabs = 2;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        Titles[0] = getString(R.string.tab1_title);
        Titles[1] = getString(R.string.tab2_title);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        final Tab1Binding binding = DataBindingUtil.setContentView(this, R.layout.tab_1);
        User user;

        if (this.getIntent().hasExtra("index")) {
            user = UserDao.lista.get(this.getIntent().getIntExtra("index", 0));
        } else {
            user = UserDao.getRandomQuote();
        }

        binding.setUser(user);

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = UserDao.getRandomQuote();
                binding.setUser(user);
            }
        });
        binding.listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });
        this.getUsers();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    public void getUsers() {
        String requestURL = "getEmpresas";
        try {
            HttpUtility.sendGetRequest(requestURL);
            String[] response = HttpUtility.readMultipleLinesRespone();
            for (String line : response) {
                System.out.println(line);
                try {
                    User user = JsonUtils.getObject("{\"firstName\":\"Ariel\", \"lastName\":\"Landaida\"}", User.class);
                    LogUtil.i(user.getFirstName());
                    LogUtil.i(user.getLastName());

                    List<User> users = JsonUtils.getList("[{\"firstName\":\"Ariel\", \"lastName\":\"Landaida\"}, {\"firstName\":\"Hernan\", \"lastName\":\"Duarte\"}]", User.class);
                    LogUtil.i(users.get(1).getFirstName());
                } catch (Exception e) {
                    LogUtil.e(e.getMessage());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        HttpUtility.disconnect();
    }
}
