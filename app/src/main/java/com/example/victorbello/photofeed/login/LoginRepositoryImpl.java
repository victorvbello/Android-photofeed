package com.example.victorbello.photofeed.login;


/**
 * Created by victorbello on 15/09/16.
 */

import com.google.firebase.database.DatabaseError;
import com.example.victorbello.photofeed.domain.FirebaseAPI;
import com.example.victorbello.photofeed.lib.base.EventBus;
import com.example.victorbello.photofeed.login.events.LoginEvent;
import com.example.victorbello.photofeed.domain.FirebaseActionListenerCallback;

public class LoginRepositoryImpl implements LoginRepository{

    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;

    public LoginRepositoryImpl(EventBus eventBus, FirebaseAPI firebaseAPI) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void signUp(final String email, final String password) {
        firebaseAPI.signup(email, password, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(LoginEvent.onSignUpSuccess);
                signIn(email, password);
            }

            @Override
            public void onError(DatabaseError error) {
                postEvent(LoginEvent.onSignUpError,error.getMessage(),null);
            }

            @Override
            public void onError(String error) {
                postEvent(LoginEvent.onSignUpError,error,null);
            }

        });
    }

    @Override
    public void signIn(String email, String password) {
        if(email!=null && password!=null){
         firebaseAPI.login(email, password, new FirebaseActionListenerCallback() {
             @Override
             public void onSuccess() {
                 String email=firebaseAPI.getAuthEmail();
                 postEvent(LoginEvent.onSignInSuccess,email);
             }

             @Override
             public void onError(DatabaseError error) {
                 postEvent(LoginEvent.onSignInError,error.getMessage(),null);
             }

             @Override
             public void onError(String error) {
                 postEvent(LoginEvent.onSignInError,error,null);
             }

         });
        }else{
            firebaseAPI.checkForSession(new FirebaseActionListenerCallback() {
                @Override
                public void onSuccess() {
                    String email=firebaseAPI.getAuthEmail();
                    postEvent(LoginEvent.onSignInSuccess,email);
                }

                @Override
                public void onError(DatabaseError error) {
                    postEvent(LoginEvent.onFailedToRecoverSession,error.getMessage(),null);
                }

                @Override
                public void onError(String error) {
                    postEvent(LoginEvent.onFailedToRecoverSession,error,null);
                }
            });
        }
    }

    private void postEvent(int type, String errorMessage, String currentUserEmail){
        LoginEvent loginEvent=new LoginEvent();
        loginEvent.setEventType(type);
        loginEvent.setCurrentUserEmail(currentUserEmail);
        if(errorMessage!=null){
            loginEvent.setErrorMessage(errorMessage);
        }

        eventBus.post(loginEvent);
    }

    private void postEvent(int type){
        postEvent(type,null,null);
    }
    private void postEvent(int type, String currentUserEmail){
        postEvent(type,null,currentUserEmail);
    }
}
