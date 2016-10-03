package com.example.victorbello.photofeed.login.events;

/**
 * Created by victorbello on 15/09/16.
 */

public class LoginEvent {
    public final static int onSignInError=0;
    public final static int onSignUpError=1;
    public final static int onSignInSuccess=2;
    public final static int onSignUpSuccess=3;
    public final static int onFailedToRecoverSession=4;
    public final static int onSignOff=5;

    private int eventType;
    private String errorMessage;
    private String currentUserEmail;

    public int getEventType(){
        return eventType;
    }

    public void setEventType(int eventType){
        this.eventType=eventType;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage=errorMessage;
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public void setCurrentUserEmail(String currentUserEmail) {
        this.currentUserEmail = currentUserEmail;
    }
}
