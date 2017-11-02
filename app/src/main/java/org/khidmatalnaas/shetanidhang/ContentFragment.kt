package org.khidmatalnaas.shetanidhang


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_content.view.*


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
        //view.cf_tv_title.text = titleString
        view.cf_tv_content.text = Html.fromHtml(bodyContent)


        return view
    }

}// Required empty public constructor
