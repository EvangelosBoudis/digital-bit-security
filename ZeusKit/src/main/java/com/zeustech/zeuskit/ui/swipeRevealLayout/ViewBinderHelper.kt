package com.zeustech.zeuskit.ui.swipeRevealLayout

import android.os.Bundle
import java.util.*

class ViewBinderHelper {

    private var mapStates = Collections.synchronizedMap(HashMap<String, Int>())
    private val mapLayouts = Collections.synchronizedMap(HashMap<String, SwipeRevealLayout>())
    private val lockedSwipeSet = Collections.synchronizedSet(HashSet<String>())

    @Volatile
    private var openOnlyOne = false
    private val stateChangeLock = Any()

    /**
     * Help to save and restore open/close state of the swipeLayout. Call this method
     * when you bind your view holder with the data object.
     *
     * @param swipeLayout swipeLayout of the current view.
     * @param id a string that uniquely defines the data object of the current view.
     */
    fun bind(swipeLayout: SwipeRevealLayout, id: String) {
        if (swipeLayout.shouldRequestLayout()) {
            swipeLayout.requestLayout()
        }
        mapLayouts.values.remove(swipeLayout)
        mapLayouts[id] = swipeLayout
        swipeLayout.abort()
        swipeLayout.setDragStateChangeListener(object: SwipeRevealLayout.DragStateChangeListener {
            override fun onDragStateChanged(state: Int) {
                mapStates[id] = state
                if (openOnlyOne) closeOthers(id, swipeLayout)
            }
        })

        // first time binding.
        if (!mapStates.containsKey(id)) {
            mapStates[id] = SwipeRevealLayout.STATE_CLOSE
            swipeLayout.close(false)
        } else {
            mapStates[id].let { state ->
                if (state == SwipeRevealLayout.STATE_CLOSE || state == SwipeRevealLayout.STATE_CLOSING || state == SwipeRevealLayout.STATE_DRAGGING) {
                    swipeLayout.close(false)
                } else {
                    swipeLayout.open(false)
                }
            }
        }

        // set lock swipe
        swipeLayout.setLockDrag(lockedSwipeSet.contains(id))
    }

    fun saveStates(outState: Bundle?) {
        val state = outState ?: return
        val statesBundle = Bundle()
        for ((key, value) in mapStates) {
            statesBundle.putInt(key, value)
        }
        state.putBundle(BUNDLE_MAP_KEY, statesBundle)
    }

    fun restoreStates(inState: Bundle?) {
        val state = inState ?: return
        if (state.containsKey(BUNDLE_MAP_KEY)) {
            val restoredMap = HashMap<String, Int>()
            state.getBundle(BUNDLE_MAP_KEY)?.keySet()?.let { keySet ->
                for (key in keySet) {
                    restoredMap[key] = state.getInt(key)
                }
            }
            mapStates = restoredMap
        }
    }

    /**
     * Lock swipe for some layouts.
     * @param id a string that uniquely defines the data object.
     */
    fun lockSwipe(vararg id: String) {
        setLockSwipe(true, *id)
    }

    /**
     * Unlock swipe for some layouts.
     * @param id a string that uniquely defines the data object.
     */
    fun unlockSwipe(vararg id: String) {
        setLockSwipe(false, *id)
    }

    /**
     * @param openOnlyOne If set to true, then only one row can be opened at a time.
     */
    fun setOpenOnlyOne(openOnlyOne: Boolean) {
        this.openOnlyOne = openOnlyOne
    }

    /**
     * Open a specific layout.
     * @param id unique id which identifies the data object which is bind to the layout.
     */
    fun openLayout(id: String) {
        synchronized(stateChangeLock) {
            mapStates[id] = SwipeRevealLayout.STATE_OPEN
            when {
                mapLayouts.containsKey(id) -> {
                    val layout = mapLayouts[id]
                    layout?.open(true)
                }
                openOnlyOne -> {
                    closeOthers(id, mapLayouts[id])
                }
                else -> { }
            }
        }
    }

    /**
     * Close a specific layout.
     * @param id unique id which identifies the data object which is bind to the layout.
     */
    fun closeLayout(id: String) {
        synchronized(stateChangeLock) {
            mapStates[id] = SwipeRevealLayout.STATE_CLOSE
            if (mapLayouts.containsKey(id)) {
                mapLayouts[id]?.close(true)
            }
        }
    }

    /**
     * Close others swipe layout.
     * @param id layout which bind with this data object id will be excluded.
     * @param swipeLayout will be excluded.
     */
    private fun closeOthers(id: String, swipeLayout: SwipeRevealLayout?) {
        synchronized(stateChangeLock) {
            // close other rows if openOnlyOne is true.
            if (openCount > 1) {
                for (entry in mapStates.entries) {
                    if (entry.key != id) {
                        entry.setValue(SwipeRevealLayout.STATE_CLOSE)
                    }
                }
                for (layout in mapLayouts.values) {
                    if (layout != swipeLayout) {
                        layout.close(true)
                    }
                }
            }
        }
    }

    private fun setLockSwipe(lock: Boolean, vararg id: String) {
        if (id.isEmpty()) return
        if (lock) lockedSwipeSet.addAll(listOf(*id)) else lockedSwipeSet.removeAll(listOf(*id))
        for (s in id) {
            mapLayouts[s]?.setLockDrag(lock)
        }
    }

    private val openCount: Int
        get() {
            var total = 0
            for (state in mapStates.values) {
                if (state == SwipeRevealLayout.STATE_OPEN || state == SwipeRevealLayout.STATE_OPENING) {
                    total++
                }
            }
            return total
        }

    companion object {
        private const val BUNDLE_MAP_KEY = "ViewBinderHelper_Bundle_Map_Key"
    }
}
