package com.example.ddw.pmd;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkoutPlanDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkoutPlanDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutPlanDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String TAG = "WorkoutPlanDetails";
    private static final String ARG_ID = "id";
    private static final String ARG_PARAM2 = "param2";
    DBAdapter db;
    View view;
    // TODO: Rename and change types of parameters
    private int id = -1;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;

    public WorkoutPlanDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutPlanDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutPlanDetailsFragment newInstance(String param1) {
        WorkoutPlanDetailsFragment fragment = new WorkoutPlanDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, -1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Meal Plan Details");
        db = new DBAdapter(this.getContext());
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        if(id > -1) {
            view = inflater.inflate(R.layout.fragment_workout_plan_details, container, false);
            setWorkoutDetails();
        }
        else {
            view = inflater.inflate(R.layout.fragment_workout_plan_add, container, false);
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
   // public void onButtonPressed(Uri uri) {
   //     if (mListener != null) {
   //        mListener.onFragmentInteraction(uri);
   //     }
   // }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        void onArticleSelected(int position);
    }

    private void setWorkoutDetails(){
        TextView planName = (TextView)view.findViewById(R.id.Plan_Name);
        TextView planDescript = (TextView)view.findViewById(R.id.Plan_Descript);
        TextView planSummary = (TextView)view.findViewById(R.id.Plan_Summary);
        db.open();

        Cursor c = db.getWorkoutplan(id);
        if(c.moveToFirst()){
            planName.setText(c.getString(1));
            planDescript.setText(c.getString(2));
            planSummary.setText(c.getString(3));
        }
        db.close();
    }
}
