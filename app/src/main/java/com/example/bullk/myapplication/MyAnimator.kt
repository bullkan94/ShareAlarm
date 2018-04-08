package com.example.bullk.myapplication

import android.animation.Animator

class MyAnimator(var _onAnimationStart : ((Animator?) -> Unit)? = null,
                 var _onAnimationRepeat : ((Animator?) -> Unit)? = null,
                 var _onAnimationCancel : ((Animator?) -> Unit)? = null,
                 var _onAnimationEnd : ((Animator?) -> Unit)? = null) : Animator.AnimatorListener{

    override fun onAnimationStart(p0: Animator?) {
        _onAnimationStart?.invoke(p0)
    }

    override fun onAnimationRepeat(p0 : Animator?) {
        _onAnimationRepeat?.invoke(p0)
    }

    override fun onAnimationCancel(p0: Animator?) {
        _onAnimationCancel?.invoke(p0)
    }

    override fun onAnimationEnd(p0: Animator?) {
        _onAnimationEnd?.invoke(p0)
    }
}

/*
    private fun GetDevicesUUID(mContext : Context) : String {
        val tm = mContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val tmp =
        ActivityCompat.requestPermissions(this, Array<String>(1, { _ -> android.Manifest.permission.READ_PHONE_STATE}), 0);
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            val tmDevice = "" + tm.getDeviceId()
            val tmSerial = "" + tm.getSimSerialNumber()
            val androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            val deviceUuid = UUID(androidId.hashCode().toLong(),
                    ((tmDevice.hashCode().toLong() shl 32) or tmSerial.hashCode().toLong()))
            val deviceId = deviceUuid.toString();

            return deviceId;
        } else {
            return "failed"
        }
    }*/