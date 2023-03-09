package com.plub.presentation.ui.main.plubing.schedule.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.plub.domain.model.vo.schedule.ScheduleVo
import com.plub.presentation.R
import com.plub.presentation.databinding.BottomSheetDeleteQuestionBinding
import com.plub.presentation.databinding.BottomSheetScheduleDetailBinding
import com.plub.presentation.util.TimeFormatter
import com.plub.presentation.util.serializable
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BottomSheetScheduleDetail : BottomSheetDialogFragment() {
    private val binding: BottomSheetScheduleDetailBinding by lazy {
        BottomSheetScheduleDetailBinding.inflate(layoutInflater)
    }

    companion object {
        private const val SCHEDULE_VO = "SCHEDULE_VO"

        fun newInstance(
            scheduleVo: ScheduleVo,
            okButtonClickEvent: ((calendarId: Int) -> Unit)? = null,
            noButtonClickEvent: ((calendarId: Int) -> Unit)? = null
        ) = BottomSheetScheduleDetail().apply {
            this.okButtonClickEvent = okButtonClickEvent
            this.noButtonClickEvent = noButtonClickEvent
            arguments = Bundle().apply {
                putSerializable(SCHEDULE_VO, scheduleVo)
            }
        }
    }

    private val scheduleVo: ScheduleVo by lazy {
        arguments?.serializable(SCHEDULE_VO) ?: ScheduleVo()
    }
    private var okButtonClickEvent: ((calendarId: Int) -> Unit)? = null
    private var noButtonClickEvent: ((calendarId: Int) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            textViewTitle.text = scheduleVo.title
            setTextViewMonth(textViewMonth, scheduleVo)
            textViewDate.text = getTextViewDate(scheduleVo)
            textViewTime.text = getTextViewTime(scheduleVo)
            setLocation(textViewLocation, imageViewLocation, scheduleVo)

//            val profileList =
//                scheduleVo.calendarAttendList.calendarAttendList.map { it.profileImage }
//            plubingScheduleProfileAdapter.submitList(profileList)


            buttonYes.setOnClickListener {
                okButtonClickEvent?.let { it(scheduleVo.calendarId) }
                dismiss()
            }

            buttonNo.setOnClickListener {
                noButtonClickEvent?.let { it(scheduleVo.calendarId) }
                dismiss()
            }
        }
    }

    private fun setTextViewMonth(textViewMonth: TextView, item: ScheduleVo) {
        val month = TimeFormatter.getIntMonthFromyyyyDashmmDashddFormat(item.startedAt)
        textViewMonth.text = binding.root.context.getString(R.string.word_birth_month,month.toString())
    }

    private fun getTextViewDate(item: ScheduleVo): String {
        val day = TimeFormatter.getStringDayFromyyyyDashmmDashddFormat(item.startedAt)
        return binding.root.context.getString(R.string.word_birth_day, day)
    }

    private fun getTextViewTime(item: ScheduleVo): String {
        val startTime = TimeFormatter.get_ah_colon_mm(item.startTime)
        val endTime = TimeFormatter.get_ah_colon_mm(item.endTime)
        return binding.root.context.getString(R.string.word_middle_hyphen, startTime, endTime)
    }

    private fun setLocation(textViewLocation: TextView, imageViewLocation: ImageView, item: ScheduleVo) {
        if(item.placeName.isEmpty()) {
            textViewLocation.visibility = View.GONE
            imageViewLocation.visibility = View.GONE
        } else textViewLocation.text = item.placeName
    }
}