@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableStateOf(HomeTab.EDITS) }
    var projects by remember { mutableStateOf<List<Project>>(emptyList()) }

    val context = LocalContext.current

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            context.contentResolver.takePersistableUriPermission(
                selectedUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            projects = projects + createProjectFromUri(selectedUri)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GreenBackground(modifier = Modifier.matchParentSize())
        SlidingSpinningSquircles(modifier = Modifier.matchParentSize())

        Column(modifier = Modifier.fillMaxSize()) {
            HomeTabRow(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            when (selectedTab) {
                HomeTab.EDITS -> EditsTabContent(
                    projects = projects,
                    onCreateProject = { videoPickerLauncher.launch("video/*") }
                )
                HomeTab.IMAGE -> { /* Image tab content */ }
                HomeTab.AUDIO -> { /* Audio tab content */ }
            }
        }
    }
}
