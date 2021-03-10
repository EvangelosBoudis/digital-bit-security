package com.nativeboys.couchbase.lite.android.dao.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.couchbase.lite.DocumentReplication
import com.couchbase.lite.Replicator
import com.couchbase.lite.ReplicatorChange
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Returns a [Flow] for observing the changes in the replication status and progress.
 *
 * @see Replicator.addChangeListener
 */
@ExperimentalCoroutinesApi
fun Replicator.changesFlow(): Flow<ReplicatorChange> = callbackFlow {
    val token = addChangeListener { change -> offer(change) }
    awaitClose { removeChangeListener(token) }
}

/**
 * Returns a [Flow] for receiving the replication status of the specified document.
 *
 * @see Replicator.addDocumentReplicationListener
 */
@ExperimentalCoroutinesApi
fun Replicator.documentReplicationFlow(): Flow<DocumentReplication> = callbackFlow {
    val token = addDocumentReplicationListener { replication -> offer(replication) }
    awaitClose { removeChangeListener(token) }
}

/**
 * Binds the [Replicator] instance to the given [Lifecycle].
 *
 * The replicator will be automatically started on the ON_RESUME event,
 * and stopped on the ON_PAUSE event.
 *
 * @see Lifecycle
 * @see Lifecycle.Event.ON_RESUME
 * @see Lifecycle.Event.ON_PAUSE
 */
fun Replicator.bindToLifecycle(lifecycle: Lifecycle) {
    lifecycle.addObserver(ReplicatorLifecycleObserver(this))
}

/**
 * Provides a binding between the Android lifecycle and the Replicator lifecycle.
 *
 * The replicator will be automatically started on the ON_RESUME event,
 * and stopped on the ON_PAUSE event.
 */
internal class ReplicatorLifecycleObserver(
    private val replicator: Replicator
) : LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> replicator.start()
            Lifecycle.Event.ON_PAUSE -> replicator.stop()
            Lifecycle.Event.ON_DESTROY -> source.lifecycle.removeObserver(this)
            else -> { } // ignored.
        }
    }
}