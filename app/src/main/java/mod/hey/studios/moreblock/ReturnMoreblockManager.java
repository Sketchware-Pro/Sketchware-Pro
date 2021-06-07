package mod.hey.studios.moreblock;

import android.util.Pair;
import android.widget.RadioGroup;

import com.besome.sketch.editor.LogicEditorActivity;

import java.util.Iterator;

public class ReturnMoreblockManager {

    public static String getLogicEditorTitle(String str) {
        return str.replaceAll("\\[.*]", " (returns " + getMbTypeList(str) + ")");
    }

    public static String getMbEnd(String str) {
        if (str.equals(" ")) {
            str = ";";
        } else {
            str = "";
        }
        return str;
    }

    public static String getMbName(String str) {
        String str2 = str;

        if (str.contains("[")) {
            str2 = str;
            if (str.contains("]")) {
                str2 = str.replaceAll("\\[.*]", "");
            }
        }

        return str2;
    }

    public static String getMbType(String str) {
        try {
            if (!str.contains("]") || !str.contains("[")) return "void";

            return str.substring(str.indexOf("[") + 1, str.lastIndexOf("]"));
        } catch (Exception e) {
            return "void";
        }
    }

    public static String getMbTypeList(String str) {
        try {
            if (!str.contains("]") || !str.contains("["))
                return "void";

            String substring = str.substring(str.indexOf("[") + 1, str.lastIndexOf("]"));
            if (substring.contains("|")) {
                substring = substring.split("\\|")[1];
            }

            return substring;
        } catch (Exception e) {
            return "void";
        }
    }

    public static String getMbTypeCode(String str) {
        try {
            if (!str.contains("]") || !str.contains("[")) return "void";

            String substring = str.substring(str.indexOf("[") + 1, str.lastIndexOf("]"));
            if (substring.contains("|")) {
                String[] split = substring.split("\\|");

                if (substring.equals("a|Map")) {
                    substring = "HashMap<String, Object>";

                } else if (substring.equals("l|List String")) {
                    substring = "ArrayList<String>";

                } else if (substring.equals("l|List Map")) {
                    substring = "ArrayList<HashMap<String, Object>>";

                } else if (split.length == 2) {
                    substring = split[1];

                } else if (split.length == 3) {
                    substring = split[2];
                }
            }

            return substring;
        } catch (Exception e) {
            return "void";
        }
    }

    public static String getMbTypeFromRadioButton(RadioGroup radioGroup) {
        String type;
        switch (radioGroup.getCheckedRadioButtonId()) {
            case 2131232434:
                type = "s";
                break;

            case 2131232435:
                type = "d";
                break;

            case 2131232436:
                type = "b";
                break;

            case 2131232448:
                type = "a|Map";
                break;

            case 2131232449:
                type = "l|List String";
                break;

            case 2131232450:
                type = "l|List Map";
                break;

            case 2131232451:
                type = "v|View";
                break;

            case 2131232437:
            case 2131232438:
            case 2131232439:
            case 2131232440:
            case 2131232441:
            case 2131232442:
            case 2131232443:
            case 2131232444:
            case 2131232445:
            case 2131232446:
            case 2131232447:
            default:
                type = " ";
                break;
        }
        return type;
    }

    public static void listMoreblocks(Iterator<Pair<String, String>> it, LogicEditorActivity logicEditorActivity) {
        while (it.hasNext()) {
            String str = it.next().second;
            String moreblockChar = getMoreblockChar(str);

            if (moreblockChar.contains("|")) {
                String[] split = moreblockChar.split("\\|");
                logicEditorActivity.a(getMbName(str), split[0], split[1], "definedFunc").setTag(getMbName(str));
            } else {
                logicEditorActivity.a(getMbName(str), moreblockChar, "definedFunc").setTag(getMbName(str));
            }
        }
    }

    public static String getMoreblockChar(String str) {
        String moreBlockChar;
        String mbType = getMbType(str);

        if (mbType.equals("void")) {
            moreBlockChar = " ";
        } else if (mbType.equals("String")) {
            moreBlockChar = "s";
        } else if (mbType.equals("double")) {
            moreBlockChar = "d";
        } else if (mbType.equals("boolean")) {
            moreBlockChar = "b";
        } else if (mbType.contains("|")) {
            return mbType;
        } else {
            moreBlockChar = " ";
        }

        return moreBlockChar;
    }

    public static String injectMbType(String str, String str2, String str3) {
        String str4 = str;

        if (!str3.equals(" ")) {
            if (str3.contains("|")) {
                str4 = injectToMbName(str4, str2, str3);

            } else if (str3.equals("s")) {
                str4 = injectToMbName(str4, str2, "String");

            } else if (str3.equals("d")) {
                str4 = injectToMbName(str4, str2, "double");

            } else if (str3.equals("b")) {
                str4 = injectToMbName(str4, str2, "boolean");
            }
        }

        return str4;

    }

    public static String injectToMbName(String str, String str2, String str3) {
        String replaceFirst;

        if (str.equals(str2)) {
            replaceFirst = str + "[" + str3 + "]";
        } else {
            replaceFirst = str.replaceFirst(str2, str2 + "[" + str3 + "]");
        }

        return replaceFirst;
    }

    public static String getPreviewType(String str) {
        if (str.contains("|")) return "a";

        return str;
    }
}
