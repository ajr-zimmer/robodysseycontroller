package com.cain.robodysseycontroller.activities;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cain.robodysseycontroller.R;
import com.cain.robodysseycontroller.utils.Utils;

public class TutorialActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    ImageButton mNextBtn;
    Button mSkipBtn, mFinishBtn;

    ImageView zero, one, two, three;
    ImageView[] indicators;

    int lastLeftValue = 0;

    CoordinatorLayout mCoordinator;

    static final String TAG = "TutorialActivity";

    int page = 0; // to track page position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mNextBtn = (ImageButton) findViewById(R.id.tutorial_btn_next);
        mSkipBtn = (Button) findViewById(R.id.tutorial_btn_skip);
        mFinishBtn = (Button) findViewById(R.id.tutorial_btn_finish);

        zero = (ImageView) findViewById(R.id.tutorial_indicator_0);
        one = (ImageView) findViewById(R.id.tutorial_indicator_1);
        two = (ImageView) findViewById(R.id.tutorial_indicator_2);
        three = (ImageView) findViewById(R.id.tutorial_indicator_3);

        mCoordinator = (CoordinatorLayout) findViewById(R.id.main_content);

        indicators = new ImageView[]{zero, one, two, three};

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(page);
        updateIndicators(page);

        final int colour1 = ContextCompat.getColor(this, R.color.green);
        final int colour2 = ContextCompat.getColor(this, R.color.red);
        final int colour3 = ContextCompat.getColor(this, R.color.cyan);
        final int colour4 = ContextCompat.getColor(this, R.color.deep_purple);

        final int[] colourList = new int[]{colour1, colour2, colour3, colour4};

        final ArgbEvaluator evaluator = new ArgbEvaluator();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                // Update colour of section
                int colourUpdate = (Integer) evaluator.evaluate(positionOffset, colourList[position],
                        colourList[position == 3 ? position : position + 1]);
                mViewPager.setBackgroundColor(colourUpdate);
            }

            @Override
            public void onPageSelected(int position){
                page = position;
                updateIndicators(page);
                switch(position){
                    case 0:
                        mViewPager.setBackgroundColor(colour1);
                        break;
                    case 1:
                        mViewPager.setBackgroundColor(colour2);
                        break;
                    case 2:
                        mViewPager.setBackgroundColor(colour3);
                        break;
                    case 3:
                        mViewPager.setBackgroundColor(colour4);
                        break;
                }
                // Overlay the Finish button on the last slide
                mNextBtn.setVisibility(position == 3 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == 3 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state){

            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                page++;
                mViewPager.setCurrentItem(page, true);
            }
        });

        mSkipBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Not sure if this should be placed before or after "finish()"
                Intent controlIntent = new Intent(TutorialActivity.this, ControlActivity.class);
                startActivity(controlIntent);
                finish();
            }
        });

        mFinishBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Not sure if this should be placed before or after "finish()"
                Intent controlIntent = new Intent(TutorialActivity.this, ControlActivity.class);
                startActivity(controlIntent);
                finish();
                // Update 1st time preferences
                //Utils.saveSharedSetting(TutorialActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");
                //Utils.saveSharedSetting(TutorialActivity.this, MainActivity.PREF_USER_FIRST_TIME, "true"); //debug purposes
            }
        });
    }

    // Matches the grey circles to the page that the user is currently on
    void updateIndicators(int position){
        for(int i=0; i<indicators.length; i++){
            indicators[i].setBackgroundResource(i == position ?
                    R.drawable.indicator_selected : R.drawable.indicator_unselected);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutorial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        // Images in each section
        ImageView img;
        int[] bgs = new int[]{R.drawable.ic_arrow_up_24dp, R.drawable.ic_arrow_down_24dp,
                R.drawable.ic_arrow_right_24dp, R.drawable.ic_arrow_left_24dp};

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            // Append direction title
            String[] titles = getResources().getStringArray(R.array.tuttitle_array);
            textView.append(titles[getArguments().getInt(ARG_SECTION_NUMBER)-1]);


            // Place images into sections
            img = (ImageView) rootView.findViewById(R.id.section_img);
            img.setBackgroundResource(bgs[getArguments().getInt(ARG_SECTION_NUMBER)-1]);

            // Text bodies in each section
            TextView txtHowto;
            String[] bodies = getResources().getStringArray(R.array.tutbody_array);
            // Changes instructions based on section
            txtHowto = (TextView) rootView.findViewById(R.id.section_body);
            txtHowto.setText(bodies[getArguments().getInt(ARG_SECTION_NUMBER)-1]);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
            }
            return null;
        }
    }
}
