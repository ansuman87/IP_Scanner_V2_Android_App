package tech.podolak.lukas.ip_scanner_v2

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val testList = generateItemList(255)

        findViewById<RecyclerView>(R.id.recycler_view).adapter = RvAdapter(testList)
        findViewById<RecyclerView>(R.id.recycler_view).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.recycler_view).setHasFixedSize(true)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}