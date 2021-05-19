package mod.agus.jcoderz.component;

import com.besome.sketch.beans.ComponentBean;

import java.util.ArrayList;

import mod.hilal.saif.components.ComponentsHandler;

public class ManageComponent {
    public static void a(ArrayList arrayList) {
        arrayList.add(new ComponentBean(27));
        ComponentsHandler.add(arrayList);
        arrayList.add(new ComponentBean(28));
        arrayList.add(new ComponentBean(29));
        arrayList.add(new ComponentBean(30));
        arrayList.add(new ComponentBean(31));
        arrayList.add(new ComponentBean(32));
        arrayList.add(new ComponentBean(33));
        arrayList.add(new ComponentBean(34));
    }
}
