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
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_POS = "pos";
    private static final String PARAM_TEXT = "taskText";
    private EditText mEditText;

    public interface EditItemDialogListener {
        void onFinishNewDialog(String inputText);
        void onFinishEditDialog(int pos, String inputText, String originalText);
    }

    public EditItemDialog() {}

    public static EditItemDialog newTaskInstance() {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, "New task");
        frag.setArguments(args);
        return frag;
    }

    public static EditItemDialog newEditTaskInstance(int pos, String taskText) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, "Edit task");
        args.putInt(PARAM_POS, pos);
        args.putString(PARAM_TEXT, taskText);
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
        String title = getArguments().getString(PARAM_TITLE, "Todo task");
        getDialog().setTitle(title);

        // Submit button
        Button btnAddTask = (Button) view.findViewById(R.id.btnAddItem);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnInputToActivity();
            }
        });

        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.etEditItem);
        String taskText = getArguments().getString(PARAM_TEXT, "");
        mEditText.setText(taskText);
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
        String taskText = mEditText.getText().toString().trim();
        // ignore blank input
        if (taskText.equals("")) {
            dismiss();
            return;
        }
        int pos = getArguments().getInt(PARAM_POS, -1);
        if (pos == -1) {
            listener.onFinishNewDialog(taskText);
        } else {
            listener.onFinishEditDialog(pos, taskText, getArguments().getString(PARAM_TEXT, ""));
        }
        dismiss();
    }
}
