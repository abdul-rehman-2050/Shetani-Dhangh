package org.khidmatalnaas.shetanidhang

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper





class MainActivity : AppCompatActivity(), ItemFragment.OnListFragmentInteractionListener {

    val fontName = "alvi_Nastaleeq_Lahori_shipped.ttf"

    override fun onListFragmentInteraction(item: String) {
       Toast.makeText(baseContext,item,Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/$fontName")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setContentView(R.layout.activity_main)


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
        bundle.putStringArrayList("titleList",dummyArray)
        itemFrag.setArguments(bundle)

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, itemFrag).commit();

    }

     override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
