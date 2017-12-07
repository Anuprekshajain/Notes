package anupreksha.com.noteit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DialogNewNote extends DialogFragment {
    View dialogView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_new_note, null);
        final EditText editTitle = dialogView.findViewById
                (R.id.editTitle);
        final EditText editDescription =dialogView.findViewById
                (R.id.editDescription);
        Button btnCancel =  dialogView.findViewById(R.id.btnCancel);
        Button btnOK =  dialogView.findViewById(R.id.btnOK);
        builder.setView(dialogView).setTitle("Add a new note");
// Handle the cancel button
        btnCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // Handle the OK button
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Create a new note
                Note newNote = new Note();
// Set its variables to match the users entries on the form
                newNote.setmTitle(editTitle.getText().toString());
                newNote.setmDescription(editDescription.getText().toString());
                Context context = dialogView.getRootView().getContext();
                boolean createSuccessful = new TableController(context).create(newNote);
                if(createSuccessful){
                    Toast.makeText(context, "Note is saved.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Unable to save note.", Toast.LENGTH_SHORT).show();
                }
// Get a reference to MainActivity
                MainActivity callingActivity = (MainActivity) getActivity();
// Pass newNote back to MainActivity
               // callingActivity.createNewNote(newNote);
                callingActivity.readRecords();
// Quit the dialog
                dismiss();
            }
        });
        return builder.create();
    }
}
