package com.zeustech.zeuskit.ui.swipeRevealLayout

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.zeustech.zeuskit.R
import kotlin.math.abs

@SuppressLint("RtlHardcoded")
open class SwipeRevealLayout : ViewGroup {
    /**
     * Main view is the view which is shown when the layout is closed.
     */
    private var mMainView: View? = null

    /**
     * Secondary view is the view which is shown when the layout is opened.
     */
    private var mSecondaryView: View? = null

    /**
     * The rectangle position of the main view when the layout is closed.
     */
    private val mRectMainClose = Rect()

    /**
     * The rectangle position of the main view when the layout is opened.
     */
    private val mRectMainOpen = Rect()

    /**
     * The rectangle position of the secondary view when the layout is closed.
     */
    private val mRectSecClose = Rect()

    /**
     * The rectangle position of the secondary view when the layout is opened.
     */
    private val mRectSecOpen = Rect()

    /**
     * The minimum distance (px) to the closest drag edge that the SwipeRevealLayout
     * will disallow the parent to intercept touch event.
     */
    private var mMinDistRequestDisallowParent = 0
    private var mIsOpenBeforeInit = false

    @Volatile
    private var mAborted = false

    @Volatile
    private var mIsScrolling = false

    /**
     * @return true if the drag/swipe motion is currently locked.
     */
    @Volatile
    var mLockDrag = false
        private set
    /**
     * Get the minimum fling velocity to cause the layout to open/close.
     * @return dp per second
     */
    /**
     * Set the minimum fling velocity to cause the layout to open/close.
     * @param velocity dp per second
     */
    var minFlingVelocity = DEFAULT_MIN_FLING_VELOCITY
    private var mState = STATE_CLOSE
    private var mMode = MODE_NORMAL
    private var mLastMainLeft = 0
    private var mLastMainTop = 0
    /**
     * Get the edge where the layout can be dragged from.
     * @return Can be one of these
     *
     *  * [.DRAG_EDGE_LEFT]
     *  * [.DRAG_EDGE_TOP]
     *  * [.DRAG_EDGE_RIGHT]
     *  * [.DRAG_EDGE_BOTTOM]
     *
     */
    /**
     * Set the edge where the layout can be dragged from.
     * @param dragEdge Can be one of these
     *
     *  * [.DRAG_EDGE_LEFT]
     *  * [.DRAG_EDGE_TOP]
     *  * [.DRAG_EDGE_RIGHT]
     *  * [.DRAG_EDGE_BOTTOM]
     *
     */
    var dragEdge = DRAG_EDGE_LEFT
    private var mDragDist = 0f
    private var mPrevX = -1f
    private var mPrevY = -1f
    private var mDragHelper: ViewDragHelper? = null
    private var mGestureDetector: GestureDetectorCompat? = null
    private var mDragStateChangeListener // only used for ViewBindHelper
            : DragStateChangeListener? = null
    private var mSwipeListener: SwipeListener? = null
    private var mOnLayoutCount = 0

    interface DragStateChangeListener {
        fun onDragStateChanged(state: Int)
    }

    /**
     * Listener for monitoring events about swipe layout.
     */
    interface SwipeListener {
        /**
         * Called when the main view becomes completely closed.
         */
        fun onClosed(view: SwipeRevealLayout)

        /**
         * Called when the main view becomes completely opened.
         */
        fun onOpened(view: SwipeRevealLayout)

        /**
         * Called when the main view's position changes.
         * @param slideOffset The new offset of the main view within its range, from 0-1
         */
        fun onSlide(view: SwipeRevealLayout, slideOffset: Float)
    }

    /**
     * No-op stub for [SwipeListener]. If you only want ot implement a subset
     * of the listener methods, you can extend this instead of implement the full interface.
     */
    class SimpleSwipeListener : SwipeListener {
        override fun onClosed(view: SwipeRevealLayout) {}
        override fun onOpened(view: SwipeRevealLayout) {}
        override fun onSlide(view: SwipeRevealLayout, slideOffset: Float) {}
    }

    constructor(context: Context?) : super(context) {
        init(context, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { event ->
            mGestureDetector?.onTouchEvent(event)
            mDragHelper?.processTouchEvent(event)
        }
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (mLockDrag) {
            return super.onInterceptTouchEvent(ev)
        }
        val event = ev ?: return false
        mDragHelper?.processTouchEvent(event)
        mGestureDetector?.onTouchEvent(event)
        accumulateDragDist(event)
        val couldBecomeClick = couldBecomeClick(event)
        val settling = mDragHelper?.viewDragState == ViewDragHelper.STATE_SETTLING
        val idleAfterScrolled = (mDragHelper?.viewDragState == ViewDragHelper.STATE_IDLE
                && mIsScrolling)

        // must be placed as the last statement
        mPrevX = event.x
        mPrevY = event.y

        // return true => intercept, cannot trigger onTableClick event
        return !couldBecomeClick && (settling || idleAfterScrolled)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // get views
        if (childCount >= 2) {
            mSecondaryView = getChildAt(0)
            mMainView = getChildAt(1)
        } else if (childCount == 1) {
            mMainView = getChildAt(0)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mAborted = false
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            var left: Int
            var right: Int
            var top: Int
            var bottom: Int
            bottom = 0
            top = bottom
            right = top
            left = right
            val minLeft = paddingLeft
            val maxRight = (r - paddingRight - l).coerceAtLeast(0)
            val minTop = paddingTop
            val maxBottom = (b - paddingBottom - t).coerceAtLeast(0)
            var measuredChildHeight = child.measuredHeight
            var measuredChildWidth = child.measuredWidth

            // need to take account if child size is match_parent
            val childParams = child.layoutParams

            childParams?.let { params ->
                val matchParentHeight = params.height == LayoutParams.MATCH_PARENT
                val matchParentWidth = params.width == LayoutParams.MATCH_PARENT

                if (matchParentHeight) {
                    measuredChildHeight = maxBottom - minTop
                    params.height = measuredChildHeight
                }

                if (matchParentWidth) {
                    measuredChildWidth = maxRight - minLeft
                    params.width = measuredChildWidth
                }
            }

            when (dragEdge) {
                DRAG_EDGE_RIGHT -> {
                    left = (r - measuredChildWidth - paddingRight - l).coerceAtLeast(minLeft)
                    top = paddingTop.coerceAtMost(maxBottom)
                    right = (r - paddingRight - l).coerceAtLeast(minLeft)
                    bottom = (measuredChildHeight + paddingTop).coerceAtMost(maxBottom)
                }
                DRAG_EDGE_LEFT, DRAG_EDGE_TOP -> {
                    left = paddingLeft.coerceAtMost(maxRight)
                    top = paddingTop.coerceAtMost(maxBottom)
                    right = (measuredChildWidth + paddingLeft).coerceAtMost(maxRight)
                    bottom = (measuredChildHeight + paddingTop).coerceAtMost(maxBottom)
                }
                DRAG_EDGE_BOTTOM -> {
                    left = paddingLeft.coerceAtMost(maxRight)
                    top = (b - measuredChildHeight - paddingBottom - t).coerceAtLeast(minTop)
                    right = (measuredChildWidth + paddingLeft).coerceAtMost(maxRight)
                    bottom = (b - paddingBottom - t).coerceAtLeast(minTop)
                }
            }
            child.layout(left, top, right, bottom)
        }

        // taking account offset when mode is SAME_LEVEL
        if (mMode == MODE_SAME_LEVEL) {
            mSecondaryView?.let { view ->
                when (dragEdge) {
                    DRAG_EDGE_LEFT -> view.offsetLeftAndRight(-view.width)
                    DRAG_EDGE_RIGHT -> view.offsetLeftAndRight(view.width)
                    DRAG_EDGE_TOP -> view.offsetTopAndBottom(-view.height)
                    DRAG_EDGE_BOTTOM -> view.offsetTopAndBottom(view.height)
                }
            }
        }
        initRects()
        if (mIsOpenBeforeInit) open(false)
        else close(false)
        mMainView?.let { view ->
            mLastMainLeft = view.left
            mLastMainTop = view.top
        }
        mOnLayoutCount++
    }

    /**
     * {@inheritDoc}
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount < 2) {
            throw RuntimeException("Layout must have two children")
        }
        val params = layoutParams
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var desiredWidth = 0
        var desiredHeight = 0
        var wrapChildHeight = 0
        var wrapChildWidth = 0

        // first find the largest child
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childParams = child.layoutParams
            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            if (childParams.height == LayoutParams.WRAP_CONTENT &&
                (dragEdge == DRAG_EDGE_LEFT || dragEdge == DRAG_EDGE_RIGHT)
            ) wrapChildHeight = child.measuredHeight.coerceAtLeast(wrapChildHeight)
            else desiredHeight = child.measuredHeight.coerceAtLeast(desiredHeight)

            if (childParams.width == LayoutParams.WRAP_CONTENT &&
                (dragEdge == DRAG_EDGE_TOP || dragEdge == DRAG_EDGE_BOTTOM)
            ) wrapChildWidth = child.measuredWidth.coerceAtLeast(wrapChildWidth)
            else desiredWidth = child.measuredWidth.coerceAtLeast(desiredWidth)
        }
        if (wrapChildHeight != 0) desiredHeight = wrapChildHeight
        if (wrapChildWidth != 0) desiredWidth = wrapChildWidth

        // create new measure spec using the largest child width
        val widthMeasure = MeasureSpec.makeMeasureSpec(desiredWidth, widthMode)
        val heightMeasure = MeasureSpec.makeMeasureSpec(desiredHeight, MeasureSpec.EXACTLY)
        val measuredHeight = MeasureSpec.getSize(heightMeasure)
        val measuredWidth = MeasureSpec.getSize(widthMeasure)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.layoutParams?.let { childParams ->
                if (childParams.height != LayoutParams.WRAP_CONTENT) childParams.height = measuredHeight
                if (childParams.width == LayoutParams.MATCH_PARENT) childParams.width = measuredWidth
            }
            measureChild(child, widthMeasure, heightMeasure)
        }

        // taking accounts of padding
        desiredWidth += paddingLeft + paddingRight
        desiredHeight += paddingTop + paddingBottom

        // adjust desired width
        if (widthMode == MeasureSpec.EXACTLY) {
            desiredWidth = measuredWidth
        } else {
            if (params.width == LayoutParams.MATCH_PARENT) {
                desiredWidth = measuredWidth
            }
            if (widthMode == MeasureSpec.AT_MOST) {
                desiredWidth = if (desiredWidth > measuredWidth) measuredWidth else desiredWidth
            }
        }

        // adjust desired height
        if (heightMode == MeasureSpec.EXACTLY) {
            desiredHeight = measuredHeight
        } else {
            if (params.height == LayoutParams.MATCH_PARENT) {
                desiredHeight = measuredHeight
            }
            if (heightMode == MeasureSpec.AT_MOST) {
                desiredHeight =
                    if (desiredHeight > measuredHeight) measuredHeight else desiredHeight
            }
        }
        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun computeScroll() {
        val cont = mDragHelper?.continueSettling(true) ?: return
        if (cont) ViewCompat.postInvalidateOnAnimation(this)
    }

    /**
     * Open the panel to show the secondary view
     * @param animation true to animate the open motion. [SwipeListener] won't be
     * called if is animation is false.
     */
    fun open(animation: Boolean) {
        mIsOpenBeforeInit = true
        mAborted = false
        if (animation) {
            mState = STATE_OPENING
            mMainView?.let { view ->
                mDragHelper?.smoothSlideViewTo(view, mRectMainOpen.left, mRectMainOpen.top)
            }
            mDragStateChangeListener?.onDragStateChanged(mState)
        } else {
            mState = STATE_OPEN
            mDragHelper?.abort()
            mMainView?.layout(
                mRectMainOpen.left,
                mRectMainOpen.top,
                mRectMainOpen.right,
                mRectMainOpen.bottom
            )
            mSecondaryView?.layout(
                mRectSecOpen.left,
                mRectSecOpen.top,
                mRectSecOpen.right,
                mRectSecOpen.bottom
            )
        }
        ViewCompat.postInvalidateOnAnimation(this@SwipeRevealLayout)
    }

    /**
     * Close the panel to hide the secondary view
     * @param animation true to animate the close motion. [SwipeListener] won't be
     * called if is animation is false.
     */
    fun close(animation: Boolean) {
        mIsOpenBeforeInit = false
        mAborted = false
        if (animation) {
            mState = STATE_CLOSING
            mMainView?.let {
                mDragHelper?.smoothSlideViewTo(it, mRectMainClose.left, mRectMainClose.top)
            }
            mDragStateChangeListener?.onDragStateChanged(mState)
        } else {
            mState = STATE_CLOSE
            mDragHelper?.abort()
            mMainView?.layout(
                mRectMainClose.left,
                mRectMainClose.top,
                mRectMainClose.right,
                mRectMainClose.bottom
            )
            mSecondaryView?.layout(
                mRectSecClose.left,
                mRectSecClose.top,
                mRectSecClose.right,
                mRectSecClose.bottom
            )
        }
        ViewCompat.postInvalidateOnAnimation(this@SwipeRevealLayout)
    }

    fun setSwipeListener(listener: SwipeListener?) {
        mSwipeListener = listener
    }

    /**
     * @param lock if set to true, the user cannot drag/swipe the layout.
     */
    fun setLockDrag(lock: Boolean) {
        mLockDrag = lock
    }

    /**
     * @return true if layout is fully opened, false otherwise.
     */
    val isOpened: Boolean
        get() = (mState == STATE_OPEN)

    /**
     * @return true if layout is fully closed, false otherwise.
     */
    val isClosed: Boolean
        get() = (mState == STATE_CLOSE)

    /** Only used for [ViewBinderHelper]  */
    fun setDragStateChangeListener(listener: DragStateChangeListener?) {
        mDragStateChangeListener = listener
    }

    /** Abort current motion in progress. Only used for [ViewBinderHelper]  */
    fun abort() {
        mAborted = true
        mDragHelper?.abort()
    }

    /**
     * In RecyclerView/ListView, onLayout should be called 2 times to display children views correctly.
     * This method check if it've already called onLayout two times.
     * @return true if you should call [.requestLayout].
     */
    fun shouldRequestLayout() = mOnLayoutCount < 2

    private val mainOpenLeft: Int
        get() = mSecondaryView?.let { view ->
            when (dragEdge) {
                DRAG_EDGE_LEFT -> mRectMainClose.left + view.width
                DRAG_EDGE_RIGHT -> mRectMainClose.left - view.width
                DRAG_EDGE_TOP, DRAG_EDGE_BOTTOM -> mRectMainClose.left
                else -> 0
            }
        } ?: 0

    private val mainOpenTop: Int
        get() = mSecondaryView?.let { view ->
            when (dragEdge) {
                DRAG_EDGE_LEFT, DRAG_EDGE_RIGHT -> mRectMainClose.top
                DRAG_EDGE_TOP -> mRectMainClose.top + view.height
                DRAG_EDGE_BOTTOM -> mRectMainClose.top - view.height
                else -> 0
            }
        } ?: 0

    private val secOpenLeft: Int
        get() {
            if (mMode == MODE_NORMAL || dragEdge == DRAG_EDGE_BOTTOM || dragEdge == DRAG_EDGE_TOP) {
                return mRectSecClose.left
            }
            return if (dragEdge == DRAG_EDGE_LEFT) {
                mRectSecClose.left + mSecondaryView!!.width
            } else {
                mRectSecClose.left - mSecondaryView!!.width
            }
        }
    private val secOpenTop: Int
        get() {
            if (mMode == MODE_NORMAL || dragEdge == DRAG_EDGE_LEFT || dragEdge == DRAG_EDGE_RIGHT) {
                return mRectSecClose.top
            }
            return if (dragEdge == DRAG_EDGE_TOP) {
                mRectSecClose.top + mSecondaryView!!.height
            } else {
                mRectSecClose.top - mSecondaryView!!.height
            }
        }

    private fun initRects() {
        mMainView?.let {
            // close position of main view
            mRectMainClose[it.left, it.top, it.right] = it.bottom
            // open position of the main view
            mRectMainOpen[mainOpenLeft, mainOpenTop, mainOpenLeft + it.width] = mainOpenTop + it.height
        }
        mSecondaryView?.let {
            // close position of secondary view
            mRectSecClose[it.left, it.top, it.right] = it.bottom
            // open position of the secondary view
            mRectSecOpen[secOpenLeft, secOpenTop, secOpenLeft + it.width] = secOpenTop + it.height
        }
    }

    private fun couldBecomeClick(ev: MotionEvent) = isInMainView(ev) && !shouldInitiateADrag()

    private fun isInMainView(ev: MotionEvent): Boolean {
        val x = ev.x
        val y = ev.y
        return mMainView?.let {
            val withinVertical = it.top <= y && y <= it.bottom
            val withinHorizontal = it.left <= x && x <= it.right
            withinVertical && withinHorizontal
        } ?: false
    }

    private fun shouldInitiateADrag(): Boolean {
        return mDragHelper?.let {
            mDragDist >= it.touchSlop.toFloat()
        } ?: false
    }

    private fun accumulateDragDist(ev: MotionEvent) {
        val action = ev.action
        if (action == MotionEvent.ACTION_DOWN) {
            mDragDist = 0f
            return
        }
        val dragHorizontally = dragEdge == DRAG_EDGE_LEFT || dragEdge == DRAG_EDGE_RIGHT
        val dragged = if (dragHorizontally) abs(ev.x - mPrevX) else abs(ev.y - mPrevY)
        mDragDist += dragged
    }

    private fun init(cont: Context?, attributeSet: AttributeSet?) {
        attributeSet?.let { attrs ->
            cont?.let { context ->
                val a = context.theme.obtainStyledAttributes(
                    attrs, R.styleable.SwipeRevealLayout, 0, 0
                )
                dragEdge = a.getInteger(R.styleable.SwipeRevealLayout_dragEdge, DRAG_EDGE_LEFT)
                minFlingVelocity = a.getInteger(
                    R.styleable.SwipeRevealLayout_flingVelocity,
                    DEFAULT_MIN_FLING_VELOCITY
                )
                mMode = a.getInteger(R.styleable.SwipeRevealLayout_mode, MODE_NORMAL)
                mMinDistRequestDisallowParent = a.getDimensionPixelSize(
                    R.styleable.SwipeRevealLayout_minDistRequestDisallowParent,
                    dpToPx(DEFAULT_MIN_DIST_REQUEST_DISALLOW_PARENT)
                )
            }
        }
        mDragHelper = ViewDragHelper.create(this, 1.0f, mDragHelperCallback)
        mDragHelper?.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL)
        mGestureDetector = GestureDetectorCompat(context, mGestureListener)
    }

    private val mGestureListener: GestureDetector.OnGestureListener =
        object : SimpleOnGestureListener() {
            var hasDisallowed = false

            override fun onDown(e: MotionEvent?): Boolean {
                mIsScrolling = false
                hasDisallowed = false
                return true
            }

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                mIsScrolling = true
                return false
            }

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                mIsScrolling = true
                parent?.let {
                    val shouldDisallow: Boolean
                    if (!hasDisallowed) {
                        shouldDisallow = distToClosestEdge >= mMinDistRequestDisallowParent
                        if (shouldDisallow) hasDisallowed = true
                    } else {
                        shouldDisallow = true
                    }
                    // disallow parent to intercept touch event so that the layout will work
                    // properly on RecyclerView or view that handles scroll gesture.
                    it.requestDisallowInterceptTouchEvent(shouldDisallow)
                }
                return false
            }
        }

    private val distToClosestEdge: Int
        get() {
            val mMain = mMainView ?: return 0
            val mSecondary = mSecondaryView ?: return 0
            when (dragEdge) {
                DRAG_EDGE_LEFT -> {
                    val pivotRight = mRectMainClose.left + mSecondary.width
                    return (mMain.left - mRectMainClose.left).coerceAtMost(pivotRight - mMain.left)
                }
                DRAG_EDGE_RIGHT -> {
                    val pivotLeft = mRectMainClose.right - mSecondary.width
                    return (mMain.right - pivotLeft).coerceAtMost(mRectMainClose.right - mMain.right)
                }
                DRAG_EDGE_TOP -> {
                    val pivotBottom = mRectMainClose.top + mSecondary.height
                    return (mMain.bottom - pivotBottom).coerceAtMost(pivotBottom - mMain.top)
                }
                DRAG_EDGE_BOTTOM -> {
                    val pivotTop = mRectMainClose.bottom - mSecondary.height
                    return (mRectMainClose.bottom - mMain.bottom).coerceAtMost(mMain.bottom - pivotTop)
                }
            }
            return 0
        }

    private val halfwayPivotHorizontal: Int
        get() {
            val mSecondary = mSecondaryView ?: return 0
            return if (dragEdge == DRAG_EDGE_LEFT) mRectMainClose.left + mSecondary.width / 2 else mRectMainClose.right - mSecondary.width / 2
        }

    private val halfwayPivotVertical: Int
        get() {
            val mSecondary = mSecondaryView ?: return 0
            return if (dragEdge == DRAG_EDGE_TOP) mRectMainClose.top + mSecondary.height / 2 else mRectMainClose.bottom - mSecondary.height / 2
        }

    private val mDragHelperCallback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            mAborted = false
            if (mLockDrag) return false
            mDragHelper?.captureChildView(mMainView!!, pointerId)
            return false
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val mSecondary = mSecondaryView ?: return child.top
            return when (dragEdge) {
                DRAG_EDGE_TOP -> top.coerceAtMost(mRectMainClose.top + mSecondary.height).coerceAtLeast(mRectMainClose.top)
                DRAG_EDGE_BOTTOM -> top.coerceAtMost(mRectMainClose.top).coerceAtLeast(mRectMainClose.top - mSecondary.height)
                else -> child.top
            }
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            val mSecondary = mSecondaryView ?: return child.top
            return when (dragEdge) {
                DRAG_EDGE_RIGHT -> left.coerceAtMost(mRectMainClose.left).coerceAtLeast(mRectMainClose.left - mSecondary.width)
                DRAG_EDGE_LEFT -> left.coerceAtMost(mRectMainClose.left + mSecondary.width).coerceAtLeast(mRectMainClose.left)
                else -> child.left
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            val velRightExceeded: Boolean = pxToDp(xvel.toInt()) >= minFlingVelocity
            val velLeftExceeded: Boolean = pxToDp(xvel.toInt()) <= -minFlingVelocity
            val velUpExceeded: Boolean = pxToDp(yvel.toInt()) <= -minFlingVelocity
            val velDownExceeded: Boolean = pxToDp(yvel.toInt()) >= minFlingVelocity
            val pivotHorizontal = halfwayPivotHorizontal
            val pivotVertical = halfwayPivotVertical
            when (dragEdge) {
                DRAG_EDGE_RIGHT -> when {
                    velRightExceeded -> { close(true) }
                    velLeftExceeded -> { open(true) }
                    else -> {
                        mMainView?.let {
                            if (it.right < pivotHorizontal) open(true)
                            else close(true)
                        }
                    }
                }
                DRAG_EDGE_LEFT -> when {
                    velRightExceeded -> { open(true) }
                    velLeftExceeded -> { close(true) }
                    else -> {
                        mMainView?.let {
                            if (it.left < pivotHorizontal) close(true)
                            else open(true)
                        }
                    }
                }
                DRAG_EDGE_TOP -> when {
                    velUpExceeded -> { close(true) }
                    velDownExceeded -> { open(true) }
                    else -> {
                        mMainView?.let {
                            if (it.top < pivotVertical) close(true)
                            else open(true)
                        }
                    }
                }
                DRAG_EDGE_BOTTOM -> when {
                    velUpExceeded -> { open(true) }
                    velDownExceeded -> { close(true) }
                    else -> {
                        mMainView?.let {
                            if (it.bottom < pivotVertical) close(true)
                            else open(true)
                        }
                    }
                }
            }
        }

        override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
            super.onEdgeDragStarted(edgeFlags, pointerId)
            if (mLockDrag) return
            val edgeStartLeft = (dragEdge == DRAG_EDGE_RIGHT
                    && edgeFlags == ViewDragHelper.EDGE_LEFT)
            val edgeStartRight = (dragEdge == DRAG_EDGE_LEFT
                    && edgeFlags == ViewDragHelper.EDGE_RIGHT)
            val edgeStartTop = (dragEdge == DRAG_EDGE_BOTTOM
                    && edgeFlags == ViewDragHelper.EDGE_TOP)
            val edgeStartBottom = (dragEdge == DRAG_EDGE_TOP
                    && edgeFlags == ViewDragHelper.EDGE_BOTTOM)
            if (edgeStartLeft || edgeStartRight || edgeStartTop || edgeStartBottom) {
                mMainView?.let { mDragHelper?.captureChildView(it, pointerId) }
            }
        }

        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            val mMain = mMainView ?: return
            if (mMode == MODE_SAME_LEVEL) {
                if (dragEdge == DRAG_EDGE_LEFT || dragEdge == DRAG_EDGE_RIGHT) {
                    mSecondaryView?.offsetLeftAndRight(dx)
                } else {
                    mSecondaryView?.offsetTopAndBottom(dy)
                }
            }
            if (mMain.left != mLastMainLeft || mMain.top != mLastMainTop) {
                if (mMain.left == mRectMainClose.left && mMain.top == mRectMainClose.top) {
                    mSwipeListener?.onClosed(this@SwipeRevealLayout)
                } else if (mMain.left == mRectMainOpen.left && mMain.top == mRectMainOpen.top) {
                    mSwipeListener?.onOpened(this@SwipeRevealLayout)
                } else {
                    mSwipeListener?.onSlide(this@SwipeRevealLayout, slideOffset)
                }
            }
            mLastMainLeft = mMain.left
            mLastMainTop = mMain.top
            ViewCompat.postInvalidateOnAnimation(this@SwipeRevealLayout)
        }

        private val slideOffset: Float
            get() {
                val mMain = mMainView ?: return 0F
                val mSecondary = mSecondaryView ?: return 0F
                return when (dragEdge) {
                    DRAG_EDGE_LEFT -> (mMain.left - mRectMainClose.left).toFloat() / mSecondary.width
                    DRAG_EDGE_RIGHT -> (mRectMainClose.left - mMain.left).toFloat() / mSecondary.width
                    DRAG_EDGE_TOP -> (mMain.top - mRectMainClose.top).toFloat() / mSecondary.height
                    DRAG_EDGE_BOTTOM -> (mRectMainClose.top - mMain.top).toFloat() / mSecondary.height
                    else -> 0F
                }
            }

        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            val mMain = mMainView ?: return
            val prevState = mState
            when (state) {
                ViewDragHelper.STATE_DRAGGING -> mState = STATE_DRAGGING
                ViewDragHelper.STATE_IDLE ->
                    // drag edge is left or right
                    mState = if (dragEdge == DRAG_EDGE_LEFT || dragEdge == DRAG_EDGE_RIGHT) {
                        if (mMain.left == mRectMainClose.left) STATE_CLOSE else STATE_OPEN
                    } else {
                        if (mMain.top == mRectMainClose.top) STATE_CLOSE else STATE_OPEN
                    }
            }
            if (!mAborted && prevState != mState) {
                mDragStateChangeListener?.onDragStateChanged(mState)
            }
        }
    }

    private fun pxToDp(px: Int): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    private fun dpToPx(dp: Int): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    companion object {
        // These states are used only for ViewBindHelper
        const val STATE_CLOSE = 0
        const val STATE_CLOSING = 1
        const val STATE_OPEN = 2
        const val STATE_OPENING = 3
        const val STATE_DRAGGING = 4
        private const val DEFAULT_MIN_FLING_VELOCITY = 300 // dp per second
        private const val DEFAULT_MIN_DIST_REQUEST_DISALLOW_PARENT = 1 // dp
        const val DRAG_EDGE_LEFT = 0x1
        const val DRAG_EDGE_RIGHT = 0x1 shl 1
        const val DRAG_EDGE_TOP = 0x1 shl 2
        const val DRAG_EDGE_BOTTOM = 0x1 shl 3

        /**
         * The secondary view will be under the main view.
         */
        const val MODE_NORMAL = 0

        /**
         * The secondary view will stick the edge of the main view.
         */
        const val MODE_SAME_LEVEL = 1
        fun getStateString(state: Int): String {
            return when (state) {
                STATE_CLOSE -> "state_close"
                STATE_CLOSING -> "state_closing"
                STATE_OPEN -> "state_open"
                STATE_OPENING -> "state_opening"
                STATE_DRAGGING -> "state_dragging"
                else -> "undefined"
            }
        }
    }
}