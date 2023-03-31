package com.example.registrationform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.registrationform.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass : ActivityMainBinding

    private var launcher : ActivityResultLauncher<Intent>? = null
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
            if (result.resultCode == RESULT_OK){
                val state = result.data?.getStringExtra(Constants.SING_STATE)
                if (state == Constants.SING_UP){
                    user = result.data!!.getSerializableExtra(Constants.USER) as User
                    bindingClass.tvResult.text = "${user.name} ${user.lastName} ${user.login} ${user.password}"
                } else if (state == Constants.SING_IN){
                    bindingClass.tvResult.text = "${user.login} ${user.password}"
                }

            }
        }
    }

    fun onClickSignUp(view: View) {
        intent = Intent(this, RegisterAvtivity::class.java)
        intent.putExtra(Constants.SING_STATE, Constants.SING_UP)
        launcher?.launch(intent)
    }

    fun onClickSignIn(view: View){
        intent = Intent(this, RegisterAvtivity::class.java)
        intent.putExtra(Constants.SING_STATE, Constants.SING_IN)
        launcher?.launch(intent)
    }
}