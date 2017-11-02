package org.khidmatalnaas.shetanidhang


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_content.view.*
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.preference.PreferenceManager
import android.widget.Toast
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


/**
 * A simple [Fragment] subclass.
 */
class ContentFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {




        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_content, container, false)

        val bodyContent = arguments?.getString("content_body") ?: ""
        val titleString = arguments?.getString("title_string") ?: ""

        activity?.findViewById<TextView>(R.id.tv_tasmia)?.text = titleString
        view.cf_tv_content.text = Html.fromHtml(bodyContent)


        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val colorName = sharedPreferences.getInt("color_foreground",255)

        if(colorName>255) {
            view.cf_tv_content.setTextColor(colorName);
        }

        return view
    }

}// Required empty public constructor
