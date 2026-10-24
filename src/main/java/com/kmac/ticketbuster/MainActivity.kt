package com.kmac.ticketbuster

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
        setContentView(webView)

        // Fullscreen, no network needed: the game is a bundled local asset.
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true          // required for localStorage (high scores)
        settings.databaseEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
        settings.setSupportZoom(false)

        // Keep navigation inside the WebView (there's nothing to navigate to,
        // but this is a safe default for a kiosk-style app).
        webView.webViewClient = WebViewClient()

        hideSystemBars()

        webView.loadUrl("file:///android_asset/game.html")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemBars()
    }

    private fun hideSystemBars() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                it.hide(android.view.WindowInsets.Type.systemBars())
                it.systemBarsBehavior =
                    android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
            )
        }
    }

    // Gamepad (and D-pad/keyboard) button presses arrive here as normal
    // Android KeyEvents. The WebView's own JavaScript Gamepad API handling
    // (already built into the game) is what actually drives gameplay —
    // this override just ensures the system doesn't intercept BACK on a
    // connected gamepad and close the app mid-session by accident.
    override fun onBackPressed() {
        // Intentionally does nothing: this is a kiosk app with one screen.
        // Remove this override if you want BACK to be able to exit the app.
    }
}
