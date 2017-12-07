package anupreksha.com.noteit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OnLongClicklistenerNotes implements View.OnLongClickListener {
    Context context;
    String id;
    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();
        final CharSequence[] items = { "Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Note")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            editRecord(Integer.parseInt(id));
                        }
                        else if (item == 1) {

                            boolean deleteSuccessful = new TableController(context).delete(Integer.parseInt(id));

                            if (deleteSuccessful){
                                Toast.makeText(context, "Note was deleted.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to delete Note.", Toast.LENGTH_SHORT).show();
                            }
                            ((MainActivity) context).readRecords();

                        }
                        dialog.dismiss();

                    }
                }).show();
        return false;
    }
    public void editRecord(final int noteId) {
        final TableController tableController = new TableController(context);
        Note newNote = tableController.readSingleRecord(noteId);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.dialog_show_note, null, false);
        final EditText editTexttitle =  formElementsView.findViewById(R.id.txtTitle);
        final EditText editTextDescription =  formElementsView.findViewById(R.id.txtDescription);
        editTexttitle.setText(newNote.mTitle);
        editTextDescription.setText(newNote.mDescription);
        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Note newNote = new Note();
                                newNote.id = noteId;
                                newNote.mTitle = editTexttitle.getText().toString();
                                newNote.mDescription = editTextDescription.getText().toString();
                                boolean updateSuccessful = tableController.update(newNote);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Note was updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to update Note.", Toast.LENGTH_SHORT).show();
                                }
                                ((MainActivity) context).readRecords();
                                dialog.cancel();
                            }

                        }).show();

    }
}
