package tech.podolak.lukas.ip_scanner_v2

import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        val ipAddress = getLocalIpAddress()
        findViewById<TextView>(R.id.ip_text).text = "Your IPv4 address is: $ipAddress"

        val scannerButton: Button = findViewById(R.id.scann_button)
        scannerButton.setOnClickListener {
            val ipList = scannNetworkIPs()

            findViewById<RecyclerView>(R.id.recycler_view).adapter = RvAdapter(ipList)
            findViewById<RecyclerView>(R.id.recycler_view).layoutManager = LinearLayoutManager(this)
            findViewById<RecyclerView>(R.id.recycler_view).setHasFixedSize(true)
        }
    }

    fun scannNetworkIPs(): List<RvItem> {
        val wm: WifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val splitIP = Formatter.formatIpAddress(wm.connectionInfo.ipAddress).split('.').toTypedArray()
        val list = ArrayList<RvItem>()

        for (i in 1 .. 255) {
            val ip = splitIP[0] + "." + splitIP[1] + "." + splitIP[2] + "." + i

            list += if (Socket(ip, 7).isBound) {
                RvItem(R.drawable.ic_check, ip, "This IP Address is available.")
            } else {
                RvItem(R.drawable.ic_using, ip, "This IP Address is not available.")
            }
        }

        return list
    }

    fun getLocalIpAddress(): String? {
        val wm: WifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        return Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
    }
}