package mod.remaker.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.sketchware.remod.R;

import mod.remaker.settings.fragment.RootSettingsFragment;

public class ExperimentalSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experimental_settings);
        switchFragment(new RootSettingsFragment(), false);
    }

    public void switchFragment(PreferenceFragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment activeFragment = getSupportFragmentManager().findFragmentById(R.id.settings_fragment_container);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        if (activeFragment != null) {
            transaction.hide(activeFragment);
        }
        transaction.add(R.id.settings_fragment_container, fragment);
        transaction.commit();
    }
}
