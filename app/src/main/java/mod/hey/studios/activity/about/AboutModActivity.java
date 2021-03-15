package mod.hey.studios.activity.about;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutModActivity extends Activity implements View.OnClickListener {

    private static final String CHANGELOG = "Version v6.3.0:\n\n-> Added \"Build Settings\" located right next to the Run button, which includes options that lets you:\n   - Switch between Java7 and Java8 (enable D8 to be able to compile Java8)\n   - Switch between Dx and D8\n   - Use custom android.jar, custom classpath\n   - Disable warnings in compile log\n   \n-> Added Command Blocks:\n   - You can add, insert or replace any part of java/xml code with new one using these. These blocks can be used anywhere in your logic editor, there is no specific place (event) for them. They can be used to achieve tasks such as:\n     • remove onActivityResult entirely, or change its code, or add more codes to it.\n     • implement interfaces in your activity.\n     • remove/change any part of code that sketchware automatically generates.\n     • modify any part of root layout, add multiple fabs, change coordinatorLayout to FrameLayout, RelativeLayout. Change the order of root layout..\n     • inject codes in onCreate or anywhere you like... etc.\n     • (supports all Java files and XML files. including AndroidManifest, styles, colors and strings.xml)\n\n-> Changed the built-in Code Editor to a super optimized one. this new one has features such as:\n   - Auto completion, various themes, pinch-to-zoom, undo-redo, find&replace etc.\n\n-> Added built-in Backup/Restore feature that provides a function to back up your project (.swb), along with the local libs and custom blocks you used.\n\n-> Updated android.jar to API 30.\n\n-> Local Library improvements.. now, in your local libs you can:\n   - Use assets, proguard rules, native libs, multiple dex files, Java8 libraries.\n   \n-> Library Downloader can now download Java8 libraries.\n   \n-> Added ProGuard Full Mode (includes local libs in ProGuarding process)\n\n-> Added Custom Blocks analyzer dialog that shows you the custom blocks you used in your project.\n\n-> New Block system optimizations. Custom blocks are noticeably faster now. Also fixed the crashing bug that happens when one leaves some of the parameters empty.\n   \n-> Assets, Resource and Nativelib managers improvements.\n\n-> Now you can navigate directly to Developer Tools from LogicEditorActivity.\n\n-> Added import/export for components, events and blocks menus.\n\n-> Improved import/export blocks.\n\n-> Added uppercase support for view IDs. Now you can use IDs like \"mTextView\".\n\n-> Added searching feature in Moreblock Importer dialog. Also fixed the critical lags there.\n\n-> Fixed Change launcher activity bug.\n\n-> Fixed duplicated imports.\n\n-> Fixed some bugs and added some improvements to blocks, events and components managers.\n\n-> Fixed block selector menu bug.\n\n-> Added BottomSheetDialogFragment (add _bottomdialog_fragment to the end of the title when creating an Activity. requires appcompat to work.)\n\n-> Fixed many bugs, added many features, spent many hours. :)\n\n--------------------------------------------------------\n\nVersion v6.2.0:\n\n-> Added StringFog\n-> Fixed the ProGuard bug on Android 7\n-> Added Direct Code Editor (thanks to IndoSW)\n-> Native libs support\n-> ResourceBundle support for jar files of libraries\n-> Now you can change the application class name from Config menu\n-> Now you can change the launcher activity from Manifest manager\n-> Now you can add manifest attributes to the custom activities you add from Java Manager\n-> Improved Java Manager (now you can add folders)\n-> Fixed CollapsingToolbarLayout bug\n-> Removed AJCode.java\n-> Enabled keyboard suggestions in the built-in Code Editor\n-> Now you can open your Java/Xml files from different apps too\n-> Added a README file in the mod\\\\'s apk\n-> Bug fixes in general\n\n--------------------------------------------------------\n\nVersion v6.1.0:\n\n- Added ProGuard (minimize/obfuscate your app)\n- Fixed the DX/Multidex bug\n- Now compile log marks errors as red and warnings as yellow, increasing its readability.\n\n--------------------------------------------------------\n\nVersion v6.0.0:\n\n- Added a ton of new layouts and widgets with events, reorganized the palette. you can also access all widgets in custom views too\n- Added \"onBindCustomView\" for Spinner, GridView, RecyclerView & ViewPager\n- Added Developer Tools section in main drawer:\n    - Added built-in Blocks Manager which lets you manage your custom blocks\n    - Added block selector menus manager in which you can create your own block selector menu dialogs\n    - Added component creator which lets you create your own components\n    - Added event creator which lets you create your own Activity events and component/widget events with listeners\n    - Added mod configuration menu in which you can enable/disable built-in blocks and make all variable/list blocks visible feature\n- Added AndroidManifest manager to add/edit/remove attributes from activities and the application tag. you can also add your own custom code inside each activity tag or the whole manifest\n- Added AppCompat Injection manager to add/edit/remove attributes from AppCompat views such as FAB, toolbar etc.\n- Added project configuration menu which lets you change targetSdkVersion and minSdkVersion of your project\n- Added return types for moreblock (boolean, number, string, map, list string, list map, view) and custom return moreblock support. ( moreblockName[type|typeName] or moreblockName[type|typeName|typeCode] )\n- Added built-in Local Libraries manager\n- Added library downloader from dependency to Local Libraries manager\n- Now you can open code editor to edit ASD\n- Added Show Source Code dialog in logic editor that lets you see the code of the current event\n- Added Fragment and DialogFragment selector in activity creating, fixed context bug in fragments\n- Added lines to show source code activity\n- Fixed collection views bug on custom views\n- Some bug fixes\n- Now you can access drawer views from Variable tab in logic editor\n- Added a better custom object creation (like in sketchware revolution)\n- Added custom import creation in Variable tab in logic editor\n- Added built-in custom blocks\n- Added option to make all variable/list blocks visible\n- Added Import event in activity events\n- Added PhoneAuth, DynamicLink, CloudMessage, GoogleLogin, OneSignal and Facebook Ads components\n- Now colorPrimary, colorPrimaryDark and colorAccent colors will be previewed in view editor (in toolbar, status bar and fab)\n- Added property \"inject\" & \"convert\" to convert any widgets\n & add attributes to widgets\n- Now your blocks will be colored with its palette's color (unless you have specified a custom color in your block JSON).\n- Now some widgets have preview-able inject attributes (circleimageview)\n- Improved Java and Resource managers: now you can create classes/files inside them\n- Improved built-in Code Editor in terms of optimization. also added auto indentation, dark theme and word wrap support\n- Optimized custom block system";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AboutPage aboutPage = new AboutPage(this);
        aboutPage.addHeader(2131166161, "Sketchware Pro", "v6.3.0");
        aboutPage.addGroupTitle("About");
        aboutPage.addGroupItem("Info", "Brief information about the mod.", this);
        aboutPage.addGroupItem("Update Log", "Latest changes and bug fixes.", this);
        aboutPage.addGroupItem("Discord Server", "Join our discord community to keep up to date.", this);
        aboutPage.addGroupTitle("Main Developers");
        aboutPage.addGroupItem(2131166289, "Agus JCoderz", "Modder", null);
        aboutPage.addGroupItem(2131166311, "Aldi Sayuti", "Modder", null);
        aboutPage.addGroupItem(2131166375, "Hilal Saif", "Modder", null);
        aboutPage.addGroupItem(2131166286, "Mike Anderson - Hey! Studios", "Modder", null);
        aboutPage.addGroupItem(2131166287, "IndoSW", "Modder", null);
        aboutPage.addGroupTitle("Special Thanks");
        aboutPage.addGroupItem("Auwal(Emptyset)");
        aboutPage.addGroupItem("Ani1nonly");
        aboutPage.addGroupItem("Dava");
        aboutPage.addGroupItem("Ilyasse Salama");
        aboutPage.addGroupItem("Zarzo");
        aboutPage.addGroupItem("AlucardTN");
        aboutPage.addGroupItem("Jbk0");
        aboutPage.addGroupItem("tyron");
        setContentView(aboutPage.createView());
    }

    @Override
    public void onClick(View view) {
        String obj = view.getTag().toString();
        switch (obj) {
            case "Info":
                dialog(obj, "This is a Sketchware mod that was re-modded from the Sketchware Revolution by Agus Jcoderz.\n\nOur Modder Team:\n- Aldi Sayuti\n- Hilal Saif\n- Mike Anderson\n\nPlease give credit when you re-mod or share.");
                break;
            case "Update Log":
                dialog(obj, CHANGELOG);
                break;
            case "Discord Server":
                startActivity(new Intent().setAction(Intent.ACTION_VIEW).setData(Uri.parse("https://discord.gg/p7D5Nt687K")));
                break;
        }
    }

    private void dialog(String str, String str2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 4);
        builder.setTitle(str);
        builder.setMessage(str2);
        builder.setPositiveButton("DISMISS", null);
        builder.create().show();
    }

    public static String getChangelog() {
        return CHANGELOG;
    }
}
