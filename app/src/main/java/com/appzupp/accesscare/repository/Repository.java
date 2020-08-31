package com.appzupp.accesscare.repository;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.appzupp.accesscare.firebase.IActivityList;
import com.appzupp.accesscare.model.Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IActivityList {


    @Override
    public void createNewActivity(String activityName, int activityImage, String activityInstructions) {

    }

    @Override
    public List<Activity> getActivityList() {
        final List<Activity> activityList=new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference activitiesCollectionReference = db.collection("activities");

        Query query = activitiesCollectionReference
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Activity activity = document.toObject(Activity.class);
                        activityList.add(activity);

                    }

                } else {
                    Activity activity=new Activity();
                    activity.setActivity_id("Query Failed");
                }
            }
        });
        return activityList;

    }

    @Override
    public void deleteActivity() {

    }

    @Override
    public void updateActivity() {

    }
}
