package com.test.fvba.testapp.runner

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.support.test.runner.AndroidJUnitRunner
import com.test.fvba.testapp.TestApplicationTest
import timber.log.Timber


open class TestApplicationTestRunner : AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(classLoader: ClassLoader, className: String, context: Context): Application {
        // replace Application class with mock one
        return super.newApplication(classLoader, TestApplicationTest::class.java.name, context)
    }

    /*
     *Fix android emulator issues on CIs
     */
    override fun onStart() {
        runOnMainSync {
            val app = this@TestApplicationTestRunner.targetContext.applicationContext

            this@TestApplicationTestRunner.disableAnimations(app)

            val name = TestApplicationTestRunner::class.java.simpleName
            unlockScreen(app, name)
            keepScreenAwake(app, name)
        }

        super.onStart()
    }


    override fun finish(resultCode: Int, results: Bundle) {
        super.finish(resultCode, results)
        enableAnimations(context)
    }

    private fun keepScreenAwake(app: Context, name: String) {
        val power = app.getSystemService(Context.POWER_SERVICE) as PowerManager
        power.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE, name)
                .acquire()
    }

    private fun unlockScreen(app: Context, name: String) {
        val manager = app.getSystemService(Context.POWER_SERVICE) as PowerManager
        manager.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE, "TempWakeLock")

    }

    internal fun disableAnimations(context: Context) {
        val permStatus = context.checkCallingOrSelfPermission(android.Manifest.permission.SET_ANIMATION_SCALE)
        if (permStatus == PackageManager.PERMISSION_GRANTED) {
            setSystemAnimationsScale(0.0f)
        }
    }

    internal fun enableAnimations(context: Context) {
        val permStatus = context.checkCallingOrSelfPermission(android.Manifest.permission.SET_ANIMATION_SCALE)
        if (permStatus == PackageManager.PERMISSION_GRANTED) {
            setSystemAnimationsScale(1.0f)
        }
    }

    private fun setSystemAnimationsScale(animationScale: Float) {
        try {
            val windowManagerStubClazz = Class.forName("android.view.IWindowManager\$Stub")
            val asInterface = windowManagerStubClazz.getDeclaredMethod("asInterface", IBinder::class.java)
            val serviceManagerClazz = Class.forName("android.os.ServiceManager")
            val getService = serviceManagerClazz.getDeclaredMethod("getService", String::class.java)
            val windowManagerClazz = Class.forName("android.view.IWindowManager")
            val setAnimationScales = windowManagerClazz.getDeclaredMethod("setAnimationScales", FloatArray::class.java)
            val getAnimationScales = windowManagerClazz.getDeclaredMethod("getAnimationScales")

            val windowManagerBinder = getService.invoke(null, "window") as IBinder
            val windowManagerObj = asInterface.invoke(null, windowManagerBinder)
            val currentScales = getAnimationScales.invoke(windowManagerObj) as FloatArray
            for (i in currentScales.indices) {
                currentScales[i] = animationScale
            }
            setAnimationScales.invoke(windowManagerObj, *arrayOf<Any>(currentScales))
            Timber.d("Changed permissions of animations")
        } catch (e: Exception) {
            Timber.e("Could not change animation scale to %s", animationScale)
        }

    }

}