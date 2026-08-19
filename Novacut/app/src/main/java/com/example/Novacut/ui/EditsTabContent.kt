@Composable
fun EditsTabContent(
    projects: List<Project>,
    onCreateProject: () -> Unit
) {
    if (projects.isEmpty()) {
        EmptyEditsState(onCreateClick = onCreateProject)
    } else {
        ProjectList(projects)
    }
}
