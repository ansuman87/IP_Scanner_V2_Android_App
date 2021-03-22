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
    private val itemList = ArrayList<RvItem>()
    private val adapter = RvAdapter(itemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<RecyclerView>(R.id.recycler_view).adapter = adapter
        findViewById<RecyclerView>(R.id.recycler_view).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recycler_view).setHasFixedSize(true)

        val ipAddress = getLocalIpAddress()
        findViewById<TextView>(R.id.ip_text).text = "Your IPv4 address is: $ipAddress"

        val scannerButton: Button = findViewById(R.id.scann_button)
        scannerButton.setOnClickListener {
            scannNetworkIPs()
        }
    }

    fun scannNetworkIPs() {
        val wm: WifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val splitIP = Formatter.formatIpAddress(wm.connectionInfo.ipAddress).split('.').toTypedArray()

        for (i in 1 .. 15) {
            val ip = splitIP[0] + "." + splitIP[1] + "." + splitIP[2] + "." + i

            if (isConnectedToThisServer(ip)) {
                itemList.add(i - 1, RvItem(R.drawable.ic_check, ip, "This IP Address is available."))
            } else {
                itemList.add(i - 1, RvItem(R.drawable.ic_using, ip, "This IP Address is not available."))
            }

            adapter.notifyItemInserted(i - 1)
        }
    }

    fun isConnectedToThisServer(host: String): Boolean {
        val runTime: Runtime = Runtime.getRuntime()

        val ipPrco: Process = runTime.exec("/system/bin/ping -c 1 " + host)
        val exitValue: Int = ipPrco.waitFor()

        if (exitValue == 0) {
            return false
        }
        else {
            return true
        }
    }

    fun getLocalIpAddress(): String? {
        val wm: WifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        return Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
    }
}