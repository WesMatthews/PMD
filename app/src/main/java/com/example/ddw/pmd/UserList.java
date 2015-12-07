package com.example.ddw.pmd;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.ddw.pmd.dtos.*;

public class UserList extends Fragment implements AbsListView.OnItemClickListener {

    public class CustomComparator implements Comparator<userDTO> {
        @Override
        public int compare(userDTO u1, userDTO u2) {
            String first = u1.getFirstname() + u1.getLastname();
            String second = u2.getFirstname() + u2.getLastname();
            return first.compareTo(second);
        }
    }

    DBAdapter db;
    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;
    private ListAdapter mAdapter;
    private ArrayList<userDTO> allUsers;

    public UserList() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("User List");
        db = new DBAdapter(this.getContext());
        db.open();
        allUsers = new ArrayList<>();

        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, getUsers());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

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
           // mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
            mListener.onArticleSelected(position);
            userDTO user = allUsers.get(position);
            Toast.makeText(getContext(), user.getFirstname() + " " +
                    user.getLastname() + "\n" +
                    user.getUsertype(), Toast.LENGTH_LONG).show();


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
        public void onArticleSelected(int position);
    }

    public ArrayList<String> getUsers() {
        ArrayList<String> users = new ArrayList<>();
        Cursor c = db.getAllUsers();
        if (c.moveToFirst())
        {
            do {
                userDTO user = new userDTO();
                user.setId(c.getInt(0));
                user.setUsername(c.getString(1));
                user.setPassword(c.getString(2));
                user.setFirstname(c.getString(3));
                user.setLastname(c.getString(4));
                user.setUsertype(c.getString(5));
                user.setEmail(c.getString(6));
                user.setMealplan(c.getInt(7));
                user.setWorkoutplan(c.getInt(8));
                allUsers.add(user);
                users.add(user.toString());
            } while (c.moveToNext());
        }
        db.close();
        Collections.sort(users);
        Collections.sort(allUsers, new CustomComparator());
        return users;
    }

}
