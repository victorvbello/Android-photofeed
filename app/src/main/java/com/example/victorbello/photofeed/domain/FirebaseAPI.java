package com.example.victorbello.photofeed.domain;


/**
 * Created by victorbello on 29/08/16.
 */

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import com.example.victorbello.photofeed.entities.Photo;

public class FirebaseAPI {

    private static final String onChildrenCountEmpty="photolist_message_db_empty";
    private static final String onCurrentUserNull="photolist_message_cantnot_getuser";

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ChildEventListener photosEventListener;

    public FirebaseAPI( DatabaseReference databaseReference){
        this.databaseReference=databaseReference;
        this.firebaseAuth=FirebaseAuth.getInstance();
    }

    public void checkForData(final FirebaseActionListenerCallback firebaseActionListenerCallback){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0){
                    firebaseActionListenerCallback.onSuccess();
                }else{
                    firebaseActionListenerCallback.onError(onChildrenCountEmpty);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                firebaseActionListenerCallback.onError(databaseError);
            }
        });
    }

    public void subscribe(final FirebaseEventListenerCallback firebaseEventListenerCallback){
        if(photosEventListener==null){
            photosEventListener= new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    firebaseEventListenerCallback.onChilAdded(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    firebaseEventListenerCallback.onChilRemoved(dataSnapshot);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    firebaseEventListenerCallback.onCancelled(databaseError);
                }
            };
            databaseReference.addChildEventListener(photosEventListener);
        }
    }

    public void unsubscribe(){
        if(photosEventListener!=null){
            databaseReference.removeEventListener(photosEventListener);
        }
    }

    public String create(){
        return this.databaseReference.push().getKey();
    }

    public void update (Photo photo){
        this.databaseReference.child(photo.getId()).setValue(photo);
    }

    public void remove(Photo photo,FirebaseActionListenerCallback firebaseActionListenerCallback){
        this.databaseReference.child(photo.getId()).removeValue();
        firebaseActionListenerCallback.onSuccess();
    }

    public String getAuthEmail(){
        String email=null;
        FirebaseUser user= firebaseAuth.getCurrentUser();
        if(user!=null){
            email=user.getEmail();
        }
        return email;
    }

    public void logout(){
        FirebaseUser user= firebaseAuth.getCurrentUser();
        if(user!=null){
            firebaseAuth.signOut();
        }
    }

    public void login(String email, String password, final FirebaseActionListenerCallback firebaseActionListenerCallback){
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        firebaseActionListenerCallback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        firebaseActionListenerCallback.onError(e.getLocalizedMessage());
                    }
                });
    }

    public void signup(String email, String password, final FirebaseActionListenerCallback firebaseActionListenerCallback){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        firebaseActionListenerCallback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        firebaseActionListenerCallback.onError(e.getLocalizedMessage());
                    }
                });
    }

    public void checkForSession(FirebaseActionListenerCallback firebaseActionListenerCallback){
        if(firebaseAuth.getCurrentUser()!=null){
            firebaseActionListenerCallback.onSuccess();
        }else{
            firebaseActionListenerCallback.onError(onCurrentUserNull);
        }
    }

}
