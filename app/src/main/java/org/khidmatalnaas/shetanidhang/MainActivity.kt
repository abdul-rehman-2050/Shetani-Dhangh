package org.khidmatalnaas.shetanidhang

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper





class MainActivity : AppCompatActivity(), ItemFragment.OnListFragmentInteractionListener {

    val fontName = "alvi_Nastaleeq_Lahori_shipped.ttf"
    var mdb:MyDBAccess? = null

    override fun onListFragmentInteraction(item: String) {
       //Toast.makeText(baseContext,item,Toast.LENGTH_SHORT).show()

        val fragContent = ContentFragment()
        val args = Bundle()

        args.putString("content_body",mdb?.getDescription(item) ?: "No value found")
        args.putString("title_string",item)

        fragContent.arguments = args

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragContent)
                .addToBackStack("title_to_content")
                .commit();




    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/$fontName")
                .setFontAttrId(R.attr.fontPath)
                .build())

        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL;
        setContentView(R.layout.activity_main)



        mdb = MyDBAccess(this);
        val dummyArray = ArrayList<String?>()



        dummyArray.add("انڈیا")
        dummyArray.add("ایران")
        dummyArray.add("بنگلہ دیش")
        dummyArray.add("پاکستان")


        // Create a new Fragment to be placed in the activity layout
        val itemFrag = ItemFragment()

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        val bundle = Bundle()
        bundle.putStringArrayList("titleList",mdb?.getAllTitlesFromTableBook1() ?: dummyArray)
        itemFrag.arguments = bundle

        // Add the fragment to the 'fragment_container' FrameLayout
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, itemFrag).commit();

    }

     override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//       menuInflater.inflate(R.menu.main_menu,menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return super.onOptionsItemSelected(item)
//    }


    override fun onStop() {
        super.onStop()
        mdb?.closeMyDB()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        tv_tasmia.text = getString(R.string.tasmia)
    }
}
