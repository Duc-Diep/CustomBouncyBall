package com.ducdiep.bouncyball

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.*
import kotlin.math.abs


class Ball(val pos: Int, val colorBall: Int,var mX:Float,var mY:Float) {
    val initX = mX
    val initY = mY
    var direction = 1
    var callBack: ((Float,Float) -> Unit)? = null
//    val mPaint = Paint().apply {
//        color = colorBall
//        style = Paint.Style.FILL_AND_STROKE
//        strokeWidth = 10F
//        strokeCap = Paint.Cap.ROUND
//        isAntiAlias = true
//    }
//    private var isAutoRun = false
//    private var isStart = true
//
//    private var radius: Float = 0F
//    private var initCoordinatesX = 0
//    private var initCoordinatesY = 0

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val mWidth = MeasureSpec.getSize(widthMeasureSpec) / numBalls
//        val mHeight = MeasureSpec.getSize(heightMeasureSpec)
//        initCoordinatesY = mHeight / 2
//        initCoordinatesX = mWidth / 2
//        radius = mWidth / 3F
//        mX = initCoordinatesX
//        mY = initCoordinatesY
//        setMeasuredDimension(mWidth, mHeight)
//    }
//
//    override fun onDraw(canvas: Canvas?) {
//        drawBall(canvas)
//        if (!isAutoRun && pos == 0) {
//            autoRun()
//            isAutoRun = true
//        }
//    }

//    private fun drawBall(canvas: Canvas?) {
//        canvas?.drawCircle(mX.toFloat(), mY.toFloat(), radius, mPaint)
//        invalidate()
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (pos == 0) {
//            when (event?.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    Log.e("size", event.y.toString())
//                    jobReset?.cancel()
////                    jobAutoRun?.cancel()
//                }
//
//                MotionEvent.ACTION_MOVE -> {
//                    mY = event.y.toInt()
//                    callBack?.invoke(mY)
//                    invalidate()
//                }
//
//                MotionEvent.ACTION_UP -> {
//                    reset(event.y.toInt())
//                }
//            }
//            return true
//        } else {
//            return true
//        }
//    }

//    private var jobReset: Job? = null
//    private var jobAutoRun: Job? = null
//    private fun reset(currenY: Int) {
//        val direction = if (currenY > initCoordinatesY) -1 else 1
//        jobReset = CoroutineScope(Dispatchers.Default).launch {
//            delay(TIME_RESET_DELAY)
//            while (abs(mY - initCoordinatesY) >= DEFAULT_SPEED) {
//                mY += DEFAULT_SPEED * direction
//                callBack?.invoke(mY)
//                delay(TIME_STEP_DELAY)
//            }
//            mY += abs(mY - initCoordinatesY) * direction
//            Log.d("Reset", "reset: $mY, $initCoordinatesY")
//            callBack?.invoke(mY)
//            autoRun()
//        }
//
//    }
//
//    private fun autoRun() {
//        jobAutoRun = CoroutineScope(Dispatchers.Default).launch {
//            var direction = -1
//            Log.d("Start Auto run", "autoRun: ")
//            Log.d("Start Auto run", "mY: $mY, Y:$initCoordinatesY")
//            if (isStart) {
//                isStart = false
//            } else {
//                delay(TIME_RESET_DELAY)
//            }
//            while (mY < initCoordinatesY + 200 && mY > initCoordinatesY - 200) {
//                if (mY >= initCoordinatesY + 190) {
//                    direction = -1
//                } else if (mY <= initCoordinatesY - 190) {
//                    direction = 1
//                }
//                mY += direction * AUTO_SPEED
//                callBack?.invoke(mY)
//                Log.d("mY", "autoRun: $mY")
//                delay(TIME_STEP_DELAY)
//            }
//        }
//    }

    fun setCallback(action: (Float,Float) -> Unit) {
        callBack = action
    }

    fun moveBall(newX: Float,newY: Float) {
        mX = newX
        mY = newY
    }

}