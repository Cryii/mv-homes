package dev.spozap.mvhomes.commands

sealed class Permissions(val permission: String) {
    data object SethomePermissions : Permissions("mvhomes.sethome")
    data object HomesPermissions : Permissions("mvhomes.homes")
}