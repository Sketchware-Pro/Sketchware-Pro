package mod.hilal.saif.blocks;

import java.util.ArrayList;

import a.a.a.Ss;
import mod.hilal.saif.asd.asdforall.AsdAll;

public class BlocksMenu {
    public static void extraBlockMenu(Ss ss, AsdAll asdAll, ArrayList arrayList) {
        String menuName = ss.getMenuName();
        if (menuName.equals("LayoutParam")) {
            asdAll.b("LayoutParams");
            arrayList.add("MATCH_PARENT");
            arrayList.add("WRAP_CONTENT");
        }
        if (menuName.equals("Command")) {
            asdAll.b("commands");
            arrayList.add("insert");
            arrayList.add("add");
            arrayList.add("replace");
            arrayList.add("find-replace");
            arrayList.add("find-replace-first");
            arrayList.add("find-replace-all");
        }
    }
}
