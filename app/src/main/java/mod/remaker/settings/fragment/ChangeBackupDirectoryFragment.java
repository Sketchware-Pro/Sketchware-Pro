package mod.remaker.settings.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sketchware.remod.databinding.ChangeBackupDirectoryFragmentBinding;

import mod.remaker.settings.PreferenceFragment;

public class ChangeBackupDirectoryFragment extends PreferenceFragment {
    @Override
    public String getTitle(Context context) {
        return "Backup Directory";
    }

    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container) {
        ChangeBackupDirectoryFragmentBinding binding = ChangeBackupDirectoryFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
