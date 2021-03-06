package edu.aucegypt.learningcentershub.ui.user.homescreen;

import android.location.Location;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import edu.aucegypt.learningcentershub.R;
import edu.aucegypt.learningcentershub.ui.user.learningcenterslistscreen.LearningCenterList_frag;
import edu.aucegypt.learningcentershub.ui.TopBar;
import edu.aucegypt.learningcentershub.ui.user.categoriesscreen.CategoriesList_frag;
import edu.aucegypt.learningcentershub.ui.user.courseslistscreen.CoursesList_frag;
import edu.aucegypt.learningcentershub.ui.user.courseslistscreen.FiltersFragment;

public class MainActivity extends AppCompatActivity implements CoursesList_frag.coursesFragOnClickListener, FiltersFragment.filtersFragmentOnClickListener, main_frag.categoriesOnClickListener, main_frag.learningCenterOnClickListener, main_frag.courseOnClickListener{

    FrameLayout filters_layout;
    ConstraintLayout main_layout;

    protected static final String TAG = "main-activity";

    protected static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    protected static final String LOCATION_ADDRESS_KEY = "location-address";


    protected Location mLastLocation;
    protected boolean mAddressRequested;

    protected String mAddressOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        filters_layout = (FrameLayout) findViewById(R.id.filters_layout);
        main_layout = (ConstraintLayout) findViewById(R.id.main_layout);


        // Set defaults, then update using values stored in the Bundle.
        mAddressRequested = false;
        mAddressOutput = "";



        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment6, new NavBar());
        fragmentTransaction.replace(R.id.fragment6_1, new TopBar());
        fragmentTransaction.replace(R.id.fragment6_2, new main_frag());
        fragmentTransaction.replace(R.id.filters_layout, new FiltersFragment());
        fragmentTransaction.commit();


    }



    @Override
    public void onFiltersBtnClick() {
        final ChangeBounds transition = new ChangeBounds();
        transition.setDuration(100L);                       // Sets a duration of 100 millisecondss

        if(filters_layout.getVisibility()== View.GONE){
            TransitionManager.beginDelayedTransition(main_layout,transition);
            filters_layout.setVisibility(View.VISIBLE);
        }
    }

    public void onClickClose(){
        final ChangeBounds transition = new ChangeBounds();
        transition.setDuration(100L);                       // Sets a duration of 100 millisecondss

        if(filters_layout.getVisibility()!= View.GONE){
            TransitionManager.beginDelayedTransition(main_layout,transition);
            filters_layout.setVisibility(View.GONE);
        }
    }

    public void onCategoriesListener()
    {
        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment6_2, new CategoriesList_frag()).commit();

    }

    public void onLearningCenterListener()
    {
        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment6_2, new LearningCenterList_frag()).commit();

    }

    public void onCourseListener()
    {
        CoursesList_frag coursesList_frag = new CoursesList_frag();
        Bundle bundle = new Bundle();
        coursesList_frag.setArguments(bundle);
        bundle.putBoolean("isFilter", false);
        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment6_2, coursesList_frag).commit();
    }

    @Override
    public void onClickApply(ArrayList<String> CatNames, ArrayList<String> AreaNames, int Price, ArrayList<Integer> DateFilters) {

        Bundle bundle=new Bundle();
        CoursesList_frag coursesList_frag = new CoursesList_frag();
        bundle.putBoolean("isFilter", false);

        if(!CatNames.isEmpty())
        {
            bundle.putSerializable("CatNames", CatNames);
            bundle.putBoolean("isFilter", true);
            bundle.putBoolean("isFilterCat", true);
        }

        if(!AreaNames.isEmpty()){
            bundle.putSerializable("AreaNames", AreaNames);
            bundle.putBoolean("isFilterArea", true);
            bundle.putBoolean("isFilter", true);
        }

        if(Price != 0){
            bundle.putInt("Price", Price);
            bundle.putBoolean("isFilterPrice", true);
            bundle.putBoolean("isFilter", true);
        }

        if(!DateFilters.isEmpty()){
            bundle.putSerializable("Start Date", DateFilters);
            bundle.putBoolean("isFilterStartDate", true);
            bundle.putBoolean("isFilter", true);
        }


        coursesList_frag.setArguments(bundle);

        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment6_2,coursesList_frag).commit();


        final ChangeBounds transition = new ChangeBounds();
        transition.setDuration(100L);                       // Sets a duration of 100 millisecondss

        if(filters_layout.getVisibility()!= View.GONE){
            TransitionManager.beginDelayedTransition(main_layout,transition);
            filters_layout.setVisibility(View.GONE);
        }


    }
}

