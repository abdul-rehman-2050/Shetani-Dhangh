package org.khidmatalnaas.shetanidhang


import android.content.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_content.view.*
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.preference.PreferenceManager
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_content.*
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import android.support.v4.content.LocalBroadcastManager
import android.util.Log


/**
 * A simple [Fragment] subclass.
 */
class ContentFragment : Fragment() {


    var myView:View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {




        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_content, container, false)

        val bodyContent = arguments?.getString("content_body") ?: ""
        val titleString = arguments?.getString("title_string") ?: ""

        activity?.findViewById<TextView>(R.id.tv_tasmia)?.text = titleString
        view.cf_tv_content.text = Html.fromHtml(bodyContent)


        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val colorName = sharedPreferences.getInt("color_foreground",0)
        val textSize = sharedPreferences.getInt("textSize",0)

        if(colorName!=0) {
            view.cf_tv_content.setTextColor(colorName);
        }
        else{
            view.cf_tv_content.setTextColor(Color.BLACK)
        }

        //Toast.makeText(context,"color is $colorName",Toast.LENGTH_LONG).show()
        LocalBroadcastManager.getInstance(context!!).registerReceiver(mMessageReceiver,
                 IntentFilter("custom-event-name"));

        if(textSize!=0) {
            view.cf_tv_content.textSize = (textSize).toFloat()
        }
        myView = view
        return view
    }


    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private val mMessageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Get extra data included in the Intent
            val message = intent.getStringExtra("message")
            Log.d("receiver", "Got message: " + message)


            var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val colorName = sharedPreferences.getInt("color_foreground",0)
            val textSize = sharedPreferences.getInt("textSize",0)

          //  Toast.makeText(context,"color is $colorName",Toast.LENGTH_SHORT).show()

            if(colorName!=0) {
                myView?.cf_tv_content?.setTextColor(colorName)
            }
            else{
                myView?.cf_tv_content?.setTextColor(Color.BLACK)
            }

            if(textSize!=0) {

                myView?.cf_tv_content?.textSize = (textSize).toFloat()
            }
        }
    }

}// Required empty public constructor
