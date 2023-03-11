package br.com.dio.appbeberagua.model

class CalcularIngestaoDiaria {

    companion object {
        private val ML_JOVEM = 40.0
        private val ML_ADULTO = 35.0
        private val ML_IDOSO = 30.0
        private val ML_MAIS_DE_66_ANOS = 25.0

        fun calcularTotalML(peso: Double, idade: Int): Double {
            val resultadoML: Double = when {
                idade <= 17 -> peso * ML_JOVEM
                idade <= 55 -> peso * ML_ADULTO
                idade <= 65 -> peso * ML_IDOSO
                else -> peso * ML_MAIS_DE_66_ANOS
            }
            return resultadoML
        }
    }

}