package mod.jbk.editor.manage.library;

import android.content.Context;

import androidx.annotation.Nullable;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.LibraryItemView;
import com.sketchware.remod.R;

public class ExcludeBuiltInLibrariesLibraryItemView extends LibraryItemView {
    private final String sc_id;

    public ExcludeBuiltInLibrariesLibraryItemView(Context context, String sc_id) {
        super(context);
        this.sc_id = sc_id;
    }

    @Override
    public void setData(@Nullable ProjectLibraryBean projectLibraryBean) {
        boolean excludingEnabled = ExcludeBuiltInLibrariesActivity.isExcludingEnabled(sc_id);
        icon.setImageResource(R.drawable.ic_detail_setting_48dp);
        title.setText(ExcludeBuiltInLibrariesActivity.getItemTitle());
        description.setText(ExcludeBuiltInLibrariesActivity.getItemDescription());
        enabled.setText(excludingEnabled ? "ON" : "OFF");
        enabled.setSelected(excludingEnabled);
    }
}
