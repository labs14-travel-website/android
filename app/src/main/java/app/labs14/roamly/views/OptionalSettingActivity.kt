package app.labs14.roamly.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import app.labs14.roamly.R
import app.labs14.roamly.models.Orientation
import app.labs14.roamly.models.TimelineAttributes
import app.labs14.roamly.utils.BorderedCircle
import app.labs14.roamly.utils.RoundedCornerBottomSheet
import com.github.vipulasri.timelineview.TimelineView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.thebluealliance.spectrum.SpectrumDialog
import kotlinx.android.synthetic.main.options_appearance_vertical.*
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class OptionalSettingActivity: RoundedCornerBottomSheet() {

    interface Callbacks {
        fun onAttributesChanged(attributes: TimelineAttributes)
    }

    companion object {

        private const val EXTRA_ATTRIBUTES = "EXTRA_ATTRIBUTES"

        fun showDialog(fragmentManager: FragmentManager, attributes: TimelineAttributes, callbacks: Callbacks) {
            val dialog = OptionalSettingActivity()
            dialog.arguments = bundleOf(
                    EXTRA_ATTRIBUTES to attributes
            )
            dialog.setCallback(callbacks)
            dialog.show(fragmentManager, "[TIMELINE_ATTRIBUTES_BOTTOM_SHEET]")
        }
    }

    private var mCallbacks: Callbacks? = null
    private lateinit var mAttributes: TimelineAttributes
    private var mBottomSheetBehavior: BottomSheetBehavior<*>? = null

    override fun onStart() {
        super.onStart()

        if (dialog != null) {
            val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        view?.post {
            val parent = view?.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            mBottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            mBottomSheetBehavior?.peekHeight = view?.measuredHeight!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme)

        return inflater.cloneInContext(contextThemeWrapper).inflate(R.layout.options_appearance_vertical, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val attributes = (arguments!!.getParcelable(EXTRA_ATTRIBUTES) as TimelineAttributes)
        mAttributes = attributes.copy()

        //orientation
        rg_orientation.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.rb_horizontal -> {
                    mAttributes.orientation = Orientation.HORIZONTAL
                }
                R.id.rb_vertical -> {
                    mAttributes.orientation = Orientation.VERTICAL
                }
            }
        }
        rg_orientation.check(if(mAttributes.orientation == Orientation.VERTICAL) R.id.rb_vertical else R.id.rb_horizontal)

        //Shoon 2019/08/28
        //Text and background color
        image_textregular_color.mFillColor=mAttributes.textColorRegular!!
        image_textregular_color.setOnClickListener{
            showColorPicker(mAttributes.textColorRegular!!, image_textregular_color)
        }
        image_textexpanded_color.mFillColor=mAttributes.textColorExpanded!!
        image_textexpanded_color.setOnClickListener{
            showColorPicker(mAttributes.textColorExpanded!!, image_textexpanded_color)
        }
        image_textediting_color.mFillColor=mAttributes.textColorEdting!!
        image_textediting_color.setOnClickListener{
            showColorPicker(mAttributes.textColorEdting!!, image_textediting_color)
        }
        image_bgregular_color.mFillColor= mAttributes.bgColorRegular!!
        image_bgregular_color.setOnClickListener{
            showColorPicker(mAttributes.bgColorRegular!!, image_bgregular_color)
        }
        image_bgexpanded_color.mFillColor= mAttributes.bgColorExpanded!!
        image_bgexpanded_color.setOnClickListener{
            showColorPicker(mAttributes.bgColorExpanded!!, image_bgexpanded_color)
        }
        image_bgediting_color.mFillColor= mAttributes.bgColorEdting!!
        image_bgediting_color.setOnClickListener{
            showColorPicker(mAttributes.bgColorEdting!!, image_bgediting_color)
        }




        //marker
        seek_marker_size.progress = mAttributes.markerSize
        image_marker_color.mFillColor = mAttributes.markerColor
        checkbox_marker_in_center.isChecked = mAttributes.markerInCenter
        seek_marker_line_padding.progress = mAttributes.linePadding

        checkbox_marker_in_center.setOnCheckedChangeListener { buttonView, isChecked ->
            mAttributes.markerInCenter = isChecked
        }

        image_marker_color.setOnClickListener { showColorPicker(mAttributes.markerColor, image_marker_color) }

        seek_marker_size.setOnProgressChangeListener(progressChangeListener)
        seek_marker_line_padding.setOnProgressChangeListener(progressChangeListener)

        //line

        Log.e(" mAttributes.lineWidth", "${ mAttributes.lineWidth}")

        seek_line_width.progress = mAttributes.lineWidth
        image_start_line_color.mFillColor = mAttributes.startLineColor
        image_end_line_color.mFillColor = mAttributes.endLineColor

        image_start_line_color.setOnClickListener { showColorPicker(mAttributes.startLineColor, image_start_line_color) }
        image_end_line_color.setOnClickListener { showColorPicker(mAttributes.endLineColor, image_end_line_color) }

        when(mAttributes.lineStyle) {
            TimelineView.LineStyle.NORMAL -> spinner_line_type.setSelection(0)
            TimelineView.LineStyle.DASHED -> spinner_line_type.setSelection(1)
        }

        spinner_line_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                when (selectedItem) {
                    "Normal" -> mAttributes.lineStyle = TimelineView.LineStyle.NORMAL
                    "Dashed" -> mAttributes.lineStyle = TimelineView.LineStyle.DASHED
                    else -> {
                        mAttributes.lineStyle = TimelineView.LineStyle.NORMAL
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        seek_line_dash_width.progress = mAttributes.lineDashWidth
        seek_line_dash_gap.progress = mAttributes.lineDashGap

        seek_line_width.setOnProgressChangeListener(progressChangeListener)
        seek_line_dash_width.setOnProgressChangeListener(progressChangeListener)
        seek_line_dash_gap.setOnProgressChangeListener(progressChangeListener)

        button_apply.setOnClickListener {
            mCallbacks?.onAttributesChanged(mAttributes)
            dismiss()
        }
    }

    private fun showColorPicker(selectedColor : Int, colorView: BorderedCircle) {
        SpectrumDialog.Builder(requireContext())
                .setColors(R.array.colors)
                .setSelectedColor(selectedColor)
                .setDismissOnColorSelected(true)
                .setOutlineWidth(1)
                .setOnColorSelectedListener { positiveResult, color ->
                    if (positiveResult) {
                        colorView.mFillColor = color

                        when(colorView.id) {
                            R.id.image_textregular_color->{mAttributes.textColorRegular=color}
                            R.id.image_textexpanded_color->{mAttributes.textColorExpanded=color}
                            R.id.image_textediting_color->{mAttributes.textColorEdting=color}
                            R.id.image_bgregular_color->{mAttributes.bgColorRegular=color}
                            R.id.image_bgexpanded_color->{mAttributes.bgColorExpanded=color}
                            R.id.image_bgediting_color->{mAttributes.bgColorEdting=color}
                            R.id.image_marker_color ->  { mAttributes.markerColor = color }
                            R.id.image_start_line_color -> { mAttributes.startLineColor = color }
                            R.id.image_end_line_color -> { mAttributes.endLineColor = color }
                            else -> {
                                //do nothing
                            }
                        }

                    }
                }.build().show(childFragmentManager, "ColorPicker")
    }

    private var progressChangeListener: DiscreteSeekBar.OnProgressChangeListener = object : DiscreteSeekBar.OnProgressChangeListener {

        override fun onProgressChanged(discreteSeekBar: DiscreteSeekBar, value: Int, b: Boolean) {

            Log.d("onProgressChanged", "value->$value")
            when(discreteSeekBar.id) {
                R.id.seek_marker_size ->  { mAttributes.markerSize = value }
                R.id.seek_marker_line_padding ->  { mAttributes.linePadding = value }
                R.id.seek_line_width -> { mAttributes.lineWidth = value }
                R.id.seek_line_dash_width -> { mAttributes.lineDashWidth = value }
                R.id.seek_line_dash_gap -> { mAttributes.lineDashGap = value }
            }
        }

        override fun onStartTrackingTouch(discreteSeekBar: DiscreteSeekBar) {

        }

        override fun onStopTrackingTouch(discreteSeekBar: DiscreteSeekBar) {

        }
    }

    private fun setCallback(callbacks: Callbacks) {
        mCallbacks = callbacks
    }

}