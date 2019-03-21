package com.rubin.rxlearning

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rubin.rxlearning.model.Task
import com.rubin.rxlearning.utils.DataSource
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val disposable = CompositeDisposable()

    companion object {
        val TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        val taskObservable = Observable
            .fromIterable(DataSource.createTaskList())
            .subscribeOn(Schedulers.io())
            .filter { task ->
                Thread.sleep(1000)

                return@filter task.isCompleted
            }
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.subscribe(object : Observer<Task> {
            override fun onComplete() {
                Log.d(TAG, "onComplete called")
            }

            override fun onSubscribe(d: Disposable) {
                disposable.add(d)
            }

            override fun onNext(task: Task) {
                Log.d(TAG, "onNext: ${Thread.currentThread().name}")
                Log.d(TAG, "onNext: ${task.description}")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: $e")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
