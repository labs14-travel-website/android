package app.labs14.roamly.views


import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.PathEffect
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import app.labs14.roamly.R

import app.labs14.roamly.utils.Utils


/**
 * Created by Vipul Asri on 05-12-2015.
 */
class TimelineView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mMarker: Drawable? = null
    private var mMarkerSize: Int = 0
    private var mMarkerInCenter: Boolean = false
    private val mLinePaint = Paint()
    private var mDrawStartLine = false
    private var mDrawEndLine = false
    private var mStartLineStartX: Float = 0.toFloat()
    private var mStartLineStartY: Float = 0.toFloat()
    private var mStartLineStopX: Float = 0.toFloat()
    private var mStartLineStopY: Float = 0.toFloat()
    private var mEndLineStartX: Float = 0.toFloat()
    private var mEndLineStartY: Float = 0.toFloat()
    private var mEndLineStopX: Float = 0.toFloat()
    private var mEndLineStopY: Float = 0.toFloat()
    var startLineColor: Int = 0
        private set
    var endLineColor: Int = 0
        private set
    private var mLineWidth: Int = 0
    var lineOrientation: Int = 0
    private var mLineStyle: Int = 0
    private var mLineStyleDashLength: Int = 0
    private var mLineStyleDashGap: Int = 0
    private var mLinePadding: Int = 0

    private var mBounds: Rect? = null

    /**
     * Sets marker.
     *
     * @param marker will set marker drawable to timeline
     */
    var marker: Drawable?
        get() = mMarker
        set(marker) {
            mMarker = marker
            initTimeline()
        }

    /**
     * Sets marker size.
     *
     * @param markerSize the marker size
     */
    var markerSize: Int
        get() = mMarkerSize
        set(markerSize) {
            mMarkerSize = markerSize
            initTimeline()
        }

    var isMarkerInCenter: Boolean
        get() = mMarkerInCenter
        set(markerInCenter) {
            this.mMarkerInCenter = markerInCenter
            initTimeline()
        }

    /**
     * Sets line width.
     *
     * @param lineWidth the line width
     */
    var lineWidth: Int
        get() = mLineWidth
        set(lineWidth) {
            mLineWidth = lineWidth
            initTimeline()
        }

    var lineStyle: Int
        get() = mLineStyle
        set(lineStyle) {
            this.mLineStyle = lineStyle
            initLinePaint()
        }

    var lineStyleDashLength: Int
        get() = mLineStyleDashLength
        set(lineStyleDashLength) {
            this.mLineStyleDashLength = lineStyleDashLength
            initLinePaint()
        }

    var lineStyleDashGap: Int
        get() = mLineStyleDashGap
        set(lineStyleDashGap) {
            this.mLineStyleDashGap = lineStyleDashGap
            initLinePaint()
        }

    /**
     * Sets line padding
     * @param padding the line padding
     */
    var linePadding: Int
        get() = mLinePadding
        set(padding) {
            mLinePadding = padding
            initTimeline()
        }


    annotation class LineOrientation {
        companion object {
            val HORIZONTAL = 0
            val VERTICAL = 1
        }
    }


    private annotation class LineType {
        companion object {
            val NORMAL = 0
            val START = 1
            val END = 2
            val ONLYONE = 3
        }
    }


    annotation class LineStyle {
        companion object {
            val NORMAL = 0
            val DASHED = 1
        }
    }

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimelineView)
        mMarker = typedArray.getDrawable(R.styleable.TimelineView_marker)
        mMarkerSize = typedArray.getDimensionPixelSize(R.styleable.TimelineView_markerSize, Utils.dpToPx(20f, context))
        mMarkerInCenter = typedArray.getBoolean(R.styleable.TimelineView_markerInCenter, true)
        startLineColor = typedArray.getColor(R.styleable.TimelineView_startLineColor, resources.getColor(android.R.color.darker_gray))
        endLineColor = typedArray.getColor(R.styleable.TimelineView_endLineColor, resources.getColor(android.R.color.darker_gray))
        mLineWidth = typedArray.getDimensionPixelSize(R.styleable.TimelineView_lineWidth, Utils.dpToPx(2f, context))
        lineOrientation = typedArray.getInt(R.styleable.TimelineView_lineOrientation, LineOrientation.VERTICAL)
        mLinePadding = typedArray.getDimensionPixelSize(R.styleable.TimelineView_linePadding, 0)
        mLineStyle = typedArray.getInt(R.styleable.TimelineView_lineStyle, LineStyle.NORMAL)
        mLineStyleDashLength = typedArray.getDimensionPixelSize(R.styleable.TimelineView_lineStyleDashLength, Utils.dpToPx(8f, context))
        mLineStyleDashGap = typedArray.getDimensionPixelSize(R.styleable.TimelineView_lineStyleDashGap, Utils.dpToPx(4f, context))
        typedArray.recycle()

        if (isInEditMode) {
            mDrawStartLine = true
            mDrawEndLine = true
        }

        if (mMarker == null) {
            mMarker = resources.getDrawable(R.drawable.marker)
        }

        initTimeline()
        initLinePaint()

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //Width measurements of the width and height and the inside view of child controls
        val w = mMarkerSize + paddingLeft + paddingRight
        val h = mMarkerSize + paddingTop + paddingBottom

        // Width and height to determine the final view through a systematic approach to decision-making
        val widthSize = View.resolveSizeAndState(w, widthMeasureSpec, 0)
        val heightSize = View.resolveSizeAndState(h, heightMeasureSpec, 0)

        setMeasuredDimension(widthSize, heightSize)
        initTimeline()
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        initTimeline()
    }

    private fun initTimeline() {

        val pLeft = paddingLeft
        val pRight = paddingRight
        val pTop = paddingTop
        val pBottom = paddingBottom

        val width = width// Width of current custom view
        val height = height

        val cWidth = width - pLeft - pRight// Circle width
        val cHeight = height - pTop - pBottom

        val markSize = Math.min(mMarkerSize, Math.min(cWidth, cHeight))

        if (mMarkerInCenter) { //Marker in center is true

            if (mMarker != null) {
                mMarker!!.setBounds(width / 2 - markSize / 2, height / 2 - markSize / 2, width / 2 + markSize / 2, height / 2 + markSize / 2)
                mBounds = mMarker!!.bounds
            }

        } else { //Marker in center is false

            if (mMarker != null) {
                mMarker!!.setBounds(pLeft, pTop, pLeft + markSize, pTop + markSize)
                mBounds = mMarker!!.bounds
            }
        }

        if (lineOrientation == LineOrientation.HORIZONTAL) {

            if (mDrawStartLine) {
                mStartLineStartX = pLeft.toFloat()
                mStartLineStartY = mBounds!!.centerY().toFloat()
                mStartLineStopX = (mBounds!!.left - mLinePadding).toFloat()
                mStartLineStopY = mBounds!!.centerY().toFloat()
            }

            if (mDrawEndLine) {
                mEndLineStartX = (mBounds!!.right + mLinePadding).toFloat()
                mEndLineStartY = mBounds!!.centerY().toFloat()
                mEndLineStopX = getWidth().toFloat()
                mEndLineStopY = mBounds!!.centerY().toFloat()
            }
        } else {

            if (mDrawStartLine) {
                mStartLineStartX = mBounds!!.centerX().toFloat()

                if (mLineStyle == LineStyle.DASHED) {
                    mStartLineStartY = (pTop - mLineStyleDashLength).toFloat()
                } else {
                    mStartLineStartY = pTop.toFloat()
                }

                mStartLineStopX = mBounds!!.centerX().toFloat()
                mStartLineStopY = (mBounds!!.top - mLinePadding).toFloat()
            }

            if (mDrawEndLine) {
                mEndLineStartX = mBounds!!.centerX().toFloat()
                mEndLineStartY = (mBounds!!.bottom + mLinePadding).toFloat()
                mEndLineStopX = mBounds!!.centerX().toFloat()
                mEndLineStopY = getHeight().toFloat()
            }
        }

        invalidate()
    }

    private fun initLinePaint() {
        mLinePaint.alpha = 0
        mLinePaint.isAntiAlias = true
        mLinePaint.color = startLineColor
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.strokeWidth = mLineWidth.toFloat()

        if (mLineStyle == LineStyle.DASHED)
            mLinePaint.pathEffect = DashPathEffect(floatArrayOf(mLineStyleDashLength.toFloat(), mLineStyleDashGap.toFloat()), 0.0f)
        else
            mLinePaint.pathEffect = PathEffect()

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mMarker != null) {
            mMarker!!.draw(canvas)
        }

        if (mDrawStartLine) {
            mLinePaint.color = startLineColor
            invalidate()
            canvas.drawLine(mStartLineStartX, mStartLineStartY, mStartLineStopX, mStartLineStopY, mLinePaint)
        }

        if (mDrawEndLine) {
            mLinePaint.color = endLineColor
            invalidate()
            canvas.drawLine(mEndLineStartX, mEndLineStartY, mEndLineStopX, mEndLineStopY, mLinePaint)
        }
    }

    /**
     * Sets marker.
     *
     * @param marker will set marker drawable to timeline
     * @param color  with a color
     */
    fun setMarker(marker: Drawable, color: Int) {
        mMarker = marker
        mMarker!!.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        initTimeline()
    }

    /**
     * Sets marker color.
     *
     * @param color the color
     */
    fun setMarkerColor(color: Int) {
        mMarker!!.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        initTimeline()
    }

    /**
     * Sets start line.
     *
     * @param color    the color of the start line
     * @param viewType the view type
     */
    fun setStartLineColor(color: Int, viewType: Int) {
        startLineColor = color
        initLine(viewType)
    }

    /**
     * Sets end line.
     *
     * @param color    the color of the end line
     * @param viewType the view type
     */
    fun setEndLineColor(color: Int, viewType: Int) {
        endLineColor = color
        initLine(viewType)
    }

    private fun showStartLine(show: Boolean) {
        mDrawStartLine = show
        initTimeline()
    }

    private fun showEndLine(show: Boolean) {
        mDrawEndLine = show
        initTimeline()
    }

    /**
     * Init line.
     *
     * @param viewType the view type
     */
    fun initLine(viewType: Int) {
        if (viewType == LineType.START) {
            showStartLine(false)
            showEndLine(true)
        } else if (viewType == LineType.END) {
            showStartLine(true)
            showEndLine(false)
        } else if (viewType == LineType.ONLYONE) {
            showStartLine(false)
            showEndLine(false)
        } else {
            showStartLine(true)
            showEndLine(true)
        }

        initTimeline()
    }

    companion object {

        val TAG = TimelineView::class.java.simpleName

        /**
         * Gets timeline view type.
         *
         * @param position   the position of current item view
         * @param totalSize the total size of the items
         * @return the timeline view type
         */
        fun getTimeLineViewType(position: Int, totalSize: Int): Int {
            return if (totalSize == 1) {
                LineType.ONLYONE
            } else if (position == 0) {
                LineType.START
            } else if (position == totalSize - 1) {
                LineType.END
            } else {
                LineType.NORMAL
            }
        }
    }
}
