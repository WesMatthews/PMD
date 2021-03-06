package com.example.ddw.pmd;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MealPlanDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MealPlanDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealPlanDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String TAG = "MealPlanDetails";
    private static final String ARG_ID = "id";
    private static final String ARG_PARAM2 = "param2";
    View view;
    DBAdapter db;
    // TODO: Rename and change types of parameters
    private int id = -1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment MealPlanDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealPlanDetailsFragment newInstance(int param1) {
        MealPlanDetailsFragment fragment = new MealPlanDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, -1);
     //   args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MealPlanDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Meal Plan Details");

        db = new DBAdapter(this.getContext());




        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
           // mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(id > -1) {
            view = inflater.inflate(R.layout.fragment_meal_plan_details, container, false);
            setMealDetails();
        }
        else{
            view = inflater.inflate(R.layout.fragment_meal_plan_add,container,false);
         }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    //public void onButtonPressed(Uri uri) {
    //    if (mListener != null) {
    //       mListener.onFragmentInteraction(uri);
    //    }
    //}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
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
        public void onArticleSelected(int position);
    }

    private void setMealDetails(){

        TextView planName = (TextView)view.findViewById(R.id.Plan_Name);
        TextView planDescript = (TextView)view.findViewById(R.id.Plan_Descript);
        TextView planSummary = (TextView)view.findViewById(R.id.Plan_Summary);
        db.open();
        Cursor c = db.getMealplan(id);
        if(c.moveToFirst()){
            planName.setText(c.getString(1));
            planDescript.setText(c.getString(2));
            planSummary.setText(c.getString(3));
        }
        db.close();
    }

}
