
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.Trace

object PerformanceManager {
    private var performanceManager: FirebasePerformance? = null
    fun initPerformance() {
        performanceManager = FirebasePerformance.getInstance()
    }

    fun startTrace(trace: String?): Trace {
        return performanceManager!!.newTrace(trace!!)
    }
}
