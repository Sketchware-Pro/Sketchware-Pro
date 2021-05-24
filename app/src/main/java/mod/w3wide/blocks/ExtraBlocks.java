package mod.w3wide.blocks;

import a.a.a.jC;

import android.util.Pair;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.editor.LogicEditorActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtraBlocks {
    
    private static HashMap hashMap;
    private static LogicEditorActivity logicEditor;
    private ProjectFileBean projectFile;
    private static String eventName = "";
    private static String javaName = "";
    private static String sc_id = "";
    
    public ExtraBlocks(LogicEditorActivity logicEditor) {
        eventName = logicEditor.D;
        this.logicEditor = logicEditor;
        projectFile = logicEditor.M;
        javaName = projectFile.getJavaName();
        sc_id = logicEditor.B;
    }
    
    public boolean isVariableUsed(int varId) {
        ArrayList<Pair<Integer, String>> arrayList = jC.a(sc_id).k(javaName);
        ArrayList<Integer> variableList = new ArrayList<>();
        for (Pair<Integer, String> intStrPair : arrayList) {
            variableList.add(intStrPair.first);
        }
        return variableList.contains(varId);
    }
    
    public boolean isListUsed(int listId) {
        ArrayList<Pair<Integer, String>> arrayList = jC.a(sc_id).j(javaName);
        ArrayList<Integer> listVar = new ArrayList<>();
        for (Pair<Integer, String> intStrPair : arrayList) {
            listVar.add(intStrPair.first);
        }
        return listVar.contains(listId);
    }
    
    public boolean isComponentUsed(int componentId) {
        return jC.a(sc_id).f(javaName, componentId);
    }
    
    public boolean isCustomVarUsed(String variable) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String variableName : jC.a(sc_id).e(javaName, 5)) {
            Matcher matcher = Pattern.compile("^(\\w+)[\\s]+(\\w+)").matcher(variableName);
            while (matcher.find()) {
                arrayList.add(matcher.group(1));
            }
        }
        return arrayList.contains(variable);
    }
    
    public void eventBlocks() {
        if (eventName.equals("onCreateOptionsMenu")) {
            logicEditor.a("Menu Item", -11184811);
            logicEditor.a(" ", "menuItemSetVisible");
            logicEditor.a(" ", "menuItemSetEnabled");
            logicEditor.a("v", "menuFindItem");
        }
    }
    
    public void fileBlocks() {
        if (isCustomVarUsed("File")) {
          logicEditor.a("File Blocks", -11184811);
          logicEditor.a("b", "fileCanExecute");
          logicEditor.a("b", "fileCanRead");
          logicEditor.a("b", "fileCanWrite");
          logicEditor.a("s", "fileGetName");
          logicEditor.a("s", "fileGetParent");
          logicEditor.a("s", "fileGetPath");
          logicEditor.a("s", "fileIsHidden");
        }
    }
    
    public static void extraBlocks(ArrayList arrayList) {
        arrayList.add(0, addBlock("caseStrAnd", " ", "", "case %s:", "#E1A92A", "case %s and"));
        arrayList.add(0, addBlock("caseNumAnd", " ", "", "case ((int)%s):", "#e1a92a", "case %d and"));
        arrayList.add(0, addBlock("continue", "f", "", "continue;", "#e1a92a", "continue"));
        arrayList.add(0, addBlock("isEmpty", "b", "", "%s.isEmpty()", "#e1a92a", "%s isEmpty"));
        arrayList.add(0, addBlock("fileutilcopydir", " ", "", "FileUtil.copyDir(%1$s, %2$s);", "#A1887F", "copy dir path %s to path %s"));
        arrayList.add(0, addBlock("instanceOfOperator", "b", "", "%1$s instanceof %2$s", "#e1a92a", "%s instanceOf %s"));
        arrayList.add(0, addBlock("checkViewVisibility", "b", "", "%s.getVisibility() == View.%s", "#4A6CD4", "visibility of %m.view equals %m.visible"));
        arrayList.add(0, addBlock("intentHasExtra", "b", "", "getIntent().hasExtra(%s)", "#2CA5E2", "Activity hasExtra key %s"));
        arrayList.add(0, addBlock("intentSetType", " ", "", "%s.setType(%s);", "#2CA5E2", "%m.intent setType %s"));
        arrayList.add(0, addBlock("intentRemoveExtra", " ", "", "%s.removeExtra(%s);", "#2CA5E2", "%m.intent removeExtra key %s"));
        arrayList.add(0, addBlock("fileContainsData", "b", "", "%1$s.contains(%2$s)", "#2CA5E2", "%m.file contains %s"));
        arrayList.add(0, addBlock("viewGetChildAt", "v", "View", "%1$s.getChildAt(%2$s)", "#4A6CD4", "%m.view getChildAt %d"));
        arrayList.add(0, addBlock("strParseInteger", "d", "", "Integer.parseInt(%s)", "#5cb722", "Integer parseInt %s"));
        arrayList.add(0, addBlock("stringSubSingle","s", "", "%1$s.substring(%2$s)", "#5cb722", "%s subString %d"));
        arrayList.add(0, addBlock("webviewGetProgress","d","","%1$s.getProgress()","#2CA5E2","%m.webview getProgress"));
        arrayList.add(0, addBlock("menuItemSetVisible", " ", "", "%1$s.setVisible(%2$s);", "#4A6CD4", "%m.MenuItem setVisible %b"));
        arrayList.add(0, addBlock("menuItemSetEnabled", " ", "", "%1$s.setEnabled(%2$s);", "#4A6CD4", "%m.MenuItem setEnabled %b"));
        arrayList.add(0, addBlock("menuFindItem", "v", "MenuItem", "menu.findItem(%s)", "#4A6CD4", "MenuItem findItem %s.inputOnly"));
        arrayList.add(0, addBlock("listAddAll", " ", "", "%1$s.addAll(%2$s);", "#cc5b22", "%m.listStr addAll from %m.list"));
        //File Blocks
        arrayList.add(0, addBlock("fileCanExecute", "b", "", "%s.canExecute()", "#a1887f", "%m.File canExecute"));
        arrayList.add(0, addBlock("fileCanRead", "b", "", "%s.canRead()", "#a1887f", "%m.File canRead"));
        arrayList.add(0, addBlock("fileCanWrite", "b", "", "%s.canWrite()", "#a1887f", "%m.File canWrite"));
        arrayList.add(0, addBlock("fileGetName", "s", "", "%s.getName()", "#a1887f", "%m.File getName"));
        arrayList.add(0, addBlock("fileGetParent", "s", "", "%s.getParent()", "#a1887f", "%m.File getParent"));
        arrayList.add(0, addBlock("fileGetPath", "s", "", "%s.getPath()", "#a1887f", "%m.File getPath"));
        arrayList.add(0, addBlock("fileIsHidden", "b", "", "%s.isHidden()", "#a1887f", "%m.File isHidden"));
    }
    
    /*
    *Get full block code in HashMap<String, Object> format
    *
    *@param name,        The Block's Name
    *@param type,        The Block's Type
    *@param typeName,    The Block's TypeName
    *@param code,        The Block's Code
    *@param color,       The Block's Color
    *@param spec,        The Block's Spec
    *
    *@return The full Block in HashMap<String, Object> format
    */
    private static HashMap<String, Object> addBlock(String name, String type, String typeName, String code, String color, String spec) {
        HashMap hashMap2 = new HashMap();
        hashMap2.put("name", name);
        hashMap2.put("type", type);
        hashMap2.put("typeName", typeName);
        hashMap2.put("code", code);
        hashMap2.put("color", color);
        hashMap2.put("palette", "-1");
        hashMap2.put("spec", spec);
        return hashMap2;
    }
    
    /*
    *Get full block code in HashMap<String, Object> format with spec2 (c type Blocks)
    *
    *@param name,        The Block's Name
    *@param type,        The Block's Type
    *@param typeName,    The Block's TypeName
    *@param code,        The Block's Code
    *@param color,       The Block's Color
    *@param spec,        The Block's Spec
    *@param spec2,       The Second Spec of Block (type c)
    *
    *@return The full Block in HashMap<String, Object> format with spec2 (c type Blocks)
    */
    private static HashMap<String, Object> addBlock(String name, String type, String typeName, String code, String color, String spec, String spec2) {
        HashMap hashMap2 = new HashMap();
        hashMap2.put("name", name);
        hashMap2.put("type", type);
        hashMap2.put("typeName", typeName);
        hashMap2.put("code", code);
        hashMap2.put("color", color);
        hashMap2.put("palette", "-1");
        hashMap2.put("spec", spec);
        hashMap2.put("spec2", spec2);
        return hashMap2;
    }
}
