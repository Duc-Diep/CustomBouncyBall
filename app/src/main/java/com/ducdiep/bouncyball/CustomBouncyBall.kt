package com.ducdiep.bouncyball

import android.R.attr
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CustomBouncyBall : GridLayout {
    private var listBalls = mutableListOf<Ball>()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        var ballsCount = 0
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomBouncyBall, 0, 0).apply {
            ballsCount = getInt(R.styleable.CustomBouncyBall_ballsCount, DEFAULT_BALLS_COUNT)
            columnCount = ballsCount
            rowCount = 1
            recycle()
        }
        initBalls(ballsCount)
    }

    private fun initBalls(ballsCount: Int) {
        for (i in 0 until ballsCount) {
            val ball = Ball(i, ballsCount, getRandomColor(), context)
            addView(ball)
            listBalls.add(ball)
        }
        listBalls[0].setCallback { newY ->
            listBalls.forEach { ball ->
                startChainBouncy(ball, newY)
            }
        }
    }

    private fun startChainBouncy(ball: Ball, newY: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            delay(TIME_CHAIN_STEP_DELAY * ball.pos)
            if (ball.pos > 0) {
                ball.moveBall(newY)
            }
        }
    }

    private fun getRandomColor(): Int {
        listColor.shuffle()
        return listColor[0]
    }

}