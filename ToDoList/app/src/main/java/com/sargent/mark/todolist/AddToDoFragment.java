package com.sargent.mark.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by mark on 7/4/17.
 */

public class AddToDoFragment extends DialogFragment{

    private EditText toDo;
    private Spinner cat;    // spinner for selecting todoitem category
    private DatePicker dp;
    private Button add;
    private final String TAG = "addtodofragment";

    public AddToDoFragment() {
    }

    //To have a way for the activity to get the data from the dialog
    public interface OnDialogCloseListener {
        // Add 'category' string to closeDialog method
        void closeDialog(int year, int month, int day, String description, String category);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_adder, container, false);
        toDo = (EditText) view.findViewById(R.id.toDo);
        cat = (Spinner) view.findViewById(R.id.category);   // Get spinner by id
        dp = (DatePicker) view.findViewById(R.id.datePicker);
        add = (Button) view.findViewById(R.id.add);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dp.updateDate(year, month, day);

        /*
            ##### Spinner setup
         */
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.typesOfCategory, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        cat.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogCloseListener activity = (OnDialogCloseListener) getActivity();
                // Add selected category item from spinner to closeDialog call
                activity.closeDialog(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), toDo.getText().toString(), cat.getSelectedItem().toString());
                AddToDoFragment.this.dismiss();
            }
        });

        return view;
    }
}



