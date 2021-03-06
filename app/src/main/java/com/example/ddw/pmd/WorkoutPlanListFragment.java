package com.example.ddw.pmd;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddw.pmd.dtos.workoutplanDTO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WorkoutPlanListFragment extends Fragment implements AbsListView.OnItemClickListener {

    public class CustomComparator implements Comparator<workoutplanDTO> {
        @Override
        public int compare(workoutplanDTO w1, workoutplanDTO w2) {
            String first = w1.getPlanname() + w1.getDescription();
            String second = w2.getPlanname() + w2.getDescription();
            return first.compareTo(second);
        }
    }

    DBAdapter db;
    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;
    private ListAdapter mAdapter;
    private ArrayList<workoutplanDTO> allWorkouts;
    Fragment frag = null;
    FloatingActionButton fab;
    public WorkoutPlanListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Workout Plan List");
        db = new DBAdapter(this.getContext());
        db.open();
        allWorkouts = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, getWorkouts());
    }

    @Override
    public void onResume() {
        super.onResume();
        db.open();
        allWorkouts = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, getWorkouts());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workoutplan, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onWorkoutArticleSelected(-1);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;
        if (context instanceof Activity) {
            a = (Activity) context;

            try {
                mListener = (OnFragmentInteractionListener) a;
            } catch (ClassCastException e) {
                throw new ClassCastException(a.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }
        else {
            try {
                mListener = (OnFragmentInteractionListener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString()
                        + "must implement OnFragmentInteractionListener");
            }
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

            workoutplanDTO workout = allWorkouts.get(position);
            mListener.onWorkoutArticleSelected(workout.getId());
           // frag = new WorkoutPlanDetailsFragment();
           // getFragmentManager().beginTransaction().replace(R.id.frameLayout, frag).commit();

        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onWorkoutArticleSelected(int position);
    }

    public ArrayList<String> getWorkouts() {
        ArrayList<String> workouts = new ArrayList<>();
        Cursor c = db.getAllWorkoutplans();
        if (c.moveToFirst()) {
            do {
                workoutplanDTO workout = new workoutplanDTO();
                workout.setId(c.getInt(0));
                workout.setPlanname(c.getString(1));
                workout.setDescription(c.getString(2));
                workout.setDetails(c.getString(3));
                allWorkouts.add(workout);
                workouts.add(workout.toString());
            } while (c.moveToNext());
        }
        db.close();
        Collections.sort(workouts);
        Collections.sort(allWorkouts, new CustomComparator());
        return workouts;
    }

}
