package mod.fnmods.chat.ia;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import mod.fnmods.chat.ia.adapter.ChatAdapter;
import pro.sketchware.R;
import pro.sketchware.databinding.ActivityChatIaBinding;
import pro.sketchware.utility.SketchwareUtil;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;


public class ChatIaActivity extends AppCompatActivity {

    private ActivityChatIaBinding binding;
    private ChatAdapter adapter;
    private ArrayList<HashMap<String, Object>> chatList = new ArrayList<>();
    private SoundPool soundPool;
    private int soundSend;
    private static final String PREFS_NAME = "IA_CONFIG";
    private static final String KEY_ACCEPTED = "terms_accepted";
    private static final String KEY_API_URL = "api_url";
    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_IA_TYPE = "ia_type";
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    private int aiResponsesCount = 0;

    private SharedPreferences prefs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatIaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .build();
        soundSend = soundPool.load(this, R.raw.message_envoye_iphone, 1);
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        binding.toolbar.setTitle("");
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowTitleEnabled(false);
        }
        binding.toolbar.setTitle("");
        Glide.with(this)
                .asDrawable()
                .load(R.raw.ia_avatar)
                .into(binding.avatar);

        if (isConnected(this)) {
            actualizarEstadoInternet(true);
        } else {
            actualizarEstadoInternet(false);
        }


        if (!prefs.getBoolean(KEY_ACCEPTED, false)) {
            showTermsDialog();
        } else if (prefs.getString(KEY_API_KEY, "").isEmpty()) {
            showConfigDialog();
        }
        setupChat();
        loadChatHistory();
        aiResponsesCount = prefs.getInt("responses", 0);
        binding.btnSend.setOnClickListener(v -> {
            String text = binding.inputMessage.getText().toString().trim();
            if (!text.isEmpty()) {
                addUserMessage(text);
                binding.inputMessage.setText("");
                soundPool.play(soundSend, 1f, 1f, 1, 0, 1f);
                simulateAIResponse(text);
            }
        });
        binding.avatar.setOnClickListener(v -> showRateLimitsDialog(this));
    }

    private void setupChat() {
        chatList = new ArrayList<>();
        adapter = new ChatAdapter(chatList);
        binding.chatRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.chatRecycler.setAdapter(adapter);
    }
    private void actualizarEstadoInternet(boolean conectado) {
        if (conectado) {
            binding.online.setText("Online");
            binding.btnSend.setEnabled(true);
            binding.online.setTextColor(getColor(R.color.scolor_green_normal));
        } else {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("No Internet Connection")
                    .setCancelable(true)
                    .setMessage("Please check your internet connection and try again.")
                            .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                                    .show();
            binding.online.setText("Offline");
            binding.btnSend.setEnabled(false);
            binding.online.setTextColor(getColor(R.color.scolor_red_02));
        }
    }

    private void addUserMessage(String text) {
        HashMap<String, Object> msg = new HashMap<>();
        msg.put("text", text);
        msg.put("isAI", false);
        chatList.add(msg);
        adapter.notifyItemInserted(chatList.size() - 1);
        binding.chatRecycler.scrollToPosition(chatList.size() - 1);
        saveChatHistory();
    }

    private void simulateAIResponse(String userPrompt) {

        binding.progressIndicator.setVisibility(View.VISIBLE);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int iaType = prefs.getInt(KEY_IA_TYPE, 0);
        String apiUrl = prefs.getString(KEY_API_URL, "");
        String apiKey = prefs.getString(KEY_API_KEY, "");

        Executors.newSingleThreadExecutor().execute(() -> {

            HttpURLConnection conn = null;
            try {

                JSONObject postData = new JSONObject();
                String finalUrl = apiUrl;
                if (iaType == 0) {
                    finalUrl = apiUrl + "?key=" + apiKey;

                    JSONArray contents = new JSONArray();
                    JSONObject contentObj = new JSONObject();
                    JSONArray parts = new JSONArray();

                    parts.put(new JSONObject().put("text", userPrompt));
                    contentObj.put("parts", parts);
                    contents.put(contentObj);

                    postData.put("contents", contents);

                } else {
                    postData.put("model", "gpt-3.5-turbo");

                    JSONArray messages = new JSONArray();
                    messages.put(
                            new JSONObject()
                                    .put("role", "user")
                                    .put("content", userPrompt)
                    );
                    postData.put("messages", messages);
                }
                URL url = new URL(finalUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(20000);
                conn.setDoOutput(true);
                conn.setDoInput(true);

                if (iaType == 1) {
                    conn.setRequestProperty("Authorization", "Bearer " + apiKey);
                }

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = postData.toString().getBytes("UTF-8");
                    os.write(input);
                    os.flush();
                }

                int responseCode = conn.getResponseCode();
                InputStream is;
                if (responseCode >= 200 && responseCode < 300) {
                    is = conn.getInputStream();
                } else {
                    is = conn.getErrorStream();
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                if (responseCode < 200 || responseCode >= 300) {
                    throw new Exception(response.toString());
                }
                String aiResult;
                JSONObject jsonRes = new JSONObject(response.toString());

                if (iaType == 0) {
                    aiResult = jsonRes
                            .getJSONArray("candidates")
                            .getJSONObject(0)
                            .getJSONObject("content")
                            .getJSONArray("parts")
                            .getJSONObject(0)
                            .getString("text");
                } else {
                    aiResult = jsonRes
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                }
                runOnUiThread(() -> {
                    binding.progressIndicator.setVisibility(View.GONE);
                    addAIMessageWithEffect(aiResult.trim());
                    onAIResponseSuccess(aiResult);
                    saveChatHistory();
                });

            } catch (Exception e) {

                runOnUiThread(() -> {
                    binding.progressIndicator.setVisibility(View.GONE);
                    manejarErrorIA(e);
                    Log.e("ChatIA", "Error", e);
                    //SketchwareUtil.toastError("IA Error: " + e.getMessage());
                });

            } finally {
                if (conn != null) conn.disconnect();
            }
        });
    }


    private void onAIResponseSuccess(String response) {
        aiResponsesCount++;
        prefs.edit()
                .putInt("responses", aiResponsesCount)
                .apply();
        updateBadge(aiResponsesCount);
       // manejarErrorIA(response);
    }

    private void manejarErrorIA(Exception e) {
        String msg = e.getMessage();

        if (msg != null && msg.contains("429")) {
            mostrarMensajeIAOcupada();
            bloquearEnvioTemporal();
        } else {
          SketchwareUtil.toastError("IA Error: " + e.getMessage());

        }
    }

    void updateBadge(int count) {
        if (count <= 0) {
            binding.badgeCount.setVisibility(View.GONE);
            return;
        }
        binding.badgeCount.setVisibility(View.VISIBLE);
        if (count >= 20) {
            binding.badgeCount.setText("20");
            binding.badgeCount.setBackgroundTintList(
                    ColorStateList.valueOf(getColor(R.drawable.circle_bg))
            );
        } else {
            binding.badgeCount.setText(String.valueOf(count));
        }
    }

    private void mostrarMensajeIAOcupada() {
        Snackbar.make(binding.getRoot(),
                "\uD83E\uDD16 AI busy. Please try again in a few seconds.Daily limit: 20 requests",
                Snackbar.LENGTH_LONG
        ).show();
    }

    private void bloquearEnvioTemporal() {
        binding.btnSend.setEnabled(false);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            binding.btnSend.setEnabled(true);
        }, 40000); // 40 segundos
    }

    private void addAIMessageWithEffect(String text) {
        HashMap<String, Object> aiMsg = new HashMap<>();
        aiMsg.put("text", "");
        aiMsg.put("isAI", true);
        chatList.add(aiMsg);
        int position = chatList.size() - 1;
        adapter.notifyItemInserted(position);
        binding.chatRecycler.scrollToPosition(position);
        startTypingEffect(text, position);
    }

    private void saveChatHistory() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = new com.google.gson.Gson().toJson(chatList);
        prefs.edit().putString("chat_history_data", json).apply();
       // Log.d("ChatIA", "Historial guardado correctamente");
    }

    private void clearConversation() {
        chatList.clear();
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().remove("chat_history_data").apply();
        adapter.notifyDataSetChanged();
        showWelcomeMessage();
        SketchwareUtil.toast("Delete Succes");
    }


    private void loadChatHistory() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = prefs.getString("chat_history_data", null);

        if (json != null) {
            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType();
            ArrayList<HashMap<String, Object>> savedList = new com.google.gson.Gson().fromJson(json, type);
            chatList.clear();
            chatList.addAll(savedList);
            adapter.notifyDataSetChanged();
            binding.chatRecycler.scrollToPosition(chatList.size() - 1);
        } else {
            if (prefs.getBoolean(KEY_ACCEPTED, false) && !prefs.getString(KEY_API_KEY, "").isEmpty()) {
                showWelcomeMessage();
            }
        }
    }

    private void showWelcomeMessage() {
        if (adapter == null) return;
        String welcomeText = "¡Hola! Soy tu IA de Sketchware Pro. Estoy lista para ayudarte a optimizar tus proyectos o resolver tus dudas. ¿En qué puedo ayudarte hoy?";
        HashMap<String, Object> welcomeMsg = new HashMap<>();
        welcomeMsg.put("text", "");
        welcomeMsg.put("isAI", true);
        chatList.add(welcomeMsg);

        int position = chatList.size() - 1;
        adapter.notifyItemInserted(position);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startTypingEffect(welcomeText, position);
        }, 500);
    }
    public void showRateLimitsDialog(Context context) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.dialog_help_ia, null);
        WebView webView = view.findViewById(R.id.webView);
        MaterialButton btnClose = view.findViewById(R.id.btnClose);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl("https://ai.google.dev/gemini-api/docs/rate-limits");
        MaterialAlertDialogBuilder dialogBuilder =
                new MaterialAlertDialogBuilder(context)
                        .setView(view)
                        .setCancelable(true);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        btnClose.setOnClickListener(v -> dialog.dismiss());
    }



    private void startTypingEffect(String fullText, int position) {
        final StringBuilder currentText = new StringBuilder();
        final int delay = 30;
        Handler handler = new Handler(Looper.getMainLooper());

        for (int i = 0; i < fullText.length(); i++) {
            final int index = i;
            handler.postDelayed(() -> {
                currentText.append(fullText.charAt(index));
                chatList.get(position).put("text", currentText.toString());
                adapter.notifyItemChanged(position);
                if (index == fullText.length() - 1) {
                    chatList.get(position).put("isFinishing", true);
                    chatList.get(position).put("text", fullText);
                    // 3. Guardamos todo el historial en SharedPreferences
                    saveChatHistory();

                    adapter.notifyItemChanged(position);
                }

                if (!binding.chatRecycler.canScrollVertically(1)) {
                    binding.chatRecycler.smoothScrollToPosition(position);
                }
            }, (long) i * delay);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        menu.add(0, 1, 0, "Delete History").setIcon(R.drawable.ic_mtrl_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(0, 2, 0, "Confi IA").setIcon(R.drawable.ic_person_add_grey600_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Delete History IA")
                    .setMessage("Are you sure you want to delete the AI \u200B\u200Bhistory?")
                    .setPositiveButton("DELETE HISTORY", (d, w) -> clearConversation())
                    .setNeutralButton("CANCEL", null)
                    .show();
            return true;
        } else if (item.getItemId() == 2) {
            showConfigDialog();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
    private void showTermsDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Terms and Conditions\")\n" +
                        "\n" +
                        ".setMessage(\"This AI is an assistance tool.\\n\\nLimitations:\\n\" +\n" +
                        "\n" +
                        "\"1. Depends on the stability of the external API.\\n\" +\n" +
                        "\n" +
                        "\"2. Do not share sensitive information.\\n\" +\n" +
                        "\n" +
                        "\"3. Usage consumes tokens from your own Key.\\n\\n\" +\n" +
                        "\n" +
                        "\"Purpose: To assist in development within Sketchware Pro.")
                .setCancelable(false)
                .setPositiveButton("Accept", (d, w) -> {
                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().putBoolean(KEY_ACCEPTED, true).apply();
                    showConfigDialog(); // Al aceptar términos, pedimos la Key
                })
                .setNegativeButton("Cancel", (d, w) -> finish())
                .show();
    }

    private void showConfigDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 30, 50, 0);
        final Spinner spinner = new Spinner(this);
        String[] iaOptions = {"Google Gemini", "ChatGPT (OpenAI)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, iaOptions);
        spinner.setAdapter(adapter);
        layout.addView(new TextView(this) {{ setText("Select the AI \u200B\u200Bengine:"); }});
        layout.addView(spinner);
        final EditText inputUrl = new EditText(this);
        inputUrl.setHint("URL de la API");
        layout.addView(inputUrl);

        final EditText inputKey = new EditText(this);
        inputKey.setHint("API Key");
        inputKey.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(inputKey);


        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        spinner.setSelection(prefs.getInt(KEY_IA_TYPE, 0));
        inputUrl.setText(prefs.getString(KEY_API_URL, "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"));
        inputKey.setText(prefs.getString(KEY_API_KEY, ""));

        // Lógica para cambiar URL según selección
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) { // Gemini
                    inputUrl.setText("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent");
                } else { // ChatGPT
                    inputUrl.setText("https://api.openai.com/v1/chat/completions");
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        new MaterialAlertDialogBuilder(this)
                .setTitle("AI Professional Settings")
                .setView(layout)
                .setPositiveButton("Save", (d, w) -> {
                    prefs.edit()
                            .putInt(KEY_IA_TYPE, spinner.getSelectedItemPosition())
                            .putString(KEY_API_URL, inputUrl.getText().toString().trim())
                            .putString(KEY_API_KEY, inputKey.getText().toString().trim())
                            .apply();
                    SketchwareUtil.toast("Updated settings");
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        initNetworkCallback();

        NetworkRequest request = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        connectivityManager.registerNetworkCallback(request, networkCallback);
        aiResponsesCount = prefs.getInt("responses", 0);
        updateBadge(aiResponsesCount);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    private void initNetworkCallback() {
        connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                runOnUiThread(() -> actualizarEstadoInternet(true));
            }

            @Override
            public void onLost(@NonNull Network network) {
                runOnUiThread(() -> actualizarEstadoInternet(false));
            }
        };
    }

    private boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) return false;

        Network network = cm.getActiveNetwork();
        if (network == null) return false;

        NetworkCapabilities caps = cm.getNetworkCapabilities(network);
        return caps != null && (
                caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        );
    }



}