package br.com.dio.appbeberagua

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TimePicker
import android.widget.Toast
import br.com.dio.appbeberagua.databinding.ActivityMainBinding
import br.com.dio.appbeberagua.model.CalcularIngestaoDiaria

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendario: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar!!.hide()
    }

    override fun onStart() {
        super.onStart()
        calcularAgua()
        redefinirDados()
        initLembrete()
        initAlarme()


    }


    fun calcularAgua() {
        binding.buttonCalcular.setOnClickListener {
            if(editTextIsEmpty()) {
                Toast.makeText(this, R.string.alerta_campo_vazio, Toast.LENGTH_SHORT).show()
            } else {
                val peso = binding.editPeso.text.toString().toDouble()
                val idade = binding.editIdade.text.toString().toInt()
                val resultadoMl = CalcularIngestaoDiaria.calcularTotalML(peso, idade).toInt()

                binding.textResultadoMl.text = "${resultadoMl}ml"
                //animacao
                val anim = AnimationUtils.loadAnimation(this, R.anim.rotate)
                binding.textResultadoMl.startAnimation(anim)

            }

        }
    }

    fun redefinirDados() {
        binding.icRedefinir.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.dialog_titulo)
                .setMessage(R.string.dialog_descricao)
                .setPositiveButton("Ok") { _, _ ->
                    binding.editPeso.text = null
                    binding.editIdade.text = null
                    binding.textResultadoMl.text = null
                    binding.textMinutos.text = getText(R.string.text_minutos)
                    binding.textHora.text = getText(R.string.text_hora)
                }
                .setNegativeButton("Cancelar") { _, _ -> }
                .show()
        }
    }

    private fun editTextIsEmpty() : Boolean {
        return (binding.editPeso.text.toString().isEmpty() || binding.editIdade.text.toString().isEmpty())
    }

    fun initLembrete() {
        var horaAtual = 0
        var minutoAtual = 0
        binding.buttomDefinirLembrente.setOnClickListener {
            calendario = Calendar.getInstance()
            horaAtual = calendario.get(Calendar.HOUR_OF_DAY)
            minutoAtual = calendario.get(Calendar.MINUTE)

            timePickerDialog = TimePickerDialog(this, {timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                binding.textHora.text = String.format("%02d", hourOfDay)
                binding.textMinutos.text = String.format("%02d", minutes)
            }, horaAtual, minutoAtual, true)
            timePickerDialog.show()
        }
    }

    fun initAlarme() {
        binding.buttomAlarme.setOnClickListener {
            if (!binding.textHora.text.toString().isEmpty() && !binding.textMinutos.text.toString().isEmpty()) {
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, binding.textHora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_HOUR, binding.textMinutos.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarme_mensagem))
                startActivity(intent)

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }

            }
        }
    }


}