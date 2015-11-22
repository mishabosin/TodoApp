package com.mishabosin.todoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemDialog extends DialogFragment implements TextView.OnEditorActionListener {
    private EditText mEditText;

    public interface EditItemDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public EditItemDialog() {}

    public static EditItemDialog newInstance(String title) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnAddTask = (Button) view.findViewById(R.id.btnAddItem);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnInputToActivity();
            }
        });
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.etEditItem);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    // Fires whenever the textfield has an action performed
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        // Check if the "Done" button is pressed
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            returnInputToActivity();
            return true;
        }
        return false;
    }

    private void returnInputToActivity() {
        EditItemDialogListener listener = (EditItemDialogListener) getActivity();
        listener.onFinishEditDialog(mEditText.getText().toString());
        dismiss();
    }
}
