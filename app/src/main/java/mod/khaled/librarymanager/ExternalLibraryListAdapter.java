package mod.khaled.librarymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sketchware.remod.R;

import java.util.ArrayList;

public class ExternalLibraryListAdapter extends RecyclerView.Adapter<ExternalLibraryListAdapter.LibraryItemViewHolder> {

    private final ArrayList<ExternalLibraryItem> data;
    private final String sc_id;

    ExternalLibraryListAdapter(ArrayList<ExternalLibraryItem> data, String sc_id) {
        this.data = data;
        this.sc_id = sc_id;
    }

    private boolean isLibraryInProject(String libraryHash) {
        return false;
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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class LibraryItemViewHolder extends RecyclerView.ViewHolder {

        ImageView libraryIcon;
        TextView libraryName, libraryPkg;
        Switch libraryEnabledSwitch;

        public LibraryItemViewHolder(View v) {
            super(v);
            libraryIcon = v.findViewById(R.id.libraryIcon);
            libraryName = v.findViewById(R.id.libraryName);
            libraryPkg = v.findViewById(R.id.libraryPkg);
            libraryEnabledSwitch = v.findViewById(R.id.libraryEnabledSwitch);
        }

        void bind(ExternalLibraryItem libraryItem) {
            libraryName.setText(libraryItem.getLibraryName());
            libraryPkg.setText(libraryItem.getLibraryPkg());
            libraryEnabledSwitch.setChecked(false);
        }
    }
}
