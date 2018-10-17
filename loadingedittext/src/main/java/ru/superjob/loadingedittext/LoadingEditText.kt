package ru.superjob.loadingedittext

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.IntegerRes
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText

class LoadingEditText : EditText {

    private var actionClickListener: ActionClickListener? = null
    private lateinit var animatedProgressDrawable: AnimatedRightDrawable
    private lateinit var staticDrawable: StaticRightDrawable

    private var lockingField: Boolean = false
    private var loadingInProgress: Boolean = false

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.LoadingEditText, defStyle, 0)
        lockingField = a.getBoolean(R.styleable.LoadingEditText_lockingField, false)

        val loadingAnimation = a.getResourceId(R.styleable.LoadingEditText_customLoadAnimation, R.drawable.ic_loading)
        animatedProgressDrawable = AnimatedRightDrawable(context, loadingAnimation)

        val styleResIconRight = if (a.hasValue(R.styleable.LoadingEditText_android_drawableEnd)) R.styleable.LoadingEditText_android_drawableEnd
        else R.styleable.LoadingEditText_android_drawableRight

        val rightDrawable = a.getResourceId(styleResIconRight, R.drawable.ic_near_me_gray)
        staticDrawable = StaticRightDrawable(context, rightDrawable)

        a.recycle()

        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, staticDrawable.getDrawable(), null)

        setOnTouchListener(View.OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                    if (!loadingInProgress) {
                        setLoading(true)
                        actionClickListener?.onClicked()
                    }
                    return@OnTouchListener true
                }
            }
            return@OnTouchListener false
        })

    }

    private fun setState(state: DrawableState) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, state.getDrawable(), null)
        state.run()
    }

    fun setRightDrawable(@IntegerRes drawable: Int) {
        staticDrawable = StaticRightDrawable(context, drawable)
    }

    fun setLoading(loading: Boolean) {
        loadingInProgress = loading
        if (loading) {
            setState(animatedProgressDrawable)
        } else {
            setState(staticDrawable)
        }
        isEnabled = !(lockingField && loading)
    }

    fun setCustomLoadingDrawable(@DrawableRes animatedVector: Int) {
        animatedProgressDrawable = AnimatedRightDrawable(context, animatedVector)
    }

    interface DrawableState {
        fun getDrawable(): Drawable?
        fun run()
    }

    private class AnimatedRightDrawable(context: Context, drawableRes: Int) : DrawableState {
        private var animatedVector: AnimatedVectorDrawableCompat? = null

        init {
            val drawable = AppCompatResources.getDrawable(context, drawableRes)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (drawable is AnimatedVectorDrawable) {
                    animatedVector = AnimatedVectorDrawableCompat.create(context, drawableRes)
                }

            } else {
                if (drawable is AnimatedVectorDrawableCompat) {
                    animatedVector = drawable
                }
            }
            if (animatedVector == null) {
                throw IllegalStateException("Drawable type must be AnimatedVector!")
            }

        }

        override fun getDrawable(): AnimatedVectorDrawableCompat? {
            return animatedVector
        }

        override fun run() {
            animatedVector?.start()
        }
    }


    private class StaticRightDrawable(context: Context, drawable: Int) : DrawableState {
        private val staticRightDrawable = ContextCompat.getDrawable(context, drawable)

        override fun run() {
            //nothing
        }

        override fun getDrawable(): Drawable? {
            return staticRightDrawable
        }

    }

    fun setActionClickListener(actionClickListener: ActionClickListener) {
        this.actionClickListener = actionClickListener
    }

    fun setActionClickListener(action: () -> Unit) {
        this.actionClickListener = object : ActionClickListener {
            override fun onClicked() {
                action.invoke()
            }
        }
    }

    interface ActionClickListener {
        fun onClicked()
    }
}