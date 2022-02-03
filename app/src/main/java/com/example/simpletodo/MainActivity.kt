package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apache.commons.io.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils.writeLines
import org.apache.commons.io.IOUtils.writeLines
import com.example.simpletodo.TaskItemAdapter as TaskItemAdapter
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // 1. Remove item from the list
                listOfTasks.removeAt(position)

                // 2. Notify the adapter that data has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }


        loadItems()

        //Detect when the user clicks the add button
//        findViewById<Button>(R.id.button).setOnClickListener{//id is button, object type is Button. id can change but not object type
            //Code is executed when Object with the ID is clicked
//            Log.i("Shiva", "User Clicked on Add Button")
//        listOfTasks.add("Do Laundry")
//        listOfTasks.add("Go Gym")
        // Lookup recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // initialize contact
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // attach adapter to recyclerView to populate items
        recyclerView.adapter = adapter
        // set layout manager to position layout
        recyclerView.layoutManager = LinearLayoutManager(this)


        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        findViewById<Button>(R.id.button).setOnClickListener{
            // 1. Grab the text user inputted into the EditText in activity_main.xml
            val userInputtedTask = inputTextField.text.toString()

            // 2. Add the String to list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)
            // Notify the adapter that data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // 3. Reset the text field
            inputTextField.setText("")

            saveItems()

        }

    }
    // Save the data that user inputted by writing and reading from a file

    // Get the file we need
    fun getDataFile(): File {
        // every line is going to represent a specific task in list of tasks
        return File(filesDir, "data.txt")

    }

        // Create a method to get the file we need

        // Load items by reading every line in the file
    fun loadItems() {
            try {
                listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }

    }


        // Save items by writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

}