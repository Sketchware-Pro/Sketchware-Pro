package com.besome.sketch.help

import a.a.a.GB
import a.a.a.aB
import a.a.a.bB
import a.a.a.mB
import a.a.a.wB
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.besome.sketch.lib.base.BaseAppCompatActivity
import com.besome.sketch.lib.ui.PropertyOneLineItem
import com.besome.sketch.lib.ui.PropertyTwoLineItem
import com.sketchware.remod.R
import com.sketchware.remod.databinding.ProgramInfoBinding
import mod.hey.studios.util.Helper

class ProgramInfoActivity : BaseAppCompatActivity() {
    private var binding: ProgramInfoBinding? = null

    private fun addTwoLineItem(key: Int, name: Int, description: Int) {
        addTwoLineItem(key, getString(name), getString(description))
    }

    private fun addTwoLineItem(key: Int, name: Int, description: Int, hideDivider: Boolean) {
        addTwoLineItem(key, getString(name), getString(description), hideDivider)
    }

    private fun addTwoLineItem(
        key: Int,
        name: String,
        description: String,
        hideDivider: Boolean = false
    ) {
        val item = PropertyTwoLineItem(this)
        item.key = key
        item.setName(name)
        item.setDesc(description)
        item.setHideDivider(hideDivider)
        binding!!.content.addView(item)
        item.setOnClickListener { v: View -> this.handleItem(v) }
    }

    private fun addSingleLineItem(key: Int, name: Int) {
        addSingleLineItem(key, getString(name))
    }

    private fun addSingleLineItem(key: Int, name: Int, hideDivider: Boolean) {
        addSingleLineItem(key, getString(name), hideDivider)
    }

    private fun addSingleLineItem(key: Int, name: String, hideDivider: Boolean = false) {
        val item = PropertyOneLineItem(this)
        item.key = key
        item.setName(name)
        item.setHideDivider(hideDivider)
        binding!!.content.addView(item)
        if (key == ITEM_SYSTEM_INFORMATION || key == ITEM_OPEN_SOURCE_LICENSES) {
            item.setOnClickListener { v: View -> this.handleItem(v) }
        }
    }

    private fun resetDialog(view: View) {
        val dialog = aB(this)
        dialog.b(Helper.getResString(R.string.program_information_reset_system_title))
        dialog.a(R.drawable.rollback_96)
        val rootView = wB.a(this, R.layout.all_init_popup)
        val radioGroup = rootView.findViewById<RadioGroup>(R.id.rg_type)
        (rootView.findViewById<View>(R.id.rb_all) as RadioButton).text =
            Helper.getResString(R.string.program_information_reset_system_title_all_settings_data)
        (rootView.findViewById<View>(R.id.rb_only_config) as RadioButton).text =
            Helper.getResString(R.string.program_information_reset_system_title_all_settings)
        dialog.a(rootView)
        dialog.b(
            Helper.getResString(R.string.common_word_yes)
        ) { v: View? ->
            if (!mB.a()) {
                val buttonId = radioGroup.checkedRadioButtonId
                val resetOnlySettings = buttonId != R.id.rb_all
                dialog.dismiss()
                setResult(
                    RESULT_OK,
                    intent.putExtra("onlyConfig", resetOnlySettings)
                )
                finish()
            }
        }
        dialog.a(
            Helper.getResString(R.string.common_word_cancel),
            Helper.getDialogDismissListener(dialog)
        )
        dialog.show()
    }

    private fun handleItem(v: View) {
        if (!mB.a()) {
            var key: Int
            if (v is PropertyOneLineItem) {
                key = v.key
                when (key) {
                    ITEM_SYSTEM_INFORMATION -> toSystemInfoActivity()
                    ITEM_OPEN_SOURCE_LICENSES -> {
                        if (!GB.h(applicationContext)) {
                            bB.a(
                                applicationContext,
                                Helper.getResString(R.string.common_message_check_network),
                                bB.TOAST_NORMAL
                            ).show()
                        } else {
                            toLicenseActivity()
                        }
                    }
                }
            }

            if (v is PropertyTwoLineItem) {
                key = v.key
                when (key) {
                    ITEM_DOCS_LOG -> openUrl(getString(R.string.link_docs_url))
                    ITEM_SUGGEST_IDEAS -> openUrl(getString(R.string.link_ideas_url))
                    ITEM_TELEGRAM -> openUrl(getString(R.string.link_telegram_invite))
                    ITEM_DISCORD -> openUrl(getString(R.string.link_discord_invite))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ProgramInfoBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.toolbar.setNavigationOnClickListener(
            Helper.getBackPressedClickListener(
                this
            )
        )
        binding!!.appVersion.text = GB.e(applicationContext)
        binding!!.btnReset.setOnClickListener { view: View ->
            this.resetDialog(
                view
            )
        }
        binding!!.btnUpgrade.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.link_github_release))
            )
            startActivity(intent)
        }

        addTwoLineItem(
            ITEM_DOCS_LOG,
            R.string.program_information_title_docs,
            R.string.link_docs_url
        )
        addTwoLineItem(
            ITEM_SUGGEST_IDEAS,
            R.string.program_information_title_suggest_ideas,
            R.string.link_ideas_url
        )
        addSingleLineItem(ITEM_SOCIAL_NETWORK, R.string.title_community)
        addTwoLineItem(ITEM_DISCORD, R.string.title_discord_community, R.string.link_discord_invite)
        addTwoLineItem(
            ITEM_TELEGRAM,
            R.string.title_telegram_community,
            R.string.link_telegram_invite
        )
        addSingleLineItem(
            ITEM_SYSTEM_INFORMATION,
            R.string.program_information_title_system_information
        )
        addSingleLineItem(
            ITEM_OPEN_SOURCE_LICENSES,
            R.string.program_information_title_open_source_license,
            true
        )
    }

    private fun toLicenseActivity() {
        val intent = Intent(
            applicationContext,
            LicenseActivity::class.java
        )
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    private fun toSystemInfoActivity() {
        val intent = Intent(
            applicationContext,
            SystemInfoActivity::class.java
        )
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    companion object {
        private const val ITEM_SYSTEM_INFORMATION = 1
        private const val ITEM_DOCS_LOG = 4
        private const val ITEM_SOCIAL_NETWORK = 5
        private const val ITEM_DISCORD = 6
        private const val ITEM_TELEGRAM = 8
        private const val ITEM_OPEN_SOURCE_LICENSES = 15
        private const val ITEM_SUGGEST_IDEAS = 17
    }
}
