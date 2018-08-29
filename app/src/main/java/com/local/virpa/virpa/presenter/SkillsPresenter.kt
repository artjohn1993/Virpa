package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.MySkills
import com.local.virpa.virpa.model.SaveMySkills
import com.local.virpa.virpa.model.Skills
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SkillsPresenterClass(val view : SkillsView,var api : ApiServices) : SkillsPresenter {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun getSkills() {
        compositeDisposable.add(
                api.getSkills()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            getMySkills(result)
                        },{
                            error ->
                            print(error.message.toString())
                        })
        )
    }

    override fun getMySkills(data: Skills.Result) {
        compositeDisposable.add(
                api.getMySkills()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.skills(data, result)
                        },{
                            error ->
                            print(error.message.toString())
                        })
        )
    }
    override fun saveMySkills(data: SaveMySkills.Post) {
        compositeDisposable.add(
                api.saveMySkills(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.saveSkillsResponse(result)
                        },{
                            error ->
                            print(error.message.toString())
                        })
        )
    }
}

interface SkillsPresenter {
    fun getSkills()
    fun getMySkills(data : Skills.Result)
    fun saveMySkills(data : SaveMySkills.Post)
}
interface SkillsView {
    fun skills(skill : Skills.Result, mySkills : MySkills.Result)
    fun saveSkillsResponse(data : MySkills.Result)
}