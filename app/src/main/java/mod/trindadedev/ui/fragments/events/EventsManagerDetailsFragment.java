package mod.trindadedev.ui.fragments.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.FragmentEventsManagerDetailsBinding;

import mod.trindadedev.ui.fragments.BaseFragment;

public class EventsManagerDetailsFragment extends BaseFragment {

     private FragmentEventsManagerDetailsBinding binding;

     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          binding = FragmentEventsManagerDetailsBinding.inflate(inflater, container, false);
          return binding.getRoot();
     }

     @Override
     public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
          super.onViewCreated(view, savedInstanceState);
          configureToolbar(binding.toolbar);
     }
}