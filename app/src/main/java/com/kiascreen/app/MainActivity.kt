
package com.kiascreen.app

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.widget.LinearLayout
import android.widget.EditText
import android.widget.Button
import android.view.inputmethod.EditorInfo

class MainActivity : Activity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val addressBar = EditText(this)
        addressBar.hint = "Adresse du site Internet"
        addressBar.setSingleLine(true)
        addressBar.inputType = 17
        addressBar.imeOptions = EditorInfo.IME_ACTION_GO

        val goButton = Button(this)
        goButton.text = "GO"

        val navigation = LinearLayout(this)
        navigation.orientation = LinearLayout.HORIZONTAL
        navigation.addView(
            addressBar,
            LinearLayout.LayoutParams(0, -2, 1f)
        )
        navigation.addView(goButton)

        webView = WebView(this)
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = true

        layout.addView(navigation)
        layout.addView(
            webView,
            LinearLayout.LayoutParams(-1, 0, 1f)
        )

        setContentView(layout)

        fun openWebsite() {
            var url = addressBar.text.toString().trim()

            if (!url.startsWith("https://") &&
                !url.startsWith("http://")) {
                url = "https://" + url
            }

            webView.loadUrl(url)
        }

        goButton.setOnClickListener {
            openWebsite()
        }

        addressBar.setOnEditorActionListener { _, _, _ ->
            openWebsite()
            true
        }

        webView.loadUrl("https://www.google.com")
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}
