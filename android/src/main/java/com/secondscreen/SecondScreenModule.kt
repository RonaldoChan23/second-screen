package com.secondscreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.display.DisplayManager
import android.view.Display
import android.widget.ImageView
import androidx.annotation.NonNull
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class SecondScreenModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName(): String {
        return "SecondScreenModule"
    }

    @ReactMethod
    fun checkAndSendImage(imagePath: String, promise: Promise) {
        val context = reactApplicationContext
        val displayManager = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val displays = displayManager.displays

        if (displays.size > 1) {
            val secondaryDisplay = displays[1]
            val image = BitmapFactory.decodeFile(imagePath)
            sendImageToDisplay(context, secondaryDisplay, image)
            promise.resolve("Image sent to secondary display")
        } else {
            promise.reject("NO_SECONDARY_DISPLAY", "No secondary display available")
        }
    }

    private fun sendImageToDisplay(context: Context, display: Display, image: Bitmap) {
        val presentation = ImagePresentation(context, display, image)
        presentation.show()
    }

    private class ImagePresentation(
        context: Context,
        display: Display,
        private val image: Bitmap
    ) : android.app.Presentation(context, display) {

        override fun onCreate(savedInstanceState: android.os.Bundle?) {
            super.onCreate(savedInstanceState)
            val imageView = ImageView(context)
            imageView.setImageBitmap(image)
            setContentView(imageView)
        }
    }
}
