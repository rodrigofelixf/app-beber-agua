package br.com.dio.appbeberagua

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.dio.appbeberagua.databinding.ActivityMainBinding
import br.com.dio.appbeberagua.model.CalcularIngestaoDiaria

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar!!.hide()
    }

    override fun onStart() {
        super.onStart()
        calcularAgua()
        redefinirDados()
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
                }
                .setNegativeButton("Cancelar") { _, _ -> }
                .show()
        }
    }

    private fun editTextIsEmpty() : Boolean {
        return (binding.editPeso.text.toString().isEmpty() || binding.editIdade.text.toString().isEmpty())
    }


}