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
import android.widget.TextView;


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
    private static final String ARG_POSITION = "position";
    private static final String ARG_PARAM2 = "param2";
    View view;
    DBAdapter db;
    // TODO: Rename and change types of parameters
    private int pos = -1;
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
        args.putInt(ARG_POSITION, 1);
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
            pos = getArguments().getInt(ARG_POSITION);
           // mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_meal_plan_details, container, false);
        if(pos > -1)
            setMealDetails();
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
      //  TextView txtName = (TextView)view.findViewById(R.id.txtName);
        Log.v(TAG,"Entering setMealDetails()");
        TextView planName = (TextView)view.findViewById(R.id.Plan_Name);
        TextView planDescript = (TextView)view.findViewById(R.id.Plan_Descript);
        TextView planSummary = (TextView)view.findViewById(R.id.Plan_Summary);
        db.open();
        Log.v(TAG,"************************* position is" + pos);
        Cursor c = db.getAllMealplans();
        Log.v(TAG,"************************* Cursor"+c.getCount());
        if(c.moveToPosition(pos)){
            Log.v(TAG,"************* entered IF BLOCK");
            planName.setText(c.getString(1));
            planDescript.setText(c.getString(2));
            planSummary.setText(c.getString(3));
        }
        db.close();
    }
}
