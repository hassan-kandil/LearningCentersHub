package edu.aucegypt.learningcentershub;

import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class CourseInfoAdmin extends AppCompatActivity implements View.OnClickListener {

    LinearLayout expandableLearningCenter;
    Button arrowBtnLearningCenter;
    CardView cardViewLearningCenter;
    Button registerBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info_admin);

        Toolbar myToolbar = findViewById(R.id.topbar4);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageButton button = findViewById(R.id.buttonback);
        button.setOnClickListener(this);
        //load exisiting info
    }

    @Override
    public void onClick(View view) {
        //should save data
        finish();
    }


      /*  expandableLearningCenter = (LinearLayout) findViewById(R.id.expandableLearningCenter);
        arrowBtnLearningCenter = (Button) findViewById(R.id.arrowBtnLearningCenter);
        cardViewLearningCenter = (CardView) findViewById(R.id.cardViewLearningCenter);
        registerBtn = (Button)findViewById(R.id.registerBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CourseRegisterActivity.class);
                startActivity(i);
            }
        });



        arrowBtnLearningCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChangeBounds transition = new ChangeBounds();
                transition.setDuration(100L);                       // Sets a duration of 100 millisecondss
                if(expandableLearningCenter.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardViewLearningCenter,transition);
                    expandableLearningCenter.setVisibility(View.VISIBLE);
                    arrowBtnLearningCenter.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);

                }else {
                    TransitionManager.beginDelayedTransition(cardViewLearningCenter,transition);
                    expandableLearningCenter.setVisibility(View.GONE);
                    arrowBtnLearningCenter.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);

                }
            }
        });



       */
    }




