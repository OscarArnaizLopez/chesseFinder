

package com.oscararnaiz.cheesefinder

import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_cheeses.*

class CheeseActivity : BaseSearchActivity() {


    private fun createButtonClickObservable(): Observable<String>{
        return Observable.create { theEmitter ->
            searchButton.setOnClickListener{
                theEmitter.onNext(queryEditText.text.toString())
            }

            theEmitter.setCancellable {
                searchButton.setOnClickListener(null)
            }

        }
    }

    override fun onStart(){
        super.onStart()

        val searchTextObservable = createButtonClickObservable()

        searchTextObservable
            .subscribe{ query ->
                println("QUERY ==>" + query)
                showResult(cheeseSearchEngine.search(query))
            }
    }

}