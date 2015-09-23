package py.com.aipotapirapire.aipotapirapire;

/**
 * Created by Ariel on 29/08/2015.
 */


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import py.com.aipotapirapire.aipotapirapire.adapter.UserAdapter;
import py.com.aipotapirapire.aipotapirapire.dao.UserDao;
import py.com.aipotapirapire.aipotapirapire.databinding.Tab1Binding;
import py.com.aipotapirapire.aipotapirapire.model.User;
import py.com.aipotapirapire.aipotapirapire.util.HttpUtility;
import py.com.aipotapirapire.aipotapirapire.util.JsonUtils;
import py.com.aipotapirapire.aipotapirapire.util.LogUtil;


public class Tab1 extends Fragment {
    private UserAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_1, container, false);

        final Tab1Binding binding = DataBindingUtil.bind(v);
        User user;

        if (this.getActivity().getIntent().hasExtra("index")) {
            user = UserDao.lista.get(this.getActivity().getIntent().getIntExtra("index", 0));
        } else {
            user = UserDao.getRandomQuote();
        }

        binding.setUser(user);


        binding.setTotal(UserDao.getTotal());
        adapter = new UserAdapter(UserDao.lista);
        binding.recyclerView.setAdapter(adapter);
        /*RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        binding.recyclerView.addItemDecoration(itemDecoration);*/
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
/*
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
                Intent intent = new Intent(getActivity().getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });
*/
        this.getUsers();

        return v;
    }


    public void getUsers() {
        String requestURL = "getEmpresas";
        try {
            HttpUtility.sendGetRequest(requestURL);
            String[] response = HttpUtility.readMultipleLinesRespone();
            for (String line : response) {
                LogUtil.i(line);
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
