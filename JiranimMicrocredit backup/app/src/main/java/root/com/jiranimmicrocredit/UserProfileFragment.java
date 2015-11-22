package root.com.jiranimmicrocredit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class UserProfileFragment extends Fragment {
    public static TabLayout tablayout;
    public static ViewPager viewpager;
    public static int int_items = 2;
    private static final String STATE_COUNTER = "counter";
    private int mCounter;

    public UserProfileFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_user_profile, null);
        tablayout = (TabLayout) RootView.findViewById(R.id.tabs);
        viewpager = (ViewPager) RootView.findViewById(R.id.viewpager);

        viewpager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tablayout.post(new Runnable() {
            @Override
            public void run() {
                tablayout.setupWithViewPager(viewpager);
            }
        });


        return RootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 1:
                    return new PersonalDetailsFragment();
                case 0:
                    return new EmploymentDetailsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Personal Details";
                case 1:
                    return "Employment Details";
            }
            return null;
        }


    }
}
