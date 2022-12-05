package a.a.a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint({"ViewConstructor", "ResourceType"})
public class gt extends LinearLayout {

    private HashMap<Integer, ArrayList<VariableItem>> allVariablesWithCategeryIndex;
    private ArrayList<VariableItem> variableItems;
    private ArrayList<VariableItem> viewsVariableList;
    private ArrayList<VariableItem> componentsVariableList;
    private VeriableItemAdapter veriableItemAdapter;
    private TextView k;
    private Dialog dialog;
    private VariableItem selectedVariableItem;

    public gt(Activity var1) {
        super(var1);
        initialize(var1);
    }

    private void setPreview(VariableItem variableItem) {
        selectedVariableItem = variableItem;
        k.setText(getTypeName(variableItem.type, variableItem.name));
    }

    private String getTypeName(String type, String name) {
        byte var4;
        byte var5;
        label77:
        {
            int var3 = type.hashCode();
            var4 = 0;
            if (var3 != 98) {
                if (var3 != 100) {
                    if (var3 == 115 && type.equals("s")) {
                        var5 = 2;
                        break label77;
                    }
                } else if (type.equals("d")) {
                    var5 = 1;
                    break label77;
                }
            } else if (type.equals("b")) {
                var5 = 0;
                break label77;
            }

            var5 = -1;
        }

        if (var5 != 0) {
            if (var5 != 1) {
                if (var5 != 2) {
                    type = kq.b(name);
                } else {
                    type = xB.b().a(getContext(), 2131625592);
                }
            } else {
                type = xB.b().a(getContext(), 2131625591);
            }
        } else {
            type = xB.b().a(getContext(), 2131625586);
        }

        label67:
        {
            switch (name.hashCode()) {
                case -823676088:
                    if (name.equals("varInt")) {
                        var5 = var4;
                        break label67;
                    }
                    break;
                case -823672651:
                    if (name.equals("varMap")) {
                        var5 = 3;
                        break label67;
                    }
                    break;
                case -823666294:
                    if (name.equals("varStr")) {
                        var5 = 2;
                        break label67;
                    }
                    break;
                case 181944945:
                    if (name.equals("listInt")) {
                        var5 = 4;
                        break label67;
                    }
                    break;
                case 181948382:
                    if (name.equals("listMap")) {
                        var5 = 6;
                        break label67;
                    }
                    break;
                case 181954739:
                    if (name.equals("listStr")) {
                        var5 = 5;
                        break label67;
                    }
                    break;
                case 235637425:
                    if (name.equals("varBool")) {
                        var5 = 1;
                        break label67;
                    }
            }

            var5 = -1;
        }

        switch (var5) {
            case 0:
                type = xB.b().a(getContext(), 2131625591);
                break;
            case 1:
                type = xB.b().a(getContext(), 2131625586);
                break;
            case 2:
                type = xB.b().a(getContext(), 2131625592);
                break;
            case 3:
                type = xB.b().a(getContext(), 2131625590);
                break;
            case 4:
                type = xB.b().a(getContext(), 2131625588);
                break;
            case 5:
                type = xB.b().a(getContext(), 2131625589);
                break;
            case 6:
                type = xB.b().a(getContext(), 2131625587);
        }

        return type;
    }

    private void a() {
        componentsVariableList.add(new VariableItem("m", "intent", 2131166254));
        componentsVariableList.add(new VariableItem("m", "file", 2131166268));
        componentsVariableList.add(new VariableItem("m", "calendar", 2131166238));
        componentsVariableList.add(new VariableItem("m", "vibrator", 2131166277));
        componentsVariableList.add(new VariableItem("m", "timer", 2131166276));
        componentsVariableList.add(new VariableItem("m", "dialog", 2131166235));
        componentsVariableList.add(new VariableItem("m", "mediaplayer", 2131166259));
        componentsVariableList.add(new VariableItem("m", "soundpool", 2131166269));
        componentsVariableList.add(new VariableItem("m", "objectanimator", 2131166262));
        componentsVariableList.add(new VariableItem("m", "firebase", 2131166245));
        componentsVariableList.add(new VariableItem("m", "firebaseauth", 2131166245));
        componentsVariableList.add(new VariableItem("m", "firebasestorage", 2131166245));
        componentsVariableList.add(new VariableItem("m", "camera", 2131166240));
        componentsVariableList.add(new VariableItem("m", "filepicker", 2131166244));
        componentsVariableList.add(new VariableItem("m", "requestnetwork", 2131166261));
        componentsVariableList.add(new VariableItem("m", "texttospeech", 2131166274));
        componentsVariableList.add(new VariableItem("m", "speechtotext", 2131166271));
        componentsVariableList.add(new VariableItem("m", "locationmanager", 2131166258));
        componentsVariableList.add(new VariableItem("m", "videoad", 2131166295));
        componentsVariableList.add(new VariableItem("m", "progressdialog", 2131166296));
        componentsVariableList.add(new VariableItem("m", "timepickerdialog", 2131166276));
        componentsVariableList.add(new VariableItem("m", "notification", 2131166298));
    }

    private void initialize(Activity activity) {
        wB.a(activity, this, 2131427770);
        k = findViewById(2131232082);
        LinearLayout j = (LinearLayout) wB.a(activity, 2131427771);
        RecyclerView f = j.findViewById(2131232303);
        RecyclerView g = j.findViewById(2131232304);
        ((TextView) j.findViewById(2131232195)).setText(xB.b().a(activity, 2131625507));
        allVariablesWithCategeryIndex = new HashMap<>();
        variableItems = new ArrayList<>();
        viewsVariableList = new ArrayList<>();
        componentsVariableList = new ArrayList<>();
        b();
        c();
        a();
        allVariablesWithCategeryIndex.put(0, variableItems);
        allVariablesWithCategeryIndex.put(1, viewsVariableList);
        allVariablesWithCategeryIndex.put(2, componentsVariableList);
        f.setHasFixedSize(true);
        LinearLayoutManager var4 = new LinearLayoutManager(activity, 1, false);
        f.setLayoutManager(var4);
        CategeryItemAdapter h = new CategeryItemAdapter();
        h.setData(allVariablesWithCategeryIndex);
        f.setAdapter(h);
        g.setHasFixedSize(true);
        var4 = new LinearLayoutManager(activity, 1, false);
        g.setLayoutManager(var4);
        veriableItemAdapter = new VeriableItemAdapter();
        g.setAdapter(veriableItemAdapter);
        h.layoutPosition = 0;
        veriableItemAdapter.setData(allVariablesWithCategeryIndex.get(0));
        setPreview(variableItems.get(0));
        dialog = new Dialog(activity);
        if (j.getParent() != null) {
            ((ViewGroup) j.getParent()).removeView(j);
        }

        dialog.setContentView(j);
        findViewById(2131230931).setOnClickListener(view -> {
            if (!mB.a()) {
                d();
            }
        });
    }

    private void b() {
        variableItems.add(new VariableItem("b", "", 2131165876));
        variableItems.add(new VariableItem("d", "", 2131166000));
        variableItems.add(new VariableItem("s", "", 2131165191));
        variableItems.add(new VariableItem("m", "varMap", 2131165785));
        variableItems.add(new VariableItem("m", "listInt", 2131165783));
        variableItems.add(new VariableItem("m", "listStr", 2131165783));
        variableItems.add(new VariableItem("m", "listMap", 2131165783));
    }

    private void c() {
        viewsVariableList.add(new VariableItem("m", "view", 2131165939));
        viewsVariableList.add(new VariableItem("m", "textview", 2131166275));
        viewsVariableList.add(new VariableItem("m", "imageview", 2131166253));
        viewsVariableList.add(new VariableItem("m", "checkbox", 2131166241));
        viewsVariableList.add(new VariableItem("m", "switch", 2131166273));
        viewsVariableList.add(new VariableItem("m", "listview", 2131166257));
        viewsVariableList.add(new VariableItem("m", "spinner", 2131166272));
        viewsVariableList.add(new VariableItem("m", "webview", 2131166278));
        viewsVariableList.add(new VariableItem("m", "seekbar", 2131166267));
        viewsVariableList.add(new VariableItem("m", "progressbar", 2131166263));
        viewsVariableList.add(new VariableItem("m", "calendarview", 2131166238));
        viewsVariableList.add(new VariableItem("m", "radiobutton", 2131166264));
        viewsVariableList.add(new VariableItem("m", "ratingbar", 2131166177));
        viewsVariableList.add(new VariableItem("m", "videoview", 2131166259));
        viewsVariableList.add(new VariableItem("m", "searchview", 2131165849));
        viewsVariableList.add(new VariableItem("m", "gridview", 2131165662));
        viewsVariableList.add(new VariableItem("m", "actv", 2131166242));
        viewsVariableList.add(new VariableItem("m", "mactv", 2131166242));
        viewsVariableList.add(new VariableItem("m", "viewpager", 2131166265));
        viewsVariableList.add(new VariableItem("m", "badgeview", 2131166031));
    }

    private void d() {
        dialog.show();
    }

    public Pair<String, String> getSelectedItem() {
        return new Pair<>(selectedVariableItem.type, selectedVariableItem.name);
    }

    private class CategeryItemAdapter extends RecyclerView.a<CategeryItemAdapter.ViewHolder> {

        private HashMap<Integer, ArrayList<VariableItem>> integerArrayListHashMap;
        private int layoutPosition;

        public CategeryItemAdapter() {
            layoutPosition = -1;
        }

        @Override
        public int a() {
            return integerArrayListHashMap.size();
        }

        @Override
        public void b(ViewHolder viewHolder, int position) {
            if (position != 0) {
                if (position != 1) {
                    if (position == 2) {
                        viewHolder.u.setImageResource(2131165504);
                        viewHolder.v.setText(xB.b().a(getContext(), 2131624979));
                    }
                } else {
                    viewHolder.u.setImageResource(2131166257);
                    viewHolder.v.setText(xB.b().a(getContext(), 2131625046));
                }
            } else {
                viewHolder.u.setImageResource(2131166227);
                viewHolder.v.setText(xB.b().a(getContext(), 2131625045));
            }

            if (layoutPosition == position) {
                viewHolder.t.setBackgroundColor(-1);
            } else {
                viewHolder.t.setBackgroundColor(getResources().getColor(2131034221));
            }

        }

        public void setData(HashMap<Integer, ArrayList<VariableItem>> integerArrayListHashMap) {
            this.integerArrayListHashMap = integerArrayListHashMap;
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(2131427769, parent, false));
        }

        private class ViewHolder extends RecyclerView.v {

            public LinearLayout t;
            public ImageView u;
            public TextView v;

            public ViewHolder(View itemVIew) {
                super(itemVIew);
                t = itemVIew.findViewById(2131230931);
                u = itemVIew.findViewById(2131231090);
                v = itemVIew.findViewById(2131231561);
                itemVIew.setOnClickListener(view -> {
                    layoutPosition = j();
                    veriableItemAdapter.setData(allVariablesWithCategeryIndex.get(layoutPosition));
                    veriableItemAdapter.c();
                    CategeryItemAdapter.this.c();
                });
            }
        }
    }

    private class VariableItem {

        public String type;
        public String name;
        public int icon;

        public VariableItem(String type, String name, int icon) {
            this.type = type;
            this.name = name;
            this.icon = icon;
        }
    }

    private class VeriableItemAdapter extends RecyclerView.a<VeriableItemAdapter.ViewHolder> {

        private ArrayList<VariableItem> variableItems1;

        public VeriableItemAdapter() {
        }

        @Override
        public int a() {
            return variableItems1.size();
        }

        @Override
        public void b(ViewHolder viewHolder, int position) {
            VariableItem variableItem = variableItems1.get(position);
            viewHolder.t.setText(getTypeName(variableItem.type, variableItem.name));
            viewHolder.u.setImageResource(variableItem.icon);
        }

        public void setData(ArrayList<VariableItem> variableItems) {
            variableItems1 = variableItems;
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(wB.a(getContext(), 2131427772));
        }

        private class ViewHolder extends RecyclerView.v {
            public TextView t;
            public ImageView u;

            public ViewHolder(View itemView) {
                super(itemView);
                t = itemView.findViewById(2131231561);
                u = itemView.findViewById(2131231090);
                itemView.setOnClickListener(view -> {
                    setPreview(allVariablesWithCategeryIndex.get(d).get(j()));
                    dialog.hide();
                });
            }
        }
    }
}
