package jun88.rr.fb88.webview

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import jun88.rr.fb88.databinding.FragmentWebviewBinding
import jun88.rr.fb88.utils.FirebaseDb


class Webview : Fragment() {

    private var _binding: FragmentWebviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstangetLinkceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        getLink()
        init()


        return binding.root
    }

    private fun getLink(){
        FirebaseDb().getDatabase().addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged", "Set#Enabled")
            override fun onDataChange(snapshot: DataSnapshot) {

                for (ds in snapshot.children) {
                    val pn = ds.child("packageName").getValue(String::class.java)
                    val url = ds.child("url").getValue(String::class.java)
                    val sts = ds.child("status").getValue(Int::class.java)
                    Log.d("TAG", "$pn / $url")

                    val webView : WebView = binding.firstWeb

                    if(activity?.packageName ==  pn)
                        if (sts == 0){
                            webView.loadUrl(url.toString())

                        }else {
//                            webView.loadUrl("https://www.fb88you.com/en-US")
                           webView.loadUrl("file:///android_asset/fb88.html")
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }

    @SuppressLint("Set#Enabled")
    private fun init() {

        with(binding.firstWeb) {
            with(settings) {
                javaScriptEnabled = false
                defaultTextEncodingName = "UTF-8"
                cacheMode = WebSettings.LOAD_NO_CACHE
                useWideViewPort = true
                pluginState = WebSettings.PluginState.ON
                domStorageEnabled = true
                builtInZoomControls = false
                layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
                loadWithOverviewMode = true
                blockNetworkImage = true
                loadsImagesAutomatically = true
                setSupportZoom(false)
                setSupportMultipleWindows(true)
            }
            requestFocusFromTouch()
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        }

        val webseting: WebSettings = binding.firstWeb.settings
        with(webseting) {
            val appCacheDir = context?.getDir(
                "cache", AppCompatActivity.MODE_PRIVATE
            )?.path
            domStorageEnabled = true
            allowFileAccess = true
            cacheMode = WebSettings.LOAD_DEFAULT
        }

        binding.firstWeb.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                binding.pbLoading.progress = newProgress
                if (newProgress == 100) {
                    binding.firstWeb.settings.blockNetworkImage = false
                }
            }
            override fun onCreateWindow(
                view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message
            ): Boolean {
                val newWebView = context?.let { WebView(it) }
                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                newWebView?.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        binding.firstWeb.loadUrl(url)
                        if (url.startsWith("http") || url.startsWith("https")) {
                            return super.shouldOverrideUrlLoading(view, url)
                        } else if (url.startsWith(WebView.SCHEME_TEL) || url.startsWith(WebView.SCHEME_MAILTO)) {
                            val dialIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(dialIntent)
                        }
                        return true
                    }
                }
                return true
            }
        }

        val settings: WebSettings = binding.firstWeb.settings
        settings.javaScriptEnabled = false
        binding.firstWeb.setOnLongClickListener { v: View ->
            val result = (v as WebView).hitTestResult
            val type = result.type
            if (type == WebView.HitTestResult.UNKNOWN_TYPE) return@setOnLongClickListener false
            when (type) {
                WebView.HitTestResult.PHONE_TYPE -> {}
                WebView.HitTestResult.EMAIL_TYPE -> {}
                WebView.HitTestResult.GEO_TYPE -> {}
                WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE -> {}

                else -> {}
            }
            true
        }

        binding.firstWeb.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                binding.pbLoading.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                binding.pbLoading.visibility = View.GONE
            }

            override fun onReceivedError(
                view: WebView, request: WebResourceRequest, error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView, handler: SslErrorHandler, error: SslError
            ) {
                val builder = android.app.AlertDialog.Builder(context)
                var message = "SSL Certificate error."
                when (error.primaryError) {
                    SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted."
                    SslError.SSL_EXPIRED -> message = "The certificate has expired."
                    SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
                    SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
                }
                message += " Do you want to continue anyway?"
                builder.setTitle("SSL Certificate Error")
                builder.setMessage(message)
                builder.setPositiveButton(
                    "Continue"
                ) { _: DialogInterface?, _: Int -> handler.proceed() }
                builder.setNegativeButton(
                    "Cancel"
                ) { _: DialogInterface?, _: Int -> handler.cancel() }
                val dialog = builder.create()
                dialog.show()
            }
        }

        binding.firstWeb.setOnKeyListener { _: View?, i: Int, keyEvent: KeyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                if (i == KeyEvent.KEYCODE_BACK && binding.firstWeb.canGoBack()) {
                    binding.firstWeb.goBack()
                    return@setOnKeyListener true
                }
            }
            false
        }
    }
}