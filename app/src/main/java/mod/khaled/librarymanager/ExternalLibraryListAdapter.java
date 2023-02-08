package mod.khaled.librarymanager;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.sketchware.remod.R;

import java.util.List;

public class ExternalLibraryListAdapter extends RecyclerView.Adapter<ExternalLibraryListAdapter.LibraryItemViewHolder> {

    private final Context context;
    private final List<ExternalLibraryItem> data;
    private final ExternalLibraryManager externalLibraryManager;

    ExternalLibraryListAdapter(Context context, List<ExternalLibraryItem> data, @Nullable String sc_id) {
        this.data = data;
        this.context = context;
        this.externalLibraryManager = new ExternalLibraryManager(sc_id);
    }

    private boolean isLibraryInProject(String libraryHash) {
        return externalLibraryManager.getLibrariesInProjectHashes().contains(libraryHash);
    }

    private void toggleLibraryInProject(String libraryHash, boolean include) {
        if (include) externalLibraryManager.addLibraryToProject(libraryHash);
        else externalLibraryManager.removeLibraryFromProject(libraryHash);
    }

    private void showLibraryDeleteDialog(ExternalLibraryItem externalLibraryItem) {
        new AlertDialog.Builder(context)
                .setTitle("Delete library?")
                .setMessage(externalLibraryItem.getLibraryName() + "\n\n" + externalLibraryItem.getLibraryPkg())
                .setPositiveButton("Delete", (dialog, which) -> {
                    toggleLibraryInProject(externalLibraryItem.getLibraryHash(), false);
                    externalLibraryItem.deleteLibraryFromStorage();
                    int removedIndex = data.indexOf(externalLibraryItem);
                    data.remove(externalLibraryItem);
                    dialog.dismiss();
                    notifyItemRemoved(removedIndex);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @NonNull
    @Override
    public LibraryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LibraryItemViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.external_library_manager_item, parent, false));
    }

    @Override
    public void onBindViewHolder(LibraryItemViewHolder holder, final int position) {
        holder.bind(data.get(position));
        holder.setLibraryEnabledSwitchState(isLibraryInProject(data.get(position).getLibraryHash()));

        if (externalLibraryManager.sc_id == null)
            holder.libraryEnabledSwitch.setVisibility(View.GONE);
        else holder.libraryEnabledSwitch.setOnClickListener((v ->
                toggleLibraryInProject(data.get(position).getLibraryHash(), holder.libraryEnabledSwitch.isChecked())));

        holder.itemView.setOnLongClickListener((v -> {
            showLibraryDeleteDialog(data.get(position));
            return true;
        }));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class LibraryItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView libraryName, libraryPkg;
        final Switch libraryEnabledSwitch;

        public LibraryItemViewHolder(View v) {
            super(v);
            libraryName = v.findViewById(R.id.libraryName);
            libraryPkg = v.findViewById(R.id.libraryPkg);
            libraryEnabledSwitch = v.findViewById(R.id.libraryEnabledSwitch);
        }

        void bind(ExternalLibraryItem libraryItem) {
            libraryName.setText(libraryItem.getLibraryName());
            libraryPkg.setText(libraryItem.getLibraryPkg());
        }

        void setLibraryEnabledSwitchState(boolean isLibraryInProject) {
            libraryEnabledSwitch.setChecked(isLibraryInProject);
        }
    }
}
