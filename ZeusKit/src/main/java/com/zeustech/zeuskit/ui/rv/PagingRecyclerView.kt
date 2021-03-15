package com.zeustech.zeuskit.ui.rv

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class PagingRecyclerView : RecyclerView {

    interface PageListener {
        fun onPreviousPage()
        fun onNextPage()
    }

    var pageListener: PageListener? = null

    constructor(context: Context) : super(context) {
        setUpView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        setUpView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        setUpView()
    }

    private fun setUpView() {
        addOnScrollListener(onScrollListener)
    }

    private val onScrollListener: OnScrollListener =
        object : OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                adapter?.let { adapter ->
                    layoutManager?.let { manager ->
                        pageListener?.let { listener ->
                            val lastItemIndex = adapter.itemCount - 1
                            (manager as? LinearLayoutManager)?.let {
                                if (it.findLastCompletelyVisibleItemPosition() == lastItemIndex) { // next
                                    listener.onNextPage()
                                } else if (it.findFirstCompletelyVisibleItemPosition() == 0) { // previous
                                    listener.onPreviousPage()
                                }
                            }
                            (manager as? StaggeredGridLayoutManager)?.let {
                                var next = false
                                for (i in it.findLastCompletelyVisibleItemPositions(null)) {
                                    if (i == lastItemIndex) {
                                        next = true
                                        break
                                    }
                                }
                                if (next) listener.onNextPage()
                            }
                        }
                    }
                }
            }
        }

    fun scrollToBottom() {
        adapter?.let {
            if (it.itemCount < 1) return
            scrollToPosition(it.itemCount - 1)
        }
    }

}