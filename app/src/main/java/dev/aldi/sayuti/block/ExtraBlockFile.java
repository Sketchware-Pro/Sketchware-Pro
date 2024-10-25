package dev.aldi.sayuti.block;

import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import pro.sketchware.utility.FileUtil;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.blocks.BlocksHandler;

public class ExtraBlockFile {

    public static final File EXTRA_BLOCKS_DATA_FILE = new File(Environment.getExternalStorageDirectory(),
            ".sketchware/resources/block/My Block/block.json");
    public static final File EXTRA_BLOCKS_PALETTE_FILE = new File(Environment.getExternalStorageDirectory(),
            ".sketchware/resources/block/My Block/palette.json");
    public static final File EXTRA_MENU_DATA_FILE = new File(Environment.getExternalStorageDirectory(),
            ".sketchware/resources/block/Menu Block/data.json");
    public static final File EXTRA_MENU_BLOCK_FILE = new File(Environment.getExternalStorageDirectory(),
            ".sketchware/resources/block/Menu Block/block.json");

    public static ArrayList<HashMap<String, Object>> getExtraBlockData() {
        ArrayList<HashMap<String, Object>> extraBlocks = new Gson().fromJson(getExtraBlockFile(), Helper.TYPE_MAP_LIST);
        BlocksHandler.builtInBlocks(extraBlocks);

        return extraBlocks;
    }

    /**
     * @return Non-empty content of {@link ExtraBlockFile#EXTRA_BLOCKS_DATA_FILE},
     * as cases of <code>""</code> as file content return <code>"[]"</code>
     */
    public static String getExtraBlockFile() {
        String fileContent;

        if (EXTRA_BLOCKS_DATA_FILE.exists() && !(fileContent = FileUtil.readFile(EXTRA_BLOCKS_DATA_FILE.getAbsolutePath())).isEmpty()) {
            return fileContent;
        } else {
            return "[]";
        }
    }

    public static String getMenuDataFile() {
        String fileContent;

        if (EXTRA_MENU_DATA_FILE.exists() && !(fileContent = FileUtil.readFile(EXTRA_MENU_DATA_FILE.getAbsolutePath())).isEmpty()) {
            return fileContent;
        } else {
            String defaultFileContent = "{\n\"til_box_mode\":[\n{\"title\":\"Select Box Background Mode\",\"value\":\"NONE+OUTLINE+FILLED\"}],\n\n\"fabsize\":[\n{\"title\":\"Select Fab Size\",\"value\":\"AUTO+MINI+NORMAL\"}],\n\n\"fabvisible\":[\n{\"title\":\"Select Visibility\",\"value\":\"hide+show\"}],\n\n\"menuitem\":[\n{\"title\":\"Select MenuItem\",\"value\":\"menuitem1+menuitem2+menuitem3+menuitem4+menuitem5\"}],\n\n\"submenu\":[\n{\"title\":\"Select SubMenu\",\"value\":\"submenu1+submenu2+submenu3+submenu4+submenu5\"}],\n\n\"menuaction\":[\n{\"title\":\"Select Menu Action\",\"value\":\"SHOW_AS_ACTION_ALWAYS+SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW+SHOW_AS_ACTION_IF_ROOM+SHOW_AS_ACTION_NEVER+SHOW_AS_ACTION_WITH_TEXT\"}],\n\n\"inputstream\":[\n{\"title\":\"Select InputStream\",\"value\":\"inputstream1+inputstream2+inputstream3+inputstream4+inputstream5\"}],\n\n\"porterduff\":[\n{\"title\":\"Select PorterDuff Mode\",\"value\":\"MULTIPLY+SCREEN+ADD+SRC_IN+SRC_ATOP+SRC_OVER\"}],\n\n\"transcriptmode\":[\n{\"title\":\"Select TranscriptMode\",\"value\":\"TRANSCRIPT_MODE_NORMAL+TRANSCRIPT_MODE_DISABLED+TRANSCRIPT_MODE_ALWAYS_SCROLL\"}],\n\n\"listscrollparam\":[\n{\"title\":\"Select ListView Scroll Param\",\"value\":\"SCROLL_STATE_IDLE+SCROLL_STATE_FLING+SCROLL_STATE_TOUCH_SCROLL\"}],\n\n\"recyclerscrollparam\":[\n{\"title\":\"Select RecyclerView Scroll Param\",\"value\":\"SCROLL_STATE_IDLE+SCROLL_STATE_SETTLING+SCROLL_STATE_DRAGGING\"}],\n\n\"pagerscrollparam\":[\n{\"title\":\"Select ViewPager Scroll Param\",\"value\":\"SCROLL_STATE_IDLE+SCROLL_STATE_SETTLING+SCROLL_STATE_DRAGGING\"}],\n\n\"gridstretchmode\":[\n{\"title\":\"Select GridView StretchMode\",\"value\":\"STRETCH_COLUMN_WIDTH+STRETCH_SPACING_UNIFORM+STRETCH_SPACING\"}],\n\n\"gravity_v\":[\n{\"title\":\"Select Gravity Vertical\",\"value\":\"TOP+BOTTOM+CENTER_VERTICAL\"}],\n\n\"gravity_h\":[\n{\"title\":\"Select Gravity Horizontal\",\"value\":\"LEFT+RIGHT+CENTER_HORIZONTAL\"}],\n\n\"gravity_t\":[\n{\"title\":\"Select Gravity Toast\",\"value\":\"TOP+CENTER+BOTTOM\"}],\n\n\"patternviewmode\":[\n{\"title\":\"Select ViewMode\",\"value\":\"CORRECT+WRONG+AUTO_DRAW\"}],\n\n\"styleprogress\":[\n{\"title\":\"Select ProgressDialog Style\",\"value\":\"STYLE_HORIZONTAL+STYLE_SPINNER\"}],\n\n\"cv_theme\":[\n{\"title\":\"Select Theme\",\"value\":\"AGATE +ANDROIDSTUDIO +ARDUINO_LIGHT +ARTA +ASCETIC +ATELIER_CAVE_DARK +ATELIER_CAVE_LIGHT +ATELIER_DUNE_DARK +ATELIER_DUNE_LIGHT +ATELIER_ESTUARY_DARK +ATELIER_ESTUARY_LIGHT +ATELIER_FOREST_DARK +ATELIER_FOREST_LIGHT +ATELIER_HEATH_DARK +ATELIER_HEATH_LIGHT +ATELIER_LAKESIDE_DARK +ATELIER_LAKESIDE_LIGHT +ATELIER_PLATEAU_DARK +ATELIER_PLATEAU_LIGHT +ATELIER_SAVANNA_DARK +ATELIER_SAVANNA_LIGHT +ATELIER_SEASIDE_DARK +ATELIER_SEASIDE_LIGHT +ATELIER_SULPHURPOOL_DARK +ATELIER_SULPHURPOOL_LIGHT +ATOM_ONE_DARK +ATOM_ONE_LIGHT +BROWN_PAPER +CODEPEN_EMBED +COLOR_BREWER +DARCULA +DARK +DARKULA +DEFAULT +DOCCO +DRACULA +FAR +FOUNDATION +GITHUB +GITHUB_GIST +GOOGLECODE +GRAYSCALE +GRUVBOX_DARK +GRUVBOX_LIGHT +HOPSCOTCH +HYBRID +IDEA +IR_BLACK +KIMBIE_DARK +KIMBIE_LIGHT +MAGULA +MONO_BLUE +MONOKAI +MONOKAI_SUBLIME +OBSIDIAN +OCEAN +PARAISO_DARK +PARAISO_LIGHT +POJOAQUE +PUREBASIC +QTCREATOR_DARK +QTCREATOR_LIGHT +RAILSCASTS +RAINBOW +SCHOOL_BOOK +SOLARIZED_DARK +SOLARIZED_LIGHT +SUNBURST +TOMORROW +TOMORROW_NIGHT +TOMORROW_NIGHT_BLUE +TOMORROW_NIGHT_BRIGHT +TOMORROW_NIGHT_EIGHTIES +VS +VS2015 +XCODE +XT256 +ZENBURN\"}],\n\n\"cv_language\":[\n{\"title\":\"Select Language\",\"value\":\"AUTO+_1C+ABNF+ACCESS_LOG+ACTIONSCRIPT+ADA+APACHE+APPLESCRIPT+ARDUINO+ARM_ASSEMBLY+ASCII_DOC+ASPECTJ+AUTOHOTKEY+AUTOIT+AVR_ASSEMBLER+AWK+AXAPTA+BASH+BASIC+BNF+BRAINFUCK+C_AL+CAP_N_PROTO+CEYLON+CLEAN+CLOJURE+CLOJURE_REPL+CMAKE+COFFEESCRIPT+COQ+CACHE_OBJECT_SCRIPT+CPP+CRMSH+CRYSTAL+C_SHARP+CSP+CSS+D+DART+DELPHI+DIFF+DJANGO+DNS+DOCKERFILE+DOS+DSCONFIG+DEVICE_TREE+DUST+EBNF+ELIXIR+ELM+ERB+ERLANG+ERLANG_REPL+EXCEL+FIX+FLIX+FORTRAN+F_SHARP+GAMS+GAUSS+GCODE+GHERKIN+GLSL+GO+GOLO+GRADLE+GROOVY+HAML+HANDLEBARS+HASKELL+HAXE+HSP+HTML+HTMLBARS+HTTP+HY+INFORM_7+INI+IRPF90+JAVA+JAVASCRIPT+JBOSS_CLI+JSON+JULIA+KOTLIN+LASSO+LDIF+LEAF+LESS+LISP+LIVECODESERVER+LIVESCRIPT+LLVM+LSL+LUA+MAKEFILE+MARKDOWN+MATHEMATICA+MATLAB+MAXIMA+MEL+MERCURY+MIPS_ASSEMBLY+MIZAR+MOJOLICIOUS+MONKEY+MOONSCRIPT+N1QL+NGINX+NIMROD+NIX+NSIS+OBJECTIVE_C+OCAML+OPENSCAD+OXYGENE+PARSER3+PERL+PF+PHP+PONY+POWERSHELL+PROCESSING+PROFILE+PROLOG+PROTOCOL_BUFFERS+PUPPET+PURE_BASIC+PYTHON+Q+QML+R+RIB+ROBOCONF+ROUTEROS+RSL+RUBY+ORACLE_RULES_LANGUAGE+RUST+SCALA+SCHEME+SCILAB+SCSS+SHELL+SMALI+SMALLTALK+SML+SQF+SQL+STAN+STATA+STEP21+STYLUS+SUBUNIT+SWIFT+TAGGERSCRIPT+TAP+TCL+TEX+THRIFT+TP+TWIG+TYPESCRIPT+VALA+VB_NET+VBSCRIPT+VBSCRIPT_HTML+VERILOG+VHDL+VIM+X86_ASSEMBLY+XL+XML+XQUERY+YAML+ZEPHIR\"}],\n\n\"import\":[\n{\"title\":\"Select a class\",\"value\":\"android.content.pm.*+android.database.sqlite.*+android.gesture.*+android.graphics.fonts.*+android.graphics.pdf.*+android.hardware.*+android.inputmethodservice.*+android.opengl.*+android.preference.*+android.print.pdf.*+android.provider.*+android.security.*+android.service.*+android.text.format.*+android.text.util.*+android.text.method.*+android.transition.*+android.view.inputmethod.*+java.math.*+java.nio.*+java.security.*+java.security.spec.*+java.sql.*+\"}]\n}";

            FileUtil.writeFile(EXTRA_MENU_DATA_FILE.getAbsolutePath(), defaultFileContent);
            return defaultFileContent;
        }
    }

    public static String getMenuBlockFile() {
        String fileContent;

        if (EXTRA_MENU_BLOCK_FILE.exists() && !(fileContent = FileUtil.readFile(EXTRA_MENU_BLOCK_FILE.getAbsolutePath())).isEmpty()) {
            return fileContent;
        } else {
            String defaultFileContent = "[\n{\"id\":\"image\",\"name\":\"CustomImage\"},\n{\"id\":\"drawable\",\"name\":\"Drawable\"},\n{\"id\":\"anim\",\"name\":\"Anim\"},\n{\"id\":\"layout\",\"name\":\"Layout\"},\n{\"id\":\"menu\",\"name\":\"Menu\"},\n{\"id\":\"import\",\"name\":\"Class path\"},\n{\"id\":\"fabsize\",\"name\":\"FabSize\"},\n{\"id\":\"fabvisible\",\"name\":\"FabVisible\"},\n{\"id\":\"inputstream\",\"name\":\"InputStream\"},\n{\"id\":\"porterduff\",\"name\":\"PorterduffMode\"},\n{\"id\":\"pagerscrollparam\",\"name\":\"ViewPagerScrollParam\"},\n{\"id\":\"recyclerscrollparam\",\"name\":\"RecyclerViewScrollParam\"},\n{\"id\":\"listscrollparam\",\"name\":\"ListViewScrollParam\"},\n{\"id\":\"gridstretchmode\",\"name\":\"StrechMode\"},\n{\"id\":\"patternviewmode\",\"name\":\"ViewMode\"},\n{\"id\":\"transcriptmode\",\"name\":\"TranscriptMode\"},\n{\"id\":\"gravity_v\",\"name\":\"GravityVertical\"},\n{\"id\":\"gravity_h\",\"name\":\"GravityHorizontal\"},\n{\"id\":\"gravity_t\",\"name\":\"GravityToast\"},\n{\"id\":\"styleprogress\",\"name\":\"ProgressStyle\"},\n{\"id\":\"menuitem\",\"name\":\"MenuItem\"},\n{\"id\":\"submenu\",\"name\":\"SubMenu\"},\n{\"id\":\"menuaction\",\"name\":\"MenuAction\"},\n{\"id\":\"cv_language\",\"name\":\"Language\"},\n{\"id\":\"cv_theme\",\"name\":\"Theme\"},\n{\"id\":\"til_box_mode\",\"name\":\"BoxMode\"}\n]";

            FileUtil.writeFile(EXTRA_MENU_BLOCK_FILE.getAbsolutePath(), defaultFileContent);
            return defaultFileContent;
        }
    }

    public static String getPaletteBlockFile() {
        return FileUtil.readFile(EXTRA_BLOCKS_PALETTE_FILE.getAbsolutePath());
    }

    public static String getExtraBlockJson() {
        return "[]";
    }
}
