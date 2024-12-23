package com.dsatm.ayurvi

import android.app.AlarmManager
import android.app.PendingIntent
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.content.Intent
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddBlogsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddBlogsFragment : Fragment() {
    private lateinit var timePicker: TimePicker
    private lateinit var setAlarmButton: Button
    private lateinit var messageEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_blogs, container, false)

        timePicker = view.findViewById(R.id.timePicker)
        setAlarmButton = view.findViewById(R.id.setAlarmButton)
        messageEditText = view.findViewById(R.id.messageEditText)

        setAlarmButton.setOnClickListener {
            setAlarm()
        }

        return view
    }

    private fun setAlarm() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
        calendar.set(Calendar.MINUTE, timePicker.minute)
        calendar.set(Calendar.SECOND, 0)

        val message = messageEditText.text.toString().trim()
        if (message.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a message", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(requireContext(), AlarmReceiver::class.java).apply {
            putExtra("ALARM_MESSAGE", message)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(
            requireContext(),
            "Alarm set for ${timePicker.hour}:${timePicker.minute} with message: $message",
            Toast.LENGTH_SHORT
        ).show()
    }
}