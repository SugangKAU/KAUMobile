package com.example.kaumobile.ui.setting

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.kaumobile.R
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingFragment : PreferenceFragmentCompat() {

    @SuppressLint("ResourceType")
    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.layout.fragment_setting)
        val sps = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val prepareLock = sps.getBoolean("key_prepare_lock", false)
        val prepareNotify = sps.getBoolean("key_prepare_notify", false)
        val reviewLock = sps.getBoolean("key_review_lock", false)
        val reviewNotify = sps.getBoolean("key_review_notify", false)
        val assignLock = sps.getBoolean("key_assign_lock", false)
        val assignNotify = sps.getBoolean("key_assign_notify", false)
        val vibe = sps.getBoolean("key_vibe", false)
        val lms = sps.getBoolean("key_lms", false)

        if(prepareLock){

        }
    }
}