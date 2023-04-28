package com.plub.presentation.util

import com.plub.presentation.util.EventFlow.Companion.MAX_CACHE_EVENT_SIZE
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.concurrent.atomic.AtomicBoolean

interface EventFlow<out T> : Flow<T> {

    companion object {

        const val DEFAULT_REPLAY: Int = 3
        const val MAX_CACHE_EVENT_SIZE: Int = 20
    }
}

interface MutableEventFlow<T> : EventFlow<T>, FlowCollector<T>

@Suppress("FunctionName")
fun <T> MutableEventFlow(
    replay: Int = EventFlow.DEFAULT_REPLAY
): MutableEventFlow<T> = EventFlowImpl(replay)

fun <T> MutableEventFlow<T>.asEventFlow(): EventFlow<T> = ReadOnlyEventFlow(this)

private class ReadOnlyEventFlow<T>(flow: EventFlow<T>) : EventFlow<T> by flow

private class EventFlowImpl<T>(
    replay: Int
) : MutableEventFlow<T> {

    private val flow: MutableSharedFlow<EventFlowSlot<T>> = MutableSharedFlow(replay = replay)

    private val slotStore: ArrayDeque<Slot<EventFlowSlot<T>>> = ArrayDeque()

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<T>) = flow
        .collect { slot ->

            /**
             * A, B, C Fragment에서 D CacheEventflow를 subscribe 하고 있다고 가정해보자.
             * D CacheEventflow가 emit되면 A, B, C Fragment에서 전부 같은 Event 객체가 수집된다. (Eventflow 객체의 주소는 @f12622로 전부 같다.)
             * CacheEventflow의 경우 한 번만 소비 되기 때문에
             * A Fragment에서 D CacheEventflow(@f12622)가 소비되면
             * B, C collector에서 D CacheEventflow(@f12622)를 소비할 수 없다.
             */

            /**
             * 따라서 이를 해결하기 위해 slotStore를 만들었음.
             * (slot == CacheEventflow)
             * key에는 collect가 생성된 디렉토리 위치 와 slot의 주소 값을 종합해서 만들었음.
             * value에는 해당 slot의 value 값을 가지는 새로운 EventFlowSlot를 만들어서 넣어줌
             *
             * 이렇게 하면 A, B, C Fragment에서 D CacheEventflow를 subscribe 하고 있을 때
             * D CacheEventflow가 emit되면 slotStore에 각각 다른 주소를 가진 D-A, D-B, D-C CacheEventflow가 저장된다.
             * 따라서 A Fragment에서는 D-A CacheEventflow가, B Fragment에서는 D-B CacheEventflow가 ... 소비되므로 안전하다.
             * 또한 D-A, D-B, D-C CacheEventflow는 각각 한번 씩만 호출되므로, 같은 Fragment에서 동일한 CacheEventflow emit되더라도 한번만 소비된다.
             */


            val slotKey = collector.javaClass.name + slot

            if(isNotContainKey(slotKey)) {
                if(slotStore.size > MAX_CACHE_EVENT_SIZE) slotStore.removeFirst()
                slotStore.addLast(Slot(slotKey, EventFlowSlot(slot.value)))
            }

            val slotValue = slotStore.find { it.key == slotKey }?.value ?: slot

            if (slotValue.markConsumed().not()) {
                collector.emit(slotValue.value)
            }
        }

    override suspend fun emit(value: T) {
        flow.emit(EventFlowSlot(value))
    }

    private fun isNotContainKey(findKey: String): Boolean {
        return slotStore.find { it.key == findKey } == null
    }
}

private class EventFlowSlot<T>(val value: T) {

    private val consumed: AtomicBoolean = AtomicBoolean(false)

    fun markConsumed(): Boolean = consumed.getAndSet(true)
}

private data class Slot<T>(
    val key: String,
    val value: T
)