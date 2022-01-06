package com.ducdiep.bouncyball

import android.graphics.Color

const val STATE_AUTO_BOUNCY = 0
const val STATE_FOLLOW = 1
const val STATE_RESET = 2
const val TIME_STEP_MOVE_DELAY = 10L
const val DEFAULT_SPEED = 15
const val TIME_DELAY_RESET = 2000L
const val AUTO_SPEED = 10
const val TIME_CHAIN_STEP_DELAY = 100L
const val TIME_STEP_FOLLOW = 200L
const val DEFAULT_BALLS_COUNT = 6
val listColor = mutableListOf(
    Color.BLUE,
    Color.GREEN,
    Color.BLACK,
    Color.GRAY,
    Color.RED,
    Color.YELLOW
)
