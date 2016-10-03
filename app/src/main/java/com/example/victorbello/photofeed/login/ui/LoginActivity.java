package com.example.victorbello.photofeed.login.ui;

/**
 * Created by victorbello on 29/08/16.
 */

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.view.View;
import android.view.View.OnClickListener;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.example.victorbello.photofeed.PhotoFeedApp;
import com.example.victorbello.photofeed.R;
import com.example.victorbello.photofeed.main.ui.MainActivity;
import com.example.victorbello.photofeed.login.LoginPresenter;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements OnClickListener,LoginView {

    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnSignin;
    private Button btnSignup;
    private ProgressBar progressBar;
    private RelativeLayout layoutMainContainer;
    private PhotoFeedApp app;

    @Inject
    public LoginPresenter loginPresenter;

    @Inject
    public SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail=(EditText) findViewById(R.id.inputEmail);
        inputPassword=(EditText) findViewById(R.id.inputPassword);

        btnSignin=(Button) findViewById(R.id.btnSignin);
        btnSignup=(Button) findViewById(R.id.btnSignup);

        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        layoutMainContainer=(RelativeLayout) findViewById(R.id.layoutMainContainer);

        btnSignin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        app=(PhotoFeedApp) getApplication();
        setubInjection();
        loginPresenter.onCreate();
        loginPresenter.validateLogin(null,null);
    }

    private void setubInjection() {
        app.getLoginComponent(this).inject(this);
    }

    //Metodos botones

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btnSignin:
                handleSignIn();
                break;
            case R.id.btnSignup:
                handleSignUp();
                break;
        }
    }

    @Override
    public void onDestroy(){
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        this.setInputs(true);
    }

    @Override
    public void disableInputs() {
        this.setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void handleSignUp() {
        loginPresenter.registerNewUser(inputEmail.getText().toString(),
                inputPassword.getText().toString());
    }

    @Override
    public void handleSignIn() {
        loginPresenter.validateLogin(inputEmail.getText().toString(),
                inputPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError=String.format(getString(R.string.login_error_message_signin),error);
        inputPassword.setError(msgError);
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(layoutMainContainer,R.string.login_notice_message_signup,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String msgError=String.format(getString(R.string.login_error_message_signup),error);
        inputPassword.setError(msgError);
    }

    @Override
    public void setUserEmail(String email) {
        if(email!=null){
            String key=app.getEmailKey();
            sharedPreferences.edit().putString(key,email).commit();
        }
    }

    private void setInputs(boolean enabled){
        inputEmail.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        btnSignin.setEnabled(enabled);
        btnSignup.setEnabled(enabled);
    }
}
