package a.a.a;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mod.hilal.saif.components.ComponentExtraCode;

public class Hx {

    private final ProjectFileBean projectFileBean;
    private final jq jq;
    private final ArrayList<c> c = new ArrayList<>();
    private final ArrayList<c> d = new ArrayList<>();
    private final ArrayList<a> e = new ArrayList<>();
    private final ArrayList<c> f = new ArrayList<>();
    private final ArrayList<c> g = new ArrayList<>();
    private final ArrayList<b> h = new ArrayList<>();
    private final ArrayList<String> imports = new ArrayList<>();
    private final HashMap<String, String> j = new HashMap<>();
    public String k = "";
    public String l = "";

    public Hx(jq jq, ProjectFileBean projectFileBean, eC eC) {
        this.jq = jq;
        this.projectFileBean = projectFileBean;

        ArrayList<ViewBean> views = new ArrayList<>(eC.d(projectFileBean.getXmlName()));
        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
            ViewBean fab = eC.h(projectFileBean.getXmlName());
            views.add(fab);
        }
        for (ViewBean view : views) {
            c.add(new c(this, view.id, view.getClassInfo()));
        }

        ArrayList<ComponentBean> components = eC.e(projectFileBean.getJavaName());
        for (ComponentBean componentBean : components) {
            int type = componentBean.type;
            if (type == ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH || type == ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD) {
                f.add(new c(this, componentBean.componentId, componentBean.getClassInfo()));
            } else {
                d.add(new c(this, componentBean.componentId, componentBean.getClassInfo()));
            }
        }

        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
            ArrayList<ViewBean> drawerViews = eC.d(projectFileBean.getDrawerXmlName());
            for (ViewBean view : drawerViews) {
                g.add(new c(this, "_drawer_" + view.id, view.getClassInfo()));
            }
        }

        a(eC.g(projectFileBean.getJavaName()), eC.b(projectFileBean.getJavaName()));
    }

    public String getOnActivityResultSwitchCases() {
        StringBuilder sb = new StringBuilder(4096);
        for (b value : h) {
            String code = value.getCode();
            if (sb.length() > 0 && code.length() > 0) {
                sb.append(Jx.EOL);
                sb.append(Jx.EOL);
            }
            sb.append(code);
        }
        return sb.toString();
    }

    private void a(int targetType, String targetId, String eventName, String eventLogic) {
        boolean hasOnSuccessLogic = eventName.equals("onPictureTaken") || eventName.equals("onAccountPicker") || eventName.equals("onFilesPicked");
        boolean hasOnCancelledLogic = eventName.equals("onPictureTakenCancel") || eventName.equals("onFilesPickedCancel") || eventName.equals("onAccountPickerCancelled");

        boolean alreadyRegistered = false;
        for (b next : h) {
            if (next.componentName.equals(targetId)) {
                if (hasOnSuccessLogic) {
                    next.setOnSuccessLogic(eventLogic);
                    return;
                } else if (hasOnCancelledLogic) {
                    next.setOnCancelledLogic(eventLogic);
                    return;
                } else {
                    alreadyRegistered = true;
                }
            }
        }
        if (!alreadyRegistered) {
            b bVar = new b(targetType, targetId);
            if (hasOnSuccessLogic) {
                bVar.setOnSuccessLogic(eventLogic);
            } else if (hasOnCancelledLogic) {
                bVar.setOnCancelledLogic(eventLogic);
            }
            h.add(bVar);
        }
    }

    private void a(String eventName, String eventLogic) {
        a target = null;

        for (a next : e) {
            if (next.name.equals(eventName)) {
                target = next;
                break;
            }
        }

        if (target == null) {
            target = new a(eventName);
            e.add(target);
        }
        target.setLogic(eventLogic);
    }

    public void a(String eventName, String viewType, String viewId) {
        if (!j.containsKey(eventName)) {
            j.put(eventName, Lx.b(eventName, viewType, viewId));
        }
    }

    private void a(ArrayList<EventBean> events, HashMap<String, ArrayList<BlockBean>> logicBlocks) {
        for (EventBean eventBean : events) {
            ArrayList<BlockBean> eventLogicBlocks = logicBlocks.get(eventBean.targetId + "_" + eventBean.eventName);
            String eventLogic = (eventLogicBlocks == null || eventLogicBlocks.size() == 0) ? "" :
                    new Fx(projectFileBean.getActivityName(), jq, eventBean.eventName, eventLogicBlocks).a();

            switch (eventBean.eventType) {
                case EventBean.EVENT_TYPE_VIEW:
                    e(eventBean.targetId, eventBean.eventName, eventLogic);
                    break;

                case EventBean.EVENT_TYPE_COMPONENT:
                    switch (eventBean.targetType) {
                        case ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH:
                        case ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD:
                            d(eventBean.targetId, eventBean.eventName, eventLogic);
                            break;

                        case ComponentBean.COMPONENT_TYPE_CAMERA:
                        case ComponentBean.COMPONENT_TYPE_FILE_PICKER:
                        case ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN:
                            a(eventBean.targetType, eventBean.targetId, eventBean.eventName, eventLogic);
                            break;

                        default:
                            b(eventBean.targetId, eventBean.eventName, eventLogic);
                    }
                    break;

                case EventBean.EVENT_TYPE_ACTIVITY:
                    a(eventBean.eventName, eventLogic);
                    break;

                case EventBean.EVENT_TYPE_DRAWER_VIEW:
                    c(eventBean.targetId, eventBean.eventName, eventLogic);
                    break;

                default:
            }
        }
    }

    public String b() {
        StringBuilder sb = new StringBuilder(4096);
        for (Map.Entry<String, String> entry : j.entrySet()) {
            String name = entry.getKey();
            String logic = entry.getValue();

            boolean found = false;
            for (a next : e) {
                if (next.name.equals(name)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                a a = new a(name);
                a.setLogic(logic);
                e.add(a);
            }
        }

        for (a value : e) {
            String code = value.getCode();
            if (sb.length() > 0 && code.length() > 0) {
                sb.append(Jx.EOL);
                sb.append(Jx.EOL);
            }
            sb.append(code);
        }
        return sb.toString();
    }

    private void b(String targetId, String eventName, String eventLogic) {
        for (c next : d) {
            if (next.a.equals(targetId)) {
                next.a(targetId, eventName, eventLogic);
                return;
            }
        }
    }

    public String c() {
        StringBuilder sb = new StringBuilder(4096);
        ComponentExtraCode componentExtraCode = new ComponentExtraCode(this, sb);
        for (c next : d) {
            componentExtraCode.s(next.a());
        }
        return sb.toString();
    }

    private void c(String targetId, String eventName, String eventLogic) {
        for (c next : g) {
            if (next.a.equals("_drawer_" + targetId)) {
                next.a(targetId, eventName, eventLogic);
                return;
            }
        }
    }

    public String d() {
        StringBuilder sb = new StringBuilder(4096);
        for (c next : g) {
            String a2 = next.a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append(Jx.EOL);
                sb.append(Jx.EOL);
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    private void d(String targetId, String eventName, String eventLogic) {
        for (c next : f) {
            if (next.a.equals(targetId)) {
                next.a(targetId, eventName, eventLogic);
                return;
            }
        }
    }

    /**
     * @return {@link Hx#imports}
     */
    public ArrayList<String> getImports() {
        return imports;
    }

    private void e(String targetId, String eventName, String eventLogic) {
        for (c next : c) {
            if (next.a.equals(targetId)) {
                next.a(targetId, eventName, eventLogic);
                return;
            }
        }
    }

    public String f() {
        StringBuilder sb = new StringBuilder(4096);
        for (c next : f) {
            next.b();
            String a2 = next.a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append(Jx.EOL);
                sb.append(Jx.EOL);
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    public String g() {
        StringBuilder sb = new StringBuilder(4096);
        for (c value : c) {
            String a2 = value.a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append(Jx.EOL);
                sb.append(Jx.EOL);
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    private static class a {

        private final String name;
        private String logic = "";
        private String targetId = "";

        private a(String name) {
            this.name = name;
        }

        private String getCode() {
            return Lx.getEventCode(targetId, name, logic);
        }

        private void setLogic(String logic) {
            this.logic = logic;
        }

        private void setTargetId(String targetId) {
            this.targetId = targetId;
        }
    }

    private static class b {

        private final int componentId;
        private final String componentName;
        private String onSuccessCode = "";
        private String onCancelledCode = "";

        private b(int componentId, String componentName) {
            this.componentId = componentId;
            this.componentName = componentName;
        }

        private void setOnCancelledLogic(String onCancelledLogic) {
            this.onCancelledCode = onCancelledLogic;
        }

        private void setOnSuccessLogic(String onSuccessLogic) {
            this.onSuccessCode = onSuccessLogic;
        }

        private String getCode() {
            return Lx.a(componentId, componentName, onSuccessCode, onCancelledCode);
        }
    }

    private static class c {

        private final Hx d;
        private final String a;
        private final ArrayList<d> c = new ArrayList<>();

        private c(Hx hx, String str, Gx gx) {
            this.d = hx;
            this.a = str;

            String[] listeners = oq.b(gx);
            if (listeners.length > 0) {
                for (String str2 : listeners) {
                    /* Found functionally same instructions in vanilla Sketchware, keep it this way */
                    if (!c.contains(str2)) {
                        c.add(new d(str2));
                    }
                }

                switch (gx.a()) {
                    case "FirebaseDB":
                    case "FirebaseStorage":
                    case "FirebaseAuth":
                    case "Gyroscope":
                    case "WebView":
                    case "InterstitialAd":
                    case "RequestNetwork":
                    case "BluetoothConnect":
                        for (d value : c) {
                            value.b = true;
                        }
                        break;

                    default:
                }
            }
        }

        private String a() {
            StringBuilder sb = new StringBuilder(4096);
            for (d value : c) {
                String a2 = value.a(a);
                if (sb.length() > 0 && a2.length() > 0) {
                    sb.append(Jx.EOL);
                    sb.append(Jx.EOL);
                }
                sb.append(a2);
            }
            return sb.toString();
        }

        private void a(String targetId, String eventName, String eventLogic) {
            for (d d : c) {
                for (a a : d.c) {
                    if (a.name.equals(eventName)) {
                        a.setLogic(eventLogic);
                        a.setTargetId(targetId);
                        d.b = true;
                    }
                }

                if (d.b) {
                    this.d.imports.addAll(mq.d(d.a));
                }
            }
        }

        private void b() {
        }
    }

    private static class d {

        private final String a;
        private final ArrayList<a> c;
        /**
         * Probably "if associated to a Component"/"got its code added"
         */
        private boolean b = false;

        private d(String str) {
            a = str;
            c = new ArrayList<>();
            for (String eventName : oq.b(str)) {
                c.add(new a(eventName));
            }
        }

        private String a(String str) {
            if (!b) {
                return "";
            }
            StringBuilder sb = new StringBuilder(4096);
            for (a value : c) {
                String code = value.getCode();
                if (sb.length() > 0 && code.length() > 0) {
                    sb.append(Jx.EOL);
                    sb.append(Jx.EOL);
                }
                sb.append(code);
            }
            return Lx.d(a, str, sb.toString());
        }
    }
}
