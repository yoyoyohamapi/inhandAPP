package milk.inhand.com.inhandmilk.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;

import java.util.List;

import milk.inhand.com.inhandmilk.R;
import milk.inhand.com.inhandmilk.activity.SyncTestActivity;
import milk.inhand.com.inhandmilk.utils.Validator;

/**
 * RegisterFragment
 * Desc:注册Fragment
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-03
 * Time: 16:56
 */
public class RegisterFragment extends BackHandleFragment {
    private View rootView;
    private Resources rs;
    private TextView topTitle;
    private EditText phoneEditor;
    private EditText vcEditor;
    private Button nextBtn;
    private Button getVCBtn;
    private AVUser user = null;
    private TimeCount timer;
    private ViewFlipper flipper;
    private EditText firstPwd;
    private EditText secondPwd;
    private EditText familyNameEditor;

    private String phoneNumber;

    @Override
    public boolean onBackPressed() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        user = null;
        getFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVUser.logOut();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_register, container, false);
        rs = getResources();
        //初始化各组件
        topTitle = (TextView) rootView.findViewById(R.id.top_title);
        phoneEditor = (EditText) rootView.findViewById(R.id.phone);
        vcEditor = (EditText) rootView.findViewById(R.id.vc);
        getVCBtn = (Button) rootView.findViewById(R.id.get_vc_btn);
        nextBtn = (Button) rootView.findViewById(R.id.next);
        flipper = (ViewFlipper) rootView.findViewById(R.id.view_flipper);
        //设置flipper动画
        flipper.setInAnimation(AnimationUtils.
                loadAnimation(getActivity(), R.anim.right_in));
        flipper.setOutAnimation(AnimationUtils.
                loadAnimation(getActivity(), R.anim.left_out));

        firstPwd = (EditText) rootView.findViewById(R.id.first_pwd);
        secondPwd = (EditText) rootView.findViewById(R.id.second_pwd);
        familyNameEditor = (EditText) rootView.findViewById(R.id.family_name);


        //确定各个按钮事件
        bindingBtn();

        return rootView;
    }

    //按键绑定
    private void bindingBtn() {
        getVCBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserPhone();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View curView = flipper.getCurrentView();
                switch (curView.getId()) {
                    case R.id.phone_page:
                        toPwdPage();
                        break;
                    case R.id.pwd_page:
                        toBaseInfoPage();
                        break;
                    case R.id.base_info_page:
                        toHomePage();
                        break;
                }

            }
        });
    }

    //验证码倒计时
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getVCBtn.setText(millisUntilFinished / 1000 + rs
                    .getString(R.string.vc_wait));
        }

        @Override
        public void onFinish() {
            getVCBtn.setText(rs.getString(R.string.vc_reget));
            getVCBtn.setClickable(true);
        }
    }

    //注册用户手机
    private void setUserPhone() {
        //开始计时
        timer = new TimeCount(60000, 1000);
        //获得电话号码
        phoneNumber = phoneEditor.getText().toString();

        //请求验证码
        if (Validator.validatePhone(phoneNumber)) {
            //判断该用户是否存在
            AVQuery<AVUser> query = AVQuery.getQuery(AVUser.class);
            query.whereEqualTo("username", phoneNumber);
            query.findInBackground(new FindCallback<AVUser>() {
                public void done(List<AVUser> objects, AVException e) {
                    if (e == null) {
                        // 查询成功
                        //用户已存在
                        if (objects.size() > 0) {
                            phoneEditor.setError(rs.getString(R.string.phone_exist));
                            phoneEditor.requestFocus();
                        } else {
                            AVOSCloud.requestSMSCodeInBackgroud(phoneNumber,
                                    rs.getString(R.string.app_name),
                                    rs.getString(R.string.register),
                                    1,
                                    new RequestMobileCodeCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if (e != null) {
                                                e.printStackTrace();
                                                Toast.makeText(getActivity(), "Bad Request", Toast.LENGTH_SHORT).show();
                                            } else {
                                                getVCBtn.setClickable(false);
                                                timer.start();
                                            }
                                        }
                                    });
                        }
                    } else {
                        // 查询出错
                    }
                }
            });
        } else {
            phoneEditor.setError(rs.getString(R.string.phone_error));
        }
    }


    //至密码填写页面
    private void toPwdPage() {
        String vCode = vcEditor.getText().toString();
        if (vCode.length() == 6) {
            AVOSCloud.verifySMSCodeInBackground(vCode, phoneNumber, new AVMobilePhoneVerifyCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        user = new AVUser();
                        user.setUsername(phoneNumber);
                        user.setMobilePhoneNumber(phoneNumber);
                        timer.cancel();
                        flipper.showNext();
                        topTitle.setText(rs.getString(R.string.reg_step_pwd));
                    } else {
                        vcEditor.setError(rs.getString(R.string.vc_error));
                    }
                }
            });
        }
    }

    //至基本信息填写页面
    private void toBaseInfoPage() {
        String firstPassword = firstPwd.getText().toString();
        String secondPassword = secondPwd.getText().toString();

        if (firstPassword.equals(secondPassword)) {
            if (Validator.validatePassword(firstPassword)) {
                user.setPassword(firstPassword);
                flipper.showNext();
                topTitle.setText(rs.getString(R.string.reg_step_family));
            } else {
                firstPwd.setError(rs.getString(R.string.pwd_error));
                firstPwd.requestFocus();
            }
        } else {
            secondPwd.setError(rs.getString(R.string.pwd_mismatch));
            secondPwd.requestFocus();
        }
    }

    //至主页
    private void toHomePage() {
        String familyName = familyNameEditor.getText().toString();
        familyName = familyName.replaceAll(" ", "");
        if (familyName.length() >= rs.getInteger(R.integer.min_family_name)) {
            user.put("family_name", familyName);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        //注册成功
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), SyncTestActivity.class);
                        startActivity(intent);
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            familyNameEditor.setError(rs.getString(R.string.family_name_error));
            familyNameEditor.requestFocus();
        }
    }
}
