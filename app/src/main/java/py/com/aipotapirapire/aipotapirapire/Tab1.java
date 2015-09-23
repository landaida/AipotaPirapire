package py.com.aipotapirapire.aipotapirapire;

/**
 * Created by Ariel on 29/08/2015.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import py.com.aipotapirapire.aipotapirapire.model.User;
import py.com.aipotapirapire.aipotapirapire.util.HttpUtility;
import py.com.aipotapirapire.aipotapirapire.util.JsonUtils;
import py.com.aipotapirapire.aipotapirapire.util.LogUtil;


public class Tab1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_1, container, false);
        return v;
    }


}
