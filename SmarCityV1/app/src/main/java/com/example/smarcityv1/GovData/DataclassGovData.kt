// InflationData.kt
package com.example.smarcityv1.GovData

data class InflationData(
    val financial_year: String,
    val cpi_c_inflation_: Double
)

data class ApiResponse(
    val records: List<InflationData>
)