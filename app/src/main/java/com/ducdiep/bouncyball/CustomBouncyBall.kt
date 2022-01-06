package com.ducdiep.bouncyball

import android.R.attr
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import android.widget.GridLayout
import kotlinx.coroutines.*
import kotlin.math.abs


class CustomBouncyBall : View {
    private var listBalls = mutableListOf<Ball>()
    private var ballsCount = DEFAULT_BALLS_COUNT
    private var initX = 0
    private var initY = 0
    private var mWidth = 0
    private var mHeight = 0
    private var radius = 0F
    private var currentState = STATE_AUTO_BOUNCY
    private var jobMoveTo: Job? = null
    private var jobBouncy: Job? = null
    private var jobFollow: Job? = null
    private var jobReset: Job? = null
    val mPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 10F
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomBouncyBall, 0, 0).apply {
            ballsCount = getInt(R.styleable.CustomBouncyBall_ballsCount, DEFAULT_BALLS_COUNT)
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
        initY = mHeight / 2
        initX = mWidth / 2
        radius = mWidth / 3F / ballsCount
        initBalls(ballsCount)
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        listBalls.forEach { ball ->
            drawBalls(canvas, ball)
        }
        if (currentState == STATE_AUTO_BOUNCY) {
            autoBouncyBalls()
        }
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            ACTION_DOWN -> {
                jobBouncy?.cancel()
                jobMoveTo?.cancel()
                jobFollow?.cancel()
                jobReset?.cancel()
                currentState = STATE_FOLLOW
//                listBalls.forEach { ball ->
//                    moveTo(ball, event.x, event.y)
//                }
            }
            ACTION_MOVE -> {
                followFirstBall(event.x, event.y)
            }
            ACTION_UP -> {
                currentState = STATE_RESET
                reset()
            }
        }
        return true
    }

    private fun reset() {
        if (currentState == STATE_RESET) {
            jobReset = CoroutineScope(Dispatchers.Default).launch {
                Log.d("TAG", "jobReset: ")
                delay(TIME_DELAY_RESET)
                listBalls.forEach { ball ->
                    moveTo(ball, ball.initX, ball.initY)
                }
                jobMoveTo?.join()
                currentState = STATE_AUTO_BOUNCY
            }
        }
    }

    private fun followFirstBall(newX: Float, newY: Float) {
        listBalls[0].mX = newX
        listBalls[0].mY = newY
        jobFollow = CoroutineScope(Dispatchers.Default).launch {
            Log.d("TAG", "jobFollow: ")
            for (i in 1 until listBalls.size) {
                delay(TIME_STEP_FOLLOW)
                listBalls[i].mX = newX
                listBalls[i].mY = newY
            }
        }
    }

    private fun drawBalls(canvas: Canvas?, ball: Ball) {
        mPaint.color = ball.colorBall
        canvas?.drawCircle(
            ball.mX, ball.mY, radius, mPaint
        )
    }

    private fun initBalls(ballsCount: Int) {
        listBalls.clear()
        for (i in 0 until ballsCount) {
            val centerXY = calculatePosition(i)
            val ball = Ball(i, getRandomColor(), centerXY.first, centerXY.second)
            listBalls.add(ball)
        }
    }

    private fun calculatePosition(pos: Int): Pair<Float, Float> {
        val cX = ((mWidth - (2 * pos + 1) * (mWidth / 2 / ballsCount))).toFloat()
        val cY = ((mHeight / 2)).toFloat()
        return Pair(cX, cY)
    }

    private fun autoBouncyBalls() {
        Log.d("TAG", "jobAutoBouncy: ")
        val firstBall = listBalls[0]
        firstBall.mY += DEFAULT_SPEED * (firstBall.direction)
        if (abs(firstBall.mY - initY / 2.toFloat()) < DEFAULT_SPEED)
            firstBall.direction = 1
        if (abs(firstBall.mY - 3 * initY / 2) < DEFAULT_SPEED)
            firstBall.direction = -1
        for (i in 1 until listBalls.size) {
            val ball = listBalls[i]
            val y = listBalls[i - 1].mY
            jobBouncy = CoroutineScope(Dispatchers.Default).launch {
                delay(TIME_CHAIN_STEP_DELAY)
                ball.mY = y
            }
        }
    }

    private fun moveTo(ball: Ball, newX: Float, newY: Float) {
        val stepX = (newX - ball.mX) / 10
        val stepY = (newY - ball.mY) / 10
        jobMoveTo = CoroutineScope(Dispatchers.Default).launch {
            Log.d("TAG", "jobMoveTo: ")
            for (i in 0..8) {
                delay(TIME_STEP_MOVE_DELAY)
                ball.mX += stepX
                ball.mY += stepY
            }
            ball.mX = newX
            ball.mY = newY
        }
    }

    private fun getRandomColor(): Int {
        listColor.shuffle()
        return listColor[0]
    }

}