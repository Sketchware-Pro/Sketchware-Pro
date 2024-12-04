package pro.sketchware.menu;
import android.util.Pair;

import a.a.a.Ss;
import a.a.a.eC;
import a.a.a.jC;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.editor.LogicEditorActivity;

import mod.agus.jcoderz.editor.manage.block.makeblock.BlockMenu;
import mod.elfilibustero.sketch.lib.utils.CustomVariableUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultExtraMenuBean {
    
    private final LogicEditorActivity logicEditor;
    private final eC projectDataManager;
    
    public DefaultExtraMenuBean(LogicEditorActivity logicEditor) {
        this.logicEditor = logicEditor;
        projectDataManager = jC.a(logicEditor.B);
    }
    
    public Pair<String, ArrayList<String>> getMenu(Ss menu) {
        var javaName = logicEditor.M.getJavaName();
        var menuName = menu.getMenuName();
        ArrayList<String> menus = new ArrayList<>();
        String title;
        Pair<String, String[]> menuPair = BlockMenu.getMenu(menuName);
        title = menuPair.first;
        menus = new ArrayList<>(Arrays.asList(menuPair.second));
        for (String s : projectDataManager.e(javaName, 5)) {
            Matcher matcher2 = Pattern.compile("^(\\w+)[\\s]+(\\w+)").matcher(s);
            while (matcher2.find()) {
                if (menuName.equals(matcher2.group(1))) {
                    title = "Select a " + matcher2.group(1) + " Variable";
                    menus.add(matcher2.group(2));
                }
            }
        }
        for (String variable : projectDataManager.e(javaName, 6)) {
            String variableType = CustomVariableUtil.getVariableType(variable);
            String variableName = CustomVariableUtil.getVariableName(variable);
            if (menuName.equals(variableType)) {
                title = "Select a " + variableType + " Variable";
                menus.add(variableName);
            }
        }
        for (ComponentBean componentBean : projectDataManager.e(javaName)) {
            if (componentBean.type > 36 && menuName.equals(ComponentBean.getComponentTypeName(componentBean.type))) {
                title = "Select a " + ComponentBean.getComponentTypeName(componentBean.type);
                menus.add(componentBean.componentId);
            }
        }
        return new Pair<>(title, menus);
    }
}
