package com.inhand.milk.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.inhand.milk.R;
import com.inhand.milk.activity.BaseActivity;

/**
 * LoginFragment
 * Desc:登陆Fragment
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-03
 * Time: 16:56
 */
public class LoginFragment extends BackHandleFragment {
    private View rootView;
    private TextView toReg;
    private Button loginBtn;
    private EditText usernameEditor;
    private EditText passwordEditor;
    private Resources rs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        toReg = (TextView) rootView.findViewById(R.id.register);
        loginBtn = (Button) rootView.findViewById(R.id.login_submit);
        usernameEditor = (EditText) rootView.findViewById(R.id.login_name);
        passwordEditor = (EditText) rootView.findViewById(R.id.login_pwd);
        rs = getResources();

        btnBinding();
        return rootView;
    }


    private void btnBinding() {
        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToReg();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }


    //   跳转到注册页面
    private void redirectToReg() {
        FragmentManager fm = getActivity().getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.main_container, new RegisterFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    //  登陆
    private void login() {
        String username = usernameEditor.getText().toString();
        String password = passwordEditor.getText().toString();
        if (username.length() == 0) {
            usernameEditor.setError(rs.getString(R.string.username_null));
            usernameEditor.requestFocus();
            return;
        }
        if (password.length() == 0) {
            passwordEditor.setError(rs.getString(R.string.password_null));
            passwordEditor.requestFocus();
            return;
        }
        AVUser.logInInBackground(username, password,
                new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (avUser == null) {
                            usernameEditor.setError(rs.getString(R.string.login_error));
                            usernameEditor.requestFocus();
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), BaseActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
