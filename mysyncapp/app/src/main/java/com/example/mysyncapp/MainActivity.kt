package com.example.mysyncapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mysyncapp.data.AppDatabase
import com.example.mysyncapp.data.entity.SyncItem
import com.example.mysyncapp.ui.theme.MysyncappTheme
import com.example.mysyncapp.worker.SyncWorker
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = AppDatabase.getInstance(applicationContext)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSyncNow = findViewById<Button>(R.id.btnSyncNow)

        btnAdd.setOnClickListener {
            lifecycleScope.launch {
                val newItem = SyncItem(title = "Titulo prueba", body = "Contenido de prueba")
                db.syncItemDao().insert(newItem)
                val work = OneTimeWorkRequestBuilder<SyncWorker>()
                    .setConstraints(SyncWorker.buildConstraints())
                    .build()
                WorkManager.getInstance(applicationContext).enqueue(work)
            }
        }

        btnSyncNow.setOnClickListener {
            val work = OneTimeWorkRequestBuilder<SyncWorker>()
                .setConstraints(SyncWorker.buildConstraints())
                .build()
            WorkManager.getInstance(applicationContext).enqueue(work)
        }
    }
}
