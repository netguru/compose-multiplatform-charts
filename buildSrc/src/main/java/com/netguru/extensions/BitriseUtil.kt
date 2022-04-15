package com.netguru.extensions

object BitriseUtil {
    private fun isCi() = System.getenv("CI") == "true"
    private fun isPr() = System.getenv("PR") == "true"
    private fun getBuildNumber() = System.getenv("BITRISE_BUILD_NUMBER").toInt()
    private fun isBitrise() = isCi() || isPr()
    fun getBuildVersionCode() = if (isBitrise()) getBuildNumber() else null
}
