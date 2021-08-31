package mod.agus.jcoderz.component;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.editor.component.ComponentAddActivity;

import java.util.ArrayList;

import mod.hilal.saif.components.ComponentsHandler;

public class ManageComponent {

    /**
     * Used in {@link ComponentAddActivity#q()} to add extra Components
     * (those added by Agus and custom ones) to the available Components list
     */
    public static void a(ArrayList<ComponentBean> components) {
        components.add(new ComponentBean(27));
        ComponentsHandler.add(components);
        components.add(new ComponentBean(28));
        components.add(new ComponentBean(29));
        components.add(new ComponentBean(30));
        components.add(new ComponentBean(31));
        components.add(new ComponentBean(32));
        components.add(new ComponentBean(33));
        components.add(new ComponentBean(34));
    }
}
