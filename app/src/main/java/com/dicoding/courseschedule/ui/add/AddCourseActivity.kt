package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private var startTime: String = ""
    private var endTime: String = ""
    private lateinit var view: View
    private lateinit var viewModel: AddCourseViewModel
    private lateinit var courseEditText: TextInputEditText
    private lateinit var daySpinner: Spinner
    private lateinit var lecturerEditText: TextInputEditText
    private lateinit var noteEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        courseEditText = findViewById(R.id.ed_course_name)
        daySpinner = findViewById(R.id.spinner_day)
        lecturerEditText = findViewById(R.id.ed_lecturer)
        noteEditText = findViewById(R.id.ed_note)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]

        viewModel.saved.observe(this) { event ->
            if (event.getContentIfNotHandled() == true) {
                onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(this, "Time cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                saveCourse()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveCourse() {
        val courseName = courseEditText.text.toString().trim()
        val day = daySpinner.selectedItemPosition
        val lecturer = lecturerEditText.text.toString().trim()
        val note = noteEditText.text.toString().trim()

        if (courseName.isNotEmpty()) {
            viewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
            finish()
        } else {
            Toast.makeText(this, "Course name cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

    fun showStartTimePicker(view: View) {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, "startPicker")
        this.view = view
    }

    fun showEndTimePicker(view: View) {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, "endPicker")
        this.view = view
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        when (tag) {
            "startPicker" -> {
                findViewById<TextView>(R.id.tv_start_time).text = timeFormat.format(calendar.time)
                startTime = timeFormat.format(calendar.time)
            }
            "endPicker" -> {
                findViewById<TextView>(R.id.tv_end_time).text = timeFormat.format(calendar.time)
                endTime = timeFormat.format(calendar.time)
            }
        }
    }
}