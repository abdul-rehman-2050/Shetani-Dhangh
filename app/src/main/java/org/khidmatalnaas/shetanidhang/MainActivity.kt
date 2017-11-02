package org.khidmatalnaas.shetanidhang

import android.app.Dialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import android.support.annotation.IdRes
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.content.DialogInterface
import android.os.Build
import android.support.v7.app.AlertDialog
import android.content.SharedPreferences
import android.graphics.Color
import android.preference.PreferenceManager
import android.widget.Toast
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener


class MainActivity : AppCompatActivity(), ItemFragment.OnListFragmentInteractionListener, ColorPickerDialogListener {


    override fun onDialogDismissed(dialogId: Int) {

    }

    override fun onColorSelected(dialogId: Int, color: Int) {

        //Toast.makeText(baseContext,"color is $color",Toast.LENGTH_SHORT).show()
        val edit = PreferenceManager.getDefaultSharedPreferences(this).edit()
        edit.putInt("color_foreground", color)
        edit.commit()

    }

    val fontName = "alvi_Nastaleeq_Lahori_shipped.ttf"
    var mdb:MyDBAccess? = null
    val MENU_ITEM_ITEM1 = 1


    override fun onListFragmentInteraction(item: String) {
       //Toast.makeText(baseContext,item,Toast.LENGTH_SHORT).show()

        val fragContent = ContentFragment()
        val args = Bundle()

        args.putString("content_body",mdb?.getDescription(item) ?: "No value found")
        args.putString("title_string",item)

        fragContent.arguments = args


        replaceFragment(R.id.fragmentContainer,fragContent,
                "content frag",
                "title_to_content")


        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val fontName = sharedPreferences.getString("fontName","")
        replaceFont(fontName)


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

        addFragment(R.id.fragmentContainer,itemFrag,"Item Frag")


    }

    protected fun addFragment(@IdRes containerViewId: Int,
                              fragment: Fragment,
                              fragmentTag: String) {
        supportFragmentManager
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit()
    }

    protected fun replaceFragment(@IdRes containerViewId: Int,
                                  fragment: Fragment,
                                  fragmentTag: String,
                                  @Nullable backStackStateName: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit()
    }

     override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menu?.add(Menu.NONE, 1, Menu.NONE, "رسم الخط");
        menu?.add(Menu.NONE, 2, Menu.NONE, "لفظی حجم");
        menu?.add(Menu.NONE, 3, Menu.NONE, "ظاہری رنگ");



      // menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

       when(item?.itemId){
           1 -> {

               val fontNames = arrayOf<CharSequence>("علوی نستعلیق",
                       "فجر نوری نستعلیق",
                       "ویب نقش",
                       "پاک نستعلیق")

               val alert = AlertDialog.Builder(this)

               alert.setTitle("برائے مہربانی اپنے پسندیدہ رسم الخط کا انتخاب فرمائیں")
               alert.setPositiveButton("منتخب کریں",null)
               alert.setNegativeButton("ارادہ ملتوی کریں",null)



               alert.setSingleChoiceItems(fontNames, -1, DialogInterface.OnClickListener { dialog, which ->


                   var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                   var editor: SharedPreferences.Editor = sharedPreferences.edit()
                   editor.putString("fontName", fontNames[which].toString())
                   editor.commit()
                   replaceFont(fontNames[which].toString())


               })
               val dlg = alert.create()
               // Here you can change the layout direction via setLayoutDirection()
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                   dlg.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
               }
               dlg.show()
               return true
           }


           2->{

               return true
           }

           3->{
               ColorPickerDialog.newBuilder().setColor(Color.BLACK).show(this)

               return true
           }


           else -> {return super.onOptionsItemSelected(item)}
       }




    }


    private  fun replaceFont(fontName:String){
        val fontList = ArrayList<String>()
        fontList.clear()
        fontList.add("alvi_Nastaleeq_Lahori_shipped.ttf")
        fontList.add("asunaskh.ttf")
        fontList.add("Fajer_Noori.ttf")
        fontList.add("Pak_Nastaleeq.ttf")

        when(fontName){
            "علوی نستعلیق" ->{

                CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/"+fontList[0])
                        .setFontAttrId(R.attr.fontPath)
                        .build())

            }
            "فجر نوری نستعلیق"->{

                CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/"+fontList[2])
                        .setFontAttrId(R.attr.fontPath)
                        .build())

            }
            "ویب نقش"->{

                CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/"+fontList[1])
                        .setFontAttrId(R.attr.fontPath)
                        .build())

            }
            "پاک نستعلیق"->{


                CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/"+fontList[3])
                        .setFontAttrId(R.attr.fontPath)
                        .build())

            }


        }

    }


    override fun onStop() {
        super.onStop()
        mdb?.closeMyDB()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        tv_tasmia.text = getString(R.string.tasmia)
    }
}
