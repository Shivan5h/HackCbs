package com.example.smarcityv1.DocumetSummerizer



class DocumentRepository {
    private val api = RetrofitInstance.api

    suspend fun simplifyLegalText(legalText: String): SimplifiedTextResponse {
        val request = LegalTextRequest(legal_text = legalText)
        return api.simplifyLegalText(request)
    }
}