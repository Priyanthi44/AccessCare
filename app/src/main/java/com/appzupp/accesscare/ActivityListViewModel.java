package com.appzupp.accesscare;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appzupp.accesscare.model.Activity;
import com.appzupp.accesscare.repository.Repository;

import java.util.List;

public class ActivityListViewModel extends ViewModel {
    Repository mRepository;

    private MutableLiveData<List<Activity>> activitiesLiveData = new MutableLiveData<>();

    public LiveData<List<Activity>> getActivities() {
        return activitiesLiveData;
    }

    public ActivityListViewModel() {
        mRepository=new Repository();
        getActivities();

    }

    void getActivityList() {
        // depending on the action, do necessary business logic calls and update the
        // userLiveData.
        activitiesLiveData= (MutableLiveData<List<Activity>>) mRepository.getActivityList();


    }

}
