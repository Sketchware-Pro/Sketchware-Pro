package mod.hey.studios.project.stringfog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import pro.sketchware.databinding.FragmentStringfogManagerBinding;

public class ManageStringFogFragment extends BottomSheetDialogFragment {

    private FragmentStringfogManagerBinding binding;
    private StringfogHandler stringfogHandler;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStringfogManagerBinding.inflate(inflater, container, false);
        initializeLogic();
        return binding.getRoot();
    }

    private void initializeLogic() {
        stringfogHandler = new StringfogHandler(requireActivity().getIntent().getStringExtra("sc_id"));
        binding.swPgEnabled.setChecked(stringfogHandler.isStringfogEnabled());
        binding.swPgEnabled.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            stringfogHandler.setStringfogEnabled(isChecked);
            setStringFogStatus(isChecked);
        });
        setStringFogStatus(stringfogHandler.isStringfogEnabled());
    }

    private void setStringFogStatus(boolean enabled) {
        if (enabled) {
            binding.swPgEnabled.setText("StringFog is enabled");
        } else {
            binding.swPgEnabled.setText("StringFog is disabled");
        }
    }
}