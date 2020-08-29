package com.gomart.guildbuddy.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.gomart.guildbuddy.Constants
import com.gomart.guildbuddy.R
import com.gomart.guildbuddy.helper.CheckBlankHelper
import com.gomart.guildbuddy.helper.DialogHelper
import com.gomart.guildbuddy.helper.NetworkHelper
import com.gomart.guildbuddy.vo.CharacterClass
import com.gomart.guildbuddy.vo.CharacterRace
import com.gomart.guildbuddy.vo.Guild
import com.gomart.guildbuddy.network.GetCharacterClassesResponse
import com.gomart.guildbuddy.network.GetCharacterRacesResponse
import com.gomart.guildbuddy.ui.presenter.GuildMemberPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity_old : AppCompatActivity() {
    private var presenter: GuildMemberPresenter? = null
    private var charClass: CharacterClass? = null
    private var charRace: CharacterRace? = null
    private var classes: ArrayList<CharacterClass>? = null
    private var races: ArrayList<CharacterRace>? = null
    private var edtGuildName: EditText? = null
    private var edtRealm: EditText? = null
    private var btnSearch: Button? = null

//    @Inject
//    var dataManager: DataManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //GuildBuddy.app().appComponent.inject(this)
        edtRealm = findViewById<View>(R.id.edtRealm) as EditText
        edtGuildName = findViewById<View>(R.id.edtGuildName) as EditText
        btnSearch = findViewById<View>(R.id.btnSearch) as Button
        presenter = GuildMemberPresenter(this, edtRealm!!.text.toString())
        if (NetworkHelper.isConnected(this)) {
            classNames
            raceNames
        } else {
            DialogHelper.showOkDialog(this, getString(R.string.oops), getString(R.string.no_connection))
        }
//        if (dataManager!![Constants.KEY_GUILD, ""] != null && dataManager!![Constants.KEY_GUILD, ""].length > 0 && dataManager!![Constants.KEY_REALM, ""] != null && dataManager!![Constants.KEY_REALM, ""].length > 0) {
//            edtGuildName!!.setText(dataManager!![Constants.KEY_GUILD, ""])
//            edtRealm!!.setText(dataManager!![Constants.KEY_REALM, ""])
//        }
        btnSearch!!.setOnClickListener {
            if (NetworkHelper.isConnected(applicationContext)) {
                val inputs: MutableList<EditText?> = ArrayList()
                inputs.add(edtRealm)
                inputs.add(edtGuildName)
                if (!CheckBlankHelper.emptyFieldExist(inputs)) {
                    val g = Guild(edtGuildName!!.text.toString(), edtRealm!!.text.toString())
                    val fields = ArrayList<String>()
                    fields.add(GUILD_MEMBERS)
                    g.fields = fields
                    //dataManager!!.save(Constants.KEY_GUILD, edtGuildName!!.text.toString())
                    //dataManager!!.save(Constants.KEY_REALM, edtRealm!!.text.toString())
                    val i = Intent(this@MainActivity_old, GuildMembersActivity::class.java)
                    i.putExtra(Constants.KEY_CLASSES, classes)
                    i.putExtra(Constants.KEY_RACES, races)
                    i.putExtra(Constants.KEY_GUILD, g)
                    startActivity(i)
                } else {
                    DialogHelper.showOkDialog(this@MainActivity_old, getString(R.string.oops), getString(R.string.empty_fields))
                }
            } else {
                DialogHelper.showOkDialog(this@MainActivity_old, getString(R.string.oops), getString(R.string.no_connection))
            }
        }
    }

    //Log.d("#CLASSNAME", cc.getName());
    private val classNames: Unit
        private get() {
            presenter!!.getClassName(object : Callback<GetCharacterClassesResponse> {
                override fun onResponse(call: Call<GetCharacterClassesResponse>, response: Response<GetCharacterClassesResponse>) {
                    classes = ArrayList()
                    if (response.isSuccessful) {
                        for (cc in response.body()!!.classes) {
                            //Log.d("#CLASSNAME", cc.getName());
                            //charClass = CharacterClass(cc.id, cc.powerType, cc.name)
                            classes!!.add(charClass!!)
                        }
                        //dataManager!!.classes = classes
                    }
                }

                override fun onFailure(call: Call<GetCharacterClassesResponse>, t: Throwable) {
                    Log.d("#CLASSNAME", t.message)
                }
            })
        }

    //Log.d("#CLASSNAME", cr.getName());
    private val raceNames: Unit
        private get() {
            presenter!!.getRaceName(object : Callback<GetCharacterRacesResponse> {
                override fun onResponse(call: Call<GetCharacterRacesResponse>, response: Response<GetCharacterRacesResponse>) {
                    races = ArrayList()
                    for (cr in response.body()!!.races) {
                        //Log.d("#CLASSNAME", cr.getName());
                        //charRace = CharacterRace(cr.id, cr.side, cr.name)
                        races!!.add(charRace!!)
                    }
                    //dataManager!!.races = races
                }

                override fun onFailure(call: Call<GetCharacterRacesResponse>, t: Throwable) {
                    Log.d("#RACENAME", t.message)
                }
            })
        }

    companion object {
        const val GUILD_MEMBERS = "members"
    }
}