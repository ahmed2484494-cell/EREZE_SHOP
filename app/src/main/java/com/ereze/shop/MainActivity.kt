package com.ereze.shop

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import android.widget.*

class MainActivity : Activity() {
    private lateinit var wm: WindowManager
    private var floating: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(30,50,30,30) }
        val title=TextView(this).apply { text="EREZE SHOP"; textSize=30f; setTextColor(Color.GREEN) }
        val info=TextView(this).apply { text="\nHACKER SIMULATOR\nواجهة وهمية فقط"; textSize=18f }
        val start=Button(this).apply { text="تشغيل الواجهة العائمة" }
        layout.addView(title); layout.addView(info); layout.addView(start); setContentView(layout)
        start.setOnClickListener { if(!Settings.canDrawOverlays(this)) startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))) else showFloating() }
    }
    private fun showFloating() {
        if(floating!=null) return
        wm=getSystemService(WINDOW_SERVICE) as WindowManager
        floating=TextView(this).apply { text="E"; textSize=25f; gravity=Gravity.CENTER; setTextColor(Color.GREEN); setBackgroundColor(Color.BLACK); setOnClickListener{showPanel()} }
        val p=WindowManager.LayoutParams(150,150,WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT)
        p.gravity=Gravity.TOP or Gravity.START; p.x=30; p.y=300; wm.addView(floating,p)
    }
    private fun showPanel() {
        val panel=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(25,25,25,25);setBackgroundColor(Color.BLACK)}
        val title=TextView(this).apply{text="EREZE SHOP";textSize=25f;setTextColor(Color.GREEN)}
        val terminal=TextView(this).apply{text="> SYSTEM ONLINE\n> READY\n> SIMULATION MODE";textSize=16f;setTextColor(Color.GREEN);setPadding(0,20,0,20)}
        val scan=Button(this).apply{text="SCAN TARGET"}; val connect=Button(this).apply{text="CONNECT"}; val close=Button(this).apply{text="CLOSE"}
        panel.addView(title);panel.addView(terminal);panel.addView(scan);panel.addView(connect);panel.addView(close)
        val p=WindowManager.LayoutParams(800,WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT);p.gravity=Gravity.CENTER;wm.addView(panel,p)
        scan.setOnClickListener{terminal.text="> SCANNING...\n\n[#####...............] 25%\n[##########..........] 50%\n[###############.....] 75%\n[####################] 100%\n\n> SCAN COMPLETE\n> SIMULATION ONLY"}
        connect.setOnClickListener{terminal.text="> CONNECTING...\n\n> SERVER: DEMO\n> STATUS: CONNECTED\n\n> NO REAL ACCESS"}
        close.setOnClickListener{wm.removeView(panel)}
    }
    override fun onDestroy(){floating?.let{try{wm.removeView(it)}catch(_:Exception){}};floating=null;super.onDestroy()}
}
