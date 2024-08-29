package mod.trindadedev.ui.fragments

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;

import mod.hey.studios.util.Helper;

public class BaseFragment extends Fragment {

     public void configureToolbar(MaterialToolbar view) {
          view.setNavigationOnClickListener(Helper.getBackPressedClickListener(getActivity()));
     }
}