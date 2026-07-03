package a.a.a;




// =========================================================
// uq_CodeView = CodeView constant arrays for uq
// =========================================================

// PURPOSE:
    // Holds the two large CodeView string arrays split out of uq.java.
    // uq delegates to these via public fields:
        // uq.CODEVIEW_THEME    = uq_CodeView.CODEVIEW_THEME
        // uq.CODEVIEW_LANGUAGE = uq_CodeView.CODEVIEW_LANGUAGE

// BELONGS TO:
    // uq (ConstantArrays) — see uq.java

// USAGE:
    // Do NOT use this class directly from outside uq.
    // Access via:
        // uq.CODEVIEW_THEME
        // uq.CODEVIEW_LANGUAGE

// =========================================================

class uq_CodeView {




    // =========================================================
    // FIELDS
    // =========================================================

    // Syntax highlight theme names supported by the CodeView component.
    static final String[] CODEVIEW_THEME = {
        "AGATE",                     "ANDROIDSTUDIO",              "ARDUINO_LIGHT",              "ARTA",
        "ASCETIC",                   "ATELIER_CAVE_DARK",          "ATELIER_CAVE_LIGHT",         "ATELIER_DUNE_DARK",
        "ATELIER_DUNE_LIGHT",        "ATELIER_ESTUARY_DARK",       "ATELIER_ESTUARY_LIGHT",      "ATELIER_FOREST_DARK",
        "ATELIER_FOREST_LIGHT",      "ATELIER_HEATH_DARK",         "ATELIER_HEATH_LIGHT",        "ATELIER_LAKESIDE_DARK",
        "ATELIER_LAKESIDE_LIGHT",    "ATELIER_PLATEAU_DARK",       "ATELIER_PLATEAU_LIGHT",      "ATELIER_SAVANNA_DARK",
        "ATELIER_SAVANNA_LIGHT",     "ATELIER_SEASIDE_DARK",       "ATELIER_SEASIDE_LIGHT",      "ATELIER_SULPHURPOOL_DARK",
        "ATELIER_SULPHURPOOL_LIGHT", "ATOM_ONE_DARK",              "ATOM_ONE_LIGHT",             "BROWN_PAPER",
        "CODEPEN_EMBED",             "COLOR_BREWER",               "DARCULA",                    "DARK",
        "DARKULA",                   "DEFAULT",                    "DOCCO",                      "DRACULA",
        "FAR",                       "FOUNDATION",                 "GITHUB",                     "GITHUB_GIST",
        "GOOGLECODE",                "GRAYSCALE",                  "GRUVBOX_DARK",               "GRUVBOX_LIGHT",
        "HOPSCOTCH",                 "HYBRID",                     "IDEA",                       "IR_BLACK",
        "KIMBIE_DARK",               "KIMBIE_LIGHT",               "MAGULA",                     "MONO_BLUE",
        "MONOKAI",                   "MONOKAI_SUBLIME",            "OBSIDIAN",                   "OCEAN",
        "PARAISO_DARK",              "PARAISO_LIGHT",              "POJOAQUE",                   "PUREBASIC",
        "QTCREATOR_DARK",            "QTCREATOR_LIGHT",            "RAILSCASTS",                 "RAINBOW",
        "SCHOOL_BOOK",               "SOLARIZED_DARK",             "SOLARIZED_LIGHT",            "SUNBURST",
        "TOMORROW",                  "TOMORROW_NIGHT",             "TOMORROW_NIGHT_BLUE",        "TOMORROW_NIGHT_BRIGHT",
        "TOMORROW_NIGHT_EIGHTIES",   "VS",                         "VS2015",                     "XCODE",
        "XT256",                     "ZENBURN"
    };

    // Programming language names supported by the CodeView syntax highlighter.
    static final String[] CODEVIEW_LANGUAGE = {
        "AUTO",              "1C",                "ABNF",              "ACCESS_LOG",         "ACTIONSCRIPT",
        "ADA",               "APACHE",            "APPLESCRIPT",       "ARDUINO",            "ARM_ASSEMBLY",
        "ASCII_DOC",         "ASPECTJ",           "AUTOHOTKEY",        "AUTOIT",             "AVR_ASSEMBLER",
        "AWK",               "AXAPTA",            "BASH",              "BASIC",              "BNF",
        "BRAINFUCK",         "C_AL",              "CAP_N_PROTO",       "CEYLON",             "CLEAN",
        "CLOJURE",           "CLOJURE_REPL",      "CMAKE",             "COFFEESCRIPT",       "COQ",
        "CACHE_OBJECT_SCRIPT","CPP",              "CRMSH",             "CRYSTAL",            "C_SHARP",
        "CSP",               "CSS",               "D",                 "DART",               "DELPHI",
        "DIFF",              "DJANGO",            "DNS",               "DOCKERFILE",         "DOS",
        "DSCONFIG",          "DEVICE_TREE",       "DUST",              "EBNF",               "ELIXIR",
        "ELM",               "ERB",               "ERLANG",            "ERLANG_REPL",        "EXCEL",
        "FIX",               "FLIX",              "FORTRAN",           "F_SHARP",            "GAMS",
        "GAUSS",             "GCODE",             "GHERKIN",           "GLSL",               "GO",
        "GOLO",              "GRADLE",            "GROOVY",            "HAML",               "HANDLEBARS",
        "HASKELL",           "HAXE",              "HSP",               "HTML",               "HTMLBARS",
        "HTTP",              "HY",                "INFORM_7",          "INI",                "IRPF90",
        "JAVA",              "JAVASCRIPT",        "JBOSS_CLI",         "JSON",               "JULIA",
        "KOTLIN",            "LASSO",             "LDIF",              "LEAF",               "LESS",
        "LISP",              "LIVECODESERVER",    "LIVESCRIPT",        "LLVM",               "LSL",
        "LUA",               "MAKEFILE",          "MARKDOWN",          "MATHEMATICA",        "MATLAB",
        "MAXIMA",            "MEL",               "MERCURY",           "MIPS_ASSEMBLY",      "MIZAR",
        "MOJOLICIOUS",       "MONKEY",            "MOONSCRIPT",        "N1QL",               "NGINX",
        "NIMROD",            "NIX",               "NSIS",              "OBJECTIVE_C",        "OCAML",
        "OPENSCAD",          "OXYGENE",           "PARSER3",           "PERL",               "PF",
        "PHP",               "PONY",              "POWERSHELL",        "PROCESSING",         "PROFILE",
        "PROLOG",            "PROTOCOL_BUFFERS",  "PUPPET",            "PURE_BASIC",         "PYTHON",
        "Q",                 "QML",               "R",                 "RIB",                "ROBOCONF",
        "ROUTEROS",          "RSL",               "RUBY",              "ORACLE_RULES_LANGUAGE","RUST",
        "SCALA",             "SCHEME",            "SCILAB",            "SCSS",               "SHELL",
        "SMALI",             "SMALLTALK",         "SML",               "SQF",                "SQL",
        "STAN",              "STATA",             "STEP21",            "STYLUS",             "SUBUNIT",
        "SWIFT",             "TAGGERSCRIPT",      "TAP",               "TCL",                "TEX",
        "THRIFT",            "TP",                "TWIG",              "TYPESCRIPT",         "VALA",
        "VB_NET",            "VBSCRIPT",          "VBSCRIPT_HTML",     "VERILOG",            "VHDL",
        "VIM",               "X86_ASSEMBLY",      "XL",                "XML",                "XQUERY",
        "YAML",              "ZEPHIR"

    };




}

