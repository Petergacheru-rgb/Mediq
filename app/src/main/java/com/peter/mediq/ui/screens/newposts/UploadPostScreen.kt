package com.peter.mediq.ui.screens.newposts

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.room.*
import coil.compose.rememberAsyncImagePainter
import com.peter.mediq.Navigation.ROUTE_HOME
import com.peter.mediq.Navigation.ROUTE_NOTIFICATION
import com.peter.mediq.Navigation.ROUTE_SETTING
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.*

// --- UNCHANGED ROOM DATABASE & VIEWMODEL CODE ---
@Entity
data class EmergencyPost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val location: String,
    val severity: String,
    val imageUri: String?
)

@Dao
interface EmergencyPostDao {
    @Insert
    suspend fun insert(post: EmergencyPost)
    @Update
    suspend fun update(post: EmergencyPost)
    @Delete
    suspend fun delete(post: EmergencyPost)
    @Query("SELECT * FROM EmergencyPost ORDER BY id DESC")
    fun getAllPosts(): kotlinx.coroutines.flow.Flow<List<EmergencyPost>>
}

@Database(entities = [EmergencyPost::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emergencyPostDao(): EmergencyPostDao
}

class EmergencyViewModel(private val dao: EmergencyPostDao) : androidx.lifecycle.ViewModel() {
    var posts by mutableStateOf(listOf<EmergencyPost>())
        private set

    init {
        // Initial data load
        refreshPosts()
    }

    fun addPost(post: EmergencyPost) = viewModelScope.launch { dao.insert(post) }
    fun deletePost(post: EmergencyPost) = viewModelScope.launch { dao.delete(post) }
    fun updatePost(post: EmergencyPost) = viewModelScope.launch { dao.update(post) }

    // New function to refresh data from the database
    fun refreshPosts() = viewModelScope.launch {
        dao.getAllPosts().collect {
            posts = it
        }
    }
}

// --- Composable Screen with New Design and Fixes ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPostScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(context, AppDatabase::class.java, "emergency-db")
            .fallbackToDestructiveMigration().build()
    }
    val viewModel = remember { EmergencyViewModel(db.emergencyPostDao()) }
    val scope = rememberCoroutineScope() // Get a CoroutineScope

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var severity by remember { mutableStateOf("Low") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var editingPostId by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { imageUri = saveImageToInternalStorage(context, it) }
    }

    // --- Start of New Color Scheme ---
    val primaryLight = Color(0xFFF0F2F5)
    val accentRed = Color(0xFFE7190A)
    val cardColor = Color.White
    val darkText = Color(0xFF333333)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🚨 Emergency Admin", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = accentRed,
                    titleContentColor = Color.White
                ),
                actions = {
                    // Update onClick to call the refreshPosts function
                    IconButton(onClick = { scope.launch { viewModel.refreshPosts() } }) {
                        Icon(Icons.Default.Refresh, tint = Color.White, contentDescription = "Refresh posts")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = cardColor, tonalElevation = 8.dp) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = accentRed) },
                    selected = true,
                    onClick = { navController.navigate(ROUTE_HOME) }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.Gray) },
                    selected = false,
                    onClick = { navController.navigate(ROUTE_SETTING) }
                )
            }
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(primaryLight, cardColor)
                    )
                )
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // --- Form Section ---
                Text("Create / Edit Post", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = darkText)

                OutlinedTextField(
                    value = title, onValueChange = { title = it },
                    label = { Text("Title", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Menu, contentDescription = null, tint = accentRed) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentRed,
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = accentRed,
                        focusedLabelColor = accentRed,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                OutlinedTextField(
                    value = description, onValueChange = { description = it },
                    label = { Text("Description", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, tint = accentRed) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentRed,
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = accentRed,
                        focusedLabelColor = accentRed,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                OutlinedTextField(
                    value = location, onValueChange = { location = it },
                    label = { Text("Location", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Place, contentDescription = null, tint = accentRed) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentRed,
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = accentRed,
                        focusedLabelColor = accentRed,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                Box {
                    OutlinedTextField(
                        value = severity, onValueChange = {}, label = { Text("Severity", color = Color.Gray) }, readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true },
                        leadingIcon = { Icon(Icons.Default.Warning, contentDescription = null, tint = accentRed) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentRed,
                            unfocusedBorderColor = Color.LightGray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = accentRed,
                            focusedLabelColor = accentRed,
                            unfocusedLabelColor = Color.Gray
                        )
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        listOf("Low", "Medium", "High", "Critical", "Immediate", "Extreme").forEach { level ->
                            val color = when (level) {
                                "Low" -> Color.Green
                                "Medium" -> Color.Yellow
                                "High" -> Color.Red.copy(alpha = 0.8f)
                                "Critical" -> Color.Red
                                "Immediate" -> Color.Red.copy(alpha = 0.9f)
                                else -> Color.Red
                            }
                            DropdownMenuItem(
                                text = { Text(level, color = color, fontWeight = FontWeight.Bold) },
                                onClick = { severity = level; expanded = false }
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .border(2.dp, Color.LightGray, RoundedCornerShape(20.dp))
                        .clickable { pickImageLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri == null) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Person, contentDescription = "Add Photo", tint = accentRed, modifier = Modifier.size(48.dp))
                            Spacer(Modifier.height(8.dp))
                            Text("Add Photo", color = accentRed, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Button(
                    onClick = {
                        val post = EmergencyPost(editingPostId ?: 0, title, description, location, severity, imageUri?.toString())
                        if (editingPostId != null) {
                            viewModel.updatePost(post)
                            editingPostId = null
                        } else {
                            viewModel.addPost(post)
                        }
                        title = ""; description = ""; location = ""; severity = "Low"; imageUri = null
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(8.dp, RoundedCornerShape(28.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = accentRed)
                ) {
                    Text(
                        if (editingPostId != null) "Update Post" else "Post Emergency Alert",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.2f))

                // --- View Posts Section ---
                Text("All Emergency Posts", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = darkText)
                Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                    viewModel.posts.forEach { post ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp, RoundedCornerShape(16.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = { viewModel.deletePost(post) },
                                        onDoubleTap = {
                                            editingPostId = post.id
                                            title = post.title
                                            description = post.description
                                            location = post.location
                                            severity = post.severity
                                            imageUri = post.imageUri?.let { Uri.parse(it) }
                                        }
                                    )
                                },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = cardColor)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .clip(CircleShape)
                                            .background(
                                                when (post.severity) {
                                                    "Low" -> Color.Green
                                                    "Medium" -> Color(0xFFFFA000)
                                                    "High" -> Color.Red
                                                    "Critical" -> Color(0xFFB71C1C)
                                                    "Immediate" -> Color(0xFFC62828)
                                                    else -> Color.Red
                                                }
                                            )
                                    )
                                    Text(post.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = darkText)
                                }
                                Spacer(Modifier.height(4.dp))
                                Text(post.description, style = MaterialTheme.typography.bodyMedium, color = darkText.copy(alpha = 0.8f))
                                Spacer(Modifier.height(8.dp))
                                Text("📍 Location: ${post.location}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                Text("⚠️ Severity: ${post.severity}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                post.imageUri?.let {
                                    Image(
                                        painter = rememberAsyncImagePainter(it),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .padding(top = 8.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- UNCHANGED HELPER FUNCTION ---
fun saveImageToInternalStorage(context: Context, uri: Uri): Uri {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val file = File(context.filesDir, "emergency_${UUID.randomUUID()}.jpg")
    val outputStream: OutputStream = file.outputStream()
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()
    return Uri.fromFile(file)
}