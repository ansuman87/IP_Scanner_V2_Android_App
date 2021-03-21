package tech.podolak.lukas.ip_scanner_v2

import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val testList = generateItemList(25)

        findViewById<RecyclerView>(R.id.recycler_view).adapter = RvAdapter(testList)
        findViewById<RecyclerView>(R.id.recycler_view).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recycler_view).setHasFixedSize(true)

        val ipAddress = getLocalIpAddress()
        findViewById<TextView>(R.id.ip_text).text = "Your IPv4 address is: $ipAddress"

    }

    fun getLocalIpAddress(): String? {
        val wm: WifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager

        return Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
    }

    private fun generateItemList(size: Int): List<RvItem> {
        val list = ArrayList<RvItem>()

        for (i in 0 until size) {
            val drawable = when (i % 3) {
                0 -> R.drawable.ic_check
                1 -> R.drawable.ic_using
                else -> R.drawable.ic_other
            }

            val item = RvItem(drawable, "Item $i", "Random second line bro.")
            list += item
        }

        return list
    }
}