package a.a.a;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mod.hilal.saif.components.ComponentExtraCode;

public class Hx {

    public ProjectFileBean a;
    public jq b;
    public ArrayList<c> c = new ArrayList<>();
    public ArrayList<c> d = new ArrayList<>();
    public ArrayList<a> e = new ArrayList<>();
    public ArrayList<c> f = new ArrayList<>();
    public ArrayList<c> g = new ArrayList<>();
    public ArrayList<b> h = new ArrayList<>();
    public ArrayList<String> i = new ArrayList<>();
    public HashMap<String, String> j = new HashMap<>();
    public String k = "";
    public String l = "";

    public Hx(jq jq, ProjectFileBean projectFileBean, eC eC) {
        this.b = jq;
        this.a = projectFileBean;

        ArrayList<ViewBean> views = new ArrayList<>(eC.d(projectFileBean.getXmlName()));
        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
            ViewBean fab = eC.h(projectFileBean.getXmlName());
            views.add(fab);
        }
        for (ViewBean view : views) {
            this.c.add(new c(this, view.id, view.getClassInfo()));
        }

        ArrayList<ComponentBean> components = eC.e(projectFileBean.getJavaName());
        for (ComponentBean componentBean : components) {
            int type = componentBean.type;
            if (type == ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH || type == ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD) {
                this.f.add(new c(this, componentBean.componentId, componentBean.getClassInfo()));
            } else {
                this.d.add(new c(this, componentBean.componentId, componentBean.getClassInfo()));
            }
        }

        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
            ArrayList<ViewBean> drawerViews = eC.d(projectFileBean.getDrawerXmlName());
            for (ViewBean view : drawerViews) {
                this.g.add(new c(this, "_drawer_" + view.id, view.getClassInfo()));
            }
        }

        a(eC.g(projectFileBean.getJavaName()), eC.b(projectFileBean.getJavaName()));
    }

    public String a() {
        StringBuilder sb = new StringBuilder(4096);
        for (Hx.b value : this.h) {
            String a2 = value.a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append("\r\n");
                sb.append("\r\n");
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    public final void a(int i2, String str, String str2, String str3) {
        Iterator<b> it = this.h.iterator();
        boolean z = false;
        while (it.hasNext()) {
            b next = it.next();
            if (next.b.equals(str)) {
                if (str2.equals("onPictureTaken") || str2.equals("onAccountPicker") || str2.equals("onFilesPicked")) {
                    next.b(str3);
                    return;
                } else if (str2.equals("onPictureTakenCancel") || str2.equals("onFilesPickedCancel") || str2.equals("onAccountPickerCancelled")) {
                    next.a(str3);
                    return;
                } else {
                    z = true;
                }
            }
        }
        if (!z) {
            b bVar = new b(i2, str);
            if (str2.equals("onPictureTaken") || str2.equals("onFilesPicked") || str2.equals("onAccountPicker")) {
                bVar.b(str3);
            } else if (str2.equals("onPictureTakenCancel") || str2.equals("onFilesPickedCancel") || str2.equals("onAccountPickerCancelled")) {
                bVar.a(str3);
            }
            this.h.add(bVar);
        }
    }

    private void a(String eventName, String eventLogic) {
        Hx.a a = null;

        for (Hx.a next : e) {
            if (next.name.equals(eventName)) {
                a = next;
            }
        }

        if (a == null) {
            a = new Hx.a(eventName);
        }
        a.setLogic(eventLogic);
        e.add(a);
    }

    public void a(String str, String str2, String str3) {
        if (!this.j.containsKey(str)) {
            this.j.put(str, Lx.b(str, str2, str3));
        }
    }

    public final void a(ArrayList<EventBean> events, HashMap<String, ArrayList<BlockBean>> logicBlocks) {
        for (EventBean eventBean : events) {
            ArrayList<BlockBean> eventLogicBlocks = logicBlocks.get(eventBean.targetId + "_" + eventBean.eventName);
            String eventLogic = (eventLogicBlocks == null || eventLogicBlocks.size() <= 0) ? "" :
                    new Fx(this.a.getActivityName(), this.b, eventBean.eventName, eventLogicBlocks).a();

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
                        case 31:
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
        boolean z;
        StringBuilder sb = new StringBuilder(4096);
        for (Map.Entry<String, String> entry : this.j.entrySet()) {
            String str = entry.getKey();
            String str2 = entry.getValue();
            Iterator<a> it = this.e.iterator();
            while (true) {
                z = false;
                if (it.hasNext()) {
                    if (it.next().name.equals(str)) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!z) {
                Hx.a a = new Hx.a(str);
                a.setLogic(str2);
                e.add(a);
            }
        }
        for (Hx.a value : e) {
            String code = value.getCode();
            if (sb.length() > 0 && code.length() > 0) {
                sb.append("\r\n");
                sb.append("\r\n");
            }
            sb.append(code);
        }
        return sb.toString();
    }

    private void b(String targetId, String eventName, String eventLogic) {
        for (Hx.c next : d) {
            if (next.a.equals(targetId)) {
                next.a(targetId, eventName, eventLogic);
                return;
            }
        }
    }

    public String c() {
        StringBuilder sb = new StringBuilder(4096);
        ComponentExtraCode componentExtraCode = new ComponentExtraCode(this, sb);
        for (Hx.c value : this.d) {
            componentExtraCode.s(value.a());
        }
        return sb.toString();
    }

    private void c(String targetId, String eventName, String eventLogic) {
        for (Hx.c next : g) {
            if (next.a.equals("_drawer_" + targetId)) {
                next.a(targetId, eventName, eventLogic);
                return;
            }
        }
    }

    public String d() {
        StringBuilder sb = new StringBuilder(4096);
        for (Hx.c value : this.g) {
            String a2 = value.a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append("\r\n");
                sb.append("\r\n");
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    private void d(String targetId, String eventName, String eventLogic) {
        for (Hx.c next : f) {
            if (next.a.equals(targetId)) {
                next.a(targetId, eventName, eventLogic);
                return;
            }
        }
    }

    public ArrayList<String> e() {
        return this.i;
    }

    private void e(String targetId, String eventName, String eventLogic) {
        for (Hx.c next : this.c) {
            if (next.a.equals(targetId)) {
                next.a(targetId, eventName, eventLogic);
                return;
            }
        }
    }

    public String f() {
        StringBuilder sb = new StringBuilder(4096);
        for (Hx.c next : this.f) {
            next.b();
            String a2 = next.a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append("\r\n");
                sb.append("\r\n");
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    public String g() {
        StringBuilder sb = new StringBuilder(4096);
        for (Hx.c value : this.c) {
            String a2 = value.a();
            if (sb.length() > 0 && a2.length() > 0) {
                sb.append("\r\n");
                sb.append("\r\n");
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    private static class a {

        private final String name;
        private String logic = "";
        private String targetId = "";

        public a(String name) {
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

        private final int a;
        private final String b;
        private String c = "";
        private String d = "";

        public b(int componentId, String componentName) {
            this.a = componentId;
            this.b = componentName;
        }

        public void a(String onCancelledLogic) {
            this.d = onCancelledLogic;
        }

        public void b(String onSuccessLogic) {
            this.c = onSuccessLogic;
        }

        public String a() {
            return Lx.a(this.a, this.b, this.c, this.d);
        }
    }

    private static class c {

        public final Hx d;
        public String a;
        public Gx b;
        public ArrayList<d> c = new ArrayList<>();

        public c(Hx hx, String str, Gx gx) {
            this.d = hx;
            this.a = str;
            this.b = gx;
            String[] b2 = oq.b(gx);
            if (b2.length > 0) {
                for (String str2 : b2) {
                    if (this.c.indexOf(str2) < 0) {
                        this.c.add(new d(hx, str2));
                    }
                }
                if (gx.a().equals("FirebaseDB") || gx.a().equals("FirebaseStorage") || gx.a().equals("FirebaseAuth") || gx.a().equals("Gyroscope") || gx.a().equals("WebView") || gx.a().equals("InterstitialAd") || gx.a().equals("RequestNetwork") || gx.a().equals("BluetoothConnect")) {
                    for (Hx.d value : this.c) {
                        value.b = true;
                    }
                }
            }
        }

        public String a() {
            StringBuilder sb = new StringBuilder(4096);
            for (Hx.d value : this.c) {
                String a2 = value.a(this.a);
                if (sb.length() > 0 && a2.length() > 0) {
                    sb.append("\r\n");
                    sb.append("\r\n");
                }
                sb.append(a2);
            }
            return sb.toString();
        }

        public void a(String targetId, String eventName, String eventLogic) {
            for (Hx.d d : c) {
                for (Hx.a a : d.c) {
                    if (a.name.equals(eventName)) {
                        a.setLogic(eventLogic);
                        a.setTargetId(targetId);
                        d.b = true;
                        break;
                    }
                }

                if (d.b) {
                    this.d.i.addAll(mq.d(d.a));
                }
            }
        }

        public void b() {
        }
    }

    private static class d {

        public final Hx d;
        public String a;
        public boolean b = false;
        public ArrayList<a> c;

        public d(Hx hx2, String str) {
            this.d = hx2;
            this.a = str;
            c = new ArrayList<>();
            for (String eventName : oq.b(str)) {
                c.add(new a(eventName));
            }
        }

        public String a(String str) {
            if (!this.b) {
                return "";
            }
            StringBuilder sb = new StringBuilder(4096);
            for (Hx.a value : this.c) {
                String code = value.getCode();
                if (sb.length() > 0 && code.length() > 0) {
                    sb.append("\r\n");
                    sb.append("\r\n");
                }
                sb.append(code);
            }
            return Lx.d(this.a, str, sb.toString());
        }
    }
}
