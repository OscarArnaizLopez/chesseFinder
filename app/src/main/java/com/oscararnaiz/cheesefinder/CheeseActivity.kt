package com.oscararnaiz.cheesefinder

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cheeses.*

class CheeseActivity : BaseSearchActivity() {


    private fun createButtonClickObservable(): Observable<String> {
        return Observable.create { theEmitter ->
            searchButton.setOnClickListener {
                theEmitter.onNext(queryEditText.text.toString())
            }

            theEmitter.setCancellable {
                searchButton.setOnClickListener(null)
            }

        }
    }

    override fun onStart() {
        super.onStart()

        val searchTextObservable = createButtonClickObservable()

        searchTextObservable
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext {showProgress()}
                .observeOn(Schedulers.io())
                .map { cheeseSearchEngine.search(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    hideProgress()
                    showResult(it)
                }

    }


}