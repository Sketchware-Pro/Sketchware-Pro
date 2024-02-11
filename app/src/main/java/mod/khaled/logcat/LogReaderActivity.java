package mod.khaled.logcat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.BackEventCompat;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.elevation.SurfaceColors;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.ActivityLogcatreaderBinding;
import com.sketchware.remod.databinding.EasyDeleteEdittextBinding;
import com.sketchware.remod.databinding.ViewLogcatItemBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mod.SketchwareUtil;
import mod.hasrat.lib.BaseTextWatcher;
import mod.hey.studios.util.Helper;

public class LogReaderActivity extends AppCompatActivity {

    private final BroadcastReceiver logger = new Logger();
    private final Pattern logPattern = Pattern.compile("^(.*\\d) ([VADEIW]) (.*): (.*)");
    private String pkgFilter = "";
    private boolean autoScroll = true;

    private final ArrayList<HashMap<String, Object>> mainList = new ArrayList<>();
    private ArrayList<String> pkgFilterList = new ArrayList<>();

    private ActivityLogcatreaderBinding binding;
    private BottomSheetBehavior<View> persistentBottomSheetBehavior;

    private final OnBackPressedCallback persistentBottomSheetBackCallback =
        new OnBackPressedCallback(/* enabled= */ false) {
            @Override
            public void handleOnBackStarted(@NonNull BackEventCompat backEvent) {
                persistentBottomSheetBehavior.startBackProgress(backEvent);
            }
    
            @Override
            public void handleOnBackProgressed(@NonNull BackEventCompat backEvent) {
                persistentBottomSheetBehavior.updateBackProgress(backEvent);
            }
    
            @Override
            public void handleOnBackPressed() {
                persistentBottomSheetBehavior.handleBackInvoked();
            }
    
            @Override
            public void handleOnBackCancelled() {
                persistentBottomSheetBehavior.cancelBackProgress();
            }
        };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);

        binding = ActivityLogcatreaderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialize();
    }

    private void initialize() {
        binding.logsRecyclerView.setAdapter(new Adapter(new ArrayList<>()));

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sketchware.remod.ACTION_NEW_DEBUG_LOG");
        registerReceiver(logger, intentFilter);

        final View decorView = getWindow().getDecorView();
        ViewCompat.setOnApplyWindowInsetsListener(decorView, (v, insets) -> {
            Insets systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            binding.appBarLayout.setPadding(
                    binding.appBarLayout.getPaddingLeft(),
                    systemBarsInsets.top,
                    binding.appBarLayout.getPaddingRight(),
                    binding.appBarLayout.getPaddingBottom());

            binding.optionsSheet.setPadding(
                    binding.optionsSheet.getPaddingLeft(),
                    binding.optionsSheet.getPaddingTop(),
                    binding.optionsSheet.getPaddingRight(),
                    systemBarsInsets.bottom);

            persistentBottomSheetBehavior.setPeekHeight(
                    getResources().getDimensionPixelSize(R.dimen.logcat_bottom_sheet_peek_height) + systemBarsInsets.bottom,
                    true);
            return WindowInsetsCompat.CONSUMED;
        });

        persistentBottomSheetBehavior = BottomSheetBehavior.from(binding.optionsSheet);
        persistentBottomSheetBehavior.addBottomSheetCallback(createBottomSheetCallback());

        binding.optionsSheet.post(() -> {
            if (persistentBottomSheetBehavior != null) {
                persistentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }

            int state = persistentBottomSheetBehavior.getState();
            updateBackHandlingEnabled(state);
        });
        setupBackHandling();

        binding.topAppBar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        binding.autoScrollSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            autoScroll = isChecked;
            if (autoScroll) {
                binding.autoScrollSwitch.setText("Auto scroll enabled");
                Objects.requireNonNull(binding.logsRecyclerView.getLayoutManager()).scrollToPosition(Objects.requireNonNull(binding.logsRecyclerView.getAdapter()).getItemCount() - 1);
            } else {
                binding.autoScrollSwitch.setText("Auto scroll disabled");
            }
        });

        binding.scrollSwitchLayout.setOnClickListener(view -> binding.autoScrollSwitch.performClick());

        binding.filterSwitchLayout.setOnClickListener(view -> showFilterDialog());

        binding.clearSwitchLayout.setOnClickListener(view -> {
            mainList.clear();
            ((Adapter) Objects.requireNonNull(binding.logsRecyclerView.getAdapter())).deleteAll();
        });

        binding.searchInput.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String _charSeq = s.toString();
                if (_charSeq.equals("") && (pkgFilterList.size() == 0)) {
                    binding.logsRecyclerView.setAdapter(new Adapter(mainList));
                } else {
                    ArrayList<HashMap<String, Object>> filteredList = new ArrayList<>();
                    for (HashMap<String, Object> m : mainList) {
                        if (pkgFilterList.size() != 0) {
                            if (m.containsKey("pkgName") && pkgFilterList.contains(m.get("pkgName").toString())) {
                                if (m.get("logRaw").toString().toLowerCase().contains(_charSeq.toLowerCase())) {
                                    filteredList.add(m);
                                }
                            }
                        } else if (m.get("logRaw").toString().toLowerCase().contains(_charSeq.toLowerCase())) {
                            filteredList.add(m);
                        }
                    }
                    binding.logsRecyclerView.setAdapter(new Adapter(filteredList));
                }
            }
        });
    }

    private void setupBackHandling() {
        getOnBackPressedDispatcher().addCallback(this, persistentBottomSheetBackCallback);
        persistentBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                updateBackHandlingEnabled(newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
        });
    }

    private void updateBackHandlingEnabled(int state) {
        switch (state) {
            case BottomSheetBehavior.STATE_EXPANDED:
            case BottomSheetBehavior.STATE_HALF_EXPANDED:
                persistentBottomSheetBackCallback.setEnabled(true);
                break;
            case BottomSheetBehavior.STATE_COLLAPSED:
            case BottomSheetBehavior.STATE_HIDDEN:
                persistentBottomSheetBackCallback.setEnabled(false);
                break;
            case BottomSheetBehavior.STATE_DRAGGING:
            case BottomSheetBehavior.STATE_SETTLING:
            default:
                break;
        }
    }

    private BottomSheetBehavior.BottomSheetCallback createBottomSheetCallback() {
        return new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.dimView.setOnClickListener(view -> persistentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED));
                    binding.dimView.setClickable(true);
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.dimView.setOnClickListener(null);
                    binding.dimView.setClickable(false);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                binding.dimView.setAlpha(slideOffset);
            }
        };
    }

    void showFilterDialog() {
        persistentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        var dialogBinding = EasyDeleteEdittextBinding.inflate(getLayoutInflater());
        View view = dialogBinding.getRoot();

        dialogBinding.imgDelete.setVisibility(View.GONE);

        var builder = new MaterialAlertDialogBuilder(this)
                .setTitle("Filter by package name")
                .setMessage("For multiple package names, separate them with a comma (,).")
                .setIcon(R.drawable.ic_filter_24)
                .setView(view)
                .setPositiveButton("Apply", (dialog, which) -> {
                    pkgFilter = dialogBinding.easyEdInput.getText().toString();
                    pkgFilterList = new ArrayList<>(Arrays.asList(pkgFilter.split(",")));
                    binding.searchInput.setText(binding.searchInput.getText().toString());
                })
                .setNegativeButton("Cancel", null)
                .create();

        builder.show();
    }

    private class Logger extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            HashMap<String, Object> map = new HashMap<>();
            if (intent.hasExtra("log") && (intent.getStringExtra("log") != null)) {
                if (intent.hasExtra("packageName")) {
                    map.put("pkgName", intent.getStringExtra("packageName"));
                }
                map.put("logRaw", intent.getStringExtra("log"));
                if (intent.getStringExtra("log") == null) return;

                Matcher matcher = logPattern.matcher(intent.getStringExtra("log"));
                if (matcher.matches()) {
                    //group 1 = pid, time stuff & idk
                    //group 2 = log type (a,d,e,i,w)
                    //group 3 = log tag,headers
                    //group 4 = everything else
                    map.put("date", matcher.group(1).trim());
                    map.put("type", matcher.group(2).trim());
                    map.put("header", matcher.group(3));
                    map.put("body", matcher.group(4));
                    map.put("culturedLog", "true");
                }

                mainList.add(map);
                if (pkgFilterList.size() == 0) {
                    if (!binding.searchInput.getText().toString().equals("")) {
                        if (map.get("logRaw").toString().toLowerCase().contains(binding.searchInput.getText().toString().toLowerCase())) {
                            ((Adapter) binding.logsRecyclerView.getAdapter()).updateList(map);
                        }
                    } else {
                        ((Adapter) binding.logsRecyclerView.getAdapter()).updateList(map);
                    }
                } else if (map.containsKey("pkgName") && pkgFilterList.contains(map.get("pkgName").toString())) {
                    if (!binding.searchInput.getText().toString().equals("")) {
                        if (map.get("logRaw").toString().toLowerCase().contains(binding.searchInput.getText().toString().toLowerCase())) {
                            ((Adapter) binding.logsRecyclerView.getAdapter()).updateList(map);
                        }
                    } else {
                        ((Adapter) binding.logsRecyclerView.getAdapter()).updateList(map);
                    }
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.searchInput.clearFocus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(logger);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private final ArrayList<HashMap<String, Object>> data;

        public void updateList(final HashMap<String, Object> _map) {
            data.add(_map);
            binding.logsRecyclerView.getAdapter().notifyItemInserted(data.size() + 1);

            if (autoScroll) {
                ((LinearLayoutManager) binding.logsRecyclerView.getLayoutManager()).scrollToPosition(data.size() - 1);
                binding.appBarLayout.setExpanded(false);
            }

            if (data.size() > 0) {
                if (persistentBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    persistentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    persistentBottomSheetBehavior.setHideable(false);
                }
                binding.noContentLayout.setVisibility(View.GONE);
            } else {
                binding.noContentLayout.setVisibility(View.VISIBLE);
            }
        }

        public void deleteAll() {
            data.clear();
            binding.logsRecyclerView.getAdapter().notifyDataSetChanged();
            binding.noContentLayout.setVisibility(View.VISIBLE);
            persistentBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        public Adapter(ArrayList<HashMap<String, Object>> data) {
            this.data = data;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            var listBinding = ViewLogcatItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            var layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            listBinding.getRoot().setLayoutParams(layoutParams);
            return new ViewHolder(listBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            var binding = holder.listBinding;

            if (data.get(position).containsKey("pkgName")) {
                binding.pkgName.setText(data.get(position).get("pkgName").toString());
                binding.pkgName.setVisibility(View.VISIBLE);
            } else {
                binding.pkgName.setVisibility(View.GONE);
            }
            if (data.get(position).containsKey("culturedLog")) {
                binding.dateHeader.setVisibility(View.VISIBLE);
                binding.type.setText(data.get(position).get("type").toString());
                binding.dateHeader.setText(data.get(position).get("date").toString() + " | " + (data.get(position).get("header").toString()));
                switch (Objects.requireNonNull(data.get(position).get("type")).toString()) {
                    case "A" -> binding.type.setBackgroundColor(0xFF9C27B0);
                    case "D" -> binding.type.setBackgroundColor(0xFF2196F3);
                    case "E" -> binding.type.setBackgroundColor(0xFFF44336);
                    case "I" -> binding.type.setBackgroundColor(0xFF4CAF50);
                    case "V" -> binding.type.setBackgroundColor(0xFF000000);
                    case "W" -> binding.type.setBackgroundColor(0xFFFFC107);
                    default -> {
                        binding.type.setBackgroundColor(0xFF000000);
                        binding.type.setText("U");
                    }
                }
                binding.log.setText(data.get(position).get("body").toString());
                try {
                    if (data.get(position).get("date").toString().equals(data.get(position + 1).get("date").toString())) {
//                        binding.divider.setVisibility(View.GONE);
                        try {
                            if (data.get(position).get("pkgName").toString().equals(data.get(position + 1).get("pkgName").toString())) {
                                binding.pkgName.setVisibility(View.GONE);
                            } else {
                                binding.pkgName.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception ignored) {
                            binding.pkgName.setVisibility(View.VISIBLE);
                        }
                        try {
                            if (data.get(position).get("header").toString().equals(data.get(position + 1).get("header").toString())) {
                                binding.dateHeader.setVisibility(View.GONE);
                            } else {
                                binding.dateHeader.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception ignored) {
                            binding.dateHeader.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception ignored) {
                }
            } else {
                binding.log.setText(data.get(position).get("logRaw").toString());
                binding.type.setBackgroundColor(0xFF000000);
                binding.type.setText("U");
                binding.dateHeader.setVisibility(View.GONE);
            }
            binding.getRoot().setOnLongClickListener(v -> {
                SketchwareUtil.toast("Copied to clipboard");
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", data.get(position).get("logRaw").toString()));
                return true;
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            private final ViewLogcatItemBinding listBinding;

            public ViewHolder(@NonNull ViewLogcatItemBinding listBinding) {
                super(listBinding.getRoot());
                this.listBinding = listBinding;
            }
        }
    }
}
