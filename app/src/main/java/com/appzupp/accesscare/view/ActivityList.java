package com.appzupp.accesscare.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.appzupp.accesscare.ActivityListViewModel;
import com.appzupp.accesscare.R;
import com.appzupp.accesscare.model.Activity;
import com.appzupp.accesscare.adapters.ActivityListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActivityList extends AppCompatActivity {
    private NewActivity mNewActivity;

    private List<String> mActivityNamesList;
    private List<Integer> mActivityImagesList;
    private List<Activity> mActivities;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private ActivityListAdapter mActivityListAdapter;
    private DocumentSnapshot documentSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.activity_list);

        mActivities = new ArrayList<>();
        mActivityNamesList = new ArrayList<>();
        mActivityImagesList = new ArrayList<>();

        initRecycleView();

        ActivityListViewModel mViewModel = new ViewModelProvider(this).get(ActivityListViewModel.class);
        mViewModel.getActivities().observe(this, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                if (mActivities.size() > 0) {
                    mActivities.clear();
                }
                if (activities != null) {
                    mActivities.addAll(activities);
                }
                mActivityListAdapter.notifyDataSetChanged();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewActivity = new NewActivity();
                mNewActivity.show(getSupportFragmentManager(), "New Activity");

            }
        });

    }

    private void initRecycleView() {
       setActivities();
        if (mActivityListAdapter == null) {
            mActivityListAdapter = new ActivityListAdapter(mActivityNamesList, mActivityImagesList, this);
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mRecyclerView.setAdapter(mActivityListAdapter);
        }
    }

    private void setActivities(){

        for (Activity activity:mActivities){
            mActivityNamesList.add(activity.getActivity_name());
            mActivityImagesList.add(activity.getActivity_image());

        }


    }

    public void createNewActivity(String activityName, int activityImage, String activityInstructions) {
        FirebaseFirestore.setLoggingEnabled(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newActivityRef = db.collection("activities").document();

        String userID = FirebaseAuth.getInstance().getUid();
        Activity activity = new Activity();
        activity.setActivity_name(activityName);
        activity.setActivity_image(activityImage);
        activity.setInstructions(activityInstructions);
        activity.setActivity_status(false);
        activity.setActivity_id(newActivityRef.getId());
        activity.setUser(userID);

        newActivityRef.set(activity).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ActivityList.this, "Created a new activity", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityList.this, "Activity failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}