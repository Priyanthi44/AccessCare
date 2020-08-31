package com.appzupp.accesscare.firebase;

import com.appzupp.accesscare.model.Activity;

import java.util.List;

public interface IActivityList {
   void createNewActivity(String activityName, int activityImage,
                                     String activityInstructions) ;



    List<Activity> getActivityList()

    ;
    void deleteActivity()

    ;
    void updateActivity()

    ;
}
