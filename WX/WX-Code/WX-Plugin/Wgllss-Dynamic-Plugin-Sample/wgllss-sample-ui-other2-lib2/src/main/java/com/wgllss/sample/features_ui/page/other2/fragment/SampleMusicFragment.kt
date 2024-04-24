package com.wgllss.sample.features_ui.page.other2.fragment

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.wgllss.core.adapter.BasePagerAdapter
import com.wgllss.core.ex.dpToPx
import com.wgllss.core.ex.finishActivity
import com.wgllss.core.ex.getIntToDip
import com.wgllss.core.ex.loadUrl
import com.wgllss.core.units.ResourceUtils
import com.wgllss.sample.feature_system.music.exoplayerimpl.ExoPlayerUtils.timestampToMSS
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.other2.viewmodel.PlayModel
import com.wgllss.ssmusic.features_system.extensions.albumArtUri
import com.wgllss.ssmusic.features_system.extensions.title

class SampleMusicFragment : BasePluginFragment<PlayModel>("fragment_play") {
    private lateinit var layout_play_bg: View
    private lateinit var img_back: ImageView
    private lateinit var mater_music_name: TextView
    private lateinit var tv_current_time: TextView
    private lateinit var tv_total_time: TextView
    private lateinit var view_pager: ViewPager
    private lateinit var sb_progress: SeekBar
    private lateinit var iv_mode: ImageView
    private lateinit var iv_prev: ImageView
    private lateinit var iv_next: ImageView
    private lateinit var iv_play: ImageView
    private lateinit var pb_load: ProgressBar

    private val views by lazy { mutableListOf<View>() }
    private var cdAnimator: ValueAnimator? = null
    private var pointAnimator: ValueAnimator? = null
    private val lin by lazy { LinearInterpolator() }

    lateinit var iv_center: ShapeableImageView
    lateinit var img_circle: ImageView
    lateinit var iv_point: ImageView
    lateinit var cd_layout: View

    override fun activitySameViewModel() = true

    override fun findView(context: Context, containerView: View) {
        img_back = findViewByID("img_back")
        mater_music_name = findViewByID("mater_music_name")
        tv_current_time = findViewByID("tv_current_time")
        tv_total_time = findViewByID("tv_total_time")
        tv_current_time = findViewByID("tv_current_time")
        sb_progress = findViewByID("sb_progress")
        iv_prev = findViewByID("iv_prev")
        iv_play = findViewByID("iv_play")
        iv_next = findViewByID("iv_next")
        layout_play_bg = findViewByID("layout_play_bg")
        view_pager = findViewByID("view_pager")
        iv_mode = findViewByID("iv_mode")
        pb_load = findViewByID("pb_load")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPage()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sb_progress.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    viewModel.seek(seekBar.progress.toLong())
                }
            })
        initPointAnimat()
        initCDAnimat()

        viewModel.nowPlaying.observe(viewLifecycleOwner) {
            mater_music_name.text = it!!.title
            iv_center.loadUrl(it.albumArtUri)
            Glide.with(this).asBitmap()
                .load(it.albumArtUri)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        resource?.let { it ->
                            Palette.from(it).generate { p ->
                                p?.lightMutedSwatch?.let { s ->
                                    layout_play_bg.setBackgroundColor(s.rgb)
                                    mater_music_name.setTextColor(s.titleTextColor)
                                    tv_total_time.setTextColor(s.bodyTextColor)
                                    tv_current_time.setTextColor(s.bodyTextColor)
                                }
                            }
                        }
                    }
                })
        }

        viewModel.playbackState.observe(viewLifecycleOwner) {
            when (it.state) {
                PlaybackStateCompat.STATE_BUFFERING -> {
                    pb_load.visibility = View.VISIBLE
                    iv_play.visibility = View.GONE
                }
                PlaybackStateCompat.STATE_PLAYING -> {
                    pb_load.visibility = View.GONE
                    iv_play.visibility = View.VISIBLE
                    if (iv_point.rotation == -40f) {
                        iv_play.isSelected = true
                        viewModel.isPlaying = true
                        startPointAnimat(-40f, 0f)
                    }
                    it.extras?.getLong(MediaMetadataCompat.METADATA_KEY_DURATION)?.let { d ->
                        tv_total_time.text = timestampToMSS(requireContext(), d)
                        sb_progress.max = d.toInt()
                    }
                }
                PlaybackStateCompat.STATE_PAUSED -> {
                    if (pb_load.visibility != View.GONE) {
                        pb_load.visibility = View.GONE
                    }
                    if (iv_play.visibility != View.VISIBLE) {
                        iv_play.visibility = View.VISIBLE
                    }
                    iv_play.isSelected = false
                    viewModel.isPlaying = false
                    if (iv_point.rotation == 0f) {
                        startPointAnimat(0f, -40f)
                    }
                }
            }
        }
        viewModel.mediaPosition.observe(viewLifecycleOwner) {
            sb_progress.progress = it.toInt()
            tv_current_time.text = timestampToMSS(requireContext(), it)
        }

        iv_prev.setOnClickListener {
            viewModel.onPlayPrevious(it)
        }
        iv_next.setOnClickListener {
            viewModel.onPlayNext(it)
        }
        iv_play.setOnClickListener {
            viewModel.onPlay(it)
        }
        img_back.setOnClickListener {
            activity?.let { it.finishActivity() }
        }
    }

    override fun onDetach() {
        super.onDetach()
        cdAnimator?.removeAllUpdateListeners()
        cdAnimator?.removeAllListeners()
        pauseCDanimat()
        cdAnimator = null
        pointAnimator?.removeAllUpdateListeners()
        pointAnimator?.removeAllListeners()
        pointAnimator?.cancel()
        pointAnimator = null
    }

    private fun initViewPage() {
        val layoutID = resourcesPlugin.getIdentifier("music_cd_layout", "layout", packageName)
        val coverView: View = LayoutInflater.from(context).inflate(resourcesPlugin.getLayout(layoutID), null)
        iv_point = findViewByID(coverView, "iv_point")
        cd_layout = findViewByID(coverView, "cd_layout")
        iv_center = findViewByID(coverView, "iv_center2")
        img_circle = findViewByID(coverView, "img_circle")
        iv_point.rotation = -40f
        views.add(coverView)
        val pagerAdapter = BasePagerAdapter(views)
        view_pager.adapter = pagerAdapter

        ResourceUtils.setImageDrawable(resources, "icon_chanpian", SkinContains.packageNameHost, img_circle)
        ResourceUtils.setImageDrawable(resources, "icon_play_point", SkinContains.packageNameHost, iv_point)
        ResourceUtils.setImageDrawable(resources, "icon_cd_default_bg", SkinContains.packageNameHost, iv_center)


        iv_center.apply {
            shapeAppearanceModel = ShapeAppearanceModel.builder().apply {
                setAllCorners(RoundedCornerTreatment())
                setAllCornerSizes(context.getIntToDip(85f))
            }.build()
        }
    }

    /**
     * 初始化指针动画
     */
    private fun initPointAnimat() {
        iv_point.pivotX = requireContext().dpToPx(17.0f)
        iv_point.pivotY = requireContext().dpToPx(15.0f)
        pointAnimator = ValueAnimator.ofFloat(0f, 0f)
        pointAnimator?.apply {
            setTarget(iv_point)
            repeatCount = 0
            duration = 300
            interpolator = lin
            addUpdateListener { animation ->
                val current = animation.animatedValue as Float
                iv_point.rotation = current
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    if (!viewModel.isPlaying) {
                        pauseCDanimat()
                    }
                }

                override fun onAnimationEnd(animation: Animator) {
                    if (viewModel.isPlaying) {
                        resumeCDanimat()
                    }
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })
        }
    }

    /**
     * 开始指针动画
     *
     * @param from
     * @param end
     */
    private fun startPointAnimat(from: Float, end: Float) {
        if (pointAnimator != null) {
            if (from < end) {
                if (!viewModel.isPlaying) {
                    return
                }
            } else {
                if (viewModel.isPlaying) {
                    return
                }
            }
            pointAnimator!!.setFloatValues(from, end)
            pointAnimator!!.start()
        }
    }

    /**
     * 初始化CD动画
     */
    private fun initCDAnimat() {
        cdAnimator = ValueAnimator.ofFloat(cd_layout.rotation, 360f + cd_layout.rotation)
        cdAnimator?.apply {
            setTarget(cd_layout)
            repeatCount = ValueAnimator.INFINITE
            duration = 15000
            interpolator = lin
            addUpdateListener { animation ->
//                setCdRodio(current)  todo
                cd_layout.rotation = animation.animatedValue as Float
            }
        }
    }

    /**
     * 开始cd动画
     */
    private fun resumeCDanimat() {
        cdAnimator?.takeIf { !it.isRunning }?.run {
            setFloatValues(cd_layout.rotation, 360f + cd_layout.rotation)
            start()
        }
    }

    /**
     * 暂停CD动画
     */
    private fun pauseCDanimat() {
        cdAnimator?.takeIf { it.isRunning }?.run { cancel() }
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.run {
            setImageDrawable(skinRes, "ic_baseline_arrow_back_24", SkinContains.packageName, img_back)
            setImageDrawable(skinRes, "play_mode_level_list", SkinContains.packageName, iv_mode)
            setImageDrawable(skinRes, "ic_play_btn_prev", SkinContains.packageName, iv_prev)
            setImageDrawable(skinRes, "ic_play_btn_next", SkinContains.packageName, iv_next)
            setImageDrawable(skinRes, "play_btn_play_pause_selector", SkinContains.packageName, iv_play)
            sb_progress.progressDrawable = getPluginDrawable(skinRes, "seek_bar_progress_style", SkinContains.packageName)
            sb_progress.thumb = getPluginDrawable(skinRes, "seekbar_thumb", SkinContains.packageName)
            pb_load.indeterminateDrawable = getPluginDrawable(skinRes, "play_load_drawable", SkinContains.packageName)

            setBackgroundDrawable(skinRes, "circle_gray_border", SkinContains.packageName, img_circle)
        }
    }
}