enum class HomeTab(val label: String) {
    EDITS("Edits"),
    IMAGE("Image"),
    AUDIO("Audio")
}

@Composable
fun HomeTabRow(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = HomeTab.entries
    val selectedIndex = tabs.indexOf(selectedTab)

    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = Color.White,
        edgePadding = 16.dp,
        indicator = { tabPositions ->
            if (selectedIndex < tabPositions.size) {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                    color = Color.White
                )
            }
        },
        divider = {} // skip default divider, it'll fight visually with the blob layer
    ) {
        tabs.forEach { tab ->
            Tab(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                text = { Text(tab.label) }
            )
        }
    }
}
