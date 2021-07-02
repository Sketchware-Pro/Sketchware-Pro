package mod.w3wide.control.logic;

import com.besome.sketch.beans.BlockBean;

import java.util.ArrayList;
import java.util.Map.Entry;

import a.a.a.Fx;
import a.a.a.Lx;
import a.a.a.jC;
import a.a.a.jq;

public class SourceHandler {

    private final String javaName;
    private final String sc_id;

    public SourceHandler(String id, String javaName) {
        this.javaName = javaName;
        sc_id = id;
    }

    public String activityResult(jq libReader, String activityName) {
        ArrayList<BlockBean> blocks = jC.a(sc_id).a(javaName, "onActivityResult_onActivityResult");
        return Lx.j(new Fx(activityName, libReader, "", blocks).a());
    }

    public String initializeLogic(jq libReader, String activityName) {
        ArrayList<BlockBean> blocks = jC.a(sc_id).a(javaName, "initializeLogic_initializeLogic");
        return Lx.j(new Fx(activityName, libReader, "", blocks).a());
    }

    public ArrayList<String> customVariables() {
        ArrayList<String> variables = new ArrayList<>();
        for (Entry<String, ArrayList<BlockBean>> blocks : jC.a(sc_id).b(javaName).entrySet()) {
            for (BlockBean block : blocks.getValue()) {
                if (block.opCode.equals("addCustomVariable") && !block.parameters.get(0).trim().isEmpty()) {
                    variables.add(block.parameters.get(0));
                }
            }
        }
        return variables;
    }

    public ArrayList<String> viewBinds() {
        ArrayList<String> ids = new ArrayList<>();
        for (Entry<String, ArrayList<BlockBean>> blocks : jC.a(sc_id).b(javaName).entrySet()) {
            for (BlockBean block : blocks.getValue()) {
                if (block.opCode.equals("addInitializer") && !block.parameters.get(0).trim().isEmpty()) {
                    ids.add(block.parameters.get(0));
                }
            }
        }
        return ids;
    }

    public String viewPageAdapter() {
        StringBuilder pager = new StringBuilder();
        pager.append("\r\npublic class PageAdapter extends FragmentStatePagerAdapter {\r\n")
        .append("private final List<Fragment> mFragments = new ArrayList<>();\r\n")
        .append("private final List<String> mTitles = new ArrayList<>();\r\n\r\n")
        .append("public PageAdapter(FragmentManager fm) {\r\n")
        .append("super(fm);\r\n}\r\n\r\n")
        .append("public void addFragment(final Fragment fragment, final String title) {\r\n")
        .append("mFragments.add(fragment);\r\n")
        .append("mTitles.add(title);\r\n}\r\n\r\n")
        .append("@Override\r\npublic int getCount(){\r\n")
        .append("return mFragments.size();\r\n}\r\n\r\n")
        .append("@Override\r\npublic CharSequence getPageTitle(int position) {\r\n")
        .append("return mTitles.get(position);\r\n}\r\n\r\n")
        .append("@Override\r\npublic Fragment getItem(int position) {\r\n")
        .append("return mFragments.get(position);\r\n}\r\n\r\n")
        .append("@Override\r\npublic float getPageWidth(int position) {\r\n")
        .append("return super.getPageWidth(position);\r\n}\r\n\r\n")
        .append("public void removeItem(int position) {\r\n")
        .append("if (mFragments != null) {\r\n")
        .append("mFragments.remove(position);\r\n")
        .append("mTitles.remove(getPageTitle(position));\r\n")
        .append("notifyDataSetChanged();\r\n}\r\n}\r\n\r\n}\r\n");
        
        return pager.toString();
    }

}
