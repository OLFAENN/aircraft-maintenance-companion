package com.olfa.aircraftmaintenance.presentation.navigation

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"

    const val EQUIPMENT_LIST = "equipment_list"
    const val EQUIPMENT_DETAIL = "equipment_detail/{equipmentId}"
    const val INSPECTION = "inspection/{equipmentId}"

    const val PROFILE = "profile"
    const val PENDING_INSPECTIONS = "pending_inspections"
    const val INCIDENTS = "incidents"
    const val SYNC_STATUS = "sync_status"

    fun equipmentDetailRoute(equipmentId: String): String {
        return "equipment_detail/$equipmentId"
    }

    fun inspectionRoute(equipmentId: String): String {
        return "inspection/$equipmentId"
    }
}